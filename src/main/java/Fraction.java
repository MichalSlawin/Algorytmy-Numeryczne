import java.math.BigInteger;

//TODO: poprawić dzielenie tak aby dzielnik nie zmienial wartosci
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

    // skrócenie ułamka
    public void reduce() {
        BigInteger gcd = nominator.gcd(denominator);
        nominator = nominator.divide(gcd);
        denominator = denominator.divide(gcd);
    }

    // odwrócenie ułamka
    public void rotate() {
        BigInteger temp = this.nominator;
        this.nominator = this.denominator;
        this.denominator = temp;
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
        setCommonDenominator(addend1, addend2);
        BigInteger nominatorsSum = (addend1.getNominator()).add(addend2.getNominator());

        return new Fraction(nominatorsSum, addend1.getDenominator());
    }

    public static Fraction sub(Fraction minuend, Fraction subtrahend) {
        setCommonDenominator(minuend, subtrahend);

        BigInteger nominatorsDiff = ((minuend.getNominator()).subtract(subtrahend.getNominator()));

        return new Fraction(nominatorsDiff, minuend.getDenominator());
    }

    public static Fraction mul(Fraction multiplicand, Fraction multiplier) {
        BigInteger nominator = (multiplier.getNominator()).multiply(multiplicand.getNominator());
        BigInteger denominator = (multiplier.getDenominator()).multiply(multiplicand.getDenominator());

        Fraction product = new Fraction(nominator, denominator);
        product.reduce();

        return product;
    }

    // UWAGA: w wyniku działania funkcji divisor staje się swoją odwrotnością
    public static Fraction div(Fraction dividend, Fraction divisor) {
        divisor.rotate();

        return mul(dividend, divisor);
    }

    @Override
    public String toString() {
        if(denominator.equals(BigInteger.ONE)) return nominator + "";
        else return nominator + "/" + denominator;
    }
}
