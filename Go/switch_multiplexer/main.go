package main

import "net/http"


func main() {
	// http.Handle("hello1", handler http.Handler)
	handler := handler{"Superior handler"}
	http.ListenAndServe(":8080", handler)
}


type handler struct {
	name string
}

func (hand handler) ServeHTTP(writer http.ResponseWriter, req *http.Request) {
	switch req.URL.Path {
		case "/dog":
			writer.Write([]byte("Dog!"))
		case "/dog/dog":
			writer.Write([]byte("two dogs!"))
		default:
		str := "Here is a string of text for you request: " + req.Method + req.URL.Path
		writer.Write([]byte(str))
	}
return
}
