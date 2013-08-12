package com.bth.anomalydetection.controllers;

import com.bth.anomalydetection.beans.AnomalyType;
import com.bth.anomalydetection.beans.CompositeKey;
import com.bth.anomalydetection.beans.VesselInfoBean;
import com.bth.anomalydetection.entities.AisVessel;
import com.bth.anomalydetection.entities.AnomalyHistory;
import com.bth.anomalydetection.entities.Track;
import com.bth.anomalydetection.services.DatabaseService;
import com.bth.anomalydetection.utils.VesselUtils;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@Controller
@RequestMapping("/home")
public class HomeController {
    
    @Autowired
    private DatabaseService databaseService;
    
    @Autowired
    private VesselUtils vesselUtils;
    
//    @Autowired
//    private ServletContext servletContext;
    
    private Log log = LogFactory.getLog(HomeController.class);
    private ArrayList <VesselInfoBean> aisVessels;
    private String currentDate;  
    private String FILE_INFO_ANOMALY_DETECTOR ="";
    private String FILE_INFO_WEB_SCRAPER ="";
    
    private static Timer wakeupTimer;
       
    private Map<CompositeKey,Boolean> alreadyDetectedVessels;
    private static final String SESSION_RECENT_DATE = "recentDate";   
    
    @RequestMapping(value="/load",method= RequestMethod.POST)
    public @ResponseBody ArrayList<VesselInfoBean> loadVessels(HttpServletRequest request) throws Exception { 
       try {
           if(request == null)
               log.info("request is null");
           if(request.getSession() == null)
               log.info("request.getSession() is null");
        if(request.getSession().getAttribute(SESSION_RECENT_DATE) != null)
            request.getSession().removeAttribute(SESSION_RECENT_DATE);
        alreadyDetectedVessels = new HashMap<CompositeKey, Boolean>();
        if(wakeupTimer == null) {
            wakeupTimer = new Timer();
            wakeupTimer.scheduleAtFixedRate(new java.util.TimerTask() {
            @Override
            public void run() {
                databaseService.wakeup();
            }
            }, 0, 5*60*1000);
        }
        File anomalyDetectorFile = new File(FILE_INFO_ANOMALY_DETECTOR);
        File webScraperFile = new File(FILE_INFO_WEB_SCRAPER);
        
        currentDate = databaseService.getRecentDate();
        log.info(currentDate);
        if(anomalyDetectorFile.exists() || webScraperFile.exists()){
            currentDate = databaseService.getSecondLastDate();
        }
        vesselUtils.initialize();
        log.info("vessel util initialize");
            List<AisVessel> hey = databaseService.getAisVessles(currentDate);
            log.info(hey.size());
        aisVessels = VesselUtils.getAisData(hey);
        log.info("Recent date " + currentDate);
        log.info("Number of AIS vessels " + aisVessels.size());
        return aisVessels;          
       } catch (Exception e){
           e.printStackTrace();
           return null;
       }
    }
    
    @RequestMapping(value="/get-anomalies",method= RequestMethod.POST)
    public @ResponseBody Object[] findAnomalies(HttpServletRequest request) throws Exception {
        try {
        File anomalyDetectorFile = new File(FILE_INFO_ANOMALY_DETECTOR);
        File webScraperFile = new File(FILE_INFO_WEB_SCRAPER);
        
        databaseService.clearSession();
        
        if(anomalyDetectorFile.exists() || webScraperFile.exists()){
            currentDate = databaseService.getSecondLastDate();
            log.info("Get second last Date: " + currentDate);
        } else {
            currentDate = databaseService.getRecentDate();
            log.info("Current Date: " + currentDate);
        }
        
        String recentDate = (String) request.getSession().getAttribute(SESSION_RECENT_DATE);
        log.info("recentDate in session: " + recentDate);
        if (currentDate.equals(recentDate)){
            log.info("recentDate is equal to current date: " + recentDate);
            return new Object[]{false};
        }
       request.getSession().setAttribute(SESSION_RECENT_DATE, currentDate);
       
                
        aisVessels = VesselUtils.getAisData(databaseService.getAisVessles(currentDate));
        log.info("AisVessels Size:" + aisVessels.size());
        List<AnomalyHistory> ahList = databaseService.getAnomalyHistoryByDate(currentDate);
        log.info("AnomalyList Size:" + ahList.size());
        
        int c1 = 0;
        for (AnomalyHistory ah :ahList) {
            for (int i = 0; i < aisVessels.size(); i++) {
                if (aisVessels.get(i).getId().equals(ah.getVesselId())) {
                    CompositeKey key = new CompositeKey(aisVessels.get(i).getMmsi(), aisVessels.get(i).getOrigin(), aisVessels.get(i).getDestination(), ah.getAnomalyType());
                    if (alreadyDetectedVessels.containsKey(key))
                        aisVessels.get(i).setAlreadyChecked(true);
                    else {
                        c1++;
                        aisVessels.get(i).setAlreadyChecked(false);
                        alreadyDetectedVessels.put(key, true);
                        log.info("New suspect vessel is detected: " + aisVessels.get(i).getName() + "  Anomaly type: " + ah.getAnomalyType());
                    }
                    break;
                }
            }
        }
        log.info("Total number of newly detected vessels: " + c1);
        
        int c2 = 0;
        for (AnomalyHistory ah : ahList) {
            for (int i = 0; i < aisVessels.size(); i++) {
                if (aisVessels.get(i).getId().equals(ah.getVesselId())) {
                    if (aisVessels.get(i).isAlreadyChecked()) {
                        c2++;
                        log.info("Suspicious vessel is already detected: " + aisVessels.get(i).getName()+ "  Anomaly type: " + ah.getAnomalyType());
                    }
                    break;
                }
            }
        }
         log.info("Total number of already detected vessels: " + c2);
        return new Object[]{aisVessels,ahList, currentDate};
        
        } catch(Exception e) {
            log.info(e.getMessage());
            return new Object[]{false};
        }
    }
    
    @RequestMapping(value="/get-track",method= RequestMethod.POST)
    public @ResponseBody List<Track> getTrack(Long vesselId) throws Exception {
        List <Track> track = databaseService.getVesselTrackByVesselId(vesselId);
        log.info("trackSize  " + track.size());
        return databaseService.getVesselTrackByVesselId(vesselId);
    }
}