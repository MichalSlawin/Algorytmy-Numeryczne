import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;

@SuppressWarnings("unchecked")
public class MyMatrix<T> {
	
	/*TODO
	 * moze jakis sposob na pominiecie Class<T> w konstruktorach
	 * no i cala eliminacja gaussa, testy
	 * napisać testy dla różnych typów (Integer ,Float, Double, Long, Fraction)
	 */

	private Class<T> c;
	private int rows;
	private int columns;
	private T matrix[][];
	
	public MyMatrix(Class<T> c, int rows, int columns) {
		this.rows = rows;
		this.columns = columns;
        this.matrix = (T[][]) Array.newInstance(c, rows, columns);
        this.c = c;
    }

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

	public Class<T> getC() { return c; }

	//naprawione
	public MyMatrix<T> gaussianElimination(MyMatrix<T> vector) {
		MyMatrix<T> steppedMatrix = new MyMatrix<T>(this);
		MyMatrix<T> vectorCopy = new MyMatrix<T>(vector);
		MyMatrix<T> result = new MyMatrix<T>(vector);
		
		for(int i=0; i<result.getColumns(); i++) {
			if(result.getCell(0, 0) instanceof Fraction)
				result.setCell((T) Fraction.zero(), 0, i);
			else result.setCell((T) Double.valueOf(0), 0, i);
		}

		for (int i = 0; i < this.getRows()-1; i++){
			for (int j = i+1; j <= this.getRows()-1; j++){
				for (int k = 0; k < this.getRows(); k++){
					steppedMatrix.setCell(MyMath.sub(this.getCell(j, k), (MyMath.mul(this.getCell(i, k), 
							(MyMath.div(this.getCell(j, i), this.getCell(i, i)))))), j, k);
				}
				vectorCopy.setCell(MyMath.sub(vector.getCell(0, j),(MyMath.mul(vector.getCell(0, i),
						(MyMath.div(this.getCell(j, i), this.getCell(i, i)))))), 0, j);

				for (int k = 0; k < steppedMatrix.getRows(); k++){
					for (int l = 0; l < steppedMatrix.getRows(); l++){
						this.setCell(steppedMatrix.getCell(k, l), k, l);
					}
					vector.setCell(vectorCopy.getCell(0, k), 0, k);
				}
			}
		}
		
		for(int i = result.getColumns()-1; i >= 0; i--) {
			T sum = null;
			if(result.getCell(0, 0) instanceof Fraction)
				sum = (T) Fraction.zero();
			else sum = (T) Double.valueOf(0);
			
			//System.out.println(sum);
			for(int j = i + 1; j < result.getColumns(); j++) {
				sum = MyMath.add(sum, MyMath.mul(steppedMatrix.getCell(i, j), result.getCell(0, j)));
			}
			result.matrix[0][i] = MyMath.div(MyMath.sub(vector.getCell(0, i), sum), steppedMatrix.getCell(i,  i));
		}
		return result;
	}
	
	public MyMatrix<T> gaussianEliminationOld(MyMatrix<T> vector) {
		MyMatrix<T> steppedMatrix = new MyMatrix<T>(this);
		MyMatrix<T> vectorCopy = new MyMatrix<T>(vector);
		MyMatrix<T> result = new MyMatrix<T>(vector);
		
		for(int i=0; i<result.getColumns(); i++) {
			if(result.getCell(0, 0) instanceof Fraction)
				result.setCell((T) Fraction.zero(), 0, i);
			else result.setCell((T) Double.valueOf(0), 0, i);
		}

		for (int i = 0; i < this.getRows()-1; i++){
			for (int j = i+1; j <= this.getRows()-1; j++){
				for (int k = 0; k < this.getRows(); k++){
					steppedMatrix.setCell(MyMath.sub(this.getCell(j, k), (MyMath.mul(this.getCell(i, k), 
							(MyMath.div(this.getCell(j, i), this.getCell(i, i)))))), j, k);
					
					steppedMatrix.getMatrix()[j][k] = MyMath.sub(this.getMatrix()[j][k], (MyMath.mul(this.getMatrix()[j][k], 
							(MyMath.div(this.getMatrix()[j][i], this.getMatrix()[i][i])))));
					
				}
				vectorCopy.setCell(MyMath.sub(vector.getCell(0, j),(MyMath.mul(vector.getCell(0, i),
						(MyMath.div(this.getCell(j, i), this.getCell(i, i)))))), 0, j);

				for (int k = 0; k < steppedMatrix.getRows(); k++){
					for (int l = 0; l < steppedMatrix.getRows(); l++){
						this.setCell(steppedMatrix.getCell(k, l), k, l);
					}
					vector.setCell(vectorCopy.getCell(0, k), 0, k);
				}
			}
		}
		
		for(int i = result.getColumns()-1; i >= 0; i--) {
			T sum = null;
			if(result.getCell(0, 0) instanceof Fraction)
				sum = (T) Fraction.zero();
			else sum = (T) Double.valueOf(0);
			
			//System.out.println(sum);
			for(int j = i + 1; j < result.getColumns(); j++) {
				sum = MyMath.add(sum, MyMath.mul(steppedMatrix.getCell(i, j), result.getCell(0, j)));
			}
			result.matrix[0][i] = MyMath.div(MyMath.sub(vector.getCell(0, i), sum), steppedMatrix.getCell(i,  i));
		}
		return result;
	}
	
