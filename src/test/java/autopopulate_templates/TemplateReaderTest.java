package autopopulate_templates;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TemplateReaderTest {

  private TemplateReader testReader;
  private String testPath;
  private Set<String> headers;
  private String expectedStr;

  @BeforeEach
  void setUp() throws TemplateInformationNotMatchException, IOException {
    headers = new HashSet<>();
    headers.add("first_name");
    headers.add("last_name");
    headers.add("company_name");
    headers.add("phone1");
    headers.add("email");
    testPath = "src" + File.separator + "test" + File.separator + "resources"+ File.separator +
        "small-template.txt";
    testReader = new TemplateReader(testPath, headers);
    expectedStr = "Hi there! I am [[first_name]] [[last_name]]. I am "
        + "working at [[company_name]]. If you have any question, please reach me at [[phone1]] OR "
        + "[[email]]. Thanks!\n";
  }

  @Test
  void getTemplatePath() {
    assertEquals(testPath, testReader.getTemplatePath());
  }

  @Test
  void getTemplateStr() {
    assertEquals(expectedStr, testReader.getTemplateStr());
  }

  @Test
  void testConstructor_exception1() {
    Set<String> anotherHeaders = new HashSet<>();
    anotherHeaders.add("first_name");
    assertThrows(TemplateInformationNotMatchException.class, () -> {
      TemplateReader anotherReader = new TemplateReader(testPath, anotherHeaders);
    });
  }

  @Test
  void testConstructor_exception2() {
    assertThrows(FileNotFoundException.class, () -> {
      TemplateReader anotherReader = new TemplateReader("src" + File.separator + "test"
          + File.separator + "resources" + File.separator + "small-template-new.txt", headers);
    });
  }

  @Test
  void testEquals() throws TemplateInformationNotMatchException, IOException {
    TemplateReader anotherReader = new TemplateReader(testPath, headers);
    assertTrue(testReader.equals(testReader));
    assertFalse(testReader.equals(null));
    assertFalse(testReader.equals(testPath));
    assertTrue(testReader.equals(anotherReader));
  }

  @Test
  void testEquals2() throws TemplateInformationNotMatchException, IOException {
    TemplateReader anotherReader = new TemplateReader("src" + File.separator + "test" +
        File.separator + "resources" + File.separator + "small-template-copy-for-test.txt", headers);
    assertFalse(testReader.equals(anotherReader));
  }

  @Test
  void testHashCode() {
    int hashcode = Objects.hash(testPath, expectedStr);
    assertEquals(hashcode, testReader.hashCode());
  }

  @Test
  void testToString() {
    String expectedString = "TemplateReader{" +
        "templatePath='" + testPath + '\'' +
        ", templateStr='" + expectedStr + '\'' +
        '}';
    assertEquals(expectedString, testReader.toString());
  }
}