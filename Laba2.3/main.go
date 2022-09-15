package main

import (
	"fmt"
	"sync"
	"time"
)

var mu sync.Mutex
var Arramain []string
var i int = 0
var i1 int = 0
var i2 int = 0

func f1(arr1 []string, arrmain []string) {
	mu.Lock()
	arrmain[i] = arr1[i1]
	i++
	i1++
	mu.Unlock()
	Arramain = arrmain
}

func f2(arr1 []string, arrmain []string) {
	mu.Lock()
	arrmain[i] = arr1[i1]
	i++
	i2++
	mu.Unlock()
	Arramain = arrmain
	println(arrmain)
}

func main() {

	arr11 := []string{"5I", "4I", "3I", "9I"}
	arr12 := []string{"4A", "7A", "2A", "5A"}

	go f1(arr11, Arramain)
	go f2(arr12, Arramain)
	time.Sleep(time.Second * 10)
	//Arramain := []string{"5I", "4A", "4I", "7A", "3I", "2A", "9I", "5A"}

	for len(Arramain) > 1 {
		var arr1 []string
		for i := 0; i < len(Arramain)-1; i = i + 2 {
			fmt.Println("i : ", i)

			if Arramain[i][0:1] > Arramain[i+1][0:1] {
				fmt.Println("Must add In ", Arramain[i])
				arr1 = append(arr1, Arramain[i])
			} else {
				arr1 = append(arr1, Arramain[i+1])
				fmt.Println("Must add An ", Arramain[i+1])
			}
		}
		println("--------------------------------")
		Arramain = arr1
	}
	fmt.Println(Arramain)

}
