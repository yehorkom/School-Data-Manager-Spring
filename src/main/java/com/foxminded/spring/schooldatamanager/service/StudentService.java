package com.foxminded.spring.schooldatamanager.service;

import com.foxminded.spring.schooldatamanager.dto.StudentDto;
import java.util.List;

public interface StudentService {
	List<StudentDto> getAllStudents();
	List<StudentDto> findStudentsRelatedToCourse(String courseName);
	StudentDto getStudentById(int studentId);
	int addStudentToCourse(int studentId, int courseId);
	StudentDto createStudent(StudentDto studentDto);
	StudentDto updateStudent(int studentId, StudentDto studentDto);
	void removeStudentFromCourse(int studentId, int courseId);
	void deleteStudent(int studentId);
}
