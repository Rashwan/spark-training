name := "Spark Training"

scalaVersion := "2.10.5"

libraryDependencies += "org.apache.spark" %% "spark-core" % "1.5.0"

libraryDependencies += "org.apache.spark" %% "spark-sql" % "1.2.1"

libraryDependencies += "org.apache.spark" %% "spark-mllib" % "1.3.1"

initialCommands += """
  val sc = new org.apache.spark.SparkContext("local[*]", "shell")
  """.stripMargin

cleanupCommands += """
  println("Closing the SparkContext:")
  sc.stop()
  """.stripMargin

fork in run := true
