package autopopulate_templates;

import java.util.HashMap;
import java.util.Map;

public class CommandLineParser {

  // TODO From coding aspect, what is the difference between email and letter option?
  private static final String OPTION_EMAIL = "--email";
  private static final String OPTION_EMAIL_TEMPLATE = "--email-template"; // --email-template <path/to/file>
  private static final String OPTION_LETTER = "--letter";
  private static final String OPTION_LETTER_TEMPLATE = "--letter-template"; // -letter-template <path/to/file
  private static final String OPTION_OUTPUT_DIR = "--output-dir"; // --output-dir <path/to/folder>
  private static final String OPTION_CSV_FILE = "--csv-file"; // --csv-file <path/to/file>
  private static final String DEFAULT_OUTPUT_DIR = "./src/main/resources/output";

  private Boolean hasEmailOption;
  private Boolean hasLetterOption;
  private Map<String, String> options;

  public CommandLineParser(String[] args) throws InvalidArgumentsException {
    options = new HashMap<>();
    hasEmailOption = false;
    hasLetterOption = false;
    this.parseArguments(args);
    this.validateArguments();
  }

  private void parseArguments(String[] args) throws InvalidArgumentsException {

    for (int i = 0; i < args.length; i++) {
      String option = args[i];
      switch (option) {
        case OPTION_EMAIL:
          hasEmailOption = true;
          break;
        case OPTION_LETTER:
          hasLetterOption = true;
          break;
        case OPTION_EMAIL_TEMPLATE:
        case OPTION_LETTER_TEMPLATE:
        case OPTION_OUTPUT_DIR:
        case OPTION_CSV_FILE:
          parseArgumentsHelper(args, option, i);
          i++;
          break;
        default:
          throw new InvalidArgumentsException("Unknown option: " + option);
      }
    }
  }

  private void parseArgumentsHelper(String[] args, String option, int index)
      throws InvalidArgumentsException {
    if (index + 1 < args.length) {
      options.put(option, args[index + 1]);
    } else {
      throw new InvalidArgumentsException(option + " provided but no value was given.");
    }
  }

  private void validateArguments() throws InvalidArgumentsException {
    if (!hasEmailOption && !hasLetterOption) {
      throw new InvalidArgumentsException("Either --email or --letter option must be provided.");
    }
    if (hasEmailOption && !options.containsKey(OPTION_EMAIL_TEMPLATE)) {
      throw new InvalidArgumentsException(
          "Option --email provided but no --email-template was given.");
    }
    if (hasLetterOption && !options.containsKey(OPTION_LETTER_TEMPLATE)) {
      throw new InvalidArgumentsException(
          "Option --letter provided but no --letter-template was given.");
    }
    if (!options.containsKey(OPTION_CSV_FILE)) {
      throw new InvalidArgumentsException("Option --csv-file must be provided.");
    }
    if (!options.containsKey(
        OPTION_OUTPUT_DIR)) { // TODO Check with the professor to see if the output directory is optional
      options.put(OPTION_OUTPUT_DIR, DEFAULT_OUTPUT_DIR);
    }
  }

  // TODO Check if we still needs to implement all getters, equals, toString if we dont need it

  // TODO To handle the case where --email-template is NOT provided but we call the respective function

  public Boolean hasEmailOption() {
    return hasEmailOption;
  }

  public String getEmailTemplate() {
    return options.get(OPTION_EMAIL_TEMPLATE);
  }

  public Boolean hasLetterOption() {
    return hasLetterOption;
  }

  public String getLetterTemplate() {
    return options.get(OPTION_LETTER_TEMPLATE);
  }

  public String getOutputDir() {
    return options.get(OPTION_OUTPUT_DIR);
  }

  public String getCsvFileDir() {
    return options.get(OPTION_CSV_FILE);
  }
}





