package autopopulate_templates;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TemplateWriterTest {
  private TemplateWriter testWriter;
  private String testTempString;
  private List<Map<String, String>> testCsvData;
  private String testOptionStr;
  private String testOutputDir;

  @BeforeEach
  void setUp() {
    testTempString = "Hi there! I am [[first_name]] [[last_name]]. I am "
        + "working at [[company_name]]. If you have any question, please reach me at [[phone1]] OR "
        + "[[email]]. Thanks!";
    testCsvData = new ArrayList<>();

    Map<String, String> row1 = new HashMap<>();
    Map<String, String> row2 = new HashMap<>();

    row1.put("first_name", "James");
    row1.put("last_name", "Butt");
    row1.put("company_name", "Benton, John B Jr");
    row1.put("address", "6649 N Blue Gum St");
    row1.put("state", "LA");
    row1.put("phone1", "504-621-8927");
    row1.put("email", "jbutt@gmail.com");

    row2.put("first_name", "Josephine");
    row2.put("last_name", "Darakjy");
    row2.put("company_name", "Chanay, Jeffrey A Esq");
    row2.put("address", "4 B Blue Ridge Blvd");
    row2.put("state", "MI");
    row2.put("phone1", "810-292-9388");
    row2.put("email", "josephine_darakjy@darakjy.org");

    testCsvData.add(row1);
    testCsvData.add(row2);
    testOptionStr = "--email";
    testOutputDir = "." + File.separator + "src" + File.separator + "test" + File.separator +
        "resources" + File.separator + "output";

    testWriter = new TemplateWriter(testTempString, testCsvData, testOptionStr, testOutputDir);
  }

  @Test
  void testGenerateOutput(){
    try {
      testWriter.generateOutput();
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }

  @Test
  void testEquals() {
    TemplateWriter anotherWriter = new TemplateWriter(testTempString, testCsvData, testOptionStr, testOutputDir);
    assertTrue(testWriter.equals(testWriter));
    assertFalse(testWriter.equals(null));
    assertFalse(testWriter.equals(testCsvData));
    assertTrue(testWriter.equals(anotherWriter));
  }

  @Test
  void testHashCode() {
    int hashcode = Objects.hash(testOptionStr, testOutputDir, testTempString, testCsvData);
    assertEquals(hashcode, testWriter.hashCode());
  }

  @Test
  void testToString() {
    String expectedString ="TemplateWriter{" +
        "option='" + testOptionStr + '\'' +
        ", outputDir='" + testOutputDir + '\'' +
        ", tempString='" + testTempString + '\'' +
        ", csvData=" + testCsvData +
        '}';
    assertEquals(expectedString, testWriter.toString());
  }
}