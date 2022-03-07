package com.taeyeong.bowling.join.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.taeyeong.bowling.group.entity.BowlingGroup;
import com.taeyeong.bowling.join.entity.GroupMember;
import com.taeyeong.bowling.join.entity.GroupMemberId;
import com.taeyeong.bowling.member.entity.Member;

@Repository
public interface GroupMemberRepository extends JpaRepository<GroupMember, GroupMemberId>{
	List<GroupMember> findByMember(Member member);
	List<GroupMember> findByBowlingGroup(BowlingGroup bowlingGroup);
	Long countByBowlingGroup(BowlingGroup bowlingGroup);
}
