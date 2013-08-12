<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.bth.anomalydetection.global.ViewConstants" %>
<!DOCTYPE html>

<script type="text/javascript" src="3pp/ofc/open_flash_chart.js"></script>
<script type="text/javascript" src="3pp/ofc/ofc.js"></script>
<script type="text/javascript" src="3pp/ofc/swfobject.js"></script>
<script type="text/javascript" src="3pp/ofc/json2.js"></script>
<style>
    .color {
        width: 10px;
        height: 10px; 
        display: inline-block
    }
    .date-range {
        display: inline-block;
        float: left;
        width: 390px;
    }
    .div-first-button div{
         display: inline-block;
    }
    .div-button label {
        cursor: pointer;
        float: right;
        left: 5px;
        position: relative;
        text-decoration: underline;
        top: 10px;
        width: 185px;
    }
    .div-button img {
        cursor: pointer;
    }
    .div-button {
        display: inline-block;
        margin-left: 36px;
        margin-bottom: 5px;
        width:500px;
    }
/*    .div-button div {
        width: 230px;
        display: inline-block;
    }*/
    .div-other-button div{
/*         margin-left: 400px;*/
        display: inline-block;
/*         margin-bottom: 5px;*/
    }
    .section-div {
        padding-left: 10px;
        padding-bottom: 20px;
    }
    .chart {
        width: 60%;
        float: left;
        display: none;
        height: 400px;
    }
    .legend {
        float: right;
        width: 32%;
        height:100%;
        margin-top: 40px;
        display: none;
    }
    
