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

/**
 * DeleteQueues is a request object
 */
public class DeleteQueues implements QueueListRequest {
  private Account account;
  private String detail;
  private Long limit;
  private String marker;

    /**
     * Constructor for DeleteQueues
     * @param account
     */
  public DeleteQueues(Account account) {
    this.account = account;
    this.marker = null;
    this.limit = null;
    this.detail = null;
  }

    /**
     * Private Constructor for DeleteQueues
     * @param account
     * @param marker
     * @param limit
     * @param detail
     */
  private DeleteQueues(Account account, String marker, Long limit, String detail) {
    this.account = account;
    this.marker = marker;
    this.limit = limit;
    this.detail = detail;
  }

    /**
     *
     * @return
     */
  public Account getAccount() {
    return account;
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
  public Long getLimit() {
    return limit;
  }

    /**
     *
     * @return
     */
  public String getMarker() {
    return marker;
  }

    /**
     *
     * @param detail
     * @return
     */
  public DeleteQueues withDetail(String detail) {
    return new DeleteQueues(account, marker, limit, detail);
  }

    /**
     *
     * @param limit
     * @return
     */
  public DeleteQueues withLimit(long limit) {
    return new DeleteQueues(account, marker, limit, detail);
  }

    /**
     *
     * @param marker
     * @return
     */
  public DeleteQueues withMarker(String marker) {
    return new DeleteQueues(account, marker, limit, detail);
  }
}
