package com.foxminded.spring.schooldatamanager.mockTest;

import com.foxminded.spring.schooldatamanager.dto.GroupDto;
import com.foxminded.spring.schooldatamanager.service.impl.GroupServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.doNothing;
@SpringBootTest
class GroupServiceTest {
	@Mock
	private GroupServiceImpl groupService;

	@Test
	void testGetAllGroup() {
		when(groupService.getAllGroups()).thenReturn(Collections.singletonList(new GroupDto()));
		List<GroupDto> result = groupService.getAllGroups();

		assertEquals(1, result.size());
		verify(groupService, times(1)).getAllGroups();
	}

	@Test
	void testGetGroupByIdGroupExists() {
		GroupDto groupDto = GroupDto.builder().groupName("Test").build();

		when(groupService.getGroupById(anyInt())).thenReturn(groupDto);
		GroupDto result = groupService.getGroupById(1);

		assertEquals(groupDto.getGroupName(), result.getGroupName());
		verify(groupService).getGroupById(1);
	}

	@Test
	void testGetGroupByIdGroupDoesNotExist() {
		when(groupService.getGroupById(anyInt())).thenReturn(null);
		groupService.getGroupById(1);
		verify(groupService).getGroupById(1);
	}

	@Test
	void testFindGroupsNamesWithLessOrEqualStudentsGroupsExist() {
		GroupDto groupDto1 = GroupDto.builder().groupName("Group1").build();
		GroupDto groupDto2 = GroupDto.builder().groupName("Group2").build();
		List<GroupDto> groups = Arrays.asList(groupDto1, groupDto2);
		when(groupService.findGroupNamesWithLessOrEqualStudents(anyInt())).thenReturn(groups);
		List<GroupDto> result = groupService.findGroupNamesWithLessOrEqualStudents(5);

		assertEquals(groups.size(), result.size());
		for (int i = 0; i < groups.size(); i++) {
			assertEquals(groups.get(i).getGroupName(), result.get(i).getGroupName());
		}
		verify(groupService).findGroupNamesWithLessOrEqualStudents(5);
	}

	@Test
	void testFindGroupsNamesWithLessOrEqualStudentsNoGroups() {
		when(groupService.findGroupNamesWithLessOrEqualStudents(anyInt())).thenReturn(Collections.emptyList());
		groupService.findGroupNamesWithLessOrEqualStudents(5);
		verify(groupService).findGroupNamesWithLessOrEqualStudents(5);
	}
	//which one is better? first or second
	@Test
	void  testCreateGroupFirst() {
		GroupDto groupDto = GroupDto.builder().groupName("Test").build();

		doAnswer(invocation -> {
			GroupDto argument = invocation.getArgument(0);
			assertEquals(groupDto.getGroupName(), argument.getGroupName());
			return null;
		}).when(groupService).createGroup(any(GroupDto.class));

		groupService.createGroup(groupDto);
		verify(groupService).createGroup(any(GroupDto.class));
	}

	@Test
	void  testCreateGroupSecond() {
		GroupDto groupDto = GroupDto.builder().groupName("Test").build();

		when(groupService.createGroup(any(GroupDto.class))).thenReturn(groupDto);
		GroupDto result = groupService.createGroup(groupDto);

		assertEquals(groupDto.getGroupName(), result.getGroupName());
		verify(groupService).createGroup(any(GroupDto.class));
	}

	@Test
	void testUpdateGroup() {
		GroupDto groupDto = GroupDto.builder().groupName("Test").build();

		when(groupService.updateGroup(1, groupDto)).thenReturn(groupDto);
		GroupDto result = groupService.updateGroup(1, groupDto);

		assertEquals(groupDto.getGroupName(), result.getGroupName());
		verify(groupService).updateGroup(1, groupDto);
	}

	@Test
	void testDeleteGroup() {
		doNothing().when(groupService).deleteGroup(anyInt());
		groupService.deleteGroup(1);
		verify(groupService).deleteGroup(1);
	}
}
