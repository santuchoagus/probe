package main

import (
	"bufio"
	"fmt"
	"log"
	"net"
)

func main() {
	li, err := net.Listen("tcp", ":8080")
	if err != nil {
		log.Fatal("TCP server couldn't be started:",err.Error())
	}
	defer li.Close()

	for {
		conn, err := li.Accept()
		if err != nil {
			log.Println(err)
			continue
		}

		go handleConnection(conn)
	}
}

func handleConnection(conn net.Conn) {
	defer conn.Close()
	readFromConnection(conn)
}

func readFromConnection(conn net.Conn) {
	//line := ""
	scanner := bufio.NewScanner(conn)

	for scanner.Scan() {
	//	line = line + scanner.Text()
		fmt.Printf("%s", scanner.Text())
	}
}
