package ug.protocols.equation;
import ug.protocols.agent.*;

public class PairsCount {
	private static int intoYY = 0;
	private static int intoNN = 0;
	private static int intoUU = 0;
	/*private static int yn = 0;
	private static int yu = 0;
	private static int nu = 0;*/
	private static int all = 0;
	
	public static void countPairs(int total, int y, int n) {
		intoYY = 0;
		intoNN = 0;
		intoUU = 0;
		/*yn = 0;
		yu = 0;
		nu = 0;*/
		all = 0;
		Agents agents = AgentManager.generateAgents(y, n, total - y - n);
		
		for(int i=0; i < total - 1; i++)
			for(int j = i + 1; j < total; j++) {
				if(agents.getTab()[i].getState() == Agent.State.Y && agents.getTab()[j].getState() == Agent.State.Y)
					intoYY++;
				else if(agents.getTab()[i].getState() == Agent.State.N && agents.getTab()[j].getState() == Agent.State.N)
					intoNN++;
				else if(agents.getTab()[i].getState() == Agent.State.U && agents.getTab()[j].getState() == Agent.State.U)
					intoUU++;
				else if(agents.getTab()[i].getState() == Agent.State.Y && agents.getTab()[j].getState() == Agent.State.N)
					intoUU++;
				else if(agents.getTab()[i].getState() == Agent.State.Y && agents.getTab()[j].getState() == Agent.State.U)
					intoYY++;
				else if(agents.getTab()[i].getState() == Agent.State.N && agents.getTab()[j].getState() == Agent.State.U)
					intoNN++;
				all++;
			}

		System.out.println("YY: " + intoYY + "/" + all + " NN: " + intoNN + "/" + all + " UU: " + intoUU + "/" + all);
	}

	/*public static int getYY() {
		return yy;
	}

	public static int getNN() {
		return nn;
	}

	public static int getUU() {
		return uu;
	}

	public static int getYN() {
		return yn;
	}

	public static int getYU() {
		return yu;
	}

	public static int getNU() {
		return nu;
	}
	
	public static int getAll() {
		return all;
	}*/
}
