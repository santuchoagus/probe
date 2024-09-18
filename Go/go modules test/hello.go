package main

import (
	"math"
)

type G interface {
	M()
}

type Person struct {
	Name string
	Surname string
}

type Vertex struct {
	X, Y float64
}

func (v Vertex) Scale(f float64) (ret Vertex) {
	ret.X = v.X * f
	ret.Y = v.Y * f
	return ret
}

func (v Vertex) Abs() float64 {
	return math.Sqrt(v.X*v.X + v.Y*v.Y)
}

func main() {
	var v = Vertex{4,3}
	println(v.Abs())
	var c = v.Scale(10.0)
	println(c.Abs(), ", ", v.Abs())
}

func (p Person) M() string {
	return p.Name + " " + p.Surname
}
