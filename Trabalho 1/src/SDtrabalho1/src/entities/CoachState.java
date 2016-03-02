/*
 * Distributed Systems
 * Rafael Ferreira and Rodrigo Cunha
 */
package entities;

/**
 *
 * @author António Ferreira, 67405; Rodrigo Cunha, 67800
 */
public enum CoachState {
    WAIT_FOR_REFEREE_COMMAND("WFRC"),
    ASSEMBLE_TEAM("AT"),
    WATCH_TRIAL("WT");
    
    private final String state;
    private CoachState(String state){
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
