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

public class AlarmUtil {

    CommonUtil common = new CommonUtil();
    String m_url = "";
    String m_host = "";
    String m_method = "POST";
    String m_api_key = "";
    String module_name = "fcm";
    int m_retry_count = 0;
    int m_retry = 1;
    

    public AlarmUtil() {        
        m_url = common.ALARM_URL;
        m_host = common.ALARM_HOST;
        m_api_key = common.ALARM_API_KEY;
    }

    public JSONObject send_alarm_req_json_object(JSONObject message) {
        JSONObject result = new JSONObject();
        try {
            SendAlarmReqRunnable sendAlarmReqRunnable = new SendAlarmReqRunnable(message);
            common.setRunable(sendAlarmReqRunnable);
            result.put("result","ok");
            result.put("message","");
        } catch (Exception e) {
            common.log(module_name,e.getMessage());
        }
        return result;
    }

    private JSONObject send_alarm_req(JSONObject message){
        JSONObject result = new JSONObject();
        try {
            m_url = common.ALARM_URL + "/alarm_req_server/api/send";            
            // Create URL instance.
            URL url = new URL(m_url);
            // create connection.
            HttpURLConnection conn;
            conn = (HttpURLConnection) url.openConnection();
            conn.setUseCaches(false);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            // set method as POST or GET
            conn.setRequestMethod("POST");
            // pass FCM server key
            conn.setRequestProperty("Authorization", "key=" + m_api_key);
            // Specify Message Format
            conn.setRequestProperty("Content-Type", "application/json;charset=utf-8;");
            String tokenId = "";

            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            common.log(module_name, "message : " + message.toString());

            wr.write(message.toString());
            wr.flush();  

            int status = 0;

            if (null != conn) {
                status = conn.getResponseCode();
            }

            if (status != 0) {
                if (status == 200) {
                    // SUCCESS message
                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    common.log(module_name, "Android Notification Response : " + reader.readLine());
                    result.put("result", "ok");
                    result.put("message", "success:" + reader.readLine());
                } else if (status == 401) {
                    // client side error
                    common.log(module_name, "Notification Response Error occurred :");
                    result.put("result", "error");
                    result.put("message", "Notification Response :  Error occurred :");
                } else if (status == 501) {
                    // server side error
                    common.log(module_name, "Notification Response : [ errorCode=ServerError ] ");
                    result.put("result", "error");
                    result.put("message", "Notification Response : [ errorCode=ServerError ] ");
                } else if (status == 503) {
                    // server side error
                    System.out.println("Notification Response : FCM Service is Unavailable ");
                    result.put("result", "error");
                    result.put("message", "Notification Response : FCM Service is Unavailable ");
                }
            }
        } catch (MalformedURLException mlfexception) {
            // Prototcal Error
            common.log(module_name, "Error occurred while sending push Notification!.." + mlfexception.getMessage());
            try {
                result.put("result", "error");
                result.put("message", "Error occurred while sending push Notification!.." + mlfexception.getMessage());
            } catch (JSONException e) {
                common.log(module_name,e.getMessage());
            }

        } catch (Exception mlfexception) {
            // URL problem
            common.log(module_name, "Reading URL, Error occurred while sending push Notification!.." + mlfexception.getMessage());

            try {
                result.put("result", "error");
                result.put("message",
                        "Reading URL, Error occurred while sending push Notification!.." + mlfexception.getMessage());
            } catch (JSONException e) {
                common.log(module_name,e.getMessage());
            }

        }
        return result;
    }

    
    class SendAlarmReqRunnable implements Runnable {

        CommonUtil common = new CommonUtil();

        JSONObject jsonData;

        public SendAlarmReqRunnable(JSONObject msg) {
            jsonData = msg;
        }

        @Override
        public void run() {
            System.out.println(jsonData);
            send_alarm_req(jsonData);
        }

    }

    public JSONObject send_alarm_json_array(JSONObject message) {

        JSONObject result = new JSONObject();
        try {
            SendAlarmRunnable sendAlarmRunnable = new SendAlarmRunnable(message);
            common.setRunable(sendAlarmRunnable);
            result.put("result","ok");
            result.put("message","");
        } catch (Exception e) {
            common.log(module_name,e.getMessage());
        }
        return result;
    }

