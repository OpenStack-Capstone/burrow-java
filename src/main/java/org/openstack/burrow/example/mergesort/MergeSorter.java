package org.openstack.burrow.example.mergesort;

import org.openstack.burrow.backend.*;
import org.openstack.burrow.client.Account;
import org.openstack.burrow.client.Message;
import org.openstack.burrow.client.Queue;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class MergeSorter implements Runnable {
    public static int DEF_MAXFAILURES = 10, DEF_JOBTIMEOUT = 10;
    private AsyncBackend back;
    private Queue inQueue;
    private Queue outQueue;
    private long jobTimeout; //Time in seconds before message becomes available again.
    private int failed;
    private int maxFailures;

    public MergeSorter(AsyncBackend back, String inQueueId, String outQueueId, String accountId) {
        this.back = back;
        Account account = new Account(accountId);
        this.inQueue = new Queue(account, inQueueId);
        this.inQueue = new Queue(account, outQueueId);
        this.jobTimeout = DEF_JOBTIMEOUT;
        this.maxFailures = DEF_MAXFAILURES;
        this.failed = 0;
    }

    public void run() {
        ArrayDeque<Message> jobs = new ArrayDeque<Message>();
        while (true) {
            while (jobs.size() < 2) {
                Future<List<Message>> future =
                        back.executeAsync(inQueue.updateMessages().withLimit(2).withMatchHidden(false).withHide(jobTimeout));
                try {
                    jobs.addAll(future.get());
                } catch (InterruptedException ie) {
                    //Something is wrong, bail.
                    return;
                } catch (ExecutionException ee) {
                    failed++;
                    if (failed > maxFailures) return; //Probably something better to do here..
                }
            }

            int[] merged = merge(Encoder.unpack(jobs.pop().getBody()), Encoder.unpack(jobs.pop().getBody()));

            Future<Message> pushed = back.executeAsync(
                         outQueue.createMessage(Arrays.hashCode(merged) + "+" + System.currentTimeMillis(), Encoder.pack(merged)));

            try {
                pushed.get();
            } catch (InterruptedException ie) {
                return; //Because, that's why.
            } catch (ExecutionException ee) {
                //Message push failed, count on constituent jobs to timeout and unhide.
                //Don't want to unhide them manually in case another worker has already claimed them.
                failed++;
                if (failed > maxFailures) return; //Probably something better to do here..
            }
        }
    }

    private int[] merge(int[] A, int[] B) {
        int a = 0, b = 0, r = 0;
        int[] result = new int[A.length + B.length];

        while ((a < A.length) && (b < B.length)) {
            if (A[a] < B[b]) {
                result[r] = A[a];
                a++;
            } else {
                result[r] = B[b];
                b++;
            }
            r++;
        }

        while (a < A.length) {
            result[r] = A[a];
            r++;
            a++;
        }

        while (b < B.length) {
            result[r] = B[b];
            r++;
            b++;
        }

        return result;
    }

    public long getJobTimeout() {
        return jobTimeout;
    }

    public void setJobTimeout(long jobTimeout) {
        this.jobTimeout = jobTimeout;
    }
}