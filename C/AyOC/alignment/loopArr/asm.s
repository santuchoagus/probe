extern helperprint
global recorrer_st

section .text

; rdi --> st arr[0]
; rsi --> length

recorrer_st:
push rbp
mov rbp, rsp
push rbx
push r12

; rbx length, used for counting
mov rbx, rsi

; r12 is the pointer
mov r12, rdi

.loop:
mov rdi, QWORD [r12]
mov sil, BYTE [r12 + 8]
mov edx, DWORD [r12 + 12]

call helperprint

add r12, 24
dec rbx
jnz .loop


pop r12
pop rbx
pop rbp
ret
