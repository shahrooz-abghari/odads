package com.bth.anomalydetection.utils;

import com.bth.anomalydetection.beans.AnomalyType;
import com.bth.anomalydetection.beans.DataSourceType;
import com.bth.anomalydetection.entities.AisVessel;
import com.bth.anomalydetection.entities.HelsinkiPort;
import com.bth.anomalydetection.entities.NorrkopingPort;
import com.bth.anomalydetection.entities.Pilot;
import com.bth.anomalydetection.entities.StockholmPort;
import com.bth.anomalydetection.entities.TallinnPort;
import com.bth.anomalydetection.beans.PortVesselInfoBean;
import com.bth.anomalydetection.beans.VesselInfoBean;
import com.bth.anomalydetection.beans.VesselStatus;
import com.bth.anomalydetection.beans.VesselType;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

@Component
public class VesselUtils {
     
    private static Log log = LogFactory.getLog(VesselUtils.class);
    public static final int SHIPS_IN_PORT = 0;
    public static final int EXPECTED_ARRIVAL = 1;
    public static final int CARGO_ARRIVAL = 2;
    public static final int PASSENGER_ARRIVAL = 3;
    public static final int PASSENGER_DEPARTURE = 4;
    public static final int PASSENGER_VISITED = 5;
    
    public static final String HELSINKI = "helsinki";
    public static final String TALLINN = "tallinn";
    public static final String PALDISKI = "paldiski";
    public static final String NORRKOPING = "norrkoping";
    public static final String STOCKHOLM = "stockholm";
    public static final String KAPELLSKAR = "kapellskar";
    public static final String NYNASHAMN = "nynashamn";

    private static Map<String,String> portShipTypeDictionary;
    private static Map<String,String> aisShipTypeDictionary;
    public static String DUMMY_TIME = "2011-01-01 00:00";
    public static String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm";
    public static String DATE_FORMAT = "yyyy-MM-dd";
   
   public void initialize(){
        createPortShipTypeDictionary();
        createAISShipTypeDictionary();
    }
    
    private static void createPortShipTypeDictionary() {
         portShipTypeDictionary = new HashMap<String, String>();
         portShipTypeDictionary.put("bilfärja för passagerare", "FERRY");
         portShipTypeDictionary.put("containerfartyg","CARGO");
         portShipTypeDictionary.put("fartyg för transport av torr bulk","CARGO");
         portShipTypeDictionary.put("fritidsskuta","OTHER");
         portShipTypeDictionary.put("kemikalietankfartyg","TANKER"); // tanker
         portShipTypeDictionary.put("kryssningsfartyg","PASSENGER");
         portShipTypeDictionary.put("produkttankfartyg","TANKER");// tanker
         portShipTypeDictionary.put("ro/ro- fartyg för passagerare", "PASSENGER");
         portShipTypeDictionary.put("skolfartyg","OTHER");
         portShipTypeDictionary.put("tankfartyg","TANKER"); //tanker
         portShipTypeDictionary.put("örlogsfartyg","OTHER");//military ops
         portShipTypeDictionary.put("övrigt passagerarfartyg","PASSENGER");

         portShipTypeDictionary.put("dry cargo","CARGO");
         portShipTypeDictionary.put("container ship","CARGO");
         portShipTypeDictionary.put("general cargo","CARGO");
         portShipTypeDictionary.put("bulk carrier","CARGO");
         portShipTypeDictionary.put("ro-ro","CARGO");
         portShipTypeDictionary.put("supply ship","CARGO");
         portShipTypeDictionary.put("tanker","TANKER");
         portShipTypeDictionary.put("bulker","CARGO");

         portShipTypeDictionary.put("trawler","OTHER");
         portShipTypeDictionary.put("tug","OTHER");
         portShipTypeDictionary.put("yacht","OTHER");
         portShipTypeDictionary.put("other","OTHER");
         
         portShipTypeDictionary.put("cruise ship","PASSENGER");
         portShipTypeDictionary.put("passenger ship","PASSENGER");
    }
    
