package com.foxminded.spring.schooldatamanager.dao.impl;

import com.foxminded.spring.schooldatamanager.dao.CourseDao;
import com.foxminded.spring.schooldatamanager.dto.CourseDto;
import com.foxminded.spring.schooldatamanager.entity.Course;
import java.util.Collections;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CourseDaoImpl implements CourseDao {
	private final JdbcTemplate jdbcTemplate;

	@Override
	public List<Course> getAllCourses() {
		try {
			return jdbcTemplate.query("SELECT * FROM courses",
				new BeanPropertyRowMapper<>(Course.class));
		} catch (EmptyResultDataAccessException e) {
				return Collections.emptyList();
		}
	}

	@Override
	public Course getCourseById(int courseId) {
		try {
			return jdbcTemplate.queryForObject("SELECT * FROM courses WHERE course_id = ?",
				new BeanPropertyRowMapper<>(Course.class), courseId);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public CourseDto createCourse(CourseDto course) {
		jdbcTemplate.update("INSERT INTO courses (course_name, course_description) VALUES (?, ?)",
				course.getCourseName(), course.getCourseDescription());
		return course;
	}

	@Override
	public void updateCourse(Course course) {
		jdbcTemplate.update("UPDATE courses SET course_name = ?, course_description = ? WHERE course_id = ?",
				course.getCourseName(), course.getCourseDescription(), course.getCourseId());
	}

	@Override
	public void deleteCourse(int courseId) {
		jdbcTemplate.update("DELETE FROM students_courses WHERE course_id = ?", courseId );
		jdbcTemplate.update("DELETE FROM courses WHERE course_id = ?", courseId);
	}
}
