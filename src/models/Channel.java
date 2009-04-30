package apes.models;

import java.awt.Point;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;

/**
 * Describes a single channel of audio information.
 *
 * @author Daniel Kvick (kvick@student.chalmers.se)
 */
public class Channel
{
  /**
   * Amount of samples in each Samples object in the samplesList.
   */
  public static final int SAMPLES_SIZE = 1000;
  
  /**
   * Contains size of silent padding object in SamplesList, if 0 there is no padding object.
   */
  private int padLength;

  /**
   * A list containing all Samples structure, and thus all audio data of the channel.
   */
  private List<Samples> samplesList;

  /**
   * Constructor which adds a list of samples to the Channel.
   *
   * @param samples All audio data to be added to the Channel.
   */
  public Channel( Samples samples )
  {
    // Divide samples into chunks of SAMPLES_SIZE.
    samplesList = splitSamples( samples );
  }
  
  public SampleIterator getIterator()
  {
    return new SampleIterator(this);
  }
  
  /**
   * Returns the amount of silent padding at the end of the Channel.
   * @return Returns <code>padLength</code>.
   */
  public int getPadLength()
  {
    return padLength;
  }
  
  /**
   * Adjust the amount of silent padding at end of file to reach the specified amount of samples.
   * @param length The wanted padding after the function returns. The function simply returns if <code>length</code> is negative.
   * @return The amount of padding, in samples, after the call. -1 if <code>length</code> is negative.
   */
  public int adjustPadding( int length )
  {
    if(length < 0)
      return -1;
    if(padLength > 0)
      samplesList.remove(samplesList.size()-1);
    Samples s = new Samples(length);
    for( int i = 0; i < length; i++ )
      s.setSampleNoUpdate(i, Short.MIN_VALUE);
    padLength = length;
    return padLength;
  }
  
  /**
   * Sets all samples within the specified range to <code>value</code> 
   * @param start The absolute index of the first value to change.
   * @param stop The absolute index of the last value to change.
   * @param value The value to set selected samples to.
   */
  public void setSamples( int start, int stop, int value )
  {
    Point startPoint = findAbsoluteIndex( start );
    int curIndex = start - startPoint.y;
    for( int objI = startPoint.x; objI < samplesList.size(); objI++ )
    {
      Samples s = samplesList.get(objI);
      for( int i = 0; i < s.getSize(); i++, curIndex++ )
      {
        if(curIndex <= stop )
        {
          if(curIndex >= start)
            s.setSampleNoUpdate(i, value);
        }
        else
        {
          s.updateMinAndMaxAmplitude();
          return;
        }
      }
      s.updateMinAndMaxAmplitude();
    }
      
  }
  
  /**
   * Changes the values of all samples within a specified range by adding <code>delta</code>.
   * @param start The index of the first sample to affect.
   * @param stop The index of the last sample to affect.
   * @param delta Value added to each sample in the selected range. May be negative.
   */
  public void alterSamples( int start, int stop, int delta )
  {
    Point startPoint = findAbsoluteIndex( start );
    int curIndex = start - startPoint.y;
    for( int objI = startPoint.x; objI < samplesList.size(); objI++ )
    {
      Samples s = samplesList.get(objI);
      for( int i = 0; i < s.getSize(); i++, curIndex++ )
      {
        if( curIndex <= stop )
        {
          if(curIndex >= start)
            s.setSampleNoUpdate(i, s.getSample(i) + delta);
        }
        else
        {
          s.updateMinAndMaxAmplitude();
          return;
        }
      }
      s.updateMinAndMaxAmplitude();
    }
  }
  
