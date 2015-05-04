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
  WHITE_KING ( 'K', PlayerColor.WHITE ),
  WHITE_QUEEN( 'Q', PlayerColor.WHITE ),
  WHITE_PAWN ( 'P', PlayerColor.WHITE ),
  WHITE_ROOK( 'R', PlayerColor.WHITE ),
  WHITE_BISHOP( 'B', PlayerColor.WHITE ),
  WHITE_KNIGHT ( 'N', PlayerColor.WHITE),
  BLACK_KING ( 'k', PlayerColor.BLACK ),
  BLACK_QUEEN( 'q', PlayerColor.BLACK ),
  BLACK_PAWN ( 'p', PlayerColor.BLACK ),
  BLACK_ROOK( 'r', PlayerColor.BLACK ),
  BLACK_BISHOP( 'b', PlayerColor.BLACK ),
  BLACK_KNIGHT ( 'n', PlayerColor.BLACK),
  EMPTY('.', PlayerColor.NEUTRAL );


  public final char symbol;
  public final PlayerColor color;

  Figure ( char symbol, PlayerColor color )
  {
    this.symbol = symbol;
    this.color = color;
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

