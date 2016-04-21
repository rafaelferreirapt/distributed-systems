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

    @Override
    public void annouceNewGame() {
        ClientProxyWrapper.connect(NodeSetts.refereeSiteServerName, 
                NodeSetts.refereeSiteServerPort, new Message(MessageType.annouceNewGame));
    }

    @Override
    public void declareGameWinner() {
        ClientProxyWrapper.connect(NodeSetts.refereeSiteServerName, 
                NodeSetts.refereeSiteServerPort, new Message(MessageType.declareGameWinner));
    }

    @Override
    public void declareMatchWinner() {
        ClientProxyWrapper.connect(NodeSetts.refereeSiteServerName, 
                NodeSetts.refereeSiteServerPort, new Message(MessageType.declareMatchWinner));
    }

    @Override
    public boolean endOfMatch() {
        WrapperMessage result = ClientProxyWrapper.connect(NodeSetts.refereeSiteServerName, 
                    NodeSetts.refereeSiteServerPort, new Message(MessageType.endOfMatch));
        return result.getMessage().getEndOfMatch();
    }

    @Override
    public void waitForInformReferee() {
        ClientProxyWrapper.connect(NodeSetts.refereeSiteServerName, 
                NodeSetts.refereeSiteServerPort, new Message(MessageType.waitForInformReferee));
    }

    @Override
    public void waitForAmDone() {
        ClientProxyWrapper.connect(NodeSetts.refereeSiteServerName, 
                NodeSetts.refereeSiteServerPort, new Message(MessageType.waitForAmDone));
    }

    @Override
    public void waitAllPositioned() {
        ClientProxyWrapper.connect(NodeSetts.refereeSiteServerName, 
                NodeSetts.refereeSiteServerPort, new Message(MessageType.waitAllPositioned));
    }

    @Override
    public void informReferee(String team) {
        ClientProxyWrapper.connect(NodeSetts.refereeSiteServerName, 
                NodeSetts.refereeSiteServerPort, new Message(MessageType.informReferee, team));
    }

    @Override
    public void amDone() {
        ClientProxyWrapper.connect(NodeSetts.refereeSiteServerName, 
                NodeSetts.refereeSiteServerPort, new Message(MessageType.amDone));
    }

    @Override
    public void positioned() {
        ClientProxyWrapper.connect(NodeSetts.refereeSiteServerName, 
                NodeSetts.refereeSiteServerPort, new Message(MessageType.positioned));
    }
}
