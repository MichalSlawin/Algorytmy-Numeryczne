package ug.protocols.matrix;
import java.awt.Point;
import java.util.HashMap;

import ug.protocols.matrix.MyMatrix;

public class Equations {
	MyMatrix matrix;
	MyMatrix vector;
	HashMap<Point, Integer> positionMap;
	private int size;
	private int N;
	
	public Equations(int total) {
		this.N = total;
		size = 0;
		positionMap = new HashMap<Point, Integer>();
		
		for(int i = 1; i <= total+1; i++)
			size += i;
		
		matrix = new MyMatrix(size, size);
		vector = new MyMatrix(size, 1);
		buildMap();
		buildMatrix();
	}
	
	private void buildMatrix() {
        double pairsCount = (N * (N - 1)) / 2;
        int yCount = 0, nCount = 0, uCount;
        for (int i = 0; i < size; i++) {
            uCount = N - yCount - nCount;

            matrix.setCell(((yCount * (yCount - 1) / 2) + (nCount * (nCount - 1) / 2) + (uCount * (uCount - 1) / 2)) / pairsCount, i, i);
            if (yCount != 0 || nCount != 0 && nCount != N) {
                matrix.setCell(matrix.getCell(i, i) - 1, i, i);
            }
            if (yCount == N) {
                matrix.setCell(1, i, i);
                vector.setCell(1, i, 0);
            }
            if (yCount != 0 && nCount != 0) {            // zmiejszenie tak i nie
                matrix.setCell(yCount * nCount / pairsCount, i, positionMap.get(new Point(yCount-1, nCount-1)));
            }

            if (yCount != 0 && N - yCount != nCount) {       //zwiekszenie tak
                matrix.setCell(yCount * uCount / pairsCount, i, positionMap.get(new Point(yCount+1, nCount)));
            }

            if (nCount != 0 && N - nCount != yCount) { // zwiekszanie nie
                matrix.setCell(nCount * uCount / pairsCount, i, positionMap.get(new Point(yCount, nCount+1)));
            }
            if(yCount != N) {
				if(yCount + nCount == N) {
					yCount++;
					nCount = 0;
				} else nCount++;
			}
        }
	}
	
	private void buildMap() {
		int yCount = 0, nCount = 0;
		for(int i = 0; i < size; i++) {
			positionMap.put(new Point(yCount, nCount), i);
			if(yCount != N) {
				if(yCount + nCount == N) {
					yCount++;
					nCount = 0;
				} else nCount++;
			}
		}
	}

	public MyMatrix getMatrix() {
		return matrix;
	}
	
	public MyMatrix getVector() {
		return vector;
	}
	
	public HashMap<Point, Integer> getMap() {
		return positionMap;
	}
}
