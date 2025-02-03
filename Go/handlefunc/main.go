package main

import (
	"net/http"
)


func main() {
	handler_home := func(writer http.ResponseWriter, req *http.Request) {
		writer.Write([]byte("Welcome to the homepage!\nCongrats."))
	}
	handler_sub := func(writer http.ResponseWriter, req *http.Request) {
		writer.Write([]byte("This is a subpage, specifically. " + req.URL.Path))
	}

	http.HandleFunc("/home", handler_home)
	http.HandleFunc("/sub/", handler_sub)

	http.ListenAndServe(":8080", nil)
}
