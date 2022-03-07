package com.taeyeong.bowling.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taeyeong.bowling.common.CommonResult;
import com.taeyeong.bowling.common.service.ResponseService;
import com.taeyeong.bowling.exception.MemberNotFoundException;
import com.taeyeong.bowling.member.repository.MemberRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@Api(tags = {"2. Member"})
@RequiredArgsConstructor
@RestController
public class MemberController {

	private final MemberRepository memberRepository;
	private final ResponseService responseService;
	
	@ApiOperation(value = "회원 조회", notes = "로그인에 성공한 회원의 정보를 조회")
	@GetMapping("/member/me")
	public CommonResult showInfo() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return responseService.getSingleResult(
				memberRepository.findByEmail(auth.getName()).orElseThrow(MemberNotFoundException::new));
	}
	
}
