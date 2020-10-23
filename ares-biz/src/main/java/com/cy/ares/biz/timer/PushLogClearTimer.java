package com.cy.ares.biz.timer;
import java.util.Date;

import com.cy.ares.dao.common.query.Ares2ConfLogQuery;
import com.cy.ares.dao.core.manager.Ares2ConfLogManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;



@Component
public class PushLogClearTimer {
   
   public static final Logger logger = LoggerFactory.getLogger(PushLogClearTimer.class);
   

    @Autowired
    private Ares2ConfLogManager ares2ConfLogManager;

   @Scheduled(cron = "59 59 23 * * ?")
   //@Scheduled(cron = "*/5 * * * * ?")
   public void cacheLoader() {
       
       logger.info("start to clean push log!");
       Ares2ConfLogQuery query = new Ares2ConfLogQuery();
       query.createCriteria().andGmtCreateLessThan(new Date());
       ares2ConfLogManager.deleteByQuery(query);
   }
}