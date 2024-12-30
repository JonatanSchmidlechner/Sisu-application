package fi.tuni.prog3.sisu;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for Assessment class. Tests include tests for getters and toString.
 */
public class AssessmentTest {

    /**
     * Unit test for getName
     */
    @Test
    public void testgetName() {
        Assessment newAssessment = new Assessment("Ohjelmointi 3", "1-5",
                Double.valueOf(5), Double.valueOf(5));

        assertEquals("Ohjelmointi 3", newAssessment.getName());
    }

    /**
     * Unit test for getMaxCredits.
     */
    @Test
    public void testgetMaxCredits() {
        Assessment newAssessment = new Assessment("Ohjelmointi 3", "1-5",
                Double.valueOf(5), Double.valueOf(4));

        assertEquals(Double.valueOf(5), newAssessment.getMaxCredits());
    }

    /**
     * Unit test for getMinCredits.
     */
    @Test
    public void testGetMinCredits() {
        Assessment newAssessment = new Assessment("Ohjelmointi 3", "1-5",
                Double.valueOf(5), Double.valueOf(4));

        assertEquals(Double.valueOf(4), newAssessment.getMinCredits());
    }

    /**
     * Unit test for getGrading.
     */
    @Test
    public void testGetGrading() {
        Assessment newAssessment = new Assessment("Ohjelmointi 3", "1-5",
                Double.valueOf(5), Double.valueOf(4));

        assertEquals("1-5", newAssessment.getGrading());
    }

    /**
     * Unit test for toString.
     */
    @Test
    public void testToString() {
        Assessment assessment = new Assessment("Exam", "1-5", Double.valueOf(5), Double.valueOf(5));
        assertEquals(String.format("Exam, 1-5, credits: 5"), assessment.toString());
    }

}
