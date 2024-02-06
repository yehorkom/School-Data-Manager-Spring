package com.foxminded.spring.schooldatamanager.controller;

import com.foxminded.spring.schooldatamanager.dto.StudentDto;
import com.foxminded.spring.schooldatamanager.mapper.StudentMapper;
import com.foxminded.spring.schooldatamanager.service.StudentService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/student")
public class StudentController {
	private final StudentService studentService;
	private final StudentMapper studentMapper;

	@GetMapping("/all")
	public ResponseEntity<List<StudentDto>> getAllStudents() {
		log.info("Getting all students");
		List<StudentDto> students = studentService.getAllStudents();

		log.info("Retrieved {} students", students.size());
		return new ResponseEntity<>(students, HttpStatus.OK);
	}

	@GetMapping("/students-to-course")
	public ResponseEntity<List<StudentDto>> findStudentsRelatedToCourse(@RequestParam("courseName") String courseName) {
		log.info("Finding students related to course {}", courseName);
		List<StudentDto> students = studentService.findStudentsRelatedToCourse(courseName);

		if (!students.isEmpty()) {
			log.info("Found {} students related to course {}", students.size(), courseName);
			return new ResponseEntity<>(students, HttpStatus.OK);
		} else {
			log.warn("No students found related to course {}", courseName);
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/{studentId}")
	public ResponseEntity<StudentDto> getStudentById(@PathVariable("studentId") int studentId) {
		log.info("Getting student with id {}", studentId);
		StudentDto student = studentService.getStudentById(studentId);

		if (student != null) {
			log.info("Found student with id {}", studentId);
			return new ResponseEntity<>(student, HttpStatus.OK);
		} else {
			log.warn("No student found with id {}", studentId);
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/{studentId}/{courseId}")
	public ResponseEntity<Void> addStudentToCourse(@PathVariable("studentId") int studentId, @PathVariable("courseId") int courseId) {
		log.info("Adding student with id {} to course with id {}", studentId, courseId);
		int rowsAffected = studentService.addStudentToCourse(studentId, courseId);

		if (rowsAffected > 0){
			log.info("Added student with id {} to course with id {}", studentId, courseId);
			return new ResponseEntity<>( HttpStatus.OK);
		} else {
			log.info("Student with id {} is exist in course with id {}", studentId, courseId);
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
	}

	@PostMapping
	public ResponseEntity<StudentDto> createStudent(@Valid @RequestBody StudentDto studentDto) {
		log.info("Creating new student");
		var createdStudent = studentService.createStudent(studentDto);
		URI location = ServletUriComponentsBuilder
			.fromCurrentRequest()
			.path("/{studentId}")
			.buildAndExpand(studentMapper.toLastStudent(createdStudent).getStudentId()).toUri();

		log.info("Created new student with id {}", studentMapper.toLastStudent(createdStudent).getStudentId());
		return ResponseEntity.created(location).build();
	}
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public void handleValidationExceptions(MethodArgumentNotValidException ex) {
		ex.getBindingResult().getAllErrors().forEach(error -> {
			String errorMessage = error.getDefaultMessage();
			log.error("Validation error: {}", errorMessage);
		});
	}

	@PutMapping("/{studentId}")
	public ResponseEntity<StudentDto> updateStudent(@PathVariable("studentId") int studentId, @RequestBody StudentDto studentDto) {
		log.info("Updating student with id {}", studentId);
		studentService.updateStudent(studentId, studentDto);

		log.info("Updated student with id {}", studentId);
		return new ResponseEntity<>(studentDto, HttpStatus.OK);
	}

	@DeleteMapping("/{studentId}/{courseId}")
	public ResponseEntity<String> removeStudentFromCourse(@PathVariable("studentId") int studentId, @PathVariable("courseId") int courseId) {
		log.info("Removing student with id {} from course with id {}", studentId, courseId);
		studentService.removeStudentFromCourse(studentId, courseId);

		log.info("Removed student with id {} from course with id {}", studentId, courseId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@DeleteMapping("/{studentId}")
	public ResponseEntity<Void> deleteStudent(@PathVariable("studentId") int studentId) {
		log.info("Deleting student with id {}", studentId);
		studentService.deleteStudent(studentId);

		log.info("Deleted student with id {}", studentId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
