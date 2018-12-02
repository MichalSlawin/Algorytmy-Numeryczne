package ug.protocols;

import ug.protocols.agent.Agent;
import ug.protocols.agent.AgentManager;
import ug.protocols.agent.Agents;

public class MainTest {

    public static void main(String [] args) {
        System.out.println(simulateVotingTest(3,2,0,100));
    }

    private static void simulateAllVotingsTest(int agentsNumber) {
        //TODO: metoda ma uruchamiac simulateVotingTest dla wszystkich mozliwych kombinacji yes,no,un przy n = agentsNumber
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
