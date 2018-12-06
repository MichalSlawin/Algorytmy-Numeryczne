package ug.protocols.equation;
import java.awt.Point;
import java.util.Arrays;
import java.util.HashMap;

import ug.protocols.matrix.MyMatrix;

public class EqTest {
	MyMatrix m;
	HashMap<Point, Integer> positionMap;
	private int size;
	private int n;
	
	public EqTest(int total) {
		this.n = total;
		size = 0;
		positionMap = new HashMap<Point, Integer>();
		
		for(int i = 1; i <= total+1; i++)
			size += i;
		
		m = new MyMatrix(size, size);
		buildMap();
	}
	
	public void buildMatrix() {
		int yes = 0, no = 0;
		for(int i = 0; i < size; i++) {
			System.out.println("P"+yes+no);
			PairsCount p = new PairsCount(n, yes, no);
			for(int j = 0; j < size; j++) {
				if(yes == 0) {
					if(no == 0 || no == n)
						m.setCell(0, i, positionMap.get(new Point(yes, no)));
					else {
						m.setCell(p.getIntoNchance(), i, positionMap.get(new Point(yes, no+1)));
						m.setCell(p.getIntoUchance(), i, positionMap.get(new Point(yes, no)));
					}
				}else if(no == 0 && yes != n) {
					m.setCell(p.getIntoYchance(), i, positionMap.get(new Point(yes+1, no)));
					m.setCell(p.getIntoUchance(), i, positionMap.get(new Point(yes, no)));
				}else {
					if(yes != n && no != n)
						m.setCell(p.getIntoUchance(), i, positionMap.get(new Point(yes-1, no-1)));
					if(yes>no)
						m.setCell(p.getIntoYchance(), i, positionMap.get(new Point(yes, no)));
					else if(yes<no)
						m.setCell(p.getIntoNchance(), i, positionMap.get(new Point(yes, no)));
					else {
						m.setCell(p.getIntoYchance(), i, positionMap.get(new Point(yes+1, no)));
						m.setCell(p.getIntoNchance(), i, positionMap.get(new Point(yes, no+1)));
						m.setCell(p.getIntoUchance(), i, positionMap.get(new Point(yes, no)));

					}
						
				}
				
				//m.setCell(value, i, positionMap.get(new Point(yes, no)));
			}
			if(yes != n) {
				if(yes + no == n) {
					yes++;
					no = 0;
				} else no++;
			}
		}
		System.out.println(Arrays.asList(positionMap));
		System.out.println(m);
	}
	
	public void buildMap() {
		int yes = 0, no = 0;
		for(int i = 0; i < size; i++) {
			positionMap.put(new Point(yes, no), i);
			if(yes != n) {
				if(yes + no == n) {
					yes++;
					no = 0;
				} else no++;
			}
		}
	}
}
