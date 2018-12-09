package ug.protocols.agent;

import ug.protocols.matrix.MyMatrix;

public class Simulations {

    public static MyMatrix simulateAllVotings(int agentsCount, int sessions) {
    	int size = 0;
    	for(int i = 1; i <= agentsCount+1; i++)
			size += i;
		MyMatrix vector = new MyMatrix(size,1);

    	int yCount = 0, nCount = 0;
    	for(int i = 0; i < size; i++) {
    		vector.setCell(simulateVoting(yCount, nCount, agentsCount - yCount - nCount, sessions), i, 0);
    		if(yCount != agentsCount) {
    			if(yCount + nCount == agentsCount) {
    				yCount++;
    				nCount = 0;
    			} else nCount++;
    		}
    	}		
    	return vector;
    }

    public static double simulateVoting(int yes, int no, int un, int sessions) {
        int yesVotes = 0;
        Agents agents;
        Agent.State state;

        for(int i = 0; i < sessions; i++) {

            agents = AgentManager.generateAgents(yes, no, un);
            state = AgentManager.simulateVoting(agents);

            if(state == Agent.State.Y) yesVotes++;
        }

        return (double)yesVotes/(double)sessions;
    }
}
