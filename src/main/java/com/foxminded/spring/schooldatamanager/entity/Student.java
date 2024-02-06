package com.foxminded.spring.schooldatamanager.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AllArgsConstructor;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Student {
	private int studentId;
	private int groupId;
	private String firstName;
	private String lastName;
}
