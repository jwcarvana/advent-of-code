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

case object Lose extends Result {
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
        case 'A'  => Rock
        case 'B'  => Paper
        case 'C'  => Scissors
    }

def parseResult(result: Char): Result =
    result match {
        case 'X' => Lose
        case 'Y' => Draw
        case 'Z' => Win
    }

def yourMove(them: Shape, result: Result): Shape =
    result match {
        case Lose =>
            them match {
                case Rock => Scissors
                case Paper => Rock
                case Scissors => Paper
            }
        case Draw =>
            them
        case Win =>
            them match {
                case Rock => Paper
                case Paper => Scissors
                case Scissors => Rock
            }
    }


def scoreGame(them: Shape, result: Result): Int =
    yourMove(them, result).points + result.points


@main def scoreTallier(fileName: String) = {
    val lines = Source.fromFile(fileName).getLines.toList
    val finalScore = lines.map { line =>
        val them = parseShape(line.charAt(0))
        val you = parseResult(line.charAt(2))

        scoreGame(them, you)
    }.sum

    println(finalScore)
}
