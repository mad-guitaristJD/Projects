#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <time.h>
#include <unistd.h>

typedef struct {
    void (*function)(void *arg);
    void *arg;                  
} Task;

typedef struct {
    Task task_queue[100];
    int queue_size;
    int queue_front;
    int queue_rear;
    pthread_t *threads;
    int num_threads;
    pthread_mutex_t mutex;
    pthread_cond_t cond;
    int stop;
} ThreadPool;

typedef struct {
    int row;       // Row index of the element to compute
    int col;       // Column index of the element to compute
    int n;         // Number of columns in Matrix A (or rows in Matrix B)
    int **A;       // Matrix A
    int **B;       // Matrix B
    int **C;       // Resultant Matrix C
} MatrixArgs;

typedef struct{
    double normal;
    double multithreaded;
}part_one_ans;

void *thread_element(void *arg) {
    MatrixArgs *data = (MatrixArgs *)arg;
    int sum = 0;

    for (int k = 0; k < data->n; k++) {
        sum += data->A[data->row][k] * data->B[k][data->col];
    }

    data->C[data->row][data->col] = sum;
    return NULL;
}

void normal(int m, int n, int p, int **A, int **B, int **C ) {
    for (int i = 0; i < m; i++) {
        for (int j = 0; j < p; j++) {
            C[i][j] = 0;
            for (int k = 0; k < n; k++) {
                C[i][j] += A[i][k] * B[k][j];
            }
        }
    }
}

void join_threads(int m, int p, pthread_t threads[]){
    for (int i = 0; i < m * p; i++) {
        pthread_join(threads[i], NULL);
    }
}

void print_matrix(int m, int p, int **C, int is_pool){
    if(is_pool==1)printf("Resultant Matrix C (Thread Pool):\n");
    if(is_pool==0)printf("Resultant Matrix C (Multithreaded):\n");
    for (int i = 0; i < m; i++) {
        for (int j = 0; j < p; j++) {
            printf("%d ", C[i][j]);
        }
        printf("\n");
    }
}

part_one_ans part_one(int m, int n, int p, int **A, int **B, int **C, int **normal_C){
    // m*p threads to calculate each element
    pthread_t threads[m * p];
    clock_t multithread_start = clock(); // starting clock timer

     
    for (int i = 0; i < m; i++) {
        for (int j = 0; j < p; j++) {
           MatrixArgs *args = (MatrixArgs *)malloc(sizeof(MatrixArgs));
            args->n = n;
            args->A = A;
            args->B = B;
            args->C = C;

            args->row = i;
            args->col = j;
            
            pthread_create(&threads[i * p + j], NULL, thread_element, (void *)args);
        }
    }

    // joining threads
    join_threads(m, p, threads);

    clock_t multithread_end = clock(); // end clock timer
    

    // normal matrix multiplication
    clock_t normal_start = clock();
    normal(m, n, p, A, B, normal_C);
    clock_t normal_end = clock();

    print_matrix(m, p, C, 0);
    
    // speedup
    double multithread_time = (double)(multithread_end - multithread_start) / CLOCKS_PER_SEC;
    printf("Multhread time: %.10f secs\n", multithread_time);

    double normal_time = (double)(normal_end - normal_start) / CLOCKS_PER_SEC;
    printf("Time taken normal: %.10f seconds\n", normal_time);

    printf("Speedup: %.5fx\n", normal_time / multithread_time);

    part_one_ans result;
    result.multithreaded = multithread_time;
    result.normal = normal_time;
    return result;
}

void *thread_pool_worker(void *arg) {
    ThreadPool *pool = (ThreadPool *)arg;

    while (1) {
        pthread_mutex_lock(&pool->mutex);

        while (pool->queue_size == 0 && !pool->stop) {
            pthread_cond_wait(&pool->cond, &pool->mutex);
        }

        if (pool->stop && pool->queue_size == 0) {
            pthread_mutex_unlock(&pool->mutex);
            break;
        }

        Task task = pool->task_queue[pool->queue_front];
        pool->queue_front = (pool->queue_front + 1) % 100;
        pool->queue_size--;

        pthread_mutex_unlock(&pool->mutex);

        // Execute the task
        task.function(task.arg);
    }

    pthread_exit(NULL);
}

