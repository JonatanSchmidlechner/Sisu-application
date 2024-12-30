/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.prog3.sisu;

/**
 *An abstract class for storing information on Modules and Courses
 */
public abstract class DegreeModule {
    private String name;
    private String id;
    private String groupId;
    private Double minCredits;
    private Double maxCredits;
    private String description;
    /**
     * A constructor for initializing the member variables.
     * @param name name of the DegreeModule.
     * @param id id of the DegreeModule.
     * @param groupId group id of the DegreeModule.
     * @param minCredits minimum credits of the DegreeModule.
     * @param maxCredits maximum credits of the DegreeModule.
     */
    public DegreeModule(String name, String id, String groupId, 
            Double minCredits, Double maxCredits) {
        this.name = name;
        this.id = id;
        this.groupId = groupId;
        this.minCredits = minCredits;;
        this.maxCredits = maxCredits;
    }

    /**
     * Sets the degree module's description to the given description.
     *
     * @param description The given description.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the name of the DegreeModule.
     *
     * @return name of the DegreeModule.
     */
    public String getName() {
        return this.name;
    }
    
    /**
     * Returns the id of the DegreeModule.
     *
     * @return id of the DegreeModule.
     */
    public String getId() {
        return this.id;
    }
    
    /**
     * Returns the group id of the DegreeModule.
     *
     * @return group id of the DegreeModule.
     */
    public String getGroupId() {
        return this.groupId;
    }
    
    /**
     * Returns the minimum credits of the DegreeModule.
     *
     * @return minimum credits of the DegreeModule.
     */
    public Double getMinCredits() {
        return this.minCredits;
    }

    /**
     * Returns the maximum credits of the DegreeModule.
     *
     * @return maximum credits of the DegreeModule.
     */
    public Double getMaxCredits() {
        return maxCredits;
    }

    /**
     * Returns the description of the DegreeModule.
     *
     * @return the description of the DegreeModule.
     */
    public String getDescription() {
        return description;
    }
}
