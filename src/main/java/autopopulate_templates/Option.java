package autopopulate_templates;

import java.util.Objects;

/**
 * Represents a command line option as parsed by the CommandLineParser. An Option object stores all
 * the necessary information for further processing a specific template, including: (1) the option
 * as a string, (2) the path to the csv database, (3) the path to the txt template, and (4) the
 * targeted output directory.
 */
public class Option {

  private String optionStr; // string to prefix filename
  private String csvFilePath;
  private String templatePath;
  private String outputDir;

  /**
   * Constructs a new Option object.
   *
   * @param optionStr    the option type as a String, to be prefixed to output files
   * @param csvFilePath  the path to the CSV database file containing customer information
   * @param templatePath the path to the txt template
   * @param outputDir    the path to the directory where the output files will be saved
   */
  public Option(String optionStr, String csvFilePath, String templatePath, String outputDir) {
    this.optionStr = optionStr;
    this.csvFilePath = csvFilePath;
    this.templatePath = templatePath;
    this.outputDir = outputDir;
  }

  /**
   * Returns the option type which is to be prefixed to output files.
   *
   * @return the option type as a String
   */
  public String getOptionStr() {
    return optionStr;
  }

  /**
   * Returns the path to the CSV database file containing customer information.
   *
   * @return the path to the CSV database file as a String
   */
  public String getCsvFilePath() {
    return csvFilePath;
  }

  /**
   * Returns the path to the txt template.
   *
   * @return the path to the txt template as a String
   */
  public String getTemplatePath() {
    return templatePath;
  }

  /**
   * Returns the path to the output directory.
   *
   * @return the path to the output directory as a String
   */
  public String getOutputDir() {
    return outputDir;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Option option = (Option) o;
    return Objects.equals(optionStr, option.optionStr) && Objects.equals(
        csvFilePath, option.csvFilePath) && Objects.equals(templatePath,
        option.templatePath) && Objects.equals(outputDir, option.outputDir);
  }

  @Override
  public int hashCode() {
    return Objects.hash(optionStr, csvFilePath, templatePath, outputDir);
  }

  @Override
  public String toString() {
    return "Option{" +
        "optionStr='" + optionStr + '\'' +
        ", csvFilePath='" + csvFilePath + '\'' +
        ", templatePath='" + templatePath + '\'' +
        ", outputDir='" + outputDir + '\'' +
        '}';
  }
}
