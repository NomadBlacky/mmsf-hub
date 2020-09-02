import dorkbox.notify.Notify
import org.scalablytyped.converter.Flavour.Slinky

enablePlugins(ScalaJSBundlerPlugin, ScalablyTypedConverterPlugin)

name := "mmsf-hub"

scalaVersion := "2.13.3"

npmDependencies in Compile ++= Seq(
  "react"                   -> "16.13.1",
  "react-dom"               -> "16.13.1",
  "react-proxy"             -> "1.1.8",
  "react-router-dom"        -> "5.1.2",
  "@types/react"            -> "16.9.42",
  "@types/react-dom"        -> "16.9.8",
  "@types/react-router-dom" -> "5.1.2",
  "@types/prop-types"       -> "15.7.3",
  "csv-parse"               -> "4.11.1",
  "@datadog/browser-rum"    -> "1.13.0",
  // https://github.com/ScalablyTyped/SlinkyDemos/blob/516a7c0b327d032afa8b8f467e7b60289692c023/build.sbt#L165
  "@material-ui/core"   -> "3.9.4", // note: version 4 is not supported yet
  "@material-ui/styles" -> "3.0.0-alpha.10", // note: version 4 is not supported yet
  "@material-ui/icons"  -> "3.0.2"
)

npmDevDependencies in Compile ++= Seq(
  "file-loader"         -> "6.0.0",
  "style-loader"        -> "1.2.0",
  "css-loader"          -> "3.5.3",
  "html-webpack-plugin" -> "4.2.0",
  "copy-webpack-plugin" -> "5.1.1",
  "webpack-merge"       -> "4.2.2"
)

libraryDependencies ++= Seq(
  "me.shadaj"     %%% "slinky-web"         % "0.6.6",
  "me.shadaj"     %%% "slinky-hot"         % "0.6.6",
  "com.beachape"  %%% "enumeratum"         % "1.6.1",
  "org.scalatest" %%% "scalatest-funsuite" % "3.2.0" % Test
)

stFlavour := Slinky

scalacOptions in Compile ++= Seq(
  "-Ymacro-annotations",
  "-Xfatal-warnings",
  "-deprecation",
  "-unchecked"
)

version in webpack := "4.43.0"
version in startWebpackDevServer := "3.10.3"

webpackResources := baseDirectory.value / "webpack" * "*"

webpackConfigFile in fastOptJS := Some(baseDirectory.value / "webpack" / "webpack-fastopt.config.js")
webpackConfigFile in fullOptJS := Some(baseDirectory.value / "webpack" / "webpack-opt.config.js")
webpackConfigFile in Test := Some(baseDirectory.value / "webpack" / "webpack-core.config.js")

webpackDevServerExtraArgs in fastOptJS := Seq("--inline", "--hot")
webpackBundlingMode in fastOptJS := BundlingMode.LibraryOnly()

requireJsDomEnv in Test := true

lazy val fastOptJSWithNotification = taskKey[sbt.Attributed[sbt.File]]("Run fastOptJS and notify compilation result.")

fastOptJSWithNotification := {
  (fastOptJS in Compile).result.value match {
    case Value(value) =>
      Notify
        .create()
        .title("fastOptJS is succeeded")
        .text("Reloading webpack dev server...")
        .hideAfter(5000)
        .show()
      value
    case Inc(cause) =>
      Notify
        .create()
        .title("fastOptJS is failed!")
        .text(cause.message.getOrElse(""))
        .hideAfter(5000)
        .show()
      throw cause
  }
}

addCommandAlias("dev", ";fastOptJS::startWebpackDevServer;~fastOptJSWithNotification")

addCommandAlias("build", "fullOptJS::webpack")
