/*
 * Copyright (c) 2015 by Kristoffer Schneider
 */

package codingfalx.minichess.bot;

import codingfalx.minichess.*;

import java.io.IOException;

/**
 * @author falx
 * @created 07.05.2015
 */
public class ClientMain
{
  public static GameMaster gameMaster;
  public static IPlayer player;

  public static void main (String[] args) throws IOException
  {
    PlayerColor current = PlayerColor.WHITE;

    char c = '?'; //prefered

    player = new TimedABLookAheadBot( 800, 12000, 12 );
   // player = new RandomBot();
    GameBoard board = new GameBoard();
    board.parseBoardFromString( "kqbnrppppp..........PPPPPRNBQK" );
    player.setGameBoard( board );

    Client client = new Client ( "imcs.svcs.cs.pdx.edu" , "3589", "42", "42" );



    //c = client.offer( 'W' );
    c = client.accept( args[0], c );
    System.out.println ( c );

    PlayerColor color = ( c == 'W' ) ? PlayerColor.WHITE : PlayerColor.BLACK;
    PlayerColor opponent = ( c == 'W' ) ? PlayerColor.BLACK : PlayerColor.WHITE;
    player.setPlayerColor( color );

    String remoteMove = null;
    boolean end = false;

    do
    {
      Move m = null;
      if ( color.equals ( current ) )
      {
        m = player.makeMove();
        String s = m == null ? " " : m.toString();
        client.sendMove ( s );
        System.out.println ( c );
      }
      else
      {
        remoteMove = client.getMove();
        if ( remoteMove == null )
        {
          end = true;
          break;
        }
        m = new ScoredMove( remoteMove, opponent );
        System.out.println ( "op") ;
      }

      board.makeMove( m );
      if ( board.getTurnsLeft() <= -1 )
        end = true;

      if ( current.equals ( PlayerColor.BLACK ) )
        current = PlayerColor.WHITE;
      else
        current = PlayerColor.BLACK;
    }
    while ( !end );

    System.out.println ( "finished" );
  }
}
