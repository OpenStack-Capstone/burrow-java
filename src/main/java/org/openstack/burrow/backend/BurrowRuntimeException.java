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
 * BurrowRuntimeException is a subclass of RuntimeException.  This will be thrown when a Runtime
 * occurs when accessing the burrow server.
 */
public class BurrowRuntimeException extends RuntimeException {

    /**
     * Constructor for BurrowRuntimeException that takes no arguments
     */
  public BurrowRuntimeException() {
  }

    /**
     * Constructor for BurrowRuntimeException that takes a message argument as a String
     * @param s A string that holds the message to be shown when the exception is thrown
     */
  public BurrowRuntimeException(String s) {
    super(s);
  }

    /**
     * Constructor for BurrowRuntimeException that takes a message argument as a String
     * @param s A string that holds the message to be shown when the exception is thrown
     * @param cause A Throwable object that signifies the cause for the exception to be thrown
     */
  public BurrowRuntimeException(String s, Throwable cause) {
    super(s, cause);
  }

    /**
     * Constructor for BurrowRuntimeException that takes a message argument as a String
     * @param cause A Throwable object that signifies the cause for the exception to be thrown
     */
  public BurrowRuntimeException(Throwable cause) {
    super(cause);
  }
}
