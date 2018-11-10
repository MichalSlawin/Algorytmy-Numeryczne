import java.math.BigInteger;

@SuppressWarnings({"rawtypes", "unused"})
public class MainTest {
	private static final Class TYP_INTEGER = Integer.class;

    private static Fraction fraction6_2 = new Fraction(BigInteger.valueOf(6), BigInteger.valueOf(2)); // 6/2
    private static Fraction fraction8_10 = new Fraction(BigInteger.valueOf(8), BigInteger.valueOf(10)); // 8/10
    private static Fraction fraction0_4 = new Fraction(BigInteger.valueOf(0), BigInteger.valueOf(4)); // 0/4
    private static Fraction fraction2_3 = new Fraction(BigInteger.valueOf(2), BigInteger.valueOf(3)); // 2/3
    private static Fraction fraction5_6 = new Fraction(BigInteger.valueOf(5), BigInteger.valueOf(6)); // 5/6
    private static Fraction fraction1_2 = new Fraction(BigInteger.valueOf(1), BigInteger.valueOf(2)); // 1/2
    private static Fraction fraction7_5 = new Fraction(BigInteger.valueOf(7), BigInteger.valueOf(5)); // 7/5
    private static Fraction fraction6_8 = new Fraction(BigInteger.valueOf(6), BigInteger.valueOf(8)); // 6/8
    private static Fraction fraction1_1 = new Fraction(BigInteger.valueOf(1), BigInteger.valueOf(1)); // 1/1
    private static Fraction fraction3_4 = new Fraction(BigInteger.valueOf(3), BigInteger.valueOf(4)); // 3/4  

    public static void main(String[] args) {
    	gaussianDoubleTest(100, 100);
    	//gaussianFractionTest(50, 50);
    }
    
    private static void matrixGenerationTest(int rows, int columns) {
    	MatrixSet set = RandomGenerator.generateMatrix(rows, columns);
    	
    	System.out.println("Float matrix:\n" + set.getFloatMatrix() + "\n\nDouble matrix:\n" + set.getDoubleMatrix() + "\n\nFraction matrix:\n" + set.getFractionMatrix());
    }
    
    private static void gaussianDoubleTest(int rows, int columns) {
    	long millisActualTime = System.currentTimeMillis();
    	MatrixSet randomMatrix = RandomGenerator.generateMatrix(rows, columns);
    	MatrixSet randomVector = RandomGenerator.generateMatrix(rows, 1);
    	
    	
    	MyMatrix<Double> double1 = new MyMatrix<Double>(randomMatrix.getDoubleMatrix());
    	MyMatrix<Double> vector1 = new MyMatrix<Double>(randomVector.getDoubleMatrix());
    	System.out.println(double1.gaussG(vector1).transpose());
    	
    	
    	double1 = new MyMatrix<Double>(randomMatrix.getDoubleMatrix());
    	vector1 = new MyMatrix<Double>(randomVector.getDoubleMatrix());
    	System.out.println(double1.gaussPG(vector1).transpose());
    	
    	
    	double1 = new MyMatrix<Double>(randomMatrix.getDoubleMatrix());
    	vector1 = new MyMatrix<Double>(randomVector.getDoubleMatrix());
    	System.out.println(double1.gaussFG(vector1).transpose());
    	
    	
    	long executionTime = System.currentTimeMillis() - millisActualTime;
    	System.out.println(executionTime);
    }
    
    public static void gaussianFractionTest(int rows, int columns) {
	   long millisActualTime = System.currentTimeMillis();
   		MatrixSet randomMatrix = RandomGenerator.generateMatrix(rows, columns);
   		MatrixSet randomVector = RandomGenerator.generateMatrix(rows, 1);
   	
   		MyMatrix<Fraction> double1= new MyMatrix<Fraction>(randomMatrix.getFractionMatrix());
   		MyMatrix<Fraction> vector1 = new MyMatrix<Fraction>(randomVector.getFractionMatrix());
   	
   	
   		System.out.println(double1.gaussG(vector1).transpose());
   		long executionTime = System.currentTimeMillis() - millisActualTime;
   		System.out.println(executionTime);
   }
    
    
}
