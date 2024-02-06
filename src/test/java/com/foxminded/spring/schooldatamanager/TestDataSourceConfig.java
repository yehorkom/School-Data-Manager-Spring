package com.foxminded.spring.schooldatamanager;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@TestConfiguration
public class TestDataSourceConfig {

	@Bean
	@Primary
	public DataSource testDataSource() {
		String jdbcUrl = "jdbc:tc:postgresql:15.3:///test";
		String username = "test";
		String password = "test";

		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("org.testcontainers.jdbc.ContainerDatabaseDriver");
		dataSource.setUrl(jdbcUrl);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		return dataSource;
	}

	@Bean
	public JdbcTemplate jdbcTemplate(DataSource testDataSource) {
		return new JdbcTemplate(testDataSource);
	}
}
