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
public class AdminCmsEventSBoardController {  

    private String module_name = "admin_cms_event_s_board";
    private CommonUtil common = new CommonUtil();
    
    @RequestMapping(value = "/admin_cms_event_s_board")    
    // @ResponseBody    
    public ModelAndView admin_cms_event_s_board(CommandMap body, HttpServletRequest request) 
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
                String TOP_MENU = "6";
                String LEFT_MENU = "5";                
                String SEARCH_TYPE = "1";
                String SEARCH_TXT = "";                
                int PAGE = 1;                

                // header.jsp
                JSONObject jsonHeader = common.getJsonHeader(request, TOP_MENU, LEFT_MENU);                
                
                jsonHeader.put("SEARCH_TYPE",SEARCH_TYPE);                                
                jsonHeader.put("SEARCH_TXT",SEARCH_TXT);                 
                jsonHeader.put("PAGE",PAGE);    
                

                String strSQL = "";
                strSQL = "CALL SP_M_CMS_EVENT_S_BOARD_SELECT('"+SEARCH_TYPE+"','"+SEARCH_TXT+"','"+PAGE+"')";
                JSONArray jsonArray = common.execute_query(strSQL);

                view.addObject("header",jsonHeader);
                view.addObject("data",jsonArray);

		        view.setViewName("board/admin_cms_event_s_board");
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

    @RequestMapping(value = "/admin_cms_event_s_board/move/{search_type}/{search_txt}/{page}")    
    // @ResponseBody    
    public ModelAndView admin_cms_event_s_board_move(CommandMap body, HttpServletRequest request,
                                        @PathVariable("search_type") String search_type,
                                        @PathVariable("search_txt") String search_txt,
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
                String TOP_MENU = "6";
                String LEFT_MENU = "5";                
                String SEARCH_TYPE = search_type;
                String SEARCH_TXT = search_txt;                
                if(SEARCH_TXT.equals(" ")) SEARCH_TXT = "";
                int PAGE = page;                

                // header.jsp
                JSONObject jsonHeader = common.getJsonHeader(request, TOP_MENU, LEFT_MENU);                
                
                jsonHeader.put("SEARCH_TYPE",SEARCH_TYPE);                                
                jsonHeader.put("SEARCH_TXT",SEARCH_TXT);                 
                jsonHeader.put("PAGE",PAGE);    
                

                String strSQL = "";
                strSQL = "CALL SP_M_CMS_EVENT_S_BOARD_SELECT('"+SEARCH_TYPE+"','"+SEARCH_TXT+"','"+PAGE+"')";
                JSONArray jsonArray = common.execute_query(strSQL);

                view.addObject("header",jsonHeader);
                view.addObject("data",jsonArray);

		        view.setViewName("board/admin_cms_event_s_board");
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