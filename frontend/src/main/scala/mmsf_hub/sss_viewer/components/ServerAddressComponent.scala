package mmsf_hub.sss_viewer.components

import slinky.core.FunctionalComponent
import slinky.core.annotations.react
import slinky.web.html._
import typings.materialUiCore.components.Button
import typings.materialUiCore.materialUiCoreStrings.outlined
import typings.react.mod.CSSProperties

@react object ServerAddressComponent {
  case class Props(selectedAddress: Option[Int], onClick: Int => Unit)

  val component: FunctionalComponent[Props] = FunctionalComponent[Props] { props =>
    div(className := "table")(
      Seq("A", "B", "C").zipWithIndex.map { case (name, index) =>
        div(key := index.toString)(
          Button
            .variant(outlined)
            .style(getStyle(props.selectedAddress.contains(index)))
            .onClick(_ => props.onClick(index))
            .apply(name)
        )
      }
    )
  }

  private def getStyle(isSelected: Boolean): CSSProperties =
    if (isSelected) CSSProperties().setBackgroundColor("lightgreen") else CSSProperties()
}
