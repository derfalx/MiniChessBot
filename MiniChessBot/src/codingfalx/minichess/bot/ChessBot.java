/*
 * Copyright (c) 2015 by Kristoffer Schneider
 */

package codingfalx.minichess.bot;

import codingfalx.minichess.*;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author falx
 * @created 05.05.2015
 */
public abstract class ChessBot
        extends AbstractPlayer
{

  //<editor-fold desc="Constructor">


  //</editor-fold>

  //<editor-fold desc="Methods">

  public abstract Move makeMove ();

  /**
   * Scans for <b>all</b> possible moves for the figure at the given position
   *
   * @param square
   *         position for which to search
   *
   * @return a list of all possible moves
   */
  public Collection<ScoredMove> moveList ( Square square )
  {
    Figure figure = this.mGameBoard.getFigure( square );
    Collection<ScoredMove> moves = new ArrayList<>();
    int dx;
    int dy;
    boolean stopShort = false;
    boolean capture = true;

    switch ( figure )
    {
      case BLACK_KING:
      case WHITE_KING:
        stopShort = true;
      case WHITE_QUEEN:
      case BLACK_QUEEN:
        for ( dx = -1; dx <= 1; dx++ )
        {
          for ( dy = -1; dy <= 1; dy++ )
          {
            if ( ( dy == 0 ) && ( dx == 0 ) )
              continue;

            moves.addAll( this.moveScan( square, dx, dy, stopShort ) );
          }
        }

        break;

      case WHITE_BISHOP:
      case BLACK_BISHOP:
        stopShort = true;
      case WHITE_ROOK:
      case BLACK_ROOK:
        dx = 1;
        dy = 0;
        if ( figure.equals( Figure.WHITE_ROOK ) || figure.equals( Figure.BLACK_ROOK ) )
          capture = true;
        else
          capture = false;

        for ( int i = 1; i <= 4; i++ )
        {
          moves.addAll( this.moveScan( square, dx, dy, capture, stopShort ) );
          int tmp = dx;
          dx = dy;
          dy = tmp;
          dy = -dy;
        }
        // if ( BISHOP )
        if ( stopShort ) // is only true if we have a BISHOP!
        {
          dx = 1;
          dy = 1;
          stopShort = false;
          capture = true;
          for ( int i = 1; i <= 4; i++ )
          {
            moves.addAll( this.moveScan( square, dx, dy, capture, stopShort ) );
            int tmp = dx;
            dx = dy;
            dy = tmp;
            dy = -dy;
          }
        }
        break;

      case WHITE_KNIGHT:
      case BLACK_KNIGHT:
        dx = 1;
        dy = 2;
        stopShort = true;
        for ( int i = 1; i <= 4; i++ )
        {
          moves.addAll( this.moveScan( square, dx, dy, stopShort ) );
          int tmp = dx;
          dx = dy;
          dy = tmp;
          dy = -dy;
        }

        dx = -1;
        dy = 2;
        for ( int i = 1; i <= 4; i++ )
        {
          moves.addAll( this.moveScan( square, dx, dy, stopShort ) );
          int tmp = dx;
          dx = dy;
          dy = tmp;
          dy = -dy;
        }

        break;

      case WHITE_PAWN:
      case BLACK_PAWN:
        dy = -1;
        if ( figure.equals( Figure.BLACK_PAWN ) )
          dy = 1;

        stopShort = true;
        Collection<ScoredMove> movesTmp;

        for ( int i = -1; i <= 1; i = i + 2 )
        {
          movesTmp = this.moveScan( square, i /*=dx*/, dy, stopShort );
          if ( movesTmp.size() == 1 )
          {
            Square to = movesTmp.toArray( new Move[1] )[0].fTo;
            Figure toFig = this.mGameBoard.getFigure( to );
            if ( !toFig.equals( Figure.EMPTY ) )
              moves.addAll( movesTmp );
          }
        }

        capture = false;
        moves.addAll( this.moveScan( square, 0 /*=dx*/, dy, capture, stopShort ) );

        break;

      case EMPTY:
        break;
    }

    return moves;
  }

  /**
   * Scans for all possible moves by the given parameters
   *
   * @param from
   *         position where to start searching from
   * @param dx
   *         to move in x-axis
   * @param dy
   *         delta to move in y-axis
   * @param capture
   *         wether or not to capture an enemy if possible
   * @param stopShort
   *         wether or not to stop searching after the nearest positions
   *
   * @return a collection of possible moves
   */
  public Collection<ScoredMove> moveScan ( Square from, int dx, int dy, boolean capture, boolean stopShort )
  {
    int y = from.fRowCount;
    int x = from.fColumnCount;

    Figure currentFigure = this.mGameBoard.getFigure( from );
    PlayerColor color = currentFigure.color;
    assert ( color.equals( this.mPlayerColor ) );
    Collection<ScoredMove> moves = new ArrayList<>();

    double score = 0;

    do
    {
      score = 0;
      x = x + dx;
      y = y + dy;
      if ( !( x < GameBoard.HORIZONTAL_SIZE
              && y < GameBoard.VERTICAL_SIZE
              && x >= 0
              && y >= 0 ) )
        break;

      Figure figure = this.mGameBoard.getFigure( new Square( x, y ) );
      PlayerColor figureColor = figure.color;
      if ( !figureColor.equals( PlayerColor.NEUTRAL ) )
      {
        if ( figureColor.equals( color ) )
          break;

        if ( !capture )
          break;

        stopShort = true;
        score = figure.scoreValue;
      }
      if ( currentFigure.equals( Figure.BLACK_PAWN ) &&
              x == 0 &&
              y == 0)
      {
        score += Figure.BLACK_QUEEN.scoreValue;
      }
      else if ( currentFigure.equals( Figure.WHITE_PAWN ) &&
                  x == GameBoard.HORIZONTAL_SIZE - 1 &&
                  y == GameBoard.VERTICAL_SIZE -1)
      {
        score += Figure.WHITE_QUEEN.scoreValue;
      }

      Square to = new Square( x, y );
      moves.add( new ScoredMove( from, to, color, score ) );
    }
    while ( !stopShort );

    return moves;
  }

  /**
   * @param from
   * @param dx
   * @param dy
   *
   * @return
   *
   * @see GreedyBot#moveScan(Square, int, int, boolean, boolean)
   */
  public Collection<ScoredMove> moveScan ( Square from, int dx, int dy )
  {
    return this.moveScan( from, dx, dy, true, false );
  }

  /**
   * @param from
   * @param dx
   * @param dy
   * @param stopShort
   *
   * @return
   *
   * @see GreedyBot#moveScan(Square, int, int, boolean, boolean)
   */
  public Collection<ScoredMove> moveScan ( Square from, int dx, int dy, boolean stopShort )
  {
    return this.moveScan( from, dx, dy, true, stopShort );
  }

  /**
   * Scans the whole GameBoard for possible moves of the its player
   *
   * @return
   */
  public Collection<ScoredMove> scanWholeGameBoard ()
  {
    Collection<ScoredMove> moves = new ArrayList<>();

    for ( int y = 0; y < GameBoard.VERTICAL_SIZE; y++ )
    {
      for ( int x = 0; x < GameBoard.HORIZONTAL_SIZE; x++ )
      {
        Figure figure = this.mGameBoard.getGameBoard()[y][x];
        if ( figure.color.equals( this.mPlayerColor ) )
        {
          moves.addAll( this.moveList( new Square( x, y ) ) );
        }
      }
    }

    return moves;
  }

  //</editor-fold>
}
