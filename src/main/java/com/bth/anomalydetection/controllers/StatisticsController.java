package com.bth.anomalydetection.controllers;

import com.bth.anomalydetection.beans.AnomalyType;
import com.bth.anomalydetection.services.DatabaseService;
import com.bth.anomalydetection.utils.VesselUtils;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/statistics")
public class StatisticsController {
    
    @Autowired
    private DatabaseService databaseService;
   @Autowired
    private ServletContext servletContext;
    @Autowired
    private VesselUtils vesselUtils;
    
    private static Log log = LogFactory.getLog(StatisticsController.class);
    private final int STACK_CHART = 1;
    private final int PIE_CHART = 2;
    private final int DAYS = 15;
    public static final int ANOMALY_COUNT = 22;
    public static final String TMP_FILE_FOLDER = "tmp";
    private int XLS = 1;
    private int TABLE = 2;
    
    @RequestMapping(method= RequestMethod.GET)
    public String populateView() throws Exception {
         return "statistics";
    }
    
    @RequestMapping(value="/get-statistics",method= RequestMethod.GET)
    public @ResponseBody String getStatistic(String params) throws Exception {
        String[] parameters = params.split("-");
        int type = Integer.parseInt(parameters[0]);
        
        DateTimeFormatter formatter =  DateTimeFormat.forPattern(VesselUtils.DATE_TIME_FORMAT);
        DateTime end= new DateTime(Long.parseLong(parameters[2]));
        DateTime start = new DateTime(Long.parseLong(parameters[1]));
        DateTime minStart = end.minusDays(DAYS);
        if(minStart.isAfter(start))
            start = minStart;
        
        
        log.info("start " + start.toString(formatter) + " end :" + end.toString(formatter));
        
        int [] anomalyCount = new int[ANOMALY_COUNT];
        int days = Days.daysBetween(start.toDateMidnight() , end.toDateMidnight() ).getDays() + 1 ;
        String [] legends = new String[days];
        DateTimeFormatter dateFormatter =  DateTimeFormat.forPattern(VesselUtils.DATE_FORMAT);
        String [] colors = {"#993b39", "#799040", "#624c7d", "#E5FA7A", "#c37938", "#5376a0","#FA7A7A", "#8faf4c", "#AD7AFA",
                            "00008B", "#F28100", "#F781CE", "#0A5093", "#81F781", "#905500", "#726B6B", "#25A390", "#9F1087", 
                            "#FFCE00","#389AE0", "#DAA38B","#d1ddb9" };

        StringBuilder chart = new StringBuilder();
        String tmp = "";
        String color = "";
        StringBuilder values = new StringBuilder();
        
        if(type == STACK_CHART){
            
           double max = 0;
                    
           chart.append("{");
           chart.append("\"title\": {\"text\": \"Daily Statistis\",");
           chart.append( "\"style\": \"{font-size: 12px; font-weight: bold; color: #000000; font-family: Verdana,Arial,sans-serif; text-align: center;}\"");
           chart.append("},");
           chart.append("\"bg_colour\": \"#ffffff\",");
          
           chart.append("\"elements\": [");
                        // total number of execution times 
                        chart.append("{");
                                chart.append("\"type\": \"bar_stack\",");
                                chart.append("\"alpha\":0.90,");
                                chart.append("\"dot-style\": { \"type\": \"solid-dot\", \"dot-size\": 4, \"halo-size\": 2 ,\"colour\": \"#8aa717\"},");
                                chart.append("\"colours\": [");
                                color = "";
                                for(int i = 0;i < ANOMALY_COUNT ;i ++){
                                    color += "\"" + colors[i] + "\",";
                                }
                                chart.append(color.substring(0, color.length()-1));
                                chart.append("],");
                                chart.append("\"values\": [");
                                 /* create arrays of values and legends */
                                
                                for (int i = 0; i < days; i++) {
                                    /* change day */
                                    if (i != 0) {
                                        start = start.plusDays(1);
                                    }
                                    
                                    String date = start.toString(dateFormatter);
                                    legends[i] = date;
                                    
                                    tmp = "";
                                   
                                    for(int j = 1; j < AnomalyType.values().length; j++ ) {
                                        anomalyCount[j-1] = databaseService.getAnomalyHistoryCountByType(AnomalyType.values()[j], date);
                                    }
                                    int total = 0;
                                    for(int j = 0; j < ANOMALY_COUNT; j++){
                                        tmp += anomalyCount[j] + ",";
                                        total += anomalyCount[j]; 
                                                
                                    }
                                    if(total > max){
                                           max = total;
                                        }
                                   tmp = tmp.substring(0, tmp.length() - 1);
                                   values.append("[" + tmp + "],");
                                  
                               }
                               values.deleteCharAt(values.length()-1);
                               chart.append(values);
                               chart.append("],");                 
                               chart.append("\"tip\": \"[#x_label#], Value [#val#]Total [#total#]\"");
                        chart.append("}");
            chart.append("],");
            chart.append("\"x_axis\": {");
                        chart.append("\"steps:\": 1,");
                        chart.append("\"stroke:\": 3,");
                        chart.append("\"colour\": \"#aaaaaa\",");
                        chart.append("\"grid-colour\": \"#dddddd\",");
                        chart.append("\"labels\": {");
                            chart.append("\"steps:\": 1,");
                            chart.append("\"rotate\": \"45\",");
                            chart.append("\"labels\": [");
                                    tmp = "";
                                    for (String legend : legends) {
                                            tmp += "\"" + legend + "\",";
                                    }
                                    tmp = tmp.substring(0, tmp.length() - 1);
                                    chart.append(tmp);
                            chart.append("]");
                        chart.append("}");
                chart.append("},");
 
                chart.append("\"y_axis\": {");
                       
                        if (max >= 50) {
                            chart.append("\"steps\": 10,");
                        } else if (max >= 20) {
                            chart.append("\"steps\": 5,");
                        } else if (max >= 10) {
                            chart.append("\"steps\": 2,");
                        } else if (max < 10) {
                            chart.append("\"steps\": 1,");
                        }

                        chart.append("\"stroke\": 3,");
                        chart.append("\"colour\": \"#aaaaaa\",");
                chart.append("\"grid-colour\": \"#dddddd\",");
                chart.append("\"min\": 0,");

                chart.append("\"max\": " + (max <5?5 : max));
                chart.append("}");
        chart.append("}"); 
           
        } else if (type == PIE_CHART) {
            String startDateStr = formatter.print(start) ;
            String endDateStr = formatter.print(end);
            chart.append("{");
                chart.append("\"title\": {");
                    chart.append("\"text\": \"Overal Statistics\", ");
                    chart.append("\"style\": \"{font-size: 12px; font-weight: bold; color: #000000; font-family: Verdana,Arial,sans-serif; text-align: center;}\"");
                chart.append("},");
		chart.append("\"x_axis\": null ,");
		chart.append("\"bg_colour\":\"#ffffff\",");
		chart.append("\"elements\": [{");
                    chart.append("\"type\": \"pie\",");
                    chart.append("\"alpha\": 0.9, ");
                    chart.append("\"start-angle\": 35,");
                    chart.append("\"stroke\": 3,");
                    chart.append("\"animate\": [");
                        chart.append("{\"type\": \"fade\"},");
			chart.append("{\"type\": \"bounce\", \"distance\": 5}");
                    chart.append("],");
                           
                                for(int j = 1; j < AnomalyType.values().length; j++ ) {
                                    anomalyCount[j-1] = databaseService.getAnomalyHistoryCountByType(AnomalyType.values()[j],startDateStr,endDateStr);
                                }
                                for(int j = 0; j < anomalyCount.length; j++){
                                 
                                    values.append("{ \"value\": "+ anomalyCount[j] +", \"label\": \"A"+ (j+1) +"\" },");
                                }
                                values.deleteCharAt(values.length()-1);
                                chart.append("\"tip\": \"#val# of #total# #percent# of 100%\",");
                                chart.append("\"colours\": [");
                                    color = "";
                                    for(int i = 0;i < ANOMALY_COUNT ;i ++){
                                        color += "\"" + colors[i] + "\",";
                                    }
                                    chart.append(color.substring(0, color.length()-1));
                                chart.append("],");
                                chart.append("\"values\": ["); 
                                chart.append(values);
                               
                            chart.append("]");
                        
				chart.append("}]");
			chart.append("}");	
        }
    return chart.toString();
    }
   @RequestMapping(value="/get-anomaly-counts",method= RequestMethod.POST)
    public @ResponseBody String getStatisticInterval(String startDate, String endDate, int type) throws Exception {
        int [] anomalyCount = new int[ANOMALY_COUNT];
        StringBuilder aaData = new StringBuilder();
        for(int i = 1; i < AnomalyType.values().length; i++ ) {
            anomalyCount[i-1] = databaseService.getAnomalyHistoryCountByType(AnomalyType.values()[i], startDate, endDate);
        }
        String retrunValue = "";
        if (type == XLS) {
            ArrayList<String[]> report = new ArrayList<String[]>();
            for(int i = 1; i < AnomalyType.values().length; i++ ) {
                report.add(new String[] { AnomalyType.values()[i].name().replace("_", " "), String.valueOf(anomalyCount[i-1])});
            }
            
            String [] headers = {"Anomaly Type", "Count"};
            retrunValue = createXLSReport("Anomaly Counts", headers, report);
        } else {
            for(int i = 1; i < AnomalyType.values().length; i++ ) {
                aaData.append("[\""+ AnomalyType.values()[i].name().replace("_", " ") + "\", " + anomalyCount[i-1]+ " ],");
            }
            aaData.deleteCharAt(aaData.length()-1);
            retrunValue = aaData.toString();
        }
        
        return retrunValue ;
     }
    private String createXLSReport(String title,String[] headers,ArrayList<String[]> report) throws IOException {
         String path = servletContext.getRealPath("/" + TMP_FILE_FOLDER);
        
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm");
		
        File folder = new File(path);
        if (!folder.exists()) {
        	folder.mkdirs();
        }
        Date reportDate = new Date();
        String fileName = formatter.format(reportDate) + "_" + title +".xls";
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(title);

        HSSFCellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        HSSFRow row = sheet.createRow(0);
        HSSFCell[] headerCells = new HSSFCell[headers.length];
        for (int i = 0; i < headers.length; i++) {
            headerCells[i] =  row.createCell((short) i);
            headerCells[i].setCellValue(new HSSFRichTextString(headers[i]));
            headerCells[i].setCellStyle(style);
        }
        
        for (int i = 0; i < report.size(); i++) {
            log.info(report.get(i).length);
            String[] fields = report.get(i);
            row = sheet.createRow(i + 1);
            log.info("length: " +fields.length);
            for(int j = 0; j < fields.length; j++) {
                
                log.info("i:" + i + " j:" + j + " "  + fields[j]);
                 headerCells[j] = row.createCell((short) j);
                 headerCells[j].setCellValue(new HSSFRichTextString(fields[j]));
            }     
        }
       for (int j = 0; j < headers.length; j++) {
           sheet.autoSizeColumn((short)j);
       }
       
        workbook.write(baos);

        FileOutputStream fos = new FileOutputStream(path + "/" + fileName);
        
        baos.writeTo(fos);
        fos.close();
        baos.close();
        
       
        return fileName;
    }
   
