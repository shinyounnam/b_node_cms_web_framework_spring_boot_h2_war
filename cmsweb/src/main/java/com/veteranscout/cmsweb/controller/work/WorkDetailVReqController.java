package com.veteranscout.cmsweb.controller.work;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.veteranscout.cmsweb.alarm.AlarmReqServer;
import com.veteranscout.cmsweb.util.*;
import org.json.*;

@Controller
public class WorkDetailVReqController {  

    private String module_name = "work_detail_v_req";
    private CommonUtil common = new CommonUtil();

    // private static AlarmReqServer alarmReqServer = new AlarmReqServer();
    private AlarmUtil alarmUtil = new AlarmUtil();
    
    @RequestMapping(value = "/work_detail_v_req/info/{work_id}/{need_id}/{work_date}")    
    // @ResponseBody    
    public ModelAndView work_detail_v_req(CommandMap body, HttpServletRequest request,
    @PathVariable("work_id") int work_id,
    @PathVariable("need_id") int need_id,
    @PathVariable("work_date") String work_date
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
                String TOP_MENU = "3";
                String LEFT_MENU = "1";
                
                int WORK_ID = work_id;
                int NEED_ID = need_id;
                String WORK_DATE = work_date;

                // header.jsp
                JSONObject jsonHeader = common.getJsonHeader(request, TOP_MENU, LEFT_MENU);
                
                
                jsonHeader.put("WORK_ID",WORK_ID);                                
                jsonHeader.put("NEED_ID",NEED_ID);                                
                jsonHeader.put("WORK_DATE",WORK_DATE);                  
                
                
                view.addObject("header",jsonHeader);
                String strSQL = "";
                strSQL = "CALL SP_M_WORK_SCHEDULE_INFO_NEED_VS_APP('"+WORK_ID+"','"+NEED_ID+"','"+WORK_DATE+"')";
                JSONArray jsonArray = common.execute_query(strSQL);
                view.addObject("data",jsonArray);

		        view.setViewName("work/work_detail_v_req");
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

    @RequestMapping(value = "/work_detail_v_req/req_veteran_service", produces = "application/text; charset=utf8")    
    @ResponseBody    
    public String req_veteran_service(HttpServletRequest request) 
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
                
                String WORK_ID = body.get("WORK_ID").toString();
                String NEED_ID = body.get("NEED_ID").toString();
                String WORK_DATE = body.get("WORK_DATE").toString();
                String MULTI_REQ_YN = "N";
                
                JSONObject jsonData = new JSONObject();
                jsonData.put("WORK_ID",WORK_ID);
                jsonData.put("WORK_DATE",WORK_DATE);
                jsonData.put("NEED_ID",NEED_ID);
                jsonData.put("PUSH_YN","Y");
                jsonData.put("EVENT_YN","N");
                jsonData.put("MULTI_REQ_YN",MULTI_REQ_YN);
                jsonData.put("CMS_YN","Y");
                
                // 알림 발송
                // alarmReqServer.send_alarm_json_object(jsonData);
                /*
                JSONObject obj = new JSONObject();
                obj.put("API_KEY",common.ALARM_API_KEY);
                obj.put("alarm_req",jsonData);
                alarmUtil.send_alarm_req_json_object(obj);
                */
                common.send_alarm_json_object(common.SEND_ALARM_HOST, common.ALARM_REQ_PORT, jsonData);
                
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