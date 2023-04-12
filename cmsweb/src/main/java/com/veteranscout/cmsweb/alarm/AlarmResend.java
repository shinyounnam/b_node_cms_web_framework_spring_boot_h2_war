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

public class AlarmResend {
    
    CommonUtil common;
    String module_name = "AlarmResend";

    public AlarmResend(){
        common = new CommonUtil();
    }

    public class SendWorkBookPushRunable implements Runnable{

        String module_name = "SendWorkBookPushRunable";
        Object jsmMsg = null;

        public SendWorkBookPushRunable(Object msg) {
            jsmMsg = msg;
        }

        @Override
        public void run() {
            System.out.println(jsmMsg);        
            SendWorkBookPush(jsmMsg);
        }   
    }

    public String SetWorkBookPush(JSONObject jsonData)
    {
        String result = "";
        int nTimeOut = 0;
        try
        {
            if (jsonData.getString("WORK_REQ_TYPE").equals("1"))
            {
                // 3분마다 알람 재전송
                nTimeOut = 1000*60*3;
                common.log(module_name,"push 3 per minutes : BOOK_ID : " + jsonData.getInt("BOOK_ID"));            
                SendWorkBookPushRunable sendWorkBookPushRunable = new SendWorkBookPushRunable(jsonData);
                common.setTimeout(sendWorkBookPushRunable, nTimeOut);            
                            
            }
            else if (jsonData.getString("WORK_REQ_TYPE").equals("2"))
            {
                // 5분마다 알람 재전송
                nTimeOut = 1000*60*5;
                common.log(module_name,"push 5 per minutes : BOOK_ID : " + jsonData.getInt("BOOK_ID"));            
                SendWorkBookPushRunable sendWorkBookPushRunable = new SendWorkBookPushRunable(jsonData);
                common.setTimeout(sendWorkBookPushRunable, nTimeOut);            
            }
            else if (jsonData.getString("WORK_REQ_TYPE").equals("3"))
            {
                // 10분마다 알람 재전송
                nTimeOut = 1000*60*10;
                common.log(module_name,"push 5 per minutes : BOOK_ID : " + jsonData.getInt("BOOK_ID"));            
                SendWorkBookPushRunable sendWorkBookPushRunable = new SendWorkBookPushRunable(jsonData);
                common.setTimeout(sendWorkBookPushRunable, nTimeOut);              
            }
        }
        catch(JSONException ex){
            ex.printStackTrace();
        }
        
        return result;
    }

    private String SendWorkBookResend(JSONObject jsonData){
        String result = "";
        try
        {

            FCM fcm = new FCM(common.user_api_key);
            if (jsonData.has("app_message")) {
                JSONObject message = (JSONObject)jsonData.get("app_message");
                JSONObject json_result_message = fcm.send(message);
                result = json_result_message.toString();
                common.log(module_name,"fcm.send result="+result);   
                int nTimeout=0;
                if (jsonData.getString("WORK_REQ_TYPE").equals("2"))
                {
                    // 5분마다
                    nTimeout = 60*1000*5;
                    common.log(module_name,"push 5 per minutes : BOOK_ID : " + jsonData.getInt("BOOK_ID"));
                    SendWorkBookPushRunable sendWorkBookPushRunable = new SendWorkBookPushRunable(jsonData);
                    common.setTimeout(sendWorkBookPushRunable, nTimeout);                    
                }
                else if (jsonData.getString("WORK_REQ_TYPE").equals("3"))
                {
                    // 10분마다
                    nTimeout = 60*1000*10;
                    common.log(module_name,"push 5 per minutes : BOOK_ID : " + jsonData.getInt("BOOK_ID"));
                    SendWorkBookPushRunable sendWorkBookPushRunable = new SendWorkBookPushRunable(jsonData);
                    common.setTimeout(sendWorkBookPushRunable, nTimeout);
                }    
            }
            
                
                      
        }
        catch(JSONException ex){
            ex.printStackTrace();
        }
        return result;
    }

