package ug.protocols.agent;

public class Simulations {

    private static int[] statesTable = new int[3];	//statesTable[0] = Y ,statesTable[1] = N , statesTable[2] = U

    public static void simulateAllVotingsTest(int x, int agentsNumber, int sessions) {
        if(x == 2) {
            if(statesTable[0]+ statesTable[1]+ statesTable[2]==agentsNumber) {
                System.out.println(simulateVotingTest(statesTable[0], statesTable[1], statesTable[2], sessions));
            }
        } else {
            for(int i = 0; i <= agentsNumber; i++) {
                statesTable[x + 1] = i;
                simulateAllVotingsTest(x + 1, agentsNumber, sessions);
            }
        }
    }

    public static double simulateVotingTest(int yes, int no, int un, int sessions) {
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
        //System.out.print("#Y=" + yes + " #N=" + no + " #U=" + un + " Py=");

        return (double)yesVotes/(double)sessions;
    }
}
