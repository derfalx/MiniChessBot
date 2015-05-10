/*
 * Copyright (c) 2015 by Kristoffer Schneider
 */

package codingfalx.minichess;

/**
 * @author falx
 * @created 04.05.2015
 */
public enum Figure
{
  WHITE_KING( 'K', PlayerColor.WHITE, 1_000_000d ),
  WHITE_QUEEN( 'Q', PlayerColor.WHITE, 900d ),
  WHITE_PAWN( 'P', PlayerColor.WHITE, 100d ),
  WHITE_ROOK( 'R', PlayerColor.WHITE, 500d ),
  WHITE_BISHOP( 'B', PlayerColor.WHITE, 300d ),
  WHITE_KNIGHT( 'N', PlayerColor.WHITE,300d ),
  BLACK_KING( 'k', PlayerColor.BLACK, 1_000_000d ),
  BLACK_QUEEN( 'q', PlayerColor.BLACK, 900d ),
  BLACK_PAWN( 'p', PlayerColor.BLACK, 100d ),
  BLACK_ROOK( 'r', PlayerColor.BLACK, 500d ),
  BLACK_BISHOP( 'b', PlayerColor.BLACK, 300d ),
  BLACK_KNIGHT( 'n', PlayerColor.BLACK, 300d ),
  EMPTY( '.', PlayerColor.NEUTRAL, 0d );


  public final PlayerColor color;
  public final char symbol;
  public final double scoreValue;

  Figure ( char symbol, PlayerColor color, double scoreValue )
  {
    this.symbol = symbol;
    this.color = color;
    this.scoreValue = scoreValue;
  }

  public static Figure getFigure ( char symbol )
  {
    switch ( symbol )
    {
      case 'K':
        return WHITE_KING;
      case 'Q':
        return WHITE_QUEEN;
      case 'P':
        return WHITE_PAWN;
      case 'R':
        return WHITE_ROOK;
      case 'B':
        return WHITE_BISHOP;
      case 'N':
        return WHITE_KNIGHT;
      case 'k':
        return BLACK_KING;
      case 'q':
        return BLACK_QUEEN;
      case 'p':
        return BLACK_PAWN;
      case 'r':
        return BLACK_ROOK;
      case 'b':
        return BLACK_BISHOP;
      case 'n':
        return BLACK_KNIGHT;
      case '.':
        return EMPTY;
    }
    return null;
  }
}

