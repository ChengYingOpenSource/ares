package com.cy.cuirass.raft.event;
import java.io.Serializable;



public class ActionEvent implements Serializable{
    
    private static final long serialVersionUID = 1700181989068201205L;
    
    private ActionType type;
    
    public ActionEvent(){}
    
    public ActionEvent(ActionType type){
        this.type = type;
    }

    public ActionType getType() {
        return type;
    }

    public ActionEvent setType(ActionType type) {
        this.type = type;
        return this;
    }
    
    
    
    
}
