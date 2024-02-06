package com.foxminded.spring.schooldatamanager.controller;
import com.foxminded.spring.schooldatamanager.dto.GroupDto;
import com.foxminded.spring.schooldatamanager.mapper.GroupMapper;
import com.foxminded.spring.schooldatamanager.service.GroupService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/group")
public class GroupController {
	private final GroupService groupService;
	private final GroupMapper groupMapper;

	@GetMapping("/all")
	public ResponseEntity<List<GroupDto>> getAllGroups() {
		log.info("Getting all groups");
		List<GroupDto> groups = groupService.getAllGroups();
		log.info("Retrieved {} groups", groups.size());
		return new ResponseEntity<>(groups, HttpStatus.OK);
	}

	@GetMapping("/{groupId}")
	public ResponseEntity<GroupDto> getGroupById(@PathVariable("groupId") int groupId) {
		log.info("Getting group with id {}", groupId);
		GroupDto group = groupService.getGroupById(groupId);

		if (group != null) {
			log.info("Found group with id {}", groupId);
			return new ResponseEntity<>(group, HttpStatus.OK);
		} else {
			log.warn("No group found with id {}", groupId);
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/less-or-equal-students")
	public ResponseEntity<List<GroupDto>> findGroupsWithLessOrEqualStudents(@RequestParam("maxStudents") int maxStudents) {
		log.info("Finding groups with less or equal than {} students", maxStudents);
		List<GroupDto> groupNames = groupService.findGroupNamesWithLessOrEqualStudents(maxStudents);

		if (!groupNames.isEmpty()) {
			log.info("Found {} groups with less or equal than {} students", groupNames.size(), maxStudents);
			return new ResponseEntity<>(groupNames, HttpStatus.OK);
		}else {
			log.warn("No groups found with less or equal than {} students", maxStudents);
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping
	public ResponseEntity<GroupDto> createGroup(@Valid @RequestBody GroupDto groupDto) {
		log.info("Creating new group");
		var createdGroup = groupService.createGroup(groupDto);
		URI location = ServletUriComponentsBuilder
			.fromCurrentRequest()
			.path("/{groupId}")
			.buildAndExpand(groupMapper.toLastGroup(createdGroup).getGroupId()).toUri();
		log.info("Created new group with id {}", groupMapper.toLastGroup(createdGroup).getGroupId());
		return ResponseEntity.created(location).build();
	}
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public void handleValidationExceptions(MethodArgumentNotValidException ex) {
		ex.getBindingResult().getAllErrors().forEach(error -> {
			String errorMessage = error.getDefaultMessage();
			log.error("Validation error: {}", errorMessage);
		});
	}

	@PutMapping("/{groupId}")
	public ResponseEntity<GroupDto> updateGroup(@PathVariable("groupId") int groupId, @RequestBody GroupDto groupDto) {
		log.info("Updating group with id {}", groupId);
		var updatedGroup = groupService.updateGroup(groupId, groupDto);

		if (updatedGroup != null) {
			log.info("Updated group with id {}", groupId);
			return new ResponseEntity<>(groupDto, HttpStatus.OK);
		} else {
			log.warn("No group found with id {} for update", groupId);
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/{groupId}")
	public ResponseEntity<Void> deleteGroup(@PathVariable("groupId") int groupId) {
		log.info("Deleting group with id {}", groupId);
		groupService.deleteGroup(groupId);
		log.info("Deleted group with id {}", groupId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
