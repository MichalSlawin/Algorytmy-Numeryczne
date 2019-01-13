package ug.protocols.approximation;

public class ApproximationFunction {
    double[] Polynomial;

    public ApproximationFunction(double[] polynomial)
    {
        Polynomial = polynomial;
    }

    public double GetResult(double argument)
    {
        double result = 0.0;
        for (int i = 0; i < Polynomial.length; i++)
        {
            result += Polynomial[i] * Math.pow(argument, i);
        }

        return result;
    }

    public String GetFunctionString()
    {

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < Polynomial.length; i++)
        {
            stringBuilder.append(Polynomial[i]);
            stringBuilder.append("*x^");
            stringBuilder.append(i);
            stringBuilder.append(" + ");
        }

        return stringBuilder.toString();
    }
}
