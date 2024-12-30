package fi.tuni.prog3.sisu;

import com.google.gson.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

/**
 * A class for fetching data from Kori-API and parsing it to one of the following classes: Course, StudyModule and
 * Assessment. Implements the interface iAPI, which contain the two public methods of this class: first of which
 * parses all degree type json objects from Kori-API and returns them in a list. The second parses a specific degree's
 * whole structure from study modules to courses and assessments.
 */
public class JsonParser implements iAPI {
    public static final String MODULE_URL = "https://sis-tuni.funidata.fi/kori/api/" +
            "modules/by-group-id?groupId=";
    public static final String UNIVERSITY_ID_URL = "&universityId=tuni-university-root-id";
    public static final String COURSE_URL = "https://sis-tuni.funidata.fi/kori/api/course-units/by-group-id?groupId=";
    public static final String DEGREES_URL = "https://sis-tuni.funidata.fi/kori/api/module-search?curriculumPeriodId" +
            "=uta-lvv-2021&universityId=tuni-university-root-id&moduleType=DegreeProgramme&limit=1000";
    public static final String ASSESSMENT_URL = "https://sis-tuni.funidata.fi/kori/api/assessment-items/v1/";
    public Map<String, StudyModule> degreeMap;
    public JsonParser() {
        this.degreeMap = new TreeMap<>();
    }




    /**
     * Retrieves json objects describing degrees from the Kori API, creates Study modules from the json objects and
     * adds them to a tree map with degree's names as key and study modules as values. Returns a list of degrees or
     * an empty list, if the fetching fails.
     * @return a list of degrees or an empty list if fetching fails.
     */
    @Override
    public List<StudyModule> parseDegrees() {
        ArrayList<StudyModule> listOfDegrees = new ArrayList<>();
        JsonObject jsonObject = null;
        try {
            jsonObject = getJsonObjectFromApi(DEGREES_URL);
        }
        catch (RuntimeException e) {
            return new ArrayList<>();
        }
        JsonArray jsonDegrees = jsonObject.get("searchResults").getAsJsonArray();
        for(int i = 0 ; i<jsonDegrees.size() ; i++) {
            JsonObject degree = jsonDegrees.get(i).getAsJsonObject();
            String degreeName =removeQuotes(degree.get("name").toString());
            String degreeId = removeQuotes(degree.get("id").toString());
            String degreeGroupId = removeQuotes(degree.get("groupId").toString());
            JsonObject credits = degree.get("credits").getAsJsonObject();
            JsonElement jsonMinCredits = credits.get("min");
            JsonElement jsonMaxCredits = credits.get("max");
            Double minCredits = null;
            Double maxCredits = null;
            if(!jsonMinCredits.isJsonNull()) {
                minCredits = jsonMinCredits.getAsDouble();
            }
            if(!jsonMaxCredits.isJsonNull()) {
                maxCredits = jsonMaxCredits.getAsDouble();
            }

            StudyModule newDegree = new StudyModule(degreeName, degreeId, degreeGroupId,
                    minCredits, maxCredits);
            degreeMap.put(degreeName, newDegree);
            listOfDegrees.add(newDegree);
        }
        return listOfDegrees;
    }

    /**
     * Parses a degree's whole structure with the given degree's name from the Kori Api. Returns the parsed degree.
     * If the parsing fails, returns null.
     * @return the parsed degree, or null if the parsing fails.
     */
    @Override
    public StudyModule parseDegreeContent(String degreeName) {
        StudyModule degree = degreeMap.get(degreeName);
        JsonObject jsonObject = null;

        if(degree == null) {
            System.out.println("Degree with given name does not exist");
            return null;
        }

        if (!degree.getModuleAndCourseList().isEmpty()) {
            return degree;
        }

        try {
            jsonObject = getJsonObjectFromApi(MODULE_URL +
                    degree.getGroupId() + UNIVERSITY_ID_URL);
        }
        catch (RuntimeException e) {
            System.out.println("Failed to parse degree from API. Error message: " + e.getMessage());
            return null;
        }
        String html = fetchDescription(jsonObject, "contentDescription", "learningOutcomes");
        String description = htmlToString(html);
        degree.setDescription(description);
        JsonObject rule = jsonObject.get("rule").getAsJsonObject();
        List<JsonObject> modules = new ArrayList<>();
        List<JsonObject> studyModules = getModuleContents(rule, modules);
        for(int i = 0 ; i < studyModules.size() ; i++) {
            JsonObject studyModule = studyModules.get(i).getAsJsonObject();
            String type = removeQuotes(studyModule.get("type").toString());
            if(type.equals("ModuleRule")) {
                String groupId = removeQuotes(studyModule.get("moduleGroupId").toString());
                parseStudyModule(groupId, degree);
            }
        }
        return degree;
    }

