package autopopulate_templates;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TemplateProcessorTest {

  private CsvFileProcessor csvProcessor;
  private List<Map<String, String>> csvDatabase;
  private TemplateProcessor testTemplateProcessor;

  @BeforeEach
  void setUp() throws IOException {
    String filePath = "./src/main/resources/csv-processor-test.csv";
    String templateDir = "./src/main/resources/small-template.txt";
    String outputDir = "./src/main/resources/test-output";
    csvProcessor = new CsvFileProcessor(filePath);
    csvDatabase = csvProcessor.getCsvData();
    testTemplateProcessor = new TemplateProcessor(csvDatabase, templateDir, outputDir);
  }

  @Test
  void getCsvDataSize() {
    assertEquals(3, csvDatabase.size());
  }

  @Test
  void generateOutput() throws IOException {
    testTemplateProcessor.generateOutput();
  }
}