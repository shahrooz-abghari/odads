/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bth.anomalydetection.utils;

import com.bth.anomalydetection.services.DatabaseService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class SystemService {
    
    @Autowired 
    private DatabaseService databaseService;
    
    private static Log log = LogFactory.getLog(SystemService.class);
    
    //@Scheduled(cron="0 42 16 * * ?")
    public void removeTrack(){
        String recentDate = databaseService.getRecentDate();
        
        String dateStr = recentDate.split(" ")[0] + " 00:00";
        log.info(dateStr);
                
        DateTimeFormatter formatter =  DateTimeFormat.forPattern(VesselUtils.DATE_TIME_FORMAT);
        DateTime  date = formatter.parseDateTime(dateStr);
        date = date.minusDays(1);
        log.info("Remove old tracks  " + date.toString(formatter));
        databaseService.removeOldTracksByDate(date.toString(formatter));
    }
}
