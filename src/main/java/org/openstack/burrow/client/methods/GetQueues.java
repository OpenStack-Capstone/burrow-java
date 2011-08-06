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
 * GetQueues is a request object
 */
public class GetQueues implements QueueListRequest {
  private Account account;
  private Long limit;
  private String marker;

    /**
     * Constructor for GetQueues
     * @param account
     */
  public GetQueues(Account account) {
    this.account = account;
    this.marker = null;
    this.limit = null;
  }

    /**
     *
     * @param account
     * @param marker
     * @param limit
     */
  private GetQueues(Account account, String marker, Long limit) {
    this.account = account;
    this.marker = marker;
    this.limit = limit;
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
     * @param limit
     * @return
     */
  public GetQueues withLimit(long limit) {
    return new GetQueues(account, marker, limit);
  }

    /**
     *
     * @param marker
     * @return
     */
  public GetQueues withMarker(String marker) {
    return new GetQueues(account, marker, limit);
  }
}
