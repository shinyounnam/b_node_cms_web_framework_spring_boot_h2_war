package com.veteranscout.cmsweb.alarm;

import com.veteranscout.cmsweb.alarm.*;
import com.veteranscout.cmsweb.util.*;
import java.io.*;
import java.net.*;
import java.nio.charset.Charset;
import java.sql.*;
import java.lang.*;
import java.beans.*;
import org.json.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;



public class MsgSend {
    
    
    CommonUtil common;
    String module_name = "MsgSend";

    public MsgSend(){
        common = new CommonUtil();
    }

    public String SetAdminMsg(JSONObject data)
    {
        String result = "";
        try
        {
            JSONObject jsonData = data;
            common.log(module_name,"SetAdminMsg");
            result = SendAdminMsg(data);
        }
        catch(JSONException ex)
        {
            ex.printStackTrace();
        }
        
        return result;
    }

    private String SendAdminMsg(JSONObject data)
    {
        String result = "";
        try
        {
            JSONObject jsonData = data;
            //console.log(jsonData);
            //console.log("SendPushAlaram ok");
            //console.log(data);
            String IN_VERSION_NAME = common.get_json_value(jsonData,"VERSION_NAME");
            String IN_CONTENT = common.get_json_value(jsonData,"CONTENT");
            String api_key ="";
            FCM fcm = null;
            String send_gbn = common.get_json_value(jsonData,"GBN");
            String IN_MSG_TYPE = "";
            String IN_SEND_DATE_NUMBER = common.get_miliseconds();
            String registration_id = "";
            
            JSONObject message = new JSONObject();
            JSONObject message_body = new JSONObject();
            JSONObject message_data = new JSONObject();
            String IN_TITLE = "";
            String IN_APP_LANG = "KR";
            

            try
            {
                int MSGD_ID = common.get_json_value_int(jsonData,"MSGD_ID");
                String SEND_YN = "Y";
                String SEND_DATE = common.now_time_string();
                //(GBN, ID_KEY_TYPE, ID_KEY, ERR_YN, ERR_MSG, EMP_ID, 
                // CORP_ID, WORK_REQ_TYPE, WORK_REJ_TYPE
                String strSQL = "CALL SP_CMS_ADMIN_MSG_NOTI_SEND_DATE_UPDATE('"+MSGD_ID+"','"+SEND_YN+"','"+SEND_DATE+"')"; 
                common.log(module_name,strSQL);
                JSONArray jsonArray = common.execute_query_commit(strSQL);
                result = common.get_data_ok(jsonArray);                
            }
            catch (Exception ex)
            {
                common.log(module_name,"SendPushAlaram error : " + ex.getMessage());
            }
            
            try
            {
                if (send_gbn.equals("A-E"))
                {
                    // 직업소개소에서 구직자에게 보내는 알람
                    api_key = common.user_api_key;
                    IN_MSG_TYPE = "ADMIN_MSG_AE";

                }
                if (send_gbn.equals("A-B"))
                {
                    // 구직자에서 직업소개소에게 보내는 알람
                    api_key = common.vendor_api_key;
                    IN_MSG_TYPE = "ADMIN_MSG_AB";
                }
                fcm = new FCM(api_key);			
                
                registration_id = common.get_json_value(jsonData,"RECV_MOBILE_REG_ID");
                IN_TITLE = common.get_json_value(jsonData,"SEND_NAME")+
                common.encode("의 메시지",IN_APP_LANG);
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
                //action_type:jsonData.GBN,
                /*
                message = {                     
                    data: { 
                        body: 
                        {
                        MILISECONDS : common.get_miliseconds(),     MSG_TYPE:"WORK_MSG",
                        CONTENT: IN_CONTENT,                        SEND_NAME:jsonData.SEND_NAME,
                        RECV_NAME: jsonData.RECV_NAME,              RECV_NAMES: jsonData.RECV_NAMES,
                        GBN:jsonData.GBN,                           ID_KEY_TYPE:jsonData.ID_KEY_TYPE,
                        ID_KEY:jsonData.ID_KEY,                     SEND_ID:jsonData.SEND_ID,   
                        RECV_ID: jsonData.RECV_ID,                  MSGD_ID: jsonData.MSGD_ID,
                        MSG_ID : jsonData.MSG_ID,                   volume: "3.21.15",                          
                        title:jsonData.SEND_NAME+"의 메시지",       body:IN_CONTENT,                            
                        BASE_TYPE : jsonData.BASE_TYPE,             id: 1,
                        type: common.msg_work_msg_type,             isShow: 1,
                        sound:"default"
                        }
                    }
                };
                */
                message_body.put("MILISECONDS",Integer.parseInt(common.get_miliseconds())); 
                message_body.put("MSG_TYPE","WORK_MSG");
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
                message_body.put("BASE_TYPE", common.get_json_value(jsonData,"BASE_TYPE"));
                message_body.put("volume","3.21.15");
                message_body.put("title",IN_TITLE); 
                message_body.put("body",IN_CONTENT);
                message_body.put("type",common.msg_work_msg_type); 
                message_body.put("isShow",1);
                message_body.put("sound","default");
                /*
                message = {                     
                    data: { 
                        body: 
                        {
                        }
                    }
                }
                */
                message_data.put("body",message_body);
                message.put("data", message_data);

                if (send_gbn.equals("A-E"))
                {
                    String strSQL = "CALL SP_M_EMP_CONF_NOTI_PUSH_SELECT('"+common.get_json_value_int(jsonData,"RECV_ID")+"')";
                    JSONArray jsonConf = common.execute_query(strSQL);
                    if(common.get_json_array_value(jsonConf,"MSG_YN").equals("Y")){
                        if (registration_id.equals("")==false)
                        {
                            JSONObject json_result_message = fcm.send(message); 
                            result = json_result_message.toString();
                            common.log(module_name,"fcm.send result="+result);
                        }                        
                    }
                    else
                    {
                        common.log(module_name,"PUSH_MSG_YN=N, EMP_ID : " + jsonData.getInt("RECV_ID"));
                    }
                }
                else if (send_gbn.equals("A-B"))
                {
                    String strSQL = "CALL SP_M_CORP_CONF_ALARM_INFO_1_1_92('"+common.get_json_value_int(jsonData,"RECV_ID")+"')";
                    JSONArray jsonConf = common.execute_query(strSQL);
                    if(common.get_json_array_value(jsonConf,"MSG_YN").equals("Y")){
                        if (registration_id.equals("")==false)
                        {
                            JSONObject json_result_message = fcm.send(message);                                
                            result = json_result_message.toString();
                            common.log(module_name,"fcm.send result="+result);
                        }
                    }
                    else{
                        common.log(module_name,"PUSH_MSG_YN=N, CORP_ID : " + jsonData.getInt("RECV_ID"));
                    }
                }                
            }
            catch (Exception ex)
            {
                common.log(module_name,"SendPushAlaram FCM error : " + ex.getMessage());
            }
            
            //common.log(module_name,"SendPushAlaram ok : " + JSON.stringify(jsonData));
        }
        catch (Exception ex)
        {
            common.log(module_name,"SendPushAlaram Error : " + ex.getMessage());
        }
        return result;
    }

   
    public String SetEmpMsg(JSONObject data)
    {
        String result = "";
        try
        {
            JSONObject jsonData = data;
            common.log(module_name,"SetEmpMsg : MSGD_ID " + jsonData.getInt("MSGD_ID"));
            result = SendEmpMsg(data);
        }
        catch(JSONException ex)
        {
            ex.printStackTrace();
        }
        
        return result;
    }

