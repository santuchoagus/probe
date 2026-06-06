#include <unordered_map>

static std::unordered_map<int, bool> GLOBAL_ONCE_FLAGS;
// Executes statement once
#define ONCE(stmt) \
    do { \
        int flag = __COUNTER__;\
        if (GLOBAL_ONCE_FLAGS.contains(flag)) \
            break; \
        GLOBAL_ONCE_FLAGS.insert({flag, true}); \
        stmt; \
    }while(0)