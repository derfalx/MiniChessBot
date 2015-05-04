/*
 * Copyright (c) 2015 by Kristoffer Schneider
 */

package codingfalx.minichess.bot;

import codingfalx.minichess.*;
import codingfalx.minichess.bot.states.PlayerState;
import com.sun.media.jfxmedia.events.PlayerStateEvent;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author falx
 * @created 04.05.2015
 */
public class ChessBot
        implements IPlayer
{
  //<editor-fold desc="Fields">

  private GameBoard mGameBoard;
  private PlayerColor mPlayerColor;
  private PlayerState mPlayerState;


  //</editor-fold>

  //<editor-fold desc="Constructor">

  public ChessBot ( GameBoard gameBoard, PlayerColor playerColor, PlayerState playerState )
  {
    this.mGameBoard = gameBoard;
    this.mPlayerColor = playerColor;
    this.mPlayerState = playerState;
  }


  //</editor-fold>

  //<editor-fold desc="Methods">

  @Override
  public GameBoard getGameBoard ()
  {
    return mGameBoard;
  }

  @Override
  public PlayerColor getPlayerColor ()
  {
    return mPlayerColor;
  }

  @Override
  public PlayerStateEvent.PlayerState getPlayerState ()
  {
    return null;
  }

  public Move makeMove ()
  {
    assert ( this.mPlayerState.equals( PlayerState.WAITING ) );
    this.mPlayerState = PlayerState.ACTIVE;
    Move moveToMake = null;


    this.mPlayerState = PlayerState.WAITING;
    return moveToMake;
  }

  public Collection<Move> moveList ( Square square )
  {
    Figure figure = this.mGameBoard.getFigure( square );
    Collection<Move> moves = new ArrayList<>();
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
        for ( dx = -1; dx <= 1; dx = dx + 2 )
        {
          for ( dy = -1; dy <= 1; dy = dy + 2 )
          {
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
            dy  = -dy;
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
        Collection<Move> movesTmp;

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
        moves.addAll( this.moveScan( square, 0 /*=dx*/, dy, stopShort, capture ) );

        break;

      case EMPTY:
        break;
    }

    return moves;
  }

  public Collection<Move> moveScan ( Square from, int dx, int dy, boolean capture, boolean stopShort )
  {
    int x = from.fRowCount;
    int y = from.fColumnCount;

    PlayerColor color = this.mGameBoard.getFigure( from ).color;
    assert ( color.equals( this.mPlayerColor ) );
    Collection<Move> moves = new ArrayList<>();

    do
    {
      x = x + dx;
      y = y + dy;
      if ( !( x < GameBoard.HORIZONTAL_SIZE
              && y < GameBoard.VERTICAL_SIZE
              && x >= 0
              && y >= 0) )
        break;

      PlayerColor figureColor = this.mGameBoard.getFigure( new Square( x, y ) ).color;
      if ( !figureColor.equals( PlayerColor.NEUTRAL ) )
      {
        if ( figureColor.equals( color ) )
          break;

        if ( !capture )
          break;

        stopShort = true;
      }
      Square to = new Square( y, x );
      moves.add( new Move( from, to, color ) );
    }
    while ( !stopShort );

    return moves;
  }

  public Collection<Move> moveScan ( Square from, int dx, int dy )
  {
    return this.moveScan( from, dx, dy, true, false );
  }

  public Collection<Move> moveScan ( Square from, int dx, int dy, boolean stopShort )
  {
    return this.moveScan( from, dx, dy, true, stopShort );
  }

  @Override
  public void setGameBoard ( GameBoard gameBoard )
  {
    mGameBoard = gameBoard;
  }

  @Override
  public void setPlayerColor ( PlayerColor playerColor )
  {
    mPlayerColor = playerColor;
  }

  //</editor-fold>
}
