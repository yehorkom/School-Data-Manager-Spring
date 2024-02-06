package com.foxminded.spring.schooldatamanager.service.impl;

import com.foxminded.spring.schooldatamanager.dao.CourseDao;
import com.foxminded.spring.schooldatamanager.dao.StudentDao;
import com.foxminded.spring.schooldatamanager.dto.StudentDto;
import com.foxminded.spring.schooldatamanager.entity.Course;
import com.foxminded.spring.schooldatamanager.entity.Student;
import com.foxminded.spring.schooldatamanager.exception.CourseNotFoundException;
import com.foxminded.spring.schooldatamanager.exception.GroupNotFoundException;
import com.foxminded.spring.schooldatamanager.exception.StudentNotFoundException;
import com.foxminded.spring.schooldatamanager.mapper.StudentMapper;
import com.foxminded.spring.schooldatamanager.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
	private final StudentDao studentDao;
	private final CourseDao courseDao;

	@Override
	public List<StudentDto> getAllStudents() {
		return studentDao.getAllStudents()
			.stream()
			.map(StudentMapper::toStudentDto)
			.toList();
	}

	@Override
	public List<StudentDto> findStudentsRelatedToCourse(String courseName) {
		return studentDao.findStudentsRelatedToCourse(courseName).stream()
			.map(StudentMapper::toStudentDto)
			.toList();
	}

	@Override
	public StudentDto getStudentById(int studentId) {
		Student student = studentDao.getStudentById(studentId);
		return StudentMapper.toStudentDto(student);
	}

	@Override
	public int addStudentToCourse(int studentId, int courseId) {
		Student existingStudent = studentDao.getStudentById(studentId);
		if (existingStudent == null) {
			log.warn("Failed to add student with id {} to course with id {}.", studentId, courseId);
			log.warn("No student found with id {}", studentId);
			throw new StudentNotFoundException("Student not found with id: " + studentId);
		}

		Course existingCourse = courseDao.getCourseById(courseId);
		if (existingCourse == null) {
			log.warn("Failed to add student with id {} to course with id {}.", studentId, courseId);
			log.warn("No course found with id {}", courseId);
			throw new CourseNotFoundException("Course not found with id: " + courseId);
		}

		return studentDao.addStudentToCourse(studentId, courseId);
	}

	@Override
	public StudentDto createStudent(StudentDto studentDto) {
		var createdStudent = studentDao.createStudent(studentDto);

		if (createdStudent == null){
			throw new GroupNotFoundException("Group not found for student");
		}

		return studentDto;
	}

	@Override
	public StudentDto updateStudent(int studentId, StudentDto studentDto) {
		Student existingStudent = studentDao.getStudentById(studentId);

		if (existingStudent == null) {
			throw new StudentNotFoundException("Student not found with id: " + studentId);
		}
		Student updatedStudent = StudentMapper.toStudent(studentDto);
		updatedStudent.setStudentId(studentId);
		var student = studentDao.updateStudent(updatedStudent);

		if (student == null){
			throw new GroupNotFoundException("Group not found for student with id: " + studentId);
		}

		return studentDto;
	}

	@Override
	public void removeStudentFromCourse(int studentId, int courseId) {
		studentDao.removeStudentFromCourse(studentId, courseId);
	}

	@Override
	public void deleteStudent(int studentId) {
		Student existingStudent = studentDao.getStudentById(studentId);

		if (existingStudent != null) {
			studentDao.deleteStudent(studentId);
		}
	}
}
