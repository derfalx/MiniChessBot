/*
 * Copyright (c) 2015 by Kristoffer Schneider
 */

package codingfalx.minichess.bot;

import codingfalx.minichess.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.TimeoutException;

import static java.lang.Math.max;

/**
 * @author falx
 * @created 07.05.2015
 */
public class TimedABLookAheadBot
        extends ChessBot
{
  //<editor-fold desc="Fields">

  private TimedABLookAheadBot mBlackNMBot;
  private int mMaxDepth;
  private HashMap<Double, ArrayList<ScoredMove>> mMoveScoringStorage;
  private long mRecursionsUntilTimeCheck;
  private long mTimeForMoveCalculation;
  private TimedABLookAheadBot mWhiteNMBot;

  //</editor-fold>

  //<editor-fold desc="Constructor">

  public TimedABLookAheadBot ( int maxDepth, long timeForMoveCalculation, long recusionsUntilTimeCheck )
  {
    this.mPlayerState = PlayerState.WAITING;

    this.mMaxDepth = maxDepth;
    this.mTimeForMoveCalculation = timeForMoveCalculation;
    this.mRecursionsUntilTimeCheck = recusionsUntilTimeCheck;
  }
  //</editor-fold>


  //<editor-fold desc="Methods">

  @Override
  public TimedABLookAheadBot clone ()
  {
    TimedABLookAheadBot bot = new TimedABLookAheadBot( this.mMaxDepth, this.mTimeForMoveCalculation, this
            .mRecursionsUntilTimeCheck );
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
    moveToMake = this.negamax( bot, this.mGameBoard.clone(), -150_000d, 150_000d );

    this.mPlayerState = PlayerState.WAITING;

    return moveToMake;
  }

  /**
   * chooses the best move out of the global move map
   *
   * @param chooseRnd
   *         wether to choose a random move out of the best, if there are many moves with the same score
   * @param score
   *         the expected maximum score
   *
   * @return move with a maximum score
   */
  private ScoredMove getBestMove ( boolean chooseRnd, double score )
  {
    if ( this.mMoveScoringStorage.size() == 0 || this.mGameBoard.getTakenKing() != null )
      return null;

    double max = Collections.max( this.mMoveScoringStorage.keySet() );
    assert ( max == score );
    ArrayList<ScoredMove> maxScoredMoves = this.mMoveScoringStorage.get( max );
   // if ( maxScoredMoves.size() == 1 )
    return maxScoredMoves.get( 0 );
    //else
/*    if ( chooseRnd )
    {
      Random rnd = new Random();
      int index = rnd.nextInt( maxScoredMoves.size() );
      return maxScoredMoves.get( index );
    }
    else
    {
      ScoredMove toRet = maxScoredMoves.get( 0 );
      for ( ScoredMove m : maxScoredMoves )
      {
        if ( toRet.getDeltaScore() < m.getDeltaScore() )
          toRet = m;
      }
      return toRet;
    }*/
  }

  /**
   * Adds the given move and score to the move map
   *
   * @param scoreValue
   *         the score assocciated with move
   * @param move
   *         the move assocciated with the score
   */
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

  /**
   * negamax algorithm using Alpha-Beta-Pruning with timed search
   *
   * @param bot
   *         the bot used to generate the next possible moves
   * @param board
   *         the board on which to do the next moves
   * @param depth
   *         current depth of recursion
   * @param alpha
   *         worst-possible-value
   * @param beta
   *         best-possible-value
   * @param onTop
   *         if it is the first recursion
   *
   * @return score of the best possible move(s)
   */
  private double negamax ( ChessBot bot, GameBoard board, int depth, double alpha, double beta, boolean onTop, final
  long
          whenToStop, long currentRecursion, final long amountRecursionUntilTimeCheck ) throws TimeoutException
  {
    if ( (currentRecursion % amountRecursionUntilTimeCheck) == 0 )
      if ( System.currentTimeMillis() >= whenToStop )
        throw new TimeoutException();

    bot.setGameBoard( board );

    if ( board.getTurnsLeft() <= 0 )
    {
      return 0.0d;
    }

    if ( board.getTakenKing() != null
            || depth <= 0 )
    {
      if ( bot.getPlayerColor().equals( PlayerColor.BLACK ) )
        return board.getBlackScore();
      else
        return board.getWhiteScore();
    }


    ChessBot nextBot = bot.getPlayerColor().equals( PlayerColor.BLACK ) ? this.mWhiteNMBot : this.mBlackNMBot;

    ArrayList<ScoredMove> moves = new ArrayList<>( 5 );
    moves.addAll( bot.scanWholeGameBoard() );
    ScoredMove move = moves.remove( 0 );

/*    GameBoard firstMoveBoard = board.clone();
    firstMoveBoard.makeMove( move );
    double firstScoreValue = -( negamax( nextBot, firstMoveBoard, depth - 1, -beta, -alpha, false, whenToStop,
                                         ++currentRecursion, amountRecursionUntilTimeCheck ) );*/
    board.makeMove( move );
    double firstScoreValue = -( negamax( nextBot, board, depth - 1, -beta, -alpha, false, whenToStop,
                                         ++currentRecursion, amountRecursionUntilTimeCheck ) );
    board.undoMove( move );


    if ( onTop )
      this.insertMoveIntoStorage( firstScoreValue, move );

    if ( firstScoreValue > beta )
      return firstScoreValue;

    alpha = max( alpha, firstScoreValue );

    for ( ScoredMove m : moves )
    {

     /*GameBoard elseMoveBoard = board.clone();
      elseMoveBoard.makeMove( m );
      double elseScoreValue = -( negamax( nextBot, elseMoveBoard, depth - 1, -beta, -alpha, false, whenToStop,
                                          ++currentRecursion, amountRecursionUntilTimeCheck ) );*/
      board.makeMove( m );
      double elseScoreValue = -( negamax( nextBot, board, depth - 1, -beta, -alpha, false, whenToStop,
                                           ++currentRecursion, amountRecursionUntilTimeCheck ) );
      board.undoMove( m );

      if ( onTop )
        this.insertMoveIntoStorage( elseScoreValue, m );

      if ( elseScoreValue >= beta )
        return elseScoreValue;

      firstScoreValue = max( firstScoreValue, elseScoreValue );
      alpha = max( alpha, elseScoreValue );
    }

    return firstScoreValue;
  }

  /**
   * Starts the process of getting the best move by using a timed based Alpha-Beta-Pruned Lookahead search
   *
   * @param bot
   *         bot to use for the first calculation
   * @param board
   *         board to use for the first calculation
   * @param alpha
   *         alpha starting value
   * @param beta
   *         beta starting value
   *
   * @return the best move
   */
  private ScoredMove negamax ( ChessBot bot, GameBoard board, double alpha, double beta )
  {
    ScoredMove bestMove = null;
    double bestScore = Double.MIN_VALUE;
    boolean end = false;
    int currentDepth = 4;
    long endTime = System.currentTimeMillis() + this.mTimeForMoveCalculation;
    int depth = this.mMaxDepth;
    boolean rnd = false;
//    if ( (80 - this.mGameBoard.getTurnsLeft()) <= 2 )
 //   {
  //    depth = 1;
  //    currentDepth = 1;
      //rnd = true;
  //  }
    do
    {
      try
      {
        bestScore = this.negamax( bot, board, currentDepth, alpha, beta, true, endTime, 0, this
                .mRecursionsUntilTimeCheck );
        bestMove = this.getBestMove( rnd, bestScore );
        currentDepth++;
        this.mMoveScoringStorage.clear();
      }
      catch ( TimeoutException e )
      {
        //e.printStackTrace();
        int i = 0;
        end = true;
      }
    }
    while ( !end && currentDepth < depth );

    System.out.println ( currentDepth - 1 );
    return bestMove;
  }
  //</editor-fold>
}
