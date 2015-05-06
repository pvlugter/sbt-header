import com.typesafe.sbt.GitPlugin
import com.typesafe.sbt.SbtScalariform
import com.typesafe.sbt.SbtScalariform.ScalariformKeys
//import de.heikoseeberger.sbtheader.HeaderPlugin
//import de.heikoseeberger.sbtheader.license.Apache2_0
import sbt._
import sbt.Keys._
import scalariform.formatter.preferences. { AlignSingleLineCaseStatements, DoubleIndentClassDeclaration }

object Build extends AutoPlugin {

  override def requires = plugins.JvmPlugin && GitPlugin // && HeaderPlugin

  override def trigger = allRequirements

  override def projectSettings =
    // Core settings
    List(
      organization := "com.typesafe.tmp",
      licenses += ("Apache-2.0", url("http://www.apache.org/licenses/LICENSE-2.0")),
      // scalaVersion := Version.scala,
      // crossScalaVersions := List(scalaVersion.value),
      scalacOptions ++= List(
        "-unchecked",
        "-deprecation",
        "-language:_",
        "-target:jvm-1.6",
        "-encoding", "UTF-8"
      ),
      unmanagedSourceDirectories.in(Compile) := List(scalaSource.in(Compile).value),
      unmanagedSourceDirectories.in(Test) := List(scalaSource.in(Test).value),
      publishTo := Some(if (isSnapshot.value) Classpaths.sbtPluginSnapshots else Classpaths.sbtPluginReleases),
      publishMavenStyle := false
    ) ++
    // Scalariform settings
    SbtScalariform.scalariformSettings ++
    List(
      ScalariformKeys.preferences := ScalariformKeys.preferences.value
        .setPreference(AlignSingleLineCaseStatements, true)
        .setPreference(AlignSingleLineCaseStatements.MaxArrowIndent, 100)
        .setPreference(DoubleIndentClassDeclaration, true)
    ) ++
    // Git settings
    List(
      GitPlugin.autoImport.git.baseVersion := "1.6.0"
    ) ++
    // Header settings
    List(
      // HeaderPlugin.autoImport.headers := Map(
      //   "scala" -> Apache2_0("2015", "Heiko Seeberger")
      // )
    )
}
