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
		T[] temp = matrix[i];
        matrix[i] = matrix[j];
        matrix[j] = temp;
	}
	
	//dodawanie macierzy
	public MyMatrix<T> plus(MyMatrix<T> B) {
		MyMatrix<T> A = this;
        if (B.rows != A.rows || B.columns != A.columns) throw new RuntimeException("Niepoprawne wymiary macierzy");
        MyMatrix<T> W = new MyMatrix<T>(this.c, rows, columns);		 //macierz wynikowa
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < columns; j++)   
            	//W.setCell((T) new Double(A.matrix[i][j].doubleValue() + B.matrix[i][j].doubleValue()), i, j);  //tymczasowo, dopoki nie napiszemy dodawania generycznych
            	W.setCell(MyMath.add(A.matrix[i][j], B.matrix[i][j]), i, j);
        
        //MyMath.add(2.0, 3.0);
       return W;
	}
	
	//mnozenie macierzy
	//TODO: zerowanie dla wszystkich typów
	public MyMatrix<T> times(MyMatrix<T> B){
		MyMatrix<T> A = this;
        if (A.rows != B.columns) throw new RuntimeException("Niepoprawne wymiary macierzy");
        MyMatrix<T> W = new MyMatrix<T>(this.c, A.rows, B.columns);
        
        //zerowanie macierzy W
        T zero;
        if(B.getCell(0, 0) instanceof Fraction)
        	zero = (T) Fraction.Zero();
        else
        	zero = (T) Integer.valueOf(0);
        for(int i = 0; i < A.rows; i++) {
        	for(int j = 0; j < B.columns; j++) {
        		W.setCell(zero ,i ,j);
			}
		}

        for (int i = 0; i < W.columns; i++)
            for (int j = 0; j < W.rows; j++)
                for (int k = 0; k < A.rows; k++)
                	W.setCell(MyMath.add(W.matrix[i][j], MyMath.mul(A.matrix[i][k], B.matrix[k][j])),i, j);
        return W;
	}

	public void initializeMatrixZero() {
		//TODO: zerowanie dla wszystkich typów
	}
	
	@Override
	public String toString() {
		String str = new String();
		for(int i=0; i<rows; i++) {
			str+="[";
			for(int j=0; j<columns; j++) {
				str+=getCell(i, j);
				if(j==columns-1)
					str+="]";
				else str+=" ";
			}
			str+="\n";
		}
		return str;
	}
	
}
