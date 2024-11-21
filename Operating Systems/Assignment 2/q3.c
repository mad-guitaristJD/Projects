#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>

const int ptable_size = 16;
const int tlb_size = 4;
const int page_size = 4096;
const int total_memory = 65536;
const int vpn_mask = 0xF000;
const int offset_mask = 0x0FFF;
const int shift = 12;
const int ptbr = 0x1000;

typedef struct {
    int pfn;
    int vpn;
} tlbentry;

int page_table[16] = {8, 1, 3, 7, 9, 14, 10, 11, 6, 0, 4, 12, 5, 15, 13, 2};
tlbentry tlb[4] = {{-1, -1}, {-1, -1}, {-1, -1}, {-1, -1}};
int tlbsize = 0;

bool tlb_lookup(int vpn, int *pfn){
    for (int i = 0; i < tlbsize; i++) {
        if (tlb[i].vpn == vpn) {
            *pfn = tlb[i].pfn;
            return true;
        }
    }
    return false;
}

void tlb_insert(int vpn, int pfn){
    if (tlbsize < tlb_size) {
        tlb[tlbsize].vpn = vpn;
        tlb[tlbsize].pfn = pfn;
        tlbsize++;
    } else {
        for (int i = 0; i < tlb_size - 1; i++) {
            tlb[i] = tlb[i+1];
        }
        tlb[tlb_size-1].vpn = vpn;
        tlb[tlb_size-1].pfn = pfn;
    }
}

int trans_address(int virtualaddress){
    int vpn = (virtualaddress & vpn_mask) >> shift;
    int offset = virtualaddress & offset_mask;
    int pfn;
    printf("Virtual Address: 0x%04X\n", virtualaddress);
    bool success = tlb_lookup(vpn, &pfn);
    if (success) {
        printf("tlb hit\n");
        int physaddr = (pfn << shift) | offset;
        printf("Physical Address: 0x%04X\n", physaddr);
        return physaddr;
    } else {
        printf("tlb miss\n");
        if (vpn >= ptable_size) {
            printf("segmentation fault\n");
            return -1;
        }
        pfn = page_table[vpn];
        tlb_insert(vpn, pfn);
        printf("TLB updated\n");
        return trans_address(virtualaddress);
    }
}

int main(int argc, char *argv[]){
    if (argc < 2) {
        printf("No input given");
        return -1;
    }
    for (int i = 1; i < argc; i++) {
        long address = strtol(argv[i], NULL, 16);
        if (address < 0 || address > 0xFFFF) {
            printf("Invalid address");
            continue;
        }
        printf("\nprocessing address: 0x%04lX\n", address);
        int phy_address = trans_address((int)address);
        if (phy_address != -1) {
            printf("Trans phy address: 0x%04X\n", phy_address);
        }
    }
    return 0;
}