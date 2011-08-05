package org.openstack.burrow.example;

import org.apache.commons.codec.binary.Base64InputStream;
import org.apache.commons.codec.binary.Base64OutputStream;
import org.openstack.burrow.backend.AsyncBackend;
import org.openstack.burrow.backend.Backend;
import org.openstack.burrow.backend.CommandException;
import org.openstack.burrow.backend.ProtocolException;
import org.openstack.burrow.client.Account;
import org.openstack.burrow.client.Message;
import org.openstack.burrow.client.Queue;

import java.io.*;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class MergeSorter implements Runnable {
    private AsyncBackend back;
    private Queue queue;

    public MergeSorter(AsyncBackend back, String queue, String acct) {
        this.back = back;
        this.queue = new Queue(new Account(acct), queue);
    }

    public void run() {
        ArrayDeque<Message> jobs = new ArrayDeque<Message> ();
        while (true) {
            while (jobs.size() < 2) {
                Future<List<Message>> future =
                            back.executeAsync(queue.getMessages().withLimit(2l).withMatchHidden(false));//withhide?
                try {
                    jobs.addAll(future.get());
                } catch (InterruptedException ie) {
                    //derp
                } catch (ExecutionException ee) {
                    //derp
                }
            }

            int[] merged = merge(unpack(jobs.pop().getBody()), unpack(jobs.pop().getBody()));

            back.executeAsync(queue.createMessage(Arrays.hashCode(merged) + "+" + System.currentTimeMillis(), pack(merged)));
        }
    }


    //Method for packing/unpacking an array into a base64 encoded string
    //adapted from OscarRyz's sample code at
    //http://stackoverflow.com/questions/134492/how-to-serialize-an-object-into-a-string/134918#134918
    private String pack(int[] A) {
        try {
            ByteArrayOutputStream base = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(new Base64OutputStream(base));

            oos.writeObject(A);
            oos.close();
            return new String(base.toByteArray());
        } catch (IOException ioe) {
            //Impossible with memory output
            return null;
        }
    }

    private int[] unpack(String S) {
        try {
            ByteArrayInputStream base = new ByteArrayInputStream(S.getBytes());
            ObjectInputStream ois = new ObjectInputStream(new Base64InputStream(base));
            int[] result = (int[]) ois.readObject();
            ois.close();
            return result;
        } catch (ClassCastException cce) {
            System.err.println("Error: Malformed job");
            System.exit(1);
        } catch (IOException ioe) {
            //Shouldn't be possible from an in memory source
        } catch (ClassNotFoundException cnfe) {
            //Impossible, pretty sure int[] exists everywhere
        }
        throw new Error("Should not be reached.");
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
}
