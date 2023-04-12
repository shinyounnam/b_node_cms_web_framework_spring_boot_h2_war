package com.veteranscout.cmsweb.controller.alert;

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
public class AlertMsgUrlController {  

    private String module_name = "alert_msg_url";
    private CommonUtil common = new CommonUtil();
    
    @RequestMapping(value = "/alert_msg_url/alert/{message}/{url}")    
    // @ResponseBody    
    public ModelAndView alert_msg_url(CommandMap body, HttpServletRequest request,
            @PathVariable("message") String message,
            @PathVariable("url") String url
            ) 
    {
        ModelAndView view = new ModelAndView();        
        JSONObject user = null;
        try {
            user = common.getAttribute(request);
            if(user!=null){

                /*
                map.put("ADMIN_ID",common.get_json_array_position_value_int(jsonData,1,"ADMIN_ID"));
                map.put("ID",common.get_json_array_position_value(jsonData,1,"ID"));
                map.put("NAME",common.get_json_array_position_value(jsonData,1,"NAME"));
                map.put("AUTH",common.get_json_array_position_value(jsonData,1,"AUTH"));
                */
                String ADMIN_ID = user.get("ADMIN_ID").toString();
                String IN_ID = user.get("ID").toString();
                String NAME = user.get("NAME").toString();
                String AUTH = user.get("AUTH").toString();
                String TOP_MENU = "";
                String LEFT_MENU = "";
                String MESSAGE = message;
                String URL = url.replace(" ","");                

                /*
                MESSAGE = decodeURIComponent(MESSAGE);
                var result = '{"result":"ok","message":"'+MESSAGE+'",';
                result += '"msg_type":"error",'
                result += '"id":"'+id+'",'
                result += '"MSG_LIST":"'+MSG_LIST+'",'
                result += '"url":"'+URL+'"}';
                */

                // header.jsp
                JSONObject jsonHeader = common.getJsonHeader(request, TOP_MENU, LEFT_MENU);                

                jsonHeader.put("result","ok");
                jsonHeader.put("message",MESSAGE);                
                jsonHeader.put("url",URL);
                
                view.addObject("header",jsonHeader);
                
		        view.setViewName("alert/alert_msg_url");
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