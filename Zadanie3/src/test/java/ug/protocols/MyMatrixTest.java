package ug.protocols;

import ug.protocols.matrix.MyMatrix;

class MyMatrixTest {
    static MyMatrix createMatrixTest() {
        double[][] tab = {{1.3,0,4.6},{-2.5, 6.624, 8},{-5, 22, 654}};

        return new MyMatrix(tab);
    }
}
