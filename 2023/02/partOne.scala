package advent.of.code
// scala 2023/02/partOne.scala 2023/02/input.txt

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
    val maxValues = Map(
        "red" -> 12,
        "green" -> 13,
        "blue" -> 14,
    )

    val lines = Source.fromFile(fileName).getLines
    val answer = lines.map(parseGame)
        .filter { game =>
            game.reveals.forall { reveal =>
                reveal.forall { (color, count) =>
                    maxValues.get(color).exists(_ >= count)
                }
            }
        }.map(_.id).sum

    println(answer)
}
