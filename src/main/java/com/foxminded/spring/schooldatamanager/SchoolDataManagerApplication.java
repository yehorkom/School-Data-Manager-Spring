package com.foxminded.spring.schooldatamanager;

import com.foxminded.spring.schooldatamanager.util.ConsoleMenu;
import com.foxminded.spring.schooldatamanager.util.DataManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SchoolDataManagerApplication {
	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(SchoolDataManagerApplication.class, args);
		DataManager dataManager = context.getBean(DataManager.class);
		dataManager.checkAndFill();

		ConsoleMenu consoleMenu = context.getBean(ConsoleMenu.class);
		consoleMenu.launchingConsole();
	}
}
