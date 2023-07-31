package autopopulate_templates;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * The MainFileIO class provides a main method for taking arguments from command line, processing
 * CSV files, reading templates, and generating emails or letters based on a given template. The
 * processing steps include parsing command line options, reading from a CSV file, screening the
 * template, and finally writing the output.
 * <p>
 * This class catches and handles various exceptions including IllegalArgumentException,
 * FileNotFoundException, IOException, and TemplateInformationNotMatchException. The relevant error
 * messages are printed to the console when these exceptions occur.
 * <p>
 * Sample CL:
 * --email --email-template ./src/main/resources/email-template.txt
 * --letter --letter-template ./src/main/resources/letter-template.txt
 * --csv-file ./src/main/resources/insurance-company-members.csv
 * --output-dir ./src/main/resources/output/
 */
public class MainFileIO {

  /**
   * The entry point of application.
   *
   * @param args the input arguments from command line, which should include options and associated
   *             paths for CSV file, template, and output directory.
   */
  public static void main(String[] args) {
    try {
      // parser the command line arguments and store as a list of option(s)
      CommandLineParser parser = new CommandLineParser(args);

      // for each option in the parser
      for (Option option : parser.getOptionsList()) {

        // process the database
        CsvFileProcessor csvFileProcessor = new CsvFileProcessor(option.getCsvFilePath());

        // read and screen template
        TemplateReader templateReader = new TemplateReader(option.getTemplatePath(),
            csvFileProcessor.getHeadersOfCsv());

        // write and output emails and letters
        TemplateWriter templateWriter = new TemplateWriter(templateReader.getTemplateStr(),
            csvFileProcessor.getCsvData(), option.getOptionStr(), option.getOutputDir());
        templateWriter.generateOutput();
      }
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    } catch (FileNotFoundException e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    } catch (IOException e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    } catch (TemplateInformationNotMatchException e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    }
  }
}

