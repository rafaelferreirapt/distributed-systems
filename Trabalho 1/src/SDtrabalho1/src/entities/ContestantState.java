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
public enum ContestantState {
    SEAT_AT_THE_BENCH{
        @Override
        public String toString(){
            return "SAB";
        }
    }, STAND_IN_POSITION{
        @Override
        public String toString(){
            return "SIP";
        }
    }, DO_YOUR_BEST{
         @Override
        public String toString(){
            return "DYB";
        }
    }
}