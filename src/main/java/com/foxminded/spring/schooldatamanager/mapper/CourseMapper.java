package com.foxminded.spring.schooldatamanager.mapper;


import com.foxminded.spring.schooldatamanager.dto.CourseDto;
import com.foxminded.spring.schooldatamanager.entity.Course;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseMapper {
	private final JdbcTemplate jdbcTemplate;

	public static Course toCourse(CourseDto courseDto){
		if(courseDto == null){
			return null;
		}

		Course course = new Course();
		course.setCourseName(courseDto.getCourseName());
		course.setCourseDescription(courseDto.getCourseDescription());

		return course;
	}

	public Course toLastCourse(CourseDto courseDto){
		if(courseDto == null){
			return null;
		}

		List<Course> lastCourse = jdbcTemplate.query("SELECT * FROM courses ORDER BY course_id DESC LIMIT 1",
			new BeanPropertyRowMapper<>(Course.class));

		Course course = new Course();
		course.setCourseId(lastCourse.getLast().getCourseId());
		course.setCourseName(courseDto.getCourseName());
		course.setCourseDescription(courseDto.getCourseDescription());

		return course;
	}

	public static CourseDto toCourseDto(Course course){
		if (course == null){
			return null;
		}

		CourseDto courseDto = new CourseDto();
		courseDto.setCourseName(course.getCourseName());
		courseDto.setCourseDescription(course.getCourseDescription());

		return courseDto;
	}
}