    /**
     * Parses the content of a StudyModule type JsonObject from Kori-API to a StudyModule object.
     *
     * @param parseId groupId of the module, which will be parsed.
     * @param parent parent of the parsed object.
     */
    private void parseStudyModule(String parseId, StudyModule parent) {
        JsonObject jsonObject = null;
        try {
            jsonObject = getJsonObjectFromApi(MODULE_URL + parseId + UNIVERSITY_ID_URL);
        }
        catch (RuntimeException e) {
            System.out.println("Failed to parse StudyModule from API. Error message: " + e.getMessage());
            return;
        }
        JsonObject rule = jsonObject.get("rule").getAsJsonObject();
        ArrayList<JsonObject> modules = new ArrayList<>();
        List<JsonObject> studyModules = getModuleContents(rule, modules);
        StudyModule self = addStudyModuleToParent(jsonObject, parent);

        for (int i = 0 ; i < studyModules.size() ; i++) {
            JsonObject studyModule = studyModules.get(i).getAsJsonObject();
            String type = removeQuotes(studyModule.get("type").toString());
            if (type.equals("ModuleRule")) {
                String moduleGroupId = removeQuotes(studyModule.get("moduleGroupId").toString());
                parseStudyModule(moduleGroupId, self);
            }
            else if (type.equals("CourseUnitRule")) {
                String courseGroupId = removeQuotes(studyModule.get("courseUnitGroupId").toString());
                parseCourseUnit(courseGroupId, self);
            }
        }
    }

    /**
     * Parses a course type JsonObject from Kori-API to a Course object.
     *
     * @param parseId groupId of the course, which is parsed.
     * @param parent parent of the parsed course.
     */
    private void parseCourseUnit(String parseId, StudyModule parent) {
        JsonObject jsonObjectCourse = null;
        try {
            jsonObjectCourse = getJsonObjectFromApi(COURSE_URL + parseId + UNIVERSITY_ID_URL);
        }
        catch (RuntimeException e) {
            System.out.println("Failed to parse course from API. Error message: " + e.getMessage());
            return;
        }
        addCourseToParent(jsonObjectCourse, parent);
    }

    /**
     * Parses an Assessment type JsonObject from Kori-API to an Assessment object.
     *
     * @param parseId groupId of the Assessment, which is parsed.
     * @return the Assessment object, which was parsed.
     */
    private Assessment parseAssessment(String parseId) {
        JsonObject jsonAssessment = null;
        try {
            jsonAssessment = getJsonObjectFromApi(ASSESSMENT_URL + parseId);
        }
        catch (RuntimeException e) {
            System.out.println("Failed to parse assessment from API. Error message: " + e.getMessage());
            return null;
        }
        JsonObject assessmentName = jsonAssessment.get("name").getAsJsonObject();
        String name = "";
        if (assessmentName.get("fi") != null) {
            name = removeQuotes(assessmentName.get("fi").toString());
        }
        else  if(assessmentName.get("en") != null) {
            name = removeQuotes(assessmentName.get("en").toString());
        }
        else if(assessmentName.get("sv") != null) {
            name = removeQuotes(assessmentName.get("sv").toString());
        }
        String grading = jsonAssessment.get("gradeScaleId").getAsString();

        JsonObject credits = jsonAssessment.get("credits").getAsJsonObject();
        Double minCredits = null;
        Double maxCredits = null;
        if (credits != null && !credits.isJsonNull()) {
            minCredits = credits.get("min").getAsDouble();
            maxCredits = credits.get("max").getAsDouble();
        }

        Assessment newAssessment = new Assessment(name, grading, minCredits, maxCredits);
        return newAssessment;
    }

