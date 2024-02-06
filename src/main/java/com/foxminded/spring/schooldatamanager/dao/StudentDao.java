package com.foxminded.spring.schooldatamanager.dao;

import com.foxminded.spring.schooldatamanager.dto.StudentDto;
import com.foxminded.spring.schooldatamanager.entity.Student;

import java.util.List;

public interface StudentDao {
	List<Student> getAllStudents();
	List<Student> findStudentsRelatedToCourse(String courseName);
	Student getStudentById(int studentId);
	int addStudentToCourse(int studentId, int courseId);
	StudentDto createStudent(StudentDto studentDto);
	Student updateStudent(Student student);
	void removeStudentFromCourse(int studentId, int courseId);
	void deleteStudent(int studentId);
}
