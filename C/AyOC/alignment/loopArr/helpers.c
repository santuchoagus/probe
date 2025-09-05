#include <stdio.h>
#include <stdint.h>



typedef struct {
		long id;
		char comision;
		int32_t dni;
		char basura[2];
} st;
/*
 *[iiii|iiii]
 *[c###|dddd]
 *[bb##|####]
 * */

extern void recorrer_st(st *, size_t len);

int main(void) {
		st arr[4] = {
				{1, 'A', 4059},
				{2, 'B', 31059},
				{3, 'C', -532},
				{4, 'D', 11}
		};

		recorrer_st(arr, 4);
		return 0;
}

void helperprint(long id, char comision, int32_t dni) {
		printf("%ld %c %d\n", id, comision, dni);
}
