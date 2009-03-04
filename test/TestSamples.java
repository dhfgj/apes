package test;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import apes.models.Samples;

/**
 * Test file for Samples
 * @author Daniel Kvick (kvick@student.chalmers.se)
 */
public class TestSamples
{
  
  /**
   * Tests that a new samples object have the right size.
   */
  @Test
  public void testSizeIsSetCorrectly()
  {
    int expected = 10;
    Samples s = new Samples( 10 );
    assertEquals( "Samples(int,int) should give specified size.", expected, s.getSize() );
    
    byte[] b = new byte[ expected * Samples.BYTES_PER_SAMPLE ];
    try
    {
      s = new Samples( Samples.BITS_PER_SAMPLE, b );
    }catch(Exception e)
    {
      e.printStackTrace();
    }
    assertEquals( "Samples(int,byte[]) should give correct size.", expected, s.getSize() );
  }
  
  /**
   * Tests that initialization seems to work. 
   * Warning: This assumes getter works. This is bad, but how can we check one without assuming the other?
   */
  @Test
  public void testInitialization()
  {
    assertTrue( "Not implemented yet", true );
  }
  
  /**
   * Tests that getSample returns the correct value.
   */
  @Test
  public void testGetSample()
  {
    assertTrue( "Not implemented yet", true );
  }
  
  /**
   * Tests that setSample updates the samples correctly.
   */
  @Test
  public void testSet()
  {
    assertTrue( "Not implemented yet", true );
  }
  
  /**
   * Tests that maximum amplitude is set correctly.
   */
  @Test
  public void testMaxAmplitudeIsCorrect()
  {
    int lowest = -6, mid = -5, max = -1, newMax = 10;
    int size = 3;
    byte[] b = new byte[ size * Samples.BYTES_PER_SAMPLE ];
    
    // First Sample
    b[0] = (byte)lowest;
    b[1] = -1;
    b[2] = -1;
    b[3] = -1;
      
    // Second Sample
    b[4] = (byte)mid;
    b[5] = -1;
    b[6] = -1;
    b[7] = -1;
    
    // Third Sample
    b[8] = (byte)max;
    b[9] = -1;
    b[10] = -1;
    b[11] = -1;
    
    try
    {
      Samples s = new Samples( size );
      assertEquals( "Default min amplitude is 0", 0, s.getMinAmplitude() );
      assertEquals( "Default max amplitude is 0", 0, s.getMaxAmplitude() );
      
      s = new Samples( Samples.BYTES_PER_SAMPLE, b );
      assertEquals( "Maximum amplitude correctly initialized", max, s.getMaxAmplitude() );
      
      s.setSample( 1, newMax );
      assertEquals( "Max amplitude updates when new max is set", newMax, s.getMaxAmplitude() );
      
      s.setSample( 1, lowest );
      assertEquals( "Max amplitude updates when current max is overwritten", max, s.getMaxAmplitude() );
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }
  
}