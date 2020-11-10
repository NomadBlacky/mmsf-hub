package mmsf_hub.sss_viewer.model

import mmsf_hub.domain.BattleCard

/** The battle card table of SSS
  *
  * @param cards 6x7
  */
case class CardTable(cards: Vector[BattleCard]) {
  require(cards.size == 35)
}

object CardTable {
  def getLocationFromIndex(index: Int): (Int, Int) = (index % 5, index / 5)
}
