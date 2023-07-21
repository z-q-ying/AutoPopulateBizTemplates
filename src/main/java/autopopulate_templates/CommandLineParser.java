package autopopulate_templates;

public class CommandLineParser {

  /**
   * TODO
   * <p>
   * Also note that some options require other options to also be present. For example, if the
   * program is run with the --email option, then the --email-template option (with it’s required
   * argument) must also be provided. Calling your program with invalid combinations of arguments
   * should generate an error. For example, the following command is illegal because it contains --
   * email but --email-template is not provided:
   * <p>
   * --email --letter-template letter-template.txt --output-dir letters/
   */

  private static final String OPTION_EMAIL = "--email";
  private static final String OPTION_EMAIL_TEMPLATE = "--email-template"; // --email-template <path/to/file>
  private static final String OPTION_LETTER = "--letter";
  private static final String OPTION_LETTER_TEMPLATE = "--letter-template"; // -letter-template <path/to/file
  private static final String OPTION_OUTPUT_DIR = "--output-dir"; // --output-dir <path/to/folder>
  private static final String OPTION_CSV_FILE = "--csv-file"; // --csv-file <path/to/file>

}
