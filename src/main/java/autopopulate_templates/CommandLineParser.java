package autopopulate_templates;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Parses command-line arguments into a list of Option objects.
 * <p>
 * This class is responsible for parsing and validating command-line arguments. It checks the
 * provided options and their values, throws an IllegalArgumentException with proper error message
 * if any argument is invalid or missing, and generates a list of Option objects for further
 * processing.
 */
public class CommandLineParser {

  private static final String GENERIC_ERROR_MSG =
      "\nUsage:\n"
          + "--email                           Generate email messages. If this option is provided, then --email-template must also be provided.\n"
          + "--email-template <path/to/file>   A filename for the email template.\n"
          + "--letter                          Generate letters. If this option is provided, then --letter-template must also be provided.\n"
          + "--letter-template <path/to/file>  A filename for the letter template.\n"
          + "--output-dir <path/to/folder>     The folder to store all generated files. This option is required.\n"
          + "--csv-file <path/to/folder>       The CSV file to process. This option is required.\n"
          + "\nExamples:\n"
          + "--email --email-template email-template.txt --output-dir ./emails --csv-file customer.csv\n"
          + "--letter --letter-template letter-template.txt --output-dir ./letters --csv-file customer.csv";

  private static final String OPTION_EMAIL = "--email";
  private static final String OPTION_EMAIL_TEMPLATE = "--email-template";
  private static final String OPTION_LETTER = "--letter";
  private static final String OPTION_LETTER_TEMPLATE = "--letter-template";
  private static final String OPTION_OUTPUT_DIR = "--output-dir";
  private static final String OPTION_CSV_FILE = "--csv-file";
  private static final String[] ALL_CL_OPTIONS = {OPTION_EMAIL, OPTION_EMAIL_TEMPLATE,
      OPTION_LETTER, OPTION_LETTER_TEMPLATE, OPTION_OUTPUT_DIR, OPTION_CSV_FILE};
  private static final String OPTION_EMAIL_SUBSTRING = "email"; // string to prefix filename
  private static final String OPTION_LETTER_SUBSTRING = "letter"; // string to prefix filename
  private ArrayList<Option> optionsList; // accessible to user i.e. getter available

  /**
   * Constructs a new CommandLineParser object with the provided command line arguments.
   *
   * @param args the command-line arguments as an array of String
   * @throws IllegalArgumentException if the provided arguments are invalid or incomplete
   */
  public CommandLineParser(String[] args) throws IllegalArgumentException {
    optionsList = new ArrayList<>();
    this.processCommandLineArgs(args);
  }

  /**
   * Returns the list of Option objects generated from the command-line arguments.
   *
   * @return an ArrayList of Option objects
   */
  public ArrayList<Option> getOptionsList() {
    return optionsList;
  }

  /**
   * Key step to process command-line arguments by parsing, validating, and creating Option list.
   *
   * @param args the command-line arguments as an array of String
   * @throws IllegalArgumentException if any argument is invalid
   */
  private void processCommandLineArgs(String[] args) throws IllegalArgumentException {
    Map<String, String> optionMap = new HashMap<>(); // temporarily store <option, value> pairs
    this.parseArguments(args, optionMap); // parse CL and store in a map
    this.validateArguments(optionMap); // validate whether the option combo are valid
    this.populateOptionList(optionMap); // general the list of Options
  }

  /**
   * Helper function to parses the command-line arguments into a Map.
   *
   * @param args   the command-line arguments as an array of String
   * @param optMap a Map to store the option-value pairs
   * @throws IllegalArgumentException if an unknown option is encountered
   */
  private void parseArguments(String[] args, Map<String, String> optMap)
      throws IllegalArgumentException {
    for (int i = 0; i < args.length; i++) {
      String option = args[i];
      switch (option) {
        case OPTION_EMAIL:
          optMap.put(OPTION_EMAIL, OPTION_EMAIL_SUBSTRING);
          break;
        case OPTION_LETTER:
          optMap.put(OPTION_LETTER, OPTION_LETTER_SUBSTRING);
          break;
        case OPTION_EMAIL_TEMPLATE:
        case OPTION_LETTER_TEMPLATE:
        case OPTION_OUTPUT_DIR:
        case OPTION_CSV_FILE:
          parseArgumentsHelper(args, option, i, optMap);
          i++;
          break;
        default:
          throw new IllegalArgumentException("Unknown option: " + option);
      }
    }
  }

