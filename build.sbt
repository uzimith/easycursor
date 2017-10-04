lazy val easycursor = (project in file("."))
  .enablePlugins(SbtIdeaPlugin)
  .settings(
    organization := "net.uzimith",
    ideaPluginName := "easycursor",
    scalaVersion := "2.12.7",
    ideaBuild := "0.1.0-SNAPSHOT",
    scalacOptions ++= Seq("-unchecked",
                          "-deprecation",
                          "-feature",
                          "-language:implicitConversions",
                          "-Ywarn-unused:imports"),
    Compile / console / scalacOptions -= "-Ywarn-unused:imports",
    assembly / assemblyOption := (assembly / assemblyOption).value.copy(includeScala = true),
    assemblyExcludedJars in assembly := ideaFullJars.value
  )

lazy val ideaRunner = createRunnerProject(easycursor, "idea-runner")
  .settings(
    run / javaOptions ++= Seq(
      s"-Didea.home=${ideaBaseDirectory.value.getPath}"),
    run / fork := true
  )

easycursor / packagePluginZip := {
  val pluginJar = (easycursor / assembly).value
  val sources = Seq(pluginJar -> s"${(easycursor / ideaPluginName).value}/lib/${pluginJar.getName}")
  val out = (easycursor / target).value / s"${(easycursor / ideaPluginName).value}.zip"
  IO.zip(sources, out)
  out
}
