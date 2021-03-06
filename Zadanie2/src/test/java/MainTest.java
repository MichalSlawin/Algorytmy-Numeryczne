import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

@SuppressWarnings({"unused"})
public class MainTest {
	
    public static void main(String[] args) {
    	
    	int[] rows = {100, 200, 500, 1000, 2500};

    	for(int row : rows) {
			MatrixSet randomMatrix = RandomGenerator.generateMatrix(row, row);
			MatrixSet randomVector = RandomGenerator.generateMatrix(row, 1);

			gaussianTest(randomMatrix.getFloatMatrix(), randomVector.getFloatMatrix(), "out_files/float");

			gaussianTest(randomMatrix.getDoubleMatrix(), randomVector.getDoubleMatrix(), "out_files/double");
			if(row <= 200)
				gaussianTest(randomMatrix.getFractionMatrix(),randomVector.getFractionMatrix(), "out_files/fraction");
		}
    }
    
    private static void matrixGenerationTest(int rows, int columns) {
    	MatrixSet set = RandomGenerator.generateMatrix(rows, columns);
    	System.out.println("Float matrix:\n" + set.getFloatMatrix() + "\n\nDouble matrix:\n" + set.getDoubleMatrix() + "\n\nFraction matrix:\n" + set.getFractionMatrix());
    }

	private static <T extends Number> void gaussianTest(MyMatrix<T> randomMatrix, MyMatrix<T> randomVector, String fileName) {
		long millisActualTime;
		long executionTime;
		MyMatrix<T> gaussMatrix;
		MyMatrix<T> timesMatrix;
		MyMatrix<T> error;

		MyMatrix<T> testMatrix = new MyMatrix<T>(randomMatrix);	//test macierz A 
		MyMatrix<T> testVector = new MyMatrix<T>(randomVector); // test wektor X 

		int rows = testMatrix.getRows();
		int columns = testMatrix.getColumns();
		
		// gauss podstawowy
		millisActualTime = System.currentTimeMillis();
		gaussMatrix = testMatrix.gaussG(testVector);	//A1 = rozwiazanie A i X
		executionTime = System.currentTimeMillis() - millisActualTime;
		timesMatrix = testMatrix.times(gaussMatrix);	// A * A1

		// obliczenie bledu
		error = randomVector.minus(timesMatrix);	
		error.absVector();

		toFile((fileName + "TimeGaussG" + ".csv"), (randomMatrix.getRows() + "," + executionTime));
		toFile((fileName + "ErrorGaussG" + ".csv"), (randomMatrix.getRows() + "," + error.vectorAvg()));
		
		testMatrix = new MyMatrix<T>(randomMatrix);
		testVector = new MyMatrix<T>(randomVector);
		System.out.println(fileName + " 2 " + rows + ": " + executionTime);
		
		// z czesciowym wyborem
		millisActualTime = System.currentTimeMillis();
		gaussMatrix = testMatrix.gaussPG(testVector);
		executionTime = System.currentTimeMillis() - millisActualTime;
		timesMatrix = testMatrix.times(gaussMatrix);

		// obliczenie bledu
		error = randomVector.minus(timesMatrix);	
		error.absVector();

		toFile((fileName + "TimeGaussPG" + ".csv"), (randomMatrix.getRows() + "," + executionTime));
		toFile((fileName + "ErrorGaussPG" + ".csv"), (randomMatrix.getRows() + "," + error.vectorAvg()));

		testMatrix = new MyMatrix<T>(randomMatrix);
		testVector = new MyMatrix<T>(randomVector);
		System.out.println(fileName + " 2 " + rows + ": " + executionTime);
		
		// z pelnym wyborem
		millisActualTime = System.currentTimeMillis();
		gaussMatrix = testMatrix.gaussFG(testVector);
		executionTime = System.currentTimeMillis() - millisActualTime;
		timesMatrix = testMatrix.times(gaussMatrix);

		// obliczenie bledu
		error = randomVector.minus(timesMatrix);	
		error.absVector();

		toFile((fileName + "TimeGaussFG" + ".csv"), (randomMatrix.getRows() + "," + executionTime));
		toFile((fileName + "ErrorGaussFG" + ".csv"), (randomMatrix.getRows() + "," + error.vectorAvg()));
		System.out.println(fileName + " 2 " + rows + ": " + executionTime);
	}

	private static void toFile(String fileName, String content) {
		try(FileWriter fw = new FileWriter(fileName, true);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter out = new PrintWriter(bw))
		{
			out.println(content);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    
}
