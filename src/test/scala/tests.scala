import org.scalatest.FunSuite

class tests extends FunSuite {
  val text1 = "1\n5\n3\n1 1 1\n2 1 0 2 0\n1 5 0"
  val text2 = "2\n5\n3\n1 1 1\n2 1 0 2 0\n1 5 0\n1\n2\n1 1 0\n1 1 1"

  val paint1 = Paint(1, 1)
  val paint2 = Paint(1, 0)
  val paint3 = Paint(2, 0)
  val paint4 = Paint(5, 0)

  val customer1 = Customer(1, Seq(paint1))
  val customer2 = Customer(2, Seq(paint2, paint3))
  val customer3 = Customer(1, Seq(paint4))

  val customer4 = Customer(1, Seq(paint2))
  val customer5 = Customer(1, Seq(paint1))

  val test1 = TestCases(5, 3, Seq(customer1, customer2, customer3))
  val test2 = TestCases(1, 2, Seq(customer4, customer5))

  val customers1 = Seq(customer1)
  val customers2 = Seq(customer1, customer2)

  val map1 = Map(paint1 -> Seq(customer1))
  val map2 = Map(paint1 -> Seq(customer1), paint2 -> Seq(customer2), paint3 -> Seq(customer2))
  val map3 = Map(paint1 -> Seq(customer1), paint2 -> Seq(customer2), paint3 -> Seq(customer2), paint4 -> Seq(customer3))



  test("check start times") {
    assert(text1.split("\n").toList === Seq("1", "5", "3", "1 1 1", "2 1 0 2 0", "1 5 0"))
    assert("1 1 1 1 1".split(" ").toList.tail.grouped(2).toList === Seq(Seq("1", "1"), Seq("1", "1")))
  }

  test("parse") {
    assert(Parser.parse(text1) === Seq(test1))
    assert(Parser.parse(text2) === Seq(test1, test2))
  }

  test("mapPaints") {
    assert(GreedyAlgorithm.mapPaints(customers1) == map1)
    assert(GreedyAlgorithm.mapPaints(customers2) == map2)
  }


  val costs2 = map2.map { case (paint, customers) => paint -> GreedyAlgorithm.costEffectiveness(customers2.toSet, paint, customers) }
  val costs3 = map2.map { case (paint, customers) => paint -> GreedyAlgorithm.costEffectiveness(Set(customer1), paint, customers) }

  test("cost effectiveness") {
    assert(GreedyAlgorithm.costEffectiveness(Set(customer4), Paint(1, 0), Seq(customer1, customer2, customer3)) === 0)
    assert(GreedyAlgorithm.costEffectiveness(Set(customer1), Paint(1, 0), Seq(customer1, customer2, customer3)) === 1)
    assert(GreedyAlgorithm.costEffectiveness(Set(customer1), Paint(1, 1), Seq(customer1, customer2, customer3)) === 1.toFloat / 2.toFloat)
    assert(GreedyAlgorithm.costEffectiveness(Set(customer1, customer2), Paint(1, 1), Seq(customer1, customer2, customer3)) === 2.toFloat / 2.toFloat)
    assert(GreedyAlgorithm.costEffectiveness(Set(customer1, customer2), Paint(1, 0), Seq(customer1, customer2, customer3)) === 2)

    assert(costs2 === Map(paint1 -> 1.toFloat / 2.toFloat, paint2 -> 1, paint3 -> 1))
    assert(costs3 === Map(paint1 -> 1.toFloat / 2.toFloat, paint2 -> 0.0, paint3 -> 0.0))
  }


  test("min cost") {
    assert(costs2.maxBy(_._2)._1 === paint2)
  }

  test("remove Customers") {
    assert(Set(customer1, customer2).filterNot(map2.getOrElse(paint2, Nil).toSet) == Set(customer1))

  }



  test("greedy") {
    assert(GreedyAlgorithm.setCover(Set.empty[Customer], map1, Map.empty[Paint, Seq[Customer]], 1) == Map.empty[Paint, Seq[Customer]])
    assert(GreedyAlgorithm.setCover(Set(customer1), map1, Map.empty[Paint, Seq[Customer]], 1) == map1)
    assert(GreedyAlgorithm.setCover(Set(customer1, customer2), map1, Map.empty[Paint, Seq[Customer]], 1) == Map.empty[Paint, Seq[Customer]])
    assert(GreedyAlgorithm.setCover(Set(customer1, customer2), map1, Map.empty[Paint, Seq[Customer]], 1) == Map.empty[Paint, Seq[Customer]])

    assert(GreedyAlgorithm.setCover(Set(customer1, customer2), map2, Map.empty[Paint, Seq[Customer]], 10) == Map(paint1 -> Seq(customer1), paint2 -> Seq(customer2)))
    assert(GreedyAlgorithm.setCover(Set(customer1, customer2, customer3), map3, Map.empty[Paint, Seq[Customer]], 3) == Map(paint1 -> Seq(customer1), paint2 -> Seq(customer2), paint4 -> Seq(customer3)))
  }
}
