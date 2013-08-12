<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.bth.anomalydetection.global.ViewConstants" %>
<%@page import="com.bth.anomalydetection.beans.VesselType" %>
<%@page import="com.bth.anomalydetection.beans.AnomalyType" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="initial-scale=1.0, user-scalable=no" />

        <title>Open Data Anomaly Detection System</title>
        <link type="text/css" rel="stylesheet" href="3pp/css/ui-smoothness/jquery-ui-1.8.16.custom.css" />
        <link type="text/css" rel="stylesheet" href="3pp/css/collapser/collapser.css" />
        <link type="text/css" rel="stylesheet" href="3pp/css/dataTables/css/dataTables.css" />
     <style>
            img {
                border: none;
            }
        
            .ui-widget {
                font-size: 0.75em;
                font-family: Verdana,Arial,sans-serif;
            }
            
            .display tr {
/*                height: 30px;*/
                font-size: 0.9em;
            }
            
            .display td {
                border:1px solid #D3D3D3;
            }
            
            .dataTables_empty {
                vertical-align: middle;
            }
      
            #header {
                width: 95%;
                margin: auto;
                height: 100px;
                padding: 0px;
            }
            #header #appInformation {
                margin: 0 10px;
            }
            #header #userInformation {
                margin-right: 10px;
                margin-top: 80px;
                color: #F6F6F6;
                float: right;
            }
            #userInformation a {
                color: #F6F6F6;
            }
            #header h1 {
                float: left;
                margin-top: 30px;
                margin-left: 15px;
            }
             #header img{
                float: left;
                margin-top: 5px;
                height: 90px;
            }
            .my-header {
                color: #F6F6F6;
                border: 1px solid #0B3E6F;
                font-weight: bold;
                background: #0D2D41;/* #003333;*/
            }
            #mainDiv {
                width: 95%;
                height: 100%;
                margin: auto;
                margin-top: 3px;
            }
            #map {
                width: 100%;
                height: 100%;
            }
            tr.even {
                background-color: white;
            }
            tr.odd {
                background-color: #E2E4FF;
            }
            
            .divmargin {
                margin: 10px;
            }
          
            .divheader {
                margin-top: 10px;
                margin-left: 10px;
                margin-right: 10px;
                font-size: 0.75em;
                    /* 0.9*/
            }
            #infoTable tr{
                font-size:8pt;
/*                line-height:1.3em;*/
            }
            .firstCol {
                font-weight: bold;
                width: 130px;
            }
        </style>
        <style>
       .sidecontentpullout {
                        background-color: #0D2D41;/* #003333;*/
                        color: #F6F6F6;
                        padding: 4px 3px;
                        width: 2%;
                        opacity: 1.0 !important;
                        -moz-border-radius-bottomright: 1em;
                        -moz-border-radius-topright: 1em;
                        -webkit-border-bottom-right-radius: 1em;
                        -webkit-border-top-right-radius: 1em;
                        border-bottom-right-radius: 1em;
                        border-top-right-radius: 1em;
                        
                }
                
        .sidecontentpullout:hover {
                background-color: #0D2D41;
                color: #F6F6F6;
        }

        .sidecontent {
                background: url("3pp/css/ui-smoothness/images/ui-bg_flat_75_ffffff_40x100.png") repeat-x scroll 50% 50% #FFFFFF; 
                color: #222222;
                height: auto !Important;
        }

