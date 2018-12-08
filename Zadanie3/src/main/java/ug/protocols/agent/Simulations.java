package ug.protocols.agent;

import java.awt.Point;

public class Simulations {

    public static String simulateAllVotings(int agentsCount, int sessions) {
    	StringBuffer s = new StringBuffer();
    	s.append("[");
    	int size = 0;
    	for(int i = 1; i <= agentsCount+1; i++)
			size += i;
    	
    	int yCount = 0, nCount = 0;
    	for(int i = 0; i < size; i++) {
    		s.append(simulateVoting(yCount, nCount, agentsCount - yCount - nCount, sessions));
    		if(i != size - 1)
    			s.append(" ");
    		else s.append("]");
    		if(yCount != agentsCount) {
    			if(yCount + nCount == agentsCount) {
    				yCount++;
    				nCount = 0;
    			} else nCount++;
    		}
    	}		
    	return s.toString();
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
