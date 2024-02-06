package com.foxminded.spring.schooldatamanager.service.impl;

import com.foxminded.spring.schooldatamanager.dao.GroupDao;
import com.foxminded.spring.schooldatamanager.dto.GroupDto;
import com.foxminded.spring.schooldatamanager.entity.Group;
import com.foxminded.spring.schooldatamanager.mapper.GroupMapper;
import com.foxminded.spring.schooldatamanager.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {
	private final GroupDao groupDao;

	@Override
	public List<GroupDto> getAllGroups() {
		return groupDao.getAllGroups()
			.stream()
			.map(GroupMapper::toGroupDto)
			.toList();
	}

	@Override
	public GroupDto getGroupById(int groupId) {
		Group group = groupDao.getGroupById(groupId);

		return GroupMapper.toGroupDto(group);
	}

	@Override
	public List<GroupDto> findGroupNamesWithLessOrEqualStudents(int maxStudents) {
		return groupDao.findGroupNamesWithLessOrEqualStudents(maxStudents)
			.stream()
			.map(GroupMapper::toGroupDto)
			.toList();
	}

	@Override
	public GroupDto createGroup(GroupDto groupDto) {
		return groupDao.createGroup(groupDto);
	}

	@Override
	public GroupDto updateGroup(int groupId, GroupDto groupDto) {
		Group existingGroup = groupDao.getGroupById(groupId);

		if (existingGroup != null) {
			Group updatedGroup = GroupMapper.toGroup(groupDto);
			updatedGroup.setGroupId(groupId);
			groupDao.updateGroup(updatedGroup);
			return groupDto;
		}

		return  null;
	}

	@Override
	public void deleteGroup(int groupId) {
		Group existingGroup = groupDao.getGroupById(groupId);

		if (existingGroup != null) {
			groupDao.deleteGroup(groupId);
		}
	}
}
