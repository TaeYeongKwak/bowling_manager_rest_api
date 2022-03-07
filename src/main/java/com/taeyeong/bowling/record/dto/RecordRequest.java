package com.taeyeong.bowling.record.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecordRequest {
	
	private String recordDateFormat;
	private String pattern;
	private int score;
	
}
