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
		int amount = 0;
		amount += Math.max(0, 900 - workDay.getStart()) / 100 * 25;
		amount -= Math.max(0, 900 - workDay.getEnd()) / 100 * 25;
		if(workDay.getStart() > 900) {
			amount += Math.max(0, 1800 - workDay.getStart()) / 100 * 15;
			amount -= Math.max(0, 1800 - workDay.getEnd()) / 100 * 15;
		} else {
			if(workDay.getEnd() > 900) {
				amount += Math.min(900, workDay.getEnd() - 900) / 100 * 15;
			}
		}
		if(workDay.getStart() > 1800) {
			amount += Math.max(0, 2359 - workDay.getStart()) / 100 * 20;
			amount -= Math.max(0, 2359 - workDay.getEnd()) / 100 * 20;
		} else {
			if(workDay.getEnd() > 1800) {
				amount += Math.min(600, workDay.getEnd() - 1800) / 100 * 20;
			}
		}
		return amount;
	}

	private int getWeekendSalary(WorkDay workDay) {
		int amount = 0;
		amount += Math.max(0, 900 - workDay.getStart()) / 100 * 30;
		amount -= Math.max(0, 900 - workDay.getEnd()) / 100 * 30;
		if(workDay.getStart() > 900) {
			amount += Math.max(0, 1800 - workDay.getStart()) / 100 * 20;
			amount -= Math.max(0, 1800 - workDay.getEnd()) / 100 * 20;
		} else {
			if(workDay.getEnd() > 900) {
				amount += Math.min(900, workDay.getEnd() - 900) / 100 * 20;
			}
		}
		if(workDay.getStart() > 1800) {
			amount += Math.max(0, 2359 - workDay.getStart()) / 100 * 25;
			amount -= Math.max(0, 2359 - workDay.getEnd()) / 100 * 25;
		} else {
			if(workDay.getEnd() > 1800) {
				amount += Math.min(600, workDay.getEnd() - 1800) / 100 * 25;
			}
		}
		return amount;
	}

	private boolean isWeekend(String day) {
		return (day.equals("SA") || day.equals("SU"));
	}

	public ArrayList<TimeEntry> getTimeEntries() {
		return timeEntries;
	}
}
