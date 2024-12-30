/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package fi.tuni.prog3.sisu;

import com.google.gson.JsonObject;

import java.util.List;

/**
 * Interface for extracting data from the Kori-API.
 */
public interface iAPI {
    /**
     * Retrieves json objects describing degrees from the Kori-API, creates Study modules from the json objects and
     * adds them to a tree map with degree's names as key and study modules as values. Returns a list of degrees or
     * an empty list, if the fetching fails.
     * @return a list of degrees or an empty list if fetching fails.
     */
    public List<StudyModule> parseDegrees();

    /**
     * Parses a degree's whole structure with the given degree's name from the Kori-Api. Returns the parsed degree.
     * If the parsing fails, returns null.
     * @return the parsed degree, or null if the parsing fails.
     */
    public StudyModule parseDegreeContent(String degreeName);
}
