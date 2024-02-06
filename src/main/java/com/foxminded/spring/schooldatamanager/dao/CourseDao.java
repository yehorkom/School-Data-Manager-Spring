package com.foxminded.spring.schooldatamanager.dao;

import com.foxminded.spring.schooldatamanager.dto.CourseDto;
import com.foxminded.spring.schooldatamanager.entity.Course;

import java.util.List;

public interface CourseDao {
	List<Course> getAllCourses();
	Course getCourseById(int courseId);
	CourseDto createCourse(CourseDto courseDto);
	void updateCourse(Course course);
	void deleteCourse(int courseId);
}
