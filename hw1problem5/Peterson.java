package Peterson;


public class Peterson  {
	volatile boolean wantCS[]={false,false};
	volatile int turn=1;
	public void requestCS(int i){
		int j=1-i;
		wantCS[i]=true;
		turn = j;
		while(wantCS[j]&&(turn==j));
	}
	public void releaseCS(int i){
		wantCS[i]=false;
	}
}
