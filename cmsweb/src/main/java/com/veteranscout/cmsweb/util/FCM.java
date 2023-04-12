package com.veteranscout.cmsweb.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.Iterator;
import java.util.Map;
import java.util.Date;
import java.util.HashMap;
import org.json.*;
import java.net.*;
import java.io.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import javax.servlet.http.*;
import java.security.*;
import java.nio.charset.*;
import java.net.*;
import javax.net.ssl.*;
import java.util.*;

public class FCM {  

    String m_url = "https://fcm.googleapis.com/fcm/send";    
    String m_host = "fcm.googleapis.com";
    String m_method = "POST";
    String m_api_key = "";
    int m_retry_count = 0;
    int m_retry = 1;
    CommonUtil common = new CommonUtil();
    

    public FCM(String api_key){
        m_api_key = api_key;
    }

    public JSONObject send(JSONObject message) 
    {
        JSONObject result = new JSONObject();        
        URL url = null;
        HttpsURLConnection con = null;                        
        
        try
        {            
            url = new URL(m_url);            
            con = (HttpsURLConnection) url.openConnection();
            // 'Authorization': 'key=' + self.serverKey
            // 'Host': mFcmOptions.host
            con.setRequestProperty("Host", m_host);
            con.setRequestProperty("Authorization","key="+m_api_key);
            con.setRequestProperty("Content-Type", "application/json;charset=utf-8;");
            con.setRequestMethod("POST");
            
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(message.toString());
            wr.flush();
            wr.close();

            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'POST' request to URL : " + url);
            System.out.println("Post parameters : " + message.toString());
            System.out.println("Response Code : " + responseCode);

            // retry error
            if(responseCode == 503){
                // result = common.get_error_message("If the server is temporary unavailable, the C2DM spec requires that we implement exponential backoff and respect any Retry-After header");
                SendRunnable sendRunnable = new SendRunnable(message);
                if(m_retry_count < m_retry) {
                    common.setTimeout(sendRunnable,1000*1);                    
                }                
                m_retry_count=m_retry_count+1;
            }
            else{
                BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                
                //print result
                System.out.println(response.toString());
                JSONObject jsonResponse = new JSONObject(response.toString());
                JSONArray jsonArray = new JSONArray();
                jsonArray.put(jsonResponse);
                // success : 1
                // failure : 1
                // fcm error : {"multicast_id":4613620083223189881,"success":0,"failure":1,"canonical_ids":0,"results":[{"error":"NotRegistered"}]}
                if(jsonResponse.has("success")){
                    if (jsonResponse.getInt("success") > 0){
                        result.put("result","ok");
                        result.put("message",response.toString());                        
                    }
                    else{
                        result.put("result","error");
                        result.put("message",response.toString());
                    }                    
                }
                else{
                    result.put("result","error");
                    result.put("message",response.toString());
                } 
            }                                   
        }
        catch(MalformedURLException ex1){
            ex1.printStackTrace();
            try
            {
                result.put("result","error");
                result.put("message",ex1.getMessage());
            }
            catch(JSONException ex_2){
                ex_2.printStackTrace();
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
            try
            {
                result.put("result","error");
                result.put("message",ex.getMessage());
            }
            catch(JSONException ex_2){
                ex_2.printStackTrace();
            }
            
        }
        
        return result;
    }

    

    class SendRunnable implements Runnable {

        CommonUtil common = new CommonUtil();
        
        JSONObject jsonData;
    
        public SendRunnable(JSONObject msg) {
            jsonData = msg;
        }
    
        @Override
        public void run() {
            System.out.println(jsonData);        
            send(jsonData);
        }   
    
    }

}