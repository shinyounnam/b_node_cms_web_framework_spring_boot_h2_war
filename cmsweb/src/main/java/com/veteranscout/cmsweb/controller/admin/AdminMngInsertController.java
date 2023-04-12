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
public class AdminMngInsertController {  

    private String module_name = "admin_mng_insert";
    private CommonUtil common = new CommonUtil();
    
    @RequestMapping(value = "/admin_mng_insert")    
    // @ResponseBody    
    public ModelAndView admin_mng_insert(CommandMap body, HttpServletRequest request, HttpServletResponse response) 
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

                String strSQL = "CALL SP_CMS_ATTEND_MNG_CODE_LIST() ";
                JSONArray jsonArray = common.execute_query(strSQL);
                view.addObject("data",jsonArray);

		        view.setViewName("admin/admin_mng_insert");
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

    @RequestMapping(value = "/admin_mng_insert/insert", produces = "application/text; charset=utf8")  
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
                
                String ID = body.get("ID").toString();
                String PWD = body.get("PWD").toString();
                PWD = common.get_sha256(PWD);
                String MG_LEVEL = body.get("MG_LEVEL").toString();
                String EMAIL = body.get("EMAIL").toString();
                String TEL = body.get("TEL").toString();
                String HP_TEL = body.get("HP_TEL").toString();
                String MEMO = body.get("MEMO").toString();
                String GROUP_ID = body.get("GROUP_ID").toString();
                String TEAM_NM = body.get("TEAM_NM").toString();
                String REG_ID = "1";
                String PSTN_NM = body.get("PSTN_NM").toString();
                
                String strSQL = "";
                strSQL = "CALL SP_CMS_ADMIN_USER_INSERT_1_0_5('"+ID+"', '"+PWD+"', '"+GROUP_ID+"', '"+TEAM_NM+"', '"+NAME+"', '"+PSTN_NM+"', '"+AUTH+"'," +
                "'"+MG_LEVEL+"', '"+EMAIL+"',  '"+TEL+"', '"+HP_TEL+"','"+MEMO+"', "+REG_ID+")";
                                 
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

    @RequestMapping(value = "/admin_mng_insert/id_check", produces = "application/text; charset=utf8")  
    @ResponseBody    
    public String id_check(HttpServletRequest request) 
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
                strSQL = "CALL SP_CMS_ADMIN_ID_CHECK('" + ID + "') ";
                                 
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