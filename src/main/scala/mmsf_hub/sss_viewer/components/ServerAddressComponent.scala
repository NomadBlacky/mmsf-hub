package mmsf_hub.sss_viewer.components

import slinky.core.StatelessComponent
import slinky.core.annotations.react
import slinky.core.facade.ReactElement
import slinky.web.html.{button, className, div}

@react class ServerAddressComponent extends StatelessComponent {
  type Props = Unit

  override def render(): ReactElement =
    div(className := "table")(
      div()(button()("A")),
      div()(button()("B")),
      div()(button()("C"))
    )

}
