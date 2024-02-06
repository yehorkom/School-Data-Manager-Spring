package com.foxminded.spring.schooldatamanager.util;

import com.foxminded.spring.schooldatamanager.dao.impl.CourseDaoImpl;
import com.foxminded.spring.schooldatamanager.dao.impl.GroupDaoImpl;
import com.foxminded.spring.schooldatamanager.dao.impl.StudentDaoImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DataManager {
	private final DataGenerator dataGenerator;
	private final CourseDaoImpl courseDAO;
	private final StudentDaoImpl studentDAO;
	private final GroupDaoImpl groupDAO;

	public void checkAndFill() {
		if (isTablesHaveInfo()) {
			return;
		}

		dataGenerator.generateData();
	}

	public boolean isTablesHaveInfo(){
		return !(groupDAO.getAllGroups().isEmpty() && courseDAO.getAllCourses().isEmpty()
			&& studentDAO.getAllStudents().isEmpty());
	}
}
