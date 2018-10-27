@SuppressWarnings("unchecked")

interface arithmetics<T extends Number> {
    T zero(); // Adding zero items
    T add(T lhs, T rhs); // Adding two items
}

public class MyMatrix<T extends Number> {
	private int rows;
	private int columns;
	private T matrix[][];
	
	public MyMatrix(int rows, int columns) {
		super();
		this.rows = rows;
		this.columns = columns;
		matrix = (T[][])new Object[rows][columns];  
	}
	
	public MyMatrix(T[][] matrix) {
		rows = matrix.length;
        columns = matrix[0].length;
        this.matrix = (T[][])new Object[rows][columns];  ;
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < columns; j++)
                    this.matrix[i][j] = matrix[i][j];
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
	
	//transpozycja
	public MyMatrix transpose() {  
	        MyMatrix A = new MyMatrix(rows, columns);
	        for (int i = 0; i < rows; i++)
	            for (int j = 0; j < columns; j++)
	                A.matrix[j][i] = this.matrix[i][j];
	        return A;
	}
	
	//zamiana wierszy
	public void swapRows(int i, int j) {
		T[] temp = matrix[i];
        matrix[i] = matrix[j];
        matrix[j] = temp;
	}
	
	//dodawanie macierzy
	public MyMatrix<T> plus(MyMatrix<T> B, arithmetics<T> arithmetics) {
		MyMatrix<T> A = this;
        if (B.rows != A.rows || B.columns != A.columns) throw new RuntimeException("Niepoprawne wymiary macierzy");
        MyMatrix<T> W = new MyMatrix<T>(rows, columns);		 //macierz wynikowa
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < columns; j++)   
                arithmetics.add(A.matrix[i][j], B.matrix[i][j]);  //nie wiem czy do tego sie nie przyczepi, moze trzeba wykombinowac cos innego
        return W;
	}
	
}
