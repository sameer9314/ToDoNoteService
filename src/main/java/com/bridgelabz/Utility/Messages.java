package com.bridgelabz.Utility;

import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Service;

@Service
public class Messages {
	  private final MessageSourceAccessor accessor;

	    public Messages(MessageSource messageSource) {
	        this.accessor = new MessageSourceAccessor(messageSource);
	    }

	    public String get(String code) {
	        return accessor.getMessage(code);
	    }
}