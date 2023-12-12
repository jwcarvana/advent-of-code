package advent.of.code
// scala 2023/04/partOne.scala 2023/04/input.txt

import scala.io.Source
import scala.math.pow

case class ScratchCard(
    winningNumbers: Set[Int],
    scratcher: Set[Int],
) {
    def getScore: Int = {
        val size = winningNumbers.intersect(scratcher).size
        pow(2, size - 1).intValue
    }
}

def parseCard(card: String): Set[Int] =
    card.trim.split("\\s+").map(_.toInt).toSet

def parseScratcher(line: String): ScratchCard = {
    val scratcherPattern = """Card\s+\d+\:([0-9 ]+ )\|([0-9 ]+)""".r

    line match {
        case scratcherPattern(winningNums, scratcher) =>
            ScratchCard(
                parseCard(winningNums),
                parseCard(scratcher),
            )
    }
}

@main def template(fileName: String) = {
    val lines = Source.fromFile(fileName).getLines()

    val answer = lines.map(parseScratcher)
        .map(_.getScore)
        .sum

    println(answer)
}
