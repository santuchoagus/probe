package main

import (
	"html/template"
	"log"
	"net/http"
)

var tpl *template.Template

func init() {
	var err error
	tpl, err = template.ParseGlob("templates/*.gohtml")
	if err != nil {
		log.Fatal("Unable to load templates:", err)
	}
}

func main() {
	http.HandleFunc("/", index)
	http.HandleFunc("/about", about)
	http.HandleFunc("/contact", contact)
	http.HandleFunc("/apply", apply)
	http.HandleFunc("/applyProcess", applyProcess)
	http.ListenAndServe(":8080", nil)
}

func index(w http.ResponseWriter, req *http.Request) {
	err := tpl.ExecuteTemplate(w, "index.gohtml", nil)
	HandleError(w, err)
}

func about(w http.ResponseWriter, req *http.Request) {
	err := tpl.ExecuteTemplate(w, "about.gohtml", nil)
	HandleError(w, err)
}

func contact(w http.ResponseWriter, req *http.Request) {
	err := tpl.ExecuteTemplate(w, "contact.gohtml", nil)
	HandleError(w, err)
}

func apply(w http.ResponseWriter, req *http.Request) {
	var err error
	switch req.Method {
		case "GET":
			err = tpl.ExecuteTemplate(w, "apply.gohtml", nil)
		case "POST":
			err = tpl.ExecuteTemplate(w, "applyProcess.gohtml", nil)
	}
	HandleError(w, err)

}

func applyProcess(w http.ResponseWriter, req *http.Request) {
	err := tpl.ExecuteTemplate(w, "applyProcess.gohtml", nil)
	HandleError(w, err)
}

func HandleError(w http.ResponseWriter, err error) {
	if err != nil {
		http.Error(w, err.Error(), http.StatusInternalServerError)
		log.Fatalln(err)
	}
}
