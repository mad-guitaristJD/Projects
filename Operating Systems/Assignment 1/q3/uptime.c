#include <stdio.h>
#include <stdlib.h>
#include <sys/sysinfo.h>

int main(){
    struct sysinfo inf;
    if(sysinfo(&inf) != 0){
        fprintf(stderr, "failed to retrieve\n");
        exit(1);
    }
    printf("%ld hours : %ld minutes : %ld seconds\n\n\n", inf.uptime/3600, (inf.uptime%3600)/60, info.uptime%60);
    return 0;
}