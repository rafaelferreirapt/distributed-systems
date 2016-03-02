/*
 * Distributed Systems
 * Rafael Ferreira and Rodrigo Cunha
 */
package entities;

/**
 *
 * @author António Ferreira, 67405; Rodrigo Cunha, 67800
 */
public enum ContestantState {
    SEAT_AT_THE_BENCH("SATB"),
    STAND_IN_POSITION("SIP"),
    DO_YOUR_BEST("DYB");
    
    private final String state;
    private ContestantState(String state){
        this.state = state;
    }

    public String getState(){
        return this.state;
    }

    @Override
    public String toString(){
        return this.name().replace("_", " ").toLowerCase();
    }
}
