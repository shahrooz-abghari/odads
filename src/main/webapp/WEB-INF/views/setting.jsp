<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.bth.anomalydetection.global.ViewConstants" %>

<style>
    .smuggling-data label {
        width: 160px;
        float: left;
        
    }
    .smuggling-data {
        margin-bottom: 5px;
    }
    .smuggling-data input.text {
        width: 155px;
    }

</style>

<script type="text/javascript">
   
    var settingCtrl = (function() {
        
        var dataTable;
        var editId = null;
        function removeSmugglingVessel() {
            var isConfirm = confirm('Do you want to remove vessel(s)?');
            if(!isConfirm) {
                return;
            }
            clearData();
            var ids = [];
            $('.smugglingVessel:checked').each(function() {
                ids[ids.length] = parseInt($(this).attr('id'));
            });
            hideError();
            jQuery.ajaxSettings.traditional = true;
            if (ids.length > 0) {
                $.ajax({
                type: 'POST',
                url: '<%= ViewConstants.SETTING_REMOVE_BLACKLIST_ACTION%>',
                data: {
                    ids:ids
                },
                success: function(response) { 
                     if (response.flag == 0){
                        getSmugglingBlackList()
                     }
                },
                error: function(xhr) {
                }
            });
            }
        }
        function hideError() {
             $('#smugglingVesselNameError').hide();
             $('#smugglingImoError').hide(); 
             $('#smugglingCallSignError').hide();
             $('#smugglingSeverError').hide();
        }
        function viewSmugglingVessel() {
             var id =  parseInt($('.smugglingVessel:checked').attr('id')); 
            
             $.ajax({
                 type: 'POST',
                 url: '<%= ViewConstants.SETTING_VIEW_BLACKLIST_ACTION%>',
                 data: {
                  id:id
                },
                success: function(response) { 
                    editId = id;
//                    console.log(response);
                    $('#smugglingVesselName').val(response.vesselName);
                    $('#smugglingImo').val(response.imo);
                    $('#smugglingCallSign').val(response.callSign);
                    if(response.enabled == true)
                        $('#smugglingEnabled').attr('checked', 'checked');
                    
                    $('#smugglingUpdate').show();
                    $('#smugglingAdd').hide();
                    
                },
                error: function(xhr) {
                }
            });
        }
        
        function clearData() {
            editId = null;
            $('#smugglingVesselName').val('');
            $('#smugglingImo').val('');
            $('#smugglingCallSign').val('');
            $('#smugglingEnabled').removeAttr('checked');
            $('#smugglingUpdate').hide();
            $('#smugglingAdd').show();
            $("#smugglingRemove").button('option', 'disabled', true);
            $("#smugglingEdit").button('option', 'disabled', true);
            
        }
      
        function addSmugglingVessel() {
            hideError();
            var vesselName = $('#smugglingVesselName').val();
            var imo = $('#smugglingImo').val();
            var callSign = $('#smugglingCallSign').val();
            var isValid = true;
            var enabled = $('#smugglingEnabled').is(':checked');
            
            if(vesselName == ''){
                 $('#smugglingVesselNameError').show();
                 isValid = false;
            }
              
            if(imo == ''){
               $('#smugglingImoError').show(); 
               isValid = false;
            }
            if (callSign =='') {
                $('#smugglingCallSignError').show();
                 isValid = false;
            }
            var url;
            if (editId == null) {
                url =  '<%= ViewConstants.SETTING_ADD_BLACKLIST_ACTION%>';
            } else {
                url =  '<%= ViewConstants.SETTING_UPDATE_BLACKLIST_ACTION%>';
            }
            if (isValid) {
                 $.ajax({
                 type: 'POST',
                 url:url,
                 data: {
                    id:editId,
                    vesselName :vesselName, 
                    imo: imo,
                    callSign:callSign,
                    enabled:enabled
                },
                success: function(response) { 
                    if(response.flag == 1){
                        $('#smugglingSeverError').html(response.message);
                        $('#smugglingSeverError').show();
                    } else {
                      
                       clearData();
                       getSmugglingBlackList();
                    }
                },
                error: function(xhr) {
                }
            });
            }
            
        }
        function getSmugglingBlackList() {
              $.ajax({
                type: 'POST',
                url: '<%= ViewConstants.SETTING_GET_BLACKLIST_ACTION%>',
                success: function(response) { 
                     if(dataTable != null){
                        dataTable.fnClearTable();
                        dataTable.fnAddData(eval('[' + response + ']'))
                    }
                    
                    
                },
                error: function(xhr) {
                }
            });
        }
        return {
            init: function(){
                                 
                 dataTable = $('#blackListTable').dataTable({
                                'bJQueryUI': true,
                                'sPaginationType': 'full_numbers',
                                'bAutoWidth': false,
                                'aoColumns':[
                                    {'bSortable': false},
                                    {'bSortable': true},
                                    {'bSortable': true},
                                    {'bSortable': true} ,
                                    {'bSortable': false} 
                                ],
                                'aaData': eval('{[]}')
                               
                                
                       });
                  $('#smugglingAdd, #smugglingUpdate').button().click(function(){
                     addSmugglingVessel();
                  });
                   $('#smugglingEdit').button().click(function(){
                     viewSmugglingVessel();
                  });
                  
                  $('#smugglingRemove').button().click(function(){
                     removeSmugglingVessel();
                  });
                  
                 getSmugglingBlackList();
                 
              
            },
             toggle: function(){
                 var size = $('.smugglingVessel:checked').length;
                 if (size == 1) {
                     $("#smugglingRemove").button('option', 'disabled', false);
                     $("#smugglingEdit").button('option', 'disabled', false);
                     
                 } else if (size > 1) {
                     $("#smugglingRemove").button('option', 'disabled', false);
                     $("#smugglingEdit").button('option', 'disabled', true);
                     
                 } else {
                     $("#smugglingRemove").button('option', 'disabled', true);
                     $("#smugglingEdit").button('option', 'disabled', true);
                    
                 }
            },
            toggleAll: function(){
                if($('#selectAllBlackList').is(':checked'))
                    $('.smugglingVessel').attr('checked', true);                          
                else                                
                    $('.smugglingVessel').attr('checked', false);
                this.toggle();
            }
         }
    })();
    $(document).ready(function() {
        settingCtrl.init();

    });
