package fi.tuni.prog3.sisu;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class for StudyModule Class. This class tests toString method, addToList and getModuleAndCourseList methods.
 */
public class StudyModuleTest {

    /**
     * Test method for addToList and getModuleAndCourseList.
     */
    @Test
    public void testAddToListAndGetModuleAndCourseList() {
        StudyModule actualStudyModule = new StudyModule("Tietotekniikka", "112", "112-g",
                Double.valueOf(180), null);
        DegreeModule addedModule = new StudyModule("Tietotekniikan yleiset opinnot", "113", "113-g",
                Double.valueOf(40), null);
        DegreeModule addedCourse = new Course("Ohjelmointi 3", "114", "114-g", Double.valueOf(5),
                Double.valueOf(5));
        actualStudyModule.addToList(addedModule);
        actualStudyModule.addToList(addedCourse);
        assertEquals(addedModule,actualStudyModule.getModuleAndCourseList().get(0));
        assertEquals(addedCourse, actualStudyModule.getModuleAndCourseList().get(1));
    }

    /**
     * Test method for toString method.
     */
    @Test
    public void testToString() {
        StudyModule actualStudyModule = new StudyModule("Tietotekniikka", "112", "112-g",
                null, Double.valueOf(180));
        assertEquals("Tietotekniikka", actualStudyModule.toString());
    }
}
