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


public class AlarmReject {


    CommonUtil common;
    String module_name = "AlarmReject";

    public AlarmReject(){
        common = new CommonUtil();        
    }

    public class RejectWorkRunable implements Runnable{

        String module_name = "RejectWorkRunable";
        Object jsmMsg = null;

        public RejectWorkRunable(Object msg) {
            jsmMsg = msg;
        }

        @Override
        public void run() {
            System.out.println(jsmMsg);        
            RejectWork(jsmMsg);
        }   
    }

    public String SetWorkReject(JSONObject jsonData) 
    {
        String result = "";
        
        try
        {
            int nTimeOut = Integer.parseInt(jsonData.getString("WORK_REJ_TYPE")) * 1000 * 60;	        

            String strSQL = "UPDATE MSG_INFO " + 
                        "SET SEND_DATE = '"+common.now_time_string()+"' " +
                        "WHERE ID_KEY_TYPE = 'BOOK_ID' " +
                        "AND R_GBN = 'B-E' AND ID_KEY = '"+jsonData.getInt("BOOK_ID")+"' ";
            //console.log(strSQL);
            common.log(module_name,"send_time update work strSQL : " + strSQL);
            result = common.execute_update_commit(strSQL);                 
            
            RejectWorkRunable rejectWorkRunable = new RejectWorkRunable(jsonData);
            common.setTimeout(rejectWorkRunable,nTimeOut);
            common.log(module_name,"start reject work timeout : " + nTimeOut + " BOOK_ID=" + jsonData.getInt("BOOK_ID"));

        }
        catch(JSONException ex)
        {
            common.log(module_name,"send_time update work err : " + ex.getMessage());
        }

        return result;
    }

    public String RejectWork(Object data)
    {
        JSONObject jsonData = (JSONObject)data;
        String result = "";
        try
        {
            int BOOK_ID = jsonData.getInt("BOOK_ID");
            String strSQL = "CALL SP_M_WORK_BOOK_REJECT('"+BOOK_ID+"')"; 
            JSONArray jsonArray = common.execute_query_commit(strSQL);
            result = common.get_data_ok(jsonArray);            
        }
        catch(JSONException ex1){
            ex1.printStackTrace();
        }
        catch(Exception ex)
        {
            common.log(module_name,"RejectWork error : " + ex.getMessage());
        }
        
        return result;
    }

    public class RejectWorkRecommandRunable implements Runnable{

        String module_name = "RejectWorkRecommandRunable";
        Object jsmMsg = null;

        public RejectWorkRecommandRunable(Object msg) {
            jsmMsg = msg;
        }

        @Override
        public void run() {
            System.out.println(jsmMsg);        
            RejectWorkRecommand(jsmMsg);
        }   
    }

    public String SetWorkRecommandReject(JSONObject jsonData)
    {
        String result = "";
        try
        {
            int nTimeOut = Integer.parseInt(jsonData.getString("WORK_REJ_TYPE")) * 1000 * 60;
            /*
            UPDATE WORK_MSG 
            SET SEND_DATE = ''
            WHERE A.ID_KEY_TYPE = 'BOOK_ID'
              AND A.R_GBN = 'B-E'
              AND A.ID_KEY = 
            */
            String strSQL = "UPDATE MSG_INFO " + 
                         "SET SEND_DATE = '"+common.now_time_string()+"' " +
                         "WHERE ID_KEY_TYPE = 'RMD_ID' " +
                         "AND R_GBN = 'B-E' AND ID_KEY = '"+jsonData.getInt("RMD_ID")+"' ";
            //console.log(strSQL);
            common.log(module_name,"send_time update work strSQL : " + strSQL);
            result = common.execute_update_commit(strSQL);

            RejectWorkRecommandRunable rejectWorkRecommandRunable = new RejectWorkRecommandRunable(jsonData);
            common.setTimeout(rejectWorkRecommandRunable,nTimeOut);
            common.log(module_name,"start reject work timeout : " + nTimeOut + " RMD_ID=" + jsonData.getInt("RMD_ID"));
        }
        catch(JSONException ex)
        {
            common.log(module_name,"send_time update work err : " + ex.getMessage());
        }
    
        
        return result;
    }

    public String RejectWorkRecommand(Object data)
    {
        
        String result = "";
        JSONObject jsonData = (JSONObject)data;
        try
        {
            int RMD_ID = common.get_json_value_int(jsonData,"RMD_ID");
            String strSQL = "CALL SP_M_WORK_RECOMMAND_REJECT('"+RMD_ID+"')"; 
            common.log(module_name,"recommand work strSQL : " + strSQL);
            JSONArray jsonArray = common.execute_query_commit(strSQL);
            result = common.get_data_ok(jsonArray);
        }
        catch(JSONException ex1){
            common.log(module_name,"RejectWorkRecommand error : "+ex1.getMessage());
        }        
        catch(Exception ex)
        {
            common.log(module_name,"RejectWorkRecommand error : "+ex.getMessage());
        }
        
        return result;
    }

    public class RejectWorkVSRunable implements Runnable{

        String module_name = "RejectWorkVSRunable";
        Object jsmMsg = null;

        public RejectWorkVSRunable(Object msg) {
            jsmMsg = msg;
        }

        @Override
        public void run() {
            System.out.println(jsmMsg);        
            RejectWorkVS(jsmMsg);
        }   
    }
    
    public String SetWorkVSReject(JSONObject jsonData)
    {
        String result = "";
        
        
        try
        {
            /*
            UPDATE WORK_MSG 
            SET SEND_DATE = ''
            WHERE A.ID_KEY_TYPE = 'BOOK_ID'
              AND A.R_GBN = 'B-E'
              AND A.ID_KEY = 
            */
            int nTimeOut = Integer.parseInt(jsonData.getString("EMC_REJ_TYPE")) * 1000 * 60;

            String strSQL = "UPDATE MSG_INFO " + 
                         "SET SEND_DATE = '"+common.now_time_string()+"' " +
                         "WHERE ID_KEY_TYPE = 'BOOK_ID' " +
                         "AND R_GBN = 'E-B' AND ID_KEY = '"+jsonData.getInt("BOOK_ID")+"' ";
            //console.log(strSQL);            
            common.log(module_name,"send_time update emc work strSQL : " + strSQL);
            result = common.execute_update_commit(strSQL);

            RejectWorkVSRunable rejectWorkVSRunable = new RejectWorkVSRunable(jsonData);
            common.setTimeout(rejectWorkVSRunable,nTimeOut);
            common.log(module_name,"start reject emc work timeout : " + nTimeOut + " BOOK_ID=" + jsonData.getInt("BOOK_ID"));
        }
        catch(JSONException ex)
        {
            common.log(module_name,"send_time update emc work err : " + ex.getMessage());
        }
        return result;
    }

