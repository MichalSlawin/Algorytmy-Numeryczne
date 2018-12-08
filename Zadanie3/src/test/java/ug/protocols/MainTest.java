package ug.protocols;

import ug.protocols.matrix.Equations;

import static ug.protocols.agent.Simulations.simulateVotingTest;

public class MainTest {
	
    public static void main(String [] args) {

    	System.out.println("Z MonteCarlo:\n["+
    	+simulateVotingTest(0, 0, 3, 100000)+" "+simulateVotingTest(0, 1, 2, 100000)+" "+simulateVotingTest(0, 2, 1, 100000)+" "+
    	+simulateVotingTest(0, 3, 0, 100000)+" "+simulateVotingTest(1, 0, 2, 100000)+" "+simulateVotingTest(1, 1, 1, 100000)+" "+
    	+simulateVotingTest(1, 2, 0, 100000)+" "+simulateVotingTest(2, 0, 1, 100000)+" "+simulateVotingTest(2, 1, 0, 100000)+" "+
    	+simulateVotingTest(3, 0, 0, 100000)+"]");

    	Equations e = new Equations(3);
    	
    	System.out.println(e.getMatrix().gaussPG(e.getVector()).transpose());

    	
    }


}
