package com.taeyeong.bowling.controller;

import java.util.Date;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taeyeong.bowling.common.CommonResult;
import com.taeyeong.bowling.common.service.ResponseService;
import com.taeyeong.bowling.exception.DuplicateEmailException;
import com.taeyeong.bowling.member.dto.LoginRequest;
import com.taeyeong.bowling.member.dto.SignUpRequest;
import com.taeyeong.bowling.member.entity.Member;
import com.taeyeong.bowling.member.entity.ProviderType;
import com.taeyeong.bowling.member.entity.Role;
import com.taeyeong.bowling.member.repository.MemberRepository;
import com.taeyeong.bowling.oauth.dto.MemberPrincipal;
import com.taeyeong.bowling.oauth.token.AuthToken;
import com.taeyeong.bowling.oauth.token.AuthTokenProvider;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@Api(tags = {"1. Auth"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
	
	private final ResponseService responseService;
	private final AuthenticationManager authenticationManager;
	private final AuthTokenProvider authTokenProvider;
	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;
	
	private long tokenValidMilisecond = 1000L * 60 * 60;
	
	@ApiOperation(value = "로그인", notes = "회원 로그인 기능")
	@PostMapping("/login")
	public CommonResult login(LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						loginRequest.getEmail(),
						loginRequest.getPassword()
					));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		MemberPrincipal principal = (MemberPrincipal)authentication.getPrincipal();
		Date now = new Date();
		AuthToken accessToken = authTokenProvider.createAuthToken(
				loginRequest.getEmail(), 
				principal.getRole().getKey(), 
				new Date(now.getTime() + tokenValidMilisecond));
		
		return responseService.getSingleResult(accessToken.getToken());
	}
	
	@ApiOperation(value = "회원가입", notes = "회원가입 기능")
	@PostMapping("/signup")
	public CommonResult signUp(SignUpRequest signUpRequest) {
		if(memberRepository.findByEmail(signUpRequest.getEmail()).isPresent())
			throw new DuplicateEmailException();
		
		Member member = Member.builder()
							.email(signUpRequest.getEmail())
							.password(passwordEncoder.encode(signUpRequest.getPassword()))
							.name(signUpRequest.getName())
							.role(Role.USER)
							.providerType(ProviderType.LOCAL)
							.build();
		
		memberRepository.save(member);
		return responseService.getSuccessfulResult();
	}
	
}
