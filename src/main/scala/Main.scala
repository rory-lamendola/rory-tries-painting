

object Main {

  def main(args: Array[String]): Unit = {
    // Idea: The problem can be formulated as a set cover problem
    // with sets of customers who like a particular paint and the universe the set of all customers

    val text1 = "1\n5\n3\n1 1 1\n2 1 0 2 0\n1 5 0"

    val problem = Parser.parse(text1)

    problem.map{testCase =>
      val customers: Seq[Customer] = testCase.customers
      val uncoveredPaints: Map[Paint, Seq[Customer]] = GreedyAlgorithm.mapPaints(customers)
      val coveredPaints: Map[Paint, Seq[Customer]] = Map()

      GreedyAlgorithm.setCover(customers.toSet, uncoveredPaints, coveredPaints, testCase.numColors)
    }
  }
}











