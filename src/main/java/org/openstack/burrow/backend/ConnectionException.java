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
 * ConnectionException is a subclass of BurrowException.  This type of exception is thrown if the
 * a connection error occurred while accessing the server.  This is the parent class
 * of HttpConnection.
 */
public class ConnectionException extends BurrowException {

    /**
     * Constructor for ConnectionException that takes no arguments
     */
  public ConnectionException() {
  }

    /**
     * Constructor for ConnectionException that takes a message as an argument
     * @param s A string that holds a message to be printed if exception is thrown
     */
  public ConnectionException(String s) {
    super(s);
  }

    /**
     * Constructor for ConnectionException that takes a message and a cause as arguments
     * @param s A string that holds a message to be printed if exception is thrown
     * @param cause A Throwable object that signifies the cause of the exception being raised
     */
  public ConnectionException(String s, Throwable cause) {
    super(s, cause);
  }

    /**
     * Constructor for ConnectionException that takes a message and a cause as arguments
     * @param cause A Throwable object that signifies the cause of the exception being raised
     */
  public ConnectionException(Throwable cause) {
    super(cause);
  }

}