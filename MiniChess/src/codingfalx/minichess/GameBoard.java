/*
 * Copyright (c) 2015 by Kristoffer Schneider
 */

package codingfalx.minichess;

/**
 * @author Kristoffer Schneider
 * @created 04.05.2015
 */
public class GameBoard
{
  //<editor-fold desc="Fields">

  public static final int HORIZONTAL_SIZE = 5;
  public static final int VERTICAL_SIZE = 6;
  public static final int fMaxTurns = 80;
  private final Figure mGameBoard[][];
  private int mTurnsLeft;

  //</editor-fold>

  //<editor-fold desc="Constructor">

  public GameBoard ()
  {
    this.mGameBoard = new Figure[VERTICAL_SIZE][HORIZONTAL_SIZE];
    this.mTurnsLeft = GameBoard.fMaxTurns;
  }

  //</editor-fold>

  //<editor-fold desc="Methods">

  public boolean makeMove ( Move move )
  {

    if ( !this.isValidMove( move ) )
    {
      return false;
    }

    Figure toMove = this.replaceFigure( move.fFrom, Figure.EMPTY );
    this.replaceFigure( move.fTo, toMove );

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

  public Figure getFigure ( Square square )
  {
    int y = square.fRowCount;
    int x = square.fColumnCount;
    return this.mGameBoard[y][x];
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