  /**
   * Helps parseArguments method by storing value associated with an option into the Map.
   *
   * @param args   the command-line arguments as an array of String
   * @param option the current option being processed, encoded as a String
   * @param index  the current index of the option in args
   * @param optMap a Map to store the option-value pairs
   * @throws IllegalArgumentException if no value is given for the option
   */
  private void parseArgumentsHelper(String[] args, String option, int index,
      Map<String, String> optMap) throws IllegalArgumentException {
    if (index + 1 < args.length && this.nextArgIsValid(args[index + 1])) {
      optMap.put(option, args[index + 1]);
    } else {
      throw new IllegalArgumentException(option + " provided but no value was given.");
    }
  }

  /**
   * Helper function to check if the given argument is a valid value.
   *
   * @param arg the argument to be checked, encoded as a String
   * @return true if arg is a value and not an option, false otherwise
   */
  private Boolean nextArgIsValid(String arg) { // nextArg should not be option; it should be value
    for (String s : ALL_CL_OPTIONS) {
      if (s.equals(arg)) {
        return Boolean.FALSE;
      }
    }
    return Boolean.TRUE;
  }

  /**
   * Validates the parsed command-line options stored in the Map.
   *
   * @param optMap a Map containing the parsed options
   * @throws IllegalArgumentException if required options are missing or have no associated value
   */
  private void validateArguments(Map<String, String> optMap)
      throws IllegalArgumentException {
    if (!optMap.containsKey(OPTION_EMAIL) && !optMap.containsKey(OPTION_LETTER)) {
      String errMsg = "Either --email or --letter option must be provided.\n" + GENERIC_ERROR_MSG;
      throw new IllegalArgumentException(errMsg);
    }
    if (optMap.containsKey(OPTION_EMAIL) && !optMap.containsKey(OPTION_EMAIL_TEMPLATE)) {
      String errMsg =
          "Option --email provided but no --email-template was given.\n" + GENERIC_ERROR_MSG;
      throw new IllegalArgumentException(errMsg);
    }
    if (!optMap.containsKey(OPTION_EMAIL) && optMap.containsKey(OPTION_EMAIL_TEMPLATE)) {
      String errMsg =
          "Option --email-template provided but no --email option was given.\n" + GENERIC_ERROR_MSG;
      throw new IllegalArgumentException(errMsg);
    }
    if (optMap.containsKey(OPTION_LETTER) && !optMap.containsKey(OPTION_LETTER_TEMPLATE)) {
      String errMsg =
          "Option --letter provided but no --letter-template was given.\n" + GENERIC_ERROR_MSG;
      throw new IllegalArgumentException(errMsg);
    }
    if (!optMap.containsKey(OPTION_LETTER) && optMap.containsKey(OPTION_LETTER_TEMPLATE)) {
      String errMsg = "Option --letter-template provided but no --letter option was given.\n"
          + GENERIC_ERROR_MSG;
      throw new IllegalArgumentException(errMsg);
    }
    if (!optMap.containsKey(OPTION_CSV_FILE)) {
      String errMsg = "Option --csv-file must be provided.\n" + GENERIC_ERROR_MSG;
      throw new IllegalArgumentException(errMsg);
    }
    if (!optMap.containsKey(OPTION_OUTPUT_DIR)) {
      String errMsg = "Option --output-dir must be provided.\n" + GENERIC_ERROR_MSG;
      throw new IllegalArgumentException(errMsg);
    }
  }

  /**
   * Populates the optionsList with Option objects created from the validated options.
   *
   * @param optMap a Map containing the validated options
   */
  private void populateOptionList(Map<String, String> optMap) {
    if (optMap.containsKey(OPTION_EMAIL)) {
      optionsList.add(
          new Option(optMap.get(OPTION_EMAIL), optMap.get(OPTION_CSV_FILE),
              optMap.get(OPTION_EMAIL_TEMPLATE), optMap.get(OPTION_OUTPUT_DIR)));
    }
    if (optMap.containsKey(OPTION_LETTER)) {
      optionsList.add(
          new Option(optMap.get(OPTION_LETTER), optMap.get(OPTION_CSV_FILE),
              optMap.get(OPTION_LETTER_TEMPLATE), optMap.get(OPTION_OUTPUT_DIR)));
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CommandLineParser parser = (CommandLineParser) o;
    return Objects.equals(optionsList, parser.optionsList);
  }

  @Override
  public int hashCode() {
    return Objects.hash(optionsList);
  }

  @Override
  public String toString() {
    return "CommandLineParser{" +
        "optionsList=" + optionsList +
        '}';
  }
}