	public void partialPivot() {
		int max = 0;
		for(int i = 1; i < this.getRows(); i++) {
			if(MyMath.compare(MyMath.abs(this.getCell(max, 0)), MyMath.abs(this.getCell(i, 0))) < 0) {
				max = i;
			}
		}
		swapRows(0, max);
	}
	
	public void fullPivot() { 
		int maxI = 0, maxJ = 0;
		
		for(int i = 0; i < this.getRows(); i++)
			for(int j = 0; j < this.getColumns(); j++) {
				if(MyMath.compare(MyMath.abs(this.getCell(maxI, maxJ)), MyMath.abs(this.getCell(i, j))) < 0) {
					maxI = i;
					maxJ = j;
				}
			}
		this.swapRows(0, maxI);
		this.swapColumns(0, maxJ);
	}
	
	//bez elementu podstawowego
	public MyMatrix<T> gaussG(MyMatrix<T> vector){
		return gaussianElimination(vector);
	}
	
	//z czesciowym wyborem elementu podstawowego
	public MyMatrix<T> gaussPG(MyMatrix<T> vector){
		partialPivot();
		return gaussianElimination(vector);
	}
	
	//z pelnym wyborem elementu podstawowego
	public MyMatrix<T> gaussFG(MyMatrix<T> vector){
		fullPivot();
		return gaussianElimination(vector);
	}

	//transpozycja
	public MyMatrix<T> transpose() {  
		MyMatrix<T> A = new MyMatrix<T>(this.c, rows, columns);
		for (int i = 0; i < rows; i++)
			for (int j = 0; j < columns; j++)
				A.matrix[j][i] = this.matrix[i][j];
		return A;
	}
	
	//zamiana wierszy
	public void swapRows(int i, int j) {
		T[] tmp = matrix[i];
        matrix[i] = matrix[j];
        matrix[j] = tmp;
	}
	
	//zamiana kolumn
	public void swapColumns(int i, int j) {
		T tmp;
		for(int k = 0; k < rows; k++) {
			tmp = matrix[k][i];
			matrix[k][i] = matrix[k][j];
			matrix[k][j] = tmp;
		}
	}

	// zwrócenie macierzy rozszerzonej o wektor
	// dodaję to z powrotem, bo przydaje mi się do testów
	public MyMatrix<T> createExpandedMatrix(MyMatrix<T> vector) {
		MyMatrix<T> expandedMatrix = new MyMatrix<T>(this.getC(), this.getRows(), this.getColumns()+1);
		for(int i = 0; i < expandedMatrix.getRows(); i++) {
			for(int j = 0; j < expandedMatrix.getColumns(); j++) {
				if(j == expandedMatrix.getColumns()-1) {
					expandedMatrix.setCell(vector.getCell(0, i), i, j);
				}
				else {
					expandedMatrix.setCell(this.getCell(i, j), i, j);
				}
			}
		}
		return expandedMatrix;
	}

	//dodawanie macierzy
	public MyMatrix<T> plus(MyMatrix<T> B) {
		MyMatrix<T> A = this;
		if (B.rows != A.rows || B.columns != A.columns) throw new RuntimeException("Niepoprawne wymiary macierzy");
		MyMatrix<T> W = new MyMatrix<T>(this.c, rows, columns);		 //macierz wynikowa
		for (int i = 0; i < rows; i++)
			for (int j = 0; j < columns; j++)
				W.setCell(MyMath.add(A.matrix[i][j], B.matrix[i][j]), i, j);
		return W;
	}

	//mnozenie macierzy
	public MyMatrix<T> times(MyMatrix<T> B){
		MyMatrix<T> A = this;
		if (A.columns != B.rows) throw new RuntimeException("Niepoprawne wymiary macierzy");
		MyMatrix<T> W = new MyMatrix<T>(this.c, A.rows, B.columns);

		//zerowanie macierzy W
		T zero;
		if(A.getCell(0, 0) instanceof Fraction)
			zero = (T) Fraction.zero();
		else if(A.getCell(0, 0) instanceof Float)
			zero = (T) Float.valueOf(0);
		else if(A.getCell(0, 0) instanceof Double)
			zero = (T) Double.valueOf(0);
		else throw new IllegalArgumentException("Wrong type, choose Float, Double or Fraction");

		for(int i = 0; i < W.rows; i++) {
			for(int j = 0; j < W.columns; j++) {
				W.setCell(zero ,i ,j);
			}
		}
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
}
