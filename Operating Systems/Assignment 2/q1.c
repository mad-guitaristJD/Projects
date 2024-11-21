#include<stdio.h>
#include<stdlib.h>
#include<unistd.h>
#include<sys/wait.h>
void merge(int arr[], int low, int mid, int end){
  int temp[100];
  int i = low; 
  int j = mid + 1;
  int k = low;
  while(i <= mid && j <= end){
    if(arr[i] < arr[j]){
      temp[k] = arr[i];
      i++;
      k++;
    }
    else{
      temp[k] = arr[j];
      j++;
      k++;
    }
  }
  while(i <= mid){
    temp[k] = arr[i];
    i++;
    k++;

  }
  while(j <= end){
    temp[k] = arr[j];
    j++;
    k++;

  }
  for(int i = low; i <= end; i++){
    arr[i] = temp[i];
  }
}

void parallel_msort(int arr[], int low, int high) {
    if(low < high) {
        int mid = (low + high)/2;
        int left_pipe[2],right_pipe[2];

        pipe(left_pipe);
        pipe(right_pipe);

        int left_pro =fork();

        if (left_pro < 0) {
            perror("fork failed");
            exit(1);
        }

        if(left_pro == 0) {
            close(left_pipe[0]);
            parallel_msort(arr, low, mid);
            write(left_pipe[1],arr + low,(mid - low + 1) * sizeof(int));
            close(left_pipe[1]);
            exit(0);
        } else {
            int right_pro =fork();
            if (right_pro<0) {
                perror("fork failed");
                exit(1);
            }

            if (right_pro==0) {
                close(right_pipe[0]);
                parallel_msort(arr, mid + 1, high);
                write(right_pipe[1],arr + mid + 1,(high - mid) * sizeof(int));
                close(right_pipe[1]);
                exit(0);
            } else {
                wait(NULL);
                wait(NULL);
                close(left_pipe[1]);
                read(left_pipe[0],arr + low,(mid - low + 1) * sizeof(int));
                close(left_pipe[0]);
                close(right_pipe[1]);
                read(right_pipe[0],arr + mid + 1,(high - mid) * sizeof(int));
                close(right_pipe[0]);
                merge(arr,low,mid,high);
            }
        }
    }
}

int main() {
    int n = 16;
    int arr[n];
    for(int i=0;i<16;i++){
        printf("Enter elements: ");
        scanf("%d", &arr[i]);
    }
    printf("Unsorted array: ");
    for (int i=0;i<16;i++){
        printf("%d ", arr[i]);
    }
    printf("\n");
    parallel_msort(arr, 0,16 - 1);
    printf("Sorted array: ");
    for(int i=0;i<16;i++){
        printf("%d ", arr[i]);
    }
    return 0;
}
