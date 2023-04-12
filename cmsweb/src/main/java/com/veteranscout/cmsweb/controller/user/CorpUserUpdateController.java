package com.veteranscout.cmsweb.controller.user;

import java.io.File;
import java.util.ArrayList;
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
public class CorpUserUpdateController {  

    private String module_name = "corp_user_update";
    private CommonUtil common = new CommonUtil();
    
    @RequestMapping(value = "/corp_user_update/info/{corp_id}")    
    // @ResponseBody    
    public ModelAndView corp_user_update_info(CommandMap body, HttpServletRequest request,
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
                String LEFT_MENU = "3";
                
                int CORP_ID = corp_id;

                // header.jsp
                JSONObject jsonHeader = common.getJsonHeader(request, TOP_MENU, LEFT_MENU);
                

                jsonHeader.put("CORP_ID",CORP_ID);                
                
                view.addObject("header",jsonHeader);

                String strSQL = "";
                strSQL = "CALL SP_CMS_ADMIN_CORP_USER_INFO('"+CORP_ID+"')";
                JSONArray jsonArray = common.execute_query(strSQL);
                view.addObject("data",jsonArray);

		        view.setViewName("user/corp_user_update");
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

    @RequestMapping(value = "/corp_user_update/update_multi", produces = "application/text; charset=utf8")    
    @ResponseBody    
    public String update_multi(HttpServletRequest request) 
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
                String TOP_MENU = "2";
                String LEFT_MENU = "3";

                String jsonString = common.get_body(request);
                JSONObject body = new JSONObject(jsonString);
                
                String CORP_ID = body.get("CORP_ID").toString();
                String CORP_NAME = body.get("CORP_NAME").toString();
                String BIZ_NO = body.get("BIZ_NO").toString();
                String REP_NAME = body.get("REP_NAME").toString();
                String REP_TEL = body.get("REP_TEL").toString();
                String POST_CODE = body.get("POST_CODE").toString();
                String ADDR1 = body.get("ADDR1").toString();
                String ADDR2 = body.get("ADDR2").toString();
                String CHAMGO = body.get("CHAMGO").toString();
                String POS_LAT = body.get("POS_LAT").toString();
                String POS_LNG = body.get("POS_LNG").toString();
                String HOME_URL = body.get("HOME_URL").toString();
                String EMAIL = body.get("EMAIL").toString();
                String MAN_NAME = body.get("MAN_NAME").toString();
                String MAN_PSTN = body.get("MAN_PSTN").toString();
                String MAN_TEL = body.get("MAN_TEL").toString();
                String INTRO_TXT = body.get("INTRO_TXT").toString();
                
                String strSQL = "";
                strSQL = "CALL SP_CMS_CORP_USER_DATA_UPDATE('"+CORP_ID+"','"+CORP_NAME+"','"+BIZ_NO+"',"+
								 "'"+REP_NAME+"','"+REP_TEL+"','"+POST_CODE+"','"+ADDR1+"','"+ADDR2+"',"+
								 "'"+CHAMGO+"','"+POS_LAT+"','"+POS_LNG+"','"+HOME_URL+"',"+
                                 "'"+EMAIL+"','"+MAN_NAME+"','"+MAN_PSTN+"','"+MAN_TEL+"','"+INTRO_TXT+"')";
                                 


                JSONArray jsonArray = common.execute_query_commit(strSQL);                
                JSONObject jsonResult = new JSONObject();
                jsonResult.put("result","ok");
                jsonResult.put("message","");
                
                result = jsonResult.toString();
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

    @RequestMapping(value = "/corp_user_update/submit")    
    // @ResponseBody    
    public ModelAndView submit(CommandMap body, HttpServletRequest request) 
    {
        String result = "";
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
                
                String CORP_ID = body.get("CORP_ID").toString();
                String CORP_NAME = body.get("CORP_NAME").toString();
                String BIZ_NO = body.get("BIZ_NO").toString();
                String REP_NAME = body.get("REP_NAME").toString();
                String REP_TEL = body.get("REP_TEL").toString();
                String POST_CODE = body.get("POST_CODE").toString();
                String ADDR1 = body.get("ADDR1").toString();
                String ADDR2 = body.get("ADDR2").toString();
                String CHAMGO = body.get("CHAMGO").toString();
                String POS_LAT = body.get("POS_LAT").toString();
                String POS_LNG = body.get("POS_LNG").toString();
                String HOME_URL = body.get("HOME_URL").toString();
                String EMAIL = body.get("EMAIL").toString();
                String MAN_NAME = body.get("MAN_NAME").toString();
                String MAN_PSTN = body.get("MAN_PSTN").toString();
                String MAN_TEL = body.get("MAN_TEL").toString();
                String INTRO_TXT = body.get("INTRO_TXT").toString();

                // header.jsp
                JSONObject jsonHeader = common.getJsonHeader(request, TOP_MENU, LEFT_MENU);                

                jsonHeader.put("CORP_ID",CORP_ID);                
                
                view.addObject("header",jsonHeader);

                String strSQL = "";
                strSQL = "CALL SP_CMS_CORP_USER_DATA_UPDATE('"+CORP_ID+"','"+CORP_NAME+"','"+BIZ_NO+"',"+
								 "'"+REP_NAME+"','"+REP_TEL+"','"+POST_CODE+"','"+ADDR1+"','"+ADDR2+"',"+
								 "'"+CHAMGO+"','"+POS_LAT+"','"+POS_LNG+"','"+HOME_URL+"',"+
								 "'"+EMAIL+"','"+MAN_NAME+"','"+MAN_PSTN+"','"+MAN_TEL+"','"+INTRO_TXT+"')";
                JSONArray jsonArray = common.execute_query_commit(strSQL);

                // 로고 파일 등록
                MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)request;
                Iterator<String> iterator = multipartHttpServletRequest.getFileNames();
                
                MultipartFile multipartFile = null;
                String originalFileName = null;
                String originalFileExtension = null;
                String storedFileName = null;
                
                List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
                Map<String, Object> listMap = null;
                String filePath = common.g_file_path;

                File file = new File(filePath);
                if(file.exists() == false){
                    file.mkdirs();
                }
                
                String IMG_TYPE = "LOGO";
                String VIRTUAL_PATH = "/images/cms_corp_img/logo/"+common.now_year_month()+"/";
                String FILE_NAME = "";
                strSQL = "";
                
                while(iterator.hasNext()){
                    multipartFile = multipartHttpServletRequest.getFile(iterator.next());
                    if(multipartFile.isEmpty() == false){                        
                        if(multipartFile.getName().equals("imageFile")){
                            originalFileName = multipartFile.getOriginalFilename();
                            originalFileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
                            storedFileName = originalFileName.substring(0,originalFileName.lastIndexOf(".")) + "_" +common.get_file_miliseconds() + originalFileExtension;
                            
                            File file_directory = new File(filePath + VIRTUAL_PATH);
                            if(file_directory.exists() == false){
                                file_directory.mkdirs();
                            }
                            file = new File(filePath + VIRTUAL_PATH + storedFileName);
                            multipartFile.transferTo(file);

                            FILE_NAME = storedFileName;

                            strSQL = strSQL = "CALL SP_CMS_CORP_USER_IMG_LOGO_INSERT"+
                            "('"+CORP_ID+"','"+IMG_TYPE+"','"+VIRTUAL_PATH+"','"+FILE_NAME+"')";

                            jsonArray = common.execute_query(strSQL);
                            if (common.get_json_array_value(jsonArray,"result").equals("error")){                                
                                result = "redirect:"+"/alert_msg_url/alert/"+common.encodeURIComponent(common.get_json_array_value(jsonArray,"message"))+"/user_search_mng";
                                view.setViewName(result);
                                return view;
                            }
                            if(common.get_json_array_value(jsonArray,"PREV_FILE_URL").equals("")==false){
                                File file_delete = new File(filePath + common.get_json_array_value(jsonArray,"PREV_FILE_URL") + common.get_json_array_value(jsonArray,"PREV_FILE_NAME"));
                                file_delete.delete();
                            }

                            result = jsonArray.toString();
                            break;
                        }                        
                    }
                }

		        view.setViewName("redirect:"+"/alert_msg_url/alert/"+common.encodeURIComponent("수정하였습니다.")+"/user_search_mng");
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

    @RequestMapping(value = "/corp_user_update/delete", produces = "application/text; charset=utf8")    
    @ResponseBody    
    public String delete(HttpServletRequest request) 
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
                String TOP_MENU = "2";
                String LEFT_MENU = "3";

                String jsonString = common.get_body(request);
                JSONObject body = new JSONObject(jsonString);
                
                String CORP_ID = body.get("CORP_ID").toString();
                
                
                String strSQL = "";
                strSQL = "CALL SP_CMS_CORP_USER_DATA_DELETE('"+CORP_ID+"')";
                                 
                JSONArray jsonArray = common.execute_query_commit(strSQL);                
                result = common.get_data_result(jsonArray);
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