package apes.plugins;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

import apes.interfaces.AudioFormatPlugin;
import apes.lib.FileHandler;
import apes.models.Channel;
import apes.models.InternalFormat;
import apes.models.Samples;
import apes.models.Tags;

/**
 * Module used for converting .wav-files to the internal format
 * and converting the internal format to .wav-files.
 *
 * @author Simon Holm
 */
public class WaveFileFormat implements AudioFormatPlugin
{

  /**
   * Converts a file from the internal file format to .wav and stores it on the disk
   *
   * @param internalFormat The file to be converted.
   * @param path           The path to the folder were the file should be saved.
   * @param filename       The name of the file to be saved.
   * @throws Exception
   */
  public void exportFile( InternalFormat internalFormat, String path, String filename ) throws Exception
  {
    //throw new Exception( "Not implemeted yet." );
    /*
    byte[] chunkID       = {'R','I','F','F'};
    int    chunkSize     = 4+(8+subchunk1Size)+(8+subchunk2Size);
    byte[] format        = {'W','A','V','E'};
    int    subchunk1ID   = 0x666d7420; // fmt
    int    subchunk1Size = 16;
    short  audioFormat   = 1;
    short  numChannels   = (short)internalFormat.getNumChannels();
    int    sampleRate    = internalFormat.getSampleRate();
    int    byteRate      = sampleRate * numChannels * (Samples.BITS_PER_SAMPLE/8);
    short  blackAlign    = (short)(numChannels * (Samples.BITS_PER_SAMPLE/8));
    short  bitsPerSample = Samples.BITS_PER_SAMPLE;
    byte[] subchunk2ID   = {'d','a','t','a'};
    //int    subchunk2Size = numSamples * numChannels * (Samples.BITS_PER_SAMPLE/8);

    byte[] data; //          = the sound data

    Channel[] channels = new Channel[numChannels];
    for( int i = 0; i < numChannels; ++i)
      channels[i] = internalFormat.getChannel(i);

    //put samples into data

      */

    /*
    ChunkID       = RIFF
    ChunkSize     = 4+(8+Subchunk1Size)+(8+Subchunk2Size)
    Format        = WAVE
    Subchunk1ID   = fmt
    Subchunk1Size = 16
    AudioFormat   = 1
    NumChannels   = internalFormat.getNumChannels();
    SampleRate    = internalFormat.getSampleRate();
    ByteRate      = SampleRate * NumChannels * BitsPerSample/8
    BlackAlign    = NumChannels * bitsPerSample/8
    BitsPerSample = Samples.BITS_PER_SAMPLE;
    Subchunk2ID   = data
    Subchunk2Size = NumSamples * NumCHannels * BitsPerSample/8
    data          = the sound data
    
    */
  }

  //TODO: Create a more detailed description of exception
  //TODO: Add better error handling, check for errors, take different endians into account, handle more chunks
  /**
   * Imports a wave file, converts it to the internal format and returns it.
   *
   * @param path     The path to the folder with the file to be loaded.
   * @param filename The name of the file to be imported.
   * @return Returns the file converted to the internal format
   * @throws Exception Will throw an exception if something bad happens
   */
  public InternalFormat importFile( String path, String filename ) throws Exception
  {
    ByteBuffer buffer = FileHandler.loadFile( path, filename );

    // Wave do not contain any tags
    Tags tag = null;

    // 4 big
    buffer.order( ByteOrder.BIG_ENDIAN );
    buffer.getInt();
    // 4 little
    buffer.order( ByteOrder.LITTLE_ENDIAN );
    buffer.getInt();
    // 4 big
    buffer.order( ByteOrder.BIG_ENDIAN );
    buffer.getInt();
    // 4 big
    buffer.order( ByteOrder.BIG_ENDIAN );
    buffer.getInt();
    // 4 little
    buffer.order( ByteOrder.LITTLE_ENDIAN );
    buffer.getInt();
    // 2 little
    buffer.getShort();
    // 2 little
    int numChannels = buffer.getShort();
    // 4 little
    int sampleRate = buffer.getInt();
    // 4 little
    buffer.getInt();
    // 2 little
    buffer.getShort();
    // 2 little
    int bitsPerSample = buffer.getShort();
    // 4 big
    buffer.order( ByteOrder.BIG_ENDIAN );
    buffer.getInt();
    // 4 little
    buffer.order( ByteOrder.LITTLE_ENDIAN );
    int subChunk2Size = buffer.getInt();
    // little    

    List<Channel> channels = new ArrayList<Channel>();

    byte[][] samplesPerChannel = new byte[numChannels][subChunk2Size/numChannels]; 

    int channel = 0;
    int bytesPerSample = bitsPerSample/8;
    
    for( int i = 0; i < subChunk2Size/bytesPerSample; ++i )
    {
      buffer.get( samplesPerChannel[channel], i*bytesPerSample, bytesPerSample );
      channel = (++channel) % numChannels;      
    }


    for ( int i = 0; i < numChannels; ++i )
      channels.add( new Channel( new Samples( bitsPerSample, samplesPerChannel[i] ) ) );

    return new InternalFormat( tag, sampleRate, channels );
  }

}
