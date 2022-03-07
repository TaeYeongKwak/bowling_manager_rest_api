package com.taeyeong.bowling.join.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.taeyeong.bowling.exception.DuplicationGroupJoinException;
import com.taeyeong.bowling.exception.GroupNotFoundException;
import com.taeyeong.bowling.exception.MemberNotFoundException;
import com.taeyeong.bowling.exception.NotWorkingDatabaseException;
import com.taeyeong.bowling.exception.OverMaxMemberException;
import com.taeyeong.bowling.group.entity.BowlingGroup;
import com.taeyeong.bowling.group.repository.GroupRepository;
import com.taeyeong.bowling.join.entity.GroupMember;
import com.taeyeong.bowling.join.entity.GroupMemberId;
import com.taeyeong.bowling.join.repository.GroupMemberRepository;
import com.taeyeong.bowling.member.entity.Member;
import com.taeyeong.bowling.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JoinServiceImpl implements JoinService {

	private final GroupMemberRepository groupMemberRepository;
	private final GroupRepository groupRepository;
	private final MemberRepository memberRepository;
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void joinGroup(String email, Long groupId, boolean isAdmin) {
		Member member = memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);
		BowlingGroup bowlingGroup = groupRepository.findById(groupId).orElseThrow(GroupNotFoundException::new);
		GroupMemberId id = new GroupMemberId(member.getMid(), bowlingGroup.getGid());
		if(groupMemberRepository.existsById(id)) 
			throw new DuplicationGroupJoinException();
		if(groupMemberRepository.countByBowlingGroup(bowlingGroup) + 1 > bowlingGroup.getMaxMember())
			throw new OverMaxMemberException();
		groupMemberRepository.save(
				GroupMember.builder()
				.id(id)
				.member(member)
				.bowlingGroup(bowlingGroup)
				.admin(isAdmin)
				.build());
	}

	@Override
	@Transactional(readOnly = true)
	public List<BowlingGroup> findJoinGroup(String email) {
		return groupMemberRepository.findByMember(memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new))
				.stream()
				.map(GroupMember::getBowlingGroup)
				.collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public List<Member> findGroupMember(Long groupId) {
		return groupMemberRepository.findByBowlingGroup(groupRepository.findById(groupId).orElseThrow(GroupNotFoundException::new))
				.stream()
				.map(GroupMember::getMember)
				.collect(Collectors.toList());
	}

	@Override
	public void deleteGroupMember(String email, Long gid) {
		Member member = memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);
		GroupMemberId id = new GroupMemberId(gid, member.getMid());
		groupMemberRepository.deleteById(id);
		if(groupMemberRepository.existsById(id))
			throw new NotWorkingDatabaseException();
	}

}
