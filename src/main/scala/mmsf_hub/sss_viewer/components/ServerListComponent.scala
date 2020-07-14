package mmsf_hub.sss_viewer.components

import mmsf_hub.sss_viewer.model.Server
import slinky.core.StatelessComponent
import slinky.core.annotations.react
import slinky.core.facade.ReactElement
import slinky.web.html.{className, key, li, ul}

@react class ServerListComponent extends StatelessComponent {
  case class Props(servers: Seq[Server])

  def render(): ReactElement =
    ul(className := "server")(
      props.servers.zipWithIndex.map {
        case (s, i) => li(key := i.toString)(s"Lv.${s.level}: ${s.name}")
      }
    )

}
