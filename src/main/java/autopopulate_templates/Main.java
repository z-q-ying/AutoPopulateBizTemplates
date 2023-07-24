package autopopulate_templates;

public class Main {

  public static void main(String[] args) {

    try {
      CommandLineParser parser = new CommandLineParser(args);

      for (Option option : parser.getOptionList()) {
        System.out.println(option);
        String template = option.getTemplate();
        String csvFile = option.getCsvFile();
        String outputDir = option.getOutputDir();
      }
    } catch (InvalidArgumentsException e) {
      System.out.println(e.getMessage());
    } finally {
      System.out.println("HW8 All Done, Hooray!");
    }
  }
}
