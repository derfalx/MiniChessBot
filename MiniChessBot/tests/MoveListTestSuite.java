/*
 * Copyright (c) 2015 by Kristoffer Schneider
 */

import codingfalx.minichess.GameBoard;
import codingfalx.minichess.Move;
import codingfalx.minichess.bot.RandomBot;

import java.io.*;

/**
 * @author falx
 * @created 05.05.2015
 */
public class MoveListTestSuite
{
  public static int testIndex = 0;

  //<editor-fold desc="Methods">

  public static MoveTestCase createMoveTestCaseFromString ( String s )
  {
    String[] parts = s.split ( " " );
    MoveTestCase tCase = new MoveTestCase();
    tCase.mAmountMoves = Integer.parseInt( parts[3] );
    tCase.mTestNumber = testIndex++;
    RandomBot bot = new RandomBot();
    bot.loadFromString( s );
    tCase.mTestString = s;
    tCase.mRandomBot = bot;

    return tCase;
  }

  public static void main ( String[] args ) throws IOException
  {
    File f = new File ( "C:\\Users\\Kristoffer\\Downloads\\text2.txt" );
    BufferedReader reader = new BufferedReader( new FileReader( f ) );

    String line;
    while ( ( line = reader.readLine() ) != null )
    {
      MoveTestCase tCase = createMoveTestCaseFromString( line );
      int moves = tCase.mRandomBot.scanWholeGameBoard() == null ? 0 : tCase.mRandomBot.scanWholeGameBoard().size();
      if ( moves != tCase.mAmountMoves )
        System.out.printf( "FEHLER BEI: \n\t %s\n\texptected: %d \tfound: %d", line, tCase.mAmountMoves, moves);
    }
  }


  //</editor-fold>

  private static class MoveTestCase
  {
    public String mTestString;
    public int mTestNumber;
    public RandomBot mRandomBot;
    public int mAmountMoves;
  }
}
