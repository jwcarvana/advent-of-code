package advent.of.code
// scala partOne.scala input.txt 3

import scala.io.Source
import scala.annotation.tailrec

@tailrec
def sumCalories(it: Iterator[String], elfCalories: Seq[Int] = Seq.empty, current: Int = 0): Seq[Int] = {
    if it.hasNext then
        val head = it.next

        if head.isBlank then
            sumCalories(it, elfCalories :+ current, 0)
        else
            sumCalories(it, elfCalories, head.toInt + current)
    else
        if current > 0 then
            elfCalories :+ current
        else
            elfCalories
}

@main def calorieCounter(fileName: String, take: Int) =
    val lines = Source.fromFile(fileName).getLines

    val topN = sumCalories(lines).sortWith(_ > _).take(take)
    println(topN.sum)
