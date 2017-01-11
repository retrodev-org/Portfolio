#define _CRT_SECURE_NO_DEPRECATE
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>

#define SIZE 10

typedef struct {
	char number[7];
	char origin[5];
	char destination[5];
	time_t time;
} FLIGHT;

//**** number of args in prototypes for populateFlights and flightReport didn't match
//**** function def or invocation.  Fixed them.
void flightReport(FLIGHT flight[], int *pSiz, time_t* time);
void populateFlights(FLIGHT[], int*, time_t* time );
void readBinaryFile(FLIGHT flight[], int *pSize);
void writeBinaryFile(FLIGHT flight[], int *pSize);

main(){
	int effectiveSize = 0;
	char *asctime(const struct tm *timeptr);
	time_t time;
	FLIGHT flightList[SIZE];
	FLIGHT *myflight = calloc(SIZE, sizeof(FLIGHT));

	// read binary file
	readBinaryFile(flightList, &effectiveSize);
	flightReport(flightList, &effectiveSize, &time);

	populateFlights(flightList, &effectiveSize, &time);
	writeBinaryFile(flightList, &effectiveSize);

	// read binary file
	readBinaryFile(flightList, &effectiveSize);
	flightReport(flightList, &effectiveSize, &time);

	system("pause");
}

void populateFlights(FLIGHT flight[], int *pSize, time_t *time){
	strcpy(flight[*pSize].number, "YV2840");
	strcpy(flight[*pSize].origin, "KCLT");
	strcpy(flight[*pSize].destination, "KDAB");
	(*pSize)++;

	strcpy(flight[*pSize].number, "YV2827");
	strcpy(flight[*pSize].origin, "KCLT");
	strcpy(flight[*pSize].destination, "KSRQ");
	(*pSize)++;

	strcpy(flight[*pSize].number, "YV2782");
	strcpy(flight[*pSize].origin, "KCLT");
	strcpy(flight[*pSize].destination, "KSRQ");
	(*pSize)++;
}

void flightReport(FLIGHT flight[], int *pSize, time_t *time){
	int i = 0;

	printf("Flight #   Origin  Dest.   Date\n");

	for (i = 0; i < *pSize; i++){
		printf("%-9s  %-6s  %-6s  ", flight[i].number, flight[i].origin,
			flight[i].destination);
		// date

		// **** Think time is already a pointer here, so don't need &
		printf("%s", asctime(localtime(&time)));
	}

	printf("\n");
}

void writeBinaryFile(FLIGHT flight[], int *pSize){
	FILE *binFile;
	int i, random;

	binFile = fopen("c:\\COP2220\\binFile.bin", "wb");

	if (binFile == NULL){
		printf("Problem writing to file.\n");
		system("pause");
		exit(-1);
	}

	srand(time(NULL));

	fwrite(flight, sizeof(FLIGHT), *pSize, binFile);

	fclose(binFile);
}

void readBinaryFile(FLIGHT flight[], int *pSize){
	FILE *binFile;
	int i;

	binFile = fopen("c:\\COP2220\\binFile.bin", "rb");

	if (binFile == NULL){
		printf("Problem reading from file.\n");
		system("pause");
		exit(-1);
	}

	fread(flight, sizeof(flight[0]), *pSize, binFile);
	fclose(binFile);
}

