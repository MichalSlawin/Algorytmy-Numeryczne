package ug.protocols.agent;

import java.util.Random;

public class AgentManager {

    private static void setAgentsStates(Agent agent1, Agent agent2) {
        if(agent1.getState() != agent2.getState()) {
            if(agent1.getState() == Agent.State.U)
                agent1.setState(agent2.getState());
            else if(agent2.getState() == Agent.State.U)
                agent2.setState(agent1.getState());
            else {
                agent1.setState(Agent.State.U);
                agent2.setState(Agent.State.U);
            }
        }
    }

    public static Agents generateRandomAgents(int howMany) {
        Random generator = new Random();
        Agent[] agentsArray = new Agent[howMany];
        int randomInt;

        for(int i = 0; i <howMany; i++) {
            randomInt = generator.nextInt(3);

            if(randomInt == 0) agentsArray[i] = new Agent(Agent.State.Y);
            if(randomInt == 1) agentsArray[i] = new Agent(Agent.State.N);
            if(randomInt == 2) agentsArray[i] = new Agent(Agent.State.U);
        }

        return new Agents(agentsArray);
    }

    public static Agents generateAgents(int yes, int no, int un) {

        int howMany = yes + no + un;
        Agent[] agentsArray = new Agent[howMany];

        for(int i = 0; i < howMany; i++) {
            if(i < yes) agentsArray[i] = new Agent(Agent.State.Y);
            else if(i < yes+no) agentsArray[i] = new Agent(Agent.State.N);
            else agentsArray[i] = new Agent(Agent.State.U);
        }

        return new Agents(agentsArray);
    }

    // RETURNS: wynik glosowania (Y/N/U)
    public static Agent.State simulateVoting(Agents agents) {
        Random generator = new Random();
        int agent1Index;
        int agent2Index;

        //agents.printAgentsSummary();
        while(!agents.isVotingFinished()) {
            agent1Index = generator.nextInt(agents.getLength());
            agent2Index = generator.nextInt(agents.getLength());

            //System.out.print(agents.getAgent(agent1Index) + " - " + agents.getAgent(agent2Index) + " => ");
            setAgentsStates(agents.getAgent(agent1Index), agents.getAgent(agent2Index));
            //System.out.print(agents.getAgent(agent1Index) + " - " + agents.getAgent(agent2Index) + "\n");
        }
        //agents.printAgentsSummary();

        return agents.getAgent(0).getState();
    }

}
