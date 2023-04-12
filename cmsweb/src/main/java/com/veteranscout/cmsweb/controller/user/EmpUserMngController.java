package com.veteranscout.cmsweb.controller.user;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.veteranscout.cmsweb.util.*;
import org.json.*;

@Controller
public class EmpUserMngController {  

    private String module_name = "emp_user_mng";
    private CommonUtil common = new CommonUtil();
    
    @RequestMapping(value = "/emp_user_mng")    
    // @ResponseBody    
    public ModelAndView emp_user_mng(CommandMap body, HttpServletRequest request) 
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
                String LEFT_MENU = "2";
                
                
                String SORT_TYPE = "2";
                String SEARCH_TYPE = "1";
                String SEARCH_TXT = "";
                int PAGE = 1;
                String GBN = "ALL";

                // header.jsp
                JSONObject jsonHeader = common.getJsonHeader(request, TOP_MENU, LEFT_MENU);                

                jsonHeader.put("SORT_TYPE",SORT_TYPE);                
                jsonHeader.put("SEARCH_TYPE",SEARCH_TYPE);                
                jsonHeader.put("SEARCH_TXT",SEARCH_TXT);                
                jsonHeader.put("PAGE",PAGE);    
                jsonHeader.put("GBN",GBN);    
                
                
                view.addObject("header",jsonHeader);

                String strSQL = "";
                strSQL = "CALL SP_CMS_CORP_POOL_EMP_LIST_1_1_92('"+GBN+"','"+SORT_TYPE+"','"+SEARCH_TYPE+"','"+SEARCH_TXT+"','"+PAGE+"')";
                JSONArray jsonArray = common.execute_query(strSQL);
                view.addObject("data",jsonArray);

		        view.setViewName("user/emp_user_mng");
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

    @RequestMapping(value = "/emp_user_mng/move/{sort_type}/{search_type}/{search_txt}/{page}/{gbn}/{end}")    
    // @ResponseBody    
    public ModelAndView emp_user_mng_move(CommandMap body, HttpServletRequest request,
                                        @PathVariable("sort_type") String sort_type,
                                        @PathVariable("search_type") String search_type,
                                        @PathVariable("search_txt") String search_txt,
                                        @PathVariable("page") int page,
                                        @PathVariable("gbn") String gbn,
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
                String TOP_MENU = "2";
                String LEFT_MENU = "2";             

                String SORT_TYPE = sort_type;
                String SEARCH_TYPE = search_type;
                String SEARCH_TXT = search_txt;
                if(SEARCH_TXT.equals(" ")) SEARCH_TXT = "";
                int PAGE = page;
                String GBN = gbn;

                // header.jsp
                JSONObject jsonHeader = common.getJsonHeader(request, TOP_MENU, LEFT_MENU);                

                jsonHeader.put("SORT_TYPE",SORT_TYPE);                
                jsonHeader.put("SEARCH_TYPE",SEARCH_TYPE);                                
                jsonHeader.put("SEARCH_TXT",SEARCH_TXT);    
                            
                jsonHeader.put("PAGE",PAGE);    
                jsonHeader.put("GBN",GBN);    
                
                
                view.addObject("header",jsonHeader);

                String strSQL = "";
                strSQL = "CALL SP_CMS_CORP_POOL_EMP_LIST_1_1_92('"+GBN+"','"+SORT_TYPE+"','"+SEARCH_TYPE+"','"+SEARCH_TXT+"','"+PAGE+"')";
                JSONArray jsonArray = common.execute_query(strSQL);
                view.addObject("data",jsonArray);

		        view.setViewName("user/emp_user_mng");
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
	
	
    @RequestMapping(value = "/emp_user_mng/delete_corp_pool", produces = "application/text; charset=utf8")
    @ResponseBody
    public String delete_corp_pool(HttpServletRequest request) {                 
        String result = "";            
        JSONObject user = null;
        try {
            user = common.getAttribute(request);
            if(user!=null){

                String ADMIN_ID = user.get("ADMIN_ID").toString();
                String IN_ID = user.get("ID").toString();
                String NAME = user.get("NAME").toString();
                String AUTH = user.get("AUTH").toString();
                String TOP_MENU = "1";
                String LEFT_MENU = "1";

                String json_string = common.get_body(request);
                JSONObject body = new JSONObject(json_string);
                
                String EMP_ID = body.get("EMP_ID").toString();                

                String strSQL = "";
                strSQL = "CALL SP_M_EMP_USER_DELETE_CORP_POOL_WEB('"+EMP_ID+"')";
                JSONArray jsonArray = common.execute_query_commit(strSQL);
                
		        result = common.get_data_ok(jsonArray);
            }
            else
            {
                result = common.get_error_message("session error");
		    }
        } 
        catch (JSONException ex){
            ex.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        return result;
    }

}