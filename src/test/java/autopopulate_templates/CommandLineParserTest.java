package autopopulate_templates;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CommandLineParserTest {

  private String fileSeparator = File.separator;
  private CommandLineParser commandLineParser1;

  @BeforeEach
  void setUp() throws IllegalArgumentException {
    String outputPath = "." + File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + "output" + File.separator;
    String[] args1 = new String[]{
        "--email",
        "--email-template", "email-template.txt",
        "--letter",
        "--letter-template", "letter-template.txt",
        "--output-dir", outputPath,
        "--csv-file", "customer.csv"
    };
    commandLineParser1 = new CommandLineParser(args1);
  }

  @Test
  void testConstructor1() throws IllegalArgumentException {
    String[] args2 = new String[]{
        "--email",
        "--email-template", "email-template.txt",
        "--output-dir", "." + fileSeparator + "output-emails",
        "--csv-file", "customer.csv"
    };
    CommandLineParser commandLineParser2 = new CommandLineParser(args2);
  }

  @Test
  void testConstructor2() throws IllegalArgumentException {
    String[] args3 = new String[]{
        "--letter",
        "--letter-template", "letter-template.txt",
        "--output-dir", "." + fileSeparator + "output-emails",
        "--csv-file", "customer.csv"
    };
    CommandLineParser commandLineParser3 = new CommandLineParser(args3);
  }

  @Test
  void testOption_errorNoOutputDir() {
    String[] otherArgs1 = new String[]{
        "--email-template", "email-template.txt",
        "--letter-template", "letter-template.txt",
        "--output-dir", "." + fileSeparator + "output",
        "--csv-file", "customer.csv"
    };
    assertThrows(IllegalArgumentException.class, () -> {
      new CommandLineParser(otherArgs1);
    });
  }

  @Test
  void testOption_errorNoLetterValue() {
    String[] otherArgs2 = new String[]{
        "--email",
        "--email-template", "email-template.txt",
        "--letter",
        "--letter-template",
        "--output-dir", "." + fileSeparator + "output",
        "--csv-file", "customer.csv"
    };
    assertThrows(IllegalArgumentException.class, () -> {
      new CommandLineParser(otherArgs2);
    });
  }

  @Test
  void testOption_errorNoLetterOption() {
    String[] otherArgs3 = new String[]{
        "--email",
        "--email-template", "email-template.txt",
        "--letter-template", "letter-template.txt",
        "--output-dir", "." + fileSeparator + "output",
        "--csv-file", "customer.csv"
    };
    assertThrows(IllegalArgumentException.class, () -> {
      new CommandLineParser(otherArgs3);
    });
  }

  @Test
  void testOption_errorNoEmailOption() {
    String[] otherArgs4 = new String[]{
        "--email-template", "email-template.txt",
        "--letter",
        "--letter-template", "letter-template.txt",
        "--output-dir", "." + fileSeparator + "output",
        "--csv-file", "customer.csv"
    };
    assertThrows(IllegalArgumentException.class, () -> {
      new CommandLineParser(otherArgs4);
    });
  }

  @Test
  void getOptionsList() {
    String outputPath = "." + File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + "output" + File.separator;
    Option option1 = new Option("email", "customer.csv", "email-template.txt", outputPath);
    Option option2 = new Option("letter", "customer.csv", "letter-template.txt", outputPath);
    ArrayList<Option> optionsList = new ArrayList<>();
    optionsList.add(option1);
    optionsList.add(option2);
    assertEquals(optionsList, commandLineParser1.getOptionsList());
  }

  @Test
  void testOption1() {
    String[] args = new String[]{
        "--email",
        "--output-dir", "." + fileSeparator + "output-emails",
        "--csv-file", "customer.csv"
    };
    assertThrows(IllegalArgumentException.class, () -> {
      new CommandLineParser(args);
    });
  }

  @Test
  void testOption2() {
    String[] args = new String[]{
        "--letter",
        "--output-dir", "." + fileSeparator + "output-emails",
        "--csv-file", "customer.csv"
    };
    assertThrows(IllegalArgumentException.class, () -> {
      new CommandLineParser(args);
    });
  }

  @Test
  void testOption3() {
    String[] args = new String[]{
        "--email",
        "--email-template", "email-template.txt",
        "--output-dir", "." + fileSeparator + "output-emails",
    };
    assertThrows(IllegalArgumentException.class, () -> {
      new CommandLineParser(args);
    });
  }

  @Test
  void testOption4() throws IllegalArgumentException {
    String[] args = new String[]{
        "--email",
        "--email-template", "email-template.txt",
        "--csv-file", "customer.csv"
    };
    assertThrows(IllegalArgumentException.class, () -> {
      new CommandLineParser(args);
    });
  }

  @Test
  void testOption5() {
    String[] args = new String[]{
        "--email",
        "--email-template", "email-template.txt",
        "--output-dir", "." + fileSeparator + "output-emails",
        "--csv-file"
    };
    assertThrows(IllegalArgumentException.class, () -> {
      new CommandLineParser(args);
    });
  }

  @Test
  void testOption6() {
    String[] args = new String[]{
        "--email",
        "--email-template", "email-template.txt",
        "--output-dir", "." + fileSeparator + "output-emails",
        "--csv", "customer.csv"
    };
    assertThrows(IllegalArgumentException.class, () -> {
      new CommandLineParser(args);
    });
  }

  @Test
  void testEquals() {
    assertTrue(commandLineParser1.equals(commandLineParser1));
    assertFalse(commandLineParser1.equals(null));
    assertFalse(commandLineParser1.equals("commandLineParser1"));
  }

  @Test
  void testEquals_differentFields() throws IllegalArgumentException {
    String[] otherArgs = new String[]{
        "--email",
        "--email-template", "other-email-template.txt",
        "--letter",
        "--letter-template", "other-letter-template.txt",
        "--output-dir", "." + fileSeparator + "output",
        "--csv-file", "customer.csv"
    };
    CommandLineParser otherCommandLineParser = new CommandLineParser(otherArgs);
    assertFalse(commandLineParser1.equals(otherCommandLineParser));
  }

  @Test
  void testEquals_sameFields() throws IllegalArgumentException {
    String[] otherArgs = new String[]{
        "--email",
        "--email-template", "email-template.txt",
        "--letter",
        "--letter-template", "letter-template.txt",
        "--output-dir", "." + File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + "output" + File.separator,
        "--csv-file", "customer.csv"
    };
    CommandLineParser otherCommandLineParser = new CommandLineParser(otherArgs);
    assertTrue(commandLineParser1.equals(otherCommandLineParser));
  }

  @Test
  void testHashCode() {
    String outputPath = "." + File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + "output" + File.separator;
    Option option1 = new Option("email", "customer.csv", "email-template.txt", outputPath);
    Option option2 = new Option("letter", "customer.csv", "letter-template.txt", outputPath);
    ArrayList<Option> optionsList = new ArrayList<>();
    optionsList.add(option1);
    optionsList.add(option2);
    int expectedHashCode = Objects.hash(optionsList);
    assertEquals(expectedHashCode, commandLineParser1.hashCode());
  }

  @Test
  void testToString() {
    String outputPath = "." + File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + "output" + File.separator;
    Option option1 = new Option("email", "customer.csv", "email-template.txt", outputPath);
    Option option2 = new Option("letter", "customer.csv", "letter-template.txt", outputPath);
    ArrayList<Option> optionsList = new ArrayList<>();
    optionsList.add(option1);
    optionsList.add(option2);
    String expectedStr = "CommandLineParser{" +
        "optionsList=" + optionsList +
        '}';
    assertEquals(expectedStr, commandLineParser1.toString());
  }
}