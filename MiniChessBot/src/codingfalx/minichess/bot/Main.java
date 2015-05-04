/*
 * Copyright (c) 2015 by Kristoffer Schneider
 */

package codingfalx.minichess.bot;

import codingfalx.minichess.*;
import codingfalx.minichess.bot.states.PlayerState;

import java.util.Collection;

/**
 * @author falx
 * @created 04.05.2015
 */
public class Main
{
  //<editor-fold desc="Fields">


  //</editor-fold>

  //<editor-fold desc="Constructor">


  //</editor-fold>

  //<editor-fold desc="Methods">

  public static void GaryTest ()
  {
    System.out.println( "\n\n\n GARY TEST" );
    String board_str = "kqb.r.p.ppp.p.n.PNP.P.P.PR.BQK";
    GameBoard board = new GameBoard();
    board.parseBoardFromString( board_str );
    System.out.println( board );
    ChessBot bot = new ChessBot( board, PlayerColor.WHITE, PlayerState.ACTIVE );
    Collection<Move> coll = bot.scanWholeGameBoard();
    int i = 0;
    for ( Move mm : coll )
    {
      System.out.println( i++ + " " + mm.toString() + " " );
    }
  }

  public static void main ( String[] args )
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

    ChessBot black = new ChessBot();
    ChessBot white = new ChessBot();
    GameMaster gm = new GameMaster( black, white );
    System.out.println( "\n\n\n\nLETS PLAY!" );
    gm.runGame();
  }

  public static void testMovList ( Square from, GameBoard board )
  {
    ChessBot bot = new ChessBot( board, PlayerColor.WHITE, PlayerState.ACTIVE );
    Collection<Move> coll = bot.moveList( from );
    for ( Move mm : coll )
    {
      System.out.println( mm.toString() + " " );
    }
  }

  //</editor-fold>
}