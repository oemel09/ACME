package de.oemel09.acme.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import de.oemel09.acme.Acme;
import de.oemel09.acme.WorkDay;

public class AcmeTest {

    private static Acme acme;

    @BeforeAll
    public static void createAcme() throws IOException {
        acme = new Acme("test_input.txt");
    }
    
    @Test()
    public void testTimeEntriesCreation() {
        assertNotNull(acme.getTimeEntries());
        assertEquals(1, acme.getTimeEntries().size());
    }

    @Test
    public void testGetDailySalary_monday_range_1() {
        WorkDay workday = new WorkDay("MO", 100, 900);
        assertEquals(200, acme.getDailySalary(workday));
    }

    @Test
    public void testGetDailySalary_tuesday_range_2() {
        WorkDay workday = new WorkDay("TU", 900, 1600);
        assertEquals(105, acme.getDailySalary(workday));
    }

    @Test
    public void testGetDailySalary_wednesday_range_3() {
        WorkDay workday = new WorkDay("WE", 1900, 2400);
        assertEquals(100, acme.getDailySalary(workday));
    }

    @Test
    public void testGetDailySalary_saturday_range_1() {
        WorkDay workday = new WorkDay("SA", 200, 400);
        assertEquals(60, acme.getDailySalary(workday));
    }

    @Test
    public void testGetDailySalary_saturday_range_2() {
        WorkDay workday = new WorkDay("SA", 1000, 1100);
        assertEquals(20, acme.getDailySalary(workday));
    }

    @Test
    public void testGetDailySalary_sunday_range_3() {
        WorkDay workday = new WorkDay("SU", 1800, 2000);
        assertEquals(50, acme.getDailySalary(workday));
    }

    @Test
    public void testGetDailySalary_monday_range_1_2() {
        WorkDay workday = new WorkDay("MO", 600, 1000);
        assertEquals(90, acme.getDailySalary(workday));
    }

    @Test
    public void testGetDailySalary_tuesday_range_2_3() {
        WorkDay workday = new WorkDay("TU", 1400, 2000);
        assertEquals(100, acme.getDailySalary(workday));
    }

    @Test
    public void testGetDailySalary_wednesday_range_1_3() {
        WorkDay workday = new WorkDay("WE", 800, 2000);
        assertEquals(200, acme.getDailySalary(workday));
    }

    @Test
    public void testGetDailySalary_saturday_range_1_2() {
        WorkDay workday = new WorkDay("SA", 300, 1100);
        assertEquals(220, acme.getDailySalary(workday));
    }

    @Test
    public void testGetDailySalary_saturday_range_2_3() {
        WorkDay workday = new WorkDay("SA", 1700, 1900);
        assertEquals(45, acme.getDailySalary(workday));
    }

    @Test
    public void testGetDailySalary_sunday_range_1_3() {
        WorkDay workday = new WorkDay("SU", 700, 2100);
        assertEquals(315, acme.getDailySalary(workday));
    }
}
