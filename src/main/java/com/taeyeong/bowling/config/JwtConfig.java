package com.taeyeong.bowling.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.taeyeong.bowling.oauth.token.AuthTokenProvider;

@Configuration
public class JwtConfig {
	
	@Bean
	public AuthTokenProvider jwtProvider() {
		return new AuthTokenProvider();
	}
	
}
