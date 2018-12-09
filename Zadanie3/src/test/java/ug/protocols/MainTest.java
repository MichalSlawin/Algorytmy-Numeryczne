package ug.protocols;

import ug.protocols.matrix.Equations;
import ug.protocols.matrix.MyMatrix;

import static ug.protocols.agent.Simulations.simulateAllVotings;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class MainTest {

	private static final int AGENTS_COUNT = 20;
	private static final double EPSILON = 1e-12;
	private static final int SESSIONS = 100000;
	
    public static void main(String [] args) {
    	//agentsCountVsTime();
		//methodsVsMonteCarlo();
		methodsVsAccuracy();
    }
    
    private static void toFile(String fileName, String content) {
		try(FileWriter fw = new FileWriter(fileName, true);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter out = new PrintWriter(bw))
		{
			out.println(content);
		} catch (IOException e) {
			e.printStackTrace();
		}
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
	
	private static void agentsCountVsTime() {
		int[] agents = {5, 10, 20, 50, 100, 150, 200};
		long millisActualTime;
		long executionTime;
		Equations e;
		
		for(int aCount : agents) {
			e = new Equations(aCount);
			millisActualTime = System.currentTimeMillis();
			e.getMatrix().gaussPG(e.getVector());
			executionTime = System.currentTimeMillis() - millisActualTime;
			System.out.println("Gauss for " + aCount +" agents done");
			toFile("testResults/gaussTimes.csv", aCount + "," + executionTime);
			
			e = new Equations(aCount);
			millisActualTime = System.currentTimeMillis();
			e.getMatrix().gaussPGOpt(e.getVector());
			executionTime = System.currentTimeMillis() - millisActualTime;
			System.out.println("GaussOpt for " + aCount +" agents done");
			toFile("testResults/gaussOptTimes.csv", aCount + "," + executionTime);
			
			e = new Equations(aCount);
			millisActualTime = System.currentTimeMillis();
			e.getMatrix().gaussSeidel(e.getVector(), EPSILON);
			executionTime = System.currentTimeMillis() - millisActualTime;
			System.out.println("GaussSeidel for " + aCount +" agents done");
			toFile("testResults/gaussSeidelTimes.csv", aCount + "," + executionTime);
			
			e = new Equations(aCount);
			millisActualTime = System.currentTimeMillis();
	    	e.getMatrix().jacobi(e.getVector(), EPSILON);
			executionTime = System.currentTimeMillis() - millisActualTime;
			System.out.println("Jacobi for " + aCount +" agents done");
			toFile("testResults/jacobiTimes.csv", aCount + "," + executionTime);
			System.out.println();
		}
	}
	
	private static void methodsVsMonteCarlo() {
		//w pliku w kolejnosci MC - Gauss, MC - GaussOpt, MC - Jacobi, MC - GaussSeidel
		
		Equations e;
		MyMatrix mcResults = simulateAllVotings(AGENTS_COUNT, SESSIONS);
		
		e = new Equations(AGENTS_COUNT);
		MyMatrix gResults = e.getMatrix().gaussPG(e.getVector());
		
		e = new Equations(AGENTS_COUNT);
		MyMatrix goResults = e.getMatrix().gaussPGOpt(e.getVector());
		
		e = new Equations(AGENTS_COUNT);
		MyMatrix gsResults = e.getMatrix().gaussSeidel(e.getVector(), EPSILON);
		
		e = new Equations(AGENTS_COUNT);
		MyMatrix jResults = e.getMatrix().jacobi(e.getVector(), EPSILON);
		
		toFile("testResults/normsWithMC.csv", (mcResults.vectorNorm() - gResults.vectorNorm()) + "," + (mcResults.vectorNorm() - goResults.vectorNorm()) + "," +
				+ (mcResults.vectorNorm() - jResults.vectorNorm()) + "," + (mcResults.vectorNorm() - gsResults.vectorNorm()));
	}

	private static void methodsVsAccuracy() {
    	double[] accuracies = {1e-6, 1e-10, 1e-14};
		long millisActualTime;
		long executionTime;
		Equations e;

    	for(double accuracy : accuracies) {
			e = new Equations(AGENTS_COUNT);
			millisActualTime = System.currentTimeMillis();
			e.getMatrix().gaussSeidel(e.getVector(), accuracy);
			executionTime = System.currentTimeMillis() - millisActualTime;
			System.out.println("GaussSeidel for " + accuracy +"  done");
			toFile("testResults/gaussSeidelAccuracyTimes.csv", accuracy + "," + executionTime);

			e = new Equations(AGENTS_COUNT);
			millisActualTime = System.currentTimeMillis();
			e.getMatrix().jacobi(e.getVector(), accuracy);
			executionTime = System.currentTimeMillis() - millisActualTime;
			System.out.println("Jacobi for " + accuracy +"  done");
			toFile("testResults/jacobiAccuracyTimes.csv", accuracy + "," + executionTime);
		}
	}

}
