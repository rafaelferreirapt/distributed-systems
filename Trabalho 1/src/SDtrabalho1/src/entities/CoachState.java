/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

/**
 *
 * @author António Ferreira, 67405; Rodrigo Cunha, 67800
 */
public enum CoachState {
    WAIT_FOR_REFEREE_COMMAND{
        @Override
        public String toString(){
            return "WFRC";
        }
    }, ASSEMBLE_TEAM{
        @Override
        public String toString(){
            return "ASTM";
        }
    }, WATCH_TRIAL{
        @Override
        public String toString(){
            return "WCTL";
        }
    }
}
