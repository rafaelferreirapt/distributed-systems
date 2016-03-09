/*
 * Distributed Systems
 * Rafael Ferreira and Rodrigo Cunha
 */
package playground;

/**
 *
 * @author António Ferreira, 67405; Rodrigo Cunha, 67800
 */
public class Trial {
    
    private int number;
    
    private int[] positionsA;
    private int[] positionsB;
    
    private int idxA = 0, idxB = 0;
    
    private int centre_of_the_rope;
    
    public Trial(int number, int centre_of_the_rope){
        this.number = number;
        
        this.positionsA = new int[3];
        this.positionsB = new int[3];
        
        this.centre_of_the_rope = centre_of_the_rope;
    }
    
    public void setPosition(int ContestantID, String team){
        if(team.equals("A")){
            this.positionsA[idxA++] = ContestantID;
        }else if(team.equals("B")){
            this.positionsB[idxB++] = ContestantID;
        }
    }
    
}
