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
public class CorpUserAddController {  

    private String module_name = "corp_user_add";
    private CommonUtil common = new CommonUtil();
    
    @RequestMapping(value = "/corp_user_add/info")    
    // @ResponseBody    
    public ModelAndView corp_user_add_info(CommandMap body, HttpServletRequest request) 
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
                String LEFT_MENU = "3";
                String CORP_ID =  "";

                // header.jsp
                JSONObject jsonHeader = common.getJsonHeader(request, TOP_MENU, LEFT_MENU);
                
                jsonHeader.put("CORP_ID",CORP_ID);
                
                view.addObject("header",jsonHeader);

		        view.setViewName("user/corp_user_add");
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

    @RequestMapping(value = "/corp_user_add/update_info/{corp_id}")    
    // @ResponseBody    
    public ModelAndView corp_user_add_update_info(CommandMap body, HttpServletRequest request,
    @PathVariable("corp_id") String corp_id
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
                String LEFT_MENU = "3";
                String CORP_ID =  corp_id;

                // header.jsp
                JSONObject jsonHeader = common.getJsonHeader(request, TOP_MENU, LEFT_MENU);
                
                jsonHeader.put("CORP_ID",CORP_ID);

                view.addObject("header",jsonHeader);

                String strSQL = "";
                strSQL = "CALL SP_CMS_ADMIN_CORP_USER_INFO('"+CORP_ID+"')";

                JSONArray jsonArray = common.execute_query(strSQL);
                view.addObject("data",jsonArray);                

		        view.setViewName("user/corp_user_add");
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


    @RequestMapping(value = "/corp_user_add/insert_submit", produces = "application/text; charset=utf8")    
    @ResponseBody    
    public String corp_user_add_update_info(HttpServletRequest request) 
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
                
                String CORP_NAME = body.get("CORP_NAME").toString();
                String BIZ_NO = body.get("BIZ_NO").toString();
                String REP_NAME = body.get("REP_NAME").toString();
                String REP_TEL = body.get("REP_TEL").toString();
                String POST_CODE = body.get("POST_CODE").toString();
                String ADDR1 = body.get("ADDR1").toString();
                String ADDR2 = body.get("ADDR2").toString();
                String CHAMGO = body.get("CHAMGO").toString();
                String POS_LAT = body.get("POS_LAT").toString();
                String POS_LNG = body.get("POS_LNG").toString();
                String HOME_URL = body.get("HOME_URL").toString();
                String EMAIL = body.get("EMAIL").toString();
                String MAN_NAME = body.get("MAN_NAME").toString();
                String MAN_PSTN = body.get("MAN_PSTN").toString();
                String MAN_TEL = body.get("MAN_TEL").toString();
                String INTRO_TXT = body.get("INTRO_TXT").toString();
                
                String strSQL = "";
                strSQL = "CALL SP_CMS_CORP_USER_DATA_INSERT('"+CORP_NAME+"','"+BIZ_NO+"',"+
                        "'"+REP_NAME+"','"+REP_TEL+"','"+POST_CODE+"','"+ADDR1+"','"+ADDR2+"',"+
                        "'"+CHAMGO+"','"+POS_LAT+"','"+POS_LNG+"','"+HOME_URL+"',"+
                        "'"+EMAIL+"','"+MAN_NAME+"','"+MAN_PSTN+"','"+MAN_TEL+"','"+INTRO_TXT+"')";

                JSONArray jsonArray = common.execute_query_commit(strSQL);
                int CORP_ID = common.get_json_array_value_int(jsonArray,"CORP_ID");

                JSONObject jsonResult = new JSONObject();
                jsonResult.put("result","ok");
                jsonResult.put("message","");
                jsonResult.put("CORP_ID",CORP_ID);
                result = jsonResult.toString();
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


    @RequestMapping(value = "/corp_user_add/insert_multi", produces = "application/text; charset=utf8")    
    @ResponseBody    
    public String insert_multi(HttpServletRequest request) 
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
                
                String CORP_NAME = body.get("CORP_NAME").toString();
                String BIZ_NO = body.get("BIZ_NO").toString();
                String REP_NAME = body.get("REP_NAME").toString();
                String REP_TEL = body.get("REP_TEL").toString();
                String POST_CODE = body.get("POST_CODE").toString();
                String ADDR1 = body.get("ADDR1").toString();
                String ADDR2 = body.get("ADDR2").toString();
                String CHAMGO = body.get("CHAMGO").toString();
                String POS_LAT = body.get("POS_LAT").toString();
                String POS_LNG = body.get("POS_LNG").toString();
                String HOME_URL = body.get("HOME_URL").toString();
                String EMAIL = body.get("EMAIL").toString();
                String MAN_NAME = body.get("MAN_NAME").toString();
                String MAN_PSTN = body.get("MAN_PSTN").toString();
                String MAN_TEL = body.get("MAN_TEL").toString();
                String INTRO_TXT = body.get("INTRO_TXT").toString();
                
                String strSQL = "";
                strSQL = "CALL SP_CMS_CORP_USER_DATA_INSERT('"+CORP_NAME+"','"+BIZ_NO+"',"+
                        "'"+REP_NAME+"','"+REP_TEL+"','"+POST_CODE+"','"+ADDR1+"','"+ADDR2+"',"+
                        "'"+CHAMGO+"','"+POS_LAT+"','"+POS_LNG+"','"+HOME_URL+"',"+
                        "'"+EMAIL+"','"+MAN_NAME+"','"+MAN_PSTN+"','"+MAN_TEL+"','"+INTRO_TXT+"')";

                JSONArray jsonArray = common.execute_query_commit(strSQL);
                int CORP_ID = common.get_json_array_value_int(jsonArray,"CORP_ID");

                JSONObject jsonResult = new JSONObject();
                jsonResult.put("result","ok");
                jsonResult.put("message","");
                jsonResult.put("CORP_ID",CORP_ID);
                result = jsonResult.toString();
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