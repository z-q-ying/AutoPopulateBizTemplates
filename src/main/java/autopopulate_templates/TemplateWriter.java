package autopopulate_templates;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class TemplateWriter stores all information about screened template, a list of maps that contains
 * all clients' information, and output path to save emails or letters.
 */
public class TemplateWriter {

  private String option;
  private String outputDir;
  private String tempString;
  private List<Map<String, String>> csvData;

  /**
   * Constructor TemplateWriter taking in screened template, a list of maps that contains all
   * clients' information, and output path to save emails or letters.
   *
   * @param tempString as StringBuilder.
   * @param csvData    as List of Maps.
   * @param outputDir  path of output as String.
   */
  public TemplateWriter(String tempString, List<Map<String, String>> csvData, String option,
      String outputDir) {
    this.tempString = tempString;
    this.csvData = csvData;
    this.option = option;
    this.outputDir = outputDir;
  }

  /**
   * Generate output files to assigned location.
   *
   * @throws IOException throws when failed in writing to file.
   */
  public void generateOutput() throws IOException {
    Integer fileNameCounter = 1;
    for (Map<String, String> row : this.csvData) {
      String output = replacePlaceholders(row);
      String fileName =
          this.outputDir + this.option + "-" + fileNameCounter + ".txt";
      writeToFile(fileName, output);
      fileNameCounter++;
    }
  }

  /**
   * Replace placeholders in template and return a copy of template.
   *
   * @param row each map in csv data list as Map.
   * @return copy of template as String.
   */
  private String replacePlaceholders(Map<String, String> row) {
    String templateCopy = new String(this.tempString);
    Pattern pattern = Pattern.compile(TemplateReader.PLACEHOLDER_PATTERN);
    Matcher matcher = pattern.matcher(templateCopy);
    while (matcher.find()) {
      String placeholder = matcher.group();
      String key = TemplateReader.subString(placeholder); // removing [[]]
      templateCopy = templateCopy.replace(placeholder, row.get(key));
    }
    return templateCopy;
  }

  /**
   * Write a file taking a file name and output path.
   *
   * @param fileName as String.
   * @param output   as String.
   * @throws IOException throws when there is an error in writing file.
   */
  private void writeToFile(String fileName, String output) throws IOException {
    try {
      FileWriter fileWriter = new FileWriter(fileName);
      fileWriter.write(output);
      fileWriter.close();
    } catch (FileNotFoundException e) {
      throw new FileNotFoundException("Directory not found: " + fileName);
    } catch (IOException e) {
      throw new IOException("Error writing to file: " + fileName);
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
    TemplateWriter that = (TemplateWriter) o;
    return Objects.equals(option, that.option) && Objects.equals(outputDir,
        that.outputDir) && Objects.equals(tempString, that.tempString)
        && Objects.equals(csvData, that.csvData);
  }

  @Override
  public int hashCode() {
    return Objects.hash(option, outputDir, tempString, csvData);
  }

  @Override
  public String toString() {
    return "TemplateWriter{" +
        "option='" + option + '\'' +
        ", outputDir='" + outputDir + '\'' +
        ", tempString='" + tempString + '\'' +
        ", csvData=" + csvData +
        '}';
  }
}