    private String SendEmpMsg(JSONObject jsonData)
    {
        String result = "";
        
        try
        {
            
            String IN_VERSION_NAME = common.get_json_value(jsonData,"VERSION_NAME");
            String IN_CONTENT = common.get_json_value(jsonData,"CONTENT");
            String api_key ="";
            FCM fcm = null;
            String send_gbn = common.get_json_value(jsonData,"GBN");
            String IN_MSG_TYPE = "";
            String IN_SEND_DATE_NUMBER = common.get_miliseconds();
            String registration_id = "";

            JSONObject message = new JSONObject();
            JSONObject message_body = new JSONObject();
            JSONObject message_data = new JSONObject();

            String IN_TITLE = "";
            JSONArray jsonArray = null;
            String strSQL = "";
            JSONObject jsonResult = null;
            String smsMsg = "";
            String IN_APP_LANG = "";
            try
            {
                String MSGD_ID = common.get_json_value(jsonData,"MSGD_ID");
                String SEND_YN = "Y";
                String SEND_DATE = common.now_time_string();
                //(GBN, ID_KEY_TYPE, ID_KEY, ERR_YN, ERR_MSG, EMP_ID, 
                // CORP_ID, WORK_REQ_TYPE, WORK_REJ_TYPE
                strSQL = "CALL SP_EMP_MSG_NOTI_SEND_DATE_UPDATE('"+MSGD_ID+"','"+SEND_YN+"','"+SEND_DATE+"')"; 

                jsonArray = common.execute_query_commit(strSQL);
                result = common.get_data_ok(jsonArray);

            }
            catch (Exception ex)
            {
                common.log(module_name,"SendEmpMsg error : " + ex.getMessage());
            }

            try
            {
                // 구직자가 구직자에게 보내는 알람
                api_key = common.user_api_key;
                fcm = new FCM(api_key);

                IN_MSG_TYPE = "EMP_MSG_EE";

                registration_id = common.get_json_value(jsonData,"RECV_MOBILE_REG_ID");
                IN_TITLE = common.get_json_value(jsonData,"SEND_NAME")+
                           common.encode("의 메시지",IN_APP_LANG);
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
                message = { //this may vary according to the message type (single recipient, multicast, topic, et cetera)                        
                    data: {  //you can send only notification or only data(or include both)         
                        body: 
                        {
                        MILISECONDS : common.get_miliseconds(), title:jsonData.SEND_NAME+"의 메시지", 
                        body:IN_CONTENT,                        id: 1,
                        type: common.msg_emp_msg_type,          isShow: 1,
                        sound:"default",                        MSG_TYPE:IN_MSG_TYPE,
                        CONTENT: IN_CONTENT,                    SEND_NAME:jsonData.SEND_NAME,
                        RECV_NAME: jsonData.RECV_NAME,          RECV_NAMES: jsonData.RECV_NAMES,
                        GBN:jsonData.GBN,                       ID_KEY_TYPE:jsonData.ID_KEY_TYPE,
                        ID_KEY:jsonData.ID_KEY,                 SEND_ID:jsonData.SEND_ID,
                        RECV_ID: jsonData.RECV_ID,              MSGD_ID: jsonData.MSGD_ID,
                        MSG_ID : jsonData.MSG_ID,               BASE_TYPE : jsonData.BASE_TYPE,
                        volume: "3.21.15"
                        }
                    }
                };
                */
                message_body.put("MILISECONDS",Integer.parseInt(common.get_miliseconds())); 
                message_body.put("title",IN_TITLE); 
                message_body.put("body",IN_CONTENT);
                message_body.put("id",1);
                message_body.put("type",common.msg_work_msg_type); 
                message_body.put("isShow",1);
                message_body.put("sound","default");
                message_body.put("volume","3.21.15");

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
                /*
                message = {                     
                    data: { 
                        body: 
                        {
                        }
                    }
                }
                */
                message_data.put("body",message_body);
                message.put("data", message_data);
                
                strSQL = "CALL SP_M_EMP_CONF_NOTI_PUSH_SELECT('"+common.get_json_value_int(jsonData,"RECV_ID")+"')";
                JSONArray jsonConf = common.execute_query(strSQL);
                if(common.get_json_array_value(jsonConf,"MSG_YN").equals("Y")){
                    if(common.get_json_value(jsonData,"ATTEND_TYPE").equals("2"))
                    {
                        smsMsg = common.get_json_value(jsonData,"SEND_NAME")+"의 메시지 \n"+
                                IN_CONTENT;
                        
                        JSONObject obj = new JSONObject();
                        obj.put("TYPE","MMS");
                        obj.put("R_TEL_NO",common.get_json_value(jsonData,"EMP_TEL"));
                        obj.put("S_TEL_NO","0221886784");
                        obj.put("S_DATE",common.now_date_str());
                        obj.put("TITLE","메시지");
                        obj.put("MESSAGE",smsMsg);
                        obj.put("REFER","http://vendor.veteranscout.co.kr");
                        strSQL = "{call sc_tran_PROC_veteran(?,?,?,?,?,?,?)}";
                        jsonArray = common.execute_query_mssql_mms(strSQL, obj);   
                        
                        // strSQL = "sc_tran_PROC_veteran 'MMS','"+common.get_json_value(jsonData,"EMP_TEL")+"','0221886784','"+common.now_date_str()+"','메시지','"+smsMsg+"','http://vendor.veteranscout.co.kr',1 ";
                        // jsonArray = common.execute_query_mssql(strSQL);                            
                    }
                    else
                    {
                        if (registration_id.equals("")==false)
                        {
                            jsonResult = fcm.send(message);
                            if(common.get_json_value(jsonResult,"result").equals("error")){
                                common.log(module_name,"SendEmpMsg fcm.send error="+common.get_json_value(jsonResult,"message"));
                            }
                            else{
                                common.log(module_name,"SendEmpMsg fcm.send success="+common.get_json_value(jsonResult,"message"));
                            }
                        }
                        else{
                            common.log(module_name,"fcm registraion id is null");
                        }                            
                    }
                }
                else{
                    common.log(module_name,"MSG_YN = N");
                }                                
            }
            catch (Exception ex2)
            {
                common.log(module_name,"SendEmpMsg FCM error : " + ex2.getMessage());
            }
            //common.log(module_name,"SendPushAlaram ok : " + JSON.stringify(jsonData));
        }
        catch (Exception ex3)
        {
            common.log(module_name,"SendEmpMsg Error : " + ex3.getMessage());
        }
        
        return result;
    }


