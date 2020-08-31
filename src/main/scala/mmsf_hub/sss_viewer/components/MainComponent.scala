package mmsf_hub.sss_viewer.components

import mmsf_hub.sss_viewer.SSSViewer
import mmsf_hub.sss_viewer.model.Server
import slinky.core.FunctionalComponent
import slinky.core.annotations.react
import slinky.web.html._
import typings.materialUiCore.components.{AppBar, Toolbar, Typography}
import typings.materialUiCore.createMuiThemeMod.ThemeOptions
import typings.materialUiCore.createPaletteMod.{PaletteColorOptions, PaletteOptions}
import typings.materialUiCore.materialUiCoreStrings.static
import typings.materialUiCore.mod.PropTypes.Color
import typings.materialUiCore.stylesMod.createMuiTheme
import typings.materialUiCore.typographyTypographyMod.Style
import typings.materialUiStyles.components.ThemeProvider
import typings.react.mod.CSSProperties
import typings.reactRouter.mod.RouteProps
import typings.reactRouterDom.components.{BrowserRouter, Redirect, Route}

@react object MainComponent {
  case class Props(servers: Seq[Server])

  private val theme = createMuiTheme(
    ThemeOptions().setPalette(PaletteOptions().setPrimary(PaletteColorOptions.SimplePaletteColorOptions("#2196f3")))
  )

  val component: FunctionalComponent[Props] = FunctionalComponent[Props] { props =>
    // TODO: Cannot apply the theme...
    ThemeProvider(theme)(
      AppBar()
        .color(Color.primary)
        .position(static)(
          Toolbar(
            img(src := "favicon.ico", width := "50px", height := "50px"),
            Typography
              .variant(Style.h6)
              .style(CSSProperties().setMarginLeft("1em").setColor("inherit").setFlexGrow(1))("MMSF Hub")
          )
        ),
      BrowserRouter(
        Route(
          RouteProps()
            .setExact(true)
            .setPath("/")
            .setRender(_ => Redirect("/sss-viewer"))
        ),
        Route(
          RouteProps()
            .setPath("/sss-viewer")
            .setRender(_ => SSSViewer(props.servers))
        )
      )
    )
  }
}
