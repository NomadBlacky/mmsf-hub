import dorkbox.notify.Notify
import org.scalablytyped.converter.Flavour.Slinky

ThisBuild / scalaVersion := "2.13.3"

lazy val fastOptJSWithNotification = taskKey[sbt.Attributed[sbt.File]]("Run fastOptJS and notify compilation result.")
lazy val packageJson               = settingKey[PackageJson]("package.json")

lazy val domain = crossProject(JVMPlatform, JSPlatform)
  .crossType(CrossType.Pure)
  .in(file("domain"))
lazy val domainJS = domain.js

lazy val frontend = (project in file("frontend"))
  .enablePlugins(ScalaJSBundlerPlugin, ScalablyTypedConverterPlugin)
  .dependsOn(domainJS)
  .settings(
    packageJson := PackageJson.readFrom(baseDirectory.value / "package.json"),
    npmDependencies in Compile ++= packageJson.value.dependencies,
    npmDevDependencies in Compile ++= packageJson.value.devDependencies,
    libraryDependencies ++= Seq(
      "me.shadaj"     %%% "slinky-web"         % "0.6.6",
      "me.shadaj"     %%% "slinky-hot"         % "0.6.6",
      "com.beachape"  %%% "enumeratum"         % "1.6.1",
      "org.scalatest" %%% "scalatest-funsuite" % "3.2.2" % Test
    ),
    stFlavour := Slinky,
    stIgnore := List("jsdom", "react-proxy"),
    scalacOptions in Compile ++= Seq(
      "-Ymacro-annotations",
      "-Xfatal-warnings",
      "-deprecation",
      "-unchecked"
    ),
    version in webpack := "4.43.0",
    version in startWebpackDevServer := "3.10.3",
    webpackResources := baseDirectory.value / "webpack" * "*",
    webpackConfigFile in fastOptJS := Some(baseDirectory.value / "webpack" / "webpack-fastopt.config.js"),
    webpackConfigFile in fullOptJS := Some(baseDirectory.value / "webpack" / "webpack-opt.config.js"),
    webpackConfigFile in Test := Some(baseDirectory.value / "webpack" / "webpack-core.config.js"),
    webpackDevServerExtraArgs in fastOptJS := Seq("--inline", "--hot"),
    webpackBundlingMode in fastOptJS := BundlingMode.LibraryOnly(),
    requireJsDomEnv in Test := true,
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
  )

addCommandAlias("dev", "frontend/fastOptJS::startWebpackDevServer;~frontend/fastOptJSWithNotification")

addCommandAlias("build", "frontend/fullOptJS::webpack")
