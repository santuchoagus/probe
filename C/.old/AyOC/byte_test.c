#include <stdio.h>
#include <stdint.h>
#include <stddef.h>
#include <inttypes.h>
#include <string.h>

int main(void) {
	uint32_t a = 100;
	int32_t b = 100;
	unsigned char num[4];
	num[0] = 0x00;
	num[1] = 0x00;
	num[2] = 0x00;
	num[3] = 0x00;

	char num3[4] = {'0'};
	strncpy(num3, (char *)(&a), 4);

	unsigned char *num4 = (unsigned char *)&b;
	num4[3] = 0xFF;
	num4[2] = 0xFF;
	num4[1] = 0xFF;
	num4[0] = 0xFE;

	printf("%"PRIu32"\n", (uint32_t)(*num));
	printf("%"PRIu32"\n", (uint32_t)(*num3));
	printf("%"PRId32"\n", b);
	return 0;
}
