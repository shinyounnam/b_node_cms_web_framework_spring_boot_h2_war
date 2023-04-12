package com.veteranscout.cmsweb.controller.alarm;

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
public class MsgInfoController1 {  

    private String module_name = "msg_info1";
    private CommonUtil common = new CommonUtil();
    
    @RequestMapping(value = "/msg_info1//info/{base_type}/{corp_id}/{msg_id}")    
    // @ResponseBody    
    public ModelAndView info(CommandMap body, HttpServletRequest request,
    @PathVariable("base_type") String base_type,
    @PathVariable("corp_id") int corp_id,
    @PathVariable("msg_id") int msg_id
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
                String TOP_MENU = "4";
                String LEFT_MENU = "1";
                
                String BASE_TYPE = base_type;                
                int CORP_ID = corp_id;                              
                int MSG_ID = msg_id;

                // header.jsp
                JSONObject jsonHeader = common.getJsonHeader(request, TOP_MENU, LEFT_MENU);                
                
                jsonHeader.put("BASE_TYPE",BASE_TYPE); 
                jsonHeader.put("CORP_ID",CORP_ID);                                                
                jsonHeader.put("MSG_ID",MSG_ID);                                                                

                view.addObject("header",jsonHeader);

                String strSQL = "";
                strSQL = "CALL SP_CMS_CORP_RECV_MSG_INFO('"+BASE_TYPE+"','"+CORP_ID+"','"+MSG_ID+"')";
                JSONArray jsonArray = common.execute_query(strSQL);
                view.addObject("data",jsonArray);

		        view.setViewName("alarm/msg_info1");
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