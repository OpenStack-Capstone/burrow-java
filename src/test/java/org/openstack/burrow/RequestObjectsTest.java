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

package org.openstack.burrow;

import org.junit.Test;
import org.openstack.burrow.backend.Backend;
import org.openstack.burrow.backend.CommandException;
import org.openstack.burrow.backend.ProtocolException;
import org.openstack.burrow.backend.http.Http;
import org.openstack.burrow.client.Account;
import org.openstack.burrow.client.Queue;
import org.openstack.burrow.client.methods.*;

import static org.mockito.Mockito.*;

public class RequestObjectsTest {

    /**
     * Unit test for the CreateMessage request object.  The goal is to see if the backend is
     * calling all the correct functions.
     */
    @Test
    public void testCreateMessage() throws CommandException, ProtocolException {
       Backend backend = new Http("localhost", 8080);
       Account account = new Account("acct");
       Queue queue = new Queue(account, "queue");
       CreateMessage cm = mock(CreateMessage.class);
       stub(cm.getId()).toReturn("msgId");
       stub(cm.getBody()).toReturn("msgBdy");
       stub(cm.getQueue()).toReturn(queue);
       stub(cm.getHide()).toReturn(0L);
       stub(cm.getTtl()).toReturn(0L);
       backend.execute(cm);
       verify(cm).getId();
       verify(cm).getBody();
       verify(cm).getQueue();
       verify(cm).getHide();
       verify(cm).getTtl();
    }

    /**
     * Unit test for the DeleteMessage request object.  The goal is to see if the backend is
     * calling all the correct functions.
     */
    @Test
    public void testDeleteMessage() throws CommandException, ProtocolException {
       Backend backend = new Http("localhost", 8080);
       Account account = new Account("acct");
       Queue queue = new Queue(account, "queue");
       DeleteMessage dm = mock(DeleteMessage.class);
       stub(dm.getId()).toReturn("msgId");
       stub(dm.getQueue()).toReturn(queue);
       backend.execute(dm);
       verify(dm).getId();
       verify(dm).getQueue();
    }

    /**
     * Unit test for the DeleteMessages request object.  The goal is to see if the backend is
     * calling all the correct functions.
     */
    @Test
    public void testDeleteMessages() throws CommandException, ProtocolException {
       Backend backend = new Http("localhost", 8080);
       Account account = new Account("acct");
       Queue queue = new Queue(account, "queue");
       DeleteMessages dms = mock(DeleteMessages.class);
       stub(dms.getDetail()).toReturn("detail");
       stub(dms.getQueue()).toReturn(queue);
       stub(dms.getMarker()).toReturn("marker");
       backend.execute(dms);
       verify(dms).getDetail();
       verify(dms).getQueue();
       verify(dms).getMarker();
    }

   /**
     * Unit test for the GetMessage request object.  The goal is to see if the backend is
     * calling all the correct functions.
     */
    @Test
    public void testGetMessage() throws CommandException, ProtocolException {
       Backend backend = new Http("localhost", 8080);
       Account account = new Account("acct");
       Queue queue = new Queue(account, "queue");
       GetMessage gm = mock(GetMessage.class);
       stub(gm.getId()).toReturn("msgId");
       stub(gm.getQueue()).toReturn(queue);
       stub(gm.getWait()).toReturn(100L);
       stub(gm.getDetail()).toReturn("detail");
       stub(gm.getMatchHidden()).toReturn(true);
       backend.execute(gm);
       verify(gm).getDetail();
       verify(gm).getQueue();
       verify(gm).getId();
       verify(gm).getMatchHidden();
       verify(gm).getWait();
    }

     /**
      * Unit test for the GetMessages request object.  The goal is to see if the backend is
      * calling all the correct functions.
      */
    @Test
    public void testGetMessages() throws CommandException, ProtocolException {
       Backend backend = new Http("localhost", 8080);
       Account account = new Account("acct");
       Queue queue = new Queue(account, "queue");
       GetMessages gms = mock(GetMessages.class);
       stub(gms.getMarker()).toReturn("marker");
       stub(gms.getQueue()).toReturn(queue);
       //stub(gms.getWait()).toReturn(100L);
       stub(gms.getDetail()).toReturn("detail");
       //stub(gms.getMatchHidden()).toReturn(true);
       backend.execute(gms);
       verify(gms).getDetail();
       verify(gms).getQueue();
       verify(gms).getMarker();
       //verify(gms).getMatchHidden();
       //verify(gms).getWait();
    }


   /**
    * Unit test for the UpdateMessage request object.  The goal is to see if the backend is
    * calling all the correct functions.
    */
   /*
   @Test
    public void testUpdateMessage() {
       Backend backend = new Http("localhost", 8080);
       Account account = new Account("acct");
       Queue queue = new Queue(account, "queue");
       UpdateMessage um = mock(UpdateMessage.class);
       stub(um.getTtl()).toReturn(100L);
       stub(um.getQueue()).toReturn(queue);
       stub(um.getWait()).toReturn(100L);
       stub(um.getDetail()).toReturn("detail");
       stub(um.getMatchHidden()).toReturn(true);
       stub(um.getId()).toReturn("msgId");
       backend.execute(um);
       verify(um).getDetail();
       verify(um).getQueue();
       verify(um).getId();
       verify(um).getMatchHidden();
       verify(um).getTtl();
       verify(um).getWait();
    }
    */
    /**
     * Unit test for UpdateMessages request object.  Checks if correct methods are called by
     * the backend.
     */
    /*
   @Test
    public void testUpdateMessages() {
       Backend backend = new Http("localhost", 8080);
       Account account = new Account("acct");
       Queue queue = new Queue(account, "queue");
       UpdateMessages ums = mock(UpdateMessages.class);
       stub(ums.getTtl()).toReturn(100L);
       stub(ums.getQueue()).toReturn(queue);
       stub(ums.getWait()).toReturn(100L);
       stub(ums.getDetail()).toReturn("detail");
       stub(ums.getMatchHidden()).toReturn(true);
       stub(ums.getMarker()).toReturn("marker");
       stub(ums.getHide()).toReturn(100L);
       stub(ums.getLimit()).toReturn(100L);
       backend.execute(ums);
       verify(ums).getDetail();
       verify(ums).getQueue();
       verify(ums).getHide();
       verify(ums).getMatchHidden();
       verify(ums).getTtl();
       verify(ums).getWait();
       verify(ums).getLimit();
    }
    */

}
