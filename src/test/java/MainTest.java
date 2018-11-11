@SuppressWarnings({"rawtypes", "unused"})
public class MainTest {
	private final static int rows = 10;
	
    public static void main(String[] args) {
		MatrixSet randomMatrix = RandomGenerator.generateMatrix(rows, rows);
		MatrixSet randomVector = RandomGenerator.generateMatrix(rows, 1);

		System.out.println("Float test " + rows);
		gaussianTest(randomMatrix.getFloatMatrix(), randomVector.getFloatMatrix());
		System.out.println("\nDouble test " + rows);
    	gaussianTest(randomMatrix.getDoubleMatrix(), randomVector.getDoubleMatrix());
		System.out.println("\nFraction test " + rows);
    	gaussianTest(randomMatrix.getFractionMatrix(),randomVector.getFractionMatrix());
    	
    	//gaussianTest(randomMatrix.getFloatMatrix(), randomVector.getFloatMatrix());
    	
    	//blêdy dla generyków

    	
    }
    
    private static void matrixGenerationTest(int rows, int columns) {
    	MatrixSet set = RandomGenerator.generateMatrix(rows, columns);
    	
    	System.out.println("Float matrix:\n" + set.getFloatMatrix() + "\n\nDouble matrix:\n" + set.getDoubleMatrix() + "\n\nFraction matrix:\n" + set.getFractionMatrix());
    }

	private static <T extends Number> void gaussianTest(MyMatrix<T> randomMatrix, MyMatrix<T> randomVector) {
		long millisActualTime;
		long executionTime;
		MyMatrix<T> gaussMatrix;
		MyMatrix<T> timesMatrix;
		MyMatrix<T> error;
		
		System.out.println("Wynik dokladny");
		System.out.println(randomVector.transpose());
		
		MyMatrix<T> testMatrix = new MyMatrix<T>(randomMatrix);	//test macierz A 
		MyMatrix<T> testVector = new MyMatrix<T>(randomVector); // test wektor X 

		int rows = testMatrix.getRows();
		int columns = testMatrix.getColumns();

		System.out.println("------------------------------------------------");
		System.out.println("Podstawowy");
		millisActualTime = System.currentTimeMillis();
		//System.out.print(testMatrix.gaussG(testVector).transpose());
		gaussMatrix = testMatrix.gaussG(testVector);	//A1 = rozwiazanie A i X 
		
		executionTime = System.currentTimeMillis() - millisActualTime;

		timesMatrix = testMatrix.times(gaussMatrix);	// A * A1
		System.out.print(timesMatrix.transpose());
		
		//--------------------------------------------------------------------------------b³¹d
		error = randomVector.minus(timesMatrix);	
		error.absVector();
		System.out.println("Blad");
		System.out.println(error.transpose()); 
		
		System.out.println("Czas wykonania: " + executionTime);

		
		testMatrix = new MyMatrix<T>(randomMatrix);
		testVector = new MyMatrix<T>(randomVector);
		
		System.out.println("\n------------------------------------------------");
		System.out.println("Z czesciowym wyborem");
		millisActualTime = System.currentTimeMillis();
		gaussMatrix = testMatrix.gaussPG(testVector);
		executionTime = System.currentTimeMillis() - millisActualTime;

		timesMatrix = testMatrix.times(gaussMatrix);
		System.out.print(timesMatrix.transpose());
		error = randomVector.minus(timesMatrix);	
		error.absVector();
		System.out.println("Blad");
		System.out.println(error.transpose()); 
		System.out.println("Czas wykonania: " + executionTime);

		testMatrix = new MyMatrix<T>(randomMatrix);
		testVector = new MyMatrix<T>(randomVector);
		

		System.out.println("\n------------------------------------------------");
		System.out.println("Z pelnym wyborem");
		millisActualTime = System.currentTimeMillis();
		gaussMatrix = testMatrix.gaussFG(testVector);
		executionTime = System.currentTimeMillis() - millisActualTime;

		timesMatrix = testMatrix.times(gaussMatrix);
		System.out.print(timesMatrix.transpose());
		error = randomVector.minus(timesMatrix);	
		error.absVector();
		System.out.println("Blad");
		System.out.println(error.transpose()); 
		System.out.println("Czas wykonania: " + executionTime);

	}
    
}
