#include <stdio.h>
#include <stdlib.h>
#include <omp.h>
#include <math.h>
#include <iostream>
#include <ctime>
#include <random>
#include <time.h>
using namespace std;

double getRand(void) {
    random_device rd;
    thread_local mt19937 generator(rd());
    uniform_real_distribution<double> distribution(0.0,1.0);
    return distribution(generator);
}

double MonteCarloPi(int s) {
	double x, y;
	int count = 0;
	double z;
	double pi;
	int i; 
	int max_thread = omp_get_max_threads();
	omp_set_num_threads(max_thread);

	#pragma omp parallel for shared(count) private(x, y, z,i) 
	for (i = 0; i<s; i++) {
		//cout << omp_get_thread_num() << endl;
		x=(double)getRand();	//get a random x coordinate
		y=(double)getRand();      //gets a random y coordinate
		z = sqrt((x*x) + (y*y));          //Checks to see if number is inside unit circle
		if (z <= 1)
		{
			#pragma omp critical
			count++;            //if it is, consider it a valid random point
		}
	}

	pi = ((double)count / (double)(s))*4.0;

	return pi;
}

int main(void) {
	double t;
	t = MonteCarloPi(10000000);
	cout << t << endl;
	return 0;
}

