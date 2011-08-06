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
 * MessageNotFoundException is a subclass of CommandException.  This type of exception is thrown if the
 * request was correctly formatted but could not be fulfilled as the requested Message does not
 * exist.
 */
public class MessageNotFoundException extends CommandException {

    /**
     * Constructor for MessageNotFoundException that takes no arguments
     */
  public MessageNotFoundException() {
  }

    /**
     * Constructor for MessageNotFoundException that takes a message as an argument
     * @param s A string that holds a message to be printed if exception is thrown
     */
  public MessageNotFoundException(String s) {
    super(s);
  }

    /**
     * Constructor for MessageNotFoundException that takes a message and a cause as arguments
     * @param s A string that holds a message to be printed if exception is thrown
     * @param cause A Throwable object that signifies the cause of the exception being raised
     */
  public MessageNotFoundException(String s, Throwable cause) {
    super(s, cause);
  }

    /**
     * Constructor for MessageNotFoundException that takes a message and a cause as arguments
     * @param cause A Throwable object that signifies the cause of the exception being raised
     */
  public MessageNotFoundException(Throwable cause) {
    super(cause);
  }

}
