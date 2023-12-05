package advent.of.code

// scala 2023/02/partTwo.scala 2023/02/input.txt

import scala.io.Source

case class Game(
    id: Int,
    reveals: List[Map[String, Int]],
)

def parseGame(line: String) = {
    val Array(game, results) = line.split(": ")
    val id = game.split(" ").last.toInt
    val reveals = results.split("; ").map { result =>
        result.split(", ").map { r =>
            val Array(count, color) = r.split(" ")
            color -> count.toInt
        }.toMap
    }.toList

    Game(id, reveals)
}

@main def template(fileName: String) = {
    val colors = List("red", "green", "blue")
    val lines = Source.fromFile(fileName).getLines
    val answer = lines.map(parseGame)
        .map { game =>
            def getMax(color: String) =
                game.reveals.map(_.getOrElse(color, 0)).max

            colors.map(getMax).reduce(_ * _)
        }.sum

    println(answer)
}
