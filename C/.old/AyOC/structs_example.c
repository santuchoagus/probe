#include <stdio.h>
#include <string.h>
#define NAME_LEN 32

typedef struct {
	char name[NAME_LEN + 1]; // \0 at the end
	float ranking;
} book_t;

void print_book(book_t book) {
	printf("%s: \"%.1f\"\n",book.name, book.ranking);
}

int main(void) {
	book_t book1 = {.name = "Foo Adventures", .ranking = 9.3f};
	book_t book2 = {.name = "Bar Horror", .ranking = 7.0f};
	
	print_book(book1);
	print_book(book2);

	// = is strict copy.
	book1 = book2;
	book2.ranking = 3.33f;
	strcpy(book1.name, "Foo Bar");

	print_book(book1);
	print_book(book2);

	return 0;
}
