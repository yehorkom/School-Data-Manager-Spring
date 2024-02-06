package com.foxminded.spring.schooldatamanager.service;

import com.foxminded.spring.schooldatamanager.dto.CourseDto;
import com.foxminded.spring.schooldatamanager.dto.GroupDto;
import java.util.List;

public interface GroupService {
	List<GroupDto> getAllGroups();
	GroupDto getGroupById(int groupId);
	List<GroupDto> findGroupNamesWithLessOrEqualStudents (int maxStudents);
	GroupDto createGroup(GroupDto group);
	GroupDto updateGroup(int groupId, GroupDto groupDto);
	void deleteGroup(int groupId);
}
