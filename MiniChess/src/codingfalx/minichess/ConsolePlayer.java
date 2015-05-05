/*
 * Copyright (c) 2015 by Kristoffer Schneider
 */

package codingfalx.minichess;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author falx
 * @created 05.05.2015
 */
public class ConsolePlayer
        extends AbstractPlayer
{
  //<editor-fold desc="Fields">

  private BufferedReader mConsoleReader;

  //</editor-fold>

  //<editor-fold desc="Constructor">

  public ConsolePlayer ( GameBoard gameboard, PlayerColor playerColor, PlayerState playerState )
  {
    this.mGameBoard = gameboard;
    this.mPlayerColor = playerColor;
    this.mPlayerState = playerState;

    InputStream in = System.in;
    this.mConsoleReader = new BufferedReader( new InputStreamReader( System.in ) );
  }

  public ConsolePlayer ()
  {
    this.mPlayerState = PlayerState.WAITING;
    this.mConsoleReader = new BufferedReader( new InputStreamReader( System.in ) );
  }
  //</editor-fold>

  //<editor-fold desc="Methods">

  @Override
  public Move makeMove ()
  {
    String in = null;
    String from = null;
    String to = null;
    boolean valid = true;

    assert ( this.mConsoleReader != null );
    do
    {
      valid = true;
      System.out.println( "Please enter your turn: " );
      try
      {
        in = this.mConsoleReader.readLine();
      }
      catch ( IOException e )
      {
        e.printStackTrace();
        throw new RuntimeException( "Nope,ConsolePlayer Input Fail!" );
      }

      in = in.trim();
      if ( in.charAt( 2 ) == ':' )
      {
        String[] split = in.split( ":" );
        if ( split.length == 2 )
        {
          for ( String s : split )
          {
            if ( s.length() == 2 &&
                    Character.isLetter( s.charAt( 0 ) ) &&
                    Character.isDigit( s.charAt( 1 ) ) )
            {
              valid &= true;
            }
            else
              valid &= false;
          }

          if ( valid )
          {
            from = split[0];
            to = split[1];
          }
        }
      }
      else
        valid = false;
    }
    while ( !valid );

    Square sq_from = new Square( from.charAt( 0 ), from.charAt( 1 ) );
    Square sq_to = new Square( to.charAt( 0 ), to.charAt( 1 ) );

    Move m = new Move( sq_from, sq_to, this.mPlayerColor );

    return m;
  }

  //</editor-fold>
}
