package main

// go run 2022/06/part_one.go 2022/06/input.txt

import (
	"bufio"
	"log"
	"os"
)

func findMarkerIndex(s string) int {
	var buffer [4]rune
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

	for i, r := range s {
		buffer[i%len(buffer)] = r

		log.Println(r, buffer)

		if i >= 4 && isUnique() {
			// The character at index i is the i+1 character
			return i + 1
		}
	}

	return -1
}

func main() {
	fileName := os.Args[1]

	f, err := os.Open(fileName)
	if err != nil {
		log.Fatal(err)
	}
	defer f.Close()

	scanner := bufio.NewScanner(f)
	for scanner.Scan() {
		line := scanner.Text()
		marker := findMarkerIndex(line)
		log.Println("marker: ", marker)
	}

	if err := scanner.Err(); err != nil {
		log.Fatal(err)
	}
}
