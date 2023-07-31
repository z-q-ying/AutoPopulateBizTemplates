package autopopulate_templates;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.Objects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OptionTest {

  private String fileSeparator = File.separator;
  private Option option1;
  private Option option2;
  private Option option3;

  @BeforeEach
  void setUp() {
    String outputFolder = "." + fileSeparator + "output-emails";
    option1 = new Option("email", "customers.csv",
        "." + fileSeparator + "resources" + fileSeparator + "email-template.txt",
        "." + fileSeparator + "output");
    option2 = new Option("email", "customers.csv", "email-template.txt", outputFolder);
    option3 = new Option("letter", "customers.csv", "letter-template.txt", outputFolder);
  }

  @Test
  void getOptionStr() {
    assertEquals("email", option1.getOptionStr());
    assertEquals("email", option2.getOptionStr());
    assertEquals("letter", option3.getOptionStr());
  }

  @Test
  void getCsvFilePath() {
    assertEquals("customers.csv", option1.getCsvFilePath());
    assertEquals("customers.csv", option2.getCsvFilePath());
    assertEquals("customers.csv", option3.getCsvFilePath());
  }

  @Test
  void getTemplatePath() {
    assertEquals("." + fileSeparator + "resources" + fileSeparator + "email-template.txt",
        option1.getTemplatePath());
    assertEquals("email-template.txt", option2.getTemplatePath());
    assertEquals("letter-template.txt", option3.getTemplatePath());
  }

  @Test
  void getOutputDir() {
    assertEquals("." + fileSeparator + "output", option1.getOutputDir());
    assertEquals("." + fileSeparator + "output-emails", option2.getOutputDir());
    assertEquals("." + fileSeparator + "output-emails", option3.getOutputDir());
  }

  @Test
  void testEquals() {
    assertTrue(option1.equals(option1));
    assertFalse(option1.equals(null));
    assertFalse(option1.equals("option1"));
  }

  @Test
  void testEquals_differentFields() {
    String emailTemplatePath =
        "." + fileSeparator + "resources" + fileSeparator + "email-template.txt";
    String outputFolder = "." + fileSeparator + "output";
    Option otherOption1 = new Option("letter", "customers.csv", emailTemplatePath, outputFolder);
    String differentDatabase = "customers-database.csv";
    Option otherOption2 = new Option("email", differentDatabase, emailTemplatePath, outputFolder);
    String relativeEmailTemplate = "email-template.txt";
    Option otherOption3 = new Option("email", "customers.csv", relativeEmailTemplate, outputFolder);
    String differentOutputDir = "." + fileSeparator + "output-dir";
    Option otherOption4 = new Option("email", "customers.csv", emailTemplatePath,
        differentOutputDir);
    assertFalse(option1.equals(otherOption1));
    assertFalse(option1.equals(otherOption2));
    assertFalse(option1.equals(otherOption3));
    assertFalse(option1.equals(otherOption4));
  }

  @Test
  void testEquals_sameFields() {
    String templatePath = "." + fileSeparator + "resources" + fileSeparator + "email-template.txt";
    String outputDir = "." + fileSeparator + "output";
    Option otherOption = new Option("email", "customers.csv", templatePath, outputDir);
    assertTrue(option1.equals(otherOption));
  }

  @Test
  void testHashCode() {
    String templatePath = "." + fileSeparator + "resources" + fileSeparator + "email-template.txt";
    String outputDir = "." + fileSeparator + "output";
    int expectedHashCode = Objects.hash("email", "customers.csv", templatePath, outputDir);
    assertEquals(expectedHashCode, option1.hashCode());
  }

  @Test
  void testToString() {
    String templatePath = "." + fileSeparator + "resources" + fileSeparator + "email-template.txt";
    String outputDir = "." + fileSeparator + "output";
    String expectedStr = "Option{" +
        "optionStr='" + "email" + '\'' +
        ", csvFilePath='" + "customers.csv" + '\'' +
        ", templatePath='" + templatePath + '\'' +
        ", outputDir='" + outputDir + '\'' +
        '}';
    assertEquals(expectedStr, option1.toString());
  }
}