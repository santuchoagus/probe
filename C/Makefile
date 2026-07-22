.PHONY: FORCE
CC = gcc
CFLAGS = -Wall -Wextra -pedantic

default:
	@echo "Usage: make <filename>"
%: %.c FORCE
ifeq ($(TEST), true)
	@if [ ! -f "$(@)_test.c" ]; then \
		echo "Missing testfile \"$(@)_test.c\" to build \"$@\""; \
		exit 1; \
	fi
	$(CC) $(CFLAGS) $(@)_test.c -o $(@)_test.out
	./$(@)_test.out
endif
ifneq ($(TEST), true)
	$(CC) $(CFLAGS) $< -o $@
endif

clean:
	rm -rf *.o *.out