/*
 * Copyright (c) 2015 by Kristoffer Schneider
 */

package codingfalx.minichess.bot;

import codingfalx.minichess.*;
import codingfalx.minichess.bot.debug.GameStateWriter;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author falx
 * @created 05.05.2015
 */
public class LookAheadBot
        extends ChessBot
        implements Cloneable
{
  //<editor-fold desc="Fields">

  private ScoredMove mBestMove;
  private LookAheadBot mBlackNMBot;
  private int mMaxDepth;
  private LookAheadBot mWhiteNMBot;

  //</editor-fold>

  //<editor-fold desc="Constructor">

  public LookAheadBot ( int maxDepth )
  {
    this.mPlayerState = PlayerState.WAITING;

    this.mMaxDepth = maxDepth;

  }
  //</editor-fold>


  //<editor-fold desc="Methods">

  @Override
  public LookAheadBot clone ()
  {
    LookAheadBot bot = new LookAheadBot(this.mMaxDepth);
    bot.mGameBoard = this.mGameBoard.clone();
    bot.mPlayerColor = this.mPlayerColor;
    bot.mPlayerState = this.mPlayerState;

    return bot;
  }

  @Override
  public Move makeMove ()
  {
    assert ( this.mPlayerState.equals( PlayerState.WAITING ) );
    this.mPlayerState = PlayerState.ACTIVE;
    ScoredMove moveToMake = null;

    this.mBlackNMBot = this.clone();
    this.mWhiteNMBot = this.clone();

    if ( this.mPlayerColor.equals( PlayerColor.BLACK ) )
      this.mWhiteNMBot.mPlayerColor = PlayerColor.WHITE;
    else
      this.mBlackNMBot.mPlayerColor = PlayerColor.BLACK;


    ChessBot bot = this.getPlayerColor().equals( PlayerColor.BLACK ) ? this.mBlackNMBot : this.mWhiteNMBot;
    double score = this.negamax( bot, this.mGameBoard.clone(), this.mMaxDepth, true );
    moveToMake = this.mBestMove;

    this.mPlayerState = PlayerState.WAITING;

    return moveToMake;
  }

  private double negamax ( ChessBot bot, GameBoard board, int depth, boolean onTop )
  {
    bot.setGameBoard( board );

    HashMap<ScoredMove, Double> map = new HashMap<>();

    if ( board.getTurnsLeft() <= 0 )
    {
      return 0.0d;
    }

    if ( board.getTakenKing() != null
            || depth <= 0  /*|| board.getTurnsLeft() <= 0 */ )
    {
      if ( bot.getPlayerColor().equals( PlayerColor.BLACK ) )
        return board.getBlackScore();
      else
        return board.getWhiteScore();
    }

    ChessBot nextBot = bot.getPlayerColor().equals( PlayerColor.BLACK ) ? this.mWhiteNMBot : this.mBlackNMBot;

    ArrayList<ScoredMove> moves = new ArrayList<>();
    moves.addAll( bot.scanWholeGameBoard() );

    ScoredMove move = moves.remove( 0 );

    GameBoard _board = board.clone();
    _board.makeMove( move );
    double _value = -( negamax( nextBot, _board, depth - 1, false ) );

    if ( onTop )
    {
      map.put ( move, _value );
    }

    if ( onTop )
      this.mBestMove = move;

    for ( ScoredMove m : moves )
    {
      GameBoard _board_ = board.clone();
      _board_.makeMove( m );
      double value = -( negamax( nextBot, _board_, depth - 1, false ) );

      if ( onTop )
        map.put( m, value );

      if ( value > _value )
      {
        _value = value;
        if ( onTop )
          this.mBestMove = m;
      }
      else if ( value == _value && onTop )
      {
        if ( this.mBestMove.getDeltaScore() < m.getDeltaScore() )
          this.mBestMove = m;
      }
    }

    if ( false )
    {
      moves.add ( move );
      GameStateWriter.Instance.writeMoveScores( this, map );
    }

    return _value;
  }
  //</editor-fold>
}
