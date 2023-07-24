package autopopulate_templates;

import java.util.Objects;

public class Option {

  private String option;
  private String template;
  private String outputDir;
  private String csvFile;

  public Option(String option, String template, String outputDir, String csvFile) {
    this.option = option;
    this.template = template;
    this.outputDir = outputDir;
    this.csvFile = csvFile;
  }

  public String getOption() {
    return option;
  }

  public String getTemplate() {
    return template;
  }

  public String getOutputDir() {
    return outputDir;
  }

  public String getCsvFile() {
    return csvFile;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Option option1 = (Option) o;
    return Objects.equals(option, option1.option) && Objects.equals(template,
        option1.template) && Objects.equals(outputDir, option1.outputDir)
        && Objects.equals(csvFile, option1.csvFile);
  }

  @Override
  public int hashCode() {
    return Objects.hash(option, template, outputDir, csvFile);
  }

  @Override
  public String toString() {
    return "Option{" +
        "option='" + option + '\'' +
        ", template='" + template + '\'' +
        ", outputDir='" + outputDir + '\'' +
        ", csvFile='" + csvFile + '\'' +
        '}';
  }
}
