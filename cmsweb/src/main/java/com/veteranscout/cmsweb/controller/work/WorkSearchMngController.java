package com.veteranscout.cmsweb.controller.work;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.veteranscout.cmsweb.util.*;
import org.json.*;

@Controller
public class WorkSearchMngController {  

    private String module_name = "work_search_mng";
    private CommonUtil common = new CommonUtil();
    
    @RequestMapping(value = "/work_search_mng")    
    // @ResponseBody    
    public ModelAndView work_search_mng(CommandMap body, HttpServletRequest request, HttpServletResponse response) 
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
                String LEFT_MENU = "2";
                
                String SEARCH_TYPE = "1";
                String SEARCH_TXT = "";
                int PAGE_SIZE = 15;
                int PAGE = 1;

                // header.jsp
                JSONObject jsonHeader = common.getJsonHeader(request, TOP_MENU, LEFT_MENU);
                                
                jsonHeader.put("SEARCH_TYPE",SEARCH_TYPE);                                
                jsonHeader.put("SEARCH_TXT",SEARCH_TXT); 
                jsonHeader.put("PAGE_SIZE",PAGE_SIZE);                               
                jsonHeader.put("PAGE",PAGE);    
                
                
                view.addObject("header",jsonHeader);

                String strSQL = "CALL SP_CMS_SEARCH_WORK_LIST('"+SEARCH_TYPE+"','"+SEARCH_TXT+"','"+PAGE_SIZE+"','"+PAGE+"')";
                JSONArray jsonArray = common.execute_query(strSQL);
                view.addObject("data",jsonArray);

		        view.setViewName("work/work_search_mng");
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

    @RequestMapping(value = "/work_search_mng/move/{search_type}/{search_txt}/{page_size}/{page}")    
    // @ResponseBody    
    public ModelAndView work_search_mng_move(CommandMap body, HttpServletRequest request,                                        
                                        @PathVariable("search_type") String search_type,
                                        @PathVariable("search_txt") String search_txt,
                                        @PathVariable("page") int page,
                                        @PathVariable("page_size") int page_size
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
                String LEFT_MENU = "2";
                
                String SEARCH_TYPE = search_type;
                String SEARCH_TXT = search_txt;
                if(SEARCH_TXT.equals(" ")) SEARCH_TXT = "";
                int PAGE_SIZE = page_size;
                int PAGE = page;

                // header.jsp
                JSONObject jsonHeader = common.getJsonHeader(request, TOP_MENU, LEFT_MENU);
                                              
                jsonHeader.put("SEARCH_TYPE",SEARCH_TYPE);                
                jsonHeader.put("SEARCH_TXT",SEARCH_TXT);                
                jsonHeader.put("PAGE_SIZE",PAGE_SIZE);  
                jsonHeader.put("PAGE",PAGE);    
                
                
                
                view.addObject("header",jsonHeader);

                String strSQL = "CALL SP_CMS_SEARCH_WORK_LIST('"+SEARCH_TYPE+"','"+SEARCH_TXT+"','"+PAGE_SIZE+"','"+PAGE+"')";
                JSONArray jsonArray = common.execute_query(strSQL);
                view.addObject("data",jsonArray);

		        view.setViewName("work/work_search_mng");
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