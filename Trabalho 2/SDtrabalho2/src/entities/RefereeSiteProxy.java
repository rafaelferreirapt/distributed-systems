/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import communication.message.Message;
import communication.message.MessageType;
import communication.message.WrapperMessage;
import communication.proxy.ClientProxyWrapper;
import referee_site.ICoach;
import referee_site.IContestant;
import referee_site.IReferee;
import settings.NodeSetts;

/**
 *
 * @author António Ferreira, 67405; Rodrigo Cunha, 67800
 */
public class RefereeSiteProxy implements IReferee, ICoach, IContestant{

    private final String SERVER_HOST = NodeSetts.SERVER_HOSTS.get("refereeSite");
    private final int SERVER_PORT = NodeSetts.SERVER_PORTS.get("refereeSite");
    
    private WrapperMessage communicate(Message m){
        return ClientProxyWrapper.connect(SERVER_HOST,  SERVER_PORT, m);
    }
    
    @Override
    public void annouceNewGame() {
        communicate(new Message(MessageType.annouceNewGame));
    }

    @Override
    public void declareGameWinner() {
        communicate(new Message(MessageType.declareGameWinner));
    }

    @Override
    public void declareMatchWinner() {
        communicate(new Message(MessageType.declareMatchWinner));
    }

    @Override
    public boolean endOfMatch() {
        WrapperMessage result = communicate(new Message(MessageType.endOfMatch));
        return result.getMessage().getEndOfMatch();
    }

    @Override
    public void waitForInformReferee() {
        communicate(new Message(MessageType.waitForInformReferee));
    }

    @Override
    public void waitForAmDone() {
        communicate(new Message(MessageType.waitForAmDone));
    }

    @Override
    public void waitAllPositioned() {
        communicate(new Message(MessageType.waitAllPositioned));
    }

    @Override
    public void informReferee(String team) {
        communicate(new Message(MessageType.informReferee, team));
    }

    @Override
    public void amDone() {
        communicate(new Message(MessageType.amDone));
    }

    @Override
    public void positioned() {
        communicate(new Message(MessageType.positioned));
    }
}
