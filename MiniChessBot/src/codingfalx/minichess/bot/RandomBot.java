/*
 * Copyright (c) 2015 by Kristoffer Schneider
 */

package codingfalx.minichess.bot;

import codingfalx.minichess.GameBoard;
import codingfalx.minichess.Move;
import codingfalx.minichess.PlayerColor;
import codingfalx.minichess.PlayerState;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

/**
 * @author falx
 * @created 05.05.2015
 */
public class RandomBot extends ChessBot
{
  //<editor-fold desc="Fields">


  //</editor-fold>

  //<editor-fold desc="Constructor">

  public RandomBot ( GameBoard gameBoard, PlayerColor playerColor, PlayerState playerState )
  {
    this.mGameBoard = gameBoard;
    this.mPlayerColor = playerColor;
    this.mPlayerState = playerState;
  }

  public RandomBot ()
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

    if ( moves.length > 0 )
    {
      Random rnd = new Random();
      int nextMoveIdx = rnd.nextInt( moves.length );
      moveToMake = moves[nextMoveIdx];
    }

    this.mPlayerState = PlayerState.WAITING;
    return moveToMake;
  }




  //</editor-fold>
}
