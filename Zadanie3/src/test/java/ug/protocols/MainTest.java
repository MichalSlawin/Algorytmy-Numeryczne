package ug.protocols;

import ug.protocols.matrix.Equations;
import ug.protocols.matrix.MyMatrix;

import static ug.protocols.agent.Simulations.simulateAllVotings;

public class MainTest {

	private static final int AGENTS_COUNT = 10;
	private static final double EPSILON = 1e-12;
	private static final int SESSIONS = 10000;
	
    public static void main(String [] args) {
		double monteCarloNorm = simulateAllVotings(AGENTS_COUNT, SESSIONS).vectorNorm();
//    	timesTest();

		System.out.println("jacobiNorm - seidelNorm = " + compareJacobiSeidel(monteCarloNorm));
    	System.out.println("GaussPg - GaussPgOpt = " + compareGausses(monteCarloNorm));
    	//System.out.println(simulateAllVotings(AGENTS_COUNT, SESSIONS));
    }

    private static void timesTest() {
    	long millisActualTime;
		long executionTime;
		Equations e = new Equations(AGENTS_COUNT);
		
		millisActualTime = System.currentTimeMillis();
		System.out.println(e.getMatrix().gaussPG(e.getVector()).transpose());
		executionTime = System.currentTimeMillis() - millisActualTime;
		System.out.println("Czas Gaussa: " + executionTime);
		
		millisActualTime = System.currentTimeMillis();
		System.out.println(e.getMatrix().gaussPGOpt(e.getVector()).transpose());
		executionTime = System.currentTimeMillis() - millisActualTime;
		System.out.println("Czas Gaussa Opt: " + executionTime);
		
		millisActualTime = System.currentTimeMillis();
		System.out.println(e.getMatrix().gaussSeidel(e.getVector(), EPSILON).transpose());
		executionTime = System.currentTimeMillis() - millisActualTime;
		System.out.println("Czas Gaussa-Seidela: " + executionTime);
		
		millisActualTime = System.currentTimeMillis();
    	System.out.println(e.getMatrix().jacobi(e.getVector(), EPSILON).transpose());
		executionTime = System.currentTimeMillis() - millisActualTime;
		System.out.println("Czas Jacobiego: " + executionTime);
    }

	private static double compareJacobiSeidel(double monteCarloNorm) {
		Equations e = new Equations(AGENTS_COUNT);

		MyMatrix jacobiResult = e.getMatrix().jacobi(e.getVector(), EPSILON);
		//System.out.println("Z jacobiego:\n" + jacobiResult.transpose());
		double jacobiNorm = jacobiResult.vectorNorm();
		System.out.println("|monteCarloNorm - jacobiNorm| = " + Math.abs(monteCarloNorm - jacobiNorm));

		MyMatrix seidelResult = e.getMatrix().gaussSeidel(e.getVector(), EPSILON);
		//System.out.println("Z seidela:\n" + seidelResult.transpose());
		double seidelNorm = seidelResult.vectorNorm();
		System.out.println("|monteCarloNorm - seidelNorm| = " + Math.abs(seidelNorm - jacobiNorm));

		return jacobiNorm-seidelNorm;
	}
	
	private static double compareGausses(double monteCarloNorm) {
		Equations e = new Equations(AGENTS_COUNT);

		MyMatrix gaussPgResult = e.getMatrix().gaussPG(e.getVector());
		//System.out.println("GaussPg:\n" + gaussPgResult.transpose());
		double gaussPgNorm = gaussPgResult.vectorNorm();
		System.out.println("|monteCarloNorm - gaussPgNorm| = " + Math.abs(monteCarloNorm - gaussPgNorm));

		MyMatrix gaussPgOptResult = e.getMatrix().gaussPGOpt(e.getVector());
		//System.out.println("GaussPg zoptymalizowany:\n" + gaussPgOptResult.transpose());
		double gaussPgOptNorm  = gaussPgOptResult.vectorNorm();
		System.out.println("|monteCarloNorm - gaussPgOptNorm| = " + Math.abs(monteCarloNorm - gaussPgOptNorm));

		return gaussPgNorm-gaussPgOptNorm;
	}

}
