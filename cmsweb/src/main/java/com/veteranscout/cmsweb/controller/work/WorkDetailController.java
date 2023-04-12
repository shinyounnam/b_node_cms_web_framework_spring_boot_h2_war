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

import com.veteranscout.cmsweb.util.*;
import org.json.*;

@Controller
public class WorkDetailController {  

    private String module_name = "work_detail";
    private CommonUtil common = new CommonUtil();
    
    @RequestMapping(value = "/work_detail/info/{work_id}")    
    // @ResponseBody    
    public ModelAndView work_detail_info(CommandMap body, HttpServletRequest request,
    @PathVariable("work_id") int work_id
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
                String NOW_DATE = common.now_date_string();

                // header.jsp
                JSONObject jsonHeader = common.getJsonHeader(request, TOP_MENU, LEFT_MENU);
                
                jsonHeader.put("WORK_ID",WORK_ID);                                
                jsonHeader.put("NOW_DATE",NOW_DATE);                  
                
                
                view.addObject("header",jsonHeader);
                String strSQL = "";
                strSQL = "CALL SP_M_CMS_WORK_SCHEDULE_INFO_DETAIL_1_1_65('"+WORK_ID+"')";
                JSONArray jsonArray = common.execute_query(strSQL);
                view.addObject("data",jsonArray);

		        view.setViewName("work/work_detail");
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

    @RequestMapping(value = "/work_detail/req_check_veteran_service_info", produces = "application/text; charset=utf8")    
    @ResponseBody    
    public String req_check_veteran_service_info(HttpServletRequest request) 
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
                
                
                String strSQL = "";
                strSQL = "CALL SP_M_WORK_SCHEDULE_INFO_NEED_CHECK_VS_APP('"+WORK_ID+"','"+NEED_ID+"','"+WORK_DATE+"')";
                                 
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