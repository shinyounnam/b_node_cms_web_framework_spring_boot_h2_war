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
import com.veteranscout.cmsweb.alarm.*;
import org.json.*;

@Controller
public class AdminCmsBoardInfoController {  

    private String module_name = "admin_cms_board_info";
    private CommonUtil common = new CommonUtil();
    // private static AlarmNoticeServer alarmNoticeServer = new AlarmNoticeServer();
    private AlarmUtil alarmUtil = new AlarmUtil();
    
    @RequestMapping(value = "/admin_cms_board_info/info/{id}")    
    // @ResponseBody    
    public ModelAndView admin_cms_board_info(CommandMap body, HttpServletRequest request,
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
                String LEFT_MENU = "1";
                
                int ID = id;
                

                // header.jsp
                JSONObject jsonHeader = common.getJsonHeader(request, TOP_MENU, LEFT_MENU);
                
                jsonHeader.put("ID",ID);                                
                
                view.addObject("header",jsonHeader);

                String strSQL = "";
                strSQL = "CALL SP_CMS_BOARD_INFO_1_1_92('"+ID+"')";
                JSONArray jsonArray = common.execute_query(strSQL);
                view.addObject("data",jsonArray);

		        view.setViewName("board/admin_cms_board_info");
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

    @RequestMapping(value = "/admin_cms_board_info/delete", produces = "application/text; charset=utf8")
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
                strSQL = "CALL SP_CMS_BOARD_DELETE('"+ID+"', '"+ADMIN_ID+"')";
                                 
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

    @RequestMapping(value = "/admin_cms_board_info/veteran_notice_service", produces = "application/text; charset=utf8")
    @ResponseBody    
    public String veteran_notice_service(HttpServletRequest request) 
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
                
                String GBN = body.get("GBN").toString();
                String GBN_2 = body.get("GBN_2").toString();
                String N_ID = body.get("N_ID").toString();
                String MSG_CORP_ID = body.get("MSG_CORP_ID").toString();
                if (MSG_CORP_ID.equals(""))
                {
                    MSG_CORP_ID = "0";
                }
                String TEST_YN = body.get("TEST_YN").toString();
                if (TEST_YN.equals(""))
                {
                    TEST_YN = "N";
                }
                String WORK_DATE = common.now_date_string();	
                String AREA_CODE1 = body.get("AREA_CODE1").toString();
                if (AREA_CODE1.equals(""))
                {
                    AREA_CODE1 = "";
                }
                
                JSONObject jsonData = new JSONObject();
                jsonData.put("GBN",GBN);
                jsonData.put("GBN_2",GBN_2);
                jsonData.put("WORK_DATE",WORK_DATE);
                jsonData.put("N_ID",N_ID);
                jsonData.put("MSG_CORP_ID",MSG_CORP_ID);
                jsonData.put("PUSH_YN","Y");
                jsonData.put("TEST_YN",TEST_YN);
                jsonData.put("AREA_CODE1",AREA_CODE1);

                // 알림 발송
                // alarmNoticeServer.send_alarm_json_object(jsonData);
                /*
                JSONObject send_alarm = new JSONObject();
                send_alarm.put("API_KEY",common.ALARM_API_KEY);
                send_alarm.put("notice",jsonData);
                alarmUtil.send_alarm_notice_json_object(send_alarm);
                */
                common.send_alarm_json_object(common.SEND_ALARM_HOST, common.NOTICE_PORT, jsonData);

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