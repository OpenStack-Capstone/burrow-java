package org.openstack.burrow.example.syslog;

import org.openstack.burrow.backend.Backend;
import org.openstack.burrow.backend.http.Http;
import org.openstack.burrow.client.Account;
import org.openstack.burrow.client.Queue;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Logger;

public class Console {
    private static final String USAGE = "Usage: burrow-syslog <server> <port> <account> <queue>";


    public static void main(String[] args) {
        if (args.length < 4) {
            System.err.println(USAGE);
            System.exit(1);
        }
        int port = -1;
        String server = args[0];
        String rawPort = args[1];
        String accountName = args[2];
        String queueName = args[3];


        try {
            port = Integer.parseInt(rawPort);
            if (port < 0 || 65535 < port) {
                System.err.println("Port is out of bounds (0-65535).");
                System.exit(2);
            }

        } catch (NumberFormatException nfe) {
            System.err.println("Could not parse '" + rawPort + "' as a port number");
            System.err.println(USAGE);
            System.exit(3);
        }

        Queue queue = new Queue(new Account(accountName), queueName);
        Backend backend = new Http(server, port);
        ConcurrentLinkedQueue<LogEntry> channel = new ConcurrentLinkedQueue<LogEntry>();

        SyslogClient client = new SyslogClient(queue, backend, channel, 10);
        new Thread(client).start();

        while (true) {
            while (!channel.isEmpty()) {
                LogEntry entry = channel.poll();
                if (entry != null) {
                    System.out.println(entry);
                }
            }

        }
    }
}
