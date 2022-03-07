package com.taeyeong.bowling.join.service;

import java.util.List;

import com.taeyeong.bowling.group.entity.BowlingGroup;
import com.taeyeong.bowling.member.entity.Member;

public interface JoinService {
	
	public void joinGroup(String email, Long groupId, boolean isAdmin);
	public List<BowlingGroup> findJoinGroup(String email);
	public List<Member> findGroupMember(Long groupId);
	public void deleteGroupMember(String email, Long gid);
	
}
