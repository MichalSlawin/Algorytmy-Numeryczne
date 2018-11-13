import java.math.BigInteger;

@SuppressWarnings("unchecked")
public class MyMath {
	
	public static <T> T add(T num1, T num2) {
		if(num1 instanceof Integer && num2 instanceof Integer)
			return (T) (Integer) ((Integer) num1 + (Integer) num2);
		else if(num1 instanceof Float && num2 instanceof Float)
			return (T) (Float) ((Float) num1 + (Float) num2);
		else if(num1 instanceof Double && num2 instanceof Double)
			return (T) (Double) ((Double) num1 + (Double) num2);
		else if(num1 instanceof Fraction && num2 instanceof Fraction)
			return (T) Fraction.add((Fraction) num1, (Fraction) num2);
		else throw new IllegalArgumentException("Wrong type, choose Float, Double or Fraction");
	}

	public static <T> T sub(T num1, T num2) {
		if(num1 instanceof Integer && num2 instanceof Integer)
			return (T) (Integer) ((Integer) num1 - (Integer) num2);
		else if(num1 instanceof Float && num2 instanceof Float)
			return (T) (Float) ((Float) num1 - (Float) num2);
		else if(num1 instanceof Double && num2 instanceof Double)
			return (T) (Double) ((Double) num1 - (Double) num2);
		else if(num1 instanceof Fraction && num2 instanceof Fraction)
			return (T) Fraction.sub((Fraction) num1, (Fraction) num2);
		else throw new IllegalArgumentException("Wrong type, choose Float, Double or Fraction");
	}

	public static <T> T mul(T num1, T num2) {
		if(num1 instanceof Integer && num2 instanceof Integer)
			return (T) (Integer) ((Integer) num1 * (Integer) num2);
		else if(num1 instanceof Float && num2 instanceof Float)
			return (T) (Float) ((Float) num1 * (Float) num2);
		else if(num1 instanceof Double && num2 instanceof Double)
			return (T) (Double) ((Double) num1 * (Double) num2);
		else if(num1 instanceof Fraction && num2 instanceof Fraction)
			return (T) Fraction.mul((Fraction) num1, (Fraction) num2);
		else throw new IllegalArgumentException("Wrong type, choose Float, Double or Fraction");
	}

	public static <T> T div(T num1, T num2) {
		if(num1 instanceof Integer && num2 instanceof Integer)
			return (T) (Integer) ((Integer) num1 / (Integer) num2);
		else if(num1 instanceof Float && num2 instanceof Float)
			return (T) (Float) ((Float) num1 / (Float) num2);
		else if(num1 instanceof Double && num2 instanceof Double)
			return (T) (Double) ((Double) num1 / (Double) num2);
		else if(num1 instanceof Fraction && num2 instanceof Fraction)
			return (T) Fraction.div((Fraction) num1, (Fraction) num2);
		else throw new IllegalArgumentException("Wrong type, choose Float, Double or Fraction");
	}
	
	public static <T> T abs(T num1) {
		if(num1 instanceof Integer)
			if((Integer) num1 < 0)
				return (T) (Integer) ((Integer) num1*(-1));
			else return num1;
		else if(num1 instanceof Float)
			if((Float) num1 < 0)
				return (T) (Float) ((Float) num1*(-1));
			else return num1;
		else if(num1 instanceof Double)
			if((Double) num1 < 0)
				return (T) (Double) ((Double) num1*(-1));
			else return num1;
		else if(num1 instanceof Fraction)
			if(((Fraction) num1).isPositive()) 
				return num1;
			else
				return (T) new Fraction(((Fraction) num1).getNominator().multiply(BigInteger.valueOf(-1)),
						((Fraction) num1).getDenominator());
		else throw new IllegalArgumentException("Wrong type, choose Float, Double or Fraction");
	}
	
	public static <T> int compare(T num1, T num2) {
		if(num1 instanceof Integer && num2 instanceof Integer)
			return Integer.compare((Integer) num1, (Integer)  num2);
		else if(num1 instanceof Float && num2 instanceof Float)
			return Float.compare((Float) num1, (Float)  num2);
		else if(num1 instanceof Double && num2 instanceof Double)
			return Double.compare((Double) num1, (Double)  num2);
		else if(num1 instanceof Fraction && num2 instanceof Fraction)
			return Fraction.compare((Fraction) num1, (Fraction)  num2);
		else throw new IllegalArgumentException("Wrong type, choose Float, Double or Fraction");
	}
	
	public static <T> T sqrt(T num1) {
		if(num1 instanceof Float)
			return (T) (Double) Math.sqrt((Float) num1);
		else if(num1 instanceof Double)
			return (T) (Double) Math.sqrt((Double) num1);
		else if(num1 instanceof Fraction)
			return (T) num1;
		else throw new IllegalArgumentException("Wrong type, choose Float, Double or Fraction");
	}
}
