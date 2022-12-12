package advent.of.code
// scala 2022/06/partOne.scala 2022/06/input.txt

import scala.annotation.tailrec
import scala.io.Source

@tailrec
def findMarkerIndex(data: String, idx: Int = 0, buffer: String = "", bufferSize: Int = 4): Int = {
    def isUnique(b: String): Boolean =
        b.length == b.distinct.length

    if (data.nonEmpty) then
        val ch = data.take(1)
        val newData = data.substring(1)
        val b = ch.toString + buffer.take(bufferSize - 1)

        if (idx >= bufferSize) then
            if (isUnique(b)) then
                idx + 1
            else
                findMarkerIndex(newData, idx + 1, b, bufferSize)
        else
            findMarkerIndex(newData, idx + 1, b, bufferSize)
    else
        -1
}

@main def template(fileName: String) = {
    val lines = Source.fromFile(fileName).getLines
    .map(line => findMarkerIndex(line))
    .foreach(println)
}
