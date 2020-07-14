package mmsf_hub.sss_viewer.components

import slinky.core.StatelessComponent
import slinky.core.annotations.react
import slinky.core.facade.ReactElement
import slinky.web.html.{button, className, div, key}

@react class CustomLocationComponent extends StatelessComponent {
  type Props = Unit

  def render(): ReactElement =
    div(className := "table")(
      div()(
        (1 to 3).map(i => button(key := i.toString)(i))
      ),
      div()(
        (4 to 6).map(i => button(key := i.toString)(i))
      )
    )

}
