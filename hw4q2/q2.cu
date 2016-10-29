#include <stdio.h>
#include <stdlib.h>
#include <cuda_runtime.h>

__global__ void a_array_b(int * d_out, int * d_in, int size)
{
    int myId = threadIdx.x + blockDim.x * blockIdx.x;
    if(myId<size){
    int p = d_in[myId]/100;
    atomicAdd(&(d_out[p]), 1);}
    __syncthreads(); 
}
__global__ void b_array_b(int * b_d_out, int * b_d_in, int size)
{   
    extern __shared__ int sdata[];
    int myId = threadIdx.x + blockDim.x * blockIdx.x;
    int tid = threadIdx.x;
    int bid = blockIdx.x;
    if(myId<size){
    int p = b_d_in[myId]/100;
    atomicAdd(&(sdata[p]), 1);}
    __syncthreads(); 
   for(int i =0;i<10;i++){
	b_d_out[i+bid*10]=sdata[i];}
}
__global__ void b_add_b(int * b_d_out, int * b_d_in, int index)
{   
    int tid = threadIdx.x;
    for(int i=0; i< index;i++){
     b_d_out[tid] += b_d_in[i*10+tid];}
    __syncthreads();  
}
__global__ void array_c(int * d_out, int * d_in, int size)
{   
    int myId = threadIdx.x + blockDim.x * blockIdx.x;
    int tid = threadIdx.x;
    int bid = blockIdx.x;
	int val;
	d_out[myId] = d_in[myId];
	__syncthreads(); 
	for(int d = 1;d<size;d=d*2){ 
        if(myId>=d){
		 val = d_out[myId-d];}	
        __syncthreads();
		if(myId>=d)
		  d_out[myId] += val;
		__syncthreads();
		}
}


int main(void)
{   
    int v[1000000];   
    FILE *infile;
    infile = fopen("inp.txt","r");
    if(infile==NULL){
        printf("error");
        exit(1);
    }
        char c;
	int size = 0;
    while(fscanf(infile,"%d,%c",&v[size],&c)!=EOF)
	size++;
    fclose(infile);
    int h_in[size]; 
    for(int i=0; i<size; i++){
	h_in[i]=v[i];
    }
    const int ARRAY_SIZE = size;
    const int ARRAY_BYTES = ARRAY_SIZE * sizeof(int);	
    const int maxThreadsPerBlock = 500;
    int threads = maxThreadsPerBlock;
    int blocks;
    if(ARRAY_SIZE % maxThreadsPerBlock==0) blocks = ARRAY_SIZE / maxThreadsPerBlock;
    else blocks = ARRAY_SIZE / maxThreadsPerBlock+1;

    // declare GPU memory pointers
    int * d_in, *d_out, *c_out, * b_d_out, * b_d_intermediate;

    // allocate GPU memory
    cudaMalloc((void **) &d_in, ARRAY_BYTES);
    cudaMalloc((void **) &d_out, 10*sizeof(int)); 
    cudaMalloc((void **) &c_out, 10*sizeof(int)); 
    cudaMalloc((void **) &b_d_out, 10*sizeof(int)); 
    cudaMalloc((void **) &b_d_intermediate, ARRAY_BYTES); // overallocated

    // transfer the input array to the GPU
    cudaMemcpy(d_in, h_in, ARRAY_BYTES, cudaMemcpyHostToDevice);
    a_array_b<<<blocks, threads>>>(d_out, d_in,ARRAY_SIZE);
    array_c<<<blocks, threads>>>(c_out,d_out,10);
    b_array_b<<<blocks, threads, 10*sizeof(int)>>>(b_d_intermediate, d_in, ARRAY_SIZE);
    int index = blocks;
    threads = 10;
    blocks=1;
    b_add_b<<<blocks,10>>>(b_d_out, b_d_intermediate, index);


    // copy back the sum from GPU
    int b[10];
    cudaMemcpy(b, d_out, 10*sizeof(int), cudaMemcpyDeviceToHost);
	FILE *fpa = fopen("q2a.txt","w");
	
    for(int i=0; i<10; i++){
    fprintf(fpa,"%d,",b[i]);}
	fclose(fpa);
	
    int cc[10];
    cudaMemcpy(cc, c_out, 10*sizeof(int), cudaMemcpyDeviceToHost);
    FILE *fpc = fopen("q2c.txt","w");
	
    for(int i=0; i<10; i++){
    fprintf(fpc,"%d,",cc[i]);}
	fclose(fpc);

    cudaFree(d_out);
	cudaFree(c_out);
	
     // copy back the sum from GPU
    int bb[10];
    cudaMemcpy(bb, b_d_out, 10*sizeof(int), cudaMemcpyDeviceToHost);
	FILE *fpb = fopen("q2b.txt","w");
    for(int i=0; i<10; i++){
     fprintf(fpb, "%d,",bb[i]);}
	 fclose(fpb);
	 
    cudaFree(d_in);
    cudaFree(b_d_out); 
     cudaFree(b_d_intermediate);
    return 0;
}