    public String SendWorkBookPush(Object data)
    {
        JSONObject jsonData = (JSONObject)data;
        String result = "";
        String strSQL = "";
        JSONArray jsonConf = null;
        try
        {		
            strSQL = "CALL SP_M_EMP_ALARM_INFO_SELECT('BOOK_ID','"+common.get_json_value_int(jsonData,"BOOK_ID")+"','"+common.get_json_value_int(jsonData,"EMP_ID")+"')";
            jsonConf = common.execute_query(strSQL);
            if(common.get_json_array_value(jsonConf,"RES_YN").equals("") && common.get_json_array_value(jsonConf,"DEL_YN").equals("N")){
                strSQL = "CALL SP_M_EMP_CONF_NOTI_PUSH_SELECT('"+common.get_json_value_int(jsonData,"EMP_ID")+"')";
                jsonConf = common.execute_query(strSQL);
                if(common.get_json_array_value(jsonConf,"POOL_WORK_YN").equals("N")){
                    common.log(module_name,"POOL_WORK_YN = N, EMP_ID : " + jsonData.getInt("EMP_ID"));
                }
                else if(common.get_json_array_value(jsonConf,"POOL_WORK_YN").equals("Y")){
                    result = SendWorkBookResend(jsonData);
                }       
            }
            else{
                common.log(module_name,"SendWorkBook RES_YN ");
            }
        }
        catch (JSONException ex1){
            ex1.printStackTrace();
        }        
        catch (Exception ex)
        {
            try
            {
                common.log(module_name,"SendWorkBookPush BOOK_ID "+jsonData.getInt("BOOK_ID")+"error " + ex.getMessage());
            }
            catch(JSONException ex1){
                ex1.printStackTrace();
            }
        }	
        return result;
    }

    public class SendWorkRecommandPushRunable implements Runnable{

        String module_name = "SendWorkRecommandPushRunable";
        Object jsonMessage = null;

        public SendWorkRecommandPushRunable(Object msg) {
            jsonMessage = msg;
        }

        @Override
        public void run() {
            System.out.println(jsonMessage);        
            SendWorkRecommandPush(jsonMessage);
        }   
    }
        
    public String SetWorkRecommandPush(JSONObject jsonData)
    {
        String result = "";
        int nTimeOut = 0;
        try
        {
            if (jsonData.getString("WORK_REQ_TYPE").equals("1"))
            {
                // 3분마다 알람 재전송
                nTimeOut = 1000*60*3;
                common.log(module_name,"push 3 per minutes : RMD_ID : " + jsonData.getInt("RMD_ID"));
                SendWorkRecommandPushRunable sendWorkRecommandPushRunable = new SendWorkRecommandPushRunable(jsonData);
                common.setTimeout(sendWorkRecommandPushRunable,nTimeOut);            
            }
            else if (jsonData.getString("WORK_REQ_TYPE").equals("2"))
            {
                // 5분마다 알람 재전송
                nTimeOut = 1000*60*5;
                common.log(module_name,"push 3 per minutes : RMD_ID : " + jsonData.getInt("RMD_ID"));
                SendWorkRecommandPushRunable sendWorkRecommandPushRunable = new SendWorkRecommandPushRunable(jsonData);
                common.setTimeout(sendWorkRecommandPushRunable,nTimeOut);
            }
            else if (jsonData.getString("WORK_REQ_TYPE").equals("3"))
            {
                // 10분마다 알람 재전송
                nTimeOut = 1000*60*10;
                common.log(module_name,"push 3 per minutes : RMD_ID : " + jsonData.getInt("RMD_ID"));
                SendWorkRecommandPushRunable sendWorkRecommandPushRunable = new SendWorkRecommandPushRunable(jsonData);
                common.setTimeout(sendWorkRecommandPushRunable,nTimeOut);
            }
        }
        catch(JSONException ex){
            ex.printStackTrace();
        }
        
        return result;
    }

