package mmsf_hub.sss_viewer

import mmsf_hub.sss_viewer.components._
import mmsf_hub.sss_viewer.model.Server
import org.scalajs.dom.ext.LocalStorage
import slinky.core.Component
import slinky.core.annotations.react
import slinky.core.facade.ReactElement
import slinky.web.html._

import scala.scalajs.js
import scala.scalajs.js.JSON
import scala.scalajs.js.annotation.JSImport
import scala.util.Try

@react class SSSViewer extends Component {
  case class Props(servers: Seq[Server])
  case class State(
      selectedServerId: Int,
      customLocation: Option[Int],
      serverAddress: Option[Int],
      favoriteServerIds: js.Array[Int]
  )

  private final val FavoriteSeverIdsStorageKey = "favoriteServerIds"

  def initialState: State = State(props.servers.head.id, None, None, loadFavoriteServerIdsOrEmpty)

  def render(): ReactElement = {
    val selectedServer = props.servers.find(_.id == state.selectedServerId).getOrElse(props.servers.head)

    div(className := "sss-viewer-root")( // root
      div(className := "contains")(
        div(className := "server-list")( // side menu (server list)
          h3("サーバリスト"),
          ServerListComponent(
            servers = props.servers.filter(s => state.favoriteServerIds.contains(s.id)),
            selectedServerId = state.selectedServerId,
            favoriteServerIds = state.favoriteServerIds,
            onClickServer = i => setState(state.copy(selectedServerId = i)),
            onToggleFavorite = onToggleFavorite
          ),
          typings.materialUiCore.components.Divider(),
          ServerListComponent(
            servers = props.servers,
            selectedServerId = state.selectedServerId,
            favoriteServerIds = state.favoriteServerIds,
            onClickServer = i => setState(state.copy(selectedServerId = i)),
            onToggleFavorite = onToggleFavorite
          )
        ),
        div(className := "main")( // main contents
          h2(className := "header")("SSS Viewer"),
          div(className := "interface")(
            div(className := "inputs")(
              div(className := "server-name")(
                FavoriteButtonComponent(
                  serverId = selectedServer.id,
                  isFavorite = state.favoriteServerIds.contains(selectedServer.id),
                  onClick = onToggleFavorite
                ),
                h3(style := js.Dynamic.literal(display = "inline-block", marginLeft = "0.5em"))(
                  s"Lv.${selectedServer.level}: ${selectedServer.name}"
                )
              ),
              div(className := "server-position")(
                p()("カスタム画面位置"),
                CustomLocationComponent(
                  selectedLocation = state.customLocation,
                  onClick = i => setState(state.copy(customLocation = Some(i), serverAddress = None))
                )
              ),
              div(className := "server-address")(
                p()("サーバアドレス"),
                ServerAddressComponent(
                  selectedAddress = state.serverAddress,
                  onClick = i => setState(state.copy(serverAddress = Some(i)))
                )
              )
            ),
            div(className := "server-content")(
              CardTableComponent(selectedServer, calcSelectedCardIndexes())
            )
          )
        )
      )
    )
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

  private def loadFavoriteServerIdsOrEmpty: js.Array[Int] =
    Try {
      JSON.parse(LocalStorage.apply(FavoriteSeverIdsStorageKey).getOrElse("[]")).asInstanceOf[js.Array[Int]]
    }.recover {
      case e =>
        js.Dynamic.global.console.warn(e.getMessage)
        LocalStorage.remove(FavoriteSeverIdsStorageKey)
        js.Array[Int]()
    }.get

  private def onToggleFavorite(serverId: Int): Unit = {
    if (state.favoriteServerIds.contains(serverId)) {
      setState(state.copy(favoriteServerIds = state.favoriteServerIds.subtractOne(serverId)))
    } else {
      setState(state.copy(favoriteServerIds = state.favoriteServerIds.addOne(serverId)))
    }
    LocalStorage(FavoriteSeverIdsStorageKey) = JSON.stringify(state.favoriteServerIds)
  }
}

// FIXME: Cannot apply it...
@JSImport("resources/SSSViewer.css", JSImport.Default)
@js.native
object SSSViewerCSS extends js.Object
