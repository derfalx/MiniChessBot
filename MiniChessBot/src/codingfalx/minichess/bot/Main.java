/*
 * Copyright (c) 2015 by Kristoffer Schneider
 */

package codingfalx.minichess.bot;

import codingfalx.minichess.GameBoard;
import codingfalx.minichess.Move;
import codingfalx.minichess.PlayerColor;
import codingfalx.minichess.Square;
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

  public static void main ( String[]args )
  {
    String board_str = "kqbnrppppp..........PPPPPRNBQK";
    GameBoard board = new GameBoard();
    board.parseBoardFromString( board_str );
    System.out.println( board.toString() );

    Square from = new Square ( 0, 1 );
    Square to = new Square( 0, 2 );
    board.makeMove( new Move( from, to, PlayerColor.BLACK ) );
    System.out.println( board.toString() );

    from = new Square ( 'a', '2' );
    to = new Square( 'a', '3' );
    board.makeMove( new Move( from, to, PlayerColor.WHITE ) ) ;
    System.out.println( board.toString() );

    from = new Square ( 'a', '1' );
    to = new Square( 'a', '2' );
    board.makeMove( new Move( from, to, PlayerColor.WHITE ) ) ;
    System.out.println( board.toString() );

    String fromTo = "a3-a4";
    Move m = new Move( fromTo, PlayerColor.WHITE );
    board.makeMove( m );
    System.out.println( board.toString() );

    from = new Square( 'e', '2' );
    ChessBot bot = new ChessBot( board, PlayerColor.WHITE, PlayerState.ACTIVE );
    Collection<Move> coll = bot.moveList( from );
    for ( Move mm : coll )
    {
      System.out.println( mm.toString() + " " );
    }

  }

  //</editor-fold>
}
