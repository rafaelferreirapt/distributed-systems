/*
 * Distributed Systems
 * Rafael Ferreira and Rodrigo Cunha
 */
package general_info_repo;

/**
 * Trial instance, only will have the centre of the rope.
 * @author António Ferreira, 67405; Rodrigo Cunha, 67800
 */
public class Trial {
    
    private int centre_of_the_rope;
    
    /**
     * Trial constructor.
     * @param centre_of_the_rope the center.
     */
    public Trial(int centre_of_the_rope){
        this.centre_of_the_rope = centre_of_the_rope;
    }
    
    /**
     * Update the center of the rope with the strength given by the team contestant
     * If is "A" it makes -strength, if is "B" it makes +strength.
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
     * Rope position.
     * @return
     */
    public int getRope(){
        return this.centre_of_the_rope;
    }
}
