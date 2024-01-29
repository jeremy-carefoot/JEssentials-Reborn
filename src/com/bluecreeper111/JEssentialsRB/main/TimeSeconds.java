package com.bluecreeper111.JEssentialsRB.main;

import java.util.HashMap;

public enum TimeSeconds {
	
	// This enum is used to store the number of seconds and string representations of each time unit
	
	SECONDS(1L, "s"), 
	MINUTES(60L, "min"),
	HOURS(3600L, "h"), 
	DAYS(86400L, "d"), 
	WEEKS(604800L, "w"), 
	MONTHS(2592000L, "mo"), 
	YEARS(29030400L, "y");
	
	private Long seconds;
	private String unit;
	private static final HashMap<String, Long> unitMap;
	
	private TimeSeconds(Long seconds, String unit) {
		this.seconds = seconds;
		this.unit = unit;
	}
	
	static {
		unitMap = new HashMap<>();
		for (TimeSeconds x : TimeSeconds.values()) {
			unitMap.put(x.getUnit(), x.getSeconds());
		}
	}
	
	public Long getSeconds() {
		return this.seconds;
	}
	
	public String getUnit() {
		return this.unit;
	}
	
	public static HashMap<String, Long> getUnitMap() {
		return unitMap;
	}

}
