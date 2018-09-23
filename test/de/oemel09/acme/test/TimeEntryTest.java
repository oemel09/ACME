package de.oemel09.acme.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import de.oemel09.acme.TimeEntry;

public class TimeEntryTest {

    @Test
    public void testIsNameParsed() {
        String entry = "TEST_NAME=MO08:00-09:00";
        TimeEntry timeEntry = new TimeEntry(entry);
        assertEquals("TEST_NAME", timeEntry.getName());
    }

    @Test
    public void testIsDaysListFilled() {
        String entry = "TEST_NAME=MO08:00-09:00,TU09:00-10:00";
        TimeEntry timeEntry = new TimeEntry(entry);
        assertNotNull(timeEntry.getDays());
        assertEquals(2, timeEntry.getDays().size());
    }
    
    @Test
    public void testIsDayParsed() {
        String entry = "TEST_NAME=WE08:00-09:00";
        TimeEntry timeEntry = new TimeEntry(entry);
        assertEquals("WE", timeEntry.getDays().get(0).getDay());
    }

    @Test
    public void testIsStartParsed() {
        String entry = "TEST_NAME=MO08:00-09:00";
        TimeEntry timeEntry = new TimeEntry(entry);
        assertEquals(800, timeEntry.getDays().get(0).getStart());
    }

    @Test
    public void testIsEndParsed() {
        String entry = "TEST_NAME=MO08:00-09:00";
        TimeEntry timeEntry = new TimeEntry(entry);
        assertEquals(900, timeEntry.getDays().get(0).getEnd());
    }

    @Test
    public void testWrongFormat() {
        String entry_1 = "TEST=NAME=MO08:00-09:00";
        assertThrows(IllegalArgumentException.class, () -> {
            new TimeEntry(entry_1);
        });
        String entry_2 = "TEST=UT08:00-09:00";
        assertThrows(IllegalArgumentException.class, () -> {
            new TimeEntry(entry_2);
        });
        String entry_3 = "TEST=TU28:60-09:00";
        assertThrows(IllegalArgumentException.class, () -> {
            new TimeEntry(entry_3);
        });
        String entry_4 = "TEST=SA08:00-09:00SA09:00-10:00";
        assertThrows(IllegalArgumentException.class, () -> {
            new TimeEntry(entry_4);
        });
    }

    @Test()
    public void testEndBeforeStart() {
        String entry = "TEST=TU14:00-09:00";
        assertThrows(IllegalArgumentException.class, () -> {
            new TimeEntry(entry);
        });
    }
}