    private JSONObject send_alarm(JSONObject message){
        JSONObject result = new JSONObject();
        try {
            m_url = common.ALARM_URL + "/alarm_send_server/api/send";
            // Create URL instance.
            URL url = new URL(m_url);
            // create connection.
            HttpURLConnection conn;
            conn = (HttpURLConnection) url.openConnection();
            conn.setUseCaches(false);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            // set method as POST or GET
            conn.setRequestMethod("POST");
            // pass FCM server key
            conn.setRequestProperty("Authorization", "key=" + m_api_key);
            // Specify Message Format
            conn.setRequestProperty("Content-Type", "application/json;charset=utf-8;");
            String tokenId = "";

            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            common.log(module_name, "message : " + message.toString());

            wr.write(message.toString());
            wr.flush();  

            int status = 0;

            if (null != conn) {
                status = conn.getResponseCode();
            }

            if (status != 0) {
                if (status == 200) {
                    // SUCCESS message
                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    System.out.println("Android Notification Response : " + reader.readLine());
                    result.put("result", "ok");
                    result.put("message", "success:" + reader.readLine());
                } else if (status == 401) {
                    // client side error
                    System.out.println("Notification Response Error occurred :");
                    result.put("result", "error");
                    result.put("message", "Notification Response :  Error occurred :");
                } else if (status == 501) {
                    // server side error
                    System.out.println("Notification Response : [ errorCode=ServerError ] ");
                    result.put("result", "error");
                    result.put("message", "Notification Response : [ errorCode=ServerError ] ");
                } else if (status == 503) {
                    // server side error
                    System.out.println("Notification Response : FCM Service is Unavailable ");
                    result.put("result", "error");
                    result.put("message", "Notification Response : FCM Service is Unavailable ");
                }
            }
        } catch (MalformedURLException mlfexception) {
            // Prototcal Error
            System.out.println("Error occurred while sending push Notification!.." + mlfexception.getMessage());
            try {
                result.put("result", "error");
                result.put("message", "Error occurred while sending push Notification!.." + mlfexception.getMessage());
            } catch (JSONException e) {
                // TODO: handle exception
            }

        } catch (Exception mlfexception) {
            // URL problem
            System.out.println(
                    "Reading URL, Error occurred while sending push Notification!.." + mlfexception.getMessage());

            try {
                result.put("result", "error");
                result.put("message",
                        "Reading URL, Error occurred while sending push Notification!.." + mlfexception.getMessage());
            } catch (JSONException e) {
                common.log(module_name,e.getMessage());
            }

        }
        return result;
    }


    class SendAlarmRunnable implements Runnable {

        CommonUtil common = new CommonUtil();

        JSONObject jsonData;

        public SendAlarmRunnable(JSONObject msg) {
            jsonData = msg;
        }

        @Override
        public void run() {
            System.out.println(jsonData);
            send_alarm(jsonData);
        }

    }

    public JSONObject send_msg_json_array(JSONObject message) {
        JSONObject result = new JSONObject();
        try {
            SendMsgRunnable sendMsgRunnable = new SendMsgRunnable(message);
            common.setRunable(sendMsgRunnable);
            result.put("result","ok");
            result.put("message","");
        } catch (Exception e) {
            common.log(module_name,e.getMessage());
        }
        return result;
        
    }

    private JSONObject send_msg(JSONObject message){
        JSONObject result = new JSONObject();
        try {
            m_url = common.ALARM_URL + "/msg_send_server/api/send";
            // Create URL instance.
            URL url = new URL(m_url);
            // create connection.
            HttpURLConnection conn;
            conn = (HttpURLConnection) url.openConnection();
            conn.setUseCaches(false);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            // set method as POST or GET
            conn.setRequestMethod("POST");
            // pass FCM server key
            conn.setRequestProperty("Authorization", "key=" + m_api_key);
            // Specify Message Format
            conn.setRequestProperty("Content-Type", "application/json;charset=utf-8;");
            String tokenId = "";

            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            common.log(module_name, "message : " + message.toString());

            wr.write(message.toString());
            wr.flush();  

            int status = 0;

            if (null != conn) {
                status = conn.getResponseCode();
            }

            if (status != 0) {
                if (status == 200) {
                    // SUCCESS message
                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    System.out.println("Android Notification Response : " + reader.readLine());
                    result.put("result", "ok");
                    result.put("message", "success:" + reader.readLine());
                } else if (status == 401) {
                    // client side error
                    System.out.println("Notification Response Error occurred :");
                    result.put("result", "error");
                    result.put("message", "Notification Response :  Error occurred :");
                } else if (status == 501) {
                    // server side error
                    System.out.println("Notification Response : [ errorCode=ServerError ] ");
                    result.put("result", "error");
                    result.put("message", "Notification Response : [ errorCode=ServerError ] ");
                } else if (status == 503) {
                    // server side error
                    System.out.println("Notification Response : FCM Service is Unavailable ");
                    result.put("result", "error");
                    result.put("message", "Notification Response : FCM Service is Unavailable ");
                }
            }
        } catch (MalformedURLException mlfexception) {
            // Prototcal Error
            System.out.println("Error occurred while sending push Notification!.." + mlfexception.getMessage());
            try {
                result.put("result", "error");
                result.put("message", "Error occurred while sending push Notification!.." + mlfexception.getMessage());
            } catch (JSONException e) {
                common.log(module_name,e.getMessage());
            }

        } catch (Exception mlfexception) {
            // URL problem
            System.out.println(
                    "Reading URL, Error occurred while sending push Notification!.." + mlfexception.getMessage());

            try {
                result.put("result", "error");
                result.put("message",
                        "Reading URL, Error occurred while sending push Notification!.." + mlfexception.getMessage());
            } catch (JSONException e) {
                common.log(module_name,e.getMessage());
            }

        }
        return result;
    }

