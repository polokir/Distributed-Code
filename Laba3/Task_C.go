package main

import (
	"math/rand"
	"sync"
)

func isReady(elements []string, element string) bool {
	for i := 0; i < len(elements); i++ {
		if elements[i] == element {
			return true
		}
	}
	return false
}

func addElements() []string {

	var elementsList = []string{"matches", "tobacco", "paper"}
	first := elementsList[rand.Intn(3)]
	second := elementsList[rand.Intn(3)]
	if first == second {
		first = elementsList[rand.Intn(3)]
	}
	print("Helper added: " + first + " " + second + "\n")
	var resultList []string
	resultList = append(resultList, first, second)
	return resultList
}

func smoking(element string, elements []string, isCigaready *bool, group *sync.WaitGroup) {
	if !isReady(elements, element) && !*isCigaready {
		elements = append(elements, element)
		print("Smoker added " + element + "\n")
		print("Start smoking" + "\n" + "\n")
		elements = []string{}
		*isCigaready = true
	}
	group.Done()
}

func main() {
	var cigaIngr []string
	var waitGroup = sync.WaitGroup{}
	var mutex = sync.Mutex{}

	for {
		var isCigaReady bool = false
		mutex.Lock()
		waitGroup.Add(1)
		go func() {
			cigaIngr = addElements()
			waitGroup.Done()
		}()

		waitGroup.Wait()
		waitGroup.Add(3)
		go smoking("matches", cigaIngr, &isCigaReady, &waitGroup)
		go smoking("tobacco", cigaIngr, &isCigaReady, &waitGroup)
		go smoking("paper", cigaIngr, &isCigaReady, &waitGroup)
		waitGroup.Wait()
		mutex.Unlock()
	}
}