/*        .sidecontent > div > div {
                padding-left: 3em;
        }  */
        
      </style>  
        <script type="text/javascript" src="3pp/js/jquery-1.6.2.min.js"></script>
        <script type="text/javascript" src="3pp/js/jquery-ui-1.8.16.custom.min.js"></script>
        <script type="text/javascript" src="3pp/js/jquery.dataTables.js"></script>
        <script type="text/javascript" src="3pp/js/jquery.sidecontent.js"></script>
        <script type="text/javascript" src="http://maps.googleapis.com/maps/api/js?sensor=false"></script>
        <script type="text/javascript" src="3pp/js/jquery.collapser.js"></script>
        <script type="text/javascript" src="3pp/js/jquery-ui-timepicker-addon.js"></script>
        <script type="text/javascript">
           var statisticAnomalyCountTable = null;
           var statisticSuspeciousVesselTable = null;
           var mapHeight;
           
           var markerBounds;

    function HomeControl(controlDiv, map) {

      // Set CSS styles for the DIV containing the control
      // Setting padding to 5 px will offset the control
      // from the edge of the map
      controlDiv.style.padding = '5px';

      // Set CSS for the control border
      var controlUI = document.createElement('div');
      controlUI.style.backgroundColor = 'white';
      controlUI.style.borderStyle = 'solid';
      controlUI.style.borderWidth = '2px';
      controlUI.style.cursor = 'pointer';
      controlUI.style.textAlign = 'center';
      controlUI.title = 'Click to set the map to Home';
      controlDiv.appendChild(controlUI);

      // Set CSS for the control interior
      var controlText = document.createElement('div');
      controlText.style.fontFamily = 'Arial,sans-serif';
      controlText.style.fontSize = '12px';
      controlText.style.paddingLeft = '4px';
      controlText.style.paddingRight = '4px';
      controlText.innerHTML = '<b>Home</b>';
      controlUI.appendChild(controlText);

      // Setup the click event listeners: simply set the map to
      // Chicago
      google.maps.event.addDomListener(controlUI, 'click', function() {
        map.fitBounds(markerBounds)
      });

    }
           function loadTab(tabId){

               if(tabId == 1){
                   $.ajax({
                            type: 'GET',
                            url: 'datasets.html',
                            success: function(response) { 
                                $('#datasetsDiv').html(response);
                            },
                            error: function(xhr) {
                                alert('failed');
                            }
                        });
                } else if(tabId == 2){
                    $.ajax({
                            type: 'GET',
                            url: 'statistics.html',
                            success: function(response) { 
                                $('#statisticsDiv').html(response);
                            },
                            error: function(xhr) {
                                alert('failed');
                            }
                        });
                }
                else if(tabId == 3){
                    $.ajax({
                            type: 'GET',
                            url: 'setting.html',
                            success: function(response) { 
                                $('#dashboardDiv').html(response);
                            },
                            error: function(xhr) {
                                alert('failed');
                            }
                        });
                }
               
            }
            var mapCtrl = (function() {
               var HEADER_SIZE = 100;
               var map;
               var vesselsInfo;
               var markerArray =[];
               var aaData;
               var dataTable;
               var FIND_ANOMALIES_PERIOD = 2 * 60 * 1000;
               var SHORT_FIND_ANOMALIES_PERIOD = 1 * 60 * 1000;
               var firstLoad = true;
               
               function createInfoWinContent(vesselsInfo,index, anomalyHistory) {
                   var table;
                   var anomalyType = '-';
                   if(anomalyHistory)
                       anomalyType = anomalyHistory.anomalyType.replace(/_/g, ' ');
                   table = '<div style="max-width:312px"><table id="infoTable"><tr style="height:2px"></tr>'
                   table += '<td colspan="2" style="background-color:#0D2D41;padding:4px;color:#FFFFFF;font-size:9pt;font-weight:bold;">' + vesselsInfo.name + '</td></tr>';
                   table += '<tr><td class="firstCol">Vessel Type : </td><td>' + vesselsInfo.type + '</td></tr>';
                   
                   table += '<tr><td class="firstCol">IMO : </td><td style="">' + vesselsInfo.imo + '</td></tr>';
                   table += '<tr><td class="firstCol">MMSI : </td><td >' + vesselsInfo.mmsi + '</td></tr>';
                   table += '<tr><td class="firstCol">Call Sign : </td><td>' + vesselsInfo.callSign + '</td></tr>';
                   table += '<tr><td class="firstCol">Flag : </td><td >' + vesselsInfo.flag + ' <img src="img/flags/' + vesselsInfo.flag + '.png"></td></tr>';
                   if (vesselsInfo.heading == -1)
                    table += '<tr><td class="firstCol">Heading : </td><td> - <img src="img/vessels/' +  vesselsInfo.type.toLowerCase() +'_-1.png" align="top" width="18" height="18"></td></tr>';   
                   else if (vesselsInfo.heading == 360) 
                    table += '<tr><td  class="firstCol">Heading : </td><td>' + vesselsInfo.heading + '° <img src="img/vessels/' +  vesselsInfo.type.toLowerCase() +'_0.png" align="top" width="18" height="18"></td></tr>';
                    else   
                        table += '<tr><td  class="firstCol">Heading : </td><td>' + vesselsInfo.heading + '° <img src="img/vessels/' +  vesselsInfo.type.toLowerCase() +'_'+ vesselsInfo.heading + '.png" align="top" width="18" height="18"></td></tr>';     
                   table += '<tr><td class="firstCol">Average Speed : </td><td >' + vesselsInfo.speedAverage + ' knot</td></tr>';
                   if (vesselsInfo.origin != null)
                    table += '<tr><td class="firstCol">Origin : </td><td>' + vesselsInfo.origin + '</td></tr>';
                   else
                    table += '<tr><td class="firstCol">Origin : </td><td> - </td></tr>'; 
                   if (vesselsInfo.destination != null)
                    table += '<tr><td class="firstCol">Destination : </td><td>' + vesselsInfo.destination + '</td></tr>';
                   else
                    table += '<tr><td class="firstCol">Destination : </td><td> - </td></tr>';
                   if (anomalyType != "-")
                    table += '<tr><td class="firstCol">Anomaly Type : </td><td style="color:red;">' + anomalyType + '</td></tr>';                  
                   else
                    table += '<tr><td class="firstCol">Anomaly Type : </td><td>' + anomalyType + '</td></tr>';   
                   table += '<tr><td class="firstCol">Arrival Time : </td><td>' + vesselsInfo.arrivalTime + '</td></tr>';
                   table += '<tr><td class="firstCol">Info Received Time: </td><td >' + vesselsInfo.infoReceivedTime + '</td></tr>';
                   if (vesselsInfo.type != "PILOT" && vesselsInfo.type != "TUG"){
                    table += '<tr><td><br/><a style="color:blue;" href="javascript:mapCtrl.showTrack('+vesselsInfo.id+ ',' + index+')">Show vessels track</a></td></tr>';
                    table += '<tr><td><a style="color:blue;" href="javascript:mapCtrl.clearTrack('+index+')">Clear vessels track</a></td></tr>';
                   }
                   table += '</table></div>';
                   return table;
               }
               
                function showAnomalies(anomalyHistories) {
                    for (var i in anomalyHistories) {
                        for (var j in markerArray) {
                            if (anomalyHistories[i].vesselId == markerArray[j].id) {
//                                var image = new google.maps.MarkerImage('img/anomaly/single/' +  markerArray[j].type.toLowerCase() +'_'+ markerArray[j].heading + '.png'
//                                          , null
//                                          , null
//                                          , new google.maps.Point(12, 12));
                                var anomalyType = "";
                                var tmp;
                                var image;
//                                for(var k in anomalyHistories[i].anomalies){
//                                if(anomalyHistories[i].anomalies.length > 1)
//                                    image = 'img/anomaly/composite/' +  markerArray[j].type.toLowerCase() +'_'+ markerArray[j].heading + '.png';
                                var row;
                                if (markerArray[j].heading == -1) 
                                    row = 36;
                                else if (markerArray[j].heading == 360) 
                                         row = 0;
                                else
                                    row = Math.floor(markerArray[j].heading / 10);
                                
                                image = new google.maps.MarkerImage("img/medium_vessels.png"
                                      , new google.maps.Size(24, 24)
                                      , new google.maps.Point(24, row * 24)
                                      , new google.maps.Point(12, 12));
                                var extraAnomaly = '';
                                if(anomalyHistories[i].anomalyType == 'SUSPECTED_OF_SMUGGLING'){
                                    extraAnomaly = ' <br/> because of: '
                                    for (var k in anomalyHistories[i].anomalies)
                                    extraAnomaly += ' <br/>• ' + anomalyHistories[i].anomalies[k].anomalyType.replace(/_/g, ' ');
                                }
                                anomalyType = anomalyHistories[i].anomalyType.replace(/_/g, ' ');
//                                    if(anomalyHistories[i].anomalies[k].anomalyType == 'VESSEL_ORDERED_PILOT_NOT_INFORMED_PORT')
//                                        tmp = 'VESSEL_ORDERED_PILOT_ NOT_INFORMED_PORT';
//                                    else 
//                                        tmp = anomalyHistories[i].anomalies[k].anomalyType;
//                                anomalyType += '<li>' + tmp + '</li>'
//                                }
//                                console.log(anomalyType);
                                var checked;
//                                console.log(markerArray[j].alreadyChecked);
                                if(markerArray[j].alreadyChecked == true) {
                                    if (markerArray[j].heading == -1) 
                                        row = 36;
                                    else if (markerArray[j].heading == 360)
                                             row = 0;
                                    else
                                        row = Math.floor(markerArray[j].heading / 10);
                                    
                                    checked = 'checked="checked"';
                                    
                                    image = new google.maps.MarkerImage("img/medium_vessels.png"
                                          , new google.maps.Size(24, 24)
                                          , new google.maps.Point(168, row * 24)
                                          , new google.maps.Point(12, 12));
                                    
//                                    image = new google.maps.MarkerImage('img/anomaly/already-detected/' +  markerArray[j].type.toLowerCase() +'_'+ markerArray[j].heading + '.png'
//                                    , null
//                                    , null
//                                    , new google.maps.Point(12, 12));
                                } else 
                                    checked = '';
                                   
                                 aaData += '[\'<input id="'+ i +'" type="checkbox" disabled="disabled" ' + checked + ' />\',\'' + markerArray[j].name + '\',\'' + 
                                    markerArray[j].type + '\',\''+ markerArray[j].imo + '\',\'' + markerArray[j].mmsi + '\',\'' +
                                    markerArray[j].callSign + '\',\'' + markerArray[j].flag + '\',\'' + markerArray[j].origin + '\',\'' +
                                    markerArray[j].destination + '\',\'' + markerArray[j].arrivalTime + '\',' + 
                                    '\''+ anomalyType + extraAnomaly + '\',\'<img src="img/common/map.png" onclick="mapCtrl.showVesselInfo('+ j +')" style="cursor:pointer"/>\'],';
//                                aaData += '[\'<input id="'+ i +'" type="checkbox" />\',\'' + markerArray[j].name + '\',\'' + 
//                                    markerArray[j].type + '\',\''+ markerArray[j].imo + '\',\'' + markerArray[j].mmsi + '\',\'' +
//                                    markerArray[j].callSign + '\',\'' + markerArray[j].flag + '\',\'' + markerArray[j].origin + '\',\'' +
//                                    markerArray[j].destination + '\',\'' + markerArray[j].arrivalTime + '\',' + 
//                                    '\'<h4 class="demo1">Anomalies</h4><ul style="display:none">'+ anomalyType + 
//                                    '</ul>\',\'<img src="img/common/map.png" onclick="mapCtrl.showVesselInfo('+ j +')"/>\'],';

                                markerArray[j].info =  createInfoWinContent(markerArray[j],j, anomalyHistories[i]);
                                markerArray[j].setIcon(image);
                            }

                        }
                    }
                }

               function showVessels() {
                   
                   markerBounds = new google.maps.LatLngBounds();
                   aaData = '';
                   for (var i in vesselsInfo) {
                       var image;
                       var tooltip;
                       var row;
                       var imgColIndex;
                       if(vesselsInfo[i].type == 'CARGO')
                           imgColIndex = 5;
                       else if(vesselsInfo[i].type == 'PASSENGER' || vesselsInfo[i].type == 'FERRY')
                           imgColIndex = 3;
                       else if(vesselsInfo[i].type == 'TANKER')
                           imgColIndex = 2;
                       else if(vesselsInfo[i].type == 'PILOT')
                           imgColIndex = 0;
                       else if(vesselsInfo[i].type == 'TUG')
                           imgColIndex = 4;
                       
                       if (vesselsInfo[i].heading == -1)
                           row = 36;
                       else if (vesselsInfo[i].heading == 360)
                                row = 0;
                       else
                           row = Math.floor(vesselsInfo[i].heading / 10);
                       
//                       if ((vesselsInfo[i].type == 'PILOT' || vesselsInfo[i].type == 'TUG') && vesselsInfo[i].heading != -1){
                        image = new google.maps.MarkerImage("img/medium_vessels.png"
                              , new google.maps.Size(24, 24)
                              , new google.maps.Point(imgColIndex * 24, row * 24)
                              , new google.maps.Point(12, 12));
                        tooltip = vesselsInfo[i].name;
//                       } else {
//                           image = new google.maps.MarkerImage("img/small_vessels.png"
//                                    , new google.maps.Size(24, 24)
//                                    , new google.maps.Point(imgColIndex * 24, row * 24)
//                                    , new google.maps.Point(12, 12));
//                       }
                                    

                       var point = new google.maps.LatLng(vesselsInfo[i].latitude, vesselsInfo[i].longitude);
                       var size = new google.maps.Size(0,12);
                       var marker = new google.maps.Marker({
                            position: point,
                            map:map,
                            icon:image,
                            title:tooltip
                        });

                        var infoWindow = new google.maps.InfoWindow({
                             pixelOffset:size
                        });
                        marker.id = vesselsInfo[i].id;
                        marker.info = createInfoWinContent(vesselsInfo[i],i);
                        marker.name = vesselsInfo[i].name ;
                        marker.type = vesselsInfo[i].type;
                        marker.imo = vesselsInfo[i].imo ;
                        marker.mmsi = vesselsInfo[i].mmsi;
                        marker.speedAverage = vesselsInfo[i].speedAverage;
                        marker.heading = vesselsInfo[i].heading;
                        marker.callSign = vesselsInfo[i].callSign;
                        marker.flag = vesselsInfo[i].flag ;
                        marker.origin = vesselsInfo[i].origin ;
                        marker.destination = vesselsInfo[i].destination ;
                        marker.arrivalTime = vesselsInfo[i].arrivalTime;
                        marker.infoReceivedTime = vesselsInfo[i].infoReceivedTime;
                        marker.alreadyChecked = vesselsInfo[i].alreadyChecked;

                       marker.track = null;
                       google.maps.event.addListener(marker, 'click', function() {
                           infoWindow.setContent(this.info);
                           infoWindow.open(map,this);
                           
                       });
                       
                       markerArray.push(marker);
                       markerBounds.extend(point);
                   }
                   if (firstLoad == true){
                       map.fitBounds(markerBounds);
                       firstLoad = false;
                   }
                       
                   
                   
                   
               }
               function getWindowHeight() {
                     var height;
                     
                    // the more standards compliant browsers (mozilla/netscape/opera/IE7) use window.innerWidth and window.innerHeight
                    if (typeof window.innerWidth != 'undefined') {
                          height = window.innerHeight
                    }

                    // IE6 in standards compliant mode (i.e. with a valid doctype as the first line in the document)
                    else if (typeof document.documentElement != 'undefined'
                         && typeof document.documentElement.clientWidth !=
                         'undefined' && document.documentElement.clientWidth != 0) {
                           height = document.documentElement.clientHeight;
                    }
                    
                    // older versions of IE
                    else {
                          
                           height = document.getElementsByTagName('body')[0].clientHeight;
                    }
                    return height;
               }
               
               function clearMap() {
                   for(i in markerArray){
                       markerArray[i].setMap(null);
                       mapCtrl.clearTrack(i);
                   }
                   markerArray = new Array();
               }
               
               return {
                   init: function(){
                       $('.side').sidecontent({
                            classmodifier: 'sidecontent',
                            attachto: 'leftside',
                            width: '100%',
                            opacity: '0.85',
                            pulloutpadding: '10',
                            textdirection: 'vertical',
                            defaultBgcolor: '#0D2D41',
                            openMethod:'loadTab'
                        });
                        var bodyWidth = $('body').css('width').replace('px','');
                        var mainDivWidth = $('#mainDiv').css('width').replace('px','');
                        $('.side').css('padding-left', (parseInt(bodyWidth) - parseInt(mainDivWidth))/2 +'px' );
                        $('#sidecontent_1').css('opacity','1');
                        $('#sidecontent_2').css('opacity','1');
                        $('#sidecontent_3').css('opacity','1');
                        dataTable = $('#vesselAnomaliesTable').dataTable({
                                'bJQueryUI': true,
                                'sPaginationType': 'full_numbers',
                                'bAutoWidth': false,
                                'aoColumns':[
                                    {'bSortable': false},
                                    {'bSortable': true},
                                    {'bSortable': true},
                                    {'bSortable': true},
                                    {'bSortable': true},
                                    {'bSortable': true},
                                    {'bSortable': true},
                                    {'bSortable': true},
                                    {'bSortable': true},
                                    {'bSortable': true},
                                    {'bSortable': true},
                                    {'bSortable': false}
                                ],
                                'aaData': eval('{[]}')
                                
                                // To show list of anomalies
                                /* 
                                "fnDrawCallback": function () {
                                     $('.demo1').collapser({
                                        target: 'next',
                                        targetOnly: 'ul',
                                        expandHtml: 'Anomalies',
                                        collapseHtml: 'Anomalies',
                                        expandClass: 'expArrow',
                                        collapseClass: 'collArrow'

                                });
                                }*/
                               
                                
                             });
                       
                        var height = getWindowHeight();
                        var mapHeight = (height - HEADER_SIZE) * 0.96 + 'px';
                        $('#map').css('height', mapHeight );
                          
                                  
                        $('#anomaliesDiv').css('min-height',mapHeight);
                        $('#datasetsDiv').css('min-height',mapHeight);
                        $('#statisticsDiv').css('min-height',mapHeight);
                        $('#dashboardDiv').css('min-height',mapHeight);
                       // $('.side').css('height', (height - HEADER_SIZE) * 0.96 + 'px');
                        var latlng = new google.maps.LatLng(59.584413537048285, 20.63232421875);
                        var myOptions = {
                          zoom: 8,
                          center: latlng,
                          mapTypeId: google.maps.MapTypeId.ROADMAP
                        };
                        map = new google.maps.Map(document.getElementById("map"),
                            myOptions);
                        
                        var homeControlDiv = document.createElement('div');
                        var homeControl = new HomeControl(homeControlDiv, map);

                        homeControlDiv.index = 1;
                        map.controls[google.maps.ControlPosition.TOP_RIGHT].push(homeControlDiv);    
                      
                         $.ajax({
                            type: 'POST',
                            url: '<%= ViewConstants.HOME_PAGE_LOAD_ACTION%>',
                            success: function(response) { 
                               vesselsInfo = response;
                               showVessels();
                               mapCtrl.findAnomalies();
                            },
                            error: function(xhr) {
                                alert('failed');
                            }
                        });
                   },
                   
                   showTrack: function(id, index) {
                       this.clearTrack(index);
                   
                    $.ajax({
                            type: 'POST',
                            url:'<%= ViewConstants.HOME_GET_TRACK_ACTION %>',
                            data: {
                                vesselId:id
                            },
                            success: function(response) { 
                               var path = [];
                               for(i in  response) {

                                  path.push(new google.maps.LatLng(response[i].latitude,response[i].longitude));
                               }
                               path.reverse();

                               var line = new google.maps.Polyline({
                                  path: path,
                                  //strokeColor: '#0D2D41',
                                  strokeColor: '#0000ff',
                                  strokeOpacity: 1.0,
                                  strokeWeight: 2
                                });
                                line.setMap(map);
                                markerArray[index].track = line;
                            },
                            error: function(xhr) {
                                alert('failed');
                            }
                        });
                },
                
                clearTrack: function(index) {
                    if( markerArray[index].track != null)
                        markerArray[index].track.setMap(null);
                },
                
                showVesselInfo: function(index) {
                    google.maps.event.trigger(markerArray[index], 'click');
                },
                showStatistics: function(){
                    $.ajax({
                            type: 'POST',
                            url:'<%= ViewConstants.STATISTICS_GET_STATISTICS_ACTION %>',
                           
                            success: function(response) { 
                               
                            },
                            error: function(xhr) {
                                alert('failed');
                            }
                        });
                },
                findAnomalies: function() {
                    $.ajax({
                            type: 'POST',
                            url: '<%= ViewConstants.HOME_GET_ANOMALIES_ACTION%>',
                            success: function(response) { 
                              if(response[0]!= false) {
                                    vesselsInfo = response[0];
                                    $('#currentDate').text(response[2]);
                                    $('#totalNumVessels').text(response[0].length);
                                    $('#totalNumAnomalyVessels').text(response[1].length);

                                    clearMap();
                                    showVessels();
                                    showAnomalies(response[1]);
                                    if(dataTable != null){
                                        dataTable.fnClearTable();
                                        dataTable.fnAddData(eval('[' + aaData.substring(0,aaData.length-1) + ']'))
                                    }

                                    $('#sidecontent_0_pullout').css('background-color', '#CC2525');
                                                           
                                    setTimeout('mapCtrl.findAnomalies()',FIND_ANOMALIES_PERIOD);
                              } else {
                                    setTimeout('mapCtrl.findAnomalies()', SHORT_FIND_ANOMALIES_PERIOD);
                              }
                            
                            },
                            error: function(xhr) {
                                 setTimeout('mapCtrl.findAnomalies()', SHORT_FIND_ANOMALIES_PERIOD);
                                alert('failed');
                            }
                        });
                      //  
               }
               }
               
            })();
            $(document).ready(function() {
                mapCtrl.init();
            });
        </script>
    </head>

    <body class="ui-widget">
       <div id="header" class="ui-widget ui-widget-content ui-corner-all my-header">
            <div id="appInformation">
                <img src="img/common/logo.png"/>
                <h1 >Open Data Anomaly Detection System</h1>
            </div>
            <div id="userInformation">
                <span>
                    Welcome <sec:authentication property="principal.username" />! &nbsp;| &nbsp; <a href="public-docs/odads-user-guide.pdf" target="_blank">User Guide</a> 
                    &nbsp;| &nbsp; <a href="j_spring_security_logout">Logout</a>
                </span>
            </div>
        </div>
        <div style="width: 93%; position: relative;">
            <div class="side" title="Anomaly" style="height: auto !important">
                <div class ="ui-widget ui-widget-content ui-corner-all" id="anomaliesDiv" >
                    <div class="ui-widget ui-widget-content ui-corner-all divheader " style="padding-bottom: 15px">
                            <div style="margin-left: 10px;">
                                <h3>Anomalies</h3>
                            </div>
                            <table style=" margin-bottom: 5px; margin-left: 10px; margin-top: 5px;">
                                <tbody> 
                                    <tr>
                                        <td>Total Number of Vessels:</td><td id="totalNumVessels"></td>
                                    </tr>
                                    <tr>
                                        <td>Total Number of Suspicious Vessels:</td><td id="totalNumAnomalyVessels"></td>
                                    </tr>
                                    <tr>
                                        <td>Current Date (CET/CEST):</td><td id="currentDate"></td>
                                    </tr>
                                   
                                </tbody>
                            </table>  
                        </div>
                    <div class="divmargin">
                        <table id="vesselAnomaliesTable" class="display" cellpadding="0" cellspacing="0" border="0" >
                            <thead>
                                <tr>
                                    <th width="1%" ></th>
                                    <th width="12%">Vessel Name</th>
                                    <th width="12%">Vessel Type</th>
                                    <th width="5%">IMO</th>
                                    <th width="5%">MMSI</th>
                                    <th width="5%">CallSign</th>
                                    <th width="2%">Flag</th>
                                    <th width="10%">Origin</th>
                                    <th width="10%">Destination</th>
                                    <th width="10%">Arrival Time</th>
<!--                                    <th width="10%">Info Received Time</th>-->
                                    <th width="24%">Anomaly Type</th>
                                    <th width="4%">Info</th>
                                        
                            </thead>
                            <tbody>
                            </tbody>
                        </table> 
                  </div>
                <br />
                <br />
                </div>
            </div>
          
            <div class="side" title="Dataset">
                 <div id="datasetsDiv" class ="ui-widget ui-widget-content ui-corner-all" style="height: auto !important">
                     
                 </div>
            </div>
             <div class="side" title="Reports">
                 <div id="statisticsDiv" class ="ui-widget ui-widget-content ui-corner-all" style="height: auto !important;">
                 </div>

            </div>
             <div class="side" title="Setting">
                 <div id="dashboardDiv" class ="ui-widget ui-widget-content ui-corner-all" style="height: auto !important;">
                 </div>

            </div>
        </div>
        <div id="mainDiv" class="ui-widget-content ui-corner-bottom" >
               
              <div id="map" ></div>
                            
        </div>
        </div>    
    </body>
</html>
