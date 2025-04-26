ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.6.4"

lazy val root = (project in file("."))
  .settings(
    name := "joint-finances",
    libraryDependencies += "dev.zio" %% "zio-cli" % "0.7.0"
  )
