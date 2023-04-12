package com.veteranscout.cmsweb.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URLConnection;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import javax.activation.FileTypeMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.veteranscout.cmsweb.util.*;
import org.json.*;

@Controller
public class LoginController {  

    private String module_name = "login";
    private CommonUtil common = new CommonUtil();
    
    @RequestMapping(value = "/")    
    // @ResponseBody    
    public ModelAndView login_view(CommandMap body, HttpServletRequest request, HttpServletResponse response) {
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
                String LEFT_MENU = "1";
                String AUTH_MESSAGE = "권한이 없습니다.";

                if(AUTH.equals("")){
                    common.ResponseAlsert(response, "권한이 없습니다.", "/logout");
                }
                else if(AUTH.indexOf("1")!=-1){
                    common.ResponseUrl(response, "/attend_req");
                }
                else if(AUTH.indexOf("2")!=-1){
                    common.ResponseUrl(response, "/user_mng");
                }
                else if(AUTH.indexOf("3")!=-1){
                    common.ResponseUrl(response, "/work_mng");
                }
                else if(AUTH.indexOf("4")!=-1){
                    common.ResponseUrl(response, "/alarm_mng");
                }
                else if(AUTH.indexOf("6")!=-1){
                    common.ResponseUrl(response, "/admin_cms_board");
                }
                else if(AUTH.indexOf("5")!=-1){
                    common.ResponseUrl(response, "/item_sale_mng");
                }
                else if(AUTH.indexOf("7")!=-1){
                    common.ResponseUrl(response, "/statistics");
                }
            }
            else{

                // header.jsp
                String TOP_MENU = "1";
                String LEFT_MENU = "1";
				JSONObject jsonHeader = common.getJsonHeader(request, TOP_MENU, LEFT_MENU);
                
                view.addObject("header",jsonHeader);

                view.setViewName("/login");                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return view;
    }

    /*
    if(commandMap.isEmpty() == false){
        Iterator<Entry<String,Object>> iterator = commandMap.getMap().entrySet().iterator();
        Entry<String,Object> entry = null;
        while(iterator.hasNext()){
            entry = iterator.next();
            System.out.println("key : "+entry.getKey()+", value : "+entry.getValue());
        }
    }
    */

