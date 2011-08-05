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

import org.openstack.burrow.client.Queue;

public class GetMessages implements MessageListRequest {
    private String detail;
    private Long limit;
    private String marker;
    private Boolean matchHidden;
    private Queue queue;


    private Long hide;
    private Long wait;

    public GetMessages(Queue queue) {
        this.queue = queue;
        this.marker = null;
        this.limit = null;
        this.matchHidden = null;
        this.detail = null;
        this.wait = null;
    }

    public Long getHide() {
        return hide;
    }

    public GetMessages(Queue queue, String marker, Long limit, Boolean matchHidden, String detail, Long wait, Long hide) {
        this.detail = detail;
        this.limit = limit;
        this.marker = marker;
        this.matchHidden = matchHidden;
        this.queue = queue;
        this.hide = hide;
        this.wait = wait;
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

    public Boolean getMatchHidden() {
        return matchHidden;
    }

    public Queue getQueue() {
        return queue;
    }

    public Long getWait() {
        return wait;
    }

    public GetMessages withDetail(String detail) {
        return new GetMessages(queue, marker, limit, matchHidden, detail, wait, hide);
    }

    public GetMessages withLimit(long limit) {
        return new GetMessages(queue, marker, limit, matchHidden, detail, wait, hide);
    }

    public GetMessages withMarker(String marker) {
        return new GetMessages(queue, marker, limit, matchHidden, detail, wait, hide);
    }

    public GetMessages withMatchHidden(boolean matchHidden) {
        return new GetMessages(queue, marker, limit, matchHidden, detail, wait, hide);
    }

    public GetMessages withWait(long wait) {
        return new GetMessages(queue, marker, limit, matchHidden, detail, wait, hide);
    }

    public GetMessages withHide(long hide) {
        return new GetMessages(queue, marker, limit, matchHidden, detail, wait, hide);
    }
}
