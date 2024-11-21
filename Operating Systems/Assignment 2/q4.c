#include<stdio.h>
#include<stdint.h>
#include<stdlib.h>
#include<assert.h>

const int pg_size=4096;
const int pte_count=1024;
const int pd_bits=10;
const int pt_bits=10;
const int off_bits=12;
const int pd_mask=0xFFC00000;
const int pt_mask=0x003FF000;
const int off_mask=0x00000FFF;

typedef struct{
    uint32_t* entries;
    int valid;
}pagetable;

typedef struct{
    pagetable* tables;
}pagedirectory;

pagedirectory pd;
uint8_t* physical_memory;
int hits=0,misses=0;

void init_pd(){
    pd.tables=calloc(pte_count,sizeof(pagetable));
}

void init_physical_memory(){
    physical_memory=calloc(pg_size*pte_count*pte_count,1);
}

uint32_t translate(uint32_t va){
    uint32_t pd_idx=(va&pd_mask)>>(pt_bits+off_bits);
    uint32_t pt_idx=(va&pt_mask)>>off_bits;
    uint32_t offset=va&off_mask;
    if(!pd.tables[pd_idx].valid){
        printf("P_directory miss creating new P_able for PD index %u\n",pd_idx);
        pd.tables[pd_idx].entries=calloc(pte_count,sizeof(uint32_t));
        pd.tables[pd_idx].valid=1;
        misses++;
        return 0;
    }
    if(!pd.tables[pd_idx].entries[pt_idx]){
        printf("P_tabel miss then allocate new frame for PD index %u, PT index %u\n",pd_idx,pt_idx);
        pd.tables[pd_idx].entries[pt_idx]=(pd_idx*pte_count+pt_idx)*pg_size;
        misses++;
        return 0;
    }
    hits++;
    return pd.tables[pd_idx].entries[pt_idx]+offset;
}

uint8_t load(uint32_t va){
    uint32_t pa=translate(va);
    if(!pa){
        printf("Page fault  0x%08X\n",va);
        pa=translate(va);
    }
    return physical_memory[pa];
}

void store(uint32_t va,uint8_t value){
    uint32_t pa=translate(va);
    if(!pa){
        printf("Page faul 0x%08X\n",va);
        pa=translate(va);
    }
    physical_memory[pa]=value;
}

void print_stats(){
    printf("P_directory size: %lu bytes\n",pte_count*sizeof(pagetable));
    printf("P_table size: %lu bytes\n",pte_count*sizeof(uint32_t));
    printf("total memory : %d\n",hits+misses);
    printf("page hit: %d\n",hits);
    printf("page miss: %d\n",misses);
    printf("hit ratio: %.2f%%\n",(float)hits/(hits+misses)*100);
}

int main(int argc,char* argv[]){
    if(argc<2){
        printf("Usage: %s <va1> [va2] ...\n",argv[0]);
        return 1;
    }
    init_pd();
    init_physical_memory();
    for(int i=1;i<argc;i++){
        uint32_t va=(uint32_t)strtoul(argv[i],NULL,16);
        printf("access virtual address 0x%08X:\n",va);
        store(va,i);
        printf("stored value: %d\n",i);
        printf("loaded value: %d\n",load(va));
    }
    print_stats();
    return 0;
}