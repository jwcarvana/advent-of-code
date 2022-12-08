package advent.of.code
// scala 2022/01/partOne.scala 2022/01/input.txt

import scala.io.Source
import scala.annotation.tailrec

@tailrec
def findMax(it: Iterator[String], max: Int = 0, current: Int = 0): Int = {
    if it.hasNext then
        val head = it.next
        val c = if head.isBlank then 0 else head.toInt + current

        val m = if (c > max) c else max

        findMax(it, m, c)

    else
        max
}

@main def calorieCounter(fileName: String) =
    val lines = Source.fromFile(fileName).getLines

    println(findMax(lines))
