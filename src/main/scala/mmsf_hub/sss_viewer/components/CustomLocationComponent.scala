package mmsf_hub.sss_viewer.components

import slinky.core.FunctionalComponent
import slinky.core.annotations.react
import slinky.web.html._
import typings.materialUiCore.components.Button
import typings.materialUiCore.materialUiCoreStrings.outlined

@react object CustomLocationComponent {
  case class Props(onClick: Int => Unit)

  val component: FunctionalComponent[Props] = FunctionalComponent[Props] { props =>
    div(className := "table")(
      div(
        (0 to 2).map(i => Button.withKey(i.toString).variant(outlined).onClick(_ => props.onClick(i))(i + 1))
      ),
      div(
        (3 to 5).map(i => Button.withKey(i.toString).variant(outlined).onClick(_ => props.onClick(i))(i + 1))
      )
    )
  }
}
