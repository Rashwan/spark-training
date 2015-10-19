import org.apache.spark._

object Main {
  def main(args: Array[String]) {
    val conf = new SparkConf().setAppName("test").setMaster("local[*]")
    val sc = new SparkContext(conf)

    val values1 = sc.parallelize(Seq(1,3,5,7))
    val values2 = sc.parallelize(Seq(5,7,9,11))
    values1.intersection(values2).collect

  
  }
}
