package de.oemel09.acme;

public class WorkDay {
	
	private String day;
	private int start, end;

	public WorkDay(String day, int start, int end) {
		this.day = day;
		this.start = start;
		this.end = end;
	}

	public String getDay() {
		return day;
	}

	public int getStart() {
		return start;
	}

	public int getEnd() {
		return end;
	}
	
	public int getAmountOfHours() {
		return (end - start) / 100;
	}
}
