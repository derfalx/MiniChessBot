/*
 * Copyright (c) 2015 by Kristoffer Schneider
 */

package codingfalx.minichess.bot;

import codingfalx.minichess.Move;
import codingfalx.minichess.ScoredMove;

import java.util.ArrayList;

/**
 * @author falx
 * @created 07.05.2015
 */
public class OpeningBook
{
  //<editor-fold desc="Fields">

  public static ArrayList<ScoredMove> openingMoves;

  //</editor-fold>

  //<editor-fold desc="Constructor">

  static
  {
    OpeningBook.openingMoves = new ArrayList<>();

  }

  private OpeningBook()
  {

  }

  //</editor-fold>

  //<editor-fold desc="Methods">


  //</editor-fold>
}
