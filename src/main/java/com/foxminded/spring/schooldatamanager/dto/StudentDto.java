package com.foxminded.spring.schooldatamanager.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentDto {
	@NotNull(message = "Group id cannot be empty")
	private int groupId;

	@NotBlank(message = "First name cannot be empty")
	private String firstName;

	@NotBlank(message = "Last name cannot be empty")
	private String lastName;
}