    private String SendWorkRecommandResend(JSONObject jsonData){
        String result = "";
        try
        {
            FCM fcm = new FCM(common.user_api_key);
            if(jsonData.has("app_message")){
                JSONObject message = (JSONObject)jsonData.get("app_message");
                JSONObject json_result_message = fcm.send(message);
                result = json_result_message.toString();
                common.log(module_name,"fcm.send result="+result);
            }
            else{
                common.log(module_name,"registration_id is null,message="+jsonData.toString());
            }
            
            int nTimeout = 0;
            if (jsonData.getString("WORK_REQ_TYPE").equals("2"))
            {
                // 5분마다
                nTimeout = 60*1000*5;
                common.log(module_name,"push 5 per minutes : RMD_ID : " + jsonData.getInt("RMD_ID"));
                SendWorkRecommandPushRunable sendWorkRecommandPushRunable = new SendWorkRecommandPushRunable(jsonData);
                common.setTimeout(sendWorkRecommandPushRunable, nTimeout);                
            }
            else if (jsonData.getString("WORK_REQ_TYPE").equals("3"))
            {
                // 10분마다
                nTimeout = 60*1000*10;
                common.log(module_name,"push 10 per minutes : RMD_ID : " + jsonData.getInt("RMD_ID"));
                SendWorkRecommandPushRunable sendWorkRecommandPushRunable = new SendWorkRecommandPushRunable(jsonData);
                common.setTimeout(sendWorkRecommandPushRunable, nTimeout);
            }
        }
        catch(JSONException ex1){
            ex1.printStackTrace();
        }        
        catch(Exception ex){
            ex.printStackTrace();
        }
        
        return result;
    }

    private String SendWorkRecommandPush(Object data)
    {
        JSONObject jsonData = (JSONObject)data;
        String result = "";
        String strSQL = "";
        JSONArray jsonConf = null;

        try
        {		
            strSQL = "CALL SP_M_EMP_ALARM_INFO_SELECT('BOOK_ID','"+common.get_json_value_int(jsonData,"BOOK_ID")+"','"+common.get_json_value_int(jsonData,"EMP_ID")+"')";
            jsonConf = common.execute_query(strSQL);
            if(common.get_json_array_value(jsonConf,"RES_YN").equals("") && common.get_json_array_value(jsonConf,"DEL_YN").equals("N")){
                strSQL = "CALL SP_M_EMP_CONF_NOTI_PUSH_SELECT('"+common.get_json_value_int(jsonData,"EMP_ID")+"')";
                jsonConf = common.execute_query(strSQL);
                if(common.get_json_array_value(jsonConf,"POOL_WORK_YN").equals("Y")){
                    result = SendWorkRecommandResend(jsonData);                            
                }
                else{
                    common.log(module_name,"POOL_WORK_YN = N, EMP_ID : " + jsonData.getInt("EMP_ID"));
                }   
            }
            else{
                common.log(module_name,"AlarmWorkRecommand RES_YN");
            }              
        }        
        catch(JSONException ex2){
            ex2.printStackTrace();
        }
        catch (Exception ex)
        {
            try
            {
                common.log(module_name,"SendWorkRecommandPush RMD_ID "+jsonData.getInt("RMD_ID")+"error " + ex.getMessage());
            }
            catch(JSONException ex1){
                ex1.printStackTrace();
            }            
        }	
        return result;
    }

    public class SendWorkBookVSPushRunable implements Runnable{

        String module_name = "SendWorkBookVSPushRunable";
        Object jsonMessage = null;

        public SendWorkBookVSPushRunable(Object msg) {
            jsonMessage = msg;
        }

        @Override
        public void run() {
            System.out.println(jsonMessage);        
            SendWorkBookVSPush(jsonMessage);
        }   
    }

