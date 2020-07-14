package mmsf_hub.sss_viewer.components

import mmsf_hub.common.model.BattleCard
import mmsf_hub.sss_viewer.model.CardTable
import slinky.core.StatelessComponent
import slinky.core.annotations.react
import slinky.core.facade.ReactElement
import slinky.web.html._

import scala.scalajs.js

@react class CardTableComponent extends StatelessComponent {
  case class Props(cardTable: CardTable, selectedCardIndexes: Set[Int])

  private type IndexInCardTable = Int
  private type CardWithIndex    = (BattleCard, IndexInCardTable)

  def render(): ReactElement =
    div(className := "table")(
      props.cardTable.cards.zipWithIndex
        .grouped(5)
        .zipWithIndex
        .map { case (row, i) => renderRows(row, i) }
        .toSeq
    )

  private def renderRows(row: Iterable[CardWithIndex], rowIndex: Int): ReactElement =
    div(key := rowIndex.toString, className := "row")(
      row.map { case (card, cId) => renderCard(card, cId) }
    )

  private def renderCard(card: BattleCard, index: IndexInCardTable): ReactElement = {
    val bg = if (props.selectedCardIndexes.contains(index)) "lightgreen" else "inherit"
    div(
      key := index.toString,
      id := index.toString,
      className := "card",
      style := js.Dynamic.literal(background = bg)
    )(card.name)
  }
}
