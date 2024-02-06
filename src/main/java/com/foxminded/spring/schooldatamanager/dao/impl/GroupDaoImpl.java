package com.foxminded.spring.schooldatamanager.dao.impl;

import com.foxminded.spring.schooldatamanager.dao.GroupDao;
import com.foxminded.spring.schooldatamanager.dto.GroupDto;
import com.foxminded.spring.schooldatamanager.entity.Group;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class GroupDaoImpl implements GroupDao {
	private final JdbcTemplate jdbcTemplate;

	@Override
	public List<Group> getAllGroups() {
		try {
			return jdbcTemplate.query("SELECT * FROM groups",
				new BeanPropertyRowMapper<>(Group.class));
		} catch (EmptyResultDataAccessException e) {
			return Collections.emptyList();
		}
	}

	@Override
	public Group getGroupById(int groupId) {
		try {
			return jdbcTemplate.queryForObject("SELECT * FROM groups WHERE group_id = ?",
				new BeanPropertyRowMapper<>(Group.class),groupId);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public List<Group> findGroupNamesWithLessOrEqualStudents(int maxStudents) {
		try {
			return jdbcTemplate.query("SELECT group_name " +
					"FROM groups " +
					"WHERE (SELECT COUNT(*) FROM students WHERE students.group_id = groups.group_id) <= ?",
				new BeanPropertyRowMapper<>(Group.class), maxStudents);
		} catch (EmptyResultDataAccessException e) {
			return Collections.emptyList();
		}
	}

	@Override
	public GroupDto createGroup(GroupDto group) {
			jdbcTemplate.update("INSERT INTO groups (group_name) VALUES (?)",
					group.getGroupName());

			return group;
	}

	@Override
	public void updateGroup(Group group) {
			jdbcTemplate.update("UPDATE groups SET group_name = ? WHERE group_id = ?",
				group.getGroupName(), group.getGroupId());
	}

	@Override
	public void deleteGroup(int groupId) {
		jdbcTemplate.update("UPDATE students SET group_id = -1 WHERE group_id = ?",
			groupId);
		jdbcTemplate.update("DELETE FROM groups WHERE group_id = ?",
			groupId);
	}
}
