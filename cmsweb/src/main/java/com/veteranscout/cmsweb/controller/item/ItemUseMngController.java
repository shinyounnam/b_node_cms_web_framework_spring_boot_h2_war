package com.veteranscout.cmsweb.controller.item;

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
public class ItemUseMngController {  

    private String module_name = "item_use_mng";
    private CommonUtil common = new CommonUtil();
    
    @RequestMapping(value = "/item_use_mng")    
    // @ResponseBody    
    public ModelAndView item_use_mng(CommandMap body, HttpServletRequest request) 
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
                String TOP_MENU = "5";
                String LEFT_MENU = "2";
                
                
                String S_DATE = common.now_date_string();
                String E_DATE = common.now_date_string();                
                String SEARCH_TYPE = "1";
                String SEARCH_TXT = "";                
                int PAGE_SIZE = 15;
                int PAGE = 1;

                // header.jsp
                JSONObject jsonHeader = common.getJsonHeader(request, TOP_MENU, LEFT_MENU);                

                jsonHeader.put("S_DATE",S_DATE);        
                jsonHeader.put("E_DATE",E_DATE);                           
                jsonHeader.put("SEARCH_TYPE",SEARCH_TYPE);                                
                jsonHeader.put("SEARCH_TXT",SEARCH_TXT);                 
                jsonHeader.put("PAGE_SIZE",PAGE_SIZE);    
                jsonHeader.put("PAGE",PAGE);    
                
                
                view.addObject("header",jsonHeader);

                String strSQL = "";
                strSQL = "CALL SP_M_CMS_ITEM_USES_LIST_PAGE('"+SEARCH_TYPE+"','"+SEARCH_TXT+"','"+S_DATE+"','"+E_DATE+"','"+PAGE_SIZE+"','"+PAGE+"')";
                JSONArray jsonArray = common.execute_query(strSQL);
                view.addObject("data",jsonArray);

		        view.setViewName("item/item_use_mng");
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

    @RequestMapping(value = "/item_use_mng/move/{search_type}/{search_txt}/{s_date}/{e_date}/{page}")    
    // @ResponseBody    
    public ModelAndView item_use_mng_move(CommandMap body, HttpServletRequest request,
                                        @PathVariable("search_type") String search_type,
                                        @PathVariable("search_txt") String search_txt,
                                        @PathVariable("s_date") String s_date,
                                        @PathVariable("e_date") String e_date,                                        
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
                String TOP_MENU = "5";
                String LEFT_MENU = "2";
                
                
                String S_DATE = s_date;
                String E_DATE = e_date;                
                String SEARCH_TYPE = search_type;
                String SEARCH_TXT = search_txt;                
                if(SEARCH_TXT.equals(" ")) SEARCH_TXT = "";
                int PAGE_SIZE = 15;
                int PAGE = page;

                // header.jsp
                JSONObject jsonHeader = common.getJsonHeader(request, TOP_MENU, LEFT_MENU);                

                jsonHeader.put("S_DATE",S_DATE);        
                jsonHeader.put("E_DATE",E_DATE);                           
                jsonHeader.put("SEARCH_TYPE",SEARCH_TYPE);                                
                jsonHeader.put("SEARCH_TXT",SEARCH_TXT);                 
                jsonHeader.put("PAGE_SIZE",PAGE_SIZE);    
                jsonHeader.put("PAGE",PAGE);    
                
                
                view.addObject("header",jsonHeader);

                String strSQL = "";
                strSQL = "CALL SP_M_CMS_ITEM_USES_LIST_PAGE('"+SEARCH_TYPE+"','"+SEARCH_TXT+"','"+S_DATE+"','"+E_DATE+"','"+PAGE_SIZE+"','"+PAGE+"')";
                JSONArray jsonArray = common.execute_query(strSQL);
                view.addObject("data",jsonArray);

		        view.setViewName("item/item_use_mng");
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