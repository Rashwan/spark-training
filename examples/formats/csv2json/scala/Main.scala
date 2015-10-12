import java.io.StringReader

import au.com.bytecode.opencsv.CSVReader
import com.fasterxml.jackson.annotation.{JsonCreator, JsonProperty}
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.jsonschema.JsonSerializableSchema
import org.apache.spark.{SparkConf, SparkContext}

case class  Person @JsonSerializableSchema() @JsonCreator() (@JsonProperty("name")name: String,@JsonProperty("animal")animal: String)


object Main {
  def main(args: Array[String]) {
    val conf = new SparkConf().setAppName("test").setMaster("local[*]")
    val sc = new SparkContext(conf)

    val lines = sc.textFile("../../input/data.csv")
    val csvResult = lines.map { line =>
      val reader = new CSVReader(new StringReader(line));
      reader.readNext();
    }
    println(csvResult.first().mkString(" "))


    val jsonObjects = csvResult.map(line=> "{\"name\" : \"" + line{0} + "\", \"animal\" : \"" + line{1} + "\"}" )
    jsonObjects.foreach(println(_))
    val mapper = new ObjectMapper()

    val persons = jsonObjects.flatMap(record=>{
      try {
        Some(mapper.readValue(record,classOf[Person]))
      }catch {
        case e: Exception => None
      }
    })

    persons.foreach(person=>println(person.animal))
    val pp = persons.filter(per=>true).map(person=>mapper.writeValueAsString(person))
    println(pp.first())
//    pp.saveAsTextFile("jsonResult")

  }
}
