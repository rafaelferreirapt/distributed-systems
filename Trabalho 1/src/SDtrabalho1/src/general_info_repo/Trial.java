/*
 * Distributed Systems
 * Rafael Ferreira and Rodrigo Cunha
 */
package general_info_repo;

/**
 *
 * @author António Ferreira, 67405; Rodrigo Cunha, 67800
 */
public class Trial {
    
    private int number;
    private int centre_of_the_rope;
    
    /**
     *
     * @param number
     * @param centre_of_the_rope
     */
    public Trial(int number, int centre_of_the_rope){
        this.number = number;    
        this.centre_of_the_rope = centre_of_the_rope;
    }
    
    /**
     *
     * @param team
     * @param strength
     */
    public void updateRope(String team, int strength){
        if(team.equals("A")){
            this.centre_of_the_rope -= strength;
        }else if(team.equals("B")){
            this.centre_of_the_rope += strength;
        }
    }
    
    /**
     *
     * @return
     */
    public int getRope(){
        return this.centre_of_the_rope;
    }
}
