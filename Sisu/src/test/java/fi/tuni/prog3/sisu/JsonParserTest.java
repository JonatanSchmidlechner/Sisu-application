package fi.tuni.prog3.sisu;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for JsonParser Class. This class test parseDegrees method and parseDegreeContent method. ParseDegrees
 *  * is tested by testing the amount of degrees and by testing the first and last degree. ParseDegreeContent is
 *  tested by testing the parsing of different types of degree structures and by testing unique and rare structures.
 *  Tests for parseDegreeContent test a pseudo random structure path from the top of the degree to assessments, chosen
 *  by me. Generally only one object is tested in each depth of the degree structure, but in situations, where the
 *  structure is more complex, than usually, more tests may have been done.
 */
class JsonParserTest {

    /**
     * This tests parseDegrees method by testing the amount of degrees parsed and by testing the first and last degree
     * parsed.
     */
    @Test
    public void testParseDegrees() {
        JsonParser parser = new JsonParser();
        List<StudyModule> actualDegrees = parser.parseDegrees();

        System.out.println("test degree count");
        int expectedDegreeCount = 273;
        int actualDegreeCount = actualDegrees.size();
        assertEquals(expectedDegreeCount, actualDegreeCount);

        System.out.println("Test first degree");
        StudyModule firstDegree = actualDegrees.get(0);
        assertEquals("Akuuttilääketieteen erikoislääkärikoulutus (55/2020)", firstDegree.getName());
        assertEquals("otm-87fb9507-a6dd-41aa-b924-2f15eca3b7ae", firstDegree.getId());
        assertEquals("otm-87fb9507-a6dd-41aa-b924-2f15eca3b7ae", firstDegree.getGroupId());
        assertEquals(0, firstDegree.getMinCredits());
        assertEquals(null, firstDegree.getMaxCredits());

        System.out.println("Test last degree");
        StudyModule lastDegree = actualDegrees.get(272);
        assertEquals("Ympäristö- ja energiatekniikan DI-ohjelma", lastDegree.getName());
        assertEquals("otm-0cff9e2d-a3c9-47b8-9a91-b155772dd823", lastDegree.getId());
        assertEquals("tut-dp-g-1162", lastDegree.getGroupId());
        assertEquals(120, lastDegree.getMinCredits());
        assertEquals(null, lastDegree.getMaxCredits());

    }

