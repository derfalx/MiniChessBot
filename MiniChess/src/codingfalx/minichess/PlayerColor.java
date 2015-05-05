/*
 * Copyright (c) 2015 by Kristoffer Schneider
 */

package codingfalx.minichess;

public enum PlayerColor
{
  BLACK ( "B" ),
  WHITE ( "W" ),
  NEUTRAL ( "" );

  public String symbol;

  PlayerColor ( String symbol)
  {
    this.symbol = symbol;
  }
}

