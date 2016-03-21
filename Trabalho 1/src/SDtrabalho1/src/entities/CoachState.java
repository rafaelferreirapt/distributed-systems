package entities;

/**
 *
 * @author António Ferreira, 67405; Rodrigo Cunha, 67800
 */
public enum CoachState {

    /**
     * WAIT_FOR_REFEREE_COMMAND = WFRC
     */
    WAIT_FOR_REFEREE_COMMAND{
        @Override
        public String toString(){
            return "WFRC";
        }
    },

    /**
     * ASSEMBLE_TEAM = ASTM
     */
    ASSEMBLE_TEAM{
        @Override
        public String toString(){
            return "ASTM";
        }
    },

    /**
     * WATCH_TRIAL = WCTL
     */
    WATCH_TRIAL{
        @Override
        public String toString(){
            return "WCTL";
        }
    }
}
