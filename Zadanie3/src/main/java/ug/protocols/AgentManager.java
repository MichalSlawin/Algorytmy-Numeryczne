package ug.protocols;

class AgentManager {
    static void setAgentsStates(Agent agent1, Agent agent2) {

        if(agent1.getState() != agent2.getState()) {
            if(agent1.getState() == Agent.State.U)
                agent1.setState(agent2.getState());
            else if(agent2.getState() == Agent.State.U)
                agent2.setState(agent1.getState());
        }

    }
}
