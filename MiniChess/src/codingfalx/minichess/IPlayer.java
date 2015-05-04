/*
 * Copyright (c) 2015 by Kristoffer Schneider
 */

package codingfalx.minichess;

import com.sun.media.jfxmedia.events.PlayerStateEvent.PlayerState;

/**
 * @author falx
 * @created 04.05.2015
 */
public interface IPlayer
{
  PlayerColor getPlayerColor();
  void setPlayerColor(PlayerColor color );
  GameBoard getGameBoard();
  void setGameBoard ( GameBoard gameBoard );
  PlayerState getPlayerState();
  Move makeMove();
}
