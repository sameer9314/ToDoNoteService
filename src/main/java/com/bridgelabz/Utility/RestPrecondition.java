package com.bridgelabz.Utility;

import org.springframework.lang.Nullable;

/**
 * Purpose : To check whether the object is null or empty.	
 * @author Sameer Saurabh
 * @version  1.0
 * @Since  31/07/2018
 */
public class RestPrecondition {
	public static <T> T checkNotNull(T reference, @Nullable Object errorMessage) {
	    if (reference == null) {
	      throw new NullPointerException(String.valueOf(errorMessage));
	    }
	    return reference;
	  }
	
	public static <T> T checkNotEmpty(T reference, @Nullable Object errorMessage) {
	    if (reference == "") {
	      throw new NullPointerException(String.valueOf(errorMessage));
	    }
	    return reference;
	  }
}
