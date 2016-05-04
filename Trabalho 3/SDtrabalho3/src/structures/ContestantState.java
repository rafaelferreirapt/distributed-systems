/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package structures;

/**
 * Contestant state of the contestant.
 * @author António Ferreira, 67405; Rodrigo Cunha, 67800
 */
public enum ContestantState {

    /**
     * SEAT_AT_THE_BENCH = SAB
     */
    SEAT_AT_THE_BENCH{
        @Override
        public String toString(){
            return "SAB";
        }
    },

    /**
     * STAND_IN_POSITION = SIP
     */
    STAND_IN_POSITION{
        @Override
        public String toString(){
            return "SIP";
        }
    },

    /**
     * DO_YOUR_BEST = DYB
     */
    DO_YOUR_BEST{
         @Override
        public String toString(){
            return "DYB";
        }
    }
}
