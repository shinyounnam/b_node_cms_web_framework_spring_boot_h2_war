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
public class ItemUseViewController {  

    private String module_name = "item_use_view";
    private CommonUtil common = new CommonUtil();
    
    @RequestMapping(value = "/item_use_view/{id_key}")    
    // @ResponseBody    
    public ModelAndView item_use_view(CommandMap body, HttpServletRequest request,
    @PathVariable("id_key") int id_key
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
                
                
                String S_DATE = common.now_date_string();
                String E_DATE = common.now_date_string();                
                String SEARCH_TYPE = "1";
                String SEARCH_TXT = "";                
                int PAGE_SIZE = 15;
                int PAGE = 1;
                int ID_KEY  = id_key;
                String SEARCH_I_CODE = "0";
                String SEARCH_USE_GBN = "0";

                // header.jsp
                JSONObject jsonHeader = common.getJsonHeader(request, TOP_MENU, LEFT_MENU);                

                jsonHeader.put("S_DATE",S_DATE);        
                jsonHeader.put("E_DATE",E_DATE);                           
                jsonHeader.put("SEARCH_TYPE",SEARCH_TYPE);                                
                jsonHeader.put("SEARCH_TXT",SEARCH_TXT);                 
                jsonHeader.put("PAGE_SIZE",PAGE_SIZE);    
                jsonHeader.put("PAGE",PAGE);    
                jsonHeader.put("ID_KEY",ID_KEY);    
                jsonHeader.put("SEARCH_I_CODE",SEARCH_I_CODE);    
                jsonHeader.put("SEARCH_USE_GBN",SEARCH_USE_GBN);    
                
                view.addObject("header",jsonHeader);

                String strSQL = "";
                strSQL = "CALL SP_M_CMS_ITEM_USES_VIEW_PAGE('"+SEARCH_I_CODE+"','"+SEARCH_USE_GBN+"','"+S_DATE+"','"+E_DATE+"','"+ID_KEY+"','"+PAGE_SIZE+"','"+PAGE+"')";
                JSONArray jsonArray = common.execute_query(strSQL);
                view.addObject("data",jsonArray);

		        view.setViewName("item/item_use_view");
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

    @RequestMapping(value = "/item_use_view/move/{search_i_code}/{search_use_gbn}/{s_date}/{e_date}/{id_key}/{page}")    
    // @ResponseBody    
    public ModelAndView item_use_view_move(CommandMap body, HttpServletRequest request,
    @PathVariable("search_i_code") String search_i_code,@PathVariable("search_use_gbn") String search_use_gbn,
    @PathVariable("s_date") String s_date, @PathVariable("e_date") String e_date,
    @PathVariable("id_key") int id_key, @PathVariable("page") int page
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
                String SEARCH_TYPE = "1";
                String SEARCH_TXT = "";                
                int PAGE_SIZE = 15;
                int PAGE = 1;
                int ID_KEY  = id_key;
                String SEARCH_I_CODE = search_i_code;
                String SEARCH_USE_GBN = search_use_gbn;

                // header.jsp
                JSONObject jsonHeader = common.getJsonHeader(request, TOP_MENU, LEFT_MENU);                

                jsonHeader.put("S_DATE",S_DATE);        
                jsonHeader.put("E_DATE",E_DATE);                           
                jsonHeader.put("SEARCH_TYPE",SEARCH_TYPE);                                
                jsonHeader.put("SEARCH_TXT",SEARCH_TXT);                 
                jsonHeader.put("PAGE_SIZE",PAGE_SIZE);    
                jsonHeader.put("PAGE",PAGE);    
                jsonHeader.put("ID_KEY",ID_KEY);    
                jsonHeader.put("SEARCH_I_CODE",SEARCH_I_CODE);    
                jsonHeader.put("SEARCH_USE_GBN",SEARCH_USE_GBN);    
                
                
                view.addObject("header",jsonHeader);

                String strSQL = "";
                strSQL = "CALL SP_M_CMS_ITEM_USES_VIEW_PAGE('"+SEARCH_I_CODE+"','"+SEARCH_USE_GBN+"','"+S_DATE+"','"+E_DATE+"','"+ID_KEY+"','"+PAGE_SIZE+"','"+PAGE+"')";
                JSONArray jsonArray = common.execute_query(strSQL);
                view.addObject("data",jsonArray);

		        view.setViewName("item/item_use_view");
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