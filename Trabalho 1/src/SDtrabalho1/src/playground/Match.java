/*
 * Distributed Systems
 * Rafael Ferreira and Rodrigo Cunha
 */
package playground;

/**
 *
 * @author António Ferreira, 67405; Rodrigo Cunha, 67800
 */
public class Match {
    
    private Game[] games;
    private int game = 0;
    
    public int trials_played = 0;
    
    private static Match instance = null;
    
    protected Match() {
        this.games = new Game[100000];
    }
    public static Match getInstance() {
       if(instance == null) {
          instance = new Match();
       }
       return instance;
    }
    
    public void newGame(){
        assert game < this.games.length;
        
        this.games[game] = new Game(game++);
    }
    
    public void newTrial(int centre_of_the_rope){
        this.games[(game-1)].newTrial(centre_of_the_rope);
        this.trials_played++;
    }
    
    public void setPontuation(int pontuation, String team){
        this.games[(game-1)].setPontuation(pontuation, team);
    }
    
    public int getPontuation(String team){
        return this.games[(game-1)].getPontuation(team);
    }
    
    public int gameNumberOfTrials(){
        return this.games[(game-1)].gameNumberOfTrials();
    }
    
    public int getNumberOfGames(){
        return this.game;
    }
    
}
