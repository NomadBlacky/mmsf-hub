resolvers += Resolver.bintrayRepo("oyvindberg", "converter")

addSbtPlugin("org.scalameta"               % "sbt-scalafmt"             % "2.4.2")
addSbtPlugin("org.scala-js"                % "sbt-scalajs"              % "1.5.1")
addSbtPlugin("ch.epfl.scala"               % "sbt-scalajs-bundler"      % "0.20.0")
addSbtPlugin("org.scalablytyped.converter" % "sbt-converter"            % "1.0.0-beta32")
addSbtPlugin("org.portable-scala"          % "sbt-scalajs-crossproject" % "1.0.0")

libraryDependencies ++= Seq(
  "com.dorkbox"  % "Notify"  % "3.7",
  "com.lihaoyi" %% "upickle" % "1.3.12"
)
