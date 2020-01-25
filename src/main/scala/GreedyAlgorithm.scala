object GreedyAlgorithm {
  def mapPaints(customers: Seq[Customer]): Map[Paint, Seq[Customer]] = {
    val paints: Set[Paint] = customers.flatMap(customer => customer.colors).toSet

    paints.map{ paint =>
      paint -> customers.filter(customer => customer.colors.contains(paint))
    }.toMap
  }

  // The cost effectiveness is the ratio of the cost (matte is more expensive than glossy) to the number of newly added elements
  def costEffectiveness(uncoveredCustomers:Set[Customer], paint: Paint, customers: Seq[Customer]): Float = {
    //If I use the finish as is, matte will be 1 and glossy will be 0, so all glossy will be the same cost effectiveness, which isn't what we want.  We also want to discourage mattes.
    val cost = if (paint.finish == 0) 1 else 2
    val added_elements = customers.count(uncoveredCustomers)

    added_elements.toFloat / cost.toFloat
  }

  def setCover(uncoveredCustomers: Set[Customer], uncoveredPaints: Map[Paint,Seq[Customer]], coveredPaints: Map[Paint,Seq[Customer]], numPaint: Int): Map[Paint,Seq[Customer]] = {
    if(uncoveredCustomers.isEmpty) coveredPaints
    else if(numPaint <= 0) Map.empty[Paint, Seq[Customer]]
    else {
      val costs = uncoveredPaints.map { case (paint, customers) => paint -> GreedyAlgorithm.costEffectiveness(uncoveredCustomers, paint, customers) }
      val mostEffectivePaint: Paint = costs.maxBy(_._2)._1
      val newlyCoveredCustomers = uncoveredPaints.getOrElse(mostEffectivePaint, Nil)

      setCover(
        uncoveredCustomers.filterNot(newlyCoveredCustomers.toSet)
        , uncoveredPaints.filterNot(paints => paints._1 == mostEffectivePaint)
        , coveredPaints + (mostEffectivePaint -> newlyCoveredCustomers)
        , numPaint - 1)
    }
  }

}