    /**
     * Test method for parseDegreeContent parsing a bachelor's degree.
     */
    @Test
    public void testParseDegreeContentForBachelorDegree() throws IOException {
        JsonParser parser = new JsonParser();
        parser.parseDegrees();
        StudyModule degree = parser.parseDegreeContent("Tieto- ja sähkötekniikan kandidaattiohjelma");

        String filename = "src/test/resources/DescriptionsForBachelorsDegree";
        List<String> descriptions = Files.readAllLines(new File(filename).toPath());
        System.out.println("Test degree");
        assertEquals("Tieto- ja sähkötekniikan kandidaattiohjelma", degree.getName());
        assertEquals("otm-d729cfc3-97ad-467f-86b7-b6729c496c82", degree.getId());
        assertEquals("otm-fa02a1e7-4fe1-43e3-818b-810d8e723531", degree.getGroupId());
        assertEquals(180, degree.getMinCredits());
        assertEquals(null, degree.getMaxCredits());
        assertEquals(descriptions.get(0), degree.getDescription());
        assertEquals(2, degree.getModuleAndCourseList().size());

        System.out.println("Test first children");
        StudyModule  itc = (StudyModule) degree.getModuleAndCourseList().get(0);

        assertEquals("Tietotekniikka", itc.getName());
        assertEquals("otm-b3cf3da9-3a62-4567-9192-f3c8822226da", itc.getId());
        assertEquals("otm-e4a8addd-5944-4f94-9e56-d1b51d1f22ce", itc.getGroupId());
        assertEquals(180, itc.getMinCredits());
        assertEquals(null, itc.getMaxCredits());
        assertEquals("", itc.getDescription());
        assertEquals(5, itc.getModuleAndCourseList().size());

        StudyModule electricity = (StudyModule) degree.getModuleAndCourseList().get(1);
        assertEquals("Sähkötekniikka", electricity.getName());
        assertEquals("otm-a31e4a68-73e1-48c3-a7dd-4efe3c258f86", electricity.getId());
        assertEquals("otm-b994335e-8759-4d7e-b3bf-ae505fd3935e", electricity.getGroupId());
        assertEquals(180, electricity.getMinCredits());
        assertEquals(null, electricity.getMaxCredits());
        assertEquals("", electricity.getDescription());
        assertEquals(5, electricity.getModuleAndCourseList().size());

        // Test first module in ITC and last module in electricity
        System.out.println("test second children");
        StudyModule commonStudies = (StudyModule) itc.getModuleAndCourseList().get(0);
        assertEquals("Tietotekniikan yhteiset opinnot", commonStudies.getName());
        assertEquals("otm-316ac8bf-ff36-4ec0-8997-617976500368", commonStudies.getId());
        assertEquals("otm-316ac8bf-ff36-4ec0-8997-617976500368", commonStudies.getGroupId());
        assertEquals(65, commonStudies.getMinCredits());
        assertEquals(null, commonStudies.getMaxCredits());
        assertEquals(descriptions.get(1), commonStudies.getDescription());
        assertEquals(15, commonStudies.getModuleAndCourseList().size());

        StudyModule electricityModule = (StudyModule) electricity.getModuleAndCourseList().get(4);
        assertEquals("Vapaasti valittavat opintojaksot", electricityModule.getName());
        assertEquals("otm-cbed2832-3231-4833-82b0-8547bac34293", electricityModule.getId());
        assertEquals("otm-cbed2832-3231-4833-82b0-8547bac34293", electricityModule.getGroupId());
        assertEquals(0, electricityModule.getMinCredits());
        assertEquals(null, electricityModule.getMaxCredits());
        assertEquals(descriptions.get(3), electricityModule.getDescription());
        assertEquals(0, electricityModule.getModuleAndCourseList().size());


        System.out.println("testCourse");
        Course course = (Course) commonStudies.getModuleAndCourseList().get(14);
        assertEquals("Teollisuustalouden perusteet", course.getName());
        assertEquals("otm-d59a2ea6-51f3-4bc2-8457-64ebd605f675", course.getId());
        assertEquals("tut-cu-g-48243", course.getGroupId());
        assertEquals(5, course.getMinCredits());
        assertEquals(5, course.getMaxCredits());
        assertEquals(descriptions.get(2), course.getDescription());
        assertEquals(3, course.getCompletionMethods().size());
        assertEquals(3, course.getCompletionMethods().get(0).size());
        assertEquals(2, course.getCompletionMethods().get(1).size());
        assertEquals(1, course.getCompletionMethods().get(2).size());

        System.out.println("Test assesments");
        List<List<Assessment>> completionMethods = course.getCompletionMethods();
        Assessment assessment31 = completionMethods.get(2).get(0);
        assertEquals("2. välikoe sekä tentti", assessment31.getName());
        assertEquals("sis-0-5", assessment31.getGrading());
        assertEquals(5, assessment31.getMinCredits());
        assertEquals(5, assessment31.getMaxCredits());

        Assessment assessment22 = completionMethods.get(1).get(1);
        assertEquals("2. välikoe sekä tentti", assessment22.getName());
        assertEquals("sis-0-5", assessment22.getGrading());
        assertEquals(5, assessment22.getMinCredits());
        assertEquals(5, assessment22.getMaxCredits());

        Assessment assessment12 = completionMethods.get(0).get(1);
        assertEquals("Oppimistapahtumat", assessment12.getName());
        assertEquals("sis-hyl-hyv", assessment12.getGrading());
        assertEquals(0, assessment12.getMinCredits());
        assertEquals(0, assessment12.getMaxCredits());
    }

