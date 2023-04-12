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
public class StatUserAddrAreaReportController {  

    private String module_name = "stat_user_addr_area_report";
    private CommonUtil common = new CommonUtil();
    
    @RequestMapping(value = "/stat_user_addr_area_report/info/{areacode1}/{end}")    
    // @ResponseBody    
    public ModelAndView stat_user_addr_area_report(CommandMap body, HttpServletRequest request,
    @PathVariable("areacode1") String areacode1,
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
                String TOP_MENU = "7";
                String LEFT_MENU = "1";
                
                String AREA_CODE_1 = areacode1;
                
                // header.jsp
                JSONObject jsonHeader = common.getJsonHeader(request, TOP_MENU, LEFT_MENU);                

                jsonHeader.put("AREA_CODE_1",AREA_CODE_1);                        
                
                view.addObject("header",jsonHeader);

                String strSQL = "";
                strSQL = "CALL SP_CMS_STAT_ATTEND_EMP_ADDR_LIST_1_1_92('"+AREA_CODE_1+"')";
                JSONArray jsonArray = common.execute_query(strSQL);
                view.addObject("data",jsonArray);

		        view.setViewName("stat/stat_user_addr_area_report");
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