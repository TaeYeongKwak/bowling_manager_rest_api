package com.taeyeong.bowling.member.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.taeyeong.bowling.exception.MemberNotFoundException;
import com.taeyeong.bowling.member.repository.MemberRepository;
import com.taeyeong.bowling.oauth.dto.MemberPrincipal;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

	private final MemberRepository memberRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return MemberPrincipal.create(
				memberRepository.findByEmail(username).orElseThrow(MemberNotFoundException::new)
		);
	}

}
