#include <stdio.h>
#define OPT1 1
#define OPT2 2

size_t change_to_opt2(void);
void print_state(size_t);

int state = OPT1;

int main(void) {
	size_t c = change_to_opt2();
	print_state(c);
	c = change_to_opt2();
	print_state(c);
	return 0;
}

size_t change_to_opt2(void) {
	static size_t times_changed = 0;
	state = OPT2;
	times_changed++;
	return times_changed;
}

void print_state(size_t c) {
	printf("state=%s, times=%zu\n", state == OPT1 ? "OPT1" : "OPT2", c);
}