</style>
<script type="text/javascript" >
    
    var statisticCtrl = (function () { 
        var XLS = 1;
        function getAnomalyCounts(type) {
            hideResult();
            var startDate = $('#reportStartTime').val();
            var endDate =$('#reportEndTime').val();
            if(startDate == '' || endDate == '' ) {
                 $('#reportTimeError').show();
                 return;
            }
           $('#statisticProgress').show();
            $.ajax({
                type: 'POST',
                url: '<%= ViewConstants.STATISTICS_GET_ANOMALY_COUNTS_ACTION%>',
                data: {
                    startDate:  startDate ,
                    endDate: endDate,
                    type:type
                        
                },
                success: function(response) { 
                     if (type == XLS) {
                        getXLSFile(response);
                        $('#statisticProgress').hide();
                    } else {
                        $('#statisticProgress').hide();
                        $('#anomalyCountsDiv').show();
                        if(statisticAnomalyCountTable != null){
                            statisticAnomalyCountTable.fnClearTable();
                            statisticAnomalyCountTable.fnAddData(eval('[' + response + ']'))
                        }
                    }
                },
                error: function(xhr) {
                    $('#statisticProgress').hide();
                }
            });
        
        }
        function getSuspiciousVessels(type) {
            
            hideResult();
            var startDate = $('#reportStartTime').val();
            var endDate =$('#reportEndTime').val();
            if(startDate == '' || endDate == '' ) {
                 $('#reportTimeError').show();
                 return;
            }
            $('#statisticProgress').show();
             $.ajax({
                type: 'POST',
                url: '<%= ViewConstants.STATISTICS_GET_SUSPICIOUS_VESSELS_ACTION%>',
                data: {
                    startDate: startDate,
                    endDate: endDate,
                    type:type
                        
                },
                success: function(response) { 
                    if (type == XLS) {
                        getXLSFile(response);
                        $('#statisticProgress').hide();
                    } else {
                         $('#suspeciousVesselsDiv').show();
                         $('#statisticProgress').hide();
                        if(statisticSuspeciousVesselTable != null){
                            statisticSuspeciousVesselTable.fnClearTable();
                            statisticSuspeciousVesselTable.fnAddData(eval('[' + response + ']'))
                        }
                    }
                    
                },
                error: function(xhr) {
                    $('#statisticProgress').hide();
                }
            });
        }
        function getXLSFile(fileName) {
           
            window.open('<%= ViewConstants.STATISTICS_GET_XLS_FILE_ACTION %>?fileName='+ fileName, 'Download');
                
        }
        function hideResult() {
             $('#chart1').hide();
             $('#chart2').hide();
             $('#legend').hide();
             $('#suspeciousVesselsDiv').hide();
             $('#anomalyCountsDiv').hide();
             $('#reportTimeError').hide();
             $('#graphicTimeError').hide();
        }
        function getDailyStatistics() {
            hideResult();  
            var startDate =  $('#graphicStartTime').val();
            var endDate = $('#graphicEndTime').val();
            if(startDate == '' || endDate == '' ) {
                 $('#graphicTimeError').show();
                 return;
            }
            $('#chart1').show();
            $('#legend').show();
            
            var so1 = new SWFObject("3pp/ofc/open-flash-chart.swf", "chart", "100%", "400", "9.0.0");
            var start =$('#graphicStartTime').datetimepicker('getDate').getTime();
            var end =$('#graphicEndTime').datetimepicker('getDate').getTime();
            so1.addVariable("data-file", encodeURI("statistics/get-statistics.html?params=1-" + start +"-" + end));
            so1.addVariable("allowScriptAccess", "always");
            so1.write("chart1");
         
        
        }
        function getOverallStatistics(){
            hideResult();
            var startDate =  $('#graphicStartTime').val();
            var endDate = $('#graphicEndTime').val();
            if(startDate == '' || endDate == '' ) {
                 $('#graphicTimeError').show();
                 return;
            }
            $('#chart2').show();
            $('#legend').show();
             var so = new SWFObject("3pp/ofc/open-flash-chart.swf", "chart", "100%", "300", "9.0.0");
            var start =$('#graphicStartTime').datetimepicker('getDate').getTime();
            var end =$('#graphicEndTime').datetimepicker('getDate').getTime();
            so.addVariable("data-file", "statistics/get-statistics.html?params=2-" + start +"-" + end);
            so.addVariable("allowScriptAccess", "always");
            so.write("chart2")
        }
        return {
            init: function() {
             $('#reportStartTime').datetimepicker({
                     "dateFormat": 'yy-mm-dd' 
             });
             $('#reportEndTime').datetimepicker({
                      "dateFormat": 'yy-mm-dd' 
             });
             
             $('#graphicStartTime').datetimepicker({
                     "dateFormat": 'yy-mm-dd' 
             });
             $('#graphicEndTime').datetimepicker({
                      "dateFormat": 'yy-mm-dd' 
             });
              
//             if(statisticAnomalyCountTable == null) {
                statisticAnomalyCountTable = $('#statisticAnomaliesTable').dataTable({
                    'bJQueryUI': true,
                    'sPaginationType': 'full_numbers',
                    'bAutoWidth': false,
                    'aaData': eval('{[]}')
                });
//            } 
//             if(statisticSuspeciousVesselTable == null) {
                statisticSuspeciousVesselTable = $('#statisticVesselsTable').dataTable({
                    'bJQueryUI': true,
                    'sPaginationType': 'full_numbers',
                    'bAutoWidth': false,
                    'aaData': eval('{[]}')
                });
//            } 
            $('#getAnomalyCounts').button().click(function(){
                     getAnomalyCounts();
            });
            $('#getSuspiciousVessels').button().click(function(){
                     getSuspiciousVessels();
            });
              
             $('#getOveralStatistics').button().click(function(){
                     getOveralStatistics();
            });
            $('#getDailyStatistics').button().click(function(){
                     getDailyStatistics();
            });
           
            var width = $('#resultDiv').css('width');
            width = width.substring(0,width.length-2);
            $('#statisticProgress').css('margin-left', width/2);
        },
        getDailyStatistics:function(type)  {
            getDailyStatistics(type);
        },
        getOverallStatistics:function(type)  {
            getOverallStatistics(type);
        },
        getSuspiciousVessels:function(type)  {
           getSuspiciousVessels(type);
        },
        getAnomalyCounts:function(type)  {
           getAnomalyCounts(type);
        }     
        }
    })();
    $(document).ready(function() {
      statisticCtrl.init(); 
    });
        
 </script>