    public String RejectWorkVS(Object data)
    {
        JSONObject jsonSendData = (JSONObject)data;
        String result = "";
        try
        {
            int BOOK_ID = jsonSendData.getInt("BOOK_ID");
            String strSQL = "CALL SP_M_WORK_BOOK_VS_REJECT('"+BOOK_ID+"')";             
            try
            {
                JSONArray jsonResult = common.execute_query_commit(strSQL);
                common.log(module_name,"reject emc work : " + strSQL);
                JSONObject jsonData = jsonResult.getJSONArray(0).getJSONObject(0);
                //console.log(jsonData);
                //console.log("SendPushAlaram ok");
                //console.log(data);

                String IN_VERISON_NAME = jsonData.getString("VERSION_NAME");                
                String IN_CONTENT = "";	
                String api_key ="";		
                String IN_MSG_TYPE = "";
                String registration_id = jsonData.getString("MOBILE_REG_ID");
                String IN_GBN = "";
                String IN_ID_KEY_TYPE = "BOOK_ID";
                String IN_SEND_DATE_NUMBER = common.now_date_integer();
                String IN_BASE_TYPE = "";
                String IN_TITLE = "";
                String IN_APP_LANG = "KR";

                if (jsonData.getString("RES_YN").equals("Y"))
                {
                    IN_GBN = jsonData.getString("GBN");
                    IN_CONTENT = jsonData.getString("WORK_DATE")+"  "+
                                jsonData.getString("SCHEDULE_NAME")+" " + 
                                "[" +
                                jsonData.getString("JOB_GBN_NM")+"] " +
                                jsonData.getString("JOB_TYPE_NM")+" "+
                                jsonData.getString("WORK_PAY")+
                                common.encode("원 ",IN_APP_LANG)+
                                " " +
                                common.encode("위의 일자리에 선발되어",IN_APP_LANG)+
                                common.encode("예약완료 되었습니다.",IN_APP_LANG);
                    IN_MSG_TYPE = "EMC_WORK_RES_ALARM";
                    api_key = common.user_api_key;
                    IN_TITLE = jsonData.getString("CORP_NAME")+
                               common.encode("의 알람",IN_APP_LANG);
                }
                else if(jsonData.getString("RES_YN").equals("N"))
                {
                    if (jsonData.getString("GBN").equals("ECN"))
                    {
                        IN_GBN = "ECN";
                        IN_CONTENT = jsonData.getString("EMP_NAME")+
                                common.encode("님이 근무예약을 취소하였습니다. ",IN_APP_LANG)+
                                jsonData.getString("SCHEDULE_NAME")+" "+
                                "["+
                                jsonData.getString("JOB_GBN_NM")+"] "+
                                jsonData.getString("JOB_TYPE_NM")+" "+
                                jsonData.getString("WORK_DATE") + " "+
                                common.encode("필요인력 ",IN_APP_LANG)+
                                jsonData.getInt("NEED_CNT")+
                                common.encode("명 중 ",IN_APP_LANG)+
                                jsonData.getInt("BOOK_CNT")+
                                common.encode("명 인력 배치 예약 완료  ",IN_APP_LANG)+
                                common.encode("( 소속 : ",IN_APP_LANG)+
                                jsonData.getInt("MEMBER_CNT")+
                                common.encode(" / 긴급 : ",IN_APP_LANG) + 
                                jsonData.getInt("BOOK_URGENT_CNT")+" )";
                        IN_MSG_TYPE = "WORK_RES_ALARM";
                        api_key = common.vendor_api_key;
                        IN_TITLE = jsonData.getString("EMP_NAME")+"의 알람";
                    }
                    else
                    {                        
                        IN_GBN = jsonData.getString("GBN");
                        IN_CONTENT = jsonData.getString("EMP_NAME")+
                                common.encode("님이 근무요청을 거절하였습니다. ",IN_APP_LANG)+
                                jsonData.getString("SCHEDULE_NAME")+" "+
                                "["+
                                jsonData.getString("JOB_GBN_NM")+"] "+
                                jsonData.getString("JOB_TYPE_NM")+" "+
                                jsonData.getString("WORK_DATE") + " "+
                                common.encode("필요인력 ",IN_APP_LANG)+
                                jsonData.getInt("NEED_CNT")+
                                common.encode("명 중 ",IN_APP_LANG)+
                                jsonData.getInt("BOOK_CNT")+
                                common.encode("명 인력 배치 예약 완료  ",IN_APP_LANG)+
                                common.encode("( 소속 : ",IN_APP_LANG)+
                                jsonData.getInt("MEMBER_CNT")+
                                common.encode(" / 긴급 : ",IN_APP_LANG) + 
                                jsonData.getInt("BOOK_URGENT_CNT")+" )";
                        IN_MSG_TYPE = "EMC_WORK_RES_ALARM";
                        api_key = common.vendor_api_key;
                        IN_TITLE = jsonData.getString("EMP_NAME")+
                                   common.encode("의 알람",IN_APP_LANG);
                    }
                    
                }

                
                FCM fcm = null;
                String send_gbn = jsonData.getString("GBN");

                JSONObject message = new JSONObject();
                JSONObject message_body = new JSONObject();
                JSONObject message_data = new JSONObject();

                int MSG_ID = 0;
                try
                {
                    String GBN = jsonData.getString("GBN");
                    String ID_TYPE = "BOOK_ID";
                    int ID_KEY = jsonData.getInt("BOOK_ID");
                    String ERR_YN = "N";
                    String ERR_MSG = "";
                    int EMP_ID = jsonData.getInt("EMP_ID");
                    int CORP_ID = jsonData.getInt("CORP_ID");
                    String EMC_REQ_TYPE = jsonData.getString("EMC_REQ_TYPE");
                    String EMC_REJ_TYPE = jsonData.getString("EMC_REJ_TYPE");
                    String WORK_DATE = jsonData.getString("WORK_DATE");
                    String W_REQ_GBN = jsonData.getString("W_REQ_GBN");
                    String R_GBN = "B-E";
                    //(GBN, ID_KEY_TYPE, ID_KEY, ERR_YN, ERR_MSG, EMP_ID, 
                    // CORP_ID, WORK_REQ_TYPE, WORK_REJ_TYPE
                    strSQL = "CALL SP_M_MSG_INFO_INSERT('"+GBN+"','"+ID_TYPE+"','"+ID_KEY+"'," +
                        "'"+EMP_ID+"','"+CORP_ID+"','"+EMC_REQ_TYPE+"'," +
                        "'"+EMC_REJ_TYPE+"','"+R_GBN+"','"+common.get_value(IN_CONTENT)+"','"+WORK_DATE+"','"+W_REQ_GBN+"')"; 
                    common.log(module_name,strSQL);

                    JSONArray jsonMsg = common.execute_query_commit(strSQL);
                    MSG_ID = common.get_json_array_value_int(jsonMsg,"MSG_ID");
                    if(jsonData.has("MSG_ID")){
                        jsonData.remove("MSG_ID");
                    }
                    jsonData.put("MSG_ID",MSG_ID);
                    
                    try
                    {
                        fcm = new FCM(api_key);
                        
                        IN_BASE_TYPE = "VS";

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
                                MILISECONDS : common.get_miliseconds(),         title:jsonData.CORP_NAME+"의 알람", 
                                body:IN_CONTENT,                                id: 1,
                                type: common.alarm_work_emc_res_msg_type,       isShow: 1,
                                sound:"default",                                MSG_TYPE:IN_MSG_TYPE,
                                GBN:IN_GBN,                                     W_REQ_GBN:jsonData.W_REQ_GBN,
                                BOOK_ID:jsonData.BOOK_ID,                       EMP_ID: jsonData.EMP_ID,
                                EMP_NAME : jsonData.EMP_NAME,                   SCHEDULE_NAME : jsonData.SCHEDULE_NAME,
                                SITE_NAME : jsonData.SITE_NAME,                 BIZ_NAME : jsonData.BIZ_NAME,
                                JOB_GBN_NM : jsonData.JOB_GBN_NM,               JOB_TYPE_NM : jsonData.JOB_TYPE_NM,
                                WORK_PAY : jsonData.WORK_PAY,                   WORK_DATE : jsonData.WORK_DATE,
                                NEED_CNT : jsonData.NEED_CNT,                   BOOK_CNT : jsonData.BOOK_CNT,
                                MEMBER_CNT : jsonData.MEMBER_CNT,               BOOK_URGENT_CNT : jsonData.BOOK_URGENT_CNT,
                                CORP_NAME : jsonData.CORP_NAME,                 WORK_REQ_TYPE : jsonData.EMC_REQ_TYPE,
                                WORK_REJ_TYPE : jsonData.EMC_REJ_TYPE,          RES_YN : jsonData.RES_YN,
                                BASE_TYPE : IN_BASE_TYPE,                       ID_KEY_TYPE : IN_ID_KEY_TYPE,
                                ID_KEY : jsonData.BOOK_ID,                      volume: "3.21.15",
                                MSG_ID : jsonData.MSG_ID,                       WORK_ID : jsonData.WORK_ID,
                                NEED_ID : jsonData.NEED_ID
                                }
                            }
                        };
                        */
                        message_body.put("MILISECONDS",Integer.parseInt(common.get_miliseconds())); 
                        message_body.put("title",IN_TITLE);
                        message_body.put("body",IN_CONTENT); 
                        message_body.put("id",1); 
                        message_body.put("type",common.alarm_work_emc_res_msg_type); 
                        message_body.put("isShow",1); 
                        message_body.put("sound","default"); 
                        message_body.put("MSG_TYPE",IN_MSG_TYPE);
                        message_body.put("GBN",IN_GBN); 
                        message_body.put("W_REQ_GBN",common.get_json_value(jsonData,"W_REQ_GBN"));
                        message_body.put("BOOK_ID",common.get_json_value_int(jsonData,"BOOK_ID")); 
                        message_body.put("EMP_ID",common.get_json_value_int(jsonData,"EMP_ID"));
                        message_body.put("EMP_NAME",common.get_json_value(jsonData,"EMP_NAME")); 
                        message_body.put("SCHEDULE_NAME",common.get_json_value(jsonData,"SCHEDULE_NAME"));
                        message_body.put("SITE_NAME",common.get_json_value(jsonData,"SITE_NAME")); 
                        message_body.put("BIZ_NAME",common.get_json_value(jsonData,"BIZ_NAME"));
                        message_body.put("JOB_GBN_NM",common.get_json_value(jsonData,"JOB_GBN_NM")); 
                        message_body.put("JOB_TYPE_NM",common.get_json_value(jsonData,"JOB_TYPE_NM"));
                        message_body.put("WORK_PAY",common.get_json_value(jsonData,"WORK_PAY")); 
                        message_body.put("WORK_DATE",common.get_json_value(jsonData,"WORK_DATE"));
                        message_body.put("NEED_CNT",common.get_json_value_int(jsonData,"NEED_CNT")); 
                        message_body.put("BOOK_CNT",common.get_json_value_int(jsonData,"BOOK_CNT"));
                        message_body.put("MEMBER_CNT",common.get_json_value_int(jsonData,"MEMBER_CNT")); 
                        message_body.put("BOOK_URGENT_CNT",common.get_json_value_int(jsonData,"BOOK_URGENT_CNT"));
                        message_body.put("CORP_NAME",common.get_json_value(jsonData,"CORP_NAME")); 
                        message_body.put("WORK_REQ_TYPE",common.get_json_value(jsonData,"EMC_REQ_TYPE"));
                        message_body.put("WORK_REJ_TYPE",common.get_json_value(jsonData,"EMC_REJ_TYPE")); 
                        message_body.put("RES_YN",common.get_json_value(jsonData,"RES_YN"));
                        message_body.put("BASE_TYPE",IN_BASE_TYPE); 
                        message_body.put("ID_KEY_TYPE",IN_ID_KEY_TYPE);
                        message_body.put("ID_KEY",common.get_json_value_int(jsonData,"BOOK_ID")); 
                        message_body.put("volume","3.21.15");
                        message_body.put("MSG_ID",common.get_json_value_int(jsonData,"MSG_ID")); 
                        message_body.put("WORK_ID",common.get_json_value_int(jsonData,"WORK_ID")); 
                        message_body.put("NEED_ID",common.get_json_value_int(jsonData,"NEED_ID")); 
                        /*
                        message = {                         
                        data : {
                            body:{}
                            }
                        }
                        */
                        message_data.put("body",message_body);
                        message.put("data",message_data); 

                        if (registration_id.equals("")==false)
                        {
                            JSONObject json_result_message = fcm.send(message);
                            result = json_result_message.toString();
                            common.log(module_name,"fcm.send result="+result+",BOOK_ID : " + jsonSendData.getInt("BOOK_ID"));
                        }
                        //common.log(module_name,"SendPushAlaram=send_message="+IN_CONTENT+" gcm_message="+JSON.stringify(message));
                    }
                    catch (JSONException ex)
                    {
                        common.log(module_name,"SendPushAlaram FCM error : " + ex.getMessage());
                    }
                }
                catch (Exception ex)
                {
                    common.log(module_name,"SendPushAlaram error : " + ex.getMessage());
                }
                //common.log(module_name,"SendPushAlaram ok : " + JSON.stringify(jsonData));
            }
            catch (Exception ex)
            {
                common.log(module_name,"SendPushAlaram Error : " + ex.getMessage());
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


    public class RejectVeteranServiceWorkRunable implements Runnable{

        String module_name = "RejectVeteranServiceWorkRunable";
        Object jsmMsg = null;

        public RejectVeteranServiceWorkRunable(Object msg) {
            jsmMsg = msg;
        }

        @Override
        public void run() {
            System.out.println(jsmMsg);        
            RejectVeteranServiceWork(jsmMsg);
        }   
    }

    public String SetVeteranServiceReject(JSONObject jsonData)
    {
        String result = "";
        
        
        try
        {
            int nTimeOut = Integer.parseInt(jsonData.getString("EMC_REJ_TYPE")) * 1000 * 60;
            /*
            UPDATE WORK_MSG 
            SET SEND_DATE = ''
            WHERE A.ID_KEY_TYPE = 'BOOK_ID'
              AND A.R_GBN = 'B-E'
              AND A.ID_KEY = 
            */
            String strSQL = "UPDATE MSG_INFO " + 
                         "SET SEND_DATE = '"+common.now_time_string()+"' " +
                         "WHERE ID_KEY_TYPE = 'I_ID' " +
                         "AND R_GBN = 'B-E' AND ID_KEY = '"+jsonData.getInt("I_ID")+"' "+
                         "AND EMP_ID = '"+jsonData.getInt("EMP_ID")+"' ";
            //console.log(strSQL);
            common.log(module_name,"send_time update emc work item strSQL : " + strSQL);
            result = common.execute_update_commit(strSQL);            

            common.log(module_name,"start reject emc work item timeout : " + nTimeOut + " I_ID =" + jsonData.getInt("I_ID") + " EMP_ID=" + jsonData.getInt("EMP_ID"));

            RejectVeteranServiceWorkRunable rejectVeteranServiceWorkRunable = new RejectVeteranServiceWorkRunable(jsonData);
            common.setTimeout(rejectVeteranServiceWorkRunable,nTimeOut);
        }
        catch(JSONException ex)
        {
            common.log(module_name,"send_time update emc work err : " + ex.getMessage());
        }

        return result;
    }

    public String RejectVeteranServiceWork(Object data)
    {
        String result = "";
        JSONObject jsonData = (JSONObject)data;

        try
        {
            int I_ID = jsonData.getInt("I_ID");
            int EMP_ID = jsonData.getInt("EMP_ID");

            String strSQL = "CALL SP_M_VETERAN_SERVICE_MSG_REJECT('"+I_ID+"','"+EMP_ID+"')"; 
            common.log(module_name,"reject emc work item strSQL : " + strSQL);
            JSONArray jsonArray = common.execute_query_commit(strSQL);
            result = common.get_data_ok(jsonArray);
        }
        catch(JSONException ex1){
            ex1.printStackTrace();
        }        
        catch(Exception ex){
            ex.printStackTrace();
        }
        return result;
    }

    public class RejectVendorServiceWorkRunable implements Runnable{

        String module_name = "RejectVendorServiceWorkRunable";
        Object jsmMsg = null;

        public RejectVendorServiceWorkRunable(Object msg) {
            jsmMsg = msg;
        }

        @Override
        public void run() {
            System.out.println(jsmMsg);        
            RejectVendorServiceWork(jsmMsg);
        }   
    }

    public String SetVendorServiceReject(JSONObject jsonData)
    {
        String result = "";
        
        
        try
        {
            int nTimeOut = Integer.parseInt(jsonData.getString("EMC_REJ_TYPE")) * 1000 * 60;
            /*
            UPDATE WORK_MSG 
            SET SEND_DATE = ''
            WHERE A.ID_KEY_TYPE = 'BOOK_ID'
              AND A.R_GBN = 'B-E'
              AND A.ID_KEY = 
            */
            String strSQL = "UPDATE MSG_INFO " + 
                         "SET SEND_DATE = '"+common.now_time_string()+"' " +
                         "WHERE ID_KEY_TYPE = 'V_I_ID' " +
                         "AND R_GBN = 'B-B' AND ID_KEY = '"+jsonData.getInt("I_ID")+"' "+
                         "AND CORP_ID = '"+jsonData.getInt("CORP_ID")+"' ";
            //console.log(strSQL);
            common.log(module_name,"send_time update emc work item strSQL : " + strSQL);
            result = common.execute_update_commit(strSQL);            

            common.log(module_name,"start reject emc work item timeout : " + nTimeOut + " I_ID=" + jsonData.getInt("I_ID") + " CORP_ID=" + jsonData.getInt("CORP_ID"));
            
            RejectVendorServiceWorkRunable rejectVendorServiceWorkRunable = new RejectVendorServiceWorkRunable(jsonData);
            common.setTimeout(rejectVendorServiceWorkRunable,nTimeOut);

        }
        catch(JSONException ex)
        {
            common.log(module_name,"send_time update emc work err : " + ex.getMessage());
        }
    
        return result;
    }

    public String RejectVendorServiceWork(Object data)
    {
        String result = "";
        JSONObject jsonData = (JSONObject)data;
        try
        {
            result = common.get_message("ok","");
        }
        catch(Exception ex){
            ex.printStackTrace();
        }        
        return result;
    }

    public class RejectShowRunable implements Runnable{

        String module_name = "RejectShowRunable";
        Object jsmMsg = null;

        public RejectShowRunable(Object msg) {
            jsmMsg = msg;
        }

        @Override
        public void run() {
            System.out.println(jsmMsg);        
            RejectShow(jsmMsg);
        }   
    }

    public String SetWorkShowReject(JSONObject jsonData)
    {
        String result = "";
        

        try
        {
            int nTimeOut = Integer.parseInt(jsonData.getString("SHOW_REJ_TYPE")) * 1000 * 60;
            String strSQL = "UPDATE MSG_INFO " + 
                        "SET SEND_DATE = '"+common.now_time_string()+"' " +
                        "WHERE ID_KEY_TYPE = 'SHOW_ID' " +
                        "AND R_GBN = 'B-E' AND ID_KEY = '"+jsonData.getInt("SHOW_ID")+"' ";
            //console.log(strSQL);
            common.log(module_name,"send_time update show strSQL : " + strSQL);
            result = common.execute_update_commit(strSQL);            

            common.log(module_name,"start reject show timeout : " + nTimeOut + " SHOW_ID=" + jsonData.getInt("SHOW_ID"));
            
            RejectShowRunable rejectShowRunable = new RejectShowRunable(jsonData);
            common.setTimeout(rejectShowRunable,nTimeOut);

        }
        catch(JSONException ex)
        {
            common.log(module_name,"send_time update show err : " + ex.getMessage());
        }
        

        return result;
    }


    public String RejectShow(Object data)
    {
        String result = "";
        JSONObject jsonData = (JSONObject)data;
        String strSQL = "";
        JSONArray jsonConf = null;
        try
        {
            strSQL = "CALL SP_M_EMP_ALARM_INFO_SELECT('SHOW_ID','"+common.get_json_value_int(jsonData,"SHOW_ID")+"','"+common.get_json_value_int(jsonData,"EMP_ID")+"')";
            jsonConf = common.execute_query(strSQL);
            if(common.get_json_array_value(jsonConf,"RES_YN").equals("")){
                strSQL = "CALL SP_M_WORK_SHOW_REJECT('"+common.get_json_value_int(jsonData,"SHOW_ID")+"')"; 
                common.log(module_name,"reject work show strSQL : " + strSQL);
                common.log(module_name,"reject work show : " + strSQL);
                try
                {
                    JSONArray jsonResult = common.execute_query_commit(strSQL);
                    if (jsonResult.getJSONArray(0).getJSONObject(0).getString("result").equals("ok"))
                    {
                        SendAlarmShowRes(jsonResult.getJSONArray(0).getJSONObject(0));
                    }
                    else
                    {
                        common.log(module_name, "WorkShow Reject SHOW_ID : " + common.get_json_value_int(jsonData,"SHOW_ID") + " result : error ");
                    }
                }
                catch (JSONException ex)
                {
                    common.log(module_name, "WorkShow Reject SHOW_ID : " + common.get_json_value_int(jsonData,"SHOW_ID") + "error : " + ex.getMessage());
                }
            }
            else{
                common.log(module_name,"WorkShow ResYN SHOW_ID = "+common.get_json_value_int(jsonData,"SHOW_ID"));
            }     
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
       
        return result;
    }

    public String SendAlarmShowRes(JSONObject data)
    {
        String result = "";
        JSONObject jsonData = data;
        
        try
        {
            String IN_VERSION_NAME = jsonData.getString("VERSION_NAME");
            String IN_CONTENT = "";
            String IN_MSG_TYPE = "";	
            String IN_GBN = "";
            String IN_ID_KEY_TYPE = "SHOW_ID";
            String IN_TITLE = "";
            String IN_APP_LANG = "KR";
            if (jsonData.getString("RES_YN").equals("Y"))
            {
                IN_GBN = jsonData.getString("GBN");
                IN_CONTENT = jsonData.getString("EMP_NAME")+
                             common.encode("님이 출근 가능으로 응답하였습니다. ",IN_APP_LANG)+
                             jsonData.getString("SCHEDULE_NAME")+" "+
                             "["+
                             jsonData.getString("JOB_GBN_NM")+"] "+
                             jsonData.getString("JOB_TYPE_NM")+" "+
                             jsonData.getString("WORK_DATE") + " "+
                             common.encode("필요인력 ",IN_APP_LANG)+
                             jsonData.getInt("NEED_CNT")+
                             common.encode("명 중 ",IN_APP_LANG)+
                             jsonData.getInt("BOOK_CNT")+
                             common.encode("명 인력 배치 예약 완료  ",IN_APP_LANG)+
                             common.encode("( 소속 : ",IN_APP_LANG)+
                             jsonData.getInt("MEMBER_CNT")+
                             common.encode(" / 긴급 : ",IN_APP_LANG) + 
                             jsonData.getInt("BOOK_URGENT_CNT")+" )";
                IN_TITLE = jsonData.getString("EMP_NAME")+
                            common.encode("의 알람",IN_APP_LANG);
                IN_MSG_TYPE = "WORK_SHOW_RES_ALARM";
            }
            else if(jsonData.getString("RES_YN").equals("N"))
            {
                IN_GBN = jsonData.getString("GBN");
                IN_CONTENT = jsonData.getString("EMP_NAME")+
                             common.encode("님이 출근 거부로 응답하였습니다. ",IN_APP_LANG)+
                             jsonData.getString("SCHEDULE_NAME")+" "+
                             "["+
                             jsonData.getString("JOB_GBN_NM")+"] "+
                             jsonData.getString("JOB_TYPE_NM")+" "+
                             jsonData.getString("WORK_DATE") + " "+
                             common.encode("필요인력 ",IN_APP_LANG)+
                             jsonData.getInt("NEED_CNT")+
                             common.encode("명 중 ",IN_APP_LANG)+
                             jsonData.getInt("BOOK_CNT")+
                             common.encode("명 인력 배치 예약 완료  ",IN_APP_LANG)+
                             common.encode("( 소속 : ",IN_APP_LANG)+
                             jsonData.getInt("MEMBER_CNT")+
                             common.encode(" / 긴급 : ",IN_APP_LANG) + 
                             jsonData.getInt("BOOK_URGENT_CNT")+" )";
                IN_TITLE = jsonData.getString("EMP_NAME")+
                            common.encode("의 알람",IN_APP_LANG);
                IN_MSG_TYPE = "WORK_SHOW_RES_ALARM";
    
            }
            
            String api_key ="";
            FCM fcm = null;
            String send_gbn = jsonData.getString("GBN");
            String IN_SEND_DATE_NUMBER = common.now_date_integer();
            String IN_BASE_TYPE = "BASE";
            String registration_id = "";

            JSONObject message = new JSONObject();
            JSONObject message_body = new JSONObject();
            JSONObject message_data = new JSONObject();

            int MSG_ID = 0;
    
            String web_api_key = common.vendor_web_api_key;
            FCM web_fcm = new FCM(web_api_key);
            String web_registration_id = jsonData.getString("CORP_WEB_REG_ID");
            
            JSONObject web_message = new JSONObject();
            JSONObject web_message_notification = new JSONObject();
            JSONObject web_message_data = new JSONObject();

            try
            {
                String GBN = jsonData.getString("GBN");
                String ID_TYPE = "SHOW_ID";
                int ID_KEY = jsonData.getInt("SHOW_ID");
                String ERR_YN = "N";
                String ERR_MSG = "";
                int EMP_ID = jsonData.getInt("EMP_ID");
                int CORP_ID = jsonData.getInt("CORP_ID");
                String SHOW_REQ_TYPE = jsonData.getString("SHOW_REQ_TYPE");
                String SHOW_REJ_TYPE = jsonData.getString("SHOW_REJ_TYPE");
                String WORK_DATE = jsonData.getString("WORK_DATE");
                String W_REQ_GBN = jsonData.getString("W_REQ_GBN");
                String R_GBN = "E-B";
                IN_CONTENT = jsonData.getString("EMP_NAME")+
                     common.encode("은 출근 자동거부 되었습니다. ",IN_APP_LANG)+
                     jsonData.getString("SCHEDULE_NAME")+" "+
                     "["+
                     jsonData.getString("JOB_GBN_NM")+"] "+
                     jsonData.getString("JOB_TYPE_NM")+" "+
                     jsonData.getString("WORK_DATE") + " "+
                     common.encode("필요인력 ",IN_APP_LANG)+
                     jsonData.getInt("NEED_CNT")+
                     common.encode("명 중 ",IN_APP_LANG)+
                     jsonData.getInt("BOOK_CNT")+
                     common.encode("명 인력 배치 예약 완료  ",IN_APP_LANG)+
                     common.encode("( 소속 : ",IN_APP_LANG)+
                     jsonData.getInt("MEMBER_CNT")+
                     common.encode(" / 긴급 : ",IN_APP_LANG) + 
                     jsonData.getInt("BOOK_URGENT_CNT")+" )";
                //(GBN, ID_KEY_TYPE, ID_KEY, ERR_YN, ERR_MSG, EMP_ID, 
                // CORP_ID, WORK_REQ_TYPE, WORK_REJ_TYPE
                String strSQL = "CALL SP_M_MSG_INFO_INSERT('"+GBN+"','"+ID_TYPE+"','"+ID_KEY+"'," +
                    "'"+EMP_ID+"','"+CORP_ID+"','"+SHOW_REQ_TYPE+"'," +
                    "'"+SHOW_REJ_TYPE+"','"+R_GBN+"','"+common.get_value(IN_CONTENT)+"','"+WORK_DATE+"','"+W_REQ_GBN+"')"; 
                common.log(module_name,strSQL);
    
                JSONArray jsonMsg = common.execute_query_commit(strSQL);
                
                MSG_ID = common.get_json_array_value_int(jsonMsg,"MSG_ID");
                

                try
                {
                    // 직업소개소->구직자
                    api_key = common.vendor_api_key;
                    fcm = new FCM(api_key);
                    registration_id = jsonData.getString("CORP_MOBILE_REG_ID");

                    /*
                    message = { 
                    //this may vary according to the message type (single recipient, multicast, topic, et cetera)
                    to: registration_id, 
                    collapse_key: IN_MSG_TYPE,
                    priority: "HIGH",					
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
                        title: jsonData.EMP_NAME+"의 알람", 
                        message: IN_CONTENT,
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
                            BOOK_ID:jsonData.BOOK_ID,                       GBN:IN_GBN,
                            W_REQ_GBN:jsonData.W_REQ_GBN,                   EMP_NAME:jsonData.EMP_NAME,
                            SCHEDULE_NAME:jsonData.SCHEDULE_NAME,           SITE_NAME:jsonData.SITE_NAME,
                            BIZ_NAME : jsonData.BIZ_NAME,                   JOB_GBN_NM:jsonData.JOB_GBN_NM,
                            JOB_TYPE_NM:jsonData.JOB_TYPE_NM,               WORK_PAY : jsonData.WORK_PAY,
                            WORK_DATE : jsonData.WORK_DATE,                 NEED_CNT : jsonData.NEED_CNT,
                            BOOK_CNT : jsonData.BOOK_CNT,                   MEMBER_CNT : jsonData.MEMBER_CNT,
                            BOOK_URGENT_CNT : jsonData.BOOK_URGENT_CNT,     SHOW_ID : jsonData.SHOW_ID,
                            EMP_ID: jsonData.EMP_ID,                        CORP_ID: jsonData.CORP_ID,
                            RES_YN : jsonData.RES_YN,                       
                            CORP_NAME : jsonData.CORP_NAME,                 SHOW_REQ_TYPE : jsonData.SHOW_REQ_TYPE,
                            SHOW_REJ_TYPE : jsonData.SHOW_REJ_TYPE,         ID_KEY_TYPE : IN_ID_KEY_TYPE,
                            ID_KEY : jsonData.SHOW_ID,                      
                            BASE_TYPE : IN_BASE_TYPE,                       volume: "3.21.15",
                            title:jsonData.EMP_NAME+"의 알람",               body:IN_CONTENT,
                            id: 1,                                          type: common.alarm_show_res_msg_type, 
                            isShow: 1,                                      sound:"default",
                            MSG_ID : jsonData.MSG_ID,                       WORK_ID : jsonData.WORK_ID,
                            NEED_ID : jsonData.NEED_ID,                     DATE_GBN : jsonData.DATE_GBN,
                            TO_WORK_DATE : jsonData.TO_WORK_DATE,           VS_SUBMIT_CNT : jsonData.VS_SUBMIT_CNT,
                            FEE_AMT : jsonData.FEE_AMT
                            }
                        }
                    };
                    */
                    message_body.put("MILISECONDS",Integer.parseInt(common.get_miliseconds())); 
                    message_body.put("MSG_TYPE",IN_MSG_TYPE);
                    message_body.put("BOOK_ID",jsonData.getInt("BOOK_ID")); 
                    message_body.put("GBN",IN_GBN); 
                    message_body.put("W_REQ_GBN",common.get_json_value(jsonData,"W_REQ_GBN")); 
                    message_body.put("EMP_NAME",common.get_json_value(jsonData,"EMP_NAME"));
                    message_body.put("SCHEDULE_NAME",common.get_json_value(jsonData,"SCHEDULE_NAME")); 
                    message_body.put("SITE_NAME",common.get_json_value(jsonData,"SITE_NAME"));
                    message_body.put("BIZ_NAME",common.get_json_value(jsonData,"BIZ_NAME")); 
                    message_body.put("JOB_GBN_NM",common.get_json_value(jsonData,"JOB_GBN_NM"));
                    message_body.put("JOB_TYPE_NM",common.get_json_value(jsonData,"JOB_TYPE_NM")); 
                    message_body.put("WORK_PAY",common.get_json_value(jsonData,"WORK_PAY"));
                    message_body.put("WORK_DATE",common.get_json_value(jsonData,"WORK_DATE")); 
                    message_body.put("NEED_CNT",common.get_json_value_int(jsonData,"NEED_CNT"));
                    message_body.put("BOOK_CNT",common.get_json_value_int(jsonData,"BOOK_CNT")); 
                    message_body.put("MEMBER_CNT",common.get_json_value_int(jsonData,"MEMBER_CNT"));
                    message_body.put("BOOK_URGENT_CNT",common.get_json_value_int(jsonData,"BOOK_URGENT_CNT")); 
                    message_body.put("SHOW_ID",common.get_json_value_int(jsonData,"SHOW_ID"));
                    message_body.put("EMP_ID",common.get_json_value_int(jsonData,"EMP_ID")); 
                    message_body.put("CORP_ID",common.get_json_value_int(jsonData,"CORP_ID"));                    
                    message_body.put("RES_YN",common.get_json_value(jsonData,"RES_YN")); 
                    message_body.put("CORP_NAME",jsonData.getString("CORP_NAME")); 
                    message_body.put("SHOW_REQ_TYPE",common.get_json_value(jsonData,"SHOW_REQ_TYPE"));
                    message_body.put("SHOW_REJ_TYPE",common.get_json_value(jsonData,"SHOW_REJ_TYPE")); 
                    message_body.put("IN_ID_KEY_TYPE",common.get_json_value(jsonData,"IN_ID_KEY_TYPE"));
                    message_body.put("SHOW_ID",common.get_json_value_int(jsonData,"SHOW_ID")); 
                    message_body.put("BASE_TYPE",IN_BASE_TYPE); 
                    message_body.put("volume","3.21.15");
                    message_body.put("title",IN_TITLE); 
                    message_body.put("body",IN_CONTENT);
                    message_body.put("id",1); 
                    message_body.put("type",common.alarm_show_res_msg_type);
                    message_body.put("isShow",1); 
                    message_body.put("sound","default");
                    message_body.put("MSG_ID",common.get_json_value_int(jsonData,"MSG_ID")); 
                    message_body.put("WORK_ID",common.get_json_value_int(jsonData,"WORK_ID"));
                    message_body.put("NEED_ID",common.get_json_value_int(jsonData,"NEED_ID")); 
                    message_body.put("DATE_GBN",common.get_json_value(jsonData,"DATE_GBN"));
                    message_body.put("TO_WORK_DATE",common.get_json_value(jsonData,"TO_WORK_DATE"));
                    message_body.put("VS_SUBMIT_CNT",common.get_json_value_int(jsonData,"VS_SUBMIT_CNT"));
                    message_body.put("FEE_AMT",common.get_json_value_int(jsonData,"FEE_AMT"));
                    /*
                    message = {                         
                    data : {
                        body:{}
                        }
                    }
                    */
                    message_data.put("body",message_body);
                    message.put("data",message_data); 


                    /*
                    web_message = { //this may vary according to the message type (single recipient, multicast, topic, et cetera)
                    to: web_registration_id, 
                    priority: "high",
                    collapse_key: IN_MSG_TYPE,
                    notification:{},
                    data : {}
                    }
                    */
                    web_message.put("to",web_registration_id);
                    web_message.put("priority","high");
                    web_message.put("collapse_key",IN_MSG_TYPE);
                    /*
                    web_message = { 
                    notification: {
                        title: jsonData.EMP_NAME+"의 알람", 
                        body: IN_CONTENT,
                        id: 1,
                        type: common.alarm_work_res_msg_type, 
                        isShow: 1,
                        sound:"default",
                        click_action:IN_MSG_TYPE					
                    },
                    data : {}
                    */
                    web_message_notification.put("title",IN_TITLE);
                    web_message_notification.put("body",IN_CONTENT);
                    web_message_notification.put("id",1);
                    web_message_notification.put("isShow",1);
                    web_message_notification.put("type",common.alarm_work_res_msg_type);
                    web_message_notification.put("sound","default");
                    web_message_notification.put("click_action",IN_MSG_TYPE);
                    /*
                    web_message = {
                        notification: {                            		
                        },
                        data: {
                            MILISECONDS : common.get_miliseconds(),         MSG_TYPE:IN_MSG_TYPE,
                            BOOK_ID:jsonData.BOOK_ID,                       GBN:IN_GBN,
                            W_REQ_GBN : jsonData.W_REQ_GBN,                 EMP_NAME:jsonData.EMP_NAME,
                            SCHEDULE_NAME:jsonData.SCHEDULE_NAME,           SITE_NAME:jsonData.SITE_NAME,
                            BIZ_NAME:jsonData.BIZ_NAME,                     JOB_GBN_NM:jsonData.JOB_GBN_NM,
                            JOB_TYPE_NM:jsonData.JOB_TYPE_NM,               WORK_PAY : jsonData.WORK_PAY,
                            WORK_DATE : jsonData.WORK_DATE,                 NEED_CNT : jsonData.NEED_CNT,
                            MEMBER_CNT : jsonData.MEMBER_CNT,               BOOK_URGENT_CNT : jsonData.BOOK_URGENT_CNT,
                            VV_BOOK_CNT : jsonData.VV_BOOK_CNT,             VS_SUBMIT_CNT : jsonData.VS_SUBMIT_CNT,
                            SHOW_ID : jsonData.SHOW_ID,                     EMP_ID: jsonData.EMP_ID,
                            CORP_ID: jsonData.CORP_ID,                      RES_YN : jsonData.RES_YN,
                            CORP_NAME : jsonData.CORP_NAME,
                            SHOW_REQ_TYPE : jsonData.SHOW_REQ_TYPE,         SHOW_REJ_TYPE : jsonData.SHOW_REJ_TYPE,
                            ID_KEY_TYPE : IN_ID_KEY_TYPE,                   ID_KEY : jsonData.SHOW_ID,
                            ADDR1:jsonData.ADDR1,
                            DISTANCE:jsonData.DISTANCE,                     JOB_CONTENT:jsonData.JOB_CONTENT,
                            BASE_TYPE : IN_BASE_TYPE,                       volume: "3.21.15",
                            title:jsonData.EMP_NAME+"의 알람",               body:IN_CONTENT,
                            id: 1,                                          type: common.alarm_show_res_msg_type, 
                            isShow: 1,                                      sound:"default",
                            MSG_ID : jsonData.MSG_ID,                       WORK_ID : jsonData.WORK_ID,     
                            NEED_ID : jsonData.NEED_ID,                     FEE_AMT : jsonData.FEE_AMT
                        }
                    };
                    */
                    web_message_data.put("MILISECONDS",Integer.parseInt(common.get_miliseconds())); 
                    web_message_data.put("MSG_TYPE",IN_MSG_TYPE);
                    web_message_data.put("BOOK_ID",common.get_json_value_int(jsonData,"BOOK_ID")); 
                    web_message_data.put("GBN",IN_GBN);
                    web_message_data.put("W_REQ_GBN",common.get_json_value(jsonData,"W_REQ_GBN")); 
                    web_message_data.put("EMP_NAME",common.get_json_value(jsonData,"EMP_NAME"));
                    web_message_data.put("SCHEDULE_NAME",common.get_json_value(jsonData,"SCHEDULE_NAME")); 
                    web_message_data.put("SITE_NAME",common.get_json_value(jsonData,"SITE_NAME"));
                    web_message_data.put("BIZ_NAME",common.get_json_value(jsonData,"BIZ_NAME")); 
                    web_message_data.put("JOB_GBN_NM",common.get_json_value(jsonData,"JOB_GBN_NM"));
                    web_message_data.put("JOB_TYPE_NM",common.get_json_value(jsonData,"JOB_TYPE_NM")); 
                    web_message_data.put("WORK_PAY",common.get_json_value(jsonData,"WORK_PAY"));
                    web_message_data.put("WORK_DATE",common.get_json_value(jsonData,"WORK_DATE")); 
                    web_message_data.put("NEED_CNT",common.get_json_value_int(jsonData,"NEED_CNT"));
                    web_message_data.put("MEMBER_CNT",common.get_json_value_int(jsonData,"MEMBER_CNT")); 
                    web_message_data.put("BOOK_URGENT_CNT",common.get_json_value_int(jsonData,"BOOK_URGENT_CNT"));
                    web_message_data.put("VV_BOOK_CNT",common.get_json_value_int(jsonData,"VV_BOOK_CNT")); 
                    web_message_data.put("VS_SUBMIT_CNT",common.get_json_value_int(jsonData,"VS_SUBMIT_CNT"));
                    web_message_data.put("SHOW_ID",common.get_json_value_int(jsonData,"SHOW_ID")); 
                    web_message_data.put("EMP_ID",common.get_json_value_int(jsonData,"EMP_ID"));
                    web_message_data.put("CORP_ID",common.get_json_value_int(jsonData,"CORP_ID")); 
                    web_message_data.put("RES_YN",common.get_json_value(jsonData,"RES_YN"));
                    web_message_data.put("CORP_NAME",common.get_json_value(jsonData,"CORP_NAME"));
                    web_message_data.put("SHOW_REQ_TYPE",common.get_json_value(jsonData,"SHOW_REQ_TYPE")); 
                    web_message_data.put("SHOW_REJ_TYPE",common.get_json_value(jsonData,"SHOW_REJ_TYPE"));
                    web_message_data.put("ID_KEY_TYPE",IN_ID_KEY_TYPE); 
                    web_message_data.put("ID_KEY",common.get_json_value_int(jsonData,"SHOW_ID"));
                    web_message_data.put("ADDR1",common.get_json_value(jsonData,"ADDR1"));
                    web_message_data.put("DISTANCE",common.get_json_value_double(jsonData,"DISTANCE")); 
                    web_message_data.put("JOB_CONTENT",common.get_json_value(jsonData,"JOB_CONTENT"));
                    web_message_data.put("BASE_TYPE",IN_BASE_TYPE); 
                    web_message_data.put("volume","3.21.15");
                    web_message_data.put("title",IN_TITLE); 
                    web_message_data.put("body",IN_CONTENT);
                    web_message_data.put("id",1); 
                    web_message_data.put("type",common.alarm_show_res_msg_type);
                    web_message_data.put("isShow",1);
                    web_message_data.put("sound","default");
                    web_message_data.put("MSG_ID",common.get_json_value_int(jsonData,"MSG_ID")); 
                    web_message_data.put("WORK_ID",common.get_json_value_int(jsonData,"WORK_ID"));
                    web_message_data.put("NEED_ID",common.get_json_value_int(jsonData,"NEED_ID")); 
                    web_message_data.put("FEE_AMT",common.get_json_value_int(jsonData,"FEE_AMT"));
                    /*
                    web_message = {                         
                        notification : {},
                        data:{}
                    }
                    */
                    web_message.put("notification",web_message_notification);
                    web_message.put("data",web_message_data); 
                    
                    if (registration_id.equals("")==false)
                    {
                        // 설정에 따른 알람 발송
                        if (jsonData.getString("RES_YN").equals("Y"))
                        {
                            strSQL = "CALL SP_M_CORP_CONF_ALARM_INFO_1_1_92('"+common.get_json_value_int(jsonData,"CORP_ID")+"')";
                            JSONArray jsonConf = common.execute_query(strSQL);
                            if(common.get_json_array_value(jsonConf,"SHOW_AVL_YN").equals("Y")){
                                JSONObject json_result_message;
                                if(registration_id.equals("")==false){
                                    json_result_message = fcm.send(message);
                                    result = json_result_message.toString();
                                    common.log(module_name,"fcm.send result="+result+",SHOW_ID : " + jsonData.getInt("SHOW_ID"));
                                }
                                
                                if (web_registration_id.equals("")==false){
                                    json_result_message = web_fcm.send(web_message);
                                    result = json_result_message.toString();
                                    common.log(module_name,"web_fcm.send result="+result+",SHOW_ID : " + jsonData.getInt("SHOW_ID"));
                                    //common.log(module_name,"PUSH_SHOW_AVL_YN=Y,SendPushAlaram=send_message="+IN_CONTENT+" gcm_message="+JSON.stringify(message));
                                }                                
                            }
                            else
                            {
                                common.log(module_name,"PUSH_SHOW_AVL_YN=N CORP_ID : " + jsonData.getInt("CORP_ID"));
                            }
                            
                        }
                        else
                        {
                            strSQL = "CALL SP_M_CORP_CONF_ALARM_INFO_1_1_92('"+common.get_json_value_int(jsonData,"CORP_ID")+"')";
                            JSONArray jsonConf = common.execute_query(strSQL);
                            if(common.get_json_array_value(jsonConf,"SHOW_REJ_YN").equals("Y")){
                                JSONObject json_result_message;
                                
                                if(registration_id.equals("")==false){
                                    json_result_message = fcm.send(message);
                                    result = json_result_message.toString();
                                    common.log(module_name,"fcm.send result="+result+",SHOW_ID : " + jsonData.getInt("SHOW_ID"));
                                }
                                
                                if(web_registration_id.equals("")==false){
                                    json_result_message = web_fcm.send(web_message);
                                    result = json_result_message.toString();
                                    common.log(module_name,"web_fcm.send result="+result+",SHOW_ID : " + jsonData.getInt("SHOW_ID"));
                                    //common.log(module_name,"PUSH_SHOW_AVL_YN=Y,SendPushAlaram=send_message="+IN_CONTENT+" gcm_message="+JSON.stringify(message));
                                }                                
                            }
                            else
                            {
                                common.log(module_name,"PUSH_SHOW_AVL_YN=N CORP_ID : " + jsonData.getInt("CORP_ID"));
                            }                           
                        }
                    }

                    
                    
                    //common.log(module_name,"SendPushAlaram=send_message="+IN_CONTENT+" gcm_message="+JSON.stringify(message));
                }
                catch (Exception ex)
                {
                    common.log(module_name,"SendPushAlaram FCM error : " + ex.getMessage());
                }

                //common.log(module_name,"Mobile Registration ID="+jsonData.CORP_MOBILE_REG_ID+",Push Message="+IN_CONTENT);
                /*
                try
                {
                    
                    BasicDBObject query = new BasicDBObject();
                    query.put("R_GBN","E-B"); query.put("API_KEY",api_key);
                    query.put("RECV_MOBILE_REG_ID",jsonData.getString("CORP_MOBILE_REG_ID")); query.put("SHOW_ID",jsonData.getInt("SHOW_ID"));
                    query.put("EMP_ID",jsonData.getInt("EMP_ID")); query.put("CORP_ID",jsonData.getInt("CORP_ID"));
                    query.put("SHOW_REQ_TYPE",jsonData.getString("SHOW_REQ_TYPE")); query.put("SHOW_REJ_TYPE",jsonData.getString("SHOW_REJ_TYPE"));
                    query.put("TITLE",IN_TITLE); query.put("BODY",IN_CONTENT);
                    query.put("MSG_TYPE",IN_MSG_TYPE); query.put("SEND_DATE_NUMBER",IN_SEND_DATE_NUMBER);
                    query.put("MSG_ID",jsonData.getInt("MSG_ID"));
                }
                catch (MongoException ex)
                {
                    common.log(module_name,"mongoose db save error : " + ex.getMessage());
                }	
                */
            }
            catch (Exception ex)
            {
                common.log(module_name,"SendPushAlaram error : " + ex.getMessage());
            }    
            //common.log(module_name,"SendPushAlaram ok : " + JSON.stringify(jsonData));
        }
        catch (JSONException ex1)
        {
            common.log(module_name,"SendPushAlaram Error : " + ex1.getMessage());
        }        
        catch (Exception ex)
        {
            common.log(module_name,"SendPushAlaram Error : " + ex.getMessage());
        }
        
        return result;
    }

}