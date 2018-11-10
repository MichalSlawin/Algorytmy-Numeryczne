import java.math.BigInteger;

public class Fraction extends Number{
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

    // skrocenie ulamka
    public void reduce() {
        BigInteger gcd = nominator.gcd(denominator);
        nominator = nominator.divide(gcd);
        denominator = denominator.divide(gcd);
    }

    // odwraca ulamek
    public Fraction rotate() {
        return new Fraction(this.denominator, this.nominator);
    }
    
    //sprawdza znak ulamka
    public boolean isPositive() {
    	if((nominator.compareTo(BigInteger.valueOf(0)) == -1 && denominator.compareTo(BigInteger.valueOf(0)) == -1) || (nominator.compareTo(BigInteger.valueOf(0)) == 1 && denominator.compareTo(BigInteger.valueOf(0)) == 1) || nominator.compareTo(BigInteger.valueOf(0)) == 0)
    		return true;
    	else return false;
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

    public static Fraction div(Fraction dividend, Fraction divisor) {
        return mul(dividend, divisor.rotate());
    }
    
    public static Fraction zero() {
    	return new Fraction(BigInteger.ZERO, BigInteger.ONE);
    }
    
    public static int compare(Fraction num1, Fraction num2) {
    	setCommonDenominator(num1, num2);
    	return num1.getNominator().compareTo(num2.getNominator());
    }

    @Override
    public String toString() {
        if(denominator.equals(BigInteger.ONE)) return nominator + "";
        else if(this.isPositive() && nominator.compareTo(BigInteger.valueOf(0))<0)
        	return nominator.multiply(BigInteger.valueOf(-1)) + "/" + denominator.multiply(BigInteger.valueOf(-1));
        else if(!this.isPositive() && denominator.compareTo(BigInteger.valueOf(0))<0)
        	return nominator.multiply(BigInteger.valueOf(-1)) + "/" + denominator.multiply(BigInteger.valueOf(-1));
        else return nominator + "/" + denominator;
    }

	@Override
	public int intValue() {
		return this.nominator.intValue() / this.denominator.intValue();
	}

	@Override
	public long longValue() {
		return this.nominator.longValue() / this.denominator.longValue();
	}

	@Override
	public float floatValue() {
		return this.nominator.floatValue() / this.denominator.floatValue();
	}

	@Override
	public double doubleValue() {
		return this.nominator.doubleValue() / this.denominator.doubleValue();
	}
}
