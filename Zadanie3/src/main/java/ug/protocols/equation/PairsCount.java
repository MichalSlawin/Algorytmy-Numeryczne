package ug.protocols.equation;
import ug.protocols.agent.*;

public class PairsCount {
	private static int intoY = 0;
	private static int intoN = 0;
	private static int intoU = 0;
	private static double intoYchance = 0;
	private static double intoNchance = 0;
	private static double intoUchance = 0;
	private static int all = 0;
	
	public PairsCount(int total, int y, int n) {
		Agents agents = AgentManager.generateAgents(y, n, total - y - n);
		intoY = 0;
		intoN = 0;
		intoU = 0;
		all = 0;
		
		for(int i=0; i < total - 1; i++)
			for(int j = i + 1; j < total; j++) {
				if(agents.getTab()[i].getState() == Agent.State.Y && agents.getTab()[j].getState() == Agent.State.N)
					intoU++;
				else if(agents.getTab()[i].getState() == Agent.State.Y && agents.getTab()[j].getState() == Agent.State.U)
					intoY++;
				else if(agents.getTab()[i].getState() == Agent.State.N && agents.getTab()[j].getState() == Agent.State.U)
					intoN++;
				else if(agents.getTab()[i].getState() == Agent.State.U && agents.getTab()[j].getState() == Agent.State.U)
					intoU++;
				else if(agents.getTab()[i].getState() == Agent.State.Y && agents.getTab()[j].getState() == Agent.State.Y)
					intoY++;
				else if(agents.getTab()[i].getState() == Agent.State.N && agents.getTab()[j].getState() == Agent.State.N)
					intoN++;
				all++;
			}
		intoYchance = (double) intoY / all;
		intoNchance = (double) intoN / all;
		intoUchance = (double) intoU / all;

		//System.out.println("Y: " + intoYchance + " N: " + intoNchance + " U: " + intoUchance);
	}

	public static double getIntoYchance() {
		return intoYchance;
	}

	public static double getIntoNchance() {
		return intoNchance;
	}

	public static double getIntoUchance() {
		return intoUchance;
	}
	
}