    @RequestMapping(value = "/login")        
    public void login(CommandMap body, HttpServletRequest request, HttpServletResponse response) {
        
        String result = "";
        HttpSession session = null;
        JSONObject user = null;
        try {
            String ID = body.get("ID").toString();
            String PWD = body.get("PWD").toString();
            PWD = common.get_sha256(PWD);
            String REMOTE_IP = common.client_ip(request);
            String strSQL = "";
            
            strSQL = "CALL SP_CMS_ADMIN_USER_LOGIN('" + ID + "','" + PWD + "','"+REMOTE_IP+"') ";

            JSONArray jsonData = common.execute_query_commit(strSQL);

            response.setContentType("text/html;charset=UTF-8");
            PrintWriter print_result = response.getWriter();

            if (common.get_json_array_value(jsonData,"result").equals("login_access_deny_ip"))
            {                
                String alert_msg = "로그인할 수 있는 아이피가 아닙니다.";
                common.ResponseAlsert(response, alert_msg, "/logout");
            }
            else if (common.get_json_array_value(jsonData,"result").equals("login_fail"))
            {                
                String alert_msg = "비밀번호가일치하지 않습니다.연속 3회이상 불일치시 접근이 제한됩니다."+
										"(비밀번호불일치: "+common.get_json_array_value(jsonData,"LOGIN_FAIL_COUNT")+"회)";
                common.ResponseAlsert(response, alert_msg, "/logout");
            }
            else if (common.get_json_array_value(jsonData,"result").equals("login_access_deny"))
            {
                
                String alert_msg = "접근제한되었습니다.";
                common.ResponseAlsert(response, alert_msg, "/logout");
            }
            else if (common.get_json_array_value(jsonData,"result").equals("login_ok"))
            {                
                JSONObject map = new JSONObject();
                map.put("ADMIN_ID",common.get_json_array_position_value(jsonData,1,"ADMIN_ID"));
                map.put("ID",common.get_json_array_position_value(jsonData,1,"ID"));
                map.put("NAME",common.get_json_array_position_value(jsonData,1,"NAME"));
                map.put("AUTH",common.get_json_array_position_value(jsonData,1,"AUTH"));                
                map.put("authorized","true");
                common.setAttribute(map, response);                
                /*
                1 : <li class="<%=strTopClass1%>"><a href="/attend_req">가입 신청</a></li>
				2 : <li class="<%=strTopClass2%>"><a href="/user_mng">회원 관리</a></li>
				3 : <li class="<%=strTopClass3%>"><a href="/work_mng">현장 관리</a></li>
				4 : <li class="<%=strTopClass4%>"><a href="/alarm_mng">알림 관리</a></li>	
				6 : <li class="<%=strTopClass6%>"><a href="/admin_cms_board">게시판 관리</a></li>
				5 : <li class="<%=strTopClass5%>"><a href="/item_sale_mng">아이템 관리</a></li>
				7 : <li class="<%=strTopClass7%>"><a href="/statistics">통계</a></li>
                */
                String AUTH = common.get_json_array_position_value(jsonData,1,"AUTH");
                if(AUTH.equals("")){
                    common.ResponseAlsert(response, "권한이 없습니다.", "/logout");
                }
                else if(AUTH.indexOf("1")!=-1){
                    common.ResponseUrl(response, "/attend_req");
                }
                else if(AUTH.indexOf("2")!=-1){
                    common.ResponseUrl(response, "/user_mng");
                }
                else if(AUTH.indexOf("3")!=-1){
                    common.ResponseUrl(response, "/work_mng");
                }
                else if(AUTH.indexOf("4")!=-1){
                    common.ResponseUrl(response, "/alarm_mng");
                }
                else if(AUTH.indexOf("6")!=-1){
                    common.ResponseUrl(response, "/admin_cms_board");
                }
                else if(AUTH.indexOf("5")!=-1){
                    common.ResponseUrl(response, "/item_sale_mng");
                }
                else if(AUTH.indexOf("7")!=-1){
                    common.ResponseUrl(response, "/statistics");
                }
            }
            else if (common.get_json_array_value(jsonData,"result").equals("login_ok_change_pwd"))
            {                
                JSONObject map = new JSONObject();
                String ADMIN_ID = common.get_json_array_position_value(jsonData,1,"ADMIN_ID");
                map.put("ADMIN_ID",ADMIN_ID);
                map.put("ID",common.get_json_array_position_value(jsonData,1,"ID"));
                map.put("NAME",common.get_json_array_position_value(jsonData,1,"NAME"));
                map.put("AUTH",common.get_json_array_position_value(jsonData,1,"AUTH"));                
                map.put("authorized","true");
                common.setAttribute(map, response);                
                common.ResponseUrl(response, "admin_mng_pwd_change/info");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
        return;
    }

    @RequestMapping(value = "/login_check", produces = "application/text; charset=utf8")    
    @ResponseBody    
    public String login_check(HttpServletRequest request, HttpServletResponse response) 
    {
        String result = "";
        try {

            String jsonString = common.get_body(request);
            JSONObject body = new JSONObject(jsonString);
            
            String ID = body.get("ID").toString();
            String PWD = body.get("PWD").toString();
            PWD = common.get_sha256(PWD);
            String REMOTE_IP = common.client_ip(request);
            String SMS_GBN = "LOGIN_SMS";
            int INT_CHECK_SMS_VALUE = ThreadLocalRandom.current().nextInt(1000, 10000);
            String CHECK_SMS_VALUE = String.valueOf(INT_CHECK_SMS_VALUE);
            String HP_TEL = "";
            String EMAIL = "";
            String strSQL = "";
            strSQL = "CALL SP_CMS_ADMIN_USER_LOGIN_CHECK_1_5_5('"+ID+"', '"+PWD+"','"+SMS_GBN+"','"+CHECK_SMS_VALUE+"','"+REMOTE_IP+"')";
                    
            JSONArray jsonArray = common.execute_query_commit(strSQL);                
            result = common.get_data_result(jsonArray);

            if(common.get_json_array_value(jsonArray, "result").equals("ok")){                
                HP_TEL = common.get_json_array_position_value(jsonArray, 1, "HP_TEL");
                EMAIL = common.get_json_array_position_value(jsonArray, 1, "EMAIL");
                if (HP_TEL.equals("")){
                    JSONObject map = new JSONObject();
                    map.put("ADMIN_ID",common.get_json_array_position_value(jsonArray,1,"ADMIN_ID"));
                    map.put("ID",common.get_json_array_position_value(jsonArray,1,"ID"));
                    map.put("NAME",common.get_json_array_position_value(jsonArray,1,"NAME"));
                    map.put("AUTH",common.get_json_array_position_value(jsonArray,1,"AUTH"));                
                    map.put("authorized","true");
                    common.setAttribute(map, response);   
                }
                else if(!HP_TEL.equals("")){
                    String M_R_TEL_NO = HP_TEL;
                    String TITLE = "CMS로그인";
                    String MMS_MSG = "CMS로그인\n인증번호["+CHECK_SMS_VALUE+"]를 인증번호 입력란에 입력해주세요.";

                    JSONObject obj = new JSONObject();
                    obj.put("TYPE","SMS");
                    obj.put("R_TEL_NO",M_R_TEL_NO);
                    obj.put("S_TEL_NO",common.g_sms_tel);
                    obj.put("S_DATE",common.now_date_str());
                    obj.put("TITLE",TITLE);
                    obj.put("MESSAGE",MMS_MSG);
                    obj.put("REFER","http://vendor.veteranscout.co.kr");
                    strSQL = "{call sc_tran_PROC_veteran(?,?,?,?,?,?,?)}";
                    JSONArray jsonResult = common.execute_query_mssql_mms(strSQL, obj);                    
                    if(!EMAIL.equals("")){
                        EmailBody emailBody = new EmailBody();
                        String strMailBody = emailBody.GetCmsAuthCodeHtml();
                        
                        strMailBody = strMailBody.replace("[##AUTH_CODE##]", CHECK_SMS_VALUE);
                        strMailBody = strMailBody.replace("[##SUBMIT_URL##]", common.g_email_url);
                        
                        strMailBody = strMailBody.replace("[##YEAR##]", common.now_year_string());
                        //String FROM_MAIL = "joowon@scout.co.kr";
                        String FROM_MAIL = common.cs_email;
                        EmailUtil emailUtil = new EmailUtil();
                        String EMAIL_SUBJECT = "[CMS] 로그인 인증번호 메일";
                        String TO_EMAIL = EMAIL;
    
                        JSONObject objResult = emailUtil.sendMailLinuxSmtps(request, FROM_MAIL, TO_EMAIL, EMAIL_SUBJECT, strMailBody);
                        if(common.get_json_value(objResult,"result").equals("error")){
                            emailUtil.sendMailLinux(request, FROM_MAIL, TO_EMAIL, EMAIL_SUBJECT, strMailBody);
                        }
                    }
                }
                
            }
        } 
        catch (Exception e) {
            e.printStackTrace();
        }     
        return result;
    }


    

    @RequestMapping(value = "/login_select_sms", produces = "application/text; charset=utf8")    
    @ResponseBody    
    public String login_select_sms(HttpServletRequest request, HttpServletResponse response) 
    {
        String result = "";
        try {

            String jsonString = common.get_body(request);
            JSONObject body = new JSONObject(jsonString);
            String SMS_GBN = "LOGIN_SMS";
            String ID = body.get("ID").toString();
            String PWD = body.get("PWD").toString();            
            PWD = common.get_sha256(PWD);            
            String CHECK_SMS_VALUE = body.get("CHECK_SMS_VALUE").toString();
            String REMOTE_IP = common.client_ip(request);            
            
            String strSQL = "";
            // CALL SP_CMS_ADMIN_USER_LOGIN_SMS_INSERT_1_0_5('LOGIN_SMS','ynshin',SHA2('syn1042#1',256),'010-3612-2531','655316');
            strSQL = "CALL SP_CMS_ADMIN_USER_LOGIN_SMS_SELECT_1_5_5('"+SMS_GBN+"','"+ID+"', '"+PWD+"','"+CHECK_SMS_VALUE+"','"+REMOTE_IP+"')";
                    
            JSONArray jsonArray = common.execute_query_commit(strSQL);                
            result = common.get_data_result(jsonArray);

            if(common.get_json_array_value(jsonArray, "result").equals("ok")){                
                
                JSONObject map = new JSONObject();
                map.put("ADMIN_ID",common.get_json_array_position_value(jsonArray,1,"ADMIN_ID"));
                map.put("ID",common.get_json_array_position_value(jsonArray,1,"ID"));
                map.put("NAME",common.get_json_array_position_value(jsonArray,1,"NAME"));
                map.put("AUTH",common.get_json_array_position_value(jsonArray,1,"AUTH"));                
                map.put("authorized","true");
                common.setAttribute(map, response);      

            }
        } 
        catch (Exception e) {
            e.printStackTrace();
        }     
        return result;
    }

    @RequestMapping(value = "/pwd_sms_insert", produces = "application/text; charset=utf8")    
    @ResponseBody    
    public String pwd_sms_insert(HttpServletRequest request, HttpServletResponse response) 
    {
        String result = "";
        try {

            String jsonString = common.get_body(request);
            JSONObject body = new JSONObject(jsonString);
            
            String ID = body.get("ID").toString();
            String NAME = body.get("NAME").toString();
            String EMAIL = body.get("EMAIL").toString();
            // PWD = common.get_sha256(PWD);
            String REMOTE_IP = common.client_ip(request);
            String SMS_GBN = "PWD_SMS";
            int INT_CHECK_SMS_VALUE = ThreadLocalRandom.current().nextInt(1000, 10000);
            String CHECK_SMS_VALUE = String.valueOf(INT_CHECK_SMS_VALUE);
            String HP_TEL = "";            
            String strSQL = "";
            strSQL = "CALL SP_CMS_ADMIN_USER_SMS_INSERT_1_5_5('"+SMS_GBN+"','"+ID+"', '"+NAME+"','"+EMAIL+"','"+CHECK_SMS_VALUE+"','"+REMOTE_IP+"')";
                    
            JSONArray jsonArray = common.execute_query_commit(strSQL);                
            result = common.get_data_result(jsonArray);

            if(common.get_json_array_value(jsonArray, "result").equals("ok")){                
                HP_TEL = common.get_json_array_value(jsonArray,"HP_TEL");
                EMAIL = common.get_json_array_value(jsonArray, "EMAIL");

                if(!EMAIL.equals("")){
                    EmailBody emailBody = new EmailBody();
                    String strMailBody = emailBody.GetCmsImsiPwdAuthCodeHtml();
                    
                    strMailBody = strMailBody.replace("[##AUTH_CODE##]", CHECK_SMS_VALUE);
                    strMailBody = strMailBody.replace("[##SUBMIT_URL##]", common.g_email_url);
                    
                    strMailBody = strMailBody.replace("[##YEAR##]", common.now_year_string());
                    //String FROM_MAIL = "joowon@scout.co.kr";
                    String FROM_MAIL = common.cs_email;
                    EmailUtil emailUtil = new EmailUtil();
                    String EMAIL_SUBJECT = "[CMS] 비밀번호 재발급 인증번호 메일";
                    String TO_EMAIL = EMAIL;
    
                    JSONObject objResult = emailUtil.sendMailLinuxSmtps(request, FROM_MAIL, TO_EMAIL, EMAIL_SUBJECT, strMailBody);
                    if(common.get_json_value(objResult,"result").equals("error")){
                        emailUtil.sendMailLinux(request, FROM_MAIL, TO_EMAIL, EMAIL_SUBJECT, strMailBody);
                    }     
                }
                

                if(!HP_TEL.equals("")){
                    String M_R_TEL_NO = HP_TEL;
                    String TITLE = "CMS비밀번호 재발급";
                    String MMS_MSG = "CMS비밀번호 재발급\n인증번호["+CHECK_SMS_VALUE+"]를 인증번호 입력란에 입력해주세요.";

                    JSONObject obj = new JSONObject();
                    obj.put("TYPE","SMS");
                    obj.put("R_TEL_NO",M_R_TEL_NO);
                    obj.put("S_TEL_NO",common.g_sms_tel);
                    obj.put("S_DATE",common.now_date_str());
                    obj.put("TITLE",TITLE);
                    obj.put("MESSAGE",MMS_MSG);
                    obj.put("REFER","http://vendor.veteranscout.co.kr");
                    strSQL = "{call sc_tran_PROC_veteran(?,?,?,?,?,?,?)}";
                    JSONArray jsonResult = common.execute_query_mssql_mms(strSQL, obj);                    
                    
                }
                
            }
        } 
        catch (Exception e) {
            e.printStackTrace();
        }     
        return result;
    }


    

    @RequestMapping(value = "/pwd_sms_select", produces = "application/text; charset=utf8")    
    @ResponseBody    
    public String pwd_sms_select(HttpServletRequest request, HttpServletResponse response) 
    {
        String result = "";
        try {

            String jsonString = common.get_body(request);
            JSONObject body = new JSONObject(jsonString);
            String SMS_GBN = "PWD_SMS";
            // PWD = common.get_sha256(PWD);
            String ID = body.get("ID").toString();
            String NAME = body.get("NAME").toString();            
            String EMAIL = body.get("EMAIL").toString();            
            String CHECK_SMS_VALUE = body.get("CHECK_SMS_VALUE").toString();
            String CHANGE_PWD = common.get_imsi_pwd();
            String CHANGE_PWD_STRING = common.get_sha256(CHANGE_PWD);                                    
            String REMOTE_IP = common.client_ip(request);     
            String HP_TEL = "";
            
            String strSQL = "";
            // CALL SP_CMS_ADMIN_USER_SMS_SELECT_1_5_5('PWD_SMS','ynshin','신윤남','ynshin@scout.co.kr','5316',SHA2('AE12FE',256),'210.207.99.43');
            strSQL = "CALL SP_CMS_ADMIN_USER_SMS_SELECT_1_5_5('"+SMS_GBN+"','"+ID+"', '"+NAME+"','"+EMAIL+"','"+CHECK_SMS_VALUE+"','"+CHANGE_PWD_STRING+"','"+REMOTE_IP+"')";
                    
            JSONArray jsonArray = common.execute_query_commit(strSQL);                
            result = common.get_data_result(jsonArray);

            if(common.get_json_array_value(jsonArray, "result").equals("ok")){                
                
                HP_TEL = common.get_json_array_value(jsonArray, "HP_TEL");
                EMAIL = common.get_json_array_value(jsonArray, "EMAIL");
                NAME = common.get_json_array_value(jsonArray, "NAME");
                ID = common.get_json_array_value(jsonArray, "ID");

                if(!EMAIL.equals("")){
                    EmailBody emailBody = new EmailBody();
                    String strMailBody = emailBody.GetCmsImsiPwdHtml();
                    
                    strMailBody = strMailBody.replace("[##USER_NAME##]",NAME);
                    strMailBody = strMailBody.replace("[##USER_ID##]", ID);
                    strMailBody = strMailBody.replace("[##USER_PWD##]", CHANGE_PWD);
                    strMailBody = strMailBody.replace("[##SUBMIT_URL##]", common.g_email_url);
                    
                    strMailBody = strMailBody.replace("[##YEAR##]", common.now_year_string());
                    //String FROM_MAIL = "joowon@scout.co.kr";
                    String FROM_MAIL = common.cs_email;
                    EmailUtil emailUtil = new EmailUtil();
                    String EMAIL_SUBJECT = "[CMS] 비밀번호 재발급 메일";
                    String TO_EMAIL = EMAIL;
    
                    JSONObject objResult = emailUtil.sendMailLinuxSmtps(request, FROM_MAIL, TO_EMAIL, EMAIL_SUBJECT, strMailBody);
                    if(common.get_json_value(objResult,"result").equals("error")){
                        emailUtil.sendMailLinux(request, FROM_MAIL, TO_EMAIL, EMAIL_SUBJECT, strMailBody);
                    }     
                }
                

                if(!HP_TEL.equals("")){
                    String M_R_TEL_NO = HP_TEL;
                    String TITLE = "CMS비밀번호 재발급";
                    String MMS_MSG = "CMS비밀번호 재발급\n";
                    MMS_MSG += "[CMS] "+NAME+" 회원님\n";
                    MMS_MSG += "비밀번호 재발급이 완료되었습니다. 아래 페이지에서 로그인 후 사용가능합니다.\n\n";
                    MMS_MSG += "아이디 : "+ID+"\n";
                    MMS_MSG += "비밀번호 : "+CHANGE_PWD+"\n\n";
                    MMS_MSG += "로그인 바로가기 : "+common.g_email_url;

                    JSONObject obj = new JSONObject();
                    obj.put("TYPE","SMS");
                    obj.put("R_TEL_NO",M_R_TEL_NO);
                    obj.put("S_TEL_NO",common.g_sms_tel);
                    obj.put("S_DATE",common.now_date_str());
                    obj.put("TITLE",TITLE);
                    obj.put("MESSAGE",MMS_MSG);
                    obj.put("REFER","http://vendor.veteranscout.co.kr");
                    strSQL = "{call sc_tran_PROC_veteran(?,?,?,?,?,?,?)}";
                    JSONArray jsonResult = common.execute_query_mssql_mms(strSQL, obj);                    
                    
                }

            }
        } 
        catch (Exception e) {
            e.printStackTrace();
        }     
        return result;
    }

    @RequestMapping(value = "/imsi_pwd")    
    // @ResponseBody    
    public ModelAndView imsi_pwd(CommandMap body, HttpServletRequest request, HttpServletResponse response) {
        ModelAndView view = new ModelAndView();                
        try {
            // header.jsp
            String TOP_MENU = "1";
            String LEFT_MENU = "1";
            JSONObject jsonHeader = common.getJsonHeader(request, TOP_MENU, LEFT_MENU);
            
            view.addObject("header",jsonHeader);
            
            view.setViewName("imsi_pwd");                
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return view;
    }

    
    @RequestMapping(value = "/login_not_access")    
    // @ResponseBody    
    public ModelAndView login_not_access(CommandMap body, HttpServletRequest request, HttpServletResponse response) {
        ModelAndView view = new ModelAndView();                
        try {
            // header.jsp
            String TOP_MENU = "1";
            String LEFT_MENU = "1";
            JSONObject jsonHeader = common.getJsonHeader(request, TOP_MENU, LEFT_MENU);
            
            view.addObject("header",jsonHeader);

            view.setViewName("login_not_access");                
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return view;
    }

    @RequestMapping(value = "/logout")    
    // @ResponseBody    
    public ModelAndView logout(CommandMap body, HttpServletRequest request, HttpServletResponse response) 
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
                
                common.removeAttribute(response);

                // header.jsp
                JSONObject jsonHeader = common.getJsonHeader(request, TOP_MENU, LEFT_MENU);
                
                
                view.addObject("header",jsonHeader);

		        view.setViewName("login");
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

    @RequestMapping(value = "/main_mng")    
    // @ResponseBody    
    public ModelAndView main_mng(CommandMap body, HttpServletRequest request, HttpServletResponse response) {
        ModelAndView view = new ModelAndView();        
        JSONObject user = null;
        try {
            user = common.getAttribute(request);
            if(user!=null){                
                String ADMIN_ID = user.get("ADMIN_ID").toString();
                String IN_ID = user.get("ID").toString();
                String NAME = user.get("NAME").toString();
                String AUTH = user.get("AUTH").toString();
                String TOP_MENU = "";
                String LEFT_MENU = "";

                String ACCP_STATUS = "";
                String SEARCH_TYPE = "1";
                String SEARCH_TXT = "";
                int PAGE = 1;

                // header.jsp
                JSONObject jsonHeader = common.getJsonHeader(request, TOP_MENU, LEFT_MENU);
                             

                jsonHeader.put("ACCP_STATUS",ACCP_STATUS);                
                jsonHeader.put("SEARCH_TYPE",SEARCH_TYPE);                
                jsonHeader.put("SEARCH_TXT",SEARCH_TXT);                
                jsonHeader.put("PAGE",PAGE);    
                
                view.addObject("header",jsonHeader);

		        view.setViewName("main_mng");
            }
            else{
				String TOP_MENU = "";
                String LEFT_MENU = "";
				JSONObject jsonHeader = common.getJsonHeader(request, TOP_MENU, LEFT_MENU);
                
                view.addObject("header",jsonHeader);

                view.setViewName("/login");                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return view;
    }

    @RequestMapping(value = "/upload/board/{ym}/{fileName:.+}")        
    public ResponseEntity<byte[]> board(HttpServletRequest request,
    @PathVariable String ym,
    @PathVariable String fileName
    ) 
    {
        String filePath = common.g_cms_file_path + "/upload/board/"+ym+"/"+fileName;
        ResponseEntity<byte[]> result = null;
        try {
            File img = new File(filePath);
            result = ResponseEntity.ok().contentType(MediaType.valueOf(FileTypeMap.getDefaultFileTypeMap().getContentType(img))).body(Files.readAllBytes(img.toPath()));    
        } catch (IOException e) {
            e.printStackTrace();
        }        
        return result;
    }

    @RequestMapping(value = "/upload/board/download/{ym}/{fileName:.+}")        
    public void board_download(HttpServletRequest request, HttpServletResponse response,
    @PathVariable String ym,
    @PathVariable String fileName
    ) 
    {
        String filePath = common.g_cms_file_path + "/upload/board/"+ym+"/"+fileName;        
        try {
            File file = new File(filePath);
            String mimeType = URLConnection.guessContentTypeFromName(file.getName());
            if (mimeType == null) {
                //unknown mimetype so set the mimetype to application/octet-stream
                mimeType = "application/octet-stream";
            }

            response.setContentType(mimeType);

            /**
             * In a regular HTTP response, the Content-Disposition response header is a
             * header indicating if the content is expected to be displayed inline in the
             * browser, that is, as a Web page or as part of a Web page, or as an
             * attachment, that is downloaded and saved locally.
             * 
             */

            /**
             * Here we have mentioned it to show inline
             */
            response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() + "\""));

            //Here we have mentioned it to show as attachment
            //response.setHeader("Content-Disposition", String.format("attachment; filename=\"" + file.getName() + "\""));

            response.setContentLength((int) file.length());

            InputStream inputStream = new BufferedInputStream(new FileInputStream(file));

            FileCopyUtils.copy(inputStream, response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }        
        return;
    }

	@RequestMapping(value = "/upload/editorimg/{ym}/{fileName:.+}")        
    public ResponseEntity<byte[]> editorimg(HttpServletRequest request,
    @PathVariable String ym,
    @PathVariable String fileName
    ) 
    {
        String filePath = common.g_cms_file_path + "/upload/editorimg/"+ym+"/"+fileName;
        ResponseEntity<byte[]> result = null;
        try {
            File img = new File(filePath);
            result = ResponseEntity.ok().contentType(MediaType.valueOf(FileTypeMap.getDefaultFileTypeMap().getContentType(img))).body(Files.readAllBytes(img.toPath()));    
        } catch (IOException e) {
            e.printStackTrace();
        }        
        return result;
    }

    @RequestMapping(value = "/upload/faq/{ym}/{fileName:.+}")        
    public void faq_download(HttpServletRequest request, HttpServletResponse response,
    @PathVariable String ym,
    @PathVariable String fileName
    ) 
    {
        String filePath = common.g_cms_file_path + "/upload/faq/"+ym+"/"+fileName;        
        try {
            File file = new File(filePath);
            String mimeType = URLConnection.guessContentTypeFromName(file.getName());
            if (mimeType == null) {
                //unknown mimetype so set the mimetype to application/octet-stream
                mimeType = "application/octet-stream";
            }

            response.setContentType(mimeType);

            /**
             * In a regular HTTP response, the Content-Disposition response header is a
             * header indicating if the content is expected to be displayed inline in the
             * browser, that is, as a Web page or as part of a Web page, or as an
             * attachment, that is downloaded and saved locally.
             * 
             */

            /**
             * Here we have mentioned it to show inline
             */
            response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() + "\""));

            //Here we have mentioned it to show as attachment
            //response.setHeader("Content-Disposition", String.format("attachment; filename=\"" + file.getName() + "\""));

            response.setContentLength((int) file.length());

            InputStream inputStream = new BufferedInputStream(new FileInputStream(file));

            FileCopyUtils.copy(inputStream, response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }        
        return;
    }
}