#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>

struct Bounds{
    int base;
    int size;
};

void decimalToHex(int decimal) {
    char hex[20];
    int index = 0;
    if (decimal == 0) {
        printf("Hexadecimal: 0\n");
        return;
    }
    while (decimal > 0) {
        int remainder = decimal % 16; 
        if (remainder < 10) {
            hex[index++] = '0' + remainder;
        } else {
            hex[index++] = 'A' + (remainder - 10); 
        }
        decimal /= 16; 
    }
    hex[index] = '\0'; 
    printf("Hexadecimal: ");
    for (int i = index - 1; i >= 0; i--) {
        printf("%c", hex[i]);
    }
    printf("\n");
}

void hexToDec(char hex[], char dec[]){
    dec[0]='\0';
    for(int i=0; i<strlen(hex); i++){
        switch (hex[i]){
            case '0' : strcat(dec, "0000"); break;
            case '1' : strcat(dec, "0001"); break;
            case '2' : strcat(dec, "0010"); break;
            case '3' : strcat(dec, "0011"); break;
            case '4' : strcat(dec, "0100"); break;
            case '5' : strcat(dec, "0101"); break;
            case '6' : strcat(dec, "0110"); break;
            case '7' : strcat(dec, "0111"); break;
            case '8' : strcat(dec, "1000"); break;
            case '9' : strcat(dec, "1001"); break;
            case 'A' : strcat(dec, "1010"); break;
            case 'B' : strcat(dec, "1011"); break;
            case 'C' : strcat(dec, "1100"); break;
            case 'D' : strcat(dec, "1101"); break;
            case 'E' : strcat(dec, "1110"); break;
            case 'F' : strcat(dec, "1111"); break;
            case 'a' : strcat(dec, "1010"); break;
            case 'b' : strcat(dec, "1011"); break;
            case 'c' : strcat(dec, "1100"); break;
            case 'd' : strcat(dec, "1101"); break;
            case 'e' : strcat(dec, "1110"); break;
            case 'f' : strcat(dec, "1111"); break;
            default  : printf("OOPS wrong string");exit(1);
        }
    }
}


long strtoint(char final[]) {
    long num = 0;
    for (int i = 0; i < 16; i++) {
        if (final[i] == '1') {
            num += (1L << (15 - i)); 
        }
    }
    return num;
}


int main(int argc, char * argv[]){
    if(argc<2){
        printf("Give Input");
        return -1;
    }
    char hex[5];
    hex[0]='\0';
    strncpy(hex, argv[1], sizeof(hex) - 1);
    char dec[20];
    hexToDec(hex, dec);
    int size = strlen(dec);
    char final[17];
    if(strlen(dec)!=16){
        for(int i=0; i<17-strlen(dec); i++){
            final[i]='0';
        }
    }
    int j=0;
    for(int i=16-strlen(dec); i<17; i++){
        final[i]=dec[j];
        j++;
    }
    final[16]='\0';
    // printf("Final : %s\n", final);
   
    struct Bounds code; 
    code.base = 32768;
    code.size = 34816;

    struct Bounds stack; 
    stack.base = 28672;
    stack.size = 26624;

    struct Bounds heap; 
    heap.base = 34816;
    heap.size = 37888;



    long virAdd = strtoint(final);
    // printf("Final : %ld\n", virAdd );
    int SEG_MASK = 49152;
    int SEG_SHIFT = 14;
    int OFFSET_MASK = 16383;
    int segment = (virAdd & SEG_MASK) >> SEG_SHIFT;
    int offset = virAdd & OFFSET_MASK;
    // printf("Segment : %d\n", segment);
    // printf("Offset : %d\n", offset);
    switch (segment){
        case 0: //code 
            if(code.base+offset > code.size){
                printf("Segmentation Fault\n");
            }
            else{
                decimalToHex(code.base+offset);

            }
            break;
        // case 1: //heap
        case 1:
        
            if(heap.base+offset > heap.size){
                printf("Segmentation Fault\n");
            }
            else{
                decimalToHex(heap.base+offset);
            }
            break;
        case 2:
        case 3:
            if(stack.base-offset < stack.size){
                printf("Segmentation Fault\n");
            }
            else{
                decimalToHex(stack.base-offset);

            }
            break;
    }
    
    return 0;
}