    public String SetWorkBookVSPush(JSONObject jsonData)
    {
        String result = "";
        int nTimeOut = 0;
        try
        {
            if (jsonData.getString("EMC_REQ_TYPE").equals("1"))
            {
                // 3분마다 알람 재전송
                nTimeOut = 1000*60*3;
                common.log(module_name,"push 3 per minutes : BOOK_ID : " + jsonData.getInt("BOOK_ID"));
                SendWorkBookVSPushRunable sendWorkBookVSPushRunable = new SendWorkBookVSPushRunable(jsonData);
                common.setTimeout(sendWorkBookVSPushRunable,nTimeOut);   
            }
            else if (jsonData.getString("EMC_REQ_TYPE").equals("2"))
            {
                // 5분마다 알람 재전송
                nTimeOut = 1000*60*5;
                common.log(module_name,"push 3 per minutes : BOOK_ID : " + jsonData.getInt("BOOK_ID"));
                SendWorkBookVSPushRunable sendWorkBookVSPushRunable = new SendWorkBookVSPushRunable(jsonData);
                common.setTimeout(sendWorkBookVSPushRunable,nTimeOut);
            }
            else if (jsonData.getString("EMC_REQ_TYPE").equals("3"))
            {
                // 5분마다 알람 재전송
                nTimeOut = 1000*60*10;
                common.log(module_name,"push 3 per minutes : BOOK_ID : " + jsonData.getInt("BOOK_ID"));
                SendWorkBookVSPushRunable sendWorkBookVSPushRunable = new SendWorkBookVSPushRunable(jsonData);
                common.setTimeout(sendWorkBookVSPushRunable,nTimeOut);
            }
        }
        catch(JSONException ex){
            ex.printStackTrace();
        }
        
        return result;
    }

