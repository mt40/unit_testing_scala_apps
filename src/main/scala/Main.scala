import org.scalacheck.{Arbitrary, Gen}
import shapeless._

object Main {

  def main(args: Array[String]): Unit = {
    // infix notation at type level
    class ~:~[A, B]
    type Pre = ~:~[Int, String]
    type In = Int ~:~ String

    println("HList test")
    val list: String :: Int :: Boolean :: HNil = "sea" :: 1 :: false :: HNil
    println(list)
    println(list(2))

    implicit val intGen = Gen.choose(0, 10)
    implicit val stringGen = Gen.alphaNumStr.map(_.take(3))

    implicit val empty: Gen[HNil] = Gen.const(HNil)

    implicit def hlist[H, T <: HList](
      implicit
      head: Gen[H],
      tail: Gen[T]
    ): Gen[H :: T] = {
      for { h <- head; t <- tail } yield h :: t
    }

    val hlistSample = implicitly[Gen[String :: Int :: HNil]].sample
    println(s"hlist sample: $hlistSample")

    val seqSample = implicitly[Arbitrary[Seq[Int]]].arbitrary.sample
    println(s"random seq: $seqSample")
  }
}
