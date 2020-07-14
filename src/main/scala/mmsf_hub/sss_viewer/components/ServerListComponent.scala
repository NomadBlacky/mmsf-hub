package mmsf_hub.sss_viewer.components

import mmsf_hub.sss_viewer.model.Server
import slinky.core.StatelessComponent
import slinky.core.annotations.react
import slinky.core.facade.ReactElement
import slinky.web.html._

import scala.scalajs.js

@react class ServerListComponent extends StatelessComponent {
  case class Props(servers: Seq[Server], selectedServerId: Int, onClick: Int => Unit)

  def render(): ReactElement =
    ul(className := "server")(
      props.servers.map { server =>
        val bg = if (props.selectedServerId == server.id) "lightgreen" else "inherit"
        li(
          key := server.id.toString,
          onClick := (() => props.onClick(server.id)),
          style := js.Dynamic.literal(background = bg)
        )(
          s"Lv.${server.level}: ${server.name}"
        )
      }
    )

}
