#include <iostream>

using namespace std;
int main() {
    int x,y,z;
    cout << "Enter three consecutive integers: ";
    
    cin >> x >> y >> z;
    
    if (x <= y) {
        if (y <= z) {
            // x <= y <= z
            cout << "(" << x << ", " << y << ", " << z << ")" << endl;
        } else if (x <= z) {
            cout << "(" << x << ", " << z << ", " << y << ")" << endl;
        } else {
            cout << "(" << z << ", " << x << ", " << y << ")" << endl;
        }
    } else { // y < x
        if (x <= z) {
            cout << "(" << y << ", " << x << ", " << z << ")" << endl;
        } else if (y <= z) {
            cout << "(" << y << ", " << z << ", " << x << ")" << endl;
        } else {
            cout << "(" << z << ", " << y << ", " << x << ")" << endl;
        }

    }
    return 0;
}
