public class MainTest {
    private static final Class TYP = Integer.class;

    public static void main(String[] args) {

        plusConstr1Test();

        timesConstr2Test();
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
    //BŁĄD: na ten moment zwracana jest macierz z samymi nullami
    private static void timesConstr2Test() {
        Integer tab1[][] = {{3,3,3},{3,3,3},{3,3,3}};
        Integer tab2[][] = {{2,2,2},{2,2,2},{2,2,2}};
        MyMatrix myMatrix1 = new MyMatrix<Integer>(TYP, tab1);
        MyMatrix myMatrix2 = new MyMatrix<Integer>(TYP, tab2);

        System.out.println(myMatrix1.times(myMatrix2));
    }
}
