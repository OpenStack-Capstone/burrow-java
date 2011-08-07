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

/**
 * DeleteAccounts is a request object that is executed by the client.  A DeleteAccounts object
 * has several filters including detail - the details of an object to return, limit -
 * the number of messages to process, and marker - a range of ids to match against.
 */
public class DeleteAccounts implements AccountListRequest {
  private String detail;
  private Long limit;
  private String marker;

    /**
     * Public Constructor for DeleteAccounts that takes no arguments
     */
  public DeleteAccounts() {
    this.marker = null;
    this.limit = null;
    this.detail = null;
  }

    /**
     * Private Constructor for DeleteAccounts that takes a marker, limit, and detail as arguments
     * @param marker A String denoting a range to limit matching of message Ids to
     * @param limit  A Long denoting the number of messages to match
     * @param detail A String denoting which attributes of a message to return
     */
  private DeleteAccounts(String marker, Long limit, String detail) {
    this.marker = marker;
    this.limit = limit;
    this.detail = detail;
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
     * Constructor for DeleteAccounts with a detail filter set
     * @param detail A String denoting which attributes of a message to return
     * @return A DeleteAccounts object with the detail set accordingly
     */
  public DeleteAccounts withDetail(String detail) {
    return new DeleteAccounts(marker, limit, detail);
  }

    /**
     * Constructor for DeleteAccounts with a limit filter set
     * @param limit A Long denoting the number of messages to match
     * @return A DeleteAccounts object with the limit set accordingly
     */
  public DeleteAccounts withLimit(long limit) {
    return new DeleteAccounts(marker, limit, detail);
  }

    /**
     * Constructor for DeleteAccounts with a marker filter set
     * @param marker A String denoting a range to limit matching of message Ids to
     * @return A DeleteAccounts object with the marker set accordingly
     */
  public DeleteAccounts withMarker(String marker) {
    return new DeleteAccounts(marker, limit, detail);
  }
}
