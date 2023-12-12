package advent.of.code
// scala 2023/04/partTwo.scala 2023/04/input.txt

import scala.io.Source
import scala.math.pow
import scala.collection.mutable.HashMap

case class ScratchCard(
    id: Int,
    winningNumbers: Set[Int],
    scratcher: Set[Int],
) {

    def matchingNumbersCount: Int =
        winningNumbers.intersect(scratcher).size

    def getScore: Int =
        pow(2, matchingNumbersCount - 1).intValue

}

def parseCard(card: String): Set[Int] =
    card.trim.split("\\s+").map(_.toInt).toSet

def parseScratcher(line: String): ScratchCard = {
    val scratcherPattern = """Card\s+(\d+)\:+([0-9 ]+ )\|([0-9 ]+)""".r

    line match {
        case scratcherPattern(id, winningNums, scratcher) =>
            ScratchCard(
                id.toInt,
                parseCard(winningNums),
                parseCard(scratcher),
            )
    }
}

def computeScratcherCount(cards: List[ScratchCard], additionalCards: List[Int] = List.empty): Int =
    cards match {
        case head :: tail =>
            val count = 1 + additionalCards.headOption.getOrElse(0)

            val carryOverCount: List[Int] = List.fill(head.matchingNumbersCount)(count)
            val additionalCardsTail = if (additionalCards.length > 1) {
                additionalCards.tail
            } else {
                List.empty
            }

            val newAdditionalCards = carryOverCount.zipAll(additionalCardsTail, 0, 0).map(t => t match {
                case (x, y) => x + y
            })

            count + computeScratcherCount(tail, newAdditionalCards)
        case _ =>
            0
    }

@main def template(fileName: String) = {
    val lines = Source.fromFile(fileName).getLines()

    val cards = lines.map(parseScratcher)
        .toList

    val answer = computeScratcherCount(cards)

    println(answer)
}
