package com.foxminded.spring.schooldatamanager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class GroupNotFoundException extends RuntimeException {
	public GroupNotFoundException(String message) {
		super(message);
	}
}
