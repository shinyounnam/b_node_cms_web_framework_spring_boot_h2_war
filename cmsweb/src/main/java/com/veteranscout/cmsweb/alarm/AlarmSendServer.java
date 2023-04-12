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
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;


public class AlarmSendServer {

    CommonUtil common = new CommonUtil();   
    ThreadPool pool = null;     

    
    private static AlarmSend alarmServer = new AlarmSend();    
    private static AlarmResendServer alarmResendServer= new AlarmResendServer();    
    private static AlarmRejectServer alarmRejectServer = new AlarmRejectServer();
    
    
    String module_name = "AlarmSendServer";

    public AlarmSendServer() {     
        pool = new ThreadPool(10,100);
        pool.toggleDebugWithQueue(true);
        pool.toggleDebugWithRunnable(true);   
    }

    /*
    public String send_alarm_json_array(JSONArray jsonArray){

        String result = "";        

        try
        {
            JSONArray jsonSendArray = new JSONArray(jsonArray.toString());
            AlarmSendRunable alarmSendRunable = new AlarmSendRunable(jsonSendArray);
            pool.execute(alarmSendRunable);
        }
        catch(Exception ex1){
            ex1.printStackTrace();
        }        
        return result;
    }
    */

    public String send_alarm_json_array(JSONArray jsonArray){

        String result = "";        

        try
        {
            result = AddPushAlaramQueue(jsonArray);
        }
        catch(Exception ex1){
            ex1.printStackTrace();
        }        
        return result;
    }

