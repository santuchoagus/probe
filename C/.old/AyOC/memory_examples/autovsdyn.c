int sum_to(int n);
int rec_sum_to(int n);
void justcreatelocals(int n);

int main(void) {
	int a = 5;
	sum_to(a);
	rec_sum_to(a);
	return 0;
}

int sum_to(int n) {
	int retv = 0;
	for (;  n > 0; n--)
		retv += n;
	return retv;
}

int rec_sum_to(int n) {
	if (n <= 0)
		return 0;
	return n + rec_sum_to(n-1);
}

void justcreatelocals(int n) {
	int foo = 2*n;
	if (n <= 0)
		return;
	justcreatelocals(n-1);
	return;
}

