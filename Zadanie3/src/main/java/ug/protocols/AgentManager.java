package ug.protocols;

import java.util.Random;

class AgentManager {
    static void setAgentsStates(Agent agent1, Agent agent2) {
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

    static Agent[] generateRandomAgents(int howMany) {
        Random generator = new Random();
        Agent[] agents = new Agent[howMany];
        int randomInt;

        for(int i = 0; i <howMany; i++) {
            randomInt = generator.nextInt(3);

            if(randomInt == 0) agents[i] = new Agent(Agent.State.Y);
            if(randomInt == 1) agents[i] = new Agent(Agent.State.N);
            if(randomInt == 2) agents[i] = new Agent(Agent.State.U);
        }

        return agents;
    }

    static int simulateVoting(Agent[] agents) {
        Random generator = new Random();
        int steps = 0;
        int agent1Index;
        int agent2Index;

        printAgentsSummary(agents);
        while(!isVotingFinished(agents)) {
            agent1Index = generator.nextInt(agents.length);
            agent2Index = generator.nextInt(agents.length);

            System.out.print(agents[agent1Index] + " - " + agents[agent2Index] + " => ");
            setAgentsStates(agents[agent1Index], agents[agent2Index]);
            System.out.print(agents[agent1Index] + " - " + agents[agent2Index] + "\n");

            steps++;
        }
        printAgentsSummary(agents);
        return steps;
    }

    private static boolean isVotingFinished(Agent[] agents) {
        int yesVotes = 0;
        int noVotes = 0;

        for(Agent agent : agents) {
            if(agent.getState() == Agent.State.Y) yesVotes++;
            if(agent.getState() == Agent.State.N) noVotes++;
        }
        return (yesVotes == agents.length || noVotes == agents.length);
    }

    private static void printAgentsSummary(Agent[] agents) {
        int yesVotes = 0;
        int noVotes = 0;
        int unVotes = 0;

        for(Agent agent : agents) {
            if(agent.getState() == Agent.State.Y) yesVotes++;
            if(agent.getState() == Agent.State.N) noVotes++;
            if(agent.getState() == Agent.State.U) unVotes++;
        }
        System.out.println("Summary: " + yesVotes + "Y ; " + noVotes + "N ; " + unVotes + "U");
    }
}
