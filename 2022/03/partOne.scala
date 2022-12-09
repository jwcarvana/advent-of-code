package advent.of.code
// scala 2022/03/partOne.scala 2022/03/input.txt

import scala.io.Source

val priority =
    ('a' to 'z').zip(1 to 26).toMap ++ ('A' to 'Z').zip(27 to 52).toMap

def findDuplicate(a: String, b: String): Char =
    (a.toSet & b.toSet).toSeq.head

def toPriority(item: Char): Int =
    priority.get(item).get


@main def rucksackPriorities(fileName: String) = {
    val lines = Source.fromFile(fileName).getLines
    val totalPriority = lines
        .map(line => line.splitAt(line.length / 2))
        .map(sacks => findDuplicate(sacks._1, sacks._2))
        .map(toPriority)
        .sum

    println(totalPriority)
}
