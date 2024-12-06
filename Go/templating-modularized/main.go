package main

// # # # # # # # # # # # # # # #
// # NESTED TEMPLATES EXAMPLE  #
// # # # # # # # # # # # # # # #

import (
	"log"
	"os"
	"text/template"
)

type Vector2d struct {
	X, Y float64
}

func (V Vector2d) Quadrant() string {
	x, y := V.X, V.Y
	if (x > 0 && y > 0) { return "First Quadrant"}
	if (x < 0 && y < 0) { return "Third Quadrant"}
	if (x < 0 && y > 0) { return "Second Quadrant"}
	if (x > 0 && y < 0) { return "Fourth Quadrant"}
	return "No Quadrant (Zero vector)"
}

func main() {
	// Name of template has to be one of the filenames on the list
	// This will fail ---> tpl := template.New("RandomTpl")
	tpl := template.New("main.tmpl")
	var err error
	tpl, err = tpl.ParseFiles("auxWithData.tmpl", "aux.tmpl", "main.tmpl", "Methods.tmpl")
	if err != nil {
		log.Fatal("Unable to parse templates, err:",err)
	}

	data := struct {
		Data int;
		Vectors []Vector2d;
	}{
		1293012,
		[]Vector2d{{1,31},{0,0},{-3,-12},{3,-1},},
	}
	err = tpl.Execute(os.Stdout, data)

	if err != nil {
		log.Fatal("Unable to execute template, err:",err)
	}
    return
}
