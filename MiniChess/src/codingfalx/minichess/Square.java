/*
 * Copyright (c) 2015 by Kristoffer Schneider
 */

package codingfalx.minichess;

/**
 * @author falx
 * @created 04.05.2015
 *
 * Represents a Position on the GameBoard using a numerical and symbolic
 * representation
 */
public class Square
{
  //<editor-fold desc="Fields">

  public final char fColumn;
  public final int fColumnCount;
  public final char fRow;
  public final int fRowCount;

  //</editor-fold>

  //<editor-fold desc="Constructor">

  public Square ( int column, int row )
  {
    if ( !this.isValid( column, row ) )
      throw new IllegalArgumentException( "Column or Row are out of board index!" );

    this.fRowCount = row;
    this.fColumnCount = column;

    this.fColumn = (char) ( 'a' + column );
    this.fRow = (char) ( '1' + ( 5 - row ) );
  }

  public Square ( char column, char row )
  {
    column = Character.toLowerCase( column );
    row = Character.toLowerCase( row );

    this.fColumnCount = column - 'a';
    this.fRowCount = ( '1' - row ) + 6 - 1;

    if ( !this.isValid( this.fColumnCount, this.fRowCount ) )
      throw new IllegalArgumentException( "Column or Row are out of board index!" );

    this.fColumn = column;
    this.fRow = row;
  }

  @Override
  public String toString ()
  {
    return fColumn + "" + fRow;
  }


  //</editor-fold>

  //<editor-fold desc="Methods">

  private boolean isValid ( int column, int row )
  {
    if ( column > GameBoard.HORIZONTAL_SIZE ||
            column < 0 )
      return false;
    if ( row > GameBoard.VERTICAL_SIZE ||
            row < 0 )
      return false;

    return true;
  }

  //</editor-fold>
}
