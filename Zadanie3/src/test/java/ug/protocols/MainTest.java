package ug.protocols;

import ug.protocols.agent.Agent;
import ug.protocols.agent.AgentManager;
import ug.protocols.agent.Agents;

import static ug.protocols.MyMatrixTest.createMatrixTest;

public class MainTest {

	private final static int[] R = new int[4];	//R[1] = Y ,R[2] = N , R[3] = U
	
    public static void main(String [] args) {
       // System.out.println(simulateVotingTest(3,2,0,100));
        //System.out.println(createMatrixTest());

        //simulateAllVotingsTest(0,3, 1000);
    	MyMatrixTest.gaussSeidelTest();

    }
    
    private static void simulateAllVotingsTest(int x, int agentsNumber, int sessions) {

		if(3 == x) {
			if(R[1]+ R[2]+ R[3]==agentsNumber) {
				System.out.println(simulateVotingTest(R[1], R[2], R[3],sessions));
			}
		} else {
			for(int i = 0; i <= agentsNumber; i++) {
				R[x + 1] = i;
				simulateAllVotingsTest(x + 1,agentsNumber, sessions);
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
