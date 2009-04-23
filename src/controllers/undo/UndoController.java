package apes.controllers.undo;

import apes.controllers.ApplicationController;
import apes.views.InternalFormatView;
import javax.swing.undo.UndoManager;

/**
 * Performs an undo action in the undo manager.
 *
 * @author Johan Andersson (johandy@student.chalmers.se)
 * @author Johan Åhlander (johan.ahlander@gmail.com)
 */
public class UndoController extends ApplicationController
{
  /**
   * The view over the internal format.
   */
  private InternalFormatView internalFormatView;
  
  /**
   * The undo manager that keeps track of all changes.
   */
  private UndoManager undoManager;
  
  public UndoController( UndoManager undoManager, InternalFormatView internalFormatView )
  {
    this.internalFormatView = internalFormatView;
    this.undoManager = undoManager;
  }

  /**
   * Performs an undo.
   */
  public void undo()
  {
    undoManager.undo();
  }
}
