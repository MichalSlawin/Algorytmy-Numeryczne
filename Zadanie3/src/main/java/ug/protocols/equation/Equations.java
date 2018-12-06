package ug.protocols.equation;

import java.util.HashMap;
import java.util.Map;

public class Equations {
    private int[] statesTable = new int[4];	//statesTable[1] = Y ,statesTable[2] = N , statesTable[3] = U
    private Map<IntPair, Double> equations = new HashMap<>();
    private int agentsNumber;

    public Equations(int agentsNumber) {
        this.agentsNumber = agentsNumber;
    }

    public void generateEmptyEquations() {
        generateEmptyEquationsBody(0);
    }

    private void generateEmptyEquationsBody(int x) {

        if(3 == x) {
            if(statesTable[1]+ statesTable[2]+ statesTable[3]==agentsNumber) {
                equations.put(new IntPair(statesTable[1], statesTable[2]), 0.0);
            }
        } else {
            for(int i = 0; i <= agentsNumber; i++) {
                statesTable[x + 1] = i;
                generateEmptyEquationsBody(x + 1);
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder equationsString = new StringBuilder();
        for(Map.Entry<IntPair, Double> entry : equations.entrySet()) {
            equationsString.append(entry.getKey());
            equationsString.append(" : ");
            equationsString.append(entry.getValue());
            equationsString.append("\n");
        }
        return equationsString.toString();
    }
}
