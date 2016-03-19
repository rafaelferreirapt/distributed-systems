/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package general_info_repo;

import java.util.Objects;

/**
 *
 * @author António Ferreira, 67405; Rodrigo Cunha, 67800
 */
public class ContestantTeam{

    private final String team;
    private final int contestant;

    public ContestantTeam(String team, int contestant){
        this.team = team;
        this.contestant = contestant;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + Objects.hashCode(this.team);
        hash = 83 * hash + this.contestant;
        return hash;
    }
    
    @Override public boolean equals(Object b){
        if(b==null){
            return false;
        }
        if(getClass()!=b.getClass()){
            return false;
        }
        return this.team.equals(((ContestantTeam)b).team) && 
                this.contestant == ((ContestantTeam)b).contestant;
    }
}
