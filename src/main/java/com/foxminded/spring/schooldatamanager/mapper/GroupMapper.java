package com.foxminded.spring.schooldatamanager.mapper;

import com.foxminded.spring.schooldatamanager.dto.GroupDto;
import com.foxminded.spring.schooldatamanager.entity.Group;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupMapper {
	private final JdbcTemplate jdbcTemplate;
	public static Group toGroup(GroupDto groupDto) {
		if(groupDto == null){
			return null;
		}

		Group group = new Group();
		group.setGroupName(groupDto.getGroupName());

		return group;
	}

	public Group toLastGroup(GroupDto groupDto) {
		if(groupDto == null){
			return null;
		}

		List<Group> lastGroup = jdbcTemplate.query("SELECT * FROM groups ORDER BY group_id DESC LIMIT 1",
			new BeanPropertyRowMapper<>(Group.class));

		Group group = new Group();
		group.setGroupId(lastGroup.getLast().getGroupId());
		group.setGroupName(groupDto.getGroupName());

		return group;
	}
	public static GroupDto toGroupDto(Group group) {
		if(group == null){
			return null;
		}

		GroupDto groupDto = new GroupDto();
		groupDto.setGroupName(group.getGroupName());

		return groupDto;
	}
}
