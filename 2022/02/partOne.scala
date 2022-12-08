package advent.of.code
// scala 2022/02/partOne.scala 2022/02/input.txt

import scala.io.Source

sealed trait Shape {
    def points: Int
}

case object Rock extends Shape {
    def points = 1
}

case object Paper extends Shape {
    def points = 2
}

case object Scissors extends Shape {
    def points = 3
}

sealed trait Result {
    def points: Int
}

case object Loss extends Result {
    def points = 0
}

case object Draw extends Result {
    def points = 3
}

case object Win extends Result {
    def points = 6
}

def parseShape(shape: Char): Shape =
    shape match {
        case 'A' | 'X' => Rock
        case 'B' | 'Y' => Paper
        case 'C' | 'Z' => Scissors
    }

def playMatch(them: Shape, you: Shape): Result =
    if (them == you) then
        Draw
    else
        (them, you) match {
            case (Rock, Paper) | (Paper, Scissors) | (Scissors, Rock) => Win
            case _ => Loss
        }

def scoreGame(them: Shape, you: Shape): Int =
    you.points + playMatch(them, you).points


@main def scoreTallier(fileName: String) = {
    val lines = Source.fromFile(fileName).getLines.toList
    val finalScore = lines.map { line =>
        val them = parseShape(line.charAt(0))
        val you = parseShape(line.charAt(2))

        scoreGame(them, you)
    }.sum

    println(finalScore)
}