    private static void createAISShipTypeDictionary() {
         aisShipTypeDictionary = new HashMap<String, String>();
         aisShipTypeDictionary.put("bulk carrier", "CARGO");
         aisShipTypeDictionary.put("livestock carrier", "CARGO");
         aisShipTypeDictionary.put("cargo", "CARGO");
         aisShipTypeDictionary.put("cement carrier", "CARGO");
         aisShipTypeDictionary.put("container ship", "CARGO");
         aisShipTypeDictionary.put("heavy lift vessel", "CARGO");
         aisShipTypeDictionary.put("heavy load carrier", "CARGO");
         aisShipTypeDictionary.put("ro-ro cargo", "CARGO");
         aisShipTypeDictionary.put("ro-ro/container carrier", "CARGO");
         aisShipTypeDictionary.put("vehicles carrier", "CARGO");
         aisShipTypeDictionary.put("reefer", "CARGO");
         aisShipTypeDictionary.put("reefer/containership", "CARGO");
         
         aisShipTypeDictionary.put("ferry", "FERRY");
         aisShipTypeDictionary.put("ro-ro/passenger ship", "FERRY");
         aisShipTypeDictionary.put("passenger", "PASSENGER");
         aisShipTypeDictionary.put("passengers ship", "PASSENGER");
         
         aisShipTypeDictionary.put("pilot", "PILOT");
         aisShipTypeDictionary.put("pilot vessel", "PILOT");
         
         aisShipTypeDictionary.put("asphalt/bitumen tanker", "TANKER");
         aisShipTypeDictionary.put("bunkering tanker", "TANKER");
         aisShipTypeDictionary.put("chemical tanker", "TANKER");
         aisShipTypeDictionary.put("crude oil tanker", "TANKER");
         aisShipTypeDictionary.put("lng tanker", "TANKER");
         aisShipTypeDictionary.put("lpg tanker", "TANKER");
         aisShipTypeDictionary.put("oil products tanker", "TANKER");
         aisShipTypeDictionary.put("oil/chemical tanker", "TANKER");
         aisShipTypeDictionary.put("tanker", "TANKER");
         
         aisShipTypeDictionary.put("pusher tug", "TUG");
         aisShipTypeDictionary.put("tug", "TUG");
         aisShipTypeDictionary.put("tug/fire fighting vessel", "TUG");
         aisShipTypeDictionary.put("tug/ice breaker", "TUG");
         aisShipTypeDictionary.put("tug/supply vessel", "TUG");
    }
     
   private static String destinationCleaning(String input) {
        if (input != null) {
            String output = input.toLowerCase();
            
            output = output.replace("ö", "o");
            output = output.replace("ä", "a");
            output = output.replace("å", "a");
            
            output = output.replace(" via ", " ");

            output = output.replace("mvvga", "muuga");
            output = output.replace("mugaa", "muuga");
            output = output.replace("muga", "muuga");
            if(output.equals("tal")){
                output = output.replace("tal", "tallinn");
            }             
            if(output.equals("tallin")){
                output = output.replace("tallin", "tallinn");
            }
            output = output.replace("talin", "tallinn");
            output = output.replace("talinn", "tallinn");
            output = output.replace("tln ", "tallinn");
            output = output.replace("paldiski south", "paldiski");
            output = output.replace(" mar", " mariehamn");
            output = output.replace(" m.hamn", " mariehamn");
            output = output.replace(" m.haml", " mariehamn");
            output = output.replace(" mhmn", " mariehamn");
            output = output.replace(" marhml", " mariehamn");
            output = output.replace(" marhmn", " mariehamn");
            output = output.replace("mariehaml", "mariehamn");
            output = output.replace("fi mhq", "mariehamn");
            output = output.replace("fimhq", "mariehamn");
            
            output = output.replace("stockh ", "stockholm ");
            output = output.replace("se sto", "stockholm");
            output = output.replace("sesto", "stockholm");
            output = output.replace("se stl", "stockholm");
            output = output.replace("sto ", "stockholm ");
            output = output.replace(" sto", " stockholm");
            output = output.replace("/sto/", " stockholm ");
            
            if(output.equals("abo")){
                output = output.replace("abo", "turku");
            }
            output = output.replace("fi tkt", "turku");
            output = output.replace("fi tku", "turku");
            output = output.replace("fitku", "turku");
            output = output.replace("fitku", "turku");
            output = output.replace("fi lan","langnas");
            output = output.replace("filan","langnas");
            
            output = output.replace("fi hel", "helsinki");
            output = output.replace("helsingi", "helsinki");

            output = output.replace("kapelskar","kapellskar");
            output = output.replace("nli-kap-nli","naantali kapellskar");
            output = output.replace("onroutekapndkap","nadendal kapellskar");        
            if (output.isEmpty()) {
                output = null;
            }  
            return output;
        }
        return null;
    }
    
