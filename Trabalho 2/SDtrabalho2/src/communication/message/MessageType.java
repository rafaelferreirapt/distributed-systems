/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package communication.message;

/**
 *
 * @author António Ferreira, 67405; Rodrigo Cunha, 67800
 */
public enum MessageType {
    callTrial, assertTrialDecision, wakeUp, 
    reviewNotes, callContestants, waitForCallTrial,
    waitForAssertTrialDecision, waitForFollowCoachAdvice,
    followCoachAdvice, seatDown, waitForCallContestants, ACK,
    annouceNewGame, declareGameWinner, declareMatchWinner,
    endOfMatch, waitForInformReferee, waitForAmDone,
    waitAllPositioned, informReferee, amDone,
    positioned
}
