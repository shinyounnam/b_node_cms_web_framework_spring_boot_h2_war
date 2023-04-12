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
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;


public class AlarmSend {
    
    CommonUtil common = new CommonUtil();    
    AlarmSendMms alarmSendMms = new AlarmSendMms();
    AlarmResendServer alarmResendServer = new AlarmResendServer();
    String module_name = "AlarmSend";
    
    public AlarmSend(){        
    }

    public JSONObject MakeResendAlarmWorkData(JSONObject jsonSendData)
    {        
        JSONObject jsonData = new JSONObject();    
        try
        {
            jsonData.put("GBN", "REQ");
            jsonData.put("BASE_TYPE", "BASE");
            jsonData.put("ID_KEY_TYPE", "BOOK_ID");
            jsonData.put("WORK_REQ_TYPE",common.get_json_value(jsonSendData,"WORK_REQ_TYPE"));
            jsonData.put("BOOK_ID", common.get_json_value_int(jsonSendData,"BOOK_ID"));
            jsonData.put("EMP_ID",common.get_json_value_int(jsonSendData,"EMP_ID"));
        }   
        catch(JSONException ex) 
        {
            ex.printStackTrace();
        }
        return jsonData;
    }

    public JSONObject MakeRejectAlarmWorkData(JSONObject jsonSendData)
    {
        JSONObject jsonData = new JSONObject();
        try
        {
            jsonData.put("GBN", "REQ");
            jsonData.put("BASE_TYPE","BASE");
            jsonData.put("ID_KEY_TYPE","BOOK_ID");
            jsonData.put("BOOK_ID",common.get_json_value(jsonSendData,"BOOK_ID"));
            jsonData.put("WORK_REJ_TYPE",common.get_json_value(jsonSendData,"WORK_REJ_TYPE"));
        }
        catch(JSONException ex){
            ex.printStackTrace();
        }        
        return jsonData;
    }

    public JSONObject MakeResendAlarmWorkRecommandData(JSONObject jsonSendData)
    {
        JSONObject jsonData = new JSONObject();
        try
        {
            jsonData.put("GBN","REQ");
            jsonData.put("BASE_TYPE","BASE");
            jsonData.put("ID_KEY_TYPE", "RMD_ID");
            jsonData.put("WORK_REQ_TYPE",common.get_json_value(jsonSendData,"WORK_REQ_TYPE"));
            jsonData.put("RMD_ID",common.get_json_value_int(jsonSendData,"RMD_ID"));
            jsonData.put("EMP_ID",common.get_json_value_int(jsonSendData,"EMP_ID"));
        }
        catch(JSONException ex)
        {
            ex.printStackTrace();
        }        
        return jsonData;
    }

    public JSONObject MakeRejectAlarmWorkRecommandData(JSONObject jsonSendData)
    {
        JSONObject jsonData = new JSONObject();
        try
        {
            jsonData.put("GBN","REQ");
            jsonData.put("BASE_TYPE","BASE");
            jsonData.put("ID_KEY_TYPE","RMD_ID");
            jsonData.put("RMD_ID",common.get_json_value_int(jsonSendData,"RMD_ID"));
            jsonData.put("WORK_REJ_TYPE",common.get_json_value_int(jsonSendData,"WORK_REJ_TYPE"));
        }
        catch(JSONException ex){
            ex.printStackTrace();
        }
        return jsonData;
    }

    public JSONObject MakeResendAlarmWorkVSData(JSONObject jsonSendData)
    {
        JSONObject jsonData = new JSONObject();
        try
        {
            jsonData.put("GBN","SLT");
            jsonData.put("BASE_TYPE","EMC");
            jsonData.put("ID_KEY_TYPE", "BOOK_ID");
            jsonData.put("EMC_REQ_TYPE", common.get_json_value(jsonSendData,"EMC_REQ_TYPE"));
            jsonData.put("BOOK_ID",common.get_json_value_int(jsonSendData,"BOOK_ID"));
        }
        catch(JSONException ex){
            ex.printStackTrace();
        }
        return jsonData;
    }

    public JSONObject MakeRejectAlarmWorkVSData(JSONObject jsonSendData)
    {
        JSONObject jsonData = new JSONObject();
        try
        {
            jsonData.put("GBN","SLT");
            jsonData.put("BASE_TYPE", "EMC");
            jsonData.put("ID_KEY_TYPE", "BOOK_ID");
            jsonData.put("BOOK_ID", common.get_json_value_int(jsonSendData,"BOOK_ID"));
            jsonData.put("EMC_REJ_TYPE", common.get_json_value(jsonSendData,"EMC_REJ_TYPE"));
        }
        catch(JSONException ex){
            ex.printStackTrace();
        }
        return jsonData;
    }

    public JSONObject MakeResendAlarmWorkVVData(JSONObject jsonSendData)
    {
        JSONObject jsonData = new JSONObject();
        try
        {
            jsonData.put("GBN","SLT");
            jsonData.put("BASE_TYPE","EMC");
            jsonData.put("ID_KEY_TYPE", "BOOK_ID");
            jsonData.put("EMC_REQ_TYPE", common.get_json_value(jsonSendData,"EMC_REQ_TYPE"));
            jsonData.put("BOOK_ID",common.get_json_value_int(jsonSendData,"BOOK_ID"));
        }
        catch(JSONException ex){
            ex.printStackTrace();
        }
        return jsonData;
    }

    public JSONObject MakeRejectAlarmWorkVVData(JSONObject jsonSendData)
    {
        JSONObject jsonData = new JSONObject();
        try
        {
            jsonData.put("GBN","SLT");
            jsonData.put("BASE_TYPE", "EMC");
            jsonData.put("ID_KEY_TYPE", "BOOK_ID");
            jsonData.put("BOOK_ID", common.get_json_value_int(jsonSendData,"BOOK_ID"));
            jsonData.put("EMC_REJ_TYPE", common.get_json_value(jsonSendData,"EMC_REJ_TYPE"));
        }
        catch(JSONException ex){
            ex.printStackTrace();
        }
        return jsonData;
    }


