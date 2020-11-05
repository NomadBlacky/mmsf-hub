package mmsf_hub.sss_viewer.components

import slinky.core.FunctionalComponent
import slinky.core.annotations.react
import slinky.web.html._
import typings.materialUiCore.components.Button
import typings.materialUiCore.materialUiCoreStrings.outlined
import typings.react.mod.CSSProperties

@react object CustomLocationComponent {
  case class Props(selectedLocation: Option[Int], onClick: Int => Unit)

  val component: FunctionalComponent[Props] = FunctionalComponent[Props] { props =>
    div(className := "table")(
      div(
        (0 to 2).map(i =>
          Button
            .withKey(i.toString)
            .variant(outlined)
            .className("button")
            .style(getStyle(props.selectedLocation.contains(i)))
            .onClick(_ => props.onClick(i))
            .apply(i + 1)
        )
      ),
      div(
        (3 to 5).map(i =>
          Button
            .withKey(i.toString)
            .variant(outlined)
            .className("button")
            .style(getStyle(props.selectedLocation.contains(i)))
            .onClick(_ => props.onClick(i))
            .apply(i + 1)
        )
      )
    )
  }

  private def getStyle(isSelected: Boolean): CSSProperties =
    if (isSelected) CSSProperties().setBackgroundColor("lightgreen") else CSSProperties()
}
