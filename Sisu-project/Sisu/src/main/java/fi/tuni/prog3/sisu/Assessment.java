package fi.tuni.prog3.sisu;

/**
 * Represents a course's assessment, which have a name, grading, min- and max credits.
 */
public class Assessment {
    private String name;
    private String grading;
    private Double maxCredits;
    private Double minCredits;

    /**
     * Creates a new assessment with the given name, grading system, min- and max credits
     *
     * @param name     the name of the assessment.
     * @param grading  the grading system used for the assessment.
     * @param minCredits assessment's minimum credits.
     * @param maxCredits assessment's maximum credits.
     */
    public Assessment(String name, String grading, Double maxCredits, Double minCredits) {
        this.name = name;
        this.grading = grading;
        this.maxCredits = maxCredits;
        this.minCredits = minCredits;
    }

    /**
     * Returns the name of the assessment.
     *
     * @return the name of the assessment.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the max credits of the assessment.
     *
     * @return the max credits of the assessment.
     */
    public Double getMaxCredits() {
        return maxCredits;
    }

    /**
     * Returns the min credits of the assessment.
     *
     * @return the min credits of the assessment.
     */
    public Double getMinCredits() {
        return minCredits;
    }

    /**
     * Returns the grading system used for the assessment.
     *
     * @return the grading system used for the assessment.
     */
    public String getGrading() {
        return grading;
    }

    /**
     * Returns a string representation of the assessment, including its name, grading system and maximum credits.
     *
     * @return a string representation of the assessment, including its name, grading system and maximum credits.
     */
    @Override
    public String toString() {
        return String.format("%s, %s, credits: %.0f", this.getName(),
                this.getGrading(), this.getMaxCredits());
    }
}
