package com.taeyeong.bowling.controller;

import org.springframework.data.domain.Slice;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taeyeong.bowling.common.CommonResult;
import com.taeyeong.bowling.common.service.ResponseService;
import com.taeyeong.bowling.group.dto.CreateGroupRequest;
import com.taeyeong.bowling.group.entity.BowlingGroup;
import com.taeyeong.bowling.group.service.GroupService;
import com.taeyeong.bowling.join.service.JoinService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@Api(tags = {"3. Group"})
@RestController
@RequiredArgsConstructor
public class GroupController {
	
	private final ResponseService responseService;
	private final GroupService groupService;
	private final JoinService joinService;
	
	@ApiOperation(value = "그룹 세부정보 조회", notes = "그룹의 세부 정보를 조회")
	@GetMapping("/group/{group-id}")
	public CommonResult findGroupOne(@PathVariable("group-id") Long gid) {
		return responseService.getSingleResult(groupService.findGroupOne(gid));
	}
	
	@ApiOperation(value = "전체 그룹리스트 조회", notes = "전체 그룹리스트를 조회")
	@GetMapping("/groups")
	public CommonResult findGroups(Integer page) {
		Slice<BowlingGroup> groupSlice =  groupService.findGroupSlice(page);
		return responseService.getSingleResult(groupSlice);
	}
	
	@ApiOperation(value = "그룹 검색", notes = "검색한 그룹리스트를 조회")
	@GetMapping("/group/search")
	public CommonResult searchGroups(String title, Integer page) {
		Slice<BowlingGroup> groupSlice =  groupService.findGroupSlice(page, title);
		return responseService.getSingleResult(groupSlice);
	}
	
	@ApiOperation(value = "그룹 생성", notes = "새로운 그룹을 생성")
	@PostMapping("/group")
	public CommonResult addGroup(CreateGroupRequest createGroupRequest) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		groupService.createGroup(auth.getName(), createGroupRequest);
		return responseService.getSuccessfulResult();
	}
	
	@ApiOperation(value = "그룹 수정", notes = "이미 생성된 그룹의 정보를 수정")
	@PutMapping("/group/{group-id}")
	public CommonResult modifyGroup(@PathVariable("group-id") Long gid, CreateGroupRequest createGroupRequest) {
		groupService.modifyGroup(gid, createGroupRequest);
		return responseService.getSuccessfulResult();
	}
	
	@ApiOperation(value = "그룹 삭제", notes = "이미 생성된 그룹을 삭제")
	@DeleteMapping("/group/{group-id}")
	public CommonResult deleteGroup(@PathVariable("group-id") Long gid) {
		groupService.deleteGroup(gid);
		return responseService.getSuccessfulResult();
	}
	
	@ApiOperation(value = "그룹 회원리스트 조회", notes = "해당 그룹에 가입되어있는 회원리스트를 조회")
	@GetMapping("/group/{group-id}/members")
	public CommonResult groupMemberList(@PathVariable("group-id") Long gid) {
		return responseService.getListResult(joinService.findGroupMember(gid));
	}
	
}
