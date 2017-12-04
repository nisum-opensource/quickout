/**
 * Copyright (C) Nisum Technologies
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 * 
*/

package com.nisum.quickout.exception;

public class QuickoutInvalidInputException extends QuickoutRuntimeException {

	public QuickoutInvalidInputException(){}
	public QuickoutInvalidInputException(String message){
		super(message);
	}

    public QuickoutInvalidInputException(String message, Throwable t) {
        super(message, t);
    }
}