    private static String dataCleaning(String input) {
        if (input != null) {
            String output = input.toLowerCase();
            ArrayList<String> specialCharachters = new ArrayList<String>();

            for (int i = 0; i < output.length(); i++) {
                if (!Character.isLetter(output.charAt(i)) && !Character.isDigit(output.charAt(i))) {
                    specialCharachters.add(Character.toString(output.charAt(i)));
                }
            }

            for (int i = 0; i < specialCharachters.size(); i++) {
                output = output.replace(specialCharachters.get(i), " ");
            }

            output = output.replace("ö", "o");
            output = output.replace("ä", "a");
            output = output.replace("å", "a");
            output = output.replace("é", "e");

            if (output.isEmpty()) {
                output = null;
            }
            return output;
        }
        return null;
    }
    
    public VesselType getVesselType(String type) {
        VesselType vesselType;
        if (type == null || type.isEmpty()) {
            vesselType = VesselType.UNKNOWN;
        } else {
            if (aisShipTypeDictionary.get(type.toLowerCase()) != null) {
                vesselType = VesselType.valueOf(aisShipTypeDictionary.get(type.toLowerCase()));
            } else {
                vesselType = VesselType.OTHER;
            }
        }
        return vesselType;
    }
       
   public static ArrayList<VesselInfoBean> getAisData(List <AisVessel> vesselList) throws ParseException {
       
        Date arrivalTime;
        Date infoReceivedTime;
        Date currentDate;
        VesselType shipType;
        
        ArrayList<VesselInfoBean> vesselInfo =  new ArrayList<VesselInfoBean>();
       
        DateFormat formatter = new SimpleDateFormat(DATE_TIME_FORMAT);
        
        for (AisVessel aisVessel: vesselList) {
            currentDate = formatter.parse(aisVessel.getCurrentDate());
            try {
                arrivalTime = formatter.parse(aisVessel.getArrivalTime());
            } catch (Exception ex) {
		arrivalTime = (Date)currentDate.clone();
            }
            
            try {
                infoReceivedTime = formatter.parse(aisVessel.getInfoReceivedTime());
            } catch (Exception ex) {
                infoReceivedTime = (Date)currentDate.clone();
            }
            
            if(aisVessel.getVesselType() == null || aisVessel.getVesselType().isEmpty()) {
                shipType = VesselType.UNKNOWN;
            } else {
                if (aisShipTypeDictionary.get(aisVessel.getVesselType().toLowerCase())!= null)
                    shipType = VesselType.valueOf(aisShipTypeDictionary.get(aisVessel.getVesselType().toLowerCase()));
                else shipType = VesselType.OTHER;
            }
            
                        if(aisVessel.getVesselTypeInfo() != null && !aisVessel.getVesselTypeInfo().isEmpty()) {
                 if (aisVessel.getVesselTypeInfo().toLowerCase().contains(VesselType.PASSENGER.name().toLowerCase()) && 
                         (shipType != VesselType.FERRY && shipType != VesselType.PASSENGER))
                     shipType = VesselType.PASSENGER;
            }
                        
            VesselInfoBean vessel =  new VesselInfoBean();
            vessel.setId(aisVessel.getId());
            vessel.setName(aisVessel.getVesselName());
            vessel.setCleanName(dataCleaning(aisVessel.getVesselName()));
            vessel.setImo(aisVessel.getImo());
            vessel.setMmsi(aisVessel.getMmsi());
            vessel.setCallSign(aisVessel.getCallSign());
            
            vessel.setType(shipType);
            
            vessel.setFlag(aisVessel.getFlag());
            vessel.setOrigin(dataCleaning(destinationCleaning(aisVessel.getOrigin())));
            
            vessel.setDestination(dataCleaning(destinationCleaning(aisVessel.getDestination())));
            vessel.setCurrentPort(aisVessel.getCurrentPort());
            
            vessel.setDeadWeight(aisVessel.getDeadWeight());
            vessel.setYear(aisVessel.getYear());
            
            vessel.setDraught(aisVessel.getDraught());
            vessel.setLength(aisVessel.getLength());
            vessel.setWidth(aisVessel.getWidth());
            
            vessel.setHeading(aisVessel.getHeading());
            vessel.setSpeedAverage(aisVessel.getSpeedAverage());
            vessel.setSpeedMax(aisVessel.getSpeedMax());
           
            vessel.setLatitude(aisVessel.getLatitude());
            vessel.setLongitude(aisVessel.getLongitude());
            
            vessel.setArrivalTime(arrivalTime);
            vessel.setCurrentDate(currentDate);
           
            vessel.setInfoReceivedTime(infoReceivedTime);
            vessel.setTrackData(aisVessel.getTrackData());
           
            EnumSet<AnomalyType> anomlayTypes = EnumSet.noneOf(AnomalyType.class);
            
            vessel.setAnomalyTypes(anomlayTypes);
            
            vessel.setStatus(VesselStatus.UNKNOWN);
            
            vesselInfo.add(vessel);
            
        }
        return vesselInfo;
    }
    