  /**
   * Changes the values of all samples within a specified range by scaling with <code>alpha</code>.
   * @param start The index of the first sample to affect.
   * @param stop The index of the last sample to affect.
   * @param alpha Value multiplied with each sample in the selected range. May be negative.
   */
  public void scaleSamples( int start, int stop, float alpha )
  {
    Point startPoint = findAbsoluteIndex( start );
    int curIndex = start - startPoint.y;
    for( int objI = startPoint.x; objI < samplesList.size(); objI++ )
    {
      Samples s = samplesList.get(objI);
      for( int i = 0; i < s.getSize(); i++, curIndex++ )
      {
        if( curIndex <= stop )
        {
          if(curIndex >= start)
            s.setSampleNoUpdate(i, Math.round(s.getSample(i) * alpha ));
        }
        else
        {
          s.updateMinAndMaxAmplitude();
          return;
        }
      }
      s.updateMinAndMaxAmplitude();
    }
  }
  
  /**
   * Removes everything from <code>start</code> to <code>stop</code> from the <code>Channel</code> and returns it in an array of Samples objects.
   * @param start
   * @param stop
   * @return null if start > stop. Otherwise all samples removed in an array of <code>Samples</code>.
   */
  public Samples[] cutSamples( int start, int stop )
  {
    // Invalid indexes.
    if( stop < start )
      return null;
    
    //  Declare some variables.
    Samples samples;            // Currently in samplesList.
    Samples newSamples;         // To replace samples in samplesList.
    Samples retSamples;         // To be added to retArray.
    
    // Find indexes
    Point startPoint = findAbsoluteIndex(start);
    Point stopPoint = findAbsoluteIndex(stop);
    
    // Create sufficient array.
    Samples[] retArray;
    retArray = new Samples[stopPoint.x - startPoint.x + 1];
    
    // Only one Samples affected.
    if( retArray.length == 1 )
    {
      // Find Samples affected.
      samples = samplesList.get(startPoint.x);
      
      // Extrmely unlikely
      /*if(startPoint.y == 0 && stopPoint.y == samples.getSize()-1)
      {
        retArray[0] = samples;
        samplesList.remove(samples);
        return retArray;
      }*/
      
      // Create Samples to return.
      retSamples = new Samples( stopPoint.y - startPoint.y + 1 );
      retArray[0] = retSamples;
      
      // Fill in retSamp
      for( int i = startPoint.y, j = 0; i <= stopPoint.y; i++, j++ )
      {
        retSamples.setSample( j, samples.getSample(i) );
      }
      
      // Create Samples to replace affected.
      newSamples = new Samples( samples.getSize() - retSamples.getSize() );
      
      // Fill in newSamples.
      int j = 0;
      for( int i = 0; i < startPoint.y; i++, j++ )
        newSamples.setSample(j, samples.getSample( i ) );
      for( int i = stopPoint.y; i < samples.getSize(); i++, j++ )
        newSamples.setSample(j, samples.getSample( i ) );
      
      // Substitute new for old.
      samplesList.set( startPoint.x, newSamples );
      
      return retArray;
    }
    
    //// Several Samples affected. ////
    
    // Get edge Samples.
    Samples firstSamples = samplesList.get( startPoint.x );
    Samples lastSamples = samplesList.get( stopPoint.x );
    
    // Fix first
    samples = samplesList.get( startPoint.x );
    newSamples = new Samples( startPoint.y );
    for(int i = 0; i < startPoint.y; i++)
      newSamples.setSample( i, samples.getSample(i) );
    samplesList.set( startPoint.x, newSamples );
    
    // Copy from first
    retSamples = new Samples( samples.getSize() - startPoint.x );
    for( int i = startPoint.x, j = 0; i < samples.getSize(); i++, j++ )
    {
      retSamples.setSample( j, samples.getSample(i) );
    }
    retArray[0] = retSamples;
    
    // Handle middle
    int midIndex = startPoint.x + 1;
    for( int index = 1; index < retArray.length - 1; index++ )
    {
      retArray[index] = samplesList.get( midIndex );
      samplesList.remove( midIndex );
    }
    
    // Fix last
    samples = samplesList.get( midIndex );
    newSamples = new Samples( samples.getSize() - stopPoint.y );
    for( int i = stopPoint.y, j = 0; i < samples.getSize(); i++, j++ )
      newSamples.setSample(j, samples.getSample( i ) );
    samplesList.set( midIndex, newSamples );
    
    // Copy from last
    retSamples = new Samples( stopPoint.y );
    for( int i = 0; i < stopPoint.y; i++ )
      retSamples.setSample( i, samples.getSample(i) );
    retArray[ retArray.length - 1] = retSamples;
    
    return retArray;
  }
  
