/*
 * Copyright (c) 2015 by Kristoffer Schneider
 */

package codingfalx.minichess;

/**
 * @author falx
 * @created 04.05.2015
 */
public class Move
{
  //<editor-fold desc="Fields">

  public final Square fFrom;
  public final PlayerColor fPlayerColor;
  public final Square fTo;

  //</editor-fold>

  //<editor-fold desc="Constructor">

  public Move ( Square from, Square to, PlayerColor playerColor )
  {
    this.fFrom = from;
    this.fTo = to;
    this.fPlayerColor = playerColor;
  }

  public Move ( String moveStr, PlayerColor playerColor )
  {
    if ( moveStr.length() != 5 )
      throw new IllegalArgumentException();

    this.fPlayerColor = playerColor;
    String[] fromTo = moveStr.split( "-" );
    this.fFrom = new Square( fromTo[0].charAt( 0 ), fromTo[0].charAt( 1 ) );
    this.fTo = new Square( fromTo[1].charAt( 0 ), fromTo[1].charAt( 1 ) );
  }

  @Override
  public String toString()
  {
    return "FROM: " + this.fFrom.toString() + " TO: " + this.fTo.toString();
  }

  //</editor-fold>

}
