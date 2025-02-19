package main

import (
	"html/template"
	"log"
	"net/http"
)

func main() {
	dogpicHandler := func(writer http.ResponseWriter, req *http.Request) {
		http.ServeFile(writer, req, "dog.jpg")
	}
	dogPageHandler := func(writer http.ResponseWriter, req *http.Request) {
		tmpl, err := template.ParseFiles("dog.gotmpl")
		if err != nil {
			log.Fatal("Unable to load template:", err)
		}
		tmpl.Execute(writer, nil)
	}

	http.HandleFunc("/dog.jpg", dogpicHandler)
	http.HandleFunc("/dog", dogPageHandler)
	http.ListenAndServe(":8080", nil)
}
