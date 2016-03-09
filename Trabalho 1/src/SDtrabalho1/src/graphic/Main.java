/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphic;

import bench.Bench;
import entities.Coach;
import entities.Contestant;
import entities.Referee;
import general_info_repo.Log;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import playground.Playground;
import referee_site.RefereeSite;

/**
 *
 * @author António Ferreira, 67405; Rodrigo Cunha, 67800
 */
public class Main extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        Button btn = new Button();
        btn.setText("Start");
        
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World!");
            }
        });
        StackPane root = new StackPane();
        
        // root.getChildren().add(btn);
        
        Scene scene = new Scene(root, 300, 250);
        
        primaryStage.setTitle("GAME OF THE ROPE");
        primaryStage.setScene(scene);
        primaryStage.show();
        
    }

    public static void startEntities(){
        /* START THE ENTITIES */
        int nCoaches = 2;
        String teams[] = {"A", "B"};
        int nContestants = 10;
        
        assert nCoaches == teams.length;
        
        Bench bench = new Bench(nContestants, nContestants);
        Playground playground = new Playground();
        RefereeSite referee_site = new RefereeSite();
        Log lg = new Log();
        
        Referee ref =  new Referee((playground.IReferee) playground, (bench.IReferee) bench, (referee_site.IReferee) referee_site, lg);
        ref.start();
        
        Coach [] coach =  new Coach [nCoaches];
        for(int i = 0; i<nCoaches; i++){
            coach[i] = new Coach((bench.ICoach) bench,(referee_site.ICoach) referee_site, i, teams[i], lg);
            coach[i].start();
        }
        
        Contestant [] contestants = new Contestant[nContestants];
        
        for(int i = 0; i<nContestants; i++){
            if(i < (nContestants/teams.length)){
                contestants[i] = new Contestant((playground.IContestant) playground,(bench.IContestant) bench, (referee_site.IContestant) referee_site, i, teams[0], lg);
            }else{
                contestants[i] = new Contestant((playground.IContestant) playground,(bench.IContestant) bench, (referee_site.IContestant) referee_site, i, teams[1], lg);
            }
            
            contestants[i].start();
        }
        
        for(int i = 0; i<nCoaches; i++){
            try {
                coach[i].join();
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
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
        startEntities();
    }
}
