/*
 * Copyright (C) 2011 OpenStack LLC.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package org.openstack.burrow.backend.memory;

import junit.framework.TestCase;
import java.util.Iterator;

public class HashedListTest extends TestCase {

    public void testPutGet() {
        HashedList<String, Integer> list = new HashedList<String, Integer>();

        list.put("1", 10000);
        list.put("2", 2);
        list.put("3", 3);
        list.put("4", 4);
        list.put("1", 1);

        for (int i = 1; i <= 4; i++) {
            if (list.get("" + i) != i)
                fail("Wrong value associated with key '" + i + "'.");
        }

        if (list.get("DNE") != null)
            fail("Expected null for get of nonexistent key, found: " + list.get("DNE"));
    }

    public void testUpdate() {
        HashedList<String, Integer> list = new HashedList<String, Integer>();

        list.put("N", 1);
        list.update("N", 2);

        if (list.get("N") != 2)
            fail("Expected 'N'->2 post update, found 'N'->" + list.get("N"));

        list.update("DNE", 666);

        if (list.get("DNE") != 666)
            fail("Expected 'DNE'->666 post update, found 'DNE'->" + list.get("DNE"));
    }

    public void testRemove() {
        HashedList<String, Integer> list = new HashedList<String, Integer>();

        list.put("1", 1);
        list.put("2", 2);
        list.put("3", 3);
        list.put("4", 4);


        Integer removed = list.remove("1");

        if (removed != 1)
            fail("Expected remove to return 1, found: " + removed);

        if (list.get("1") != null)
            fail("Expected null for get of removed key, found: " + list.get("1"));

    }

    public void testIsEmpty() {
        HashedList<String, Integer> list = new HashedList<String, Integer>();

        if (!list.isEmpty())
            fail("Newly created list reports nonempty.");

        list.put("1", 1);

        if (list.isEmpty())
            fail("Nonempty list reports empty.");


        list.put("2", 2);
        list.put("3", 3);
        list.remove("1");
        list.remove("2");
        list.remove("3");

        if (!list.isEmpty())
            fail("List reports nonempty after removal of all elements.");

    }

    public void testNewIterator() {
        HashedList<String, Integer> list = new HashedList<String, Integer>();
        list.put("1", 1);
        list.put("2", 2);
        list.put("3", 3);
        list.put("4", 4);

        Iterator<Entry<String, Integer>> iterator = list.newIterator();


        for (int i = 1; iterator.hasNext(); i++) {
            Entry<String, Integer> e = iterator.next();

            if (i != e.getValue())
                fail("Value '" + e.getValue() + "' was retrieved out of order, expected '" + i + "'.");

            if (i == 3) iterator.remove();

        }

        if (list.get("3") != null)
            fail("Expected null from get of removed key, found: '" + list.get("3") + "'.");
    }

    public void testNewIteratorFrom() {
        HashedList<String, Integer> list = new HashedList<String, Integer>();

        list.put("1", 1);
        list.put("2", 2);
        list.put("3", 3);
        list.put("4", 4);

        Iterator<Entry<String, Integer>> iterator = list.newIteratorFrom("1");


        for (int i = 2; iterator.hasNext(); i++) {
            Entry<String, Integer> e = iterator.next();

            if (i != e.getValue())
                fail("Value '" + e.getValue() + "' was retrieved out of order, expected '" + i + "'.");

            if (i == 3) iterator.remove();

        }

        if (list.get("3") != null)
            fail("Expected null from get of removed key, found: '" + list.get("3") + "'.");
    }
}
