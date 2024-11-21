#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <semaphore.h>
#include <unistd.h>
#include <stdbool.h>

#define BUFFER_SIZE 10
#define MAX_PRODUCT 10

sem_t full; // number of filled slots
sem_t empty; // number of empty slots
pthread_mutex_t mutex = PTHREAD_MUTEX_INITIALIZER; // lock initalisation

int buffer[BUFFER_SIZE];
int in=0, out=0, count=0;
volatile bool is_running = true;

void *delivery(void *number){
    int num = *(int *) number;
    while(is_running){
        srand(time(NULL)+num); // filling seed using id of truck
        int products = (rand()%MAX_PRODUCT)+1; // generating number of products less than MAX_PRODUCT
        
        sem_wait(&empty);
        pthread_mutex_lock(&mutex);

        if(count<BUFFER_SIZE){
            buffer[in] = products;
            in = (in+1)%BUFFER_SIZE;
            count++;
            printf("Truck: %d Delivered: %d Count:%d\n", num, products, count);
        }
        else{
            printf("-----BUFFER FULL-----\n");
        }

        pthread_mutex_unlock(&mutex);
        sem_post(&full);

        sleep(rand()%2+1); // Making the thread sleep
    }
}

void *storage(void *number){
    int num = *(int *) number;
    while(is_running){
        srand(time(NULL)+num); // filling seed 
        int store_products = (rand()%5)+1; // generating number of buffer items to consume

        sem_wait(&full);
        pthread_mutex_lock(&mutex);
        
        printf("Stored Products: %d ", store_products);
        while(store_products>0 && count>0){
            buffer[out]=0;
            out = (out+1) % BUFFER_SIZE;
            store_products--;
            count--;
        }
        

        printf("Manager: %d Count: %d\n", num, count);
    
        if(store_products>count){
            printf("-----BUFFER EMPTY-----\n");
        }
        pthread_mutex_unlock(&mutex);
        sem_post(&empty);

        sleep(rand()%2+1);
    }
    
}

void * stop(void *arg){
    sleep(5);
    is_running=false;
    return NULL;
}

int main(){
    sem_init(&full, 0, 0);
    sem_init(&empty, 0, BUFFER_SIZE);

    printf("Enter Truck Numbers and Manager Numbers ");
    int truck_numbers, manager_numbers;
    scanf("%d %d", &truck_numbers, &manager_numbers);
    
    pthread_t truck_threads[truck_numbers];
    pthread_t manager_threads[manager_numbers];
    int truck_ids[truck_numbers];
    int manager_ids[manager_numbers];

    pthread_t stop_thread;
    pthread_create(&stop_thread, NULL, stop, NULL);

    for(int i=0; i<truck_numbers; i++){
        truck_ids[i] = i;
        pthread_create(&truck_threads[i], NULL, delivery, (void*)&truck_ids[i]);
    }
    for(int i=0; i<manager_numbers; i++){
        manager_ids[i] = i;
        pthread_create(&manager_threads[i], NULL, storage, (void*)&manager_ids[i]);
    }

    pthread_join(stop_thread, NULL);

    for(int i=0; i<truck_numbers; i++){
        pthread_join(truck_threads[i], NULL);
    }
    for(int i=0; i<manager_numbers; i++){
        pthread_join(manager_threads[i], NULL);
    }

    return 0;
}