package ug.protocols;

import ug.protocols.agent.Agent;
import ug.protocols.agent.AgentManager;
import ug.protocols.agent.Agents;

public class MainTest {

	public final static int[] r = new int[4];	//r[1] = Y ,r[2] = N , r[3] = U 
	
    public static void main(String [] args) {
       // System.out.println(simulateVotingTest(3,2,0,100));
        simulateAllVotingsTest(0,3);
    }
/*
    private static void simulateAllVotingsTest(int agentsNumber) {
        //TODO: metoda ma uruchamiac simulateVotingTest dla wszystkich mozliwych kombinacji yes,no,un przy n = agentsNumber

    	
    }
   */
    
    public static void simulateAllVotingsTest(int x,int agentsNumber) {

		if(3 == x) {
			if(r[1]+r[2]+r[3]==agentsNumber) {
				System.out.println(simulateVotingTest(r[1],r[2],r[3],100));
			}
		} else {
			for(int i = 0; i <= agentsNumber; i++) {
				r[x + 1] = i;
				simulateAllVotingsTest(x + 1,agentsNumber);
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
