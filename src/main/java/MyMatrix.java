import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;

@SuppressWarnings("unchecked")
public class MyMatrix<T extends Number> {
	
	/*TODO:
	 * Implementacja ulamkow w eliminacjach gaussa - na poczatku tylko do wariantu bez elementu podstawowego i zobaczymy jak to dziala
	 * Refaktoryzacja kodu eliminacji gaussa
	 * Testy nowych funkcji
	 * Obliczenie bledow i czasow, wykresy do sprawozdania i samo sprawozdanie
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

	public Class<T> getC() { 
		return c; 
	}

	//transpozycja
	public MyMatrix<T> transpose() {  
		MyMatrix<T> A = new MyMatrix<T>(this.c, columns, rows);
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
	
	public MyMatrix<T> gaussWithoutChoice(MyMatrix<T> matrix, MyMatrix<T> vector, MyMatrix<T> MatrixCopy) {
        int n = vector.rows;
        MyMatrix<T> resultVector = new MyMatrix<T>(c, n, 1);
        MyMatrix<T> VectorCheck = new MyMatrix<T>(c, n, 1);


        if (c.equals(Float.class)) {
            for (int i = 0; i < n; i++) {
                for (int j = i + 1; j < n; j++) {
                    float factor = matrix.matrix[j][i].floatValue() / matrix.matrix[i][i].floatValue();
                    vector.matrix[j][0] = (T) (Float) (vector.matrix[j][0].floatValue() - factor * vector.matrix[i][0].floatValue());

                    for (int k = i; k < n; k++) {
                        matrix.matrix[j][k] = (T) (Float) (matrix.matrix[j][k].floatValue() - factor * matrix.matrix[i][k].floatValue());
                    }
                }
            }

            for (int i = n - 1; i >= 0; i--) {
                float sum = 0;
                for (int j = i + 1; j < n; j++) {
                    sum += matrix.matrix[i][j].floatValue() * resultVector.matrix[j][0].floatValue();
                }
                resultVector.matrix[i][0] = (T) (Float) ((vector.matrix[i][0].floatValue() - sum) / matrix.matrix[i][i].floatValue());
            }

            for (int i = 0; i < n; i++) {
                float sum = 0;
                for (int j = 0; j < n; j++) {
                    sum += MatrixCopy.matrix[i][j].floatValue() * resultVector.matrix[j][0].floatValue();

                }
                VectorCheck.matrix[i][0] = (T) (Float) (sum) ;
            }

//            System.out.println("Wynik bez wyboru: ");
//            System.out.println(resultVector);
        }

        if (c.equals(Double.class)) {
            for (int i = 0; i < n; i++) {
                for (int j = i + 1; j < n; j++) {
                    Double factor = ((Double) matrix.matrix[j][i]).doubleValue() / ((Double) matrix.matrix[i][i]).doubleValue();
                    vector.matrix[j][0] = (T) (Double) (vector.matrix[j][0].doubleValue() - factor * vector.matrix[i][0].doubleValue());

                    for (int k = i; k < n; k++) {
                        matrix.matrix[j][k] = (T) (Double) (matrix.matrix[j][k].floatValue() - factor * matrix.matrix[i][k].doubleValue());
                    }
                }
            }

            for (int i = n - 1; i >= 0; i--) {
                double sum = 0;
                for (int j = i + 1; j < n; j++) {
                    sum += matrix.matrix[i][j].doubleValue() * resultVector.matrix[j][0].doubleValue();
                }
                resultVector.matrix[i][0] = (T) (Double) ((vector.matrix[i][0].doubleValue() - sum) / matrix.matrix[i][i].doubleValue());
            }


            for (int i = 0; i < n; i++) {
                double sum = 0;
                for (int j = 0; j < n; j++) {
                    sum += MatrixCopy.matrix[i][j].doubleValue() * resultVector.matrix[j][0].doubleValue();

                }
                VectorCheck.matrix[i][0] = (T) (Double) (sum) ;
            }

//            System.out.println("sprawdzenie wektora "+VectorCheck);
        }

        return VectorCheck;
    }
	
	public MyMatrix<T> gaussWithPartialChoice(MyMatrix<T> matrix, MyMatrix<T> vector, MyMatrix<T> MatrixCopy) {
        int n = vector.rows;
        MyMatrix<T> resultVector = new MyMatrix<T>(c, n, 1);
        MyMatrix<T> VectorCheck = new MyMatrix<T>(c, n, 1);

        if (c.equals(Float.class)) {
            for (int i = 0; i < n; i++) {
                int max = i;

                for (int j = i + 1; j < n; j++) {
                    if (Math.abs(matrix.matrix[j][i].floatValue()) > Math.abs(matrix.matrix[max][i].floatValue())) {
                        max = j;
                    }
                }

                for (int j = 0; j < matrix.columns; j++) {
                    T temp = matrix.matrix[i][j];
                    matrix.matrix[i][j] = matrix.matrix[max][j];
                    matrix.matrix[max][j] = temp;
                }

                for (int j = 0; j < vector.columns; j++) {
                    T temp = vector.matrix[i][j];
                    vector.matrix[i][j] = vector.matrix[max][j];
                    vector.matrix[max][j] = temp;
                }

                for (int j = i + 1; j < n; j++) {
                    float factor = matrix.matrix[j][i].floatValue() / matrix.matrix[i][i].floatValue();
                    vector.matrix[j][0] = (T) (Float) (vector.matrix[j][0].floatValue() - factor * vector.matrix[i][0].floatValue());

                    for (int k = i; k < n; k++) {
                        matrix.matrix[j][k] = (T) (Float) (matrix.matrix[j][k].floatValue() - factor * matrix.matrix[i][k].floatValue());
                    }
                }
            }

            for (int i = n - 1; i >= 0; i--) {
                float sum = 0;
                for (int j = i + 1; j < n; j++) {
                    sum += matrix.matrix[i][j].floatValue() * resultVector.matrix[j][0].floatValue();
                }
                resultVector.matrix[i][0] = (T) (Float) ((vector.matrix[i][0].floatValue() - sum) / matrix.matrix[i][i].floatValue());
            }



            for (int i = 0; i < n; i++) {
                float sum = 0;
                for (int j = 0; j < n; j++) {
                    sum += MatrixCopy.matrix[i][j].floatValue() * resultVector.matrix[j][0].floatValue();

                }
                VectorCheck.matrix[i][0] = (T) (Float) (sum) ;
            }

//            System.out.println("Wynik z czesciowym wyborem: ");
//            System.out.println(resultVector);
        }

        if (c.equals(Double.class)) {
            for (int i = 0; i < n; i++) {
                int max = i;

                for (int j = i + 1; j < n; j++) {
                    if (Math.abs(matrix.matrix[j][i].doubleValue()) > Math.abs(matrix.matrix[max][i].doubleValue())) {
                        max = j;
                    }
                }

                for (int j = 0; j < matrix.columns; j++) {
                    T temp = matrix.matrix[i][j];
                    matrix.matrix[i][j] = matrix.matrix[max][j];
                    matrix.matrix[max][j] = temp;
                }

                for (int j = 0; j < vector.columns; j++) {
                    T temp = vector.matrix[i][j];
                    vector.matrix[i][j] = vector.matrix[max][j];
                    vector.matrix[max][j] = temp;
                }

                for (int j = i + 1; j < n; j++) {
                    double factor = matrix.matrix[j][i].doubleValue() / matrix.matrix[i][i].doubleValue();
                    vector.matrix[j][0] = (T) (Double) (vector.matrix[j][0].doubleValue() - factor * vector.matrix[i][0].doubleValue());

                    for (int k = i; k < n; k++) {
                        matrix.matrix[j][k] = (T) (Double) (matrix.matrix[j][k].floatValue() - factor * matrix.matrix[i][k].doubleValue());
                    }
                }
            }

            for (int i = n - 1; i >= 0; i--) {
                double sum = 0;
                for (int j = i + 1; j < n; j++) {
                    sum += matrix.matrix[i][j].doubleValue() * resultVector.matrix[j][0].doubleValue();
                }
                resultVector.matrix[i][0] = (T) (Double) ((vector.matrix[i][0].doubleValue() - sum) / matrix.matrix[i][i].doubleValue());
            }


            for (int i = 0; i < n; i++) {
                double sum = 0;
                for (int j = 0; j < n; j++) {
                    sum += MatrixCopy.matrix[i][j].doubleValue() * resultVector.matrix[j][0].doubleValue();

                }
                VectorCheck.matrix[i][0] = (T) (Double) (sum) ;
            }

//            System.out.println("sprawdzenie wektora "+VectorCheck);

        }

        return VectorCheck;
    }

    public MyMatrix<T> gaussWithFullChoice(MyMatrix<T> matrix, MyMatrix<T> vector, MyMatrix<T> MatrixCopy) {
        int n = vector.rows;
        MyMatrix<T> resultVector = new MyMatrix<T>(c, n, 1);
        MyMatrix<T> trueResultVector = new MyMatrix<T>(c, n, 1);

        MyMatrix<T> VectorCheck = new MyMatrix<T>(c, n, 1);


        int[] truePosition;
        truePosition= new int[n];

        for (int j = 0; j < n; j++) {
            truePosition[j]=j;
        }

        if (c.equals(Float.class)) {
            for (int i = 0; i < n; i++) {
                int maxRow = i;
                int maxColumn = i;

                for (int j = i; j < matrix.rows; j++) {
                    for (int k = i; k < matrix.columns; k++) {
                        if (Math.abs(matrix.matrix[j][k].floatValue()) > Math.abs(matrix.matrix[maxRow][maxColumn].floatValue())) {
                            maxRow = j;
                            maxColumn = k;

                        }
                    }
                }

                    int tempInt = truePosition[i];
                    truePosition[i] = truePosition[maxColumn];

                    truePosition[maxColumn] = tempInt;


                for (int j = 0; j < matrix.rows; j++) {
                    T temp = matrix.matrix[i][j];
                    matrix.matrix[i][j] = matrix.matrix[maxRow][j];
                    matrix.matrix[maxRow][j] = temp;
                }

                for (int j = 0; j < vector.columns; j++) {
                    T temp = vector.matrix[i][j];
                    vector.matrix[i][j] = vector.matrix[maxRow][j];
                    vector.matrix[maxRow][j] = temp;
                }

                for (int j = 0; j < matrix.rows; j++) {
                    T temp = matrix.matrix[j][i];
                    matrix.matrix[j][i] = matrix.matrix[j][maxColumn];
                    matrix.matrix[j][maxColumn] = temp;
                }


                for (int j = i + 1; j < n; j++) {
                    float factor = matrix.matrix[j][i].floatValue() / matrix.matrix[i][i].floatValue();
                    vector.matrix[j][0] = (T) (Float) (vector.matrix[j][0].floatValue() - factor * vector.matrix[i][0].floatValue());

                    for (int k = i; k < n; k++) {
                        matrix.matrix[j][k] = (T) (Float) (matrix.matrix[j][k].floatValue() - factor * matrix.matrix[i][k].floatValue());
                    }
                }
            }

            for (int j = 0; j < n; j++) {
//                System.out.println(truePosition[j]);
            }

            for (int i = n - 1; i >= 0; i--) {
                float sum = 0;
                for (int j = i + 1; j < n; j++) {
                    sum += matrix.matrix[i][j].floatValue() * resultVector.matrix[j][0].floatValue();
                }
                resultVector.matrix[i][0] = (T) (Float) ((vector.matrix[i][0].floatValue() - sum) / matrix.matrix[i][i].floatValue());
            }
            for (int j = 0; j < n; j++) {
                trueResultVector.matrix[truePosition[j]][0]= (T) (Float) (resultVector.matrix[j][0].floatValue());
            }

//            System.out.println("Wynik z pelnym: ");
//            System.out.println(trueResultVector);
//            System.out.println("zmienone wyniki do sprawdzania \n" + trueResultVector);
//
//
//            System.out.println("zmienone wyniki do sprawdzania \n" + trueResultVector);
//            System.out.println("macierz bez zmian"+MatrixCopy);


            for (int i = 0; i < n; i++) {
                float sum = 0;
                for (int j = 0; j < n; j++) {
                    sum += MatrixCopy.matrix[i][j].floatValue() * trueResultVector.matrix[j][0].floatValue();

                }
                VectorCheck.matrix[i][0] = (T) (Float) (sum) ;
            }

//            System.out.println("sprawdzenie wektora "+VectorCheck);
        }

        if (c.equals(Double.class)) {


            for (int i = 0; i < n; i++) {
                int maxRow = i;
                int maxColumn = i;

                for (int j = i; j < matrix.rows; j++) {
                    for (int k = i; k < matrix.columns; k++) {
                        if (Math.abs(matrix.matrix[j][k].doubleValue()) > Math.abs(matrix.matrix[maxRow][maxColumn].doubleValue())) {
                            maxRow = j;
                            maxColumn = k;

                        }
                    }
                }

                int tempInt = truePosition[i];
                truePosition[i] = truePosition[maxColumn];

                truePosition[maxColumn] = tempInt;


                for (int j = 0; j < matrix.rows; j++) {
                    T temp = matrix.matrix[i][j];
                    matrix.matrix[i][j] = matrix.matrix[maxRow][j];
                    matrix.matrix[maxRow][j] = temp;
                }

                for (int j = 0; j < vector.columns; j++) {
                    T temp = vector.matrix[i][j];
                    vector.matrix[i][j] = vector.matrix[maxRow][j];
                    vector.matrix[maxRow][j] = temp;
                }

                for (int j = 0; j < matrix.rows; j++) {
                    T temp = matrix.matrix[j][i];
                    matrix.matrix[j][i] = matrix.matrix[j][maxColumn];
                    matrix.matrix[j][maxColumn] = temp;
                }


                for (int j = i + 1; j < n; j++) {
                    double factor = matrix.matrix[j][i].doubleValue() / matrix.matrix[i][i].doubleValue();
                    vector.matrix[j][0] = (T) (Double) (vector.matrix[j][0].doubleValue() - factor * vector.matrix[i][0].doubleValue());

                    for (int k = i; k < n; k++) {
                        matrix.matrix[j][k] = (T) (Double) (matrix.matrix[j][k].doubleValue() - factor * matrix.matrix[i][k].doubleValue());
                    }
                }
            }

            for (int j = 0; j < n; j++) {
//                System.out.println(truePosition[j]);
            }

            for (int i = n - 1; i >= 0; i--) {
                double sum = 0;
                for (int j = i + 1; j < n; j++) {
                    sum += matrix.matrix[i][j].doubleValue() * resultVector.matrix[j][0].doubleValue();
                }
                resultVector.matrix[i][0] = (T) (Double) ((vector.matrix[i][0].doubleValue() - sum) / matrix.matrix[i][i].doubleValue());
            }
            for (int j = 0; j < n; j++) {
                trueResultVector.matrix[truePosition[j]][0]= (T) (Double) (resultVector.matrix[j][0].doubleValue());
            }

//            System.out.println("zmienone wyniki do sprawdzania \n" + trueResultVector);
//            System.out.println("macierz bez zmian"+MatrixCopy);


            for (int i = 0; i < n; i++) {
                double sum = 0;
                for (int j = 0; j < n; j++) {
                    sum += MatrixCopy.matrix[i][j].doubleValue() * trueResultVector.matrix[j][0].doubleValue();

                }
                VectorCheck.matrix[i][0] = (T) (Double) (sum) ;
            }

//            System.out.println("sprawdzenie wektora "+VectorCheck);

        }

        return VectorCheck;
    }
}
