package ug.protocols;

import ug.protocols.matrix.Equations;

import static ug.protocols.agent.Simulations.simulateVoting;
import static ug.protocols.agent.Simulations.simulateAllVotings;

public class MainTest {
	
    public static void main(String [] args) {
    	timesTest();
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
		System.out.println(e.getMatrix().gaussSeidel(e.getVector(), 1000).transpose());
		executionTime = System.currentTimeMillis() - millisActualTime;
		System.out.println("Czas Gaussa-Seidela: " + executionTime);
		
		millisActualTime = System.currentTimeMillis();
    	System.out.println(e.getMatrix().jacobi(e.getVector(), 1000).transpose());
		executionTime = System.currentTimeMillis() - millisActualTime;
		System.out.println("Czas Jacobiego: " + executionTime);
    }
    
}
