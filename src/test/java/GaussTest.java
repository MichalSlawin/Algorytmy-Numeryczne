import java.math.BigInteger;
import java.util.Random;
import java.lang.reflect.Array;

// Do czego porównać błędy: wylosowac wektor X, przemnozyc przez macierz, porownac otrzymany wynik
// do wynikow z metody eliminacji gaussa

@SuppressWarnings("unchecked")
public class GaussTest {
    private static final Double minRandom = -65536.0;
    private static final Double maxRandom = 65535.0;
    private static final Double dividerRandom = 65536.0;
	private static final BigInteger minBigInt = BigInteger.valueOf(-65536);
	private static final BigInteger maxBigInt = BigInteger.valueOf(65535);
    private static Random random = new Random();

    @SuppressWarnings("unchecked")
	public static MatrixSet generateMatrix(int rows, int columns) {
    	//random.setSeed(123456789);
    	Fraction[][] fractionTab = new Fraction[rows][columns];
    	Double[][] doubleTab = new Double[rows][columns];
    	Float[][] floatTab = new Float[rows][columns];
    	Fraction fractionValue;
    	Double doubleValue;
    	Float floatValue;
		
		for(int i = 0; i< rows; i++) {
			for(int j = 0; j < columns; j++) {
				BigInteger diff = maxBigInt.subtract(minBigInt);
				BigInteger randomBigInteger = new BigInteger(maxBigInt.bitLength(), random); // <0;65535>
				BigInteger nominator = randomBigInteger.multiply(BigInteger.valueOf(2)); // <0;131070>
				if(nominator.compareTo(maxBigInt) > 0) // nominator > 65535
					nominator = nominator.subtract(maxBigInt); // -65535
				else if(nominator.compareTo(maxBigInt) <= 0) // nominator <= 65535
					nominator = nominator.add(minBigInt); // -65536
				fractionValue = new Fraction(nominator, BigInteger.valueOf(65536));
				doubleValue = fractionValue.getNominator().doubleValue() / fractionValue.getDenominator().doubleValue();
				floatValue = fractionValue.getNominator().floatValue() / fractionValue.getDenominator().floatValue();
				fractionTab[i][j] = fractionValue;;
				doubleTab[i][j] = doubleValue;
				floatTab[i][j] = floatValue;
			}
		}
		
		return new MatrixSet(new MyMatrix<Float>(Float.class, floatTab), 
							 new MyMatrix<Double>(Double.class, doubleTab), 
							 new MyMatrix<Fraction>(Fraction.class, fractionTab));
    }
    
    public static <T> MyMatrix<T> gaussWithoutChoice(MyMatrix<T> matrix, MyMatrix<T> vector) {
        int n = vector.getRows();
        MyMatrix<T> resultVector = new MyMatrix<T>(vector.getC(), vector.getRows(), vector.getColumns());
        MyMatrix<T> VectorCheck = new MyMatrix<T>(vector.getC(), vector.getRows(), vector.getColumns());
        MyMatrix<T> MatrixCopy = new MyMatrix<T>(matrix);

            for (int i = 0; i < n; i++) {
                for (int j = i + 1; j < n; j++) {
                    T factor =MyMath.sub(matrix.getMatrix()[j][i], matrix.getMatrix()[i][i]);
                    vector.getMatrix()[j][0] = MyMath.sub(vector.getMatrix()[j][0], MyMath.mul(factor, vector.getMatrix()[i][0]));

                    for (int k = i; k < n; k++) {
                        matrix.getMatrix()[j][k] = MyMath.sub(matrix.getMatrix()[j][k], MyMath.mul(factor, matrix.getMatrix()[i][k]));
                    }
                }
            }

            for (int i = n - 1; i >= 0; i--) {
            		T sum;
            		if(matrix.getCell(0, 0) instanceof Fraction)
            			sum = (T) Fraction.zero();
            		else sum = (T) Float.valueOf(0);
            	
                for (int j = i + 1; j < n; j++) {
                    sum = MyMath.add(sum, MyMath.mul(matrix.getMatrix()[i][j], matrix.getMatrix()[i][j]));
                    
                }
                resultVector.getMatrix()[i][0] = MyMath.div(MyMath.sub(vector.getMatrix()[i][0], sum), matrix.getMatrix()[i][i]);
            }

            for (int i = 0; i < n; i++) {
            	T sum;
        		if(matrix.getCell(0, 0) instanceof Fraction)
        			sum = (T) Fraction.zero();
        		else sum = (T) Float.valueOf(0);
                for (int j = 0; j < n; j++) {
                    sum = MyMath.add(sum, MyMath.mul(MatrixCopy.getMatrix()[i][j], MatrixCopy.getMatrix()[i][j]));

                }
                VectorCheck.getMatrix()[i][0] = (T) (Float) (sum) ;
            }

//            System.out.println("Wynik bez wyboru: ");
//            System.out.println(resultVector);

        return VectorCheck;
    }
    
    
}
