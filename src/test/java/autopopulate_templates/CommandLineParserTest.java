package autopopulate_templates;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CommandLineParserTest {

  private CommandLineParser commandLineParser1;
  private CommandLineParser commandLineParser2;

  @BeforeEach
  void setUp() throws InvalidArgumentsException {
    String[] args1 = new String[]{
        "--email",
        "--email-template", "email-template.txt",
        "--letter",
        "--letter-template", "letter-template.txt",
        "--output-dir", "./output",
        "--csv-file", "customer.csv"
    };

    String[] args2 = new String[]{
        "--email",
        "--email-template", "email-template.txt",
        "--output-dir", "./output-emails",
        "--csv-file", "customer.csv"
    };

    commandLineParser1 = new CommandLineParser(args1);
    commandLineParser2 = new CommandLineParser(args2);
  }

  @Test
  void testOption0() {
    String[] args = new String[]{
        "--email-template", "email-template.txt",
        "--letter-template", "letter-template.txt",
        "--output-dir", "./output",
        "--csv-file", "customer.csv"
    };
    assertThrows(InvalidArgumentsException.class, () -> {
      new CommandLineParser(args);
    });
  }

  @Test
  void testOption1() {
    String[] args = new String[]{
        "--email",
        "--output-dir", "./output-emails",
        "--csv-file", "customer.csv"
    };
    assertThrows(InvalidArgumentsException.class, () -> {
      new CommandLineParser(args);
    });
  }

  @Test
  void testOption2() {
    String[] args = new String[]{
        "--letter",
        "--output-dir", "./output-emails",
        "--csv-file", "customer.csv"
    };
    assertThrows(InvalidArgumentsException.class, () -> {
      new CommandLineParser(args);
    });
  }

  @Test
  void testOption3() {
    String[] args = new String[]{
        "--email",
        "--email-template", "email-template.txt",
        "--output-dir", "./output-emails"
    };
    assertThrows(InvalidArgumentsException.class, () -> {
      new CommandLineParser(args);
    });
  }

  @Test
  void testOption4() throws InvalidArgumentsException {
    String[] args = new String[]{
        "--email",
        "--email-template", "email-template.txt",
        "--csv-file", "customer.csv"
    };
    CommandLineParser testCommandLineParser = new CommandLineParser(args);
    assertEquals("./src/main/resources/output", testCommandLineParser.getOutputDir());
  }

  @Test
  void testOption5() {
    String[] args = new String[]{
        "--email",
        "--email-template", "email-template.txt",
        "--output-dir", "./output-emails",
        "--csv-file"
    };
    assertThrows(InvalidArgumentsException.class, () -> {
      new CommandLineParser(args);
    });
  }

  @Test
  void testOption6() {
    String[] args = new String[]{
        "--email",
        "--email-template", "email-template.txt",
        "--output-dir", "./output-emails",
        "--csv", "customer.csv"
    };
    assertThrows(InvalidArgumentsException.class, () -> {
      new CommandLineParser(args);
    });
  }

  @Test
  void getHasEmailOption() {
    assertTrue(commandLineParser1.hasEmailOption());
    assertTrue(commandLineParser2.hasEmailOption());
  }

  @Test
  void getHasLetterOption() {
    assertTrue(commandLineParser1.hasLetterOption());
    assertFalse(commandLineParser2.hasLetterOption());
  }

  @Test
  void getEmailTemplate() {
    assertEquals("email-template.txt", commandLineParser1.getEmailTemplate());
    assertEquals("email-template.txt", commandLineParser2.getEmailTemplate());
  }

  @Test
  void getLetterTemplate() {
    assertEquals("letter-template.txt", commandLineParser1.getLetterTemplate());
    assertEquals(null, commandLineParser2.getLetterTemplate());
  }

  @Test
  void getCsvFile() {
    assertEquals("customer.csv", commandLineParser1.getCsvFileDir());
    assertEquals("customer.csv", commandLineParser2.getCsvFileDir());
  }
}