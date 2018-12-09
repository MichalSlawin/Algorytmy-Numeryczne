package ug.protocols;

import ug.protocols.matrix.Equations;
import ug.protocols.matrix.MyMatrix;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import static ug.protocols.agent.Simulations.simulateAllVotings;

public class MainTest {

	private static final int AGENTS_COUNT = 20;
	private static final double EPSILON = 1e-12;
	private static final int SESSIONS = 100000;
	
    public static void main(String [] args) {
    	agentsCountVsTime();
		//methodsVsMonteCarlo();
		//mcPrecisionAndTimes();
		//methodsVsAccuracy();
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
	
	private static void agentsCountVsTime() {
		// w plikach w kolejnosci: ilosc agentow, czas wywolania

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
			e.getMatrix().jacobiSeidel(e.getVector(), EPSILON, true);
			executionTime = System.currentTimeMillis() - millisActualTime;
			System.out.println("GaussSeidel for " + aCount +" agents done");
			toFile("testResults/gaussSeidelTimes.csv", aCount + "," + executionTime);
			
			e = new Equations(aCount);
			millisActualTime = System.currentTimeMillis();
	    	e.getMatrix().jacobiSeidel(e.getVector(), EPSILON, false);
			executionTime = System.currentTimeMillis() - millisActualTime;
			System.out.println("Jacobi for " + aCount +" agents done");
			toFile("testResults/jacobiTimes.csv", aCount + "," + executionTime);
			System.out.println();
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
			toFile("testResults/mcPrecAndTimes.csv", s + "," + Math.abs(mcResults.vectorNorm() - gResults.vectorNorm()) + "," + executionTime);
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

}
