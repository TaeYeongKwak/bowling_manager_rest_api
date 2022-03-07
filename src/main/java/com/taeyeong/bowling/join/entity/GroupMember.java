package com.taeyeong.bowling.join.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import com.taeyeong.bowling.group.entity.BowlingGroup;
import com.taeyeong.bowling.member.entity.Member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "group_member")
public class GroupMember implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
    private GroupMemberId id;
	
	@ManyToOne(targetEntity = BowlingGroup.class, fetch = FetchType.EAGER)
	@MapsId("bowlingGroupId")
	@JoinColumn(name = "bowling_group_id")
	private BowlingGroup bowlingGroup;
	
	@ManyToOne(targetEntity = Member.class, fetch = FetchType.EAGER)
	@MapsId("memberId")
	@JoinColumn(name = "member_id")
	private Member member;
	@Column
	private boolean admin;
	
}
