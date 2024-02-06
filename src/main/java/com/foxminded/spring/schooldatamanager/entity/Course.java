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
public class Course {
	private int courseId;
	private String courseName;
	private String courseDescription;
}
