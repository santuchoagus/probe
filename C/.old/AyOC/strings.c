#include <stdio.h>

void modifyArr1(char str[]) {
	str[0] = '1';
	printf("ModifyArr1: %s\n", str);
}

void modifyArr2(char* str) {
	str[0] = '2';
	printf("ModifyArr2: %s\n", str);
}

int main() {
	// C copies string literal "string\0" into a.
	char a[] = "string";
	// b points to "string\0" string literal.
	char* b = "string";

	a[0] = 'S';
	//b[0] = 'S';

	printf("a: %s\n", a);
	printf("b: %s\n", b);

	char* c = a;
	a[0] = 'X';
	printf("c: %s\n", c);

	modifyArr1(a);
	modifyArr2(a);

	// modifyArr1(b);
	modifyArr1(b);
	return 0;
}

/* What happens if a function takes char* as param then?
 * can you pass it a string literal or simply a char[]?
 * How the user would know that it expect char[] or string literal?
 * */
