/*
 * Distributed Systems
 * Rafael Ferreira and Rodrigo Cunha
 */
package entities;

import general_info_repo.Log;

/**
 *
 * @author António Ferreira, 67405; Rodrigo Cunha, 67800
 */
public class Coach extends Thread {
    
    private CoachState state;
    private final int id;
    private final Log log;
    private final String team;
    private final bench.ICoach bench;
    private final referee_site.ICoach referee_site;
    
    public Coach(bench.ICoach b, referee_site.ICoach r, int id, String team, Log log){
        this.bench = b;
        this.referee_site = r;
        this.team = team;
        this.setName("Coach " + id + " of the team " + team);
        this.id = id;
        this.log = log;
        state = CoachState.WAIT_FOR_REFEREE_COMMAND;
    }
    
    /**
     * This function represents the life cycle of Coach.
     */
    
    @Override
    public void run(){
        while(!referee_site.endOfMatch()){
            if(this.state.getState().equals(CoachState.ASSEMBLE_TEAM.getState())){
                this.bench.callContestants(this.team);
                this.bench.waitForFollowCoachAdvice(this.team);
                System.err.println("vou informar " + this.team);
                this.referee_site.informReferee(this.team);
                this.setState(CoachState.WATCH_TRIAL);
            }else if(this.state.getState().equals(CoachState.WAIT_FOR_REFEREE_COMMAND.getState())){
                this.bench.waitForCallTrial();
                if(this.referee_site.endOfMatch()){
                    break;
                }
                this.setState(CoachState.ASSEMBLE_TEAM);
            }else if(this.state.getState().equals(CoachState.WATCH_TRIAL.getState())){
                this.bench.waitForAssertTrialDecision();

                this.setState(CoachState.WAIT_FOR_REFEREE_COMMAND);
            }
        }
    }
    
    public void setState(CoachState state){
        this.state = state;
    }
    
    public CoachState getCurrentState(){
        return this.state;
    }
}
