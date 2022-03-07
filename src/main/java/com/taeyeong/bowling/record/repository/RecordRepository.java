package com.taeyeong.bowling.record.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.taeyeong.bowling.member.entity.Member;
import com.taeyeong.bowling.record.dto.SummaryRecord;
import com.taeyeong.bowling.record.entity.Record;

@Repository
public interface RecordRepository extends JpaRepository<Record, Long>{
	List<Record> findAllByMember(Member member);
	List<Record> findAllByMemberAndRecordDateBetween(Member member, Date start, Date end);
	
	@Query(value = "SELECT "
			+ "new com.taeyeong.bowling.record.dto.SummaryRecord("
			+ "AVG(r.score), "
			+ "MAX(r.score), "
			+ "COUNT(r.rid)) "
			+ "FROM Record r "
			+ "WHERE r.member_id")
	SummaryRecord summaryRecordByMember(@Param("member_id") Long memberId);
	
}
