package com.veteranscout.cmsweb.controller.board;

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
public class AdminCmsFaqInfoController {  

    private String module_name = "admin_cms_faq_info";
    private CommonUtil common = new CommonUtil();
    
    @RequestMapping(value = "/admin_cms_faq_info/info/{id}")    
    // @ResponseBody    
    public ModelAndView admin_cms_faq_info(CommandMap body, HttpServletRequest request,
    @PathVariable("id") int id
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
                String TOP_MENU = "6";
                String LEFT_MENU = "2";         

                int ID = id;

                // header.jsp
                JSONObject jsonHeader = common.getJsonHeader(request, TOP_MENU, LEFT_MENU);                
                
                jsonHeader.put("ID",ID);                                
                
                String strSQL = "";
                strSQL = "CALL SP_CMS_FAQ_INFO('"+ID+"')";
                JSONArray jsonArray = common.execute_query(strSQL);

                view.addObject("header",jsonHeader);
                view.addObject("data",jsonArray);

		        view.setViewName("board/admin_cms_faq_info");
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

    @RequestMapping(value = "/admin_cms_faq_info/delete", produces = "application/text; charset=utf8")
    @ResponseBody    
    public String delete(HttpServletRequest request) 
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
                
                String ID = body.get("ID").toString();
                
                String strSQL = "";
                strSQL = "CALL SP_CMS_FAQ_DELETE('"+ID+"', '"+ADMIN_ID+"')";
                                 
                JSONArray jsonArray = common.execute_query_commit(strSQL);                
                result = common.get_message("ok","");
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