</script>
<div class="ui-widget ui-widget-content ui-corner-all divheader" style="padding-left: 10px; padding-bottom: 20px;">
    <div>
        <h3>Suspected of Smuggling</h3>
    </div>
           
    <div style="width: 400px;">
        
        <div class="smuggling-data">
            <label for="smugglingVesselName">Vessel Name</label>
            <input type="text" name="smugglingVesselName" id="smugglingVesselName" value="" /> <span id="smugglingVesselNameError" style="display: none;color: red"> Mandatory field is missing</span>
        </div>    
        <div class="smuggling-data">
            <label for="smugglingImo">IMO</label>
            <input type="text" name="smugglingImo" id="smugglingImo" value="" /><span id="smugglingImoError" style="display: none;color: red"> Mandatory field is missing</span>
        </div>
        <div class="smuggling-data">
            <label for="smugglingCallSign">CallSign</label>
            <input type="text" name="smugglingCallSign" id="smugglingCallSign" value="" /><span id="smugglingCallSignError" style="display: none;color: red"> Mandatory field is missing</span>
        </div>
        <div class="smuggling-data">
            <label for="smugglingEnabled">Enabled</label>
            <input type="checkbox" name="smugglingEnabled" id="smugglingEnabled" value="" />
        </div>
        <div style="clear: both">
            <span id="smugglingSeverError" style="display: none;color: red"></span>
        </div>  
         
   </div>   
    <label style="width:160px;float:left;display:inline-block;padding-top:10px">&nbsp;</label>
    
   <div >
        <input type="button" name="smugglingAdd" id="smugglingAdd" value="Add" /> 
        <input type="button" name="smugglingUpdate" id="smugglingUpdate" value="Update" style="display: none" /> 
   </div>
</div>
<div class="divmargin">
    <div style="margin-bottom: 5px;">
    <input type="button" name="smugglingRemove" id="smugglingRemove" value="Remove" disabled="disabled" /> 
    <input type="button" name="smugglingEdit" id="smugglingEdit" value="Edit"  disabled="disabled"/> 
    </div>
    <table id="blackListTable" class="display" cellpadding="0" cellspacing="0" border="0" >
    <thead>
        <tr>
            <th width="3%"><input id="selectAllBlackList" type="checkbox" onclick="settingCtrl.toggleAll()"/></th>
            <th width="34%">Vessel Name</th>
            <th width="30%">IMO</th>
            <th width="30%">Call Sign</th>
            <th width="3%">Enabled</th>
            
            <!--th width="10%">Current Date</th-->
    </tr>
    <tbody>
    </tbody>
    </table> 
</div>