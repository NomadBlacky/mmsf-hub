package mmsf_hub.sss_viewer.components

import mmsf_hub.common.model.BattleCard
import mmsf_hub.sss_viewer.model.CardTable
import slinky.core.StatelessComponent
import slinky.core.annotations.react
import slinky.core.facade.ReactElement
import slinky.web.html._

@react class CardTableComponent extends StatelessComponent {
  case class Props(cardTable: CardTable)

  private type IdInCardTable = Int
  private type CardWithId    = (BattleCard, IdInCardTable)

  def render(): ReactElement =
    div(className := "table")(
      props.cardTable.cards.zipWithIndex
        .grouped(5)
        .zipWithIndex
        .map { case (row, i) => renderRows(row, i) }
        .toSeq
    )

  private def renderRows(row: Iterable[CardWithId], rowIndex: Int): ReactElement =
    div(key := rowIndex.toString, className := "row")(
      row.map { case (card, cId) => renderCard(card, cId) }
    )

  private def renderCard(card: BattleCard, cardId: IdInCardTable): ReactElement =
    div(key := cardId.toString, id := cardId.toString, className := "card")(card.name)
}
