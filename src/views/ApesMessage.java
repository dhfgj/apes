package apes.views;

import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;

import apes.lib.Language;

/**
 * Use this class to send the user a message. A message can be something like:
 * <pre>"Please wait while we are processing your data"</pre>
 *
 * @author Johan Andersson (johandy@student.chalmers.se)
 */
public class ApesMessage extends JPanel implements Runnable
{
  /**
   * An instance of this class.
   */
  private static ApesMessage instance = null;

  /**
   * A language object.
   */
  private Language language;

  /**
   * A label to place text on.
   */
  private JLabel label;

  /**
   * The message that should be displayed.
   */
  private String message;
  
  /**
   * The thread this class is running in.
   */
  private Thread thread;
  
  /**
   * The panel width.
   */
  private final static int WIDTH = 600;
  
  /**
   * The panel height.
   */
  private final static int HEIGHT = 25;
  
  /**
   * How many milliseconds it should take for the panel to occur when
   * printing a message.
   */
  private static int OCCUR_TIME = 1000;
  
  /**
   * How long the message should be vissible.
   */
  private static int DURATION_TIME = 5000;
  
  
  /**
   * Private so that it only can be created through getInstance().
   */
  private ApesMessage()
  {
    // Set panel settings.
    setSize(WIDTH, 0);
    setLayout(new FlowLayout(FlowLayout.LEFT));
    setVisible(false);
    
    // The label the text is on.
    label = new JLabel();
    label.setFont( new Font( "verdana", 1, 12 ) );
    add(label);
    
    language = Language.getInstance();
    
    // We need to run this as a thread.
    thread = new Thread(this);
    thread.start();
  }

  /**
   * Prints a message.
   *
   * NOTE: Tag is automatically prepended with "message.". So if
   * <code>tag</code> is "file.save", the locale tag should be
   * "message.locale.tag".
   *
   * @param tag The locale tag.
   */
  public void print(String tag)
  {
    message = language.get("message." + tag);
  }

  public void run()
  {
    while(true)
    {
      if(message == null)
      {
        sleep(100);
      }
      else
      {
        // We want the message to appear.
        setSize(WIDTH, 0);
        label.setText(message);
        setVisible(true);
        int sleep = OCCUR_TIME / HEIGHT;
        
        for(int i = 0; i < HEIGHT; i++)
        {
          setSize(WIDTH, i);
          sleep(sleep);
        }
        
        // Reset
        message = null;
        sleep(DURATION_TIME);
        setSize(WIDTH, 0);
        setVisible(false);
      }
    }
  }

  /**
   * Sleeps the thread for <code>sleep</code>.
   *
   * @param sleep How long to sleep.
   */
  private void sleep(int sleep)
  {
    try
    {
      Thread.sleep(sleep);
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }

  /**
   * Will return an instance of this class.
   *
   * @return An instance of this class.
   */
  public static ApesMessage getInstance()
  {
    if(instance == null)
    {
      instance = new ApesMessage();
    }

    return instance;
  }
}