    /**
     * Test method for parseDegreeContent parsing a diploma engineer degree.
     */
    @Test
    public void testParseDegreeContentForDiplomaEngineerDegree() throws IOException {
        JsonParser parser = new JsonParser();
        parser.parseDegrees();
        StudyModule degree = parser.parseDegreeContent("Tuotantotalouden DI-ohjelma");
        String filename = "src/test/resources/DescriptionsForDiplomEngineerDegree";
        List<String> descriptions = Files.readAllLines(new File(filename).toPath());
        System.out.println("Test degree");

        assertEquals("Tuotantotalouden DI-ohjelma", degree.getName());
        assertEquals("otm-fe3b8866-6304-45a3-9ff9-a2ca85959c85", degree.getId());
        assertEquals("tut-dp-g-1166", degree.getGroupId());
        assertEquals(120, degree.getMinCredits());
        assertEquals(null, degree.getMaxCredits());
        assertEquals(descriptions.get(0), degree.getDescription());
        assertEquals(4, degree.getModuleAndCourseList().size());

        System.out.println("Test first children");
        StudyModule  firstOfFirstChildren = (StudyModule) degree.getModuleAndCourseList().get(0);

        assertEquals("International Sales and Sourcing", firstOfFirstChildren.getName());
        assertEquals("otm-e5984df0-3d5a-434f-ad4a-0b763848d473", firstOfFirstChildren.getId());
        assertEquals("otm-e5984df0-3d5a-434f-ad4a-0b763848d473", firstOfFirstChildren.getGroupId());
        assertEquals(120, firstOfFirstChildren.getMinCredits());
        assertEquals(null, firstOfFirstChildren.getMaxCredits());
        assertEquals("", firstOfFirstChildren.getDescription());
        assertEquals(5, firstOfFirstChildren.getModuleAndCourseList().size());

        StudyModule lastOfFirstChildren = (StudyModule) degree.getModuleAndCourseList().get(3);
        assertEquals("Tuotannon ja toimitusketjun hallinta", lastOfFirstChildren.getName());
        assertEquals("otm-4c131c8f-70c2-4d75-ac0b-4bc230aee725", lastOfFirstChildren.getId());
        assertEquals("otm-4c131c8f-70c2-4d75-ac0b-4bc230aee725", lastOfFirstChildren.getGroupId());
        assertEquals(120, lastOfFirstChildren.getMinCredits());
        assertEquals(null, lastOfFirstChildren.getMaxCredits());
        assertEquals("", lastOfFirstChildren.getDescription());
        assertEquals(5, lastOfFirstChildren.getModuleAndCourseList().size());

        // Test one module from middle
        System.out.println("test second generation children");
        StudyModule middleModule = (StudyModule) firstOfFirstChildren.getModuleAndCourseList().get(2);
        assertEquals("Tekninen tai luonnontieteellinen aineopintokokonaisuus", middleModule.getName());
        assertEquals("otm-47ac46bb-65bb-4dfd-88b8-b4336cc1a107", middleModule.getId());
        assertEquals("otm-47ac46bb-65bb-4dfd-88b8-b4336cc1a107", middleModule.getGroupId());
        assertEquals(20, middleModule.getMinCredits());
        assertEquals(null, middleModule.getMaxCredits());
        assertEquals("", middleModule.getDescription());
        assertEquals(31, middleModule.getModuleAndCourseList().size());

        System.out.println("test third generation children");
        StudyModule randomModuleFromMiddleModule = (StudyModule) middleModule.getModuleAndCourseList().get(26);
        assertEquals("Intermediate Studies in Information Security as Free Choice Studies",
                randomModuleFromMiddleModule.getName());
        assertEquals("otm-4b013d2a-c56e-454a-be90-d2c1226d231c", randomModuleFromMiddleModule.getId());
        assertEquals("otm-4b013d2a-c56e-454a-be90-d2c1226d231c", randomModuleFromMiddleModule.getGroupId());
        assertEquals(20, randomModuleFromMiddleModule.getMinCredits());
        assertEquals(null, randomModuleFromMiddleModule.getMaxCredits());
        assertEquals("", randomModuleFromMiddleModule.getDescription());
        assertEquals(8, randomModuleFromMiddleModule.getModuleAndCourseList().size());

        // Test two courses from same module, but different rules (different array in KORI-API)
        System.out.println("testCourse");
        Course firstCourse = (Course) randomModuleFromMiddleModule.getModuleAndCourseList().get(1);
        assertEquals("Cyber Security II: Specialisation", firstCourse.getName());
        assertEquals("otm-5c32200c-ac67-461a-8fd1-1ddd3d898457", firstCourse.getId());
        assertEquals("tut-cu-g-45508", firstCourse.getGroupId());
        assertEquals(5, firstCourse.getMinCredits());
        assertEquals(5, firstCourse.getMaxCredits());
        assertEquals(descriptions.get(1), firstCourse.getDescription());
        assertEquals(1, firstCourse.getCompletionMethods().size());
        assertEquals(2, firstCourse.getCompletionMethods().get(0).size());

        Course secondCourse = (Course) randomModuleFromMiddleModule.getModuleAndCourseList().get(7);
        assertEquals("Internet of Things", secondCourse.getName());
        assertEquals("otm-78ff85cb-13f1-405d-9639-be7228e66a19", secondCourse.getId());
        assertEquals("tut-cu-g-45816", secondCourse.getGroupId());
        assertEquals(5, secondCourse.getMinCredits());
        assertEquals(5, secondCourse.getMaxCredits());
        assertEquals(descriptions.get(2), secondCourse.getDescription());
        assertEquals(2, secondCourse.getCompletionMethods().size());
    }

