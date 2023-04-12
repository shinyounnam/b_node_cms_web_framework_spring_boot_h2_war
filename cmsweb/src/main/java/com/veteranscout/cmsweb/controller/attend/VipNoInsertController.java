package com.veteranscout.cmsweb.controller.attend;

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
public class VipNoInsertController {  

    private String module_name = "vip_no_insert";
    private CommonUtil common = new CommonUtil();
    
    @RequestMapping(value = "/vip_no_insert")    
    // @ResponseBody    
    public ModelAndView vip_no_insert(CommandMap body, HttpServletRequest request) 
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
                String TOP_MENU = "1";
                String LEFT_MENU = "4";
                
                String SEARCH_TYPE = "1";
                String SEARCH_TXT = "";
                int PAGE = 1;

                
			    String S_DATE = common.now_date_string();
			    String E_DATE = common.now_date_string();

                // header.jsp
                JSONObject jsonHeader = common.getJsonHeader(request, TOP_MENU, LEFT_MENU);                
                
                jsonHeader.put("SEARCH_TYPE",SEARCH_TYPE);                
                jsonHeader.put("SEARCH_TXT",SEARCH_TXT);                
                jsonHeader.put("PAGE",PAGE);    
                jsonHeader.put("S_DATE",S_DATE);    
                jsonHeader.put("E_DATE",E_DATE);                    
                
                view.addObject("header",jsonHeader);

		        view.setViewName("attend/vip_no_insert");
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

    @RequestMapping(value = "/vip_no_insert/insert", produces = "application/text; charset=utf8")    
    @ResponseBody    
    public String insert(HttpServletRequest request) 
    {        
        JSONObject user = null;
        String result = "";
        try {
            user = common.getAttribute(request);
            if(user!=null){		        
                String ADMIN_ID = user.get("ADMIN_ID").toString();
                String IN_ID = user.get("ID").toString();
                String NAME = user.get("NAME").toString();
                String AUTH = user.get("AUTH").toString();
                String TOP_MENU = "1";
                String LEFT_MENU = "4";

                String jsonString = common.get_body(request);
                JSONObject body = new JSONObject(jsonString);
                
                String VIP_STATUS = body.get("VIP_STATUS").toString();
                String S_DATE = body.get("S_DATE").toString();
                String E_DATE = body.get("E_DATE").toString();
                String VIP_USE = body.get("VIP_USE").toString();
                String VIP_USE_CNT = body.get("VIP_USE_CNT").toString();
                String VIP_TYPE = body.get("VIP_TYPE").toString();
                String VIP_NO = body.get("VIP_NO").toString();
                String CONTENT = body.get("CONTENT").toString();
                
                String strSQL = "";
                strSQL = "CALL SP_M_VIP_NO_INFO_INSERT('"+NAME+"', '"+VIP_STATUS+"','"+S_DATE+"','"+E_DATE+"',"+
					         "'"+VIP_USE+"','"+VIP_USE_CNT+"','"+VIP_TYPE+"','"+VIP_NO+"','"+CONTENT+"')";
                                 
                JSONArray jsonArray = common.execute_query_commit(strSQL);                
                result = common.get_data_ok(jsonArray);
            }
            else
            {
                 result = common.get_error_message("session error");

		    }
        } 
        catch (Exception e) {
            e.printStackTrace();
        }     
        return result;
    }

}