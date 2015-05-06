/*
 * Copyright (c) 2015 by Kristoffer Schneider
 */

package codingfalx.minichess;

import codingfalx.minichess.Move;
import codingfalx.minichess.PlayerColor;
import codingfalx.minichess.Square;

/**
 * @author falx
 * @created 05.05.2015
 */
public class ScoredMove
        extends Move
{
  //<editor-fold desc="Fields">

  private double fDeltaScore;

  //</editor-fold>

  //<editor-fold desc="Constructor">

  public ScoredMove ( Square from, Square to, PlayerColor playerColor )
  {
    super( from, to, playerColor );
  }

  public ScoredMove ( String moveStr, PlayerColor playerColor )
  {
    super( moveStr, playerColor );
  }

  public ScoredMove ( Square from, Square to, PlayerColor color, double score )
  {
    super( from, to, color );
    this.fDeltaScore = score;
  }


  //</editor-fold>

  //<editor-fold desc="Methods">

  public double getDeltaScore ()
  {
    return fDeltaScore;
  }

  public void setDeltaScore ( double fDeltaScore )
  {
    this.fDeltaScore = fDeltaScore;
  }

  //</editor-fold>
}
