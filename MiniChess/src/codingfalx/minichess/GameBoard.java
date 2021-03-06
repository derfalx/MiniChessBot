/*
 * Copyright (c) 2015 by Kristoffer Schneider
 */

package codingfalx.minichess;

import java.io.BufferedWriter;

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
  private double mBlackScore = 1_002_000;
  private String mGameBoardSource;
  private Figure mTakenKing;
  private int mTurnsLeft;
  private double mWhiteScore = 1_002_000;



  //</editor-fold>

  //<editor-fold desc="Constructor">

  public GameBoard ()
  {
    this.mGameBoard = new Figure[VERTICAL_SIZE][HORIZONTAL_SIZE];
    this.mTurnsLeft = GameBoard.fMaxTurns;
  }


  //</editor-fold>

  //<editor-fold desc="Methods">

  /**
   * @see Object#clone()
   */
  @Override
  public GameBoard clone ()
  {
    GameBoard clone = new GameBoard();
    for ( int y = 0; y < GameBoard.VERTICAL_SIZE; y++ )
    {
      for ( int x = 0; x < GameBoard.HORIZONTAL_SIZE; x++ )
        clone.mGameBoard[y][x] = this.mGameBoard[y][x];
    }
    clone.mTurnsLeft = this.mTurnsLeft;
    clone.mBlackScore = this.mBlackScore;
    clone.mWhiteScore = this.mWhiteScore;
    clone.mTakenKing = this.mTakenKing;

    return clone;
  }

  public double getBlackScore ()
  {
    if ( this.mTakenKing != null )
    {
      if ( this.mTakenKing.color.equals( PlayerColor.BLACK ) )
      {
        return -Figure.BLACK_KING.scoreValue;
      }
      else
        return Figure.WHITE_KING.scoreValue;
    }
    return this.mBlackScore - this.mWhiteScore;
  }

  /**
   * @param square
   *         of which to get the Figure
   *
   * @return figure at the given square
   */
  public Figure getFigure ( Square square )
  {
    int y = square.fRowCount;
    int x = square.fColumnCount;
    return this.mGameBoard[y][x];
  }

  public Figure[][] getGameBoard ()
  {
    // TODO: return clone of the gameboard?!
    return mGameBoard;
  }

  /**
   * @return the taken king if one player won, else {@see Figure.EMPTY}
   */
  public Figure getTakenKing ()
  {
    return mTakenKing;
  }

  /**
   * @return the moves left on this board
   */
  public int getTurnsLeft ()
  {
    return this.mTurnsLeft;
  }

  public double getWhiteScore ()
  {
    if ( this.mTakenKing != null )
    {
      if ( this.mTakenKing.color.equals( PlayerColor.WHITE) )
      {
        return -Figure.WHITE_KING.scoreValue;
      }
      else
        return Figure.BLACK_KING.scoreValue;
    }

    return this.mWhiteScore - this.mBlackScore;
  }

  /**
   * applies the given move to the gameboard if it is valid
   *
   * @param move
   *         the move to apply
   *
   * @return true if successful, else false
   */
  public boolean makeMove ( Move move )
  {

    if ( !this.isValidMove( move ) )
    {
      return false;
    }

    move.mBlackScore = this.mBlackScore;
    move.mWhiteScore = this.mWhiteScore;

    Figure toMove = this.replaceFigure( move.fFrom, Figure.EMPTY );
    Figure replaced = this.replaceFigure( move.fTo, toMove );

    if ( move.fPlayerColor.equals( PlayerColor.BLACK ) )
    {
      this.mWhiteScore -= replaced.scoreValue;
    }
    else if ( move.fPlayerColor.equals( PlayerColor.WHITE ) )
    {
      this.mBlackScore -= replaced.scoreValue;
    }


    if ( replaced.equals( Figure.BLACK_KING ) || replaced.equals( Figure.WHITE_KING ) )
    {
      this.mTakenKing = replaced;
    }

    this.mTurnsLeft--;
    return true;
  }

  /**
   * parses a board setup from the given string
   *
   * @param s
   *         the board setup as string
   *
   * @return true if it was successful, else false
   */
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

  public String serializeBoardToString ()
  {
    StringBuilder sb = new StringBuilder();
    for ( Figure[] row : this.mGameBoard )
    {
      for ( Figure f : row )
        sb.append( f.symbol );
    }
    return sb.toString();
  }

  public void setTurnsLeft ( int turnsLeft )
  {
    mTurnsLeft = turnsLeft;
  }

  /**
   * @return
   *
   * @see Object#toString()
   */
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

  /**
   * checks if the given move is valid or not.
   * A move is only valid if the Player moves its own figures
   *
   * @param move
   *         move to check
   *
   * @return true if valid, else false
   */
  private boolean isValidMove ( Move move )
  {
    Figure figure = this.getFigure( move.fFrom );
    if ( !figure.color.equals( move.fPlayerColor ) )
      return false;

    return true;
  }

  /**
   * Replaces a Figure at the given square using the given Figure for replacement.
   * If the Figure to replace the Square with is a Pawn, and the Pawn is able to promote,
   * then it will be promoted
   *
   * @param square
   *         where to replace
   * @param replacement
   *         Figure to replace with
   *
   * @return the replaced Figure
   */
  private Figure replaceFigure ( Square square, Figure replacement )
  {
    int y = square.fRowCount;
    int x = square.fColumnCount;
    if ( ( y == 0 ) && replacement.equals( Figure.WHITE_PAWN ) )
    {
      replacement = Figure.WHITE_QUEEN;
      this.mWhiteScore -= Figure.WHITE_PAWN.scoreValue;
      this.mWhiteScore += Figure.WHITE_QUEEN.scoreValue;
    }
    else if ( ( y == ( GameBoard.VERTICAL_SIZE - 1 ) ) &&
            replacement.equals( Figure.BLACK_PAWN ) )
    {
      replacement = Figure.BLACK_QUEEN;
      this.mBlackScore -= Figure.BLACK_PAWN.scoreValue;
      this.mBlackScore += Figure.BLACK_QUEEN.scoreValue;
    }

    Figure toReturn = this.getFigure( square );
    this.mGameBoard[y][x] = replacement;

    return toReturn;
  }

  public void undoMove ( Move m )
  {
    Figure fromFigure = m.fFrom.figure;
    Figure toFigure = m.fTo.figure;

    this.mGameBoard[m.fFrom.fRowCount][m.fFrom.fColumnCount] = fromFigure;
    this.mGameBoard[m.fTo.fRowCount][m.fTo.fColumnCount] = toFigure;

    this.mBlackScore = m.mBlackScore;
    this.mWhiteScore = m.mWhiteScore;

    this.mTurnsLeft++;

    if ( toFigure.equals( Figure.BLACK_KING ) || toFigure.equals( Figure.WHITE_KING ) )
      this.mTakenKing = null;
  }

  //</editor-fold>
}

