package fi.tuni.prog3.sisu;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for Course Class. Unit tests include tests for toString, addCompletionMethods and getCompletionMethods.
 */
public class CourseTest {

    /**
     * Unit tests for addCompletionMethods and getCompletionMethods. AddCompletionMethod is tested with a
     * completion method consisting of 1 assessment and 2 assessments.
     */
    @Test
    public void testAddCompletionMethodAndGetCompletionMethods() {
        Course actualCourse = new Course("Ohjelmointi 3", "1", "g1", Double.valueOf(5),
                Double.valueOf(5));
        List<Assessment> expectedCompletionMethod1 = new ArrayList<>();
        List<Assessment> expectedCompletionMethod2 = new ArrayList<>();
        Assessment assessment1 = new Assessment("Exam", "1-5", Double.valueOf(0), Double.valueOf(0));
        Assessment assessment2 = new Assessment("Osallistuminen opetukseen", "hyl-hyv",
                Double.valueOf(5), Double.valueOf(5));
        Assessment assessment3 = new Assessment("2. Exam", "1-5", Double.valueOf(3),
                Double.valueOf(3));
        expectedCompletionMethod1.add(assessment1);
        expectedCompletionMethod1.add(assessment2);
        expectedCompletionMethod2.add(assessment3);
        actualCourse.addCompletionMethod(expectedCompletionMethod1);
        actualCourse.addCompletionMethod(expectedCompletionMethod2);

        List<Assessment> actualCompletionMethod1 = actualCourse.getCompletionMethods().get(0);
        List<Assessment> actualCompletionMethod2 = actualCourse.getCompletionMethods().get(1);
        assertEquals(expectedCompletionMethod1, actualCompletionMethod1);
        assertEquals(expectedCompletionMethod2, actualCompletionMethod2);
    }

    /**
     * Unit test for toString.
     */
    @Test
    public void testToString() {
        Course newCourse = new Course("Ohjelmointi", "11", "11-G", Double.valueOf(5),
                Double.valueOf(5));
        assertEquals("Ohjelmointi, credits: 5", newCourse.toString());
    }
}
