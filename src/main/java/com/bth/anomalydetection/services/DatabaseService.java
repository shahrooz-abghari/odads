package com.bth.anomalydetection.services;

import com.bth.anomalydetection.beans.AnomalyType;
import com.bth.anomalydetection.entities.*;
import com.bth.anomalydetection.utils.VesselUtils;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DatabaseService {
    
    private EntityManager entityManger;
    private static Log log = LogFactory.getLog(DatabaseService.class);
     
    @Autowired
    public DatabaseService(EntityManagerFactory entityManagerFactory) {
        entityManger = entityManagerFactory.createEntityManager();
    }
    
    public void clearSession() {
        entityManger.clear();
    }
    
    public String getRecentDate() {
        List<String> dates = entityManger.createQuery("select d.currentDate from Dates d order by d.currentDate").getResultList();
        DateFormat formatter = new SimpleDateFormat(VesselUtils.DATE_TIME_FORMAT);
        if (dates != null && !dates.isEmpty())
            return dates.get(dates.size()-1);
        else
            return formatter.format(new Date());
    }
    
    public String getSecondLastDate() {
        List<String> dates = entityManger.createQuery("select d.currentDate from Dates d order by d.currentDate").getResultList();
        DateFormat formatter = new SimpleDateFormat(VesselUtils.DATE_TIME_FORMAT);
        if (dates != null && !dates.isEmpty()){
            if(dates.size()>1)
               return dates.get(dates.size()-2);
            else
               return dates.get(dates.size()-1);
        } else
            return formatter.format(new Date());
   }
         
    public List<Track> getVesselTrackByVesselId(Long vesselId) {
        return entityManger.createQuery("from Track t where t.vessel.id = :vesselId")
               .setParameter("vesselId", vesselId).getResultList();
        
    }
            
    public List<AisVessel> getAisVessles(String date) {
        return entityManger.createQuery("from AisVessel av where av.currentDate = :date and "
                + "(vesselType like '%cargo%' or vesselType like '%carrier%' or vesselType like '%container%' or "
                + "vesselType like '%heavy%' or vesselType like '%reefer%' or vesselType like '%passenger%' or "
                + "vesselType like '%ferry%' or vesselType like '%tanker%' or vesselType like '%pilot%' or "
                + "vesselType like '%tug%')")        
               .setParameter("date", date).getResultList();
    }
    
      
     public List<TallinnPort> getDistinctTallinnVessles(String startDate, String endDate) {
        List<Object[]> results = entityManger.createNativeQuery("select distinct tp.vesselName, tp.vesselType, tp.vesselLine, "
                + "tp.time, tp.companyName, tp.arrivalDeparture "
                + "from tallinn_port tp where tp.currentDate >= :startDate and tp.currentDate < :endDate ")
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .getResultList();
        
        
       List<TallinnPort> vessels = new ArrayList<TallinnPort>();
       for (Object[] result : results) {
              TallinnPort vessel = new TallinnPort();
              
              vessel.setVesselName((String)result[0]);
              vessel.setVesselType((String)result[1]);
              vessel.setVesselLine((String)result[2]);
             
              vessel.setTime((String)result[3]);
              vessel.setCompanyName((String)result[4]);
              vessel.setArrivalDeparture((Integer)result[5]);
              vessel.setCurrentDate(VesselUtils.DUMMY_TIME);
              
              vessels.add(vessel);
       }
        
       return vessels;
    }    
    
     public List<StockholmPort> getDistinctStockholmVessles(String startDate, String endDate) {
        List<Object[]> results = entityManger.createNativeQuery("select distinct sp.vesselName, sp.vesselType, sp.origin, sp.destination,"
                + " sp.arrivalTime, sp.departureTime, sp.companyName, sp.arrivalDeparture"
                + " from stockholm_port sp where sp.currentDate >= :startDate and sp.currentDate <= :endDate")
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .getResultList();
        
       List<StockholmPort> vessels = new ArrayList<StockholmPort>();
       for (Object[] result : results) {
              StockholmPort vessel = new StockholmPort();
              
              vessel.setVesselName((String)result[0]);
              vessel.setVesselType((String)result[1]);
              vessel.setOrigin((String)result[2]);
              vessel.setDestination((String)result[3]);
              vessel.setArrivalTime((String)result[4]);
              vessel.setDepartureTime((String)result[5]);
              vessel.setCompanyName((String)result[6]);
              vessel.setArrivalDeparture((Integer)result[7]);
              vessel.setCurrentDate(VesselUtils.DUMMY_TIME);
              
              vessels.add(vessel);
       }
        
       return vessels;
    }
        
    public List<HelsinkiPort> getDistinctHelsinkiVessles(String startDate, String endDate) {
          List<Object[]> results = entityManger.createNativeQuery("select distinct vesselName, origin, destination, arrivalTime,"
                  + " departureTime, companyName, arrivalDeparture from helsinki_port"
                  + " where currentDate >= :startDate and currentDate <= :endDate")
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .getResultList();
          
       List<HelsinkiPort> vessels = new ArrayList<HelsinkiPort>();
       
       for (Object[] result : results) {
              HelsinkiPort vessel = new HelsinkiPort();
              
              vessel.setVesselName((String)result[0]);
              vessel.setOrigin((String)result[1]);
              vessel.setDestination((String)result[2]);
              vessel.setArrivalTime((String)result[3]);
              vessel.setDepartureTime((String)result[4]);
              vessel.setCompanyName((String)result[5]);
              vessel.setArrivalDeparture((Integer)result[6]);
              vessel.setCurrentDate(VesselUtils.DUMMY_TIME);
              
              vessels.add(vessel);
       }
        
       return vessels;
}
    
    public List<NorrkopingPort> getDistinctNorrkopingVessles(String startDate, String endDate) {
        List <Object[]> results = entityManger.createNativeQuery("select distinct np.vesselName, np.origin, np.destination, np.arrivalDate,"
                + " np.companyName, np.arrivalDeparture from norrkoping_port np where np.currentDate >= :startDate"
                + " and np.currentDate < :endDate ")
                     .setParameter("startDate", startDate)
                     .setParameter("endDate", endDate)
                     .getResultList();
            
       List<NorrkopingPort> vessels = new ArrayList<NorrkopingPort>();
       
       for (Object[] result : results) {
              NorrkopingPort vessel = new NorrkopingPort();
              
              vessel.setVesselName((String)result[0]);
              vessel.setOrigin((String)result[1]);
              vessel.setDestination((String)result[2]);
              vessel.setArrivalDate((String)result[3]);
              vessel.setCompanyName((String)result[4]);
              vessel.setArrivalDeparture((Integer)result[5]);
              vessel.setCurrentDate(VesselUtils.DUMMY_TIME);
              
              vessels.add(vessel);
       }
        
       return vessels;
    }
    
    public List<Pilot> getDistinctPilotVessles(String startDate, String endDate) {
        List <Object[]> results =  entityManger.createNativeQuery("select distinct p.vesselName, p.origin, p.destination, p.orderTime, "
                + "p.startTime, p.oStartTime, p.oEndTime from pilot p where p.currentDate >= :startDate and p.currentDate <= :endDate")
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .getResultList();
        
        List<Pilot> vessels = new ArrayList<Pilot>();
       
       for (Object[] result : results) {
              Pilot vessel = new Pilot();
              
              vessel.setVesselName((String)result[0]);
              vessel.setOrigin((String)result[1]);
              vessel.setDestination((String)result[2]);
              vessel.setOrderTime((String)result[3]);
              vessel.setStartTime((String)result[4]);
              vessel.setOpStartTime((String)result[5]);
              vessel.setOpEndTime((String)result[6]);
              vessel.setCurrentDate(VesselUtils.DUMMY_TIME);
              
              vessels.add(vessel);
       }
        
       return vessels;
    }

    public int getAnomalyHistoryCountByType(AnomalyType anomalyType, String date) {
          int size =  entityManger.createNativeQuery("select distinct ships.vesselName, ships.vesselType, ships.imo, ships.mmsi, "
                + "ships.lastknownport, ships.destination, SUBSTRING(ships.arrivalTime,1,15), anomaly_history.anomalyType "
                + "from ships inner join anomaly_history on ships.id = anomaly_history.vesselId "
                + "where anomaly_history.anomalyType = :anomalyType and anomaly_history.date like '" + date +"%'")
                .setParameter("anomalyType", anomalyType.ordinal())
                .getResultList().size();
                return size;
    }
    public int getAnomalyHistoryCountByType(AnomalyType anomalyType, String startDate, String endDate) {   
          int size =  entityManger.createNativeQuery("select distinct ships.vesselName, ships.vesselType, ships.imo, ships.mmsi, "
                + "ships.lastknownport, ships.destination, SUBSTRING(ships.arrivalTime,1,15), anomaly_history.anomalyType "
                + "from ships inner join anomaly_history on ships.id = anomaly_history.vesselId "
                + "where anomaly_history.anomalyType = :anomalyType and anomaly_history.date >= :startDate and anomaly_history.date <= :endDate")
                .setParameter("anomalyType", anomalyType.ordinal())
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
               .getResultList().size();
                return size;
    }
   
    public void removeOldTracksByDate(String date) {
        EntityTransaction transaction = entityManger.getTransaction();
        transaction.begin();
        entityManger.createNativeQuery("delete from track where time < '" + date +"'").executeUpdate();
        transaction.commit();
    }

    public List<AnomalyHistory> getAnomalyHistoryByDate(String date) {
      
        return entityManger.createQuery("from AnomalyHistory ah where ah.date= :date ").setParameter("date", date).getResultList();
    }
	 public void wakeup() {
        entityManger.createNativeQuery("select 1").getSingleResult();
        log.info("Wakeup Hibernate!!!!");
    }
    
    public List<Object[]> getAnomalyHistory(String startDate, String endDate) {
        List<Object[]> results = entityManger.createNativeQuery("select distinct ships.vesselName, ships.vesselType, ships.imo, ships.mmsi,  "
                + "ships.lastknownport, ships.destination, SUBSTRING(ships.arrivalTime,1,15), anomaly_history.anomalyType "
                + "from ships inner join anomaly_history on ships.id = anomaly_history.vesselId "
                + "where anomaly_history.date >= :startDate and anomaly_history.date <= :endDate order by anomaly_history.date")
                .setParameter("startDate", startDate).setParameter("endDate", endDate).getResultList();
        return results;
    }     
    
    public List<SmugglingBlackList> getSmugglingBlackList() {
        return entityManger.createQuery("from SmugglingBlackList sbl").getResultList();
    }
    
    public void addSmugglingBlackList(SmugglingBlackList sbl) {
        EntityTransaction transaction = entityManger.getTransaction();
        transaction.begin();
        entityManger.persist(sbl);
        transaction.commit();
    }
    
     public SmugglingBlackList getSmugglingBlackList(Long id) {
        List<SmugglingBlackList>  list = (List<SmugglingBlackList>)entityManger.createQuery("from SmugglingBlackList sbl where sbl.id = :id")
                .setParameter("id", id).getResultList();
        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }
    
     public void removeSmugglingBlackList(Long[] ids) {
        EntityTransaction transaction = entityManger.getTransaction();
        transaction.begin();
        for(Long id : ids) {
        SmugglingBlackList sbl = getSmugglingBlackList(id);
            entityManger.remove(sbl);
        }
        entityManger.flush();
        transaction.commit();
       
        //log.info("vessel with id:" + id + " and name: " + sbl.getVesselName() + " removed from database");
             
    }

    public SmugglingBlackList getSmugglingBlackListByVesselInfo(String vesselName, String imo) {
        List<SmugglingBlackList>  list = (List<SmugglingBlackList>)entityManger.createQuery("from SmugglingBlackList sbl where sbl.vesselName = :vesselName and sbl.imo = :imo")
                .setParameter("vesselName",vesselName)
                .setParameter("imo", imo)
                .getResultList();
        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }

    public void updateSmugglingBlackList(SmugglingBlackList sbl) {
        EntityTransaction transaction = entityManger.getTransaction();
        transaction.begin();
        entityManger.merge(sbl);
        entityManger.flush();
        transaction.commit();
    }
}