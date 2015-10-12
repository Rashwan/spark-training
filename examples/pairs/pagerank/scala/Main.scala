import org.apache.spark._

object Main {
  def main(args: Array[String]) {
    val conf = new SparkConf().setAppName("test").setMaster("local[*]")
    val sc = new SparkContext(conf)

    var nodes = sc.textFile("../input").flatMap(_.split(": ")).flatMap(_.split(", "))
      .distinct().map(_->1.toFloat)
    nodes.foreach(o=>println(o._1 + "->" + o._2))

    val links = sc.textFile("../input").map(_.split(": ")).map(o=>o{0}->o{1}.split(", "))
    links.foreach(o=>println(o._1 + " :  " + o._2.mkString(" ") ))

    val cost = links.flatMap(link => link._2.map(_ -> 1 / link._2.length.toFloat)).reduceByKey(_+_)
    cost.foreach(println(_))

    for(i <- 1 to 1 ) {
      nodes = nodes.leftOuterJoin(cost).mapValues(v =>v._1 * (v._2.sum * .85 + .15).toFloat)
    }
    nodes.foreach(println(_))
//    nodes.saveAsTextFile("result")

  }
}
//nodes = nodes.cogroup(cost).mapValues(v => v._1 ++ v._2).mapValues(v=>(v.sum * .85 + .15).toFloat)
