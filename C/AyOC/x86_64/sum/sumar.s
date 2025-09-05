extern sumar_c
global _start

section .data

section .text
_start:
mov edi, 20
mov esi, 12
call sumar_c

mov rax, 60
mov rdi, 0
syscall
