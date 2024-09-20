#include <iostream>

using namespace std;

int main() {
    string name;
    int number;

    // This will get a random number from memory on each execution
    int initnumber;
    cout << initnumber << endl;

    std::cout << "Insert a name please: ";
    std::cin >> name;
    std::cout << "Welcome " << name << "!\n";

    std::cout << "Insert a number please: ";
    std::cin >> number;
    std::cout << "Your number is " << number << "!\n";

    // Version 2, two consecutive cin
    std::cout << "Insert a name followed by a number please: ";
    std::cin >> name;
    std::cin >> number;
    std::cout << "Welcome " << name << " Your number is " << number << "!\n";
    return 0;
}

