package com.foxminded.spring.schooldatamanager.service.impl;

import com.foxminded.spring.schooldatamanager.dao.CourseDao;
import com.foxminded.spring.schooldatamanager.dto.CourseDto;
import com.foxminded.spring.schooldatamanager.entity.Course;
import com.foxminded.spring.schooldatamanager.mapper.CourseMapper;
import com.foxminded.spring.schooldatamanager.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {
	private final CourseDao courseDao;
	@Override
	public List<CourseDto> getAllCourses() {
		return courseDao.getAllCourses()
			.stream()
			.map(CourseMapper::toCourseDto)
			.toList();
	}

	@Override
	public CourseDto getCourseById(int courseId) {
		Course course = courseDao.getCourseById(courseId);

		return CourseMapper.toCourseDto(course);
	}

	@Override
	public CourseDto createCourse(CourseDto courseDto) {
		return courseDao.createCourse(courseDto);
	}

	@Override
	public CourseDto updateCourse(int courseId, CourseDto courseDto) {
		Course existingCourse = courseDao.getCourseById(courseId);

		if (existingCourse != null) {
			Course updatedCourse = CourseMapper.toCourse(courseDto);
			updatedCourse.setCourseId(courseId);
			courseDao.updateCourse(updatedCourse);
			return courseDto;
		}

		return null;
	}

	@Override
	public void deleteCourse(int courseId) {
		Course existingCourse = courseDao.getCourseById(courseId);

		if (existingCourse != null) {
			courseDao.deleteCourse(courseId);
		}
	}
}
