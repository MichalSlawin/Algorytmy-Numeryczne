package ug.protocols.approximation;

import ug.protocols.matrix.MyMatrix;

public class ApproximationBuilder {

    public static ApproximationManager getApproximation(int degree, double[] arguments, double[] values)
    {
        int sNumber = degree * 2 + 1;
        double sMatrix[][] = new double[arguments.length][sNumber];
        double sVector[] = new double[sNumber];

        for (int i = 0; i < arguments.length; i++)
        {
            for (int j = 0; j < sNumber; j++)
            {
                sMatrix[i][j] = Math.pow(arguments[i], j);
                sVector[j] += sMatrix[i][j];
            }
        }

        int tNumber = degree + 1;
        double tMatrix[][] = new double[arguments.length][sNumber];
        double tVector[] = new double[sNumber];

        for (int i = 0; i < arguments.length; i++)
        {
            for (int j = 0; j < tNumber; j++)
            {
                tMatrix[i][j] = values[i] * sMatrix[i][j];
                tVector[j] += tMatrix[i][j];
            }
        }

        double equationsMatrix[][] = new double[tNumber][tNumber];
        double equationsVector[] = new double[tNumber];
        int offset = 0;

        for (int i = 0; i < tNumber; i++)
        {
            for (int j = 0; j < tNumber; j++)
            {
                equationsMatrix[i][j] = sVector[j + offset];
            }

            equationsVector[i] = tVector[i];
            offset++;
        }

        MyMatrix equationsMatrixObj = new MyMatrix(equationsMatrix);
        MyMatrix equationsVectorObj = new MyMatrix(equationsVector);
        MyMatrix resultVectorObj = equationsMatrixObj.gaussPGOpt(equationsVectorObj);

        double resultVector[] = new double[resultVectorObj.getRows()];
        for(int i = 0; i < resultVectorObj.getRows(); i++) {
            resultVector[i] = resultVectorObj.getCell(i, 0);
        }

        return new ApproximationManager(resultVector);
    }
}
