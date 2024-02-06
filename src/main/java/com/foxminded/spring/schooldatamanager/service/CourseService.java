package com.foxminded.spring.schooldatamanager.service;

import com.foxminded.spring.schooldatamanager.dto.CourseDto;
import java.util.List;

public interface CourseService {
	List<CourseDto> getAllCourses();
	CourseDto getCourseById(int courseId);
	CourseDto createCourse(CourseDto courseDto);
	CourseDto updateCourse(int courseId,CourseDto courseDto);
	void deleteCourse(int courseId);
}
