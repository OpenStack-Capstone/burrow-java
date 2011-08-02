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

package org.openstack.burrow.client.methods;

import org.openstack.burrow.client.Queue;

public class DeleteMessage implements SingleMessageRequest {
  private String id;
  private Boolean matchHidden;
  private Queue queue;

  public DeleteMessage(Queue queue, String id) {
    this.queue = queue;
    this.id = id;
    this.matchHidden = null;
  }

  private DeleteMessage(Queue queue, String id, Boolean matchHidden) {
    this.queue = queue;
    this.id = id;
    this.matchHidden = matchHidden;
  }

  public String getId() {
    return id;
  }

  public Boolean getMatchHidden() {
    return matchHidden;
  }

  public Queue getQueue() {
    return queue;
  }

  public DeleteMessage withMatchHidden(boolean matchHidden) {
    return new DeleteMessage(queue, id, matchHidden);
  }
}
