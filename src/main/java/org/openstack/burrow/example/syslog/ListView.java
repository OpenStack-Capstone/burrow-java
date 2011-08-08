package org.openstack.burrow.example.syslog;

import javax.swing.*;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ListView<E> implements ListModel, Iterable<E> {
    protected ListView<E> viewed;
    protected List<ListDataListener> listeners;
    protected List<E> backing;

    protected ListView() {
        this.viewed = null;
        backing = Collections.synchronizedList(new ArrayList<E>());
        listeners = new ArrayList<ListDataListener>();
    }

    protected ListView(ListView<E> viewed) {
        this.viewed = viewed;
        listeners = viewed.getListeners();
        backing = Collections.synchronizedList(new ArrayList<E>());
    }

    private ListView<E> getBaseList() {
        if (viewed == null) return this;
        return viewed.getBaseList();
    }

    ArrayList<ListDataListener> getListeners() {
        return new ArrayList<ListDataListener>(listeners);
    }

    public Iterator<E> iterator() {
        return backing.iterator();
    }

    private class NotifyWorker extends SwingWorker<Object, Object> {
        private ListDataEvent event;

        private NotifyWorker(ListDataEvent event) {
            this.event = event;
        }

        @Override
        protected Object doInBackground() throws Exception {
            switch (event.getType()) {
                case ListDataEvent.INTERVAL_ADDED:
                    for (ListDataListener ldl : listeners) ldl.intervalAdded(event);
                case ListDataEvent.INTERVAL_REMOVED:
                    for (ListDataListener ldl : listeners) ldl.intervalRemoved(event);
                case ListDataEvent.CONTENTS_CHANGED:
                    for (ListDataListener ldl : listeners) ldl.contentsChanged(event);
            }
            return null;
        }
    }

    private void notify(ListDataEvent event) {
        NotifyWorker worker = new NotifyWorker(event);
        worker.execute();
        try {
            worker.get();
        } catch (Exception ignore) {
            //TODO: Something smarter?
        }
    }

    protected boolean addElement(E e) {
        boolean b = backing.add(e);
        int i = backing.size() - 1;
        notify(new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, i, i));

        return b;
    }

    public ListView<E> getListView(Filter<E> filter) {
        ListView<E> view = new ListView<E>(this);

        for (E e : backing) {
            if (filter.accept(e)) view.addElement(e);
        }

        return view;
    }

    public int getSize() {
        return backing.size();
    }

    public E getElementAt(int i) {
        return backing.get(i);
    }

    public void addListDataListener(ListDataListener listDataListener) {
        listeners.add(listDataListener);
    }

    public void removeListDataListener(ListDataListener listDataListener) {
        listeners.remove(listDataListener);
    }

    public E remove(int i) {
        if (viewed != null) viewed.removeNoNotify(getElementAt(i));
        E result = backing.remove(i);
        notify(new ListDataEvent(this, ListDataEvent.INTERVAL_REMOVED, i, i));
        return result;
    }

    public boolean remove(Object o) {
        if (viewed != null) viewed.removeNoNotify(o);
        int i = backing.indexOf(o);
        boolean result = backing.remove(o);
        notify(new ListDataEvent(this, ListDataEvent.INTERVAL_REMOVED, i, i));
        return result;
    }

    private boolean removeNoNotify(Object o) {
        if (viewed != null) viewed.removeNoNotify(o);
        boolean result = backing.remove(o);
        return result;
    }
}
