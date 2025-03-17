package main

import (
	"fmt"
	"io"
	"log"
	"os"
	"time"
)

func main() {
	// totalBytes, err := FileLen("file.txt")
	// if err != nil {
	// 	fmt.Println("Couldn't read:", err)
	// 	os.Exit(1)
	// }
	// fmt.Println("Total bytes readed from file.txt:", totalBytes)

	// ProcItemNilPointer()
	// ProcPrefixMethodValue()
	// procMethodSet()
	procInterfaceReceivers()
}

func ProcSwitchWithExpr() {
	a := 123
	switch {
	case a == 0:
		fmt.Println("Divisible by 0")
	case a%11 == 0:
		fmt.Println("Divisible by 11")
	case a == 123:
		fmt.Println("What a coincidence")
	}
}

func ProcModifySlices() {
	modMapFunc := func(m map[int]string) {
		m[0] = "Hello modified"
	}
	modSlicesFunc := func(s []int) {
		if len(s) > 1 {
			s[1] = -111
		} else {
			return
		}
		// This won't be reflected because s is a copy of the slice.
		s = append(s, 6)
	}

	mm := make(map[int]string)
	modMapFunc(mm)

	ss := make([]int, 0)
	ss = append(ss, 21)
	ss = append(ss, 22)
	modSlicesFunc(ss)

	// For maps, each change is reflected in the map
	// For slices, you can change them but they won't be expandable.
	fmt.Println(mm)
	fmt.Println(ss)
}

// Ch-5 filelen
func FileLen(fileName string) (bb int, err error) {
	file, err := os.Open(fileName)
	if err != nil {
		return 0, err
	}

	bbSlice := make([]byte, 16)
	bb = 0

	for {
		nBytes, err := file.Read(bbSlice)
		if err != nil {
			if err != io.EOF {
				// Returning bb anyways for debbuging purposes.
				return bb, err
			}
			break
		}

		bb += nBytes
	}
	return bb, nil
}

// Ch-6 - Exercise 1
type Person struct {
	FirstName string
	LastName  string
	Age       int
}

func MakePerson(firstName string, lastName string, age int) Person {
	return Person{FirstName: firstName, LastName: lastName, Age: age}
}

// This will escape the heap because it's returning a pointer, which in another language like C or C++
// would be invalid because you're returning a local variable which is on the stack.
// &Person{...} escapes to heap
func MakePersonPointer(firstName string, lastName string, age int) *Person {
	return &Person{FirstName: firstName, LastName: lastName, Age: age}
}

// This would say on the console:
// moved to heap: ret
// it is technically the same thing, but one first moves it to heap memory instead of leaving it on stack
func RetInt(input int) (ret *int) {
	ret = &input
	return ret
}

// ###### Pointer receivers that handle nil instances
type Position struct {
	X, Y int
}

type WorldObject struct {
	ItemInfo string
	Position *Position
}

func CreateItem(itemInfo string) WorldObject {
	return WorldObject{ItemInfo: itemInfo}
}

// This func will check if it's inside of the square at the origin using it's apothem
// Doing this to show that the func handles worldObjects without any position (<nil> positions)
func (wo WorldObject) ApothemFromOrigin(apothem int) bool {
	if wo.Position == nil {
		fmt.Println("Trying to acces a position of a world object not initialized in the world coordinates... ITEM:", wo.ItemInfo)
		return false
	}

	if wo.Position.X*wo.Position.X <= apothem*apothem &&
		wo.Position.Y*wo.Position.Y <= apothem*apothem {
		return true
	}

	return false
}

func ProcItemNilPointer() {
	item := CreateItem("Mida's Gauntlet")
	item.Position = &Position{4, -2}
	fmt.Println("*** New Item:", item)
	fmt.Println(item.ItemInfo, "is inside the square:", item.ApothemFromOrigin(3))

	item = CreateItem("Mida's Boots")
	fmt.Println("*** New Item:", item)
	fmt.Println(item.ItemInfo, "is inside the square:", item.ApothemFromOrigin(3))

	item = CreateItem("Mida's Glasses")
	item.Position = &Position{1, -3}
	fmt.Println("*** New Item:", item)
	fmt.Println(item.ItemInfo, "is inside the square:", item.ApothemFromOrigin(3))
}

// Method values (methods as functions)
type Prefixer struct {
	PrefixStr string
}

func (prefix *Prefixer) Prefix(sPref string) {
	prefix.PrefixStr = sPref
}

func (prefix Prefixer) Println(message string) {
	fmt.Println(prefix.PrefixStr, message)
}

func ProcPrefixMethodValue() {
	pref := Prefixer{"[REDACTED]:"}
	chat := pref.Println

	chat("Here is some sensitive message")

	// Here is the weird behaviour
	Prefixer.Println(Prefixer{"Admin:"}, "Hello")
	// same idea:
	chat2 := Prefixer.Println
	chat2(Prefixer{"Admin:"}, "Goodbye")

	//TODO: Theorically this is useful for dependency injection
}

func ProcTypeConversion() {
	type Score int
	type HighScore Score

	var i int = 120
	var s Score = 100
	var hs HighScore = 150

	hs = HighScore(i)
	s = 50
	s = Score(hs)
	i = int(s)
	hs = HighScore(s) + 203
}

// Inner outer example, type embedded
// You can edit this code!
// Click here and start typing.
type Inner struct {
	A int
}
type Outer struct {
	Inner
	S string
}

func (i Inner) Met0() {
	fmt.Println("Inner Met0:", i)
}

func (i Inner) Met() {
	fmt.Println("Inner:", i)
}

func (o Outer) Met() {
	fmt.Println("Outer:", o)
}

func ProcInnerVsOuter() {
	ou := Outer{
		Inner: Inner{10},
		S:     "Hello outer",
	}

	ou.Met()
}

// The method set of a pointer instance implement both the value and the pointer receiver,
// while the method set of a value instance only constains the value receivers.
type TypeStruct struct {
	Name string
}

func (ts TypeStruct) MValue() string {
	return ts.Name
}

func (ts *TypeStruct) MRef(name string) {
	ts.Name = name
}

func procMethodSet() {
	var ptrInstance *TypeStruct = new(TypeStruct)
	ptrInstance.MRef("Foo")
	log.Println("Hello", ptrInstance.MValue())

	var valInstance TypeStruct = TypeStruct{"Bar"}
	valInstance.MRef("Bar (Renamed)")
	log.Println("Hello", valInstance.MValue())
}

// A quick lesson on interfaces (Learning go. Page 157)
type Counter struct {
	total       int
	lastUpdated time.Time
}

func (c *Counter) Increment() {
	c.total++
	c.lastUpdated = time.Now()
}

func (c Counter) String() string {
	return fmt.Sprintf("total: %d, last updated: %v", c.total, c.lastUpdated)
}

type Incrementer interface {
	Increment()
}

// type Stringer interface {
// 	String()
// }

func procInterfaceReceivers() {
	var myStringer fmt.Stringer
	var myIncrementer Incrementer
	myStringer.String()
	myIncrementer.Increment()

	pointerCounter := &Counter{}
	valueCounter := Counter{}

	myStringer = pointerCounter // ok
	myStringer = valueCounter
	myIncrementer = pointerCounter // ok
	// myIncrementer = valueCounter // this one fails
	valueCounter.Increment() // However this works for some reason
}
