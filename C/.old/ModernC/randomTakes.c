#include <stdio.h>
#include <unistd.h>

void what(int n);

int main(void) {
    // Pass grade or not
    int score = 2u;

    switch (score) {
    case 10: case 9: case 8: case 7: case 6:
        printf("Grade passed with %d congrats\n", score);
        break;
    default:
        printf("Grade not passed sadly, points: %d\n", score);
    }
    what(0);
    what(0);
    return 0;
}

void what(int n) {
   static int count = 0;
   count++;
   printf("Current count %d\n", count);
   return;
}
