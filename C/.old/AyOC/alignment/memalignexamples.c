#include <stdint.h>
#include <stdio.h>

struct st_1 {
		char a[3];
		uint32_t b;
};
/*
 *[aaa-|bbbb]
 * */

struct st_2 {
		uint32_t a;
		char b[3];
		uint32_t c;
};
/*
 *[aaaa|bbb-]
 *[cccc|----]
 * */

struct st_3 {
		uint16_t a;
		char b[3];
		uint32_t c;
};
/*[aabb|b---]
 *[cccc|----]
 * */

void call(void) { return; }

int main(void) {
		// inits
		struct st_1 st_1 = {.a="aaa", .b=0xffffffff};
		struct st_1 st_11 = {.a="aaa", .b=0xffffffff};
		struct st_1 st_12 = {.a="aaa", .b=0xffffffff};
		struct st_1 st_13 = {.a="aaa", .b=0xffffffff};
		struct st_1 st_14 = {.a="aaa", .b=0xffffffff};
		struct st_1 st_15 = {.a="aaa", .b=0xffffffff};
		
		struct st_2 st_2 = {.a=0xffffffff, .b="aaa", .c=0xeeeeeeee};
		struct st_3 st_3 = {.a=0xeeee, .b="aaa", .c=0xffffffff};
		struct st_3 st_3_2 = {.a=0xeeee, .b="aaa", .c=0xffffffff};
		struct st_3 st_3_3 = {.a=0xeeee, .b="aaa", .c=0xffffffff};
		struct st_3 st_3_4 = {.a=0xeeee, .b="aaa", .c=0xffffffff};
		struct st_3 st_3_5 = {.a=0xeeee, .b="aaa", .c=0xffffffff};
		call();
		printf("st_1 %zu\n", sizeof(struct st_1));
		printf("st_2 %zu\n", sizeof(struct st_2));
		printf("st_3 %zu\n", sizeof(struct st_3));
		return 0;
}
