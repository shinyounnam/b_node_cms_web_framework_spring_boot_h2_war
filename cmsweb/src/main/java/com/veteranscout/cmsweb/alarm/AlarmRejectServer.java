package com.veteranscout.cmsweb.alarm;

import com.veteranscout.cmsweb.alarm.*;
import com.veteranscout.cmsweb.util.*;
import java.io.*;
import java.net.*;
import java.sql.*;
import java.lang.*;
import java.beans.*;
import org.json.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


public class AlarmRejectServer {
    
    
    private CommonUtil common;    
    private AlarmReject corpReject;
    private String module_name = "AlarmRejectServer";

    public AlarmRejectServer() {        
        common = new CommonUtil();
        corpReject = new AlarmReject();
    }

    public String send_alarm_json_object(JSONObject jsonObject){
        String result = "";
        
        try
        {
            common.system_log(jsonObject.toString());
            result = SendRejectPushAlaram(jsonObject);
        }
        catch(Exception ex2){
            ex2.printStackTrace();
        }
        
        return result;
    }

    private String SendRejectPushAlaram(JSONObject jsonData)
    {
        String result = "";
        try
        {           

            try
            {
                
                if (jsonData.getString("GBN").equals("REQ") && jsonData.getString("BASE_TYPE").equals("BASE") && jsonData.getString("ID_KEY_TYPE").equals("BOOK_ID"))
                {
                    result = corpReject.SetWorkReject(jsonData);
                    // common.log(module_name,"corpReject.SetWorkReject : BOOK_ID : " + jsonData.BOOK_ID);
                }
                else if (jsonData.getString("W_REQ_GBN").equals("SS") && jsonData.getString("BASE_TYPE").equals("BASE") && jsonData.getString("ID_KEY_TYPE").equals("RMD_ID"))
                {
                    result = corpReject.SetWorkRecommandReject(jsonData);
                    // common.log(module_name,"corpReject.SetWorkRecommandReject : RMD_ID : " + jsonData.RMD_ID);
                }
                else if (jsonData.getString("GBN").equals("SLT") && jsonData.getString("BASE_TYPE").equals("EMC") && jsonData.getString("ID_KEY_TYPE").equals("BOOK_ID"))
                {
                    // corpReject.SetWorkVSReject(jsonData);
                    // common.log(module_name,"corpReject.SetWorkVSReject not reject : BOOK_ID : " + jsonData.BOOK_ID);
                }
                else if (jsonData.getString("GBN").equals("REQ") && jsonData.getString("BASE_TYPE").equals("EMC") && jsonData.getString("ID_KEY_TYPE").equals("I_ID"))
                {
                    result = corpReject.SetVeteranServiceReject(jsonData);
                    // console.log("corpReject.SetVeteranServiceReject : I_ID : " + jsonData.I_ID + " EMP_ID : " + jsonData.EMP_ID);
                    // common.log(module_name,"corpReject.SetVeteranServiceReject : I_ID : " + jsonData.I_ID + " EMP_ID : " + jsonData.EMP_ID);
                }
                else if (jsonData.getString("W_REQ_GBN").equals("VV") && jsonData.getString("BASE_TYPE").equals("EMC") && jsonData.getString("ID_KEY_TYPE").equals("V_I_ID"))                
                {
                    result = corpReject.SetVendorServiceReject(jsonData);
                    //console.log("corpReject.SetEmcWorkItemReject : EMC_ID : " + jsonData.EMC_ID + " EMP_ID : " + jsonData.EMP_ID);
                    // common.log(module_name,"corpReject.SetVendorServiceReject : I_ID : " + jsonData.I_ID + " EMP_ID : " + jsonData.EMP_ID);
                }
                else if (jsonData.getString("GBN").equals("REQ") && jsonData.getString("BASE_TYPE").equals("BASE") && jsonData.getString("ID_KEY_TYPE").equals("SHOW_ID"))                
                {
                    result = corpReject.SetWorkShowReject(jsonData);
                    // common.log(module_name,"corpReject.SetWorkShowReject : SHOW_ID : " + jsonData.SHOW_ID);
                }
                else
                {
                    common.log(module_name,"SendRejectPushAlaram error  : " + jsonData.toString());
                }
            }
            catch (Exception ex1)
            {
                // common.log(module_name,"SetWorkReject error : " + ex.message);
            }
            
            //common.log(module_name,"SendPushAlaram ok : " + JSON.stringify(jsonData));
        }
        catch (Exception ex)
        {
            // common.log(module_name,"SendRejectPushAlaram Error : " + ex.message);
        }
        return result;
    }
}