    /**
     * Creates a StudyModule and adds it to its parent's list.
     *
     * @param jsonStudyModule the StudyModule as jsonObject, which will be parsed and added to its parent.
     * @param parent the parent where the StudyModule gets added to.
     */
    private StudyModule addStudyModuleToParent(JsonObject jsonStudyModule, StudyModule parent) {
        String name = "";
        JsonObject studyModuleName = jsonStudyModule.get("name").getAsJsonObject();
        if (studyModuleName.get("fi") != null) {
            name = removeQuotes(studyModuleName.get("fi").toString());
        }
        else {
            name = removeQuotes(studyModuleName.get("en").toString());
        }
        String id = removeQuotes(jsonStudyModule.get("id").toString());
        String groupId = removeQuotes(jsonStudyModule.get("groupId").toString());
        String type = removeQuotes(jsonStudyModule.get("type").toString());

        if (type.equals("StudyModule")) {
            JsonObject credits = jsonStudyModule.get("targetCredits").getAsJsonObject();
            JsonElement jsonMinCredits = credits.get("min");
            JsonElement jsonMaxCredits = credits.get("max");
            Double minCredits = null;
            Double maxCredits = null;
            if (!jsonMinCredits.isJsonNull()) {
                minCredits = jsonMinCredits.getAsDouble();
            }
            if (!jsonMaxCredits.isJsonNull()) {
                maxCredits = jsonMaxCredits.getAsDouble();
            }

            StudyModule newStudyModule = new StudyModule(name, id, groupId, minCredits, maxCredits);
            parent.addToList(newStudyModule);

            String html = fetchDescription(jsonStudyModule, "contentDescription", "outcomes");
            String description = htmlToString(html);
            newStudyModule.setDescription(description);

            return newStudyModule;
        }
        else if (type.equals("GroupingModule")) {
            String html = fetchDescription(jsonStudyModule, "description", "");
            String description = htmlToString(html);

            StudyModule newGroupingModule = new StudyModule(name, id, groupId);
            newGroupingModule.setDescription(description);
            parent.addToList(newGroupingModule);
                return newGroupingModule;
        }
        return null;
    }

    /**
     * Creates and adds a Course to its parent's list.
     *
     * @param jsonObjectCourse the course as json, which is parsed to Course and added to its parent.
     * @param parent the parent where the course gets added to.
     */
    private void addCourseToParent(JsonObject jsonObjectCourse, StudyModule parent) {
        String name = null;
        JsonObject courseName = jsonObjectCourse.get("name").getAsJsonObject();
        if (courseName.get("fi") != null) {
            name = removeQuotes(courseName.get("fi").toString());
        }
        else  if(courseName.get("en") != null) {
            name = removeQuotes(courseName.get("en").toString());
        }
        else if(courseName.get("sv") != null) {
            name = removeQuotes(courseName.get("sv").toString());
        }
        String id = removeQuotes(jsonObjectCourse.get("id").toString());
        String groupId = removeQuotes(jsonObjectCourse.get("groupId").toString());
        JsonObject credits = jsonObjectCourse.get("credits").getAsJsonObject();
        JsonElement jsonMinCredits = credits.get("min");
        JsonElement jsonMaxCredits = credits.get("max");
        Double minCredits = null;
        Double maxCredits = null;
        if(!jsonMinCredits.isJsonNull()) {
            minCredits = jsonMinCredits.getAsDouble();
        }
        if(!jsonMaxCredits.isJsonNull()) {
            maxCredits = jsonMaxCredits.getAsDouble();
        }

        String html = fetchDescription(jsonObjectCourse, "content", "outcomes");
        String description = htmlToString(html);

        Course newCourse = new Course(name, id, groupId, minCredits, maxCredits);
        newCourse.setDescription(description);

        JsonArray completionMethods = jsonObjectCourse.get("completionMethods").getAsJsonArray();
        if (completionMethods.size() != 0) {
            for (JsonElement completionMethod : completionMethods) {
                List<Assessment> assessments = new ArrayList<>();
                JsonArray jsonAssessments =completionMethod.getAsJsonObject().get("assessmentItemIds").getAsJsonArray();
                for (JsonElement assessment : jsonAssessments) {
                    String assessmentId = assessment.getAsString();
                    Assessment newAssessment = parseAssessment(assessmentId);
                    assessments.add(newAssessment);
                }
                newCourse.addCompletionMethod(assessments);
            }
        }
        parent.addToList(newCourse);
    }