    /**
     * Test method for parseDegreeContent parsing a master's degree
     */
    @Test
    public void testParseDegreeContentForMastersDegree() throws IOException {
        JsonParser parser = new JsonParser();
        parser.parseDegrees();
        StudyModule degree = parser.parseDegreeContent("Työn ja hyvinvoinnin maisteriohjelma, Pori");
        String filename = "src/test/resources/DescriptionForMastersDegree";
        List<String> descriptions = Files.readAllLines(new File(filename).toPath());
        System.out.println("Test degree");

        assertEquals("Työn ja hyvinvoinnin maisteriohjelma, Pori", degree.getName());
        assertEquals("otm-118039f8-1716-4ad0-bcff-2ce927c8734e", degree.getId());
        assertEquals("otm-c9ad1e20-7435-40d0-aace-0bf30436c7d9", degree.getGroupId());
        assertEquals(120, degree.getMinCredits());
        assertEquals(null, degree.getMaxCredits());
        assertEquals(descriptions.get(0), degree.getDescription());
        assertEquals(3, degree.getModuleAndCourseList().size());

        System.out.println("Test first children");
        StudyModule  firstOfFirstChildren = (StudyModule) degree.getModuleAndCourseList().get(0);

        assertEquals("Työn ja hyvinvoinnin maisteriohjelman syventävät opinnot",
                firstOfFirstChildren.getName());
        assertEquals("otm-f7e5f75a-4992-4f06-bdb3-25a002cf5b72", firstOfFirstChildren.getId());
        assertEquals("uta-ok-ykoodi-46355", firstOfFirstChildren.getGroupId());
        assertEquals(95, firstOfFirstChildren.getMinCredits());
        assertEquals(95, firstOfFirstChildren.getMaxCredits());
        assertEquals(descriptions.get(1), firstOfFirstChildren.getDescription());
        assertEquals(5, firstOfFirstChildren.getModuleAndCourseList().size());

        StudyModule lastOfFirstChildren = (StudyModule) degree.getModuleAndCourseList().get(2);
        assertEquals("Vapaasti valittavat opintojaksot", lastOfFirstChildren.getName());
        assertEquals("otm-535eae3a-0efe-42b7-81e9-7056cef2f218", lastOfFirstChildren.getId());
        assertEquals("otm-812c3307-67da-4c6e-8f99-ccfce20ccd83", lastOfFirstChildren.getGroupId());
        assertEquals(0, lastOfFirstChildren.getMinCredits());
        assertEquals(null, lastOfFirstChildren.getMaxCredits());
        assertEquals("", lastOfFirstChildren.getDescription());
        assertEquals(0, lastOfFirstChildren.getModuleAndCourseList().size());

        // Test pro-gradu
        System.out.println("test pro-gradu");
        StudyModule gradu = (StudyModule) firstOfFirstChildren.getModuleAndCourseList().get(4);
        assertEquals("Pro gradu -tutkielma ja kypsyysnäyte", gradu.getName());
        assertEquals("otm-df69260f-7e1e-4fc8-b1fa-9f1818923830", gradu.getId());
        assertEquals("otm-df69260f-7e1e-4fc8-b1fa-9f1818923830", gradu.getGroupId());
        assertEquals(null, gradu.getMinCredits());
        assertEquals(null, gradu.getMaxCredits());
        assertEquals("", gradu.getDescription());
        assertEquals(2, gradu.getModuleAndCourseList().size());

        System.out.println("test gradu children");
        StudyModule graduChild = (StudyModule) gradu.getModuleAndCourseList().get(1);
        assertEquals("Kypsyysnäyte ylemmässä korkeakoulututkinnossa",
                graduChild.getName());
        assertEquals("otm-c705226b-ba63-4865-aef1-82079a3f8573", graduChild.getId());
        assertEquals("otm-72783505-da93-4d4d-8a87-21add81026de", graduChild.getGroupId());
        assertEquals(0, graduChild.getMinCredits());
        assertEquals(0, graduChild.getMaxCredits());
        assertEquals("", graduChild.getDescription());
        assertEquals(2, graduChild.getModuleAndCourseList().size());

        System.out.println("test graduChild child");
        StudyModule graduChildsChild = (StudyModule) graduChild.getModuleAndCourseList().get(1);
        assertEquals("Asetuksen mukaista suomen tai ruotsin kielen taitoa ei ole osoitettu aiemman" +
                        " korkeakoulututkinnon yhteydessä", graduChildsChild.getName());
        assertEquals("otm-e1478af5-4a24-46ea-aefd-a67ab17180e6", graduChildsChild.getId());
        assertEquals("otm-e1478af5-4a24-46ea-aefd-a67ab17180e6", graduChildsChild.getGroupId());
        assertEquals(null, graduChildsChild.getMinCredits());
        assertEquals(null, graduChildsChild.getMaxCredits());
        assertEquals("", graduChildsChild.getDescription());
        assertEquals(2, graduChildsChild.getModuleAndCourseList().size());

        System.out.println("testCourse");
        Course firstCourse = (Course) graduChildsChild.getModuleAndCourseList().get(0);
        assertEquals("Kypsyysnäyte suomeksi ylemmässä korkeakoulututkinnossa, sisällön ja kielen tarkistus",
                firstCourse.getName());
        assertEquals("otm-ee27e091-3fea-43b1-9670-c28e9917109a", firstCourse.getId());
        assertEquals("otm-ecfe169f-c9f4-4f1d-85ed-67aabe30bd1b", firstCourse.getGroupId());
        assertEquals(0, firstCourse.getMinCredits());
        assertEquals(0, firstCourse.getMaxCredits());
        assertEquals("", firstCourse.getDescription());
        assertEquals(1, firstCourse.getCompletionMethods().size());
        assertEquals(2, firstCourse.getCompletionMethods().get(0).size());

    }

