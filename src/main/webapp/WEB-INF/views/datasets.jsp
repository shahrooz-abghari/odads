<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.bth.anomalydetection.global.ViewConstants" %>

<style>
    /* css for timepicker */
    .ui-timepicker-div .ui-widget-header { margin-bottom: 8px; }
    .ui-timepicker-div dl { text-align: left; }
    .ui-timepicker-div dl dt { height: 25px; margin-bottom: -25px; }
    .ui-timepicker-div dl dd { margin: 0 10px 10px 65px; }
    .ui-timepicker-div td { font-size: 90%; }
    .ui-tpicker-grid-label { background: none; border: none; margin: 0; padding: 0; }
    
    .dataTables_processing {
            border: medium none;
            color: black;
            font-weight: bold;
    }
</style>

<script type="text/javascript">
   
    var datasetCtrl = (function() {
        
        var dataTable;
        
        return {
            init: function(){
                
                 $('#startTime').datetimepicker({
                     "dateFormat": 'yy-mm-dd' 
                 });
                 $('#endTime').datetimepicker({
                      "dateFormat": 'yy-mm-dd' 
                 });
               
                 dataTable = $('#portsTable').dataTable({
                                'bServerSide': true,
                                'bProcessing': true,
                                'bJQueryUI': true,
                                'sPaginationType': 'full_numbers',
                                'bAutoWidth': false,
                                //'bLengthChange': false,
                               // "iDisplayLength": 10,
                                "sAjaxSource": '<%= ViewConstants.DATASETS_GET_ACTION%>',
                                'fnServerData' : function ( url, data, callback ) {
                                    data.push( { "name": "startTime", "value": $('#startTime').val() } );
                                    data.push( { "name": "endTime", "value": $('#endTime').val() } );
                                     $.ajax( {
					"url": url,
					"data": data,
					"success": function (json) {
						callback( json );
                                                 $('#refresh').removeAttr('disabled');
                                                 $('#refresh').removeClass( 'ui-state-disabled' );
					},
					"dataType": "json",
					"cache": false,
					"error": function (xhr, error, thrown) {
						if ( error == "parsererror" ) {
							alert( "DataTables warning: JSON data from server could not be parsed. "+
								"This is caused by a JSON formatting error." );
						}
					}
				} );
			} 
                        
                       });
                  $('#refresh').button().click(function(){
                      $('#refresh').attr('disabled', 'disabled').addClass( 'ui-state-disabled' );                     
                      dataTable.refresh();
                      
                       
                  })
                  
              $('#portsTable_filter input')
                .unbind('keypress keyup')
                .bind('keypress keyup', function(e){
                  if ((e.keyCode == 8 || e.keyCode== 46) && $(this).val().length == 0)
                    dataTable.fnFilter($(this).val());
                  else if (e.keyCode != 13 ) return;
                  else dataTable.fnFilter($(this).val());
                });
            
            }
        }
    })();
    $(document).ready(function() {
        datasetCtrl.init();

    });
</script>

<div class="ui-widget ui-widget-content ui-corner-all divheader" style="padding-left: 10px;padding-bottom:20px">
     <div>
        <h3>Ports and Pilot Data</h3>
    </div>
    <label for="startTime">From:</label>
    <input type="text" name="startTime" id="startTime" value="" readonly="readonly"/>
    <label for="endTime">To:</label>
    <input type="text" name="endTime" id="endTime" value="" readonly="readonly"/>
    &nbsp;&nbsp;
    <input type="button" name="refresh" id="refresh" value="Show data" />
</div>

<div class="divmargin">
    <table id="portsTable" class="display" cellpadding="0" cellspacing="0" border="0" >
    <thead>
        <tr>
            <th width="10%">Vessel Name</th>
            <th width="10%">Vessel Type</th>
            <th width="10%">Company Name</th>
            <th width="15%">Origin</th>
            <th width="15%">Destination</th>
            <th width="10%">Time</th>
            <th width="10%">Status</th>
            <th width="10%">Data Source</th>
            <!--th width="10%">Current Date</th-->
    <tbody>
    </tbody>
    </table> 
</div>