package main

import (
	"html/template"
	"log"
	"net/http"
)


func main() {
	http.HandleFunc("/", index)
	http.HandleFunc("/dark", setDark)
	http.HandleFunc("/light", setLight)
	http.ListenAndServe(":8080", nil)
}

func setDark(w http.ResponseWriter, req *http.Request) {
	http.SetCookie(w, &http.Cookie{Name: "Mode", Value: "dark"})
}

func setLight(w http.ResponseWriter, req *http.Request) {
	http.SetCookie(w, &http.Cookie{Name: "Mode", Value: "light"})
}

func index(w http.ResponseWriter, req *http.Request) {
	var mode string
	cookie, err := req.Cookie("Mode")

	if err == http.ErrNoCookie {
		mode = "light"
	} else {
		mode = cookie.Value
	}

	tmpl, err := template.ParseFiles("index.tmpl")
	if err != nil {
		log.Fatal("Unable to parse index")
	}
	err = tmpl.Execute(w, struct {Mode string}{Mode:mode})
	if err != nil {log.Fatal(err)}
	log.Print("Logged cookie:",mode)
}
