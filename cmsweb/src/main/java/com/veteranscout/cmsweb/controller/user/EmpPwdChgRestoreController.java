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
public class EmpPwdChgRestoreController {  

    private String module_name = "emp_pwd_chg_restore";
    private CommonUtil common = new CommonUtil();
    
    @RequestMapping(value = "/emp_pwd_chg_restore/info/{emp_id}")    
    // @ResponseBody    
    public ModelAndView emp_user_detail_restore_info(CommandMap body, HttpServletRequest request,
    @PathVariable("emp_id") int emp_id
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
                
                int EMP_ID = emp_id;                

                // header.jsp
                JSONObject jsonHeader = common.getJsonHeader(request, TOP_MENU, LEFT_MENU);
                

                jsonHeader.put("EMP_ID",EMP_ID);
                
                
                view.addObject("header",jsonHeader);
		        view.setViewName("user/emp_pwd_chg_restore");
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

    @RequestMapping(value = "/emp_pwd_chg_restore/update_pwd", produces = "application/text; charset=utf8")
    @ResponseBody
    public String update_pwd(HttpServletRequest request) {                 
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
                String PWD = body.get("PWD").toString(); 
                PWD = common.get_sha256(PWD);

                String strSQL = "";
                strSQL = "CALL SP_CMS_EMP_RESTORE_USER_PWD_UPDATE('"+EMP_ID+"','"+PWD+"','"+common.client_ip(request)+"')";
                JSONArray jsonArray = common.execute_query_commit(strSQL);
                
		        result = common.get_data_result(jsonArray);
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