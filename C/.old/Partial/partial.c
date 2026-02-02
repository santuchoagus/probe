#include "expand.h"

#ifndef PARTIAL_H
#define PARTIAL_H

typedef struct Partial * (*partial_func_t)();

typedef struct Partial {
    unsigned char arity;
    partial_func_t func;
} Partial;

#endif

#define PARTIAL(arity, ret_t, name, ...) \
EXPAND_PART_##arity(ret_t, name, __VA_ARGS__) \
typedef struct Partial_ ## ret_t (*partial_ ## ret_t ## _func_t)(); \

PARTIAL(3,
    int, addthree, int a, int b, int c, {
    return a + b + c;
});

int main(int argc, char* argv[]) {
    
    partial_func_t pf = addthree(3);
    pf = pf(2);
    int result = pf(1);
    return 0;
}