package com.bth.anomalydetection.controllers;

import com.bth.anomalydetection.beans.JQueryDataTableParamModel;
import com.bth.anomalydetection.beans.PortVesselInfoBean;
import com.bth.anomalydetection.services.DatabaseService;
import com.bth.anomalydetection.utils.DataTablesParamUtility;
import com.bth.anomalydetection.utils.VesselUtils;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.collections.comparators.NullComparator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/datasets")
public class DatasetsController {
    @Autowired
    private DatabaseService databaseService;
    
     private Log log = LogFactory.getLog(DatasetsController.class);
     
    @RequestMapping(method= RequestMethod.GET)
    public String populateView() throws Exception {
         return "datasets";
    }
   
    @RequestMapping(value="/get-data",method= RequestMethod.GET)
    public @ResponseBody String dataTableService(HttpServletRequest request) throws Exception {
        JQueryDataTableParamModel param = DataTablesParamUtility.getParam(request);
        
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        
        if(param == null) 
            return "{\"aaData\":[]}";
        
        String sEcho = param.sEcho;
        
        if (startTime == null || endTime == null || startTime.isEmpty() || endTime.isEmpty() ){
             return "{\"sEcho\":\"" + sEcho + "\", \"iTotalRecords\":0,  \"iTotalDisplayRecords\": 0, \"aaData\":[]}";
        }
        
        log.info("startTime " +startTime);
        log.info("endTime " + endTime);
        
        ArrayList<PortVesselInfoBean> vessels = new ArrayList<PortVesselInfoBean>();
       
        vessels = VesselUtils.getHelsinkiData(databaseService.getDistinctHelsinkiVessles(startTime, endTime));
        vessels.addAll(VesselUtils.getTallinnData(databaseService.getDistinctTallinnVessles(startTime, endTime)));
        vessels.addAll(VesselUtils.getStockholmData(databaseService.getDistinctStockholmVessles(startTime, endTime)));
        vessels.addAll(VesselUtils.getNorrkopingData(databaseService.getDistinctNorrkopingVessles(startTime, endTime)));
        vessels.addAll(VesselUtils.getPilotData(databaseService.getDistinctPilotVessles(startTime, endTime)));
       
             
        // total number of records (unfiltered)
	int iTotalRecords = vessels.size(); 
	
        List<PortVesselInfoBean> newVesselList = new LinkedList<PortVesselInfoBean>();
        
        log.info("!" +param.sSearch +"!");
        for(PortVesselInfoBean vessel : vessels){
            
            if(vessel.getVesselName().contains(param.sSearch.toLowerCase()) ||
                  (vessel.getCompanyName()!= null &&  vessel.getCompanyName().contains(param.sSearch.toLowerCase())) ||
                  (vessel.getOrigin()!= null && vessel.getOrigin().contains(param.sSearch.toLowerCase())) ||
                  (vessel.getDestination() != null && vessel.getDestination().contains(param.sSearch.toLowerCase())) ||
                   vessel.getVesselStatus().name().toLowerCase().contains(param.sSearch.toLowerCase()) ||
                   vessel.getDataSourceType().name().toLowerCase().contains(param.sSearch.toLowerCase()) ||
                   vessel.getVesselType().name().toLowerCase().contains(param.sSearch.toLowerCase())) {
                   
                newVesselList.add(vessel); 
            }
        }
        //value will be set when code filters entries by keyword
        int iTotalDisplayRecords = newVesselList.size();
        
        log.info("newVesselList.size()" + iTotalDisplayRecords);
        
        final int sortColumnIndex = param.iSortColumnIndex;
        final int sortDirection = param.sSortDirection.equals("asc") ? -1 : 1;
        
	Comparator comparator = new Comparator<PortVesselInfoBean>(){
            
            private Comparator nullComparator = new NullComparator(true);
            
            //@Override
            public int compare(PortVesselInfoBean v1, PortVesselInfoBean v2) {
                    switch(sortColumnIndex){
                        case 0:
                                return v1.getVesselName().compareTo(v2.getVesselName()) * sortDirection;
                        case 1:
                                return v1.getVesselType().compareTo(v2.getVesselType()) * sortDirection;
                        case 2:
                                return nullComparator.compare(v1.getCompanyName(),v2.getCompanyName()) * sortDirection;
                        case 3:
                                return nullComparator.compare(v1.getOrigin(), v2.getOrigin()) * sortDirection;
                        case 4:
                                return nullComparator.compare(v1.getDestination(), v2.getDestination()) * sortDirection;
                        case 5:
                                return v1.getTime().compareTo(v2.getTime()) * sortDirection;
                        case 6:
                                return v1.getVesselStatus().compareTo(v2.getVesselStatus()) * sortDirection;
                        case 7:
                                return v1.getDataSourceType().compareTo(v2.getDataSourceType()) * sortDirection;
                    }
                    return 0;
            }
        };
     
        Collections.sort(newVesselList, comparator);
		
        if(newVesselList.size()< param.iDisplayStart + param.iDisplayLength)
            newVesselList = newVesselList.subList(param.iDisplayStart, newVesselList.size());
        else
            newVesselList = newVesselList.subList(param.iDisplayStart, param.iDisplayStart + param.iDisplayLength);

        JsonFactory f = new JsonFactory();
        StringWriter swr = new StringWriter();
        JsonGenerator g = f.createJsonGenerator(swr);
      
        g.writeStartObject();
        g.writeStringField("sEcho", sEcho);
        g.writeNumberField("iTotalRecords", iTotalRecords);
        g.writeNumberField("iTotalDisplayRecords", iTotalDisplayRecords);
        DateFormat formatter = new SimpleDateFormat(VesselUtils.DATE_TIME_FORMAT);
        g.writeArrayFieldStart("aaData");
        for(PortVesselInfoBean vessel : newVesselList){
            g.writeStartArray();
            g.writeString(vessel.getVesselName());
            g.writeString(vessel.getVesselType().name());
            g.writeString(vessel.getCompanyName());
            g.writeString(vessel.getOrigin());
            g.writeString(vessel.getDestination());
            g.writeString(formatter.format(vessel.getTime()));
            g.writeString(vessel.getVesselStatus().name());
            g.writeString(vessel.getDataSourceType().name());
            g.writeEndArray();
        }
        
        g.writeEndArray();
        g.writeEndObject();
        g.close();
        log.info(swr.toString());
        return swr.toString();
       
     }
    
     

}