    private String AddPushAlaramQueue(JSONArray jsonResultData){
        String result = "";
        String jsonResult = jsonResultData.toString();
        
        try
        {
            common.log(module_name, jsonResultData.toString());            
            JSONArray jsonData = new JSONArray(jsonResultData.toString());
            int i = 0;
            int j = 0;
            String send_message = "";
            int port = 0;
            String host = "";
            //common.log(module_name,"jsonData[0] length : "+jsonData[0].length);
            
            if (jsonData.length() > 0)
            {
                if (common.get_json_array_value(jsonData, "GBN").equals("CAN"))
                {
                    JSONArray jsonArray = jsonData.getJSONArray(0);                    
                    for (i = 0;i < jsonArray.length(); i++)
                    {
                        if (jsonArray.getJSONObject(i).getString("result").equals("ok"))
                        {
                            result = SendAlaram(jsonArray.getJSONObject(i));
                        }
                        else
                        {
                            common.log(module_name,"AddPushAlaramQueue=do_not_send_message " + jsonData.toString());
                        }
                    }
                }
                else if (common.get_json_array_value(jsonData,"GBN").equals("ATTEND"))
                {
                    JSONArray jsonArray = jsonData.getJSONArray(0);
                    for (i = 0;i < jsonArray.length(); i++)
                    {
                        if (jsonArray.getJSONObject(i).getString("result").equals("ok"))
                        {
                            result = SendAlaram(jsonArray.getJSONObject(i));
                        }
                        else
                        {
                            common.log(module_name,"AddPushAlaramQueue=do_not_send_message " + jsonData.toString());
                        }
                    }
                }			
                else
                {
                    for (i = 0;i < jsonData.length(); i++)
                    {
                        if (common.get_json_array_position_value(jsonData,i,"result").equals("ok"))
                        {
                            //common.log(module_name,"AddPushAlaramQueue=enqueue_message "+JSON.stringify(jsonData[i][0]));
                            JSONObject jsonSendData = jsonData.getJSONArray(i).getJSONObject(0);                                                             
                            SendAlaram(jsonData.getJSONArray(i).getJSONObject(0));
                            if (common.get_json_value(jsonSendData,"ALARM_TYPE").equals("AlarmWork") && 
                                common.get_json_value(jsonSendData,"GBN").equals("REQ"))
                            {
                                // JSONObject jsonResendData = alarmServer.MakeResendAlarmWorkData(jsonSendData);                                    
                                // alarmResendServer.send_alarm_json_object(jsonResendData);
                                JSONObject jsonRejectData = alarmServer.MakeRejectAlarmWorkData(jsonSendData);
                                alarmRejectServer.send_alarm_json_object(jsonRejectData);                                    
                            }		
                            else if (common.get_json_value(jsonSendData,"ALARM_TYPE").equals("AlarmWorkVS") && 
                                            common.get_json_value(jsonSendData,"GBN").equals("SLT"))
                            {
                                // JSONObject jsonResendData = alarmServer.MakeResendAlarmWorkVSData(jsonSendData);                                        
                                // alarmResendServer.send_alarm_json_object(jsonResendData);
                                JSONObject jsonRejectData = alarmServer.MakeRejectAlarmWorkVSData(jsonSendData);
                                alarmRejectServer.send_alarm_json_object(jsonRejectData);                                        						
                            }
                            else if (common.get_json_value(jsonSendData,"ALARM_TYPE").equals("AlarmWorkVV") && 
                                        common.get_json_value(jsonSendData,"GBN").equals("VV"))
                            {
                                // JSONObject jsonResendData = alarmServer.MakeResendAlarmWorkVVData(jsonSendData);
                                // alarmResendServer.send_alarm_json_object(jsonResendData);                                        
                                JSONObject jsonRejectData = alarmServer.MakeRejectAlarmWorkVVData(jsonSendData);
                                alarmRejectServer.send_alarm_json_object(jsonRejectData);                                        						
                            }
                            else if (common.get_json_value(jsonSendData,"ALARM_TYPE").equals("AlarmWorkRecommand") && 
                                        common.get_json_value(jsonSendData,"GBN").equals("SS"))
                            {
                                // JSONObject jsonResendData = alarmServer.MakeResendAlarmWorkRecommandData(jsonSendData);
                                // alarmResendServer.send_alarm_json_object(jsonResendData);                                        
                                JSONObject jsonRejectData = alarmServer.MakeRejectAlarmWorkRecommandData(jsonSendData);
                                alarmRejectServer.send_alarm_json_object(jsonRejectData);                                        						
                            }                                    					                                
                        }
                        else
                        {
                            common.log(module_name,"AddPushAlaramQueue=do_not_send_message " + jsonData.toString());
                        }
                        
                    }
                    
                }
            }		
            else
            {
                common.log(module_name,"AddPushAlaramQueue=do_not_send_message jsonData[i].length < 1 ");
            }     
            
            //console.log(jsonData);
            //console.log(message);
            
        }
        catch(JSONException ex){
            ex.printStackTrace();
        }
        catch (Exception ex1)
        {
            common.log(module_name,"AddPushAlaramQueue error : " + ex1.getMessage());            
        }

        return result;

    }

    
    private String SendAlaram(JSONObject jsonData)
    {
        String result = "";
        try
        {            
            
            switch(common.get_json_value(jsonData,"ALARM_TYPE"))
            {
                case "AlarmWork":                    
                    result = alarmServer.SetAlarmWork(jsonData);
                    break;
                case "AlarmWorkRes":                    
                    result = alarmServer.SetAlarmWorkRes(jsonData);
                    break;
                case "AlarmShowRes":                    
                    result = alarmServer.SetAlarmShowRes(jsonData);
                    break;
                case "AlarmShowGpsRes":                    
                    result = alarmServer.SetAlarmShowGpsRes(jsonData);
                    break;
                case "AlarmShowSignRes":
                    result = alarmServer.SetAlarmShowSignRes(jsonData);
                    break;
                case "AlarmWorkEmcNo":                    
                    result = alarmServer.SetAlarmWorkEmcNo(jsonData);
                    break;
                case "AlarmWorkVS":                    
                    result = alarmServer.SetAlarmWorkVS(jsonData);
                    break;			
                case "AlarmWorkVSRes":                    
                    result = alarmServer.SetAlarmWorkVSRes(jsonData);
                    break;
                case "AlarmWorkRecommand":                    
                    result = alarmServer.SetAlarmWorkRecommand(jsonData);
                    break;
                case "AlarmWorkRecommandRes":                    
                    result = alarmServer.SetAlarmWorkRecommandRes(jsonData);
                    break;
                case "AlarmWorkVV":                    
                    result = alarmServer.SetAlarmWorkVV(jsonData);
                    break;			
                case "AlarmWorkVVRes":                    
                    result = alarmServer.SetAlarmWorkVVRes(jsonData);
                    break;
                default:
                    common.log(module_name,"SendPushAlaram Error ALARM_TYPE : " + jsonData.toString());
                    break;
            }
            
        }
        catch (Exception ex)
        {
            common.log(module_name,"SendPushAlaram Error : " + ex.getMessage());
        }
        return result;
    }
}