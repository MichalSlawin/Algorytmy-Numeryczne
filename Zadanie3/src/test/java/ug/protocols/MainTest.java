package ug.protocols;

import ug.protocols.agent.*;
import ug.protocols.equation.*;

public class MainTest {

	private static int[] R = new int[3];	//R[0] = Y ,R[1] = N , R[2] = U
	
    public static void main(String [] args) {
       // System.out.println(simulateVotingTest(3,2,0,100));
        //System.out.println(createMatrixTest());

        //simulateAllVotingsTest(0,3, 1000);
    	//MyMatrixTest.gaussSeidelTest();

        //generateEmptyEquationsTest();
    	EqTest eq = new EqTest(3);
    	
    	eq.buildMatrix();

    }

    private static void generateEmptyEquationsTest() {
        Equations equations = new Equations(3);
        Equations equations2 = new Equations(4);

        equations.generateEmptyEquations();
        System.out.println(equations);

        equations2.generateEmptyEquations();
        System.out.println(equations2);
    }
    
    private static void simulateAllVotingsTest(int x, int agentsNumber, int sessions) {

		if(x == 3) {
			if(R[0]+ R[1]+ R[2]==agentsNumber) {
				System.out.println(simulateVotingTest(R[0], R[1], R[2], sessions));
			}
		} else {
			for(int i = 0; i <= agentsNumber; i++) {
				R[x + 1] = i;
				simulateAllVotingsTest(x + 1, agentsNumber, sessions);
			}
		}
	}

    private static double simulateVotingTest(int yes, int no, int un, int sessions) {
        int yesVotes = 0;
        Agents agents;
        Agent.State state;

        for(int i = 0; i < sessions; i++) {
            //System.out.println("\nVoting #" + i);

            agents = AgentManager.generateAgents(yes, no, un);
            state = AgentManager.simulateVoting(agents);

            //System.out.println("Voting ended with result: " + state);
            if(state == Agent.State.Y) yesVotes++;
        }
        System.out.print("#Y=" + yes + " #N=" + no + " #U=" + un + " Py=");

        return (double)yesVotes/(double)sessions;
    }

}