<!--<div style="text-align: right;margin-top: 10px;margin-right: 20px"><a href="javascript:statisticCtrl.showStatisticsTable()">Show Statistics in Table</a></div>-->
    
<div class="ui-widget ui-widget-content ui-corner-all divheader section-div" >
    <h3>Reports</h3>
    <div class="date-range">
        <label for="reportStartTime">From:</label>
        <input type="text" name="reportStartTime" id="reportStartTime" value="" readonly="readonly"/>
        <label for="reportEndTime">To:</label>
        <input type="text" name="reportEndTime" id="reportEndTime" value="" readonly="readonly"/>
        <span id="reportTimeError" style="display: none;color: red"> Mandatory field is missing</span>
    </div>
    <div class="div-button">
    <div class="div-first-button">
        <div>
            <img  src="img/common/table-icon.png"  onclick="statisticCtrl.getAnomalyCounts(2)"/>
            <label onclick="statisticCtrl.getAnomalyCounts(2)">View Anomaly Counts</label>
        </div>
        <div>
            <img onclick="statisticCtrl.getAnomalyCounts(1)" src="img/common/table-excel-icon.png"  />
            <label onclick="statisticCtrl.getAnomalyCounts(1)">Get Anomaly Counts (XLS)</label>
        </div>
    </div>
    <div class="div-other-button">
        <div>
            <img onclick="statisticCtrl.getSuspiciousVessels(2)" src="img/common/table-icon.png" onclick=""/>
            <label onclick="statisticCtrl.getSuspiciousVessels(2)">View Suspicious Vessels</label>
        </div>
        <div>
            <img onclick="statisticCtrl.getSuspiciousVessels(1)" src="img/common/table-excel-icon.png" />
            <label onclick="statisticCtrl.getSuspiciousVessels(1)">Get Suspicious Vessels (XLS)</label>
        </div>
    </div>
        </div>
</div>

<div class="ui-widget ui-widget-content ui-corner-all divheader section-div" >
    <h3>Graphics</h3>
    <div class="date-range">
        <label for="graphicStartTime">From:</label>
        <input type="text" name="graphicStartTime" id="graphicStartTime" value="" readonly="readonly"/>
        <label for="graphicEndTime">To:</label>
        <input type="text" name="graphicEndTime" id="graphicEndTime" value="" readonly="readonly"/>
        <span id="graphicTimeError" style="display: none;color: red"> Mandatory field is missing</span>
    </div>
  
    <div class="div-button div-first-button">
        <div>
            <img onclick="statisticCtrl.getOverallStatistics()" src="img/common/chart-pie-icon.png" />
            <label onclick="statisticCtrl.getOverallStatistics()">Show Overall Statistics</label>
        </div>
        <div>
            <img onclick="statisticCtrl.getDailyStatistics()" src="img/common/chart-bar-icon.png" />
            <label onclick="statisticCtrl.getDailyStatistics()">Show Daily Statistics</label>
        </div>
    </div>
