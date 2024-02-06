package com.foxminded.spring.schooldatamanager.dbTest;

import com.foxminded.spring.schooldatamanager.TestDataSourceConfig;
import com.foxminded.spring.schooldatamanager.dao.StudentDao;
import com.foxminded.spring.schooldatamanager.dto.StudentDto;
import com.foxminded.spring.schooldatamanager.entity.Student;
import com.foxminded.spring.schooldatamanager.dao.impl.StudentDaoImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(
	scripts = {"/initDB.sql","/clear_tables.sql", "/sample_data.sql"},
	executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
@ContextConfiguration(classes = TestDataSourceConfig.class)
class StudentDaoTest {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	private StudentDao studentDAO;

	@Container
	static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(DockerImageName.parse("postgres:15.3"));

	@BeforeEach
	void setUp() {
		studentDAO = new StudentDaoImpl(jdbcTemplate);
	}

	@Test
	@Order(value = 1)
	void testConnectionToDatabase() {
		assertNotNull(studentDAO);
	}

	@Test
	void testFindAllStudents() {
		List<Student> students = studentDAO.getAllStudents();
		assertEquals(5, students.size());
	}

	@Test
	void testFindStudentById() {
		Student student1 = studentDAO.getStudentById(1);
		assertEquals("John", student1.getFirstName());
		assertEquals("Doe", student1.getLastName());

		Student student2 = studentDAO.getStudentById(5);
		assertEquals("Michael", student2.getFirstName());
		assertEquals("Brown", student2.getLastName());
	}

	@Test
	void testAddStudent() {
		StudentDto student = new StudentDto();
		student.setFirstName("John");
		student.setLastName("White");
		student.setGroupId(1);

		studentDAO.createStudent(student);

		Student studentExpected = studentDAO.getStudentById(6);
		assertEquals("John", studentExpected.getFirstName());
		assertEquals("White", studentExpected.getLastName());
	}

	@Test
	void testDeleteStudentById() {
		Student studentToDelete = studentDAO.getStudentById(5);

		assertNotNull(studentToDelete);

		studentDAO.deleteStudent(5);
		Student deletedStudent = studentDAO.getStudentById(5);

		assertNull(deletedStudent);
	}

	@Test
	void testUpdateStudent() {
		Student studentToUpdate = studentDAO.getStudentById(1);
		studentToUpdate.setFirstName("UpdatedFirstName");
		studentToUpdate.setLastName("UpdatedLastName");
		studentDAO.updateStudent(studentToUpdate);

		Student updatedStudent = studentDAO.getStudentById(1);
		assertEquals("UpdatedFirstName", updatedStudent.getFirstName());
		assertEquals("UpdatedLastName", updatedStudent.getLastName());
	}
}
