package main

import (
	"fmt"
	"math/rand"
	"time"
)

func showArray(arr []int) {
	fmt.Print("{ ")
	for i := 0; i < len(arr); i++ {
		fmt.Print("  ", arr[i])
	}
	fmt.Printf("}\n")
}

func versus(fighters []int, final chan int) int {
	showArray(fighters)
	if len(fighters) == 1 {
		final <- fighters[0]
		return fighters[0]
	}
	var winners []int
	for i := 0; i < len(fighters); i += 2 {
		if fighters[i] > fighters[i+1] {
			winners = append(winners, fighters[i])
		} else {
			winners = append(winners, fighters[i+1])
		}
	}

	return versus(winners, final)
}

func main() {
	var competitors []int
	var total int = 32

	ver := rand.NewSource(time.Now().UnixNano())
	ver1 := rand.New(ver)

	for i := 0; i < total; i++ {
		competitors = append(competitors, ver1.Intn(100))
	}
	final := make(chan int, 2)

	go versus(competitors[:total/2], final)
	go versus(competitors[:total/2], final)

	firstWinner := <-final
	secondWinner := <-final

	if secondWinner > firstWinner {
		fmt.Printf("Winner is %d", secondWinner)
	} else {
		fmt.Printf("Winner is %d", firstWinner)
	}

}
