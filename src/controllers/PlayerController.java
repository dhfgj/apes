package apes.controllers;

import apes.controllers.ApplicationController;
import apes.models.Player;

/**
 * Controller for the player.
 *
 * @author Johan Andersson (johandy@student.chalmers.se)
 */
public class PlayerController extends ApplicationController
{
  /**
   * Player model.
   */
  private Player player;

  /**
   * Creates a new Player controller.
   */
  public PlayerController()
  {
    this.player = Player.getInstance();
  }

  /**
   * Go backward.
   */
  public void backward()
  {
    player.backward();
  }

  /**
   * Pause playing.
   */
  public void pause()
  {
    player.pause();
  }

  /**
   * Play.
   */
  public void play()
  {
    player.play();
  }

  /**
   * Stop playing.
   */
  public void stop()
  {
    player.stop();
  }

  /**
   * Forward playing.
   */
  public void forward()
  {
    player.forward();
  }
}