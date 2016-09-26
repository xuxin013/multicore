#include<stdio.h>
#include<stdlib.h>
#include<omp.h>
#include<math.h>
#include<iostream>

#define numthreads 4
using namespace std;
double MonteCarloPi(int s) {
	double x, y;
	int count = 0;
	double z;
	double pi;
	int i;
	omp_set_num_threads(numthreads);
#pragma omp parallel for firstprivate(x,y,z,i) shared(count) num_threads(numthreads)
	{
		for (i = 0; i < s; i++) {
			int tid = omp_get_thread_num();
			//cout << "thread " << tid << " did row " << i << endl;

			x = (double)rand() / RAND_MAX;
			y = (double)rand() / RAND_MAX;
			z = sqrt((x*x) + (y*y));
			if (z <= 1) {
#pragma omp critical
				++count;
			}
		}
	}
	pi = ((double)count / s)*4.0;
	return pi;

}
void main(void) {
	double t;
	t = MonteCarloPi(10000000);
	cout << t << endl;
}