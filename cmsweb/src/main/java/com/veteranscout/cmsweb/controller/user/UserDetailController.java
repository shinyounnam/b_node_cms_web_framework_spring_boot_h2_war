package com.veteranscout.cmsweb.controller.user;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.veteranscout.cmsweb.util.*;
import org.json.*;

@Controller
public class UserDetailController {  

    private String module_name = "user_detail";
    private CommonUtil common = new CommonUtil();
    
    @RequestMapping(value = "/user_detail/info/{corp_id}")    
    // @ResponseBody    
    public ModelAndView user_detail_info(CommandMap body, HttpServletRequest request,
    @PathVariable("corp_id") int corp_id
    ) 
    {
        ModelAndView view = new ModelAndView();
        JSONObject user = null;
        try {
            user = common.getAttribute(request);
            if(user!=null){

                String ADMIN_ID = user.get("ADMIN_ID").toString();
                String IN_ID = user.get("ID").toString();
                String NAME = user.get("NAME").toString();
                String AUTH = user.get("AUTH").toString();
                String TOP_MENU = "2";
                String LEFT_MENU = "1";
                
                int CORP_ID = corp_id;
                String CONT_YY = common.now_yyyy();
                String CONT_MM = common.now_mm();
                String CONT_DD = common.now_dd();

                // header.jsp
                JSONObject jsonHeader = common.getJsonHeader(request, TOP_MENU, LEFT_MENU);
                

                jsonHeader.put("CORP_ID",CORP_ID);
                jsonHeader.put("CONT_YY",CONT_YY);
                jsonHeader.put("CONT_MM",CONT_MM);
                jsonHeader.put("CONT_DD",CONT_DD);
                
                view.addObject("header",jsonHeader);

                String strSQL = "";
                strSQL = "CALL SP_CMS_CORP_USER_LEVEL_INFO('"+CORP_ID+"')";
                JSONArray jsonArray = common.execute_query(strSQL);
                view.addObject("data",jsonArray);

				strSQL = "INSERT INTO PRIVATE_ADMIN_LOG "+
                        "(ADMIN_TYPE, ADMIN_ID, USER_TYPE,ID_KEY,ID,LOG_TYPE, REMOTE_IP, LOG_DATE) "+                        
                        "SELECT 'ADMIN_USER','"+ADMIN_ID+"','CORP_USER',CORP_ID,ID,'회원정보 조회','"+common.client_ip(request)+"',sysdate() "+
                        "FROM CORP_USER WHERE CORP_ID = '"+CORP_ID+"' ";
                
                common.execute_query(strSQL);

		        view.setViewName("user/user_detail");
            }
            else
            {
                view = common.redirectView(view);   

		    }
        } 
        catch (JSONException ex){
            ex.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }     
        return view;
    }

