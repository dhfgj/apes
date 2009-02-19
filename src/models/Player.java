package apes.models;

import apes.models.InternalFormat;

/**
 * This class plays a music file. Or more precise, an {@link
 * InternalFormat InternalFormat}.
 *
 * @author Johan Andersson (johandy@student.chalmers.se)
 */
public class Player
{
  /**
   * An instance of this class.
   */
  private static Player instance = null;

  /**
   * This variable holds the status for the player. true means that
   * it's playing and false means it's not.
   */
  private boolean status;

  /**
   * A Player is always connected to an internal format.
   */
  private InternalFormat interalFormat;

  /**
   * Current volume.
   */
  private int volume;

  /**
   * Minimum value for the player.
   */
  public static final int MIN_VALUE = 0;

  /**
   * Maximum value for the player.
   */
  public static final int MAX_VALUE = 100;

  /**
   * Empty and private so that you can not create an object of this
   * class without using the Singleton pattern.
   */
  private Player() { }

  /**
   *
   */
  public void play()
  {
    // If there's no playing.
    if( status == false )
    {

    }

    setStatus( true );
  }

  /**
   *
   *
   */
  public void pause()
  {
    // If there's playing.
    if( status )
    {

    }

    setStatus( false );
  }

  /**
   *
   *
   */
  public void stop()
  {
    // If there's no playing.
    if( status == false )
    {

    }

    setStatus( false );
  }

  /**
   *
   */
  public void forward()
  {

  }

  /**
   *
   */
  public void backward()
  {

  }

  /**
   * Get the volume for this player.
   *
   * @return the volume.
   */
  public int getVolume()
  {
    return volume;
  }

  /**
   * Set the volume for this player.
   *
   * @param volume The new volume.
   */
  public void setVolume( int volume )
  {
    if( volume >= MIN_VALUE && volume <= MAX_VALUE )
    {
      this.volume = volume;
    }
  }

  /**
   * Returns the <code>InternalFormat</code> connected to this player.
   *
   * @return the <code>InternalFormat</code>.
   */
  public InternalFormat getInternalFormat()
  {
    return this.interalFormat;
  }

  /**
   * Set the <code>InternalFormat</code> for this player.
   *
   * @param interalFormat the <code>InternalFormat</code> to use.
   * @return true if interalFormat is not null. false otherwise.
   */
  public boolean setInternalFormat( InternalFormat interalFormat )
  {
    // Stop before change.
    stop();

    if( interalFormat != null )
    {
      this.interalFormat = interalFormat;

      return true;
    }

    return false;
  }
  
  /**
   * Returns current status.
   *
   * @return Current status.
   */
  public boolean getStatus()
  {
    return this.status;
  }
  
  /**
   * Sets the status to <code>status</code>
   *
   * @param status The new status.
   */
  private void setStatus( boolean status )
  {
    this.status = status;
  }

  /**
   * Will return an instance of this class.
   *
   * @return An instance of this class.
   */
  public static Player getInstance()
  {
    if( instance == null )
    {
      instance = new Player();
    }

    return instance;
  }
}