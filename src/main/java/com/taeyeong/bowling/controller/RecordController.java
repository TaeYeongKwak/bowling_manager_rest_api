package com.taeyeong.bowling.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taeyeong.bowling.common.CommonResult;
import com.taeyeong.bowling.common.service.ResponseService;
import com.taeyeong.bowling.record.dto.DateBetweenRequest;
import com.taeyeong.bowling.record.dto.RecordRequest;
import com.taeyeong.bowling.record.service.RecordService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@Api(tags = {"5. Record"})
@RequiredArgsConstructor
@RestController
public class RecordController {
	
	private final ResponseService responseService;
	private final RecordService recordService;
	
	@ApiOperation(value = "그룹 회원 기록 전체조회", notes = "해당 그룹 회원들의 전체 기록을 조회")
	@GetMapping("/records/group")
	public CommonResult searchGroupRecord(Long gid) {
		return responseService.getSingleResult(recordService.getGroupMemberRecord(gid));
	}
	
	@ApiOperation(value = "그룹 회원 정해진 기간내 기록 조회", notes = "해당 그룹 회원들의 정해진 기간내의 기록을 조회")
	@GetMapping("/records/group/date")
	public CommonResult searchGroupRecordDate(Long gid, DateBetweenRequest dateBetween) {
		return responseService.getSingleResult(recordService.getGroupMemberRecord(gid, dateBetween));
	}
	
	@ApiOperation(value = "그룹 회원 기록 요약정보 조회", notes = "해당 그룹 회원들의 기록 요약정보를 조회")
	@GetMapping("/records/group/summary")
	public CommonResult recordSummaryGroup(Long gid) {
		return responseService.getSingleResult(recordService.getSummaryGroup(gid));
	}
	
	@ApiOperation(value = "본인 기록 조회", notes = "본인의 전체 기록 조회")
	@GetMapping("/records/me")
	public CommonResult myRecord() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return responseService.getSingleResult(recordService.getMemberRecord(auth.getName()));
	}
	
	@ApiOperation(value = "본인 정해진 기간내 기록 조회", notes = "본인의 정해진 기간내의 기록을 조회")
	@GetMapping("/records/me/date")
	public CommonResult myRecordBetweenDate(DateBetweenRequest dateBetween) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return responseService.getSingleResult(recordService.getMemberRecord(auth.getName(), dateBetween));
	}
	
	@ApiOperation(value = "본인 기록 요약정보 조회", notes = "본인의 기록 요약정보를 조회")
	@GetMapping("/records/me/summary")
	public CommonResult recordSummary(DateBetweenRequest dateBetween) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return responseService.getSingleResult(recordService.getSummaryMe(auth.getName()));
	}
	
	@ApiOperation(value = "본인 기록 저장", notes = "본인의 기록을 저장")
	@PostMapping("/record")
	public CommonResult record(RecordRequest recordRequest) throws Exception {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		recordService.saveRecord(auth.getName(), recordRequest);
		return responseService.getSuccessfulResult();
	}
	
}
