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


public class AlarmSendMms {


    CommonUtil common;
    String module_name = "AlarmSend";
    
    public AlarmSendMms(){
        common = new CommonUtil();
    }

    public String SendReq(JSONObject jsonData){
        String result = "";
        try {

            String smsMsg = "";
            smsMsg = "[알림]"+common.get_json_value(jsonData,"CORP_NAME")+"에서 근무요청 드립니다.\n"+
            "안녕하세요. " + common.get_json_value(jsonData,"EMP_NAME") + "님! 여기는 "+
            common.get_json_value(jsonData,"CORP_NAME")+"입니다. "+
            common.get_json_value(jsonData,"WORK_DATE") + " " + 
            common.get_json_value(jsonData,"SITE_NAME") + " 현장에서 ["+ 
            common.get_json_value(jsonData,"JOB_GBN_NM") + "] "+
            common.get_json_value(jsonData,"JOB_TYPE_NM") + " / " + 
            common.get_json_value(jsonData,"WORK_PAY") +"원의 조건으로 근무하시기를 요청드립니다."+
            "빠른 회신 부탁드립니다.";
            common.system_log("smsMsg : " +smsMsg + " EMP_TEL:"+common.get_json_value(jsonData,"EMP_TEL"));
            common.log(module_name,"smsMsg : " +smsMsg + " EMP_TEL:"+common.get_json_value(jsonData,"EMP_TEL"));
            /*
            request.input('TYPE', "MMS");
            request.input('R_TEL_NO', jsonData.EMP_TEL);
            request.input('S_TEL_NO', "0221886784");
            request.input('S_DATE', common.now_date_str());
            request.input('TITLE', "메시지");
            request.input('MESSAGE', smsMsg);
            request.input('REFER', "http://vendor.veteranscout.co.kr");
            request.input('LOGBIT',1);
            
            request.execute("sc_tran_PROC_veteran",(err,result) =>{
            */
            /*
            
            stmt.setString(1,get_json_value(obj,"TYPE"));
            stmt.setString(2,get_json_value(obj,"R_TEL_NO"));
            stmt.setString(3,get_json_value(obj,"S_TEL_NO"));
            stmt.setString(4,get_json_value(obj,"S_DATE"));
            stmt.setString(5,get_json_value(obj,"TITLE"));
            stmt.setString(6,get_json_value(obj,"MESSAGE"));
            stmt.setString(7,get_json_value(obj,"REFER"));   
            */
            JSONObject obj = new JSONObject();
            obj.put("TYPE","MMS");
            obj.put("R_TEL_NO",common.get_json_value(jsonData,"EMP_TEL"));
            obj.put("S_TEL_NO","0221886784");
            obj.put("S_DATE",common.now_date_str());
            obj.put("TITLE","메시지");
            obj.put("MESSAGE",smsMsg);
            obj.put("REFER","http://vendor.veteranscout.co.kr");
            String strSQL = "{call sc_tran_PROC_veteran(?,?,?,?,?,?,?)}";
            JSONArray jsonResult = common.execute_query_mssql_mms(strSQL, obj);
            result = common.get_data_ok(jsonResult);
            /*
            String strSQL = "exec sc_tran_PROC_veteran 'MMS','"+common.get_json_value(jsonData,"EMP_TEL")+
                            "','0221886784','"+common.now_date_str()+"','메시지','"+smsMsg+
                            "','http://vendor.veteranscout.co.kr',1 ";
            common.system_log("sc_tran_PROC_veteran EMP_TEL : " + common.get_json_value(jsonData,"EMP_TEL") + " EMP_ID : " + common.get_json_value(jsonData,"EMP_ID"));
            JSONArray jsonResult = common.execute_query_mssql(strSQL);                                    
            result = common.get_data_ok(jsonResult);
            */
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return result; 
    }

    public String SendVcn(JSONObject jsonData){
        String result = "";
        try {

            String smsMsg = "";
            smsMsg = "[알림]"+common.get_json_value(jsonData,"CORP_NAME")+"에서 근무예약이 취소됨을 알려 드립니다.\n"+
                    "안녕하세요. " + common.get_json_value(jsonData,"EMP_NAME") + "님! 여기는 "+
                    common.get_json_value(jsonData,"CORP_NAME")+"입니다. "+
                    common.get_json_value(jsonData,"WORK_DATE") + " " + 
                    common.get_json_value(jsonData,"SITE_NAME") + " 현장에서 ["+ 
                    common.get_json_value(jsonData,"JOB_GBN_NM") + "] "+
                    common.get_json_value(jsonData,"JOB_TYPE_NM") + " / " + 
                    common.get_json_value(jsonData,"WORK_PAY") +"원의 조건으로 근무하시는 것에 대한 예약이 취소됨을 알려드립니다. "+
                    "빠른 회신 부탁드립니다.";
            common.system_log("smsMsg : " +smsMsg + " EMP_TEL : " + common.get_json_value(jsonData,"EMP_TEL"));
            common.log(module_name,"smsMsg : " +smsMsg + " EMP_TEL:"+common.get_json_value(jsonData,"EMP_TEL"));
            /*
            request.input('TYPE', "MMS");
            request.input('R_TEL_NO', jsonData.EMP_TEL);
            request.input('S_TEL_NO', "0221886784");
            request.input('S_DATE', common.now_date_str());
            request.input('TITLE', "메시지");
            request.input('MESSAGE', smsMsg);
            request.input('REFER', "http://vendor.veteranscout.co.kr");
            request.input('LOGBIT',1);
            console.log("sc_tran_PROC_veteran EMP_TEL : " + jsonData.EMP_TEL + " EMP_ID : " + jsonData.EMP_ID);
            request.execute("sc_tran_PROC_veteran",(err,result) =>{
            */
            JSONObject obj = new JSONObject();
            obj.put("TYPE","MMS");
            obj.put("R_TEL_NO",common.get_json_value(jsonData,"EMP_TEL"));
            obj.put("S_TEL_NO","0221886784");
            obj.put("S_DATE",common.now_date_str());
            obj.put("TITLE","메시지");
            obj.put("MESSAGE",smsMsg);
            obj.put("REFER","http://vendor.veteranscout.co.kr");
            String strSQL = "{call sc_tran_PROC_veteran(?,?,?,?,?,?,?)}";
            JSONArray jsonResult = common.execute_query_mssql_mms(strSQL, obj);
            result = common.get_data_ok(jsonResult);
            /*
            String strSQL = "exec sc_tran_PROC_veteran 'MMS','"+common.get_json_value(jsonData,"EMP_TEL")+
                            "','0221886784','"+common.now_date_str()+"','메시지','"+smsMsg+
                            "','http://vendor.veteranscout.co.kr',1 ";
            common.system_log("sc_tran_PROC_veteran EMP_TEL : " + common.get_json_value(jsonData,"EMP_TEL") + " EMP_ID : " + common.get_json_value(jsonData,"EMP_ID"));
            JSONArray jsonResult = common.execute_query_mssql(strSQL);                                    
            result = common.get_data_ok(jsonResult);
            */
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return result; 
    }

    public String SendCan(JSONObject jsonData){
        String result = "";
        try {

            String smsMsg = "";
            smsMsg = "[알림]"+common.get_json_value(jsonData,"CORP_NAME")+"에서 근무예약이 취소됨을 알려 드립니다.\n"+
                    "안녕하세요. " + common.get_json_value(jsonData,"EMP_NAME") + "님! 여기는 "+
                    common.get_json_value(jsonData,"CORP_NAME")+"입니다. "+
                    common.get_json_value(jsonData,"WORK_DATE") + " " + 
                    common.get_json_value(jsonData,"SITE_NAME") + " 현장에서 ["+ 
                    common.get_json_value(jsonData,"JOB_GBN_NM") + "] "+
                    common.get_json_value(jsonData,"JOB_TYPE_NM") + " / " + 
                    common.get_json_value(jsonData,"WORK_PAY") +"원의 조건으로 근무하시는 것에 대한 예약이 취소됨을 알려드립니다. "+
                    "빠른 회신 부탁드립니다.";
            common.system_log("smsMsg : " +smsMsg + "EMP_TEL : " + common.get_json_value(jsonData,"EMP_TEL"));
            common.log(module_name,"smsMsg : " +smsMsg + " EMP_TEL:"+common.get_json_value(jsonData,"EMP_TEL"));
            /*
            request.input('TYPE', "MMS");
            request.input('R_TEL_NO', jsonData.EMP_TEL);
            request.input('S_TEL_NO', "0221886784");
            request.input('S_DATE', common.now_date_str());
            request.input('TITLE', "메시지");
            request.input('MESSAGE', smsMsg);
            request.input('REFER', "http://vendor.veteranscout.co.kr");
            request.input('LOGBIT',1);
            console.log("sc_tran_PROC_veteran EMP_TEL : " + jsonData.EMP_TEL + " EMP_ID : " + jsonData.EMP_ID);
            request.execute("sc_tran_PROC_veteran",(err,result) =>{
            */
            JSONObject obj = new JSONObject();
            obj.put("TYPE","MMS");
            obj.put("R_TEL_NO",common.get_json_value(jsonData,"EMP_TEL"));
            obj.put("S_TEL_NO","0221886784");
            obj.put("S_DATE",common.now_date_str());
            obj.put("TITLE","메시지");
            obj.put("MESSAGE",smsMsg);
            obj.put("REFER","http://vendor.veteranscout.co.kr");
            String strSQL = "{call sc_tran_PROC_veteran(?,?,?,?,?,?,?)}";
            JSONArray jsonResult = common.execute_query_mssql_mms(strSQL, obj);
            result = common.get_data_ok(jsonResult);
            /*
            String strSQL = "exec sc_tran_PROC_veteran 'MMS','"+common.get_json_value(jsonData,"EMP_TEL")+
                            "','0221886784','"+common.now_date_str()+"','메시지','"+smsMsg+
                            "','http://vendor.veteranscout.co.kr',1 ";
            common.system_log("sc_tran_PROC_veteran EMP_TEL : " + common.get_json_value(jsonData,"EMP_TEL") + " EMP_ID : " + common.get_json_value(jsonData,"EMP_ID"));
            JSONArray jsonResult = common.execute_query_mssql(strSQL);                                    
            result = common.get_data_ok(jsonResult);    
            */
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return result; 
    }

}