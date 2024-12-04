package greetings2

type person struct {
	x int
	y int
}

func NewPerson(x int, y int) (*person) {
	return &person{x,y}
}

func (p person) Move(x, y int) {
	p.x += x
	p.y += y
}
