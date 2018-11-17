package ug.protocols;

public class MainTest {

    public static void main(String [] args) {
        Agent agent1 = new Agent(Agent.State.U);
        Agent agent2 = new Agent(Agent.State.N);

        AgentManager.setAgentsStates(agent1, agent2);

        System.out.println(agent1 + " , " + agent2);
    }

}
