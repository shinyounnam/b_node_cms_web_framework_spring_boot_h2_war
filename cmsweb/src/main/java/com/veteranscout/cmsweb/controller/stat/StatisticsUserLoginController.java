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
public class StatisticsUserLoginController {  

    private String module_name = "statistics_user_login";
    private CommonUtil common = new CommonUtil();
    
    @RequestMapping(value = "/statistics_user_login")    
    // @ResponseBody    
    public ModelAndView statistics_user_login(CommandMap body, HttpServletRequest request) 
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
                String LEFT_MENU = "3";
                
                String S_DATE = common.start_now_date_string();
                String E_DATE = common.now_date_string();
                int PAGE = 1;
                int EVENT_ID = 0;
                String EVENT_NAME = "";

                // header.jsp
                JSONObject jsonHeader = common.getJsonHeader(request, TOP_MENU, LEFT_MENU);
                

                jsonHeader.put("S_DATE",S_DATE);        
                jsonHeader.put("E_DATE",E_DATE);        
                jsonHeader.put("PAGE",PAGE);        
                jsonHeader.put("EVENT_ID",EVENT_ID);        
                jsonHeader.put("EVENT_NAME",EVENT_NAME);        
                
                view.addObject("header",jsonHeader);

                String strSQL = "CALL SP_CMS_STAT_EMP_LOGIN_LIST_1_1_92_1('"+S_DATE+"','"+E_DATE+"','"+EVENT_ID+"','"+PAGE+"')";
                JSONArray jsonArray = common.execute_query(strSQL);
                view.addObject("data",jsonArray);

		        view.setViewName("stat/statistics_user_login");
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

    @RequestMapping(value = "/statistics_user_login/move/{s_date}/{e_date}/{event_id}/{page}")    
    // @ResponseBody    
    public ModelAndView statistics_user_login_move(CommandMap body, HttpServletRequest request,                                                                                
                                        @PathVariable("s_date") String s_date,
                                        @PathVariable("e_date") String e_date,
                                        @PathVariable("event_id") int event_id,
                                        @PathVariable("page") int page
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
                String LEFT_MENU = "3";
                
                String S_DATE = s_date;
                String E_DATE = e_date;
                int PAGE = page;
                int EVENT_ID = event_id;
                String EVENT_NAME = "";

                // header.jsp
                JSONObject jsonHeader = common.getJsonHeader(request, TOP_MENU, LEFT_MENU);               

                jsonHeader.put("S_DATE",S_DATE);        
                jsonHeader.put("E_DATE",E_DATE);        
                jsonHeader.put("PAGE",PAGE);        
                jsonHeader.put("EVENT_ID",EVENT_ID);        
                jsonHeader.put("EVENT_NAME",EVENT_NAME);        
                
                view.addObject("header",jsonHeader);

                String strSQL = "CALL SP_CMS_STAT_EMP_LOGIN_LIST_1_1_92_1('"+S_DATE+"','"+E_DATE+"','"+EVENT_ID+"','"+PAGE+"')";
                JSONArray jsonArray = common.execute_query(strSQL);
                view.addObject("data",jsonArray);

		        view.setViewName("stat/statistics_user_login");
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