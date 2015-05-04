/*
 * Copyright (c) 2015 by Kristoffer Schneider
 */

package codingfalx.minichess.bot.states;

import codingfalx.minichess.GameBoard;
import codingfalx.minichess.PlayerColor;

/**
 * @author falx
 * @created 04.05.2015
 */
public class GameState
{
  //<editor-fold desc="Fields">

  public final int fMaxTurns;
  private GameBoard mGameBoard;
  private int mTurnsLeft;


  //</editor-fold>

  //<editor-fold desc="Constructor">

  public GameState ( GameBoard gameBoard, int maxTurns )
  {
    this.mGameBoard = gameBoard;
    this.fMaxTurns = maxTurns;
    this.mTurnsLeft = this.fMaxTurns;
  }


  //</editor-fold>

  //<editor-fold desc="Methods">

  public GameBoard getGameBoard ()
  {
    return mGameBoard;
  }

  public int getTurnsLeft ()
  {
    return mTurnsLeft;
  }

  public void setGameBoard ( GameBoard gameBoard )
  {
    mGameBoard = gameBoard;
  }

  public void setTurnsLeft ( int turnsLeft )
  {
    mTurnsLeft = turnsLeft;
  }

  //</editor-fold>
}
