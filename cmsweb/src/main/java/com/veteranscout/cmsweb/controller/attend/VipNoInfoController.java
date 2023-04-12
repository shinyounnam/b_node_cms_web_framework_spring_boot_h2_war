package com.veteranscout.cmsweb.controller.attend;

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
public class VipNoInfoController {  

    private String module_name = "vip_no_info";
    private CommonUtil common = new CommonUtil();
    
    @RequestMapping(value = "/vip_no_info/info/{vip_id}")    
    // @ResponseBody    
    public ModelAndView vip_no_info_info(CommandMap body, HttpServletRequest request,
    @PathVariable("vip_id") int vip_id
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
                String TOP_MENU = "1";
                String LEFT_MENU = "4";
                
                int VIP_ID = vip_id;

                // header.jsp
                JSONObject jsonHeader = common.getJsonHeader(request, TOP_MENU, LEFT_MENU);                

                jsonHeader.put("VIP_ID",VIP_ID);    
                                
                view.addObject("header",jsonHeader);

                String strSQL = "";
                strSQL = "CALL SP_M_VIP_NO_INFO_SELECT_DETAIL('"+VIP_ID+"')";;

                JSONArray jsonArray = common.execute_query(strSQL);
                view.addObject("data",jsonArray);

		        view.setViewName("attend/vip_no_info");
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