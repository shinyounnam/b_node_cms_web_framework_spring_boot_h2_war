package com.veteranscout.cmsweb.alarm;

import com.veteranscout.cmsweb.util.*;
import org.json.*;
import org.springframework.beans.factory.annotation.Autowired;

public class AlarmNoticeServer {

    
    CommonUtil common;
    String module_name = "AlarmNoticeServer";    
    AlarmNotice alarmNotice;

    public AlarmNoticeServer(){
        common = new CommonUtil();
        alarmNotice = new AlarmNotice();
    }

    public String send_alarm_json_object(JSONObject jsonObject){
        String result = "";        
        try
        {
            result = PushAlaram_Add_List_Queue(jsonObject);
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
            // common.log(module_name,"PushAlaram_Add_List_Queue_Resend Start : " + JSON.stringify(jsonData) + " nTimeOut : " + nTimeOut);
            // setTimeout(function() { PushAlaram_Add_List_Queue_Resend(jsonData); },nTimeOut);
            String MSG_CORP_ID = common.get_json_value(jsonData,"MSG_CORP_ID");
            if (MSG_CORP_ID.equals(""))
            {
                MSG_CORP_ID = "0";
            }
            
            String N_ID = common.get_json_value(jsonData,"N_ID");
            String WORK_DATE = common.get_json_value(jsonData,"WORK_DATE");
            String GBN = common.get_json_value(jsonData,"GBN");
            String TEST_YN = common.get_json_value(jsonData,"TEST_YN");
            if (TEST_YN.equals(""))
            {
                TEST_YN = "N";
            }
            String AREA_CODE1 = common.get_json_value(jsonData,"AREA_CODE1");            
            common.log(module_name,"N_ID : "+N_ID);
            common.log(module_name,"GBN : "+GBN);
            common.log(module_name,"WORK_DATE : "+WORK_DATE);
            common.log(module_name,"AREA_CODE1 : "+AREA_CODE1);

            String strSQL = "";
            strSQL = "CALL SP_M_WORK_REQUEST_VETERAN_NOTICE_COUNT_1_1_92_1('"+GBN+"','"+N_ID+"','"+MSG_CORP_ID+"', '"+WORK_DATE+"','"+TEST_YN+"','"+AREA_CODE1+"')"; 
            common.system_log("strSQL : " + strSQL);
            common.log(module_name,"strQuery : " + strSQL);
            JSONArray jsonTotalPage = common.execute_query_commit(strSQL);
            result = common.get_data_ok(jsonTotalPage);
            int i = 0;						
            String send_message = "";
            int nTotalPage = common.get_json_array_value_int(jsonTotalPage,"TOTAL_PAGE");
            String aI_ID = common.get_json_array_value(jsonTotalPage,"I_ID");
            String aMSG_ID = common.get_json_array_value(jsonTotalPage,"MSG_ID");
            //WORK_DATE = jsonTotalPage[0][0].WORK_DATE;
            //jsonData.WORK_DATE;
            common.log(module_name,"I_ID : "+aI_ID);
            common.system_log("I_ID : "+aI_ID);        

            if (nTotalPage == 0)
            {
                common.log(module_name,"PushAlaram_Add_List_Queue: TOTAL_PAGE : " + nTotalPage);
            }
            else
            {
                int aPage = 1;                                
                JSONObject jsonMessage = null;
                for(i = 1; i <= nTotalPage; i++) {
                    //setTimeout(function() { EmcWorkScheduleItem_Alarm(aEmcID, aPage); },(200*(aPage-1)));
                    // jsonMessage = { "WORK_DATE":WORK_DATE, "N_ID":N_ID,  "MSG_ID":aMSG_ID, "GBN":GBN, "I_ID":aI_ID, "PAGE":i }
                    jsonMessage = new JSONObject();
                    jsonMessage.put("WORK_DATE",WORK_DATE);
                    jsonMessage.put("N_ID",N_ID);
                    jsonMessage.put("MSG_ID",aMSG_ID);
                    jsonMessage.put("GBN",GBN);
                    jsonMessage.put("I_ID",aI_ID);
                    jsonMessage.put("PAGE",i);

                    result = alarmNotice.send_alarm_json_object(jsonMessage);
                    
                };
            }
        }
        catch (Exception ex)
        {
            common.log(module_name,"PushAlaram_Add_Queue error : " + ex.getMessage());		            
        }	
        return result;
    }

}