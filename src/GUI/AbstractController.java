package GUI;

/**
 * Abstract class to collect all the controller classes and easily refresh them
 * @version 1
 * @author Greg
 */
public abstract class   AbstractController
{
  /**
   * Refreshes the current Tab
   */
  public abstract void refresh();
  /**
   * Initialize the current Tab
   */
  public abstract void initialize();
}
