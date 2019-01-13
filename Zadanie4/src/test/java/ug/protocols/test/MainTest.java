package ug.protocols.test;

import ug.protocols.approximation.ApproximationFunction;
import ug.protocols.approximation.Approximator;
import ug.protocols.matrix.Equations;
import ug.protocols.matrix.MyMatrix;

import java.awt.Point;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import static ug.protocols.agent.Simulations.simulateAllVotings;

public class MainTest {

	private static final int AGENTS_COUNT = 10;
	private static final double EPSILON = 1e-10;
	private static final int SESSIONS = 1000;
	private enum Algorithm {
		GAUSS_PG,
		GAUSS_PG_OPT,
		GAUSS_SEIDEL
	}
	
    public static void main(String [] args) {
		deleteAllFiles();

		approximationTest(Algorithm.GAUSS_PG, 3, "testResults/gaussTimes.csv");
		approximationTest(Algorithm.GAUSS_PG_OPT, 2, "testResults/gaussOptTimes.csv");
		approximationTest(Algorithm.GAUSS_SEIDEL, 2, "testResults/gaussSeidelTimes.csv");
}

	private static void approximationTest(Algorithm algorithm, int degree, String filename) throws IllegalArgumentException {
		Equations e;
		long millisActualTime;
		long executionTime;
		double arguments[] = new double[16];
		double values[] = new double[16];
		ApproximationFunction approximationFunction;

		for(int aCount = 4; aCount <= 64; aCount += 4) { // dla 64 agentow liczba rownan wynosi 2145
			e = new Equations(aCount);
			millisActualTime = System.currentTimeMillis();
			executionTime = System.currentTimeMillis() - millisActualTime;
			toFile("testResults/buildTimes.csv", aCount + "," + executionTime);
			millisActualTime = System.currentTimeMillis();
			if(algorithm == Algorithm.GAUSS_PG) e.getMatrix().gaussPG(e.getVector());
			else if(algorithm == Algorithm.GAUSS_PG_OPT) e.getMatrix().gaussPGOpt(e.getVector());
			else if(algorithm == Algorithm.GAUSS_SEIDEL) e.getMatrix().jacobiSeidel(e.getVector(), EPSILON, true);
			else throw new IllegalArgumentException("Nie ma takiego algorytmu");
			executionTime = System.currentTimeMillis() - millisActualTime;
			toFile(filename, aCount + "," + executionTime);
			arguments[(aCount/4)-1] = aCount;
			values[(aCount/4)-1] = executionTime;
		}
		approximationFunction = Approximator.GetApproximation(degree, arguments, values);
		System.out.println(approximationFunction.GetFunctionString());
	}
	
	private static void agentsCountVsTimeAndAccuracy() {
		// w plikach xTimes.csv w kolejnosci: ilosc agentow, czas wywolania
		//w pliku methodsErrorsVsAgentsCount w kolejnosci: ilosc agentow, blad gaussa, blad gaussaOpt, blad jacobiego, blad seidela
		MyMatrix result;
		long millisActualTime;
		long executionTime;
		Equations e;
		double gError, goError, gsError, jError;
		
		for(int aCount = 20; aCount < 200; aCount += 20) {
			e = new Equations(aCount);
			millisActualTime = System.currentTimeMillis();
			result = e.getMatrix().gaussPG(e.getVector());
			executionTime = System.currentTimeMillis() - millisActualTime;
			System.out.println("Gauss for " + aCount +" agents done");
			toFile("testResults/gaussTimes.csv", aCount + "," + executionTime);
			gError =  Math.abs(0.5 - result.getCell(e.getMap().get(new Point(aCount/2, aCount/2)), 0));
			
			e = new Equations(aCount);
			millisActualTime = System.currentTimeMillis();
			result = e.getMatrix().gaussPGOpt(e.getVector());
			executionTime = System.currentTimeMillis() - millisActualTime;
			System.out.println("GaussOpt for " + aCount +" agents done");
			toFile("testResults/gaussOptTimes.csv", aCount + "," + executionTime);
			goError =  Math.abs(0.5 - result.getCell(e.getMap().get(new Point(aCount/2, aCount/2)), 0));
			
			e = new Equations(aCount);
			millisActualTime = System.currentTimeMillis();
			result = e.getMatrix().jacobiSeidel(e.getVector(), EPSILON, true);
			executionTime = System.currentTimeMillis() - millisActualTime;
			System.out.println("GaussSeidel for " + aCount +" agents done");
			toFile("testResults/gaussSeidelTimes.csv", aCount + "," + executionTime);
			gsError = Math.abs(0.5 - result.getCell(e.getMap().get(new Point(aCount/2, aCount/2)), 0));
			
			e = new Equations(aCount);
			millisActualTime = System.currentTimeMillis();
			result = e.getMatrix().jacobiSeidel(e.getVector(), EPSILON, false);
			executionTime = System.currentTimeMillis() - millisActualTime;
			System.out.println("Jacobi for " + aCount +" agents done");
			toFile("testResults/jacobiTimes.csv", aCount + "," + executionTime);
			jError = Math.abs(0.5 - result.getCell(e.getMap().get(new Point(aCount/2, aCount/2)), 0));
			System.out.println();
			
			toFile("testResults/methodsErrorsVsAgentsCount.csv", aCount + "," + gError + "," + goError + "," + jError + "," + gsError);
		}
	}
	
