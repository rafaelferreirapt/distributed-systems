/*
 * Distributed Systems
 * Rafael Ferreira and Rodrigo Cunha
 */
package playground;

/**-
 *
 * @author António Ferreira, 67405; Rodrigo Cunha, 67800
 */
public class Game {
    
    private int id;
    private String winner;
    private Trial[] trials;
    private int next_trial_idx = 0;
    private int[] pontuation = new int[2];
    
    public Game(int id){
        this.trials = new Trial[6];
        this.id = id;
    }
    
    public void newTrial(int centre_of_the_rope){
        this.trials[next_trial_idx] = new Trial(next_trial_idx++, centre_of_the_rope);
    }
    
    public void setPontuation(int pontuation, String team){
        if(team.equals("A")){
            this.pontuation[0] = pontuation;
        }else if(team.equals("B")){
            this.pontuation[1] = pontuation;
        }
    }
    
    public int getPontuation(String team){
        if(team.equals("A")){
            return this.pontuation[0];
        }else{
            return this.pontuation[1];
        }
    }
    
    public void setPosition(int ContestantID, String team){
        this.trials[this.next_trial_idx-1].setPosition(ContestantID, team);
    }
    
    public int gameNumberOfTrials(){
        return this.next_trial_idx-1;
    }
}
