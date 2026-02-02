.PHONY: FORCE
CC = gcc
CFLAGS = -Wall -Wextra -pedantic

default:
	@echo "Usage: make <filename>"

Makefile:;

%.c: FORCE
	$(CC) $(CFLAGS) $@ -o a.out

clean:
	rm -rf *.o *.out