package main

import (
	"log"
	"net"
)

func main() {
	laddr := net.UDPAddr{
		IP: net.ParseIP("0.0.0.0"),
		Port: 8080,
	}

	conn, err := net.ListenUDP("udp", &laddr)

	if err != nil {
		log.Fatal("Couldn't create a socket.", err)
	}
	defer conn.Close()
	bytes := make([]byte, 256)
	for {
		n, _, err := conn.ReadFrom(bytes)
		if err != nil {
			log.Println("error reading UDP packet.", err)
			continue
		}

		log.Println(string(bytes[:n]))

		if err != nil {
			break
		}
	}
}
