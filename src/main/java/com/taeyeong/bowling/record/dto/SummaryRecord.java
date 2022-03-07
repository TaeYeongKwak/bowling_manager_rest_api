package com.taeyeong.bowling.record.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SummaryRecord {
	
	private float average;
	private int max;
	private int playCount;
	
}
