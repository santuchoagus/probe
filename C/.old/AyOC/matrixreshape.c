#include <stdio.h>

#include <stdio.h>

void print_mtx_ptr(int *m, size_t rows, size_t cols) {
	for (size_t r=0; r < rows; ++r) {
		size_t c=0;
		printf("%s%s", r ? "\n" : "", !c ? "|" : "");
		for (c=0; c < cols; ++c) {
			printf("%d%s", *(m + r*cols + c), c == cols-1 ? "|" : ", ");
		}
	}
	printf("%s", "\n");
}

int main() {
	int a[2][3] = {{1,2,3}, {4,5,6}};
	int (*resh)[2] = (int (*)[2]) a; // {{1,2}{3,4}{5,6}}
	print_mtx_ptr((int*)a, 2, 3);
	 
	printf("Try programiz.pro\n");
	print_mtx_ptr((int*)a, 3, 2);
	return 0;
}
