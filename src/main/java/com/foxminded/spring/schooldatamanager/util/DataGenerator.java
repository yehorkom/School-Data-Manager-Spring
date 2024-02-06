package com.foxminded.spring.schooldatamanager.util;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;
@Service
@RequiredArgsConstructor
public class DataGenerator {
	private final JdbcTemplate jdbcTemplate;
	private final Random random = new Random();

	public void generateData() {
		generateGroupsData();
		generateStudentsData();
		generateCoursesData();
		generateStudentsCoursesData();
	}

	private void generateGroupsData() {
		String[] letters = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};

		for (int i = 0; i < 10; i++) {
			String groupName = letters[random.nextInt(letters.length)] +
				letters[random.nextInt(letters.length)] + "-"
				+ random.nextInt(10) + random.nextInt(10);

			jdbcTemplate.update("INSERT INTO groups (group_name) VALUES (?)", groupName);
			}
	}

	private void generateStudentsData() {
		String[] firstNames = {"John", "Jane", "Michael", "Mary", "David", "Susan", "Robert", "Lisa", "William", "Jessica"};
		String[] lastNames = {"Doe", "Smith", "Jones", "Johnson", "Williams", "Brown", "Taylor", "Davis", "White", "Miller"};

			for (int i = 0; i < 200; i++) {
				String firstName = firstNames[random.nextInt(firstNames.length)];
				String lastName = lastNames[random.nextInt(lastNames.length)];
				int groupId = random.nextInt(10) + 1;

				jdbcTemplate.update("INSERT INTO students (first_name, last_name, group_id) VALUES (?, ?, ?)", firstName, lastName, groupId);
			}
	}

	private void generateCoursesData() {
		String[] courseNames = {"Math", "Biology", "Physics", "Chemistry", "History", "English", "Geography", "Computer Science", "Art", "Music"};

			for (int i = 0; i < 10; i++) {
				String courseName = courseNames[i];
				String description = courseName + " course";

				jdbcTemplate.update("INSERT INTO courses (course_name, course_description) VALUES (?, ?) ON CONFLICT DO NOTHING", courseName, description);
			}
	}

	private void generateStudentsCoursesData() {
		for (int i = 0; i < 200; i++) {
			int studentId = i + 1;
			int numberOfCourses = random.nextInt(3) + 1;

			for (int j = 0; j < numberOfCourses; j++) {
				int courseId = random.nextInt(10) + 1;

				jdbcTemplate.update("INSERT INTO students_courses (student_id, course_id) VALUES (?, ?) ON CONFLICT DO NOTHING", studentId, courseId);
			}
		}
	}
}
