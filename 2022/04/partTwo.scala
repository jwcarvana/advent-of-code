package advent.of.code
// scala 2022/04/partTwo.scala 2022/04/input.txt

import scala.io.Source

def splitLine(l: String, char: String): (String, String) = {
    val splitAt = l.indexOf(char)
    val start = l.take(splitAt)
    val end = l.substring(splitAt + 1)
    (start, end)
}

def toRange(r: String): Set[Int] = {
    val split = splitLine(r, "-")
    val start = split._1.toInt
    val end = split._2.toInt
    (start to end).toSet
}

def hasOverlap(a: Set[Int], b: Set[Int]): Boolean =
    (a & b).nonEmpty

@main def campCleanup(fileName: String) = {
    val lines = Source.fromFile(fileName).getLines
    val idleElves = lines
        .map { line =>
            val elfWork = splitLine(line, ",")
            val r1 = toRange(elfWork._1)
            val r2 = toRange(elfWork._2)
            hasOverlap(r1, r2)
        }
        .count(b => b)

    println(idleElves)
}
