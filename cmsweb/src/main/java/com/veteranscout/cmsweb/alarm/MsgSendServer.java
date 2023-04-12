package com.veteranscout.cmsweb.alarm;

import com.veteranscout.cmsweb.alarm.*;
import com.veteranscout.cmsweb.thread.ThreadPool;
import com.veteranscout.cmsweb.util.*;
import java.io.*;
import java.net.*;
import java.sql.*;

import java.lang.*;
import java.beans.*;
import org.json.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


public class MsgSendServer {

    
    CommonUtil common = new CommonUtil();    
    ThreadPool pool = null;   
    
    
    String module_name = "MsgSendServer";

    public MsgSendServer() {        
        pool = new ThreadPool(10,100);
        pool.toggleDebugWithQueue(true);
        pool.toggleDebugWithRunnable(true);   
    }

    public String send_alarm_json_array(JSONArray jsonArray){
        String result = "";           
        try
        {
            int i = 0;
            JSONObject jsonObject = null;
            if(jsonArray.length() > 0){
                JSONArray jsonResult = jsonArray.getJSONArray(0);
                for(i = 0; i < jsonResult.length(); i++){
                    jsonObject = jsonResult.getJSONObject(i);
                    MsgSendRunable msgSendRunable = new MsgSendRunable(jsonObject);
                    pool.execute(msgSendRunable);
                }
            }            
        }
        catch(Exception ex2){
            ex2.printStackTrace();
        }
        
        return result;
    }

    


}