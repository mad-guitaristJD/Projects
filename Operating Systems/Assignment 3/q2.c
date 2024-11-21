#include <stdio.h>
#include <pthread.h>
#include <unistd.h>
#include <semaphore.h>

sem_t network_channels[5];

void *function(void* arg){
    int server_ID = *(int *) arg;
    int left_channel = server_ID;
    int right_channel = (server_ID+1)%5;

    for(int i=0; i<3; i++){
        printf("Server: %d is waiting\n", server_ID+1);

        if(server_ID==4){ // To avoid deadlock of dining philosophers
            sem_wait(&network_channels[right_channel]);
            sem_wait(&network_channels[left_channel]);
        }
        else{
            sem_wait(&network_channels[left_channel]);
            sem_wait(&network_channels[right_channel]);
        }

        // Critical Section
        printf("Server: %d is processing\n", server_ID+1);
        sleep(1);

        // Releasing the channels
        // Increasing the number of the semaphore 
        sem_post(&network_channels[left_channel]);
        sem_post(&network_channels[right_channel]);
        
        sleep(1);

    }
    return NULL;
}


int main(){
    pthread_t server_threads[5];
    int threads_IDs[5] = {0,1,2,3,4};

    // Semaphores for each network
    for(int i=0; i<5; i++){
        sem_init(&network_channels[i], 0, 1);
    }

    // Creating threads for each server
    for(int i=0; i<5; i++){
        pthread_create(&server_threads[i], NULL, function, &threads_IDs[i]);
    }

    // Joining threads
    for(int i=0; i<5; i++){
        pthread_join(server_threads[i], NULL);
    }

    printf("Completed");
    return 0;

}

