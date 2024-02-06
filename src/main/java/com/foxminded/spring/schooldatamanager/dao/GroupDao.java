package com.foxminded.spring.schooldatamanager.dao;

import com.foxminded.spring.schooldatamanager.dto.GroupDto;
import com.foxminded.spring.schooldatamanager.entity.Group;

import java.util.List;

public interface GroupDao {
	List<Group> getAllGroups();
	Group getGroupById(int groupId);
	List<Group> findGroupNamesWithLessOrEqualStudents (int maxStudents);
	GroupDto createGroup(GroupDto groupDto);
	void updateGroup(Group group);
	void deleteGroup(int groupId);
}
