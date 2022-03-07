package com.taeyeong.bowling.record.service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.taeyeong.bowling.record.dto.DateBetweenRequest;
import com.taeyeong.bowling.record.dto.RecordRequest;
import com.taeyeong.bowling.record.dto.SummaryRecord;
import com.taeyeong.bowling.record.entity.Record;

public interface RecordService {
		
	public void saveRecord(String email, RecordRequest recordRequest) throws ParseException;
	public List<Record> getMemberRecord(String email);
	public List<Record> getMemberRecord(String email, DateBetweenRequest dateBetween);
	public Map<Long, List<Record>> getGroupMemberRecord(Long gid);
	public Map<Long, List<Record>> getGroupMemberRecord(Long gid, DateBetweenRequest dateBetween);
	public SummaryRecord getSummaryMe(String email);
	public Map<Long, SummaryRecord> getSummaryGroup(Long gid);
	
}
