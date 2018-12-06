package ug.protocols.equation;
import ug.protocols.matrix.MyMatrix;

public class EqTest {
	MyMatrix m;
	int size = 0;
	int n;
	
	public EqTest(int total){
		this.n= total;
		for(int i = 1; i <= total+1; i++)
			size += i;
		m = new MyMatrix(size, size);
	}
	
	public void buildMatrix(){
		int yes = 0, no = 0;
		for(int i = 0; i < size; i++) {
			yes = 0;
			no = 0;
			for(int j = 0; j < size; j++) {
				System.out.println("P" + yes + no);
				PairsCount.countPairs(n, yes, no);
				if(yes != n) {
					if(yes + no == n) {
						yes++;
						no = 0;
					} else no++;
				}
			}
		}
	}
}