  /**
   * Finds the sample with the given absolute index.
   * @param absIndex The absolute index to find.
   * @return A point with x being the SamplesObject of the requested sample 
   */
  public Point findAbsoluteIndex( int absIndex )
  {
    int sampObj = 0;
    int curIndex = 0;
    for(; sampObj < samplesList.size(); sampObj++ )
    {
      Samples s = samplesList.get(sampObj);
      int newIndex = curIndex + s.getSize();
      if( newIndex == absIndex )
      {
        return new Point(sampObj+1, 0);
      }
      
      if( absIndex < newIndex )
      {
        return new Point(sampObj, newIndex - absIndex);
      }
      
      curIndex = newIndex;
    }
    
    return new Point(-1, -1);
  }
  
  /**
   * Splits a <code>Samples</code> object into chunks of size <code>SAMPLES_SIZE</code>.
   * @param samples The samples object to split.
   * @return A samples object containing all samples in <code>samples</code> divided into Samples objects of size <code>SAMPLES_SIZE</code>
   */
  private List<Samples> splitSamples( Samples samples )
  {
    // Amount of full chunks
    int fullChunks = samples.getSize() / SAMPLES_SIZE;
    // Remaining samples
    int remainder = samples.getSize() % SAMPLES_SIZE;
    
    List<Samples> split = new ArrayList<Samples>();

    // Make full chunks
    for( int i = 0; i < fullChunks; i++ )
    {
      Samples sampObj = new Samples( SAMPLES_SIZE );
      for( int j = 0; j < SAMPLES_SIZE; j++ )
      {
        sampObj.setSampleNoUpdate( j, samples.getSample( i * SAMPLES_SIZE + j ) );
      }
      sampObj.updateMinAndMaxAmplitude();
      split.add( sampObj );
    }
    
    
    // Add remainder in smaller chunk
    Samples sampObj = new Samples( remainder );
    for( int i = 0; i < remainder; i++ )
    {
      //TODO: Had to add the try/catch, handle this in a better way maybe??
      try
      {
        sampObj.setSample( i, samples.getSample( fullChunks * SAMPLES_SIZE + i ) );
      } catch ( Exception e )
      {
        e.printStackTrace();
      }
    }
    split.add(sampObj);
    
    // Return chunks.
    return split;
  }
  
  /**
   * Get sample chunk at point <code>index</code>.
   *
   * @param index the index value
   * @return a chunk of <code>Samples</code> for <code>index</code>.
   */
  public Samples getSamples( int index )
  {
    return samplesList.get( index );
  }

  /**
   * Describe <code>getSamplesSize</code> method here.
   *
   * @return an <code>int</code> value
   */
  public int getSamplesSize()
  {
    return samplesList.size();
  }
  
  /**
   * Returns that smallest amplitude for this channel.
   *
   * @return The smallest amplitude.
   */
  public int getMinAmplitude()
  {
    int min = Integer.MAX_VALUE;
    int value;
    for( Samples s : samplesList )
      if( (value = s.getMinAmplitude()) < min )
        min = value;
    return min;
  }
  
  /**
   * Returns that largest amplitude for this channel.
   *
   * @return The largest amplitude.
   */
  public int getMaxAmplitude()
  {
    int max = Integer.MAX_VALUE;
    int value;
    for( Samples s : samplesList )
      if( (value = s.getMaxAmplitude()) > max )
        max = value;
    return max;
  }
  
  /**
   * Returns the diff between the largest and smallest amplitude.
   *
   * @return Diff between amplitues.
   */
  public long getDiffAmplitude()
  {
    return (long)getMaxAmplitude() - getMinAmplitude();
  }
}