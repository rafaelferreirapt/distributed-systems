/*
 * Distributed Systems
 * Rafael Ferreira and Rodrigo Cunha
 */
package main;

/**
 * FXML Controller class
 *
 * @author António Ferreira, 67405; Rodrigo Cunha, 67800
 */
public class Main{
    static MatchThread match;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        match = new MatchThread();
        match.start();
    }
}
