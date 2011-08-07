package org.openstack.burrow.example.syslog;

public class LogEntry {
    public static final int DATE_WIDTH = 15;
    private String time;
    private String host;
    private String tag;
    private String body;

    //TODO: Allow configuration of entry parsing via syslog format string
    //For now, assume RSYSLOG_TraditionalFileFormat
    public static LogEntry fromRawEntry(String raw) throws MalformedEntryException {
        String date = raw.substring(0, DATE_WIDTH);
        raw = raw.substring(DATE_WIDTH);

        String[] split = raw.trim().split(" ", 3);
        if (split.length < 3) throw new MalformedEntryException();

        return new LogEntry(date, split[0], split[1], split[2]);
    }

    public LogEntry(String time, String host, String tag, String body) {
        this.time = time;
        this.host = host;
        this.tag = tag;
        this.body = body;
    }

    public String toString() {
        return time + "//" + host + "//" + tag + "//" + body;
    }

    public String getTimeStamp() {
        return time;
    }

    public String getHost() {
        return host;
    }

    public String getTag() {
        return tag;
    }

    public String getBody() {
        return body;
    }
}
