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
public class AdminMngPwdController {  

    private String module_name = "admin_mng_pwd";
    private CommonUtil common = new CommonUtil();
    
    @RequestMapping(value = "/admin_mng_pwd/info/{admin_id}")    
    // @ResponseBody    
    public ModelAndView admin_mng_pwd(CommandMap body, HttpServletRequest request,
    @PathVariable("admin_id") int admin_id
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
                String TOP_MENU = "0";
                String LEFT_MENU = "1";
                
                int IN_ADMIN_ID = admin_id;

                // header.jsp
                JSONObject jsonHeader = common.getJsonHeader(request, TOP_MENU, LEFT_MENU);                

                jsonHeader.put("IN_ADMIN_ID",IN_ADMIN_ID);        
                
                view.addObject("header",jsonHeader);                
                
		        view.setViewName("admin/admin_mng_pwd");
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

    @RequestMapping(value = "/admin_mng_pwd_change/info")    
    // @ResponseBody    
    public ModelAndView admin_mng_pwd_change(CommandMap body, HttpServletRequest request) 
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
                String LEFT_MENU = "1";

                // header.jsp
                JSONObject jsonHeader = common.getJsonHeader(request, TOP_MENU, LEFT_MENU);                                
                
                view.addObject("header",jsonHeader);                
                
		        view.setViewName("admin/admin_mng_pwd_change");
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

    @RequestMapping(value = "/admin_mng_pwd/update_pwd", produces = "application/text; charset=utf8")    
    @ResponseBody    
    public String update_pwd(HttpServletRequest request) 
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
                
                String ID = body.get("ADMIN_ID").toString();                
                String PWD = body.get("PWD").toString();
                PWD = common.get_sha256(PWD);
                
                String strSQL = "";
                strSQL = "CALL SP_CMS_ADMIN_UPDATE_PWD_1_0_1('"+ID+"', '"+PWD+"')";

                JSONArray jsonArray = common.execute_query_commit(strSQL);                
                result = common.get_data_result(jsonArray);
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