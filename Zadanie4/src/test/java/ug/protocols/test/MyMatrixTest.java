package ug.protocols.test;

import ug.protocols.matrix.MyMatrix;

import static ug.protocols.matrix.RandomGenerator.generateMatrix;

class MyMatrixTest {
    static MyMatrix createMatrixTest() {
        double[][] tab = {{1.3,0,4.6},{-2.5, 6.624, 8},{-5, 22, 654}};

        return new MyMatrix(tab);
    }

    static void gaussPGTest() {
        MyMatrix randomMatrix = generateMatrix(3, 3);
        MyMatrix randomVector = generateMatrix(3, 1);

        System.out.println(randomMatrix);
        System.out.println(randomVector);

        System.out.println(randomMatrix.gaussPG(randomVector));
    }
    
    static void gaussSeidelTest(double eps) {
    	double[][] matrixTab = {{1.3,0,4.6},{-2.5, 6.624, 8},{-5, 22, 654}};
    	double[][] vectorTab = {{1}, {2}, {3}};

    	MyMatrix matrix = new MyMatrix(matrixTab);
    	MyMatrix vector = new MyMatrix(vectorTab);

    	System.out.println(matrix.jacobiSeidel(vector, eps, true).minus(matrix.gaussPGOpt(vector)));

    	MyMatrix randomMatrix = generateMatrix(3, 3);
    	MyMatrix randomVector = generateMatrix(3, 1);

    	System.out.println(randomMatrix);
        System.out.println(randomVector);

        System.out.println(randomMatrix.jacobiSeidel(randomVector, 1e-12, true).minus(randomMatrix.gaussPGOpt(randomVector)));
    }

    static void jacobiTest(double eps) {
        double[][] matrixTab = {{1.3,0,4.6},{-2.5, 6.624, 8},{-5, 22, 654}};
        double[][] vectorTab = {{1}, {2}, {3}};

        MyMatrix matrix = new MyMatrix(matrixTab);
        MyMatrix vector = new MyMatrix(vectorTab);

        System.out.println(matrix.jacobiSeidel(vector, eps, false).minus(matrix.gaussPGOpt(vector)));

        MyMatrix randomMatrix = generateMatrix(3, 3);
        MyMatrix randomVector = generateMatrix(3, 1);

        System.out.println(randomMatrix);
        System.out.println(randomVector);

        System.out.println(randomMatrix.jacobiSeidel(randomVector, eps, false).minus(randomMatrix.gaussPGOpt(randomVector)));
    }
}
