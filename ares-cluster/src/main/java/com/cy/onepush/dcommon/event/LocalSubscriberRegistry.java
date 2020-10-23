package com.cy.onepush.dcommon.event;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 注册中心
 * 
 * @author maoxq
 *
 * @Description 
 *
 * @date 2018年3月23日 下午3:32:36
 * @version V1.0
 */
public class LocalSubscriberRegistry {
    
    // namespace; 
    // 事件 : key = namespace+eventName
    private Map<String, List<Subscriber>> eventPool = new ConcurrentHashMap<>();
    
    public synchronized void registe(Subscriber subscriber){
        
        String namespace = subscriber.getNamespace();
        Set<String> eventSet = subscriber.getEventName();
        
        for(String eventName:eventSet){
            String key = namespace.concat("_").concat(eventName);
            List<Subscriber> listSub = eventPool.get(key);
            if(listSub == null){
                listSub = new ArrayList<>();
                eventPool.put(key, listSub);
            }
            listSub.add(subscriber);
        }
        
    }
    
    public synchronized void registe(String eventName,Subscriber subscriber){
        
        String namespace = subscriber.getNamespace();
        String key = namespace.concat("_").concat(eventName);
        List<Subscriber> listSub = eventPool.get(key);
        if(listSub == null){
            listSub = new ArrayList<>();
            eventPool.put(key, listSub);
        }
        listSub.add(subscriber);
        
    }
    
    public synchronized void unregiste(Subscriber subscriber){
        
        String namespace = subscriber.getNamespace();
        Set<String> eventSet = subscriber.getEventName();
        
        for(String eventName:eventSet){
            String key = namespace.concat("_").concat(eventName);
            List<Subscriber> listSub = eventPool.get(key);
            if(listSub != null){
                listSub.remove(subscriber);
            }
        }
        
    }
    
    public  List<Subscriber> findSub(MEvent event){
        
        String namespace = event.getNamespace();
        String eventName = event.getEventName();
        String key = namespace.concat("_").concat(eventName);
                
        List<Subscriber> listSub = eventPool.get(key);
        
        return listSub;
        
    }
    
    
}
