package com.taeyeong.bowling.group.dto;

import com.taeyeong.bowling.group.entity.BowlingGroup;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateGroupRequest {
	private String title;
	private String description;
	private Integer maxMember;
	
	public BowlingGroup toEntity() {
		return BowlingGroup.builder()
				.title(title)
				.description(description)
				.maxMember(maxMember)
				.build();
	}
	
	public BowlingGroup toEntity(Long gid) {
		return BowlingGroup.builder()
				.gid(gid)
				.title(title)
				.description(description)
				.maxMember(maxMember)
				.build();
	}
}