    /**
     * Test method for parseDegreeContent parsing a doctor-licentiate degree.
     */
    @Test
    public void testParseDegreeContentForDoctorLicentiateDegree() throws IOException {
        JsonParser parser = new JsonParser();
        parser.parseDegrees();
        StudyModule degree = parser.parseDegreeContent("Teknis-taloudellinen tohtoriohjelma," +
                " lisensiaatin tutkinto");
        String filename = "src/test/resources/DescriptionForDoctorLicentiateDegree";
        List<String> descriptions = Files.readAllLines(new File(filename).toPath());
        System.out.println("Test degree");

        assertEquals("Teknis-taloudellinen tohtoriohjelma, lisensiaatin tutkinto", degree.getName());
        assertEquals("otm-b54c8540-1b18-4187-b6f6-5c86123de0f7", degree.getId());
        assertEquals("otm-ebba9288-6a6c-4d08-855f-54c0177b5bf3", degree.getGroupId());
        assertEquals(130, degree.getMinCredits());
        assertEquals(null, degree.getMaxCredits());
        assertEquals(descriptions.get(0), degree.getDescription());
        assertEquals(2, degree.getModuleAndCourseList().size());

        System.out.println("Test first children");
        StudyModule firstChild = (StudyModule) degree.getModuleAndCourseList().get(0);

        assertEquals("Tuotantotalous", firstChild.getName());
        assertEquals("otm-e422cefa-15a5-41bd-b48f-4cd1b4c08a8b", firstChild.getId());
        assertEquals("otm-b892ebdb-f706-46c2-aecf-c455a45fc760", firstChild.getGroupId());
        assertEquals(130, firstChild.getMinCredits());
        assertEquals(null, firstChild.getMaxCredits());
        assertEquals("", firstChild.getDescription());
        assertEquals(3, firstChild.getModuleAndCourseList().size());

        StudyModule lastChildOfFirstChild = (StudyModule) firstChild.getModuleAndCourseList().get(2);
        assertEquals("Tutkimusalan ja tieteenalan opinnot, tuotantotalous", lastChildOfFirstChild.getName());
        assertEquals("otm-4574681e-ab71-4f98-aac9-ea9de66138ad", lastChildOfFirstChild.getId());
        assertEquals("otm-2e139db1-a65d-45fb-9213-c4d051fe8f68", lastChildOfFirstChild.getGroupId());
        assertEquals(25, lastChildOfFirstChild.getMinCredits());
        assertEquals(null, lastChildOfFirstChild.getMaxCredits());
        assertEquals(descriptions.get(1), lastChildOfFirstChild.getDescription());
        assertEquals(5, lastChildOfFirstChild.getModuleAndCourseList().size());

        System.out.println("testCourse");
        Course lastCourse = (Course) lastChildOfFirstChild.getModuleAndCourseList().get(4);
        assertEquals("Tiedeyhteisön toimintaan osallistuminen",
                lastCourse.getName());
        assertEquals("otm-e726b216-3a16-4494-b095-382e3e8e1435", lastCourse.getId());
        assertEquals("otm-029018e9-64cf-4cb4-96c8-e4c03b2109a4", lastCourse.getGroupId());
        assertEquals(1, lastCourse.getMinCredits());
        assertEquals(4, lastCourse.getMaxCredits());
        assertEquals(descriptions.get(2), lastCourse.getDescription());
        assertEquals(1, lastCourse.getCompletionMethods().size());
        assertEquals(1, lastCourse.getCompletionMethods().get(0).size());
    }

