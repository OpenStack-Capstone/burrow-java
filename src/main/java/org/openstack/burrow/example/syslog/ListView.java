package org.openstack.burrow.example.syslog;

import javax.swing.*;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ListView<E> extends ArrayList<E> implements ListModel {
    protected ListView<E> viewed;
    protected List<ListDataListener> listeners;

    protected ListView() {
        this.viewed = null;
        listeners = new ArrayList<ListDataListener>();
    }

    protected ListView(ListView<E> viewed) {
        this.viewed = viewed;
        listeners = new ArrayList<ListDataListener>();
    }

    private ListView<E> getBaseList() {
        if (viewed == null) return this;
        return viewed.getBaseList();
    }

    private void removalNotify(int i) {
        ListDataEvent event = new ListDataEvent(this, ListDataEvent.INTERVAL_REMOVED, i, i);

        for (ListDataListener ldl : listeners) ldl.intervalRemoved(event);
    }

    protected boolean addElement(E e) {
        boolean b = super.add(e);
        int i = size() - 1;
        ListDataEvent event = new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, i, i);

        for (ListDataListener ldl : listeners) ldl.intervalAdded(event);
        return b;
    }

    public ListView<E> getListView(Filter<E> filter) {
        ListView<E> view = new ListView<E>(this);

        for (E e: this) {
            if (filter.accept(e)) view.addElement(e);
        }

        return view;
    }

    public int getSize() {
        return super.size();
    }

    public E getElementAt(int i) {
        return super.get(i);
    }

    public void addListDataListener(ListDataListener listDataListener) {
        listeners.add(listDataListener);
    }

    public void removeListDataListener(ListDataListener listDataListener) {
        listeners.remove(listDataListener);
    }

    @Override
    protected void removeRange(int lower, int upper) {
        for (int i = lower; i < upper; i++) {
            remove(i);
        }
    }

    @Override
    public E remove(int i) {
        if (viewed != null) viewed.remove(getElementAt(i));
        E result = super.remove(i);
        removalNotify(i);
        return result;
    }

    @Override
    public boolean remove(Object o) {
        if (viewed != null) viewed.remove(o);
        int index = indexOf(o);
        boolean result = super.remove(o);
        removalNotify(index);
        return result;
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> objects) {
        return super.removeAll(objects);
    }

    //Everything else from ArrayList would modify the list in a way we don't want, don't allow it.
    @Override
    public E set(int i, E e) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void trimToSize() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object clone() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean add(E e) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(int i, E e) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(Collection<? extends E> es) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection<?> objects) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(int i, Collection<? extends E> es) {
        throw new UnsupportedOperationException();
    }

}
