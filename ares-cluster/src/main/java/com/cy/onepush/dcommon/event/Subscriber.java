package com.cy.onepush.dcommon.event;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import lombok.EqualsAndHashCode;
import lombok.Getter;


@Getter
@EqualsAndHashCode
public abstract class Subscriber {
    
    public static final String Default = "default";
    
    private String namespace;
    
    private Set<String> eventName = new HashSet<>();
    
    public abstract MEvent action(MEvent event);
    
    public Subscriber(){
        this.namespace = Default;
    }
    
    public Subscriber(String namespace,List<String> event){
        this.namespace = StringUtils.isBlank(namespace)?Default:namespace;
        for(String e:event){
            eventName.add(e);
        }
    }
    
    public Subscriber(String namespace,String event){
        this.namespace = StringUtils.isBlank(namespace)?Default:namespace;
        eventName.add( event);
    }
    
    public Subscriber(String event){
        this.namespace = Default;
        eventName.add( event);
    }
    
    public Subscriber(String ...event){
        this.namespace = Default;
        for(String e:event){
            eventName.add(e);
        }
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public void addEventName(String eventName) {
        this.eventName.add(eventName);
    }
    
    
    
}
