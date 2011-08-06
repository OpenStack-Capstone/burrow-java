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
 * DeleteAccounts is a request object
 */
public class DeleteAccounts implements AccountListRequest {
  private String detail;
  private Long limit;
  private String marker;

    /**
     * Constructor for DeleteAccounts
     */
  public DeleteAccounts() {
    this.marker = null;
    this.limit = null;
    this.detail = null;
  }

    /**
     * Private Constructor for DeleteAccounts
     * @param marker
     * @param limit
     * @param detail
     */
  private DeleteAccounts(String marker, Long limit, String detail) {
    this.marker = marker;
    this.limit = limit;
    this.detail = detail;
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
  public DeleteAccounts withDetail(String detail) {
    return new DeleteAccounts(marker, limit, detail);
  }

    /**
     *
     * @param limit
     * @return
     */
  public DeleteAccounts withLimit(long limit) {
    return new DeleteAccounts(marker, limit, detail);
  }

    /**
     *
     * @param marker
     * @return
     */
  public DeleteAccounts withMarker(String marker) {
    return new DeleteAccounts(marker, limit, detail);
  }
}
