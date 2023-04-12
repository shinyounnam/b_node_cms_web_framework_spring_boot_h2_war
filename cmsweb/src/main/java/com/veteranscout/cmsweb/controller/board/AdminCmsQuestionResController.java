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
public class AdminCmsQuestionResController {  

    private String module_name = "admin_cms_question_res";
    private CommonUtil common = new CommonUtil();
    private MmsUtil mmsUtil = new MmsUtil();
    
    @RequestMapping(value = "/admin_cms_question_res/info/{id}")    
    // @ResponseBody    
    public ModelAndView admin_cms_question_res_info(CommandMap body, HttpServletRequest request,
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
                String LEFT_MENU = "4";                
                
                int ID = id;

                // header.jsp
                JSONObject jsonHeader = common.getJsonHeader(request, TOP_MENU, LEFT_MENU);                
                
                jsonHeader.put("ID",ID);                                
                

                String strSQL = "";
                strSQL = "CALL SP_M_QUESTION_REQ_INFO_SELECT_WEB('"+ID+"')";
                JSONArray jsonArray = common.execute_query(strSQL);

                view.addObject("header",jsonHeader);
                view.addObject("data",jsonArray);

		        view.setViewName("board/admin_cms_question_res");
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

    @RequestMapping(value = "/admin_cms_question_res/save_send_sms", produces = "application/text; charset=utf8")    
    @ResponseBody    
    public String save_send_sms(HttpServletRequest request) 
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
                String TOP_MENU = "6";
                String LEFT_MENU = "3";
                
                String jsonString = common.get_body(request);
                JSONObject body = new JSONObject(jsonString);
                
                String TITLE = body.get("TITLE").toString();
                String CONTENT = body.get("CONTENT").toString();
                String REQ_ID = body.get("REQ_ID").toString();
                String RES_CONTENT = body.get("RES_CONTENT").toString();
                String TEL = body.get("TEL").toString();
                
                String strSQL = "";
                strSQL = "CALL SP_M_QUESTION_REQ_INFO_RES_INSERT_WEB('"+REQ_ID+"', '"+RES_CONTENT+"')";
                                 
                JSONArray jsonArray = common.execute_query_commit(strSQL);                

                JSONObject obj0_0 = new JSONObject();
                obj0_0.put("TITLE",TITLE);
                obj0_0.put("RES_CONTENT",RES_CONTENT);
                obj0_0.put("TEL",TEL);
                mmsUtil.SendQuestionRes(obj0_0);

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

    @RequestMapping(value = "/admin_cms_question_res/delete", produces = "application/text; charset=utf8")    
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
                String TOP_MENU = "6";
                String LEFT_MENU = "3";
                
                String jsonString = common.get_body(request);
                JSONObject body = new JSONObject(jsonString);
                
                String REQ_ID = body.get("REQ_ID").toString();
                
                String strSQL = "";
                strSQL = "CALL SP_M_QUESTION_REQ_INFO_RES_DELETE_WEB('"+REQ_ID+"')";
                                 
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