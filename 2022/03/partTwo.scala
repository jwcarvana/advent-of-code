package advent.of.code
// scala 2022/03/partOne.scala 2022/03/input.txt

import scala.io.Source

val priority =
    ('a' to 'z').zip(1 to 26).toMap ++ ('A' to 'Z').zip(27 to 52).toMap

def findDuplicate(packs: Seq[String]): Char =
    packs.map(_.toSet).reduce(_ & _).toSeq.head

def toPriority(item: Char): Int =
    priority.get(item).get


@main def rucksackPriorities(fileName: String) = {
    val lines = Source.fromFile(fileName).getLines
    val totalPriority = lines.grouped(3)
        .map(findDuplicate)
        .map(toPriority)
        .sum

    println(totalPriority)
}
