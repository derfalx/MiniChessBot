/*
 * Copyright (c) 2015 by Kristoffer Schneider
 */

package codingfalx.minichess.bot;

import codingfalx.minichess.*;

import java.util.*;

/**
 * @author falx
 * @created 04.05.2015
 */
public class GreedyBot
        extends ChessBot
{
  //<editor-fold desc="Fields">


  //</editor-fold>

  //<editor-fold desc="Constructor">

  public GreedyBot ( GameBoard gameBoard, PlayerColor playerColor, PlayerState playerState )
  {
    this.mGameBoard = gameBoard;
    this.mPlayerColor = playerColor;
    this.mPlayerState = playerState;
  }

  public GreedyBot ()
  {
    this.mPlayerState = PlayerState.WAITING;
  }

  //</editor-fold>

  //<editor-fold desc="Methods">

  @Override
  public Move makeMove ()
  {
    assert ( this.mPlayerState.equals( PlayerState.WAITING ) );
    this.mPlayerState = PlayerState.ACTIVE;
    ScoredMove moveToMake = null;

    Collection<ScoredMove> movesCollection = this.scanWholeGameBoard();
    ScoredMove[] moves = new ScoredMove[movesCollection.size()];
    moves = movesCollection.toArray( moves );

/*    if ( moves.length > 0 )
    {
      Random rnd = new Random();
      int nextMoveIdx = rnd.nextInt( moves.length );
      moveToMake = moves[nextMoveIdx];
    }*/
    ArrayList<ScoredMove> scoredMoves = this.getMaxScoredMove ( moves );
    if ( scoredMoves.size() == 1 )
    {
      moveToMake = scoredMoves.get(0);
    }
    else
    {
      Random rnd = new Random();
      int nextMoveIdx = rnd.nextInt( scoredMoves.size() );
      moveToMake = scoredMoves.get(nextMoveIdx);
    }

    this.mPlayerState = PlayerState.WAITING;
    return moveToMake;
  }

  private ArrayList<ScoredMove> getMaxScoredMove ( ScoredMove[] moves )
  {
    double currentScoreSum = 0;
    double maxMoveSum = 0;
    ScoredMove move = null;
    HashMap<Double, ArrayList<ScoredMove>> scoreMap = new HashMap<>();

    if ( this.mPlayerColor.equals( PlayerColor.WHITE ) )
    {
      currentScoreSum = this.mGameBoard.getWhiteScore();
    }
    else
    {
      currentScoreSum = this.mGameBoard.getBlackScore();
    }

    for ( ScoredMove m : moves )
    {
      double score = currentScoreSum + m.getDeltaScore();
      ArrayList<ScoredMove> list = scoreMap.get( score );
      if ( list == null )
      {
        list = new ArrayList<>();
        scoreMap.put( score, list );
      }

      list.add( m );
    }

    Set<Double> keys = scoreMap.keySet();
    double max = Collections.max( keys );

    return scoreMap.get( max );
  }

  //</editor-fold>
}
