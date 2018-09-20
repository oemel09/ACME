package de.oemel09.acme;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Acme {

	private ArrayList<TimeEntry> timeEntries;

	public static void main(String[] args) throws IOException {
		Acme acme = new Acme("input.txt");
		for (TimeEntry te : acme.getTimeEntries()) {
			acme.calculateSalary(te);
		}
	}

	public Acme(String fileName) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		timeEntries = new ArrayList<>();
		String row;
		while ((row = br.readLine()) != null) {
			timeEntries.add(new TimeEntry(row));
		}
		br.close();
	}

	private void calculateSalary(TimeEntry timeEntry) {
		int salary = 0;
		for(WorkDay wd : timeEntry.getDays()) {
			salary+=getSalary(wd);
		}
		String output = String.format("The amount to pay %s is: %d USD", timeEntry.getName(), salary);
		System.out.println(output);
	}

	private int getSalary(WorkDay workDay) {
		return (isWeekend(workDay.getDay())) ? getWeekendSalary(workDay) : getWeekSalary(workDay);
	}

	private int getWeekSalary(WorkDay workDay) {
		if(workDay.getStart() <= 900 && workDay.getEnd() <= 900) {
			return 25 * ((workDay.getEnd() - workDay.getStart()) / 100);
		}
		if(workDay.getStart() > 900 && workDay.getStart() <= 1800
				&& workDay.getEnd() > 900 && workDay.getEnd() <= 1800) {
			return 15 * ((workDay.getEnd() - workDay.getStart()) / 100);
		}
		if(workDay.getStart() > 1800 && workDay.getEnd() <= 2359) {
			return 20 * ((workDay.getEnd() - workDay.getStart()) / 100);
		}
		return 0;
	}

	private int getWeekendSalary(WorkDay workDay) {
		if(workDay.getStart() <= 900 && workDay.getEnd() <= 900) {
			return 30 * ((workDay.getEnd() - workDay.getStart()) / 100);
		}
		if(workDay.getStart() > 900 && workDay.getStart() <= 1800
				&& workDay.getEnd() > 900 && workDay.getEnd() <= 1800) {
			return 20 * ((workDay.getEnd() - workDay.getStart()) / 100);
		}
		if(workDay.getStart() > 1800 && workDay.getEnd() <= 2359) {
			return 25 * ((workDay.getEnd() - workDay.getStart()) / 100);
		}
		return 0;
	}

	private boolean isWeekend(String day) {
		return (day.equals("SA") || day.equals("SU"));
	}

	public ArrayList<TimeEntry> getTimeEntries() {
		return timeEntries;
	}
}
