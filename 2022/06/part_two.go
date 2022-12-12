package main

// go run 2022/06/part_one.go 2022/06/input.txt 14

import (
	"bufio"
	"log"
	"os"
	"strconv"
)

func findMarkerIndex(data string, bufferSize int) int {
	buffer := make([]rune, bufferSize)
	isUnique := func() bool {
		set := map[rune]struct{}{}
		for _, r := range buffer {
			_, ok := set[r]
			if ok {
				return false
			}
			set[r] = struct{}{}
		}

		return true
	}

	for i, r := range data {
		buffer[i%len(buffer)] = r

		log.Println(r, buffer)

		if i >= bufferSize && isUnique() {
			// The character at index i is the i+1 character
			return i + 1
		}
	}

	return -1
}

func main() {
	fileName, startAfter := os.Args[1], os.Args[2]

	f, err := os.Open(fileName)
	if err != nil {
		log.Fatal(err)
	}
	defer f.Close()

	start, err := strconv.ParseInt(startAfter, 10, 32)
	if err != nil {
		log.Fatal(err)
	}

	scanner := bufio.NewScanner(f)
	for scanner.Scan() {
		line := scanner.Text()
		marker := findMarkerIndex(line, int(start))
		log.Println("marker: ", marker)
	}

	if err := scanner.Err(); err != nil {
		log.Fatal(err)
	}
}
