/*
 * Copyright (c) 2015 by Kristoffer Schneider
 */

package codingfalx.minichess;

/**
 * @author falx
 * @created 05.05.2015
 */
public abstract class AbstractPlayer
        implements IPlayer
{
  //<editor-fold desc="Fields">

  protected GameBoard mGameBoard;
  protected PlayerColor mPlayerColor;
  protected PlayerState mPlayerState;

  //</editor-fold>

  //<editor-fold desc="Methods">

  @Override
  public abstract Move makeMove ();

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
  public PlayerState getPlayerState ()
  {
    return mPlayerState;
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
