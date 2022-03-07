package com.taeyeong.bowling.record.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.taeyeong.bowling.exception.GroupNotFoundException;
import com.taeyeong.bowling.exception.MemberNotFoundException;
import com.taeyeong.bowling.group.repository.GroupRepository;
import com.taeyeong.bowling.join.entity.GroupMember;
import com.taeyeong.bowling.join.repository.GroupMemberRepository;
import com.taeyeong.bowling.member.entity.Member;
import com.taeyeong.bowling.member.repository.MemberRepository;
import com.taeyeong.bowling.record.dto.DateBetweenRequest;
import com.taeyeong.bowling.record.dto.RecordRequest;
import com.taeyeong.bowling.record.dto.SummaryRecord;
import com.taeyeong.bowling.record.entity.Record;
import com.taeyeong.bowling.record.repository.RecordRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class RecordServiceImpl implements RecordService{
	
	private final RecordRepository recordRepository;
	private final MemberRepository memberRepository;
	private final GroupRepository groupRepository;
	private final GroupMemberRepository groupMemberRepository;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveRecord(String email, RecordRequest recordRequest) throws ParseException {
		Member member = memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yy-mm-dd");
		recordRepository.save(
				Record.builder()
					.member(member)
					.recordDate(dateFormat.parse(recordRequest.getRecordDateFormat()))
					.pattern(recordRequest.getPattern())
					.score(recordRequest.getScore())
					.build());
	}

	@Override
	@Transactional(readOnly = true)
	public List<Record> getMemberRecord(String email) {
		return recordRepository.findAllByMember(
				memberRepository.findByEmail(email)
				.orElseThrow(MemberNotFoundException::new));
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Record> getMemberRecord(String email, DateBetweenRequest dateBetween) {
		return recordRepository.findAllByMemberAndRecordDateBetween(
				memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new), 
				dateBetween.getStart(), 
				dateBetween.getEnd());
	}

	@Override
	@Transactional(readOnly = true)
	public Map<Long, List<Record>> getGroupMemberRecord(Long gid) {
		List<Member> members = this.getGroupMemberList(gid);
		Map<Long, List<Record>> data = new HashMap<Long, List<Record>>(); 
		for(Member member : members)
			data.put(member.getMid(), recordRepository.findAllByMember(member));
		
		return data;
	}

	@Override
	@Transactional(readOnly = true)
	public Map<Long, List<Record>> getGroupMemberRecord(Long gid, DateBetweenRequest dateBetween) {
		List<Member> members = this.getGroupMemberList(gid);
		Map<Long, List<Record>> data = new HashMap<Long, List<Record>>();
		for(Member member : members)
			data.put(member.getMid(), 
					recordRepository.findAllByMemberAndRecordDateBetween(
							member, 
							dateBetween.getStart(), 
							dateBetween.getEnd()));
		
		return data;
	}
	
	@Override
	@Transactional(readOnly = true)
	public SummaryRecord getSummaryMe(String email) {
		Member member = memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);
		return recordRepository.summaryRecordByMember(member.getMid());
	}
	
	@Override
	public Map<Long, SummaryRecord> getSummaryGroup(Long gid) {
		List<Member> members = this.getGroupMemberList(gid);
		Map<Long, SummaryRecord> data = new HashMap<Long, SummaryRecord>();
		for(Member member : members)
			data.put(member.getMid(), recordRepository.summaryRecordByMember(member.getMid()));
		return data;
	}
	
	private List<Member> getGroupMemberList(Long gid){
		return groupMemberRepository.findByBowlingGroup(
				groupRepository.findById(gid).orElseThrow(GroupNotFoundException::new))
				.stream()
				.map(GroupMember::getMember)
				.collect(Collectors.toList());
	}

}