    /**
     * Test method for parseDegreeContent parsing a doctoral degree.
     */
    @Test
    public void testParseDegreeContentForGeneralDoctoralDegree() throws IOException {
        JsonParser parser = new JsonParser();
        parser.parseDegrees();
        StudyModule degree = parser.parseDegreeContent("Yhteiskuntatieteiden tiedekunnan yleinen" +
                " tohtoriohjelma");
        String filename = "src/test/resources/DescriptionsForGeneralDoctoralDegree";
        List<String> descriptions = Files.readAllLines(new File(filename).toPath());
        System.out.println("Test degree");

        assertEquals("Yhteiskuntatieteiden tiedekunnan yleinen tohtoriohjelma", degree.getName());
        assertEquals("otm-5d6f31cd-4e25-4fab-8eac-0290a091dbde", degree.getId());
        assertEquals("otm-04cee8ad-9cac-4d13-8946-47f0f0abe62f", degree.getGroupId());
        assertEquals(240, degree.getMinCredits());
        assertEquals(null, degree.getMaxCredits());
        assertEquals("", degree.getDescription());
        assertEquals(27, degree.getModuleAndCourseList().size());

        System.out.println("Test first children");
        StudyModule firstChild = (StudyModule) degree.getModuleAndCourseList().get(0);

        assertEquals("Biostatistiikka, yleinen tohtoriohjelma", firstChild.getName());
        assertEquals("otm-f2fb7f15-5389-4b3d-afe1-686186ce35ee", firstChild.getId());
        assertEquals("otm-b5a2932e-e06e-493d-ba34-771fe1cf3b7d", firstChild.getGroupId());
        assertEquals(240, firstChild.getMinCredits());
        assertEquals(null, firstChild.getMaxCredits());
        assertEquals("", firstChild.getDescription());
        assertEquals(0, firstChild.getModuleAndCourseList().size());

        StudyModule lastChild = (StudyModule) degree.getModuleAndCourseList().get(26);
        assertEquals("Vokologia, yleinen tohtoriohjelma", lastChild.getName());
        assertEquals("otm-83285cf4-bd26-409d-ae4f-dc9a79fa37d2", lastChild.getId());
        assertEquals("otm-0cbd3b56-5bcf-46d0-a4ae-0d9ac924c24b", lastChild.getGroupId());
        assertEquals(240, lastChild.getMinCredits());
        assertEquals(null, lastChild.getMaxCredits());
        assertEquals("", lastChild.getDescription());
        assertEquals(0, lastChild.getModuleAndCourseList().size());
    }