    @RequestMapping(value="/get-suspicious-vessels",method= RequestMethod.POST)
    public @ResponseBody String getSuspiciousVessel(String startDate, String endDate, int type) throws Exception {
        StringBuilder aaData = new StringBuilder();
        if (startDate == null || startDate.isEmpty() || endDate == null || endDate.isEmpty())
             return "";
        List<Object[]> vessels = databaseService.getAnomalyHistory(startDate, endDate);
        
       ArrayList<String[]> report = new ArrayList<String[]>();
        for (int i = 0; i < vessels.size(); i++) {
            Object[] array = vessels.get(i);
            String[] fields = new String[array.length];
            
            fields[0] =((String)array[0]).toLowerCase();
            fields[1] = vesselUtils.getVesselType((String)array[1]).name();
            fields[2] = (String)array[2];
            fields[3] = (String)array[3];
            fields[4] =((String)array[4]) == null ? "":((String)array[4]).toLowerCase().replace(";'>","");
            fields[5] =((String)array[5]) == null ? "":((String)array[5]).toLowerCase().replace(";'>","");
            fields[6] =(String)array[6] == null ? "":(String)array[6] + "0";
            fields[7] = AnomalyType.values()[(Integer) array[7]].name().replace("_", " ");
            
            report.add(Arrays.copyOf(fields, fields.length,String[].class));
        }
        
        String returnValue = "";
        if (type == XLS) {
            String [] headers = {"Vessel Name", "Vessel Type", "IMO", "MMSI","Origin", "Destination","Arrival Time", "Anomaly Type"};
            returnValue = createXLSReport("Suspicious Vessels",headers,report);
        } else {
            if (vessels.isEmpty()) {
                return "";
            }
            for (int i = 0; i < report.size(); i++) {
                aaData.append("[");
                String[] array = report.get(i);
                for (int j = 0; j < array.length; j++) {
                    aaData.append("\"" + array[j] + "\",");
                }
                aaData.append("],");
            }
            aaData.deleteCharAt(aaData.length() - 1);
            returnValue = aaData.toString();
        }
        
        return returnValue;
    }
    @RequestMapping(value="/get-xls-file",method= RequestMethod.GET)
    public void getXlsReport(String fileName, HttpServletResponse response) throws IOException {
        String path = servletContext.getRealPath("/" + TMP_FILE_FOLDER);
        File file = new File(path + "/" + fileName);
        if (file.exists()) {
            FileInputStream fis = new FileInputStream(path + "/" + fileName);
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");		

            byte[] outputByte = new byte[4096];
            while(fis.read(outputByte, 0, 4096) != -1){
                    response.getOutputStream().write(outputByte, 0, 4096);
            }
            fis.close();
            response.getOutputStream().close();
            file.delete();
        }
    }    
}

