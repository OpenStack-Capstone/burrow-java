package org.openstack.burrow.example.syslog;

import org.openstack.burrow.backend.*;
import org.openstack.burrow.client.Message;
import org.openstack.burrow.client.Queue;
import org.openstack.burrow.client.methods.DeleteMessages;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SyslogClient implements Runnable {
    public static final int DEF_WAITTIME = 10;

    private Queue syslog;
    private Backend back;
    private ConcurrentLinkedQueue<LogEntry> entries;
    private Logger log; //Who logs the loggers?
    private int consecutiveErrors;
    private int waitTime;
    private boolean keepGoing;
    private boolean running;

    public SyslogClient(Queue syslog, Backend back, ConcurrentLinkedQueue<LogEntry> entries, Logger log, int waitTime) {
        this.syslog = syslog;
        this.back = back;
        this.entries = entries;
        this.log = log;
        this.waitTime = waitTime;
    }

    public void run() {
        running = true;

        while (keepGoing) {
            List<Message> grabbed = null;
            try {
                grabbed = back.execute(new DeleteMessages(syslog).withWait(waitTime).withDetail("all"));
            } catch (ProtocolException pe) {
                consecutiveErrors++;
                log.log(Level.WARNING, "Attempt to retrieve log entries failed", pe);
                continue;

            } catch (ConnectionException ce) {
                consecutiveErrors++;
                log.log(Level.WARNING, "Connection attempt failed", ce);
                continue;

            } catch (QueueNotFoundException qe) {
                //To be expected, will happen any time we drain the queue
                //And nothing new arrives before our wait expires.
                continue;

            } catch (CommandException ce) {
                consecutiveErrors++;
                log.log(Level.WARNING, "Connection attempt failed", ce);
                continue;

            } catch (Exception e) {
                //Not reachable with the current BurrowException hierarchy,
                //barring something going seriously wrong.
                log.log(Level.SEVERE, "An unexpected error has occurred", e);
                throw new Error(e); //Per Bart: Crash early, crash often.
            }

            consecutiveErrors = 0;

            for (Message message : grabbed) {
                entries.add(munge(message));
            }
        }
        running = false;
        //cleanup?
    }

    public void halt() {
        if (running == false) throw new IllegalStateException("Attempt to halt an non-running thread");

        keepGoing = false;
    }

    public boolean isRunning() {
        return running;
    }

    public int getConsecutiveErrors() {
        return consecutiveErrors;
    }

    private LogEntry munge(Message msg) {
        return null;
    }
}
