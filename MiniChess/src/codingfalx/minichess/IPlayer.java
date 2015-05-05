/*
 * Copyright (c) 2015 by Kristoffer Schneider
 */

package codingfalx.minichess;

/**
 * @author falx
 * @created 04.05.2015
 *
 *
 */
public interface IPlayer
{
  GameBoard getGameBoard ();

  PlayerColor getPlayerColor ();

  PlayerState getPlayerState ();

  /**
   * Chooses a move to make and returns it
   * @return move to make
   */
  Move makeMove ();

  void setGameBoard ( GameBoard gameBoard );

  void setPlayerColor ( PlayerColor color );
}
