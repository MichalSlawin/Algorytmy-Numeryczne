package ug.protocols.equation;
import ug.protocols.agent.*;

public class PairsCount {
	private static int yy = 0;
	private static int nn = 0;
	private static int uu = 0;
	private static int yn = 0;
	private static int yu = 0;
	private static int nu = 0;
	private static int all = 0;
	
	public static void countPairs(int total, int y, int n) {
		yy = 0;
		nn = 0;
		uu = 0;
		yn = 0;
		yu = 0;
		nu = 0;
		all = 0;
		Agents agents = AgentManager.generateAgents(y, n, total - y - n);
		
		for(int i=0; i < total - 1; i++)
			for(int j = i + 1; j < total; j++) {
				if(agents.getTab()[i].getState() == Agent.State.Y && agents.getTab()[j].getState() == Agent.State.Y)
					yy++;
				else if(agents.getTab()[i].getState() == Agent.State.N && agents.getTab()[j].getState() == Agent.State.N)
					nn++;
				else if(agents.getTab()[i].getState() == Agent.State.U && agents.getTab()[j].getState() == Agent.State.U)
					uu++;
				else if(agents.getTab()[i].getState() == Agent.State.Y && agents.getTab()[j].getState() == Agent.State.N)
					yn++;
				else if(agents.getTab()[i].getState() == Agent.State.Y && agents.getTab()[j].getState() == Agent.State.U)
					yu++;
				else if(agents.getTab()[i].getState() == Agent.State.N && agents.getTab()[j].getState() == Agent.State.U)
					nu++;
				all++;
			}

		System.out.println("YY: " + yy + " NN: " + nn + " UU: " + uu + " YN: " + yn + " YU: " + yu + " NU: " + nu + " all: " + all);
	}

	public static int getYY() {
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
	}
}
