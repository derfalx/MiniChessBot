/*
 * Copyright (c) 2015 by Kristoffer Schneider
 */

package codingfalx.minichess;

/**
 * @author Kristoffer Schneider
 * @created 04.05.2015
 */
public class GameMaster
{
  //<editor-fold desc="Fields">

  private GameBoard mGameBoard;
  private IPlayer mPlayerWhite;
  private IPlayer mPlayerBlack;
  private PlayerColor mActivePlayerColor;


  //</editor-fold>

  //<editor-fold desc="Constructor">

  public GameMaster( IPlayer playerBlack, IPlayer playerWhite )
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

  public IPlayer runGame ()
  {
    IPlayer activePlayer;
    IPlayer inactivePlayer;
    int i = 0;
    Move old = null;
    while ( this.mGameBoard.getTurnsLeft() > 0 )
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
      assert ( m.fPlayerColor.equals( activePlayer.getPlayerColor() ) );
      boolean valid = this.mGameBoard.makeMove( m );
      assert ( valid );
      valid = inactivePlayer.getGameBoard().makeMove( m );
      assert ( valid );

      this.mActivePlayerColor = inactivePlayer.getPlayerColor();

      if ( (i % 2) == 0 )
      {
        System.out.println ( "ZUG: " + i );
        System.out.println( this.mGameBoard );
        System.out.println ( old );
        System.out.println ( m );
        System.out.println( "------------------" );
      }
      else
        old = m;
      i++;
    }

    Figure king = this.mGameBoard.getTakenKing();
    if ( king != null )
    {
      if ( king.color.equals( PlayerColor.WHITE ) )
        return this.mPlayerWhite;
      else
        return this.mPlayerBlack;
    }
    return null;
  }

  //</editor-fold>
}
