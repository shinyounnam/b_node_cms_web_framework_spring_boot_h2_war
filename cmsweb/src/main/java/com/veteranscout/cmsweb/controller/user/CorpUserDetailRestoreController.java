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
public class CorpUserDetailRestoreController {  

    private String module_name = "corp_user_detail_restore";
    private CommonUtil common = new CommonUtil();
    
    @RequestMapping(value = "/corp_user_detail_restore/info/{corp_id}")    
    // @ResponseBody    
    public ModelAndView corp_user_detail_restore_info(CommandMap body, HttpServletRequest request,
    @PathVariable("corp_id") int corp_id
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
                String LEFT_MENU = "1";
                
                int CORP_ID = corp_id;
                String CONT_YY = common.now_yyyy();
                String CONT_MM = common.now_mm();
                String CONT_DD = common.now_dd();

                // header.jsp
                JSONObject jsonHeader = common.getJsonHeader(request, TOP_MENU, LEFT_MENU);
                

                jsonHeader.put("CORP_ID",CORP_ID);
                jsonHeader.put("CONT_YY",CONT_YY);
                jsonHeader.put("CONT_MM",CONT_MM);
                jsonHeader.put("CONT_DD",CONT_DD);
                
                view.addObject("header",jsonHeader);

                String strSQL = "";
                strSQL = "CALL SP_CMS_CORP_USER_RESTORE_LEVEL_INFO('"+CORP_ID+"')";
                JSONArray jsonArray = common.execute_query(strSQL);
                view.addObject("data",jsonArray);

		        view.setViewName("user/corp_user_detail_restore");
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