package com.taeyeong.bowling.advice;

import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.taeyeong.bowling.common.CommonResult;
import com.taeyeong.bowling.common.service.ResponseService;
import com.taeyeong.bowling.exception.AuthenticationEntryPointException;
import com.taeyeong.bowling.exception.DuplicateEmailException;
import com.taeyeong.bowling.exception.DuplicationGroupJoinException;
import com.taeyeong.bowling.exception.GroupNotFoundException;
import com.taeyeong.bowling.exception.MemberNotFoundException;
import com.taeyeong.bowling.exception.NotConsentEmailException;
import com.taeyeong.bowling.exception.NotWorkingDatabaseException;
import com.taeyeong.bowling.exception.OAuth2ProviderMissMatchException;
import com.taeyeong.bowling.exception.OverMaxMemberException;

import dev.akkinoc.util.YamlResourceBundle;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestControllerAdvice
public class ExceptionAdvice {
	
	@Value("${spring.messages.basename}")
	private String baseName;
	
	private final ResponseService responseService;
	
	@ExceptionHandler(AuthenticationEntryPointException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	protected CommonResult unknownException() {
		return responseService.getFailResult(getCode("entryPoint"), getMessage("entryPoint"));
	}
	
	@ExceptionHandler(AccessDeniedException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	protected CommonResult accessDeniedException() {
		return responseService.getFailResult(getCode("accessDenied"), getMessage("accessDenied"));
	}
	
	@ExceptionHandler(NotConsentEmailException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	protected CommonResult notConsentEmailException() {
		return responseService.getFailResult(getCode("notConsentEmail"), getMessage("notConsentEmail"));
	}
	
	@ExceptionHandler(MemberNotFoundException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	protected CommonResult memberNotFoundException() {
		return responseService.getFailResult(getCode("memberNotFound"), getMessage("memberNotFound"));
	}
	
	@ExceptionHandler(DuplicateEmailException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	protected CommonResult duplicateEmailException(){
		return responseService.getFailResult(getCode("duplicateEmail"), getMessage("duplicateEmail"));
	}
	
	@ExceptionHandler(GroupNotFoundException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	protected CommonResult groupNotFoundException(){
		return responseService.getFailResult(getCode("groupNotFound"), getMessage("groupNotFound"));
	}
	
	@ExceptionHandler(NotWorkingDatabaseException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	protected CommonResult notWorkingDatabaseException(){
		return responseService.getFailResult(getCode("notWorkingDatabase"), getMessage("notWorkingDatabase"));
	}
	
	@ExceptionHandler(OAuth2ProviderMissMatchException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	protected CommonResult oAuth2ProviderMissMatchException(){
		return responseService.getFailResult(getCode("oauth2ProviderMissMatch"), getMessage("oauth2ProviderMissMatch"));
	}
	
	@ExceptionHandler(DuplicationGroupJoinException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	protected CommonResult duplicationGroupJoinException(){
		return responseService.getFailResult(getCode("duplicationGroupJoin"), getMessage("duplicationGroupJoin"));
	}
	
	@ExceptionHandler(OverMaxMemberException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	protected CommonResult overMaxMemberException(){
		return responseService.getFailResult(getCode("overMaxMember"), getMessage("overMaxMember"));
	}
	
	private String getMessage(String key) {
		ResourceBundle bundle = ResourceBundle.getBundle(baseName, YamlResourceBundle.Control.INSTANCE);
		return bundle.getString(key + ".message");
	}
	
	private int getCode(String key) {
		ResourceBundle bundle = ResourceBundle.getBundle(baseName, YamlResourceBundle.Control.INSTANCE);
		return Integer.parseInt(bundle.getString(key + ".code"));
	}

}
