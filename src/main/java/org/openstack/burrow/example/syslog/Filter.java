package org.openstack.burrow.example.syslog;

public interface Filter<E> {
    public boolean accept(E item);
}
