#include <iostream>
#include <typeinfo>
#include <fstream>
#include <vector>
#include <ranges>
#include <sstream>

#include "string_and_vectors.h"
#include "oncemacro.hpp"
#include "Point.hpp"

void example_exceptions(void) {
    throw overflow_error("Nothing");
}

void example_1(void) {
    std::cout << "Enter two numbers:" << std::endl;
    int v1 = 0, v2 = 0;
    std::cin >> v1 >> v2;
    std::string s = std::format("The sum of {} and {} is {}", v1, v2, v1 + v2);
    std::cout << s << std::endl;

    while (std::cin >> v1) {
        std::cout << "value: " << v1 << std::endl;
    }
}

void example_point_objects() {
    Point a{"group_a"}, b{"group_b"};
    print(std::cout, a);
    if (read(std::cin, a) && read(std::cin, b)) {
        if (a == b) throw runtime_error("Cannot define the same point");
        
        a.combine(b);
        print(std::cout, a) << std::endl;
    } else {
        std::cerr << "No data given" << std::endl;
    }
}

#include "Screen.hpp"
void example_screen() {
    Screen screen = Screen(8, 16, '#', '.');
    screen.display(std::cout).move(2, 2).display(std::cout);
}

void trimStringPrint(std::string s) {
    decltype(s.size()) i = 0, j = s.size() - 1;
    for (; i < s.size() && isspace(s[i]); i++);
    for (; j > 0 && isspace(s[j]); j--);

    std::string res;
    for (decltype(i) k = i; k < j; k++) 
        res += s[k];

    std::cout << std::format("i={} j={}, \"{}\"\n", i, j, res);
}

void io_library(char *n) {
    std::istringstream str = std::istringstream(n);
    int a;
    str >> a;
    printf("%d\n", a);

    if (str.rdstate() & std::ios::eofbit)
        printf("eofbit\n");
    if (str.rdstate() & std::ios::failbit)
        printf("failbit\n");
    if (str.rdstate() & std::ios::badbit)
        printf("badbit\n");
}

void copy_stream(void) {
    std::istringstream s1 = std::istringstream("foo bar baz");
    string str;
    while (s1 >> str)
        printf("%s\n", str.c_str());
}

void do_stuff_once(void) {
    printf("foo\n");
    ONCE(printf("foobarbar2\n"));
}



int main(int argc, char *argv[]) {
    // copy_stream();
    if (argc > 1) io_library(argv[1]);
    // example_point_objects();
    // example_screen();

    // const int * ptr = NULL;
    // std::cout << std::format("{:<5}\n", typeid(ptr).name()) << std::endl;

    // int a(230);
    // std::cout << a << "\n" << std::endl;
    // trimStringPrint("     hello, world!   ");
    // string_and_vector("example.json");
    // try {
    //     example_exceptions();
    // } catch(overflow_error e) {
    //     std::cout << "Caught exception: " << e.what() << std::endl;
    // }
    return 0;
}
