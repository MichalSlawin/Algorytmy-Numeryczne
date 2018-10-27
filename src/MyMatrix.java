@SuppressWarnings("unchecked")
public class MyMatrix<T> {
	private int rows;
	private int columns;
	private T matrix[][];
	
	public MyMatrix(int rows, int columns) {
		super();
		this.rows = rows;
		this.columns = columns;
		matrix = (T[][])new Object[rows][columns];  
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
	
}
