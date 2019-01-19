package ug.protocols.approximation;

import ug.protocols.matrix.MyMatrix;

public class Approximator {
    public static ApproximationFunction getApproximation(int degree, double[] arguments, double[] values)
    {
        int unknownsNumber = degree * 2 + 1;
        double sMatrix[][] = new double[arguments.length][unknownsNumber];
        double sVector[] = new double[unknownsNumber];

        for (int i = 0; i < arguments.length; i++)
        {
            for (int j = 0; j < unknownsNumber; j++)
            {
                sMatrix[i][j] = Math.pow(arguments[i], j);
                sVector[j] += sMatrix[i][j];
            }
        }

        int tUnknowns = degree + 1;
        double tMatrix[][] = new double[arguments.length][unknownsNumber];
        double tVector[] = new double[unknownsNumber];

        for (int i = 0; i < arguments.length; i++)
        {
            for (int j = 0; j < tUnknowns; j++)
            {
                tMatrix[i][j] = values[i] * sMatrix[i][j];
                tVector[j] += tMatrix[i][j];
            }
        }


        double rMatrix[][] = new double[tUnknowns][tUnknowns];
        double rVector[] = new double[tUnknowns];
        int offset = 0;

        for (int i = 0; i < tUnknowns; i++)
        {
            for (int j = 0; j < tUnknowns; j++)
            {
                rMatrix[i][j] = sVector[j + offset];
            }

            rVector[i] = tVector[i];
            offset += 1;
        }

        MyMatrix matrixObj = new MyMatrix(rMatrix);
        MyMatrix vectorObj = new MyMatrix(rVector);
        MyMatrix resultVectorMatrix = matrixObj.gaussPGOpt(vectorObj);
        double resultVector[] = new double[resultVectorMatrix.getRows()];
        for(int i = 0; i < resultVectorMatrix.getRows(); i++) {
            resultVector[i] = resultVectorMatrix.getCell(i, 0);
        }

        return new ApproximationFunction(resultVector);
    }
}
