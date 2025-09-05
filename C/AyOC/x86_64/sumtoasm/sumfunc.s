global sumar_2
section .data

section .text

sumar_2:
push rbp
mov rbp, rsp
mov eax, edi
add eax, esi
pop rbp
ret
