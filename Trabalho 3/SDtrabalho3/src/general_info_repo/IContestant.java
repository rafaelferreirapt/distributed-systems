/*
 * Distributed Systems
 * Rafael Ferreira and Rodrigo Cunha
 */
package general_info_repo;

import entities.ContestantState;

/**
 * 
 * @author António Ferreira, 67405; Rodrigo Cunha, 67800
 */
public interface IContestant {
    
    /**
     *
     * @param state
     * @param team
     * @param contestant
     */
    public void initContestant(ContestantState state, String team, int contestant);

    /**
     *
     * @param team
     * @param contestant
     */
    public void setContestantLastTrial(String team, int contestant);
    
    /**
     *
     * @param team
     * @param contestant
     */
    public void removePosition(String team, int contestant);
    
    /**
     *
     * @param team
     * @param contestant
     */
    public void setPosition(String team, int contestant);
    
    /**
     *
     * @param state
     * @param team
     * @param contestant
     */
    public void setContestantState(ContestantState state, String team, int contestant);
}
