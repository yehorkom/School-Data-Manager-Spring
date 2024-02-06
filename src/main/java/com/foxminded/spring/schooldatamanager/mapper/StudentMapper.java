package com.foxminded.spring.schooldatamanager.mapper;

import com.foxminded.spring.schooldatamanager.dto.StudentDto;
import com.foxminded.spring.schooldatamanager.entity.Student;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class StudentMapper {
	private final JdbcTemplate jdbcTemplate;
	public static Student toStudent(StudentDto studentDto) {
		if(studentDto == null){
			return null;
		}

		Student student = new Student();
		student.setGroupId(studentDto.getGroupId());
		student.setFirstName(studentDto.getFirstName());
		student.setLastName(studentDto.getLastName());

		return student;
	}

	public Student toLastStudent(StudentDto studentDto) {
		if(studentDto == null){
			return null;
		}

		List<Student> lastStudent = jdbcTemplate.query("SELECT * FROM students ORDER BY student_id DESC LIMIT 1",
			new BeanPropertyRowMapper<>(Student.class));

		Student student = new Student();
		student.setStudentId(lastStudent.getLast().getStudentId());
		student.setGroupId(studentDto.getGroupId());
		student.setFirstName(studentDto.getFirstName());
		student.setLastName(studentDto.getLastName());

		return student;
	}
	public static StudentDto toStudentDto(Student student) {
		if(student == null){
			return null;
		}

		StudentDto studentDTO = new StudentDto();
		studentDTO.setGroupId(student.getGroupId());
		studentDTO.setFirstName(student.getFirstName());
		studentDTO.setLastName(student.getLastName());

		return studentDTO;
	}
}
