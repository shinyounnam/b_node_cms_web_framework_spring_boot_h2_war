package com.veteranscout.cmsweb.controller.admin;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.veteranscout.cmsweb.util.*;
import org.json.*;

@Controller
public class AdminMngUpdateController {  

    private String module_name = "admin_mng_update";
    private CommonUtil common = new CommonUtil();
    
    @RequestMapping(value = "/admin_mng_update/info/{admin_id}")    
    // @ResponseBody    
    public ModelAndView admin_mng_update(CommandMap body, HttpServletRequest request, HttpServletResponse response,
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
                
                String SEARCH_TYPE = "1";
                String SEARCH_TXT = "";
                int PAGE = 1;

                // header.jsp
                JSONObject jsonHeader = common.getJsonHeader(request, TOP_MENU, LEFT_MENU);                

                jsonHeader.put("SEARCH_TYPE",SEARCH_TYPE);        
                jsonHeader.put("SEARCH_TXT",SEARCH_TXT);                        
                jsonHeader.put("PAGE",PAGE);    
                
                
                view.addObject("header",jsonHeader);
                int IN_ADMIN_ID = admin_id;
                String strSQL = "";
                strSQL = "CALL SP_CMS_ADMIN_MNG_UPDATE_INFO('"+IN_ADMIN_ID+"') ";
                JSONArray jsonArray = common.execute_query(strSQL);
                view.addObject("data",jsonArray);

		        view.setViewName("admin/admin_mng_update");
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

    @RequestMapping(value = "/admin_mng_update/update", produces = "application/text; charset=utf8")    
    @ResponseBody    
    public String update(HttpServletRequest request) 
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
                String MG_LEVEL = body.get("MG_LEVEL").toString();
                String IN_NAME = body.get("NAME").toString();
                String EMAIL = body.get("EMAIL").toString();
                String TEL = body.get("TEL").toString();
                String HP_TEL = body.get("HP_TEL").toString();
                String MEMO = body.get("MEMO").toString();
                String GROUP_ID = body.get("GROUP_ID").toString();
                String TEAM_NM = body.get("TEAM_NM").toString();
                String REG_ID = body.get("ADMIN_ID").toString();
                String PSTN_NM = body.get("PSTN_NM").toString();                
                
                String strSQL = "";
                strSQL = "CALL SP_CMS_ADMIN_USER_UPDATE_1_0_5("+ID+", '"+GROUP_ID+"', "+
								 "'"+TEAM_NM+"', '"+IN_NAME+"', '"+PSTN_NM+"', '"+AUTH+"'," +
								 "'"+MG_LEVEL+"', '"+EMAIL+"',  '"+TEL+"',  '"+HP_TEL+"', '"+MEMO+"', "+REG_ID+")";
                                 
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

    @RequestMapping(value = "/admin_mng_update/delete", produces = "application/text; charset=utf8")    
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
                
                String ID = body.get("ADMIN_ID").toString();                                    
                
                String strSQL = "";
                strSQL = "CALL SP_CMS_ADMIN_USER_DELETE('"+ID+"', "+ADMIN_ID+")";
                                 
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