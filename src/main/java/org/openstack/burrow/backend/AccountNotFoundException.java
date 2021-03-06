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
 * AccountNotFoundException is a subclass of CommandException.  This type of exception is thrown if the
 * request was correctly formatted but could not be fulfilled as the requested Account does not
 * exist.
 */
public class AccountNotFoundException extends CommandException {

    /**
     * Constructor for AccountNotFoundException that takes no arguments
     */
  public AccountNotFoundException() {
  }

    /**
     * Constructor for AccountNotFoundException that takes a message as an argument
     * @param s A String that holds a message to be printed if exception is thrown
     */
  public AccountNotFoundException(String s) {
    super(s);
  }

    /**
     * Constructor for AccountNotFoundException that takes a message and a cause as arguments
     * @param s A String that holds a message to be printed if exception is thrown
     * @param cause A Throwable object that signifies the cause of the exception being raised
     */
  public AccountNotFoundException(String s, Throwable cause) {
    super(s, cause);
  }

    /**
     * Constructor for QueueNotFoundException that takes a message and a cause as arguments
     * @param cause A Throwable object that signifies the cause of the exception being raised
     */
  public AccountNotFoundException(Throwable cause) {
    super(cause);
  }

}
