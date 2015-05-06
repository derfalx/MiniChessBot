/*
 * Copyright (c) 2015 by Kristoffer Schneider
 */

package codingfalx.minichess.bot;

import codingfalx.minichess.GameBoard;
import codingfalx.minichess.PlayerColor;
import codingfalx.minichess.ScoredMove;

import java.util.ArrayList;

/**
 * @author falx
 * @created 05.05.2015
 */
public class NegamaxEvaluator
{
  //<editor-fold desc="Fields">

  private ScoredMove mBestMove;
  private ChessBot mBot;
  private GameBoard mInitialGameBoard;
  private int mMaxDepth;
  private PlayerColor mInitColor;


  //</editor-fold>

  //<editor-fold desc="Constructor">

  public NegamaxEvaluator ( ChessBot bot, int maxDepth )
  {
    this.mBot = bot;
    this.mInitialGameBoard = bot.getGameBoard();
    this.mBestMove = null;
    this.mMaxDepth = maxDepth;
    this.mInitColor = this.mBot.getPlayerColor();
  }

  //</editor-fold>

  //<editor-fold desc="Methods">

  public double negamax ( )
  {
    return this.negamax( this.mInitialGameBoard, this.mMaxDepth, true );
  }

  public double negamax ( GameBoard board, int depth, boolean onTop )
  {
    if ( board.getTakenKing() != null
            || board.getTurnsLeft() <= 0
            || depth <= 0 )
    {
      assert ( !this.mBot.getPlayerColor().equals( PlayerColor.NEUTRAL ) );
      if ( this.mBot.getPlayerColor().equals( PlayerColor.BLACK ) )
      {
          return board.getBlackScore();
      }
      else
      {
          return board.getWhiteScore();
      }
    }

    this.mBot.setGameBoard( board );

    ArrayList<ScoredMove> moves = new ArrayList<>();
    moves.addAll( this.mBot.scanWholeGameBoard() );

    int amount = moves.size();
    ScoredMove move = moves.remove( 0 );
    assert(moves.size() == amount - 1);
    GameBoard board_ = board.clone();
    boolean valid = board_.makeMove( move );
    assert ( valid );

    PlayerColor color = this.mBot.getPlayerColor();
    this.switchColor();
    double value_ =  - (negamax( board_, depth - 1, false ) );
    this.mBot.setPlayerColor( color );
    this.mBot.setGameBoard( board );

    if ( false )
    {
      System.out.print( move + "\t" );
      System.out.println ( value_ );
    }

    if ( onTop )
      this.mBestMove = move;

    for ( ScoredMove m : moves )
    {
      GameBoard board__ = board.clone();
      valid = board__.makeMove( m );
      assert ( valid );
      color = this.mBot.getPlayerColor();
      this.switchColor();
      double value =  - ( negamax( board__, depth - 1, false ) );
      this.mBot.setPlayerColor( color );
      this.mBot.setGameBoard( board );

      if ( false )
      {
        System.out.print( m + "\t" );
        System.out.println( value );
      }

      if ( value > value_ )
      {
        value_ = value;
        if ( onTop )
          this.mBestMove = m;

      }
/*      if ( value == value_ )
      {
        if ( onTop && this.mBestMove.getDeltaScore() < m.getDeltaScore() )
          this.mBestMove = m;
      }*/

    }

    return value_;
  }

  private void switchColor ()
  {
    if ( this.mBot.getPlayerColor().equals( PlayerColor.BLACK ) )
      this.mBot.setPlayerColor( PlayerColor.WHITE );
    else
      this.mBot.setPlayerColor( PlayerColor.BLACK );
  }


  public ScoredMove getBestMove ()
  {
    return mBestMove;
  }

  public ChessBot getBot ()
  {
    return mBot;
  }

  public GameBoard getInitialGameBoard ()
  {
    return mInitialGameBoard;
  }

  public int getMaxDepth ()
  {
    return mMaxDepth;
  }

  public void setBestMove ( ScoredMove bestMove )
  {
    mBestMove = bestMove;
  }

  public void setBot ( ChessBot bot )
  {
    mBot = bot;
  }

  public void setInitialGameBoard ( GameBoard initialGameBoard )
  {
    mInitialGameBoard = initialGameBoard;
  }

  public void setMaxDepth ( int maxDepth )
  {
    mMaxDepth = maxDepth;
  }


  //</editor-fold>
}
