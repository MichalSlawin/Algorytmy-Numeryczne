package ug.protocols;

import ug.protocols.matrix.Equations;
import ug.protocols.matrix.MyMatrix;

import static ug.protocols.agent.Simulations.simulateAllVotings;

public class MainTest {

	private static final int AGENTS_COUNT = 4;
	private static final double EPSILON = 1e-12;
	private static final int SESSIONS = 10000;
	
    public static void main(String [] args) {
//    	timesTest();

//		System.out.println("jacobiNorm - seidelNorm = " + compareJacobiSeidel());
//    	System.out.println("GaussPg - GaussPgOpt = " + compareGausses());
    	System.out.println(simulateAllVotings(AGENTS_COUNT, SESSIONS));
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

	private static double compareJacobiSeidel() {
		Equations e = new Equations(AGENTS_COUNT);

		MyMatrix jacobiResult = e.getMatrix().jacobi(e.getVector(), EPSILON);
		//System.out.println("Z jacobiego:\n" + jacobiResult.transpose());
		double jacobiNorm = jacobiResult.vectorNorm();

		MyMatrix seidelResult = e.getMatrix().gaussSeidel(e.getVector(), EPSILON);
		//System.out.println("Z seidela:\n" + seidelResult.transpose());
		double seidelNorm = seidelResult.vectorNorm();

		return jacobiNorm-seidelNorm;
	}
	
	private static double compareGausses() {
		Equations e = new Equations(AGENTS_COUNT);

		MyMatrix gaussPgResult = e.getMatrix().gaussPG(e.getVector());
		//System.out.println("GaussPg:\n" + gaussPgResult.transpose());
		double guassPgNorm = gaussPgResult.vectorNorm();

		MyMatrix gaussPgOptResult = e.getMatrix().gaussPGOpt(e.getVector());
		//System.out.println("GaussPg zoptymalizowany:\n" + gaussPgOptResult.transpose());
		double guassPgOptNorm  = gaussPgOptResult.vectorNorm();

		return guassPgNorm-guassPgOptNorm;
	}

}