   public static ArrayList<PortVesselInfoBean> getNorrkopingData(List<NorrkopingPort> vesselList)  throws ParseException {
        Date time;
        Date currentDate;
        DateFormat tmpFormatter = new SimpleDateFormat("yyyy-MM-dd");
        PortVesselInfoBean vessel = null;
        ArrayList<PortVesselInfoBean> vesselInfo =  new ArrayList<PortVesselInfoBean>();
        
        DateFormat formatter = new SimpleDateFormat(DATE_TIME_FORMAT);
        
        for (NorrkopingPort norrkopingVessel: vesselList) {
            currentDate = formatter.parse(norrkopingVessel.getCurrentDate());
            try {
                    time = tmpFormatter.parse(norrkopingVessel.getArrivalDate());
            } catch (Exception ex) {
                time = (Date)currentDate.clone();
            }
            
            if (norrkopingVessel.getArrivalDeparture() == SHIPS_IN_PORT) {
                
               vessel = new PortVesselInfoBean(dataCleaning(norrkopingVessel.getVesselName()), VesselType.CARGO, NORRKOPING, dataCleaning(norrkopingVessel.getDestination()), 
                    dataCleaning(norrkopingVessel.getCompanyName()), time, VesselStatus.DEPARTURE,DataSourceType.NORRKOPING_PORT, currentDate);
                              
            } else if (norrkopingVessel.getArrivalDeparture() == EXPECTED_ARRIVAL) {
                
                vessel = new PortVesselInfoBean(dataCleaning(norrkopingVessel.getVesselName()),VesselType.CARGO, dataCleaning(norrkopingVessel.getOrigin()), NORRKOPING, 
                    dataCleaning(norrkopingVessel.getCompanyName()), time, VesselStatus.ARRIVAL, DataSourceType.NORRKOPING_PORT, currentDate);

            }
            vesselInfo.add(vessel);
 
         }
        return vesselInfo;
    }
    
