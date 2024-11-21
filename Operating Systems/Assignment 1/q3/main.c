#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <time.h>
#include <string.h>

int main(){
    char inp[4];
    int month, year;
    char mnth[3];
    char yr[5];
    printf("Available flags u, d, f, c \ninput the flag ");
    fgets(inp, 5, stdin );
    printf("Input Month and Year (MM YYYY)");
    scanf("%d %d", &month, &year);
    
    for(int i=1; i<4; i++){
        int rc = fork();
        if(rc<0){
            fprintf(stderr, "fork failed");
            exit(1);
        }
        else if (rc==0){
            if(i==1){
                // printf("\n\n\n\n");
                // char* arg[2]={"./uptime", NULL};
                // execvp(arg[0], arg);
                exit(0);
            }
            else if(i==2){
                char* arg[4]={"./cal","15", "2005", NULL};
                sprintf(mnth, "%d", month);
                sprintf(yr, "%d", year);
                arg[1] = mnth;
                arg[2] = yr;
                execvp(arg[0], arg);
            }
            else{
                char* arg[3]={"./date"};
                arg[1] = strdup(inp);
                arg[2] = NULL;
                execvp(arg[0], arg);
            }
        }
    }
    while (wait(NULL)>0){
    }
    wait(NULL);
    // printf("parent process with PID: %d is over\n", (int)getpid());

    
}