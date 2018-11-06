import java.util.Random;

import static java.lang.Math.pow;

public class GaussTest {
    private static final Float minRandom = -65536.0f;
    private static final Float maxRandom = 65535.0f;
    private static final Float dividerRandom = 65536.0f;
    private static Random random = new Random();

    public static MyMatrix generateMatrix(String typeStr, int rows, int columns) {
        MyMatrix myMatrix;

        random.setSeed(123456789);

        if(typeStr.equals("Float")) {
            Float tab[][] = new Float[rows][columns];
            for(int i = 0; i < rows; i++) {
                for(int j = 0; j < columns; j++) {
                    tab[i][j] = (minRandom + random.nextFloat() *
                            (maxRandom - minRandom))/dividerRandom;
                }
            }
            myMatrix = new MyMatrix<Float>(Float.class, tab);
        }

        else if(typeStr.equals("Double")) {
            Double tab[][] = new Double[rows][columns];
            for(int i = 0; i < rows; i++) {
                for(int j = 0; j < columns; j++) {
                    tab[i][j] = (minRandom + random.nextDouble() *
                            (maxRandom - minRandom))/dividerRandom;
                }
            }
            myMatrix = new MyMatrix<Double>(Double.class, tab);
        }

        else if(typeStr.equals("Fraction")) {
            Fraction tab[][] = new Fraction[rows][columns];
            //TODO: generowanie tablicy, jak wyzej
            myMatrix = new MyMatrix<Fraction>(Fraction.class, tab);
        }

        else {
            throw new IllegalArgumentException("Wrong type, choose Float, Double or Fraction");
        }

        return myMatrix;
    }
}
