/*
 * Copyright (c) 2015 by Kristoffer Schneider
 */

package codingfalx.minichess.bot;

import codingfalx.minichess.*;
import codingfalx.minichess.bot.debug.GameStateWriter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author Kristoffer Schneider
 * @created 04.05.2015
 *
 * Coordinates a MiniChess-session including the Players and their GameBoards
 */
public class GameMaster
{
  //<editor-fold desc="Fields">

  private PlayerColor mActivePlayerColor;
  private GameBoard mGameBoard;
  private ChessBot mPlayerBlack;
  private ChessBot mPlayerWhite;

  private BufferedWriter writer;


  //</editor-fold>

  //<editor-fold desc="Constructor">

  public GameMaster ( ChessBot playerBlack, ChessBot playerWhite ) throws IOException
  {
    this.mGameBoard = new GameBoard();
    this.mGameBoard.parseBoardFromString( "kqbnrppppp..........PPPPPRNBQK" );
    this.mActivePlayerColor = PlayerColor.WHITE;

    this.mPlayerWhite = playerWhite;
    this.mPlayerWhite.setPlayerColor( PlayerColor.WHITE );
    this.mPlayerWhite.setGameBoard( this.mGameBoard.clone() );

    this.mPlayerBlack = playerBlack;
    this.mPlayerBlack.setPlayerColor( PlayerColor.BLACK );
    this.mPlayerBlack.setGameBoard( this.mGameBoard.clone() );


  }

  //</editor-fold>

  //<editor-fold desc="Methods">

  public PlayerColor runGame ( boolean silent ) throws IOException
  {

   // System.out.println( this.mGameBoard );

    ChessBot activePlayer;
    ChessBot inactivePlayer;
    int i = 0;
    Move old = null;
    while ( ( this.mGameBoard.getTurnsLeft() - 1 ) > 0 )
    {
      assert ( !this.mActivePlayerColor.equals( PlayerColor.NEUTRAL ) );

      if ( this.mActivePlayerColor.equals( PlayerColor.WHITE ) )
      {
        activePlayer = this.mPlayerWhite;
        inactivePlayer = this.mPlayerBlack;
      }
      else
      {
        activePlayer = this.mPlayerBlack;
        inactivePlayer = this.mPlayerWhite;
      }

      Move m = activePlayer.makeMove();
      assert ( m != null );
      assert ( m.fPlayerColor.equals( activePlayer.getPlayerColor() ) );
      boolean valid = this.mGameBoard.makeMove( m );
      assert ( valid );
      valid = activePlayer.getGameBoard().makeMove( m );
      assert ( valid );
      valid = inactivePlayer.getGameBoard().makeMove( m );
      assert ( valid );

      this.mActivePlayerColor = inactivePlayer.getPlayerColor();

      if ( i%2 == 0 )
      {
       // GameStateWriter.Instance.writeBotState( activePlayer );
      }

      if ( ( ( i % 2 ) == 0 ) &&  !silent )
      {
        System.out.println( "ZUG: " + i );
        System.out.println( this.mGameBoard );
        System.out.println( old );
        System.out.println( m );
        System.out.println( "------------------" );
      }
      else
        old = m;
      i++;

      if ( this.mGameBoard.getTakenKing() != null )
        break;
    }

    if ( this.mGameBoard.getTurnsLeft() < -1 )
      throw new RuntimeException();

    Figure king = this.mGameBoard.getTakenKing();
    if ( king != null )
    {
      if ( king.color.equals( PlayerColor.WHITE ) )
        return this.mPlayerBlack.getPlayerColor();
      else
        return this.mPlayerWhite.getPlayerColor();
    }

    writer.close();

    return PlayerColor.NEUTRAL;
  }

  //</editor-fold>
}
