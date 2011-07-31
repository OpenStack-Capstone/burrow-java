package org.openstack.burrow.backend.memory;

import org.openstack.burrow.client.Message;
import org.openstack.burrow.client.MessageHiddenException;
import org.openstack.burrow.client.NoSuchMessageException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class MemoryQueue {
    public static final long DEFAULT_TTL = 600;
    private HashedList<String, MessageRecord> queue;

    private class MessageRecord extends Message {
            long lastTick;

            private MessageRecord(String id, String body, Long ttl, Long hide) {
                if (ttl == null) ttl = 0l;
				if (hide == null) hide = 0l;
				this.ttl = ttl == 0l ? DEFAULT_TTL : ttl; //No way to detect a null arg here due to autoboxing
                this.hide =  hide;
                this.body = body;
                this.id = id;


                lastTick = System.currentTimeMillis();
            }

            private void tick() {
                //The rest of burrow operates in seconds, no easy way to avoid it here.
                long now = System.currentTimeMillis() / 1000;

                ttl = ttl - (now - lastTick);
                if (ttl < 0) ttl = 0l;

                if (hide != 0) {
                    hide = hide - (now - lastTick);
                    if (hide < 0) hide = 0l;
                }
            }


            private void update(Long ttl, Long hide) {
                if (ttl != null) this.ttl = ttl;
                if (hide != null) this.hide = hide;
                tick();
            }
        }

        MemoryQueue() {
            queue = new HashedList<String, MessageRecord>();
        }

        synchronized void put(String messageId, String body, Long ttl, Long hide) {
            clean();
            queue.put(messageId, new MessageRecord(messageId, body, ttl, hide));
        }

        synchronized Message get(String messageId) {
            clean();
            MessageRecord message = queue.get(messageId);

            if (message == null) throw new NoSuchMessageException();

            if (message.getHide() != 0) throw new MessageHiddenException();

            return message;
        }

        synchronized List<Message> get(String marker, Long limit, Boolean matchHidden, Long wait) {
            clean();
            List<Message> messages = new ArrayList<Message>();

            Iterator<Entry<String, MessageRecord>> iter;
            if (marker != null) iter = queue.newIteratorFrom(marker);
            else iter = queue.newIterator();

            if (limit == null) limit = -1l;
            if (matchHidden == null) matchHidden = false;

            while ((limit != 0) && (iter.hasNext())) {
                MessageRecord msg = iter.next().getValue();
                if (matchHidden || (msg.getHide() == 0)) {
                    messages.add(msg);
                    limit--;
                }
            }

            return messages;
        }

        synchronized Message remove(String id) {
            clean();
            MessageRecord m = queue.remove(id);

            if (m == null) throw new NoSuchMessageException();

            return m;
        }

        synchronized List<Message> remove(String marker, Long limit, Boolean matchHidden, Long wait) {
            clean();
            List<Message> messages = new ArrayList<Message>();

            Iterator<Entry<String, MessageRecord>> iter;
            if (marker != null) iter = queue.newIteratorFrom(marker);
            else iter = queue.newIterator();

            if (limit == null) limit = -1l;
            if (matchHidden == null) matchHidden = false;

            while ((limit != 0) && (iter.hasNext())) {
                MessageRecord msg = iter.next().getValue();
                if (matchHidden || (msg.getHide() == 0)) {
                    messages.add(msg);
                    limit--;
                    iter.remove();
                }
            }

            return messages;
        }

        synchronized List<Message> update(String marker, Long limit, Boolean matchHidden, Long ttl, Long hide, Long wait) {
            clean();
            List<Message> messages = new ArrayList<Message>();

            Iterator<Entry<String, MessageRecord>> iter;
            if (marker != null) iter = queue.newIteratorFrom(marker);
            else iter = queue.newIterator();

            if (limit == null) limit = -1l;
            if (matchHidden == null) matchHidden = false;

            while ((limit != 0) && (iter.hasNext())) {
                MessageRecord msg = iter.next().getValue();
                if (matchHidden || (msg.getHide() == 0)) {
                    messages.add(msg);
                    limit--;
                    msg.update(ttl, hide);
                }
            }

            return messages;
        }

        synchronized Message update(String messageId, Long ttl, Long hide) {
            clean();

            MessageRecord msg = queue.get(messageId);
            if (msg == null) throw new NoSuchMessageException();

            msg.update(ttl, hide);

            return msg;
        }

        void clean() {
            Iterator<Entry<String, MessageRecord>> iter = queue.newIterator();

            while (iter.hasNext()) {
                MessageRecord msg = iter.next().getValue();

                msg.tick();

                if (msg.getTtl() == 0) iter.remove();
            }
        }
    }