</div>
<div id="resultDiv" class="ui-widget ui-widget-content ui-corner-all divheader" style="padding-left: 10px;margin-bottom: 10px;padding-right: 10px" >
<img id="statisticProgress" src="img/common/progress.gif" style="margin-top: 100px;display: none" />
<div style="min-height:410px;margin-top: 10px;margin-bottom:10px">
    <div id="anomalyCountsDiv" style="margin-top: 10px;display: none">
        <table id="statisticAnomaliesTable" class="display" cellpadding="0" cellspacing="0" border="0" >
            <thead>
                <tr>
                    <th width="80%">Anomaly Type</th>
                    <th width="20%">Count</th>                
                </tr>
            </thead>
            <tbody>
            </tbody>
        </table> 
    </div>
    <div id="suspeciousVesselsDiv" style="margin-top: 10px;display: none">
        <table id="statisticVesselsTable" class="display" cellpadding="0" cellspacing="0" border="0" >
            <thead>
                <tr>
                    <th width="10%">Vessel Name</th>
                    <th width="10%">Vessel Type</th>
                    <th width="5%">IMO</th>
                    <th width="5%">MMSI</th>
                    <th width="10%">Origin</th>
                    <th width="10%">Destination</th>
                    <th width="10%">Arrival Time</th>
                    <th width="15%">Anomaly Type</th>
                </tr>
            </thead>
            <tbody>
            </tbody>
        </table> 
    </div>
    <div id="chart1" class="chart"></div>
    <div id="chart2" class="chart"></div>
    <div id="legend" class="legend">
        <div ><span class="color" style="background-color: #993b39;"></span>&nbsp;A1&nbsp;&nbsp;&nbsp;VESSEL NOT INFORMED PORT</div>
        <div ><span class="color" style="background-color: #799040;"></span>&nbsp;A2&nbsp;&nbsp;&nbsp;ARRIVAL TIME MISMATCHED</div> 
        <div ><span class="color" style="background-color: #624c7d;"></span>&nbsp;A3&nbsp;&nbsp;&nbsp;VESSEL ENTERED PORT WITHOUT NOTICE</div>
        <div ><span class="color" style="background-color: #E5FA7A;"></span>&nbsp;A4&nbsp;&nbsp;&nbsp;VESSEL NOT USED PILOT</div>
        
        <div ><span class="color" style="background-color: #c37938;"></span>&nbsp;A5&nbsp;&nbsp;&nbsp;UNUSUAL TRIP PATTERN</div>
        <div ><span class="color" style="background-color: #5376a0;"></span>&nbsp;A6&nbsp;&nbsp;&nbsp;VESSEL NOT LEFT PORT</div>
        <div ><span class="color" style="background-color: #FA7A7A;"></span>&nbsp;A7&nbsp;&nbsp;&nbsp;VESSEL NOT ENTERED PORT </div>
        <div ><span class="color" style="background-color: #8faf4c;"></span>&nbsp;A8&nbsp;&nbsp;&nbsp;VESSEL ORDERED PILOT NOT INFORMED PORT </div>
        <div ><span class="color" style="background-color: #AD7AFA;"></span>&nbsp;A9&nbsp;&nbsp;&nbsp;VESSEL MOORED PORT </div>
        <div ><span class="color" style="background-color: #00008B;"></span>&nbsp;A10&nbsp;SUSPECTED OF SMUGGLING</div>
        <br/>

        <div>
            <span class="color" style="background-color: #F28100;"></span>&nbsp;A11&nbsp;<-A1,A5
            &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
            <span class="color" style="background-color: #F781CE;"></span>&nbsp;A12&nbsp;<-A2,A5
        </div>
        <div >
            <span class="color" style="background-color: #0A5093;"></span>&nbsp;A13&nbsp;<-A7,A5
            &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
            <span class="color" style="background-color: #81F781;"></span>&nbsp;A14&nbsp;<-A8,A5
        </div>
        
        <div >
            <span class="color" style="background-color: #905500;"></span>&nbsp;A15&nbsp;<-A3,A6
            &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
            <span class="color" style="background-color: #726B6B;"></span>&nbsp;A16&nbsp;<-A2,A4
        </div>
        
        <div>
            <span class="color" style="background-color: #25A390;"></span>&nbsp;A17&nbsp;<-A4,A5
            &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
            <span class="color" style="background-color: #9F1087;"></span>&nbsp;A18&nbsp;<-A7,A4
        </div>
        
        <div>
            <span class="color" style="background-color: #FFCE00;"></span>&nbsp;A19&nbsp;<-A8,A4
            &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
            <span class="color" style="background-color: #389AE0;"></span>&nbsp;A20&nbsp;<-A2,A4,A5
        </div>
        
        <div>
            <span class="color" style="background-color: #DAA38B;"></span>&nbsp;A21&nbsp;<-A7,A4,A5
            &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
            <span class="color" style="background-color: #d1ddb9;"></span>&nbsp;A22&nbsp;<-A8,A4,A5
       </div>
   
</div>
 
 
</div>

 
