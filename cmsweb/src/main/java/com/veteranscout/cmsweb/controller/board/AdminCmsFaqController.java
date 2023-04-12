package com.veteranscout.cmsweb.controller.board;

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
public class AdminCmsFaqController {  

    private String module_name = "admin_cms_faq";
    private CommonUtil common = new CommonUtil();
    
    @RequestMapping(value = "/admin_cms_faq")    
    // @ResponseBody    
    public ModelAndView admin_cms_faq(CommandMap body, HttpServletRequest request) 
    {
        ModelAndView view = new ModelAndView();
        JSONObject user = null;
        try {
            user = common.getAttribute(request);
            if(user!=null){

                String ADMIN_ID = user.get("ADMIN_ID").toString();
                String ID = user.get("ID").toString();
                String NAME = user.get("NAME").toString();
                String AUTH = user.get("AUTH").toString();
                String TOP_MENU = "6";
                String LEFT_MENU = "2";                
                String SEARCH_TYPE = "1";
                String SEARCH_TXT = "";                
                int PAGE = 1;
                String GBN = "ALL";

                // header.jsp
                JSONObject jsonHeader = common.getJsonHeader(request, TOP_MENU, LEFT_MENU);                
                
                jsonHeader.put("SEARCH_TYPE",SEARCH_TYPE);                                
                jsonHeader.put("SEARCH_TXT",SEARCH_TXT);                 
                jsonHeader.put("PAGE",PAGE);    
                jsonHeader.put("GBN",GBN);    
                
                String strSQL = "";
                strSQL = "CALL SP_CMS_FAQ_PAGE('"+SEARCH_TYPE+"','"+SEARCH_TXT+"','"+PAGE+"','"+GBN+"')";
                JSONArray jsonArray = common.execute_query(strSQL);

                view.addObject("header",jsonHeader);
                view.addObject("data",jsonArray);

		        view.setViewName("board/admin_cms_faq");
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

    @RequestMapping(value = "/admin_cms_faq/move/{search_type}/{search_txt}/{page}/{gbn}")    
    // @ResponseBody    
    public ModelAndView admin_cms_faq_move(CommandMap body, HttpServletRequest request,
                                        @PathVariable("search_type") String search_type,
                                        @PathVariable("search_txt") String search_txt,
                                        @PathVariable("page") int page,
                                        @PathVariable("gbn") String gbn
    ) 
    {
        ModelAndView view = new ModelAndView();
        JSONObject user = null;
        try {
            user = common.getAttribute(request);
            if(user!=null){

                String ADMIN_ID = user.get("ADMIN_ID").toString();
                String ID = user.get("ID").toString();
                String NAME = user.get("NAME").toString();
                String AUTH = user.get("AUTH").toString();
                String TOP_MENU = "6";
                String LEFT_MENU = "2";                
                String SEARCH_TYPE = search_type;
                String SEARCH_TXT = search_txt;    
                if(SEARCH_TXT.equals(" ")) SEARCH_TXT = "";
                int PAGE = page;
                String GBN = gbn;

                // header.jsp
                JSONObject jsonHeader = common.getJsonHeader(request, TOP_MENU, LEFT_MENU);
                
                
                jsonHeader.put("SEARCH_TYPE",SEARCH_TYPE);                                
                jsonHeader.put("SEARCH_TXT",SEARCH_TXT);                 
                jsonHeader.put("PAGE",PAGE);    
                jsonHeader.put("GBN",GBN);    
                
                String strSQL = "";
                strSQL = "CALL SP_CMS_FAQ_PAGE('"+SEARCH_TYPE+"','"+SEARCH_TXT+"','"+PAGE+"','"+GBN+"')";
                JSONArray jsonArray = common.execute_query(strSQL);

                view.addObject("header",jsonHeader);
                view.addObject("data",jsonArray);

		        view.setViewName("board/admin_cms_faq");
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