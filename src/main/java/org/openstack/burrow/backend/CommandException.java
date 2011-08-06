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
 * CommandException is a subclass of BurrowException.  This type of exception is thrown if the
 * request was correctly formatted but could not be fulfilled as the request information was
 * not found.  This is the parent of MessageNotFound, QueueNotFound, AccountNotFound.
 */
public class CommandException extends BurrowException {

    /**
     * Constructor for CommandException that takes no arguments
     */
  public CommandException() {
  }

    /**
     * Constructor for CommandException that takes a message as an argument
     * @param s A string that holds a message to be printed if exception is thrown
     */
  public CommandException(String s) {
    super(s);
  }

    /**
     * Constructor for CommandException that takes a message and a cause as arguments
     * @param s A string that holds a message to be printed if exception is thrown
     * @param cause A Throwable object that signifies the cause of the exception being raised
     */
  public CommandException(String s, Throwable cause) {
    super(s, cause);
  }

    /**
     * Constructor for CommandException that takes a message and a cause as arguments
     * @param cause A Throwable object that signifies the cause of the exception being raised
     */
  public CommandException(Throwable cause) {
    super(cause);
  }

}
