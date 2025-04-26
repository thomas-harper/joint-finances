import sbt.Compile
import sbt.Keys.mainClass

ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.6.4"

lazy val root = (project in file("."))
  .enablePlugins(NativeImagePlugin)
  .settings(
    name := "joint-finances",
    libraryDependencies ++= Seq(
      "dev.zio" %% "zio-cli" % "0.7.1"
    ),
    Compile / mainClass := Some("eu.woodhouse.App")
  )
