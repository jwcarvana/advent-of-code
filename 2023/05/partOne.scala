package advent.of.codes
// scala 2023/05/partOne.scala 2023/05/input.txt

import scala.io.Source

case class CategoryRange(
    destination: Long,
    source: Long,
    length: Long,
)

case class Category(
    source: String,
    destination: String,
    ranges: List[CategoryRange]
) {

    def mapSource(sourceId: Long): Long =
        ranges.find(cr => cr.source <= sourceId && sourceId < cr.source + cr.length)
        .map(cr => cr.destination + sourceId - cr.source)
        .getOrElse(sourceId)

}

def parseSeeds(line: String): List[Long] =
    line.stripPrefix("seeds: ").split(" ").map(_.toLong).toList

def parseAlmanac(lines: Iterator[String], current: Option[Category] = None, almanac: List[Category] = List.empty): Map[String, Category] = {
    def newAlmanac =
        if(current.isDefined) {
            almanac :+ current.get
        } else {
            almanac
        }

    if (lines.hasNext) {
        val categoryPattern = """([a-zA-Z]+)-to-([a-zA-Z]+) map:""".r
        val rangesPattern = """(\d+) (\d+) (\d+)""".r

        lines.next match {
            case categoryPattern(source, destination) =>
                val newCategory = Category(source, destination, List.empty)
                parseAlmanac(lines, Some(newCategory), almanac)

            case rangesPattern(destinationStart, sourceStart, rangeLength) =>
                val (destination, source, length) = (destinationStart.toLong, sourceStart.toLong, rangeLength.toLong)
                val newCategoryRange = CategoryRange(destination, source, length)

                val newCurrent = current.map{ c =>
                    c.copy(ranges = c.ranges :+ newCategoryRange)
                }

                parseAlmanac(lines, newCurrent,  almanac)

            case _ =>


                parseAlmanac(lines, None, newAlmanac)
        }
    } else {
        newAlmanac.map(c => c.source -> c).toMap
    }
}

def mapSource(currentId: Long, currentSource: String, almanac: Map[String, Category]): Long = {
    // println(s"$currentId $currentSource")
    val destinationOpt = almanac.get(currentSource)

    if (destinationOpt.isDefined) {
        val destination = destinationOpt.get
        val nextId = destination.mapSource(currentId)
        val nextSource = destination.destination

        mapSource(nextId, nextSource, almanac)
    } else {
        currentId
    }
}



@main def template(fileName: String) = {
    val lines = Source.fromFile(fileName).getLines()

    val seeds = parseSeeds(lines.next)
    // println(seeds)

    val almanac = parseAlmanac(lines)

    val answer = seeds.map(s => mapSource(s, "seed", almanac)).min

    println(answer)
}
