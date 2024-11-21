#include <stdio.h>
#include <stdlib.h>

int zeller(int day, int month, int year){
	if(month < 3){
        month += 12;
        year--;
    }
    int h = day + 13 * (month + 1) / 5 + (year%100) + (year%100) / 4 + (year/100) / 4 + 5 * (year/100);
	h = h % 7;
    char* arr[7] = {"Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
	return h;
}

int daysnumber(int month, int year){
    if(month==2 && year%4==0) return 29;
    if(month==2) return 28;
    if(month == 4 || month == 6 || month == 9 || month == 11) return 30;
    return 31;
}

void printcal(int start, int month, int year){
    int number = daysnumber(month, year);
    printf("Sa Su Mo Tu We Th Fr\n");
    for(int i=0; i<start; i++){
            printf("   ");
    }
    for (int day = 1; day <= number; day++) {
        printf("%2d ", day);
        if ((start + day) % 7 == 0) {
            printf("\n");
    
        }
    }
    printf("\n\n");
}

int main(int argc, char* argv[]){
    if(argc!=3){
        printf("ERROR NO PROPER INPUT GIVEN\n\n");
        return 1;    
    }
    int month=atoi(argv[1]);
    int year=atoi(argv[2]);
    if(month>=1 && month<=12){
        int start = zeller(1,month,year); 
        printcal(start, month, year);
    }
    else printf("ERROR NO PROPER INPUT GIVEN\n\n");
	

	return 0;
}