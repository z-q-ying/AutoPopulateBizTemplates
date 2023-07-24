package autopopulate_templates;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CsvFileProcessorTest {

  private String filePath = "./src/main/resources/csv-processor-test.csv";
  private CsvFileProcessor csvFileProcessor;

  Map<String, String> expectedResult1;
  Map<String, String> expectedResult2;
  Map<String, String> expectedResult3;

  @BeforeEach
  void setUp() throws IOException {
    filePath = "./src/main/resources/csv-processor-test.csv";
    csvFileProcessor = new CsvFileProcessor(filePath);

    expectedResult1 = new HashMap<>();
    expectedResult1.put("first_name", "James");
    expectedResult1.put("last_name", "Butt");
    expectedResult1.put("company_name", "Benton, John B Jr");
    expectedResult1.put("address", "6649 N Blue Gum St");
    expectedResult1.put("state", "LA");
    expectedResult1.put("phone1", "504-621-8927");
    expectedResult1.put("email", "jbutt@gmail.com");

    expectedResult2 = new HashMap<>();
    expectedResult2.put("first_name", "Josephine");
    expectedResult2.put("last_name", "Darakjy");
    expectedResult2.put("company_name", "Chanay, Jeffrey A Esq");
    expectedResult2.put("address", "4 B Blue Ridge Blvd");
    expectedResult2.put("state", "MI");
    expectedResult2.put("phone1", "810-292-9388");
    expectedResult2.put("email", "josephine_darakjy@darakjy.org");

    expectedResult3 = new HashMap<>();
    expectedResult3.put("first_name", "Art");
    expectedResult3.put("last_name", "Venere");
    expectedResult3.put("company_name", "Chemel, James L Cpa");
    expectedResult3.put("address", "8 W Cerritos Ave #54");
    expectedResult3.put("state", "NJ");
    expectedResult3.put("phone1", "856-636-8749");
    expectedResult3.put("email", "art@venere.org");
  }

  @Test
  public void getFirstElement() {
    assertEquals(expectedResult1, csvFileProcessor.getCsvData().get(0));
  }

  @Test
  public void getSecondElement() {
    assertEquals(expectedResult2, csvFileProcessor.getCsvData().get(1));
  }

  @Test
  public void getThirdElement() {
    assertEquals(expectedResult3, csvFileProcessor.getCsvData().get(2));
  }

  @Test
  public void getFourthElement() {
    assertThrows(IndexOutOfBoundsException.class, () -> csvFileProcessor.getCsvData().get(3));
  }

  @Test
  public void testToString() {
    List<Map<String, String>> resultList = new ArrayList<>();
    resultList.add(expectedResult1);
    resultList.add(expectedResult2);
    resultList.add(expectedResult3);
    String expectedString = "CsvFileProcessor{" +
        "csvData=" + resultList +
        '}';
    assertEquals(expectedString, csvFileProcessor.toString());
  }
}