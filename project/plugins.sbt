resolvers += Resolver.bintrayRepo("oyvindberg", "converter")

addSbtPlugin("org.scalameta"               % "sbt-scalafmt"        % "2.4.0")
addSbtPlugin("org.scala-js"                % "sbt-scalajs"         % "1.1.1")
addSbtPlugin("ch.epfl.scala"               % "sbt-scalajs-bundler" % "0.18.0")
addSbtPlugin("org.scalablytyped.converter" % "sbt-converter"       % "1.0.0-beta21")

libraryDependencies ++= Seq(
  "com.dorkbox"  % "Notify"  % "3.7",
  "com.lihaoyi" %% "upickle" % "1.2.0"
)