	private static void methodsVsMonteCarlo() {
		//w pliku w kolejnosci: MC - Gauss, MC - GaussOpt, MC - Jacobi, MC - GaussSeidel
		
		Equations e;
		MyMatrix mcResults = simulateAllVotings(AGENTS_COUNT, SESSIONS);
		
		e = new Equations(AGENTS_COUNT);
		MyMatrix gResults = e.getMatrix().gaussPG(e.getVector());
		
		e = new Equations(AGENTS_COUNT);
		MyMatrix goResults = e.getMatrix().gaussPGOpt(e.getVector());
		
		e = new Equations(AGENTS_COUNT);
		MyMatrix gsResults = e.getMatrix().jacobiSeidel(e.getVector(), EPSILON, true);
		
		e = new Equations(AGENTS_COUNT);
		MyMatrix jResults = e.getMatrix().jacobiSeidel(e.getVector(), EPSILON, false);
		
		toFile("testResults/normsWithMC.csv", Math.abs(mcResults.vectorNorm() - gResults.vectorNorm()) + "," + Math.abs(mcResults.vectorNorm() - goResults.vectorNorm()) + "," +
				+ Math.abs(mcResults.vectorNorm() - jResults.vectorNorm()) + "," + Math.abs(mcResults.vectorNorm() - gsResults.vectorNorm()));
		System.out.println("Methods vs Monte Carlo done");
	}

	private static void mcPrecisionAndTimes() {
		//w pliku w kolejnosci: liczba sesji, roznica norm z gaussOpt, czas wywolania

		int[] sessions = {500, 1000, 5000, 10000, 50000, 100000};
		long millisActualTime;
		long executionTime;
		Equations e = new Equations(AGENTS_COUNT);
		MyMatrix gResults = e.getMatrix().gaussPGOpt(e.getVector());
		MyMatrix mcResults;

		for(int s : sessions) {
			millisActualTime = System.currentTimeMillis();
			mcResults = simulateAllVotings(AGENTS_COUNT, s);
			executionTime = System.currentTimeMillis() - millisActualTime;
			toFile("testResults/mcPrecAndTimes.csv", s + "," + Math.abs(mcResults.getCell(e.getMap().get(new Point(AGENTS_COUNT/2, AGENTS_COUNT/2)), 0) - 0.5) + "," + executionTime);
			System.out.println("Monte Carlo for " + s + " sessions and " + AGENTS_COUNT + " agents done");
		}
	}

	private static void methodsVsAccuracy() {
    	double[] accuracies = {1e-6, 1e-10, 1e-14};
		long millisActualTime;
		long executionTime;
		Equations e;

    	for(double accuracy : accuracies) {
			e = new Equations(AGENTS_COUNT);
			millisActualTime = System.currentTimeMillis();
			e.getMatrix().jacobiSeidel(e.getVector(), accuracy, true);
			executionTime = System.currentTimeMillis() - millisActualTime;
			System.out.println("GaussSeidel for " + accuracy +"  done");
			toFile("testResults/gaussSeidelAccuracyTimes.csv", accuracy + "," + executionTime);

			e = new Equations(AGENTS_COUNT);
			millisActualTime = System.currentTimeMillis();
			e.getMatrix().jacobiSeidel(e.getVector(), accuracy, false);
			executionTime = System.currentTimeMillis() - millisActualTime;
			System.out.println("Jacobi for " + accuracy +"  done");
			toFile("testResults/jacobiAccuracyTimes.csv", accuracy + "," + executionTime);
		}
	}

	private static void deleteFile(String filename) {
		File file = new File(filename);
		try {
			Files.deleteIfExists(file.toPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void deleteAllFiles() {
		deleteFile("testResults/gaussTimes.csv");
		deleteFile("testResults/gaussOptTimes.csv");
		deleteFile("testResults/gaussSeidelTimes.csv");
		deleteFile("testResults/buildTimes.csv");
		deleteFile("testResults/seidelIter.csv");
		deleteFile("testResults/jacobiIter.csv");
	}

	public static void toFile(String fileName, String content) {
		try(FileWriter fw = new FileWriter(fileName, true);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter out = new PrintWriter(bw))
		{
			out.println(content);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
