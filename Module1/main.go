package main

import (
	"fmt"
	"time"
)

func main() {
	allBus := [5]string{"bus1", "bus2", "bus3", "bus4", "bus5"}
	bus := make(chan string)
	// Заїзд автобусів
	go func(allBus [5]string) {
		for _, Busi := range allBus {
			fmt.Println("на зупинку приїв :" + Busi)
			bus <- Busi
		}
	}(allBus)
	// виїзд автобусів
	go func() {
		for i := 0; i < 5; i++ {
			busOut := <-bus
			fmt.Println("Виїхав з зупинки " + busOut)
		}
	}()
	<-time.After(time.Second * 5)
}
