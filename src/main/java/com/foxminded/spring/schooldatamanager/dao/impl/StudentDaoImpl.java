package com.foxminded.spring.schooldatamanager.dao.impl;

import com.foxminded.spring.schooldatamanager.dao.StudentDao;
import com.foxminded.spring.schooldatamanager.dto.StudentDto;
import com.foxminded.spring.schooldatamanager.entity.Group;
import com.foxminded.spring.schooldatamanager.entity.Student;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class StudentDaoImpl implements StudentDao {
	private final JdbcTemplate jdbcTemplate;

	@Override
	public List<Student> getAllStudents() {
		try {
			return jdbcTemplate.query("SELECT * FROM students",
				new BeanPropertyRowMapper<>(Student.class));
		} catch (EmptyResultDataAccessException e) {
			return Collections.emptyList();
		}
	}

	@Override
	public List<Student> findStudentsRelatedToCourse(String courseName) {
		try {
			return jdbcTemplate.query("SELECT students.first_name, students.last_name, students.group_id " +
					"FROM students " +
					"INNER JOIN students_courses ON students.student_id = students_courses.student_id " +
					"INNER JOIN courses ON students_courses.course_id = courses.course_id " +
					"WHERE LOWER(courses.course_name) = LOWER(?)",
				new BeanPropertyRowMapper<>(Student.class), courseName.toLowerCase());
		} catch (EmptyResultDataAccessException e) {
			return Collections.emptyList();
		}
	}

	@Override
	public Student getStudentById(int studentId) {
		try {
			return jdbcTemplate.queryForObject("SELECT * FROM students WHERE student_id = ?",
				new BeanPropertyRowMapper<>(Student.class), studentId);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public int addStudentToCourse(int studentId, int courseId) {
		var student = jdbcTemplate.query("SELECT 1 FROM students_courses WHERE student_id = ? AND course_id = ?",
			new BeanPropertyRowMapper<>(Student.class),studentId, courseId);

		if (student.isEmpty()){
			return jdbcTemplate.update("INSERT INTO students_courses (student_id, course_id) VALUES (?, ?)",
				studentId, courseId);
		} else {
			return 0;
		}
	}

	@Override
	public StudentDto createStudent(StudentDto student) {
		var group =  jdbcTemplate.query("SELECT 1 FROM groups WHERE group_id = ?",
			new BeanPropertyRowMapper<>(Group.class), student.getGroupId());

		if (!group.isEmpty()){
			jdbcTemplate.update("INSERT INTO students (group_id, first_name, last_name) VALUES (?, ?, ?)",
				student.getGroupId(), student.getFirstName(), student.getLastName());
			return student;
		}else {
			return null;
		}
	}

	@Override
	public Student updateStudent(Student student) {
		var group =  jdbcTemplate.query("SELECT 1 FROM groups WHERE group_id = ?",
			new BeanPropertyRowMapper<>(Group.class), student.getGroupId());

		if (!group.isEmpty()){
			jdbcTemplate.update("UPDATE students SET group_id = ?, first_name = ?, last_name = ? WHERE student_id = ?",
				student.getGroupId(), student.getFirstName(), student.getLastName(), student.getStudentId());
			return student;
		} else {
			return null;
		}
	}

	@Override
	public void removeStudentFromCourse(int studentId, int courseId) {
		jdbcTemplate.update("DELETE FROM students_courses WHERE student_id = ? AND course_id = ?",
			studentId, courseId);
	}

	@Override
	public void deleteStudent(int studentId) {
		jdbcTemplate.update("DELETE FROM students_courses WHERE student_id = ?", studentId );
		jdbcTemplate.update("DELETE FROM students WHERE student_id = ?", studentId);
	}
}