    /**
     * Test method for parseDegreeContent parsing a special medicine degree.
     */
    @Test
    public void testParseDegreeContentForSpecialMedicineDegree() throws IOException {
        JsonParser parser = new JsonParser();
        parser.parseDegrees();
        StudyModule degree = parser.parseDegreeContent("Fysiatrian erikoislääkärikoulutus (56/2015)");
        String filename = "src/test/resources/DescriptionForMedicineDegree";
        List<String> descriptions = Files.readAllLines(new File(filename).toPath());
        System.out.println("Test degree");

        assertEquals("Fysiatrian erikoislääkärikoulutus (56/2015)", degree.getName());
        assertEquals("otm-a80884dc-fdfd-473b-bbf0-c73680f680fe", degree.getId());
        assertEquals("otm-a80884dc-fdfd-473b-bbf0-c73680f680fe", degree.getGroupId());
        assertEquals(0, degree.getMinCredits());
        assertEquals(null, degree.getMaxCredits());
        assertEquals("", degree.getDescription());
        assertEquals(1, degree.getModuleAndCourseList().size());

        System.out.println("Test first children");
        StudyModule firstChild = (StudyModule) degree.getModuleAndCourseList().get(0);

        assertEquals("Fysiatrian erikoislääkärikoulutus", firstChild.getName());
        assertEquals("otm-17563fb6-d01f-4813-9ae4-b11abd4f2144", firstChild.getId());
        assertEquals("otm-cfa5fade-8d52-4403-84f2-cfd8d0b9963f", firstChild.getGroupId());
        assertEquals(0, firstChild.getMinCredits());
        assertEquals(null, firstChild.getMaxCredits());
        assertEquals(descriptions.get(0), firstChild.getDescription());
        assertEquals(5, firstChild.getModuleAndCourseList().size());

        StudyModule firstChildsChild = (StudyModule) firstChild.getModuleAndCourseList().get(4);
        assertEquals("Moniammatilliset johtamisopinnot", firstChildsChild.getName());
        assertEquals("otm-b170aa6d-4f71-4ea5-beea-67d627b7ba97", firstChildsChild.getId());
        assertEquals("otm-867c3d2c-0332-4ef0-935b-d61a045473d3", firstChildsChild.getGroupId());
        assertEquals(10, firstChildsChild.getMinCredits());
        assertEquals(30, firstChildsChild.getMaxCredits());
        assertEquals(descriptions.get(1), firstChildsChild.getDescription());
        assertEquals(2, firstChildsChild.getModuleAndCourseList().size());

        StudyModule lastModule = (StudyModule) firstChildsChild.getModuleAndCourseList().get(1);
        assertEquals("Johtamisosaamista laajentavat opinnot", lastModule.getName());
        assertEquals("otm-64685c5d-56cb-45b4-88b5-73edcc55ba30", lastModule.getId());
        assertEquals("otm-64685c5d-56cb-45b4-88b5-73edcc55ba30", lastModule.getGroupId());
        assertEquals(null, lastModule.getMinCredits());
        assertEquals(null, lastModule.getMaxCredits());
        assertEquals("", lastModule.getDescription());
        assertEquals(6, lastModule.getModuleAndCourseList().size());

        Course course = (Course) lastModule.getModuleAndCourseList().get(2);
        assertEquals("Tiedolla johtaminen terveydenhuollossa", course.getName());
        assertEquals("otm-dc07b153-42e1-4934-a8f4-8b2b21187d35", course.getId());
        assertEquals("otm-249970b2-3784-4eba-bc31-bcb49c082a0f", course.getGroupId());
        assertEquals(2, course.getMinCredits());
        assertEquals(2, course.getMaxCredits());
        assertEquals(descriptions.get(2), course.getDescription());
        assertEquals(1, course.getCompletionMethods().size());
        assertEquals(1, course.getCompletionMethods().get(0).size());
    }

    /**
     * Test method for parseDegreeContent parsing a real degree before parseDegrees method is called. This also
     * tests parseDegreeContent using an invalid parameter.
     */
    @Test
    public void testParseDegreeContentInvalid() {
        JsonParser parser = new JsonParser();
        System.out.println("Test method without parsing degrees first:");
        assertNull(parser.parseDegreeContent("Tiedolla johtaminen terveydenhuollossa"));

        parser.parseDegrees();
        assertNull(parser.parseDegreeContent("Not a degreeName"));
    }
}
