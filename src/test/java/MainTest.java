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

		System.out.println("Wynik dokladny");
		System.out.println(randomVector.transpose());
		
		MyMatrix<T> testMatrix = new MyMatrix<T>(randomMatrix);
		MyMatrix<T> testVector = new MyMatrix<T>(randomVector);

		int rows = testMatrix.getRows();
		int columns = testMatrix.getColumns();

		System.out.println("Podstawowy");
		millisActualTime = System.currentTimeMillis();
		//System.out.print(testMatrix.gaussG(testVector).transpose());
		gaussMatrix = testMatrix.gaussG(testVector);
		executionTime = System.currentTimeMillis() - millisActualTime;

		timesMatrix = testMatrix.times(gaussMatrix);
		System.out.print(timesMatrix.transpose());
		System.out.println("Czas wykonania: " + executionTime);

		testMatrix = new MyMatrix<T>(randomMatrix);
		testVector = new MyMatrix<T>(randomVector);

		System.out.println("Z czesciowym wyborem");
		millisActualTime = System.currentTimeMillis();
		gaussMatrix = testMatrix.gaussPG(testVector);
		executionTime = System.currentTimeMillis() - millisActualTime;

		timesMatrix = testMatrix.times(gaussMatrix);
		System.out.print(timesMatrix.transpose());
		System.out.println("Czas wykonania: " + executionTime);

		testMatrix = new MyMatrix<T>(randomMatrix);
		testVector = new MyMatrix<T>(randomVector);

		System.out.println("Z pelnym wyborem");
		millisActualTime = System.currentTimeMillis();
		gaussMatrix = testMatrix.gaussFG(testVector);
		executionTime = System.currentTimeMillis() - millisActualTime;

		timesMatrix = testMatrix.times(gaussMatrix);
		System.out.print(timesMatrix.transpose());
		System.out.println("Czas wykonania: " + executionTime);

	}
    
}
