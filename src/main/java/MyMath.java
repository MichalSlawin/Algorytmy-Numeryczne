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
		else if(num1 instanceof Long && num2 instanceof Long)
			return (T) (Long) ((Long) num1 + (Long) num2);
		else if(num1 instanceof BigInteger && num2 instanceof BigInteger)
			return (T) ((BigInteger) num1).add((BigInteger) num2);
		else if(num1 instanceof Fraction && num2 instanceof Fraction)
			return (T) Fraction.add((Fraction) num1, (Fraction) num2);
		else return null;
	}

	public static <T> T sub(T num1, T num2) {
		if(num1 instanceof Integer && num2 instanceof Integer)
			return (T) (Integer) ((Integer) num1 - (Integer) num2);
		else if(num1 instanceof Float && num2 instanceof Float)
			return (T) (Float) ((Float) num1 - (Float) num2);
		else if(num1 instanceof Double && num2 instanceof Double)
			return (T) (Double) ((Double) num1 - (Double) num2);
		else if(num1 instanceof Long && num2 instanceof Long)
			return (T) (Long) ((Long) num1 - (Long) num2);
		else if(num1 instanceof Fraction && num2 instanceof Fraction)
			return (T) Fraction.sub((Fraction) num1, (Fraction) num2);
		else return null;
	}

	public static <T> T mul(T num1, T num2) {
		if(num1 instanceof Integer && num2 instanceof Integer)
			return (T) (Integer) ((Integer) num1 * (Integer) num2);
		else if(num1 instanceof Float && num2 instanceof Float)
			return (T) (Float) ((Float) num1 * (Float) num2);
		else if(num1 instanceof Double && num2 instanceof Double)
			return (T) (Double) ((Double) num1 * (Double) num2);
		else if(num1 instanceof Long && num2 instanceof Long)
			return (T) (Long) ((Long) num1 * (Long) num2);
		else if(num1 instanceof BigInteger && num2 instanceof BigInteger)
			return (T) ((BigInteger) num1).multiply((BigInteger) num2);
		else if(num1 instanceof Fraction && num2 instanceof Fraction)
			return (T) Fraction.mul((Fraction) num1, (Fraction) num2);
		else return null;
	}

	public static <T> T div(T num1, T num2) {
		if(num1 instanceof Integer && num2 instanceof Integer)
			return (T) (Integer) ((Integer) num1 / (Integer) num2);
		else if(num1 instanceof Float && num2 instanceof Float)
			return (T) (Float) ((Float) num1 / (Float) num2);
		else if(num1 instanceof Double && num2 instanceof Double)
			return (T) (Double) ((Double) num1 / (Double) num2);
		else if(num1 instanceof Long && num2 instanceof Long)
			return (T) (Long) ((Long) num1 / (Long) num2);
		else if(num1 instanceof Fraction && num2 instanceof Fraction)
			return (T) Fraction.div((Fraction) num1, (Fraction) num2);
		else return null;
	}
}