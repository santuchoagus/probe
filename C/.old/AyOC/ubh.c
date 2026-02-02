void f(int * restrict a, int * restrict b) {
    (*a)++;
    (*b)++;
}

int main(void) {
    int a[2];
    f(a, a+1);
    return 0;
}
