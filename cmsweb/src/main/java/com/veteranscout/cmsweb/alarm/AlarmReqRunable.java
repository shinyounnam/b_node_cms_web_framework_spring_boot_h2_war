package com.veteranscout.cmsweb.alarm;

import com.veteranscout.cmsweb.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;
import org.json.*;
import java.net.*;
import java.io.*;
import java.sql.*;


public class AlarmReqRunable implements Runnable{
    
    CommonUtil common;

    String module_name = "AlarmReqRunable";
    Object jsmMsg = "";
    private AlarmSend alarmServer = new AlarmSend();    
    private AlarmResendServer alarmResendServer= new AlarmResendServer();    
    private AlarmRejectServer alarmRejectServer = new AlarmRejectServer();

    public AlarmReqRunable(Object msg) {
        common = new CommonUtil();
        jsmMsg = msg;
    }

    @Override
    public void run() {
        String result = "";
        System.out.println(jsmMsg);        
        result = SendAlarmJsonData(jsmMsg);
    }   

    private String SendAlarmJsonData(Object aJsonData)
    {        
        JSONObject jsonData = (JSONObject)aJsonData;
        String result = "";
        String IN_VERSION_NAME = common.get_json_value(jsonData,"VERSION_NAME");
        String IN_APP_LANG = common.get_json_value(jsonData,"APP_LANG");
        if (IN_APP_LANG.equals(""))
        {
            IN_APP_LANG = "KO";
        }
        String IN_TITLE = common.get_json_value(jsonData,"TITLE");
        if (IN_TITLE.equals(""))
        {
            IN_TITLE = common.get_json_value(jsonData,"CORP_NAME") + 
                       common.encode("의 긴급일자리 알림",IN_APP_LANG);
        }
        String IN_CONTENT = common.get_json_value(jsonData,"CONTENT");
        if (IN_CONTENT.equals(""))
        {
            IN_CONTENT = common.get_json_value(jsonData,"CORP_NAME")+
                         common.encode("에서 ",IN_APP_LANG)+
                         common.get_json_value(jsonData,"WORK_DATE")+
                         common.encode("에 긴급일자리 알림을 등록하였습니다.",IN_APP_LANG);
        }
        if (IN_APP_LANG.equals("KO"))
        {
            IN_TITLE = common.get_json_value(jsonData,"CORP_NAME") + 
                       common.encode("의 긴급일자리 알림",IN_APP_LANG);
            IN_CONTENT = common.get_json_value(jsonData,"CORP_NAME")+
                        common.encode("에서 ",IN_APP_LANG)+
                        common.get_json_value(jsonData,"WORK_DATE")+
                        common.encode("에 긴급일자리 알림을 등록하였습니다.",IN_APP_LANG);
        }
        else if (IN_APP_LANG.equals("EN"))
        {
            IN_TITLE = common.get_json_value(jsonData,"CORP_NAME") + 
                       common.encode(" urgently job notification",IN_APP_LANG);
            IN_CONTENT = common.get_json_value(jsonData,"CORP_NAME")+
                         common.encode(" requested a urgently job notification on ",IN_APP_LANG)+
                         common.get_json_value(jsonData,"WORK_DATE")+".";
        }
        else if (IN_APP_LANG.equals("ZH"))
        {
            IN_TITLE = common.get_json_value(jsonData,"CORP_NAME") + 
                       common.encode(" 紧急工作 通知",IN_APP_LANG);
            IN_CONTENT = common.get_json_value(jsonData,"CORP_NAME")+
                        common.encode(" 在 ",IN_APP_LANG)+
                        common.get_json_value(jsonData,"WORK_DATE")+
                        common.encode(" 紧急工作岗位通知.",IN_APP_LANG);
        }
    
        // var IN_CONTENT = jsonData.CORP_NAME+"에서 "+jsonData.WORK_DATE+"에 베테랑 서비스 요청을 등록하였습니다.";
        //common.log(module_name,"SendPushAlaram=send_message="+IN_CONTENT+" jsonData="+ JSON.stringify(jsonData));
        
        String api_key ="";
        FCM fcm = null;
        String send_gbn = common.get_json_value(jsonData,"GBN");
    
        String IN_MSG_TYPE = "";
        String strNowTimeString = common.now_time_string();
        String IN_SEND_DATE_NUMBER = common.now_date_integer();
        String registration_id = common.get_json_value(jsonData,"MOB_REG_ID");        
    
        try
        {
    
            common.log(module_name,"queue start alarm memory : ");
            String GBN = "EMC";
            String ID_TYPE = "I_ID";
            int ID_KEY = common.get_json_value_int(jsonData,"I_ID");
            String ERR_YN = "N";
            String ERR_MSG = "";
            int EMP_ID = common.get_json_value_int(jsonData,"EMP_ID");
            int CORP_ID = common.get_json_value_int(jsonData,"CORP_ID");
            String EMC_REQ_TYPE = common.get_json_value(jsonData,"EMC_REQ_TYPE");
            String EMC_REJ_TYPE = common.get_json_value(jsonData,"EMC_REJ_TYPE");
            String WORK_DATE = common.get_json_value(jsonData,"WORK_DATE");
            String W_REQ_GBN = "VS";
            String R_GBN = "B-E";
            int IN_MSG_ID = common.get_json_value_int(jsonData,"MSG_ID");
            common.log(module_name,"IN_MSG_ID : " + IN_MSG_ID);
            // 직업소개소->구직자
            api_key = common.user_api_key;
            fcm = new FCM(api_key);			
            IN_MSG_TYPE = "EMC_WORK_ITEM_ALARM";
            if(jsonData.has("MSG_ID")){
                jsonData.remove("MSG_ID");
            }
            jsonData.put("MSG_ID",IN_MSG_ID);

            JSONObject message = new JSONObject();
            JSONObject message_body = new JSONObject();
            JSONObject message_data = new JSONObject();

            IN_VERSION_NAME = common.get_json_value(jsonData,"VERSION_NAME");
            
            
            common.log(module_name,"Veteran Service EmpName : " + common.get_json_value(jsonData,"EMP_NAME") + " EmpID : " + common.get_json_value(jsonData,"EMP_ID"));            
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
                    GBN:jsonData.GBN,                               W_REQ_GBN:jsonData.W_REQ_GBN,
                    MSG_ID : IN_MSG_ID,                             CORP_ID: jsonData.CORP_ID,
                    WORK_ID: jsonData.WORK_ID,                      NEED_ID: jsonData.NEED_ID,
                    I_ID:jsonData.I_ID,                             EMP_ID: jsonData.EMP_ID,
                    CORP_NAME : jsonData.CORP_NAME,                 SCHEDULE_NAME : jsonData.SCHEDULE_NAME,
                    WORK_DATE : jsonData.WORK_DATE,                 WORK_REQ_TYPE : jsonData.EMC_REQ_TYPE,
                    WORK_REJ_TYPE : jsonData.EMC_REJ_TYPE,          SEND_TIME:strNowTimeString,
                    DATE_GBN : jsonData.DATE_GBN,                   DATE_GBN_NM : jsonData.DATE_GBN_NM,
                    AS_WORK_DATE : jsonData.AS_WORK_DATE,           TO_WORK_DATE : jsonData.TO_WORK_DATE,
                    ADDR1:jsonData.ADDR1,                           DISTANCE:jsonData.DISTANCE,
                    JOB_CONTENT:jsonData.JOB_CONTENT,               CORP_GBN:jsonData.CORP_GBN,
                    EMC_ALARM_YN:jsonData.EMC_ALARM_YN,
                    title: IN_TITLE,                                body:IN_CONTENT,
                    id: 1,                                          type: common.alarm_work_item_emc_msg_type, 
                    isShow: 1,                                      sound:"default"
                    }
                }
            };
            */
            message_body.put("MILISECONDS",Integer.parseInt(common.get_miliseconds())); 
            message_body.put("MSG_TYPE",IN_MSG_TYPE);
            message_body.put("GBN",GBN); 
            message_body.put("W_REQ_GBN",W_REQ_GBN); 
            message_body.put("MSG_ID",IN_MSG_ID); 
            message_body.put("CORP_ID",common.get_json_value_int(jsonData,"CORP_ID")); 
            message_body.put("WORK_ID",common.get_json_value_int(jsonData,"WORK_ID")); 
            message_body.put("NEED_ID",common.get_json_value_int(jsonData,"NEED_ID")); 
            message_body.put("I_ID",common.get_json_value_int(jsonData,"I_ID")); 
            message_body.put("EMP_ID",common.get_json_value_int(jsonData,"EMP_ID")); 
            message_body.put("CORP_NAME",common.get_json_value(jsonData,"CORP_NAME")); 
            message_body.put("SCHEDULE_NAME",common.get_json_value(jsonData,"SCHEDULE_NAME")); 
            message_body.put("WORK_DATE",common.get_json_value(jsonData,"WORK_DATE")); 
            message_body.put("WORK_REQ_TYPE",common.get_json_value(jsonData,"EMC_REQ_TYPE")); 
            message_body.put("WORK_REJ_TYPE",common.get_json_value(jsonData,"EMC_REJ_TYPE"));
            message_body.put("SEND_TIME",strNowTimeString); 
            message_body.put("DATE_GBN",common.get_json_value(jsonData,"DATE_GBN")); 
            message_body.put("DATE_GBN_NM",common.get_json_value(jsonData,"DATE_GBN_NM")); 
            message_body.put("AS_WORK_DATE",common.get_json_value(jsonData,"AS_WORK_DATE"));
            message_body.put("TO_WORK_DATE",common.get_json_value(jsonData,"TO_WORK_DATE")); 
            message_body.put("ADDR1",common.get_json_value(jsonData,"ADDR1")); 
            message_body.put("DISTANCE",common.get_json_value_double(jsonData,"DISTANCE")); 
            message_body.put("JOB_CONTENT",common.get_json_value(jsonData,"JOB_CONTENT")); 
            message_body.put("CORP_GBN",common.get_json_value(jsonData,"CORP_GBN")); 
            message_body.put("EMC_ALARM_YN",common.get_json_value(jsonData,"EMC_ALARM_YN")); 
            message_body.put("title",IN_TITLE); 
            message_body.put("body",IN_CONTENT);
            message_body.put("id",1); 
            message_body.put("type",common.alarm_work_item_emc_msg_type);
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

            if (registration_id.equals("")==false)
            {
                JSONObject jsonResendData = alarmServer.MakeResendAlarmWorkVSData(jsonData);
                jsonResendData.put("app_message",message);
                alarmResendServer.send_alarm_json_object(jsonResendData);
                JSONObject jsonRejectData = alarmServer.MakeRejectAlarmWorkVSData(jsonData);
                alarmRejectServer.send_alarm_json_object(jsonRejectData);  

                JSONObject jsonResult = fcm.send(message);
                result = jsonResult.toString();                
                common.log(module_name,"fcm.send result="+result+"REG_ID:"+registration_id+",I_ID : " + common.get_json_value_int(jsonData,"I_ID") + ",EMP_ID : " + common.get_json_value_int(jsonData,"EMP_ID"));
                if(common.get_json_value(jsonResult,"result").equals("error")){
                    int aI_ID = common.get_json_value_int(jsonData,"I_ID");
                    int aEmpID = common.get_json_value_int(jsonData,"EMP_ID");
                    String aSendYN = "N";
                    String aSendCont = "fcm error : " + jsonResult.getString("message");
                    String strUpdateSQL = "CALL SP_M_V_REQ_EMP_INFO_RESULT_UPDATE('"+aI_ID+"','"+aEmpID+"','"+aSendYN+"','"+aSendCont+"')";
                    common.system_log("strUpdateSQL : " + strUpdateSQL);
                    common.execute_query_commit(strUpdateSQL);                    
                }
                else{
                    int aI_ID = common.get_json_value_int(jsonData,"I_ID");
                    int aEmpID = common.get_json_value_int(jsonData,"EMP_ID");
                    String aSendYN = "Y";
                    String aSendCont = "send ok";
                    String strUpdateSQL = "CALL SP_M_V_REQ_EMP_INFO_RESULT_UPDATE('"+aI_ID+"','"+aEmpID+"','"+aSendYN+"','"+aSendCont+"')";
                    common.system_log("strUpdateSQL : " + strUpdateSQL);
                    common.execute_query_commit(strUpdateSQL);          
                }
            }
            else
            {
                common.log(module_name,"registration_id is null I_ID : "+common.get_json_value_int(jsonData,"I_ID") +" EMP_ID:"+common.get_json_value_int(jsonData,"EMP_ID")+" ");
                common.system_log("registration_id is null I_ID : "+common.get_json_value_int(jsonData,"I_ID") +" EMP_ID:"+common.get_json_value_int(jsonData,"EMP_ID")+" ");
                
                int aI_ID = common.get_json_value_int(jsonData,"I_ID");
                int aEmpID = common.get_json_value_int(jsonData,"EMP_ID");
                String aSendYN = "N";
                String aSendCont = "registration_id is null";
                String strUpdateSQL = "CALL SP_M_V_REQ_EMP_INFO_RESULT_UPDATE('"+aI_ID+"','"+aEmpID+"','"+aSendYN+"','"+aSendCont+"')";
                common.system_log("strUpdateSQL : " + strUpdateSQL);
                common.execute_query_commit(strUpdateSQL);                                      
            }    
        }
        catch (JSONException ex)
        {
            common.log(module_name,"SendPushAlaram : " + ex.getMessage());
        }
        catch(Exception ex1){
            common.log(module_name,"SendPushAlaram : " + ex1.getMessage());
        }
        return result;
    }
}