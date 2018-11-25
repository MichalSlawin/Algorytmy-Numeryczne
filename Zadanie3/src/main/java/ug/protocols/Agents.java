package ug.protocols;

// TODO: Nie wiem czy nie bedzie lepiej przeniesc tutaj wszystkie metody dla Agent[] i dzialac na obiektach tej klasy

class Agents {
    private Agent[] agents;

    Agents(Agent[] agents) {
        this.agents = agents;
    }

    private int howManyAgents(Agent.State state) {
        int counter = 0;
        for(Agent agent : agents) {
            if(agent.getState() == state) counter++;
        }
        return counter;
    }

    boolean isVotingFinished() {
        int yesVotes = howManyAgents(Agent.State.Y);
        int noVotes = howManyAgents(Agent.State.N);
        int unVotes = howManyAgents(Agent.State.U);

        return (yesVotes == agents.length || noVotes == agents.length || unVotes == agents.length);
    }

    void printAgentsSummary() {
        int yesVotes = howManyAgents(Agent.State.Y);
        int noVotes = howManyAgents(Agent.State.N);
        int unVotes = howManyAgents(Agent.State.U);

        System.out.println("Summary: " + yesVotes + "Y ; " + noVotes + "N ; " + unVotes + "U");
    }

    int getLength() {
        return agents.length;
    }

    Agent getAgent(int index) {
        return agents[index];
    }
}
