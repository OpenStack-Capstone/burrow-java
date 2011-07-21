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

import java.util.List;
import org.openstack.burrow.backend.Backend;

public class DeleteQueues {
  private String account;
  private Backend backend;
  private String detail;
  private Long limit;
  private String marker;

  DeleteQueues(Backend backend, String account) {
    this.backend = backend;
    this.account = account;
    this.marker = null;
    this.limit = null;
    this.detail = null;
  }

  private DeleteQueues(Backend backend, String account, String marker, Long limit, String detail) {
    this.backend = backend;
    this.account = account;
    this.marker = marker;
    this.limit = limit;
    this.detail = detail;
  }

  public List<Queue> execute() {
    return backend.deleteQueues(account, marker, limit, detail);
  }

  public DeleteQueues matchLimit(Long limit) {
    return new DeleteQueues(backend, account, marker, limit, detail);
  }

  public DeleteQueues requestDetail(String detail) {
    return new DeleteQueues(backend, account, marker, limit, detail);
  }

  public DeleteQueues withMarker(String marker) {
    return new DeleteQueues(backend, account, marker, limit, detail);
  }
}
