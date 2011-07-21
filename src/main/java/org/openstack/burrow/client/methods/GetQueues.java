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

import java.util.List;

import org.openstack.burrow.backend.Backend;
import org.openstack.burrow.client.Queue;

public class GetQueues {
  private String account;
  private Backend backend;
  private Long limit;
  private String marker;

  public GetQueues(Backend backend, String account) {
    this.backend = backend;
    this.account = account;
    this.marker = null;
    this.limit = null;
  }

  private GetQueues(Backend backend, String account, String marker, Long limit) {
    this.backend = backend;
    this.account = account;
    this.marker = marker;
    this.limit = limit;
  }

  public List<Queue> execute() {
    return backend.getQueues(account, marker, limit);
  }

  public GetQueues matchLimit(long limit) {
    return new GetQueues(backend, account, marker, limit);
  }

  public GetQueues withMarker(String marker) {
    return new GetQueues(backend, account, marker, limit);
  }
}
