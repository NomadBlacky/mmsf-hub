package mmsf_hub.sss_viewer

import mmsf_hub.sss_viewer.model.Server
import slinky.core.StatelessComponent
import slinky.core.annotations.react
import slinky.core.facade.ReactElement
import slinky.web.html._

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

@react class SSSViewer extends StatelessComponent {
  case class Props(servers: Seq[Server])

  override def render(): ReactElement = {
    div(className := "sss-viewer-root")( // root
      div(className := "server-list")(   // side menu (server list)
        p()("サーバリスト"),
        ul()(
          props.servers.zipWithIndex.map {
            case (s, i) => li(key := i.toString)(s"Lv.${s.level}: ${s.name}")
          }
        )
      ),
      div(className := "main")( // main contents
        div(className := "inputs")(
          div(className := "server-position")(
            p()("カスタム画面位置"),
            div(className := "table")(
              div()(
                (1 to 3).map(i => button(key := i.toString)(i))
              ),
              div()(
                (4 to 6).map(i => button(key := i.toString)(i))
              )
            )
          ),
          div(className := "server-address")(
            p()("サーバアドレス"),
            div(className := "table")(
              div()(button()("A")),
              div()(button()("B")),
              div()(button()("C"))
            )
          )
        ),
        div(className := "server-content")(
          p()("カードテーブル"),
          div(className := "table")(
            props.servers.head.cardTable.cards.zipWithIndex
              .grouped(5)
              .zipWithIndex
              .map {
                case (row, i) =>
                  div(key := i.toString, className := "row")(
                    row.map { case (card, j) => div(key := j.toString, className := "card")(card.name) }
                  )
              }
              .toSeq
          )
        )
      )
    )
  }
}

// FIXME: Cannot apply it...
@JSImport("resources/SSSViewer.css", JSImport.Default)
@js.native
object SSSViewerCSS extends js.Object
