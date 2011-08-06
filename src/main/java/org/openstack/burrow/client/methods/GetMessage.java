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

/**
 * GetMessage is a request object
 */
public class GetMessage implements SingleMessageRequest {
  private String detail;
  private String id;
  private Long hide;
  private Boolean matchHidden;
  private Queue queue;
  private Long wait;
  private Long hide;

    /**
     * Constructor for GetMessage
     * @param queue
     * @param id
     */
  public GetMessage(Queue queue, String id) {
    this.queue = queue;
    this.id = id;
    this.hide = null;
    this.matchHidden = null;
    this.detail = null;
    this.wait = null;
  }

<<<<<<< HEAD
  private GetMessage(Queue queue, String id, Boolean matchHidden, String detail, Long wait, Long hide) {
=======
    /**
     * Private Constructor for GetMessage
     * @param queue
     * @param id
     * @param matchHidden
     * @param detail
     * @param wait
     */
  private GetMessage(Queue queue, String id, Long hide, Boolean matchHidden, String detail, Long wait) {
>>>>>>> e3c94ed0e8ea6fd09c1ff47bc9a9805cbb5abd03
    this.queue = queue;
    this.id = id;
    this.hide = hide;
    this.matchHidden = matchHidden;
    this.detail = detail;
    this.wait = wait;
    this.hide = hide;
  }

    /**
     *
     * @return
     */
  public String getDetail() {
    return detail;
  }

    /**
     *
     * @return
     */
  public String getId() {
    return id;
  }

<<<<<<< HEAD
    public Long getHide() {
        return hide;
    }

=======
    /**
     *
     * @return
     */
  public Long getHide() {
    return hide;
  }

    /**
     *
     * @return
     */
>>>>>>> e3c94ed0e8ea6fd09c1ff47bc9a9805cbb5abd03
  public Boolean getMatchHidden() {
    return matchHidden;
  }

    /**
     *
     * @return
     */
  public Queue getQueue() {
    return queue;
  }

    /**
     *
     * @return
     */
  public Long getWait() {
    return wait;
  }


    /**
     *
     * @param detail
     * @return
     */
  public GetMessage withDetail(String detail) {
<<<<<<< HEAD
    return new GetMessage(queue, id, matchHidden, detail, wait, hide);
  }

  public GetMessage withHide(long hide) {
    return new GetMessage(queue, id, matchHidden, detail, wait, hide);
=======
    return new GetMessage(queue, id, hide, matchHidden, detail, wait);
  }

    /**
     *
     * @param hide
     * @return
     */
  public GetMessage withHide(long hide) {
    return new GetMessage(queue, id, hide, matchHidden, detail, wait);
>>>>>>> e3c94ed0e8ea6fd09c1ff47bc9a9805cbb5abd03
  }

    /**
     *
     * @param matchHidden
     * @return
     */
  public GetMessage withMatchHidden(boolean matchHidden) {
<<<<<<< HEAD
    return new GetMessage(queue, id, matchHidden, detail, wait, hide);
=======
    return new GetMessage(queue, id, hide, matchHidden, detail, wait);
>>>>>>> e3c94ed0e8ea6fd09c1ff47bc9a9805cbb5abd03
  }

    /**
     *
     * @param wait
     * @return
     */
  public GetMessage withWait(long wait) {
<<<<<<< HEAD
    return new GetMessage(queue, id, matchHidden, detail, wait, hide);
=======
    return new GetMessage(queue, id, hide, matchHidden, detail, wait);
>>>>>>> e3c94ed0e8ea6fd09c1ff47bc9a9805cbb5abd03
  }
}
