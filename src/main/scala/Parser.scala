object Parser {

  def parse(text: String): Seq[TestCases] = {
    parseHelper(text.split("\n").toList.tail, Nil)
  }

  @scala.annotation.tailrec
  def parseHelper(strings: Seq[String], acc: Seq[TestCases]): Seq[TestCases] = strings match {
    case Nil => acc
    case _ => {
      val numColors = strings(0).toInt
      val numCustomers = strings(1).toInt

      val parsedCustomer = strings.slice(2, 2 + numCustomers)
      val customers = parsedCustomer.map(c => Customer.makeCustomer(c))
      val newTestCase = TestCases(numColors, numCustomers, customers)

      parseHelper(strings.drop(2 + numCustomers), acc :+ newTestCase)
    }

  }
}
