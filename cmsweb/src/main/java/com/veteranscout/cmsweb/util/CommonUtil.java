package com.veteranscout.cmsweb.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Iterator;
import java.util.Map;
import java.util.Date;
import java.util.HashMap;

import org.apache.tomcat.util.codec.binary.Base64;
import org.json.*;
import java.net.*;
import java.io.*;
import java.sql.*;
import java.text.SimpleDateFormat;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.*;
import javax.servlet.http.Cookie;

import java.security.*;
import java.nio.charset.*;

import org.slf4j.Logger; 
import org.slf4j.LoggerFactory;
import org.apache.commons.lang3.StringUtils;

public class CommonUtil {  

    public String node_alarm_host = "127.0.0.1"; 
    
    public int node_api_server_port = 100;
    public int node_api_server_port1 = 101;
    public int node_api_server_port2 = 102;
    public int node_api_server_port3 = 103;
    public int node_api_server_port4 = 104;
    public int node_api_server_port5 = 105;
    public int node_api_server_port6 = 106;
    public int node_api_server_port7 = 107;
    public int node_api_server_port8 = 108;
    public int node_api_server_port9 = 109;
    public int node_api_server_port10 = 110;
    public int node_api_server_port11 = 111;
    public int node_api_server_port12 = 112;
    public int node_api_server_port13 = 113;
    public int node_api_server_port14 = 114;
    public int node_api_server_port15 = 115;
    public int node_api_server_port16 = 116;
    public int node_api_server_port17 = 117;
    public int node_api_server_port18 = 118;
    public int node_api_server_port19 = 119;

    public int node_work_alarm_port=1700;
    public int node_work_alarm_res_port=1701;

    public int node_alarm_resend_port = 1800;
    public int node_alarm_reject_port = 1801;
    public int node_mongo_data_delete_port = 1802;
    public int node_alarm_schedule_port = 1803;

    public int node_alarm_server_port = 1901;

    public int node_alarm_server_port1 = 1903;
    public int node_alarm_server_port2 = 1904;
    public int node_alarm_server_port3 = 1905;

    public int node_msg_server_port = 2001;
    public int node_msg_server_port1 = 2002;
    public int node_msg_server_port2 = 2003;
    public int node_msg_server_port3 = 2004;

    public int node_alarm_show_all_server_port = 2101;

    public int node_veteran_service_alarm_port=1702;

    public int node_veteran_service_alarm_port1=1703;
    public int node_veteran_service_alarm_port2=1704;

    public int node_veteran_service_alarm_d_port=1710;
    public int node_veteran_service_alarm_d_port1=1711;
    public int node_veteran_service_alarm_d_port2=1712;
    public int node_veteran_service_alarm_d_port3=1713;

    public int node_vendor_service_alarm_port=3102;

    public int node_vendor_service_alarm_port1=3103;
    public int node_vendor_service_alarm_port2=3104;

    public int node_vendor_service_alarm_d_port=3110;
    public int node_vendor_service_alarm_d_port1=3111;
    public int node_vendor_service_alarm_d_port2=3112;
    public int node_vendor_service_alarm_d_port3=3113;

    public int node_alarm_wather_all_server_port=1410;
    public int node_show_alarm_res_port = 0;
    public int node_show_emc_alarm_res_port = 0;

    public int alarm_work_msg_type = 1;
    public int alarm_work_res_msg_type = 2;
    public int alarm_work_item_emc_msg_type = 3;
    public int alarm_work_emc_msg_type = 4;
    public int alarm_work_emc_res_msg_type = 5;
    public int alarm_show_msg_type = 6;
    public int alarm_show_res_msg_type = 7;
    public int alarm_show_emc_msg_type = 8;
    public int alarm_show_emc_res_msg_type = 9;
    public int msg_corp_msg_type = 10;
    public int msg_emc_msg_type = 11;
    public int msg_emp_msg_type = 12;
    public int alarm_notice_msg_type = 15;
    public int msg_work_msg_type = 20;

    public String vendor_api_key = "AAAAI-J-4p8:APA91bEOXdm1MzRV_uTaVZzTfMx2pqzAGT0dIQ6CfonioNZaQOJvKpGlnyu4dl_LJ2B2Q16NUuQMfx9SB2niW3m-Y1_XyyUk_Y94XVKo26JWJQ9iT_g4aFnNEL3wsj8KBBxWIsFGC6Tr4VCsmBQRu0ouAyjfdbkT-w";
    public String user_api_key = "AAAA_l9a9cY:APA91bHMh_cVyTUKwlmkgVqbhE0XVkIvzrWm6u8VZkLb4oeflOcPC_eX3EwgDQ6ss4ugKmJIrm3izoGkubqS25p15uxNMoVUwpLz3UiBevemk3XamgPXA9fEqOqfajF5xXN2MH3_EZPcGLBPOu1hdx2yk5gDyfF1WQ";
    public String vendor_web_api_key = "AAAAUgt3S1s:APA91bGg9WstTacPtadxUhmbsEweXM32pWwpMTJmrBWyc1_xjLlg-0YMB2ChadlT7awoEYTPnlKqje_ZetuQz1grvjk5dmPKuhmbknp6aommYAQMHrwIEi73UKghW0Pa1wAM7su0OLz3";


    public String mysql_driver = "com.mysql.cj.jdbc.Driver";
    public String mysql_db_url = "jdbc:mysql://210.207.99.64/n_platform?characterEncoding=UTF-8&serverTimezone=UTC";
    public String mysql_user_name = "mysql";
    public String mysql_user_password = "qpxpfkdtmzkdnxm!";

    public String mssql_driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    public String mssql_db_url = "jdbc:sqlserver://222.237.77.132:1433;databaseName=emma_1;user=platform;password=vmffotvha!@#.1;";
    public String mssql_user_name = "scoutdba";
    public String mssql_user_password = "#@!3rdheyday";

    public String monk_db_host = "210.207.99.63";

    public String images_dir_path = "/n_platform/spring_boot/b_node_image_service_spring_boot_files";
    public String dir_path = "/n_platform/spring_boot/b_node_vendor_web_spring_boot_files";
    //exports.cs_email = "cs@veteranscout.co.kr";
    public String cs_email = "cs@veteranscout.co.kr";
    public String r_cs_email = "veteran_team@scout.co.kr";

    public String g_images_url = "https://images.veteranscout.co.kr";
    public String g_innopay_url = "http://pay.veteranscout.co.kr/innopay/vendor_web";
    public String g_danal_url = "http://pay.veteranscout.co.kr/danal/vendor_web";
    public String g_file_path = "/n_platform/spring_boot/b_node_cms_web_framework_spring_boot_files";
    public String cms_dir = "/n_platform/spring_boot/b_node_cms_web_framework_spring_boot_files";
	public String g_cms_file_path = "/n_platform/spring_boot/b_node_cms_web_framework_spring_boot_files";
	public String g_xls_url = "https://xls.veteranscout.co.kr/cms_web";

	private String cookie_key = "Bar12345Bar12345"; // 128 bit key
    private String cookie_initVector = "RandomInitVector"; // 16 bytes IV
    private String cookie_name = "cmsweb_user";

	public String ALARM_API_KEY = "54697c49-9cac-11e6-9095-001279d65bd1";
    public String ALARM_URL = "http://alarm1.veteranscout.co.kr";
    public String ALARM_HOST = "alarm1.veteranscout.co.kr";

    public String SEND_ALARM_HOST = "127.0.0.1";
    public int ALARM_PORT = 121;
    public int ALARM_REQ_PORT = 122;
    public int MSG_PORT = 123;
    public int NOTICE_PORT = 124;
    private String module_name = "CommonUtil";
	public String g_log_path = "/n_platform/spring_boot/b_node_cms_web_framework_spring_boot_h2_war/cmsweb/logs/";
    public String g_sms_tel = "0221886784";
    //public String g_email_host = "mlinux.scout.co.kr";
    public String g_email_host = "222.237.77.143";
    public String g_email_url = "https://cms1.veteranscout.co.kr";
    private static final Logger logger = LoggerFactory.getLogger(CommonUtil.class);

    public String get_error_message(String err){
        JSONObject jsonObject = new JSONObject();
        try
        {
            jsonObject.put("result","error");
            jsonObject.put("messsage",err);
        }
        catch(JSONException e)
        {
            e.printStackTrace();
        }
        
        return jsonObject.toString();
    }

