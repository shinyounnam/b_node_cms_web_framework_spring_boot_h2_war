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


public class AlarmNoticeRunableMms {
    
    CommonUtil common;
    String module_name = "AlarmNoticeRunableMms";
    Object jsmMsg = "";

    public AlarmNoticeRunableMms() {
        common = new CommonUtil();
    }

    public JSONArray SendMms(JSONObject jsonData){
        JSONArray jsonArray = null;
        try
        {
            String M_R_TEL_NO = "";
            int M_FILE_ID = 0;
            String MMS_MSG = "";
            String M_TITLE = jsonData.getString("TITLE");
            String strSQL = "sc_tran_PROC_veteran";                    
            String M_GBN = jsonData.getString("GBN");
            
            M_R_TEL_NO = jsonData.getString("TEL_NO");
            // console.log("M_R_TEL_NO : "+jsonResult.TEL_NO);
            MMS_MSG = jsonData.getString("CONTENT");
            common.system_log("MMS_MSG : " +MMS_MSG);

            /*
            request.input('TYPE', "MMS");
            request.input('R_TEL_NO', M_R_TEL_NO);
            request.input('S_TEL_NO', "0221886784");
            request.input('S_DATE', common.now_time_string());
            request.input('TITLE',  jsonResult.TITLE);
            request.input('MESSAGE', MMS_MSG);                    
            request.input('REFER', M_GBN);
            request.input('EVENT_ID', jsonData.N_ID);
            request.input('NAME', jsonData.NAME);
            request.input('ADDR1', jsonData.ADDR1);
            request.input('APP_VERSION', jsonData.APP_VERSION);            
            request.input('APP_VERSION_DATE', jsonData.APP_VERSION_DATE);
            request.input('LST_LOGIN_DATE', jsonData.LST_LOGIN_DATE);
            request.input('LINK_TYPE', jsonData.LINK_TYPE);
            request.input('AREA_CODE1', jsonData.AREA_CODE1);
            if (M_FILE_ID != 0)
            {
                request.input('FILE_ID', M_FILE_ID);
            }
            request.input('LOGBIT',1);
            */
            JSONObject obj = new JSONObject();
            if (jsonData.getInt("SMS_FILE_ID") == 0)
            {
                /*
                strSQL = "exec sc_tran_PROC_veteran_date 'MMS','"+M_R_TEL_NO+"','0221886784','"+common.now_time_string()+
                            "','"+M_TITLE+"','"+MMS_MSG+"','"+M_GBN+"','"+jsonData.getInt("N_ID")+"','"+
                            jsonData.getString("NAME")+"','"+jsonData.getString("ADDR1")+"','"+
                            jsonData.getString("APP_VERSION")+"','"+jsonData.getString("APP_VERSION_DATE")+"','"+
                            jsonData.getString("APP_VERSION_DATE")+"','"+jsonData.getString("LST_LOGIN_DATE")+"','"+
                            jsonData.getString("LINK_TYPE")+"','"+jsonData.getInt("AREA_CODE1")+"',1 ";
                */
                
                obj.put("TYPE","MMS");
                obj.put("R_TEL_NO",M_R_TEL_NO);
                obj.put("S_TEL_NO","0221886784");
                obj.put("S_DATE",common.now_date_str());
                obj.put("TITLE",M_TITLE);
                obj.put("MESSAGE",MMS_MSG);
                obj.put("REFER",M_GBN);
                obj.put("EVENT_ID",common.get_json_value(jsonData,"N_ID"));
                obj.put("NAME",common.get_json_value(jsonData,"NAME"));
                obj.put("ADDR1",common.get_json_value(jsonData,"ADDR1"));
                obj.put("APP_VERSION",common.get_json_value(jsonData,"APP_VERSION"));
                obj.put("APP_VERSION_DATE",common.get_json_value(jsonData,"APP_VERSION_DATE"));
                obj.put("LST_LOGIN_DATE",common.get_json_value(jsonData,"LST_LOGIN_DATE"));
                obj.put("LINK_TYPE",common.get_json_value(jsonData,"LINK_TYPE"));
                obj.put("AREA_CODE1",common.get_json_value(jsonData,"AREA_CODE1"));
                strSQL = "{call sc_tran_PROC_veteran_date(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";                
                /*
                stmt.setString(1,get_json_value(obj,"TYPE"));               stmt.setString(2,get_json_value(obj,"R_TEL_NO"));
                stmt.setString(3,get_json_value(obj,"S_TEL_NO"));           stmt.setString(4,get_json_value(obj,"S_DATE"));
                stmt.setString(5,get_json_value(obj,"TITLE"));              stmt.setString(6,get_json_value(obj,"MESSAGE"));
                stmt.setString(7,get_json_value(obj,"REFER"));              stmt.setString(8,get_json_value(obj,"EVENT_ID"));            
                stmt.setString(9,get_json_value(obj,"NAME"));               stmt.setString(10,get_json_value(obj,"ADDR1"));            
                stmt.setString(11,get_json_value(obj,"APP_VERSION"));       stmt.setString(12,get_json_value(obj,"APP_VERSION_DATE"));            
                stmt.setString(13,get_json_value(obj,"LST_LOGIN_DATE"));    stmt.setString(14,get_json_value(obj,"LINK_TYPE"));            
                stmt.setString(15,get_json_value(obj,"AREA_CODE1"));            
                if(obj.has("FILE_ID")){
                    stmt.setInt(16,get_json_value_int(obj,"FILE_ID"));            
                }
                */
            }
            else
            {
                /*
                M_FILE_ID = jsonData.getInt("SMS_FILE_ID");
                strSQL = "sc_tran_PROC_veteran_file_date 'MMS','"+M_R_TEL_NO+"','0221886784','"+common.now_time_string()+
                "','"+M_TITLE+"','"+MMS_MSG+"','"+M_GBN+"','"+jsonData.getInt("N_ID")+"','"+
                jsonData.getString("NAME")+"','"+jsonData.getString("ADDR1")+"','"+
                jsonData.getString("APP_VERSION")+"','"+jsonData.getString("APP_VERSION_DATE")+"','"+
                jsonData.getString("APP_VERSION_DATE")+"','"+jsonData.getString("LST_LOGIN_DATE")+"','"+
                jsonData.getString("LINK_TYPE")+"','"+jsonData.getInt("AREA_CODE1")+"',"+M_FILE_ID+",1 ";
                */
                obj.put("TYPE","MMS");
                obj.put("R_TEL_NO",M_R_TEL_NO);
                obj.put("S_TEL_NO","0221886784");
                obj.put("S_DATE",common.now_date_str());
                obj.put("TITLE",M_TITLE);
                obj.put("MESSAGE",MMS_MSG);
                obj.put("REFER",M_GBN);
                obj.put("EVENT_ID",common.get_json_value(jsonData,"N_ID"));
                obj.put("NAME",common.get_json_value(jsonData,"NAME"));
                obj.put("ADDR1",common.get_json_value(jsonData,"ADDR1"));
                obj.put("APP_VERSION",common.get_json_value(jsonData,"APP_VERSION"));
                obj.put("APP_VERSION_DATE",common.get_json_value(jsonData,"APP_VERSION_DATE"));
                obj.put("LST_LOGIN_DATE",common.get_json_value(jsonData,"LST_LOGIN_DATE"));
                obj.put("LINK_TYPE",common.get_json_value(jsonData,"LINK_TYPE"));
                obj.put("AREA_CODE1",common.get_json_value(jsonData,"AREA_CODE1"));
                obj.put("FILE_ID",common.get_json_value(jsonData,"SMS_FILE_ID"));
                strSQL = "{call sc_tran_PROC_veteran_file_date(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";                
                /*
                stmt.setString(1,get_json_value(obj,"TYPE"));               stmt.setString(2,get_json_value(obj,"R_TEL_NO"));
                stmt.setString(3,get_json_value(obj,"S_TEL_NO"));           stmt.setString(4,get_json_value(obj,"S_DATE"));
                stmt.setString(5,get_json_value(obj,"TITLE"));              stmt.setString(6,get_json_value(obj,"MESSAGE"));
                stmt.setString(7,get_json_value(obj,"REFER"));              stmt.setString(8,get_json_value(obj,"EVENT_ID"));            
                stmt.setString(9,get_json_value(obj,"NAME"));               stmt.setString(10,get_json_value(obj,"ADDR1"));            
                stmt.setString(11,get_json_value(obj,"APP_VERSION"));       stmt.setString(12,get_json_value(obj,"APP_VERSION_DATE"));            
                stmt.setString(13,get_json_value(obj,"LST_LOGIN_DATE"));    stmt.setString(14,get_json_value(obj,"LINK_TYPE"));            
                stmt.setString(15,get_json_value(obj,"AREA_CODE1"));            
                if(obj.has("FILE_ID")){
                    stmt.setInt(16,get_json_value_int(obj,"FILE_ID"));            
                }
                */
                
            }
            
            jsonArray = common.execute_query_mssql_mms_date(strSQL,obj);            
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return jsonArray;
    }

}