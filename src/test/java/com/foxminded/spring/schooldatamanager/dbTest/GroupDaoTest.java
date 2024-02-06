package com.foxminded.spring.schooldatamanager.dbTest;


import com.foxminded.spring.schooldatamanager.TestDataSourceConfig;
import com.foxminded.spring.schooldatamanager.dao.GroupDao;
import com.foxminded.spring.schooldatamanager.dto.GroupDto;
import com.foxminded.spring.schooldatamanager.entity.Group;
import com.foxminded.spring.schooldatamanager.dao.impl.GroupDaoImpl;
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
	scripts = {"/initDB.sql","/clear_tables.sql", "/sample_data.sql"},
	executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
@ContextConfiguration(classes = TestDataSourceConfig.class)
class GroupDaoTest {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	private GroupDao groupDAO;

	@Container
	static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(DockerImageName.parse("postgres:15.3"));

	@BeforeEach
	void setUp() {
		groupDAO = new GroupDaoImpl(jdbcTemplate);
	}

	@Test
	@Order(value = 1)
	void testConnectionToDatabase() {
		assertNotNull(groupDAO);
	}

	@Test
	void testGetAllGroups() {
		List<Group> groups = groupDAO.getAllGroups();

		assertNotNull(groups);
		assertEquals(3, groups.size());
	}

	@Test
	void testGetGroupById() {
		Group group = groupDAO.getGroupById(1);

		assertNotNull(group);
		assertEquals("FI-01", group.getGroupName());
	}

	@Test
	void testCreateGroup() {
		GroupDto newGroup = new GroupDto();
		newGroup.setGroupName("Test Group");
		groupDAO.createGroup(newGroup);

		List<Group> groups = groupDAO.getAllGroups();
		assertNotNull(groups);
		assertEquals(4, groups.size());
	}

	@Test
	void testUpdateGroup() {
		Group groupToUpdate = groupDAO.getGroupById(1);
		groupToUpdate.setGroupName("Updated Group Name");
		groupDAO.updateGroup(groupToUpdate);

		Group updatedGroup = groupDAO.getGroupById(1);
		assertEquals("Updated Group Name", updatedGroup.getGroupName());
	}

	@Test
	void testDeleteGroup() {
		groupDAO.deleteGroup(2);

		List<Group> groups = groupDAO.getAllGroups();
		assertNotNull(groups);
		assertEquals(2, groups.size());
	}
}
