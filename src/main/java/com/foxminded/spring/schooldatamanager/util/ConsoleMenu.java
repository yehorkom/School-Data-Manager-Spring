package com.foxminded.spring.schooldatamanager.util;

import com.foxminded.spring.schooldatamanager.dto.CourseDto;
import com.foxminded.spring.schooldatamanager.dto.GroupDto;
import com.foxminded.spring.schooldatamanager.dto.StudentDto;
import com.foxminded.spring.schooldatamanager.exception.GroupNotFoundException;
import com.foxminded.spring.schooldatamanager.exception.StudentNotFoundException;
import com.foxminded.spring.schooldatamanager.service.CourseService;
import com.foxminded.spring.schooldatamanager.service.GroupService;
import com.foxminded.spring.schooldatamanager.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Scanner;

import static java.lang.System.*;

@Service
@RequiredArgsConstructor
public class ConsoleMenu {
	private final CourseService courseService;
	private final StudentService studentService;
	private final GroupService groupService;

	private static final String INVALID_INPUT_MESSAGE = "Invalid input. Please enter a numeric.";
	private final Scanner scanner = new Scanner(in);
	public void launchingConsole() {

			while (true) {
				out.println("Choose an option:");
				out.println("a. Find all groups with less or equal students’ number");
				out.println("b. Find all students related to the course with the given name");
				out.println("c. Add a new student");
				out.println("d. Delete a student by the STUDENT_ID");
				out.println("e. Add a student to the course (from a list)");
				out.println("f. Remove the student from one of their courses");
				out.println("q. Quit");

				String option = scanner.nextLine().trim().toLowerCase();

                switch (option) {
                    case "a" -> findAllGroupsNamesWithLessOrEqualStudents();
                    case "b" -> findStudentsRelatedToCourse();
                    case "c" -> addNewStudent();
                    case "d" -> deleteStudent();
                    case "e" -> addStudentToCourse();
                    case "f" -> removeStudentFromCourse();
                    case "q" -> {
                        out.println("Exiting.");
                        return;
                    }
                    default -> out.println("Invalid option. Please try again.");
                }
			}
	}

	private void findAllGroupsNamesWithLessOrEqualStudents()  {
		int maxStudents = readIntFromUser("Enter the maximum number of students: ");
		if (maxStudents == -1) {
			return;
		}

		List<GroupDto> groupNames = groupService.findGroupNamesWithLessOrEqualStudents(maxStudents);
		if (groupNames.isEmpty()){
			out.println("There are no groups with that amount of students.");
		} else {
			out.println("Groups with less or equal students' names:");
			for (GroupDto groupDto : groupNames){
				out.println(groupDto.getGroupName());
			}
		}
	}

	private void findStudentsRelatedToCourse() {
		String courseName = readStringFromUser("Enter the course name: ");
		if (isEmpty(courseName)) {
			return;
		}

		List<StudentDto> students = studentService.findStudentsRelatedToCourse(courseName);
		if (students.isEmpty()){
			out.println("Invalid course or no students on this course.");
		} else {
			out.println("Students related to the course " + courseName + ":");
			for (StudentDto studentDto : students){
				out.println(studentDto.getFirstName() + " " + studentDto.getLastName());
			}
		}
	}

	private void addNewStudent() {
		String firstName = readStringFromUser("Enter first name: ");
		if (isEmpty(firstName)) {
			return;
		}

		String lastName = readStringFromUser("Enter last name: ");
		if (isEmpty(lastName)) {
			return;
		}

		int groupId = readIntFromUser("Enter group ID: ");
		if (groupId == -1) {
			return;
		}

		StudentDto studentDto = StudentDto.builder().groupId(groupId).firstName(firstName).lastName(lastName).build();
		try {
			studentService.createStudent(studentDto);
			out.println("Student created successfully:" + " " + studentDto.getFirstName()
				+ " " + studentDto.getLastName() + " " + studentDto.getGroupId());
		} catch (GroupNotFoundException e) {
			out.println("Group not found for student");
		}
	}

	private void deleteStudent() {
		int studentId = readIntFromUser("Enter STUDENT_ID to delete: ");
		if (studentId == -1) {
			return;
		}

		StudentDto student = studentService.getStudentById(studentId);
		if (student != null){
			studentService.deleteStudent(studentId);
			out.println("Student with STUDENT_ID " + studentId + " deleted successfully.");
		} else {
			out.println("Student with STUDENT_ID " + studentId + " not found.");
		}
	}

	private void addStudentToCourse() {
		List<CourseDto> courses = courseService.getAllCourses();

		out.println("Available Courses:");
		for (int i = 0; i < courses.size(); i++) {
			out.println((i + 1) + ": " + courses.get(i).getCourseName());
		}

		int courseId = readIntFromUser("Enter the number of the course to add the student to: ");
		if (courseId < 1 || courseId > courses.size()) {
			out.println("Invalid course selection.");
			return;
		}

		int studentId = readIntFromUser("Enter STUDENT_ID to add to the course: ");
		if (studentId == -1) {
			return;
		}

		try {
			int rowsAffected = studentService.addStudentToCourse(studentId, courseId);

			if (rowsAffected > 0){
				out.println("Student added to the course successfully.");
			} else {
                out.printf("Student with id %d is exist in course with id %d%n", studentId, courseId);
			}
		} catch (StudentNotFoundException e){
			out.println("Student not found with id: " + studentId);
		}
	}

	private void removeStudentFromCourse() {
		int studentId = readIntFromUser("Enter STUDENT_ID to remove from the course: ");
		if (studentId == -1) {
			return;
		}

		int courseId = readIntFromUser("Enter COURSE_ID to remove the student from: ");
		if (courseId == -1) {
			return;
		}

		studentService.removeStudentFromCourse(studentId, courseId);
		out.println("Student removed from the course successfully.");
	}

	private String readStringFromUser(String prompt) {
		out.print(prompt);
		return scanner.nextLine().trim();
	}

	private int readIntFromUser(String prompt) {
		out.print(prompt);
		try {
			return Integer.parseInt(scanner.nextLine().trim());
		} catch (NumberFormatException e) {
			out.println(INVALID_INPUT_MESSAGE);
			return -1;
		}
	}

	private boolean isEmpty(String string) {
		if (string.isEmpty()) {
			out.println("Input cannot be empty.");
			return true;
		}
		return false;
	}
}
