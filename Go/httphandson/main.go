package main

import (
	"log"
	"net/http"
	"text/template"
)

func homePage(writer http.ResponseWriter, req *http.Request) {
	tmpl, err := template.ParseFiles("template.gotmpl")
	if err != nil {
		log.Fatal("Couldn't read template template.gotmpl")
	}
	tmpl.Execute(writer, nil)
	//writer.Write([]byte("Welcome to the home page"))
}

func dogPage(writer http.ResponseWriter, req *http.Request) {
	dogList := []string{"Firulais", "Sisa", "Piro"}
	tmpl, err := template.ParseFiles("dogtmpl.gotmpl")
	if err != nil {
		log.Fatal("Couldn't read template dog.gotmpl", err)
	}
	log.Print(err)
	tmpl.Execute(writer, dogList)
}

func mePage(writer http.ResponseWriter, req *http.Request) {
	writer.Write([]byte("Name"))
}

func main() {
	//http.HandleFunc("/", homePage)
	http.HandleFunc("/dog",dogPage)
	http.HandleFunc("/me", mePage)
	http.ListenAndServe(":8080", nil)
}
