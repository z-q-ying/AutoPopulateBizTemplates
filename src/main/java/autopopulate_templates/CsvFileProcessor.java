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
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class is responsible for reading and processing CSV files. It extracts data from the CSV
 * file and stores it as a list of maps, where each map represents a row in the CSV file with
 * headers as keys and corresponding cell values as values.
 * <p>
 * All methods in this class is private. Client(Main) only needs to call constructor with
 * csvFilePath as input to finish all tasks.
 */
public class CsvFileProcessor {

  private static final String CSV_PATTERN = "\"([^\"]*)\"|(?<=,|^)([^,]*)(?=,|$)"; // to match csv values

  private List<Map<String, String>> csvData;

  /**
   * Constructor for CsvFileProcessor.
   *
   * @param csvFilePath The path of the CSV file to be processed.
   * @throws IOException If there is an error reading the CSV file.
   */
  public CsvFileProcessor(String csvFilePath) throws IOException {
    this.csvData = new ArrayList<>();
    this.readAndProcessFile(csvFilePath);
  }

  /**
   * Getter for field csvData, as an ArrayList of maps.
   *
   * @return A list of maps, where each map represents a row in the CSV file with headers as keys
   * and corresponding cell values as values.
   */
  public List<Map<String, String>> getCsvData() {
    return csvData;
  }

  /**
   * Getter for field headersOfCsv, as a HashSet of String.
   *
   * @return a HashSet of String representing the headers
   */
  public Set<String> getHeadersOfCsv() {
    return this.csvData.get(0).keySet();
  }

  /**
   * Read and process the CSV file, populating the data into a list of maps.
   *
   * @param csvFilePath The path of the CSV file to be processed.
   * @throws IOException If there is an error reading the CSV file.
   */
  private void readAndProcessFile(String csvFilePath) throws IOException {
    try (BufferedReader bufferedReader =
        new BufferedReader(new FileReader(csvFilePath))) {

      // process headers, edge case of empty file already included
      String headerLine = bufferedReader.readLine();
      if (headerLine == null) {
        bufferedReader.close();
        throw new IOException("The provided CSV file is empty!");
      }
      String[] headers = this.getSeperatedValues(headerLine);

      // process subsequent rows
      String line;
      while ((line = bufferedReader.readLine()) != null) {
        // skip empty lines or lines that only contain whitespace
        if (line.trim().isEmpty()) {continue;}

        // process a valid line with values
        String[] values = this.getSeperatedValues(line);
        this.populateMap(headers, values);
      }

    } catch (FileNotFoundException e) {
      throw new FileNotFoundException("File not found: " + csvFilePath);
    } catch (IOException e) {
      throw new IOException(
          "Error reading the csv file: " + csvFilePath + " Error Message: " + e.getMessage());
    }
  }

  /**
   * Helper method to generate a map with headers and values.
   *
   * @param headers The array of headers extracted from the CSV file using getSeperatedValues().
   * @param values  The array of values extracted from a CSV line using getSeperatedValues().
   * @throws IllegalArgumentException If the number of headers and values do not match.
   */
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

  /**
   * A helper method to extract info from a CSV String line and turn into an array of Strings based
   * on the defined regex CSV_PATTERN.
   *
   * @param line The CSV line to be processed.
   * @return An array of values extracted from the CSV line.
   */
  private String[] getSeperatedValues(String line) {
    // list to store the extracted value
    List<String> values = new ArrayList<>();

    // create a pattern and a matcher
    Pattern pattern = Pattern.compile(CSV_PATTERN);
    Matcher matcher = pattern.matcher(line);

    // find and add each element to the list
    while (matcher.find()) {
      String value = matcher.group().trim();

      //  if it's an empty string, skip this line
      if (value == null || value.isEmpty()) {
        continue;
      }

      // remove double quote if any
      if (value.startsWith("\"") && value.endsWith("\"")) {
        value = value.substring(1, value.length() - 1);
      }
      values.add(value);
    }

    // return the list of extracted values
    return values.toArray(new String[0]);
  }

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