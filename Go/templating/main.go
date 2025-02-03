package main

import (
	"log"
	"os"
	"text/template"
	"time"
)

func f1(x float64) float64 {
	return x*x
}
func f2(x,y float64) float64 {
	return float64(x+y)
}

func main() {
	var tpl *template.Template = template.New("Example")
	var err error

	// Templating time
	tpl, err = tpl.Parse("Today is: {{.Time}}!\n")

	if err != nil { log.Fatal("Unable to parse template") }

	err = tpl.Execute(os.Stdout, struct{Time string}{time.Now().Format("\nExample 1:\nMonday 2-1-2006")})


	// Templating pipelining with functions
	//tpl = template.New("Example2")
	funcMap := template.FuncMap{"f1": f1, "f2":f2}
	tpl, err = tpl.Funcs(funcMap).Parse("\nExample 2:\n{{f2 .X .X}}\n")
	err = tpl.Execute(os.Stdout, struct {X float64} {13})

	// Pipelining multiple funcs
	tpl, err = tpl.Funcs(funcMap).Parse("\nExample 3:\nToday is: {{.Time}}!\n{{f2 .X .X | f1}}\n")
	output := struct {
		Time string
		X float64
	} {
		Time: time.Now().Format("Monday 2-1-2006"),
		X: 3,
	}
	err = tpl.Execute(os.Stdout, output) // Should output (3 + 3)^2 = 36
    return
}
