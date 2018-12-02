package ug.protocols.agent;

public class Agent {
   public enum State {
       Y, N, U
   }
   private static long idCounter = 0;

   private State state;
   private long id;

   Agent(State state) {
       this.state = state;
       id = idCounter;
       idCounter++;
   }

    State getState() {
        return state;
    }

    void setState(State state) {
        this.state = state;
    }

    @Override
    public String toString() {
       return id + ":" + state;
    }
}
