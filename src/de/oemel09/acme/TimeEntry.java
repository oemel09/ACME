package de.oemel09.acme;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimeEntry {

	private String name;
	private ArrayList<WorkDay> days = new ArrayList<>();

	public TimeEntry(String entry) throws IllegalArgumentException {
		parseEntry(entry);
	}

	private void parseEntry(String entry) {

		// create pattern to match the whole string
		String entryRegex = "(\\w+)=(MO|TU|WE|TH|FR|SA|SU)([0-1][0-9]|2[0-3]):([0-5][0-9])-([0-1][0-9]|2[0-3]):([0-5][0-9])(?:,(MO|TU|WE|TH|FR|SA|SU)([0-1][0-9]|2[0-3]):([0-5][0-9])-([0-1][0-9]|2[0-3]):([0-5][0-9]))*";
		if (entry.matches(entryRegex)) {
			this.name = entry.split("=")[0];

			// create pattern to get
			String workDayRegex = "(MO|TU|WE|TH|FR|SA|SU)([0-1][0-9]|2[0-3]):([0-5][0-9])-([0-1][0-9]|2[0-3]):([0-5][0-9])";
			Pattern workDayPattern = Pattern.compile(workDayRegex);
			Matcher matcher = workDayPattern.matcher(entry);
			while (matcher.find()) {
				this.days.add(new WorkDay(matcher.group(1), 
						Integer.parseInt(matcher.group(2) + matcher.group(3)),
						Integer.parseInt(matcher.group(4) + matcher.group(5))));
			}
		} else {
			throw new IllegalArgumentException();
		}
	}

	public String getName() {
		return name;
	}

	public ArrayList<WorkDay> getDays() {
		return days;
	}
}
