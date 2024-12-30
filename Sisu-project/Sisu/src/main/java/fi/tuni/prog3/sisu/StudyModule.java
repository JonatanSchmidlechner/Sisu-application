package fi.tuni.prog3.sisu;

import java.util.ArrayList;
import java.util.List;

/**
 * A class representing a study module that can contain other modules and courses.
 * Study module has a name, id, groupId, minimum and maximum credits, minimum- and maximum choices,
 * a list of StudyModules and courses, and a description. StudyModule extends DegreeModule.
 */
public class StudyModule extends DegreeModule{
    private List<DegreeModule> moduleAndCourseList;

    /**
     * Constructs a new StudyModule object with the given name, id, group id, minimum credits,
     * maximum credits, minimum choices, maximum choices, and description.
     *
     * @param name the name of the study module
     * @param id the id of the study module
     * @param groupId the group id of the study module
     * @param minCredits the minimum credits required for the study module
     * @param maxCredits the maximum credits allowed for the study module
     */
    public StudyModule(String name, String id, String groupId, Double minCredits, Double maxCredits) {
        super(name, id, groupId, minCredits, maxCredits);
        this.moduleAndCourseList = new ArrayList<>();
    }

    /**
     * Constructs a new StudyModule object with the given name, id, group id, minimum and maximum choices
     * and description. This differs from the first constructor as the constructor does not get min and max credits and
     * automatically puts the value of min and max credits to null.
     *
     * @param name the name of the study module
     * @param id the id of the study module
     * @param groupId the group id of the study module
     */
    public StudyModule(String name, String id, String groupId) {
        super(name, id, groupId, null, null);
        this.moduleAndCourseList = new ArrayList<>();
    }

    /**
     * Returns a string representation of this StudyModule object.
     *
     * @return a string representation of this StudyModule object
     */
    @Override
    public String toString() {
        return String.format("%s", this.getName());
    }
    
    /**
     * Adds the given StudyModule or Course to the list of modules and courses in this study module.
     *
     * @param unit the StudyModule or Course to add to this study module
     */
    public void addToList(DegreeModule unit) {
        moduleAndCourseList.add(unit);
    }

    /**
     * Returns the list of study modules and courses in this study module.
     *
     * @return the list of study modules and courses in this study module
     */
    public List<DegreeModule> getModuleAndCourseList() {
        return moduleAndCourseList;
    }
}
