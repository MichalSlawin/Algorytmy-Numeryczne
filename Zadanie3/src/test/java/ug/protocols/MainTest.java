package ug.protocols;

public class MainTest {
    private static final int AGENTS_NUMBER = 6;

    public static void main(String [] args) {
        simulateVotingTest();
    }

    public static void simulateVotingTest() {
        Agent agents[] = AgentManager.generateRandomAgents(AGENTS_NUMBER);

        System.out.println("Voting ended in steps: " + AgentManager.simulateVoting(agents));
    }

}
