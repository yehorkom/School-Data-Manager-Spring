package com.foxminded.spring.schooldatamanager.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseDto {
	@NotEmpty(message = "Course name cannot be empty")
	private String courseName;

	@NotEmpty(message = "Course description cannot be empty")
	private String courseDescription;
}
