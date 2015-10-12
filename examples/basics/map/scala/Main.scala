import org.apache.spark._

object Main {
  def main(args: Array[String]) {
    val conf = new SparkConf().setAppName("test")
    val sc = new SparkContext(conf)
    val words= sc.parallelize(Seq("hello","world"))
    val newWords = words.map(_.toUpperCase).collect()
    newWords.foreach(println)
  }
}
