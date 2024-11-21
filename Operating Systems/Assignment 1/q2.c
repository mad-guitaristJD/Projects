#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <stdbool.h>

void printarr(int arr[]){
    for(int i=0; i<15; i++){
        printf("%d ", arr[i]);
    }
    printf("\n");
}

void binSearch(int arr[], int l, int r, int target) {
    if (l > r) {
        printf("-1\n");
        return;
    }
    int mid = l + (r - l) / 2;
    if (arr[mid] == target) {
        printf("Target %d found at index %d\n", target, mid);
        exit(0);
    }
    int rc = fork();
    if (rc < 0) {
        fprintf(stderr, "fork failed");
        exit(1);
    }
    if (rc == 0) {
        if (target < arr[mid]) {
            binSearch(arr, l, mid - 1, target);
        } else {
            binSearch(arr, mid + 1, r, target);
        }
        exit(0);
    } else {
        wait(NULL);
    }
}


int main() {
    int arr[16] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16};
    printarr(arr);
    int target;
    printf("Enter a target value: ");
    scanf("%d", &target);
    binSearch(arr, 0, 15, target);       
}


