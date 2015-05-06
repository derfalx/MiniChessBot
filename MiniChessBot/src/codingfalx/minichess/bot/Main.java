/*
 * Copyright (c) 2015 by Kristoffer Schneider
 */

package codingfalx.minichess.bot;

import codingfalx.minichess.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author falx
 * @created 04.05.2015
 */
public class Main
{
  //<editor-fold desc="Methods">

  public static void GaryTest ()
  {
    System.out.println( "\n\n\n GARY TEST" );
    String board_str = "kqb.r.p.ppp.p.n.PNP.P.P.PR.BQK";
    GameBoard board = new GameBoard();
    board.parseBoardFromString( board_str );
    System.out.println( board );
    GreedyBot bot = new GreedyBot( board, PlayerColor.WHITE, PlayerState.ACTIVE );
    Collection<ScoredMove> coll = bot.scanWholeGameBoard();
    int i = 0;
    for ( Move mm : coll )
    {
      System.out.println( i++ + " " + mm.toString() + " " );
    }
  }

  public static void GreedyVsLookAheadTest () throws IOException
  {
    int black_ = 0 ;
    int white_ = 0;
    int draw_ = 0;
    ArrayList<Double> times = new ArrayList<>();
    for ( int j = 0; j < 1_000; j++ )
    {
      double start = System.currentTimeMillis();
      for ( int i = 0; i < 20; i++ )
      {
        //GreedyBot white = new GreedyBot();
        RandomBot white = new RandomBot();
        //LookAheadBot white = new LookAheadBot();
        LookAheadBot black = new LookAheadBot( 3 );
        GameMaster gm = new GameMaster( black, white );
        //System.out.println( "LETS PLAY!" );
        PlayerColor c = gm.runGame( true );
        System.out.println( c );
        if ( c.equals( PlayerColor.WHITE ) )
          white_++;
        else if ( c.equals( PlayerColor.BLACK ) )
          black_++;
        else
          draw_++;
      }
      times.add( System.currentTimeMillis() - start );
    }
    double sum = 0;
    double min = Double.MAX_VALUE;
    double max = Double.MIN_VALUE;
    for ( double d : times )
    {
      sum += d;
      if ( d < min )
        min = d;
      if ( d > max )
        max = d;
    }
    System.out.println ( sum/50 + " " + min  + " " + max + " " + sum );
    System.out.printf( "Black: %d\tWhite: %d\tDraw: %d\n", black_, white_, draw_ );
  }

  public static void GreedyVsABLookAheadTest() throws IOException
  {
    int black_ = 0 ;
    int white_ = 0;
    int draw_ = 0;
    ArrayList<Double> times = new ArrayList<>();
    for ( int j = 0; j < 10; j++ )
    {
      double start = System.currentTimeMillis();
      for ( int i = 0; i < 20; i++ )
      {
        GreedyBot white = new GreedyBot();
        //ABPrunedLookAheadBot white = new ABPrunedLookAheadBot();
        ABPrunedLookAheadBot black = new ABPrunedLookAheadBot( 4 );
        GameMaster gm = new GameMaster( black, white );
        //System.out.println( "LETS PLAY!" );
        PlayerColor c = gm.runGame( true );
        System.out.println( c );
        if ( c.equals( PlayerColor.WHITE ) )
          white_++;
        else if ( c.equals( PlayerColor.BLACK ) )
          black_++;
        else
          draw_++;
      }
      times.add( System.currentTimeMillis() - start );
    }
    double sum = 0;
    double min = Double.MAX_VALUE;
    double max = Double.MIN_VALUE;
    for ( double d : times )
    {
      sum += d;
      if ( d < min )
        min = d;
      if ( d > max )
        max = d;
    }
    System.out.println ( sum/50 + " " + min  + " " + max + " " + sum );
    System.out.printf( "Black: %d\tWhite: %d\tDraw: %d\n", black_, white_, draw_ );
  }

  public static void RandomVsConsoleTest () throws IOException
  {
    GreedyBot black = new GreedyBot();
    ConsolePlayer white = new ConsolePlayer();
    //GameMaster gm = new GameMaster( black, white );
    //System.out.println( gm.runGame( false ) );
  }

