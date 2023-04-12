package com.veteranscout.cmsweb.controller.attend;

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
public class AttendReqDetailController {  

    private String module_name = "attend_req_detail";
    private CommonUtil common = new CommonUtil();
    
    @RequestMapping(value = "/attend_req_detail/info/{corp_id}")    
    // @ResponseBody    
    public ModelAndView attend_req_detail_info(CommandMap body, HttpServletRequest request,
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
                String TOP_MENU = "1";
                String LEFT_MENU = "1";
                
                
                int CORP_ID = corp_id;

                // header.jsp
                JSONObject jsonHeader = common.getJsonHeader(request, TOP_MENU, LEFT_MENU);                
                
                String strSQL = "";
                strSQL = "CALL SP_CMS_CORP_USER_INFO_DETAIL('"+CORP_ID+"')";
                JSONArray jsonArray = common.execute_query(strSQL);

                view.addObject("data",jsonArray);

                view.addObject("header",jsonHeader);

		        view.setViewName("attend/attend_req_detail");
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

    @RequestMapping(value = "/attend_req_detail/update_accp_status", produces = "application/text; charset=utf8")
    @ResponseBody
    public String update_accp_status(HttpServletRequest request) {                 
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
                String ACCP_STATUS = body.get("ACCP_STATUS").toString();
                

                String strSQL = "";
                strSQL = "CALL SP_CMS_CORP_UPDATE_ACCP_STATUS('"+CORP_ID+"', '"+ACCP_STATUS+"', '"+ADMIN_ID+"')";
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

    @RequestMapping(value = "/attend_req_detail/save_contact", produces = "application/text; charset=utf8")
    @ResponseBody
    public String save_contact(HttpServletRequest request) {                 
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
                String CONTENT = body.get("CONTENT").toString();
                String REG_ID = body.get("REG_ID").toString();
                

                String strSQL = "";
                strSQL = "CALL SP_CMS_CORP_CONTACT_INSERT('"+CORP_ID+"', '"+CONT_METHOD+"', '"+CONTENT+"','"+REG_ID+"')";
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

    @RequestMapping(value = "/attend_req_detail/delete_corp", produces = "application/text; charset=utf8")
    @ResponseBody
    public String delete_corp(HttpServletRequest request) {                 
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

                String strSQL = "";
                strSQL = "CALL SP_CMS_CORP_USER_DELETE_WEB('"+CORP_ID+"')";
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

}