    public String SetAlarmWork(JSONObject jsonData)
    {
        String result = "";        
        try {
            common.log(module_name,"SetAlarmWork : " + jsonData.toString());
            result = SendAlarmWork(jsonData);    
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return result;
    }

    private String SendAlarmWork(JSONObject jsonData)
    {
        String result = "";
        try
        {
            String IN_VERISON_NAME = common.get_json_value(jsonData,"VERSION_NAME");
            String IN_TITLE = "";
            String IN_CONTENT = "";
            String IN_MESSAGE_TYPE = common.get_json_value(jsonData,"GBN");
            String IN_APP_LANG = common.get_json_value(jsonData,"APP_LANG");
            if (IN_APP_LANG.equals(""))
            {
                IN_APP_LANG = "KR";
            }
            //common.log(module_name,"jsonData.GBN : " + jsonData.GBN);
            String IN_GBN = "";
            String IN_BASE_TYPE = "BASE";
            if (IN_MESSAGE_TYPE.equals(""))
            {
                common.log(module_name,"error IN_MESSAGE_TYPE : " + IN_MESSAGE_TYPE);
                return common.get_error_message("error IN_MESSAGE_TYPE : " + IN_MESSAGE_TYPE);
            }

            if (IN_MESSAGE_TYPE.equals("REQ"))
            {
                IN_GBN = "REQ";
                IN_TITLE = common.get_json_value(jsonData,"TITLE");
                if (IN_TITLE.equals(""))
                {
                    IN_TITLE = common.get_json_value(jsonData,"CORP_NAME") + common.encode("의 근무 요청 알람","KR");
                }
                IN_CONTENT = common.get_json_value(jsonData,"CONTENT");
                if (IN_CONTENT.equals(""))
                {
                    IN_CONTENT = common.get_json_value(jsonData,"CORP_NAME")+common.encode("에서 ",IN_APP_LANG)+
                                 common.get_json_value(jsonData,"WORK_DATE")+common.encode("에 근무요청하였습니다.",IN_APP_LANG);
                }
                if (IN_APP_LANG.equals("KR"))
                {                                        
                    IN_TITLE = common.get_json_value(jsonData,"CORP_NAME") + common.encode("의 근무 요청 알람",IN_APP_LANG);
                    IN_CONTENT = common.get_json_value(jsonData,"CORP_NAME")+common.encode("에서 ",IN_APP_LANG)+
                                 common.get_json_value(jsonData,"WORK_DATE")+common.encode("에 근무요청하였습니다.",IN_APP_LANG);
                }
                else if (IN_APP_LANG.equals("EN"))
                {
                    IN_TITLE = common.get_json_value(jsonData,"CORP_NAME") + common.encode(" affiliated job notification",IN_APP_LANG);
                    IN_CONTENT = common.get_json_value(jsonData,"CORP_NAME")+common.encode(" asked you to work on ",IN_APP_LANG)+
                    common.get_json_value(jsonData,"WORK_DATE")+"";
                }
                else if (IN_APP_LANG.equals("ZH"))
                {
                    IN_TITLE = common.get_json_value(jsonData,"CORP_NAME") + common.encode(" 工作请求通知",IN_APP_LANG);
                    IN_CONTENT = common.get_json_value(jsonData,"CORP_NAME")+common.encode(" 在 ",IN_APP_LANG)+
                                 common.get_json_value(jsonData,"WORK_DATE")+common.encode(" 申請退伍老手服務請求.",IN_APP_LANG);
                }
            }
            else if(IN_MESSAGE_TYPE.equals("CAN"))
            {
                IN_GBN = "CAN";
                if (IN_TITLE.equals(""))
                {
                    IN_TITLE = common.get_json_value(jsonData,"CORP_NAME") + common.encode("의 일자리 삭제 알람",IN_APP_LANG);
                }
                IN_CONTENT = common.get_json_value(jsonData,"CONTENT");
                
                if (IN_CONTENT.equals(""))
                {
                    IN_CONTENT = common.encode("일자리가 삭제되었습니다. ",IN_APP_LANG) + 
                                common.get_json_value(jsonData,"WORK_DATE")+" "+
                                common.get_json_value(jsonData,"SCHEDULE_NAME")+" ["+
                                common.get_json_value(jsonData,"JOB_GBN_NM")+"] "+
                                common.get_json_value(jsonData,"JOB_TYPE_NM")+" "+
                                common.get_json_value(jsonData,"WORK_PAY")+
                                common.encode(" 원. 다시 예약해주시기 바랍니다.",IN_APP_LANG);
                }
                if (IN_APP_LANG.equals("KR"))
                {
                    IN_TITLE = common.get_json_value(jsonData,"CORP_NAME") + common.encode("의 일자리 삭제 알림",IN_APP_LANG);
                    IN_CONTENT = common.encode("일자리가 삭제되었습니다. ",IN_APP_LANG) + common.get_json_value(jsonData,"WORK_DATE")+" "+
                                common.get_json_value(jsonData,"SCHEDULE_NAME")+" ["+
                                common.get_json_value(jsonData,"JOB_GBN_NM")+"] "+
                                common.get_json_value(jsonData,"JOB_TYPE_NM")+" "+
                                common.get_json_value(jsonData,"WORK_PAY")+
                                common.encode(" 원. 다시 예약해주시기 바랍니다.",IN_APP_LANG);
                }
                else if (IN_APP_LANG.equals("EN"))
                {
                    IN_TITLE = common.get_json_value(jsonData,"CORP_NAME") + common.encode(" deleted Job notification",IN_APP_LANG);
                    IN_CONTENT = "Job has been deleted." + common.get_json_value(jsonData,"WORK_DATE")+" "+
                                common.get_json_value(jsonData,"SCHEDULE_NAME")+" ["+
                                common.get_json_value(jsonData,"JOB_GBN_NM")+"] "+
                                common.get_json_value(jsonData,"JOB_TYPE_NM")+" "+
                                common.get_json_value(jsonData,"WORK_PAY")+
                                common.encode(". Please make a reservation again.",IN_APP_LANG);
                }
                else if (IN_APP_LANG.equals("ZH"))
                {
                    IN_TITLE = common.get_json_value(jsonData,"CORP_NAME") + 
                               common.encode(" 工作岗位删除通知",IN_APP_LANG);
                    IN_CONTENT = common.encode("工作岗位删除通知。 ",IN_APP_LANG) + 
                                common.get_json_value(jsonData,"WORK_DATE")+" "+
                                common.get_json_value(jsonData,"SCHEDULE_NAME")+" ["+
                                common.get_json_value(jsonData,"JOB_GBN_NM")+"] "+
                                common.get_json_value(jsonData,"JOB_TYPE_NM")+" "+
                                common.get_json_value(jsonData,"WORK_PAY")+" 。";
                }
            }
            else if(IN_MESSAGE_TYPE.equals("VCN"))
            {
                IN_GBN = "VCN";
                if (IN_TITLE.equals(""))
                {
                    IN_TITLE = common.get_json_value(jsonData,"CORP_NAME") + 
                               common.encode("의 예약취소 알림",IN_APP_LANG);
                }
                IN_CONTENT = common.get_json_value(jsonData,"CONTENT");
                if (IN_CONTENT.equals(""))
                {
                    IN_CONTENT = common.get_json_value(jsonData,"WORK_DATE")+" "+
                                common.get_json_value(jsonData,"SCHEDULE_NAME")+" ["+
                                common.get_json_value(jsonData,"JOB_GBN_NM")+"] "+
                                common.get_json_value(jsonData,"JOB_TYPE_NM")+" "+
                                common.get_json_value(jsonData,"WORK_PAY")+
                                common.encode(" 원 위의 일자리가 취소되었습니다. 다시 예약해주시기 바랍니다.",IN_APP_LANG);
                }
                if (IN_APP_LANG.equals("KR"))
                {
                    IN_TITLE = common.get_json_value(jsonData,"CORP_NAME") + "의 예약취소 알람";
                    IN_CONTENT = common.get_json_value(jsonData,"WORK_DATE")+" "+
                                common.get_json_value(jsonData,"SCHEDULE_NAME")+" ["+
                                common.get_json_value(jsonData,"JOB_GBN_NM")+"] "+
                                common.get_json_value(jsonData,"JOB_TYPE_NM")+" "+
                                common.get_json_value(jsonData,"WORK_PAY")+
                                common.encode(" 원 위의 일자리가 취소되었습니다. 다시 예약해주시기 바랍니다.",IN_APP_LANG);
                }
                else if (IN_APP_LANG.equals("EN"))
                {
                    IN_TITLE = common.get_json_value(jsonData,"CORP_NAME") + 
                               common.encode(" job cancellation notification",IN_APP_LANG);
                    IN_CONTENT = common.encode("The job was canceled. Please reserve a job again. ",IN_APP_LANG)+ 
                                common.get_json_value(jsonData,"WORK_DATE")+" "+
                                common.get_json_value(jsonData,"SCHEDULE_NAME")+" ["+
                                common.get_json_value(jsonData,"JOB_GBN_NM")+"] "+
                                common.get_json_value(jsonData,"JOB_TYPE_NM")+" "+
                                common.get_json_value(jsonData,"WORK_PAY")+".";
                }
                else if (IN_APP_LANG.equals("ZH"))
                {
                    IN_TITLE = common.get_json_value(jsonData,"CORP_NAME") + 
                               common.encode(" 紧急请求工作岗位预约/取消通知",IN_APP_LANG);
                    IN_CONTENT = "工作岗位 已被取消。请重新预约。" + common.get_json_value(jsonData,"WORK_DATE")+" "+
                                common.get_json_value(jsonData,"SCHEDULE_NAME")+" ["+
                                common.get_json_value(jsonData,"JOB_GBN_NM")+"] "+
                                common.get_json_value(jsonData,"JOB_TYPE_NM")+" "+
                                common.get_json_value(jsonData,"WORK_PAY")+" 。";
                }
            }
            
            String api_key ="";
            FCM fcm;
            String send_gbn = common.get_json_value(jsonData,"GBN");
            
            String IN_MSG_TYPE = "";
            JSONObject message = new JSONObject();
            JSONObject message_body = new JSONObject();
            JSONObject message_data = new JSONObject();
            String IN_SEND_DATE_NUMBER = common.now_date_integer();
            String VERSION_NAME = common.get_json_value(jsonData,"VERSION_NAME");
            String MSG_ID = "";
            String GBN = common.get_json_value(jsonData,"GBN");
            String ID_TYPE = "BOOK_ID";
            String ID_KEY = common.get_json_value(jsonData,"BOOK_ID");
            String ERR_YN = "N";
            String ERR_MSG = "";
            String EMP_ID = common.get_json_value(jsonData,"EMP_ID");
            String CORP_ID = common.get_json_value(jsonData,"CORP_ID");
            String WORK_REQ_TYPE = common.get_json_value(jsonData,"WORK_REQ_TYPE");
            String WORK_REJ_TYPE = common.get_json_value(jsonData,"WORK_REJ_TYPE");
            String WORK_DATE = common.get_json_value(jsonData,"WORK_DATE");
            String W_REQ_GBN = common.get_json_value(jsonData,"W_REQ_GBN");
            String R_GBN = "B-E";
            String strSQL = "";
            JSONArray jsonConf = null;

            try
            {
                
                //(GBN, ID_KEY_TYPE, ID_KEY, ERR_YN, ERR_MSG, EMP_ID, 
                // CORP_ID, WORK_REQ_TYPE, WORK_REJ_TYPE

                strSQL = "CALL SP_M_MSG_INFO_INSERT('"+GBN+"','"+ID_TYPE+"','"+ID_KEY+"'," +
                    "'"+EMP_ID+"','"+CORP_ID+"','"+WORK_REQ_TYPE+"'," +
                    "'"+WORK_REJ_TYPE+"','"+R_GBN+"','"+IN_CONTENT+"','"+WORK_DATE+"','"+W_REQ_GBN+"')"; 

                common.log(module_name,"work msg insert : " + strSQL);

                JSONArray jsonArray = common.execute_query_commit(strSQL);

                common.log(module_name,"근무 요청 알람 저장 완료 = OK : BOOK_ID " + ID_KEY);                
                
                MSG_ID = common.get_json_array_value(jsonArray,"MSG_ID");
                if(jsonData.has("MSG_ID")){
                    jsonData.remove("MSG_ID");
                }
                jsonData.put("MSG_ID",MSG_ID);                
                try
                {	
                    if (IN_MESSAGE_TYPE.equals(""))
                    {
                        common.log(module_name,"error IN_MESSAGE_TYPE : " + IN_MESSAGE_TYPE);
                        return common.get_error_message("error IN_MESSAGE_TYPE : " + IN_MESSAGE_TYPE);
                    }

                    if (IN_MESSAGE_TYPE.equals("REQ"))
                    {
                        api_key = common.user_api_key;
                    }
                    else if(IN_MESSAGE_TYPE.equals("CAN"))
                    {
                        api_key = common.user_api_key;
                    }
                    else if(IN_MESSAGE_TYPE.equals("VCN"))
                    {
                        api_key = common.user_api_key;
                    }
                    // 직업소개소->구직자
                    api_key = common.user_api_key;
                    fcm = new FCM(api_key);
                    
                    String registration_id =  common.get_json_value(jsonData,"EMP_MOBILE_REG_ID");
                    
                    
                    String strNowTimeString = common.now_time_string();
                    if (IN_MESSAGE_TYPE.equals("REQ"))
                    {
                        IN_MSG_TYPE = "WORK_ALARM";
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
                            data:{                            
                                body:
                                {
                                    MILISECONDS : common.get_miliseconds(),
                                    title: IN_TITLE, 
                                    body:IN_CONTENT,
                                    id: 1,
                                    type: common.alarm_work_msg_type, 
                                    isShow: 1,
                                    sound:"default",
                                    click_action:"WORK_ALARM",
                                    MSG_TYPE:IN_MSG_TYPE,
                                    CONTENT: IN_CONTENT,
                                    GBN:IN_GBN,
                                    W_REQ_GBN : jsonData.W_REQ_GBN,
                                    BASE_TYPE : IN_BASE_TYPE,
                                    ID_KEY_TYPE : "BOOK_ID",
                                    ID_KEY :jsonData.BOOK_ID, 
                                    BOOK_ID:jsonData.BOOK_ID,
                                    EMP_ID: jsonData.EMP_ID,
                                    CORP_NAME : jsonData.CORP_NAME,
                                    WORK_DATE : jsonData.WORK_DATE,
                                    WORK_REQ_TYPE : jsonData.WORK_REQ_TYPE,
                                    WORK_REJ_TYPE : jsonData.WORK_REJ_TYPE,		// 소속일자리 수락에서 자동 거절 타이머를 위해 WORK_REJ_TYPE으로 통일
                                    SEND_TIME : strNowTimeString,
                                    DATE_GBN : jsonData.DATE_GBN,
                                    DATE_GBN_NM : jsonData.DATE_GBN_NM,
                                    AS_WORK_DATE : jsonData.AS_WORK_DATE,
                                    TO_WORK_DATE : jsonData.TO_WORK_DATE,
                                    ADDR1 : jsonData.ADDR1,
                                    DISTANCE : jsonData.DISTANCE,
                                    JOB_CONTENT : jsonData.JOB_CONTENT,
                                    MSG_ID : jsonData.MSG_ID,
                                    volume: "3.21.15",
                                    WORK_ID : jsonData.WORK_ID,
                                    NEED_ID : jsonData.NEED_ID,
                                    SCHEDULE_NAME : jsonData.SCHEDULE_NAME,
                                    SITE_NAME : jsonData.SITE_NAME,
                                    ATTEND_TYPE : jsonData.ATTEND_TYPE
                                }						
                            }
                        };
                        */
                        message_body.put("MILISECONDS",Integer.parseInt(common.get_miliseconds()));
                        message_body.put("title",IN_TITLE);
                        message_body.put("body",IN_CONTENT);
                        message_body.put("id",1);
                        message_body.put("type",common.alarm_work_msg_type);
                        message_body.put("isShow",1);
                        message_body.put("sound","default");
                        message_body.put("click_action","WORK_ALARM");
                        message_body.put("MSG_TYPE",IN_MSG_TYPE);
                        message_body.put("CONTENT",IN_CONTENT);
                        message_body.put("GBN",IN_GBN);
                        message_body.put("W_REQ_GBN",common.get_json_value(jsonData,"W_REQ_GBN"));
                        message_body.put("BASE_TYPE",IN_BASE_TYPE);
                        message_body.put("ID_KEY_TYPE","BOOK_ID");
                        message_body.put("ID_KEY",common.get_json_value_int(jsonData,"ID_KEY"));
                        message_body.put("BOOK_ID",common.get_json_value_int(jsonData,"BOOK_ID"));
                        message_body.put("EMP_ID",common.get_json_value_int(jsonData,"EMP_ID"));
                        message_body.put("CORP_NAME",common.get_json_value(jsonData,"CORP_NAME"));
                        message_body.put("WORK_DATE",common.get_json_value(jsonData,"WORK_DATE"));
                        message_body.put("WORK_REQ_TYPE",common.get_json_value(jsonData,"WORK_REQ_TYPE"));
                        message_body.put("WORK_REJ_TYPE",common.get_json_value(jsonData,"WORK_REJ_TYPE"));
                        message_body.put("SEND_TIME",strNowTimeString);
                        message_body.put("DATE_GBN",common.get_json_value(jsonData,"DATE_GBN"));
                        message_body.put("DATE_GBN_NM",common.get_json_value(jsonData,"DATE_GBN_NM"));
                        message_body.put("AS_WORK_DATE",common.get_json_value(jsonData,"AS_WORK_DATE"));
                        message_body.put("TO_WORK_DATE",common.get_json_value(jsonData,"TO_WORK_DATE"));
                        message_body.put("ADDR1",common.get_json_value(jsonData,"ADDR1"));
                        message_body.put("DISTANCE",common.get_json_value_double(jsonData,"DISTANCE"));
                        message_body.put("JOB_CONTENT",common.get_json_value(jsonData,"JOB_CONTENT"));
                        message_body.put("MSG_ID",common.get_json_value_int(jsonData,"MSG_ID"));
                        message_body.put("volume","3.21.15");
                        message_body.put("WORK_ID",common.get_json_value_int(jsonData,"WORK_ID"));
                        message_body.put("NEED_ID",common.get_json_value_int(jsonData,"NEED_ID"));
                        message_body.put("SCHEDULE_NAME",common.get_json_value(jsonData,"SCHEDULE_NAME"));
                        message_body.put("SITE_NAME",common.get_json_value(jsonData,"SITE_NAME"));
                        message_body.put("ATTEND_TYPE",common.get_json_value(jsonData,"ATTEND_TYPE"));
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
                    else if(IN_MESSAGE_TYPE.equals("CAN"))
                    {
                        IN_MSG_TYPE = "WORK_ALARM";                        
                        /*
                         message = { //this may vary according to the message type (single recipient, multicast, topic, et cetera)
                            to: registration_id, 
                            priority: "high",
                            collapse_key: IN_MSG_TYPE,
                            data: {  //you can send only notification or only data(or include both)                                
                                body: 
                                {
                                }
                            }
                        */
                        message.put("to",registration_id);
                        message.put("priority","high");
                        message.put("collapse_key",IN_MSG_TYPE);
                        /*
                        message = { //this may vary according to the message type (single recipient, multicast, topic, et cetera)                        
                            data: {  //you can send only notification or only data(or include both)
                                title: IN_TITLE, 
                                message: IN_CONTENT,
                                click_action:IN_MSG_TYPE,
                                body: 
                                {                                    
                                }	
                            }
                        };
                        */
                        message_data.put("title",IN_TITLE);
                        message_data.put("message",IN_CONTENT);
                        message_data.put("click_action",IN_MSG_TYPE);
                        /*
                        message = { //this may vary according to the message type (single recipient, multicast, topic, et cetera)                        
                            data: {  //you can send only notification or only data(or include both)                                
                                body: 
                                {
                                    MILISECONDS : common.get_miliseconds(), title: IN_TITLE, 
                                    body:IN_CONTENT,                        id: 1,                  
                                    type: common.alarm_work_msg_type, 
                                    isShow: 1,                              sound:"default",
                                    MSG_TYPE:IN_MSG_TYPE,                   CONTENT: IN_CONTENT,
                                    GBN:IN_GBN,                             W_REQ_GBN : jsonData.W_REQ_GBN,
                                    BOOK_ID:jsonData.BOOK_ID,               
                                    BASE_TYPE : IN_BASE_TYPE,
                                    ID_KEY_TYPE : "BOOK_ID",                ID_KEY :jsonData.BOOK_ID, 
                                    EMP_ID: jsonData.EMP_ID,                
                                    WORK_DATE : jsonData.WORK_DATE,
                                    SITE_NAME : jsonData.SITE_NAME,         SCHEDULE_NAME:jsonData.SCHEDULE_NAME,
                                    BIZ_NAME : jsonData.BIZ_NAME,           JOB_GBN_NM : jsonData.JOB_GBN_NM,
                                    JOB_TYPE_NM : jsonData.JOB_TYPE_NM,     WORK_PAY : jsonData.WORK_PAY,
                                    CORP_NAME : jsonData.CORP_NAME,         RES_YN : "N",
                                    WORK_REQ_TYPE : jsonData.WORK_REQ_TYPE, WORK_REJ_TYPE:jsonData.WORK_REJ_TYPE,
                                    SEND_TIME : strNowTimeString,           DATE_GBN : jsonData.DATE_GBN,
                                    DATE_GBN_NM : jsonData.DATE_GBN_NM,     AS_WORK_DATE : jsonData.AS_WORK_DATE,
                                    TO_WORK_DATE : jsonData.TO_WORK_DATE,   ADDR1 : jsonData.ADDR1,
                                    DISTANCE : jsonData.DISTANCE,           JOB_CONTENT:jsonData.JOB_CONTENT,
                                    MSG_ID : jsonData.MSG_ID,               volume: "3.21.15",
                                    WORK_ID : jsonData.WORK_ID,             NEED_ID : jsonData.NEED_ID,
                                    ATTEND_TYPE : jsonData.ATTEND_TYPE
                                }	
                            }
                        };
                        */
                        message_body.put("MILISECONDS",Integer.parseInt(common.get_miliseconds()));       
                        message_body.put("title",IN_TITLE);
                        message_body.put("body",IN_CONTENT);                            
                        message_body.put("id",1);
                        message_body.put("type",common.alarm_work_msg_type);        
                        message_body.put("isShow",1);                                   
                        message_body.put("sound","default");                        
                        message_body.put("MSG_TYPE",IN_MSG_TYPE);                       
                        message_body.put("CONTENT",IN_CONTENT);                     
                        message_body.put("GBN",IN_GBN);                                 
                        message_body.put("W_REQ_GBN",common.get_json_value(jsonData,"W_REQ_GBN"));
                        message_body.put("BOOK_ID",common.get_json_value_int(jsonData,"BOOK_ID"));
                        message_body.put("BASE_TYPE",IN_BASE_TYPE);
                        message_body.put("ID_KEY_TYPE","BOOK_ID");                          
                        message_body.put("ID_KEY",common.get_json_value_int(jsonData,"BOOK_ID"));
                        message_body.put("EMP_ID",common.get_json_value_int(jsonData,"EMP_ID"));
                        message_body.put("WORK_DATE",common.get_json_value(jsonData,"WORK_DATE"));
                        message_body.put("SITE_NAME",common.get_json_value(jsonData,"SITE_NAME"));   
                        message_body.put("SCHEDULE_NAME",common.get_json_value(jsonData,"SCHEDULE_NAME"));
                        message_body.put("BIZ_NAME",common.get_json_value(jsonData,"BIZ_NAME"));     
                        message_body.put("JOB_GBN_NM",common.get_json_value(jsonData,"JOB_GBN_NM")); 
                        message_body.put("JOB_TYPE_NM",common.get_json_value(jsonData,"JOB_TYPE_NM"));   
                        message_body.put("WORK_PAY",common.get_json_value(jsonData,"WORK_PAY")); 
                        message_body.put("CORP_NAME",common.get_json_value(jsonData,"CORP_NAME"));       
                        message_body.put("RES_YN","N"); 
                        message_body.put("WORK_REQ_TYPE",common.get_json_value(jsonData,"WORK_REQ_TYPE")); 
                        message_body.put("WORK_REJ_TYPE",common.get_json_value(jsonData,"WORK_REJ_TYPE")); 
                        message_body.put("SEND_TIME",strNowTimeString);         
                        message_body.put("DATE_GBN",common.get_json_value(jsonData,"DATE_GBN")); 
                        message_body.put("DATE_GBN_NM",common.get_json_value(jsonData,"DATE_GBN_NM")); 
                        message_body.put("AS_WORK_DATE",common.get_json_value(jsonData,"AS_WORK_DATE")); 
                        message_body.put("TO_WORK_DATE",common.get_json_value(jsonData,"TO_WORK_DATE")); 
                        message_body.put("ADDR1",common.get_json_value(jsonData,"ADDR1")); 
                        message_body.put("DISTANCE",common.get_json_value_double(jsonData,"DISTANCE")); 
                        message_body.put("JOB_CONTENT",common.get_json_value_double(jsonData,"JOB_CONTENT")); 
                        message_body.put("MSG_ID",common.get_json_value_int(jsonData,"MSG_ID")); 
                        message_body.put("volume","3.21.15"); 
                        message_body.put("WORK_ID",common.get_json_value_int(jsonData,"WORK_ID")); 
                        message_body.put("NEED_ID",common.get_json_value_int(jsonData,"NEED_ID")); 
                        message_body.put("ATTEND_TYPE",common.get_json_value(jsonData,"ATTEND_TYPE")); 
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
                    else if(IN_MESSAGE_TYPE.equals("VCN"))
                    {
                        IN_MSG_TYPE = "WORK_ALARM";
                        /*
                         message = { //this may vary according to the message type (single recipient, multicast, topic, et cetera)
                            to: registration_id, 
                            priority: "high",
                            collapse_key: IN_MSG_TYPE,
                            data: {  //you can send only notification or only data(or include both)                                
                                body: 
                                {
                                }
                            }
                        */
                        message.put("to",registration_id);
                        message.put("priority","high");
                        message.put("collapse_key",IN_MSG_TYPE);
                        /*
                        message = { //this may vary according to the message type (single recipient, multicast, topic, et cetera)                        
                            data: {  //you can send only notification or only data(or include both)
                                title: IN_TITLE, 
                                message: IN_CONTENT,
                                click_action:IN_MSG_TYPE,
                                body: 
                                {                                    
                                }	
                            }
                        };
                        */
                        message_data.put("title",IN_TITLE);
                        message_data.put("message",IN_CONTENT);
                        message_data.put("click_action",IN_MSG_TYPE);
                        /*
                        message = { //this may vary according to the message type (single recipient, multicast, topic, et cetera)                            
                            data: {  //you can send only notification or only data(or include both)                                
                                body: 
                                {
                                    MILISECONDS : common.get_miliseconds(), title:jsonData.CORP_NAME+"의 알람", 
                                    body:IN_CONTENT,                        id: 1,
                                    type: common.alarm_work_msg_type,       isShow: 1,
                                    sound:"default",                        MSG_TYPE:IN_MSG_TYPE,
                                    CONTENT: IN_CONTENT,                    GBN:IN_GBN,
                                    W_REQ_GBN : jsonData.W_REQ_GBN,         BOOK_ID:jsonData.BOOK_ID,
                                    BASE_TYPE : IN_BASE_TYPE,               ID_KEY_TYPE : "BOOK_ID",                                    
                                    ID_KEY :jsonData.BOOK_ID,               EMP_ID: jsonData.EMP_ID,
                                    WORK_DATE : jsonData.WORK_DATE,         SITE_NAME : jsonData.SITE_NAME,
                                    SCHEDULE_NAME:jsonData.SCHEDULE_NAME,   BIZ_NAME : jsonData.BIZ_NAME,
                                    JOB_GBN_NM : jsonData.JOB_GBN_NM,       JOB_TYPE_NM : jsonData.JOB_TYPE_NM,
                                    WORK_PAY : jsonData.WORK_PAY,           CORP_NAME : jsonData.CORP_NAME,
                                    RES_YN : "N",                           WORK_REQ_TYPE : jsonData.WORK_REQ_TYPE,
                                    WORK_REJ_TYPE : jsonData.WORK_REJ_TYPE, SEND_TIME : strNowTimeString,
                                    DATE_GBN : jsonData.DATE_GBN,           DATE_GBN_NM : jsonData.DATE_GBN_NM,
                                    AS_WORK_DATE : jsonData.AS_WORK_DATE,   TO_WORK_DATE : jsonData.TO_WORK_DATE,
                                    ADDR1 : jsonData.ADDR1,                 DISTANCE : jsonData.DISTANCE,
                                    JOB_CONTENT : jsonData.JOB_CONTENT,     MSG_ID : jsonData.MSG_ID,
                                    volume: "3.21.15",                      WORK_ID : jsonData.WORK_ID,
                                    NEED_ID : jsonData.NEED_ID,             ATTEND_TYPE : jsonData.ATTEND_TYPE
                                }	
                            }
                        };                        
                        */
                        message_body.put("MILISECONDS",Integer.parseInt(common.get_miliseconds()));
                        message_body.put("title",IN_TITLE);
                        message_body.put("body",IN_CONTENT);    
                        message_body.put("id",1);
                        message_body.put("type",common.alarm_work_msg_type);    
                        message_body.put("isShow",1);
                        message_body.put("sound","default");       
                        message_body.put("MSG_TYPE",IN_MSG_TYPE);
                        message_body.put("CONTENT",IN_CONTENT);     
                        message_body.put("GBN",IN_GBN);
                        message_body.put("W_REQ_GBN",common.get_json_value(jsonData,"W_REQ_GBN")); 
                        message_body.put("BOOK_ID",common.get_json_value_int(jsonData,"BOOK_ID"));
                        message_body.put("BASE_TYPE",IN_BASE_TYPE); 
                        message_body.put("ID_KEY_TYPE","BOOK_ID");
                        message_body.put("ID_KEY",common.get_json_value_int(jsonData,"BOOK_ID")); 
                        message_body.put("EMP_ID",common.get_json_value_int(jsonData,"EMP_ID"));
                        message_body.put("WORK_DATE",common.get_json_value(jsonData,"WORK_DATE")); 
                        message_body.put("SITE_NAME",common.get_json_value(jsonData,"SITE_NAME"));
                        message_body.put("SCHEDULE_NAME",common.get_json_value(jsonData,"SCHEDULE_NAME")); 
                        message_body.put("BIZ_NAME",common.get_json_value(jsonData,"BIZ_NAME"));
                        message_body.put("JOB_GBN_NM",common.get_json_value(jsonData,"JOB_GBN_NM")); 
                        message_body.put("JOB_TYPE_NM",common.get_json_value(jsonData,"JOB_TYPE_NM"));
                        message_body.put("WORK_PAY",common.get_json_value(jsonData,"WORK_PAY")); 
                        message_body.put("CORP_NAME",common.get_json_value(jsonData,"CORP_NAME"));
                        message_body.put("RES_YN","N"); 
                        message_body.put("WORK_REQ_TYPE",common.get_json_value(jsonData,"WORK_REQ_TYPE"));
                        message_body.put("WORK_REJ_TYPE",common.get_json_value(jsonData,"WORK_REJ_TYPE")); 
                        message_body.put("SEND_TIME",strNowTimeString);
                        message_body.put("DATE_GBN",common.get_json_value(jsonData,"DATE_GBN")); 
                        message_body.put("DATE_GBN_NM",common.get_json_value(jsonData,"DATE_GBN_NM"));
                        message_body.put("AS_WORK_DATE",common.get_json_value(jsonData,"AS_WORK_DATE")); 
                        message_body.put("TO_WORK_DATE",common.get_json_value(jsonData,"TO_WORK_DATE"));
                        message_body.put("ADDR1",common.get_json_value(jsonData,"ADDR1")); 
                        message_body.put("DISTANCE",common.get_json_value_double(jsonData,"DISTANCE")); 
                        message_body.put("JOB_CONTENT",common.get_json_value(jsonData,"JOB_CONTENT")); 
                        message_body.put("MSG_ID",common.get_json_value_int(jsonData,"MSG_ID")); 
                        message_body.put("volume","3.21.15"); 
                        message_body.put("WORK_ID",common.get_json_value_int(jsonData,"WORK_ID")); 
                        message_body.put("NEED_ID",common.get_json_value_int(jsonData,"NEED_ID")); 
                        message_body.put("ATTEND_TYPE",common.get_json_value(jsonData,"ATTEND_TYPE"));                         
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

                     // 설정에 따라 발송 처리
                    try
                    {                        
                        strSQL = "CALL SP_M_EMP_CONF_NOTI_PUSH_SELECT('"+common.get_json_value_int(jsonData,"EMP_ID")+"')";
                        jsonConf = common.execute_query(strSQL);
                        if(common.get_json_array_value(jsonConf,"POOL_WORK_YN").equals("Y")){
                            if (common.get_json_value(jsonData,"ATTEND_TYPE").equals("2") && 
                                IN_MESSAGE_TYPE.equals("REQ"))
                            {
                                result = alarmSendMms.SendReq(jsonData);                                                                    
                            }
                            else if (common.get_json_value(jsonData,"ATTEND_TYPE").equals("2") && 
                                        IN_MESSAGE_TYPE.equals("VCN"))
                            {
                                result = alarmSendMms.SendVcn(jsonData);                                                                        
                            }
                            else if (common.get_json_value(jsonData,"ATTEND_TYPE").equals("2") && 
                                        IN_MESSAGE_TYPE.equals("CAN"))
                            {
                                result = alarmSendMms.SendCan(jsonData);                                    
                            }
                            else
                            {
                                if (common.get_json_value(jsonData,"ATTEND_TYPE").equals("2") && 
                                    IN_MESSAGE_TYPE.equals("REQ"))
                                {
                                    result = alarmSendMms.SendReq(jsonData);   
                                }
                                else if (common.get_json_value(jsonData,"ATTEND_TYPE").equals("2") && 
                                            IN_MESSAGE_TYPE.equals("VCN"))
                                {
                                    result = alarmSendMms.SendVcn(jsonData);                                        
                                }
                                else if (common.get_json_value(jsonData,"ATTEND_TYPE").equals("2") && 
                                        IN_MESSAGE_TYPE.equals("CAN"))
                                {
                                    result = alarmSendMms.SendCan(jsonData);                                                  
                                }
                                else
                                {
                                    if (registration_id.equals("")==false)
                                    {                                            
                                        JSONObject json_result_message = fcm.send(message);
                                        String response_result = json_result_message.toString();
                                        common.log(module_name,"fcm.send result = "+response_result+ ", BOOK_ID : " + common.get_json_value(jsonData,"BOOK_ID"));
                                    }
                                    
                                }
                                
                            }   
                        }
                        else
                        {
                            common.log(module_name,"POOL_WORK_YN : N, EMP_ID : "+jsonData.getInt("EMP_ID")+",BOOK_ID : " + jsonData.getInt("BOOK_ID"));
                        }                                                                              
                    }
                    catch(Exception ex1)
                    {
                        ex1.printStackTrace();
                    }
                                       
                    try
                    {			
                        // 근무요청 알람 재발송을 위한 값 저장
                        if (IN_MESSAGE_TYPE.equals("REQ"))
                        {
                            if (common.get_json_value(jsonData,"ALARM_TYPE").equals("AlarmWork") && 
                                    common.get_json_value(jsonData,"GBN").equals("REQ"))
                            {
                                JSONObject jsonResendData = MakeResendAlarmWorkData(jsonData);                                                                    
                                if(registration_id.equals("")==false){
                                    jsonResendData.put("app_message",message);
                                }                                
                                alarmResendServer.send_alarm_json_object(jsonResendData);
                            }
                        }
                    }
                    catch (Exception ex)
                    {
                        common.log(module_name,"mongoose db save error : " + ex.getMessage());
                    }
                    
                }
                catch (Exception ex)
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
        return result;
    }

    public String SetAlarmWorkRecommand(JSONObject data)
    {
        String result = "";
        JSONObject jsonData = data;
        common.log(module_name,"SetAlarmWorkRecommand : RMD_ID " + common.get_json_value(jsonData,"RMD_ID"));
        result = SendAlarmWorkRecommand(data);
        return result;
    }

        
    private String SendAlarmWorkRecommand(JSONObject data)
    {
        String result = "";
        try
        {
            JSONObject jsonData = data;

            String IN_VERISON_NAME = common.get_json_value(jsonData,"VERSION_NAME");
            String IN_TITLE = common.get_json_value(jsonData,"CORP_NAME") + "의 알람";
            String IN_CONTENT = "";
            String IN_MESSAGE_TYPE = common.get_json_value(jsonData,"GBN");
            //common.log(module_name,"jsonData.GBN : " + jsonData.GBN);
            String IN_GBN = "";
            String IN_BASE_TYPE = "BASE";
            String IN_APP_LANG = "";
            if (IN_MESSAGE_TYPE.equals(""))
            {
                common.log(module_name,"error IN_MESSAGE_TYPE : " + IN_MESSAGE_TYPE);
                result = common.get_error_message("error IN_MESSAGE_TYPE : " + IN_MESSAGE_TYPE);
                return result;
            }

            if (IN_MESSAGE_TYPE.equals("REQ"))
            {
                IN_GBN = common.get_json_value(jsonData,"GBN");
                IN_CONTENT = common.get_json_value(jsonData,"CORP_NAME")+
                             common.encode("에서 ",IN_APP_LANG)+
                             common.get_json_value(jsonData,"WORK_DATE")+
                             common.encode("에 근무요청하였습니다.",IN_APP_LANG);
            }
            else if(IN_MESSAGE_TYPE.equals("CAN"))
            {
                IN_GBN = "CAN";
                IN_CONTENT = common.get_json_value(jsonData,"WORK_DATE")+"  "+
                             common.get_json_value(jsonData,"SCHEDULE_NAME")+" "+
                             "["+
                             common.get_json_value(jsonData,"JOB_GBN_NM")+"]" + 
                             common.get_json_value(jsonData,"JOB_TYPE_NM")+" "+
                             common.get_json_value(jsonData,"WORK_PAY")+
                             common.encode("원  ",IN_APP_LANG) +
                            " "+
                            common.encode("위의 일자리가 취소되었습니다. ",IN_APP_LANG)+
                            "다시 예약해주시기 바랍니다.";
            }
            else if(IN_MESSAGE_TYPE.equals("VCN"))
            {
                IN_GBN = "VCN";
                IN_CONTENT = common.get_json_value(jsonData,"WORK_DATE")+"  "+
                             common.get_json_value(jsonData,"SCHEDULE_NAME")+" "+
                             "["+
                             common.get_json_value(jsonData,"JOB_GBN_NM")+"]" + 
                             common.get_json_value(jsonData,"JOB_TYPE_NM")+" "+
                             common.get_json_value(jsonData,"WORK_PAY")+"원  " +
                            " "+
                            common.encode("위의 일자리가 예약/취소되었습니다. ",IN_APP_LANG)+
                            common.encode("다시 예약해주시기 바랍니다.",IN_APP_LANG);
            }
            
            String api_key ="";
            FCM fcm = null;
            String send_gbn = common.get_json_value(jsonData,"GBN");            
            String IN_MSG_TYPE = "";
            JSONObject message = new JSONObject();
            JSONObject message_body = new JSONObject();
            JSONObject message_data = new JSONObject();
            String IN_SEND_DATE_NUMBER = common.now_date_integer();
            String VERSION_NAME = common.get_json_value(jsonData,"VERSION_NAME");
            String MSG_ID = "";
            String strSQL = "";
            JSONArray jsonConf = null;
            try
            {
                String GBN = common.get_json_value(jsonData,"GBN");
                String ID_TYPE = "RMD_ID";
                String ID_KEY = common.get_json_value(jsonData,"RMD_ID");
                String ERR_YN = "N";
                String ERR_MSG = "";
                String EMP_ID = common.get_json_value(jsonData,"EMP_ID");
                String CORP_ID = common.get_json_value(jsonData,"CORP_ID");
                String WORK_REQ_TYPE = common.get_json_value(jsonData,"WORK_REQ_TYPE");
                String WORK_REJ_TYPE = common.get_json_value(jsonData,"WORK_REJ_TYPE");
                String WORK_DATE = common.get_json_value(jsonData,"WORK_DATE");
                String W_REQ_GBN = common.get_json_value(jsonData,"W_REQ_GBN");
                String R_GBN = "B-E";
                //(GBN, ID_KEY_TYPE, ID_KEY, ERR_YN, ERR_MSG, EMP_ID, 
                // CORP_ID, WORK_REQ_TYPE, WORK_REJ_TYPE
                strSQL = "CALL SP_M_MSG_INFO_INSERT('"+GBN+"','"+ID_TYPE+"','"+ID_KEY+"'," +
                    "'"+EMP_ID+"','"+CORP_ID+"','"+WORK_REQ_TYPE+"'," +
                    "'"+WORK_REJ_TYPE+"','"+R_GBN+"','"+IN_CONTENT+"','"+WORK_DATE+"','"+W_REQ_GBN+"')"; 

                common.log(module_name,"work msg insert : " + strSQL);
                JSONArray jsonArray = common.execute_query_commit(strSQL);
                common.log(module_name,"근무 요청 알람 저장 완료 = OK : BOOK_ID " + ID_KEY);                
                
                MSG_ID = common.get_json_array_value(jsonArray,"MSG_ID");
                if(jsonData.has("MSG_ID")){
                    jsonData.remove("MSG_ID");
                }
                jsonData.put("MSG_ID", MSG_ID);
                try
                {	
                    if (IN_MESSAGE_TYPE.equals(""))
                    {
                        common.log(module_name,"error IN_MESSAGE_TYPE : " + IN_MESSAGE_TYPE);
                        result = common.get_error_message("error IN_MESSAGE_TYPE : " + IN_MESSAGE_TYPE);
                        return result;
                    }

                    if (IN_MESSAGE_TYPE.equals("REQ"))
                    {
                        api_key = common.user_api_key;
                    }
                    else if(IN_MESSAGE_TYPE.equals("CAN"))
                    {
                        api_key = common.user_api_key;
                    }
                    else if(IN_MESSAGE_TYPE.equals("VCN"))
                    {
                        api_key = common.user_api_key;
                    }
                    // 직업소개소->구직자
                    api_key = common.user_api_key;
                    fcm = new FCM(api_key);
                    
                    String registration_id = common.get_json_value(jsonData,"EMP_MOBILE_REG_ID");
                    String strNowTimeString = common.now_time_string();

                    IN_MSG_TYPE = "WORK_RMD_ALARM";

                    if (IN_MESSAGE_TYPE.equals("REQ"))
                    {
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
                                    MILISECONDS : common.get_miliseconds(), title:jsonData.CORP_NAME+"의 알람", 
                                    body:IN_CONTENT,    id: 1,
                                    type: common.alarm_work_msg_type,   isShow: 1,
                                    sound:"default",    click_action:IN_MSG_TYPE,
                                    MSG_TYPE:IN_MSG_TYPE,   CONTENT: IN_CONTENT,
                                    GBN:IN_GBN, W_REQ_GBN : jsonData.W_REQ_GBN,
                                    BASE_TYPE : IN_BASE_TYPE,   ID_KEY_TYPE : "RMD_ID",
                                    ID_KEY :jsonData.RMD_ID,    RMD_ID:jsonData.RMD_ID,
                                    EMP_ID: jsonData.EMP_ID,    CORP_NAME : jsonData.CORP_NAME,
                                    WORK_DATE : jsonData.WORK_DATE, WORK_REQ_TYPE : jsonData.WORK_REQ_TYPE,
                                    WORK_REJ_TYPE : jsonData.WORK_REJ_TYPE, SEND_TIME : strNowTimeString,
                                    DATE_GBN : jsonData.DATE_GBN,   DATE_GBN_NM : jsonData.DATE_GBN_NM,
                                    AS_WORK_DATE : jsonData.AS_WORK_DATE,   TO_WORK_DATE : jsonData.TO_WORK_DATE,
                                    MSG_ID : jsonData.MSG_ID,   WORK_ID : jsonData.WORK_ID,
                                    NEED_ID : jsonData.NEED_ID
                                    //volume: "3.21.15"
                                }						
                            }
                        };
                        */
                        message_body.put("MILISECONDS",Integer.parseInt(common.get_miliseconds()));   
                        message_body.put("title",IN_TITLE);
                        message_body.put("body",IN_CONTENT);    
                        message_body.put("id",1);
                        message_body.put("type",common.alarm_work_msg_type);    
                        message_body.put("isShow",1);
                        message_body.put("sound","default");    
                        message_body.put("click_action",IN_MSG_TYPE);
                        message_body.put("MSG_TYPE",IN_MSG_TYPE);   
                        message_body.put("CONTENT",IN_CONTENT);
                        message_body.put("BASE_TYPE",IN_BASE_TYPE); 
                        message_body.put("ID_KEY_TYPE","RMD_ID");
                        message_body.put("ID_KEY",common.get_json_value_int(jsonData,"RMD_ID")); 
                        message_body.put("RMD_ID",common.get_json_value_int(jsonData,"RMD_ID"));
                        message_body.put("EMP_ID",common.get_json_value_int(jsonData,"EMP_ID")); 
                        message_body.put("CORP_NAME",common.get_json_value(jsonData,"CORP_NAME"));
                        message_body.put("WORK_DATE",common.get_json_value(jsonData,"WORK_DATE")); 
                        message_body.put("WORK_REQ_TYPE",common.get_json_value(jsonData,"WORK_REQ_TYPE"));
                        message_body.put("WORK_REJ_TYPE",common.get_json_value(jsonData,"WORK_REJ_TYPE")); 
                        message_body.put("SEND_TIME",strNowTimeString);
                        message_body.put("DATE_GBN",common.get_json_value(jsonData,"DATE_GBN")); 
                        message_body.put("DATE_GBN_NM",common.get_json_value(jsonData,"DATE_GBN_NM"));
                        message_body.put("AS_WORK_DATE",common.get_json_value(jsonData,"AS_WORK_DATE")); 
                        message_body.put("TO_WORK_DATE",common.get_json_value(jsonData,"TO_WORK_DATE"));
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
                    }
                    else if(IN_MESSAGE_TYPE.equals("CAN"))
                    {
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
                                    MILISECONDS : common.get_miliseconds(), title:jsonData.CORP_NAME+"의 알람", 
                                    body:IN_CONTENT,                        id: 1,
                                    type: common.alarm_work_msg_type,       isShow: 1,
                                    sound:"default",                        MSG_TYPE:IN_MSG_TYPE,
                                    CONTENT: IN_CONTENT,                    GBN:IN_GBN,
                                    W_REQ_GBN : jsonData.W_REQ_GBN,         RMD_ID:jsonData.RMD_ID,
                                    BASE_TYPE : IN_BASE_TYPE,               ID_KEY_TYPE : "RMD_ID",
                                    ID_KEY :jsonData.RMD_ID,                EMP_ID: jsonData.EMP_ID,
                                    WORK_DATE : jsonData.WORK_DATE,         SITE_NAME : jsonData.SITE_NAME,
                                    SCHEDULE_NAME:jsonData.SCHEDULE_NAME,   BIZ_NAME : jsonData.BIZ_NAME,
                                    JOB_GBN_NM : jsonData.JOB_GBN_NM,       JOB_TYPE_NM : jsonData.JOB_TYPE_NM,
                                    WORK_PAY : jsonData.WORK_PAY,           CORP_NAME : jsonData.CORP_NAME,
                                    RES_YN : "N",                           WORK_REQ_TYPE : jsonData.WORK_REQ_TYPE,
                                    WORK_REJ_TYPE : jsonData.WORK_REJ_TYPE, SEND_TIME : strNowTimeString,
                                    DATE_GBN : jsonData.DATE_GBN,           DATE_GBN_NM : jsonData.DATE_GBN_NM,
                                    AS_WORK_DATE : jsonData.AS_WORK_DATE,   TO_WORK_DATE : jsonData.TO_WORK_DATE,
                                    MSG_ID : jsonData.MSG_ID,               WORK_ID : jsonData.WORK_ID,
                                    NEED_ID : jsonData.NEED_ID
                                    //volume: "3.21.15"
                                }	
                            }
                        };
                        */
                        message_body.put("MILISECONDS",Integer.parseInt(common.get_miliseconds())); 
                        message_body.put("title",IN_TITLE);
                        message_body.put("body",IN_CONTENT); 
                        message_body.put("id",1);
                        message_body.put("type",common.alarm_work_msg_type); 
                        message_body.put("isShow",1);
                        message_body.put("sound","default"); 
                        message_body.put("MSG_TYPE",IN_MSG_TYPE);
                        message_body.put("CONTENT",IN_CONTENT); message_body.put("GBN",IN_GBN);
                        message_body.put("W_REQ_GBN",common.get_json_value(jsonData,"W_REQ_GBN")); 
                        message_body.put("RMD_ID",common.get_json_value_int(jsonData,"RMD_ID"));
                        message_body.put("BASE_TYPE",IN_BASE_TYPE); 
                        message_body.put("ID_KEY_TYPE","RMD_ID");
                        message_body.put("ID_KEY",common.get_json_value_int(jsonData,"ID_KEY"));
                        message_body.put("EMP_ID",common.get_json_value_int(jsonData,"EMP_ID"));
                        message_body.put("WORK_DATE",common.get_json_value(jsonData,"WORK_DATE"));
                        message_body.put("SITE_NAME",common.get_json_value(jsonData,"SITE_NAME"));
                        message_body.put("SCHEDULE_NAME",common.get_json_value(jsonData,"SCHEDULE_NAME")); 
                        message_body.put("BIZ_NAME",common.get_json_value(jsonData,"BIZ_NAME"));
                        message_body.put("JOB_GBN_NM",common.get_json_value(jsonData,"JOB_GBN_NM")); 
                        message_body.put("JOB_TYPE_NM",common.get_json_value(jsonData,"JOB_TYPE_NM"));
                        message_body.put("WORK_PAY",common.get_json_value(jsonData,"WORK_PAY")); 
                        message_body.put("CORP_NAME",common.get_json_value(jsonData,"CORP_NAME"));
                        message_body.put("RES_YN","N"); 
                        message_body.put("WORK_REQ_TYPE",common.get_json_value(jsonData,"WORK_REQ_TYPE"));
                        message_body.put("WORK_REJ_TYPE",common.get_json_value(jsonData,"WORK_REJ_TYPE")); 
                        message_body.put("SEND_TIME",strNowTimeString);
                        message_body.put("DATE_GBN",common.get_json_value(jsonData,"DATE_GBN")); 
                        message_body.put("DATE_GBN_NM",common.get_json_value(jsonData,"DATE_GBN_NM"));
                        message_body.put("AS_WORK_DATE",common.get_json_value(jsonData,"AS_WORK_DATE")); 
                        message_body.put("TO_WORK_DATE",common.get_json_value(jsonData,"TO_WORK_DATE"));
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
                        
                    }
                    else if(IN_MESSAGE_TYPE.equals("VCN"))
                    {				
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
                            data: {                                  
                                body: 
                                {
                                    MILISECONDS : common.get_miliseconds(), title:jsonData.CORP_NAME+"의 알람", 
                                    body:IN_CONTENT,                        id: 1,
                                    type: common.alarm_work_msg_type,       isShow: 1,
                                    sound:"default",                        MSG_TYPE:IN_MSG_TYPE,
                                    CONTENT: IN_CONTENT,                    GBN:IN_GBN,
                                    W_REQ_GBN : jsonData.W_REQ_GBN,         RMD_ID:jsonData.RMD_ID,
                                    BASE_TYPE : IN_BASE_TYPE,               ID_KEY_TYPE : "RMD_ID",     
                                    ID_KEY :jsonData.RMD_ID,                EMP_ID: jsonData.EMP_ID,
                                    WORK_DATE : jsonData.WORK_DATE,         SITE_NAME : jsonData.SITE_NAME,
                                    SCHEDULE_NAME:jsonData.SCHEDULE_NAME,   BIZ_NAME : jsonData.BIZ_NAME,
                                    JOB_GBN_NM : jsonData.JOB_GBN_NM,       JOB_TYPE_NM : jsonData.JOB_TYPE_NM,
                                    WORK_PAY : jsonData.WORK_PAY,           CORP_NAME : jsonData.CORP_NAME,
                                    RES_YN : "N",                           WORK_REQ_TYPE : jsonData.WORK_REQ_TYPE,
                                    WORK_REJ_TYPE : jsonData.WORK_REJ_TYPE, SEND_TIME : strNowTimeString,           
                                    DATE_GBN : jsonData.DATE_GBN,           DATE_GBN_NM : jsonData.DATE_GBN_NM,
                                    AS_WORK_DATE : jsonData.AS_WORK_DATE,   TO_WORK_DATE : jsonData.TO_WORK_DATE,
                                    MSG_ID : jsonData.MSG_ID,               WORK_ID : jsonData.WORK_ID,
                                    NEED_ID : jsonData.NEED_ID
                                    //volume: "3.21.15"
                                }	
                            }
                        };
                        */
                        message_body.put("MILISECONDS",Integer.parseInt(common.get_miliseconds())); 
                        message_body.put("title",IN_TITLE);
                        message_body.put("body",IN_CONTENT); 
                        message_body.put("id",1);
                        message_body.put("type",common.alarm_work_msg_type); 
                        message_body.put("isShow",1);
                        message_body.put("sound","default"); 
                        message_body.put("MSG_TYPE",IN_MSG_TYPE);
                        message_body.put("CONTENT",IN_CONTENT); 
                        message_body.put("GBN",IN_GBN);
                        message_body.put("W_REQ_GBN",common.get_json_value(jsonData,"W_REQ_GBN")); 
                        message_body.put("RMD_ID",common.get_json_value_int(jsonData,"RMD_ID"));
                        message_body.put("BASE_TYPE",IN_BASE_TYPE); 
                        message_body.put("ID_KEY_TYPE","RMD_ID");
                        message_body.put("ID_KEY",common.get_json_value_int(jsonData,"RMD_ID")); 
                        message_body.put("EMP_ID",common.get_json_value_int(jsonData,"EMP_ID"));
                        message_body.put("WORK_DATE",common.get_json_value(jsonData,"WORK_DATE")); 
                        message_body.put("SITE_NAME",common.get_json_value(jsonData,"SITE_NAME"));
                        message_body.put("SCHEDULE_NAME",common.get_json_value(jsonData,"SCHEDULE_NAME")); 
                        message_body.put("BIZ_NAME",common.get_json_value(jsonData,"BIZ_NAME"));
                        message_body.put("JOB_GBN_NM",common.get_json_value(jsonData,"JOB_GBN_NM")); 
                        message_body.put("JOB_TYPE_NM",common.get_json_value(jsonData,"JOB_TYPE_NM"));
                        message_body.put("WORK_PAY",common.get_json_value(jsonData,"WORK_PAY")); 
                        message_body.put("CORP_NAME",common.get_json_value(jsonData,"CORP_NAME"));
                        message_body.put("RES_YN","N"); 
                        message_body.put("WORK_REQ_TYPE",common.get_json_value(jsonData,"WORK_REQ_TYPE"));
                        message_body.put("WORK_REJ_TYPE",common.get_json_value(jsonData,"WORK_REJ_TYPE")); 
                        message_body.put("SEND_TIME",strNowTimeString);
                        message_body.put("DATE_GBN",common.get_json_value(jsonData,"DATE_GBN")); 
                        message_body.put("DATE_GBN_NM",common.get_json_value(jsonData,"DATE_GBN_NM"));
                        message_body.put("AS_WORK_DATE",common.get_json_value(jsonData,"AS_WORK_DATE")); 
                        message_body.put("TO_WORK_DATE",common.get_json_value(jsonData,"TO_WORK_DATE"));
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
                        
                    }

                    strSQL = "CALL SP_M_EMP_CONF_NOTI_PUSH_SELECT('"+common.get_json_value_int(jsonData,"EMP_ID")+"')";
                    jsonConf = common.execute_query(strSQL);
                    if(common.get_json_array_value(jsonConf,"POOL_WORK_YN").equals("Y")){
                        JSONObject json_result_message;
                        if(registration_id.equals("")==false){
                            json_result_message = fcm.send(message);
                            result = json_result_message.toString();
                            common.log(module_name,"fcm.send result "+result+",RMD_ID : " + common.get_json_value(jsonData,"RMD_ID"));                                    
                        }                        
                    }                    
                    try
                    {			
                        // 근무요청 알람 재발송을 위한 값 저장
                        if (IN_MESSAGE_TYPE.equals("SS"))
                        {                           
                            if (common.get_json_value(jsonData,"ALARM_TYPE").equals("AlarmWorkRecommand") && 
                                        common.get_json_value(jsonData,"GBN").equals("SS"))
                            {
                                JSONObject jsonResendData = MakeResendAlarmWorkRecommandData(jsonData);
                                if(registration_id.equals("")==false){
                                    jsonResendData.put("app_message",message);
                                    alarmResendServer.send_alarm_json_object(jsonResendData);   
                                }
                                
                            } 
                        }                        
                    }
                    catch (Exception ex)
                    {
                        common.log(module_name,"mongoose db save error : " + ex.getMessage());
                    }
                    
                }
                catch (Exception ex)
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
        return result;
    }


    public String SetAlarmWorkRes(JSONObject data) 
    {
        String result = "";
        JSONObject jsonData = data;
        common.log(module_name,"SetAlarmWorkRes : " + common.get_json_value(jsonData,"BOOK_ID"));
        result = SendAlarmWorkRes(data);
        return result;
    }

