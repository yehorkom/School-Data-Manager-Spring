package com.foxminded.spring.schooldatamanager.dbTest;

import com.foxminded.spring.schooldatamanager.TestDataSourceConfig;
import com.foxminded.spring.schooldatamanager.dao.CourseDao;
import com.foxminded.spring.schooldatamanager.dto.CourseDto;
import com.foxminded.spring.schooldatamanager.entity.Course;
import com.foxminded.spring.schooldatamanager.dao.impl.CourseDaoImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Testcontainers
@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(
	scripts = {"/initDB.sql", "/clear_tables.sql", "/sample_data.sql"},
	executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
@ContextConfiguration(classes = TestDataSourceConfig.class)
class CourseDaoTest {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	private CourseDao courseDAO;

	@Container
	static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(DockerImageName.parse("postgres:15.3"));

	@BeforeEach
	void setUp() {
		courseDAO = new CourseDaoImpl(jdbcTemplate);
	}

	@Test
	@Order(value = 1)
	void testConnectionToDatabase() {
		assertNotNull(courseDAO);
	}

	@Test
	void testGetAllCourses() {
		List<Course> courses = courseDAO.getAllCourses();

		assertNotNull(courses);
		assertEquals(4, courses.size());
	}

	@Test
	void testGetCourseById() {
		Course course = courseDAO.getCourseById(1);

		assertNotNull(course);
		assertEquals("Math", course.getCourseName());
	}

	@Test
	void testCreateCourse() {
		CourseDto newCourse = new CourseDto();
		newCourse.setCourseName("New Course");
		newCourse.setCourseDescription("New Course Description");
		courseDAO.createCourse(newCourse);

		List<Course> courses = courseDAO.getAllCourses();
		assertNotNull(courses);
		assertEquals(5, courses.size());
	}

	@Test
	void testUpdateCourse() {
		Course courseToUpdate = courseDAO.getCourseById(1);
		courseToUpdate.setCourseName("Updated Course Name");
		courseDAO.updateCourse(courseToUpdate);

		Course updatedCourse = courseDAO.getCourseById(1);
		assertEquals("Updated Course Name", updatedCourse.getCourseName());
	}

	@Test
	void testDeleteCourse() {
		courseDAO.deleteCourse(2);

		List<Course> courses = courseDAO.getAllCourses();
		assertNotNull(courses);
		assertEquals(3, courses.size());
	}
}
