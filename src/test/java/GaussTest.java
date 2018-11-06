import java.util.Random;
import java.lang.reflect.Array;

@SuppressWarnings("unchecked")
public class GaussTest {
    private static final Float minRandom = -65536.0f;
    private static final Float maxRandom = 65535.0f;
    private static final Float dividerRandom = 65536.0f;
    private static Random random = new Random();

    @SuppressWarnings("unchecked")
	public static <T> MyMatrix<T> generateMatrix(Class<T> c, int rows, int columns) {
    	random.setSeed(123456789);
    	T[][] tab = (T[][]) Array.newInstance(c, rows, columns);
    	
    	for(int i = 0; i < rows; i++) {
            for(int j = 0; j < columns; j++) {
            	if(c == Float.class)
            		tab[i][j] = (T) MyMath.div((MyMath.add(minRandom, MyMath.mul(random.nextFloat(), MyMath.sub(maxRandom, minRandom)))), dividerRandom);
            	else if(c == Double.class)
            		tab[i][j] = (T) MyMath.div((MyMath.add((double) minRandom, MyMath.mul(random.nextDouble(), MyMath.sub((double) maxRandom, (double) minRandom)))), (double) dividerRandom);
            	else  if(c == Fraction.class);
            		//TODO: dla ulamkow
            	else
            		throw new IllegalArgumentException("Wrong type, choose Float, Double or Fraction");
            }
        }
    	return new MyMatrix<T>(c, tab);
    }
}
