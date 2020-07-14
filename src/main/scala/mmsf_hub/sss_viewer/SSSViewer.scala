package mmsf_hub.sss_viewer

import mmsf_hub.sss_viewer.components.{
  CardTableComponent,
  CustomLocationComponent,
  ServerAddressComponent,
  ServerListComponent
}
import mmsf_hub.sss_viewer.model.Server
import slinky.core.Component
import slinky.core.annotations.react
import slinky.core.facade.ReactElement
import slinky.web.html._

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

@react class SSSViewer extends Component {
  case class Props(servers: Seq[Server])
  case class State(selectedServerIndex: Int, customLocation: Option[Int], serverAddress: Option[Int])

  def initialState: State = State(0, None, None)

  def render(): ReactElement = {
    div(className := "sss-viewer-root")( // root
      div(className := "server-list")(   // side menu (server list)
        p()("サーバリスト"),
        ServerListComponent(props.servers)
      ),
      div(className := "main")( // main contents
        div(className := "inputs")(
          div(className := "server-position")(
            p()("カスタム画面位置"),
            CustomLocationComponent(onClick = i => setState(state.copy(customLocation = Some(i), serverAddress = None)))
          ),
          div(className := "server-address")(
            p()("サーバアドレス"),
            ServerAddressComponent(onClick = i => setState(state.copy(serverAddress = Some(i))))
          )
        ),
        div(className := "server-content")(
          p()("カードテーブル"),
          p()(showSelected()),
          CardTableComponent(props.servers.head.cardTable, calcSelectedCardIndexes())
        )
      )
    )
  }

  private def showSelected(): String = {
    val addressNames = IndexedSeq("A", "B", "C")
    val locStr       = state.customLocation.fold("")(i => (i + 1).toString)
    val addrStr      = state.serverAddress.fold("")(addressNames(_))
    s"$locStr-$addrStr"
  }

  private def calcSelectedCardIndexes(): Set[Int] = {
    (state.customLocation, state.serverAddress) match {
      case (Some(loc), None) =>
        val start = (loc % 3) + (loc / 3 * 5)
        val indexes = for {
          y <- 0 to 5
          x <- 0 to 2
        } yield start + (y * 5) + x
        indexes.toSet
      case (Some(loc), Some(addr)) =>
        val start = (loc % 3) + (loc / 3 * 5) + (addr * 10)
        val indexes = for {
          y <- 0 to 1
          x <- 0 to 2
        } yield start + (y * 5) + x
        indexes.toSet
      case _ =>
        Set.empty
    }
  }
}

// FIXME: Cannot apply it...
@JSImport("resources/SSSViewer.css", JSImport.Default)
@js.native
object SSSViewerCSS extends js.Object
