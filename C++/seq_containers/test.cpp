#include <deque>
#include <list>
#include <vector>

#define DOCTEST_CONFIG_IMPLEMENT_WITH_MAIN
#include "doctest.h"

TEST_CASE("vector") {
    char arr[] = {1,23,2,32,2};
    REQUIRE(sizeof(arr) == 5);
}

TEST_CASE("deque") {
    //int arr[] = {1,23,2,32,2};
    std::deque<int> queue;
    queue.push_back(3);
    queue.push_back(4);
    queue.push_back(10);
    REQUIRE(queue.front() == 3);
    queue.pop_front();
    REQUIRE(queue.front() == 4);
    queue.pop_front();
    REQUIRE(queue.front() == 10);
    queue.pop_front();
}

TEST_CASE("Iterators") {
    std::list<int> lst1{1,3,20,1,40};
    std::list<int>::iterator iter1 = lst1.begin(), iter2 = lst1.end();
    std::list<int>::iterator last = lst1.end();
    last--;
    std::list<int>::difference_type diff = std::distance(iter1, iter2);
    printf("%ld\n", diff);
}

TEST_CASE("Swap") {
    std::vector<int> v1{1, 33, 10};
    std::vector<int> v2{10, 1};
    v1.swap(v2);
    REQUIRE(v1 == std::vector<int>{10, 1});
    REQUIRE(v2 == std::vector<int>{1, 33, 10});
}