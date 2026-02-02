#include <stdint.h>
#include <stdio.h>
#include <stddef.h>
#include <inttypes.h>

typedef struct {char buff[7];} buff7_t;

int main(void) {
	buff7_t buffers[10];
	buff7_t* b2 = &buffers[8];
	buff7_t* b1 = &buffers[5];

	ptrdiff_t elem_diff_0 = b2 - b1;
	ptrdiff_t elem_diff_1 = b1 - b2;
	ptrdiff_t elem_diff_2 = ((intptr_t)b2 - (intptr_t)b1)/sizeof(buff7_t);

	printf("0:%td 1:%td 2:%td\n", elem_diff_0, elem_diff_1, elem_diff_2);
	
	intptr_t diff_3 = (intptr_t)b2 - (intptr_t)b1;
	intptr_t diff_4 = (intptr_t)b1 - (intptr_t)b2;
	uintptr_t diff_5 = (uintptr_t)b2 - (uintptr_t)b1;
	uintptr_t diff_6 = (intptr_t)b1 - (intptr_t)b2;
	printf("3:%" PRIdPTR " 4:%" PRIdPTR " 5:%" PRIuPTR " 6:%" PRIuPTR " \n", diff_3, diff_4, diff_5, diff_6);
	return 0;
}
