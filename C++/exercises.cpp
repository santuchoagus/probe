#include <deque>
#include <list>
#include <vector>
#include <iostream>
#include <string>

#define DOCTEST_CONFIG_IMPLEMENT_WITH_MAIN
#include "doctest.h"

TEST_CASE("Exercise 9.14") {
    // Write a program to assign the elements from a list of char* pointers to C-style character strings to a vector of strings.

    std::list<const char *> ls{"foo", "bar", "baz"};
    std::vector<std::string> ls2;
    ls2.assign(ls.begin(), ls.end());

    REQUIRE(ls2.at(0) == std::string("foo"));
    REQUIRE(ls2.at(1) == std::string("bar"));
    REQUIRE(ls2.at(2) == std::string("baz"));
}