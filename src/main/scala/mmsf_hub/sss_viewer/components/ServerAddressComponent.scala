package mmsf_hub.sss_viewer.components

import slinky.core.FunctionalComponent
import slinky.core.annotations.react
import slinky.web.html._
import typings.materialUiCore.materialUiCoreStrings.outlined
import typings.materialUiCore.components.Button

@react object ServerAddressComponent {
  case class Props(onClick: Int => Unit)

  val component: FunctionalComponent[Props] = FunctionalComponent[Props] { props =>
    div(className := "table")(
      div()(Button.variant(outlined).onClick(_ => props.onClick(0))("A")),
      div()(Button.variant(outlined).onClick(_ => props.onClick(1))("B")),
      div()(Button.variant(outlined).onClick(_ => props.onClick(2))("C"))
    )
  }
}
