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
 * BurrowException that is a subclass of Exception.  This will serve as the parent of the rest
 * of the BurrowExceptions except for BurrowRuntimeException.
 */
public class BurrowException extends Exception {

    /**
     * Constructor for BurrowException that takes no arguments
     */
  public BurrowException() {
  }

    /**
     * Constructor for BurrowException that takes a message
     * @param s A string that holds a message to be printed if exception is thrown
     */
  public BurrowException(String s) {
    super(s);
  }

    /**
     * Constructor for BurrowException that takes a message and a cause
     * @param s A string that holds a message to be printed if exception is thrown
     * @param cause A Throwable object that signifies the cause of the exception being raised
     */
  public BurrowException(String s, Throwable cause) {
    super(s, cause);
  }

    /**
     * Constructor for BurrowException that takes a message and a cause
     * @param cause A Throwable object that signifies the cause of the exception being raised
     */
  public BurrowException(Throwable cause) {
    super(cause);
  }
}
