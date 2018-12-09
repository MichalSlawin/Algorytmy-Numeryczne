package ug.protocols;

import ug.protocols.matrix.Equations;
import ug.protocols.matrix.MyMatrix;

import static ug.protocols.agent.Simulations.simulateVoting;
import static ug.protocols.agent.Simulations.simulateAllVotings;

public class MainTest {
	
    public static void main(String [] args) {
    	timesTest();

		//System.out.println("jacobiNorm - seidelNorm = " + compareJacobiSeidel(30,1000000));
    	//System.out.println("GaussPg - GaussPgOpt = " + compareGausses(30));
    }

    public static void timesTest() {
    	long millisActualTime;
		long executionTime;
		Equations e = new Equations(30);
		
		millisActualTime = System.currentTimeMillis();
		System.out.println(e.getMatrix().gaussPG(e.getVector()).transpose());
		executionTime = System.currentTimeMillis() - millisActualTime;
		System.out.println("Czas Gaussa: " + executionTime);
		
		millisActualTime = System.currentTimeMillis();
		System.out.println(e.getMatrix().gaussPGOpt(e.getVector()).transpose());
		executionTime = System.currentTimeMillis() - millisActualTime;
		System.out.println("Czas Gaussa Opt: " + executionTime);
		
		millisActualTime = System.currentTimeMillis();
		System.out.println(e.getMatrix().gaussSeidel(e.getVector(), 1000).transpose());
		executionTime = System.currentTimeMillis() - millisActualTime;
		System.out.println("Czas Gaussa-Seidela: " + executionTime);
		
		millisActualTime = System.currentTimeMillis();
    	System.out.println(e.getMatrix().jacobi(e.getVector(), 1000).transpose());
		executionTime = System.currentTimeMillis() - millisActualTime;
		System.out.println("Czas Jacobiego: " + executionTime);
    }

	public static double compareJacobiSeidel(int agentsCount, int iterationsNo) {
		Equations e = new Equations(agentsCount);

		MyMatrix jacobiResult = e.getMatrix().jacobi(e.getVector(), iterationsNo);
		System.out.println("Z jacobiego:\n" + jacobiResult.transpose());
		double jacobiNorm = jacobiResult.vectorNorm();

		MyMatrix seidelResult = e.getMatrix().gaussSeidel(e.getVector(), iterationsNo);
		System.out.println("Z seidela:\n" + seidelResult.transpose());
		double seidelNorm = seidelResult.vectorNorm();

		return jacobiNorm-seidelNorm;
	}
	
	public static double compareGausses(int agentsCount) {
		Equations e = new Equations(agentsCount);

		MyMatrix gaussPgResult = e.getMatrix().gaussPG(e.getVector());
		System.out.println("GaussPg:\n" + gaussPgResult.transpose());
		double guassPgNorm = gaussPgResult.vectorNorm();

		MyMatrix gaussPgOptResult = e.getMatrix().gaussPGOpt(e.getVector());
		System.out.println("GaussPg zoptymalizowany:\n" + gaussPgOptResult.transpose());
		double guassPgOptNorm  = gaussPgOptResult.vectorNorm();

		return guassPgNorm-guassPgOptNorm;
	}

}
