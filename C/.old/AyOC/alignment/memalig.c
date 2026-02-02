#include <stdio.h>
#include <stdint.h>

typedef struct {
		char c;
		uint32_t num;
} example_s;

int main(void) {
		example_s st = {.c='a', .num=0xee};
		printf("char: %zu\n", sizeof(char));
		printf("int: %zu\n", sizeof(uint32_t));
		printf("example_s: %zu\n", sizeof(example_s));
		return 0;
}
