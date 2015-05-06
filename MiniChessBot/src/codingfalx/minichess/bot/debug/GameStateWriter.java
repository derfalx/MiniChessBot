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
import java.util.HashMap;

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
      GameStateWriter.Instance = new GameStateWriter( "C:\\Users\\falx\\200k.txt", true );
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
      sb.append( score );
      sb.append ( " ");
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

  public void writeMoveScores ( ChessBot bot, HashMap<ScoredMove, Double> moves )
  {
    try
    {
      this.mBufferedWriter.append( bot.writeToString( false ) );
      StringBuilder sb = new StringBuilder();
      int i = 0;
      int max = moves.keySet().size();
      for ( ScoredMove m : moves.keySet() )
      {
        String s = m.toString();
        double score = moves.get( m );
        if ( i != 0 )
          sb.append( " " );
        i++;
        sb.append( score );
        sb.append ( " " );
        sb.append( s );
      }
      sb.append( "\n" );
      this.mBufferedWriter.append( sb.toString() );
    }
    catch ( IOException e )
    {
      e.printStackTrace();
    }
  }

  public void writeMoveScores ( ChessBot bot, double score, Move move )
  {
    try
    {
      this.mBufferedWriter.append( bot.writeToString( false ) );
      StringBuilder sb = new StringBuilder();
      sb.append ( score );
      sb.append ( " " );
      sb.append( move.toString() );
      this.mBufferedWriter.append( sb.toString() );
    }
    catch ( IOException e )
    {
      e.printStackTrace();
    }
  }


  //</editor-fold>
}
