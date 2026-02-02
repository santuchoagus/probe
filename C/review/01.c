#ifndef TYPE_T
#define TYPE_T
typedef int type_t;
#endif

#ifndef MAIN
#define MAIN

int main(void) {
    /*(?) C is statically typed so a variable cannot change its type during runtime. */ */
    type_t x = 1290;
    
    /* https://en.cppreference.com/w/c/types/integer.html */ */

    /*Declaration: Where the variable is announced but not necessarily assigned a memory space*/
    extern int y;
    int sum(int a, int b);
    /*Definition: Where it is created or a memory is assigned */
    char c = 'a';
    int a; /* Defined with garbage values, except when the variable is defined globally */


    /* Duration:
        Static (Data): Variable exist during the entire program execution
        Automatic (Stack): Variable exist during the function execution (every local variable inside a function by default)
        Dynamic (Heap): Variable exist while memory is allocated
    */

    /* Memory:
        Code: Reserved read-only memory for the instructions
        Automatic: Reserved at runtime for local variables, can change during execution, it also called stack
        Dinamic: Reserved at runtime for dynamic variables, can change during execution, it also called heap
        Static: Reserved at runtime for static variables, can't change during execution, it also called data
   
        There is also a concept called linkage:
        External: The variable is visible in any file that includes its declaration (e.g. global variables)
        Internal: The variable is visible only in the file it is defined (e.g. static variables)
        None: The variable is not visible outside the scope it is defined (e.g. local variables)
    */


    /*Parameters of a function are always passed by value*/
    int *ptr;
    extern void foo(int *);
    foo(ptr); /* ptr is technically a copy of the original pointer */

    char *str1 = "This string is stored in the read-only data segment";
    /*Technically the variable itself "str1" is in the stack, but the string it points to is in the read-only data segment*/
    char str2[] = "This string is stored in the stack (automatic memory)";
    /*The entire array is stored in the stack*/

    /* Compiling generate .s files (assembly) */
    /* Assembler generate .o files (object files) without resolving symbols */
    /* Linking resolve the symbols (externs and functions) generate the final executable, this also includes the dynamic libraries */
    /* The linker can also generate shared libraries (.so) */


    /* Multidimensional arrays are arrays of arrays 
    Row major order.
    a[i][j] notation is equivalent to *(a[i] + j)
    which is also equivalent to *(a + i*COLS + j)
    The type of "a" is "int (*)[COLS]"
    The type of "a[i]" is "int *"
    The type of "a[i][j]" is "int"
    */
    #define COLS 3
    int a[][COLS] = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
    int (*ptr_a)[COLS] = a; /* type int (*)[COLS] */

    /* TODO: Function pointers */
    /* https://cdecl.org/ */

    /* TODO: GNU Debugger (gdb) */
    /* https://www.cs.cmu.edu/~gilpin/tutorial/ */
    /* Also Valgrind, LLDB, rr, ASan, man, info*/

    /* TODO: Kinds of memory leaks */
    /* Definitive, Possible, Indirectly*/

    /* TODO: Memory alignment */
    /* https://www.geeksforgeeks.org/memory-alignment-in-c/ */
    struct Example {
        char a; // 1 byte
        int b;  // 4 bytes
        char c; // 1 byte
    };
    /*
    The total size is 12 bytes, not 6 bytes.
    The compiler adds padding (empty bytes) to ensure each member is properly aligned.
    */
    /* 
        [a | _ | _ | _ ]
        [b | b | b | b ]
        [c | _ | _ | _ ]

        Underscore is padding

        alignof(struct Example) == 4
        sizeof(struct Example) == 12
    */
    
	return 0;
}
#endif