    public String SetCorpMsg(JSONObject jsonData)
    {
        String result = "";
        try
        {            
            common.system_log("SetCorpMsg ");
            // common.log(module_name,"send message : "+JSON.stringify(data));
            result = SendCorpMsg(jsonData);
        }
        catch(JSONException ex)
        {
            ex.printStackTrace();
        }
        	
        return result;
    }

    private String SendCorpMsg(JSONObject jsonData)
    {
        String result = "";
        
        
        String IN_VERSION_NAME = common.get_json_value(jsonData,"VERSION_NAME");
        String IN_CONTENT = common.get_json_value(jsonData,"CONTENT");
        String api_key ="";
        FCM fcm = null;
        String send_gbn = common.get_json_value(jsonData,"GBN");
        String IN_MSG_TYPE = "";
        String IN_SEND_DATE_NUMBER = common.get_miliseconds();
        String registration_id ="";

        String web_api_key = "";
        FCM web_fcm = null;
        String web_registration_id = "";
        JSONObject web_message = new JSONObject();
        JSONObject web_notification = new JSONObject();
        JSONObject web_data = new JSONObject();
        
        JSONObject message = new JSONObject();
        JSONObject message_body = new JSONObject();
        JSONObject message_data = new JSONObject();
        JSONArray jsonArray = null;
        JSONObject jsonResult = null;
        String IN_TITLE = "";
        String smsMsg = "";
        
        try
        {
            
            String MSGD_ID = common.get_json_value(jsonData,"MSGD_ID");
            String SEND_YN = "Y";
            String SEND_DATE = common.now_time_string();
            String IN_APP_LANG = "KR";
            //(GBN, ID_KEY_TYPE, ID_KEY, ERR_YN, ERR_MSG, EMP_ID, 
            // CORP_ID, WORK_REQ_TYPE, WORK_REJ_TYPE
            String strSQL = "CALL SP_CORP_MSG_NOTI_SEND_DATE_UPDATE('"+MSGD_ID+"','"+SEND_YN+"','"+SEND_DATE+"')"; 

            jsonArray = common.execute_query_commit(strSQL);
            result = common.get_data_ok(jsonArray);

            String aMsgID = MSGD_ID;
            if (send_gbn.equals("B-E"))
            {
                // 직업소개소에서 구직자에게 보내는 알람
                api_key = common.user_api_key;
                IN_MSG_TYPE = "CORP_MSG_BE";
            }
            if (send_gbn.equals("E-B"))
            {
                // 구직자에서 직업소개소에게 보내는 알람
                api_key = common.vendor_api_key;
                IN_MSG_TYPE = "CORP_MSG_EB";
            }
            fcm = new FCM(api_key);
            
            registration_id = common.get_json_value(jsonData,"RECV_MOBILE_REG_ID");
            String IN_TITLE_COMMENT = common.encode("의 메시지",IN_APP_LANG);            
            IN_TITLE = common.get_json_value(jsonData,"SEND_NAME")+IN_TITLE_COMMENT;
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
            message_data.put("action_type",common.get_json_value(jsonData,"GBN"));
            
            /*
            message = { 
                data: 
                {  
                    body: 
                    {
                    MILISECONDS : common.get_miliseconds(),
                    MSG_TYPE:IN_MSG_TYPE,
                    CONTENT: IN_CONTENT,
                    SEND_NAME:jsonData.SEND_NAME,
                    RECV_NAME: jsonData.RECV_NAME,
                    RECV_NAMES: jsonData.RECV_NAMES,
                    GBN:jsonData.GBN,
                    ID_KEY_TYPE:jsonData.ID_KEY_TYPE,
                    ID_KEY:jsonData.ID_KEY,
                    SEND_ID:jsonData.SEND_ID,
                    RECV_ID: jsonData.RECV_ID,
                    MSGD_ID: jsonData.MSGD_ID,
                    MSG_ID : jsonData.MSG_ID,
                    BASE_TYPE : jsonData.BASE_TYPE,
                    title:jsonData.SEND_NAME+"의 메시지", 
                    body:IN_CONTENT,
                    id: 1,
                    type: common.msg_corp_msg_type, 
                    isShow: 1
                    }
                }
            };
            */
            message_body.put("MILISECONDS",Integer.parseInt(common.get_miliseconds())); 
            message_body.put("title",IN_TITLE); 
            message_body.put("body",IN_CONTENT);
            message_body.put("id",1);
            message_body.put("type",common.msg_work_msg_type); 
            message_body.put("isShow",1);
            message_body.put("sound","default");
            message_body.put("volume","3.21.15");

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
            /*
            message = {                     
                data: { 
                    body: 
                    {
                    }
                }
            }
            */
            message_data.put("body",message_body);
            message.put("data", message_data);

            if (send_gbn.equals("B-E"))
            {   
                strSQL = "CALL SP_M_EMP_CONF_NOTI_PUSH_SELECT('"+common.get_json_value_int(jsonData,"RECV_ID")+"')";
                JSONArray jsonConf = common.execute_query(strSQL);
                if(common.get_json_array_value(jsonConf,"MSG_YN").equals("Y")){
                    if(common.get_json_value(jsonData,"ATTEND_TYPE").equals("2"))
                    {
                        smsMsg = common.get_json_value(jsonData,"SEND_NAME")+"의 메시지 \n"+
                                IN_CONTENT;
                        
                        JSONObject obj = new JSONObject();
                        obj.put("TYPE","MMS");
                        obj.put("R_TEL_NO",common.get_json_value(jsonData,"EMP_TEL"));
                        obj.put("S_TEL_NO","0221886784");
                        obj.put("S_DATE",common.now_date_str());
                        obj.put("TITLE","메시지");
                        obj.put("MESSAGE",smsMsg);
                        obj.put("REFER","http://vendor.veteranscout.co.kr");
                        strSQL = "{call sc_tran_PROC_veteran(?,?,?,?,?,?,?)}";
                        jsonArray = common.execute_query_mssql_mms(strSQL, obj);   
                        
                        // strSQL = "sc_tran_PROC_veteran 'MMS','"+common.get_json_value(jsonData,"EMP_TEL")+"','0221886784','"+common.now_date_str()+"','메시지','"+smsMsg+"','http://vendor.veteranscout.co.kr',1 ";
                        // jsonArray = common.execute_query_mssql(strSQL);                            
                    }
                    else
                    {
                        if (registration_id.equals("")==false)
                        {
                            jsonResult = fcm.send(message);
                            if(common.get_json_value(jsonResult,"result").equals("error")){
                                common.log(module_name,"SendEmpMsg fcm.send error="+common.get_json_value(jsonResult,"message"));
                            }
                            else{
                                common.log(module_name,"SendEmpMsg fcm.send success="+common.get_json_value(jsonResult,"message"));
                            }
                        }
                        else{
                            common.log(module_name,"fcm registraion id is null");
                        }                            
                    }
                }
                else{
                    common.log(module_name,"MSG_YN = N");
                }
            }
            else
            {
                
                web_api_key = common.vendor_web_api_key;			
                web_fcm = new FCM(web_api_key);
                web_registration_id = common.get_json_value(jsonData,"CORP_WEB_REG_ID");
                /*
                web_message = { //this may vary according to the message type (single recipient, multicast, topic, et cetera)
                to: web_registration_id, 
                priority: "high",
                collapse_key: IN_MSG_TYPE,
                notification: {
                },
                data {                    
                }
                */
                web_message.put("to",web_registration_id);
                web_message.put("priority","high");
                web_message.put("collapse_key",IN_MSG_TYPE);
                /*
                web_message = {
                    notification: {
                        title: jsonData.SEND_NAME+"의 메시지", 
                        body: IN_CONTENT,
                        id: 1,
                        type: common.alarm_work_res_msg_type, 
                        isShow: 1,
                        sound:"default",
                        click_action:IN_MSG_TYPE					
                    },
                    data: {  //you can send only notification or only data(or include both)
                    }
                }
                */
                web_notification.put("title",IN_TITLE);
                web_notification.put("body",IN_CONTENT);
                web_notification.put("id",1);
                web_notification.put("type",common.alarm_work_res_msg_type);
                web_notification.put("isShow",1);
                web_notification.put("sound","default");
                web_notification.put("click_action",IN_MSG_TYPE);
                /*
                web_message = {
                    notification: {                	
                    },
                    data: {  //you can send only notification or only data(or include both)
                        MILISECONDS : common.get_miliseconds(),
                        MSG_TYPE:IN_MSG_TYPE,
                        CONTENT: IN_CONTENT,
                        SEND_NAME:jsonData.SEND_NAME,
                        RECV_NAME: jsonData.RECV_NAME,
                        RECV_NAMES: jsonData.RECV_NAMES,
                        GBN:jsonData.GBN,
                        ID_KEY_TYPE:jsonData.ID_KEY_TYPE,
                        ID_KEY:jsonData.ID_KEY,
                        SEND_ID:jsonData.SEND_ID,
                        RECV_ID: jsonData.RECV_ID,
                        MSGD_ID: jsonData.MSGD_ID,
                        MSG_ID : jsonData.MSG_ID,
                        BASE_TYPE : jsonData.BASE_TYPE,
                        title:jsonData.SEND_NAME+"의 메시지", 
                        body:IN_CONTENT,
                        id: 1,
                        type: common.msg_corp_msg_type, 
                        isShow: 1
                    }
                };
                */
                web_data.put("MILISECONDS",Integer.parseInt(common.get_miliseconds())); 
                web_data.put("title",IN_TITLE); 
                web_data.put("body",IN_CONTENT);
                web_data.put("id",1);
                web_data.put("type",common.alarm_work_res_msg_type); 
                web_data.put("isShow",1);
                web_data.put("sound","default");
                web_data.put("volume","3.21.15");

                web_data.put("MSG_TYPE",IN_MSG_TYPE);
                web_data.put("CONTENT",IN_CONTENT); 
                web_data.put("SEND_NAME",common.get_json_value(jsonData,"SEND_NAME"));   
                web_data.put("RECV_NAME",common.get_json_value(jsonData,"RECV_NAME")); 
                web_data.put("RECV_NAMES",common.get_json_value(jsonData,"RECV_NAMES")); 
                web_data.put("GBN",common.get_json_value(jsonData,"GBN")); 
                web_data.put("ID_KEY_TYPE",common.get_json_value(jsonData,"ID_KEY_TYPE")); 
                web_data.put("ID_KEY",common.get_json_value_int(jsonData,"ID_KEY")); 
                web_data.put("SEND_ID",common.get_json_value_int(jsonData,"SEND_ID")); 
                web_data.put("RECV_ID",common.get_json_value_int(jsonData,"RECV_ID")); 
                web_data.put("MSGD_ID",common.get_json_value_int(jsonData,"MSGD_ID")); 
                web_data.put("MSG_ID",common.get_json_value_int(jsonData,"MSG_ID")); 

                strSQL = "CALL SP_M_CORP_CONF_ALARM_SELECT_1_1_92('"+common.get_json_value(jsonData,"RECV_ID")+"','MSG_YN')";
                jsonArray = common.execute_query(strSQL);
                jsonResult = jsonArray.getJSONArray(0).getJSONObject(0);
                if(common.get_json_value(jsonResult,"RESULT_YN").equals("Y")){
                    if(registration_id.equals("")==false){
                        jsonResult=fcm.send(message);
                    }
                    if(web_registration_id.equals("")==false){
                        jsonResult=web_fcm.send(web_message);
                    }                    
                }
                else
                {
                    common.log(module_name,"SendCorpMsg PUSH_MSG_YN=N");
                }                                                     
            }            
        }
        catch (Exception ex)
        {
            common.log(module_name,"error:"+ex.getMessage());
        }
        
        return result;
    }


}