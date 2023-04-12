package com.veteranscout.cmsweb.controller.work;

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
public class ScheduleAddController {  

    private String module_name = "schedule_add";
    private CommonUtil common = new CommonUtil();
    
    @RequestMapping(value = "/schedule_add/info")    
    // @ResponseBody    
    public ModelAndView schedule_add_info(CommandMap body, HttpServletRequest request) 
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
                String TOP_MENU = "3";
                String LEFT_MENU = "2";
                
                String CORP_ID = "";

                // header.jsp
                JSONObject jsonHeader = common.getJsonHeader(request, TOP_MENU, LEFT_MENU);
                                
                jsonHeader.put("CORP_ID",CORP_ID);    
                
                
                view.addObject("header",jsonHeader);

                String strSQL = "";
                strSQL = "CALL SP_CORP_SCHEDULE_ADD_CODE_LIST()";

                JSONArray jsonArray = common.execute_query(strSQL);
                view.addObject("data",jsonArray);

		        view.setViewName("work/schedule_add");
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

    @RequestMapping(value = "/schedule_add/insert_multi", produces = "application/text; charset=utf8")   
    @ResponseBody    
    public String insert_multi(HttpServletRequest request) 
    {
        JSONObject user = null;
        String result = "";
        try {
            user = common.getAttribute(request);
            if(user!=null){

                String ADMIN_ID = user.get("ADMIN_ID").toString();
                String IN_ID = user.get("ID").toString();
                String IN_NAME = user.get("NAME").toString();
                String AUTH = user.get("AUTH").toString();
                String TOP_MENU = "2";
                String LEFT_MENU = "3";
                String WORK_ID = "0";
                String WORK_NEED_ID = "0";

                String jsonString = common.get_body(request);
                JSONObject body = new JSONObject(jsonString);

                String CORP_ID = body.get("CORP_ID").toString();
                String CORP_NAME = body.get("CORP_NAME").toString();
                String WORK_DATE = body.get("WORK_DATE").toString();
                String TO_WORK_DATE = body.get("TO_WORK_DATE").toString();
                String NAME = body.get("NAME").toString();
                String POST_CODE = body.get("POST_CODE").toString();
                String ADDR1 = body.get("ADDR1").toString();
                String ADDR2 = body.get("ADDR2").toString();
                String MAN_NAME = body.get("MAN_NAME").toString();
                String MAN_PSTN = body.get("MAN_PSTN").toString();
                String MAN_TEL = body.get("MAN_TEL").toString();
                String WORK_TEL = body.get("WORK_TEL").toString();
                String JOB_CONTENT = body.get("JOB_CONTENT").toString();
                String CHAMGO = body.get("CHAMGO").toString();
                String POS_LAT = body.get("POS_LAT").toString();
                String POS_LNG = body.get("POS_LNG").toString();
                String DATA = body.get("DATA").toString();
                JSONArray jsonData2 = new JSONArray(DATA);

                JOB_CONTENT = JOB_CONTENT.replace("'","`");
                JOB_CONTENT = JOB_CONTENT.replace("'","`");
                
                String strSQL = "";
                strSQL = "CALL SP_CMS_WORK_SCHEDULE_INFO_INSERT_1_1_92('"+WORK_ID+"','"+CORP_ID+"','"+WORK_DATE+"','"+TO_WORK_DATE+"','"+NAME+"','"+POST_CODE+"','"+ADDR1+"','"+ADDR2+"','"+CHAMGO+"',"+
					 "'"+MAN_NAME+"','"+MAN_PSTN+"','"+MAN_TEL+"','"+WORK_TEL+"'," +
                     "'"+JOB_CONTENT+"','"+POS_LAT+"','"+POS_LNG+"')";
                     
                JSONArray jsonArray = common.execute_query_commit(strSQL);
                WORK_ID = common.get_json_array_value(jsonArray,"WORK_ID");

                int i = 0;
                JSONObject obj = null;
                String strSQL2 = "INSERT INTO CMS_WORK_NEED " +
                        "(WORK_ID, SEQ, JOB_GBN, JOB_TYPE, WORK_PAY, MAN_CNT, BOOK_CNT, SHOW_CNT, REG_DATE, UPD_DATE)"+
                        "VALUES ";

                for (i=0;i<jsonData2.length();i++)
                {
                    obj = jsonData2.getJSONObject(i);
                    if (i==0)
                    {
                        strSQL2 = strSQL2 + "('"+WORK_ID+"','"+(i+1)+"','"+obj.getString("JOB_GUBUN")+"',"+
                                    "'"+obj.getString("JOB_CODE")+"','"+obj.getString("WORK_PAY")+"','"+obj.getString("WORK_CNT")+"',"+
                                    "'0','0',sysdate(),sysdate())";
                    }
                    else
                    {
                        strSQL2 = strSQL2 + ",('"+WORK_ID+"','"+(i+1)+"','"+obj.getString("JOB_GUBUN")+"',"+
                                    "'"+obj.getString("JOB_CODE")+"','"+obj.getString("WORK_PAY")+"','"+obj.getString("WORK_CNT")+"',"+
                                    "'0','0',sysdate(),sysdate())";
                    }
                }

                result = common.execute_update_commit(strSQL2);

                strSQL2 = "CALL SP_CMS_WORK_NEED_JOB_UPDATE('"+WORK_ID+"')";
                jsonArray = common.execute_query_commit(strSQL);

                result = common.get_message("ok","");
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