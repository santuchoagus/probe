package main

import (
	"bufio"
	"fmt"
	"io"
	"log"
	"net"
	"time"
)

func main() {
	listener, err := net.Listen("tcp", ":8080")

	if err != nil {
		log.Fatal("Unable to open connection", err)
	}

	defer listener.Close()

	for {
		conn, err := listener.Accept()
		if err != nil {
			log.Fatal("Unable to accept connection", err)
		}
		go handleConn(conn)
	}
}

func handleConn(conn net.Conn) {
	defer conn.Close()
	scanner := bufio.NewScanner(conn)
	for scanner.Scan() {
		ln := scanner.Text()

		if ln == "" {
			break
		}
		fmt.Println(ln)
	}
	for i := 0; i<=3; i++ {
		io.WriteString(conn, "I see you connected\n")
		time.Sleep(1 * time.Second)
	}
}
