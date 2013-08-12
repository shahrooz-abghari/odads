<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <!-- css -->
        <link type="text/css" rel="stylesheet" href="3pp/css/ui-smoothness/jquery-ui-1.8.16.custom.css" />
        <style>
                 img {
                border: none;
            }
        
            .ui-widget {
                font-size: 0.75em;
                font-family: Verdana,Arial,sans-serif;
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
                background:#0D2D41;/* #003333;*/
            }
            #mainDiv {
                width: 95%;
                height: 100%;
                margin: auto;
                margin-top: 3px;
            }
           
            .ui-state-error-text {
                margin-top: 15px;
            }
            
            #loginForm {
                margin-top: 300px;
            }
            
            #loginFormTable {
                margin: auto;
                height: 238px;
                width: 400px;
                background-color: #FBFBFB;
                border-color: #E5E5E5;
                border-style: solid;
                border-width: 1px;
            }
            
            #submitButton{
                margin-left:80px;
                text-shadow: 0 -1px 1px rgba(51, 51, 51, 0.2);
                box-shadow: 0 -1px 3px rgba(152, 152, 152, 0.5);
                cursor: pointer;  
            }
            
        </style>
       
        <!-- javascript -->

        <script type="text/javascript" src="3pp/js/jquery-1.6.2.min.js"></script>
        <script type="text/javascript" src="3pp/js/jquery-ui-1.8.16.custom.min.js"></script>
        <script type="text/javascript">
            function getWindowHeight() {
                     var height;
                     
                    // the more standards compliant browsers (mozilla/netscape/opera/IE7) use window.innerWidth and window.innerHeight
                    if (typeof window.innerWidth != 'undefined') {
                          height = window.innerHeight
                    }

                    // IE6 in standards compliant mode (i.e. with a valid doctype as the first line in the document)
                    else if (typeof document.documentElement != 'undefined' && typeof document.documentElement.clientWidth !=
                         'undefined' && document.documentElement.clientWidth != 0) {
                           height = document.documentElement.clientHeight;
                    }
                    
                    // older versions of IE
                    else {
                          
                           height = document.getElementsByTagName('body')[0].clientHeight;
                    }
                    return height;
               }
               
            $(document).ready(function(){
                
                $('#submitButton').button();
                var height = getWindowHeight();
                $('#loginForm').css('margin-top', (height - 100) * 0.20 + 'px');
                $('#mainDiv').css('height', (height - 100)+ 'px');
                $("#submitButton").button().addClass('ui-button.loginButton');

            })
        </script>
    </head>
    <body class="ui-widget">
        <div id="header" class="ui-widget ui-widget-content ui-corner-all my-header">
            <div id="appInformation">
                <img src="img/common/logo.png"/>
                <h1 >Open Data Anomaly Detection System</h1>
            </div>
            
        </div>
        <div id="mainDiv" class="ui-widget ui-widget-content ui-corner-all" >
            
            <form id="loginForm" action="j_spring_security_check" method="post">
                <div id="loginFormTable" class="ui-corner-all" >
                    <div   style="padding: 25px">
                        <h2 style="color:#666666;margin-bottom: 27px">ODADS Login</h2>

                        <div style="margin-bottom: 10px">
                            <label for="usernameField" style="width:75px;display:inline-block;color:#666666">Username:</label>
                            <input id="usernameField" type="text" name="j_username" class="text ui-widget-content ui-corner-all" style="width:265px" />
                        </div>

                        <div style="margin-bottom: 20px">
                            <label for="passwordField" style="width:75px;display:inline-block;color:#666666">Password:</label>
                            <input id="passwordField" type="password" name="j_password" class="text ui-widget-content ui-corner-all" style="width:265px"/>
                        </div>

                        <input id="submitButton" type="submit" value="Login"/>    
                        <c:choose>
                            <c:when test="${!empty param.login_error}">
                                <div class="ui-state-error-text">The user name or password is incorrect!</div>                      
                            </c:when>
                        </c:choose>
                    </div>
                </div>
            </form>
        </div>
    </body>
</html>