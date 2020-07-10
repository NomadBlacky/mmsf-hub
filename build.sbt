import dorkbox.notify.Notify

enablePlugins(ScalaJSBundlerPlugin)

name := "mmsf-hub"

scalaVersion := "2.13.3"

npmDependencies in Compile ++= Seq(
  "react"       -> "16.13.1",
  "react-dom"   -> "16.13.1",
  "react-proxy" -> "1.1.8"
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
  "me.shadaj"     %%% "slinky-web"         % "0.6.5",
  "me.shadaj"     %%% "slinky-hot"         % "0.6.5",
  "org.scalatest" %%% "scalatest-funsuite" % "3.2.0" % Test
)

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
