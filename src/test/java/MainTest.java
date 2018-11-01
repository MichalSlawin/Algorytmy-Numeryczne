import java.math.BigInteger;

@SuppressWarnings("unchecked")
public class MainTest {
	private static final Class TYP = Integer.class;

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
    	
        plusConstr1Test();
        timesConstr2Test();
        fractionClassTest();
        fractionMyMatrixTest();
    }

    //test pierwszego konstruktora i dodawania
    private static void plusConstr1Test() {
        MyMatrix A = new MyMatrix<Integer>(TYP, 3, 3);
        MyMatrix B = new MyMatrix<Integer>(TYP, 3, 3);

        for(int i=0; i<3; i++) {
            for (int j = 0; j < 3; j++) {
                A.setCell(4, i, j);
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

    private static void fractionClassTest() {
        System.out.println(fraction6_2 + " ; " + fraction8_10 + " ; " + fraction0_4);
        System.out.println(fraction2_3 + " + " + fraction5_6 + " = " + Fraction.add(fraction2_3, fraction5_6));
        System.out.println(fraction6_2 + " - " + fraction8_10 + " = " + Fraction.sub(fraction6_2, fraction8_10));
        System.out.println(fraction8_10 + " * " + fraction2_3 + " = " + Fraction.mul(fraction8_10, fraction2_3));
        System.out.println(fraction5_6 + " / " + fraction2_3 + " = " + Fraction.div(fraction5_6, fraction2_3));
    }

    private static void fractionMyMatrixTest() {
        Fraction tab1[][] = {{fraction1_2, fraction2_3, fraction1_1}, {fraction7_5, fraction0_4, fraction1_1}, {fraction6_8, fraction1_1, fraction1_1}};
        Fraction tab2[][] = {{fraction1_1, fraction3_4, fraction1_2}, {fraction6_8, fraction0_4, fraction6_2}, {fraction1_1, fraction3_4, fraction1_2}};

        MyMatrix myMatrix1 = new MyMatrix<Fraction>(Fraction.class, tab1);
        MyMatrix myMatrix2 = new MyMatrix<Fraction>(Fraction.class, tab2);

        System.out.println(myMatrix1.times(myMatrix2));
        System.out.println(myMatrix1.plus(myMatrix2));
    }
}