    class SendMsgRunnable implements Runnable {

        CommonUtil common = new CommonUtil();

        JSONObject jsonData;

        public SendMsgRunnable(JSONObject msg) {
            jsonData = msg;
        }

        @Override
        public void run() {
            System.out.println(jsonData);
            send_msg(jsonData);
        }
    }

    public JSONObject send_alarm_notice_json_object(JSONObject message) {
        JSONObject result = new JSONObject();
        try {
            SendAlarmNoticeRunnable sendAlarmNoticeRunnable = new SendAlarmNoticeRunnable(message);
            common.setRunable(sendAlarmNoticeRunnable);
            result.put("result","ok");
            result.put("message","");
        } catch (Exception e) {
            common.log(module_name,e.getMessage());
        }
        return result;
    }

    private JSONObject send_alarm_notice(JSONObject message){
        JSONObject result = new JSONObject();
        try {
            m_url = common.ALARM_URL + "/alarm_notice_server/api/send";            
            // Create URL instance.
            URL url = new URL(m_url);
            // create connection.
            HttpURLConnection conn;
            conn = (HttpURLConnection) url.openConnection();
            conn.setUseCaches(false);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            // set method as POST or GET
            conn.setRequestMethod("POST");
            // pass FCM server key
            conn.setRequestProperty("Authorization", "key=" + m_api_key);
            // Specify Message Format
            conn.setRequestProperty("Content-Type", "application/json;charset=utf-8;");
            String tokenId = "";

            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            common.log(module_name, "message : " + message.toString());

            wr.write(message.toString());
            wr.flush();  

            int status = 0;

            if (null != conn) {
                status = conn.getResponseCode();
            }

            if (status != 0) {
                if (status == 200) {
                    // SUCCESS message
                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    common.log(module_name, "Android Notification Response : " + reader.readLine());
                    result.put("result", "ok");
                    result.put("message", "success:" + reader.readLine());
                } else if (status == 401) {
                    // client side error
                    common.log(module_name, "Notification Response Error occurred :");
                    result.put("result", "error");
                    result.put("message", "Notification Response :  Error occurred :");
                } else if (status == 501) {
                    // server side error
                    common.log(module_name, "Notification Response : [ errorCode=ServerError ] ");
                    result.put("result", "error");
                    result.put("message", "Notification Response : [ errorCode=ServerError ] ");
                } else if (status == 503) {
                    // server side error
                    System.out.println("Notification Response : FCM Service is Unavailable ");
                    result.put("result", "error");
                    result.put("message", "Notification Response : FCM Service is Unavailable ");
                }
            }
        } catch (MalformedURLException mlfexception) {
            // Prototcal Error
            common.log(module_name, "Error occurred while sending push Notification!.." + mlfexception.getMessage());
            try {
                result.put("result", "error");
                result.put("message", "Error occurred while sending push Notification!.." + mlfexception.getMessage());
            } catch (JSONException e) {
                // TODO: handle exception
            }

        } catch (Exception mlfexception) {
            // URL problem
            common.log(module_name, "Reading URL, Error occurred while sending push Notification!.." + mlfexception.getMessage());

            try {
                result.put("result", "error");
                result.put("message",
                        "Reading URL, Error occurred while sending push Notification!.." + mlfexception.getMessage());
            } catch (JSONException e) {
                // TODO: handle exception
            }

        }
        return result;
    }

    
    class SendAlarmNoticeRunnable implements Runnable {

        CommonUtil common = new CommonUtil();

        JSONObject jsonData;

        public SendAlarmNoticeRunnable(JSONObject msg) {
            jsonData = msg;
        }

        @Override
        public void run() {
            System.out.println(jsonData);
            send_alarm_notice(jsonData);
        }

    }   

}