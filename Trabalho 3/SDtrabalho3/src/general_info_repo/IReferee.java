/*
 * Distributed Systems
 * Rafael Ferreira and Rodrigo Cunha
 */
package general_info_repo;

import entities.RefereeState;

/**
 * 
 * @author António Ferreira, 67405; Rodrigo Cunha, 67800
 */
public interface IReferee {
    
    /**
     *
     * @param state
     */
    public void initRefereeState(RefereeState state);
    
    /**
     *
     */
    public void newGame();
    
    /**
     *
     */
    public void newTrial();
    
    /**
     *
     * @return
     */
    public int assertTrialDecision();
    
    /**
     *
     * @return
     */
    public int getNumberOfGames();
    
    /**
     *
     * @return
     */
    public int getTotalNumberOfGames();
    
    /**
     *
     */
    public void printGameWinner();
    
    /**
     *
     * @param gameNumber
     */
    public void newGame(int gameNumber);
    
    /**
     *
     * @param state
     */
    public void setRefereeState(RefereeState state);
    
    /**
     *
     */
    public void declareMatchWinner();
}
