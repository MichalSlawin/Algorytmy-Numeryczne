import java.math.BigInteger;

public class MainTest {
    private static final Class TYP = Integer.class;

    public static void main(String[] args) {

        plusConstr1Test();
        timesConstr2Test();
        fractionTest();
    }

    //test pierwszego konstruktora i dodawania
    private static void plusConstr1Test() {
        MyMatrix A = new MyMatrix<Integer>(TYP, 3, 3);
        MyMatrix B = new MyMatrix<Integer>(TYP, 3, 3);

        for(int i=0; i<3; i++) {
            for (int j = 0; j < 3; j++) {
                A.setCell(3, i, j);
                B.setCell(2, i, j);
            }
        }
        System.out.println(A.plus(B));
    }

    //test drugiego konstruktora i mnożenia
    private static void timesConstr2Test() {
        Integer tab1[][] = {{3,5,3},{3,3,3},{3,3,3}};
        Integer tab2[][] = {{2,2,2},{2,4,2},{2,2,2}};
        MyMatrix myMatrix1 = new MyMatrix<Integer>(TYP, tab1);
        MyMatrix myMatrix2 = new MyMatrix<Integer>(TYP, tab2);

        System.out.println(myMatrix1.times(myMatrix2));
    }

    private static void fractionTest() {
        Fraction fraction1 = new Fraction(BigInteger.valueOf(6), BigInteger.valueOf(2)); // 3
        Fraction fraction2 = new Fraction(BigInteger.valueOf(8), BigInteger.valueOf(10)); // 4/5
        Fraction fraction3 = new Fraction(BigInteger.valueOf(0), BigInteger.valueOf(4)); // 0
        //Fraction fraction4 = new Fraction(BigInteger.valueOf(3), BigInteger.valueOf(0)); tak jak powinno wywala exception
        Fraction fraction5 = new Fraction(BigInteger.valueOf(2), BigInteger.valueOf(3)); // 2/3
        Fraction fraction6 = new Fraction(BigInteger.valueOf(5), BigInteger.valueOf(6)); // 5/6

        System.out.println(fraction1 + " ; " + fraction2 + " ; " + fraction3);

        Fraction.setCommonDenominator(fraction5, fraction6); // 4/6 i 5/6
        System.out.println("Common denominator: " + fraction5 + " ; " + fraction6);
    }
}
