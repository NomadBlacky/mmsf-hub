package mmsf_hub.sss_viewer.components

import mmsf_hub.sss_viewer.model.Server
import slinky.core.StatelessComponent
import slinky.core.annotations.react
import slinky.core.facade.ReactElement
import slinky.web.html._
import typings.materialUiCore.{components => Mui}
import typings.react.mod.CSSProperties

import scala.scalajs.js

@react class ServerListComponent extends StatelessComponent {
  case class Props(
      servers: Seq[Server],
      selectedServerId: Int,
      favoriteServerIds: js.Array[Int],
      onClickServer: Int => Unit,
      onToggleFavorite: Int => Unit
  )

  def render(): ReactElement =
    Mui.List.className("server")(
      props.servers.map { server =>
        val bg = if (props.selectedServerId == server.id) "lightgreen" else "inherit"
        Mui.ListItem
          .withKey(server.id.toString)
          .style(CSSProperties().setBackground(bg))
          .onClick(_ => props.onClickServer(server.id))(
            FavoriteButtonComponent(
              serverId = server.id,
              isFavorite = props.favoriteServerIds.contains(server.id),
              onClick = props.onToggleFavorite
            ),
            div(style := js.Dynamic.literal(marginLeft = "0.5em"))(
              s"Lv.${server.level}: ${server.name}"
            )
          )
      }
    )

}
