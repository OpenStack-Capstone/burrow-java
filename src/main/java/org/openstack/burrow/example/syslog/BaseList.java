package org.openstack.burrow.example.syslog;

public class BaseList<E> extends ListView<E> {
    public boolean add(E e) {
        return addElement(e);
    }

}
