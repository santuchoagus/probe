#include <stdio.h>
#include <assert.h>


void rot_simple(int *arr, size_t len);
void print_int_arr(int *arr, size_t len);


int main() {
	int arr[5] = {6,11,3,6,5};
	print_int_arr(arr, 5);

	rot_simple(arr, 5);
	print_int_arr(arr, 5);
	return 0;
}

void rot_simple(int *arr, size_t len) {
	assert(len > 1);

	int firstElem = arr[0];
	
	for (size_t i = 1; i < len; i++)
		arr[i-1] = arr[i];

	arr[len - 1] = firstElem;
	return;
}

void print_int_arr(int *arr, size_t len) {
	for (size_t i = 0; i < len; i++) 
		printf("%d ", arr[i]);

	printf("\n");
}
