package autopopulate_templates;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TemplateProcessor {

  private String template;
  private String outputDir;
  private List<Map<String, String>> csvData;

  private static final String PLACEHOLDER_PATTERN = "\\[\\[(\\w+)\\]\\]";
  private static final String SUBSTRING_LEFT = "[[";
  private static final String SUBSTRING_RIGHT = "]]";
  private static final Integer LEN_OF_SUBSTRING = 2;

  public TemplateProcessor(List<Map<String, String>> csvData, String template, String outputDir) {
    this.template = template;
    this.outputDir = outputDir;
    this.csvData = csvData;
  }

  public void generateOutput() throws IOException {
    String template = readTemplate(this.template);
    Integer fileNameCounter = 1;
    for (Map<String, String> row : this.csvData) {
      String output = replacePlaceholders(template, row);
      String fileName = this.outputDir + Integer.toString(fileNameCounter) + ".txt"; // no duplicate
      writeToFile(fileName, output);
      fileNameCounter++;
    }
  }

  private String replacePlaceholders(String template, Map<String, String> row) {
    // creates a deep copy of the template (as the template needs to be reused and shall not be modified)
    String templateCopy = new String(template);
    Pattern pattern = Pattern.compile(PLACEHOLDER_PATTERN);
    Matcher matcher = pattern.matcher(templateCopy);

    while (matcher.find()) {
      String placeholder = matcher.group();
      String key = subString(placeholder);
      if (row.containsKey(key)) {
        templateCopy = templateCopy.replace(placeholder, row.get(key));
      } else {
        throw new NoSuchElementException("Placeholder key " + key + " not found!");
      }
    }
    return templateCopy;
  }

  private static String subString(String str) {
    int startIndex = str.indexOf(SUBSTRING_LEFT) + LEN_OF_SUBSTRING;
    int endIndex = str.indexOf(SUBSTRING_RIGHT);
    return str.substring(startIndex, endIndex);
  }

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

  private String readTemplate(String template) throws IOException {
    String result = ""; // TODO Can we use StringBuilder?
    try (BufferedReader bf = new BufferedReader(new FileReader(this.template))) {
      String line;
      while ((line = bf.readLine()) != null) {
        result = result + line + "\n";
      }
    } catch (FileNotFoundException e) {
      throw new FileNotFoundException("File not found: " + this.template);
    } catch (IOException e) {
      throw new IOException("Error reading the template file: " + this.template);
    }
    return result;
  }
}
