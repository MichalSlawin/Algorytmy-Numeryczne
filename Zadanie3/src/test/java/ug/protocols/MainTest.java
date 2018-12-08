package ug.protocols;

import ug.protocols.matrix.Equations;

import static ug.protocols.agent.Simulations.simulateVoting;
import static ug.protocols.agent.Simulations.simulateAllVotings;

public class MainTest {
	
    public static void main(String [] args) {

    	System.out.println("Z MonteCarlo:\n"+ simulateAllVotings(3, 100000));

    	Equations e = new Equations(3);
    	
    	System.out.println("Z Gaussa:\n" + e.getMatrix().gaussPG(e.getVector()).transpose());
    }


}
