package main

// go run 2022/08/part_one.go 2022/08/input.txt

import (
	"bufio"
	"fmt"
	"log"
	"os"
	"strconv"
	"strings"
)

type tree struct {
	height  int
	visible bool
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

func computeVisibility(forestMap [][]*tree) {
	length := len(forestMap)
	width := 0
	if length > 0 {
		width = len(forestMap[0])
	}
	// top
	func() {
		heights := make([]int, width)
		for i := 0; i < length; i++ {
			for j := 0; j < width; j++ {
				t := forestMap[i][j]

				h := heights[j]

				if i == 0 || t.height > h {
					heights[j] = t.height
					t.visible = true
				}
			}
		}
	}()

	// right
	func() {
		heights := make([]int, length)
		for j := width - 1; j >= 0; j-- {
			for i := 0; i < length; i++ {
				t := forestMap[i][j]

				h := heights[i]

				if j == width-1 || t.height > h {
					heights[i] = t.height
					t.visible = true
				}
			}
		}

	}()

	// bottom
	func() {
		heights := make([]int, width)
		for i := length - 1; i >= 0; i-- {
			for j := 0; j < width; j++ {
				t := forestMap[i][j]

				h := heights[j]

				if i == length-1 || t.height > h {
					heights[j] = t.height
					t.visible = true
				}
			}
		}

	}()

	// left
	func() {
		heights := make([]int, length)
		for j := 0; j < width; j++ {
			for i := 0; i < length; i++ {
				t := forestMap[i][j]

				h := heights[i]

				if j == 0 || t.height > h {
					heights[i] = t.height
					t.visible = true
				}
			}
		}

	}()
}

func countVisible(forestMap [][]*tree) int {
	sum := 0
	for _, row := range forestMap {
		for _, t := range row {
			if t.visible {
				sum++
			}
		}
	}

	return sum
}

func debugMap(forestMap [][]*tree) string {
	var b strings.Builder

	for _, row := range forestMap {
		for _, t := range row {
			b.WriteString(fmt.Sprintf("%d:%t ", t.height, t.visible))
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

	computeVisibility(forestMap)

	// fmt.Println(debugMap(forestMap))

	fmt.Println(countVisible(forestMap))
}
