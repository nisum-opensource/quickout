/**
 * Copyright (C) Nisum Technologies
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 * 
*/

package com.nisum.quickout.exception;

public class QuickoutRuntimeException extends RuntimeException {

	public QuickoutRuntimeException(){}
	public QuickoutRuntimeException(String message){
		super(message);
	}

    public QuickoutRuntimeException(String message, Throwable t) {
        super(message, t);
    }
}
