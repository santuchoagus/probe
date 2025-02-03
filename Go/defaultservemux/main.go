package main

import (
	"net/http"
)

type handler struct { }

type homepage handler
type sub handler

func (hand homepage) ServeHTTP(writer http.ResponseWriter, req *http.Request) {
	writer.Write([]byte("Welcome to the homepage!\nCongrats."))
}

func (hand sub) ServeHTTP(writer http.ResponseWriter, req *http.Request) {
	writer.Write([]byte("This is a subpage, specifically. " + req.URL.Path))
}

func main() {
	http.Handle("GET /home", homepage{})
	http.Handle("GET /subpage/", sub{})
	http.ListenAndServe(":8080", nil)
}
