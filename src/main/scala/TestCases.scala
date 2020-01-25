case class TestCases(
  numColors: Int,
  numCustomers: Int,
  customers: Seq[Customer]
)

case class Paint(
  color: Int,
  finish: Int
)

case class Customer(
  numColors: Int,
  colors: Seq[Paint]
)

object Customer {
  def makeCustomer(s: String): Customer = {
    val split = s.split(" ").toList.map(n => n.toInt)
    val numColors = split.head
    val colors = split.tail.grouped(2).toList

    Customer(numColors, colors.map(c => Paint(c.head, c.tail.head)))
  }
}

