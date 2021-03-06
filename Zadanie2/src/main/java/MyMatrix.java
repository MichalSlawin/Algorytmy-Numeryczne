import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;

@SuppressWarnings("unchecked")
public class MyMatrix<T extends Number> {

	private Class<T> c;
	private int rows;
	private int columns;
	private T matrix[][];
	
	//konstruktor do tworzenia macierzy zerowej rozmiaru rows x columns
	public MyMatrix(Class<T> c, int rows, int columns) {
		this.rows = rows;
		this.columns = columns;
        this.matrix = (T[][]) Array.newInstance(c, rows, columns);
        this.c = c;
        
        T zero;
        if(c == Fraction.class)
			zero = (T) Fraction.valueOf(0);
		else if(c == Float.class)
			zero = (T) Float.valueOf(0);
		else if(c == Double.class)
			zero = (T) Double.valueOf(0);
		else throw new IllegalArgumentException("Wrong type, choose Float, Double or Fraction");

		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < columns; j++)
				this.setCell(zero ,i ,j);
		}
    }

	// konstruktor do tworzenia macierzy z tablicy
	public MyMatrix(Class<T> c, T[][] tab) {
		this.rows = tab.length;
        this.columns = tab[0].length;
        this.matrix = (T[][]) Array.newInstance(c, rows, columns);
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < columns; j++)
            	this.matrix[i][j]=tab[i][j];
        this.c = c;
	}
	
	// konstruktor do tworzenia kopii obiektu
	public MyMatrix(MyMatrix<T> myMatrix) {
		this.c = myMatrix.getC();
		this.rows = myMatrix.getRows();
		this.columns = myMatrix.getColumns();
		this.matrix = (T[][]) Array.newInstance(this.c, this.rows, this.columns);
		for(int i = 0; i < myMatrix.getRows(); i++)
			for(int j = 0; j < myMatrix.getColumns(); j++)
				this.matrix[i][j] = myMatrix.matrix[i][j];
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

	public T[][] getMatrix() {
		return matrix;
	}

	public void setMatrix(T[][] matrix) {
		this.matrix = matrix;
	}
	
	public void setCell(T value, int i, int j) {
		this.matrix[i][j] = value;
	}
	
	public T getCell(int row, int column) {
		return matrix[row][column];
	}

	public Class<T> getC() { 
		return c; 
	}

	// transpozycja macierzy
	public MyMatrix<T> transpose() {  
		MyMatrix<T> A = new MyMatrix<T>(this.c, columns, rows);
		for (int i = 0; i < rows; i++)
			for (int j = 0; j < columns; j++)
				A.matrix[j][i] = this.matrix[i][j];
		return A;
	}
	
	// zamiana wierszy
	public void swapRows(int i, int j) {
		T[] tmp = matrix[i];
        matrix[i] = matrix[j];
        matrix[j] = tmp;
	}
	
	// zamiana kolumn
	public void swapColumns(int i, int j) {
		T tmp;
		for(int k = 0; k < rows; k++) {
			tmp = matrix[k][i];
			matrix[k][i] = matrix[k][j];
			matrix[k][j] = tmp;
		}
	}
	
	// wartosc bezwzgledna wszystkich elementow w wektorze
	public void absVector() {  
		for (int i = 0; i < this.getRows(); i++)
			this.setCell(MyMath.abs(this.getCell(i, 0)), i, 0);
	}
	
	// liczy sredina ze wszystkich elementow macierzy
	public T vectorAvg() {
		T sum = zeroValue();
		for(int i = 0; i < this.rows; i++)
			for(int j = 0; j< this.columns; j++)
				sum = MyMath.add(sum, this.getCell(i, j));
		return (T) MyMath.div(sum, valueOf(this.rows * this.columns));
	}
	
	// norma wektora
	public T vectorNorm() {
		T sum = zeroValue();
		for(int i = 0; i < this.rows; i++)
			for(int j = 0; j< this.columns; j++)
				sum = MyMath.add(sum, MyMath.mul(this.getCell(i, j), this.getCell(i, j)));
		return (T) MyMath.sqrt(sum);
		
	}
	
	// dodawanie macierzy
	public MyMatrix<T> plus(MyMatrix<T> B) {
		MyMatrix<T> A = this;
		if (B.rows != A.rows || B.columns != A.columns) throw new RuntimeException("Wrong matrix dimensions");
		MyMatrix<T> W = new MyMatrix<T>(this.c, rows, columns);		 //macierz wynikowa
		for (int i = 0; i < rows; i++)
			for (int j = 0; j < columns; j++)
				W.setCell(MyMath.add(A.matrix[i][j], B.matrix[i][j]), i, j);
		return W;
	}
	
	// odejmowanie macierzy
	public MyMatrix<T> minus(MyMatrix<T> B) {
		MyMatrix<T> A = this;
		if (B.rows != A.rows || B.columns != A.columns) throw new RuntimeException("Wrong matrix dimensions");
		MyMatrix<T> W = new MyMatrix<T>(this.c, rows, columns);		 //macierz wynikowa
		for (int i = 0; i < rows; i++)
			for (int j = 0; j < columns; j++)
				W.setCell(MyMath.sub(A.matrix[i][j], B.matrix[i][j]), i, j);
		return W;
	}

	// mnozenie macierzy
	public MyMatrix<T> times(MyMatrix<T> B){
		MyMatrix<T> A = this;
		if (A.columns != B.rows) throw new RuntimeException("Wrong matrix dimensions");
		MyMatrix<T> W = new MyMatrix<T>(this.c, A.rows, B.columns);
		
		for (int i = 0; i < W.rows; i++)
			for (int j = 0; j < W.columns; j++)
				for (int k = 0; k < A.columns; k++)
					W.setCell(MyMath.add(W.matrix[i][j], MyMath.mul(A.matrix[i][k], B.matrix[k][j])),i, j);
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
	
	//zwraca wartosc 0 konkretnego typu
	public T zeroValue() {
		T zero = null;
    	if(c == Float.class)
    		zero = (T) Float.valueOf(0);
    	else if(c == Double.class)
    		zero = (T) Double.valueOf(0);
    	else if(c == Fraction.class)
    		zero = (T) Fraction.valueOf(0);
    	return zero;
	}
	
	//konwertuje int na typ uzywany przez obiekt
	public T valueOf(int value) {
		T sum = null;
    	if(c == Float.class)
    		sum = (T) Float.valueOf(value);
    	else if(c == Double.class)
    		sum = (T) Double.valueOf(value);
    	else if(c == Fraction.class)
    		sum = (T) Fraction.valueOf(value);
    	return sum;
	}
	
	// zapisuje wyniki eliminacji do result
	private void gaussSetResult(MyMatrix<T> vector, MyMatrix<T> result, MyMatrix<T> matrix) {
		for (int i = vector.rows - 1; i >= 0; i--) {
			T sum = zeroValue();

			for (int j = i + 1; j < vector.rows; j++)
				sum = MyMath.add(sum, MyMath.mul(matrix.matrix[i][j], result.matrix[j][0]));
			
			result.matrix[i][0] = MyMath.div(MyMath.sub(vector.matrix[i][0], sum), matrix.matrix[i][i]);
		}
	}

	
	private void buildSteppedMatrix(MyMatrix<T> matrix, MyMatrix<T> vector, int i) {
		for (int j = i + 1; j < vector.rows; j++) {
			T param = MyMath.div(matrix.matrix[j][i], matrix.matrix[i][i]);
			vector.matrix[j][0] = MyMath.sub(vector.matrix[j][0], MyMath.mul(param, vector.matrix[i][0]));

			for (int k = i; k < vector.rows; k++)
				matrix.matrix[j][k] = MyMath.sub(matrix.matrix[j][k], MyMath.mul(param, matrix.matrix[i][k]));
		}
	}
	
	// eliminacja gaussa bez wyboru elementu podstawowego
	public MyMatrix<T> gaussG(MyMatrix<T> vector) {
		MyMatrix<T> matrix = new MyMatrix<T>(this);
        MyMatrix<T> result = new MyMatrix<T>(c, vector.rows, 1);

        for (int i = 0; i < vector.rows; i++)
        	buildSteppedMatrix(matrix, vector, i);
        
        gaussSetResult(vector, result, matrix);
        
        return result;
    }
	
	// eliminacja gaussa z czesciowym wyborem elementu podstawowego
	public MyMatrix<T> gaussPG(MyMatrix<T> vector) {
        MyMatrix<T> matrix = new MyMatrix<T>(this);
        MyMatrix<T> result = new MyMatrix<T>(c, vector.rows, 1);

        for (int i = 0; i < vector.rows; i++) {
        	int max = i;
        	for (int j = i + 1; j < vector.rows; j++)
        		if (MyMath.compare(MyMath.abs(matrix.matrix[j][i]), MyMath.abs(matrix.matrix[max][i])) == 1)
        			max = j;
        	
        	matrix.swapRows(i, max);
        	vector.swapRows(i, max);

        	buildSteppedMatrix(matrix, vector, i);
        }

        gaussSetResult(vector, result, matrix);

        return result;
    }

	// eliminacja gaussa z pelnym wyborem elemenetu podstawowego
    public MyMatrix<T> gaussFG( MyMatrix<T> vector) {
        MyMatrix<T> matrix = new MyMatrix<T>(this);
        MyMatrix<T> result = new MyMatrix<T>(c, vector.rows, 1);
        MyMatrix<T> originalResult = new MyMatrix<T>(c, vector.rows, 1);

        int[] originalPosition;
        originalPosition= new int[vector.rows];

        for (int j = 0; j < vector.rows; j++)
            originalPosition[j]=j;

        
        for (int i = 0; i < vector.rows; i++) {
        	int maxRow = i;
        	int maxColumn = i;

        	for (int j = i; j < matrix.rows; j++) {
        		for (int k = i; k < matrix.columns; k++) {
        			if (MyMath.compare(MyMath.abs(matrix.matrix[j][k]), MyMath.abs(matrix.matrix[maxRow][maxColumn])) == 1) {
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
}
