package com.veteranscout.cmsweb.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.Iterator;
import java.util.Map;
import java.util.Base64;
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

import org.slf4j.Logger; 
import org.slf4j.LoggerFactory;

@Component
public class MmsUtil {  

    CommonUtil common;
    String module_name = "MmsUtil";

    public MmsUtil(){
        common = new CommonUtil();
    }

    public String SendOnlineRes(JSONObject obj0_0){
        String result = "";
        try
        {
            
            String SMS_TEL = obj0_0.getString("TEL");
            String M_TITLE = "문의내용에 대한 답변";
            String MMS_MSG = "문의제목 : " + obj0_0.getString("REQ_TITLE");
            MMS_MSG += "에 대한 답변 내용을 플랫포머스에서 보내드립니다. ";
            MMS_MSG += "답변내용 : " + obj0_0.getString("RES_CONTENT");

            JSONObject obj = new JSONObject();
            obj.put("TYPE","SMS");
            obj.put("R_TEL_NO",SMS_TEL);
            obj.put("S_TEL_NO","0221886784");
            obj.put("S_DATE",common.now_date_str());
            obj.put("TITLE","문의 내용 답변");
            obj.put("MESSAGE",MMS_MSG);
            obj.put("REFER","http://vendor.veteranscout.co.kr");
            String strSQL = "{call sc_tran_PROC_veteran(?,?,?,?,?,?,?)}";
            JSONArray jsonResult = common.execute_query_mssql_mms(strSQL, obj);
            result = common.get_data_ok(jsonResult);

            // String strSQL = "";
            // strSQL = "exec sc_tran_PROC_veteran 'SMS','"+SMS_TEL+"','0221886784','"+common.now_date_str()+"','"+M_TITLE+"','"+MMS_MSG+"','http://vendor.veteranscout.co.kr',1 ";            
            // JSONArray jsonResult = common.execute_query_mssql(strSQL);
            // result = common.get_data_ok(jsonResult);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public String SendQuestionRes(JSONObject obj0_0){
        String result = "";
        try
        {
            
            String SMS_TEL = obj0_0.getString("TEL");
            String M_TITLE = obj0_0.getString("TITLE")+"의 답변";
            String MMS_MSG = "[문의제목]"+obj0_0.getString("TITLE")+"에 대하여 답변 보내드립니다.\n"+
				  "[답변내용]"+obj0_0.getString("RES_CONTENT");

            

            JSONObject obj = new JSONObject();
            obj.put("TYPE","SMS");
            obj.put("R_TEL_NO",SMS_TEL);
            obj.put("S_TEL_NO","0221886784");
            obj.put("S_DATE",common.now_date_str());
            obj.put("TITLE","문의 제목 답변");
            obj.put("MESSAGE",MMS_MSG);
            obj.put("REFER","http://vendor.veteranscout.co.kr");
            String strSQL = "{call sc_tran_PROC_veteran(?,?,?,?,?,?,?)}";
            JSONArray jsonResult = common.execute_query_mssql_mms(strSQL, obj);
            result = common.get_data_ok(jsonResult);
            
            // String strSQL = "";
            // strSQL = "exec sc_tran_PROC_veteran 'SMS','"+SMS_TEL+"','0221886784','"+common.now_date_str()+"','"+M_TITLE+"','"+MMS_MSG+"','http://vendor.veteranscout.co.kr',1 ";
            // JSONArray jsonResult = common.execute_query_mssql(strSQL);
            // result = common.get_data_ok(jsonResult);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }


}