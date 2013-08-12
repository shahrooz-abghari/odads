##Open Data Anomaly Detection System Project

###Introduction
In collaboration with the Swedish Coastguard at Karlskrona an anomaly detector system for finding suspicious activities in seas based on open data and expert rules is implemented in order to increase the maritime safety and security. The aforementioned system is called Open Data Anomaly Detection System (ODADS).

ODADS consists of three modules:

- Data Collector
- Anomaly Detector
- Display Client (Code in this repository)

#####Data Collector
The Data Collector module is responsible for collecting open data from the Internet, preprocessing and storing the data in a database. The data can be related to vessel traffic (such as AIS reports, ports and pilots timetables), vessel characteristics, ports equipments and facilities, etc. 

#####Anomaly Detector
The Anomaly Detector module analyses the available data (open and closed data) and detects possible anomalies by utilizing both knowledge-driven and data-driven techniques.

#####Display Client
The Display Client module is the graphical user interface of ODADS which is a web-based application.

More information about this project can be found at [Open Data for Anomaly Detection in Maritime Surveillance](http://www.bth.se/fou/cuppsats.nsf/1d345136c12b9a52c1256608004f0519/2bd92c1db26aa75fc1257ac4006d6cc1!OpenDocument).

#####Building the project
Building the project
The project is a multi-module Maven project. To build the whole project, just run `mvn install` from the root directory.
The user name and password of the database is hard-coded in the file `persistence.xml`. 
The dump of the database is also enclosed to this repository.  
The default user name and password for loging in to the system.
 
    User name: guest
    Password: odads2012
