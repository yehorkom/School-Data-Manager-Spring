package com.foxminded.spring.schooldatamanager.controller;

import com.foxminded.spring.schooldatamanager.dto.CourseDto;
import com.foxminded.spring.schooldatamanager.mapper.CourseMapper;
import com.foxminded.spring.schooldatamanager.service.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/course")
public class CourseController {
	private final CourseService courseService;
	private final CourseMapper courseMapper;

	@GetMapping("/all")
	public ResponseEntity<List<CourseDto>> getAllCourses() {
		log.info("Getting all courses");
		List<CourseDto> courses = courseService.getAllCourses();

			return new ResponseEntity<>(courses, HttpStatus.OK);
	}
	@GetMapping("/{courseId}")
	public ResponseEntity<CourseDto> getCourseById(@PathVariable("courseId") int courseId) {
		log.info("Getting course with id {}", courseId);
		CourseDto course = courseService.getCourseById(courseId);

		if (course != null) {
			log.info("Found course with id {}", courseId);
			return new ResponseEntity<>(course, HttpStatus.OK);
		} else {
			log.warn("No course found with id {}", courseId);
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping
	public ResponseEntity<CourseDto> createCourse(@Valid @RequestBody CourseDto courseDto) {
		log.info("Creating new course");
		var createdCourse = courseService.createCourse(courseDto);
		URI location = ServletUriComponentsBuilder
			.fromCurrentRequest()
			.path("/{courseId}")
			.buildAndExpand(courseMapper.toLastCourse(createdCourse).getCourseId()).toUri();
		log.info("Created new course with id {}", courseMapper.toLastCourse(createdCourse).getCourseId());
		return ResponseEntity.created(location).build();
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public void handleValidationExceptions(MethodArgumentNotValidException ex) {
		ex.getBindingResult().getAllErrors().forEach(error -> {
			String errorMessage = error.getDefaultMessage();
			log.error("Validation error: {}", errorMessage);
		});
	}

	@PutMapping("/{courseId}")
	public ResponseEntity<CourseDto> updateCourse(@PathVariable("courseId") int courseId, @RequestBody CourseDto courseDto) {
		log.info("Updating course with id {}", courseId);
		var updatedCourse = courseService.updateCourse(courseId,courseDto);

		if (updatedCourse != null) {
			log.info("Updated course with id {}", courseId);
			return new ResponseEntity<>(courseDto, HttpStatus.OK);
		} else {
			log.warn("No course found with id {} for update", courseId);
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/{courseId}")
	public ResponseEntity<Void> deleteCourse(@PathVariable("courseId") int courseId) {
		log.info("Deleting course with id {}", courseId);
		courseService.deleteCourse(courseId);
		log.info("Deleted course with id {}", courseId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
