package com.cy.cuirass.raft.event;

import java.io.Serializable;

import com.cy.cuirass.raft.state.NodeState;

public class StateChangedEvent implements Serializable{
    
    
    private static final long serialVersionUID = 8878988456093631082L;
    
    private NodeState will;
    
    public StateChangedEvent(){}
    
    public StateChangedEvent(NodeState will){
        this.will = will;
    }
    
    public NodeState getWill() {
        return will;
    }

    public StateChangedEvent setWill(NodeState will) {
        this.will = will;
        return this;
    }
}
