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
public class StatisticsUserController {  

    private String module_name = "statistics_user";
    private CommonUtil common = new CommonUtil();
    
    @RequestMapping(value = "/statistics_user")    
    // @ResponseBody    
    public ModelAndView statistics_user(CommandMap body, HttpServletRequest request) 
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
                String LEFT_MENU = "2";
                
                String DATE = common.now_yyyy();

                // header.jsp
                JSONObject jsonHeader = common.getJsonHeader(request, TOP_MENU, LEFT_MENU);                              

                jsonHeader.put("DATE",DATE);        
                
                view.addObject("header",jsonHeader);

                String strSQL = "CALL SP_CMS_STAT_EMP_LIST_1_1_92('"+DATE+"')";
                JSONArray jsonArray = common.execute_query(strSQL);
                view.addObject("data",jsonArray);

		        view.setViewName("stat/statistics_user");
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

    @RequestMapping(value = "/statistics_user/move/{date}/{end}")    
    // @ResponseBody    
    public ModelAndView statistics_user_move(CommandMap body, HttpServletRequest request,                                                                                
                                        @PathVariable("date") String date,
                                        @PathVariable("end") String end                                       
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
                String LEFT_MENU ="2";
                
                
                String DATE = date;

                // header.jsp
                JSONObject jsonHeader = common.getJsonHeader(request, TOP_MENU, LEFT_MENU);                

                jsonHeader.put("DATE",DATE);        
                
                view.addObject("header",jsonHeader);

                String strSQL = "CALL SP_CMS_STAT_EMP_LIST_1_1_92('"+DATE+"')";
                JSONArray jsonArray = common.execute_query(strSQL);
                view.addObject("data",jsonArray);

		        view.setViewName("stat/statistics_user");
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