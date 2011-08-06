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
 * GetAccounts is a request object
 */
public class GetAccounts implements AccountListRequest {
  private String detail;
  private Long limit;
  private String marker;

    /**
     * Public Constructor for GetAccounts
     */
  public GetAccounts() {
    this.marker = null;
    this.limit = null;
    this.detail = null;
  }

    /**
     * Private Constructor for GetAccounts
     */
  private GetAccounts(String marker, Long limit, String detail) {
    this.marker = marker;
    this.limit = limit;
    this.detail = detail;
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

  public GetAccounts withDetail(String detail) {
    return new GetAccounts(marker, limit, detail);
  }

  public GetAccounts withLimit(long limit) {
    return new GetAccounts(marker, limit, detail);
  }

  public GetAccounts withMarker(String marker) {
    return new GetAccounts(marker, limit, detail);
  }
}
