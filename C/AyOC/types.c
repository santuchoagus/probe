#include <stdio.h>
#include <stdint.h>

int main(void) {
	printf("signed char(%zu)\n", 8*sizeof(signed char));
	printf("unsigned char(%zu)\n", 8*sizeof(unsigned char));
	printf("char(%zu)\n", 8*sizeof(char));
	printf("short(%zu)\n", 8*sizeof(short));
	printf("unsigned short(%zu)\n", 8*sizeof(unsigned short));
	printf("int(%zu)\n", 8*sizeof(int));
	printf("unsigned int(%zu)\n", 8*sizeof(unsigned int));
	printf("long(%zu)\n", 8*sizeof(long));
	printf("unsigned long(%zu)\n", 8*sizeof(unsigned long));
	printf("long long(%zu)\n", 8*sizeof(long long));
	printf("unsigned long long(%zu)\n", 8*sizeof(unsigned long long));
	printf("float(%zu)\n", 8*sizeof(float));
	printf("double(%zu)\n", 8*sizeof(double));
	printf("long double(%zu)\n", 8*sizeof(long double));

	printf("int8_t(%zu)\n", 8*sizeof(int8_t));
	printf("int16_t(%zu)\n", 8*sizeof(int16_t));
	printf("uint8_t(%zu)\n", 8*sizeof(uint8_t));
	printf("intptr_t(%zu)\n", 8*sizeof(intptr_t));
	printf("char*(%zu)\n", 8*sizeof(char*));
	printf("long*(%zu)\n", 8*sizeof(long*));
	printf("size_t(%zu)\n", 8*sizeof(size_t));

	printf("%s\n", "\n# references:");
	printf("%s\n", "- https://en.cppreference.com/w/c/types/integer.html");
	printf("%s\n", "- https://en.cppreference.com/w/c/language/arithmetic_types.html");
	printf("%s", "\n");
	return 0;
}
