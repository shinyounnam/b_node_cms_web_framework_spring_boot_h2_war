package com.veteranscout.cmsweb.alarm;

import com.veteranscout.cmsweb.alarm.*;
import com.veteranscout.cmsweb.util.*;
import com.veteranscout.cmsweb.thread.*;
import java.io.*;
import java.net.*;
import java.sql.*;
import java.lang.*;
import java.beans.*;
import org.json.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


public class AlarmNotice {

    ThreadPool pool = null;    
    CommonUtil common;

    String module_name = "AlarmNotice";

    public AlarmNotice(){
        common = new CommonUtil();
        pool = new ThreadPool(10,100);
        pool.toggleDebugWithQueue(true);
        pool.toggleDebugWithRunnable(true);
    }

    public String send_alarm_json_object(JSONObject jsonObject){
        String result = "";
        result = WorkScheduleVService_Alarm(jsonObject);
        return result;
    }

    
    public String WorkScheduleVService_Alarm(JSONObject jsonObject)
    {
        String result = "";
        int add_queue_count = 0;
        int i = 0;
        String aGBN = common.get_json_value(jsonObject,"GBN");
        String aN_ID = common.get_json_value(jsonObject,"N_ID");
        String aWorkDate = common.get_json_value(jsonObject,"WORK_DATE");
        String aI_ID = common.get_json_value(jsonObject,"I_ID");
        String aMSG_ID = common.get_json_value(jsonObject,"MSG_ID");
        String aPage = common.get_json_value(jsonObject,"PAGE");
        try
        {
            String strPageSQL = "CALL SP_M_WORK_REQUEST_VETERAN_NOTICE_PAGE('"+aI_ID+"','"+aN_ID+"','"+aMSG_ID+"','"+aGBN+"','"+aPage+"','"+aWorkDate+"')"; 
            
            JSONArray jsonArray = common.execute_query(strPageSQL);
            result = common.get_data_ok(jsonArray);

            JSONArray jsonResult = jsonArray.getJSONArray(0);
            JSONArray jsonTotalResult = jsonArray.getJSONArray(1);
            
            for (i =0; i<jsonResult.length();i++)
            {
                //common.log(module_name,"PushAlaram_Add_Queue=enqueue_message "+JSON.stringify(jsonResult[i]));
                JSONObject jsonSendData = jsonResult.getJSONObject(i);
                jsonSendData.put("BASE_TYPE","EMC");
                jsonSendData.put("VERSION_NAME","pop_alarm");
                // queue.addToQueue(JSON.stringify(jsonSendData));

                try
                {
                    AlarmNoticeRunable alarmNoticeRunable = new AlarmNoticeRunable(jsonSendData);
                    pool.execute(alarmNoticeRunable);
                }
                catch(Exception ex1){
                    result = common.get_error_message(ex1.getMessage());
                }                            
            }		
        }
        catch (Exception ex)
        {
            common.log(module_name,"PushAlaram_Add_Queue error : " + ex.getMessage());
            result = common.get_error_message(ex.getMessage());
        }		
        return result;
    }

}