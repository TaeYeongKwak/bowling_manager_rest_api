package com.taeyeong.bowling.group.service;

import org.springframework.data.domain.Slice;

import com.taeyeong.bowling.group.dto.CreateGroupRequest;
import com.taeyeong.bowling.group.entity.BowlingGroup;

public interface GroupService {
	
	public void createGroup(String email, CreateGroupRequest createGroupRequest);
	public BowlingGroup findGroupOne(Long gid);
	public Slice<BowlingGroup> findGroupSlice(Integer page);
	public Slice<BowlingGroup> findGroupSlice(Integer page, String title);
	public void modifyGroup(Long gid, CreateGroupRequest createGroupRequest);
	public void deleteGroup(Long gid);
	
}
