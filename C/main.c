#include <stdio.h>
#include <stdlib.h>
#include "examples/matrix.c"
#include "examples/opaque.h"
#include "examples/hailstones.c"

#define PRIO_T_DEFAULT_CAP 2
#include "examples/generic1.h"

#define PRINT_QUEUE(queue, format) \
{ \
    (printf("top=%zu cap=%zu values=[", queue->top, queue->cap)); \
    for (size_t i = 0; i < queue->top; ++i) { \
        printf(format, queue->buf[i]); \
        if (i < queue->top - 1) printf(", "); \
    } \
    printf("]\n"); \
}

int compare_func(int a, int b) { return((a > b) - (a < b)); } 
REGISTER_PRIO_T(int, compare_func)

int main(int argc, char *args[]) {
    hailstones(2);
    hailstones(3);
    hailstones(4);
    hailstones(5);
    hailstones(7);

    
    __opaque_example__();

    int_prio_t *queue = create_int_prio();
    push_int_prio(queue, -1);
    PRINT_QUEUE(queue, "%d");
    push_int_prio(queue, 5);
    PRINT_QUEUE(queue, "%d");
    push_int_prio(queue, 0);
    PRINT_QUEUE(queue, "%d");
    pop_int_prio(queue, NULL);
    PRINT_QUEUE(queue, "%d");

    for (size_t i = 0; i < argc; ++i)
        printf("%s\n", args[i]);

    int n = 3, m = 4;
    double count = 0;
    double **mat = alloc_double_matrix(n, m);
    
    for (size_t i = 0; i < n; ++i) {
        for (size_t j = 0; j < m; ++j) {
            mat[i][j] = count++;
            printf("%.2f ", mat[i][j]);
        }
        printf("\n");
    }

    mat = NULL;
    return(0);
}