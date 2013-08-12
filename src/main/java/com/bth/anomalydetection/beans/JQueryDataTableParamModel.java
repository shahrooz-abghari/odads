package com.bth.anomalydetection.beans;

public class JQueryDataTableParamModel {


    // Request sequence number sent by DataTable, same value must be returned in response  
    public String sEcho;

    // Text used for filtering
    public String sSearch;
    
    // Number of records that should be shown in table
    public int iDisplayLength;
    
    // First record that should be shown(used for paging)
    public int iDisplayStart;
    
    // Number of columns in table
    public int iColumns;	

    // Number of columns that are used in sorting
    public int iSortingCols;
    
    // Index of the column that is used for sorting
    public int iSortColumnIndex;
    
    // Sorting direction "asc" or "desc"
    public String sSortDirection;

    // Comma separated list of column names
    public String sColumns;
}
