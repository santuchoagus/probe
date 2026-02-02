typedef struct Partial Partial;

#ifndef EXPAND_PART_H
#define EXPAND_PART_H

#define EXPAND_PART_1(ret_t, name, a1, block) \
ret_t name ## __part1_(a1);

#define EXPAND_PART_2(ret_t, name, a2, a1, block) \
typedef ret_t (* name ## __part1__t)(a1); \
EXPAND_PART_1(ret_t, name, a1, block)\
name ## __part1__t name ## __part2_(a2) { \
    return &name ## __part1_; \
}

#define EXPAND_PART_3(ret_t, name, a3, a2, a1, block) \
typedef name ## __part1__t (* name ## __part2__t)(a2, a1); \ 
EXPAND_PART_2(ret_t, name, a2, a1, block);\
name ## __part2__t name ## __part3_(a3) { \
    return &name ## __part2_; \
}

#endif

EXPAND_PART_3(int, addthree, int a, float b, char c, {
    return a;
});