package fi.tuni.prog3.sisu;

import java.util.ArrayList;
import java.util.List;

/**
 * The Course class represents a course within a degree program. It extends the `DegreeModule` class.
 * A course contains a name, id, groupId, minimum and maximum credits, assessments that determine the final
 * grade of the course and a description.
 */
public class Course extends DegreeModule{
    private List<List<Assessment>> completionMethods;
    /**
     * Constructs a new `Course` object with the specified attributes.
     *
     * @param name       The name of the course.
     * @param id         The id of the course.
     * @param groupId    The group id of the course.
     * @param minCredits The minimum credits required for the course.
     * @param maxCredits The maximum credits allowed for the course.
     */
    public Course(String name, String id, String groupId, Double minCredits, Double maxCredits) {
        super(name, id, groupId, minCredits, maxCredits);
        this.completionMethods = new ArrayList<>();
    }

    /**
     * Adds a completion method to this course's list of completion methods. A completion method consists of
     * multiple assessments.
     *
     * @param completionMethod The completion method to add.
     */
    public void addCompletionMethod(List<Assessment> completionMethod) {
        this.completionMethods.add(completionMethod);
    }

    /**
     * Returns the completion methods of this course.
     *
     * @return The completion methods of this course.
     */
    public List<List<Assessment>> getCompletionMethods() {
        return completionMethods;
    }

    /**
     * Returns a string representation of this course. The representation includes course's name and maximum credits.
     *
     * @return A string representation of this course.
     */
    @Override
    public String toString() {
        return String.format("%s, credits: %.0f", this.getName(), this.getMaxCredits());
    }
}
