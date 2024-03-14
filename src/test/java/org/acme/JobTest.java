package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.acme.villagers.Job;

@QuarkusTest
class JobTest {

    @Test
    void testJobs(){
        assertEquals(Job.UNEMPLOYED, Job.fromString("Unemployed"));
        assertEquals(Job.FARMER, Job.fromString("Farmer"));
        assertEquals(Job.WARRIOR, Job.fromString("Soldier"));
        assertEquals(Job.HEALER, Job.fromString("Doctor"));

        assertEquals("Unemployed", Job.UNEMPLOYED.getName());
        assertEquals("Farmer", Job.FARMER.getName());
        assertEquals("Soldier", Job.WARRIOR.getName());
        assertEquals("Doctor", Job.HEALER.getName());
    }
}
