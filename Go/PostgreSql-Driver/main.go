package main

import (
	"context"
	"log"
	"os"
	pgx "github.com/jackc/pgx/v4"
)


func main() {
	conn, err := pgx.Connect(context.Background(), os.Getenv("DB_URI"))
	if err != nil {
		log.Fatal("Couldn't connect:", err)
	}

	rows, err := conn.Query(context.Background(), "SELECT bid, name FROM books;")
	if err != nil {
		log.Fatal("Couldn't query:", err)
	}
	defer rows.Close()


	for rows.Next() {
		var intt struct{Bid int32; Name string}
		rows.Scan(&intt.Bid, &intt.Name)
		log.Println(intt)
	}
}
