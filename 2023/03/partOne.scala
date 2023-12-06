package advent.of.code
// scala 2023/03/partOne.scala 2023/03/input.txt

import scala.io.Source

case class Number(
    value: Int,
    x: Int,
    y: Int,
    length: Int
) {

    def hasSymbol(schematic: Schematic): Boolean = {
        def symbolAt(x: Int, y: Int): Boolean =
            schematic.symbols.lift(x).map(_.exists(_.y == y)).getOrElse(false)

        (for {
            i <- (x-1 to x+1)
            j <- (y-1 to y+length)
        } yield symbolAt(i, j)).reduce(_ || _)
    }
}

case class Symbol(
    value: Char,
    x: Int,
    y: Int
)

case class Schematic(
    numbers: List[List[Number]] = List.empty,
    symbols: List[List[Symbol]] = List.empty,
)

def parseLine(line: List[Char], x: Int, y: Int, currentNum: String = "", numbers: List[Number] = List.empty, symbols: List[Symbol] = List.empty): (List[Number], List[Symbol]) = {
    def newNumbers =
        if (currentNum.nonEmpty) {
            val n =
                Number(
                    currentNum.toInt,
                    x,
                    y - currentNum.length,
                    currentNum.length
                )

            numbers :+ n
        } else {
            numbers
        }

    line match {
        case head :: tail =>
            if (head.isDigit) {
                parseLine(tail, x, y+1, currentNum + head, numbers, symbols)
            } else {

                val newSymbols =
                    if (head != '.') {
                        val s =
                            Symbol(
                                head,
                                x,
                                y
                            )

                        symbols :+ s
                    } else {
                        symbols
                    }

                parseLine(tail, x, y+1, "", newNumbers, newSymbols)
            }
        case _ =>
            (newNumbers, symbols)
    }
}


def processInput(lines: Iterator[String]) =
    lines.zipWithIndex.map{ (line, x) =>
        parseLine(line.toList, x, 0)
    }.foldLeft(Schematic()) { (acc, row: (List[Number], List[Symbol]) ) =>
        acc.copy(
            numbers = acc.numbers :+ row._1,
            symbols = acc.symbols :+ row._2
        )
    }


@main def template(fileName: String) = {
    val lines = Source.fromFile(fileName).getLines
    val schematic = processInput(lines)
    val numbers = schematic.numbers.flatten.filter(n => n.hasSymbol(schematic))
    val answer = numbers.map(_.value).sum

    println(answer)
}
