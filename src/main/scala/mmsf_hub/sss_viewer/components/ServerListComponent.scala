package mmsf_hub.sss_viewer.components

import mmsf_hub.sss_viewer.model.Server
import slinky.core.StatelessComponent
import slinky.core.annotations.react
import slinky.core.facade.ReactElement
import typings.materialUiCore.{components => Mui}
import typings.react.mod.CSSProperties

@react class ServerListComponent extends StatelessComponent {
  case class Props(servers: Seq[Server], selectedServerId: Int, onClick: Int => Unit)

  def render(): ReactElement =
    Mui.List.className("server")(
      props.servers.map { server =>
        val bg = if (props.selectedServerId == server.id) "lightgreen" else "inherit"
        Mui.ListItem
          .withKey(server.id.toString)
          .style(CSSProperties().setBackground(bg))
          .onClick(_ => props.onClick(server.id))(
            s"Lv.${server.level}: ${server.name}"
          )
      }
    )

}
