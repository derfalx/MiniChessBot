/*
 * Copyright (c) 2015 by Kristoffer Schneider
 */

package codingfalx.minichess;

import java.util.Arrays;

/**
 * @author Kristoffer Schneider
 * @created 04.05.2015
 */
public class GameBoard
        implements Cloneable
{
  //<editor-fold desc="Fields">

  public static final int HORIZONTAL_SIZE = 5;
  public static final int VERTICAL_SIZE = 6;
  public static final int fMaxTurns = 80;
  private final Figure mGameBoard[][];
  private String mGameBoardSource;
  private int mTurnsLeft;
  private Figure mTakenKing;

  //</editor-fold>

  //<editor-fold desc="Constructor">

  public GameBoard ()
  {
    this.mGameBoard = new Figure[VERTICAL_SIZE][HORIZONTAL_SIZE];
    this.mTurnsLeft = GameBoard.fMaxTurns;
  }

  @Override
  public GameBoard clone ()
  {
    GameBoard clone = new GameBoard();
    clone.parseBoardFromString( this.mGameBoardSource );
    clone.mTurnsLeft = this.mTurnsLeft;

    return clone;
  }

  //</editor-fold>

  //<editor-fold desc="Methods">

  public Figure getFigure ( Square square )
  {
    int y = square.fRowCount;
    int x = square.fColumnCount;
    return this.mGameBoard[y][x];
  }

  public Figure[][] getGameBoard ()
  {
    return mGameBoard;
  }

  public Figure getTakenKing ()
  {
    return mTakenKing;
  }

  public int getTurnsLeft ()
  {
    return this.mTurnsLeft;
  }

  public boolean makeMove ( Move move )
  {

    if ( !this.isValidMove( move ) )
    {
      return false;
    }

    Figure toMove = this.replaceFigure( move.fFrom, Figure.EMPTY );
    Figure replaced = this.replaceFigure( move.fTo, toMove );

    if ( replaced.equals( Figure.BLACK_KING ) || replaced.equals( Figure.WHITE_KING ) )
    {
      this.mTakenKing = replaced;
      this.mTurnsLeft = 0;
    }

    this.mTurnsLeft--;
    return true;
  }

  public boolean parseBoardFromString ( String s )
  {
    if ( s.length() != ( VERTICAL_SIZE * HORIZONTAL_SIZE ) )
      return false;

    int x = 0;
    int y = 0;
    char symbols[] = s.toCharArray();

    for ( char c : symbols )
    {
      Figure fig = Figure.getFigure( c );
      this.mGameBoard[y][x] = fig;
      x = ( x + 1 ) % HORIZONTAL_SIZE;
      if ( x == 0 )
        y++;
    }

    this.mGameBoardSource = s;

    return true;
  }

  @Override
  public String toString ()
  {
    StringBuilder sb = new StringBuilder();
    sb.append( "  abcde\n" );

    int i = 6;
    for ( Figure[] row : this.mGameBoard )
    {
      sb.append( i-- );
      sb.append( ' ' );
      for ( Figure currentFigure : row )
        sb.append( currentFigure.symbol );

      sb.append( '\n' );
    }

    return sb.toString();
  }

  private boolean isValidMove ( Move move )
  {
    Figure figure = this.getFigure( move.fFrom );
    if ( !figure.color.equals( move.fPlayerColor ) )
      return false;

    return true;
  }

  private Figure replaceFigure ( Square square, Figure replacement )
  {
    int y = square.fRowCount;
    int x = square.fColumnCount;
    Figure toReturn = this.getFigure( square );
    this.mGameBoard[y][x] = replacement;

    return toReturn;
  }

  //</editor-fold>
}

