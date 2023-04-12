package com.veteranscout.cmsweb.alarm;

import com.veteranscout.cmsweb.util.*;
import org.springframework.beans.factory.annotation.Autowired;  
import java.util.List;
import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;
import org.json.*;
import java.net.*;
import java.io.*;
import java.sql.*;


public class AlarmNoticeRunable implements Runnable{

    
    CommonUtil common;
    String module_name = "AlarmNoticeRunable";    
    AlarmNoticeRunableMms alarmNoticeRunableMms;
    Object jsmMsg = "";

    public AlarmNoticeRunable(Object msg) {
        common = new CommonUtil();
        alarmNoticeRunableMms = new AlarmNoticeRunableMms();
        jsmMsg = msg;
    }

    @Override
    public void run() {
        System.out.println(jsmMsg);        
        SendAlarmJsonData(jsmMsg);
    }   


    private String SendAlarmJsonData(Object aJsonData)
    {
        
        String result = "";

        try
        {
            JSONObject jsonData = (JSONObject)aJsonData;

            String IN_TITLE = common.get_json_value(jsonData,"TITLE");
            String IN_CONTENT = common.get_json_value(jsonData,"CONTENT");
            
            String api_key ="";
            FCM fcm = null;
            String send_gbn = common.get_json_value(jsonData,"GBN");

            String IN_MSG_TYPE = "";
            String strNowTimeString = common.now_time_string();
            String IN_SEND_DATE_NUMBER = common.now_date_integer();
            String registration_id = common.get_json_value(jsonData,"MOBILE_REG_ID");
            
            String ID_TYPE = "I_ID";
            String ID_KEY = common.get_json_value(jsonData,"I_ID");
            String ERR_YN = "N";
            String ERR_MSG = "";
            String GBN = common.get_json_value(jsonData,"GBN");
            String ID = common.get_json_value(jsonData,"ID");
            String N_ID = common.get_json_value(jsonData,"N_ID");
            String MSG_ID = common.get_json_value(jsonData,"MSG_ID");
            if (MSG_ID.equals(""))
            {
                MSG_ID = "0";
            }
            String WORK_DATE = jsonData.getString("WORK_DATE");
            String aMsgID = "";

            JSONObject message = new JSONObject();	
            JSONObject message_body = new JSONObject();
            JSONObject message_data = new JSONObject();
            JSONObject message_notification = new JSONObject();
            

            if (GBN.equals("CORP") || GBN.equals("EMP") || GBN.equals("CORP_ADVM") || GBN.equals("EMP_ADVM") || GBN.equals("EMP_MSG"))
            {
                try
                {
                    String strMMS_YN = "N";
                    if (GBN.equals("CORP"))
                    {
                        // 직업소개소 
                        api_key = common.vendor_api_key;
                        fcm = new FCM(api_key);			
                        IN_MSG_TYPE = "CORP_NOTICE_ALARM";		                        
                        
                        if (common.get_json_value(jsonData,"APP_VERSION").compareTo("2.1.02") >= 0)
                        {
                            /*
                            message = { 
                            //this may vary according to the message type (single recipient, multicast, topic, et cetera)
                            to: registration_id, 
                            priority: "HIGH",
                            collapse_key: IN_MSG_TYPE,
                            data : {body{}}
                            }
                            */
                            message.put("to",registration_id);
                            message.put("priority","HIGH");
                            message.put("collapse_key",IN_MSG_TYPE);
                            /*
                            message = { 
                            data: {  
                                //you can send only notification or only data(or include both)
                                title: IN_TITLE, 
                                message:IN_CONTENT,
                                click_action:IN_MSG_TYPE,
                                body:{}
                            }
                            */
                            message_data.put("title",IN_TITLE);
                            message_data.put("message",IN_CONTENT);
                            message_data.put("click_action",IN_MSG_TYPE);
                            /*
                            message = {                                 												
                                data: {                                      
                                    body: 
                                    {
                                    MILISECONDS : common.get_miliseconds(),         MSG_TYPE:IN_MSG_TYPE,
                                    GBN:jsonData.GBN,							    ID: jsonData.ID,
                                    N_ID: jsonData.N_ID,                            N_GBN:jsonData.N_GBN,
                                    I_ID:jsonData.I_ID,                             NAME : jsonData.NAME,									
                                    WORK_DATE : jsonData.WORK_DATE,                 LAST_VERSION_YN : jsonData.LAST_VERSION_YN,
                                    SEND_TIME:strNowTimeString,                     title:IN_TITLE, 
                                    body:IN_CONTENT,                                id: 1,
                                    type: common.alarm_notice_msg_type,             isShow: 1,
                                    sound:"default"
                                    }
                                }
                            };
                            */
                            message_body.put("MILISECONDS",Integer.parseInt(common.get_miliseconds())); 
                            message_body.put("MSG_TYPE",IN_MSG_TYPE);
                            message_body.put("GBN",common.get_json_value(jsonData,"GBN")); 
                            message_body.put("ID",common.get_json_value_int(jsonData,"ID"));
                            message_body.put("N_ID",common.get_json_value_int(jsonData,"N_ID")); 
                            message_body.put("N_GBN",common.get_json_value(jsonData,"N_GBN")); 
                            message_body.put("I_ID",common.get_json_value_int(jsonData,"I_ID")); 
                            message_body.put("NAME",common.get_json_value(jsonData,"NAME")); 
                            message_body.put("WORK_DATE",common.get_json_value(jsonData,"WORK_DATE")); 
                            message_body.put("LAST_VERSION_YN",common.get_json_value(jsonData,"LAST_VERSION_YN")); 
                            message_body.put("SEND_TIME",strNowTimeString); 
                            message_body.put("title",IN_TITLE); 
                            message_body.put("body",IN_CONTENT); 
                            message_body.put("id",1); 
                            message_body.put("type",common.alarm_notice_msg_type); 
                            message_body.put("isShow",1); 

                            /*
                            message = {                         
                            data : {
                                body:{}
                                }
                            }
                            */
                            message_data.put("body",message_body);
                            message.put("data",message_data); 
                        }
                        else
                        {
                            // APP 실행
                            
                            /*
                            message = { 
                            //this may vary according to the message type (single recipient, multicast, topic, et cetera)
                            to: registration_id, 
                            priority: "HIGH",
                            collapse_key: IN_MSG_TYPE,
                            notification : {}
                            }
                            */
                            message.put("to",registration_id);
                            message.put("priority","HIGH");
                            message.put("collapse_key",IN_MSG_TYPE);
                            /*
                            message = { //this may vary according to the message type (single recipient, multicast, topic, et cetera)                                
                                notification : {
                                    title: IN_TITLE,   message: IN_CONTENT,
                                    // click_action:"https://play.google.com/store/apps/details?id=com.veteranscout.vendorapp"
                                }
                            };
                            */
                            message_notification.put("title",IN_TITLE); message_notification.put("message",IN_CONTENT);
                            message.put("notification",message_notification);
                            
                            strMMS_YN = "N";

                            /*
                            var q = async.queue(function(data,callback){
                                var jsonData1 = data;
                                console.log("CORP MMS TEL : " + jsonData1.TEL_NO);
                                SendSmsAsyncQueue(data,callback);
                            },1);

                        
                            console.log("SendPushAlaram(data) : " + nTimeOut + " time : " + common.now_time());
                            setTimeout(function() { 
                                // SendPushAlaram(data); 
                                q.push(jsonData, function (err) { 
                                    console.log("SendSmsAsyncQueue : error : "+ err);
                                    common.log(module_name,"SendSmsAsyncQueue : error : "+ err);
                                });
                            },nTimeOut);
                            */
                            // 구글 플레이 설치로 이동 APP 최신버전 에서만 동작
                            /*
                            message = { //this may vary according to the message type (single recipient, multicast, topic, et cetera)
                                to: registration_id, 
                                priority: "HIGH",
                                collapse_key: IN_MSG_TYPE,		
                                notification : {
                                    title: IN_TITLE, 
                                    message: IN_CONTENT,
                                    click_action: "google_play_url",
                                    url : "https://play.google.com/store/apps/details?id=com.veteranscout.vendorapp"
                                },
                                data : {
                                    title: IN_TITLE, 
                                    message: IN_CONTENT,
                                    click_action: "google_play_url",
                                    body:{
                                        title: IN_TITLE, 
                                        message: IN_CONTENT,
                                        click_action: "google_play_url",
                                        url : "https://play.google.com/store/apps/details?id=com.veteranscout.vendorapp"
                                    }
                                    
                                }
                            };
                            */
                        }
                    }
                    else if (GBN.equals("EMP"))
                    {
                        // 구직자
                        api_key = common.user_api_key;
                        fcm = new FCM(api_key);			
                        IN_MSG_TYPE = "EMP_NOTICE_ALARM";                        
                        
                        if (common.get_json_value(jsonData,"APP_VERSION").toString().compareTo("2.0.05") >= 0 )
                        {
                            /*
                            message = { 
                            //this may vary according to the message type (single recipient, multicast, topic, et cetera)
                            to: registration_id, 
                            priority: "HIGH",
                            collapse_key: IN_MSG_TYPE,
                            data : {body{}}
                            }
                            */
                            message.put("to",registration_id);
                            message.put("priority","HIGH");
                            message.put("collapse_key",IN_MSG_TYPE);
                            /*
                            message = { 
                            data: {  
                                //you can send only notification or only data(or include both)
                                title: IN_TITLE, 
                                message:IN_CONTENT,
                                click_action:IN_MSG_TYPE,
                                body:{}
                            }
                            */
                            message_data.put("title",IN_TITLE);
                            message_data.put("message",IN_CONTENT);
                            message_data.put("click_action",IN_MSG_TYPE);
                            /*
                            message = { //this may vary according to the message type (single recipient, multicast, topic, et cetera)                                												
                                data: {  //you can send only notification or only data(or include both)					                                    
                                    body: 
                                    {
                                    MILISECONDS : common.get_miliseconds(),         MSG_TYPE:IN_MSG_TYPE,
                                    GBN:jsonData.GBN,						        ID: jsonData.ID,
                                    N_ID: jsonData.N_ID,                            N_GBN : jsonData.N_GBN,
                                    I_ID:jsonData.I_ID,                             NAME : jsonData.NAME,									
                                    WORK_DATE : jsonData.WORK_DATE,                 LAST_VERSION_YN : jsonData.LAST_VERSION_YN,
                                    SEND_TIME:strNowTimeString,                     title:IN_TITLE, 
                                    body:IN_CONTENT,                                id: 1,
                                    type: common.alarm_notice_msg_type,             isShow: 1,
                                    sound:"default"
                                    }
                                }
                            };
                            */
                            message_body.put("MILISECONDS",Integer.parseInt(common.get_miliseconds())); 
                            message_body.put("MSG_TYPE",IN_MSG_TYPE); 
                            message_body.put("GBN",common.get_json_value(jsonData,"GBN")); 
                            message_body.put("ID",common.get_json_value_int(jsonData,"ID")); 
                            message_body.put("N_ID",common.get_json_value_int(jsonData,"N_ID")); 
                            message_body.put("N_GBN",common.get_json_value(jsonData,"N_GBN")); 
                            message_body.put("WORK_DATE",common.get_json_value(jsonData,"WORK_DATE")); 
                            message_body.put("LAST_VERSION_YN",common.get_json_value(jsonData,"LAST_VERSION_YN")); 
                            message_body.put("SEND_TIME",strNowTimeString); 
                            message_body.put("title",IN_TITLE); 
                            message_body.put("body",IN_CONTENT); 
                            message_body.put("id",1); 
                            message_body.put("type",common.alarm_notice_msg_type); 
                            message_body.put("isShow",1); 
                            message_body.put("sound","default"); 
                            /*
                            message = {                         
                            data : {
                                body:{}
                                }
                            }
                            */
                            message_data.put("body",message_body);
                            message.put("data",message_data); 
                        }
                        else
                        {
                            // APP 실행
                             /*
                            message = { 
                            //this may vary according to the message type (single recipient, multicast, topic, et cetera)
                            to: registration_id, 
                            priority: "HIGH",
                            collapse_key: IN_MSG_TYPE,
                            notification : {}
                            }
                            */
                            message.put("to",registration_id);
                            message.put("priority","HIGH");
                            message.put("collapse_key",IN_MSG_TYPE);
                            /*
                            message = { //this may vary according to the message type (single recipient, multicast, topic, et cetera)                                
                                notification : {
                                    title: IN_TITLE,   message: IN_CONTENT,
                                    // click_action:"https://play.google.com/store/apps/details?id=com.veteranscout.vendorapp"
                                }
                            };
                            */
                            message_notification.put("title",IN_TITLE); message_notification.put("message",IN_CONTENT);
                            message.put("notification",message_notification);
                            /*
                            message = { //this may vary according to the message type (single recipient, multicast, topic, et cetera)
                                to: registration_id, 
                                priority: "HIGH",
                                collapse_key: IN_MSG_TYPE,		
                                // click_action:"https://play.google.com/store/apps/details?id=com.veteranscout.userapp",
                                notification : {
                                    title: IN_TITLE, 
                                    message: IN_CONTENT,
                                    // click_action:"https://play.google.com/store/apps/details?id=com.veteranscout.userapp"
                                }
                            };
                            */
                            // 구글 플레이 설치로 이동 APP 최신버전 에서만 동작
                            /*
                            message = { //this may vary according to the message type (single recipient, multicast, topic, et cetera)
                                to: registration_id, 
                                priority: "HIGH",
                                collapse_key: IN_MSG_TYPE,		
                                notification : {
                                    title: IN_TITLE, 
                                    message: IN_CONTENT,
                                    click_action: "google_play_url",
                                    url : "https://play.google.com/store/apps/details?id=com.veteranscout.userapp"
                                },
                                data : {
                                    title: IN_TITLE, 
                                    message: IN_CONTENT,
                                    click_action: "google_play_url",
                                    body:{
                                        title: IN_TITLE, 
                                        message: IN_CONTENT,
                                        click_action: "google_play_url",
                                        url : "https://play.google.com/store/apps/details?id=com.veteranscout.userapp"
                                    }
                                    
                                }
                            };
                            */
                        }								
                    }
                    else if (GBN == "EMP_MSG")
                    {
                        // 구직자
                        aMsgID = common.get_json_value(jsonData,"MSGD_ID");
                        // 직업소개소에서 구직자에게 보내는 알람
                        api_key = common.user_api_key;
                        IN_MSG_TYPE = "CORP_MSG_BE";
                        fcm = new FCM(api_key);			
                        
                        IN_TITLE = common.get_json_value(jsonData,"SEND_NAME")+"의 메시지";
                        /*
                        message = { 
                        //this may vary according to the message type (single recipient, multicast, topic, et cetera)
                        to: registration_id, 
                        priority: "high",
                        collapse_key: IN_MSG_TYPE,
                        data : {body{}}
                        }
                        */
                        message.put("to",registration_id);
                        message.put("priority","high");
                        message.put("collapse_key",IN_MSG_TYPE);
                        /*
                        message = { 
                        data: {  
                            //you can send only notification or only data(or include both)
                            title: IN_TITLE, 
                            message:IN_CONTENT,
                            click_action:IN_MSG_TYPE,
                            body:{}
                        }
                        */
                        //action_type:jsonData.MSG_GBN,
                        message_data.put("title",IN_TITLE);
                        message_data.put("message",IN_CONTENT);
                        message_data.put("click_action",IN_MSG_TYPE);
                        /*
                        message = {                             				
                            data: 
                            {  
                                body: 
                                {
                                MILISECONDS : common.get_miliseconds(),     MSG_TYPE:IN_MSG_TYPE,
                                CONTENT: IN_CONTENT,                        SEND_NAME:jsonData.SEND_NAME,
                                RECV_NAME: jsonData.RECV_NAME,              RECV_NAMES: jsonData.RECV_NAMES,
                                GBN:jsonData.MSG_GBN,                       ID_KEY_TYPE:jsonData.ID_KEY_TYPE,
                                ID_KEY:jsonData.ID_KEY,                     SEND_ID:jsonData.SEND_ID,
                                RECV_ID: jsonData.RECV_ID,                  MSGD_ID: jsonData.MSGD_ID,      
                                MSG_ID : jsonData.MSG_ID,                   BASE_TYPE : jsonData.BASE_TYPE,
                                title:jsonData.SEND_NAME+"의 메시지",       body:IN_CONTENT,    
                                id: 1,                                      type: common.msg_corp_msg_type, 
                                isShow: 1
                                }
                            }
                        };
                        */
                        message_body.put("MILISECONDS",Integer.parseInt(common.get_miliseconds())); 
                        message_body.put("MSG_TYPE",IN_MSG_TYPE); 
                        message_body.put("CONTENT",IN_CONTENT); 
                        message_body.put("SEND_NAME",common.get_json_value(jsonData,"SEND_NAME")); 
                        message_body.put("RECV_NAME",common.get_json_value(jsonData,"RECV_NAME")); 
                        message_body.put("RECV_NAMES",common.get_json_value(jsonData,"RECV_NAMES")); 
                        message_body.put("GBN",common.get_json_value(jsonData,"GBN")); 
                        message_body.put("ID_KEY_TYPE",common.get_json_value(jsonData,"ID_KEY_TYPE"));
                        message_body.put("ID_KEY",common.get_json_value_int(jsonData,"ID_KEY")); 
                        message_body.put("SEND_ID",common.get_json_value_int(jsonData,"SEND_ID"));
                        message_body.put("RECV_ID",common.get_json_value_int(jsonData,"RECV_ID")); 
                        message_body.put("MSGD_ID",common.get_json_value_int(jsonData,"MSGD_ID"));
                        message_body.put("MSG_ID",common.get_json_value_int(jsonData,"MSG_ID")); 
                        message_body.put("BASE_TYPE",common.get_json_value(jsonData,"BASE_TYPE"));
                        message_body.put("title",IN_TITLE); 
                        message_body.put("body",IN_CONTENT);
                        message_body.put("id",1); 
                        message_body.put("type",common.msg_corp_msg_type); 
                        message_body.put("isShow",1);
                        /*
                        message = {                         
                        data : {
                            body:{}
                            }
                        }
                        */
                        message_data.put("body",message_body);
                        message.put("data",message_data); 
                        
                    }
                    else if (GBN.equals("CORP_ADVM"))
                    {
                        // 직업소개소 
                        api_key = common.vendor_api_key;
                        fcm = new FCM(api_key);			
                        IN_MSG_TYPE = "CORP_ADVM_ALARM";
                        
                        
                        common.log(module_name,"Veteran Notice Name : " + jsonData.getString("NAME") + " ID : " + jsonData.getString("ID") + " GBN : " + jsonData.getString("GBN"));
                        common.system_log("Veteran Notice Name : " + jsonData.getString("NAME") + " ID : " + jsonData.getString("ID") + " GBN : " + jsonData.getString("GBN"));
                        
                        /*
                        message = { 
                        //this may vary according to the message type (single recipient, multicast, topic, et cetera)
                        to: registration_id, 
                        priority: "high",
                        collapse_key: IN_MSG_TYPE,
                        data : {body{}}
                        }
                        */
                        message.put("to",registration_id);
                        message.put("priority","high");
                        message.put("collapse_key",IN_MSG_TYPE);
                        /*
                        message = { 
                        data: {  
                            //you can send only notification or only data(or include both)
                            title: IN_TITLE, 
                            message:IN_CONTENT,
                            click_action:IN_MSG_TYPE,
                            body:{}
                        }
                        */
                        message_data.put("title",IN_TITLE);
                        message_data.put("message",IN_CONTENT);
                        message_data.put("click_action",IN_MSG_TYPE);
                        /*
                        message = { 
                            data: { 
                                body: 
                                {
                                MILISECONDS : common.get_miliseconds(),     MSG_TYPE:IN_MSG_TYPE,
                                GBN:jsonData.GBN,							ID: jsonData.ID,
                                N_ID: jsonData.N_ID,                        N_GBN:jsonData.N_GBN,
                                I_ID:jsonData.I_ID,                         NAME : jsonData.NAME,									
                                WORK_DATE : jsonData.WORK_DATE,             LAST_VERSION_YN : "",
                                SEND_TIME:strNowTimeString,                 title:IN_TITLE, 
                                body:IN_CONTENT,                            id: 1,
                                type: common.alarm_notice_msg_type,         isShow: 1,
                                sound:"default"
                                }
                            }
                        };
                        */
                        message_body.put("MILISECONDS",Integer.parseInt(common.get_miliseconds())); 
                        message_body.put("MSG_TYPE",IN_MSG_TYPE);
                        message_body.put("GBN",common.get_json_value(jsonData,"GBN")); 
                        message_body.put("ID",common.get_json_value_int(jsonData,"ID")); 
                        message_body.put("N_ID",common.get_json_value_int(jsonData,"N_ID")); 
                        message_body.put("N_GBN",common.get_json_value(jsonData,"N_GBN"));
                        message_body.put("I_ID",common.get_json_value_int(jsonData,"I_ID")); 
                        message_body.put("NAME",common.get_json_value(jsonData,"NAME"));
                        message_body.put("WORK_DATE",common.get_json_value(jsonData,"WORK_DATE")); 
                        message_body.put("LAST_VERSION_YN","");
                        message_body.put("SEND_TIME",strNowTimeString); 
                        message_body.put("title",IN_TITLE);
                        message_body.put("body",IN_CONTENT); 
                        message_body.put("id",1);
                        message_body.put("type",common.alarm_notice_msg_type); 
                        message_body.put("isShow",1);
                        message_body.put("sound","default");
                        /*
                        message = {                         
                        data : {
                            body:{}
                            }
                        }
                        */
                        message_data.put("body",message_body);
                        message.put("data",message_data); 
                        
                    }
                    else if (GBN.equals("EMP_ADVM"))
                    {
                        // 구직자
                        api_key = common.user_api_key;
                        fcm = new FCM(api_key);			
                        IN_MSG_TYPE = "EMP_ADVM_ALARM";
                        
                        
                        common.log(module_name,"Veteran Notice Name : " + jsonData.getString("NAME") + " ID : " + jsonData.getInt("ID") + " GBN : " + jsonData.getString("GBN"));
                        common.system_log("Veteran Notice Name : " + jsonData.getString("NAME") + " ID : " + jsonData.getInt("ID") + " GBN : " + jsonData.getString("GBN"));
                        /*
                        message = { 
                        //this may vary according to the message type (single recipient, multicast, topic, et cetera)
                        to: registration_id, 
                        priority: "high",
                        collapse_key: IN_MSG_TYPE,
                        data : {body{}}
                        }
                        */
                        message.put("to",registration_id);
                        message.put("priority","high");
                        message.put("collapse_key",IN_MSG_TYPE);
                        /*
                        message = { 
                        data: {  
                            //you can send only notification or only data(or include both)
                            title: IN_TITLE, 
                            message:IN_CONTENT,
                            click_action:IN_MSG_TYPE,
                            body:{}
                        }
                        */
                        message_data.put("title",IN_TITLE);
                        message_data.put("message",IN_CONTENT);
                        message_data.put("click_action",IN_MSG_TYPE);

                        /*
                        message = {
                            data: {                                
                                body: 
                                {
                                MILISECONDS : common.get_miliseconds(),         MSG_TYPE:IN_MSG_TYPE,
                                GBN:jsonData.GBN,							    ID: jsonData.ID,
                                N_ID: jsonData.N_ID,                            N_GBN : jsonData.N_GBN,
                                I_ID:jsonData.I_ID,                             NAME : jsonData.NAME,									
                                WORK_DATE : jsonData.WORK_DATE,                 LAST_VERSION_YN : "",
                                SEND_TIME:strNowTimeString,                     title:IN_TITLE, 
                                body:IN_CONTENT,                                id: 1,
                                type: common.alarm_notice_msg_type,             isShow: 1,
                                sound:"default"
                                }
                            }
                        };
                        */
                        message_body.put("MILISECONDS",Integer.parseInt(common.get_miliseconds())); 
                        message_body.put("MSG_TYPE",IN_MSG_TYPE);
                        message_body.put("GBN",common.get_json_value_int(jsonData,"GBN")); 
                        message_body.put("ID",common.get_json_value_int(jsonData,"ID"));
                        message_body.put("N_ID",common.get_json_value_int(jsonData,"N_ID")); 
                        message_body.put("N_GBN",common.get_json_value(jsonData,"N_GBN"));
                        message_body.put("I_ID",common.get_json_value_int(jsonData,"I_ID")); 
                        message_body.put("NAME",common.get_json_value(jsonData,"NAME"));
                        message_body.put("WORK_DATE",common.get_json_value(jsonData,"WORK_DATE")); 
                        message_body.put("LAST_VERSION_YN","");
                        message_body.put("SEND_TIME",strNowTimeString); 
                        message_body.put("title",IN_TITLE);
                        message_body.put("body",IN_CONTENT); 
                        message_body.put("id",1); 
                        message_body.put("type",common.alarm_notice_msg_type); 
                        message_body.put("isShow",1); 
                        message_body.put("sound","default");

                        /*
                        message = {                         
                        data : {
                            body:{}
                            }
                        }
                        */
                        message_data.put("body",message_body);
                        message.put("data",message_data); 
                    }
                    
                    
                    if (registration_id.equals("")==false && strMMS_YN.equals("N"))
                    {
                        // test
                        /*
                        if (GBN == "EMP")
                        {
                            common.log(module_name,"fcm.send success=ok REG_ID:"+registration_id+",N_ID : " + jsonData.N_ID + ",ID : " + jsonData.ID + " NAME : " + jsonData.NAME);
                            console.log("fcm.send success=ok REG_ID:"+registration_id+",N_ID : " + jsonData.N_ID + ",ID : " + jsonData.ID + " NAME : " + jsonData.NAME);
                            var aI_ID = jsonData.I_ID;
                            var aGBN = jsonData.GBN;
                            var aID = jsonData.ID;
                            var aSendYN = "Y";
                            var aSendCont = "send ok"
                            var strUpdateSQL = "CALL SP_M_V_NOTICE_EMP_INFO_RESULT_UPDATE('"+aI_ID+"','"+aGBN+"','"+aID+"','"+aSendYN+"','"+aSendCont+"')";
                            console.log("strUpdateSQL : " + strUpdateSQL);
                            JSONArray jsonArray = common.execute_query_commit(strUpdateSQL);
                        }	
                        */
                        JSONObject json_result_message = fcm.send(message);
                        result = json_result_message.toString();

                        if(json_result_message.getString("result").equals("error")){                            
                            int aI_ID = common.get_json_value_int(jsonData,"I_ID");
                            String aGBN = common.get_json_value(jsonData,"GBN");
                            int aID = common.get_json_value_int(jsonData,"ID");
                            String aSendYN = "N";
                            String aSendCont = json_result_message.getString("message");
                            String strUpdateSQL = "CALL SP_M_V_NOTICE_EMP_INFO_RESULT_UPDATE('"+aI_ID+"','"+aGBN+"','"+aID+"','"+aSendYN+"','"+aSendCont+"')";
                            common.system_log("strUpdateSQL : " + strUpdateSQL);
                            
                            JSONArray jsonArray = common.execute_query_commit(strUpdateSQL);
                            result = common.get_data_ok(jsonArray);
                        }
                        else{
                            
                            int aI_ID = common.get_json_value_int(jsonData,"I_ID");
                            String aGBN = common.get_json_value(jsonData,"GBN");
                            int aID = common.get_json_value_int(jsonData,"ID");
                            String aSendYN = "Y";
                            String aSendCont = "send ok";
                            String strUpdateSQL = "CALL SP_M_V_NOTICE_EMP_INFO_RESULT_UPDATE('"+aI_ID+"','"+aGBN+"','"+aID+"','"+aSendYN+"','"+aSendCont+"')";
                            common.system_log("strUpdateSQL : " + strUpdateSQL);
                            
                            JSONArray jsonArray = common.execute_query_commit(strUpdateSQL);
                            result = common.get_data_ok(jsonArray);
                        }                                
                    }
                    else if (strMMS_YN.equals("N"))
                    {                        
                        int aI_ID = common.get_json_value_int(jsonData,"I_ID");
                        String aGBN = common.get_json_value(jsonData,"GBN");
                        int aID = common.get_json_value_int(jsonData,"ID");
                        String aSendYN = "N";
                        String aSendCont = "registration_id is null";
                        String strUpdateSQL = "CALL SP_M_V_NOTICE_EMP_INFO_RESULT_UPDATE('"+aI_ID+"','"+aGBN+"','"+aID+"','"+aSendYN+"','"+aSendCont+"')";
                        
                        common.system_log("strUpdateSQL : " + strUpdateSQL);
                            
                        JSONArray jsonArray = common.execute_query_commit(strUpdateSQL);
                        result = common.get_data_ok(jsonArray);
                    }						
                    
                }
                catch (JSONException ex)
                {
                    common.log(module_name,"SendPushAlaram FCM error : " + ex.getMessage());                    
                }

                //console.log("Mobile Registration ID : "+jsonData.MOB_REG_ID+",send_message="+IN_CONTENT);
                /*
                try
                {					
                    
                    monk_db.get("AlarmNotice").insert({
                        API_KEY: api_key,                               GBN : jsonData.GBN,
                        MSG_TYPE : IN_MSG_TYPE,                         RECV_MOBILE_REG_ID: jsonData.MOBILE_REG_ID,
                        N_ID : jsonData.N_ID,                           I_ID:jsonData.I_ID,
                        ID: jsonData.ID,                                NAME : jsonData.NAME,
                        WORK_DATE : jsonData.WORK_DATE,                 TITLE: IN_TITLE,
                        BODY: IN_CONTENT,                               SEND_DATE_NUMBER : IN_SEND_DATE_NUMBER,         
                        SEND_TIME:strNowTimeString,
                    });		
                                       
                }
                catch (MongoException ex)
                {
                    common.log(module_name,"mongoose db save error : " + ex.getMessage());                    
                }
                */
            }
            else if (GBN.equals("CORP_SMS") || GBN.equals("EMP_SMS"))
            {
                
                try
                {                    
                    String M_R_TEL_NO = "";
                    int M_FILE_ID = 0;
                    String MMS_MSG = "";
                    String M_TITLE = common.get_json_value(jsonData,"TITLE");
                    String strSQL = "sc_tran_PROC_veteran";                    
                    String M_GBN = common.get_json_value(jsonData,"GBN");
                    
                    M_R_TEL_NO = common.get_json_value(jsonData,"TEL_NO");
                    // console.log("M_R_TEL_NO : "+jsonResult.TEL_NO);
                    MMS_MSG = common.get_json_value(jsonData,"CONTENT");
                    common.system_log("MMS_MSG : " +MMS_MSG);
                    
                    JSONArray jsonArray = alarmNoticeRunableMms.SendMms(jsonData);
                    result = common.get_data_ok(jsonArray);



                    if(common.get_json_array_value(jsonArray,"result").equals("error")){                        
                        int aI_ID = common.get_json_value_int(jsonData,"I_ID");
                        String aGBN = common.get_json_value(jsonData,"GBN");
                        int aID = common.get_json_value_int(jsonData,"ID");
                        String aSendYN = "N";
                        String aSendCont = "error : " + common.get_json_array_value(jsonArray,"message");
                        String strUpdateSQL = "CALL SP_M_V_NOTICE_EMP_INFO_RESULT_UPDATE('"+aI_ID+"','"+aGBN+"','"+aID+"','"+aSendYN+"','"+aSendCont+"')";
                        common.system_log("strUpdateSQL : " + strUpdateSQL);

                        JSONArray jsonResult = common.execute_query_commit(strUpdateSQL);
                        result = common.get_data_ok(jsonResult);
                    }
                    else{
                        
                        common.log(module_name,"send res alarm ok");

                        int aI_ID = common.get_json_value_int(jsonData,"I_ID");
                        String aGBN = common.get_json_value(jsonData,"GBN");
                        int aID = common.get_json_value_int(jsonData,"ID");
                        String aSendYN = "Y";
                        String aSendCont = "send ok";
                        String strUpdateSQL = "CALL SP_M_V_NOTICE_EMP_INFO_RESULT_UPDATE('"+aI_ID+"','"+aGBN+"','"+aID+"','"+aSendYN+"','"+aSendCont+"')";
                        common.system_log("strUpdateSQL : " + strUpdateSQL);

                        JSONArray jsonResult = common.execute_query_commit(strUpdateSQL);
                        result = common.get_data_ok(jsonResult);

                    }


                    // common.log(module_name,"MMS_MSG : " +MMS_MSG);
                    // test
                    /*
                    var aI_ID = jsonData.I_ID;
                    var aGBN = jsonData.GBN;
                    var aID = jsonData.ID;
                    var aSendYN = "Y";
                    var aSendCont = "send ok";
                    var strUpdateSQL = "CALL SP_M_V_NOTICE_EMP_INFO_RESULT_UPDATE('"+aI_ID+"','"+aGBN+"','"+aID+"','"+aSendYN+"','"+aSendCont+"')";
                    var result = "ok";
                    console.log("strUpdateSQL : " + strUpdateSQL);
                    common.execute_query_commit(strUpdateSQL);
                    */                    
                }
                catch (JSONException ex)
                {
                    common.log(module_name,"SendResSms error "+ex.getMessage());
                }
            }		
            
        }
        catch (Exception ex)
        {
            common.log(module_name,"SendPushAlaram : " + ex.getMessage());
        }
        return result;
    }

}