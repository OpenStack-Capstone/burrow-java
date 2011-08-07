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
 * GetQueues is a request object that is executed by the client.  A GetQueues object
 * has several filters including the account to delete the queue from,
 * detail - the details of an object to return, limit - the number of messages to process,
 * and marker - a range of ids to match against.
 */
public class GetQueues implements QueueListRequest {
  private Account account;
  private Long limit;
  private String marker;

    /**
     * Public Constructor for GetQueues that takes an Account as an argument
     * @param account The Account to delete the queues of
     */
  public GetQueues(Account account) {
    this.account = account;
    this.marker = null;
    this.limit = null;
  }

    /**
     * Private Constructor for GetQueues that takes an Account, a marker, a limit, and a detail
     * as arguments
     * @param account The Account to delete the queues of
     * @param marker A String denoting a range to limit matching of message Ids to
     * @param limit  A Long denoting the number of messages to match
     */
  private GetQueues(Account account, String marker, Long limit) {
    this.account = account;
    this.marker = marker;
    this.limit = limit;
  }

    /**
     * A getter function that returns the designated Account
     * @return An account from which the queues will be deleted
     */
  public Account getAccount() {
    return account;
  }

    /**
     * A getter function that will return the number of matched messages to be sent back on request
     * @return  A Long signifying the determined limit
     */
  public Long getLimit() {
    return limit;
  }

    /**
     * A getter function that will return the range of message ids to be matched against
     * @return  A String of the message Id to match Ids after
     */
  public String getMarker() {
    return marker;
  }

    /**
     * Constructor for GetQueues with a limit filter set
     * @param limit A Long denoting the number of messages to match
     * @return A GetQueues object with the limit set accordingly
     */
  public GetQueues withLimit(long limit) {
    return new GetQueues(account, marker, limit);
  }

    /**
     * Constructor for GetQueues with a marker filter set
     * @param marker A String denoting a range to limit matching of message Ids to
     * @return A GetQueues object with the marker set accordingly
     */
  public GetQueues withMarker(String marker) {
    return new GetQueues(account, marker, limit);
  }
}
