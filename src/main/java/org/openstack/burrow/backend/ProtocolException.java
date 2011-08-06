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

package org.openstack.burrow.backend;

/**
 * ProtocolException is a subclass of BurrowException.  This type of exception is thrown if an
 * issue arising in processing the request such as the response from the server is not parsable.
 * This is the parent of HttpProtocolException.
 */
public class ProtocolException extends BurrowException {

    /**
     * Constructor for ProtocolException that takes no arguments
     */
  public ProtocolException() {
  }

    /**
     * Constructor for ProtocolException that takes a message as an argument
     * @param s A string that holds a message to be printed if exception is thrown
     */
  public ProtocolException(String s) {
    super(s);
  }

    /**
     * Constructor for ProtocolException that takes a message and a cause as arguments
     * @param s A string that holds a message to be printed if exception is thrown
     * @param cause A Throwable object that signifies the cause of the exception being raised
     */
  public ProtocolException(String s, Throwable cause) {
    super(s, cause);
  }

    /**
     * Constructor for ProtocolException that takes a message and a cause as arguments
     * @param cause A Throwable object that signifies the cause of the exception being raised
     */
  public ProtocolException(Throwable cause) {
    super(cause);
  }

}
