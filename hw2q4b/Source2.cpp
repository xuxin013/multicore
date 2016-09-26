#include <stdio.h>
#include <stdlib.h>
#include <omp.h>
#include <math.h>
#include<iostream>
#include<ctime>


using namespace std;
double MonteCarloPi(int s) {
	double x, y;
	int count = 0;
	double z;
	double pi;
	int i;
	int max_thread = omp_get_max_threads();
	omp_set_num_threads(max_thread);
	srand((int)time(NULL) ^ omp_get_thread_num());
	//#pragma omp parallel firstprivate(x, y, z, i) shared(count) num_threads(numthreads)
#pragma omp parallel for private(x, y, z) shared(count) num_threads(max_thread)
	for (i = 0; i<s; ++i)              //main loop
	{
		cout << omp_get_thread_num() << endl;
		x = (double)rand() / RAND_MAX;      //gets a random x coordinate
		y = (double)rand() / RAND_MAX;      //gets a random y coordinate
		z = sqrt((x*x) + (y*y));          //Checks to see if number is inside unit circle
		if (z <= 1)
		{
			++count;            //if it is, consider it a valid random point
		}
	}
	//print the value of each thread/rank

	pi = ((double)count / (double)(s))*4.0;

	return pi;
}

int main(void) {
	double t;
	t = MonteCarloPi(10000000);
	cout << t << endl;
	return 1;
}

