package apes.views;

import apes.models.Channel;
import apes.models.SampleIterator;

import javax.swing.*;
import java.awt.*;

public class ProgressView extends JFrame
{
  private static ProgressView instance = new ProgressView();
  private JProgressBar progressBar;

  private ProgressView()
  {
    super();
    progressBar = new JProgressBar(0,100);
    progressBar.setValue( 0 );
    progressBar.setStringPainted( true );
    JPanel panel = new JPanel();
    panel.add(progressBar);
    this.add(panel);
    this.setSize( 200, 30 );
    this.setUndecorated( true );
    this.setResizable( false );
    this.setVisible( false );
    this.setLocation( 300,200 );
  }

  public static ProgressView getInstance()
  {
    return instance;
  }

  public void setValue(int procent)

  {
    progressBar.setValue(procent);      
  }
}