ThreadPool *thread_pool_init(int num_threads) {
    ThreadPool *pool = (ThreadPool *)malloc(sizeof(ThreadPool));
    pool->num_threads = num_threads;
    pool->queue_size = 0;
    pool->queue_front = 0;
    pool->queue_rear = 0;
    pool->stop = 0;
    int i=0;

    pthread_mutex_init(&pool->mutex, NULL);
    pthread_cond_init(&pool->cond, NULL);

    pool->threads = (pthread_t *)malloc(num_threads * sizeof(pthread_t));
    while(i < num_threads){
        pthread_create(&pool->threads[i], NULL, thread_pool_worker, (void *)pool);
        i++;
    }

    return pool;
}

void thread_pool_destroy(ThreadPool *pool) {
    pthread_mutex_lock(&pool->mutex);
    pool->stop = 1;
    pthread_cond_broadcast(&pool->cond);
    pthread_mutex_unlock(&pool->mutex);

    for (int i = 0; i < pool->num_threads; i++) {
        pthread_join(pool->threads[i], NULL);
    }

    pthread_mutex_destroy(&pool->mutex);
    pthread_cond_destroy(&pool->cond);
}

void thread_pool_add_task(ThreadPool *pool, void (*function)(void *), void *arg) {
    pthread_mutex_lock(&pool->mutex);

    // Add task to the queue
    pool->task_queue[pool->queue_rear].function = function;
    pool->task_queue[pool->queue_rear].arg = arg;
    pool->queue_rear = (pool->queue_rear + 1) % 100;
    pool->queue_size++;

    pthread_cond_signal(&pool->cond);
    pthread_mutex_unlock(&pool->mutex);
}


void compute_element(void *arg) {
    MatrixArgs *data = (MatrixArgs *)arg;
    int sum = 0;
    for (int k = 0; k < data->n; k++) {
        sum += data->A[data->row][k] * data->B[k][data->col];
    }
    data->C[data->row][data->col] = sum;
    free(arg); // Free the allocated argument
}

double thread_pool_part(int m, int n, int p, int **A, int **B, int **C){
    int num_threads = sysconf(_SC_NPROCESSORS_ONLN); // Number of cores
    ThreadPool *pool = thread_pool_init(num_threads);

    clock_t pool_start = clock();

    for (int i = 0; i < m; i++) {
        for (int j = 0; j < p; j++) {
            MatrixArgs *args = (MatrixArgs *)malloc(sizeof(MatrixArgs));
            args->row = i;
            args->col = j;
            args->n = n;
            args->A = A;
            args->B = B;
            args->C = C;
            thread_pool_add_task(pool, compute_element, args);
        }
    }

    clock_t pool_end = clock();
    print_matrix(m,p,C,1);
    double pool_time = (double)(pool_end - pool_start) / CLOCKS_PER_SEC;
    return pool_time;
}

int main() {
    int m, n, p;
    printf("Enter dimensions m n p: ");
    scanf("%d %d %d", &m, &n, &p);

    // making matrices
    int **A = (int **)malloc(m * sizeof(int *));
    int **B = (int **)malloc(n * sizeof(int *));
    int **C = (int **)malloc(m * sizeof(int *));
    int **normal_C = (int **)malloc(m * sizeof(int *));

    int i=0;
    while(i < m) {
        A[i] = (int *)malloc(n * sizeof(int));
        C[i] = (int *)malloc(p * sizeof(int));
        normal_C[i] = (int *)malloc(p * sizeof(int));
        i++;
    }
    i=0;
    while(i < n) {
        B[i] = (int *)malloc(p * sizeof(int));
        i++;
    }

    // A ka input
    printf("Enter elements of Matrix A:\n");
    for (int i = 0; i < m; i++) {
        for (int j = 0; j < n; j++) {
            scanf("%d", &A[i][j]);
        }
    }
    // B ka input
    printf("Enter elements of Matrix B:\n");
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < p; j++) {
            scanf("%d", &B[i][j]);
        }
    }
    printf("\nPART ONE\n");
    part_one_ans result = part_one(m, n, p, A, B, C, normal_C);
    
    printf("\nSECOND PART\n");

    double thread_time = thread_pool_part(m, n, p, A, B, C);
    printf("Thread pool time: %.10f seconds\n", thread_time);

    double multithread_time = result.multithreaded;
    printf("Multhread time: %.10f secs\n", multithread_time);

    double normal_time = result.normal;
    printf("Time taken normal: %.10f seconds\n", normal_time);

    printf("Speedup(normal): %.5fx\n", normal_time / thread_time);   
    printf("Speedup(multithreaded): %.5fx\n", multithread_time / thread_time);   

    return 0;
}
