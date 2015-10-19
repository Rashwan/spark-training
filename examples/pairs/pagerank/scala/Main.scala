import org.apache.spark._

object Main {
  def main(args: Array[String]) {
    val conf = new SparkConf().setAppName("test").setMaster("local[*]")
    val sc = new SparkContext(conf)

    val connections = sc.textFile("../input").map(_.split(": ")).map(o=>o{0}->o{1}.split(", "))
    connections.foreach(o=>println(o._1 + " :  " + o._2.mkString(" ") ))

    var ranks = connections.mapValues(v => 1.0)
    ranks.foreach(println(_))

    for(i <- 1 to 100 ) {
      val contrib = connections.join(ranks).flatMap {
        case (node,(links,rank)) =>
          links.map(link => link -> rank / links.length)
      }
      ranks = contrib.reduceByKey(_+_).mapValues(v => .15 + .85 * v)
    }
    ranks.foreach(println(_))
    ranks.saveAsTextFile("result")
  }
}
