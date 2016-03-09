/*
 * Distributed Systems
 * Rafael Ferreira and Rodrigo Cunha
 */
package graphic;

import bench.Bench;
import entities.Coach;
import entities.Contestant;
import entities.Referee;
import general_info_repo.Log;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import playground.Playground;
import referee_site.RefereeSite;

/**
 *
 * @author António Ferreira, 67405; Rodrigo Cunha, 67800
 */
public class Game extends Thread{

    private static Bench bench;
    private static Playground playground;
    private static RefereeSite referee_site;
    private static Log lg;
    private static Coach [] coachs;
    private static Contestant [] contestants;
    private static Referee ref;
    
    private static GridPane table_team;
    
    
    
    public Game(GridPane table_team){
        this.table_team = table_team;
    }
    
    @Override
    public void run() {
        /* START THE ENTITIES */
        int nCoaches = 2;
        String teams[] = {"A", "B"};
        int nContestants = 10;
        
        assert nCoaches == teams.length;
        
        bench = new Bench(nContestants, nContestants);
        playground = new Playground();
        referee_site = new RefereeSite();
        lg = new Log();
        
        ref =  new Referee((playground.IReferee) playground, (bench.IReferee) bench, (referee_site.IReferee) referee_site, lg);
        //ref.start();
        
        coachs =  new Coach [nCoaches];
        for(int i = 0; i<nCoaches; i++){
            coachs[i] = new Coach((bench.ICoach) bench,(referee_site.ICoach) referee_site, i, teams[i], lg);
            //coachs[i].start();
        }
        
        contestants = new Contestant[nContestants];
        
        for(int i = 0; i<nContestants; i++){
            if(i < (nContestants/teams.length)){
                contestants[i] = new Contestant((playground.IContestant) playground,(bench.IContestant) bench, (referee_site.IContestant) referee_site, i, teams[0], lg);
            }else{
                contestants[i] = new Contestant((playground.IContestant) playground,(bench.IContestant) bench, (referee_site.IContestant) referee_site, i, teams[1], lg);
            }
            //contestants[i].start();
        }
        
        refresh_info();
            
        for(int i = 0; i<nCoaches; i++){
            try {
                coachs[i].join();
            } catch (InterruptedException ex) {
                //Escrever para o log
            }
        }
        
        for(int i = 0; i<nContestants; i++){
            try {
                contestants[i].join();
            } catch (InterruptedException ex) {
                //Escrever para o log
            }
        }
        
        try {
            ref.join();
        } catch (InterruptedException ex) {
            //Escrever para o log
        }
    }
    
    public static void refresh_info(){
        for(int i=0; i<contestants.length; i++){
            if(i<5){
                // team A
                // cell ID
                Text id = (Text)getNodeByRowColumnIndex(i+1, 0);
                // cell state
                Text state = (Text)getNodeByRowColumnIndex(i+1, 0);
                // cell running
                Text running = (Text)getNodeByRowColumnIndex(i+1, 0);
            }else{
                // team B
                // cell ID
                
                // cell state
                
                // cell running
                
            }
        }
        for(Contestant c : contestants){
            c.isInterrupted();
            
        }
    }
    
    public static Node getNodeByRowColumnIndex(final int row,final int column) {
        Node result = null;
        ObservableList<Node> childrens = table_team.getChildren();
        
        for(Node node : childrens) {
            if(table_team.getRowIndex(node) == row && table_team.getColumnIndex(node) == column) {
                result = node;
                break;
            }
        }
        return result;
    }
    
}