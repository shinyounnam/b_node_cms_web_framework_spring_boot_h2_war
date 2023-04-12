package com.veteranscout.cmsweb.controller.stat;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.veteranscout.cmsweb.util.*;
import org.json.*;

@Controller
public class StatUserLoginReportController {  

    private String module_name = "stat_user_login_report";
    private CommonUtil common = new CommonUtil();
    
    @RequestMapping(value = "/stat_user_login_report/info/{s_date}/{e_date}/{event_id}")    
    // @ResponseBody    
    public ModelAndView stat_user_login_report(CommandMap body, HttpServletRequest request,    
    @PathVariable("s_date") String s_date,
    @PathVariable("e_date") String e_date,
    @PathVariable("event_id") int event_id
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
                String TOP_MENU = "7";
                String LEFT_MENU = "1";

                String S_DATE = s_date;
                String E_DATE = e_date;
                int EVENT_ID = event_id;
                
                // header.jsp
                JSONObject jsonHeader = common.getJsonHeader(request, TOP_MENU, LEFT_MENU);                            

                jsonHeader.put("S_DATE",S_DATE);                
                jsonHeader.put("E_DATE",E_DATE);                
                jsonHeader.put("EVENT_ID",EVENT_ID);                

                view.addObject("header",jsonHeader);

                String strSQL = "";
                strSQL = "CALL SP_CMS_STAT_EMP_LOGIN_REPORT_1_1_92_1('"+S_DATE+"','"+E_DATE+"','"+EVENT_ID+"')";
                JSONArray jsonArray = common.execute_query(strSQL);
                view.addObject("data",jsonArray);

		        view.setViewName("stat/stat_user_login_report");
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

}