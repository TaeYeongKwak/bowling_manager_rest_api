package com.taeyeong.bowling.record.dto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.Setter;

@Setter
public class DateBetweenRequest {
	
	private String start;
	private String end;
	
	private SimpleDateFormat simpleDateFormat;
	
	public DateBetweenRequest() {
		this.simpleDateFormat = new SimpleDateFormat("yy-mm-dd");
	}
	
	public Date getStart() {
		try {
			return this.simpleDateFormat.parse(this.start);
		} catch (ParseException e) {
			e.printStackTrace();
			return new Date();
		}
	}
	
	public Date getEnd() {
		try {
			return this.simpleDateFormat.parse(this.end);
		} catch (ParseException e) {
			e.printStackTrace();
			return new Date();
		}
	}
}
