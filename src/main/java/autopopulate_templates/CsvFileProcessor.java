package autopopulate_templates;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CsvFileProcessor {

  //private static final String CSV_PATTERN = "\"([^\"]+)\""; // recognize a non-empty string in ""
  private static final String CSV_PATTERN = "\"([^\"]*)\"|(?<=,|^)([^,]*)(?=,|$)"; // recognize a non-empty string in ""
  private static final String EMAIL_FILE_IDENTIFIER = "email";
  private static final String LETTER_FILE_IDENTIFIER = "letter";

  private List<Map<String, String>> csvData;

  public CsvFileProcessor(String csvFilePath) throws IOException {
    csvData = new ArrayList<>();
    this.readAndProcessFile(csvFilePath);
  }

  public List<Map<String, String>> getCsvData() {
    return csvData;
  }

  private void readAndProcessFile(String csvFilePath) throws IOException {
    try {
      FileReader fileReader = new FileReader(csvFilePath);
      BufferedReader bufferedReader = new BufferedReader(fileReader);

      // process headers
      String headerLine = bufferedReader.readLine();
      if (headerLine == null) {
        bufferedReader.close();
        throw new IOException("The provided CSV file is empty!");
      }
      String[] headers = this.getSeperatedValues(headerLine);

      // process subsequent rows
      String line;
      while ((line = bufferedReader.readLine()) != null) {
        String[] values = this.getSeperatedValues(line);
        this.populateMap(headers, values);
      }

      // Close the resources
      bufferedReader.close();
    } catch (FileNotFoundException e) {
      throw new FileNotFoundException("File not found: " + csvFilePath);
    } catch (IOException e) {
      throw new IOException("Error reading the csv file: " + csvFilePath);
    }
  }

  private void populateMap(String[] headers, String[] values) {
    if (headers.length != values.length) {
      throw new IllegalArgumentException("Number of headers and values do not match!");
    }
    Map<String, String> lineToMap = new HashMap<>();
    for (int i = 0; i < headers.length; i++) {
      lineToMap.put(headers[i], values[i]);
    }
    csvData.add(lineToMap);
  }

  private String[] getSeperatedValues(String line) {
    // list to store the extracted value
    List<String> values = new ArrayList<>();

    // create a pattern and a matcher
    Pattern pattern = Pattern.compile(CSV_PATTERN);
    Matcher matcher = pattern.matcher(line);

    // find and add each element to the list
    while (matcher.find()) {
      String value = matcher.group().trim();

      // TODO shall we deal with empty string

      // remove double quote if any
      if (value.startsWith("\"") && value.endsWith("\"")) {
        value = value.substring(1, value.length() - 1);
      }
      values.add(value);
    }

    // return the list of extracted values
    return values.toArray(new String[0]);
  }

  // TODO Do I need to implement equals, hashCode, and toString methods as they are no needed here?

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CsvFileProcessor that = (CsvFileProcessor) o;
    return Objects.equals(csvData, that.csvData);
  }

  @Override
  public int hashCode() {
    return Objects.hash(csvData);
  }

  @Override
  public String toString() {
    return "CsvFileProcessor{" +
        "csvData=" + csvData +
        '}';
  }
}