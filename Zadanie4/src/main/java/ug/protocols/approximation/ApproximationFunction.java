package ug.protocols.approximation;

public class ApproximationFunction {
    double[] polynomial;

    public ApproximationFunction(double[] polynomial)
    {
        this.polynomial = polynomial;
    }

    public double GetResult(double argument)
    {
        double result = 0.0;
        for (int i = 0; i < polynomial.length; i++)
        {
            result += polynomial[i] * Math.pow(argument, i);
        }

        return result;
    }

    public String GetFunctionString()
    {

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < polynomial.length; i++)
        {
            stringBuilder.append(polynomial[i]);
            stringBuilder.append("*x^");
            stringBuilder.append(i);
            stringBuilder.append(" + ");
        }

        return stringBuilder.toString();
    }
}
