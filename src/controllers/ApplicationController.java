package apes.controllers;

/**
 * This class contains stuff common to all controllers. All
 * controllers, other than {@link ActionController ActionController},
 * should extend this class.
 *
 * @author Johan Andersson (johandy@student.chalmers.se)
 */
public class ApplicationController extends ActionController
{
  /**
   * If there's an event connected that is to be called by the
   * component name and there's no method with the same name, this
   * method is called. Override it in your controllers to implement a
   * special behavior in these cases.
   */
  public void methodMissing() {}
}
