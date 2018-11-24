package ug.protocols;

public class MainTest {
    private static final int AGENTS_NUMBER = 6;
    private static final int SESSIONS = 4;

    public static void main(String [] args) {
        System.out.println(simulateVotingTest());
    }

    private static double simulateVotingTest() {
        int yesVotes = 0;
        Agent agents[];
        Agent.State state;

        for(int i = 0; i < SESSIONS; i++) {
            System.out.println("\nVoting #" + i);

            agents = AgentManager.generateRandomAgents(AGENTS_NUMBER);
            state = AgentManager.simulateVoting(agents);

            System.out.println("Voting ended with result: " + state);
            if(state == Agent.State.Y) yesVotes++;
        }
        System.out.println("\n" + yesVotes + "/" + SESSIONS + " with result YES");

        return (double)yesVotes/(double)SESSIONS;
    }

}
