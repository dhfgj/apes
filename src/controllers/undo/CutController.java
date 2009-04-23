package apes.controllers.undo;

import apes.controllers.ApplicationController;
import apes.models.undo.CutEdit;
import apes.views.InternalFormatView;
import javax.swing.undo.UndoManager;

/**
 * Cut action.
 *
 * @author Johan Andersson (johandy@student.chalmers.se)
 * @author Johan Åhlander (johan.ahlander@gmail.com)
 */
public class CutController extends ApplicationController
{
 /**
  * The view over the internal format.
  */
  private InternalFormatView internalFormatView;
  
  /**
   * Cut edit model.
   */
  private CutEdit cutEdit;
  
  /**
   * The undo manager that keeps track of all changes.
   */
  private UndoManager undoManager;

  public CutController( UndoManager undoManager, InternalFormatView internalFormatView )
  {
    this.internalFormatView = internalFormatView;
    this.cutEdit = new CutEdit();
    this.undoManager = undoManager;
  }
  
  /**
   *
   */
  public void cut()
  {
    
  }
}
