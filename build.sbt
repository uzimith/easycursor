ThisBuild / ideaPluginName := "easycursor"
ThisBuild / ideaEdition := IdeaEdition.Community
ThisBuild / ideaBuild := "183.4284.36"

lazy val easycursor = (project in file("."))
  .enablePlugins(SbtIdeaPlugin)
  .settings(
    organization := "net.uzimith",
    scalaVersion := "2.12.7",
    scalacOptions ++= Seq("-unchecked",
                          "-deprecation",
                          "-feature",
                          "-language:implicitConversions",
                          "-Ywarn-unused:imports"),
    Compile / console / scalacOptions -= "-Ywarn-unused:imports",
    assemblyExcludedJars in assembly := ideaFullJars.value
  )

lazy val ideaRunner = (project in file("target/tools"))
  .settings(
    unmanagedJars in Compile := ideaMainJars.value,
    unmanagedJars in Compile += file(System.getProperty("java.home")).getParentFile / "lib" / "tools.jar",
    mainClass in (Compile, run) := Some("com.intellij.idea.Main"),
    run / javaOptions := Seq(
      "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005",
      s"-Didea.home=${ideaBaseDirectory.value.getPath}",
      s"-Dplugin.path=${(easycursor / assembly / assemblyOutputPath).value}",
    ),
    run / fork := true
  )

addCommandAlias("runIde", "; assembly; ideaRunner/run")

easycursor / packagePluginZip := {
  val pluginJar = (easycursor / assembly).value
  val sources = Seq(pluginJar -> s"${(easycursor / ideaPluginName).value}/lib/${pluginJar.getName}")
  val out = (easycursor / target).value / s"${(easycursor / ideaPluginName).value}.zip"
  IO.zip(sources, out)
  out
}
