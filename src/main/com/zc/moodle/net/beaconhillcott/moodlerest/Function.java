
package main.com.zc.moodle.net.beaconhillcott.moodlerest;

/**
 * Class to store the name and version details of a Moodle webservice.
 *
 * @author Bill Antonia
 */
public class Function {
  private String name=null;
  private double version;

  /**
   * Constructor for bean requirements
   */
  public Function() {}

  /**
   * Constructor for Moodle webservice function
   *
   * @param name String
   * @param version double
   */
  public Function(String name, double version) {
      this.name=name;
      this.version=version;
  }

  /**
   * Method to return the name of the webservice function
   *
   * @return String
   */
  public String getName() {
      return name;
  }

  /**
   * Method to return the version of the webservice function
   *
   * @return double
   */
  public double getVersion() {
      return version;
  }

  /**
   * Method to set the name of a webservice function within the object
   *
   * @param name
   */
  public void setName(String name) {
      this.name=name;
  }
  
  /**
   * Method to set the version of a webservice function within the object
   *
   * @param version double
   */
  public void setVersion(double version) {
      this.version=version;
  }

  /**
   * Method to set a field given its field name and value as Strings, the value for the "version" field is parsed to a double within the method.
   * 
   * @param field String
   * @param value String
   */
  public void setFunctionField(String field, String value) {
      if (field.equals("name") && !value.equals("")) setName(value);
      if (field.equals("version") && !value.equals("")) setVersion(Double.parseDouble(value));
  }
}
