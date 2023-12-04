package advent.of.code
// scala 2023/01/partTwo.scala 2023/01/input.txt

import scala.collection.concurrent.TrieMap
import scala.io.Source

val words = Map(
    "one" -> '1',
    "two" -> '2',
    "three" -> '3',
    "four" -> '4',
    "five" -> '5',
    "six"-> '6',
    "seven" -> '7',
    "eight"-> '8',
    "nine" -> '9',
)

// val digits = words.foldLeft(TrieMap.empty[String, Char]){
//     case (acc, (word, digit)) => acc + (word -> digit)
//   }

def parseDigitWord(characters: List[Char]) : Option[Char] =
    words.find { (word, digit) =>
        characters.startsWith(word.toList)
    }.map(_._2)

def parseDigits(characters: List[Char], currentDigits: List[Char] = List.empty): List[Char] =
    characters match {
        case head :: tail =>
            if (head.isDigit) then
                parseDigits(tail, currentDigits :+ head)
            else
                val digit = parseDigitWord(characters)
                if (digit.isDefined) then
                    parseDigits(tail, currentDigits :+ digit.get)
                else
                    parseDigits(tail, currentDigits)
        case _ =>
            currentDigits

    }


@main def template(fileName: String) = {
    val lines = Source.fromFile(fileName).getLines
    val answer = lines.map { line =>
        val digits = parseDigits(line.toList)
        val n = s"${digits.headOption.getOrElse('0')}${digits.lastOption.getOrElse('0')}"
        n.toInt
    }.sum

    println(answer)
}
