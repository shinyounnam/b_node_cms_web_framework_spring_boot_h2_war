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
public class CorpPwdChgController {  

    private String module_name = "corp_pwd_chg";
    private CommonUtil common = new CommonUtil();
    
    @RequestMapping(value = "/corp_pwd_chg/info/{corp_id}")    
    // @ResponseBody    
    public ModelAndView corp_pwd_chg(CommandMap body, HttpServletRequest request,
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

                // header.jsp
                JSONObject jsonHeader = common.getJsonHeader(request, TOP_MENU, LEFT_MENU);
                

                jsonHeader.put("CORP_ID",CORP_ID);
                
                
                view.addObject("header",jsonHeader);
		        view.setViewName("user/corp_pwd_chg");
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

    @RequestMapping(value = "/corp_pwd_chg/update_pwd", produces = "application/text; charset=utf8")
    @ResponseBody
    public String update_pwd(HttpServletRequest request) {                 
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
                String PWD = body.get("PWD").toString(); 
                PWD = common.get_sha256(PWD);

                String strSQL = "";
                strSQL = "CALL SP_CMS_CORP_USER_UPDATE_F_PWD('"+CORP_ID+"','"+PWD+"','"+common.client_ip(request)+"')";
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