   public static ArrayList<PortVesselInfoBean> getHelsinkiData(List <HelsinkiPort> vesselList)throws ParseException {
        PortVesselInfoBean vessel1 = null;
        PortVesselInfoBean vessel2 = null;
        
        ArrayList<PortVesselInfoBean> vesselInfo =  new ArrayList<PortVesselInfoBean>();
        
        Date time;
        Date currentDate;
        DateFormat formatter = new SimpleDateFormat(DATE_TIME_FORMAT);
        
        for (HelsinkiPort helsinkiVessel: vesselList) {
            currentDate = formatter.parse(helsinkiVessel.getCurrentDate());
            
            if (helsinkiVessel.getArrivalDeparture() == SHIPS_IN_PORT) { 
                try {
                    time = formatter.parse(helsinkiVessel.getArrivalTime());
                } catch (Exception ex) { 
                    time = (Date)currentDate.clone();
                }
                vessel1 = new PortVesselInfoBean(dataCleaning(helsinkiVessel.getVesselName()), VesselType.CARGO, dataCleaning(helsinkiVessel.getOrigin()), HELSINKI, 
                        dataCleaning(helsinkiVessel.getCompanyName()), time, VesselStatus.IN_PORT,DataSourceType.HLSINKI_PORT, currentDate);
                               try {
                    time = formatter.parse(helsinkiVessel.getDepartureTime());
                } catch (Exception ex) {
                    time = (Date)currentDate.clone();
                }
                vessel2 = new PortVesselInfoBean(dataCleaning(helsinkiVessel.getVesselName()), VesselType.CARGO, HELSINKI, dataCleaning(helsinkiVessel.getDestination()), 
                        dataCleaning(helsinkiVessel.getCompanyName()), time, VesselStatus.DEPARTURE, DataSourceType.HLSINKI_PORT, currentDate);
                             
               vesselInfo.add(vessel1);
               vesselInfo.add(vessel2);
            } 
            if(helsinkiVessel.getArrivalDeparture() == CARGO_ARRIVAL) {
                try {
                    time = formatter.parse(helsinkiVessel.getArrivalTime());
                } catch (Exception ex) { 
                    time = (Date)currentDate.clone();
                }
                vessel1 = new PortVesselInfoBean(dataCleaning(helsinkiVessel.getVesselName()), VesselType.CARGO, dataCleaning(helsinkiVessel.getOrigin()), HELSINKI, 
                        dataCleaning(helsinkiVessel.getCompanyName()), time, VesselStatus.ARRIVAL, DataSourceType.HLSINKI_PORT, currentDate);
               
                vesselInfo.add(vessel1);
            }
            else if (helsinkiVessel.getArrivalDeparture() == PASSENGER_ARRIVAL) {
                try {
                    time = formatter.parse(helsinkiVessel.getArrivalTime());
                } catch (Exception ex) {
                    time = (Date)currentDate.clone();
                }
                vessel1 = new PortVesselInfoBean(dataCleaning(helsinkiVessel.getVesselName()), VesselType.PASSENGER, dataCleaning(helsinkiVessel.getOrigin()), HELSINKI, 
                        dataCleaning(helsinkiVessel.getCompanyName()), time, VesselStatus.ARRIVAL, DataSourceType.HLSINKI_PORT, currentDate);
               
                vesselInfo.add(vessel1);
                
            } else if (helsinkiVessel.getArrivalDeparture() == PASSENGER_DEPARTURE) {
                try {
                    time = formatter.parse(helsinkiVessel.getDepartureTime());
                } catch (Exception ex) {
                    time = (Date)currentDate.clone();
                }
                vessel1 = new PortVesselInfoBean(dataCleaning(helsinkiVessel.getVesselName()), VesselType.PASSENGER, HELSINKI, dataCleaning(helsinkiVessel.getDestination()), 
                        dataCleaning(helsinkiVessel.getCompanyName()), time, VesselStatus.DEPARTURE, DataSourceType.HLSINKI_PORT, currentDate);
              
                vesselInfo.add(vessel1);
                
            } 
        }
        return vesselInfo;
    }
   
