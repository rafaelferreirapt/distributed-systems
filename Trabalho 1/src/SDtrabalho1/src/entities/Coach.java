/*
 * Distributed Systems
 * Rafael Ferreira and Rodrigo Cunha
 */
package entities;

import general_info_repo.Log;

/**
 * Coach instance.
 * @author António Ferreira, 67405; Rodrigo Cunha, 67800
 */
public class Coach extends Thread {
    
    private CoachState state;
    
    private final Log log;
    private final String team;
    private final bench.ICoach bench;
    private final referee_site.ICoach referee_site;
    
    /**
     * It will be passed to the Coach the methods of the bench and referee site
     * that the coach have acess. The team is the coach team, very important to know
     * the identity of the coach.
     * @param b Instance that implements bench coach methods.
     * @param r Instance that implements referee site coach methods.
     * @param team Team identifier, can be A or B.
     */
    public Coach(bench.ICoach b, referee_site.ICoach r, String team){
        this.bench = b;
        this.referee_site = r;
        this.log = Log.getInstance();
        
        this.team = team;
        
        this.setName("Coach of the team " + team);
        state = CoachState.WAIT_FOR_REFEREE_COMMAND;
        
        this.log.initCoachState(team, state);
    }
    
    /**
     * This function represents the life cycle of Coach.
     */
    @Override
    public void run(){
        while(!referee_site.endOfMatch()){
            switch(this.state){
                case ASSEMBLE_TEAM:
                    this.bench.waitForFollowCoachAdvice(this.team);
                    this.referee_site.informReferee(this.team);
                    this.state = CoachState.WATCH_TRIAL;
                    break;
                case WAIT_FOR_REFEREE_COMMAND:
                    this.bench.waitForCallTrial();

                    if(this.referee_site.endOfMatch()){
                        break;
                    }

                    this.bench.callContestants(this.team);
                    this.state = CoachState.ASSEMBLE_TEAM;
                    break;
                case WATCH_TRIAL:
                    this.bench.waitForAssertTrialDecision();
                    this.bench.reviewNotes(team);
                    
                    this.log.refreshStrengths(this.team);
                    
                    this.state = CoachState.WAIT_FOR_REFEREE_COMMAND;
                    break;
            }
            if(!referee_site.endOfMatch()){
                this.log.setCoachState(team, state);
            }
        }
    }
}