    /**
     * Returns a JsonObject that is extracted from the Kori-API.
     *
     * @param urlString URL for retrieving information from the Kori API.
     * @return JsonObject.
     */
    private JsonObject getJsonObjectFromApi(String urlString) {
        Gson gson = new Gson();
        try {
            JsonObject jsonObject = null;
            URL url = new URL(urlString);
            URLConnection urlConnection = url.openConnection();
            BufferedReader bReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            JsonElement jsonElement = gson.fromJson(bReader, JsonElement.class);
            if(jsonElement.isJsonArray()) {
                if(jsonElement.getAsJsonArray().size() == 0) {
                    throw new IndexOutOfBoundsException("Index out of bounds: array's size is 0");
                }
                jsonObject = jsonElement.getAsJsonArray().get(0).getAsJsonObject();
            }
            else {
                jsonObject = jsonElement.getAsJsonObject();
            }
            bReader.close();
            if (jsonObject.has("cause") && jsonObject.get("cause") == null) {
                throw new IllegalArgumentException();
            }
            return jsonObject;
        }
        catch (IOException e) {
            System.out.println("Error message: " + e.getMessage() + "\nProbable cause: Invalid URL-String in" +
                    " API-call. Cannot form connection to Kori-API. The beginning of the URL-String is likely invalid");
            throw new RuntimeException(e);
        }
        catch (IndexOutOfBoundsException e) {
            System.out.println("Error message: " + e.getMessage() + "\nProbable cause: invalid group-id.");
            throw new RuntimeException(e);
        }
        catch (IllegalArgumentException e) {
            System.out.println("Cannot find the object to parse.\nProbable cause: The end of the URL-String" +
                    " is likely invalid. The connection is formed, but no valid object is found");
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns StudyModule's submodules or courses as a JsonArray.
     *
     * @param jsonObject the module to be parsed.
     * @return a JsonArray containing modules or courses.
     */
    private List<JsonObject> getModuleContents(JsonObject jsonObject, List<JsonObject> modules) {
         if (jsonObject.has("moduleGroupId") || jsonObject.has("courseUnitGroupId")) {
            modules.add(jsonObject);
        }
         if (jsonObject.has("rule")) {
             JsonObject rule = jsonObject.get("rule").getAsJsonObject();
             getModuleContents(rule, modules);
         }
        else if (jsonObject.has("rules")) {
        JsonArray innerRulesArray = jsonObject.get("rules").getAsJsonArray();
        for (JsonElement innerRuleElement : innerRulesArray) {
            JsonObject innerRuleObject = innerRuleElement.getAsJsonObject();
            getModuleContents(innerRuleObject, modules);
            }
        }
        return modules;
    }

    /**
     * Returns a description containing information on jsonObject's content or learning outcomes, as string. This method
     * tries to fetch content as first priority and learning outcomes as second priority. The String may also contain
     * html code in it, so it needs to be processed further before use.
     *
     * @param jsonObject the jsonObject, whose description is fetched.
     * @param content a key for the jsonObject, which is used to find the content.
     * @param outcomes a key for the jsonObject, which is used to find the learning outcomes.
     * @return the description, if such was found, otherwise it's blank.
     */
    private String fetchDescription(JsonObject jsonObject, String content, String outcomes) {
        JsonElement contentElement = jsonObject.get(content);
        JsonElement learningOutcomesElement = jsonObject.get(outcomes);
        String html = "";
        if (jsonObject.has("type") && jsonObject.get("type").getAsString().equals("GroupingModule")) {
            contentElement = jsonObject.get("rule").getAsJsonObject().get(content);
        }
        if (contentElement != null && !contentElement.isJsonNull()) {
            if ( contentElement.getAsJsonObject().get("fi") != null ) {
                html = contentElement.getAsJsonObject().get("fi").toString();
            }
            else if ( contentElement.getAsJsonObject().get("en") != null) {
                html = contentElement.getAsJsonObject().get("en").toString();
            }
            else {
                html = contentElement.getAsString();
            }
        }
        else if (learningOutcomesElement != null && !learningOutcomesElement.isJsonNull()) {
            if ( learningOutcomesElement.getAsJsonObject().get("fi") != null ) {
                html = learningOutcomesElement.getAsJsonObject().get("fi").toString();
            }
            else if ( learningOutcomesElement.getAsJsonObject().get("en") != null) {
                html = learningOutcomesElement.getAsJsonObject().get("en").toString();
            }
            else {
                html = learningOutcomesElement.getAsString();
            }
        }
        return html;
    }

    /**
     * A method, which parses a string containing html code to a string without html code. This method also removes
     * newline symbols from the string.
     *
     * @param html the string containing html code.
     * @return a clean string without html code or newline symbols.
     */
    private String htmlToString(String html) {
        String trimmedText = "";
        if(html.length() > 1) {
            Document descriptionWithHTML = Jsoup.parse(html);
            String descriptionWithoutHTML = descriptionWithHTML.body().text();
            String descriptionWithoutNewLines = descriptionWithoutHTML.replace("\\n", "");
            String finalText = removeQuotes(descriptionWithoutNewLines);
            trimmedText = finalText.trim();
        }
        return trimmedText;
    }

    /**
     * Removes quotes from a word by deleting the first and last character of the word.
     *
     * @param word the word with the quotes to be removed.
     * @return Substring without quote marks.
     */
    private String removeQuotes(String word) {
        return word.substring(1, word.length()-1);
    }

}