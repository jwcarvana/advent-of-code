package advent.of.code
// scala 2023/01/partOne.scala 2023/01/input.txt

import scala.io.Source

@main def template(fileName: String) = {
    val lines = Source.fromFile(fileName).getLines
    val answer = lines.map { line =>
        val digits = line.filter(_.isDigit).toList
        val n = s"${digits.headOption.getOrElse('0')}${digits.lastOption.getOrElse('0')}"
        n.toInt
    }.sum

    println(answer)
}
