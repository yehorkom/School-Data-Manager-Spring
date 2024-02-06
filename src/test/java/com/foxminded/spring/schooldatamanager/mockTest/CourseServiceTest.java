package com.foxminded.spring.schooldatamanager.mockTest;

import com.foxminded.spring.schooldatamanager.dto.CourseDto;
import com.foxminded.spring.schooldatamanager.service.impl.CourseServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.doNothing;


@SpringBootTest
class CourseServiceTest {
	@Mock
	private CourseServiceImpl courseService;

	@Test
	 void testGetAllCourses() {
		when(courseService.getAllCourses()).thenReturn(Collections.singletonList(new CourseDto()));
		List<CourseDto> result = courseService.getAllCourses();

		assertEquals(1, result.size());
		verify(courseService, times(1)).getAllCourses();
	}

	@Test
	void testGetCourseByIdDoesNotExists() {
		when(courseService.getCourseById(anyInt())).thenReturn(null);
		courseService.getCourseById(1);
		verify(courseService).getCourseById(1);
	}

	@Test
	void testGetCourseByIdExists() {
		CourseDto courseDto = CourseDto.builder().courseName("Test").build();

		when(courseService.getCourseById(anyInt())).thenReturn(courseDto);
		CourseDto result = courseService.getCourseById(1);

		assertEquals(courseDto.getCourseName(), result.getCourseName());
		verify(courseService).getCourseById(1);
	}

	@Test
	void testCreateCourse() {
		CourseDto courseDto = CourseDto.builder().courseName("Test").build();

		when(courseService.createCourse(any(CourseDto.class))).thenReturn(courseDto);
		CourseDto result = courseService.createCourse(courseDto);

		assertEquals(courseDto.getCourseName(), result.getCourseName());
		verify(courseService).createCourse(any(CourseDto.class));
	}

	@Test
	void testUpdateCourse() {
		CourseDto courseDto = CourseDto.builder().courseName("Test").build();

		when(courseService.updateCourse(1, courseDto)).thenReturn(courseDto);
		CourseDto result = courseService.updateCourse(1, courseDto);

		assertEquals(courseDto.getCourseName(), result.getCourseName());
		verify(courseService).updateCourse(1, courseDto);
	}

	@Test
	void testDeleteCourse() {
		doNothing().when(courseService).deleteCourse(anyInt());
		courseService.deleteCourse(1);
		verify(courseService).deleteCourse(1);
	}
}
