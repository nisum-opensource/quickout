/**
 * Copyright (C) Nisum Technologies
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 * 
*/

package com.nisum.quickout.exception;

public class QuickoutNotFoundException extends QuickoutRuntimeException {

	public QuickoutNotFoundException(){}
	
	public QuickoutNotFoundException(String message) {
		super(message);
	}

}
