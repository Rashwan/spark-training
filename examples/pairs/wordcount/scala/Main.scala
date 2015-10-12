import org.apache.spark._

object Main {
  def main(args: Array[String]) {
    val conf = new SparkConf().setAppName("test").setMaster("local[*]")
    val sc = new SparkContext(conf)
    val pairs = sc.textFile("../input").flatMap(_.split("\\W+")).map(_->1).reduceByKey(_+_)
    pairs.saveAsTextFile("asd")
    pairs.foreach(o=>println(o._1 +  " ->" +o._2))



  }
}
