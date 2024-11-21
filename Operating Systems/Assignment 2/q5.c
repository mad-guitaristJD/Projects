#include <stdio.h>
#include <stdint.h>
#include <stdbool.h>

struct page{
    uint8_t page_number;
    bool use_bit;
    bool dirty_bit;
};

void display(struct page array[], int num_frames){
    for (int i = 0; i < num_frames; i++) {
        printf("%d ", array[i].page_number);
    }
    printf("\n");
}

void check(struct page array[], int num_frames, int page, int *hit, int *fault){
    //checking hit
    for(int i=0; i<num_frames; i++){
        if(page==array[i].page_number){
            (*hit)++;
            array[i].dirty_bit=1;
            array[i].use_bit=1;
            return;
        }
    }
    (*fault)++;
    int index=0;
    while(index<num_frames){ //checking for both use and dirty to be 0
        index = index%num_frames;
        if(array[index].dirty_bit==0 && array[index].use_bit==0){
            array[index].page_number = page;
            array[index].dirty_bit = 1;
            array[index].use_bit = 1;
            return;
        }
        array[index].use_bit=0;
        index++;
    }
    index=0;
    while(index<num_frames){ //checking for use = 0
        index = index%num_frames;
        if(array[index].use_bit==0){
            array[index].page_number = page;
            array[index].dirty_bit = 1;
            array[index].use_bit = 1;
            return;
        }
        index++;
    }
}

int main(){
    int num_frames;
    printf("Enter num_frames : ");
    scanf("%d", &num_frames);
    struct page array[num_frames];
    for (int i = 0; i < num_frames; i++) {
        array[i].page_number = -1;
        array[i].use_bit=0;
        array[i].dirty_bit=0;
    }

    int size;
    printf("Enter size of page_request : ");
    scanf("%d", &size);
    printf("Enter integers separated by spaces : ");
    int page_request[size];
    int i=0;
    while (i < size && scanf("%d", &page_request[i]) == 1) {
        i++;
    }
    int page_faults=0;
    int page_hits=0;


    for(int i=0; i<size; i++){
        check(array, num_frames, page_request[i], &page_hits, &page_faults);
        
    }
    printf("Page hits : %d ", page_hits);
    printf("Page Faults : %d ", page_faults);
    float hit_rate = (float)page_hits/(page_faults+page_hits);
    printf("Hit Rate : %.2f\n", hit_rate);
    return 0;
}