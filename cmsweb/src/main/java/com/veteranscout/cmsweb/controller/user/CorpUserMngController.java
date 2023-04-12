package com.veteranscout.cmsweb.controller.user;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.veteranscout.cmsweb.util.*;
import org.json.*;

@Controller
public class CorpUserMngController {  

    private String module_name = "corp_user_mng";
    private CommonUtil common = new CommonUtil();
    
    @RequestMapping(value = "/corp_user_mng/info/{corp_id}")    
    // @ResponseBody    
    public ModelAndView info(CommandMap body, HttpServletRequest request,
    @PathVariable("corp_id") String corp_id
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
                String LEFT_MENU = "3";
                String CORP_ID =  corp_id;

                // header.jsp
                JSONObject jsonHeader = common.getJsonHeader(request, TOP_MENU, LEFT_MENU);
                
                jsonHeader.put("CORP_ID",CORP_ID);

                view.addObject("header",jsonHeader);

                String strSQL = "";
                strSQL = "CALL SP_CMS_CORP_USER_INFO('"+CORP_ID+"')";

                JSONArray jsonArray = common.execute_query(strSQL);
                view.addObject("data",jsonArray);                

		        view.setViewName("user/corp_user_mng");
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

    @RequestMapping(value = "/corp_user_mng/update_multi")    
    @ResponseBody    
    public String update_multi(HttpServletRequest request    
    ) 
    {        
        String result = "";
        JSONObject user = null;
        try {
            user = common.getAttribute(request);
            if(user!=null){

                String ADMIN_ID = user.get("ADMIN_ID").toString();
                String IN_ID = user.get("ID").toString();
                String NAME = user.get("NAME").toString();
                String AUTH = user.get("AUTH").toString();
                String TOP_MENU = "6";
                String LEFT_MENU = "1";

                String json_string = common.get_body(request);
                JSONObject body = new JSONObject(json_string);

                String CORP_ID = body.get("CORP_ID").toString();
                String CORP_NAME = body.get("CORP_NAME").toString();
                String BIZ_NO = body.get("BIZ_NO").toString();
                String REP_NAME = body.get("REP_NAME").toString();
                String REP_TEL = body.get("REP_TEL").toString();
                String ADDR1 = body.get("ADDR1").toString();
                String ADDR2 = body.get("ADDR2").toString();
                String CHAMGO = body.get("CHAMGO").toString();
                String POST_CODE = body.get("POST_CODE").toString();
                String TEL = body.get("TEL").toString();
                String HOME_URL = body.get("HOME_URL").toString();
                String EMAIL = body.get("EMAIL").toString();
                String INTRO_TXT = body.get("INTRO_TXT").toString();
                String POS_LAT = body.get("POS_LAT").toString();
                String POS_LNG = body.get("POS_LNG").toString();
                String MAN_NAME = body.get("MAN_NAME").toString();
                String MAN_PSTN = "";
                String MAN_TEL = body.get("MAN_TEL").toString();
                String VIP_NO = body.get("VIP_NO").toString();
                String CORP_GBN = body.get("CORP_GBN").toString();
                String DATA = body.get("DATA").toString();
                JSONArray jsonData2 = null;
                if(DATA.equals("[]")==false){
                    jsonData2 = new JSONArray(DATA);
                }
                // CONTENT = CONTENT.replace(/"/gi,'&quot;');
                String strSQL = "";

                strSQL = "CALL SP_CMS_CORP_USER_UPDATE_WEB_1_1_92('"+CORP_ID+"','"+CORP_NAME+"','"+BIZ_NO+"','"+REP_NAME+"', '"+REP_TEL+"',"+
					 "'"+ADDR1+"', '"+ADDR2+"', '"+POST_CODE+"', '"+CHAMGO+"','"+TEL+"', " +
					 "'"+HOME_URL+"', '"+EMAIL+"','"+INTRO_TXT+"', " +
					 "'"+POS_LAT+"','"+POS_LNG+"','"+MAN_NAME+"','"+MAN_PSTN+"','"+MAN_TEL+"','"+VIP_NO+"','"+CORP_GBN+"')";

                JSONArray jsonArray = common.execute_query_commit(strSQL);

                if(DATA.equals("[]")==true){
                    result = common.get_message("ok","");
                }
                else{

                    String strSQL2 = "INSERT INTO CORP_JOB_TYPE " +
                        "(JOB_ID, JOB_GUBUN, JOB_CODE, CORP_ID, REG_DATE, UPD_DATE)"+
                        "SELECT T.JOB_ID, T.JOB_GUBUN, T.JOB_CODE, T.CORP_ID, T.REG_DATE, T.UPD_DATE FROM (";
                    String jobID = "";
                    int i = 0;
                    JSONObject obj = null;
                    for (i=0;i<jsonData2.length();i++)
                    {
                        obj = jsonData2.getJSONObject(i);
                        if (obj.getString("JOB_ID").equals(""))
                        {
                            jobID = "null";
                        }
                        else
                        {
                            jobID = obj.getString("JOB_ID");
                        }
                        if (i==0)
                        {
                            
                            strSQL2 = strSQL2 + "SELECT "+jobID+" as JOB_ID," +
                                        "'"+obj.getString("JOB_GUBUN")+"' as JOB_GUBUN,"+
                                        "'"+obj.getString("JOB_CODE")+"' as JOB_CODE,"+
                                        "'"+CORP_ID+"' as CORP_ID, " +
                                        "sysdate() as REG_DATE, " +
                                        "sysdate() as UPD_DATE ";
                        }
                        else
                        {
                            strSQL2 = strSQL2 + " UNION ALL "+
                                        "SELECT "+jobID+" as JOB_ID,"+
                                        "'"+obj.getString("JOB_GUBUN")+"' as JOB_GUBUN,"+
                                        "'"+obj.getString("JOB_CODE")+"' as JOB_CODE,"+
                                        "'"+CORP_ID+"' as CORP_ID,"+
                                        "sysdate() as REG_DATE," +
                                        "sysdate() as UPD_DATE ";
                        }
                    }
                    strSQL2 = strSQL2 + ") T " +
                                "ON DUPLICATE KEY UPDATE " +
                                "JOB_GUBUN = T.JOB_GUBUN, " +
                                "JOB_CODE = T.JOB_CODE, "+
                                "UPD_DATE = T.UPD_DATE ";

                    jsonArray = common.execute_query_commit(strSQL2);

                    result = common.get_message("ok","");
                }
            }
            else
            {
                result = common.get_error_message("ssession error");

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