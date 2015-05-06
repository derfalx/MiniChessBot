/*
 * Copyright (c) 2015 by Kristoffer Schneider
 */

package codingfalx.minichess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

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
    this.mGameBoard = gameBoard;
  }

  @Override
  public void setPlayerColor ( PlayerColor playerColor )
  {
    mPlayerColor = playerColor;
  }

  public void loadFromString( String s )
  {
    String[] parts = s.split( " " );
    int turnsLeft = 80 - ( Integer.parseInt( parts[0] ) * 2 );
    PlayerColor color;
    if ( parts[1].equals ( PlayerColor.BLACK.symbol ) )
      this.mPlayerColor = PlayerColor.BLACK;
    else if ( parts[1].equals( PlayerColor.WHITE.symbol ) )
      this.mPlayerColor = PlayerColor.WHITE;

    if ( this.mGameBoard == null )
      this.mGameBoard = new GameBoard();

    this.mGameBoard.parseBoardFromString( parts[2] );
    this.mGameBoard.setTurnsLeft( turnsLeft );
  }

  //</editor-fold>
}
