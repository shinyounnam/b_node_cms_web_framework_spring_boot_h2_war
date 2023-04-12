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


public class AlarmResendServer {
    
    CommonUtil common;    
    AlarmResend corpAlarmNoti;
    String module_name = "AlarmResendServer";

    public AlarmResendServer() {    
        common = new CommonUtil();
        corpAlarmNoti = new AlarmResend();
    }

    public String send_alarm_json_object(JSONObject jsonObject){
        String result = "";              
        try
        {
            AddResendPushAlaramQueue(jsonObject);
        }        
        catch(Exception ex2){
            ex2.printStackTrace();
        }
        
        return result;
    }

    private void AddResendPushAlaramQueue(JSONObject data) {
        JSONObject jsonData = data;
        try
        {	
            // String registration_id = common.get_json_value(jsonData,"REGISTRATION_ID");
            
            if (common.get_json_value(jsonData,"GBN").equals("REQ") && 
                common.get_json_value(jsonData,"BASE_TYPE").equals("BASE") && 
                common.get_json_value(jsonData,"ID_KEY_TYPE").equals("BOOK_ID"))
            {
                corpAlarmNoti.SetWorkBookPush(jsonData);
                common.log(module_name,"corpAlarmNoti SetWorkBookPush BOOK_ID : " + common.get_json_value(jsonData,"BOOK_ID"));
            }
            else if (common.get_json_value(jsonData,"W_REQ_GBN").equals("SS") && 
                    common.get_json_value(jsonData,"BASE_TYPE").equals("BASE") && 
                    common.get_json_value(jsonData,"ID_KEY_TYPE").equals("RMD_ID"))
            {
                corpAlarmNoti.SetWorkRecommandPush(jsonData);
                common.log(module_name,"corpAlarmNoti SetWorkRecommandPush RMD_ID : " + common.get_json_value(jsonData,"RMD_ID"));
            }
            else if (common.get_json_value(jsonData,"W_REQ_GBN").equals("VS") && 
                    common.get_json_value(jsonData,"BASE_TYPE").equals("BASE") && 
                    common.get_json_value(jsonData,"ID_KEY_TYPE").equals("BOOK_ID"))            
            {
                corpAlarmNoti.SetWorkBookVSPush(jsonData);
                common.log(module_name,"corpAlarmNoti SetEmcWorkBookPush BOOK_ID : " + common.get_json_value(jsonData,"BOOK_ID"));
            }
            else if (common.get_json_value(jsonData,"GBN").equals("REQ") && 
                    common.get_json_value(jsonData,"BASE_TYPE").equals("EMC") && 
                    common.get_json_value(jsonData,"ID_KEY_TYPE").equals("I_ID"))                  
            {
                // 지원알림 1개
                // jsonData.WORK_ID = jsonData.NEED_ID;
                corpAlarmNoti.SetWorkVeteranServicePush(jsonData);
                common.log(module_name,"corpAlarmNoti SetWorkVServicePush I_ID : " + common.get_json_value(jsonData,"I_ID"));
            }
            else if (common.get_json_value(jsonData,"W_REQ_GBN").equals("VV") && 
                    common.get_json_value(jsonData,"BASE_TYPE").equals("EMC") && 
                    common.get_json_value(jsonData,"ID_KEY_TYPE").equals("V_I_ID"))                 
            {
                corpAlarmNoti.SetWorkVendorServicePush(jsonData);
                common.log(module_name,"corpAlarmNoti SetWorkVendorServicePush V_I_ID : " + common.get_json_value(jsonData,"I_ID"));
            }				
            else if (common.get_json_value(jsonData,"GBN").equals("REQ") && 
                    common.get_json_value(jsonData,"BASE_TYPE").equals("BASE") && 
                    common.get_json_value(jsonData,"ID_KEY_TYPE").equals("SHOW_ID"))                
            {
                corpAlarmNoti.SetWorkShowReqPush(jsonData);
                common.log(module_name,"corpAlarmNoti SetWorkShowReqPush SHOW_ID : " + common.get_json_value(jsonData,"SHOW_ID"));
            }
            else
            {
                common.log(module_name,"SendResendPushAlaram error  : " + jsonData.toString());
            }
        }
        catch (Exception ex)
        {
            common.log(module_name,"SendResendPushAlaram FCM error : " + ex.getMessage());
        }
    }

}