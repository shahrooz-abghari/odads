package com.bth.anomalydetection.controllers;

import com.bth.anomalydetection.beans.StatusBean;
import com.bth.anomalydetection.entities.SmugglingBlackList;
import com.bth.anomalydetection.services.DatabaseService;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/setting")
public class SettingController {
    @Autowired
    private DatabaseService databaseService;
    
     private Log log = LogFactory.getLog(SettingController.class);
     
    @RequestMapping(method= RequestMethod.GET)
    public String populateView() throws Exception {
         return "setting";
    }
   
    @RequestMapping(value="/get-blacklist",method= RequestMethod.POST)
    public @ResponseBody String getBlackList(HttpServletRequest request) throws Exception {
       StringBuilder aaData = new StringBuilder("");
       List<SmugglingBlackList> smugglingBlackList = databaseService.getSmugglingBlackList();
       for (SmugglingBlackList sbl : smugglingBlackList) {
            aaData.append("[");
            aaData.append("\"<input type='checkbox' id='" + sbl.getId() + "' class='smugglingVessel' onclick='settingCtrl.toggle()'/>\",");
            aaData.append("\"" + sbl.getVesselName() + "\",");
            aaData.append("\"" + sbl.getImo() + "\",");
            aaData.append("\"" + sbl.getCallSign() + "\",");
            String checked = "";
            if(sbl.isEnabled())
                checked = "checked='checked'";
            aaData.append("\"<input type='checkbox' id='enabled_" + sbl.getId() + "' " + checked +" disabled='disabled'/>\"");

            aaData.append("],");
        }
        if(aaData.length()> 0)
            aaData.deleteCharAt(aaData.length() - 1);
       
        return aaData.toString();

     }
    
    @RequestMapping(value="/add-blacklist",method= RequestMethod.POST)
    public @ResponseBody StatusBean addBlackList(String vesselName, String imo, String callSign, Boolean enabled) throws Exception {
         SmugglingBlackList sbl = new SmugglingBlackList();
         sbl.setVesselName(vesselName);
         sbl.setImo(imo);
         sbl.setCallSign(callSign);
         sbl.setEnabled(enabled);
         StatusBean status;
         SmugglingBlackList smblOld = databaseService.getSmugglingBlackListByVesselInfo(vesselName, imo);
         if(smblOld != null)
             status = new StatusBean("", "This vesselalready exists", StatusBean.ERROR_FLAG);
         else {
             databaseService.addSmugglingBlackList(sbl);
             status = new StatusBean("", "", StatusBean.OK_FLAG);
         }
       
        return status;

     }
    
    @RequestMapping(value="/remove-blacklist",method= RequestMethod.POST)
    public @ResponseBody StatusBean removeBlackList(Long[] ids) throws Exception {
        StatusBean status = new StatusBean("", "", StatusBean.ERROR_FLAG);
        try {
            databaseService.removeSmugglingBlackList(ids);
            status = new StatusBean("", "", StatusBean.OK_FLAG);
        } catch(Exception e) {
            log.info(e.getMessage());
        }
       return status;
      
     }
    
    @RequestMapping(value="/view-blacklist",method= RequestMethod.POST)
    public @ResponseBody SmugglingBlackList viewBlackList(Long id) throws Exception {
      
       return databaseService.getSmugglingBlackList(id);
      
     }
    
     @RequestMapping(value="/update-blacklist",method= RequestMethod.POST)
    public @ResponseBody StatusBean updateBlackList(Long id,String vesselName, String imo, String callSign, Boolean enabled ) throws Exception {
         StatusBean status = new StatusBean("", "Internal Error", StatusBean.ERROR_FLAG);;
         if (id != null) {
             SmugglingBlackList sbl = databaseService.getSmugglingBlackList(id);
             sbl.setImo(imo);
             sbl.setVesselName(vesselName);
             sbl.setCallSign(callSign);
             sbl.setEnabled(enabled);
             SmugglingBlackList smblOld = databaseService.getSmugglingBlackListByVesselInfo(vesselName, imo);
             if (smblOld == null || smblOld.getId().equals(sbl.getId())){
                 try{
                    databaseService.updateSmugglingBlackList(sbl);
                    status = new StatusBean("", "", StatusBean.OK_FLAG);
                 } catch (Exception e) {
                    status = new StatusBean("", "Internal Error", StatusBean.ERROR_FLAG);
                 }
             }
             else 
                 status = new StatusBean("", "This vessel already exists", StatusBean.ERROR_FLAG);
         }
       
        return status;
      
    }
     

}
