package fi.tuni.prog3.sisu;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class for the abstract DegreeModule Class's getters and setter. All tests are done for both study modules and
 * courses.
 */
public class DegreeModuleTest {

    /**
     * Test method for setDescription and getDescription.
     */
    @Test
    public void testSetDescriptionAndGetDescription() {
        DegreeModule newStudyModule = new StudyModule("Tietotekniikka", "asd-123", "asd-123G",
                Double.valueOf(180), null);
        DegreeModule newCourse = new Course("Ohjelmointi 3", "asd-12345", "asd-12345G",
                Double.valueOf(4), Double.valueOf(5));

        newStudyModule.setDescription("This is description for newStudyModule");
        newCourse.setDescription("This is description for newCourse");

        assertEquals("This is description for newStudyModule", newStudyModule.getDescription());
        assertEquals("This is description for newCourse", newCourse.getDescription());
    }

    /**
     * Test method for getName.
     */
    @Test
    public void testGetName() {
        DegreeModule newStudyModule = new StudyModule("Tietotekniikka", "asd-123", "asd-123G",
                Double.valueOf(180), null);
        DegreeModule newCourse = new Course("Ohjelmointi 3", "asd-12345", "asd-12345G",
                Double.valueOf(4), Double.valueOf(5));

        assertEquals("Tietotekniikka", newStudyModule.getName());
        assertEquals("Ohjelmointi 3", newCourse.getName());
    }

    /**
     * Test method for getId.
     */
    @Test
    public void testGetId() {
        DegreeModule newStudyModule = new StudyModule("Tietotekniikka", "asd-123", "asd-123G",
                Double.valueOf(180), null);
        DegreeModule newCourse = new Course("Ohjelmointi 3", "asd-12345", "asd-12345G",
                Double.valueOf(4), Double.valueOf(5));

        assertEquals("asd-123", newStudyModule.getId());
        assertEquals("asd-12345", newCourse.getId());
    }

    /**
     * Test method for getGroupId.
     */
    @Test
    public void testGetGroupId() {
        DegreeModule newStudyModule = new StudyModule("Tietotekniikka", "asd-123", "asd-123G",
                Double.valueOf(180), null);
        DegreeModule newCourse = new Course("Ohjelmointi 3", "asd-12345", "asd-12345G",
                Double.valueOf(4), Double.valueOf(5));

        assertEquals("asd-123G", newStudyModule.getGroupId());
        assertEquals("asd-12345G", newCourse.getGroupId());
    }

    /**
     * Test method for getMinCredits.
     */
    @Test
    public void testGetMinCredits() {
        DegreeModule newStudyModule = new StudyModule("Tietotekniikka", "asd-123", "asd-123G",
                Double.valueOf(180), null);
        DegreeModule newCourse = new Course("Ohjelmointi 3", "asd-12345", "asd-12345G",
                null, Double.valueOf(5));

        assertEquals(Double.valueOf(180), newStudyModule.getMinCredits());
        assertEquals(null, newCourse.getMinCredits());
    }

    /**
     * Test method for getMaxCredits.
     */
    @Test
    public void testGetMaxCredits() {
        DegreeModule newStudyModule = new StudyModule("Tietotekniikka", "asd-123", "asd-123G",
                null, null);
        DegreeModule newCourse = new Course("Ohjelmointi 3", "asd-12345", "asd-12345G",
                Double.valueOf(4), Double.valueOf(5));

        assertEquals(null, newStudyModule.getMaxCredits());
        assertEquals(Double.valueOf(5), newCourse.getMaxCredits());
    }
}
