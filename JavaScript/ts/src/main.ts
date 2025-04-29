console.log("hello world");

class Person<T> {
    public fullname : string;
    private t? : T;
    constructor(firstName: number);
    constructor(firstName: string);
    constructor(firstName: string, lastName?: string);
    constructor(firstName: string | number, lastName: string, t: T);
    constructor(firstName: string | number, lastName?: string, t?: T) {
        if (typeof firstName === "number") {
            firstName = "<Numeros>"
        } 
        this.fullname = firstName + (lastName ?? "") ;
        this.t = t;   
    }

    public getT() : T {
        if (this.t == undefined) {
            throw new TypeError("attribute 't' was not declared at creation: undefined");
        }
        return this.t;
    }
}

console.log((new Person("12344")).fullname);
console.log((typeof (new Person("Name", "Surname", 2e3)).getT()));
// console.log((new Person(12344)).getT()); // Arroja una excepción