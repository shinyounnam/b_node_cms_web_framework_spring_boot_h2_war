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
public class SltEventController {  

    private String module_name = "slt_event";
    private CommonUtil common = new CommonUtil();
    
    @RequestMapping(value = "/slt_event/info")    
    // @ResponseBody    
    public ModelAndView slt_event(CommandMap body, HttpServletRequest request) 
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
                String LEFT_MENU = "4";
                
                String SEARCH_TYPE = "1";
                String SEARCH_TXT = "";
                int PAGE = 1;
                int PAGE_SIZE = 20;

                // header.jsp
                JSONObject jsonHeader = common.getJsonHeader(request, TOP_MENU, LEFT_MENU);                

                jsonHeader.put("SEARCH_TYPE",SEARCH_TYPE);        
                jsonHeader.put("SEARCH_TXT",SEARCH_TXT);        
                jsonHeader.put("PAGE",PAGE);        
                jsonHeader.put("PAGE_SIZE",PAGE_SIZE);                            
                
                view.addObject("header",jsonHeader);

                String strSQL = "";
                strSQL = "CALL SP_M_CMS_BOARD_SELECT_POP_1_1_92('"+SEARCH_TYPE+"','"+SEARCH_TXT+"','"+PAGE_SIZE+"','"+PAGE+"')";
                JSONArray jsonArray = common.execute_query(strSQL);
                view.addObject("data",jsonArray);

		        view.setViewName("stat/slt_event");
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

    @RequestMapping(value = "/slt_event/move/{search_type}/{search_txt}/{page_size}/{page}")    
    // @ResponseBody    
    public ModelAndView move(CommandMap body, HttpServletRequest request,
    @PathVariable("search_type") String search_type,
    @PathVariable("search_txt") String search_txt,
    @PathVariable("page_size") int page_size,
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
                String LEFT_MENU = "4";
                
                String SEARCH_TYPE = search_type;
                String SEARCH_TXT = search_txt;
                SEARCH_TXT = common.get_empty_value(SEARCH_TXT);
                int PAGE = page;
                int PAGE_SIZE = page_size;

                // header.jsp
                JSONObject jsonHeader = common.getJsonHeader(request, TOP_MENU, LEFT_MENU);
                

                jsonHeader.put("SEARCH_TYPE",SEARCH_TYPE);        
                jsonHeader.put("SEARCH_TXT",SEARCH_TXT);        
                jsonHeader.put("PAGE",PAGE);        
                jsonHeader.put("PAGE_SIZE",PAGE_SIZE);                            
                
                view.addObject("header",jsonHeader);

                String strSQL = "";
                strSQL = "CALL SP_M_CMS_BOARD_SELECT_POP_1_1_92('"+SEARCH_TYPE+"','"+SEARCH_TXT+"','"+PAGE_SIZE+"','"+PAGE+"')";
                JSONArray jsonArray = common.execute_query(strSQL);
                view.addObject("data",jsonArray);

		        view.setViewName("stat/slt_event");
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