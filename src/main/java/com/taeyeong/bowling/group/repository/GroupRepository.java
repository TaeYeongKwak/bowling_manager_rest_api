package com.taeyeong.bowling.group.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.taeyeong.bowling.group.entity.BowlingGroup;

@Repository
public interface GroupRepository extends JpaRepository<BowlingGroup, Long> {
	Slice<BowlingGroup> findByTitleContaining(String title, Pageable pageable);
}
