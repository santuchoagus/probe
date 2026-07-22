#include <stddef.h>
#include <stdio.h>
#include <assert.h>

static void (*__tests_arr[1024])(void);
static const char *__tests_names[1024];

static size_t __tests_count = 0;

#define TEST(description) \
    static void test_ ## description(void); \
    static void __attribute__((constructor)) register_ ## description(void) { \
        __tests_arr[__tests_count] = test_ ## description; \
        __tests_names[__tests_count++] = "test " # description; \
    } \
    static void test_ ## description(void)

#define RUN_TESTS() \
    do { for (size_t i = 0; i < __tests_count; i++) { \
        __tests_arr[i]();\
        printf("- Test passed: \"%s\"\n", __tests_names[i]);\
    }} while (0)

#ifdef USE_TEST_MAIN
int main(void) { RUN_TESTS(); return 0; }
#endif

/* USAGE:
TEST(const_player_is_initialized) {
    Game *game = new_game(4);
    assert(num_players(game) == 4);
}
*/