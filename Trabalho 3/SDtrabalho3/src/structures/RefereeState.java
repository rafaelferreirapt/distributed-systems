/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package structures;

/**
 * Referee state for the Referee.
 * @author António Ferreira, 67405; Rodrigo Cunha, 67800
 */
public enum RefereeState {

    /**
     * START_OF_THE_MATCH = SOM
     */
    START_OF_THE_MATCH{
        @Override
        public String toString(){
            return "SOM";
        }
    },

    /**
     * START_OF_A_GAME = SOF
     */
    START_OF_A_GAME{
        @Override
        public String toString(){
            return "SOF";
        }
    },

    /**
     * TEAMS_READY = TRY
     */
    TEAMS_READY{
        @Override
        public String toString(){
            return "TRY";
        }
    },

    /**
     * WAIT_FOR_TRIAL_CONCLUSION = WTC
     */
    WAIT_FOR_TRIAL_CONCLUSION{
        @Override
        public String toString(){
            return "WTC";
        }
    },

    /**
     * END_OF_A_GAME = EOG
     */
    END_OF_A_GAME{
        @Override
        public String toString(){
            return "EOG";
        }
    },

    /**
     * END_OF_THE_MATCH = EOM
     */
    END_OF_THE_MATCH{
        @Override
        public String toString(){
            return "EOM";
        }
    }
}
