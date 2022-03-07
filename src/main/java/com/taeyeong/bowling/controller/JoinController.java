package com.taeyeong.bowling.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taeyeong.bowling.common.CommonResult;
import com.taeyeong.bowling.common.service.ResponseService;
import com.taeyeong.bowling.join.service.JoinService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@Api(tags = {"4. Join"})
@RestController
@RequiredArgsConstructor
public class JoinController {
	
	private final ResponseService responseService;
	private final JoinService joinService;
	
	@ApiOperation(value = "그룹 가입", notes = "이미 생성된 그룹에 가입")
	@PostMapping("/join/{group-id}")
	public CommonResult join(@PathVariable("group-id") Long groupId) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		joinService.joinGroup(auth.getName(), groupId, false);
		return responseService.getSuccessfulResult();
	}
	
	@ApiOperation(value = "가입한 그룹 조회", notes = "이미 가입한 그룹의 정보를 조회")
	@GetMapping("/join/groups")
	public CommonResult joinGroupList() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return responseService.getSingleResult(joinService.findJoinGroup(auth.getName()));
	}
	
	@ApiOperation(value = "가입한 그룹 탈퇴", notes = "가입한 그룹에서 탈퇴")
	@DeleteMapping("/join/{group-id}")
	public CommonResult withDrawalGroup(@PathVariable("group-id") Long groupId) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		joinService.deleteGroupMember(auth.getName(), groupId);
		return responseService.getSuccessfulResult();
	}
	
}
