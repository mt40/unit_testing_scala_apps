import org.apache.log4j
import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession
import org.scalacheck.ScalacheckShapeless
import org.scalatest.prop.GeneratorDrivenPropertyChecks
import org.scalatest.{FunSuite, Matchers}

trait LocalSparkApp {
  log4j.Logger.getLogger("org").setLevel(log4j.Level.ERROR)
  log4j.Logger.getLogger("akka").setLevel(log4j.Level.ERROR)

  @transient
  protected implicit lazy val spark: SparkSession = {
    val localConf = new SparkConf()
      .set("spark.ui.showConsoleProgress", "false")
      .set("spark.sql.shuffle.partitions", "5")

    SparkSession.builder().master("local[2]").config(localConf).getOrCreate()
  }
}

abstract class Suite
    extends FunSuite with LocalSparkApp with Matchers with GeneratorDrivenPropertyChecks
    with ScalacheckShapeless

case class User(id: Int, name: String)

class Tests extends Suite {

  import spark.implicits._

  test("first test") {
    forAll { users: Seq[User] =>
      val ds = users.toDS.map(u => u.copy(name = "name: " + u.name))
      val names = ds.collect().map(_.name)

      ds.show(3)
      every(names) should not be empty
    }
  }
}
