package com.veteranscout.cmsweb.controller.board;

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
public class AdminCmsBoardController {  

    private String module_name = "admin_cms_board";
    private CommonUtil common = new CommonUtil();
    
    @RequestMapping(value = "/admin_cms_board")    
    // @ResponseBody    
    public ModelAndView admin_cms_board(CommandMap body, HttpServletRequest request,HttpServletResponse response) 
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
                String LEFT_MENU = "1";
                
                String AUTH_MESSAGE = "권한이 없습니다.";

                if (AUTH.indexOf(TOP_MENU)==-1)
                {
                    common.ResponseAlsert(response, AUTH_MESSAGE, "/logout");                    
                }
                
                String SEARCH_TYPE = "1";
                String SEARCH_TXT = "";                
                int PAGE = 1;

                // header.jsp
                JSONObject jsonHeader = common.getJsonHeader(request, TOP_MENU, LEFT_MENU);                
                
                jsonHeader.put("SEARCH_TYPE",SEARCH_TYPE);                                
                jsonHeader.put("SEARCH_TXT",SEARCH_TXT);                 
                jsonHeader.put("PAGE",PAGE);    
                
                
                view.addObject("header",jsonHeader);

                String strSQL = "CALL SP_CMS_BOARD_PAGE('"+SEARCH_TYPE+"','"+SEARCH_TXT+"','"+PAGE+"')";
                JSONArray jsonArray = common.execute_query(strSQL);
                view.addObject("data",jsonArray);

		        view.setViewName("board/admin_cms_board");
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

    @RequestMapping(value = "/admin_cms_board/move/{search_type}/{search_txt}/{page}")    
    // @ResponseBody    
    public ModelAndView admin_cms_board_move(CommandMap body, HttpServletRequest request,                                        
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
                String ID = user.get("ID").toString();
                String NAME = user.get("NAME").toString();
                String AUTH = user.get("AUTH").toString();     
                String TOP_MENU = "5";
                String LEFT_MENU = "1";
                
                String SEARCH_TYPE = search_type;
                String SEARCH_TXT = search_txt;           
                if(SEARCH_TXT.equals(" ")) SEARCH_TXT = "";     
                int PAGE = page;

                // header.jsp
                JSONObject jsonHeader = common.getJsonHeader(request, TOP_MENU, LEFT_MENU);
                
                              
                jsonHeader.put("SEARCH_TYPE",SEARCH_TYPE);                
                jsonHeader.put("SEARCH_TXT",SEARCH_TXT);                                
                jsonHeader.put("PAGE",PAGE);    
                
                
                
                view.addObject("header",jsonHeader);

                String strSQL = "CALL SP_CMS_BOARD_PAGE('"+SEARCH_TYPE+"','"+SEARCH_TXT+"','"+PAGE+"')";
                JSONArray jsonArray = common.execute_query(strSQL);
                view.addObject("data",jsonArray);

		        view.setViewName("board/admin_cms_board");
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