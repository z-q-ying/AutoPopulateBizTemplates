package autopopulate_templates;

public class Main {

  public static void main(String[] args) {
    try {
      // TODO: Parse command line arguments and handle errors
      CommandLineParser parser = new CommandLineParser(args);
      // TODO: Read the template file
      // TODO: Read the CSV file
      // TODO: Process templates and generate output files
    } catch (Exception e) {
      System.out.println("ERROR: " + e.getMessage());
    }
  }
}
