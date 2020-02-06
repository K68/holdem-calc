name := "holdem-calc"

version := "1.0.0"

lazy val `holdem-calc` = (project in file(".")).enablePlugins(PlayScala)
      
scalaVersion := "2.12.10"

libraryDependencies ++= Seq(
  guice,
  caffeine
)

