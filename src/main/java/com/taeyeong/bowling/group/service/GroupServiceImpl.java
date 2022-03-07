package com.taeyeong.bowling.group.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.taeyeong.bowling.exception.GroupNotFoundException;
import com.taeyeong.bowling.exception.MemberNotFoundException;
import com.taeyeong.bowling.exception.NotWorkingDatabaseException;
import com.taeyeong.bowling.group.dto.CreateGroupRequest;
import com.taeyeong.bowling.group.entity.BowlingGroup;
import com.taeyeong.bowling.group.repository.GroupRepository;
import com.taeyeong.bowling.join.entity.GroupMember;
import com.taeyeong.bowling.join.entity.GroupMemberId;
import com.taeyeong.bowling.join.repository.GroupMemberRepository;
import com.taeyeong.bowling.member.entity.Member;
import com.taeyeong.bowling.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class GroupServiceImpl implements GroupService {
	
	private final GroupRepository groupRepository;
	private final MemberRepository memberRepository;
	private final GroupMemberRepository groupMemberRepository;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void createGroup(String email, CreateGroupRequest createGroupRequest) {
		BowlingGroup group = groupRepository.saveAndFlush(createGroupRequest.toEntity());
		if(group == null) 
			throw new NotWorkingDatabaseException();
		Member member = memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);
		groupMemberRepository.save(
				GroupMember.builder()
				.id(new GroupMemberId(member.getMid(), group.getGid()))
				.member(member)
				.bowlingGroup(group)
				.admin(true)
				.build());

	}

	@Override
	@Transactional(readOnly = true)
	public BowlingGroup findGroupOne(Long gid) {
		return groupRepository.findById(gid).orElseThrow(GroupNotFoundException::new);
	}

	@Override
	@Transactional(readOnly = true)
	public Slice<BowlingGroup> findGroupSlice(Integer page) {
		return groupRepository.findAll(PageRequest.of(page, 10));
	}

	@Override
	@Transactional(readOnly = true)
	public Slice<BowlingGroup> findGroupSlice(Integer page, String title) {
		return groupRepository.findByTitleContaining(title, PageRequest.of(page, 10));
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void modifyGroup(Long gid, CreateGroupRequest createGroupRequest) {
		BowlingGroup group = groupRepository.save(createGroupRequest.toEntity(gid));
		if(group == null) throw new NotWorkingDatabaseException();
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteGroup(Long gid) {
		groupRepository.deleteById(gid);
		if(groupRepository.existsById(gid)) throw new NotWorkingDatabaseException();
	}

}