    public String get_json_string(String jsonString) {
        try
        {
            jsonString = jsonString.replace("=","");
            jsonString = jsonString.substring(1,jsonString.length());
            jsonString = jsonString.substring(0,jsonString.length()-1);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return jsonString;
    }

    public String get_json_value(JSONObject jsonObject, String strKey) 
    {
        String result = "";
        try
        {
            if(jsonObject.has(strKey))
            {
                if(jsonObject.get(strKey)==JSONObject.NULL){
                    result = "";
                }
                else{
                    result = jsonObject.get(strKey).toString();
                }
                
            }
        }
        catch(JSONException e)
        {                     
            e.printStackTrace();
        }
        return result;
    }
    
    public JSONObject put_json_obj_string(String strKey, JSONObject jsonPutObject, JSONObject jsonData) 
    {        
        try
        {
            if(jsonPutObject.has(strKey))
            {
                jsonPutObject.remove(strKey);                
            }
            jsonPutObject.put(strKey,jsonData.get(strKey).toString());
        }
        catch(JSONException e)
        {
            e.printStackTrace();
        }
        return jsonPutObject;
    }

    public JSONObject put_json_obj_int(String strKey, JSONObject jsonPutObject, JSONObject jsonData) 
    {        
        try
        {
            if(jsonPutObject.has(strKey))
            {
                jsonPutObject.remove(strKey);                
            }
            jsonPutObject.put(strKey,Integer.parseInt(jsonData.get(strKey).toString()));
        }
        catch(JSONException e)
        {
            e.printStackTrace();
        }
        return jsonPutObject;
    }

    public JSONObject put_json_obj_double(String strKey, JSONObject jsonPutObject, JSONObject jsonData) 
    {        
        try
        {
            if(jsonPutObject.has(strKey))
            {
                jsonPutObject.remove(strKey);                
            }
            jsonPutObject.put(strKey,jsonData.getDouble(strKey));
        }
        catch(JSONException e)
        {
            e.printStackTrace();
        }
        return jsonPutObject;
    }

    public JSONObject put_json_string(String strKey, JSONObject jsonPutObject, String jsonData) 
    {        
        try
        {
            if(jsonPutObject.has(strKey))
            {
                jsonPutObject.remove(strKey);                
            }
            jsonPutObject.put(strKey,jsonData);
        }
        catch(JSONException e)
        {
            e.printStackTrace();
        }
        return jsonPutObject;
    }

    public JSONObject put_json_int(String strKey, JSONObject jsonPutObject, int jsonData) 
    {        
        try
        {
            if(jsonPutObject.has(strKey))
            {
                jsonPutObject.remove(strKey);                
            }
            jsonPutObject.put(strKey,jsonData);
        }
        catch(JSONException e)
        {
            e.printStackTrace();
        }
        return jsonPutObject;
    }



    public int get_json_value_int(JSONObject jsonObject, String strKey) 
    {
        int result = 0;
        try
        {
            if(jsonObject.has(strKey))
            {
                if(jsonObject.get(strKey)==JSONObject.NULL){
                    result = 0;
                }
                else{
                    result = Integer.parseInt(jsonObject.get(strKey).toString());
                }
                
            }
        }
        catch(JSONException e)
        {
            e.printStackTrace();
        }
        return result;
    }

    public Double get_json_value_double(JSONObject jsonObject, String strKey) 
    {
        Double result = 0.0;
        try
        {
            if(jsonObject.has(strKey))
            {
                if(jsonObject.get(strKey)==JSONObject.NULL){
                    result = 0.0;
                }
                else{
                    result = Double.parseDouble(jsonObject.get(strKey).toString());
                }
                
            }
        }
        catch(JSONException e)
        {
            e.printStackTrace();
        }
        return result;
    }

    public String get_json_obj_value(JSONObject jsonObject, String strKey1, String strKey2) 
    {
        String result = "";
        try
        {
            if(jsonObject.has(strKey1))
            {
                if(jsonObject.getJSONObject(strKey1).has(strKey2)){
                    result = jsonObject.getJSONObject(strKey1).get(strKey2).toString();
                }
            }
        }
        catch(JSONException e)
        {
            e.printStackTrace();
        }
        return result;
    }

    public String get_json_array_value(JSONArray jsonArray, String strKey) 
    {
        String result = "";
        
        try
        {
            JSONObject jsonObject = null;
            if (jsonArray.getJSONArray(0).length() > 0)
            {
                jsonObject = jsonArray.getJSONArray(0).getJSONObject(0);
                if(jsonObject.has(strKey))
                {
                    result = jsonObject.get(strKey).toString();
                }
            }                                
        }
        catch(JSONException e)
        {
            e.printStackTrace();
        }
        return result;
    }

    public String get_json_array_value_result(JSONArray jsonArray) 
    {
        String result = "";
        String strKey = "result";
        
        try
        {
            JSONObject jsonObject = null;
            if (jsonArray.getJSONArray(0).length() > 0)
            {
                jsonObject = jsonArray.getJSONArray(0).getJSONObject(0);
                if(jsonObject.has(strKey))
                {
                    result = jsonObject.get(strKey).toString();
                }
            }                                
        }
        catch(JSONException e)
        {
            e.printStackTrace();
        }
        return result;
    }

    public String get_json_array_value_message(JSONArray jsonArray) 
    {
        String result = "";
        String strKey = "message";
        
        try
        {
            JSONObject jsonObject = null;
            if (jsonArray.getJSONArray(0).length() > 0)
            {
                jsonObject = jsonArray.getJSONArray(0).getJSONObject(0);
                if(jsonObject.has(strKey))
                {
                    result = jsonObject.get(strKey).toString();
                }
            }                                
        }
        catch(JSONException e)
        {
            e.printStackTrace();
        }
        return result;
    }

    public int get_json_array_value_int(JSONArray jsonArray, String strKey) 
    {
        int result = 0;
        
        try
        {
            JSONObject jsonObject = null;
            if (jsonArray.getJSONArray(0).length() > 0)
            {
                jsonObject = jsonArray.getJSONArray(0).getJSONObject(0);
                if(jsonObject.has(strKey))
                {
                    result = Integer.parseInt(jsonObject.get(strKey).toString());
                }
            }                                
        }
        catch(JSONException e)
        {
            e.printStackTrace();
        }
        return result;
    }

    public String get_json_array_position_value(JSONArray jsonArray, int nArrayPosition, String strKey) 
    {
        String result = "";
        
        try
        {
            JSONObject jsonObject = null;
            if (jsonArray.getJSONArray(nArrayPosition).length() > 0)
            {
                jsonObject = jsonArray.getJSONArray(nArrayPosition).getJSONObject(0);
                if(jsonObject.has(strKey))
                {
                    result = jsonObject.get(strKey).toString();
                }
            }            
        }
        catch(JSONException e)
        {
            e.printStackTrace();
        }
        return result;
    }

    public int get_json_array_position_value_int(JSONArray jsonArray, int nArrayPosition, String strKey) 
    {
        int result = 0;
        
        try
        {
            JSONObject jsonObject = null;
            if (jsonArray.getJSONArray(nArrayPosition).length() > 0)
            {
                jsonObject = jsonArray.getJSONArray(nArrayPosition).getJSONObject(0);
                if(jsonObject.has(strKey))
                {
                    result = Integer.parseInt(jsonObject.get(strKey).toString());
                }
            }            
        }
        catch(JSONException e)
        {
            e.printStackTrace();
        }
        return result;
    }

    public Double get_json_array_position_value_double(JSONArray jsonArray, int nArrayPosition, String strKey) 
    {
        Double result = 0.0;
        
        try
        {
            JSONObject jsonObject = null;
            if (jsonArray.getJSONArray(nArrayPosition).length() > 0)
            {
                jsonObject = jsonArray.getJSONArray(nArrayPosition).getJSONObject(0);
                if(jsonObject.has(strKey))
                {
                    result = Double.parseDouble(jsonObject.get(strKey).toString());
                }
            }            
        }
        catch(JSONException e)
        {
            e.printStackTrace();
        }
        return result;
    }

    public String get_data(String result, String message, JSONArray jsonArray)
    {
        String result_message = "";
        try
        {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("result",result);
            jsonObject.put("message",message);
            jsonObject.put("data",jsonArray);
            result_message = jsonObject.toString();
        }
        catch(JSONException e)
        {
            e.printStackTrace();            
        }
        return result_message;
    }

    public String get_data_ok(JSONArray jsonArray)
    {
        String result_message = "";
        try
        {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("result","ok");
            jsonObject.put("message","");
            jsonObject.put("data",jsonArray);
            result_message = jsonObject.toString();
        }
        catch(JSONException e)
        {
            e.printStackTrace();            
        }
        return result_message;
    }

    public String get_data_obj_rows(JSONObject jsonObject, JSONArray jsonArray)
    {
        String result_message = "";
        try
        {            
            jsonObject.put("data",jsonArray);
            result_message = jsonObject.toString();
        }
        catch(JSONException e)
        {
            e.printStackTrace();            
        }
        return result_message;
    }

    public String get_data_result(JSONArray jsonArray) {
        String result_message = "";
        try {
            JSONObject jsonObject = new JSONObject();
            JSONObject jsonResult = jsonArray.getJSONArray(0).getJSONObject(0);
            String json_result = get_json_value(jsonResult,"result");
            String json_message = get_json_value(jsonResult,"message");
            String ERROR_CODE = get_json_value(jsonResult,"ERROR_CODE");
            jsonObject.put("result", json_result);
            jsonObject.put("message", json_message);
            jsonObject.put("ERROR_CODE", ERROR_CODE);
            jsonObject.put("data", jsonArray);
            result_message = jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result_message;
    }

    public JSONObject get_data_result_object(JSONArray jsonArray)
    {
        JSONObject jsonObject = new JSONObject();
        try
        {
            
            String json_result = jsonArray.getJSONArray(0).getJSONObject(0).getString("result");
            String json_message = jsonArray.getJSONArray(0).getJSONObject(0).getString("message");
            jsonObject.put("result",json_result);
            jsonObject.put("message",json_message);                        
        }
        catch(JSONException e)
        {
            e.printStackTrace();            
        }
        return jsonObject;
    }

    public JSONArray execute_query(String strSQL) 
    {
        Connection conn = null;
        CallableStatement stmt = null;
        JSONArray jsonArray = null;
        try{
            
            Class.forName(mysql_driver);
            conn = DriverManager.getConnection(mysql_db_url,mysql_user_name,mysql_user_password);
            
            //System.out.println("strSQL : "+ strSQL);
            // logger.info("strSQL : "+ strSQL);
            log(module_name,"strSQL : "+ strSQL);

            stmt = conn.prepareCall(strSQL);
            stmt.execute();

            

            ResultSet rs = stmt.getResultSet();
            jsonArray = new JSONArray();    
            
            JSONArray jsonArray1 = new JSONArray();
            while(rs.next())
            {
                JSONObject  jo  = new JSONObject();
                ResultSetMetaData rmd = rs.getMetaData();

                for (int i=1; i<=rmd.getColumnCount(); i++ )
                {
                    int nColumType = rmd.getColumnType(i);
                    if (rs.getObject(rmd.getColumnLabel(i))==null){
                        jo.put(rmd.getColumnLabel(i),JSONObject.NULL);
                    }
                    else if (nColumType == Types.DOUBLE) {                        
                        jo.put(rmd.getColumnLabel(i),rs.getDouble(rmd.getColumnLabel(i)));                        
                    }
                    else if (nColumType == Types.BIGINT || nColumType == Types.INTEGER || nColumType == Types.SMALLINT || nColumType == Types.REAL || nColumType == Types.TINYINT) {
                        jo.put(rmd.getColumnLabel(i),rs.getInt(rmd.getColumnLabel(i)));
                    }
                    else
                    {
                        jo.put(rmd.getColumnLabel(i),rs.getString(rmd.getColumnLabel(i)));                        
                    }
                }

                jsonArray1.put(jo);
            }
            jsonArray.put(jsonArray1);
            rs.close();

            while(stmt.getMoreResults()){
                ResultSet rs2 = stmt.getResultSet();
                JSONArray jsonArray2 = new JSONArray();
                while(rs2.next()){
                    JSONObject  jo  = new JSONObject();
                    ResultSetMetaData rmd = rs2.getMetaData();

                    for (int i=1; i<=rmd.getColumnCount(); i++ )
                    {
                        int nColumType = rmd.getColumnType(i);
                        if (rs2.getObject(rmd.getColumnLabel(i))==null){
                            jo.put(rmd.getColumnLabel(i),JSONObject.NULL);
                        }
                        else if (nColumType == Types.DOUBLE) {                        
                            jo.put(rmd.getColumnLabel(i),rs2.getDouble(rmd.getColumnLabel(i)));                        
                        }
                        else if (nColumType == Types.BIGINT || nColumType == Types.INTEGER || nColumType == Types.SMALLINT || nColumType == Types.REAL || nColumType == Types.TINYINT) {
                            jo.put(rmd.getColumnLabel(i),rs2.getInt(rmd.getColumnLabel(i)));
                        }
                        else
                        {
                            jo.put(rmd.getColumnLabel(i),rs2.getString(rmd.getColumnLabel(i)));                        
                        }
                    }
                    jsonArray2.put(jo);
                }
                jsonArray.put(jsonArray2);
                rs2.close();
            }

            stmt.close();
            conn.close();


        }catch(SQLException se1){
            try
            {
                JSONObject jsonObject_1 = new JSONObject();
                jsonObject_1.put("result","error");
                jsonObject_1.put("message",se1.getMessage());
                jsonArray = new JSONArray();
                JSONArray jsonArray1 = new JSONArray();
                jsonArray1.put(jsonObject_1);
                jsonArray.put(jsonArray1);
            }
            catch(JSONException e)
            {
                e.printStackTrace();
            }
            
        }catch(Exception ex){
            try
            {
                JSONObject jsonObject1 = new JSONObject();
                jsonObject1.put("result","error");
                jsonObject1.put("message",ex.getMessage());
                jsonArray = new JSONArray();
                JSONArray jsonArray1 = new JSONArray();
                jsonArray1.put(jsonObject1);
                jsonArray.put(jsonArray1);
            }
            catch(JSONException e)
            {
                e.printStackTrace();
            }
            
        }finally{
            try{
                if(stmt!=null)
                {
                    stmt.close();
                }                            
            }catch(SQLException se2){
                try
                {
                    JSONObject jsonObject2 = new JSONObject();
                    jsonObject2.put("result","error");
                    jsonObject2.put("message",se2.getMessage());
                    jsonArray = new JSONArray();
                    JSONArray jsonArray1 = new JSONArray();
                    jsonArray1.put(jsonObject2);
                    jsonArray.put(jsonArray1);
                }
                catch(JSONException e)
                {
                    e.printStackTrace();

                }                
            }
            
            try{
                
                if(conn!=null)
                {
                    conn.close();
                } 
                                    
            }catch(SQLException se){
                try
                {
                    JSONObject jsonObject3 = new JSONObject();
                    jsonObject3.put("result","error");
                    jsonObject3.put("message",se.getMessage());
                    jsonArray = new JSONArray();
                    JSONArray jsonArray1 = new JSONArray();
                    jsonArray1.put(jsonObject3);
                    jsonArray.put(jsonArray1);
                }
                catch(JSONException e)
                {
                    e.printStackTrace();
                }
                
            }
            
        }

        return jsonArray;
    }

    
    public JSONArray execute_query_mssql(String strSQL) 
    {
        Connection conn = null;
        CallableStatement stmt = null;
        JSONArray jsonArray = null;
        try{
            
            Class.forName(mssql_driver);            
            conn = DriverManager.getConnection(mssql_db_url);
            // System.out.println("strSQL : "+ strSQL);
            // logger.info("strSQL : "+ strSQL);
            log(module_name,"strSQL : "+ strSQL);

            stmt = conn.prepareCall(strSQL);
            stmt.execute();
            
            ResultSet rs = stmt.getResultSet();
            jsonArray = new JSONArray();    
            
            JSONArray jsonArray1 = new JSONArray();
            while(rs.next())
            {
                JSONObject  jo  = new JSONObject();
                ResultSetMetaData rmd = rs.getMetaData();

                for (int i=1; i<=rmd.getColumnCount(); i++ )
                {
                    int nColumType = rmd.getColumnType(i);
                        if(rs.getObject(rmd.getColumnLabel(i))==null){
                            jo.put(rmd.getColumnLabel(i),JSONObject.NULL);
                        }
                        else if (nColumType == Types.DOUBLE) {
                            jo.put(rmd.getColumnLabel(i),rs.getDouble(rmd.getColumnLabel(i)));
                        }
                        else if (nColumType == Types.BIGINT || nColumType == Types.INTEGER || nColumType == Types.SMALLINT || nColumType == Types.REAL || nColumType == Types.TINYINT) {
                            jo.put(rmd.getColumnLabel(i),rs.getInt(rmd.getColumnLabel(i)));
                        }
                        else
                        {
                            jo.put(rmd.getColumnLabel(i),rs.getString(rmd.getColumnLabel(i)));
                        }
                }

                jsonArray1.put(jo);
            }
            jsonArray.put(jsonArray1);
            rs.close();

            while(stmt.getMoreResults()){
                ResultSet rs2 = stmt.getResultSet();
                JSONArray jsonArray2 = new JSONArray();
                while(rs2.next()){
                    JSONObject  jo  = new JSONObject();
                    ResultSetMetaData rmd = rs2.getMetaData();

                    for (int i=1; i<=rmd.getColumnCount(); i++ )
                    {
                        int nColumType = rmd.getColumnType(i);
                        if(rs2.getObject(rmd.getColumnLabel(i))==null){
                            jo.put(rmd.getColumnLabel(i),JSONObject.NULL);
                        }
                        else if (nColumType == Types.DOUBLE) {
                            jo.put(rmd.getColumnLabel(i),rs2.getDouble(rmd.getColumnLabel(i)));
                        }
                        else if (nColumType == Types.BIGINT || nColumType == Types.INTEGER || nColumType == Types.SMALLINT || nColumType == Types.REAL || nColumType == Types.TINYINT) {
                            jo.put(rmd.getColumnLabel(i),rs2.getInt(rmd.getColumnLabel(i)));
                        }
                        else
                        {
                            jo.put(rmd.getColumnLabel(i),rs2.getString(rmd.getColumnLabel(i)));
                        }
                    }
                    jsonArray2.put(jo);
                }
                jsonArray.put(jsonArray2);
                rs2.close();
            }
            
                   
            rs.close();
            stmt.close();
            conn.close();


        }catch(SQLException se1){
            try
            {
                JSONObject jsonObject_1 = new JSONObject();
                jsonObject_1.put("result","error");
                jsonObject_1.put("message",se1.getMessage());
                jsonArray = new JSONArray();
                JSONArray jsonArray1 = new JSONArray();
                jsonArray1.put(jsonObject_1);
                jsonArray.put(jsonArray1);
            }
            catch(JSONException e)
            {
                e.printStackTrace();
            }
            
        }catch(Exception ex){
            ex.printStackTrace();
            try
            {
                JSONObject jsonObject1 = new JSONObject();
                jsonObject1.put("result","error");
                jsonObject1.put("message",ex.getMessage());
                jsonArray = new JSONArray();
                JSONArray jsonArray1 = new JSONArray();
                jsonArray1.put(jsonObject1);
                jsonArray.put(jsonArray1);
            }
            catch(JSONException e)
            {
                e.printStackTrace();
            }
            
        }finally{
            try{
                if(stmt!=null)
                {
                    stmt.close();
                }                            
            }catch(SQLException se2){
                try
                {
                    JSONObject jsonObject2 = new JSONObject();
                    jsonObject2.put("result","error");
                    jsonObject2.put("message",se2.getMessage());
                    jsonArray = new JSONArray();
                    JSONArray jsonArray1 = new JSONArray();
                    jsonArray1.put(jsonObject2);
                    jsonArray.put(jsonArray1);
                }
                catch(JSONException e)
                {
                    e.printStackTrace();

                }                
            }
            try{
                if(conn!=null)
                {
                    conn.close();
                }                            
            }catch(SQLException se){
                try
                {
                    JSONObject jsonObject3 = new JSONObject();
                    jsonObject3.put("result","error");
                    jsonObject3.put("message",se.getMessage());
                    jsonArray = new JSONArray();
                    JSONArray jsonArray1 = new JSONArray();
                    jsonArray1.put(jsonObject3);
                    jsonArray.put(jsonArray1);
                }
                catch(JSONException e)
                {
                    e.printStackTrace();
                }
                
            }
        }

        return jsonArray;
    }

    public JSONArray execute_query_mssql_mms(String strSQL, JSONObject obj) 
    {
        Connection conn = null;
        CallableStatement stmt = null;
        JSONArray jsonArray = null;
        try{
            
            Class.forName(mssql_driver);            
            conn = DriverManager.getConnection(mssql_db_url);
            // System.out.println("strSQL : "+ strSQL);
            // logger.info("strSQL : "+ strSQL);
            log(module_name,"strSQL : "+ strSQL);

            stmt = conn.prepareCall(strSQL);
            /*
            request.input('TYPE', "MMS");
            request.input('R_TEL_NO', jsonData.EMP_TEL);
            request.input('S_TEL_NO', "0221886784");
            request.input('S_DATE', common.now_date_str());
            request.input('TITLE', "ë©”ì‹œì§€");
            request.input('MESSAGE', smsMsg);
            request.input('REFER', "http://vendor.veteranscout.co.kr");
            request.input('LOGBIT',1);
            // {call sc_tran_PROC_veteran(?,?,?,?,?,?,?)}
            */
            
            stmt.setString(1,get_json_value(obj,"TYPE"));
            stmt.setString(2,get_json_value(obj,"R_TEL_NO"));
            stmt.setString(3,get_json_value(obj,"S_TEL_NO"));
            stmt.setString(4,get_json_value(obj,"S_DATE"));
            stmt.setString(5,get_json_value(obj,"TITLE"));
            stmt.setString(6,get_json_value(obj,"MESSAGE"));
            stmt.setString(7,get_json_value(obj,"REFER"));            

            stmt.execute();
            
            ResultSet rs = stmt.getResultSet();
            jsonArray = new JSONArray();    
            
            JSONArray jsonArray1 = new JSONArray();
            while(rs.next())
            {
                JSONObject  jo  = new JSONObject();
                ResultSetMetaData rmd = rs.getMetaData();

                for (int i=1; i<=rmd.getColumnCount(); i++ )
                {
                    int nColumType = rmd.getColumnType(i);
                        if(rs.getObject(rmd.getColumnLabel(i))==null){
                            jo.put(rmd.getColumnLabel(i),JSONObject.NULL);
                        }
                        else if (nColumType == Types.DOUBLE) {
                            jo.put(rmd.getColumnLabel(i),rs.getDouble(rmd.getColumnLabel(i)));
                        }
                        else if (nColumType == Types.BIGINT || nColumType == Types.INTEGER || nColumType == Types.SMALLINT || nColumType == Types.REAL || nColumType == Types.TINYINT) {
                            jo.put(rmd.getColumnLabel(i),rs.getInt(rmd.getColumnLabel(i)));
                        }
                        else
                        {
                            jo.put(rmd.getColumnLabel(i),rs.getString(rmd.getColumnLabel(i)));
                        }
                }

                jsonArray1.put(jo);
            }
            jsonArray.put(jsonArray1);
            rs.close();

            while(stmt.getMoreResults()){
                ResultSet rs2 = stmt.getResultSet();
                JSONArray jsonArray2 = new JSONArray();
                while(rs2.next()){
                    JSONObject  jo  = new JSONObject();
                    ResultSetMetaData rmd = rs2.getMetaData();

                    for (int i=1; i<=rmd.getColumnCount(); i++ )
                    {
                        int nColumType = rmd.getColumnType(i);
                        if(rs2.getObject(rmd.getColumnLabel(i))==null){
                            jo.put(rmd.getColumnLabel(i),JSONObject.NULL);
                        }
                        else if (nColumType == Types.DOUBLE) {
                            jo.put(rmd.getColumnLabel(i),rs2.getDouble(rmd.getColumnLabel(i)));
                        }
                        else if (nColumType == Types.BIGINT || nColumType == Types.INTEGER || nColumType == Types.SMALLINT || nColumType == Types.REAL || nColumType == Types.TINYINT) {
                            jo.put(rmd.getColumnLabel(i),rs2.getInt(rmd.getColumnLabel(i)));
                        }
                        else
                        {
                            jo.put(rmd.getColumnLabel(i),rs2.getString(rmd.getColumnLabel(i)));
                        }
                    }
                    jsonArray2.put(jo);
                }
                jsonArray.put(jsonArray2);
                rs2.close();
            }
            
                   
            rs.close();
            stmt.close();
            conn.close();


        }catch(SQLException se1){
            try
            {
                JSONObject jsonObject_1 = new JSONObject();
                jsonObject_1.put("result","error");
                jsonObject_1.put("message",se1.getMessage());
                jsonArray = new JSONArray();
                JSONArray jsonArray1 = new JSONArray();
                jsonArray1.put(jsonObject_1);
                jsonArray.put(jsonArray1);
            }
            catch(JSONException e)
            {
                e.printStackTrace();
            }
            
        }catch(Exception ex){
            ex.printStackTrace();
            try
            {
                JSONObject jsonObject1 = new JSONObject();
                jsonObject1.put("result","error");
                jsonObject1.put("message",ex.getMessage());
                jsonArray = new JSONArray();
                JSONArray jsonArray1 = new JSONArray();
                jsonArray1.put(jsonObject1);
                jsonArray.put(jsonArray1);
            }
            catch(JSONException e)
            {
                e.printStackTrace();
            }
            
        }finally{
            try{
                if(stmt!=null)
                {
                    stmt.close();
                }                            
            }catch(SQLException se2){
                try
                {
                    JSONObject jsonObject2 = new JSONObject();
                    jsonObject2.put("result","error");
                    jsonObject2.put("message",se2.getMessage());
                    jsonArray = new JSONArray();
                    JSONArray jsonArray1 = new JSONArray();
                    jsonArray1.put(jsonObject2);
                    jsonArray.put(jsonArray1);
                }
                catch(JSONException e)
                {
                    e.printStackTrace();

                }                
            }
            try{
                if(conn!=null)
                {
                    conn.close();
                }                            
            }catch(SQLException se){
                try
                {
                    JSONObject jsonObject3 = new JSONObject();
                    jsonObject3.put("result","error");
                    jsonObject3.put("message",se.getMessage());
                    jsonArray = new JSONArray();
                    JSONArray jsonArray1 = new JSONArray();
                    jsonArray1.put(jsonObject3);
                    jsonArray.put(jsonArray1);
                }
                catch(JSONException e)
                {
                    e.printStackTrace();
                }
                
            }
        }

        return jsonArray;
    }

    public JSONArray execute_query_mssql_mms_date(String strSQL, JSONObject obj) 
    {
        Connection conn = null;
        CallableStatement stmt = null;
        JSONArray jsonArray = null;
        try{
            
            Class.forName(mssql_driver);            
            conn = DriverManager.getConnection(mssql_db_url);
            // System.out.println("strSQL : "+ strSQL);
            // logger.info("strSQL : "+ strSQL);
            log(module_name,"strSQL : "+ strSQL);

            stmt = conn.prepareCall(strSQL);
            /*
            strSQL = "sc_tran_PROC_veteran_file_date 'MMS','"+M_R_TEL_NO+"','0221886784','"+common.now_time_string()+
            "','"+M_TITLE+"','"+MMS_MSG+"','"+M_GBN+"','"+jsonData.getInt("N_ID")+"','"+
            jsonData.getString("NAME")+"','"+jsonData.getString("ADDR1")+"','"+
            jsonData.getString("APP_VERSION")+"','"+jsonData.getString("APP_VERSION_DATE")+"','"+
            jsonData.getString("APP_VERSION_DATE")+"','"+jsonData.getString("LST_LOGIN_DATE")+"','"+
            jsonData.getString("LINK_TYPE")+"','"+jsonData.getInt("AREA_CODE1")+"',"+M_FILE_ID+",1 ";
            */
            
            stmt.setString(1,get_json_value(obj,"TYPE"));
            stmt.setString(2,get_json_value(obj,"R_TEL_NO"));
            stmt.setString(3,get_json_value(obj,"S_TEL_NO"));
            stmt.setString(4,get_json_value(obj,"S_DATE"));
            stmt.setString(5,get_json_value(obj,"TITLE"));
            stmt.setString(6,get_json_value(obj,"MESSAGE"));
            stmt.setString(7,get_json_value(obj,"REFER"));            
            stmt.setString(8,get_json_value(obj,"EVENT_ID"));            
            stmt.setString(9,get_json_value(obj,"NAME"));            
            stmt.setString(10,get_json_value(obj,"ADDR1"));            
            stmt.setString(11,get_json_value(obj,"APP_VERSION"));            
            stmt.setString(12,get_json_value(obj,"APP_VERSION_DATE"));            
            stmt.setString(13,get_json_value(obj,"LST_LOGIN_DATE"));            
            stmt.setString(14,get_json_value(obj,"LINK_TYPE"));            
            stmt.setString(15,get_json_value(obj,"AREA_CODE1"));            
            if(obj.has("FILE_ID")){
                stmt.setInt(16,get_json_value_int(obj,"FILE_ID"));            
            }

            stmt.execute();
            
            ResultSet rs = stmt.getResultSet();
            jsonArray = new JSONArray();    
            
            JSONArray jsonArray1 = new JSONArray();
            while(rs.next())
            {
                JSONObject  jo  = new JSONObject();
                ResultSetMetaData rmd = rs.getMetaData();

                for (int i=1; i<=rmd.getColumnCount(); i++ )
                {
                    int nColumType = rmd.getColumnType(i);
                        if(rs.getObject(rmd.getColumnLabel(i))==null){
                            jo.put(rmd.getColumnLabel(i),JSONObject.NULL);
                        }
                        else if (nColumType == Types.DOUBLE) {
                            jo.put(rmd.getColumnLabel(i),rs.getDouble(rmd.getColumnLabel(i)));
                        }
                        else if (nColumType == Types.BIGINT || nColumType == Types.INTEGER || nColumType == Types.SMALLINT || nColumType == Types.REAL || nColumType == Types.TINYINT) {
                            jo.put(rmd.getColumnLabel(i),rs.getInt(rmd.getColumnLabel(i)));
                        }
                        else
                        {
                            jo.put(rmd.getColumnLabel(i),rs.getString(rmd.getColumnLabel(i)));
                        }
                }

                jsonArray1.put(jo);
            }
            jsonArray.put(jsonArray1);
            rs.close();

            while(stmt.getMoreResults()){
                ResultSet rs2 = stmt.getResultSet();
                JSONArray jsonArray2 = new JSONArray();
                while(rs2.next()){
                    JSONObject  jo  = new JSONObject();
                    ResultSetMetaData rmd = rs2.getMetaData();

                    for (int i=1; i<=rmd.getColumnCount(); i++ )
                    {
                        int nColumType = rmd.getColumnType(i);
                        if(rs2.getObject(rmd.getColumnLabel(i))==null){
                            jo.put(rmd.getColumnLabel(i),JSONObject.NULL);
                        }
                        else if (nColumType == Types.DOUBLE) {
                            jo.put(rmd.getColumnLabel(i),rs2.getDouble(rmd.getColumnLabel(i)));
                        }
                        else if (nColumType == Types.BIGINT || nColumType == Types.INTEGER || nColumType == Types.SMALLINT || nColumType == Types.REAL || nColumType == Types.TINYINT) {
                            jo.put(rmd.getColumnLabel(i),rs2.getInt(rmd.getColumnLabel(i)));
                        }
                        else
                        {
                            jo.put(rmd.getColumnLabel(i),rs2.getString(rmd.getColumnLabel(i)));
                        }
                    }
                    jsonArray2.put(jo);
                }
                jsonArray.put(jsonArray2);
                rs2.close();
            }
            
                   
            rs.close();
            stmt.close();
            conn.close();


        }catch(SQLException se1){
            try
            {
                JSONObject jsonObject_1 = new JSONObject();
                jsonObject_1.put("result","error");
                jsonObject_1.put("message",se1.getMessage());
                jsonArray = new JSONArray();
                JSONArray jsonArray1 = new JSONArray();
                jsonArray1.put(jsonObject_1);
                jsonArray.put(jsonArray1);
            }
            catch(JSONException e)
            {
                e.printStackTrace();
            }
            
        }catch(Exception ex){
            ex.printStackTrace();
            try
            {
                JSONObject jsonObject1 = new JSONObject();
                jsonObject1.put("result","error");
                jsonObject1.put("message",ex.getMessage());
                jsonArray = new JSONArray();
                JSONArray jsonArray1 = new JSONArray();
                jsonArray1.put(jsonObject1);
                jsonArray.put(jsonArray1);
            }
            catch(JSONException e)
            {
                e.printStackTrace();
            }
            
        }finally{
            try{
                if(stmt!=null)
                {
                    stmt.close();
                }                            
            }catch(SQLException se2){
                try
                {
                    JSONObject jsonObject2 = new JSONObject();
                    jsonObject2.put("result","error");
                    jsonObject2.put("message",se2.getMessage());
                    jsonArray = new JSONArray();
                    JSONArray jsonArray1 = new JSONArray();
                    jsonArray1.put(jsonObject2);
                    jsonArray.put(jsonArray1);
                }
                catch(JSONException e)
                {
                    e.printStackTrace();

                }                
            }
            try{
                if(conn!=null)
                {
                    conn.close();
                }                            
            }catch(SQLException se){
                try
                {
                    JSONObject jsonObject3 = new JSONObject();
                    jsonObject3.put("result","error");
                    jsonObject3.put("message",se.getMessage());
                    jsonArray = new JSONArray();
                    JSONArray jsonArray1 = new JSONArray();
                    jsonArray1.put(jsonObject3);
                    jsonArray.put(jsonArray1);
                }
                catch(JSONException e)
                {
                    e.printStackTrace();
                }
                
            }
        }

        return jsonArray;
    }

    public JSONArray execute_query_commit(String strSQL) 
    {
        Connection conn = null;
        CallableStatement stmt = null;
        JSONArray jsonArray = null;
        try{
            
            Class.forName(mysql_driver);
            conn = DriverManager.getConnection(mysql_db_url,mysql_user_name,mysql_user_password);
            
            //System.out.println("strSQL : "+ strSQL);
            // logger.info("strSQL : "+ strSQL);
            log(module_name,"strSQL : "+ strSQL);
            
            stmt = conn.prepareCall(strSQL);
            stmt.execute();

            

            ResultSet rs = stmt.getResultSet();
            jsonArray = new JSONArray();    
            
            JSONArray jsonArray1 = new JSONArray();
            while(rs.next())
            {
                JSONObject  jo  = new JSONObject();
                ResultSetMetaData rmd = rs.getMetaData();

                for (int i=1; i<=rmd.getColumnCount(); i++ )
                {
                    int nColumType = rmd.getColumnType(i);
                    if (rs.getObject(rmd.getColumnLabel(i))==null){
                        jo.put(rmd.getColumnLabel(i),JSONObject.NULL);
                    }
                    else if (nColumType == Types.DOUBLE) {                        
                        jo.put(rmd.getColumnLabel(i),rs.getDouble(rmd.getColumnLabel(i)));                        
                    }
                    else if (nColumType == Types.BIGINT || nColumType == Types.INTEGER || nColumType == Types.SMALLINT || nColumType == Types.REAL || nColumType == Types.TINYINT) {
                        jo.put(rmd.getColumnLabel(i),rs.getInt(rmd.getColumnLabel(i)));
                    }
                    else
                    {
                        jo.put(rmd.getColumnLabel(i),rs.getString(rmd.getColumnLabel(i)));                        
                    }
                }

                jsonArray1.put(jo);
            }
            jsonArray.put(jsonArray1);
            rs.close();

            while(stmt.getMoreResults()){
                ResultSet rs2 = stmt.getResultSet();
                JSONArray jsonArray2 = new JSONArray();
                while(rs2.next()){
                    JSONObject  jo  = new JSONObject();
                    ResultSetMetaData rmd = rs2.getMetaData();

                    for (int i=1; i<=rmd.getColumnCount(); i++ )
                    {
                        int nColumType = rmd.getColumnType(i);
                        if (rs2.getObject(rmd.getColumnLabel(i))==null){
                            jo.put(rmd.getColumnLabel(i),JSONObject.NULL);
                        }
                        else if (nColumType == Types.DOUBLE) {                        
                            jo.put(rmd.getColumnLabel(i),rs2.getDouble(rmd.getColumnLabel(i)));                        
                        }
                        else if (nColumType == Types.BIGINT || nColumType == Types.INTEGER || nColumType == Types.SMALLINT || nColumType == Types.REAL || nColumType == Types.TINYINT) {
                            jo.put(rmd.getColumnLabel(i),rs2.getInt(rmd.getColumnLabel(i)));
                        }
                        else
                        {
                            jo.put(rmd.getColumnLabel(i),rs2.getString(rmd.getColumnLabel(i)));                        
                        }
                    }
                    jsonArray2.put(jo);
                }
                jsonArray.put(jsonArray2);
                rs2.close();
            }

            stmt.close();
            conn.close();


        }catch(SQLException se1){
            try
            {
                JSONObject jsonObject_1 = new JSONObject();
                jsonObject_1.put("result","error");
                jsonObject_1.put("message",se1.getMessage());
                jsonArray = new JSONArray();
                JSONArray jsonArray1 = new JSONArray();
                jsonArray1.put(jsonObject_1);
                jsonArray.put(jsonArray1);
            }
            catch(JSONException e)
            {
                e.printStackTrace();
            }
            
        }catch(Exception ex){
            try
            {
                JSONObject jsonObject1 = new JSONObject();
                jsonObject1.put("result","error");
                jsonObject1.put("message",ex.getMessage());
                jsonArray = new JSONArray();
                JSONArray jsonArray1 = new JSONArray();
                jsonArray1.put(jsonObject1);
                jsonArray.put(jsonArray1);
            }
            catch(JSONException e)
            {
                e.printStackTrace();
            }
            
        }finally{
            try{
                if(stmt!=null)
                {
                    stmt.close();
                }                            
            }catch(SQLException se2){
                try
                {
                    JSONObject jsonObject2 = new JSONObject();
                    jsonObject2.put("result","error");
                    jsonObject2.put("message",se2.getMessage());
                    jsonArray = new JSONArray();
                    JSONArray jsonArray1 = new JSONArray();
                    jsonArray1.put(jsonObject2);
                    jsonArray.put(jsonArray1);
                }
                catch(JSONException e)
                {
                    e.printStackTrace();

                }                
            }
            
            try{
                
                if(conn!=null)
                {
                    conn.close();
                } 
                                    
            }catch(SQLException se){
                try
                {
                    JSONObject jsonObject3 = new JSONObject();
                    jsonObject3.put("result","error");
                    jsonObject3.put("message",se.getMessage());
                    jsonArray = new JSONArray();
                    JSONArray jsonArray1 = new JSONArray();
                    jsonArray1.put(jsonObject3);
                    jsonArray.put(jsonArray1);
                }
                catch(JSONException e)
                {
                    e.printStackTrace();
                }
                
            }
            
        }

        return jsonArray;
    }
    public String execute_update(String strSQL) 
    {
        Connection conn = null;
        Statement stmt = null;        
        String result = "";
        try{
            Class.forName(mysql_driver);
            conn = DriverManager.getConnection(mysql_db_url,mysql_user_name,mysql_user_password);
            System.out.println("strSQL : "+ strSQL);
            stmt = conn.createStatement();                                
            stmt.executeUpdate(strSQL);
            
            stmt.close();
            conn.close();

            result = get_message("ok", "");
        }catch(SQLException se1){
            result = get_message("ok", se1.getMessage());            
        }catch(Exception ex){
            result = get_message("ok", ex.getMessage());            
        }finally{
            try{
                if(stmt!=null)
                {
                    stmt.close();
                }                            
            }catch(SQLException se2){
                result = get_message("ok", se2.getMessage());                          
            }
            try{
                if(conn!=null)
                {
                    conn.close();
                }                            
            }catch(SQLException se){
                result = get_message("ok", se.getMessage());                          
                
            }
        }
        return result;
    }

    public String execute_update_commit(String strSQL) 
    {
        Connection conn = null;
        Statement stmt = null;        
        String result = "";
        try{
            Class.forName(mysql_driver);
            conn = DriverManager.getConnection(mysql_db_url,mysql_user_name,mysql_user_password);
            System.out.println("strSQL : "+ strSQL);
            stmt = conn.createStatement();                                
            stmt.executeUpdate(strSQL);
            
            stmt.close();
            conn.close();

            result = get_message("ok", "");
        }catch(SQLException se1){
            result = get_message("ok", se1.getMessage());            
        }catch(Exception ex){
            result = get_message("ok", ex.getMessage());            
        }finally{
            try{
                if(stmt!=null)
                {
                    stmt.close();
                }                            
            }catch(SQLException se2){
                result = get_message("ok", se2.getMessage());                          
            }
            try{
                if(conn!=null)
                {
                    conn.close();
                }                            
            }catch(SQLException se){
                result = get_message("ok", se.getMessage());                          
                
            }
        }
        return result;
    }

    public String send_alarm_json_array(String host, int port, JSONArray jsonArray) {
        
        String result = "";
        String error_message = "";
        try (Socket socket = new Socket(host, port)) {

            //write to socket using ObjectOutputStream
            String json_string = jsonArray.toString();
            json_string = StringUtils.normalizeSpace(json_string);
            json_string = json_string.replace("\n","");
			json_string = json_string.replace("\r","");
			json_string = json_string.replace("\\n","");
            json_string = json_string.replace("\\r","");
            json_string = json_string.replaceAll("\n","");
            json_string = json_string.replaceAll("\\n","");
            json_string = json_string.replaceAll("[\\n\\r\\t]","");
            log(module_name,"send_alarm_json_array : " + json_string);
            OutputStream oStream = socket.getOutputStream();
            oStream.write(json_string.getBytes("UTF-8"));
            oStream.close();

        } catch (UnknownHostException ex) {        
            return get_error_message("Server not found: " + ex.getMessage());            
        } catch (IOException ex) {                        
            return get_error_message("I/O error: " + ex.getMessage());         
        }
        
        if(error_message.equals(""))
        {          
            try
            {
                String jsonResult = jsonArray.getJSONArray(0).getJSONObject(0).getString("result");
                String jsonMessage = jsonArray.getJSONArray(0).getJSONObject(0).getString("message");  
                String response = get_data(jsonResult, jsonMessage, jsonArray);
                return response;
            }
            catch(JSONException e)
            {
                return get_error_message(e.getMessage());
            }            
        }
        else
        {
            return get_error_message(error_message);
        }      
    }

    public String send_alarm_json_object(String host, int port, JSONObject jsonObject) {
        
        String result = "";
        String error_message = "";
        try (Socket socket = new Socket(host, port)) {

            String json_string = jsonObject.toString();
            json_string = StringUtils.normalizeSpace(json_string);
            json_string = json_string.replace("\n","");
			json_string = json_string.replace("\r","");
			json_string = json_string.replace("\\n","");
            json_string = json_string.replace("\\r","");
            json_string = json_string.replaceAll("\n","");
            json_string = json_string.replaceAll("\\n","");
            json_string = json_string.replaceAll("[\\n\\r\\t]","");
            log(module_name,"send_alarm_json_object : " + json_string);
            OutputStream oStream = socket.getOutputStream();
            oStream.write(json_string.getBytes("UTF-8"));
            oStream.close();

        } catch (UnknownHostException ex) {        
            return get_error_message("Server not found: " + ex.getMessage());            
        } catch (IOException ex) {                        
            return get_error_message("I/O error: " + ex.getMessage());         
        }
        
        if(error_message.equals(""))
        {          
            try
            {
                String jsonResult = "ok";
                String jsonMessage = "";  
                JSONArray jsonArray = new JSONArray();
                JSONArray jsonArray1 = new JSONArray();
                jsonArray1.put(jsonObject);
                String response = get_data(jsonResult, jsonMessage, jsonArray);
                return response;
            }
            catch(Exception e)
            {
                return get_error_message(e.getMessage());
            }            
        }
        else
        {
            return get_error_message(error_message);
        }      
    }

    public String now_time_hour_min_string()
    {
        
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd HH:mm");
        return format.format(date);        
    }

    public String get_message(String result, String message) {
        String result_message = "";
        try
        {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("result",result);
            jsonObject.put("message",message);
            result_message = jsonObject.toString();
        }
        catch(JSONException e)
        {
            e.printStackTrace();
        }
        return result_message;
    }

    public void system_log(String strSQL)
    {
        // System.out.println(strSQL);
        try {
			/*
            String fileName = g_log_path + "["+now_date_string()+"]"+module_name+".log";
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));            
			writer.newLine();
            writer.append("["+now_time_string()+"] "+strSQL);        
            writer.close();    
			*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void log(String module_name, String strLog){
        System.out.println(module_name + " " + strLog);
        try {
			/*
            String fileName = g_log_path + "["+now_date_string()+"]"+module_name+".log";
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));            
			writer.newLine();
            writer.append("["+now_time_string()+"] " + strLog);        
            writer.close();    
			*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String now_date_string(){
        
        String result =  "";
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        result = formatter.format(date);
        return result;
    }

    public String now_yyyy(){
        String result =  "";
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
        result = formatter.format(date);
        return result;
    }

    public String now_mm(){
        String result =  "";
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("MM");
        result = formatter.format(date);
        return result;
    }

    public String now_dd(){
        String result =  "";
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd");
        result = formatter.format(date);
        return result;
    }

    public String start_now_date_string(){
        String result =  "";
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
        result = formatter.format(date)+"-01";
        return result;
    }

    public String now_year_string(){
        
        String result =  "";
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
        result = formatter.format(date);
        return result;
    }

    public String now_month_string(){
        
        String result =  "";
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("MM");
        result = formatter.format(date);
        return result;
    }

    public String now_hour_string(){
        
        String result =  "";
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("HH");
        result = formatter.format(date);
        return result;
    }

    public String now_minute_string(){
        
        String result =  "";
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("mm");
        result = formatter.format(date);
        return result;
    }

    public String now_date_str(){
        
        String result =  "";
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        result = formatter.format(date);
        return result;
    }

    public String now_time_string()
    {
        String result = "";
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        result = formatter.format(date);
        return result;        
    }

    public String now_date_integer()
    {
        String strTime = "";
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        strTime = formatter.format(date);        
        return strTime;
    }

    public String get_miliseconds() {        
        String strTime = "";
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("HHmmssSSS");
        strTime = formatter.format(date);      
        return strTime;
    }

    public String get_file_miliseconds() {        
        String strTime = "";
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        strTime = formatter.format(date);      
        return strTime;
    }

    public String get_sha256(String str){
        String SHA = "";     
        try{        
            MessageDigest sh = MessageDigest.getInstance("SHA-256"); 
            sh.update(str.getBytes()); 
            byte byteData[] = sh.digest();
            StringBuffer sb = new StringBuffer(); 
            for(int i = 0 ; i < byteData.length ; i++){    
                sb.append(Integer.toString((byteData[i]&0xff) + 0x100, 16).substring(1));    
            }
            SHA = sb.toString();
        }catch(NoSuchAlgorithmException e){    
            e.printStackTrace();     
            SHA = null; 
        }    
        return SHA;    
    }

    public String https_response(String myURL) {
		System.out.println("Requeted URL:" + myURL);
		StringBuilder sb = new StringBuilder();
		URLConnection urlConn = null;
        InputStreamReader in = null;
        
		try {
			URL url = new URL(myURL);
			urlConn = url.openConnection();
			if (urlConn != null)
				urlConn.setReadTimeout(60 * 1000);
			if (urlConn != null && urlConn.getInputStream() != null) {
				in = new InputStreamReader(urlConn.getInputStream(),
						Charset.defaultCharset());
				BufferedReader bufferedReader = new BufferedReader(in);
				if (bufferedReader != null) {
					int cp;
					while ((cp = bufferedReader.read()) != -1) {
						sb.append((char) cp);
					}
					bufferedReader.close();
				}
			}
		in.close();
		} catch (Exception e) {
			e.printStackTrace();
		} 
 
		return sb.toString();
    }
    
    public void setTimeout(Runnable runnable, int delay){
        new Thread(() -> {
            try {
                Thread.sleep(delay);
                runnable.run();
            }
            catch (Exception e){
                System.err.println(e);
            }
        }).start();
    }

    public void setRunable(Runnable runnable){
        new Thread(() -> {
            try {                
                runnable.run();
            }
            catch (Exception e){
                System.err.println(e);
            }
        }).start();
    }

    
    public String get_value(String aValue)
    {
        aValue = aValue.replace("'","`");
        return aValue;
    }

    public String client_ip(HttpServletRequest request) {
 
        String ip = request.getHeader("X-Forwarded-For");
 
 
        if (ip == null) {
            ip = request.getHeader("Proxy-Client-IP");            
        }
        if (ip == null) {
            ip = request.getHeader("WL-Proxy-Client-IP"); // 웹로직            
        }
        if (ip == null) {
            ip = request.getHeader("HTTP_CLIENT_IP");            
        }
        if (ip == null) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");            
        }
        if (ip == null) {
            ip = request.getRemoteAddr();
        }

        return ip;
 
    }

    public String now_year_month() {

        String result = "";
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMM");
        result = formatter.format(date);
        return result;
    }

    public String encodeURIComponent(String strMsg){
        String result = "";
        try {
			result = URLEncoder.encode(strMsg, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return result;
    }

    
    public String decodeURIComponent(String strMsg){
        String result = "";
        try {
			result = URLDecoder.decode(strMsg, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return result;
    }

    public ModelAndView redirectView(ModelAndView view){
        try {
            view.setViewName("redirect:/");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    public String get_body(HttpServletRequest request){
        String result = "";
        if (request.getMethod().equals("POST") )
        {
            StringBuffer sb = new StringBuffer();
            BufferedReader bufferedReader = null;
            String content = "";

            try {
                //InputStream inputStream = request.getInputStream();
                //inputStream.available();
                //if (inputStream != null) {
                bufferedReader =  request.getReader() ; //new BufferedReader(new InputStreamReader(inputStream));
                char[] charBuffer = new char[128];
                int bytesRead;
                while ( (bytesRead = bufferedReader.read(charBuffer)) != -1 ) {
                    sb.append(charBuffer, 0, bytesRead);
                }
                //} else {
                //        sb.append("");
                //}

            } catch (IOException ex) {
                result = get_error_message(ex.getMessage());
            } finally {
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException ex) {
                        result = get_error_message(ex.getMessage());
                    }
                }
            }

            result = sb.toString();
        }
        return result;
    }

    public String encode(String strContent, String lang){
        String result = "";
        try {               
            if (lang.equals(""))  {
                
                // result = new String(strContent.getBytes("euc-kr"),"UTF8");
                result = strContent;
            }       
            else if(lang.equals("KR")){
                // result = new String(strContent.getBytes("euc-kr"),"UTF8");
                result = strContent;
            }
            else{
                result = strContent;
            }
            // logger.info("result : "+ result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public String get_empty_value(String value){
        String result= "";
        try {
            result = value;
            if(result.equals(" ")) result = "";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    
    public JSONObject getAttribute(HttpServletRequest request) {
        JSONObject result = null;
        String string_result = "";
        String strValue = cookie_name;
        try {
            Cookie[] cookies = request.getCookies();
            Cookie cookie = null;
            int i = 0;
            for(i=0;i<cookies.length;i++){
                cookie = cookies[i];
                if(cookie.getName().equals(strValue)){
                    try {
                        string_result = cookie.getValue().toString();    
                        string_result = decryptCookie(cookie_key, cookie_initVector, string_result);
                        result = new JSONObject(string_result);
                    } 
                    catch (JSONException ex){
                        ex.printStackTrace();                        
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }                    
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public JSONObject setAttribute(JSONObject map, HttpServletResponse response) {
        JSONObject result = null;
        String string_result = "";
        String strValue = cookie_name;
        try {
            string_result = map.toString();
            string_result = encryptCookie(cookie_key, cookie_initVector, string_result);
            Cookie setCookie = new Cookie(strValue, string_result); // 쿠키 이름을 name으로 생성
            setCookie.setMaxAge(60*60*24); // 기간을 하루로 지정    
            response.addCookie(setCookie);            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public void removeAttribute(HttpServletResponse response) {                
        try {
            Cookie cookie = new Cookie(cookie_name, null); // choiceCookieName(쿠키 이름)에 대한 값을 null로 지정
            cookie.setMaxAge(0); // 유효시간을 0으로 설정
            response.addCookie(cookie); // 응답 헤더에 추가해서 없어지도록 함
        } catch (Exception e) {
            e.printStackTrace();
        }
        return;
    }

    public String encryptCookie(String key, String initVector, String value) {
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            byte[] encrypted = cipher.doFinal(value.getBytes());
            System.out.println("encrypted string: "
                    + Base64.encodeBase64String(encrypted));

            return Base64.encodeBase64String(encrypted);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public String decryptCookie(String key, String initVector, String encrypted) {
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

            byte[] original = cipher.doFinal(Base64.decodeBase64(encrypted));

            return new String(original);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }


    public void ResponseAlsert(HttpServletResponse response, String alert_message, String url){
        String result = "";
        try {
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter print_result = response.getWriter();    
            String alert_msg = alert_message;
            result = "<script>";
            result += "alert('"+alert_msg+"');";
            result += "location.href='"+url+"';";
            result += "</script>";
            print_result.println(result);
            print_result.flush();  
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ResponseUrl(HttpServletResponse response, String url){
        String result = "";
        try {
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter print_result = response.getWriter();                
            result = "<script>";            
            result += "location.href='"+url+"';";
            result += "</script>";
            print_result.println(result);
            print_result.flush();  
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public JSONObject getJsonHeader(HttpServletRequest request, String TOP_MENU, String LEFT_MENU){
        JSONObject jsonHeader = new JSONObject();
        JSONObject user = null;
        try {
            user = getAttribute(request);
            String ADMIN_ID = "";
            String ID = "";
            String NAME = "";
            String AUTH = "";
		
			if (user!= null)
			{
				ADMIN_ID = user.get("ADMIN_ID").toString();
				ID = user.get("ID").toString();
				NAME = user.get("NAME").toString();
				AUTH = user.get("AUTH").toString();
			}

            String G_EXCEL_URL = g_xls_url;
            jsonHeader.put("ADMIN_ID",ADMIN_ID);
            jsonHeader.put("IN_ID",ID);
            jsonHeader.put("NAME",NAME);
            jsonHeader.put("AUTH",AUTH);              
            jsonHeader.put("TOP_MENU",TOP_MENU);
            jsonHeader.put("LEFT_MENU",LEFT_MENU);
			
			jsonHeader.put("G_EXCEL_URL",G_EXCEL_URL);   
			
			String user_agent = request.getHeader("User-Agent");
			String samsung_browser_version = get_samsung_browser_version(user_agent);
			jsonHeader.put("G_USER_AGENT", user_agent);
			jsonHeader.put("G_SAMSUNG_BROWSER_VERSION", samsung_browser_version);


			if (user_agent.indexOf("SAMSUNG") != -1 && Integer.parseInt(samsung_browser_version) < 15)
			{
				g_images_url = g_images_url.replace("https://","http://");
			}
            jsonHeader.put("G_IMAGES_URL",g_images_url);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return jsonHeader;
    }

	String get_samsung_browser_version(String str_user_agent_string) {

        String[] str_arr_user_agent;
        int str_user_agent_index;
        String str_user_agent_version_string;
        String[] str_arr_user_agent_version;
        String str_result;

        int version_1 = 0;
		int version_2 = 0;

        str_result = "0";

        try {
            if (str_user_agent_string.indexOf("SAMSUNG") != -1) {
                if (str_user_agent_string.indexOf(" ") != -1) {
                    str_arr_user_agent = str_user_agent_string.split(" ");
                    for (str_user_agent_index = 0; str_user_agent_index < str_arr_user_agent.length; str_user_agent_index++) {

                        if (str_arr_user_agent[str_user_agent_index].indexOf("SamsungBrowser/") != -1) {
                            str_user_agent_version_string = str_arr_user_agent[str_user_agent_index]
                                    .replace("SamsungBrowser/", "");
                            if (str_user_agent_version_string.indexOf(".") != -1) {
                                str_arr_user_agent_version = str_user_agent_version_string.split(".");
                                if (str_arr_user_agent_version.length >= 2) {
                                    version_1 = Integer.parseInt(str_arr_user_agent_version[0]);
									version_2 = Integer.parseInt(str_arr_user_agent_version[1]);


									// SamsungBrowser/15.0
									if ((version_1 == 15 && version_2 >= 0))
									{
										str_result = String.valueOf(version_1);
									}									
									else if (version_1 > 15)
									{
										str_result = String.valueOf(version_1);
									}
									else if (version_1 == 15)
									{
										str_result = "14";
									}
									else
									{
										str_result = String.valueOf(version_1);
									}
                                } else {
                                    str_result = "0";
                                }

                            } else {
                                str_result = "0";
                            }

                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return str_result;
    }

    public String get_imsi_pwd() {
        String result = "";
        int size = 10;
        char[] charSet = new char[] {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                '!', '@', '#', '$', '%', '^', '&'
                };

        StringBuffer sb = new StringBuffer();
        SecureRandom sr = new SecureRandom();
        sr.setSeed(new Date().getTime());

        int idx = 0;
        int len = charSet.length;
        for (int i=0; i<size; i++) {
            // idx = (int) (len * Math.random());
            idx = sr.nextInt(len);    // 강력한 난수를 발생시키기 위해 SecureRandom을 사용한다.
            sb.append(charSet[idx]);
        }

        //result = "cms"+sb.toString()+"!!";
		result = sb.toString();

        return result;
    }
}