   public static ArrayList<PortVesselInfoBean> getStockholmData(List<StockholmPort> vesselList) throws ParseException {
        PortVesselInfoBean vessel1 =null;
        PortVesselInfoBean vessel2 =null;
        
        Date currentDate;
        Date time;
        
        ArrayList<PortVesselInfoBean> vesselInfo =  new ArrayList<PortVesselInfoBean>();
        DateFormat formatter = new SimpleDateFormat(DATE_TIME_FORMAT);
        
         for (StockholmPort stockholmVessel: vesselList) {
            currentDate = formatter.parse(stockholmVessel.getCurrentDate());
            
            
            VesselType shipType;
            if(stockholmVessel.getVesselType()== null || stockholmVessel.getVesselType().isEmpty()) {
                shipType = VesselType.UNKNOWN;
            } else {
                if (portShipTypeDictionary.get(stockholmVessel.getVesselType().toLowerCase())!= null)
                    shipType = VesselType.valueOf(portShipTypeDictionary.get(stockholmVessel.getVesselType().toLowerCase()));
                else 
                    shipType = VesselType.OTHER;
             }
              
            if (stockholmVessel.getArrivalDeparture() == SHIPS_IN_PORT) {
                try {
                    time = formatter.parse(stockholmVessel.getArrivalTime());
                } catch (Exception ex) {
                    time = (Date)currentDate.clone();
                }
                vessel1 = new PortVesselInfoBean(dataCleaning(stockholmVessel.getVesselName()), shipType, dataCleaning(stockholmVessel.getOrigin()), STOCKHOLM, 
                        dataCleaning(stockholmVessel.getCompanyName()), time, VesselStatus.IN_PORT, DataSourceType.STOCKHOLM_PORT,currentDate);
             
                try {
                    time = formatter.parse(stockholmVessel.getDepartureTime());
                } catch (Exception ex) {
                    time = (Date)currentDate.clone();
                }
                vessel2 = new PortVesselInfoBean(dataCleaning(stockholmVessel.getVesselName()), shipType, STOCKHOLM, dataCleaning(stockholmVessel.getDestination()), 
                        dataCleaning(stockholmVessel.getCompanyName()), time, VesselStatus.DEPARTURE, DataSourceType.STOCKHOLM_PORT, currentDate);
                
                vesselInfo.add(vessel1);
                vesselInfo.add(vessel2);
                
          } 
            
            if(stockholmVessel.getArrivalDeparture() == EXPECTED_ARRIVAL) {
                try {
                    time = formatter.parse(stockholmVessel.getArrivalTime());
                } catch (Exception ex) {
                    time = (Date)currentDate.clone();
                }
                vessel1 = new PortVesselInfoBean(dataCleaning(stockholmVessel.getVesselName()), shipType, dataCleaning(stockholmVessel.getOrigin()), STOCKHOLM, 
                        dataCleaning(stockholmVessel.getCompanyName()), time, VesselStatus.ARRIVAL, DataSourceType.STOCKHOLM_PORT,currentDate);
                
               vesselInfo.add(vessel1);
            }
        }
        return  vesselInfo;
   }
   
