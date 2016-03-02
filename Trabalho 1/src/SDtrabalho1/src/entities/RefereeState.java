/*
 * Distributed Systems
 * Rafael Ferreira and Rodrigo Cunha
 */
package entities;

/**
 *
 * @author António Ferreira, 67405; Rodrigo Cunha, 67800
 */
public enum RefereeState {
    START_OF_THE_MATCH("SOFTM"),
    START_OF_A_GAME("SOFAG"),
    TEAMS_READY("TR"),
    WAIT_FOR_TRIAL_CONCLUSION("WFTC"),
    END_OF_A_GAME("EOFAG"),
    END_OF_THE_MATCH("EOTM");
    
    
    private final String state;
    private RefereeState(String state){
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
