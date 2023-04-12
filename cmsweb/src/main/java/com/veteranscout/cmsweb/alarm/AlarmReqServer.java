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

public class AlarmReqServer {
    
    CommonUtil common;
    String module_name = "";    
    MsgSendServer msgSendServer;
    AlarmReq alarmReq = new AlarmReq();

    public AlarmReqServer(){
        common = new CommonUtil();
        msgSendServer = new MsgSendServer();
    }

    public String send_alarm_json_object(JSONObject jsonObject){
        String result = "";              
        try
        {
            PushAlaram_Add_List_Queue(jsonObject);
        }
        catch(Exception ex2){
            ex2.printStackTrace();
        }
        
        return result;
    }

    private String PushAlaram_Add_List_Queue(JSONObject data)
    {
        String result = "";        
        try
        {
            JSONObject jsonData = data;
            // jsonData.REQ_COUNT = 1;		

            String WORK_ID = common.get_json_value(jsonData,"WORK_ID");
            String WORK_DATE = common.get_json_value(jsonData,"WORK_DATE");
            String NEED_ID = common.get_json_value(jsonData,"NEED_ID");
            String EVENT_YN = common.get_json_value(jsonData,"EVENT_YN");
            if (EVENT_YN.equals(""))
            {
                EVENT_YN = "Y";
            }
            String MULTI_REQ_YN = common.get_json_value(jsonData,"MULTI_REQ_YN");
            if (MULTI_REQ_YN.equals(""))
            {
                MULTI_REQ_YN = "N";
            }
            String VERSION_NAME = common.get_json_value(jsonData,"VERSION_NAME");
            if (VERSION_NAME.equals(""))
            {
                VERSION_NAME = "1.1.92";
            }
            String USE_STATUS = common.get_json_value(jsonData,"USE_STATUS");
            if (USE_STATUS.equals(""))
            {
                USE_STATUS = "APP";
            }
            String V_REG_DATE = common.get_json_value(jsonData,"V_REG_DATE");
            if (V_REG_DATE.equals(""))
            {
                V_REG_DATE = common.now_time_string();
            }
            String CMS_YN = common.get_json_value(jsonData,"CMS_YN");
            if (CMS_YN.equals(""))
            {
                CMS_YN = "N";
            }
            String V_ID = common.get_json_value(jsonData,"V_ID");
            if(V_ID.equals("")){
                V_ID = "0";
            }
            String ALARM_VERSION = common.get_json_value(jsonData,"ALARM_VERSION");
            
            common.log(module_name,"WORK_ID : "+WORK_ID + " WORK_DATE : " + WORK_DATE + " VERSION_NAME : " + VERSION_NAME + " V_ID :" + V_ID);
            common.system_log("WORK_ID : "+WORK_ID + " WORK_DATE : " + WORK_DATE + " VERSION_NAME : " + VERSION_NAME + " V_ID :" + V_ID);

            String strSQL = "";

            if (VERSION_NAME.equals("1.1.92.1"))
            {
                strSQL = "CALL SP_M_WORK_REQUEST_VETERAN_SVC_COUNT_1_1_92_2_2('"+WORK_ID+"','"+NEED_ID+"','"+WORK_DATE+"','"+EVENT_YN+"','"+MULTI_REQ_YN+"','"+USE_STATUS+"','"+V_REG_DATE+"','"+V_ID+"')"; 
            }
            else if (VERSION_NAME.equals("1.1.92"))
            {
                strSQL = "CALL SP_M_WORK_REQUEST_VETERAN_SVC_COUNT_1_1_92_1_2('"+WORK_ID+"','"+NEED_ID+"','"+WORK_DATE+"','"+EVENT_YN+"','"+MULTI_REQ_YN+"','"+CMS_YN+"','"+V_ID+"')"; 
            }
            else
            {
                strSQL = "CALL SP_M_WORK_REQUEST_VETERAN_SVC_COUNT_1_1_92_1_2('"+WORK_ID+"','"+NEED_ID+"','"+WORK_DATE+"','"+EVENT_YN+"','"+MULTI_REQ_YN+"','"+CMS_YN+"','"+V_ID+"')"; 
            }
            
            common.system_log("strSQL : " + strSQL);
            common.log(module_name,"strQuery : " + strSQL);
            
            JSONArray jsonTotalPage = common.execute_query_commit(strSQL);
            int i = 0;						
            String send_message = "";
            int nTotalPage = common.get_json_array_position_value_int(jsonTotalPage, 0, "TOTAL_PAGE");
            int aI_ID = common.get_json_array_position_value_int(jsonTotalPage, 0, "I_ID");
            
            
            if (VERSION_NAME.equals("1.1.92") && CMS_YN.equals("N"))
            {
                String GBN_1 = "E-B";
                String ID_KEY_TYPE_1 = "CORP";
                String ID_KEY_1 = common.get_json_array_position_value(jsonTotalPage, 1, "CORP_ID");
                String SEND_ID_1 = common.get_json_array_position_value(jsonTotalPage, 1, "EMP_ID");
                String RECV_IDS_1 = common.get_json_array_position_value(jsonTotalPage, 1, "CORP_ID");
                String CONTENT_1 = common.get_json_array_position_value(jsonTotalPage, 1, "CONTENT");
                String UP_MSG_ID_1 = "0";
                String strSQL_1 = "";
                strSQL_1 = "CALL SP_CORP_MSG_NOTI_INSERT_POP('"+GBN_1+"','"+ID_KEY_TYPE_1+"','"+ID_KEY_1+"',"+
                "'"+SEND_ID_1+"','"+RECV_IDS_1+"','"+CONTENT_1+"','"+UP_MSG_ID_1+"')"; 
                common.log(module_name,"strSQL_1 :" + strSQL_1);

                JSONArray jsonMessage_1 = common.execute_query_commit(strSQL_1);
                JSONObject sendMessage_1 = null;
                if(jsonMessage_1.length() > 0){
                    sendMessage_1 = jsonMessage_1.getJSONObject(0);
                }
                if (sendMessage_1 != null){
                    // 메시지 발송
                    msgSendServer.send_alarm_json_array(jsonMessage_1);
                }
            }
            
            if (nTotalPage == 0)
            {
                common.log(module_name,"PushAlaram_Add_List_Queue: TOTAL_PAGE : " + nTotalPage);
            }
            else
            {
                int aPage = 1;
                i = 0;
                int nFirstTimeOut = 10 * 1000;
	            int nAddTimeOut = 0;
	            

                for(i = 1; i <= nTotalPage; i++) {
                    //setTimeout(function() { EmcWorkScheduleItem_Alarm(aEmcID, aPage); },(200*(aPage-1)));
                    // var jsonMessage = { "WORK_DATE":WORK_DATE, "NEED_ID":NEED_ID, "WORK_ID":WORK_ID, "I_ID":aI_ID, "PAGE":i, "VERSION_NAME":jsonData.ALARM_VERSION }
                    JSONObject jsonMessage = new JSONObject();
                    jsonMessage.put("WORK_DATE",WORK_DATE);
                    jsonMessage.put("NEED_ID",NEED_ID);
                    jsonMessage.put("I_ID",aI_ID);
                    jsonMessage.put("PAGE",i);
                    jsonMessage.put("WORK_ID",WORK_ID);
                    jsonMessage.put("VERSION_NAME",ALARM_VERSION);
                    int nAlarmTimeOut = i * 3 * 1000;	
                    nAlarmTimeOut = nAlarmTimeOut + nFirstTimeOut;
                    /*
                    if ((i%10)==0){
                        nAddTimeOut = i * 20 * 1000;
                    }
                    */
                    ReqWorkRunable reqWorkRunable = new ReqWorkRunable(jsonMessage);
                    common.setTimeout(reqWorkRunable, nAlarmTimeOut+nAddTimeOut);
                    common.log(module_name,"reqWorkRunable start timeout : "+ nAlarmTimeOut+nAddTimeOut);
                    // alarmReq.send_alarm_req(jsonMessage);
                };
            }
        }
        catch (Exception ex)
        {
            common.log(module_name,"PushAlaram_Add_Queue error : " + ex.getMessage());		            
        }	
        return result;
    }

    public class ReqWorkRunable implements Runnable{

        String module_name = "ReqWorkRunable";
        Object jsmMsg = null;
        AlarmReq alarmReq = new AlarmReq();

        public ReqWorkRunable(Object msg) {
            jsmMsg = msg;
        }

        @Override
        public void run() {
            System.out.println(jsmMsg);        
            alarmReq.send_alarm_req((JSONObject)jsmMsg);
        }   
    }
}   