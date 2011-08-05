package org.openstack.burrow.example.syslog;

import java.util.logging.Level;

public class LogEntry {
    private String process;
    private long timeMillis;
    private Level severity;
    private String body;
}
