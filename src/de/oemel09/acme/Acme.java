package de.oemel09.acme;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Acme {

    private static final String[] dayAbbreviation = { "MO", "TU", "WE", "TH", "FR", "SA", "SU" };
    
    private static final int[] weekdayRange = { 0, 900, 1800, 2400 };
    private static final int[] weekendRange = { 0, 900, 1800, 2400 };
    private static final int[][] rangeMatrix = {
            weekdayRange, 
            weekdayRange,
            weekdayRange,
            weekdayRange,
            weekdayRange,
            weekendRange,
            weekendRange,
    };
    
    private static final int[] weekdayRate = { 25, 15, 20 };
    private static final int[] weekendRate = { 30, 20, 25 };
    private static final int[][] rateMatrix = { 
            weekdayRate, 
            weekdayRate, 
            weekdayRate, 
            weekdayRate, 
            weekdayRate, 
            weekendRate,
            weekendRate,        
    };

    private ArrayList<TimeEntry> timeEntries;
    private HashMap<String, int[]> ranges;
    private HashMap<String, int[]> rates;

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
        
        createRanges();
        createRates();
    }

    private void createRanges() {
        ranges = new HashMap<>();
        for(int i = 0; i < 7; i++) {
            ranges.put(dayAbbreviation[i], rangeMatrix[i]);
        }
    }
    
    private void createRates() {
        rates = new HashMap<>();
        for(int i = 0; i < 7; i++) {
            rates.put(dayAbbreviation[i], rateMatrix[i]);
        }
    }
    
    private void calculateSalary(TimeEntry timeEntry) {
        int salary = 0;
        for (WorkDay wd : timeEntry.getDays()) {
            salary += getSalary(wd);
        }
        String output = String.format("The amount to pay %s is: %d USD", timeEntry.getName(), salary);
        System.out.println(output);
    }

    private int getSalary(WorkDay workDay) {
        int amount = 0;
        String day = workDay.getDay();
        int start = workDay.getStart();
        int end = workDay.getEnd();
        
        // first range
        amount += Math.max(0, ranges.get(day)[1] - start) / 100 * rates.get(day)[0];
        amount -= Math.max(0, ranges.get(day)[1] - end) / 100 * rates.get(day)[0];

        // first we assume the employee worked the whole second range
        int interval = ranges.get(day)[2] - ranges.get(day)[1];
        amount += (interval) / 100 * rates.get(day)[1];
        
        // then we subtract the hours we assumed too much
        if(end < ranges.get(day)[2]) {
            // when employee worked only in first or in the first and second range 
            amount -= Math.max(0, start - ranges.get(day)[1]) / 100 * rates.get(day)[1];
            amount -= Math.min(interval, ranges.get(day)[2] - end) / 100 * rates.get(day)[1];    
        } else if(start > ranges.get(day)[1]) {
            // when employee worked in the second and third or only in the third range
            amount -= Math.min(interval, start - ranges.get(day)[1]) / 100 * rates.get(day)[1];
            amount -= Math.max(0, ranges.get(day)[2] - end) / 100 * rates.get(day)[1];    
        }

        // third range
        amount += Math.max(0, end - ranges.get(day)[2]) / 100 * rates.get(day)[2];
        amount -= Math.max(0, start - ranges.get(day)[2]) / 100 * rates.get(day)[2];

        return amount;
    }

    public ArrayList<TimeEntry> getTimeEntries() {
        return timeEntries;
    }
}
