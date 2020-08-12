package mmsf_hub.sss_viewer.components

import slinky.core.FunctionalComponent
import slinky.core.annotations.react
import slinky.web.html._
import typings.materialUiCore.materialUiCoreStrings.secondary
import typings.materialUiIcons.{components => Icon}

import scala.scalajs.js

@react object FavoriteButtonComponent {
  case class Props(serverId: Int, isFavorite: Boolean, onClick: Int => Unit)

  val component: FunctionalComponent[Props] = FunctionalComponent[Props] { props =>
    div(style := js.Dynamic.literal(display = "inline-block"))(
      if (props.isFavorite) {
        Icon.Star().color(secondary).onClick(_ => props.onClick(props.serverId))
      } else {
        Icon.StarBorder().color(secondary).onClick(_ => props.onClick(props.serverId))
      }
    )
  }
}
