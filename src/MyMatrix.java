import java.lang.reflect.Array;

//testowe, interfejs jeszcze nie dziala - na razie castuje wszystko na double
interface arithmetics<T extends Number> {
    T zero(); // Adding zero items
    T add(T lhs, T rhs);
    T multiply(T lhs, T rhs);
}

@SuppressWarnings("unchecked")
public class MyMatrix<T extends Number> {
	
	/*TODO 
	 * przetestowac poprawnosc mnozenia
	 * moze jakis sposob na pominiecie Class<T> w konstruktorach
	 * no i cala eliminacja gaussa, testy
	 */
	
	
	//cos takiego wymyslilem na dzialania generyczne, jak ktos ma ladniejszy sposob to zamiencie
	//trzeba tu dodac reszte typow dalem te ktore pamietalem tylko
	class Calc{
		T add(T num1, T num2) {
			if(num1 instanceof Integer && num2 instanceof Integer)
				return (T) (Integer) ((Integer) num1 + (Integer) num2);
			else if(num1 instanceof Float && num2 instanceof Float)
				return (T) (Float) ((Float) num1 + (Float) num2);
			else if(num1 instanceof Double && num2 instanceof Double)
				return (T) (Double) ((Double) num1 + (Double) num2);
			else if(num1 instanceof Long && num2 instanceof Long)
				return (T) (Long) ((Long) num1 + (Long) num2);
			else return null;
		}
		
		T sub(T num1, T num2) {
			if(num1 instanceof Integer && num2 instanceof Integer)
				return (T) (Integer) ((Integer) num1 - (Integer) num2);
			else if(num1 instanceof Float && num2 instanceof Float)
				return (T) (Float) ((Float) num1 - (Float) num2);
			else if(num1 instanceof Double && num2 instanceof Double)
				return (T) (Double) ((Double) num1 - (Double) num2);
			else if(num1 instanceof Long && num2 instanceof Long)
				return (T) (Long) ((Long) num1 - (Long) num2);
			else return null;
		}
		
		T mul(T num1, T num2) {
			if(num1 instanceof Integer && num2 instanceof Integer)
				return (T) (Integer) ((Integer) num1 * (Integer) num2);
			else if(num1 instanceof Float && num2 instanceof Float)
				return (T) (Float) ((Float) num1 * (Float) num2);
			else if(num1 instanceof Double && num2 instanceof Double)
				return (T) (Double) ((Double) num1 * (Double) num2);
			else if(num1 instanceof Long && num2 instanceof Long)
				return (T) (Long) ((Long) num1 * (Long) num2);
			else return null;
		}
		
		T div(T num1, T num2) {
			if(num1 instanceof Integer && num2 instanceof Integer)
				return (T) (Integer) ((Integer) num1 / (Integer) num2);
			else if(num1 instanceof Float && num2 instanceof Float)
				return (T) (Float) ((Float) num1 / (Float) num2);
			else if(num1 instanceof Double && num2 instanceof Double)
				return (T) (Double) ((Double) num1 / (Double) num2);
			else if(num1 instanceof Long && num2 instanceof Long)
				return (T) (Long) ((Long) num1 / (Long) num2);
			else return null;
		}
	}
	
	private Calc calc = new Calc();
	private Class<T> c;
	private int rows;
	private int columns;

	private T matrix[][];
	
	public MyMatrix(Class<T> c, int rows, int columns) {
		this.rows = rows;
		this.columns = columns;
        final T[][] matrix = (T[][]) Array.newInstance(c, rows, columns);
        this.matrix = matrix;
        this.c = c;
    }
	
	//ten konstruktor nie testowany, mozliwe ze nie dziala
	public MyMatrix(Class<T> c, T[][] tab) {
		rows = matrix.length;
        columns = matrix[0].length;
		final T[][] matrix = (T[][]) Array.newInstance(c, rows, columns);
        this.matrix = matrix;
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < columns; j++)
            	this.matrix[i][j]=tab[i][j];
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
            	W.setCell(calc.add(A.matrix[i][j], B.matrix[i][j]), i, j);
       return W;
	}
	
	//mnozenie macierzy
	public MyMatrix<T> times(MyMatrix<T> B){
		MyMatrix<T> A = this;
        if (A.rows != B.columns) throw new RuntimeException("Niepoprawne wymiary macierzy");
        MyMatrix<T> W = new MyMatrix<T>(this.c, A.rows, B.columns);
        for (int i = 0; i < W.columns; i++)
            for (int j = 0; j < W.rows; j++)
                for (int k = 0; k < A.rows; k++)
                	W.setCell(calc.add(W.matrix[i][j], calc.mul(A.matrix[i][k], B.matrix[k][j])),i, j);
                	//W.setCell((T) new Double(W.matrix[i][j].doubleValue()+(A.matrix[i][k].doubleValue()*B.matrix[k][j].doubleValue())), i, j); //tymczasowo
        return W;
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

	public static void main(String[] args) {
		
		Class TYP = Integer.class;
		
		MyMatrix A = new MyMatrix<Integer>(TYP, 3, 3);
		MyMatrix B = new MyMatrix<Integer>(TYP, 3, 3);
		
		for(int i=0; i<3; i++)
			for(int j=0; j<3; j++) {
				A.setCell(3, i, j);
				B.setCell(2, i, j);
			}
				
		System.out.println(A.plus(B));
	}
	
}
