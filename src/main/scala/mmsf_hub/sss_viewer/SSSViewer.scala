package mmsf_hub.sss_viewer

import slinky.core.StatelessComponent
import slinky.core.annotations.react
import slinky.core.facade.ReactElement
import slinky.web.html._

@react class SSSViewer extends StatelessComponent {
  override type Props = Unit

  override def render(): ReactElement = {
    div()("foofoofoo")
  }
}
