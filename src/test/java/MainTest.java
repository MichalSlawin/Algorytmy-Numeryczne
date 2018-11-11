@SuppressWarnings({"rawtypes", "unused"})
public class MainTest {

    public static void main(String[] args) {
    	int rows = 100;

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

	private static <T extends Number> void gaussianTest(MyMatrix randomMatrix, MyMatrix randomVector) {
		long millisActualTime;
		long executionTime;

		MyMatrix<T> floatMatrix = new MyMatrix<T>(randomMatrix);
		MyMatrix<T> floatVector = new MyMatrix<T>(randomVector);

		int rows = floatMatrix.getRows();
		int columns = floatMatrix.getColumns();

		System.out.println("Podstawowy");
		millisActualTime = System.currentTimeMillis();
		System.out.print(floatMatrix.gaussG(floatVector).transpose());
		executionTime = System.currentTimeMillis() - millisActualTime;
		System.out.println("Czas wykonania: " + executionTime);

		floatMatrix = new MyMatrix(randomMatrix);
		floatVector = new MyMatrix(randomVector);

		System.out.println("Z czesciowym wyborem");
		millisActualTime = System.currentTimeMillis();
		System.out.print(floatMatrix.gaussPG(floatVector).transpose());
		executionTime = System.currentTimeMillis() - millisActualTime;
		System.out.println("Czas wykonania: " + executionTime);

		floatMatrix = new MyMatrix<T>(randomMatrix);
		floatVector = new MyMatrix<T>(randomVector);

		System.out.println("Z pelnym wyborem");
		millisActualTime = System.currentTimeMillis();
		System.out.print(floatMatrix.gaussFG(floatVector).transpose());
		executionTime = System.currentTimeMillis() - millisActualTime;
		System.out.println("Czas wykonania: " + executionTime);

	}
    
}
