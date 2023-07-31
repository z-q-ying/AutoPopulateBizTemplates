package autopopulate_templates;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class TemplateReader stores all information of template path, the set of headers from CSV data,
 * and would save the screened template in a string builder.
 */
public class TemplateReader {

  private String templatePath;
  private String templateStr;

  protected static final String PLACEHOLDER_PATTERN = "\\[\\[(\\w+)\\]\\]"; // recognize [[]]
  private static final String SUBSTRING_LEFT = "[[";
  private static final String SUBSTRING_RIGHT = "]]";
  private static final Integer LEN_OF_SUBSTRING = 2;

  /**
   * Constructor for reader taking in template path, and a set of headers from csv file.
   *
   * @param template_path as String.
   * @param headers       as Set.
   */
  public TemplateReader(String template_path, Set<String> headers)
      throws TemplateInformationNotMatchException, IOException {
    this.templatePath = template_path;
    this.readTemplate(headers);
  }

  /**
   * Get the path of the template as a String.
   *
   * @return template path as a String.
   */
  public String getTemplatePath() {
    return templatePath;
  }

  /**
   * Get the screened template for TemplateWriter use.
   *
   * @return template as a String.
   */
  public String getTemplateStr() {
    return templateStr;
  }

  /**
   * Read template to check whether the keywords that screened by regular expression in template can
   * match to the header of set in csv data.
   *
   * @throws TemplateInformationNotMatchException throws when the keywords in template failed to
   *                                              match to the header in set.
   */
  public void readTemplate(Set<String> headers)
      throws TemplateInformationNotMatchException, IOException {
    StringBuilder templateStringBuilder = new StringBuilder();
    try (BufferedReader reader = new BufferedReader(new FileReader(this.templatePath))) {
      String line;
      Pattern pattern = Pattern.compile(PLACEHOLDER_PATTERN); //give pattern
      while ((line = reader.readLine()) != null) {
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) { // check whether keywords to replace can match headers
          String match = matcher.group();
          match = this.subString(match); // from [[email]] to email
          if (!headers.contains(match)) { // if keywords not in headers, read terminates and create a new file failed
            throw new TemplateInformationNotMatchException("The key words '" + match + "' to replace in template not found in the given csv file.");
          }
        }
        templateStringBuilder.append(line + "\n"); // add valid line into string builder
      }
      this.templateStr = templateStringBuilder.toString(); // convert to string
    } catch (FileNotFoundException e) {
      throw new FileNotFoundException("Directory not found: " + this.templatePath);
    } catch (IOException e) {
      throw new IOException("Error in reading the file: " + this.templatePath);
    }
  }

  /**
   * Return substring of given string taking in start index and end index, start index inclusive and
   * end index exclusive.
   *
   * @param str provided string as String.
   * @return substring of provided string as String.
   */
  protected static String subString(String str) {
    int startIndex = str.indexOf(SUBSTRING_LEFT) + LEN_OF_SUBSTRING;
    int endIndex = str.indexOf(SUBSTRING_RIGHT);
    return str.substring(startIndex, endIndex);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TemplateReader that = (TemplateReader) o;
    return Objects.equals(templatePath, that.templatePath) && Objects.equals(
        templateStr, that.templateStr);
  }

  @Override
  public int hashCode() {
    return Objects.hash(templatePath, templateStr);
  }

  @Override
  public String toString() {
    return "TemplateReader{" +
        "templatePath='" + templatePath + '\'' +
        ", templateStr='" + templateStr + '\'' +
        '}';
  }
}