   public static ArrayList<PortVesselInfoBean> getTallinnData(List <TallinnPort> vesselList) throws ParseException {
        PortVesselInfoBean vessel =null;
        Date currentDate;
        Date time;
        
        ArrayList<PortVesselInfoBean> vesselInfo =  new ArrayList<PortVesselInfoBean>();
        DateFormat formatter = new SimpleDateFormat(DATE_TIME_FORMAT);
        
        for (TallinnPort tallinnVessel: vesselList) {
            currentDate = formatter.parse(tallinnVessel.getCurrentDate());
            
            if (tallinnVessel.getArrivalDeparture() == SHIPS_IN_PORT){
                VesselType shipType = VesselType.OTHER;
                try {
                     if(tallinnVessel.getVesselType()== null || tallinnVessel.getVesselType().isEmpty()) {
                        shipType = VesselType.UNKNOWN;
                    } else {
                        if (portShipTypeDictionary.get(tallinnVessel.getVesselType().toLowerCase())!= null)
                            shipType = VesselType.valueOf(portShipTypeDictionary.get(tallinnVessel.getVesselType().toLowerCase()));
                        else 
                            shipType = VesselType.OTHER;
                     }
                    time = formatter.parse(tallinnVessel.getTime());
                } catch (Exception ex) {
                    time = (Date)currentDate.clone();
                }

                vessel = new PortVesselInfoBean(dataCleaning(tallinnVessel.getVesselName()), shipType, TALLINN, null, 
                        null, time, VesselStatus.IN_PORT, DataSourceType.TALLIN_PORT, currentDate);
              
            } else if(tallinnVessel.getArrivalDeparture() == CARGO_ARRIVAL) {
                try {
                    time = formatter.parse(tallinnVessel.getTime());
                } catch (Exception ex) {
                    time = (Date)currentDate.clone();
                }
                vessel = new PortVesselInfoBean(dataCleaning(tallinnVessel.getVesselName()), VesselType.CARGO, null, TALLINN, 
                        null, time, VesselStatus.ARRIVAL, DataSourceType.TALLIN_PORT, currentDate);
                
            } else if (tallinnVessel.getArrivalDeparture() == PASSENGER_ARRIVAL) {
                try {
                    time = formatter.parse(tallinnVessel.getTime());
                } catch (Exception ex) {
                    time = (Date)currentDate.clone();
                }
                String origin = null;
                int index = tallinnVessel.getVesselLine().indexOf("-");
               
                if (index != -1)
                    origin = tallinnVessel.getVesselLine().substring(0, index);
                vessel = new PortVesselInfoBean(dataCleaning(tallinnVessel.getVesselName()), VesselType.PASSENGER, dataCleaning(origin), TALLINN, 
                        dataCleaning(tallinnVessel.getCompanyName()), time, VesselStatus.ARRIVAL, DataSourceType.TALLIN_PORT, currentDate);
               
            } else if (tallinnVessel.getArrivalDeparture() == PASSENGER_DEPARTURE) {
                try {
                    time = formatter.parse(tallinnVessel.getTime());
                } catch (Exception ex) {
                    time = (Date)currentDate.clone();
                }
                String destination = null;
                int index = tallinnVessel.getVesselLine().indexOf("-");
                if (index != -1)
                   destination = tallinnVessel.getVesselLine().substring(index + 1);
                vessel = new PortVesselInfoBean(dataCleaning(tallinnVessel.getVesselName()), VesselType.PASSENGER, TALLINN, dataCleaning(destination), 
                        dataCleaning(tallinnVessel.getCompanyName()), time, VesselStatus.DEPARTURE, DataSourceType.TALLIN_PORT, currentDate);
         
            }
           vesselInfo.add(vessel);
        }
        
        return vesselInfo;
   }
   
   public static ArrayList<PortVesselInfoBean> getPilotData(List <Pilot> vesselList) throws ParseException{
        PortVesselInfoBean vessel =null;
        Date currentDate;
        Date time;
        
        ArrayList<PortVesselInfoBean> vesselInfo =  new ArrayList<PortVesselInfoBean>();
       
        DateFormat formatter = new SimpleDateFormat(DATE_TIME_FORMAT);
         for (Pilot pilotVessel: vesselList) {
            currentDate = formatter.parse(pilotVessel.getCurrentDate());
            
            if (pilotVessel.getOpStartTime()!= null){
                try {
                    time = formatter.parse(pilotVessel.getOpStartTime());
                } catch (Exception ex) {
                    time = (Date)currentDate.clone();
                }
                
                vessel = new PortVesselInfoBean(dataCleaning(pilotVessel.getVesselName()), VesselType.CARGO, dataCleaning(pilotVessel.getOrigin()), 
                        dataCleaning(pilotVessel.getDestination()), null, time, VesselStatus.RECEIVED_SERVICE, DataSourceType.PILOT,currentDate);
               
            } else {
                try {
                    time = formatter.parse(pilotVessel.getStartTime());
                } catch (Exception ex) {
                    time = (Date)currentDate.clone();
                }
                
                vessel = new PortVesselInfoBean(dataCleaning(pilotVessel.getVesselName()), VesselType.CARGO, dataCleaning(pilotVessel.getOrigin()), 
                        dataCleaning(pilotVessel.getDestination()), null, time, VesselStatus.ORDERED_SERVICE, DataSourceType.PILOT,currentDate);
                
            }
            vesselInfo.add(vessel);
        }
        return vesselInfo;
   }
}