  public static void GreedyVsRandomTest () throws IOException
  {
    int black_ = 0 ;
    int white_ = 0;
    int draw_ = 0;
    ArrayList<Double> times = new ArrayList<>();
    for ( int j = 0; j < 10; j++ )
    {
      double start = System.currentTimeMillis();
      for ( int i = 0; i < 10_000; i++ )
      {
        GreedyBot black = new GreedyBot();
        RandomBot white = new RandomBot();
        GameMaster gm = new GameMaster( black, white );
        //System.out.println( "LETS PLAY!" );
        PlayerColor c = gm.runGame( true );
        //System.out.println( c );
        if ( c.equals( PlayerColor.WHITE ) )
          white_++;
        else if ( c.equals( PlayerColor.BLACK ) )
          black_++;
        else
          draw_++;
      }
      times.add( System.currentTimeMillis() - start );
    }
    double sum = 0;
    double min = Double.MAX_VALUE;
    double max = Double.MIN_VALUE;
    for ( double d : times )
    {
      sum += d;
      if ( d < min )
        min = d;
      if ( d > max )
        max = d;
    }
    System.out.println ( sum/50 + " " + min  + " " + max);
    System.out.printf( "Black: %d\tWhite: %d\tDraw: %d\n", black_, white_, draw_ );
  }

  public static void GreedyVsGreedyTest() throws IOException
  {
    int black_ = 0 ;
    int white_ = 0;
    int draw_ = 0;
    ArrayList<Double> times = new ArrayList<>();
    double total = System.currentTimeMillis();
    for ( int j = 0; j < 2; j++ )
    {
      double start = System.currentTimeMillis();
      for ( int i = 0; i < 10_000; i++ )
      {
   /*     GreedyBot white = new GreedyBot();
        GreedyBot black = new GreedyBot();*/
        RandomBot white = new RandomBot();
        RandomBot black = new RandomBot();
        GameMaster gm = new GameMaster( black, white );
        //System.out.println( "LETS PLAY!" );
        PlayerColor c = gm.runGame( true );
        //System.out.println( c );
        if ( c.equals( PlayerColor.WHITE ) )
          white_++;
        else if ( c.equals( PlayerColor.BLACK ) )
          black_++;
        else
          draw_++;
      }
      times.add( System.currentTimeMillis() - start );
    }
    double sum = 0;
    double min = Double.MAX_VALUE;
    double max = Double.MIN_VALUE;
    for ( double d : times )
    {
      sum += d;
      if ( d < min )
        min = d;
      if ( d > max )
        max = d;
    }
    System.out.println ( sum/50 + " " + min  + " " + max);
    System.out.println( System.currentTimeMillis() - total );
    System.out.printf( "Black: %d\tWhite: %d\tDraw: %d\n", black_, white_, draw_ );
  }


  public static void main ( String[] args ) throws IOException
  {
/*    String board_str = "kqbnrppppp..........PPPPPRNBQK";
    GameBoard board = new GameBoard();
    board.parseBoardFromString( board_str );
    System.out.println( board.toString() );

    Square from = new Square( 0, 1 );
    Square to = new Square( 0, 2 );
    board.makeMove( new Move( from, to, PlayerColor.BLACK ) );
    System.out.println( board.toString() );

    from = new Square( 'a', '2' );
    to = new Square( 'a', '3' );
    board.makeMove( new Move( from, to, PlayerColor.WHITE ) );
    System.out.println( board.toString() );

    from = new Square( 'a', '1' );
    to = new Square( 'a', '2' );
    board.makeMove( new Move( from, to, PlayerColor.WHITE ) );
    System.out.println( board.toString() );

    String fromTo = "a3-a4";
    Move m = new Move( fromTo, PlayerColor.WHITE );
    board.makeMove( m );
    System.out.println( board.toString() );

    from = new Square( 'a', '2' );
    testMovList( from, board );
    from = new Square( 'b', '1' );
    testMovList( from, board );
    from = new Square( 'a', '6' );
    testMovList( from, board );


    GaryTest();*/

    //GreedyVsRandomTest();

    GreedyVsLookAheadTest();

    //GreedyVsABLookAheadTest();

    //PawnToQueenTest();

   /* RandomVsConsoleTest();*/

    //GreedyVsGreedyTest();

  }

  public static void testMovList ( Square from, GameBoard board )
  {
    GreedyBot bot = new GreedyBot( board, PlayerColor.WHITE, PlayerState.ACTIVE );
    Collection<ScoredMove> coll = bot.moveList( from );
    for ( Move mm : coll )
    {
      System.out.println( mm.toString() + " " );
    }
  }

  private static void PawnToQueenTest ()
  {
    String board_str = "........................P.....";
    GameBoard board = new GameBoard();
    board.parseBoardFromString( board_str );
    GreedyBot bot = new GreedyBot( board, PlayerColor.WHITE, PlayerState.WAITING );
    for ( int i = 0; i < 8; i++ )
    {
      Move m = bot.makeMove();
      board.makeMove( m );
      System.out.println( board );
    }
  }

  //</editor-fold>
}