    private String SendWorkBookVSPushResend(JSONObject jsonData){
        String result = "";
        try {

            FCM fcm = new FCM(common.user_api_key);
            if(jsonData.has("app_message")){
                JSONObject message = (JSONObject)jsonData.get("app_message");
                JSONObject json_result_message = fcm.send(message);
                result = json_result_message.toString();
                common.log(module_name,"fcm.send result="+result);

                int nTimeout = 0;
                if (jsonData.getString("EMC_REQ_TYPE").equals("2"))
                {
                    // 5분마다
                    nTimeout=60*1000*5;
                    SendWorkBookVSPushRunable sendWorkBookVSPushRunable = new SendWorkBookVSPushRunable(jsonData);
                    common.setTimeout(sendWorkBookVSPushRunable,nTimeout);                
                }
                else if (jsonData.getString("EMC_REQ_TYPE").equals("3"))
                {
                    // 10분마다
                    nTimeout=60*1000*10;
                    SendWorkBookVSPushRunable sendWorkBookVSPushRunable = new SendWorkBookVSPushRunable(jsonData);
                    common.setTimeout(sendWorkBookVSPushRunable,nTimeout);
                }
            }
            else{
                common.log(module_name,"registration_id is null,result="+result);
            }

            
        }
        catch (JSONException ex1){
            ex1.printStackTrace();
        }        
        catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private String SendWorkBookVSPush(Object data)
    {
        JSONObject jsonData = (JSONObject)data;
        String result = "";
        String strSQL = "";
        JSONArray jsonConf = null;
        try
        {
            strSQL = "CALL SP_M_EMP_ALARM_INFO_SELECT('BOOK_ID','"+common.get_json_value_int(jsonData,"BOOK_ID")+"','"+common.get_json_value_int(jsonData,"EMP_ID")+"')";
            jsonConf = common.execute_query(strSQL);
            if(common.get_json_array_value(jsonConf,"RES_YN").equals("") && common.get_json_array_value(jsonConf,"DEL_YN").equals("N")){
                result = SendWorkBookVSPushResend(jsonData);                            
            }
            else{
                common.log(module_name,"SendWorkBookVSPush BOOK_ID : " + common.get_json_value_int(jsonData,"BOOK_ID"));
            }           
        }
        catch (JSONException ex1) {
            ex1.printStackTrace();            
        }        
        catch (Exception ex)
        {
            try
            {
                common.log(module_name,"SendWorkBookVSPush BOOK_ID "+jsonData.getInt("BOOK_ID")+"error " + ex.getMessage());
            }
            catch(JSONException ex1){
                ex1.printStackTrace();
            }
            
        }	
        return result;
    }

    public class SendWorkVeteranServicePushRunable implements Runnable{

        String module_name = "SendWorkVeteranServicePushRunable";
        Object jsonMessage = null;

        public SendWorkVeteranServicePushRunable(Object msg) {
            jsonMessage = msg;
        }

        @Override
        public void run() {
            System.out.println(jsonMessage);        
            SendWorkVeteranServicePush(jsonMessage);
        }   
    }


    public String SetWorkVeteranServicePush(JSONObject jsonData)
    {
        String result = "";
        int nTimeOut = 0;
        try
        {

            if (jsonData.getString("EMC_REQ_TYPE").equals("1"))
            {
                // 3분마다 알람 재전송
                nTimeOut = 1000*60*3;
                common.log(module_name,"push 3 per minutes : NEED_ID : " + jsonData.getInt("NEED_ID")+ " EMP_ID : " + jsonData.getInt("EMP_ID"));
                SendWorkVeteranServicePushRunable sendWorkVeteranServicePushRunable = new SendWorkVeteranServicePushRunable(jsonData);
                common.setTimeout(sendWorkVeteranServicePushRunable,nTimeOut);               
            }
            else if (jsonData.getString("EMC_REQ_TYPE").equals("2"))
            {
                // 5분마다 알람 재전송
                nTimeOut = 1000*60*5;
                common.log(module_name,"push 5 per minutes : NEED_ID : " + jsonData.getInt("NEED_ID")+ " EMP_ID : " + jsonData.getInt("EMP_ID"));
                SendWorkVeteranServicePushRunable sendWorkVeteranServicePushRunable = new SendWorkVeteranServicePushRunable(jsonData);
                common.setTimeout(sendWorkVeteranServicePushRunable,nTimeOut);               
            }
            else if (jsonData.getString("EMC_REQ_TYPE").equals("3"))
            {
                // 5분마다 알람 재전송
                nTimeOut = 1000*60*10;
                common.log(module_name,"push 5 per minutes : NEED_ID : " + jsonData.getInt("NEED_ID")+ " EMP_ID : " + jsonData.getInt("EMP_ID"));
                SendWorkVeteranServicePushRunable sendWorkVeteranServicePushRunable = new SendWorkVeteranServicePushRunable(jsonData);
                common.setTimeout(sendWorkVeteranServicePushRunable,nTimeOut);               
            }
        }
        catch(JSONException ex1){
            ex1.printStackTrace();
        }
        
        return result;
    }

    private String SendWorkVeteranServicePushResend(JSONObject jsonData){
        String result = "";
        String strSQL = "";
        JSONArray jsonConf = null;
        try 
        {            
            FCM fcm = new FCM(common.user_api_key);
            
            strSQL = "CALL SP_M_EMP_CONF_NOTI_PUSH_SELECT('"+common.get_json_value_int(jsonData,"EMP_ID")+"')";
            jsonConf = common.execute_query(strSQL);
            if(common.get_json_array_value(jsonConf,"TODAY_YN").equals("Y") && 
               common.get_json_array_value(jsonConf,"TODAY_YN_TIME").equals(common.now_date_string())
            ){
                common.log(module_name,"EmpConfNotiPushToday is not null today yn : N EMP_ID:"+jsonData.getInt("EMP_ID")+" ");
            }
            else{
                
                if (jsonData.has("app_message"))
                {
                    JSONObject message = (JSONObject)jsonData.get("app_message");
                    JSONObject json_result_message = fcm.send(message);
                    result = json_result_message.toString();
                    common.log(module_name,"fcm.send result="+result);     
                    
                    int nTimeout = 0;
                    
                    if (jsonData.getString("EMC_REQ_TYPE").equals("2"))
                    {
                        // 5분마다
                        nTimeout = 60*1000*5;
                        SendWorkVeteranServicePushRunable sendWorkVeteranServicePushRunable = new SendWorkVeteranServicePushRunable(jsonData);
                        common.setTimeout(sendWorkVeteranServicePushRunable,nTimeout);                
                    }
                    else if (jsonData.getString("EMC_REQ_TYPE").equals("3"))
                    {
                        // 10분마다
                        nTimeout = 60*1000*10;
                        SendWorkVeteranServicePushRunable sendWorkVeteranServicePushRunable = new SendWorkVeteranServicePushRunable(jsonData);
                        common.setTimeout(sendWorkVeteranServicePushRunable,nTimeout);                
                    }               
                }
                else
                {
                    common.log(module_name,"registration_id is null ");
                }

            }            
            
        } 
        catch (JSONException ex1){
            ex1.printStackTrace();
        }        
        catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private String SendWorkVeteranServicePush(Object data)
    {
        String result = "";
        JSONObject jsonData = (JSONObject)data;
        String strSQL = "";
        JSONArray jsonConf = null;
        try
        {
            strSQL = "CALL SP_M_EMP_ALARM_INFO_SELECT('I_ID','"+common.get_json_value_int(jsonData,"I_ID")+"','"+common.get_json_value_int(jsonData,"EMP_ID")+"')";
            jsonConf = common.execute_query(strSQL);
            if(common.get_json_array_value(jsonConf,"EMC_RES_YN").equals("") && common.get_json_array_value(jsonConf,"DEL_YN").equals("N")){
                strSQL = "CALL SP_M_EMP_CONF_NOTI_PUSH_SELECT('"+common.get_json_value_int(jsonData,"EMP_ID")+"')";
                jsonConf = common.execute_query(strSQL);
                if(common.get_json_array_value(jsonConf,"EMC_WORK_YN").equals("N")){
                    common.log(module_name,"EmpConfNotiPush conf_data EMC_WORK_YN : Y, EMC_ID : " + jsonData.getInt("EMC_ID") + " : EMP_ID : " + jsonData.getInt("EMP_ID"));
                }
                else if (common.get_json_array_value(jsonConf,"EMC_WORK_YN").equals("Y")){
                    result = SendWorkVeteranServicePushResend(jsonData);
                }      
            }
            
        }
        catch (Exception ex)
        {
            try
            {
                common.log(module_name,"SendWorkVeteranServicePush BOOK_ID "+jsonData.getInt("BOOK_ID")+"error " + ex.getMessage());
            }
            catch(JSONException ex1){
                ex1.printStackTrace();
            }
        }	
        return result;
    }

    public class SendWorkVendorServicePushRunable implements Runnable{

        String module_name = "SendWorkVendorServicePushRunable";
        Object jsonMessage = null;

        public SendWorkVendorServicePushRunable(Object msg) {
            jsonMessage = msg;
        }

        @Override
        public void run() {
            System.out.println(jsonMessage);        
            SendWorkVendorServicePush(jsonMessage);
        }   
    }

    private String SetWorkVendorServicePushResend(JSONObject jsonData){
        String result = "";
        try {
            FCM fcm = new FCM(common.user_api_key);
            if(jsonData.has("app_message")){
                JSONObject message = (JSONObject)jsonData.get("app_message");
                JSONObject json_result_message = fcm.send(message);
                result = json_result_message.toString();
                common.log(module_name,"fcm.send result="+result);
                int nTimeout = 0;
                if (jsonData.getString("EMC_REQ_TYPE").equals("2"))
                {
                    // 5분마다
                    nTimeout = 60*1000*5;
                    SendWorkVendorServicePushRunable sendWorkVendorServicePushRunable = new SendWorkVendorServicePushRunable(jsonData);
                    common.setTimeout(sendWorkVendorServicePushRunable,nTimeout);                    
                }
                else if (jsonData.getString("EMC_REQ_TYPE").equals("3"))
                {
                    // 10분마다
                    nTimeout = 60*1000*10;
                    SendWorkVendorServicePushRunable sendWorkVendorServicePushRunable = new SendWorkVendorServicePushRunable(jsonData);
                    common.setTimeout(sendWorkVendorServicePushRunable,nTimeout);                    
                }	
            }
            
           
        } 
        catch (JSONException ex1){
            ex1.printStackTrace();
        }        
        catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public String SetWorkVendorServicePush(JSONObject jsonData)
    {
        String result = "";
        int nTimeOut = 0;
        try
        {
            if (jsonData.getString("EMC_REQ_TYPE").equals("1"))
            {
                // 3분마다 알람 재전송
                nTimeOut = 1000*60*3;
                common.log(module_name,"push 3 per minutes : NEED_ID : " + jsonData.getInt("NEED_ID")+ " EMP_ID : " + jsonData.getInt("EMP_ID"));
                SendWorkVendorServicePushRunable sendWorkVendorServicePushRunable = new SendWorkVendorServicePushRunable(jsonData);
                common.setTimeout(sendWorkVendorServicePushRunable,nTimeOut);
            }
            else if (jsonData.getString("EMC_REQ_TYPE").equals("2"))
            {
                // 5분마다 알람 재전송
                nTimeOut = 1000*60*5;
                common.log(module_name,"push 5 per minutes : NEED_ID : " + jsonData.getInt("NEED_ID")+ " EMP_ID : " + jsonData.getInt("EMP_ID"));
                SendWorkVendorServicePushRunable sendWorkVendorServicePushRunable = new SendWorkVendorServicePushRunable(jsonData);
                common.setTimeout(sendWorkVendorServicePushRunable,nTimeOut);
            }
            else if (jsonData.getString("EMC_REQ_TYPE").equals("3"))
            {
                // 10분마다 알람 재전송
                nTimeOut = 1000*60*10;
                common.log(module_name,"push 10 per minutes : NEED_ID : " + jsonData.getInt("NEED_ID")+ " EMP_ID : " + jsonData.getInt("EMP_ID"));
                SendWorkVendorServicePushRunable sendWorkVendorServicePushRunable = new SendWorkVendorServicePushRunable(jsonData);
                common.setTimeout(sendWorkVendorServicePushRunable,nTimeOut);
            }
        }
        catch(JSONException ex1){
            ex1.printStackTrace();
        }

        
        return result;
    }



    private String SendWorkVendorServicePush(Object data)
    {
        JSONObject jsonData = (JSONObject)data;
        String result = "";
        String strSQL = "";
        JSONArray jsonConf = null;
        try
        {
            strSQL = "CALL SP_M_EMP_ALARM_INFO_SELECT('V_I_ID','"+common.get_json_value_int(jsonData,"I_ID")+"','"+common.get_json_value_int(jsonData,"CORP_ID")+"')";
            jsonConf = common.execute_query(strSQL);
            if(common.get_json_array_value(jsonConf,"EMC_RES_YN").equals("")){
                strSQL = "CALL SP_M_CORP_CONF_ALARM_INFO_1_1_92('"+common.get_json_value_int(jsonData,"CORP_ID")+"')";
                jsonConf = common.execute_query(strSQL);
                if(common.get_json_array_value(jsonConf,"EMC_WORK_YN").equals("N") && common.get_json_array_value(jsonConf,"DEL_YN").equals("N")){
                    common.log(module_name,"CorpConfNotiPush conf_data EMC_WORK_YN : Y, I_ID : " + common.get_json_value_int(jsonData,"I_ID") + 
                                " : CORP_ID : " + common.get_json_value_int(jsonData,"CORP_ID"));
                }
                else if(common.get_json_array_value(jsonConf,"EMC_WORK_YN").equals("Y")){
                    result = SetWorkVendorServicePushResend(jsonData);
                }     
            }            
        }
        catch (Exception ex)
        {
            try
            {
                common.log(module_name,"SetWorkVendorServicePush BOOK_ID "+jsonData.getInt("BOOK_ID")+" error " + ex.getMessage());
            }
            catch(JSONException ex1){
                ex1.printStackTrace();
            }            
        }	
        return result;
    }

    public class SendWorkShowPushRunable implements Runnable{

        String module_name = "SendWorkShowPushRunable";
        Object jsonMessage = null;

        public SendWorkShowPushRunable(Object msg) {
            jsonMessage = msg;
        }

        @Override
        public void run() {
            System.out.println(jsonMessage);        
            SendWorkShowPush(jsonMessage);
        }   
    }

    public String SetWorkShowReqPush(JSONObject jsonData)
    {
        String result = "";
        int nTimeOut = 0;
        try
        {
            if (jsonData.getString("SHOW_REQ_TYPE").equals("1"))
            {
                // 3분마다 알람 재전송
                nTimeOut = 1000*60*3;
                common.log(module_name,"push 3 per minutes : SHOW_ID : " + jsonData.getInt("SHOW_ID"));
                SendWorkShowPushRunable sendWorkShowPushRunable = new SendWorkShowPushRunable(jsonData);
                common.setTimeout(sendWorkShowPushRunable,nTimeOut);
            }
            else if (jsonData.getString("SHOW_REQ_TYPE").equals("2"))
            {
                // 5분마다 알람 재전송
                nTimeOut = 1000*60*5;
                common.log(module_name,"push 5 per minutes : SHOW_ID : " + jsonData.getInt("SHOW_ID"));
                SendWorkShowPushRunable sendWorkShowPushRunable = new SendWorkShowPushRunable(jsonData);
                common.setTimeout(sendWorkShowPushRunable,nTimeOut);
            }
            else if (jsonData.getString("SHOW_REQ_TYPE").equals("3"))
            {
                // 5분마다 알람 재전송
                nTimeOut = 1000*60*10;
                common.log(module_name,"push 10 per minutes : SHOW_ID : " + jsonData.getInt("SHOW_ID"));
                SendWorkShowPushRunable sendWorkShowPushRunable = new SendWorkShowPushRunable(jsonData);
                common.setTimeout(sendWorkShowPushRunable,nTimeOut);
            }
        }
        catch(JSONException ex1){
            ex1.printStackTrace();
        }
        
        return result;
    }

    private String SendWorkShowPushResend(JSONObject jsonData){
        String result = "";
        try {

            FCM fcm = new FCM(common.user_api_key);
            if(jsonData.has("app_message")){
                JSONObject message = (JSONObject)jsonData.get("app_message");
                JSONObject json_result_message = fcm.send(message);
                result = json_result_message.toString();
                common.log(module_name,"fcm.send success="+result+",message="+message.toString());

                int nTimeout = 0;
                if (jsonData.getString("SHOW_REQ_TYPE").equals("2"))
                {
                    // 5분
                    nTimeout = 60*1000*5;
                    SendWorkShowPushRunable sendWorkShowPushRunable = new SendWorkShowPushRunable(jsonData);
                    common.setTimeout(sendWorkShowPushRunable, nTimeout);                    
                }
                else if (jsonData.getString("SHOW_REQ_TYPE").equals("3"))
                {
                    // 10분
                    nTimeout = 60*1000*10;
                    SendWorkShowPushRunable sendWorkShowPushRunable = new SendWorkShowPushRunable(jsonData);
                    common.setTimeout(sendWorkShowPushRunable, nTimeout);                    
                }
            }

        } 
        catch (JSONException ex1){
            ex1.printStackTrace();
        }        
        catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    private String SendWorkShowPush(Object data)
    {
        JSONObject jsonData = (JSONObject)data;
        String result = "";
        String strSQL = "";
        JSONArray jsonConf = null;
        try
        {
            strSQL = "CALL SP_M_EMP_ALARM_INFO_SELECT('SHOW_ID','"+common.get_json_value_int(jsonData,"SHOW_ID")+"','"+common.get_json_value_int(jsonData,"EMP_ID")+"')";
            jsonConf = common.execute_query(strSQL);
            if(common.get_json_array_value(jsonConf,"RES_YN").equals("") && common.get_json_array_value(jsonConf,"DEL_YN").equals("N")){
                result = SendWorkShowPushResend(jsonData);
            }
            else{
                common.log(module_name,"AlarmShow RES_YN");
            }       
        }
        catch (Exception ex)
        {
            try
            {
                common.log(module_name,"SendWorkShowPush SHOW_ID "+jsonData.getInt("SHOW_ID")+"error " + ex.getMessage());
            }
            catch(JSONException ex1){
                ex1.printStackTrace();
            }            
        }	
        return result;
    }

}