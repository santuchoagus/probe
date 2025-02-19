package main

import (
	"html/template"
	"log"
	"net/http"
)

func main() {
	dogPageHandler := func(writer http.ResponseWriter, req *http.Request) {
		tmpl, err := template.ParseFiles("dog.gotmpl")
		if err != nil {
			log.Fatal("Unable to load template:", err)
		}
		tmpl.Execute(writer, nil)
	}
	fs := http.FileServer(http.Dir("assets/"))
	http.Handle("/resources/", http.StripPrefix("/resources/", fs))

	http.HandleFunc("/dog", dogPageHandler)
	http.ListenAndServe(":8080", nil)
}
