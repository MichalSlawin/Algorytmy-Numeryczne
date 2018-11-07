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
	public static <T> MyMatrix<T> generateMatrix(Class<T> c, int rows, int columns) {
    	random.setSeed(123456789);
    	T[][] tab = (T[][]) Array.newInstance(c, rows, columns);
    	Fraction fraction;

    	
    	for(int i = 0; i < rows; i++) {
            for(int j = 0; j < columns; j++) {
            	if(c == Float.class)
            		tab[i][j] = (T) MyMath.div((MyMath.add(minRandom.floatValue(), MyMath.mul(random.nextFloat(),
                            MyMath.sub(maxRandom.floatValue(), minRandom.floatValue())))), dividerRandom.floatValue());
            	else if(c == Double.class)
            		tab[i][j] = (T) MyMath.div((MyMath.add(minRandom, MyMath.mul(random.nextDouble(),
                            MyMath.sub(maxRandom, minRandom)))), dividerRandom);
            	else  if(c == Fraction.class) {
					//TODO: dla ulamkow
					// UWAGA: wychodza liczby bardzo przekraczajace zakres
					BigInteger diff = maxBigInt.subtract(minBigInt);
					int randomInt = random.nextInt();
					BigInteger product = BigInteger.valueOf(randomInt).multiply(diff);

					BigInteger nominator = minBigInt.add(product);

					fraction = new Fraction(nominator, BigInteger.valueOf(65536));
					tab[i][j] = (T) fraction;
				}

            	else
            		throw new IllegalArgumentException("Wrong type, choose Float, Double or Fraction");
            }
        }
    	return new MyMatrix<T>(c, tab);
    }
}