    private String SendAlarmWorkRes(JSONObject data)
    {
        String result = "";
        try
        {
            JSONObject jsonData = data;

            String IN_VERISON_NAME = common.get_json_value(jsonData,"VERSION_NAME");
            String IN_CONTENT = "";
            String IN_MSG_TYPE = "";
            String IN_GBN = "";
            String IN_ID_KEY_TYPE = "BOOK_ID";
            String IN_SEND_DATE_NUMBER = common.now_date_integer();

            String api_key ="";
            FCM fcm = null;
            String registration_id = "";
            JSONObject message = new JSONObject();
            JSONObject message_body = new JSONObject();
            JSONObject message_data = new JSONObject();

            String web_api_key = "";
            FCM web_fcm = null;
            String web_registration_id = common.get_json_value(jsonData,"CORP_WEB_REG_ID");
            JSONObject web_message = new JSONObject();
            JSONObject web_message_notification = new JSONObject();
            JSONObject web_message_data = new JSONObject();
            
            String send_gbn = common.get_json_value(jsonData,"GBN");
            String IN_BASE_TYPE = "BASE";
            String IN_TITLE = "";
            String IN_APP_LANG = "KR";
            if (common.get_json_value(jsonData,"RES_YN").equals("Y"))
            {
                IN_GBN = common.get_json_value(jsonData,"GBN");	
                IN_TITLE = common.get_json_value(jsonData,"EMP_NAME")+common.encode("의 알람",IN_APP_LANG);
                IN_CONTENT = common.get_json_value(jsonData,"EMP_NAME")+common.encode("님이 근무요청에 수락하였습니다. ",IN_APP_LANG)+
                             common.get_json_value(jsonData,"SCHEDULE_NAME")+" "+
                            "["+
                            common.get_json_value(jsonData,"JOB_GBN_NM")+"] "+
                            common.get_json_value(jsonData,"JOB_TYPE_NM")+" "+
                            common.get_json_value(jsonData,"WORK_DATE") + " "+
                            common.encode("필요인력 ",IN_APP_LANG)+
                            common.get_json_value(jsonData,"NEED_CNT")+
                            common.encode("명 중 ",IN_APP_LANG)+
                            common.get_json_value(jsonData,"BOOK_CNT")+
                            common.encode("명 인력 배치 예약 완료  ",IN_APP_LANG)+
                            common.encode("( 소속 : ",IN_APP_LANG)+
                            common.get_json_value(jsonData,"MEMBER_CNT")+" " +
                            common.encode("/ 베테랑 인력 : ",IN_APP_LANG) + 
                            common.get_json_value(jsonData,"BOOK_URGENT_CNT")+" "+
                            common.encode("/ 베테랑 인력 추천 ",IN_APP_LANG) + 
                            common.get_json_value(jsonData,"VV_BOOK_CNT") + " )";
                IN_MSG_TYPE = "WORK_RES_ALARM";
            }
            else if(common.get_json_value(jsonData,"RES_YN").equals("N"))
            {
                if (common.get_json_value(jsonData,"GBN").equals("ECN"))
                {
                    IN_GBN = "ECN";
                    IN_TITLE = common.get_json_value(jsonData,"EMP_NAME")+
                               common.encode("의 알람",IN_APP_LANG);
                    IN_CONTENT = common.get_json_value(jsonData,"EMP_NAME")+
                                 common.encode("님이 예약을 취소하였습니다. ",IN_APP_LANG)+
                            common.get_json_value(jsonData,"SCHEDULE_NAME")+" "+
                            "["+
                            common.get_json_value(jsonData,"JOB_GBN_NM")+"] "+
                            common.get_json_value(jsonData,"JOB_TYPE_NM")+" "+
                            common.get_json_value(jsonData,"WORK_DATE") + " "+
                            common.encode("필요인력 ",IN_APP_LANG)+
                            common.get_json_value(jsonData,"NEED_CNT")+
                            common.encode("명 중 ",IN_APP_LANG)+
                            common.get_json_value(jsonData,"BOOK_CNT")+
                            common.encode("명 인력 배치 예약 완료  ",IN_APP_LANG)+
                            common.encode("( 소속 : ",IN_APP_LANG)+
                            common.get_json_value(jsonData,"MEMBER_CNT")+" "+
                            common.encode("/ 베테랑 인력 : ",IN_APP_LANG) + 
                            common.get_json_value(jsonData,"BOOK_URGENT_CNT")+" "+
                            common.encode("/ 베테랑 인력 추천 ",IN_APP_LANG) + 
                            common.get_json_value(jsonData,"VV_BOOK_CNT") + " )";
                    IN_MSG_TYPE = "WORK_RES_ALARM";
                }
                else if (common.get_json_value(jsonData,"GBN").equals("VCN"))
                {
                    IN_GBN = "VCN";
                    IN_TITLE = common.get_json_value(jsonData,"CORP_NAME")+ 
                               common.encode("의 알람",IN_APP_LANG);
                    IN_CONTENT = common.get_json_value(jsonData,"CORP_NAME")+
                                 common.encode("직업소개소에서 예약을 취소하였습니다. ",IN_APP_LANG)+
                            common.get_json_value(jsonData,"SCHEDULE_NAME")+" "+
                            "["+
                            common.get_json_value(jsonData,"JOB_GBN_NM")+"] "+
                            common.get_json_value(jsonData,"JOB_TYPE_NM")+" "+
                            common.get_json_value(jsonData,"WORK_DATE") + " "+
                            common.encode("필요인력 ",IN_APP_LANG)+
                            common.get_json_value(jsonData,"NEED_CNT")+
                            common.encode("명 중 ",IN_APP_LANG)+
                            common.get_json_value(jsonData,"BOOK_CNT")+
                            common.encode("명 인력 배치 예약 완료  ",IN_APP_LANG)+
                            common.encode("( 소속 : ",IN_APP_LANG)+
                            common.get_json_value(jsonData,"MEMBER_CNT")+" "+
                            common.encode("/ 베테랑 인력 : ",IN_APP_LANG) + 
                            common.get_json_value(jsonData,"BOOK_URGENT_CNT")+" "+
                            common.encode("/ 베테랑 인력 추천 ",IN_APP_LANG) + 
                            common.get_json_value(jsonData,"VV_BOOK_CNT") + " )";
                    IN_MSG_TYPE = "WORK_RES_ALARM";
                }
                else
                {
                    IN_GBN = common.get_json_value(jsonData,"GBN");	
                    IN_TITLE = common.get_json_value(jsonData,"EMP_NAME")+  
                               common.encode("의 알람",IN_APP_LANG);
                    IN_CONTENT = common.get_json_value(jsonData,"EMP_NAME")+
                                 common.encode("님이 근무요청을 거절하였습니다. ",IN_APP_LANG)+
                            common.get_json_value(jsonData,"SCHEDULE_NAME")+" "+
                            "["+common.get_json_value(jsonData,"JOB_GBN_NM")+"] "+
                            common.get_json_value(jsonData,"JOB_TYPE_NM")+" "+
                            common.get_json_value(jsonData,"WORK_DATE") + " "+
                            common.encode("필요인력 ",IN_APP_LANG)+
                            common.get_json_value(jsonData,"NEED_CNT")+
                            common.encode("명 중 ",IN_APP_LANG)+
                            common.get_json_value(jsonData,"BOOK_CNT")+
                            common.encode("명 인력 배치 예약 완료  ",IN_APP_LANG)+
                            common.encode("( 소속 : ",IN_APP_LANG)+
                            common.get_json_value(jsonData,"MEMBER_CNT")+" "+
                            common.encode("/ 베테랑 인력 : ",IN_APP_LANG) + 
                            common.get_json_value(jsonData,"BOOK_URGENT_CNT")+" "+
                            common.encode("/ 베테랑 인력 추천 ",IN_APP_LANG) + 
                            common.get_json_value(jsonData,"VV_BOOK_CNT") + " )";
                    IN_MSG_TYPE = "WORK_RES_ALARM";
                }
            }

            
            String MSG_ID = "";
            
            try
            {
                String GBN = common.get_json_value(jsonData,"GBN");
                String ID_TYPE = "BOOK_ID";
                String ID_KEY = common.get_json_value(jsonData,"BOOK_ID");
                String ERR_YN = "N";
                String ERR_MSG = "";
                String EMP_ID = common.get_json_value(jsonData,"EMP_ID");
                String CORP_ID = common.get_json_value(jsonData,"CORP_ID");
                String WORK_REQ_TYPE = common.get_json_value(jsonData,"WORK_REQ_TYPE");
                String WORK_REJ_TYPE = common.get_json_value(jsonData,"WORK_REJ_TYPE");
                String WORK_DATE = common.get_json_value(jsonData,"WORK_DATE");
                String W_REQ_GBN = common.get_json_value(jsonData,"W_REQ_GBN");
                String R_GBN = "E-B";
                //(GBN, ID_KEY_TYPE, ID_KEY, ERR_YN, ERR_MSG, EMP_ID, 
                // CORP_ID, WORK_REQ_TYPE, WORK_REJ_TYPE
                String strSQL = "CALL SP_M_MSG_INFO_INSERT('"+GBN+"','"+ID_TYPE+"','"+ID_KEY+"'," +
                    "'"+EMP_ID+"','"+CORP_ID+"','"+WORK_REQ_TYPE+"'," +
                    "'"+WORK_REJ_TYPE+"','"+R_GBN+"','"+common.get_value(IN_CONTENT)+"','"+WORK_DATE+"','"+W_REQ_GBN+"')"; 
                common.log(module_name,strSQL);

                JSONArray jsonArray = common.execute_query_commit(strSQL);
                
                
                common.log(module_name,"INSERT OK BOOK_ID : " + common.get_json_value(jsonData,"BOOK_ID"));
                MSG_ID = common.get_json_array_value(jsonArray,"MSG_ID");
                if(jsonData.has("MSG_ID")){
                    jsonData.remove("MSG_ID");
                }
                jsonData.put("MSG_ID",MSG_ID);                

                try
                {
                    // 직업소개소->구직자
                    if (IN_GBN.equals("VCN"))
                    {
                        api_key = common.user_api_key;			
                        registration_id = common.get_json_value(jsonData,"EMP_MOBILE_REG_ID");
                    }
                    else
                    {
                        api_key = common.vendor_api_key;			
                        registration_id = common.get_json_value(jsonData,"CORP_MOBILE_REG_ID");
                    }
                    
                    fcm = new FCM(api_key);
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
                            MILISECONDS : common.get_miliseconds(), title:jsonData.EMP_NAME+"의 알람", 
                            body:IN_CONTENT,    id: 1,
                            type: common.alarm_work_res_msg_type,   isShow: 1,
                            sound:"default",    click_action:IN_MSG_TYPE,
                            MSG_TYPE:IN_MSG_TYPE,   GBN:IN_GBN,
                            W_REQ_GBN : jsonData.W_REQ_GBN, EMP_NAME : jsonData.EMP_NAME,
                            SCHEDULE_NAME : jsonData.SCHEDULE_NAME, SITE_NAME : jsonData.SITE_NAME,
                            BIZ_NAME : jsonData.BIZ_NAME,   JOB_GBN_NM : jsonData.JOB_GBN_NM,
                            JOB_TYPE_NM : jsonData.JOB_TYPE_NM, FEE_AMT : jsonData.FEE_AMT,
                            WORK_PAY : jsonData.WORK_PAY,   WORK_DATE : jsonData.WORK_DATE,
                            NEED_CNT : jsonData.NEED_CNT,   BOOK_CNT : jsonData.BOOK_CNT,
                            MEMBER_CNT : jsonData.MEMBER_CNT,   BOOK_URGENT_CNT : jsonData.BOOK_URGENT_CNT,
                            VV_BOOK_CNT : jsonData.VV_BOOK_CNT, BOOK_ID:jsonData.BOOK_ID,
                            EMP_ID: jsonData.EMP_ID,    CORP_ID: jsonData.CORP_ID,												
                            RES_YN : jsonData.RES_YN,	BASE_TYPE : IN_BASE_TYPE,
                            CORP_NAME : jsonData.CORP_NAME, WORK_REQ_TYPE : jsonData.WORK_REQ_TYPE,
                            WORK_REJ_TYPE : jsonData.WORK_REJ_TYPE, ID_KEY_TYPE : IN_ID_KEY_TYPE,
                            ID_KEY : jsonData.BOOK_ID,  DATE_GBN : jsonData.DATE_GBN,
                            DATE_GBN_NM : jsonData.DATE_GBN_NM, AS_WORK_DATE : jsonData.AS_WORK_DATE,
                            TO_WORK_DATE : jsonData.TO_WORK_DATE,   ADDR1 : jsonData.ADDR1,
                            DISTANCE : jsonData.DISTANCE,   JOB_CONTENT : jsonData.JOB_CONTENT,
                            MSG_ID : jsonData.MSG_ID,   WORK_ID : jsonData.WORK_ID,
                            NEED_ID : jsonData.NEED_ID,     DAY_OF_WEEK : jsonData.DAY_OF_WEEK,
                            volume: "3.21.15"
                            }
                        }
                    };
                    */
                    message_body.put("MILISECONDS",Integer.parseInt(common.get_miliseconds())); 
                    message_body.put("title",IN_TITLE);
                    message_body.put("body",IN_CONTENT); 
                    message_body.put("id",1);
                    message_body.put("type",common.alarm_work_res_msg_type); 
                    message_body.put("isShow",1);
                    message_body.put("sound","default"); 
                    message_body.put("click_action",IN_MSG_TYPE);
                    message_body.put("MSG_TYPE",IN_MSG_TYPE); 
                    message_body.put("GBN",IN_GBN);
                    message_body.put("W_REQ_GBN",common.get_json_value(jsonData,"W_REQ_GBN")); 
                    message_body.put("EMP_NAME",common.get_json_value(jsonData,"EMP_NAME"));
                    message_body.put("SCHEDULE_NAME",common.get_json_value(jsonData,"SCHEDULE_NAME")); 
                    message_body.put("SITE_NAME",common.get_json_value(jsonData,"SITE_NAME"));
                    message_body.put("BIZ_NAME",common.get_json_value(jsonData,"BIZ_NAME")); 
                    message_body.put("JOB_GBN_NM",common.get_json_value(jsonData,"JOB_GBN_NM"));
                    message_body.put("JOB_TYPE_NM",common.get_json_value(jsonData,"JOB_TYPE_NM")); 
                    message_body.put("FEE_AMT",common.get_json_value_int(jsonData,"FEE_AMT"));
                    message_body.put("WORK_PAY",common.get_json_value(jsonData,"WORK_PAY")); 
                    message_body.put("WORK_DATE",common.get_json_value(jsonData,"WORK_DATE"));
                    message_body.put("NEED_CNT",common.get_json_value_int(jsonData,"NEED_CNT")); 
                    message_body.put("BOOK_CNT",common.get_json_value_int(jsonData,"BOOK_CNT"));
                    message_body.put("MEMBER_CNT",common.get_json_value_int(jsonData,"MEMBER_CNT")); 
                    message_body.put("BOOK_URGENT_CNT",common.get_json_value_int(jsonData,"BOOK_URGENT_CNT"));
                    message_body.put("VV_BOOK_CNT",common.get_json_value_int(jsonData,"VV_BOOK_CNT")); 
                    message_body.put("BOOK_ID",common.get_json_value_int(jsonData,"BOOK_ID")); 
                    message_body.put("EMP_ID",common.get_json_value_int(jsonData,"EMP_ID")); 
                    message_body.put("CORP_ID",common.get_json_value_int(jsonData,"CORP_ID")); 
                    message_body.put("RES_YN",common.get_json_value(jsonData,"RES_YN")); 
                    message_body.put("BASE_TYPE",IN_BASE_TYPE);
                    message_body.put("CORP_NAME",common.get_json_value(jsonData,"CORP_NAME")); 
                    message_body.put("WORK_REQ_TYPE",common.get_json_value(jsonData,"WORK_REQ_TYPE")); 
                    message_body.put("WORK_REJ_TYPE",common.get_json_value(jsonData,"WORK_REJ_TYPE")); 
                    message_body.put("ID_KEY_TYPE",IN_ID_KEY_TYPE); 
                    message_body.put("ID_KEY",common.get_json_value_int(jsonData,"BOOK_ID")); 
                    message_body.put("DATE_GBN",common.get_json_value(jsonData,"DATE_GBN"));
                    message_body.put("DATE_GBN_NM",common.get_json_value(jsonData,"DATE_GBN_NM")); 
                    message_body.put("AS_WORK_DATE",common.get_json_value(jsonData,"AS_WORK_DATE"));
                    message_body.put("TO_WORK_DATE",common.get_json_value(jsonData,"TO_WORK_DATE")); 
                    message_body.put("ADDR1",common.get_json_value(jsonData,"ADDR1"));
                    message_body.put("DISTANCE",common.get_json_value_double(jsonData,"DISTANCE")); 
                    message_body.put("JOB_CONTENT",common.get_json_value(jsonData,"JOB_CONTENT")); 
                    message_body.put("MSG_ID",common.get_json_value_int(jsonData,"MSG_ID")); 
                    message_body.put("WORK_ID",common.get_json_value_int(jsonData,"WORK_ID")); 
                    message_body.put("NEED_ID",common.get_json_value_int(jsonData,"NEED_ID"));  
                    message_body.put("DAY_OF_WEEK",common.get_json_value(jsonData,"DAY_OF_WEEK")); 
                    message_body.put("volume","3.21.15");
                    /*
                    message = {                         
                    data : {
                            body:{}
                            }
                    }
                    */
                    message_data.put("body",message_body);
                    message.put("data",message_data);  
                    
                    if (IN_GBN.equals("VCN")==false)
                    {
                        web_api_key = common.vendor_web_api_key;			
                        web_fcm = new FCM(web_api_key);

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
                        web_message = { //this may vary according to the message type (single recipient, multicast, topic, et cetera)                            
                            notification: {                            		
                            },
                            data: {  //you can send only notification or only data(or include both)
                                MILISECONDS : common.get_miliseconds(), title:jsonData.EMP_NAME+"의 알람", 
                                body:IN_CONTENT,    id: 1,
                                type: common.alarm_work_res_msg_type,   isShow: 1,
                                sound:"default",    click_action:IN_MSG_TYPE,
                                MSG_TYPE:IN_MSG_TYPE,   GBN:IN_GBN,
                                W_REQ_GBN : jsonData.W_REQ_GBN, EMP_NAME : jsonData.EMP_NAME,
                                SCHEDULE_NAME : jsonData.SCHEDULE_NAME, SITE_NAME : jsonData.SITE_NAME,
                                BIZ_NAME : jsonData.BIZ_NAME,   JOB_GBN_NM : jsonData.JOB_GBN_NM,
                                JOB_TYPE_NM : jsonData.JOB_TYPE_NM, FEE_AMT : jsonData.FEE_AMT,
                                WORK_PAY : jsonData.WORK_PAY,   WORK_DATE : jsonData.WORK_DATE,
                                NEED_CNT : jsonData.NEED_CNT,   BOOK_CNT : jsonData.BOOK_CNT,
                                MEMBER_CNT : jsonData.MEMBER_CNT,   BOOK_URGENT_CNT : jsonData.BOOK_URGENT_CNT,
                                VV_BOOK_CNT : jsonData.VV_BOOK_CNT, BOOK_ID:jsonData.BOOK_ID,
                                EMP_ID: jsonData.EMP_ID,    CORP_ID: jsonData.CORP_ID,
                                RES_YN : jsonData.RES_YN,	BASE_TYPE : IN_BASE_TYPE,
                                CORP_NAME : jsonData.CORP_NAME, WORK_REQ_TYPE : jsonData.WORK_REQ_TYPE,
                                WORK_REJ_TYPE : jsonData.WORK_REJ_TYPE, ID_KEY_TYPE : IN_ID_KEY_TYPE,
                                ID_KEY : jsonData.BOOK_ID,  DATE_GBN : jsonData.DATE_GBN,
                                DATE_GBN_NM : jsonData.DATE_GBN_NM, AS_WORK_DATE : jsonData.AS_WORK_DATE,
                                TO_WORK_DATE : jsonData.TO_WORK_DATE, ADDR1 : jsonData.ADDR1,
                                DISTANCE : jsonData.DISTANCE,   JOB_CONTENT : jsonData.JOB_CONTENT,
                                MSG_ID : jsonData.MSG_ID,   WORK_ID : jsonData.WORK_ID,
                                NEED_ID : jsonData.NEED_ID, DAY_OF_WEEK : jsonData.DAY_OF_WEEK,
                                volume: "3.21.15"
                            }
                        };
                         */
                        web_message_data.put("MILISECONDS",Integer.parseInt(common.get_miliseconds())); 
                        web_message_data.put("title",IN_TITLE);
                        web_message_data.put("body",IN_CONTENT); 
                        web_message_data.put("id",1);
                        web_message_data.put("type",common.alarm_work_res_msg_type); 
                        web_message_data.put("isShow",1);
                        web_message_data.put("sound","default"); 
                        web_message_data.put("click_action",IN_MSG_TYPE);
                        web_message_data.put("MSG_TYPE",IN_MSG_TYPE); 
                        web_message_data.put("GBN",IN_GBN);
                        web_message_data.put("W_REQ_GBN",common.get_json_value(jsonData,"W_REQ_GBN")); 
                        web_message_data.put("EMP_NAME",common.get_json_value(jsonData,"EMP_NAME"));
                        web_message_data.put("SCHEDULE_NAME",common.get_json_value(jsonData,"SCHEDULE_NAME")); 
                        web_message_data.put("SITE_NAME",common.get_json_value(jsonData,"SITE_NAME"));
                        web_message_data.put("BIZ_NAME",common.get_json_value(jsonData,"BIZ_NAME")); 
                        web_message_data.put("JOB_GBN_NM",common.get_json_value(jsonData,"JOB_GBN_NM"));
                        web_message_data.put("JOB_TYPE_NM",common.get_json_value(jsonData,"JOB_TYPE_NM")); 
                        web_message_data.put("FEE_AMT",common.get_json_value_int(jsonData,"FEE_AMT"));
                        web_message_data.put("WORK_PAY",common.get_json_value(jsonData,"WORK_PAY")); 
                        web_message_data.put("WORK_DATE",common.get_json_value(jsonData,"WORK_DATE"));
                        web_message_data.put("NEED_CNT",common.get_json_value_int(jsonData,"NEED_CNT")); 
                        web_message_data.put("BOOK_CNT",common.get_json_value_int(jsonData,"BOOK_CNT"));
                        web_message_data.put("MEMBER_CNT",common.get_json_value_int(jsonData,"MEMBER_CNT"));
                         web_message_data.put("BOOK_URGENT_CNT",common.get_json_value_int(jsonData,"BOOK_URGENT_CNT"));
                        web_message_data.put("VV_BOOK_CNT",common.get_json_value_int(jsonData,"VV_BOOK_CNT")); 
                        web_message_data.put("BOOK_ID",common.get_json_value_int(jsonData,"BOOK_ID")); 
                        web_message_data.put("EMP_ID",common.get_json_value_int(jsonData,"EMP_ID")); 
                        web_message_data.put("CORP_ID",common.get_json_value_int(jsonData,"CORP_ID")); 
                        web_message_data.put("RES_YN",common.get_json_value(jsonData,"RES_YN")); 
                        web_message_data.put("BASE_TYPE",IN_BASE_TYPE);
                        web_message_data.put("CORP_NAME",common.get_json_value(jsonData,"CORP_NAME")); 
                        web_message_data.put("WORK_REQ_TYPE",common.get_json_value(jsonData,"WORK_REQ_TYPE")); 
                        web_message_data.put("WORK_REJ_TYPE",common.get_json_value(jsonData,"WORK_REJ_TYPE")); 
                        web_message_data.put("ID_KEY_TYPE",IN_ID_KEY_TYPE); 
                        web_message_data.put("ID_KEY",common.get_json_value_int(jsonData,"BOOK_ID")); 
                        web_message_data.put("DATE_GBN",common.get_json_value(jsonData,"DATE_GBN"));
                        web_message_data.put("DATE_GBN_NM",common.get_json_value(jsonData,"DATE_GBN_NM")); 
                        web_message_data.put("AS_WORK_DATE",common.get_json_value(jsonData,"AS_WORK_DATE"));
                        web_message_data.put("TO_WORK_DATE",common.get_json_value(jsonData,"TO_WORK_DATE")); 
                        web_message_data.put("ADDR1",common.get_json_value(jsonData,"ADDR1"));
                        web_message_data.put("DISTANCE",common.get_json_value_double(jsonData,"DISTANCE")); 
                        web_message_data.put("JOB_CONTENT",common.get_json_value(jsonData,"JOB_CONTENT")); 
                        web_message_data.put("MSG_ID",common.get_json_value_int(jsonData,"MSG_ID")); 
                        web_message_data.put("WORK_ID",common.get_json_value_int(jsonData,"WORK_ID")); 
                        web_message_data.put("NEED_ID",common.get_json_value_int(jsonData,"NEED_ID"));  
                        web_message_data.put("DAY_OF_WEEK",common.get_json_value(jsonData,"DAY_OF_WEEK")); 
                        web_message_data.put("volume","3.21.15");

                        /*
                        web_message = {                         
                            notification : {},
                            data:{}
                        }
                        */
                        web_message.put("notification",web_message_notification);
                        web_message.put("data",web_message_data);                     
                    }
                    
                    
                    // 근무요청 수락
                    if (common.get_json_value(jsonData,"RES_YN").equals("Y"))
                    {

                        strSQL = "CALL SP_M_WORK_ALARM_SELECT_1_1_92('"+common.get_json_value(jsonData,"CORP_ID")+"','"+
                                 common.get_json_value(jsonData,"WORK_ID")+"','WORK_RES_ALARM','"+
                                 common.get_json_value(jsonData,"RES_YN")+"')";
                        JSONArray jsonAlarm = common.execute_query(strSQL);
                        if (common.get_json_array_value(jsonAlarm,"result").equals("ok"))
                        {
                            if (registration_id.equals("")==false)
                            {
                                JSONObject json_result_message = fcm.send(message);
                                result = json_result_message.toString();
                                common.log(module_name,"fcm.send result="+result+",BOOK_ID : "+common.get_json_value(jsonData,"BOOK_ID"));                                
                            }
                            
                            if (web_registration_id.equals("")==false)
                            {
                                JSONObject json_result_message = web_fcm.send(web_message);
                                result = json_result_message.toString();
                                common.log(module_name,"web_fcm.send result="+result+",BOOK_ID : "+common.get_json_value(jsonData,"BOOK_ID"));                                                                
                            }                            
                        }

                        
                    }
                    else
                    {
                        strSQL = "CALL SP_M_WORK_ALARM_SELECT_1_1_92('"+common.get_json_value(jsonData,"CORP_ID")+"','"+
                                 common.get_json_value(jsonData,"WORK_ID")+"','WORK_RES_ALARM','"+
                                 common.get_json_value(jsonData,"RES_YN")+"')";
                        JSONArray jsonAlarm = common.execute_query(strSQL);
                        if (common.get_json_array_value(jsonAlarm,"result").equals("ok"))
                        {
                            if (registration_id.equals("")==false)
                            {
                                JSONObject json_result_message = fcm.send(message);
                                result = json_result_message.toString();
                                common.log(module_name,"fcm.send result="+result+",BOOK_ID : "+common.get_json_value(jsonData,"BOOK_ID"));     
                            }
                            if (IN_GBN.equals("VCN")==false)
                            {
                                if (web_registration_id.equals("")==false)
                                {
                                    JSONObject json_result_message = web_fcm.send(web_message);
                                    result = json_result_message.toString();
                                    common.log(module_name,"web_fcm.send result="+result+",BOOK_ID : "+common.get_json_value(jsonData,"BOOK_ID"));                                                                
                                }  
                            }                            
                        }				
                    }                    
                }
                catch (Exception ex)
                {
                    common.log(module_name,"SendPushAlaram FCM error : " + ex.getMessage());
                }
            }
            catch (Exception ex)
            {
                common.log(module_name,"SendPushAlaram error : " + ex.getMessage());
            }
        }
        catch (Exception ex)
        {
            common.log(module_name,"SendPushAlaram Error : " + ex.getMessage());
        }

        return result;
    }

    public String SetAlarmWorkRecommandRes(JSONObject data) 
    {
        String result = "";
        JSONObject jsonData = data;
        common.log(module_name,"SetAlarmWorkRecommandRes RMD_ID : " + common.get_json_value(jsonData,"RMD_ID"));
        result = SendAlarmWorkRecommandRes(data);
        return result;
    }

    private String SendAlarmWorkRecommandRes(JSONObject data)
    {
        String result = "";
        try
        {
            JSONObject jsonData = data;            
            String IN_VERISON_NAME = common.get_json_value(jsonData,"VERSION_NAME");
            String IN_CONTENT = "";
            String IN_MSG_TYPE = "";
            String IN_GBN = "";
            String IN_ID_KEY_TYPE = "RMD_ID";
            String IN_SEND_DATE_NUMBER = common.now_date_integer();

            String api_key ="";
            FCM fcm = null;
            String registration_id = "";
            JSONObject message = new JSONObject();
            JSONObject message_body = new JSONObject();
            JSONObject message_data = new JSONObject();

            String web_api_key = "";
            FCM web_fcm = null;
            String web_registration_id = common.get_json_value(jsonData,"CORP_WEB_REG_ID");
            JSONObject  web_message = new JSONObject();
            JSONObject web_message_notification = new JSONObject();
            JSONObject web_message_data = new JSONObject();
            
            String send_gbn = common.get_json_value(jsonData,"GBN");
            String IN_BASE_TYPE = "BASE";
            String IN_TITLE = "";
            String IN_APP_LANG = "KR";
            if (common.get_json_value(jsonData,"RES_YN").equals("Y"))
            {
                IN_GBN = common.get_json_value(jsonData,"GBN");	
                IN_TITLE = common.get_json_value(jsonData,"EMP_NAME") + 
                           common.encode("의 알람",IN_APP_LANG);
                IN_CONTENT = common.get_json_value(jsonData,"EMP_NAME")+
                             common.encode("님이 근무요청에 수락하였습니다. ",IN_APP_LANG)+
                            common.get_json_value(jsonData,"SCHEDULE_NAME")+" "+
                            "["+
                            common.get_json_value(jsonData,"JOB_GBN_NM")+"] "+
                            common.get_json_value(jsonData,"JOB_TYPE_NM")+" "+
                            common.get_json_value(jsonData,"WORK_DATE") + " "+
                            common.encode("필요인력 ",IN_APP_LANG)+
                            common.get_json_value(jsonData,"NEED_CNT")+
                            common.encode("명 중 ",IN_APP_LANG)+
                            common.get_json_value(jsonData,"BOOK_CNT")+
                            common.encode("명 인력 배치 예약 완료  ",IN_APP_LANG)+
                            common.encode("( 소속 : ",IN_APP_LANG)+
                            common.get_json_value(jsonData,"MEMBER_CNT")+" " +
                            common.encode("/ 베테랑 인력 : ",IN_APP_LANG) + 
                            common.get_json_value(jsonData,"BOOK_URGENT_CNT")+" "+
                            common.encode("/ 베테랑 인력 추천 ",IN_APP_LANG) + 
                            common.get_json_value(jsonData,"VV_BOOK_CNT") + " )";
                IN_MSG_TYPE = "WORK_RECOMMAND_RES_ALARM";
            }
            else if(common.get_json_value(jsonData,"RES_YN").equals("N"))
            {
                if (common.get_json_value(jsonData,"GBN").equals("ECN"))
                {
                    IN_GBN = "ECN";
                    IN_TITLE = common.get_json_value(jsonData,"EMP_NAME") + 
                               common.encode("의 알람",IN_APP_LANG);
                    IN_CONTENT = common.get_json_value(jsonData,"EMP_NAME")+    
                                 common.encode("님이 예약을 취소하였습니다. ",IN_APP_LANG)+
                            common.get_json_value(jsonData,"SCHEDULE_NAME")+" "+
                            "["+
                            common.get_json_value(jsonData,"JOB_GBN_NM")+"] "+
                            common.get_json_value(jsonData,"JOB_TYPE_NM")+" "+
                            common.get_json_value(jsonData,"WORK_DATE") + " "+
                            common.encode("필요인력 ",IN_APP_LANG)+
                            common.get_json_value(jsonData,"NEED_CNT")+
                            common.encode("명 중 ",IN_APP_LANG)+
                            common.get_json_value(jsonData,"BOOK_CNT")+
                            common.encode("명 인력 배치 예약 완료  ",IN_APP_LANG)+
                            common.encode("( 소속 : ",IN_APP_LANG)+
                            common.get_json_value(jsonData,"MEMBER_CNT")+" "+
                            common.encode("/ 베테랑 인력 : ",IN_APP_LANG) +
                            common.get_json_value(jsonData,"BOOK_URGENT_CNT")+" "+
                            common.encode("/ 베테랑 인력 추천 ",IN_APP_LANG) + 
                            common.get_json_value(jsonData,"VV_BOOK_CNT") + " )";
                    IN_MSG_TYPE = "WORK_RECOMMAND_RES_ALARM";
                }
                else if (common.get_json_value(jsonData,"GBN").equals("VCN"))
                {
                    IN_GBN = "VCN";
                    IN_TITLE = common.get_json_value(jsonData,"CORP_NAME") +    
                               common.encode("의 알람",IN_APP_LANG);
                    IN_CONTENT = common.get_json_value(jsonData,"CORP_NAME")+
                                 common.encode("직업소개소에서 예약을 취소하였습니다. ",IN_APP_LANG)+
                            common.get_json_value(jsonData,"SCHEDULE_NAME")+" "+
                            "["+
                            common.get_json_value(jsonData,"JOB_GBN_NM")+"] "+
                            common.get_json_value(jsonData,"JOB_TYPE_NM")+" "+
                            common.get_json_value(jsonData,"WORK_DATE") + " "+
                            common.encode("필요인력 ",IN_APP_LANG)+
                            common.get_json_value(jsonData,"NEED_CNT")+
                            common.encode("명 중 ",IN_APP_LANG)+
                            common.get_json_value(jsonData,"BOOK_CNT")+
                            common.encode("명 인력 배치 예약 완료  ",IN_APP_LANG)+
                            common.encode("( 소속 : ",IN_APP_LANG)+
                            common.get_json_value(jsonData,"MEMBER_CNT")+" "+
                            common.encode("/ 베테랑 인력 : ",IN_APP_LANG) + 
                            common.get_json_value(jsonData,"BOOK_URGENT_CNT")+" "+
                            common.encode("/ 베테랑 인력 추천 ",IN_APP_LANG) + 
                            common.get_json_value(jsonData,"VV_BOOK_CNT") + " )";
                    IN_MSG_TYPE = "WORK_RECOMMAND_RES_ALARM";
                }
                else
                {
                    IN_GBN = common.get_json_value(jsonData,"GBN");	
                    IN_TITLE = common.get_json_value(jsonData,"EMP_NAME") + 
                               common.encode("의 알람",IN_APP_LANG);
                    IN_CONTENT = common.get_json_value(jsonData,"EMP_NAME")+
                                 common.encode("님이 근무요청을 거절하였습니다. ",IN_APP_LANG)+
                            common.get_json_value(jsonData,"SCHEDULE_NAME")+" "+
                            "["+
                            common.get_json_value(jsonData,"JOB_GBN_NM")+"] "+
                            common.get_json_value(jsonData,"JOB_TYPE_NM")+" "+
                            common.get_json_value(jsonData,"WORK_DATE") + " "+
                            common.encode("필요인력 ",IN_APP_LANG)+
                            common.get_json_value(jsonData,"NEED_CNT")+
                            common.encode("명 중 ",IN_APP_LANG)+
                            common.get_json_value(jsonData,"BOOK_CNT")+
                            common.encode("명 인력 배치 예약 완료  ",IN_APP_LANG)+
                            common.encode("( 소속 : ",IN_APP_LANG)+
                            common.get_json_value(jsonData,"MEMBER_CNT")+" "+
                            common.encode("/ 베테랑 인력 : ",IN_APP_LANG) + 
                            common.get_json_value(jsonData,"BOOK_URGENT_CNT")+" "+
                            common.encode("/ 베테랑 인력 추천 ",IN_APP_LANG) + 
                            common.get_json_value(jsonData,"VV_BOOK_CNT") + " )";
                    IN_MSG_TYPE = "WORK_RECOMMAND_RES_ALARM";
                }
            }

            String MSG_ID = "";

            try
            {
                String GBN = common.get_json_value(jsonData,"GBN");
                String ID_TYPE = "RMD_ID";
                String ID_KEY = common.get_json_value(jsonData,"RMD_ID");
                String ERR_YN = "N";
                String ERR_MSG = "";
                String EMP_ID = common.get_json_value(jsonData,"EMP_ID");
                String CORP_ID = common.get_json_value(jsonData,"CORP_ID");
                String WORK_REQ_TYPE = common.get_json_value(jsonData,"WORK_REQ_TYPE");
                String WORK_REJ_TYPE = common.get_json_value(jsonData,"WORK_REJ_TYPE");
                String WORK_DATE = common.get_json_value(jsonData,"WORK_DATE");
                String W_REQ_GBN = common.get_json_value(jsonData,"W_REQ_GBN");
                String R_GBN = "E-B";
                //(GBN, ID_KEY_TYPE, ID_KEY, ERR_YN, ERR_MSG, EMP_ID, 
                // CORP_ID, WORK_REQ_TYPE, WORK_REJ_TYPE
                String strSQL = "CALL SP_M_MSG_INFO_INSERT('"+GBN+"','"+ID_TYPE+"','"+ID_KEY+"'," +
                    "'"+EMP_ID+"','"+CORP_ID+"','"+WORK_REQ_TYPE+"'," +
                    "'"+WORK_REJ_TYPE+"','"+R_GBN+"','"+common.get_value(IN_CONTENT)+"','"+WORK_DATE+"','"+W_REQ_GBN+"')"; 
                common.log(module_name,strSQL);
                
                JSONArray jsonMsg = common.execute_query_commit(strSQL);
                
                common.log(module_name,"INSERT OK BOOK_ID : " + common.get_json_value(jsonData,"BOOK_ID"));
                
                MSG_ID = common.get_json_array_value(jsonMsg,"MSG_ID");
                if(jsonData.has("MSG_ID")){
                    jsonData.remove("MSG_ID");
                }
                jsonData.put("MSG_ID",MSG_ID);
                try
                {
                    // 직업소개소->구직자
                    if (IN_GBN == "VCN")
                    {
                        api_key = common.user_api_key;			
                        registration_id = common.get_json_value(jsonData,"EMP_MOBILE_REG_ID");
                    }
                    else
                    {
                        api_key = common.vendor_api_key;			
                        registration_id = common.get_json_value(jsonData,"CORP_MOBILE_REG_ID");
                    }
                    
                    fcm = new FCM(api_key);
                    
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
                            MILISECONDS : common.get_miliseconds(), title:jsonData.EMP_NAME+"의 알람", 
                            body:IN_CONTENT,                        id: 1,
                            type: common.alarm_work_res_msg_type,   isShow: 1,
                            sound:"default",                        MSG_TYPE:IN_MSG_TYPE,
                            GBN:IN_GBN,                             W_REQ_GBN : jsonData.W_REQ_GBN,
                            EMP_NAME : jsonData.EMP_NAME,           SCHEDULE_NAME : jsonData.SCHEDULE_NAME,
                            SITE_NAME : jsonData.SITE_NAME,         BIZ_NAME : jsonData.BIZ_NAME,
                            JOB_GBN_NM : jsonData.JOB_GBN_NM,       JOB_TYPE_NM : jsonData.JOB_TYPE_NM,
                            WORK_PAY : jsonData.WORK_PAY,           WORK_DATE : jsonData.WORK_DATE,
                            NEED_CNT : jsonData.NEED_CNT,           BOOK_CNT : jsonData.BOOK_CNT,
                            MEMBER_CNT : jsonData.MEMBER_CNT,       BOOK_URGENT_CNT : jsonData.BOOK_URGENT_CNT,
                            VV_BOOK_CNT : jsonData.VV_BOOK_CNT,     RMD_ID:jsonData.RMD_ID,
                            EMP_ID: jsonData.EMP_ID,                CORP_ID: jsonData.CORP_ID,  
                            RES_YN : jsonData.RES_YN,	            BASE_TYPE : IN_BASE_TYPE,
                            CORP_NAME : jsonData.CORP_NAME,         WORK_REQ_TYPE : jsonData.WORK_REQ_TYPE,
                            WORK_REJ_TYPE : jsonData.WORK_REJ_TYPE, ID_KEY_TYPE : IN_ID_KEY_TYPE,
                            ID_KEY : jsonData.BOOK_ID,              DATE_GBN : jsonData.DATE_GBN,
                            DATE_GBN_NM : jsonData.DATE_GBN_NM,     AS_WORK_DATE : jsonData.AS_WORK_DATE,
                            TO_WORK_DATE : jsonData.TO_WORK_DATE,   MSG_ID : jsonData.MSG_ID,
                            WORK_ID : jsonData.WORK_ID,             NEED_ID : jsonData.NEED_ID
                            }
                        }
                    };
                    */
                    message_body.put("MILISECONDS",Integer.parseInt(common.get_miliseconds())); 
                    message_body.put("title",IN_TITLE);
                    message_body.put("body",IN_CONTENT); 
                    message_body.put("id",1);  
                    message_body.put("type",common.alarm_work_res_msg_type); 
                    message_body.put("isShow",1);
                    message_body.put("sound","default"); 
                    message_body.put("MSG_TYPE",IN_MSG_TYPE);
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
                    message_body.put("VV_BOOK_CNT",common.get_json_value_int(jsonData,"VV_BOOK_CNT")); 
                    message_body.put("RMD_ID",common.get_json_value_int(jsonData,"RMD_ID"));
                    message_body.put("EMP_ID",common.get_json_value_int(jsonData,"EMP_ID")); 
                    message_body.put("CORP_ID",common.get_json_value_int(jsonData,"CORP_ID"));
                    message_body.put("RES_YN",common.get_json_value(jsonData,"RES_YN")); 
                    message_body.put("BASE_TYPE",IN_BASE_TYPE);
                    message_body.put("CORP_NAME",common.get_json_value(jsonData,"CORP_NAME")); 
                    message_body.put("WORK_REQ_TYPE",common.get_json_value(jsonData,"WORK_REQ_TYPE"));
                    message_body.put("WORK_REJ_TYPE",common.get_json_value(jsonData,"WORK_REJ_TYPE")); 
                    message_body.put("ID_KEY_TYPE",IN_ID_KEY_TYPE);
                    message_body.put("ID_KEY",common.get_json_value_int(jsonData,"BOOK_ID")); 
                    message_body.put("DATE_GBN",common.get_json_value(jsonData,"DATE_GBN"));
                    message_body.put("DATE_GBN_NM",common.get_json_value(jsonData,"DATE_GBN_NM")); 
                    message_body.put("AS_WORK_DATE",common.get_json_value(jsonData,"AS_WORK_DATE"));
                    message_body.put("TO_WORK_DATE",common.get_json_value(jsonData,"TO_WORK_DATE")); 
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
                    
                    if (IN_GBN.equals("VCN")==false)
                    {
                        web_api_key = common.vendor_web_api_key;			
                        web_fcm = new FCM(web_api_key);
                        
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
                        web_message_notification.put("type",common.alarm_work_res_msg_type);
                        web_message_notification.put("isShow",1);
                        web_message_notification.put("sound","default");
                        web_message_notification.put("click_action",IN_MSG_TYPE);
                        /*
                        web_message = { //this may vary according to the message type (single recipient, multicast, topic, et cetera)                            
                            notification: {                                
                            },
                            data: {  //you can send only notification or only data(or include both)
                                MILISECONDS : common.get_miliseconds(), title:jsonData.EMP_NAME+"의 알람", 
                                body:IN_CONTENT,    id: 1,
                                type: common.alarm_work_res_msg_type,   isShow: 1,
                                sound:"default",    MSG_TYPE:IN_MSG_TYPE,
                                GBN:IN_GBN, click_action:IN_MSG_TYPE,
                                W_REQ_GBN : jsonData.W_REQ_GBN, EMP_NAME : jsonData.EMP_NAME,   
                                SCHEDULE_NAME : jsonData.SCHEDULE_NAME, SITE_NAME : jsonData.SITE_NAME, 
                                BIZ_NAME : jsonData.BIZ_NAME, JOB_GBN_NM : jsonData.JOB_GBN_NM, 
                                JOB_TYPE_NM : jsonData.JOB_TYPE_NM, WORK_PAY : jsonData.WORK_PAY, 
                                WORK_DATE : jsonData.WORK_DATE, NEED_CNT : jsonData.NEED_CNT,
                                BOOK_CNT : jsonData.BOOK_CNT, MEMBER_CNT : jsonData.MEMBER_CNT, 
                                BOOK_URGENT_CNT : jsonData.BOOK_URGENT_CNT, VV_BOOK_CNT : jsonData.VV_BOOK_CNT, 
                                RMD_ID:jsonData.RMD_ID, EMP_ID: jsonData.EMP_ID, 
                                CORP_ID: jsonData.CORP_ID, RES_YN : jsonData.RES_YN,	 
                                BASE_TYPE : IN_BASE_TYPE, CORP_NAME : jsonData.CORP_NAME, 
                                WORK_REQ_TYPE : jsonData.WORK_REQ_TYPE, WORK_REJ_TYPE : jsonData.WORK_REJ_TYPE, 
                                ID_KEY_TYPE : IN_ID_KEY_TYPE, ID_KEY : jsonData.BOOK_ID, 
                                DATE_GBN : jsonData.DATE_GBN, DATE_GBN_NM : jsonData.DATE_GBN_NM, 
                                AS_WORK_DATE : jsonData.AS_WORK_DATE, TO_WORK_DATE : jsonData.TO_WORK_DATE, 
                                MSG_ID : jsonData.MSG_ID, WORK_ID : jsonData.WORK_ID, 
                                NEED_ID : jsonData.NEED_ID
                            }
                        };
                        */
                        web_message_data.put("MILISECONDS",Integer.parseInt(common.get_miliseconds())); 
                        web_message_data.put("title",IN_TITLE);
                        web_message_data.put("body",IN_CONTENT); 
                        web_message_data.put("id",1);
                        web_message_data.put("type",common.alarm_work_res_msg_type); 
                        web_message_data.put("isShow",1);
                        web_message_data.put("sound","default"); 
                        web_message_data.put("click_action",IN_MSG_TYPE);
                        web_message_data.put("MSG_TYPE",IN_MSG_TYPE); 
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
                        web_message_data.put("NEED_CNT",common.get_json_value(jsonData,"NEED_CNT"));
                        web_message_data.put("BOOK_CNT",common.get_json_value(jsonData,"BOOK_CNT")); 
                        web_message_data.put("MEMBER_CNT",common.get_json_value(jsonData,"MEMBER_CNT"));
                        web_message_data.put("BOOK_URGENT_CNT",common.get_json_value(jsonData,"BOOK_URGENT_CNT")); 
                        web_message_data.put("VV_BOOK_CNT",common.get_json_value(jsonData,"VV_BOOK_CNT"));
                        web_message_data.put("RMD_ID",common.get_json_value(jsonData,"RMD_ID")); 
                        web_message_data.put("EMP_ID",common.get_json_value(jsonData,"EMP_ID"));
                        web_message_data.put("CORP_ID",common.get_json_value(jsonData,"CORP_ID")); 
                        web_message_data.put("RES_YN",common.get_json_value(jsonData,"RES_YN"));
                        web_message_data.put("BASE_TYPE",IN_BASE_TYPE); 
                        web_message_data.put("CORP_NAME",common.get_json_value(jsonData,"CORP_NAME"));
                        web_message_data.put("WORK_REQ_TYPE",common.get_json_value(jsonData,"WORK_REQ_TYPE")); 
                        web_message_data.put("WORK_REJ_TYPE",common.get_json_value(jsonData,"WORK_REJ_TYPE"));
                        web_message_data.put("ID_KEY_TYPE",IN_ID_KEY_TYPE); 
                        web_message_data.put("ID_KEY",common.get_json_value(jsonData,"BOOK_ID"));
                        web_message_data.put("DATE_GBN",common.get_json_value(jsonData,"DATE_GBN")); 
                        web_message_data.put("DATE_GBN_NM",common.get_json_value(jsonData,"DATE_GBN_NM"));
                        web_message_data.put("AS_WORK_DATE",common.get_json_value(jsonData,"AS_WORK_DATE")); 
                        web_message_data.put("TO_WORK_DATE",common.get_json_value(jsonData,"TO_WORK_DATE"));
                        web_message_data.put("MSG_ID",common.get_json_value(jsonData,"MSG_ID")); 
                        web_message_data.put("WORK_ID",common.get_json_value(jsonData,"WORK_ID"));
                        web_message_data.put("NEED_ID",common.get_json_value(jsonData,"NEED_ID"));
                        /*
                        web_message = {                         
                            notification : {},
                            data:{}
                        }
                        */
                        web_message.put("notification",web_message_notification);
                        web_message.put("data",web_message_data); 
                    }
                    
                    // 근무요청 수락
                    if (common.get_json_value(jsonData,"RES_YN").equals("Y"))
                    {
                        strSQL = "CALL SP_M_CORP_CONF_ALARM_INFO_1_1_92('"+common.get_json_value_int(jsonData,"CORP_ID")+"')";
                        JSONArray jsonConf = common.execute_query(strSQL);
                        if(common.get_json_array_value(jsonConf,"WORK_REQ_YN").equals("Y")){
                            if (registration_id.equals("")==false)
                            {
                                JSONObject json_result_message = fcm.send(message);
                                result = json_result_message.toString();
                                common.log(module_name,"fcm.send result="+result+",RMD_ID : "+common.get_json_value(jsonData,"RMD_ID"));

                            }
                            if (IN_GBN.equals("VCN")==false)
                            {    
                                if (web_registration_id.equals("")==false)
                                {
                                    JSONObject json_result_message = web_fcm.send(web_message);
                                    result = json_result_message.toString();
                                    common.log(module_name,"web_fcm.send result="+result+",RMD_ID : "+common.get_json_value(jsonData,"RMD_ID"));
                                }
                            }
                        }
                        else
                        {
                            common.log(module_name,"PUSH_WORK_REQ_YN=N,SendPushAlaram=send_message="+IN_CONTENT+" gcm_message="+message.toString());
                        }                        
                    }                                     
                }
                catch (Exception ex)
                {
                    common.log(module_name,"mongoose db save error : " + ex.getMessage());
                }              
            }
            catch (Exception ex)
            {
                common.log(module_name,"SendPushAlaram error : " + ex.getMessage());
            }
        }
        catch (Exception ex)
        {
            common.log(module_name,"SendPushAlaram Error : " + ex.getMessage());
        }
        
        return result;
    }

    public String SetAlarmShowRes(JSONObject data)
    {
        String result = "";
        JSONObject jsonData = data;
        common.log(module_name,"SetAlarmShowRes : " + common.get_json_value(jsonData,"SHOW_ID"));
        result = SendAlarmShowRes(data);
        return result;
    }

    public String SendAlarmShowRes(JSONObject data)
    {
        String result = "";
        try
        {
            JSONObject jsonData = data;
            
            String IN_VERISON_NAME = common.get_json_value(jsonData,"VERSION_NAME");
            String IN_CONTENT = "";
            String IN_MSG_TYPE = "";	
            String IN_GBN = "";
            String IN_ID_KEY_TYPE = "SHOW_ID";

            String send_gbn = common.get_json_value(jsonData,"GBN");
            String IN_SEND_DATE_NUMBER = common.now_date_integer();
            String IN_BASE_TYPE = "BASE";

            String api_key ="";
            FCM fcm = null;
            String registration_id = "";
            JSONObject message = new JSONObject();
            JSONObject message_body = new JSONObject();
            JSONObject message_data = new JSONObject();

            String web_api_key = common.vendor_web_api_key;
            FCM web_fcm = new FCM(web_api_key);
            String web_registration_id = common.get_json_value(jsonData,"CORP_WEB_REG_ID");
            JSONObject web_message = new JSONObject();
            JSONObject web_message_notification = new JSONObject();
            JSONObject web_message_data = new JSONObject();
            String IN_APP_LANG = "KR";

            String IN_TITLE = "";

            if (common.get_json_value(jsonData,"RES_YN").equals("Y"))
            {
                IN_GBN = common.get_json_value(jsonData,"GBN");
                IN_TITLE = common.get_json_value(jsonData,"EMP_NAME") + 
                           common.encode("의 알람",IN_APP_LANG);
                IN_CONTENT = common.get_json_value(jsonData,"EMP_NAME")+
                            common.encode("님이 출근 가능으로 응답하였습니다. ",IN_APP_LANG)+
                            common.get_json_value(jsonData,"SCHEDULE_NAME")+" "+
                            "["+
                            common.get_json_value(jsonData,"JOB_GBN_NM")+"] "+
                            common.get_json_value(jsonData,"JOB_TYPE_NM")+" "+
                            common.get_json_value(jsonData,"WORK_DATE")+ " "+
                            common.encode("필요인력 ",IN_APP_LANG)+
                            common.get_json_value(jsonData,"NEED_CNT")+
                            common.encode("명 중 ",IN_APP_LANG)+
                            common.get_json_value(jsonData,"BOOK_CNT")+
                            common.encode("명 인력 배치 예약 완료  ",IN_APP_LANG)+
                            common.encode("( 소속 : ",IN_APP_LANG)+
                            common.get_json_value(jsonData,"MEMBER_CNT")+" "+
                            common.encode("/ 베테랑 인력 : ",IN_APP_LANG) +
                            common.get_json_value(jsonData,"BOOK_URGENT_CNT")+" "+
                            common.encode("/ 베테랑 인력 추천 : ",IN_APP_LANG) +
                            common.get_json_value(jsonData,"VV_BOOK_CNT")+")";
                IN_MSG_TYPE = "WORK_SHOW_RES_ALARM";
            }
            else if(common.get_json_value(jsonData,"RES_YN").equals("N"))
            {
                if (common.get_json_value(jsonData,"GBN").equals("ECN"))
                {
                    IN_GBN = "ECN";
                    IN_TITLE = common.get_json_value(jsonData,"EMP_NAME") + 
                               common.encode("의 알람",IN_APP_LANG);
                    IN_CONTENT = common.get_json_value(jsonData,"EMP_NAME")+
                                 common.encode("님이 출근을 취소하였습니다. ",IN_APP_LANG)+
                                common.get_json_value(jsonData,"SCHEDULE_NAME")+" "+
                                "["+
                                common.get_json_value(jsonData,"JOB_GBN_NM")+"] "+
                                common.get_json_value(jsonData,"JOB_TYPE_NM")+" "+
                                common.get_json_value(jsonData,"WORK_DATE") + " "+
                                common.encode("필요인력 ",IN_APP_LANG)+
                                common.get_json_value_int(jsonData,"NEED_CNT")+
                                common.encode("명 중 ",IN_APP_LANG)+
                                common.get_json_value_int(jsonData,"BOOK_CNT")+
                                common.encode("명 인력 배치 예약 완료  ",IN_APP_LANG)+
                                common.encode("( 소속 : ",IN_APP_LANG)+
                                common.get_json_value_int(jsonData,"MEMBER_CNT")+" "+
                                common.encode("/ 베테랑 인력 : ",IN_APP_LANG) + 
                                common.get_json_value_int(jsonData,"BOOK_URGENT_CNT")+" "+
                                common.encode("/ 베테랑 인력 추천 : ",IN_APP_LANG) +
                                common.get_json_value_int(jsonData,"VV_BOOK_CNT")+")";
                    IN_MSG_TYPE = "WORK_SHOW_RES_ALARM";
                }
                else
                {
                    IN_GBN = common.get_json_value(jsonData,"GBN");
                    IN_TITLE = common.get_json_value(jsonData,"EMP_NAME") + 
                               common.encode("의 알람",IN_APP_LANG);
                    IN_CONTENT = common.get_json_value(jsonData,"EMP_NAME")+    
                                common.encode("님이 출근 거부로 응답하였습니다. ",IN_APP_LANG)+
                                common.get_json_value(jsonData,"SCHEDULE_NAME")+" "+
                                "["+
                                common.get_json_value(jsonData,"JOB_GBN_NM")+"] "+
                                common.get_json_value(jsonData,"JOB_TYPE_NM")+" "+
                                common.get_json_value(jsonData,"WORK_DATE") + " "+
                                common.encode("필요인력 ",IN_APP_LANG)+
                                common.get_json_value(jsonData,"NEED_CNT")+"명 중 "+
                                common.get_json_value(jsonData,"BOOK_CNT")+"명 인력 배치 예약 완료  "+
                                common.encode("( 소속 : ",IN_APP_LANG)+
                                common.get_json_value(jsonData,"MEMBER_CNT")+" "+
                                common.encode("/ 베테랑 인력 : ",IN_APP_LANG) +
                                common.get_json_value(jsonData,"BOOK_URGENT_CNT")+" "+
                                common.encode("/ 베테랑 인력 추천 : ",IN_APP_LANG) + 
                                common.get_json_value(jsonData,"VV_BOOK_CNT")+")";
                    IN_MSG_TYPE = "WORK_SHOW_RES_ALARM";
                }

            }
            
            String MSG_ID = "";

            try
            {
                String GBN = "REQ";
                String ID_TYPE = "SHOW_ID";
                String ID_KEY = common.get_json_value(jsonData,"SHOW_ID");
                String ERR_YN = "N";
                String ERR_MSG = "";
                String EMP_ID = common.get_json_value(jsonData,"EMP_ID");
                String CORP_ID = common.get_json_value(jsonData,"CORP_ID");
                String SHOW_REQ_TYPE = common.get_json_value(jsonData,"SHOW_REQ_TYPE");
                String SHOW_REJ_TYPE = common.get_json_value(jsonData,"SHOW_REJ_TYPE");
                String WORK_DATE = common.get_json_value(jsonData,"WORK_DATE");
                String W_REQ_GBN = common.get_json_value(jsonData,"W_REQ_GBN");
                String R_GBN = "E-B";
                //(GBN, ID_KEY_TYPE, ID_KEY, ERR_YN, ERR_MSG, EMP_ID, 
                // CORP_ID, WORK_REQ_TYPE, WORK_REJ_TYPE
                String strSQL = "CALL SP_M_MSG_INFO_INSERT('"+GBN+"','"+ID_TYPE+"','"+ID_KEY+"'," +
                    "'"+EMP_ID+"','"+CORP_ID+"','"+SHOW_REQ_TYPE+"'," +
                    "'"+SHOW_REJ_TYPE+"','"+R_GBN+"','"+common.get_value(IN_CONTENT)+"','"+WORK_DATE+"','"+W_REQ_GBN+"')"; 
                common.log(module_name,strSQL);

                JSONArray jsonMsg = common.execute_query_commit(strSQL);

                MSG_ID = jsonMsg.getJSONArray(0).getJSONObject(0).get("MSG_ID").toString();
                if(jsonData.has("MSG_ID")){
                    jsonData.remove("MSG_ID");
                }
                jsonData.put("MSG_ID",MSG_ID);

                // 직업소개소->구직자
                api_key = common.vendor_api_key;
                fcm = new FCM(api_key);

                registration_id = common.get_json_value(jsonData,"CORP_MOBILE_REG_ID");

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
                message = { //this may vary according to the message type (single recipient, multicast, topic, et cetera)                    
                    data: {  //you can send only notification or only data(or include both)					                        
                        body: 
                        {
                        MILISECONDS : common.get_miliseconds(), MSG_TYPE:IN_MSG_TYPE,
                        BOOK_ID:jsonData.BOOK_ID, GBN:IN_GBN,
                        W_REQ_GBN : jsonData.W_REQ_GBN, EMP_NAME:jsonData.EMP_NAME,
                        SCHEDULE_NAME : jsonData.SCHEDULE_NAME, SITE_NAME:jsonData.SITE_NAME,
                        BIZ_NAME : jsonData.BIZ_NAME, JOB_GBN_NM:jsonData.JOB_GBN_NM,
                        JOB_TYPE_NM:jsonData.JOB_TYPE_NM, WORK_PAY : jsonData.WORK_PAY,
                        WORK_DATE : jsonData.WORK_DATE, NEED_CNT : jsonData.NEED_CNT,
                        BOOK_CNT : jsonData.BOOK_CNT, MEMBER_CNT : jsonData.MEMBER_CNT,
                        BOOK_URGENT_CNT : jsonData.BOOK_URGENT_CNT, VV_BOOK_CNT : jsonData.VV_BOOK_CNT,
                        VS_SUBMIT_CNT : jsonData.VS_SUBMIT_CNT, SHOW_ID : jsonData.SHOW_ID,
                        EMP_ID: jsonData.EMP_ID, CORP_ID: jsonData.CORP_ID,
                        RES_YN : jsonData.RES_YN, WORK_DATE : jsonData.WORK_DATE,
                        CORP_NAME : jsonData.CORP_NAME, SHOW_REQ_TYPE : jsonData.SHOW_REQ_TYPE,
                        SHOW_REJ_TYPE : jsonData.SHOW_REJ_TYPE, ID_KEY_TYPE : IN_ID_KEY_TYPE,
                        ID_KEY : jsonData.SHOW_ID, RES_YN : jsonData.RES_YN,
                        ADDR1:jsonData.ADDR1, DISTANCE:jsonData.DISTANCE,
                        JOB_CONTENT:jsonData.JOB_CONTENT, BASE_TYPE : IN_BASE_TYPE,
                        volume: "3.21.15", title:jsonData.EMP_NAME+"의 알람", 
                        body:IN_CONTENT, id: 1, type: common.alarm_show_res_msg_type,  
                        isShow: 1, sound:"default", 
                        MSG_ID : jsonData.MSG_ID, WORK_ID : jsonData.WORK_ID, 
                        NEED_ID : jsonData.NEED_ID, FEE_AMT : jsonData.FEE_AMT, 
                        DAY_OF_WEEK : jsonData.DAY_OF_WEEK
                        }
                    }
                };
                */
                message_body.put("MILISECONDS",Integer.parseInt(common.get_miliseconds())); 
                message_body.put("MSG_TYPE",IN_MSG_TYPE);
                message_body.put("BOOK_ID",common.get_json_value_int(jsonData,"BOOK_ID")); 
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
                message_body.put("VV_BOOK_CNT",common.get_json_value_int(jsonData,"VV_BOOK_CNT"));
                message_body.put("VS_SUBMIT_CNT",common.get_json_value_int(jsonData,"VS_SUBMIT_CNT")); 
                message_body.put("SHOW_ID",common.get_json_value_int(jsonData,"SHOW_ID"));
                message_body.put("EMP_ID",common.get_json_value_int(jsonData,"EMP_ID")); 
                message_body.put("CORP_ID",common.get_json_value_int(jsonData,"CORP_ID"));
                message_body.put("RES_YN",common.get_json_value(jsonData,"RES_YN")); 
                message_body.put("WORK_DATE",common.get_json_value(jsonData,"WORK_DATE"));
                message_body.put("CORP_NAME",common.get_json_value(jsonData,"CORP_NAME")); 
                message_body.put("SHOW_REQ_TYPE",common.get_json_value(jsonData,"SHOW_REQ_TYPE"));
                message_body.put("SHOW_REJ_TYPE",common.get_json_value(jsonData,"SHOW_REJ_TYPE")); 
                message_body.put("ID_KEY_TYPE",IN_ID_KEY_TYPE);
                message_body.put("ID_KEY",common.get_json_value_int(jsonData,"SHOW_ID")); 
                message_body.put("RES_YN",common.get_json_value(jsonData,"RES_YN"));
                message_body.put("ADDR1",common.get_json_value(jsonData,"ADDR1")); 
                message_body.put("DISTANCE",common.get_json_value_double(jsonData,"DISTANCE"));
                message_body.put("JOB_CONTENT",common.get_json_value(jsonData,"JOB_CONTENT")); 
                message_body.put("BASE_TYPE",IN_BASE_TYPE);
                message_body.put("volume","3.21.15"); message_body.put("title",IN_TITLE);
                message_body.put("body",IN_CONTENT); message_body.put("type",common.alarm_show_res_msg_type);
                message_body.put("isShow",1); message_body.put("sound","default");
                message_body.put("MSG_ID",common.get_json_value_int(jsonData,"MSG_ID")); 
                message_body.put("WORK_ID",common.get_json_value_int(jsonData,"WORK_ID"));
                message_body.put("NEED_ID",common.get_json_value_int(jsonData,"NEED_ID")); 
                message_body.put("FEE_AMT",common.get_json_value(jsonData,"FEE_AMT"));
                message_body.put("DAY_OF_WEEK",common.get_json_value(jsonData,"DAY_OF_WEEK"));
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
                    data: {  //you can send only notification or only data(or include both)
                        MILISECONDS : common.get_miliseconds(), MSG_TYPE:IN_MSG_TYPE,
                        BOOK_ID:jsonData.BOOK_ID, GBN:IN_GBN,
                        W_REQ_GBN : jsonData.W_REQ_GBN, EMP_NAME:jsonData.EMP_NAME,
                        SCHEDULE_NAME:jsonData.SCHEDULE_NAME, SITE_NAME:jsonData.SITE_NAME,
                        BIZ_NAME:jsonData.BIZ_NAME, JOB_GBN_NM:jsonData.JOB_GBN_NM,
                        JOB_TYPE_NM:jsonData.JOB_TYPE_NM, WORK_PAY : jsonData.WORK_PAY,
                        WORK_DATE : jsonData.WORK_DATE, NEED_CNT : jsonData.NEED_CNT,
                        MEMBER_CNT : jsonData.MEMBER_CNT, BOOK_URGENT_CNT : jsonData.BOOK_URGENT_CNT,
                        VV_BOOK_CNT : jsonData.VV_BOOK_CNT, VS_SUBMIT_CNT : jsonData.VS_SUBMIT_CNT,
                        SHOW_ID : jsonData.SHOW_ID, EMP_ID: jsonData.EMP_ID,
                        CORP_ID: jsonData.CORP_ID, RES_YN : jsonData.RES_YN,
                        CORP_NAME : jsonData.CORP_NAME,
                        SHOW_REQ_TYPE : jsonData.SHOW_REQ_TYPE, SHOW_REJ_TYPE : jsonData.SHOW_REJ_TYPE,
                        ID_KEY_TYPE : IN_ID_KEY_TYPE, ID_KEY : jsonData.SHOW_ID,
                        ADDR1:jsonData.ADDR1,
                        DISTANCE:jsonData.DISTANCE, JOB_CONTENT:jsonData.JOB_CONTENT,
                        BASE_TYPE : IN_BASE_TYPE, volume: "3.21.15",
                        title:jsonData.EMP_NAME+"의 알람",  body:IN_CONTENT,
                        id: 1, type: common.alarm_show_res_msg_type, 
                        isShow: 1, sound:"default",
                        MSG_ID : jsonData.MSG_ID, WORK_ID : jsonData.WORK_ID,
                        NEED_ID : jsonData.NEED_ID, FEE_AMT : jsonData.FEE_AMT,
                        DAY_OF_WEEK : jsonData.DAY_OF_WEEK
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
                web_message_data.put("SHOW_ID",common.get_json_value_int(jsonData,"SHOW_ID")); 
                web_message_data.put("ADDR1",common.get_json_value(jsonData,"ADDR1"));
                web_message_data.put("DISTANCE",common.get_json_value_double(jsonData,"DISTANCE")); 
                web_message_data.put("JOB_CONTENT",common.get_json_value(jsonData,"JOB_CONTENT"));
                web_message_data.put("BASE_TYPE",IN_BASE_TYPE); 
                web_message_data.put("volume","3.21.15"); 
                web_message_data.put("id",1); 
                web_message_data.put("sound","default");
                web_message_data.put("MSG_ID",common.get_json_value_int(jsonData,"MSG_ID")); 
                web_message_data.put("WORK_ID",common.get_json_value_int(jsonData,"WORK_ID"));
                web_message_data.put("NEED_ID",common.get_json_value_int(jsonData,"NEED_ID")); 
                web_message_data.put("FEE_AMT",common.get_json_value(jsonData,"FEE_AMT"));
                web_message_data.put("DAY_OF_WEEK",common.get_json_value(jsonData,"DAY_OF_WEEK"));
                /*
                web_message = {                         
                    notification : {},
                    data:{}
                }
                */
                web_message.put("notification",web_message_notification);
                web_message.put("data",web_message_data); 
                

                // 설정에 따른 알람 발송
                if (common.get_json_value(jsonData,"RES_YN").equals("Y"))
                {

                    strSQL = "CALL SP_M_WORK_ALARM_SELECT_1_1_92('"+common.get_json_value(jsonData,"CORP_ID")+"','"+
                             common.get_json_value(jsonData,"WORK_ID")+"','WORK_SHOW_RES_ALARM','"+
                             common.get_json_value(jsonData,"RES_YN")+"')";
                    common.system_log("strQuery : " + strSQL);
                    
                    JSONArray jsonAlarm = common.execute_query(strSQL);

                    if (common.get_json_array_value(jsonAlarm,"result").equals("ok"))
                    {
                        if (registration_id.equals("")==false)
                        {
                            JSONObject json_result_message = fcm.send(message);
                            result = json_result_message.toString();
                            common.log(module_name,"fcm.send result="+result+",SHOW_ID : " + common.get_json_value(jsonData,"SHOW_ID"));
                        }
                        
                        if (web_registration_id.equals("")==false)
                        {                            
                            JSONObject json_result_message = web_fcm.send(web_message);
                            result = json_result_message.toString();
                            common.log(module_name,"web_fcm.send result="+result+",SHOW_ID : " + common.get_json_value(jsonData,"SHOW_ID"));
                        }                       
                    }
                    
                }
                else
                {
                    strSQL = "CALL SP_M_WORK_ALARM_SELECT_1_1_92('"+common.get_json_value(jsonData,"CORP_ID")+"','"+
                                    common.get_json_value(jsonData,"WORK_ID")+"','WORK_SHOW_RES_ALARM','"+
                                    common.get_json_value(jsonData,"RES_YN")+"')";
                    common.system_log("strQuery : " + strSQL);
                    JSONArray jsonAlarm = common.execute_query(strSQL);
                    if (common.get_json_array_value(jsonAlarm,"result").equals("ok"))
                    {
                        if (registration_id.equals("")==false)
                        {
                            JSONObject json_result_message = fcm.send(message);
                            result = json_result_message.toString();
                            common.log(module_name,"fcm.send result="+result+",SHOW_ID : " + common.get_json_value(jsonData,"SHOW_ID"));
                        }
                        
                        if (web_registration_id.equals("")==false)
                        {                            
                            JSONObject json_result_message = web_fcm.send(web_message);
                            result = json_result_message.toString();
                            common.log(module_name,"web_fcm.send result="+result+",SHOW_ID : " + common.get_json_value(jsonData,"SHOW_ID"));
                        }   
                        
                    }
                    
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

        return result;
    }

    public String SetAlarmShowGpsRes(JSONObject data)
    {
        String result = "";

        JSONObject jsonData = data;
        common.log(module_name,"SetAlarmShowGpsRes : " + common.get_json_value(jsonData,"SHOW_ID"));
        result = SendAlarmShowGpsRes(data);
        return result;
    }

    public String SendAlarmShowGpsRes(JSONObject data)
    {
        String result = "";
        try
        {
            JSONObject jsonData = data;
            
            String IN_VERISON_NAME = common.get_json_value(jsonData,"VERSION_NAME");
            String IN_CONTENT = "";
            String IN_MSG_TYPE = "";	
            String IN_GBN = "";
            String IN_ID_KEY_TYPE = "SHOW_ID";
            String WORK_DATE = "";

            String api_key ="";
            FCM fcm = null;
            String registration_id = "";
            JSONObject message = new JSONObject();
            JSONObject message_body = new JSONObject();
            JSONObject message_data = new JSONObject();

            String web_api_key = common.vendor_web_api_key;
            FCM web_fcm = new FCM(web_api_key);
            String web_registration_id = common.get_json_value(jsonData,"CORP_WEB_REG_ID");

            String send_gbn = common.get_json_value(jsonData,"GBN");
            String IN_SEND_DATE_NUMBER = common.now_date_integer();
            String IN_BASE_TYPE = "BASE";
            String IN_TITLE = "";
            String IN_APP_LANG = "KR";
            
            IN_TITLE = common.get_json_value(jsonData,"EMP_NAME")+
                       common.encode("의 알림",IN_APP_LANG);
            IN_GBN = common.get_json_value(jsonData,"GBN");
            IN_CONTENT = common.get_json_value(jsonData,"EMP_NAME")+
                        common.encode("님이 현장에서 출근 등록하였습니다. ",IN_APP_LANG)+
                        common.get_json_value(jsonData,"SCHEDULE_NAME")+" "+
                        "["+
                        common.get_json_value(jsonData,"JOB_GBN_NM")+"] "+
                        common.get_json_value(jsonData,"JOB_TYPE_NM")+" "+
                        common.get_json_value(jsonData,"WORK_DATE") + " "+
                        common.encode("필요인력 ",IN_APP_LANG)+
                        common.get_json_value(jsonData,"NEED_CNT")+
                        common.encode("명 ",IN_APP_LANG)+
                        common.encode("중 ",IN_APP_LANG)+ 
                        common.get_json_value(jsonData,"BOOK_CNT")+
                        common.encode("명 인력 배치 예약 완료  ",IN_APP_LANG)+
                        common.encode("( 소속 : ",IN_APP_LANG)+
                        common.get_json_value(jsonData,"MEMBER_CNT")+" "+
                        common.encode("/ 베테랑 인력 : ",IN_APP_LANG) + 
                        common.get_json_value(jsonData,"BOOK_URGENT_CNT") +" "+
                        common.encode("/ 베테랑 인력 추천 : ",IN_APP_LANG) + 
                        common.get_json_value(jsonData,"VV_BOOK_CNT") +" "+
                        ")";
            IN_MSG_TYPE = "WORK_SHOW_GPS_RES_ALARM";
            
            String MSG_ID = "";
            

            try
            {
                String GBN = common.get_json_value(jsonData,"GBN");
                String ID_TYPE = "SHOW_ID";
                String ID_KEY = common.get_json_value(jsonData,"SHOW_ID");
                String ERR_YN = "N";
                String ERR_MSG = "";
                String EMP_ID = common.get_json_value(jsonData,"EMP_ID");
                String CORP_ID = common.get_json_value(jsonData,"CORP_ID");
                String SHOW_REQ_TYPE = common.get_json_value(jsonData,"SHOW_REQ_TYPE");
                String SHOW_REJ_TYPE = common.get_json_value(jsonData,"SHOW_REJ_TYPE");
                String W_REQ_GBN = common.get_json_value(jsonData,"W_REQ_GBN");			
                String R_GBN = "E-B";

                //(GBN, ID_KEY_TYPE, ID_KEY, ERR_YN, ERR_MSG, EMP_ID, 
                // CORP_ID, WORK_REQ_TYPE, WORK_REJ_TYPE
                String strSQL = "CALL SP_M_MSG_INFO_INSERT('"+GBN+"','"+ID_TYPE+"','"+ID_KEY+"'," +
                    "'"+EMP_ID+"','"+CORP_ID+"','"+SHOW_REQ_TYPE+"'," +
                    "'"+SHOW_REJ_TYPE+"','"+R_GBN+"','"+common.get_value(IN_CONTENT)+"','"+
                    common.get_json_value(jsonData,"WORK_DATE")+"','"+W_REQ_GBN+"')"; 
                common.log(module_name,strSQL);
                
                JSONArray jsonMsg = common.execute_query_commit(strSQL);
                MSG_ID = common.get_json_array_value(jsonMsg,"MSG_ID");
                if(jsonData.has("MSG_ID")){
                    jsonData.remove("MSG_ID");
                }
                jsonData.put("MSG_ID",MSG_ID);                
                
                try
                {
                    // 직업소개소->구직자
                    api_key = common.vendor_api_key;
                    fcm = new FCM(api_key);

                    registration_id = common.get_json_value(jsonData,"CORP_MOBILE_REG_ID");

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
                            MILISECONDS : common.get_miliseconds(), MSG_TYPE:IN_MSG_TYPE,
                            BOOK_ID:jsonData.BOOK_ID, GBN:IN_GBN,
                            W_REQ_GBN : jsonData.W_REQ_GBN, EMP_NAME:jsonData.EMP_NAME,
                            SCHEDULE_NAME : jsonData.SCHEDULE_NAME, SITE_NAME:jsonData.SITE_NAME,
                            BIZ_NAME:jsonData.BIZ_NAME, JOB_GBN_NM:jsonData.JOB_GBN_NM,
                            JOB_TYPE_NM:jsonData.JOB_TYPE_NM, FEE_AMT : jsonData.FEE_AMT,
                            WORK_PAY : jsonData.WORK_PAY, WORK_DATE : jsonData.WORK_DATE,
                            NEED_CNT : jsonData.NEED_CNT, BOOK_CNT : jsonData.BOOK_CNT,
                            MEMBER_CNT : jsonData.MEMBER_CNT, BOOK_URGENT_CNT : jsonData.BOOK_URGENT_CNT,
                            VV_BOOK_CNT : jsonData.VV_BOOK_CNT, SHOW_ID : jsonData.SHOW_ID,
                            EMP_ID: jsonData.EMP_ID, CORP_ID: jsonData.CORP_ID,
                            RES_YN : jsonData.RES_YN, WORK_DATE : jsonData.WORK_DATE,
                            CORP_NAME : jsonData.CORP_NAME, SHOW_REQ_TYPE : jsonData.SHOW_REQ_TYPE,
                            SHOW_REJ_TYPE : jsonData.SHOW_REJ_TYPE, ID_KEY_TYPE : IN_ID_KEY_TYPE,
                            ID_KEY : jsonData.SHOW_ID, BASE_TYPE : IN_BASE_TYPE,
                            volume: "3.21.15", title:jsonData.EMP_NAME+"의 알람", 
                            body:IN_CONTENT, id: 1,
                            type: common.alarm_show_res_msg_type,  isShow: 1,
                            sound:"default", MSG_ID : jsonData.MSG_ID,
                            WORK_ID : jsonData.WORK_ID, NEED_ID : jsonData.NEED_ID,
                            DAY_OF_WEEK : jsonData.DAY_OF_WEEK
                            }
                        }
                    };
                    */
                    message_body.put("MILISECONDS",Integer.parseInt(common.get_miliseconds())); 
                    message_body.put("MSG_TYPE",IN_MSG_TYPE);
                    message_body.put("BOOK_ID",common.get_json_value_int(jsonData,"BOOK_ID")); 
                    message_body.put("EMP_NAME",common.get_json_value(jsonData,"EMP_NAME"));                     
                    message_body.put("SCHEDULE_NAME",common.get_json_value(jsonData,"SCHEDULE_NAME")); 
                    message_body.put("SITE_NAME",common.get_json_value(jsonData,"SITE_NAME"));
                    message_body.put("BIZ_NAME",common.get_json_value(jsonData,"BIZ_NAME")); 
                    message_body.put("JOB_GBN_NM",common.get_json_value(jsonData,"JOB_GBN_NM"));
                    message_body.put("JOB_TYPE_NM",common.get_json_value(jsonData,"JOB_TYPE_NM")); 
                    message_body.put("FEE_AMT",common.get_json_value(jsonData,"FEE_AMT"));
                    message_body.put("WORK_PAY",common.get_json_value(jsonData,"WORK_PAY")); 
                    message_body.put("WORK_DATE",common.get_json_value(jsonData,"WORK_DATE"));
                    message_body.put("NEED_CNT",common.get_json_value_int(jsonData,"NEED_CNT")); 
                    message_body.put("BOOK_CNT",common.get_json_value_int(jsonData,"BOOK_CNT"));
                    message_body.put("MEMBER_CNT",common.get_json_value_int(jsonData,"MEMBER_CNT")); 
                    message_body.put("BOOK_URGENT_CNT",common.get_json_value_int(jsonData,"BOOK_URGENT_CNT"));
                    message_body.put("VV_BOOK_CNT",common.get_json_value_int(jsonData,"VV_BOOK_CNT")); 
                    message_body.put("SHOW_ID",common.get_json_value_int(jsonData,"SHOW_ID"));
                    message_body.put("EMP_ID",common.get_json_value_int(jsonData,"EMP_ID")); 
                    message_body.put("CORP_ID",common.get_json_value_int(jsonData,"CORP_ID"));
                    message_body.put("CORP_NAME",common.get_json_value(jsonData,"CORP_NAME")); 
                    message_body.put("SHOW_REQ_TYPE",common.get_json_value(jsonData,"SHOW_REQ_TYPE"));
                    message_body.put("SHOW_REJ_TYPE",common.get_json_value(jsonData,"SHOW_REJ_TYPE")); 
                    message_body.put("ID_KEY_TYPE",IN_ID_KEY_TYPE);
                    message_body.put("ID_KEY",common.get_json_value_int(jsonData,"SHOW_ID")); 
                    message_body.put("BASE_TYPE",IN_BASE_TYPE);
                    message_body.put("volume","3.21.15"); 
                    message_body.put("title",IN_TITLE);
                    message_body.put("body",IN_CONTENT); 
                    message_body.put("isShow",1);
                    message_body.put("sound","default"); 
                    message_body.put("MSG_ID",common.get_json_value_int(jsonData,"MSG_ID"));
                    message_body.put("WORK_ID",common.get_json_value_int(jsonData,"WORK_ID")); 
                    message_body.put("NEED_ID",common.get_json_value_int(jsonData,"NEED_ID")); 
                    message_body.put("DAY_OF_WEEK",common.get_json_value(jsonData,"DAY_OF_WEEK"));
                    /*
                    message = {                         
                    data : {
                        body:{}
                        }
                    }
                    */
                    message_data.put("body",message_body);
                    message.put("data",message_data);   

                    JSONObject web_message = new JSONObject();
                    JSONObject web_message_notification = new JSONObject();
                    JSONObject web_message_data = new JSONObject();


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
                        data: {  //you can send only notification or only data(or include both)
                            MILISECONDS : common.get_miliseconds(), MSG_TYPE:IN_MSG_TYPE,
                            BOOK_ID:jsonData.BOOK_ID, GBN:IN_GBN,
                            W_REQ_GBN : jsonData.W_REQ_GBN, EMP_NAME:jsonData.EMP_NAME,
                            SCHEDULE_NAME:jsonData.SCHEDULE_NAME, SITE_NAME:jsonData.SITE_NAME,
                            BIZ_NAME:jsonData.BIZ_NAME, JOB_GBN_NM:jsonData.JOB_GBN_NM,
                            JOB_TYPE_NM:jsonData.JOB_TYPE_NM, WORK_PAY : jsonData.WORK_PAY,
                            WORK_DATE : jsonData.WORK_DATE, NEED_CNT : jsonData.NEED_CNT,
                            BOOK_CNT : jsonData.BOOK_CNT, MEMBER_CNT : jsonData.MEMBER_CNT,
                            BOOK_URGENT_CNT : jsonData.BOOK_URGENT_CNT, VV_BOOK_CNT:jsonData.VV_BOOK_CNT,
                            SHOW_ID : jsonData.SHOW_ID, EMP_ID: jsonData.EMP_ID,
                            CORP_ID: jsonData.CORP_ID, RES_YN : jsonData.RES_YN,
                            CORP_NAME : jsonData.CORP_NAME,
                            SHOW_REQ_TYPE : jsonData.SHOW_REQ_TYPE, SHOW_REJ_TYPE : jsonData.SHOW_REJ_TYPE,
                            ID_KEY_TYPE : IN_ID_KEY_TYPE, ID_KEY : jsonData.SHOW_ID,
                            BASE_TYPE : IN_BASE_TYPE,
                            volume: "3.21.15", title:jsonData.EMP_NAME+"의 알람", 
                            body:IN_CONTENT, id: 1,
                            type: common.alarm_show_res_msg_type,  isShow: 1,
                            sound:"default", MSG_ID : jsonData.MSG_ID,
                            WORK_ID : jsonData.WORK_ID, NEED_ID : jsonData.NEED_ID,
                            FEE_AMT : jsonData.FEE_AMT, DAY_OF_WEEK : jsonData.DAY_OF_WEEK
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
                    web_message_data.put("BOOK_CNT",common.get_json_value_int(jsonData,"BOOK_CNT")); 
                    web_message_data.put("MEMBER_CNT",common.get_json_value_int(jsonData,"MEMBER_CNT")); 
                    web_message_data.put("BOOK_URGENT_CNT",common.get_json_value_int(jsonData,"BOOK_URGENT_CNT")); 
                    web_message_data.put("VV_BOOK_CNT",common.get_json_value_int(jsonData,"VV_BOOK_CNT")); 
                    web_message_data.put("SHOW_ID",common.get_json_value_int(jsonData,"SHOW_ID")); 
                    web_message_data.put("EMP_ID",common.get_json_value_int(jsonData,"EMP_ID")); 
                    web_message_data.put("SHOW_ID",common.get_json_value_int(jsonData,"SHOW_ID")); 
                    web_message_data.put("EMP_ID",common.get_json_value_int(jsonData,"EMP_ID")); 
                    web_message_data.put("CORP_ID",common.get_json_value_int(jsonData,"CORP_ID")); 
                    web_message_data.put("RES_YN",common.get_json_value(jsonData,"RES_YN")); 
                    web_message_data.put("CORP_NAME",common.get_json_value(jsonData,"CORP_NAME")); 
                    web_message_data.put("SHOW_REQ_TYPE",common.get_json_value(jsonData,"SHOW_REQ_TYPE")); 
                    web_message_data.put("SHOW_REJ_TYPE",common.get_json_value(jsonData,"SHOW_REJ_TYPE")); 
                    web_message_data.put("ID_KEY_TYPE",IN_ID_KEY_TYPE); 
                    web_message_data.put("SHOW_ID",common.get_json_value_int(jsonData,"SHOW_ID"));
                    web_message_data.put("BASE_TYPE",IN_BASE_TYPE);
                    /*
                    web_message = {                         
                        notification : {},
                        data:{}
                    }
                    */
                    web_message.put("notification",web_message_notification);
                    web_message.put("data",web_message_data); 
                    
                    
                    // 설정에 따른 알람 발송
                    if (common.get_json_value(jsonData,"RES_YN").equals("Y"))
                    {
                        strSQL = "CALL SP_M_WORK_ALARM_SELECT_1_1_92('"+common.get_json_value(jsonData,"CORP_ID")+"','"+
                                 common.get_json_value(jsonData,"WORK_ID")+"','WORK_SHOW_RES_ALARM','"+
                                 common.get_json_value(jsonData,"RES_YN")+"')";
                        common.system_log("strQuery : " + strSQL);
                        
                        JSONArray jsonAlarm = common.execute_query(strSQL);
                        if (common.get_json_array_value(jsonAlarm,"result").equals("ok"))
                        {
                            if (registration_id.equals("")==false)
                            {
                                JSONObject json_result_message = fcm.send(message);
                                result = json_result_message.toString();
                                common.log(module_name,"fcm.send result="+result+",SHOW_ID : " + common.get_json_value(jsonData,"SHOW_ID"));
                            }
                            
                            if (web_registration_id.equals("")==false)
                            {
                                JSONObject json_result_message = web_fcm.send(web_message);
                                result = json_result_message.toString();
                                common.log(module_name,"web_fcm.send result="+result+",SHOW_ID : " + common.get_json_value(jsonData,"SHOW_ID"));
                            }                            
                        }
                    }
                    else
                    {
                        strSQL = "CALL SP_M_WORK_ALARM_SELECT_1_1_92('"+common.get_json_value(jsonData,"CORP_ID")+"','"+
                                 common.get_json_value(jsonData,"WORK_ID")+"','WORK_SHOW_RES_ALARM','"+
                                 common.get_json_value(jsonData,"RES_YN")+"')";
                        common.system_log("strQuery : " + strSQL);
                        
                        JSONArray jsonAlarm = common.execute_query(strSQL);
                        if (common.get_json_array_value(jsonAlarm,"result").equals("ok"))
                        {
                            if (registration_id.equals("")==false)
                            {
                                JSONObject json_result_message = fcm.send(message);
                                result = json_result_message.toString();
                                common.log(module_name,"fcm.send result="+result+",SHOW_ID : " + common.get_json_value(jsonData,"SHOW_ID"));                                                                    
                            }

                            if (web_registration_id.equals("")==false)
                            {
                                JSONObject json_result_message = web_fcm.send(web_message);
                                result = json_result_message.toString();
                                common.log(module_name,"web_fcm.send result="+result+",SHOW_ID : " + common.get_json_value(jsonData,"SHOW_ID"));
                            }
                        }                        
                    }
                }
                catch (Exception ex)
                {
                    common.log(module_name,"SendPushAlaram error : " + ex.getMessage());
                }
            
            }
            catch (Exception ex)
            {
                common.log(module_name,"SendPushAlaram error : " + ex.getMessage());
            }
        }
        catch (Exception ex)
        {
            common.log(module_name,"SendPushAlaram Error : " + ex.getMessage());
        }
        return result;
    }

    public String SetAlarmShowSignRes(JSONObject data)
    {
        String result = "";
        JSONObject jsonData = data;
        common.log(module_name,"SetAlarmShowSignRes : " + common.get_json_value(jsonData,"SHOW_ID"));
        result = SendAlarmShowSignRes(data);
        return result;
    }

    private String SendAlarmShowSignRes(JSONObject data)
    {
        String result = "";
        try
        {
            JSONObject jsonData = data;
            
            String IN_VERISON_NAME = common.get_json_value(jsonData,"VERSION_NAME");
            String IN_CONTENT = "";		
            String IN_MSG_TYPE = "";	
            String IN_GBN = "";
            String IN_ID_KEY_TYPE = "SHOW_ID";

            String api_key ="";
            FCM fcm = null;
            String registration_id = "";
            JSONObject message = new JSONObject();
            JSONObject message_body = new JSONObject();
            JSONObject message_data = new JSONObject();

            String web_api_key = common.vendor_web_api_key;
            FCM web_fcm = new FCM(web_api_key);
            String web_registration_id = common.get_json_value(jsonData,"CORP_WEB_REG_ID");
            JSONObject web_message = new JSONObject();
            JSONObject web_message_notification = new JSONObject();
            JSONObject web_message_data = new JSONObject();

            String send_gbn = common.get_json_value(jsonData,"GBN");
            String IN_SEND_DATE_NUMBER = common.now_date_integer();
            String IN_BASE_TYPE = "BASE";
            String IN_TITLE = "";
            String IN_APP_LANG = "KR";

            IN_TITLE = common.get_json_value(jsonData,"EMP_NAME")+
                       common.encode("의 알람",IN_APP_LANG);
            IN_GBN = common.get_json_value(jsonData,"GBN");
            IN_CONTENT = common.get_json_value(jsonData,"EMP_NAME")+
                        common.encode("님이 현장에서 근무 확인 등록하였습니다. ",IN_APP_LANG)+
                        common.get_json_value(jsonData,"SCHEDULE_NAME")+" "+
                        "["+
                        common.get_json_value(jsonData,"JOB_GBN_NM")+"] "+
                        common.get_json_value(jsonData,"JOB_TYPE_NM")+" "+
                        common.get_json_value(jsonData,"WORK_DATE") + " "+
                        common.encode("필요인력 ",IN_APP_LANG)+
                        common.get_json_value(jsonData,"NEED_CNT")+
                        common.encode("명 중 ",IN_APP_LANG)+
                        common.get_json_value(jsonData,"BOOK_CNT")+
                        common.encode("명 인력 배치 예약 완료  ",IN_APP_LANG)+
                        common.encode("( 소속 : ",IN_APP_LANG)+
                        common.get_json_value(jsonData,"MEMBER_CNT")+
                        common.encode(" / 긴급 : ",IN_APP_LANG) + 
                        common.get_json_value(jsonData,"BOOK_URGENT_CNT")+" )";
            IN_MSG_TYPE = "WORK_SHOW_SIGN_RES_ALARM";
            String MSG_ID = "";
            String strSQL = "";
            try
            {
                String GBN = common.get_json_value(jsonData,"GBN");
                String ID_TYPE = "SHOW_ID";
                String ID_KEY = common.get_json_value(jsonData,"SHOW_ID");
                String ERR_YN = "N";
                String ERR_MSG = "";
                String EMP_ID = common.get_json_value(jsonData,"EMP_ID");
                String CORP_ID = common.get_json_value(jsonData,"CORP_ID");
                String SHOW_REQ_TYPE = common.get_json_value(jsonData,"SHOW_REQ_TYPE");
                String SHOW_REJ_TYPE = common.get_json_value(jsonData,"SHOW_REJ_TYPE");
                String WORK_DATE = common.get_json_value(jsonData,"WORK_DATE");
                String W_REQ_GBN = common.get_json_value(jsonData,"W_REQ_GBN");
                String R_GBN = "E-B";
                
                strSQL = "CALL SP_M_MSG_INFO_INSERT('"+GBN+"','"+ID_TYPE+"','"+ID_KEY+"'," +
                    "'"+EMP_ID+"','"+CORP_ID+"','"+SHOW_REQ_TYPE+"'," +
                    "'"+SHOW_REJ_TYPE+"','"+R_GBN+"','"+common.get_value(IN_CONTENT)+"','"+WORK_DATE+"','"+W_REQ_GBN+"')"; 
                common.log(module_name,strSQL);

                JSONArray jsonMsg = common.execute_query_commit(strSQL);
                MSG_ID = common.get_json_array_value(jsonMsg,"MSG_ID");
                if(jsonData.has("MSG_ID")){
                    jsonData.remove("MSG_ID");
                }
                jsonData.put("MSG_ID",MSG_ID);
                

                try
                {
                    // 직업소개소->구직자
                    api_key = common.vendor_api_key;
                    fcm = new FCM(api_key);

                    registration_id = common.get_json_value(jsonData,"CORP_MOBILE_REG_ID");
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
                            MILISECONDS : common.get_miliseconds(), MSG_TYPE:IN_MSG_TYPE,
                            BOOK_ID:jsonData.BOOK_ID, GBN:IN_GBN,
                            W_REQ_GBN : jsonData.W_REQ_GBN, EMP_NAME:jsonData.EMP_NAME,
                            SCHEDULE_NAME:jsonData.SCHEDULE_NAME, SITE_NAME:jsonData.SITE_NAME,
                            BIZ_NAME:jsonData.BIZ_NAME, JOB_GBN_NM:jsonData.JOB_GBN_NM,
                            JOB_TYPE_NM:jsonData.JOB_TYPE_NM, WORK_PAY : jsonData.WORK_PAY,
                            WORK_DATE : jsonData.WORK_DATE, NEED_CNT : jsonData.NEED_CNT,
                            BOOK_CNT : jsonData.BOOK_CNT, MEMBER_CNT : jsonData.MEMBER_CNT,
                            BOOK_URGENT_CNT : jsonData.BOOK_URGENT_CNT, VV_BOOK_CNT : jsonData.VV_BOOK_CNT,
                            SHOW_ID : jsonData.SHOW_ID, EMP_ID: jsonData.EMP_ID,
                            CORP_ID: jsonData.CORP_ID, RES_YN : jsonData.RES_YN,
                            CORP_NAME : jsonData.CORP_NAME,
                            SHOW_REQ_TYPE : jsonData.SHOW_REQ_TYPE, SHOW_REJ_TYPE : jsonData.SHOW_REJ_TYPE,
                            ID_KEY_TYPE : IN_ID_KEY_TYPE, ID_KEY : jsonData.SHOW_ID,
                            BASE_TYPE : IN_BASE_TYPE,
                            volume: "3.21.15", title:jsonData.EMP_NAME+"의 알람", 
                            body:IN_CONTENT, id: 1,
                            type: common.alarm_show_res_msg_type,  isShow: 1,
                            sound:"default", MSG_ID : jsonData.MSG_ID,
                            WORK_ID : jsonData.WORK_ID, NEED_ID : jsonData.NEED_ID
                            }
                        }
                    };
                    */
                    message_body.put("MILISECONDS",Integer.parseInt(common.get_miliseconds())); 
                    message_body.put("MSG_TYPE",IN_MSG_TYPE);
                    message_body.put("BOOK_ID",common.get_json_value_int(jsonData,"BOOK_ID")); 
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
                    message_body.put("VV_BOOK_CNT",common.get_json_value_int(jsonData,"VV_BOOK_CNT"));
                    message_body.put("SHOW_ID",common.get_json_value_int(jsonData,"SHOW_ID")); 
                    message_body.put("EMP_ID",common.get_json_value_int(jsonData,"EMP_ID"));
                    message_body.put("CORP_ID",common.get_json_value_int(jsonData,"CORP_ID")); 
                    message_body.put("RES_YN",common.get_json_value(jsonData,"RES_YN"));
                    message_body.put("CORP_ID",common.get_json_value(jsonData,"CORP_ID")); 
                    message_body.put("RES_YN",common.get_json_value(jsonData,"RES_YN"));
                    message_body.put("CORP_NAME",common.get_json_value(jsonData,"CORP_NAME"));
                    message_body.put("SHOW_REQ_TYPE",common.get_json_value(jsonData,"SHOW_REQ_TYPE")); 
                    message_body.put("SHOW_REJ_TYPE",common.get_json_value(jsonData,"SHOW_REJ_TYPE"));
                    message_body.put("ID_KEY_TYPE",IN_ID_KEY_TYPE); 
                    message_body.put("ID_KEY",common.get_json_value_int(jsonData,"SHOW_ID"));
                    message_body.put("BASE_TYPE",IN_BASE_TYPE);
                    message_body.put("volume","3.21.15"); 
                    message_body.put("title",IN_TITLE);
                    message_body.put("body",IN_CONTENT); 
                    message_body.put("id",1);
                    message_body.put("type",common.alarm_show_res_msg_type); 
                    message_body.put("isShow",1);
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
                    web_message = { //this may vary according to the message type (single recipient, multicast, topic, et cetera)                        
                        notification: {                            		
                        },
                        data: {  //you can send only notification or only data(or include both)
                            MILISECONDS : common.get_miliseconds(), MSG_TYPE:IN_MSG_TYPE,
                            BOOK_ID:jsonData.BOOK_ID, GBN:IN_GBN,
                            W_REQ_GBN : jsonData.W_REQ_GBN, EMP_NAME:jsonData.EMP_NAME,
                            SCHEDULE_NAME:jsonData.SCHEDULE_NAME, SITE_NAME:jsonData.SITE_NAME,
                            BIZ_NAME:jsonData.BIZ_NAME, JOB_GBN_NM:jsonData.JOB_GBN_NM, 
                            JOB_TYPE_NM:jsonData.JOB_TYPE_NM, WORK_PAY : jsonData.WORK_PAY,
                            WORK_DATE : jsonData.WORK_DATE, NEED_CNT : jsonData.NEED_CNT,
                            BOOK_CNT : jsonData.BOOK_CNT, MEMBER_CNT : jsonData.MEMBER_CNT,
                            BOOK_URGENT_CNT : jsonData.BOOK_URGENT_CNT, VV_BOOK_CNT : jsonData.VV_BOOK_CNT,
                            SHOW_ID : jsonData.SHOW_ID, EMP_ID: jsonData.EMP_ID,
                            CORP_ID: jsonData.CORP_ID, RES_YN : jsonData.RES_YN,
                            CORP_NAME : jsonData.CORP_NAME,
                            SHOW_REQ_TYPE : jsonData.SHOW_REQ_TYPE, SHOW_REJ_TYPE : jsonData.SHOW_REJ_TYPE,
                            ID_KEY_TYPE : IN_ID_KEY_TYPE, ID_KEY : jsonData.SHOW_ID,
                            BASE_TYPE : IN_BASE_TYPE,
                            volume: "3.21.15", title:jsonData.EMP_NAME+"의 알람", 
                            body:IN_CONTENT, id: 1,
                            type: common.alarm_show_res_msg_type,  isShow: 1,
                            sound:"default", MSG_ID : jsonData.MSG_ID,
                            WORK_ID : jsonData.WORK_ID, NEED_ID : jsonData.NEED_ID
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
                    web_message_data.put("JOB_TYPE_NM",common.get_json_value(jsonData,"JOB_TYPE_NM")); 
                    web_message_data.put("WORK_PAY",common.get_json_value(jsonData,"WORK_PAY"));
                    web_message_data.put("WORK_DATE",common.get_json_value(jsonData,"WORK_DATE")); 
                    web_message_data.put("NEED_CNT",common.get_json_value_int(jsonData,"NEED_CNT"));
                    web_message_data.put("BOOK_CNT",common.get_json_value_int(jsonData,"BOOK_CNT")); 
                    web_message_data.put("MEMBER_CNT",common.get_json_value_int(jsonData,"MEMBER_CNT"));
                    web_message_data.put("BOOK_URGENT_CNT",common.get_json_value_int(jsonData,"BOOK_URGENT_CNT")); 
                    web_message_data.put("VV_BOOK_CNT",common.get_json_value_int(jsonData,"VV_BOOK_CNT"));
                    web_message_data.put("SHOW_ID",common.get_json_value_int(jsonData,"SHOW_ID")); 
                    web_message_data.put("EMP_ID",common.get_json_value_int(jsonData,"EMP_ID"));
                    web_message_data.put("CORP_ID",common.get_json_value_int(jsonData,"CORP_ID")); 
                    web_message_data.put("RES_YN",common.get_json_value(jsonData,"RES_YN"));
                    web_message_data.put("CORP_NAME",common.get_json_value(jsonData,"CORP_NAME"));
                    web_message_data.put("SHOW_REQ_TYPE",common.get_json_value(jsonData,"SHOW_REQ_TYPE")); 
                    web_message_data.put("SHOW_REJ_TYPE",common.get_json_value(jsonData,"SHOW_REJ_TYPE"));
                    web_message_data.put("ID_KEY_TYPE",IN_ID_KEY_TYPE); 
                    web_message_data.put("ID_KEY",common.get_json_value_int(jsonData,"SHOW_ID"));
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
                    /*
                    web_message = {                         
                        notification : {},
                        data:{}
                    }
                    */
                    web_message.put("notification",web_message_notification);
                    web_message.put("data",web_message_data); 
                    

                    // 설정에 따른 알람 발송
                    if (common.get_json_value(jsonData,"RES_YN").equals("Y"))
                    {
                        strSQL = "CALL SP_M_CORP_CONF_ALARM_INFO_1_1_92('"+common.get_json_value_int(jsonData,"CORP_ID")+"')";
                        JSONArray jsonConf = common.execute_query(strSQL);
                        if(common.get_json_array_value(jsonConf,"SHOW_AVL_YN").equals("Y")){
                            if (registration_id.equals("")==false)
                            {
                                JSONObject json_result_message = fcm.send(message);
                                result = json_result_message.toString();
                                common.log(module_name,"fcm.send result="+result+",SHOW_ID : " + common.get_json_value(jsonData,"SHOW_ID"));
                            }

                            if (web_registration_id.equals("")==false)
                            {
                                JSONObject json_result_message = web_fcm.send(web_message);
                                result = json_result_message.toString();
                                common.log(module_name,"web_fcm.send result="+result+",SHOW_ID : " + common.get_json_value(jsonData,"SHOW_ID"));
                            }
                        }                        
                    }
                    else
                    {
                        strSQL = "CALL SP_M_CORP_CONF_ALARM_INFO_1_1_92('"+common.get_json_value_int(jsonData,"CORP_ID")+"')";
                        JSONArray jsonConf = common.execute_query(strSQL);
                        if(common.get_json_array_value(jsonConf,"SHOW_REJ_YN").equals("Y")){
                            if (registration_id.equals("")==false)
                            {
                                JSONObject json_result_message = fcm.send(message);
                                result = json_result_message.toString();
                                common.log(module_name,"fcm.send result="+result+",SHOW_ID : " + common.get_json_value(jsonData,"SHOW_ID"));
                            }
                            
                            if (web_registration_id.equals("")==false)
                            {
                                JSONObject json_result_message = web_fcm.send(web_message);
                                result = json_result_message.toString();
                                common.log(module_name,"web_fcm.send result="+result+",SHOW_ID : " + common.get_json_value(jsonData,"SHOW_ID"));
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
        
        return result;
    }

    public String SetAlarmWorkVS(JSONObject data)
    {
        String result = "";
        JSONObject jsonData = data;
        common.log(module_name,"SetAlarmWorkVS : " + common.get_json_value(jsonData,"BOOK_ID"));
        result = SendAlarmWorkVS(data);
        return result;
    }   

    private String SendAlarmWorkVS(JSONObject data)
    {
        String result = "";
        try
        {
            JSONObject jsonData = data;
            String IN_VERISON_NAME = common.get_json_value(jsonData,"VERSION_NAME");
            String IN_CONTENT = "";
            
            String IN_GBN = "";
            String IN_BASE_TYPE = "EMC";
            String IN_SEND_DATE_NUMBER = common.now_date_integer();

            FCM fcm = null;
            String api_key ="";
            String registration_id = "";		
            JSONObject message = new JSONObject();
            JSONObject message_body = new JSONObject();
            JSONObject message_data = new JSONObject();

            String send_gbn = common.get_json_value(jsonData,"GBN");
            String strNowTimeString = common.now_time_string();
            String IN_MSG_TYPE = "";
            String IN_ID_KEY_TYPE = "BOOK_ID";
            
            FCM web_fcm = null;
            String web_api_key = "";
            String web_registration_id = common.get_json_value(jsonData,"CORP_WEB_REG_ID");
            JSONObject web_message = new JSONObject();
            JSONObject web_message_notification = new JSONObject();
            JSONObject web_message_data = new JSONObject();

            String IN_TITLE = "";
            String IN_APP_LANG = "KR";

            if (common.get_json_value(jsonData,"GBN").equals("SLT"))
            {
                IN_GBN = common.get_json_value(jsonData,"GBN");
                IN_TITLE = common.get_json_value(jsonData,"EMP_NAME")+
                           common.encode("의 알람",IN_APP_LANG);
                IN_CONTENT = common.encode("[베테랑서비스]",IN_APP_LANG)+
                            common.get_json_value(jsonData,"EMP_NAME")+
                            common.encode(" 구직자가 ",IN_APP_LANG)+
                            common.get_json_value(jsonData,"WORK_DATE")+
                            common.encode("에 ",IN_APP_LANG) +
                            common.get_json_value(jsonData,"SCHEDULE_NAME")+
                            common.encode(" 일자리로 ",IN_APP_LANG)+
                            common.encode("베테랑 서비스에 지원하였습니다.",IN_APP_LANG);
                api_key = common.vendor_api_key;
            }
            else if (common.get_json_value(jsonData,"GBN").equals("REQ"))
            {
                IN_GBN = common.get_json_value(jsonData,"GBN");
                IN_TITLE = common.get_json_value(jsonData,"CORP_NAME") + 
                           common.encode("의 알람",IN_APP_LANG);
                IN_CONTENT = common.get_json_value(jsonData,"CORP_NAME")+
                            common.encode("에서 ",IN_APP_LANG)+
                            common.get_json_value(jsonData,"WORK_DATE")+
                            common.encode("에 근무요청하였습니다.",IN_APP_LANG);
                api_key = common.user_api_key;
            }
            else if (common.get_json_value(jsonData,"GBN").equals("CAN"))
            {
                IN_GBN = "CAN";
                IN_TITLE = common.get_json_value(jsonData,"CORP_NAME") + 
                           common.encode("의 알람",IN_APP_LANG);
                IN_CONTENT = common.get_json_value(jsonData,"WORK_DATE")+" " +
                            common.get_json_value(jsonData,"SCHEDULE_NAME")+" "+
                            "["+
                            common.get_json_value(jsonData,"JOB_GBN_NM")+"] "+
                            common.get_json_value(jsonData,"JOB_TYPE_NM")+" "+
                            common.get_json_value(jsonData,"WORK_PAY")+
                            common.encode("원 ",IN_APP_LANG)+
                            " "+
                            common.encode("위의 일자리가 취소되었습니다. ",IN_APP_LANG)+
                            common.encode("다시 예약해주시기 바랍니다.",IN_APP_LANG);
                api_key = common.user_api_key;
            }

            String MSG_ID = "";
            
            
            try
            {
                String GBN = common.get_json_value(jsonData,"GBN");
                String ID_TYPE = "BOOK_ID";
                String ID_KEY = common.get_json_value(jsonData,"BOOK_ID");
                String ERR_YN = "N";
                String ERR_MSG = "";
                String EMP_ID = common.get_json_value(jsonData,"EMP_ID");
                String CORP_ID = common.get_json_value(jsonData,"CORP_ID");
                String EMC_REQ_TYPE = common.get_json_value(jsonData,"EMC_REQ_TYPE");
                String EMC_REJ_TYPE = common.get_json_value(jsonData,"EMC_REJ_TYPE");
                String WORK_DATE = common.get_json_value(jsonData,"S_WORK_DATE");
                String W_REQ_GBN = common.get_json_value(jsonData,"W_REQ_GBN");
                String R_GBN = "E-B";
                
                String strSQL = "CALL SP_M_MSG_INFO_INSERT('"+GBN+"','"+ID_TYPE+"','"+ID_KEY+"'," +
                    "'"+EMP_ID+"','"+CORP_ID+"','"+EMC_REQ_TYPE+"'," +
                    "'"+EMC_REJ_TYPE+"','"+R_GBN+"','"+common.get_value(IN_CONTENT)+"','"+WORK_DATE+"','"+W_REQ_GBN+"')"; 
                common.log(module_name,strSQL);

                JSONArray jsonMsg = common.execute_query_commit(strSQL);
                MSG_ID = common.get_json_array_value(jsonMsg,"MSG_ID");
                if(jsonData.has("MSG_ID")){
                    jsonData.remove("MSG_ID");
                }
                jsonData.put("MSG_ID", MSG_ID);
                common.log(module_name,"function=SendPushAlaram,methodwork_msg_insert,result=ok");
                
                try
                {			

                    fcm = new FCM(api_key);

                    if(common.get_json_value(jsonData,"GBN").equals("SLT"))
                    {
                        registration_id = common.get_json_value(jsonData,"CORP_MOBILE_REG_ID");

                        IN_MSG_TYPE = "EMC_WORK_ALARM";
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
                            MILISECONDS : common.get_miliseconds(),title:jsonData.EMP_NAME+"의 알람", 
                            body:IN_CONTENT,                        id: 1,
                            type: common.alarm_work_emc_msg_type,   isShow: 1,
                            sound:"default",                        MSG_TYPE:IN_MSG_TYPE,
                            GBN:jsonData.GBN,                       W_REQ_GBN : jsonData.W_REQ_GBN,
                            CORP_ID:jsonData.CORP_ID,               EMC_ID:jsonData.EMC_ID,     
                            BOOK_ID:jsonData.BOOK_ID,               EMP_ID: jsonData.EMP_ID,
                            EMP_NAME : jsonData.EMP_NAME,           S_WORK_DATE : jsonData.S_WORK_DATE,
                            WORK_DATE : jsonData.WORK_DATE,         SCHEDULE_NAME:jsonData.SCHEDULE_NAME,
                            SITE_NAME : jsonData.SITE_NAME,         BIZ_NAME : jsonData.BIZ_NAME,
                            CORP_NAME : jsonData.CORP_NAME,         JOB_GBN_NM : jsonData.JOB_GBN_NM,
                            JOB_TYPE_NM : jsonData.JOB_TYPE_NM,     FEE_AMT : jsonData.FEE_AMT,
                            WORK_PAY : jsonData.WORK_PAY,           
                            NEED_CNT : jsonData.NEED_CNT,           BOOK_CNT : jsonData.BOOK_CNT,
                            MEMBER_CNT : jsonData.MEMBER_CNT,       BOOK_URGENT_CNT : jsonData.BOOK_URGENT_CNT,
                            VV_BOOK_CNT : jsonData.VV_BOOK_CNT,     VS_SUBMIT_CNT : jsonData.VS_SUBMIT_CNT,
                            SEND_TIME : strNowTimeString,           WORK_REQ_TYPE : jsonData.EMC_REQ_TYPE,
                            WORK_REJ_TYPE : jsonData.EMC_REJ_TYPE,  RES_YN : jsonData.RES_YN,
                            BASE_TYPE : IN_BASE_TYPE,               ID_KEY_TYPE : IN_ID_KEY_TYPE,
                            ID_KEY : jsonData.BOOK_ID,              DATE_GBN : jsonData.DATE_GBN,
                            DATE_GBN_NM : jsonData.DATE_GBN_NM,     AS_WORK_DATE : jsonData.AS_WORK_DATE,
                            TO_WORK_DATE : jsonData.TO_WORK_DATE,   ADDR1:jsonData.ADDR1,
                            DISTANCE:jsonData.DISTANCE,             JOB_CONTENT:jsonData.JOB_CONTENT,
                            MSG_ID : jsonData.MSG_ID,               WORK_ID : jsonData.WORK_ID,
                            NEED_ID : jsonData.NEED_ID,             DAY_OF_WEEK : jsonData.DAY_OF_WEEK
                            }
                            }
                        };                    
                        */
                        message_body.put("MILISECONDS",Integer.parseInt(common.get_miliseconds())); 
                        message_body.put("title",IN_TITLE);
                        message_body.put("body",IN_CONTENT); 
                        message_body.put("id",1);
                        message_body.put("type",common.alarm_work_emc_msg_type); 
                        message_body.put("isShow",1);
                        message_body.put("sound","default"); 
                        message_body.put("MSG_TYPE",IN_MSG_TYPE);
                        message_body.put("GBN",common.get_json_value(jsonData,"GBN")); 
                        message_body.put("W_REQ_GBN",common.get_json_value(jsonData,"W_REQ_GBN"));
                        message_body.put("CORP_ID",common.get_json_value_int(jsonData,"CORP_ID")); 
                        message_body.put("EMC_ID",common.get_json_value_int(jsonData,"EMC_ID"));
                        message_body.put("BOOK_ID",common.get_json_value_int(jsonData,"BOOK_ID")); 
                        message_body.put("EMP_ID",common.get_json_value_int(jsonData,"EMP_ID"));
                        message_body.put("EMP_NAME",common.get_json_value(jsonData,"EMP_NAME")); 
                        message_body.put("S_WORK_DATE",common.get_json_value(jsonData,"S_WORK_DATE"));
                        message_body.put("WORK_DATE",common.get_json_value(jsonData,"WORK_DATE")); 
                        message_body.put("SCHEDULE_NAME",common.get_json_value(jsonData,"SCHEDULE_NAME"));
                        message_body.put("SITE_NAME",common.get_json_value(jsonData,"SITE_NAME")); 
                        message_body.put("BIZ_NAME",common.get_json_value(jsonData,"BIZ_NAME"));
                        message_body.put("CORP_NAME",common.get_json_value(jsonData,"CORP_NAME")); 
                        message_body.put("JOB_GBN_NM",common.get_json_value(jsonData,"JOB_GBN_NM")); 
                        message_body.put("WORK_PAY",common.get_json_value(jsonData,"WORK_PAY")); 
                        message_body.put("NEED_CNT",common.get_json_value_int(jsonData,"NEED_CNT")); 
                        message_body.put("BOOK_CNT",common.get_json_value_int(jsonData,"BOOK_CNT")); 
                        message_body.put("MEMBER_CNT",common.get_json_value_int(jsonData,"MEMBER_CNT")); 
                        message_body.put("BOOK_URGENT_CNT",common.get_json_value_int(jsonData,"BOOK_URGENT_CNT")); 
                        message_body.put("VV_BOOK_CNT",common.get_json_value_int(jsonData,"VV_BOOK_CNT")); 
                        message_body.put("VS_SUBMIT_CNT",common.get_json_value_int(jsonData,"VS_SUBMIT_CNT")); 
                        message_body.put("SEND_TIME",strNowTimeString); 
                        message_body.put("WORK_REQ_TYPE",common.get_json_value(jsonData,"EMC_REQ_TYPE")); 
                        message_body.put("WORK_REJ_TYPE",common.get_json_value(jsonData,"EMC_REJ_TYPE")); 
                        message_body.put("WORK_REQ_TYPE",common.get_json_value(jsonData,"EMC_REQ_TYPE")); 
                        message_body.put("BASE_TYPE",IN_BASE_TYPE); 
                        message_body.put("ID_KEY_TYPE",IN_ID_KEY_TYPE); 
                        message_body.put("ID_KEY",common.get_json_value_int(jsonData,"BOOK_ID")); 
                        message_body.put("DATE_GBN",common.get_json_value(jsonData,"DATE_GBN")); 
                        message_body.put("DATE_GBN_NM",common.get_json_value(jsonData,"DATE_GBN_NM")); 
                        message_body.put("AS_WORK_DATE",common.get_json_value(jsonData,"AS_WORK_DATE")); 
                        message_body.put("TO_WORK_DATE",common.get_json_value(jsonData,"TO_WORK_DATE")); 
                        message_body.put("ADDR1",common.get_json_value(jsonData,"ADDR1")); 
                        message_body.put("DISTANCE",common.get_json_value_double(jsonData,"DISTANCE")); 
                        message_body.put("JOB_CONTENT",common.get_json_value(jsonData,"JOB_CONTENT")); 
                        message_body.put("MSG_ID",common.get_json_value_int(jsonData,"MSG_ID")); 
                        message_body.put("WORK_ID",common.get_json_value_int(jsonData,"WORK_ID")); 
                        message_body.put("NEED_ID",common.get_json_value_int(jsonData,"NEED_ID")); 
                        message_body.put("DAY_OF_WEEK",common.get_json_value(jsonData,"DAY_OF_WEEK")); 
                        /*
                        message = {                         
                        data : {
                            body:{}
                            }
                        }
                        */
                        message_data.put("body",message_body);
                        message.put("data",message_data); 


                        web_api_key = common.vendor_web_api_key;			
                        web_fcm = new FCM(web_api_key);
                        web_registration_id = common.get_json_value(jsonData,"CORP_WEB_REG_ID");
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
                        web_message = { //this may vary according to the message type (single recipient, multicast, topic, et cetera)                        
                            notification: {                            				
                            },
                            data: {  //you can send only notification or only data(or include both)
                                MILISECONDS : common.get_miliseconds(),     title:jsonData.EMP_NAME+"의 알람", 
                                body:IN_CONTENT,                            id: 1,
                                type: common.alarm_work_emc_msg_type,       isShow: 1,
                                sound:"default",                            MSG_TYPE:IN_MSG_TYPE,
                                GBN:jsonData.GBN,                           W_REQ_GBN : jsonData.W_REQ_GBN,
                                CORP_ID:jsonData.CORP_ID,                   EMC_ID:jsonData.EMC_ID,
                                BOOK_ID:jsonData.BOOK_ID,                   EMP_ID: jsonData.EMP_ID,
                                EMP_NAME : jsonData.EMP_NAME,               S_WORK_DATE : jsonData.S_WORK_DATE,
                                WORK_DATE : jsonData.WORK_DATE,             SCHEDULE_NAME:jsonData.SCHEDULE_NAME,
                                SITE_NAME : jsonData.SITE_NAME,             BIZ_NAME : jsonData.BIZ_NAME,
                                CORP_NAME : jsonData.CORP_NAME,             JOB_GBN_NM : jsonData.JOB_GBN_NM,
                                JOB_TYPE_NM : jsonData.JOB_TYPE_NM,         WORK_PAY : jsonData.WORK_PAY,
                                NEED_CNT : jsonData.NEED_CNT,
                                BOOK_CNT : jsonData.BOOK_CNT,               MEMBER_CNT : jsonData.MEMBER_CNT,
                                BOOK_URGENT_CNT : jsonData.BOOK_URGENT_CNT, VV_BOOK_CNT : jsonData.VV_BOOK_CNT,
                                VS_SUBMIT_CNT : jsonData.VS_SUBMIT_CNT,     SEND_TIME : strNowTimeString,
                                WORK_REQ_TYPE : jsonData.EMC_REQ_TYPE,      WORK_REJ_TYPE : jsonData.EMC_REJ_TYPE,
                                RES_YN : jsonData.RES_YN,                   BASE_TYPE : IN_BASE_TYPE,
                                ID_KEY_TYPE : IN_ID_KEY_TYPE,               ID_KEY : jsonData.BOOK_ID,
                                DATE_GBN : jsonData.DATE_GBN,               DATE_GBN_NM : jsonData.DATE_GBN_NM,
                                AS_WORK_DATE : jsonData.AS_WORK_DATE,       TO_WORK_DATE : jsonData.TO_WORK_DATE,
                                ADDR1:jsonData.ADDR1,                       DISTANCE:jsonData.DISTANCE,
                                JOB_CONTENT : jsonData.JOB_CONTENT,         MSG_ID : jsonData.MSG_ID,
                                WORK_ID : jsonData.WORK_ID,                 NEED_ID : jsonData.NEED_ID,
                                DAY_OF_WEEK : jsonData.DAY_OF_WEEK,         FEE_AMT : jsonData.FEE_AMT
                            }
                        };
                        */
                        web_message_data.put("MILISECONDS",Integer.parseInt(common.get_miliseconds())); 
                        web_message_data.put("title",IN_TITLE);
                        web_message_data.put("body",IN_CONTENT); 
                        web_message_data.put("id",1);
                        web_message_data.put("type",common.alarm_work_emc_msg_type); 
                        web_message_data.put("isShow",1); 
                        web_message_data.put("sound","default"); 
                        web_message_data.put("MSG_TYPE",IN_MSG_TYPE);
                        web_message_data.put("GBN",common.get_json_value(jsonData,"GBN")); 
                        web_message_data.put("W_REQ_GBN",common.get_json_value(jsonData,"W_REQ_GBN"));
                        web_message_data.put("CORP_ID",common.get_json_value_int(jsonData,"CORP_ID")); 
                        web_message_data.put("EMC_ID",common.get_json_value_int(jsonData,"EMC_ID"));
                        web_message_data.put("BOOK_ID",common.get_json_value_int(jsonData,"BOOK_ID")); 
                        web_message_data.put("EMP_ID",common.get_json_value_int(jsonData,"EMP_ID"));
                        web_message_data.put("EMP_NAME",common.get_json_value(jsonData,"EMP_NAME")); 
                        web_message_data.put("S_WORK_DATE",common.get_json_value(jsonData,"S_WORK_DATE"));
                        web_message_data.put("WORK_DATE",common.get_json_value(jsonData,"WORK_DATE")); 
                        web_message_data.put("SCHEDULE_NAME",common.get_json_value(jsonData,"SCHEDULE_NAME"));
                        web_message_data.put("SITE_NAME",common.get_json_value(jsonData,"SITE_NAME")); 
                        web_message_data.put("BIZ_NAME",common.get_json_value(jsonData,"BIZ_NAME"));
                        web_message_data.put("CORP_NAME",common.get_json_value(jsonData,"CORP_NAME")); 
                        web_message_data.put("JOB_GBN_NM",common.get_json_value(jsonData,"JOB_GBN_NM"));
                        web_message_data.put("JOB_TYPE_NM",common.get_json_value(jsonData,"JOB_TYPE_NM")); 
                        web_message_data.put("WORK_PAY",common.get_json_value(jsonData,"WORK_PAY"));
                        web_message_data.put("NEED_CNT",common.get_json_value_int(jsonData,"NEED_CNT")); 
                        web_message_data.put("BOOK_CNT",common.get_json_value_int(jsonData,"BOOK_CNT")); 
                        web_message_data.put("MEMBER_CNT",common.get_json_value_int(jsonData,"MEMBER_CNT")); 
                        web_message_data.put("BOOK_URGENT_CNT",common.get_json_value_int(jsonData,"BOOK_URGENT_CNT")); 
                        web_message_data.put("VV_BOOK_CNT",common.get_json_value_int(jsonData,"VV_BOOK_CNT")); 
                        web_message_data.put("VS_SUBMIT_CNT",common.get_json_value_int(jsonData,"VS_SUBMIT_CNT")); 
                        web_message_data.put("SEND_TIME",strNowTimeString); 
                        web_message_data.put("WORK_REQ_TYPE",common.get_json_value(jsonData,"EMC_REQ_TYPE")); 
                        web_message_data.put("WORK_REJ_TYPE",common.get_json_value(jsonData,"EMC_REJ_TYPE"));
                        web_message_data.put("RES_YN",common.get_json_value(jsonData,"RES_YN")); 
                        web_message_data.put("BASE_TYPE",IN_BASE_TYPE);
                        web_message_data.put("DATE_GBN",common.get_json_value(jsonData,"DATE_GBN")); 
                        web_message_data.put("DATE_GBN_NM",common.get_json_value(jsonData,"DATE_GBN_NM"));
                        web_message_data.put("AS_WORK_DATE",common.get_json_value(jsonData,"AS_WORK_DATE")); 
                        web_message_data.put("TO_WORK_DATE",common.get_json_value(jsonData,"TO_WORK_DATE"));
                        web_message_data.put("ADDR1",common.get_json_value(jsonData,"ADDR1")); 
                        web_message_data.put("DISTANCE",common.get_json_value_double(jsonData,"DISTANCE"));
                        web_message_data.put("JOB_CONTENT",common.get_json_value(jsonData,"JOB_CONTENT")); 
                        web_message_data.put("MSG_ID",common.get_json_value_int(jsonData,"MSG_ID"));
                        web_message_data.put("WORK_ID",common.get_json_value_int(jsonData,"WORK_ID")); 
                        web_message_data.put("NEED_ID",common.get_json_value_int(jsonData,"NEED_ID"));
                        web_message_data.put("DAY_OF_WEEK",common.get_json_value(jsonData,"DAY_OF_WEEK")); 
                        web_message_data.put("FEE_AMT",common.get_json_value(jsonData,"FEE_AMT"));
                        /*
                        message = {                         
                            notification : {},
                            data:{}
                        }
                        */
                        web_message.put("notification",web_message_notification);
                        web_message.put("data",web_message_data); 
                    }
                    else if (common.get_json_value(jsonData,"GBN").equals("CAN"))
                    {
                        registration_id = common.get_json_value(jsonData,"EMP_MOBILE_REG_ID");
                        IN_MSG_TYPE = "WORK_ALARM";

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
                                MILISECONDS : common.get_miliseconds(), title:jsonData.CORP_NAME+"의 알람", 
                                body:IN_CONTENT,                        id: 1,
                                type: common.alarm_work_emc_msg_type,   isShow: 1,
                                sound:"default",                        MSG_TYPE:IN_MSG_TYPE,
                                GBN:jsonData.GBN,                       W_REQ_GBN : jsonData.W_REQ_GBN,
                                BOOK_ID:jsonData.BOOK_ID,               EMP_ID: jsonData.EMP_ID,
                                WORK_DATE : jsonData.WORK_DATE,         S_WORK_DATE : jsonData.S_WORK_DATE,
                                SCHEDULE_NAME:jsonData.SCHEDULE_NAME,   SITE_NAME : jsonData.SITE_NAME,
                                BIZ_NAME : jsonData.BIZ_NAME,           JOB_GBN_NM : jsonData.JOB_GBN_NM,
                                JOB_TYPE_NM : jsonData.JOB_TYPE_NM,     WORK_PAY : jsonData.WORK_PAY,
                                NEED_CNT : jsonData.NEED_CNT,
                                BOOK_CNT : jsonData.BOOK_CNT,           MEMBER_CNT : jsonData.MEMBER_CNT,
                                BOOK_URGENT_CNT : jsonData.BOOK_URGENT_CNT, VV_BOOK_CNT : jsonData.VV_BOOK_CNT,
                                VS_SUBMIT_CNT : jsonData.VS_SUBMIT_CNT, SEND_TIME : strNowTimeString,
                                WORK_PAY : jsonData.WORK_PAY,           EMP_NAME : jsonData.EMP_NAME,
                                CORP_NAME : jsonData.CORP_NAME,         BASE_TYPE : IN_BASE_TYPE,
                                ID_KEY_TYPE : IN_ID_KEY_TYPE,           ID_KEY : jsonData.BOOK_ID,
                                RES_YN : "N",                           WORK_REQ_TYPE : jsonData.EMC_REQ_TYPE,		
                                WORK_REJ_TYPE : jsonData.EMC_REJ_TYPE,  
                                DATE_GBN : jsonData.DATE_GBN,           DATE_GBN_NM : jsonData.DATE_GBN_NM,
                                AS_WORK_DATE : jsonData.AS_WORK_DATE,   TO_WORK_DATE : jsonData.TO_WORK_DATE,
                                ADDR1:jsonData.ADDR1,                   DISTANCE:jsonData.DISTANCE,
                                JOB_CONTENT:jsonData.JOB_CONTENT,       MSG_ID : jsonData.MSG_ID,
                                WORK_ID : jsonData.WORK_ID,             NEED_ID : jsonData.NEED_ID,
                                FEE_AMT : jsonData.FEE_AMT
                                }						
                            }
                        };
                        */
                        message_body.put("MILISECONDS",Integer.parseInt(common.get_miliseconds()));
                        message_body.put("title",IN_TITLE);
                        message_body.put("body",IN_CONTENT);
                        message_body.put("id",1);
                        message_body.put("type",common.alarm_work_emc_msg_type); 
                        message_body.put("isShow",1);
                        message_body.put("sound","default");
                        message_body.put("MSG_TYPE",IN_MSG_TYPE);
                        message_body.put("GBN",common.get_json_value(jsonData,"GBN"));
                        message_body.put("W_REQ_GBN",common.get_json_value(jsonData,"W_REQ_GBN"));
                        message_body.put("BOOK_ID",common.get_json_value_int(jsonData,"BOOK_ID"));
                        message_body.put("EMP_ID",common.get_json_value(jsonData,"W_REQ_GBN"));
                        message_body.put("WORK_DATE",common.get_json_value(jsonData,"WORK_DATE"));
                        message_body.put("S_WORK_DATE",common.get_json_value(jsonData,"S_WORK_DATE"));
                        message_body.put("SCHEDULE_NAME",common.get_json_value(jsonData,"SCHEDULE_NAME"));
                        message_body.put("SITE_NAME",common.get_json_value(jsonData,"SITE_NAME"));
                        message_body.put("BIZ_NAME",common.get_json_value(jsonData,"BIZ_NAME"));
                        message_body.put("JOB_GBN_NM",common.get_json_value(jsonData,"JOB_GBN_NM"));
                        message_body.put("JOB_TYPE_NM",common.get_json_value(jsonData,"JOB_TYPE_NM"));
                        message_body.put("WORK_PAY",common.get_json_value(jsonData,"WORK_PAY"));
                        message_body.put("NEED_CNT",common.get_json_value_int(jsonData,"NEED_CNT"));
                        message_body.put("BOOK_CNT",common.get_json_value_int(jsonData,"BOOK_CNT"));
                        message_body.put("MEMBER_CNT",common.get_json_value_int(jsonData,"MEMBER_CNT"));
                        message_body.put("BOOK_URGENT_CNT",common.get_json_value_int(jsonData,"BOOK_URGENT_CNT"));
                        message_body.put("VV_BOOK_CNT",common.get_json_value_int(jsonData,"VV_BOOK_CNT"));
                        message_body.put("VS_SUBMIT_CNT",common.get_json_value_int(jsonData,"VS_SUBMIT_CNT"));
                        message_body.put("SEND_TIME",strNowTimeString);
                        message_body.put("WORK_PAY",common.get_json_value(jsonData,"WORK_PAY"));
                        message_body.put("EMP_NAME",common.get_json_value(jsonData,"EMP_NAME"));
                        message_body.put("CORP_NAME",common.get_json_value(jsonData,"CORP_NAME"));
                        message_body.put("BASE_TYPE",IN_BASE_TYPE);
                        message_body.put("ID_KEY_TYPE",IN_ID_KEY_TYPE);
                        message_body.put("ID_KEY",common.get_json_value_int(jsonData,"BOOK_ID"));
                        message_body.put("RES_YN","Y");
                        message_body.put("WORK_REQ_TYPE",common.get_json_value(jsonData,"EMC_REQ_TYPE"));
                        message_body.put("WORK_REJ_TYPE",common.get_json_value(jsonData,"EMC_REJ_TYPE"));
                        message_body.put("DATE_GBN",common.get_json_value(jsonData,"DATE_GBN"));
                        message_body.put("DATE_GBN_NM",common.get_json_value(jsonData,"DATE_GBN_NM"));
                        message_body.put("AS_WORK_DATE",common.get_json_value(jsonData,"AS_WORK_DATE"));
                        message_body.put("TO_WORK_DATE",common.get_json_value(jsonData,"TO_WORK_DATE"));
                        message_body.put("ADDR1",common.get_json_value(jsonData,"ADDR1"));
                        message_body.put("DISTANCE",common.get_json_value_double(jsonData,"DISTANCE"));
                        message_body.put("JOB_CONTENT",common.get_json_value(jsonData,"JOB_CONTENT"));
                        message_body.put("MSG_ID",common.get_json_value_int(jsonData,"MSG_ID"));
                        message_body.put("WORK_ID",common.get_json_value_int(jsonData,"WORK_ID"));
                        message_body.put("NEED_ID",common.get_json_value_int(jsonData,"NEED_ID"));
                        message_body.put("FEE_AMT",common.get_json_value(jsonData,"FEE_AMT"));

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
                    
                    if (common.get_json_value(jsonData,"GBN").equals("SLT")==false)
                    {
                        
                        strSQL = "CALL SP_M_WORK_ALARM_SELECT_1_1_92('"+common.get_json_value(jsonData,"CORP_ID")+"','"+
                                        common.get_json_value(jsonData,"WORK_ID")+"','EMC_WORK_ALARM','')";
                        JSONArray jsonAlarm = common.execute_query(strSQL);
                        if (common.get_json_array_value(jsonAlarm,"result").equals("ok"))
                        {
                            if (registration_id.equals("")==false)
                            {
                                JSONObject json_result_message = fcm.send(message);
                                result = json_result_message.toString();
                                common.log(module_name,"fcm.send result="+result+",BOOK_ID : "+common.get_json_value(jsonData,"BOOK_ID"));
                            }

                            if (web_registration_id.equals("")==false)
                            {
                                JSONObject json_result_message = web_fcm.send(web_message);
                                result = json_result_message.toString();
                                common.log(module_name,"web_fcm.send result="+result+",BOOK_ID : "+common.get_json_value(jsonData,"BOOK_ID"));                            
                            }

                        }
                    }
                    else
                    {
                        strSQL = "CALL SP_M_WORK_ALARM_SELECT_1_1_92('"+common.get_json_value(jsonData,"CORP_ID")+"','"+
                                common.get_json_value(jsonData,"WORK_ID")+"','EMC_WORK_ALARM','')";
                        
                        JSONArray jsonAlarm = common.execute_query(strSQL);
                        if (common.get_json_array_value(jsonAlarm,"result").equals("ok"))
                        {
                            if (registration_id.equals("")==false)
                            {
                                JSONObject json_result_message = fcm.send(message);
                                result = json_result_message.toString();
                                common.log(module_name,"fcm.send result="+result+",BOOK_ID : "+common.get_json_value(jsonData,"BOOK_ID"));
                            }

                            if (web_registration_id.equals("")==false)
                            {
                                JSONObject json_result_message = web_fcm.send(web_message);
                                result = json_result_message.toString();
                                common.log(module_name,"web_fcm.send result="+result+",BOOK_ID : "+common.get_json_value(jsonData,"BOOK_ID"));                            
                            }
                        }
                        
                    }
                    //common.log(module_name,"SendPushAlaram=send_message="+IN_CONTENT+" gcm_message="+JSON.stringify(message));
                    //console.log("Mobile Registration Key="+jsonData.MOBILE_REG_ID+"send_message="+IN_CONTENT);
                    try
                    {
                        // 긴급지원한 알람 정보 저장
                        if (common.get_json_value(jsonData,"GBN").equals("SLT"))
                        {                            
                            if (common.get_json_value(jsonData,"ALARM_TYPE").equals("AlarmWorkVS") && 
                                             common.get_json_value(jsonData,"GBN").equals("SLT"))
                            {
                                JSONObject jsonResendData = MakeResendAlarmWorkVSData(jsonData);                                        
                                if(registration_id.equals("")==false){
                                    jsonResendData.put("app_message",message);
                                    alarmResendServer.send_alarm_json_object(jsonResendData);
                                }                                
                            }
                        }                       
                    }
                    catch (Exception ex)
                    {
                        common.log(module_name,"mongoose db save error : " + ex.getMessage());
                    }
                    
                }
                catch (Exception ex)
                {
                    common.log(module_name,"SendPushAlaram FCM error : " + ex.getMessage());
                }          	
            }
            catch (Exception ex)
            {
                common.log(module_name,"SendPushAlaram error : " + ex.getMessage());
            }
        
        }
        catch (Exception ex)
        {
            common.log(module_name,"SendPushAlaram Error : " + ex.getMessage());
        }
        return result;
    }

    public String SetAlarmWorkVSRes(JSONObject data)
    {
        String result = "";
        JSONObject jsonData = data;
        common.log(module_name,"SetAlarmWorkVSRes : " + common.get_json_value(jsonData,"BOOK_ID"));
        result = SendAlarmWorkVSRes(data);
        return result;
    }

    private String SendAlarmWorkVSRes(JSONObject data)
    {
        String result = "";
        try
        {
            JSONObject jsonData = data;

            String IN_VERISON_NAME = common.get_json_value(jsonData,"VERSION_NAME");
            String IN_TITLE = "";
            String IN_CONTENT = "";	
            String IN_APP_LANG = common.get_json_value(jsonData,"APP_LANG");
            if (IN_APP_LANG.equals(""))
            {
                IN_APP_LANG = "KR";
            }
            String api_key ="";		
            String IN_MSG_TYPE = "";
            String registration_id = common.get_json_value(jsonData,"MOBILE_REG_ID");
            String IN_GBN = "";
            String IN_ID_KEY_TYPE = "BOOK_ID";
            String IN_SEND_DATE_NUMBER = common.now_date_integer();
            String IN_BASE_TYPE = "";            
            if (common.get_json_value(jsonData,"RES_YN").equals("Y"))
            {
                IN_GBN = common.get_json_value(jsonData,"GBN");
                IN_TITLE = common.get_json_value(jsonData,"TITLE");
                if (IN_TITLE.equals(""))
                {
                    IN_TITLE = common.get_json_value(jsonData,"CORP_NAME") + 
                    common.encode("의 긴급일자리 예약 알림",IN_APP_LANG);
                }
                IN_CONTENT = common.get_json_value(jsonData,"CONTENT");
                if (IN_CONTENT.equals(""))
                {
                    IN_CONTENT = common.get_json_value(jsonData,"WORK_DATE")+"  "+
                                common.get_json_value(jsonData,"SCHEDULE_NAME")+" " + 
                                "[" + 
                                common.get_json_value(jsonData,"JOB_GBN_NM")+"] " + 
                                common.get_json_value(jsonData,"JOB_TYPE_NM")+" "+
                                common.get_json_value(jsonData,"WORK_PAY")+
                                common.encode("원 ",IN_APP_LANG)+
                                " " +
                                common.encode("위의 일자리에 선발되어",IN_APP_LANG)+
                                common.encode("예약완료 되었습니다.",IN_APP_LANG);
                }

                if (IN_APP_LANG.equals("KR"))
                {
                    IN_TITLE = common.get_json_value(jsonData,"CORP_NAME") + 
                               common.encode("의 긴급일자리 예약 알림",IN_APP_LANG);
                    IN_CONTENT = common.get_json_value(jsonData,"WORK_DATE")+"  "+
                                common.get_json_value(jsonData,"SCHEDULE_NAME")+" " + 
                                "[" + 
                                common.get_json_value(jsonData,"JOB_GBN_NM")+"] " + 
                                common.get_json_value(jsonData,"JOB_TYPE_NM")+" "+
                                common.get_json_value(jsonData,"WORK_PAY")+
                                common.encode("원 ",IN_APP_LANG)+
                                " " +
                                common.encode("위의 일자리에 선발되어",IN_APP_LANG)+
                                common.encode("예약완료 되었습니다.",IN_APP_LANG);
                }
                else if (IN_APP_LANG.equals("EN"))
                {
                    IN_TITLE = common.get_json_value(jsonData,"CORP_NAME") + 
                               common.encode(" urgently job reservation notification",IN_APP_LANG);
                    IN_CONTENT = common.encode("You were selected for the job above and your reservation has been completed.",IN_APP_LANG)+ 
                                common.get_json_value(jsonData,"CORP_NAME")+ " "+
                                common.get_json_value(jsonData,"WORK_DATE")+" "+
                                common.get_json_value(jsonData,"SCHEDULE_NAME")+" " + 
                                "[" + 
                                common.get_json_value(jsonData,"JOB_GBN_NM")+"] " + 
                                common.get_json_value(jsonData,"JOB_TYPE_NM")+" "+
                                common.get_json_value(jsonData,"WORK_PAY")+
                                common.encode(" Won",IN_APP_LANG);
                }
                else if (IN_APP_LANG.equals("ZH"))
                {
                    IN_TITLE = common.get_json_value(jsonData,"CORP_NAME") + 
                               common.encode(" 紧急请求工作岗位预约通知",IN_APP_LANG);
                    IN_CONTENT = common.encode("被上述工作岗位录用 预约 已完成. ",IN_APP_LANG) + " " +
                                common.get_json_value(jsonData,"CORP_NAME") + " "+
                                common.get_json_value(jsonData,"WORK_DATE") + " "+
                                common.get_json_value(jsonData,"SCHEDULE_NAME")+" " + 
                                "[" + 
                                common.get_json_value(jsonData,"JOB_GBN_NM")+"] " + 
                                common.get_json_value(jsonData,"JOB_TYPE_NM")+" "+
                                common.get_json_value(jsonData,"WORK_PAY")+
                                common.encode(" 韩元",IN_APP_LANG);
                }

                IN_MSG_TYPE = "EMC_WORK_RES_ALARM";
                api_key = common.user_api_key;
            }
            else if(common.get_json_value(jsonData,"RES_YN").equals("N"))
            {
                if (common.get_json_value(jsonData,"GBN") == "ECN")
                {
                    IN_GBN = "ECN";
                    IN_TITLE = common.get_json_value(jsonData,"CORP_NAME") + 
                               common.encode("의 알람",IN_APP_LANG);
                    IN_CONTENT = common.get_json_value(jsonData,"EMP_NAME")+
                                 common.encode("님이 근무예약을 취소하였습니다. ",IN_APP_LANG)+
                            common.get_json_value(jsonData,"SCHEDULE_NAME")+" "+
                            "["+
                            common.get_json_value(jsonData,"JOB_GBN_NM")+"] "+
                            common.get_json_value(jsonData,"JOB_TYPE_NM")+" "+
                            common.get_json_value(jsonData,"WORK_DATE") + " "+
                            common.encode("필요인력 ",IN_APP_LANG)+
                            common.get_json_value(jsonData,"NEED_CNT")+
                            common.encode("명 중 ",IN_APP_LANG)+
                            common.get_json_value(jsonData,"BOOK_CNT")+
                            common.encode("명 인력 배치 예약 완료  ",IN_APP_LANG)+
                            common.encode("( 소속 : ",IN_APP_LANG)+
                            common.get_json_value(jsonData,"MEMBER_CNT")+" / "+
                            common.encode("베테랑 인력 : ",IN_APP_LANG) + 
                            common.get_json_value(jsonData,"BOOK_URGENT_CNT")+" / "+
                            common.encode("베테랑 추천 인력 : ",IN_APP_LANG) +
                            common.get_json_value(jsonData,"VV_BOOK_CNT") + ")";
                    IN_MSG_TYPE = "WORK_RES_ALARM";
                    api_key = common.vendor_api_key;
                }
                else
                {
                    
                    IN_GBN = common.get_json_value(jsonData,"GBN");
                    IN_TITLE = common.get_json_value(jsonData,"CORP_NAME") + 
                               common.encode("의 알람",IN_APP_LANG);
                    IN_CONTENT = common.get_json_value(jsonData,"EMP_NAME")+
                                 common.encode("님이 근무요청을 거절하였습니다. ",IN_APP_LANG)+
                            common.get_json_value(jsonData,"SCHEDULE_NAME")+" "+
                            "["+
                            common.get_json_value(jsonData,"JOB_GBN_NM")+"] "+
                            common.get_json_value(jsonData,"JOB_TYPE_NM")+" "+
                            common.get_json_value(jsonData,"WORK_DATE") + " "+
                            common.encode("필요인력 ",IN_APP_LANG)+
                            common.get_json_value(jsonData,"NEED_CNT")+
                            common.encode("명 중 ",IN_APP_LANG)+
                            common.get_json_value(jsonData,"BOOK_CNT")+
                            common.encode("명 인력 배치 예약 완료  ",IN_APP_LANG)+
                            common.encode("( 소속 : ",IN_APP_LANG)+
                            common.get_json_value(jsonData,"MEMBER_CNT")+" / "+
                            common.encode("베테랑 인력 : ",IN_APP_LANG) + 
                            common.get_json_value(jsonData,"BOOK_URGENT_CNT")+" / " +
                            common.encode("베테랑 추천 인력 : ",IN_APP_LANG) + 
                            common.get_json_value(jsonData,"VV_BOOK_CNT") + ")";
                    IN_MSG_TYPE = "EMC_WORK_RES_ALARM";
                    api_key = common.user_api_key;
                }
                
            }

            
            FCM fcm = null;
            String send_gbn = common.get_json_value(jsonData,"GBN");
            JSONObject message = new JSONObject();
            JSONObject message_body = new JSONObject();
            JSONObject message_data = new JSONObject();
            
            String MSG_ID = "";

            try
            {
                String GBN = common.get_json_value(jsonData,"GBN");
                String ID_TYPE = "BOOK_ID";
                String ID_KEY = common.get_json_value(jsonData,"BOOK_ID");
                String ERR_YN = "N";
                String ERR_MSG = "";
                String EMP_ID = common.get_json_value(jsonData,"EMP_ID");
                String CORP_ID = common.get_json_value(jsonData,"CORP_ID");
                String EMC_REQ_TYPE = common.get_json_value(jsonData,"EMC_REQ_TYPE");
                String EMC_REJ_TYPE = common.get_json_value(jsonData,"EMC_REJ_TYPE");
                String WORK_DATE = common.get_json_value(jsonData,"WORK_DATE");
                String W_REQ_GBN = common.get_json_value(jsonData,"W_REQ_GBN");
                String R_GBN = "B-E";
                String strSQL = "CALL SP_M_MSG_INFO_INSERT('"+GBN+"','"+ID_TYPE+"','"+ID_KEY+"'," +
                    "'"+EMP_ID+"','"+CORP_ID+"','"+EMC_REQ_TYPE+"'," +
                    "'"+EMC_REJ_TYPE+"','"+R_GBN+"','"+common.get_value(IN_CONTENT)+"','"+WORK_DATE+"','"+W_REQ_GBN+"')"; 
                common.log(module_name,strSQL);
                
                JSONArray jsonMsg = common.execute_query_commit(strSQL);
                MSG_ID = common.get_json_array_value(jsonMsg,"MSG_ID");
                if(jsonData.has("MSG_ID")){
                    jsonData.remove("MSG_ID");
                }
                jsonData.put("MSG_ID",MSG_ID);
                

                try
                {
                    fcm = new FCM(api_key);
                    
                    IN_BASE_TYPE = "EMC";
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
                            MILISECONDS : common.get_miliseconds(),         title: IN_TITLE, 
                            body:IN_CONTENT,                                id: 1,
                            type: common.alarm_work_emc_res_msg_type,       isShow: 1,
                            sound:"default",                                MSG_TYPE:IN_MSG_TYPE,
                            GBN:IN_GBN,                                     W_REQ_GBN : jsonData.W_REQ_GBN,
                            BOOK_ID:jsonData.BOOK_ID,                       EMP_ID: jsonData.EMP_ID,
                            EMP_NAME : jsonData.EMP_NAME,                   SCHEDULE_NAME:jsonData.SCHEDULE_NAME,
                            SITE_NAME : jsonData.SITE_NAME,                 BIZ_NAME : jsonData.BIZ_NAME,
                            JOB_GBN_NM : jsonData.JOB_GBN_NM,               JOB_TYPE_NM : jsonData.JOB_TYPE_NM,
                            FEE_AMT : jsonData.FEE_AMT,                     WORK_PAY : jsonData.WORK_PAY,
                            WORK_DATE : jsonData.WORK_DATE,                 NEED_CNT : jsonData.NEED_CNT,
                            BOOK_CNT : jsonData.BOOK_CNT,                   MEMBER_CNT : jsonData.MEMBER_CNT,
                            BOOK_URGENT_CNT : jsonData.BOOK_URGENT_CNT,     VV_BOOK_CNT : jsonData.VV_BOOK_CNT,
                            VS_SUBMIT_CNT : jsonData.VS_SUBMIT_CNT,         CORP_NAME : jsonData.CORP_NAME,
                            WORK_REQ_TYPE : jsonData.EMC_REQ_TYPE,          WORK_REJ_TYPE : jsonData.EMC_REJ_TYPE,
                            RES_YN : jsonData.RES_YN,                       BASE_TYPE : IN_BASE_TYPE,
                            ID_KEY_TYPE : IN_ID_KEY_TYPE,                   ID_KEY : jsonData.BOOK_ID,
                            DATE_GBN : jsonData.DATE_GBN,                   DATE_GBN_NM : jsonData.DATE_GBN_NM,
                            AS_WORK_DATE : jsonData.AS_WORK_DATE,           TO_WORK_DATE : jsonData.TO_WORK_DATE,
                            ADDR1 : jsonData.ADDR1,                         DISTANCE : jsonData.DISTANCE,
                            JOB_CONTENT : jsonData.JOB_CONTENT,             MSG_ID : jsonData.MSG_ID,
                            WORK_ID : jsonData.WORK_ID,                     NEED_ID : jsonData.NEED_ID
                            }
                        }
                    };
                    */
                    message_body.put("MILISECONDS",Integer.parseInt(common.get_miliseconds())); 
                    message_body.put("title",IN_TITLE);
                    message_body.put("body",IN_CONTENT); message_body.put("id",1);
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
                    message_body.put("FEE_AMT",common.get_json_value(jsonData,"FEE_AMT")); 
                    message_body.put("WORK_PAY",common.get_json_value(jsonData,"WORK_PAY"));
                    message_body.put("WORK_DATE",common.get_json_value(jsonData,"WORK_DATE")); 
                    message_body.put("NEED_CNT",common.get_json_value_int(jsonData,"NEED_CNT"));
                    message_body.put("BOOK_CNT",common.get_json_value_int(jsonData,"BOOK_CNT")); 
                    message_body.put("MEMBER_CNT",common.get_json_value_int(jsonData,"MEMBER_CNT"));
                    message_body.put("BOOK_URGENT_CNT",common.get_json_value_int(jsonData,"BOOK_URGENT_CNT")); 
                    message_body.put("VV_BOOK_CNT",common.get_json_value_int(jsonData,"VV_BOOK_CNT"));
                    message_body.put("VS_SUBMIT_CNT",common.get_json_value_int(jsonData,"VS_SUBMIT_CNT")); 
                    message_body.put("CORP_NAME",common.get_json_value(jsonData,"CORP_NAME"));
                    message_body.put("WORK_REQ_TYPE",common.get_json_value(jsonData,"EMC_REQ_TYPE")); 
                    message_body.put("WORK_REJ_TYPE",common.get_json_value(jsonData,"EMC_REJ_TYPE"));
                    message_body.put("RES_YN",common.get_json_value(jsonData,"RES_YN")); 
                    message_body.put("BASE_TYPE",IN_BASE_TYPE);
                    message_body.put("ID_KEY_TYPE",IN_ID_KEY_TYPE); 
                    message_body.put("ID_KEY",common.get_json_value_int(jsonData,"BOOK_ID"));
                    message_body.put("DATE_GBN",common.get_json_value(jsonData,"DATE_GBN")); 
                    message_body.put("DATE_GBN_NM",common.get_json_value(jsonData,"DATE_GBN_NM"));
                    message_body.put("AS_WORK_DATE",common.get_json_value(jsonData,"AS_WORK_DATE")); 
                    message_body.put("TO_WORK_DATE",common.get_json_value(jsonData,"TO_WORK_DATE"));
                    message_body.put("ADDR1",common.get_json_value(jsonData,"ADDR1")); 
                    message_body.put("DISTANCE",common.get_json_value_double(jsonData,"DISTANCE"));
                    message_body.put("JOB_CONTENT",common.get_json_value(jsonData,"JOB_CONTENT")); 
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
                        common.log(module_name,"fcm.send result="+result+",BOOK_ID : " + common.get_json_value(jsonData,"BOOK_ID"));
                    }
                }
                catch (Exception ex)
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
        return result;
    }

    public String SetAlarmWorkVV(JSONObject data)
    {
        String result = "";
        JSONObject jsonData = data;
        common.log(module_name,"SetAlarmWorkVV : " + common.get_json_value(jsonData,"BOOK_ID"));
        result = SendAlarmWorkVV(data);
        return result;
    }

    private String SendAlarmWorkVV(JSONObject data)
    {
        String result = "";
        try
        {
            JSONObject jsonData = data;
            String IN_VERISON_NAME = common.get_json_value(jsonData,"VERSION_NAME");
            String IN_CONTENT = "";
            String api_key ="";
            String IN_GBN = "";
            String IN_BASE_TYPE = "EMC";
            String IN_SEND_DATE_NUMBER = common.now_date_integer();
            String IN_TITLE = "";
            String IN_APP_LANG = "KR";
            if (common.get_json_value(jsonData,"GBN").equals("SLT"))
            {
                IN_GBN = common.get_json_value(jsonData,"GBN");
                IN_TITLE = common.get_json_value(jsonData,"CORP_NAME") + 
                           common.encode("의 알람",IN_APP_LANG);
                IN_CONTENT = common.encode("[직업소개소서비스]",IN_APP_LANG)+
                            common.get_json_value(jsonData,"CORP_NAME")+
                            common.encode(" 직업소개소가 ",IN_APP_LANG)+
                            common.get_json_value(jsonData,"WORK_DATE")+
                            common.encode("에 ",IN_APP_LANG) +
                            common.get_json_value(jsonData,"SCHEDULE_NAME")+
                            common.encode(" 일자리로 ",IN_APP_LANG)+
                            common.encode("베테랑 직업소개소 서비스에 지원하였습니다.",IN_APP_LANG);
                api_key = common.vendor_api_key;
            }
            else if (common.get_json_value(jsonData,"GBN").equals("REQ"))
            {
                IN_GBN = common.get_json_value(jsonData,"GBN");
                IN_TITLE = common.get_json_value(jsonData,"CORP_NAME") + 
                           common.encode("의 알람",IN_APP_LANG);
                IN_CONTENT = common.get_json_value(jsonData,"CORP_NAME")+
                             common.encode("에서 ",IN_APP_LANG)+
                            common.get_json_value(jsonData,"WORK_DATE")+
                            common.encode("에 근무요청하였습니다.",IN_APP_LANG);
                api_key = common.user_api_key;
            }
            else if (common.get_json_value(jsonData,"GBN").equals("CAN"))
            {
                IN_GBN = "CAN";
                IN_TITLE = common.get_json_value(jsonData,"CORP_NAME") + 
                           common.encode("의 알람",IN_APP_LANG);
                IN_CONTENT = common.get_json_value(jsonData,"WORK_DATE")+" " +
                            common.get_json_value(jsonData,"SCHEDULE_NAME")+" "+
                            "["+
                            common.get_json_value(jsonData,"JOB_GBN_NM")+"] "+
                            common.get_json_value(jsonData,"JOB_TYPE_NM")+" "+
                            common.get_json_value(jsonData,"WORK_PAY")+
                            common.encode("원 ",IN_APP_LANG)+
                            " "+
                            common.encode("위의 일자리가 취소되었습니다. ",IN_APP_LANG)+
                            common.encode("다시 예약해주시기 바랍니다.",IN_APP_LANG);
                api_key = common.user_api_key;
            }
            
            FCM fcm = null;
            String send_gbn = common.get_json_value(jsonData,"GBN");
            String strNowTimeString = common.now_time_string();
            String IN_MSG_TYPE = "";
            String IN_ID_KEY_TYPE = "BOOK_ID";
            String registration_id = "";
            
            JSONObject message = new JSONObject();
            JSONObject message_body = new JSONObject();
            JSONObject message_data = new JSONObject();

            String MSG_ID = "";
            try
            {
                String GBN = common.get_json_value(jsonData,"GBN");
                String ID_TYPE = "BOOK_ID";
                String ID_KEY = common.get_json_value(jsonData,"BOOK_ID");
                String ERR_YN = "N";
                String ERR_MSG = "";
                String REQ_CORP_ID = common.get_json_value(jsonData,"REQ_CORP_ID");
                String CORP_ID = common.get_json_value(jsonData,"CORP_ID");
                String EMC_REQ_TYPE = common.get_json_value(jsonData,"EMC_REQ_TYPE");
                String EMC_REJ_TYPE = common.get_json_value(jsonData,"EMC_REJ_TYPE");
                String WORK_DATE = common.get_json_value(jsonData,"WORK_DATE");
                String W_REQ_GBN = common.get_json_value(jsonData,"W_REQ_GBN");
                String R_GBN = "E-B";
                //(GBN, ID_KEY_TYPE, ID_KEY, ERR_YN, ERR_MSG, EMP_ID, 
                // CORP_ID, WORK_REQ_TYPE, WORK_REJ_TYPE
                String strSQL = "CALL SP_M_MSG_INFO_INSERT('"+GBN+"','"+ID_TYPE+"','"+ID_KEY+"'," +
                    "'"+CORP_ID+"','"+REQ_CORP_ID+"','"+EMC_REQ_TYPE+"'," +
                    "'"+EMC_REJ_TYPE+"','"+R_GBN+"','"+common.get_value(IN_CONTENT)+"','"+WORK_DATE+"','"+W_REQ_GBN+"')"; 
                common.log(module_name,strSQL);

                JSONArray jsonMsg = common.execute_query_commit(strSQL);
                MSG_ID = common.get_json_array_value(jsonMsg,"MSG_ID");
                if(jsonData.has("MSG_ID")){
                    jsonData.remove("MSG_ID");
                }
                jsonData.put("MSG_ID",MSG_ID);
                
                try
                {			
                    fcm = new FCM(api_key);

                    if(common.get_json_value(jsonData,"GBN").equals("SLT"))
                    {
                        registration_id = common.get_json_value(jsonData,"CORP_MOBILE_REG_ID");

                        IN_MSG_TYPE = "VETERAN_WORK_ALARM";
                        /*
                        message = { //this may vary according to the message type (single recipient, multicast, topic, et cetera)
                            to: registration_id, 
                            priority: "high",
                            collapse_key: IN_MSG_TYPE,
                            data: {  //you can send only notification or only data(or include both)                                
                            body: 
                            {
                            }
                            }
                        */
                        message.put("to",registration_id);
                        message.put("priority","high");
                        message.put("collapse_key",IN_MSG_TYPE);
                        /*
                        message = { //this may vary according to the message type (single recipient, multicast, topic, et cetera)                        
                            data: {  //you can send only notification or only data(or include both)
                            title: IN_TITLE, 
                            message: IN_CONTENT,
                            click_action:IN_MSG_TYPE,
                            body: 
                            {                                    
                            }	
                            }
                        };
                        */
                        message_data.put("title",IN_TITLE);
                        message_data.put("message",IN_CONTENT);
                        message_data.put("click_action",IN_MSG_TYPE);
                        /*
                        message = {                             
                            data: {                                 
                                body: 
                                {
                                MILISECONDS : common.get_miliseconds(),     title:jsonData.CORP_NAME+"의 알람", 
                                body:IN_CONTENT,                            id: 1,
                                type: common.alarm_work_emc_msg_type,       isShow: 1,
                                sound:"default",                            MSG_TYPE:IN_MSG_TYPE,
                                GBN:jsonData.GBN,                           W_REQ_GBN:jsonData.W_REQ_GBN,
                                CORP_ID:jsonData.CORP_ID,                   EMC_ID:jsonData.EMC_ID,
                                BOOK_ID:jsonData.BOOK_ID,                   EMP_ID: jsonData.EMP_ID,
                                EMP_NAME : jsonData.EMP_NAME,               WORK_DATE : jsonData.WORK_DATE,
                                SCHEDULE_NAME:jsonData.SCHEDULE_NAME,       SITE_NAME : jsonData.SITE_NAME,
                                BIZ_NAME : jsonData.BIZ_NAME,               CORP_NAME : jsonData.CORP_NAME,
                                JOB_GBN_NM : jsonData.JOB_GBN_NM,           JOB_TYPE_NM : jsonData.JOB_TYPE_NM,
                                WORK_PAY : jsonData.WORK_PAY,               
                                NEED_CNT : jsonData.NEED_CNT,               BOOK_CNT : jsonData.BOOK_CNT,
                                MEMBER_CNT : jsonData.MEMBER_CNT,           BOOK_URGENT_CNT : jsonData.BOOK_URGENT_CNT,
                                VV_BOOK_CNT : jsonData.VV_BOOK_CNT,         SEND_TIME : strNowTimeString,
                                WORK_REQ_TYPE : jsonData.EMC_REQ_TYPE,      WORK_REJ_TYPE : jsonData.EMC_REJ_TYPE,
                                BASE_TYPE : IN_BASE_TYPE,                   ID_KEY_TYPE : IN_ID_KEY_TYPE,
                                ID_KEY : jsonData.BOOK_ID,                  MSG_ID : jsonData.MSG_ID,           
                                WORK_ID : jsonData.WORK_ID,                 NEED_ID : jsonData.NEED_ID
                                }
                            }
                        };
                        */
                        message_body.put("MILISECONDS",Integer.parseInt(common.get_miliseconds()));       
                        message_body.put("title",IN_TITLE);
                        message_body.put("body",IN_CONTENT); 
                        message_body.put("id",1);
                        message_body.put("type",common.alarm_work_emc_msg_type); 
                        message_body.put("isShow",1);
                        message_body.put("sound","default"); 
                        message_body.put("MSG_TYPE",IN_MSG_TYPE);
                        message_body.put("GBN",common.get_json_value(jsonData,"GBN"));   
                        message_body.put("W_REQ_GBN",common.get_json_value(jsonData,"W_REQ_GBN"));
                        message_body.put("CORP_ID",common.get_json_value_int(jsonData,"CORP_ID"));   
                        message_body.put("EMC_ID",common.get_json_value_int(jsonData,"EMC_ID"));
                        message_body.put("BOOK_ID",common.get_json_value_int(jsonData,"BOOK_ID"));   
                        message_body.put("EMP_ID",common.get_json_value_int(jsonData,"EMP_ID"));
                        message_body.put("EMP_NAME",common.get_json_value(jsonData,"EMP_NAME"));   
                        message_body.put("WORK_DATE",common.get_json_value(jsonData,"WORK_DATE"));
                        message_body.put("SCHEDULE_NAME",common.get_json_value(jsonData,"SCHEDULE_NAME"));  
                        message_body.put("SITE_NAME",common.get_json_value(jsonData,"SITE_NAME"));
                        message_body.put("BIZ_NAME",common.get_json_value(jsonData,"BIZ_NAME"));   
                        message_body.put("CORP_NAME",common.get_json_value(jsonData,"CORP_NAME"));
                        message_body.put("JOB_GBN_NM",common.get_json_value(jsonData,"JOB_GBN_NM"));   
                        message_body.put("JOB_TYPE_NM",common.get_json_value(jsonData,"JOB_TYPE_NM"));
                        message_body.put("WORK_PAY",common.get_json_value(jsonData,"WORK_PAY")); 
                        message_body.put("NEED_CNT",common.get_json_value_int(jsonData,"NEED_CNT"));   
                        message_body.put("BOOK_CNT",common.get_json_value_int(jsonData,"BOOK_CNT"));
                        message_body.put("MEMBER_CNT",common.get_json_value_int(jsonData,"MEMBER_CNT"));   
                        message_body.put("BOOK_URGENT_CNT",common.get_json_value_int(jsonData,"BOOK_URGENT_CNT"));
                        message_body.put("VV_BOOK_CNT",common.get_json_value_int(jsonData,"VV_BOOK_CNT"));   
                        message_body.put("SEND_TIME",strNowTimeString);
                        message_body.put("WORK_REQ_TYPE",common.get_json_value(jsonData,"EMC_REQ_TYPE"));   
                        message_body.put("WORK_REJ_TYPE",common.get_json_value(jsonData,"EMC_REJ_TYPE"));
                        message_body.put("BASE_TYPE",IN_BASE_TYPE);   
                        message_body.put("ID_KEY_TYPE",IN_ID_KEY_TYPE);
                        message_body.put("ID_KEY",common.get_json_value_int(jsonData,"BOOK_ID"));   
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
                        
                    }
                    else if (common.get_json_value(jsonData,"GBN").equals("CAN"))
                    {
                        registration_id = common.get_json_value(jsonData,"EMP_MOBILE_REG_ID");
                        IN_MSG_TYPE = "WORK_ALARM";
                        /*
                        message = { //this may vary according to the message type (single recipient, multicast, topic, et cetera)
                            to: registration_id, 
                            priority: "HIGH",
                            collapse_key: IN_MSG_TYPE,
                            data: {  //you can send only notification or only data(or include both)                                
                            body: 
                            {
                            }
                            }
                        */
                        message.put("to",registration_id);
                        message.put("priority","HIGH");
                        message.put("collapse_key",IN_MSG_TYPE);
                        /*
                        message = { //this may vary according to the message type (single recipient, multicast, topic, et cetera)                        
                            data: {  //you can send only notification or only data(or include both)
                            title: IN_TITLE, 
                            message: IN_CONTENT,
                            click_action:IN_MSG_TYPE,
                            body: 
                            {                                    
                            }	
                            }
                        };
                        */
                        message_data.put("title",IN_TITLE);
                        message_data.put("message",IN_CONTENT);
                        message_data.put("click_action",IN_MSG_TYPE);
                        /*
                        message = { //this may vary according to the message type (single recipient, multicast, topic, et cetera)                            
                            data: {  //you can send only notification or only data(or include both)                                
                                body: 
                                {
                                MILISECONDS : common.get_miliseconds(),         title:jsonData.CORP_NAME+"의 알람", 
                                body:IN_CONTENT,                                id: 1,
                                type: common.alarm_work_emc_msg_type,           isShow: 1,
                                sound:"default",                                MSG_TYPE:IN_MSG_TYPE,
                                GBN:jsonData.GBN,                               W_REQ_GBN:jsonData.W_REQ_GBN,
                                BOOK_ID:jsonData.BOOK_ID,                       EMP_ID: jsonData.EMP_ID,
                                WORK_DATE : jsonData.WORK_DATE,                 SCHEDULE_NAME:jsonData.SCHEDULE_NAME,
                                SITE_NAME : jsonData.SITE_NAME,                 BIZ_NAME : jsonData.BIZ_NAME,
                                JOB_GBN_NM : jsonData.JOB_GBN_NM,               JOB_TYPE_NM : jsonData.JOB_TYPE_NM,
                                WORK_PAY : jsonData.WORK_PAY,                   
                                NEED_CNT : jsonData.NEED_CNT,                   BOOK_CNT : jsonData.BOOK_CNT,
                                MEMBER_CNT : jsonData.MEMBER_CNT,               BOOK_URGENT_CNT : jsonData.BOOK_URGENT_CNT,
                                VV_BOOK_CNT : jsonData.VV_BOOK_CNT,             SEND_TIME : strNowTimeString,
                                EMP_NAME : jsonData.EMP_NAME,
                                CORP_NAME : jsonData.CORP_NAME,                 BASE_TYPE : IN_BASE_TYPE,
                                ID_KEY_TYPE : IN_ID_KEY_TYPE,                   ID_KEY : jsonData.BOOK_ID,
                                RES_YN : "N",                                   WORK_REQ_TYPE : jsonData.EMC_REQ_TYPE,		
                                WORK_REJ_TYPE : jsonData.EMC_REJ_TYPE,          MSG_ID : jsonData.MSG_ID,
                                WORK_ID : jsonData.WORK_ID,                     NEED_ID : jsonData.NEED_ID
                                }						
                            }
                        };
                        */   
                        message_body.put("MILISECONDS",Integer.parseInt(common.get_miliseconds()));       
                        message_body.put("title",IN_TITLE);
                        message_body.put("body",IN_CONTENT); 
                        message_body.put("id",1);
                        message_body.put("type",common.alarm_work_emc_msg_type); 
                        message_body.put("isShow",1);
                        message_body.put("sound","default"); 
                        message_body.put("MSG_TYPE",IN_MSG_TYPE);
                        message_body.put("GBN",common.get_json_value(jsonData,"GBN"));   
                        message_body.put("W_REQ_GBN",common.get_json_value(jsonData,"W_REQ_GBN"));
                        message_body.put("BOOK_ID",common.get_json_value_int(jsonData,"BOOK_ID"));   
                        message_body.put("EMP_ID",common.get_json_value_int(jsonData,"EMP_ID"));
                        message_body.put("WORK_DATE",common.get_json_value(jsonData,"WORK_DATE"));   
                        message_body.put("SCHEDULE_NAME",common.get_json_value(jsonData,"SCHEDULE_NAME"));
                        message_body.put("SITE_NAME",common.get_json_value(jsonData,"SITE_NAME"));   
                        message_body.put("BIZ_NAME",common.get_json_value(jsonData,"BIZ_NAME"));
                        message_body.put("JOB_GBN_NM",common.get_json_value(jsonData,"JOB_GBN_NM"));   
                        message_body.put("JOB_TYPE_NM",common.get_json_value(jsonData,"JOB_TYPE_NM"));
                        message_body.put("WORK_PAY",common.get_json_value(jsonData,"WORK_PAY")); 
                        message_body.put("NEED_CNT",common.get_json_value_int(jsonData,"NEED_CNT"));   
                        message_body.put("BOOK_CNT",common.get_json_value_int(jsonData,"BOOK_CNT"));
                        message_body.put("MEMBER_CNT",common.get_json_value_int(jsonData,"MEMBER_CNT"));   
                        message_body.put("BOOK_URGENT_CNT",common.get_json_value_int(jsonData,"BOOK_URGENT_CNT"));
                        message_body.put("VV_BOOK_CNT",common.get_json_value_int(jsonData,"VV_BOOK_CNT"));  
                        message_body.put("SEND_TIME",strNowTimeString);
                        message_body.put("EMP_NAME",common.get_json_value(jsonData,"EMP_NAME")); 
                        message_body.put("CORP_NAME",common.get_json_value(jsonData,"CORP_NAME"));   
                        message_body.put("BASE_TYPE",IN_BASE_TYPE);
                        message_body.put("ID_KEY_TYPE",IN_ID_KEY_TYPE);   
                        message_body.put("ID_KEY",common.get_json_value_int(jsonData,"BOOK_ID"));
                        message_body.put("RES_YN","N");   
                        message_body.put("WORK_REQ_TYPE",common.get_json_value(jsonData,"EMC_REQ_TYPE"));
                        message_body.put("WORK_REJ_TYPE",common.get_json_value(jsonData,"EMC_REJ_TYPE"));
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

                    }
                    
                    if (common.get_json_value(jsonData,"GBN").equals("SLT")==false)
                    {
                        if (registration_id.equals("")==false)
                        {                        
                            JSONObject json_result_message = fcm.send(message);
                            result = json_result_message.toString();
                            common.log(module_name,"fcm.send result="+result+",BOOK_ID : "+common.get_json_value(jsonData,"BOOK_ID"));
                        }
                    }
                    else
                    {
                        if (registration_id.equals("")==false)
                        {
                            JSONObject json_result_message = fcm.send(message);
                            result = json_result_message.toString();
                            common.log(module_name,"fcm.send success="+result+",BOOK_ID : "+common.get_json_value(jsonData,"BOOK_ID"));
                        }
                    }
                    //common.log(module_name,"SendPushAlaram=send_message="+IN_CONTENT+" gcm_message="+JSON.stringify(message));
                    //console.log("Mobile Registration Key="+jsonData.MOBILE_REG_ID+"send_message="+IN_CONTENT);
                    try
                    {
                        // 긴급지원한 알람 정보 저장
                        if (common.get_json_value(jsonData,"GBN").equals("SLT"))
                        {                                                       
                            if (common.get_json_value(jsonData,"ALARM_TYPE").equals("AlarmWorkVV") && 
                                            common.get_json_value(jsonData,"GBN").equals("VV"))
                            {
                                JSONObject jsonResendData = MakeResendAlarmWorkVVData(jsonData);                                
                                if(registration_id.equals("")==false){
                                    jsonResendData.put("app_message",message);
                                    alarmResendServer.send_alarm_json_object(jsonResendData);  
                                }
                            }
                        }                        
                    }
                    catch (Exception ex)
                    {
                        common.log(module_name,"mongoose db save error : " + ex.getMessage());
                    }
                    
                }
                catch (Exception ex)
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
        return result;
    }

    public String SetAlarmWorkVVRes(JSONObject data)
    {
        String result = "";
        JSONObject jsonData = data;
        common.log(module_name,"SetAlarmWorkVVRes : " + common.get_json_value(jsonData,"BOOK_ID"));
        result = SendAlarmWorkVVRes(data);
        return result;
    }

    public String SendAlarmWorkVVRes(JSONObject data)
    {
        String result = "";
        try
        {
            JSONObject jsonData = data;            

            String IN_VERISON_NAME = common.get_json_value(jsonData,"VERSION_NAME");
            String IN_CONTENT = "";	
            String api_key ="";		
            String IN_MSG_TYPE = "";
            String registration_id = common.get_json_value(jsonData,"MOBILE_REG_ID");
            String IN_GBN = "";
            String IN_ID_KEY_TYPE = "BOOK_ID";
            String IN_SEND_DATE_NUMBER = common.now_date_integer();
            String IN_BASE_TYPE = "";
            String IN_APP_LANG = "KR";

            String IN_TITLE = "";
            if (common.get_json_value(jsonData,"RES_YN").equals("Y"))
            {
                IN_GBN = common.get_json_value(jsonData,"GBN");
                IN_TITLE = common.get_json_value(jsonData,"CORP_NAME")+
                          common.encode("의 알람",IN_APP_LANG);
                IN_CONTENT = common.get_json_value(jsonData,"WORK_DATE")+"  "+
                            common.get_json_value(jsonData,"SCHEDULE_NAME")+" " + 
                            "[" + 
                            common.get_json_value(jsonData,"JOB_GBN_NM")+"] " + 
                            common.get_json_value(jsonData,"JOB_TYPE_NM")+" "+
                            common.get_json_value(jsonData,"WORK_PAY")+
                            common.encode("원 ",IN_APP_LANG)+
                            " " +
                            common.encode("위의 일자리에 선발되어",IN_APP_LANG)+
                            common.encode("예약완료 되었습니다.",IN_APP_LANG);
                IN_MSG_TYPE = "VETERAN_WORK_RES_ALARM";
                api_key = common.user_api_key;
            }
            else if(common.get_json_value(jsonData,"RES_YN").equals("N"))
            {
                if (common.get_json_value(jsonData,"GBN").equals("ECN"))
                {
                    IN_GBN = "ECN";
                    IN_TITLE = common.get_json_value(jsonData,"EMP_NAME")+
                               common.encode("의 알람",IN_APP_LANG);
                    IN_CONTENT = common.get_json_value(jsonData,"EMP_NAME")+
                                 common.encode("님이 근무예약을 취소하였습니다. ",IN_APP_LANG)+
                            common.get_json_value(jsonData,"SCHEDULE_NAME")+" "+
                            "["+
                            common.get_json_value(jsonData,"JOB_GBN_NM")+"] "+
                            common.get_json_value(jsonData,"JOB_TYPE_NM")+" "+
                            common.get_json_value(jsonData,"WORK_DATE") + " "+
                            common.encode("필요인력 ",IN_APP_LANG)+
                            common.get_json_value(jsonData,"NEED_CNT")+
                            common.encode("명 중 ",IN_APP_LANG)+
                            common.get_json_value(jsonData,"BOOK_CNT")+
                            common.encode("명 인력 배치 예약 완료  ",IN_APP_LANG)+
                            common.encode("( 소속 : ",IN_APP_LANG)+
                            common.get_json_value(jsonData,"MEMBER_CNT")+" / "+
                            common.encode("베테랑 인력 : ",IN_APP_LANG) + 
                            common.get_json_value(jsonData,"BOOK_URGENT_CNT")+" / " +
                            common.encode("베테랑 추천 인력 : ",IN_APP_LANG) + 
                            common.get_json_value(jsonData,"VV_BOOK_CNT") + ")";
                    IN_MSG_TYPE = "WORK_RES_ALARM";
                    api_key = common.vendor_api_key;
                }
                else
                {
                    
                    IN_GBN = common.get_json_value(jsonData,"GBN");
                    IN_TITLE = common.get_json_value(jsonData,"EMP_NAME")+
                               common.encode("의 알람",IN_APP_LANG);
                    IN_CONTENT = common.get_json_value(jsonData,"EMP_NAME")+
                                 common.encode("님이 근무요청을 거절하였습니다. ",IN_APP_LANG)+
                            common.get_json_value(jsonData,"SCHEDULE_NAME")+" "+
                            "["+
                            common.get_json_value(jsonData,"JOB_GBN_NM")+"] "+
                            common.get_json_value(jsonData,"JOB_TYPE_NM")+" "+
                            common.get_json_value(jsonData,"WORK_DATE") + " "+
                            common.encode("필요인력 ",IN_APP_LANG)+
                            common.get_json_value(jsonData,"NEED_CNT")+
                            common.encode("명 중 ",IN_APP_LANG)+
                            common.get_json_value(jsonData,"BOOK_CNT")+
                            common.encode("명 인력 배치 예약 완료  ",IN_APP_LANG)+
                            common.encode("( 소속 : ",IN_APP_LANG)+
                            common.get_json_value(jsonData,"MEMBER_CNT")+" / "+
                            common.encode("베테랑 인력 : ",IN_APP_LANG) + 
                            common.get_json_value(jsonData,"BOOK_URGENT_CNT")+" / " +
                            common.encode("베테랑 추천 인력 : ",IN_APP_LANG) + 
                            common.get_json_value(jsonData,"VV_BOOK_CNT") + ")";
                    IN_MSG_TYPE = "VETERAN_WORK_RES_ALARM";
                    api_key = common.user_api_key;
                }
                
            }

            
            FCM fcm = null;
            String send_gbn = common.get_json_value(jsonData,"GBN");
            
            JSONObject message = new JSONObject();
            JSONObject message_body = new JSONObject();
            JSONObject message_data = new JSONObject();

            String MSG_ID = "";


            try
            {
                String GBN = common.get_json_value(jsonData,"GBN");
                String ID_TYPE = "BOOK_ID";
                String ID_KEY = common.get_json_value(jsonData,"BOOK_ID");
                String ERR_YN = "N";
                String ERR_MSG = "";
                String EMP_ID = common.get_json_value(jsonData,"EMP_ID");
                String CORP_ID = common.get_json_value(jsonData,"CORP_ID");
                String EMC_REQ_TYPE = common.get_json_value(jsonData,"EMC_REQ_TYPE");
                String EMC_REJ_TYPE = common.get_json_value(jsonData,"EMC_REJ_TYPE");
                String R_GBN = "B-E";
                String WORK_DATE = common.get_json_value(jsonData,"WORK_DATE");
                String W_REQ_GBN = common.get_json_value(jsonData,"W_REQ_GBN");

                String strSQL = "CALL SP_M_MSG_INFO_INSERT('"+GBN+"','"+ID_TYPE+"','"+ID_KEY+"'," +
                    "'"+EMP_ID+"','"+CORP_ID+"','"+EMC_REQ_TYPE+"'," +
                    "'"+EMC_REJ_TYPE+"','"+R_GBN+"','"+common.get_value(IN_CONTENT)+"','"+WORK_DATE+"','"+W_REQ_GBN+"')"; 
                common.log(module_name,strSQL);

                JSONArray jsonMsg = common.execute_query_commit(strSQL);

                MSG_ID = common.get_json_array_value(jsonMsg,"MSG_ID");
                if(jsonData.has("MSG_ID")){
                    jsonData.remove("MSG_ID");
                }
                jsonData.put("MSG_ID",MSG_ID);
                
                try
                {
                    fcm = new FCM(api_key);
                    
                    IN_BASE_TYPE = "VV";
                    /*
                    message = { //this may vary according to the message type (single recipient, multicast, topic, et cetera)
                        to: registration_id, 
                        priority: "HIGH",
                        collapse_key: IN_MSG_TYPE,
                        data: {  //you can send only notification or only data(or include both)                                
                        body: 
                        {
                        }
                        }
                    */
                    message.put("to",registration_id);
                    message.put("priority","HIGH");
                    message.put("collapse_key",IN_MSG_TYPE);
                    /*
                    message = { //this may vary according to the message type (single recipient, multicast, topic, et cetera)                        
                        data: {  //you can send only notification or only data(or include both)
                        title: IN_TITLE, 
                        message: IN_CONTENT,
                        click_action:IN_MSG_TYPE,
                        body: 
                        {                                    
                        }	
                        }
                    };
                    */
                    message_data.put("title",IN_TITLE);
                    message_data.put("message",IN_CONTENT);
                    message_data.put("click_action",IN_MSG_TYPE);
                    /*
                    message = {                          
                        data: { 
                            body: 
                            {
                            MILISECONDS : common.get_miliseconds(),     title:jsonData.CORP_NAME+"의 알람", 
                            body:IN_CONTENT,                            id: 1,
                            type: common.alarm_work_emc_res_msg_type,   isShow: 1,
                            sound:"default",                            MSG_TYPE:IN_MSG_TYPE,
                            GBN:IN_GBN,                                 W_REQ_GBN:jsonData.W_REQ_GBN,
                            BOOK_ID:jsonData.BOOK_ID,                   EMP_ID: jsonData.EMP_ID,
                            EMP_NAME : jsonData.EMP_NAME,               SCHEDULE_NAME:jsonData.SCHEDULE_NAME,
                            SITE_NAME : jsonData.SITE_NAME,             BIZ_NAME : jsonData.BIZ_NAME,
                            JOB_GBN_NM : jsonData.JOB_GBN_NM,           JOB_TYPE_NM : jsonData.JOB_TYPE_NM,
                            WORK_PAY : jsonData.WORK_PAY,               WORK_DATE : jsonData.WORK_DATE,
                            NEED_CNT : jsonData.NEED_CNT,               BOOK_CNT : jsonData.BOOK_CNT,
                            MEMBER_CNT : jsonData.MEMBER_CNT,           BOOK_URGENT_CNT : jsonData.BOOK_URGENT_CNT,
                            VV_BOOK_CNT : jsonData.VV_BOOK_CNT,         CORP_NAME : jsonData.CORP_NAME,
                            WORK_REQ_TYPE : jsonData.EMC_REQ_TYPE,      WORK_REJ_TYPE : jsonData.EMC_REJ_TYPE,
                            RES_YN : jsonData.RES_YN,                   BASE_TYPE : IN_BASE_TYPE,
                            ID_KEY_TYPE : IN_ID_KEY_TYPE,               ID_KEY : jsonData.BOOK_ID,
                            volume: "3.21.15",                          MSG_ID : jsonData.MSG_ID,
                            WORK_ID : jsonData.WORK_ID,                 NEED_ID : jsonData.NEED_ID
                            }
                        }
                    };
                    */
                    message_body.put("MILISECONDS",Integer.parseInt(common.get_miliseconds()));       
                    message_body.put("title",IN_TITLE);
                    message_body.put("body",IN_CONTENT); message_body.put("id",1);
                    message_body.put("type",common.alarm_work_emc_msg_type); message_body.put("isShow",1);
                    message_body.put("sound","default"); message_body.put("MSG_TYPE",IN_MSG_TYPE);

                    message_body.put("GBN",common.get_json_value(jsonData,"GBN"));   
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
                    message_body.put("VV_BOOK_CNT",common.get_json_value_int(jsonData,"VV_BOOK_CNT"));   
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
                        common.log(module_name,"fcm.send result="+result+",BOOK_ID : " + common.get_json_value(jsonData,"BOOK_ID"));
                    }
                }
                catch (Exception ex)
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
        return result;
    }

    public String SetAlarmWorkEmcNo(JSONObject data)
    {
        String result = "";
        JSONObject jsonData = data;
        common.log(module_name,"SetAlarmWorkEmcNO : " + common.get_json_value(jsonData,"BOOK_ID"));
        result = SendAlarmWorkEmcNo(data);
        return result;
    }

    private String SendAlarmWorkEmcNo(JSONObject data)
    {
        String result = "";
        try
        {
            JSONObject jsonData = data;
            
            String IN_VERISON_NAME = common.get_json_value(jsonData,"VERSION_NAME");
            String IN_CONTENT = "";
            String api_key ="";
            String IN_GBN = "";
            String IN_BASE_TYPE = "EMC";
            String IN_SEND_DATE_NUMBER = common.now_date_integer();
            String IN_TITLE = "";
            String IN_APP_LANG = "KR";
            IN_GBN = common.get_json_value(jsonData,"GBN");
            IN_TITLE = common.get_json_value(jsonData,"EMP_NAME")+
                       common.encode("의 알람",IN_APP_LANG);
            IN_CONTENT = common.encode("[베테랑서비스]",IN_APP_LANG)+
                        common.get_json_value(jsonData,"EMP_NAME")+
                        common.encode(" 구직자가 ",IN_APP_LANG)+
                        common.get_json_value(jsonData,"WORK_DATE")+
                        common.encode("에 ",IN_APP_LANG) +
                        common.get_json_value(jsonData,"SCHEDULE_NAME")+
                        common.encode(" 일자리로 ",IN_APP_LANG)+
                        common.encode("근무 거절하였습니다.",IN_APP_LANG);
            api_key = common.vendor_api_key;
            
            String web_api_key = "";
            FCM web_fcm = null;
            String web_registration_id = common.get_json_value(jsonData,"CORP_WEB_REG_ID");

            JSONObject web_message = new JSONObject();
            JSONObject web_message_notification = new JSONObject();
            JSONObject web_message_data = new JSONObject();
    
            FCM fcm = null;
            String send_gbn = common.get_json_value(jsonData,"GBN");
            String strNowTimeString = common.now_time_string();
            String IN_MSG_TYPE = "";
            String IN_ID_KEY_TYPE = "EMC_ID";
            String registration_id = "";

            JSONObject message = new JSONObject();
            JSONObject message_body = new JSONObject();
            JSONObject message_data = new JSONObject();

            String MSG_ID = "";
            try
            {
                
                String GBN = common.get_json_value(jsonData,"GBN");
                String ID_TYPE = "I_ID";
                String ID_KEY = common.get_json_value(jsonData,"EMC_ID");
                String ERR_YN = "N";
                String ERR_MSG = "";
                String EMP_ID = common.get_json_value(jsonData,"EMP_ID");
                String CORP_ID = common.get_json_value(jsonData,"CORP_ID");
                String EMC_REQ_TYPE = common.get_json_value(jsonData,"EMC_REQ_TYPE");
                String EMC_REJ_TYPE = common.get_json_value(jsonData,"EMC_REJ_TYPE");
                String R_GBN = "E-B";
                String W_REQ_GBN = "VS";
                String WORK_DATE = common.get_json_value(jsonData,"WORK_DATE");
                
                String strSQL = "CALL SP_M_MSG_INFO_INSERT('"+GBN+"','"+ID_TYPE+"','"+ID_KEY+"'," +
                    "'"+EMP_ID+"','"+CORP_ID+"','"+EMC_REQ_TYPE+"'," +
                    "'"+EMC_REJ_TYPE+"','"+R_GBN+"','"+common.get_value(IN_CONTENT)+"','"+WORK_DATE+"','"+W_REQ_GBN+"')"; 
                common.log(module_name,strSQL);

                JSONArray jsonMsg = common.execute_query_commit(strSQL);

                MSG_ID = common.get_json_array_value(jsonMsg,"MSG_ID");
                if(jsonData.has("MSG_ID")){
                    jsonData.remove("MSG_ID");
                }
                jsonData.put("MSG_ID", MSG_ID);
                
                try
                {			
                    fcm = new FCM(api_key);

                    registration_id = common.get_json_value(jsonData,"CORP_MOBILE_REG_ID");

                    IN_MSG_TYPE = "EMC_WORK_ALARM";

                    /*
                    message = { //this may vary according to the message type (single recipient, multicast, topic, et cetera)
                        to: registration_id, 
                        priority: "HIGH",
                        collapse_key: IN_MSG_TYPE,
                        data: {  //you can send only notification or only data(or include both)                                
                        body: 
                        {
                        }
                        }
                    */
                    message.put("to",registration_id);
                    message.put("priority","HIGH");
                    message.put("collapse_key",IN_MSG_TYPE);
                    /*
                    message = { //this may vary according to the message type (single recipient, multicast, topic, et cetera)                        
                        data: {  //you can send only notification or only data(or include both)
                        title: IN_TITLE, 
                        message: IN_CONTENT,
                        click_action:IN_MSG_TYPE,
                        body: 
                        {                                    
                        }	
                        }
                    };
                    */
                    message_data.put("title",IN_TITLE);
                    message_data.put("message",IN_CONTENT);
                    message_data.put("click_action",IN_MSG_TYPE);
                    /*
                    message = { //this may vary according to the message type (single recipient, multicast, topic, et cetera)
                        data: {  //you can send only notification or only data(or include both)                            
                            body: 
                            {
                            MILISECONDS : common.get_miliseconds(),         title:jsonData.CORP_NAME+"의 알람", 
                            body:IN_CONTENT,                                id: 1,
                            type: common.alarm_work_emc_msg_type,           isShow: 1,
                            sound:"default",                                MSG_TYPE:IN_MSG_TYPE,
                            GBN:jsonData.GBN,                               CORP_ID:jsonData.CORP_ID,
                            EMC_ID:jsonData.EMC_ID,                         BOOK_ID:jsonData.BOOK_ID,
                            EMP_ID: jsonData.EMP_ID,                        EMP_NAME : jsonData.EMP_NAME,
                            WORK_DATE : jsonData.WORK_DATE,                 SCHEDULE_NAME : jsonData.SCHEDULE_NAME,
                            DATE_GBN : jsonData.DATE_GBN,                   DATE_GBN_NM : jsonData.DATE_GBN_NM,
                            AS_WORK_DATE : jsonData.AS_WORK_DATE,           TO_WORK_DATE : jsonData.TO_WORK_DATE,
                            CORP_NAME : jsonData.CORP_NAME,                 JOB_GBN_NM : jsonData.JOB_GBN_NM,
                            JOB_TYPE_NM : jsonData.JOB_TYPE_NM,             WORK_PAY : jsonData.WORK_PAY,
                            NEED_CNT : jsonData.NEED_CNT,                   BOOK_CNT : jsonData.BOOK_CNT,
                            MEMBER_CNT : jsonData.MEMBER_CNT,               BOOK_URGENT_CNT : jsonData.BOOK_URGENT_CNT,
                            VS_SUBMIT_CNT : jsonData.VS_SUBMIT_CNT,         SEND_TIME : strNowTimeString,
                            WORK_REQ_TYPE : jsonData.EMC_REQ_TYPE,          WORK_REJ_TYPE : jsonData.EMC_REJ_TYPE,
                            BASE_TYPE : IN_BASE_TYPE,                       ID_KEY_TYPE : IN_ID_KEY_TYPE,
                            ID_KEY : jsonData.BOOK_ID,                      MSG_ID : jsonData.MSG_ID,
                            WORK_ID : jsonData.WORK_ID,                     NEED_ID : jsonData.NEED_ID,
                            W_REQ_GBN : jsonData.W_REQ_GBN,                 FEE_AMT : jsonData.FEE_AMT
                            //volume: "3.21.15"
                            }
                        }
                    };
                    */
                    message_body.put("MILISECONDS",Integer.parseInt(common.get_miliseconds()));       
                    message_body.put("title",IN_TITLE);
                    message_body.put("body",IN_CONTENT); 
                    message_body.put("id",1);
                    message_body.put("type",common.alarm_work_emc_msg_type); message_body.put("isShow",1);
                    message_body.put("sound","default"); message_body.put("MSG_TYPE",IN_MSG_TYPE);                    
                    message_body.put("GBN",common.get_json_value(jsonData,"GBN"));   
                    message_body.put("CORP_ID",common.get_json_value_int(jsonData,"CORP_ID"));
                    message_body.put("EMC_ID",common.get_json_value_int(jsonData,"EMC_ID"));  
                    message_body.put("BOOK_ID",common.get_json_value_int(jsonData,"BOOK_ID"));
                    message_body.put("EMP_ID",common.get_json_value_int(jsonData,"EMP_ID"));   
                    message_body.put("EMP_NAME",common.get_json_value(jsonData,"EMP_NAME"));
                    message_body.put("WORK_DATE",common.get_json_value(jsonData,"WORK_DATE"));   
                    message_body.put("SCHEDULE_NAME",common.get_json_value(jsonData,"SCHEDULE_NAME"));
                    message_body.put("DATE_GBN",common.get_json_value(jsonData,"DATE_GBN"));   
                    message_body.put("DATE_GBN_NM",common.get_json_value(jsonData,"DATE_GBN_NM"));
                    message_body.put("AS_WORK_DATE",common.get_json_value(jsonData,"AS_WORK_DATE"));   
                    message_body.put("TO_WORK_DATE",common.get_json_value(jsonData,"TO_WORK_DATE"));
                    message_body.put("CORP_NAME",common.get_json_value(jsonData,"CORP_NAME"));   
                    message_body.put("JOB_GBN_NM",common.get_json_value(jsonData,"JOB_GBN_NM"));
                    message_body.put("JOB_TYPE_NM",common.get_json_value(jsonData,"JOB_TYPE_NM"));  
                    message_body.put("WORK_PAY",common.get_json_value(jsonData,"WORK_PAY"));
                    message_body.put("NEED_CNT",common.get_json_value_int(jsonData,"NEED_CNT"));   
                    message_body.put("BOOK_CNT",common.get_json_value_int(jsonData,"BOOK_CNT"));
                    message_body.put("MEMBER_CNT",common.get_json_value_int(jsonData,"MEMBER_CNT"));   
                    message_body.put("BOOK_URGENT_CNT",common.get_json_value_int(jsonData,"BOOK_URGENT_CNT"));
                    message_body.put("VS_SUBMIT_CNT",common.get_json_value_int(jsonData,"VS_SUBMIT_CNT"));   message_body.put("SEND_TIME",strNowTimeString);
                    message_body.put("WORK_REQ_TYPE",common.get_json_value(jsonData,"WORK_REQ_TYPE"));   
                    message_body.put("EMC_REJ_TYPE",common.get_json_value(jsonData,"EMC_REJ_TYPE"));
                    message_body.put("BASE_TYPE",IN_BASE_TYPE);   message_body.put("ID_KEY_TYPE",IN_ID_KEY_TYPE);
                    message_body.put("ID_KEY",common.get_json_value_int(jsonData,"BOOK_ID"));  
                    message_body.put("MSG_ID",common.get_json_value_int(jsonData,"MSG_ID"));
                    message_body.put("WORK_ID",common.get_json_value_int(jsonData,"WORK_ID"));   
                    message_body.put("NEED_ID",common.get_json_value_int(jsonData,"NEED_ID"));
                    message_body.put("W_REQ_GBN",common.get_json_value(jsonData,"W_REQ_GBN"));   
                    message_body.put("FEE_AMT",common.get_json_value(jsonData,"FEE_AMT"));

                    /*
                    message = {                         
                    data : {
                        body:{}
                        }
                    }
                    */
                    message_data.put("body",message_body);
                    message.put("data",message_data);
                    
                    
                    web_api_key = common.vendor_web_api_key;			
                    web_fcm = new FCM(web_api_key);

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
                    web_message = { //this may vary according to the message type (single recipient, multicast, topic, et cetera)                        
                        notification: {                            
                        },
                        data: {  
                            MILISECONDS : common.get_miliseconds(),     title:jsonData.EMP_NAME+"의 알람", 
                            body:IN_CONTENT,                            id: 1,
                            type: common.alarm_work_emc_msg_type,       isShow: 1,
                            sound:"default",                            MSG_TYPE:IN_MSG_TYPE,
                            GBN:jsonData.GBN,							CORP_ID:jsonData.CORP_ID,
                            EMC_ID:jsonData.EMC_ID,                     BOOK_ID:jsonData.BOOK_ID,
                            EMP_ID: jsonData.EMP_ID,                    EMP_NAME : jsonData.EMP_NAME,
                            S_WORK_DATE : jsonData.S_WORK_DATE,         WORK_DATE : jsonData.WORK_DATE,
                            SCHEDULE_NAME:jsonData.SCHEDULE_NAME,       SITE_NAME : jsonData.SITE_NAME,
                            BIZ_NAME : jsonData.BIZ_NAME,               CORP_NAME : jsonData.CORP_NAME,
                            JOB_GBN_NM : jsonData.JOB_GBN_NM,           JOB_TYPE_NM : jsonData.JOB_TYPE_NM,
                            WORK_PAY : jsonData.WORK_PAY,               WORK_DATE : jsonData.WORK_DATE,
                            NEED_CNT : jsonData.NEED_CNT,               BOOK_CNT : jsonData.BOOK_CNT,
                            MEMBER_CNT : jsonData.MEMBER_CNT,           BOOK_URGENT_CNT : jsonData.BOOK_URGENT_CNT,
                            VV_BOOK_CNT : jsonData.VV_BOOK_CNT,         VS_SUBMIT_CNT : jsonData.VS_SUBMIT_CNT,
                            SEND_TIME : strNowTimeString,               WORK_REQ_TYPE : jsonData.EMC_REQ_TYPE,
                            WORK_REJ_TYPE : jsonData.EMC_REJ_TYPE,      RES_YN : jsonData.RES_YN,
                            BASE_TYPE : IN_BASE_TYPE,                   ID_KEY_TYPE : IN_ID_KEY_TYPE,
                            ID_KEY : jsonData.BOOK_ID,                  DATE_GBN : jsonData.DATE_GBN,
                            DATE_GBN_NM : jsonData.DATE_GBN_NM,         AS_WORK_DATE : jsonData.AS_WORK_DATE,
                            TO_WORK_DATE : jsonData.TO_WORK_DATE,       MSG_ID : jsonData.MSG_ID,
                            WORK_ID : jsonData.WORK_ID,                 NEED_ID : jsonData.NEED_ID,
                            W_REQ_GBN : jsonData.W_REQ_GBN,             FEE_AMT : jsonData.FEE_AMT
                        }
                    };
                    */
                    web_message_data.put("MILISECONDS",Integer.parseInt(common.get_miliseconds())); 
                    web_message_data.put("title",IN_TITLE);
                    web_message_data.put("body",IN_CONTENT); 
                    web_message_data.put("id",1);
                    web_message_data.put("type",common.alarm_work_res_msg_type); 
                    web_message_data.put("isShow",1);
                    web_message_data.put("sound","default"); 
                    web_message_data.put("click_action",IN_MSG_TYPE);
                    web_message_data.put("MSG_TYPE",IN_MSG_TYPE);
                    web_message_data.put("GBN",common.get_json_value(jsonData,"GBN"));
                    web_message_data.put("CORP_ID",common.get_json_value_int(jsonData,"CORP_ID"));
                    web_message_data.put("EMC_ID",common.get_json_value_int(jsonData,"EMC_ID"));
                    web_message_data.put("BOOK_ID",common.get_json_value_int(jsonData,"BOOK_ID"));
                    web_message_data.put("EMP_ID",common.get_json_value_int(jsonData,"EMP_ID"));
                    web_message_data.put("EMP_NAME",common.get_json_value(jsonData,"EMP_NAME"));
                    web_message_data.put("S_WORK_DATE",common.get_json_value(jsonData,"S_WORK_DATE"));
                    web_message_data.put("WORK_DATE",common.get_json_value(jsonData,"WORK_DATE"));
                    web_message_data.put("SCHEDULE_NAME",common.get_json_value(jsonData,"SCHEDULE_NAME"));
                    web_message_data.put("SITE_NAME",common.get_json_value(jsonData,"SITE_NAME"));
                    web_message_data.put("BIZ_NAME",common.get_json_value(jsonData,"BIZ_NAME"));
                    web_message_data.put("CORP_NAME",common.get_json_value(jsonData,"CORP_NAME"));
                    web_message_data.put("JOB_GBN_NM",common.get_json_value(jsonData,"JOB_GBN_NM"));
                    web_message_data.put("JOB_TYPE_NM",common.get_json_value(jsonData,"JOB_TYPE_NM"));
                    web_message_data.put("WORK_PAY",common.get_json_value(jsonData,"WORK_PAY"));
                    web_message_data.put("WORK_DATE",common.get_json_value(jsonData,"WORK_DATE"));
                    web_message_data.put("NEED_CNT",common.get_json_value_int(jsonData,"NEED_CNT"));
                    web_message_data.put("BOOK_CNT",common.get_json_value_int(jsonData,"BOOK_CNT"));
                    web_message_data.put("MEMBER_CNT",common.get_json_value_int(jsonData,"MEMBER_CNT"));
                    web_message_data.put("BOOK_URGENT_CNT",common.get_json_value_int(jsonData,"BOOK_URGENT_CNT"));
                    web_message_data.put("VV_BOOK_CNT",common.get_json_value_int(jsonData,"VV_BOOK_CNT"));
                    web_message_data.put("VS_SUBMIT_CNT",common.get_json_value_int(jsonData,"VS_SUBMIT_CNT"));
                    web_message_data.put("SEND_TIME",strNowTimeString);
                    web_message_data.put("WORK_REQ_TYPE",common.get_json_value(jsonData,"EMC_REQ_TYPE"));
                    web_message_data.put("WORK_REJ_TYPE",common.get_json_value(jsonData,"EMC_REJ_TYPE"));
                    web_message_data.put("RES_YN",common.get_json_value(jsonData,"RES_YN"));
                    web_message_data.put("BASE_TYPE",IN_BASE_TYPE);
                    web_message_data.put("ID_KEY_TYPE",IN_ID_KEY_TYPE);
                    web_message_data.put("ID_KEY",common.get_json_value_int(jsonData,"BOOK_ID"));
                    web_message_data.put("DATE_GBN",common.get_json_value(jsonData,"DATE_GBN"));
                    web_message_data.put("DATE_GBN_NM",common.get_json_value(jsonData,"DATE_GBN_NM"));
                    web_message_data.put("AS_WORK_DATE",common.get_json_value(jsonData,"AS_WORK_DATE"));
                    web_message_data.put("TO_WORK_DATE",common.get_json_value(jsonData,"TO_WORK_DATE"));
                    web_message_data.put("MSG_ID",common.get_json_value_int(jsonData,"MSG_ID"));
                    web_message_data.put("WORK_ID",common.get_json_value_int(jsonData,"WORK_ID"));
                    web_message_data.put("NEED_ID",common.get_json_value_int(jsonData,"NEED_ID"));
                    web_message_data.put("W_REQ_GBN",common.get_json_value(jsonData,"W_REQ_GBN"));
                    web_message_data.put("FEE_AMT",common.get_json_value(jsonData,"FEE_AMT"));
                    
                    /*
                    web_message = {                         
                        notification : {},
                        data:{}
                    }
                    */
                    web_message.put("notification",web_message_notification);
                    web_message.put("data",web_message_data); 

                    /*
                    // 알람 발송 
                    if (registration_id.equals("")==false)
                    {
                        
                        if (common.get_json_value(jsonData,"GBN").equals("SLT")==false)
                        {
                            JSONObject json_result_message = fcm.send(message);
                            result = json_result_message.toString();
                            common.log(module_name,"fcm.send result="+result+",BOOK_ID : "+common.get_json_value(jsonData,"BOOK_ID"));
                            
                            json_result_message = web_fcm.send(web_message);
                            result = json_result_message.toString();
                            common.log(module_name,"web_fcm.send result="+result+",BOOK_ID : "+common.get_json_value(jsonData,"BOOK_ID"));
                        }
                        else
                        {
                            JSONObject json_result_message = fcm.send(message);
                            result = json_result_message.toString();
                            common.log(module_name,"fcm.send result="+result+",BOOK_ID : "+common.get_json_value(jsonData,"BOOK_ID"));
                            
                            json_result_message = web_fcm.send(web_message);
                            result = json_result_message.toString();
                            common.log(module_name,"web_fcm.send result="+result+",BOOK_ID : "+common.get_json_value(jsonData,"BOOK_ID"));
                        }
                        
                    }
                    */
                    
                    
                }
                catch (Exception ex)
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
        return result;
    }    
}