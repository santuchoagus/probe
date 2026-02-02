CC = gcc
CFLAGS = -Wall -Wextra -pedantic

ifdef SRC
TARGET = $(basename $(SRC))
BINARY = $(TARGET).out
endif

all: check-src $(BINARY)

check-src:
# SRC = hola.c
ifndef SRC
	$(error You must specify SRC, e.g. "make SRC=example.c")
endif
	@:

run: check-src $(BINARY)
	./$(BINARY)

$(BINARY): $(TARGET).o
	$(CC) $(CFLAGS) $(TARGET).o -o $@

$(TARGET).o: $(SRC)
	$(CC) $(CFLAGS) -c $(SRC) -o $@

clean:
	rm *.o *.out

.PHONY: all run clean check-src
