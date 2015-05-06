/*
 * Copyright (c) 2015 by Kristoffer Schneider
 */

package codingfalx.minichess.bot.debug;

import codingfalx.minichess.Move;
import codingfalx.minichess.ScoredMove;
import codingfalx.minichess.bot.ChessBot;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * @author falx
 * @created 06.05.2015
 */
public class GameStateWriter
{
  static
  {
    try
    {
      GameStateWriter.Instance = new GameStateWriter( "C:\\\\Users\\\\Kristoffer\\\\Desktop\\\\out.txt", true );
    }
    catch ( IOException e )
    {
      e.printStackTrace();
    }
  }
  //<editor-fold desc="Fields">

  private BufferedWriter mBufferedWriter;
  public static GameStateWriter Instance;

  //</editor-fold>

  //<editor-fold desc="Constructor">


  private GameStateWriter ( String file, boolean append ) throws IOException
  {
    this.mBufferedWriter = new BufferedWriter( new FileWriter( file, append ) );
  }

  //</editor-fold>

  //<editor-fold desc="Methods">

  public void writeBotState ( ChessBot bot, boolean withAllTurns, double score, ArrayList<ScoredMove> toAppend )
  {
    try
    {
      this.mBufferedWriter.append( bot.writeToString( false )  );
      StringBuilder sb = new StringBuilder();
      sb.append ( " " );
      sb.append( score );
      sb.append ( "  ");
      ArrayList<String> str = new ArrayList<>( toAppend.size() );
      for ( ScoredMove m : toAppend )
        str.add( m.toString() );
      int i = 0;
      Collections.sort( str );
      int max = str.size();
      for ( String s : str )
      {
        sb.append ( s );
        if ( i < max - 1 )
          sb.append( " " );
        i++;
      }
      sb.append( '\n' );
      this.mBufferedWriter.append ( sb.toString() );
    }
    catch ( IOException e )
    {
      e.printStackTrace();
    }
  }

  //</editor-fold>
}
