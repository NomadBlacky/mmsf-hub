package mmsf_hub.sss_viewer.components

import slinky.core.StatelessComponent
import slinky.core.annotations.react
import slinky.core.facade.ReactElement
import slinky.web.html._

@react class ServerAddressComponent extends StatelessComponent {
  case class Props(onClick: Int => Unit)

  override def render(): ReactElement =
    div(className := "table")(
      div()(button(onClick := (() => props.onClick(0)))("A")),
      div()(button(onClick := (() => props.onClick(1)))("B")),
      div()(button(onClick := (() => props.onClick(2)))("C"))
    )

}
