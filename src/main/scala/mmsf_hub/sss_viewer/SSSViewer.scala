package mmsf_hub.sss_viewer

import mmsf_hub.sss_viewer.model.Server
import slinky.core.StatelessComponent
import slinky.core.annotations.react
import slinky.core.facade.ReactElement
import slinky.web.html._

@react class SSSViewer extends StatelessComponent {
  case class Props(servers: Seq[Server])

  override def render(): ReactElement = {
    div()(   // root
      div()( // side menu (server list)
        ul()(
          props.servers.map(s => li()(s"Lv.${s.level}: ${s.name}"))
        )
      ),
      div()( // main contents
      )
    )
  }
}
