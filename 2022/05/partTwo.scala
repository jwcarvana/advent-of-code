package advent.of.code
// scala 2022/05/partOne.scala 2022/05/input.txt

import scala.io.Source
import scala.collection.mutable.{ListBuffer, Map}
import scala.util.matching.Regex

// Represent a board using a map of an Int the index to a List[Char] of the stack of crates.
// The stack is ordered from top to bottom. The head of the List[Char] is the top element.
def parseBoard(b: List[String]) = {
    val board = ListBuffer[List[Char]]()
    b.reverse.foreach { row =>
        (0 to row.length - 1).foreach { r =>
            val ch = row.charAt(r)

            if (r < board.length) then
                board(r) = board(r) :+ ch
            else
                board += List(ch)
        }
    }

    board.foldLeft(Map[Int, List[Char]]()) { (m, row) =>
        val head = row.head
        val tail = row.tail

        if (head.isDigit) then
            m + (head.toString.toInt -> tail.filter(_.isLetter).reverse)
        else
            m
    }
}

case class Move(
    count: Int,
    from: Int,
    to: Int
)

def parseMove(move: String): Move = {
    val pattern = "move (\\d+) from (\\d+) to (\\d+)".r

    val m = pattern.findFirstMatchIn(move).get

    Move(m.group(1).toInt, m.group(2).toInt, m.group(3).toInt)
}

def updateBoard(board: Map[Int, List[Char]], move: Move, groupBy: Int): Unit = {
    val from = move.from
    val to = move.to
    val stack = board(from).splitAt(move.count)

    board(from) = stack._2
    board(to) = stack._1.grouped(groupBy).toList.reverse.flatten ::: board(to)
}

def printBoard(board: Map[Int, List[Char]]): Unit =
    val b = (1 to board.size).map(idx => s"$idx ${board(idx).mkString}").mkString("\n")
    println(b)
    println

def getTop(board: Map[Int, List[Char]]): String =
    (1 to board.size).map(idx => board(idx).headOption.getOrElse(' ')).mkString

@main def template(fileName: String, groupBy: Int) = {
    val lines = Source.fromFile(fileName).getLines

    val boardLines = lines.takeWhile(_.nonEmpty).toList
    val board = parseBoard(boardLines)

    lines.foreach { line =>
        val move = parseMove(line)

        updateBoard(board, move, groupBy)
    }

    println(getTop(board))
}
