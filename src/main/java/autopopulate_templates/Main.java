package autopopulate_templates;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {

  public static void main(String[] args) {

    try {
      CommandLineParser parser = new CommandLineParser(args);

      for (Option option : parser.getOptionList()) {
        System.out.println(option);

        // Get the template, database and output directory
        String csvFile = option.getCsvFile();
        String templateDir = option.getTemplate();
        String outputDir = option.getOutputDir();

        // Process the database
        CsvFileProcessor csvFileProcessor = new CsvFileProcessor(csvFile);

        // read and process the template
        TemplateProcessor templateProcessor = new TemplateProcessor(option.getOption(), csvFileProcessor.getCsvData(), templateDir, outputDir);
        templateProcessor.generateOutput();

        // populate the output
      }
    } catch (InvalidArgumentsException e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    } catch (FileNotFoundException e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    } catch (IOException e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    } finally {
      System.out.println("HW8 All Done, Hooray!");
    }
  }
}
