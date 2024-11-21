#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <time.h>

int main(int argc, char *argv[]){
    time_t this;
    struct tm *this_info;
    char str[100];
    time(&this);
    this_info = localtime(&this);
    if(argc!=2){
        strftime(str, 100, "%a %b %d %H:%M:%S %Y", this_info);
        printf("%s\n", str);
    }
    else{
        char *arr[] = {"date", NULL, NULL };
        if(strcmp(argv[1], "c")==0){
            strftime(str, 100, "%d %b %a %H:%M:%S %Y", this_info);
            printf("%s\n", str);
        }
        else if(strcmp(argv[1], "d")==0){
            strftime(str, 100, "%x", this_info);
            printf("%s\n", str);
        }
        else if(strcmp(argv[1], "f")==0){
            strftime(str, 100, "%F", this_info);
            printf("%s\n", str);
        }
        else {
            strftime(str, 100, "%a %b %d %H:%M:%S %Y", this_info);
            printf("%s\n", str);
        }
    }
    printf("\n\n");
    exit(0);
    
}
