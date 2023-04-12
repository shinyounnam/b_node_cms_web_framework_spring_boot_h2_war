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


public class AlarmReq {

    ThreadPool pool = null;
    
    CommonUtil common;
    String module_name = "AlarmReq";
    
    public AlarmReq(){
        common = new CommonUtil();
        pool = new ThreadPool(10,1000);
        pool.toggleDebugWithQueue(true);
        pool.toggleDebugWithRunnable(true);
    }

    public String send_alarm_req(JSONObject jsonData){
        String result = "";
        try {
            String WORK_DATE = common.get_json_value(jsonData,"WORK_DATE");
            String WORK_ID = common.get_json_value(jsonData,"WORK_ID");
            String NEED_ID = common.get_json_value(jsonData,"NEED_ID");
            String I_ID = common.get_json_value(jsonData,"I_ID");
            String PAGE = common.get_json_value(jsonData,"PAGE");
            result = WorkScheduleVService_Alarm(WORK_ID, NEED_ID, WORK_DATE, I_ID, PAGE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    
    private String WorkScheduleVService_Alarm(String aWorkID, String aNeedID, String aWorkDate, String aI_ID, String aPage)
    {
        String result = "";
        int add_queue_count = 0;
        int i = 0;
        try
        {
            String strPageSQL = "CALL SP_M_WORK_REQUEST_VETERAN_SVC_PAGE_1('"+aI_ID+"','"+aWorkID+"','"+aNeedID+"','"+aPage+"','"+aWorkDate+"')"; 
            JSONArray jsonArray = common.execute_query(strPageSQL);
            result = common.get_data_ok(jsonArray);
            
            JSONArray jsonResult = jsonArray.getJSONArray(0);

            for (i =0; i<jsonResult.length();i++)
            {
                //common.log(module_name,"PushAlaram_Add_Queue=enqueue_message "+JSON.stringify(jsonResult[i]));
                JSONObject jsonSendData = jsonResult.getJSONObject(i);
                if(jsonSendData.has("BASE_TYPE")){
                    jsonSendData.remove("BASE_TYPE");
                }
                jsonSendData.put("BASE_TYPE", "EMC");
                if(jsonSendData.has("VERSION_NAME")){
                    jsonSendData.remove("VERSION_NAME");
                }
                jsonSendData.put("VERSION_NAME", "pop_alarm");
                // queue.addToQueue(JSON.stringify(jsonSendData));

                try
                {
                    AlarmReqRunable alarmReqRunable = new AlarmReqRunable(jsonSendData);
                    pool.execute(alarmReqRunable);
                }
                catch(Exception ex1){
                    ex1.printStackTrace();
                }
            }	
        }
        catch (Exception ex)
        {
            common.log(module_name,"PushAlaram_Add_Queue error : " + ex.getMessage());            
        }		
        return result;
    }

    
}