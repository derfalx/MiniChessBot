/*
 * Copyright (c) 2015 by Kristoffer Schneider
 */

package codingfalx.minichess.bot;

import codingfalx.minichess.*;

import static java.lang.Math.max;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

/**
 * @author falx
 * @created 06.05.2015
 */
public class ABPrunedLookAheadBot
        extends ChessBot
{
  //<editor-fold desc="Fields">

  private HashMap<Double, ArrayList<ScoredMove>> mMoveScoringStorage;
  private ABPrunedLookAheadBot mBlackNMBot;
  private int mMaxDepth;
  private ABPrunedLookAheadBot mWhiteNMBot;

  //</editor-fold>

  //<editor-fold desc="Constructor">

  public ABPrunedLookAheadBot ( int maxDepth )
  {
    this.mPlayerState = PlayerState.WAITING;

    this.mMaxDepth = maxDepth;

  }
  //</editor-fold>


  //<editor-fold desc="Methods">

  @Override
  public ABPrunedLookAheadBot clone ()
  {
    ABPrunedLookAheadBot bot = new ABPrunedLookAheadBot( this.mMaxDepth );
    bot.mGameBoard = this.mGameBoard.clone();
    bot.mPlayerColor = this.mPlayerColor;
    bot.mPlayerState = this.mPlayerState;

    return bot;
  }

  @Override
  public Move makeMove ()
  {
    this.mMoveScoringStorage = new HashMap<>( 7 );

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
    double score = this.negamax( bot, this.mGameBoard.clone(), this.mMaxDepth, Double.MIN_VALUE, Double.MAX_VALUE,
                                 true );
    moveToMake = this.getBestMove( true, score );

    this.mPlayerState = PlayerState.WAITING;

    return moveToMake;
  }

  private ScoredMove getBestMove ( boolean chooseRnd, double score )
  {
    double max = Collections.max( this.mMoveScoringStorage.keySet() );
    assert ( max == score );
    ArrayList<ScoredMove> maxScoredMoves = this.mMoveScoringStorage.get ( max );
    if ( maxScoredMoves.size() == 1 )
      return maxScoredMoves.get(0);
    //else
    if ( chooseRnd )
    {
      Random rnd = new Random();
      int index = rnd.nextInt( maxScoredMoves.size() );
      return maxScoredMoves.get ( index );
    }
    else
    {
      ScoredMove toRet = maxScoredMoves.get(0);
      for ( ScoredMove m : maxScoredMoves )
      {
        if ( toRet.getDeltaScore() < m.getDeltaScore() )
          toRet = m;
      }
      return toRet;
    }
  }

  private double negamax ( ChessBot bot, GameBoard board, int depth, double alpha, double beta, boolean onTop )
  {
    bot.setGameBoard( board );

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

    ArrayList<ScoredMove> moves = new ArrayList<>(5);
    moves.addAll( bot.scanWholeGameBoard() );
    ScoredMove move = moves.remove( 0 );

    GameBoard firstMoveBoard = board.clone();
    firstMoveBoard.makeMove( move );
    double firstScoreValue = -( negamax( nextBot, firstMoveBoard, depth - 1, -beta, -alpha, false ) );

    if ( onTop )
      this.insertMoveIntoStorage ( firstScoreValue, move );

    if ( firstScoreValue > beta )
      return firstScoreValue;

    alpha = max( alpha, firstScoreValue );

    for ( ScoredMove m : moves )
    {
      GameBoard elseMoveBoard = board.clone();
      elseMoveBoard.makeMove( m );
      double elseScoreValue = -( negamax( nextBot, elseMoveBoard, depth - 1, -beta, -alpha, false ) );

      if ( onTop )
        this.insertMoveIntoStorage( elseScoreValue, m );

      if ( elseScoreValue >= beta )
        return elseScoreValue;

      firstScoreValue = max( firstScoreValue, elseScoreValue );
      alpha = max( alpha, elseScoreValue );
    }

    return firstScoreValue;
  }

  private void insertMoveIntoStorage ( double scoreValue, ScoredMove move )
  {
    assert ( move.fPlayerColor.equals( this.mPlayerColor ) );

    ArrayList<ScoredMove> list = this.mMoveScoringStorage.get( scoreValue );
    if ( list == null )
    {
      list = new ArrayList<>( 5 );
      this.mMoveScoringStorage.put( scoreValue, list );
    }

    list.add( move );
  }
  //</editor-fold>
}
