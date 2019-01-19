package ug.protocols.approximation;

public class ApproximationFunction {

    private double[] function;

    ApproximationFunction(double[] function)
    {
        this.function = function;
    }

    public double getResult(double argument)
    {
        double result = 0.0;
        for (int i = 0; i < function.length; i++)
        {
            result += function[i] * Math.pow(argument, i);
        }

        return result;
    }

    public String getFunctionString()
    {

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < function.length; i++)
        {
            stringBuilder.append(function[i]);
            stringBuilder.append("*x^");
            stringBuilder.append(i);
            if(i != function.length-1) stringBuilder.append(" + ");
        }

        return stringBuilder.toString();
    }
}
