#include <stdio.h>
#include <stdlib.h>
#include <omp.h>
#include <math.h>
#include<iostream>

#define numthreads 4
using namespace std;
double MonteCarloPi(int s) {
	double x, y;
	int count = 0;
	double z;
	double pi;
	int i;
	//omp_set_num_threads(numthreads);
#pragma omp parallel firstprivate(x, y, z, i) shared(count) num_threads(numthreads)
	{
		for (i = 0; i<s; ++i)              //main loop
		{
			x = (double)rand() / RAND_MAX;      //gets a random x coordinate
			y = (double)rand() / RAND_MAX;      //gets a random y coordinate
			z = sqrt((x*x) + (y*y));          //Checks to see if number is inside unit circle
			if (z <= 1)
			{
				++count;            //if it is, consider it a valid random point
			}
		}
		//print the value of each thread/rank
	}
	pi = ((double)count / (double)(s*numthreads))*4.0;

	return pi;
}

int main(void) {
	double t;
	t = MonteCarloPi(10000000);
	cout << t << endl;
	return 1;
}

