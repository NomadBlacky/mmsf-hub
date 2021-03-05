package mmsf_hub.sss_viewer.components

import mmsf_hub.sss_viewer.model.Server
import slinky.core.FunctionalComponent
import slinky.core.annotations.react
import slinky.core.facade.ReactElement
import slinky.web.html._

@react object AccessCodeComponent {
  case class Props(server: Server)

  private val commandTable = ('A' to 'T').toList

  val component: FunctionalComponent[Props] = FunctionalComponent[Props] { props =>
    def renderRow(row: Seq[Char], rowIndex: Int): ReactElement =
      div(key := rowIndex.toString, className := "row")(
        row.zipWithIndex.map { case (k, i) =>
          div(key := i.toString, className := "key")(props.server.accessCode.orderOf(k).mkString(","))
        }
      )

    div(className := "table")(
      commandTable
        .grouped(5)
        .zipWithIndex
        .map { case (row, i) => renderRow(row, i) }
        .toSeq
    )
  }

}
