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

import org.openstack.burrow.client.Account;

public class DeleteQueues {
  private Account account;
  private String detail;
  private Long limit;
  private String marker;

  public DeleteQueues(Account account) {
    this.account = account;
    this.marker = null;
    this.limit = null;
    this.detail = null;
  }

  private DeleteQueues(Account account, String marker, Long limit, String detail) {
    this.account = account;
    this.marker = marker;
    this.limit = limit;
    this.detail = detail;
  }

  public Account getAccount() {
    return account;
  }

  public String getDetail() {
    return detail;
  }

  public Long getLimit() {
    return limit;
  }

  public String getMarker() {
    return marker;
  }

  public DeleteQueues withDetail(String detail) {
    return new DeleteQueues(account, marker, limit, detail);
  }

  public DeleteQueues withLimit(long limit) {
    return new DeleteQueues(account, marker, limit, detail);
  }

  public DeleteQueues withMarker(String marker) {
    return new DeleteQueues(account, marker, limit, detail);
  }
}