    @RequestMapping(value = "/user_detail/update_corp_level", produces = "application/text; charset=utf8")
    @ResponseBody
    public String update_corp_level(HttpServletRequest request) {                 
        String result = "";                    
        JSONObject user = null;
        try {
            user = common.getAttribute(request);
            if(user!=null){

                String ADMIN_ID = user.get("ADMIN_ID").toString();
                String IN_ID = user.get("ID").toString();
                String NAME = user.get("NAME").toString();
                String AUTH = user.get("AUTH").toString();
                String TOP_MENU = "1";
                String LEFT_MENU = "1";

                String json_string = common.get_body(request);
                JSONObject body = new JSONObject(json_string);
                
                String CORP_ID = body.get("CORP_ID").toString();                
                String SALE_LEVEL = body.get("SALE_LEVEL").toString();          

                String strSQL = "";
                strSQL = "CALL SP_CMS_CORP_USER_LEVEL_UPDATE('"+CORP_ID+"', '"+SALE_LEVEL+"')";
                JSONArray jsonArray = common.execute_query_commit(strSQL);
                
		        result = common.get_data_ok(jsonArray);
            }
            else
            {
                result = common.get_error_message("session error");
		    }
        } 
        catch (JSONException ex){
            ex.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        return result;
    }

    @RequestMapping(value = "/user_detail/update_corp_user", produces = "application/text; charset=utf8")
    @ResponseBody
    public String update_corp_user(HttpServletRequest request) {                 
        String result = "";            
        JSONObject user = null;
        try {
            user = common.getAttribute(request);
            if(user!=null){

                String ADMIN_ID = user.get("ADMIN_ID").toString();
                String IN_ID = user.get("ID").toString();
                String NAME = user.get("NAME").toString();
                String AUTH = user.get("AUTH").toString();
                String TOP_MENU = "1";
                String LEFT_MENU = "1";

                String json_string = common.get_body(request);
                JSONObject body = new JSONObject(json_string);
                
                String CORP_ID = body.get("CORP_ID").toString();                
                String SALE_LEVEL = body.get("SALE_LEVEL").toString();          
                String INTRO_TXT = body.get("INTRO_TXT").toString();          

                String strSQL = "";
                strSQL = "CALL SP_CMS_CORP_USER_INFO_UPDATE('"+CORP_ID+"', '"+SALE_LEVEL+"', '"+INTRO_TXT+"')";
                JSONArray jsonArray = common.execute_query_commit(strSQL);
                
		        result = common.get_data_ok(jsonArray);
            }
            else
            {
                result = common.get_error_message("session error");
		    }
        } 
        catch (JSONException ex){
            ex.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        return result;
    }

    @RequestMapping(value = "/user_detail/update_corp_mng", produces = "application/text; charset=utf8")
    @ResponseBody
    public String update_corp_mng(HttpServletRequest request) {                 
        String result = "";                    
        JSONObject user = null;
        try {
            user = common.getAttribute(request);
            if(user!=null){

                String ADMIN_ID = user.get("ADMIN_ID").toString();
                String IN_ID = user.get("ID").toString();
                String NAME = user.get("NAME").toString();
                String AUTH = user.get("AUTH").toString();
                String TOP_MENU = "1";
                String LEFT_MENU = "1";

                String json_string = common.get_body(request);
                JSONObject body = new JSONObject(json_string);
                
                String CORP_ID = body.get("CORP_ID").toString();                
                String IN_NAME = body.get("NAME").toString();          
                String PSTN_NM = body.get("PSTN_NM").toString();          
                String TEL = body.get("TEL").toString();                          

                String strSQL = "";
                strSQL = "CALL SP_CMS_CORP_MNG_UPDATE('"+CORP_ID+"', '"+IN_NAME+"', '"+PSTN_NM+"','"+TEL+"','"+ADMIN_ID+"')";
                JSONArray jsonArray = common.execute_query_commit(strSQL);
                
		        result = common.get_data_ok(jsonArray);
            }
            else
            {
                result = common.get_error_message("session error");
		    }
        } 
        catch (JSONException ex){
            ex.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        return result;
    }

    @RequestMapping(value = "/user_detail/insert_contact_info", produces = "application/text; charset=utf8")
    @ResponseBody
    public String insert_contact_info(HttpServletRequest request) {                 
        String result = "";            
        JSONObject user = null;
        try {
            user = common.getAttribute(request);
            if(user!=null){

                String ADMIN_ID = user.get("ADMIN_ID").toString();
                String IN_ID = user.get("ID").toString();
                String NAME = user.get("NAME").toString();
                String AUTH = user.get("AUTH").toString();
                String TOP_MENU = "1";
                String LEFT_MENU = "1";

                String json_string = common.get_body(request);
                JSONObject body = new JSONObject(json_string);
                
                String CORP_ID = body.get("CORP_ID").toString();                
                String CONT_METHOD = body.get("CONT_METHOD").toString();          
                String CONT_DATE = body.get("CONT_DATE").toString();          
                String CONTENT = body.get("CONTENT").toString();                                          

                String strSQL = "";
                strSQL = "CALL SP_CMS_CORP_CONTACT_DATE_INSERT('"+CORP_ID+"', '"+CONT_METHOD+"', '"+CONT_DATE+"','"+CONTENT+"','"+ADMIN_ID+"')";
                JSONArray jsonArray = common.execute_query_commit(strSQL);
                
		        result = common.get_data_ok(jsonArray);
            }
            else
            {
                result = common.get_error_message("session error");
		    }
        } 
        catch (JSONException ex){
            ex.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        return result;
    }

    @RequestMapping(value = "/user_detail/contact_info", produces = "application/text; charset=utf8")
    @ResponseBody
    public String contact_info(HttpServletRequest request) {                 
        String result = "";            
        JSONObject user = null;
        try {
            user = common.getAttribute(request);
            if(user!=null){

                String ADMIN_ID = user.get("ADMIN_ID").toString();
                String IN_ID = user.get("ID").toString();
                String NAME = user.get("NAME").toString();
                String AUTH = user.get("AUTH").toString();
                String TOP_MENU = "1";
                String LEFT_MENU = "1";

                String json_string = common.get_body(request);
                JSONObject body = new JSONObject(json_string);
                
                String CONT_ID = body.get("CONT_ID").toString();                                                                        

                String strSQL = "";
                strSQL = "CALL SP_CMS_CORP_CONTACT_INFO('"+CONT_ID+"')";
                JSONArray jsonArray = common.execute_query(strSQL);
                
		        result = common.get_data_ok(jsonArray);
            }
            else
            {
                result = common.get_error_message("session error");
		    }
        } 
        catch (JSONException ex){
            ex.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        return result;
    }

    @RequestMapping(value = "/user_detail/update_contact_info", produces = "application/text; charset=utf8")
    @ResponseBody
    public String update_contact_info(HttpServletRequest request) {                 
        String result = "";            
        JSONObject user = null;
        try {
            user = common.getAttribute(request);
            if(user!=null){

                String ADMIN_ID = user.get("ADMIN_ID").toString();
                String IN_ID = user.get("ID").toString();
                String NAME = user.get("NAME").toString();
                String AUTH = user.get("AUTH").toString();
                String TOP_MENU = "1";
                String LEFT_MENU = "1";

                String json_string = common.get_body(request);
                JSONObject body = new JSONObject(json_string);
                
                String CONT_ID = body.get("CONT_ID").toString();                                                                        
                String CONT_METHOD = body.get("CONT_METHOD").toString();                                                                        
                String CONT_DATE = body.get("CONT_DATE").toString();                                                                        
                String CONTENT = body.get("CONTENT").toString();                                                                        

                String strSQL = "";
                strSQL = "CALL SP_CMS_CORP_CONTACT_UPDATE('"+CONT_ID+"', '"+CONT_METHOD+"', '"+CONT_DATE+"','"+CONTENT+"','"+ADMIN_ID+"')";
                JSONArray jsonArray = common.execute_query_commit(strSQL);
                
		        result = common.get_data_ok(jsonArray);
            }
            else
            {
                result = common.get_error_message("session error");
		    }
        } 
        catch (JSONException ex){
            ex.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        return result;
    }

    @RequestMapping(value = "/user_detail/delete_contact_info", produces = "application/text; charset=utf8")
    @ResponseBody
    public String delete_contact_info(HttpServletRequest request) {                 
        String result = "";            
        JSONObject user = null;
        try {
            user = common.getAttribute(request);
            if(user!=null){

                String ADMIN_ID = user.get("ADMIN_ID").toString();
                String IN_ID = user.get("ID").toString();
                String NAME = user.get("NAME").toString();
                String AUTH = user.get("AUTH").toString();
                String TOP_MENU = "1";
                String LEFT_MENU = "1";

                String json_string = common.get_body(request);
                JSONObject body = new JSONObject(json_string);
                
                String CONT_ID = body.get("CONT_ID").toString();                                                                                                                                                       

                String strSQL = "";
                strSQL = "CALL SP_CMS_CORP_CONTACT_DELETE('"+CONT_ID+"')";
                JSONArray jsonArray = common.execute_query_commit(strSQL);
                
		        result = common.get_data_ok(jsonArray);
            }
            else
            {
                result = common.get_error_message("session error");
		    }
        } 
        catch (JSONException ex){
            ex.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        return result;
    }

    @RequestMapping(value = "/user_detail/insert_item_history", produces = "application/text; charset=utf8")
    @ResponseBody
    public String insert_item_history(HttpServletRequest request) {                 
        String result = "";            
        JSONObject user = null;
        try {
            user = common.getAttribute(request);
            if(user!=null){

                String ADMIN_ID = user.get("ADMIN_ID").toString();
                String IN_ID = user.get("ID").toString();
                String NAME = user.get("NAME").toString();
                String AUTH = user.get("AUTH").toString();
                String TOP_MENU = "1";
                String LEFT_MENU = "1";

                String json_string = common.get_body(request);
                JSONObject body = new JSONObject(json_string);
                
                String REQ_ID = body.get("REQ_ID").toString();
                String IN_STAT = body.get("IN_STAT").toString(); 
                String MEMO = body.get("MEMO").toString(); 

                String strSQL = "";
                strSQL = "CALL SP_CMS_ITEM_REQ_HISTORY_INSERT('"+REQ_ID+"','"+IN_STAT+"','"+MEMO+"','"+ADMIN_ID+"')";
                JSONArray jsonArray = common.execute_query_commit(strSQL);
                
		        result = common.get_data_ok(jsonArray);
            }
            else
            {
                result = common.get_error_message("session error");
		    }
        } 
        catch (JSONException ex){
            ex.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        return result;
    }


}