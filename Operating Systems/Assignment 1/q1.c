#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <time.h>

int average(int arr[]){
    int sum=0;
    for(int i=0; i<4; i++){
        sum+=arr[i];
    }
    return sum/4;
}

int main() {
        for (int i = 1; i <= 7; ++i) {
            int rc_child = fork();
            if (rc_child < 0) {
                fprintf(stderr,"fork filed");
                exit(1);
            }
            if (rc_child == 0) {
                printf("this is the %d process pid : %d\n",i , (int) getpid()); 
                int arr[4];
                for(int i=0; i<4; i++){ 
                    int r = rand() % 100;
                    srand(getpid());
                    arr[i]=r;
                }
                printf("Mean of the numbers %d\n\n", average(arr));
                exit(1);
            }
        }
        while(wait(NULL)>0){
        }

        wait(NULL);
        printf("this is the parent process terminating at last %d \n", (int)getpid());

    return 0;
}
