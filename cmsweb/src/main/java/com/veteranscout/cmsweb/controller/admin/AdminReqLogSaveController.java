package com.veteranscout.cmsweb.controller.admin;

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
public class AdminReqLogSaveController {  

    private String module_name = "admin_req_log_save";
    private CommonUtil common = new CommonUtil();
    
    @RequestMapping(value = "/admin_req_log_save")    
    // @ResponseBody    
    public ModelAndView admin_req_log_save(CommandMap body, HttpServletRequest request) 
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
                String TOP_MENU = "0";
                String LEFT_MENU = "4";
                
                // header.jsp
                JSONObject jsonHeader = common.getJsonHeader(request, TOP_MENU, LEFT_MENU);
                
                view.addObject("header",jsonHeader);

		        view.setViewName("admin/admin_req_log_save");
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

    @RequestMapping(value = "/admin_req_log_save/insert", produces = "application/text; charset=utf8")   
    @ResponseBody    
    public String insert(HttpServletRequest request) 
    {        
        JSONObject user = null;
        String result = "";
        try {
            user = common.getAttribute(request);
            if(user!=null){		        

                String ADMIN_ID = user.get("ADMIN_ID").toString();
                String IN_ID = user.get("ID").toString();
                String NAME = user.get("NAME").toString();
                String AUTH = user.get("AUTH").toString();
                String TOP_MENU = "2";
                String LEFT_MENU = "3";

                String jsonString = common.get_body(request);
                JSONObject body = new JSONObject(jsonString);
                
                String USER_TYPE = body.get("USER_TYPE").toString();
                String CONTENT = body.get("CONTENT").toString();
                String USER_ID = body.get("USER_ID").toString();
                
                String strSQL = "";
                strSQL = "CALL SP_CMS_EMP_POS_REQ_LIST_INSERT('"+ADMIN_ID+"', '"+USER_TYPE+"', '"+USER_ID+"', '"+CONTENT+"')";
                                 
                JSONArray jsonArray = common.execute_query_commit(strSQL);                
                result = common.get_data_ok(jsonArray);
            }
            else
            {
                 result = common.get_error_message("session error");

		    }
        } 
        catch (Exception e) {
            e.printStackTrace();
        }     
        return result;
    }

}