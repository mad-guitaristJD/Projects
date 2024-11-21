#include <stdio.h>
#include <pthread.h>
#include <unistd.h>

//define two locks Lock_A and Lock_B
pthread_mutex_t Lock_A = PTHREAD_MUTEX_INITIALIZER;
pthread_mutex_t Lock_B = PTHREAD_MUTEX_INITIALIZER;

//function for the lock to acquire
void *function(void *arg){
    int thread_ID = *(int*)arg;
    for(int i=0; i<3; i++){
        //Lock_A
        printf("T%d attempting to acquire Lock_A\n", thread_ID);
        pthread_mutex_lock(&Lock_A);
        printf("T%d acquired Lock_A\n", thread_ID);
        sleep(1);


        //Lock_B
        printf("T%d attempting to acquire Lock_B\n", thread_ID);
        pthread_mutex_lock(&Lock_B);
        printf("T%d acquired Lock_B\n", thread_ID);
        sleep(1);
        
        
        //realease both
        printf("T%d in critical section \n", thread_ID);
        sleep(1);
        printf("COMPLETED T%d\n", thread_ID);
        
        pthread_mutex_unlock(&Lock_B);
        printf("T%d released the Lock_B\n", thread_ID);
        
        pthread_mutex_unlock(&Lock_A);
        printf("T%d released the Lock_A\n", thread_ID);
        // printf("released both the locks\n");
        sleep(1);
    }
    return NULL;

}

int main(){
    pthread_t threads[3];
    int thread_IDs[3] = {1,2,3};

    pthread_create(&threads[0], NULL, function, &thread_IDs[0]);
    pthread_create(&threads[1], NULL, function, &thread_IDs[1]);
    pthread_create(&threads[2], NULL, function, &thread_IDs[2]);

    pthread_join(threads[0], NULL);
    pthread_join(threads[1], NULL);
    pthread_join(threads[2], NULL);

    return 0;
}

