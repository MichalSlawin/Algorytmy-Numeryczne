package ug.protocols.matrix;

import java.math.BigInteger;
import java.util.Random;

public class RandomGenerator {
	private static final double RANGE_MIN = -10.0;
	private static final double RANGE_MAX = 10.0;
    private static Random random = new Random();

    // generuje zestaw losowych macierzy typow Float, Double i Fraction
    public static MyMatrix generateMatrix(int rows, int columns) {
    	double[][] doubleTab = new double[rows][columns];
    	double doubleValue;
		
		for(int i = 0; i< rows; i++) {
			for(int j = 0; j < columns; j++) {
				doubleValue = RANGE_MIN + (RANGE_MAX - RANGE_MIN) * random.nextDouble();
				doubleTab[i][j] = doubleValue;
			}
		}
		return new MyMatrix(doubleTab);
    }
}
