package advent.of.code
// scala 2022/07/partOne.scala 2022/07/input.txt

import scala.io.Source

sealed trait Command

case class CDCommand(target: String) extends Command

case object LSCommand extends Command

sealed trait FSObject

case class Directory(
    name: String,
    size: Int = 0,
    parent: Option[Directory] = None,
    children: List[FSObject] = List()
) extends FSObject

case class File(name: String, size: Int) extends FSObject


def parseLine(line: String): Command | FSObject = {
    val tokens = line.split(" ")

    tokens.toSeq match {
        case Seq("$", "cd", target) =>
            CDCommand(target)
        case Seq("$", "ls") =>
            LSCommand
        case Seq("dir", name) =>
            Directory(name)
        case Seq(size, name) =>
            File(name, size.toInt)
        case _ =>
            throw new Error(s"Line does not conform to expected input $line")

    }
}

def findDirectory(name: String, children: List[FSObject]): Option[Directory] =
    children match {
        case head :: tail =>
            head match {
                case d: Directory if d.name == name =>
                    Some(d)
                case _ =>
                    findDirectory(name, tail)
            }
        case _ =>
            None
    }


type Input = Iterator[Command | FSObject]

// Expore the filesystem using the input commands. Return the top most directory.
// Permit cd'ing into directories that have not been revealed using 'ls'. In this
// case overwrite the directory name when we ls.
def exploreFileSystem(input: Input, curDir: Option[Directory]): Directory =
    if input.hasNext then
        val cmd: Command | FSObject = input.next
        cmd match {
            case CDCommand("..") =>
                exploreFileSystem(input, curDir.get.parent)
            case CDCommand(target) =>
                val nextDir = curDir.map { d =>
                    findDirectory(target, d.children)
                        .getOrElse(Directory(target, parent = curDir))
                }

                exploreFileSystem(input, nextDir)
        }
//             case CDCommand(target) =>
//                 val nextDir =
//                     if curDir.isDefined then
//                         curDir.get.children.find(c =>
//                             c match {
//                                 case d: Directory =>
//                                     d.name == target
//                                 case _ => false
//                             }
//                         )
//                     else
//                         Some(Directory(target))
//                 exploreFileSystem(input, nextDir)
//             case LSCommand =>
//                 exploreFileSystem(input, curDir)
//             case d: Directory =>
//                 val d2 = d.copy(parent = Some(curDir))
//                 val newDir = curDir.copy(children =  curDir.children :+ d2)
//                 exploreFileSystem(input, newDir)
//             case f: File =>
//                 val newDir = curDir.copy(children =  curDir.children :+ d)
//                 exploreFileSystem(input, newDir)

    else
        topLevelDir(curDir).get

def topLevelDir(curDir: Option[Directory]): Option[Directory] =
    curDir match {
        case Some(d) =>
            topLevelDir(d.parent)
        case _ =>
            curDir
    }

@main def template(fileName: String) = {
    val input = Source.fromFile(fileName).getLines
        .map(line => parseLine(line))
}
