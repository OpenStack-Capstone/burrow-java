package org.openstack.burrow.example.syslog;

import org.openstack.burrow.backend.Backend;
import org.openstack.burrow.backend.http.Http;
import org.openstack.burrow.client.Account;
import org.openstack.burrow.client.Queue;

import javax.swing.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        SyslogHUD hud = new SyslogHUD();
        hud.embedIn(frame);

        String accountName = "teamd", queueName = "syslog", server = "localhost";
        int port = 8080;

        Queue queue = new Queue(new Account(accountName), queueName);
        Backend backend = new Http(server, port);
        ConcurrentLinkedQueue<LogEntry> channel = new ConcurrentLinkedQueue<LogEntry>();

        SyslogClient client = new SyslogClient(queue, backend, channel, 10);
        new Thread(client).start();

        frame.setVisible(true);

        while (true) {
            while (!channel.isEmpty()) {
                LogEntry entry = channel.poll();
                if (entry != null) {
                    hud.addEntry(entry);
                }
            }
        }
    }
}
