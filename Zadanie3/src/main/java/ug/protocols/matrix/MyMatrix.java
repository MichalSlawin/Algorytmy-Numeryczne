package ug.protocols.matrix;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.Arrays;

public class MyMatrix {

	private int rows;
	private int columns;
	private double matrix[][];
	
	//konstruktor do tworzenia macierzy zerowej rozmiaru rows x columns
	public MyMatrix(int rows, int columns) {
		this.rows = rows;
		this.columns = columns;
        this.matrix = new double[rows][columns];

		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < columns; j++)
				this.setCell(0.0 ,i ,j);
		}
    }

	// konstruktor do tworzenia macierzy z tablicy
	public MyMatrix(double[][] tab) {
		this.rows = tab.length;
        this.columns = tab[0].length;
        this.matrix = tab.clone();
	}
	
	// konstruktor do tworzenia kopii obiektu
	public MyMatrix(MyMatrix myMatrix) {
		this.rows = myMatrix.getRows();
		this.columns = myMatrix.getColumns();
		this.matrix = new double[rows][columns];
		this.matrix = myMatrix.getMatrix().clone();
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getColumns() {
		return columns;
	}

	public void setColumns(int columns) {
		this.columns = columns;
	}

	public double[][] getMatrix() {
		return matrix;
	}

	public void setMatrix(double[][] matrix) {
		this.matrix = matrix;
	}
	
	public void setCell(double value, int i, int j) {
		this.matrix[i][j] = value;
	}
	
	public double getCell(int row, int column) {
		return matrix[row][column];
	}
	
	public void fillMatrix(double x) {
		for(int i = 0; i < rows; i++)
			for(int j = 0; j < columns; j++)
				matrix[i][j] = x;
	}

	// transpozycja macierzy
	public MyMatrix transpose() {
		MyMatrix A = new MyMatrix(columns, rows);
		for (int i = 0; i < rows; i++)
			for (int j = 0; j < columns; j++)
				A.matrix[j][i] = this.matrix[i][j];
		return A;
	}
	
	// zamiana wierszy
	public void swapRows(int i, int j) {
		double[] tmp = matrix[i];
        matrix[i] = matrix[j];
        matrix[j] = tmp;
	}
	
	// zamiana kolumn
	public void swapColumns(int i, int j) {
		double tmp;
		for(int k = 0; k < rows; k++) {
			tmp = matrix[k][i];
			matrix[k][i] = matrix[k][j];
			matrix[k][j] = tmp;
		}
	}
	
	// wartosc bezwzgledna wszystkich elementow w wektorze
	public void absVector() {  
		for (int i = 0; i < this.getRows(); i++)
			this.setCell(Math.abs(this.getCell(i, 0)), i, 0);
	}
	
	// liczy sredina ze wszystkich elementow macierzy
	public double vectorAvg() {
		double sum = 0.0;
		for(int i = 0; i < this.rows; i++)
			for(int j = 0; j< this.columns; j++)
				sum = sum + this.getCell(i, j);
		return sum / (this.rows * this.columns);
	}
	
	// norma wektora
	public double vectorNorm() {
		double sum = 0.0;
		for(int i = 0; i < this.rows; i++)
			for(int j = 0; j< this.columns; j++)
				sum = sum + (this.getCell(i, j) * this.getCell(i, j));
		return Math.sqrt(sum);
		
	}
	
	// dodawanie macierzy
	public MyMatrix plus(MyMatrix B) {
		MyMatrix A = this;
		if (B.rows != A.rows || B.columns != A.columns) throw new RuntimeException("Wrong matrix dimensions");
		MyMatrix W = new MyMatrix(rows, columns);		 //macierz wynikowa
		for (int i = 0; i < rows; i++)
			for (int j = 0; j < columns; j++)
				W.setCell(A.matrix[i][j] + B.matrix[i][j], i, j);
		return W;
	}
	
	// odejmowanie macierzy
	public MyMatrix minus(MyMatrix B) {
		MyMatrix A = this;
		if (B.rows != A.rows || B.columns != A.columns) throw new RuntimeException("Wrong matrix dimensions");
		MyMatrix W = new MyMatrix(rows, columns);		 //macierz wynikowa
		for (int i = 0; i < rows; i++)
			for (int j = 0; j < columns; j++)
				W.setCell(A.matrix[i][j] - B.matrix[i][j], i, j);
		return W;
	}

	// mnozenie macierzy
	public MyMatrix times(MyMatrix B){
		MyMatrix A = this;
		if (A.columns != B.rows) throw new RuntimeException("Wrong matrix dimensions");
		MyMatrix W = new MyMatrix(A.rows, B.columns);
		
		for (int i = 0; i < W.rows; i++)
			for (int j = 0; j < W.columns; j++)
				for (int k = 0; k < A.columns; k++)
					W.setCell(W.matrix[i][j] + (A.matrix[i][k] * B.matrix[k][j]), i, j);
		return W;
	}
	
	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		for(int i = 0; i < rows; i++) {
			stringBuilder.append("[");
			for(int j = 0; j < columns; j++) {
				stringBuilder.append(getCell(i, j));
				if(j == columns - 1)
					stringBuilder.append("]");
				else stringBuilder.append(" ");
			}
			stringBuilder.append("\n");
		}
		return stringBuilder.toString();
	}

	// zapis do pliku
	public void toFile(String fileName) {
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(fileName);
			PrintWriter printWriter = new PrintWriter(fileWriter);
			printWriter.print(this.toString());
			printWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// zapisuje wyniki eliminacji do result
	private void gaussSetResult(MyMatrix vector, MyMatrix result, MyMatrix matrix) {
		for (int i = vector.rows - 1; i >= 0; i--) {
			double sum = 0.0;

			for (int j = i + 1; j < vector.rows; j++)
				sum = (sum + (matrix.matrix[i][j] * result.matrix[j][0]));
			
			//if(matrix.matrix[i][i] != 0)
				result.matrix[i][0] = ((vector.matrix[i][0] - sum) / matrix.matrix[i][i]);  // POWODUJE DZIELENIE PRZEZ 0
		}
	}
	
	// budowa macierzy schodkowej
	private void buildSteppedMatrix(MyMatrix matrix, MyMatrix vector, int i) {
		double param = 0;
		for (int j = i + 1; j < vector.rows; j++) {
			//if(matrix.matrix[i][i] != 0)
				param = (matrix.matrix[j][i] / matrix.matrix[i][i]);  // POWODUJE DZIELENIE PRZEZ 0 - po odkomentowaniu if'ow w wynikach 0.0
			vector.matrix[j][0] = (vector.matrix[j][0] - (param * vector.matrix[i][0]));

			for (int k = i; k < vector.rows; k++)
				matrix.matrix[j][k] = (matrix.matrix[j][k] - (param * matrix.matrix[i][k]));
		}
	}
	
	// eliminacja gaussa bez wyboru elementu podstawowego
	public MyMatrix gaussG(MyMatrix vector) {
		MyMatrix matrix = new MyMatrix(this);
        MyMatrix result = new MyMatrix(vector.rows, 1);

        for (int i = 0; i < vector.rows; i++)
        	buildSteppedMatrix(matrix, vector, i);
        
        gaussSetResult(vector, result, matrix);
        
        return result;
    }
	
	// eliminacja gaussa z czesciowym wyborem elementu podstawowego
	public MyMatrix gaussPG(MyMatrix vector) {
        MyMatrix matrix = new MyMatrix(this);
        MyMatrix result = new MyMatrix(vector.rows, 1);

        for (int i = 0; i < vector.rows; i++) {
        	int max = i;
        	for (int j = i + 1; j < vector.rows; j++)
        		if (Math.abs(matrix.matrix[j][i]) == Math.abs(matrix.matrix[max][i]))
        			max = j;
        	
        	matrix.swapRows(i, max);
        	vector.swapRows(i, max);

        	buildSteppedMatrix(matrix, vector, i);
        }

        gaussSetResult(vector, result, matrix);

        return result;
    }

	// eliminacja gaussa z pelnym wyborem elemenetu podstawowego
    public MyMatrix gaussFG( MyMatrix vector) {
        MyMatrix matrix = new MyMatrix(this);
        MyMatrix result = new MyMatrix(vector.rows, 1);
        MyMatrix originalResult = new MyMatrix(vector.rows, 1);

        int[] originalPosition;
        originalPosition= new int[vector.rows];

        for (int j = 0; j < vector.rows; j++)
            originalPosition[j]=j;

        
        for (int i = 0; i < vector.rows; i++) {
        	int maxRow = i;
        	int maxColumn = i;

        	for (int j = i; j < matrix.rows; j++) {
        		for (int k = i; k < matrix.columns; k++) {
        			if (Math.abs(matrix.matrix[j][k]) == Math.abs(matrix.matrix[maxRow][maxColumn])) {
        				maxRow = j;
        				maxColumn = k;
        			}
        		}
        	}

        	int tmp = originalPosition[i];
        	originalPosition[i] = originalPosition[maxColumn];
        	originalPosition[maxColumn] = tmp;
        	
        	matrix.swapRows(i,  maxRow);
        	matrix.swapColumns(i, maxColumn);
        	vector.swapRows(i, maxRow);

        	buildSteppedMatrix(matrix, vector, i);
        }

        gaussSetResult(vector, result, matrix);

        for (int j = 0; j < vector.rows; j++)
        	originalResult.matrix[originalPosition[j]][0] = result.matrix[j][0];

        return originalResult;
    }
    
    // metoda Gaussa-Seidela
    public MyMatrix gaussSeidel(MyMatrix vector, int iterationsNo) {
    	int n = this.rows;
    	MyMatrix matrix = new MyMatrix(this);
    	MyMatrix result = new MyMatrix(n, 1);
    	
    	double x[] = new double[n];

    	for(int iter = 0; iter < iterationsNo; iter++){
    		for(int i=0; i<n; i++){
    			result.setCell((vector.getCell(i, 0) / matrix.getCell(i, i)), i, 0);
    			for(int j = 0; j < n; j++){
    				if(j == i)
    					continue;
    				result.setCell(result.getCell(i, 0) - ((matrix.getCell(i, j) / matrix.getCell(i, i)) * x[j]), i, 0);
    				x[i] = result.getCell(i, 0);
    			}
    			//System.out.println("x" + (i+1) + " = " + result.getCell(i,  0));
    		}
    	}
    	return result;
    }
}
