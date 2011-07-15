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

package org.openstack.burrow.client;

public class Message {
  // TODO Add more attributes.
  private String body;
  private Long hide;
  private String id;
  private Long ttl;

  protected Message(String id, String body, Long ttl, Long hide) {
    this.id = id;
    this.body = body;
    this.ttl = ttl;
    this.hide = hide;
  }

  public String getBody() {
    return body;
  }

  public long getHide() {
    return hide;
  }

  public String getId() {
    return id;
  }

  public long getTtl() {
    return ttl;
  }
}
