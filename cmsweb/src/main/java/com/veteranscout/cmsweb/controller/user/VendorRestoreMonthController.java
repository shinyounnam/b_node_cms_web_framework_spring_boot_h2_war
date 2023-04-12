package com.veteranscout.cmsweb.controller.user;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.veteranscout.cmsweb.util.*;
import org.json.*;

@Controller
public class VendorRestoreMonthController {  

    private String module_name = "vendor_restore_month";
    private CommonUtil common = new CommonUtil();
    
    @RequestMapping(value = "/vendor_restore_month")    
    // @ResponseBody    
    public ModelAndView vendor_restore_month(CommandMap body, HttpServletRequest request, HttpServletResponse response) 
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
                String LEFT_MENU = "5";
                
                String SEARCH_TYPE = "ALL";
                String SEARCH_TXT = "";
                int PAGE = 1;

                // header.jsp
                JSONObject jsonHeader = common.getJsonHeader(request, TOP_MENU, LEFT_MENU);
                              
                jsonHeader.put("SEARCH_TYPE",SEARCH_TYPE);                
                jsonHeader.put("SEARCH_TXT",SEARCH_TXT);                
                jsonHeader.put("PAGE",PAGE);    
                
                
                view.addObject("header",jsonHeader);

                String strSQL = "CALL SP_M_CORP_USER_RESTORE_MONTH_LIST_PAGE_1_1_92('"+SEARCH_TYPE+"','"+SEARCH_TXT+"','"+PAGE+"')";
                JSONArray jsonArray = common.execute_query(strSQL);
                view.addObject("data",jsonArray);

		        view.setViewName("user/vendor_restore_month");
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

    @RequestMapping(value = "/vendor_restore_month/move",method=RequestMethod.GET)    
    // @ResponseBody    
    public ModelAndView vendor_restore_month_move(CommandMap body, HttpServletRequest request,                                        
                                        @RequestParam("search_type") String search_type,
                                        @RequestParam("search_txt") String search_txt,
                                        @RequestParam("page") int page
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
                String LEFT_MENU = "5";             

                String SEARCH_TYPE = search_type;
                String SEARCH_TXT = common.decodeURIComponent(search_txt);                
                int PAGE = page;

                // header.jsp
                JSONObject jsonHeader = common.getJsonHeader(request, TOP_MENU, LEFT_MENU);
                
                jsonHeader.put("SEARCH_TYPE",SEARCH_TYPE);                
                jsonHeader.put("SEARCH_TXT",SEARCH_TXT);                
                jsonHeader.put("PAGE",PAGE);    
                
                
                
                view.addObject("header",jsonHeader);

                String strSQL = "CALL SP_M_CORP_USER_RESTORE_MONTH_LIST_PAGE_1_1_92('"+SEARCH_TYPE+"','"+SEARCH_TXT+"','"+PAGE+"')";
                JSONArray jsonArray = common.execute_query(strSQL);
                view.addObject("data",jsonArray);

		        view.setViewName("user/vendor_restore_month");
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