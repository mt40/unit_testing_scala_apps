name := "unit_testing_spark_apps"

version := "0.1"

scalaVersion := "2.12.8"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % "2.4.0",
  "org.apache.spark" %% "spark-sql" % "2.4.0",
  "com.chuusai" %% "shapeless" % "2.3.3",
  "org.scalacheck" %% "scalacheck" % "1.13.5",
  "org.scalatest" %% "scalatest" % "3.0.5",
  "com.github.alexarchambault" %% "scalacheck-shapeless_1.13" % "1.1.6"
)