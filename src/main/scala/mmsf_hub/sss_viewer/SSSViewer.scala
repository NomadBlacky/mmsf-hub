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
            CustomLocationComponent(onClick = i => setState(state.copy(customLocation = Some(i))))
          ),
          div(className := "server-address")(
            p()("サーバアドレス"),
            ServerAddressComponent()
          )
        ),
        div(className := "server-content")(
          p()("カードテーブル"),
          CardTableComponent(props.servers.head.cardTable)
        )
      )
    )
  }
}

// FIXME: Cannot apply it...
@JSImport("resources/SSSViewer.css", JSImport.Default)
@js.native
object SSSViewerCSS extends js.Object
