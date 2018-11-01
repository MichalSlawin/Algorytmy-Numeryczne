import java.math.BigInteger;

//TODO: napisać działania na ułamkach (dodawanie, mnożenie, odejmowanie, dzielenie)
public class Fraction {
    private BigInteger nominator;
    private BigInteger denominator;

    public Fraction(BigInteger nominator, BigInteger denominator) {
        if(denominator.equals(BigInteger.ZERO)) {
            throw new IllegalArgumentException("The denominator is zero.");
        }
        if(nominator.equals(BigInteger.ZERO)) {
            this.nominator = BigInteger.ZERO;
            this.denominator = BigInteger.ONE;
        }
        else {
            this.nominator = nominator;
            this.denominator = denominator;
            this.reduce();
        }

    }

    public BigInteger getNominator() {
        return nominator;
    }

    public void setNominator(BigInteger nominator) {
        this.nominator = nominator;
    }

    public BigInteger getDenominator() {
        return denominator;
    }

    public void setDenominator(BigInteger denominator) {
        this.denominator = denominator;
    }

    private void reduce() {
        BigInteger gcd = nominator.gcd(denominator);
        nominator = nominator.divide(gcd);
        denominator = denominator.divide(gcd);
    }

    public static void setCommonDenominator(Fraction fraction1, Fraction fraction2) {
        BigInteger nominator1 = fraction1.getNominator();
        BigInteger nominator2 = fraction2.getNominator();
        BigInteger denominator1 = fraction1.getDenominator();
        BigInteger denominator2 = fraction2.getDenominator();

        BigInteger gcdDenominator = denominator1.gcd(denominator2);
        BigInteger commonDenominator = (denominator1.multiply(denominator2)).divide(gcdDenominator);

        fraction1.setNominator((commonDenominator.divide(denominator1)).multiply(nominator1));
        fraction1.setDenominator(commonDenominator);
        fraction2.setNominator((commonDenominator.divide(denominator2)).multiply(nominator2));
        fraction2.setDenominator(commonDenominator);
    }

    public static Fraction add(Fraction addend1, Fraction addend2) {
        return null;
    }

    @Override
    public String toString() {
        if(denominator.equals(BigInteger.ONE)) return nominator + "";
        else return nominator + "/" + denominator;
    }
}
