.data
    str: .byte 0x64, 0x65, 0x63, 0x69, 0x70, 0x68, 0x65, 0x72, 0x65, 0x64
.text
addi s0, zero, 0x20

la s0, str          # s0 -> s[0]
xor s1, s1, s1      # int i = 0
li t2, 10           # M = 10

for:
bge s1, t2, endfor      # if i >= 10 jump.
add t0, s0, s1          # t0 = addr str[i]
lw t1, 0(t0)            # t1 = str[i]
addi t1, t1, -32        # t1 = str[i] - 32
sw t1, 0(t0)            # str[i] = str[i] - 32
addi s1, s1, 1          # i = i + 1
j for
endfor:
