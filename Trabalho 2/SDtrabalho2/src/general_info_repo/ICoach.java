/*
 * Distributed Systems
 * Rafael Ferreira and Rodrigo Cunha
 */
package general_info_repo;

import entities.CoachState;

/**
 * 
 * @author António Ferreira, 67405; Rodrigo Cunha, 67800
 */
public interface ICoach {
    
    /**
     *
     * @param team
     * @param state
     */
    public void initCoachState(String team, CoachState state);
    
    /**
     *
     * @param team
     */
    public void refreshStrengths(String team);
    
    /**
     *
     * @param team
     * @param state
     */
    public void setCoachState(String team, CoachState state);
}
