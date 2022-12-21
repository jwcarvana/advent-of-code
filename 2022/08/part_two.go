package main

// go run 2022/08/part_two.go 2022/08/input.txt

import (
	"bufio"
	"fmt"
	"log"
	"os"
	"strconv"
	"strings"
)

type tree struct {
	height int
	rating int
}

func parseLine(line string) ([]*tree, error) {
	row := make([]*tree, 0, len(line))

	for _, r := range line {
		height, err := strconv.ParseInt(string(r), 10, 32)
		if err != nil {
			return nil, err
		}

		t := &tree{
			height: int(height),
		}
		row = append(row, t)
	}

	return row, nil
}

func computeScenicRating(forestMap [][]*tree) {
	length := len(forestMap)
	width := 0
	if length > 0 {
		width = len(forestMap[0])
	}

	for x, row := range forestMap {
		for y, treeNode := range row {
			treeHeight := treeNode.height

			top := func() int {
				viewDist := 0
				for i := x - 1; i >= 0; i-- {
					viewDist++

					t := forestMap[i][y]

					if t.height >= treeHeight {
						break
					}
				}

				return viewDist
			}

			right := func() int {
				viewDist := 0
				for j := y + 1; j < width; j++ {
					viewDist++

					t := forestMap[x][j]

					if t.height >= treeHeight {
						break
					}
				}

				return viewDist
			}

			bottom := func() int {
				viewDist := 0
				for i := x + 1; i < length; i++ {
					viewDist++

					t := forestMap[i][y]

					if t.height >= treeHeight {
						break
					}
				}

				return viewDist
			}

			left := func() int {
				viewDist := 0
				treeHeight := forestMap[x][y].height
				for j := y - 1; j >= 0; j-- {
					viewDist++

					t := forestMap[x][j]

					if t.height >= treeHeight {
						break
					}
				}

				return viewDist
			}

			treeNode.rating = top() * right() * bottom() * left()
		}
	}
}

func findMaxScenic(forestMap [][]*tree) int {
	max := 0
	for _, row := range forestMap {
		for _, t := range row {
			if t.rating > max {
				max = t.rating
			}
		}
	}

	return max
}

func debugMap(forestMap [][]*tree) string {
	var b strings.Builder

	for _, row := range forestMap {
		for _, t := range row {
			b.WriteString(fmt.Sprintf("%d:%t ", t.height, t.rating))
		}

		b.WriteString("\n")
	}

	return b.String()
}

func main() {
	fileName := os.Args[1]

	f, err := os.Open(fileName)
	if err != nil {
		log.Fatal(err)
	}
	defer f.Close()

	forestMap := make([][]*tree, 0)

	scanner := bufio.NewScanner(f)
	for scanner.Scan() {
		line := scanner.Text()

		row, err := parseLine(line)
		if err != nil {
			log.Fatal(err)
		}

		forestMap = append(forestMap, row)
	}

	computeScenicRating(forestMap)

	// fmt.Println(debugMap(forestMap))

	fmt.Println(findMaxScenic(forestMap))
}
