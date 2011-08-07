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
 * DeleteQueues is a request object that is executed by the client.  A DeleteQueues object
 * has several filters including the account to delete the queue from,
 * detail - the details of an object to return, limit - the number of messages to process,
 * and marker - a range of ids to match against.
 */
public class DeleteQueues implements QueueListRequest {
  private Account account;
  private String detail;
  private Long limit;
  private String marker;

    /**
     * Public Constructor for DeleteQueues that takes an Account as an argument
     * @param account The Account to delete the queues of
     */
  public DeleteQueues(Account account) {
    this.account = account;
    this.marker = null;
    this.limit = null;
    this.detail = null;
  }

    /**
     * Private Constructor for DeleteQueues that takes an Account, a marker, a limit, and a detail
     * as arguments
     * @param account The Account to delete the queues of
     * @param marker A String denoting a range to limit matching of message Ids to
     * @param limit  A Long denoting the number of messages to match
     * @param detail A String denoting which attributes of a message to return
     */
  private DeleteQueues(Account account, String marker, Long limit, String detail) {
    this.account = account;
    this.marker = marker;
    this.limit = limit;
    this.detail = detail;
  }

    /**
     * A getter function that returns the designated Account
     * @return An account from which the queues will be deleted
     */
  public Account getAccount() {
    return account;
  }

    /**
     * A getter function that will return which attributes of a message to be sent back on request
     * @return  A String containing the desired attributes
     */
  public String getDetail() {
    return detail;
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
     * Constructor for DeleteQueues with a detail filter set
     * @param detail A String denoting which attributes of a message to return
     * @return A DeleteQueues object with the detail set accordingly
     */
  public DeleteQueues withDetail(String detail) {
    return new DeleteQueues(account, marker, limit, detail);
  }

    /**
     * Constructor for DeleteQueues with a limit filter set
     * @param limit A Long denoting the number of messages to match
     * @return A DeleteQueues object with the limit set accordingly
     */
  public DeleteQueues withLimit(long limit) {
    return new DeleteQueues(account, marker, limit, detail);
  }

    /**
     * Constructor for DeleteQueues with a marker filter set
     * @param marker A String denoting a range to limit matching of message Ids to
     * @return A DeleteQueues object with the marker set accordingly
     */
  public DeleteQueues withMarker(String marker) {
    return new DeleteQueues(account, marker, limit, detail);
  }
}
