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
    	random.setSeed(123456789);
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
}
