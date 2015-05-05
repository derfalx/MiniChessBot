/*
 * Copyright (c) 2015 by Kristoffer Schneider
 */

import codingfalx.minichess.GameBoard;
import codingfalx.minichess.Move;
import codingfalx.minichess.PlayerColor;
import codingfalx.minichess.Square;
import junit.framework.TestCase;
import org.junit.Test;

/**
 * @author falx
 * @created 04.05.2015
 */
public class GameBoardTest
        extends TestCase
{

  public void setUp () throws Exception
  {
    super.setUp();

  }

  @Test
  public void stupidMakeMove () throws Exception
  {
    String board_str = "kqbnrppppp..........PPPPPRNBQK";
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
  }

  public void tearDown () throws Exception
  {

  }

  public void testMakeMove () throws Exception
  {

  }

  public void testParseBoardFromString () throws Exception
  {

  }

  public void testToString () throws Exception
  {

  }
}