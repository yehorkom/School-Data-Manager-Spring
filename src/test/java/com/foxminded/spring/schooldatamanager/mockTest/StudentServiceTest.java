package com.foxminded.spring.schooldatamanager.mockTest;

import com.foxminded.spring.schooldatamanager.dto.StudentDto;
import com.foxminded.spring.schooldatamanager.service.impl.StudentServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.doNothing;
@SpringBootTest
class StudentServiceTest {
	@Mock
	private StudentServiceImpl studentService;

	@Test
	void testGetAllStudents() {
		when(studentService.getAllStudents()).thenReturn(Collections.singletonList(new StudentDto()));
		List<StudentDto> result = studentService.getAllStudents();

		assertEquals(1, result.size());
		verify(studentService, times(1)).getAllStudents();
	}

	@Test
	void  testCreateStudent() {
		StudentDto studentDto = StudentDto.builder().groupId(1).firstName("John").lastName("Doe").build();

		when(studentService.createStudent(any(StudentDto.class))).thenReturn(studentDto);
		StudentDto result = studentService.createStudent(studentDto);

		assertEquals(studentDto.getGroupId(), result.getGroupId());
		assertEquals(studentDto.getFirstName(), result.getFirstName());
		assertEquals(studentDto.getLastName(), result.getLastName());
		verify(studentService).createStudent(any(StudentDto.class));
	}

	@Test
	void testUpdateStudent() {
		StudentDto studentDto = StudentDto.builder().groupId(1).firstName("John").lastName("Doe").build();

		when(studentService.updateStudent(1, studentDto)).thenReturn(studentDto);
		StudentDto result = studentService.updateStudent(1, studentDto);

		assertEquals(studentDto.getGroupId(), result.getGroupId());
		assertEquals(studentDto.getFirstName(), result.getFirstName());
		assertEquals(studentDto.getLastName(), result.getLastName());
		verify(studentService).updateStudent(1, studentDto);
	}

	@Test
	void testGetStudentByIdStudentExists() {
		StudentDto studentDto = StudentDto.builder().groupId(1).firstName("John").lastName("Doe").build();

		when(studentService.getStudentById(anyInt())).thenReturn(studentDto);
		StudentDto result = studentService.getStudentById(1);

		assertEquals(studentDto.getGroupId(), result.getGroupId());
		assertEquals(studentDto.getFirstName(), result.getFirstName());
		assertEquals(studentDto.getLastName(), result.getLastName());
		verify(studentService).getStudentById(1);
	}
	@Test
	void testGetStudentByIdStudentDoesNotExists() {
		when(studentService.getStudentById(anyInt())).thenReturn(null);
		studentService.getStudentById(1);
		verify(studentService).getStudentById(1);
	}
	@Test
	void testAddStudentToCourse() {
		when(studentService.addStudentToCourse(anyInt(), anyInt())).thenReturn(1);
		int result = studentService.addStudentToCourse(1, 1);

		assertEquals(1, result);
		verify(studentService).addStudentToCourse(1, 1);
	}

	@Test
	void testRemoveStudentFromCourse() {
		doNothing().when(studentService).removeStudentFromCourse(anyInt(), anyInt());
		studentService.removeStudentFromCourse(1, 1);
		verify(studentService).removeStudentFromCourse(1, 1);
	}

	@Test
	void testDeleteStudent() {
		doNothing().when(studentService).deleteStudent(anyInt());
		studentService.deleteStudent(1);
		verify(studentService).deleteStudent(1);
	}

	@Test
	void testFindStudentsRelatedToCourse() {
		StudentDto studentDto1 = StudentDto.builder().groupId(1).firstName("John").lastName("Doe").build();
		StudentDto studentDto2 = StudentDto.builder().groupId(2).firstName("Jane").lastName("Doe").build();
		List<StudentDto> students = Arrays.asList(studentDto1, studentDto2);

		when(studentService.findStudentsRelatedToCourse(anyString())).thenReturn(students);

		List<StudentDto> result = studentService.findStudentsRelatedToCourse("Course1");

		assertEquals(students.size(), result.size());

		for (int i = 0; i < students.size(); i++) {
			assertEquals(students.get(i).getGroupId(), result.get(i).getGroupId());
			assertEquals(students.get(i).getFirstName(), result.get(i).getFirstName());
			assertEquals(students.get(i).getLastName(), result.get(i).getLastName());
		}

		verify(studentService).findStudentsRelatedToCourse("Course1");
	}
}
