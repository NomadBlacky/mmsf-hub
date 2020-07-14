package mmsf_hub.sss_viewer.components

import slinky.core.StatelessComponent
import slinky.core.annotations.react
import slinky.core.facade.ReactElement
import slinky.web.html._

@react class CustomLocationComponent extends StatelessComponent {
  case class Props(onClick: Int => Unit)

  def render(): ReactElement =
    div(className := "table")(
      div()(
        (0 to 2).map(i => button(key := i.toString, onClick := (() => props.onClick(i)))(i + 1))
      ),
      div()(
        (3 to 5).map(i => button(key := i.toString, onClick := (() => props.onClick(i)))(i + 1))
      )
    )

}
