package com.taeyeong.bowling.join.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Embeddable
public class GroupMemberId implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Column(name = "bowling_group_id")
	private Long bowlingGroupId;
	
	@Column(name = "member_id")
	private Long memberId;
}
