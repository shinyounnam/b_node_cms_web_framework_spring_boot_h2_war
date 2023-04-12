package com.veteranscout.cmsweb.controller.board;

import java.util.HashMap;
import java.util.Map;
import java.io.File;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.veteranscout.cmsweb.util.*;
import org.json.*;

@Controller
public class AdminCmsBoardInsertController {  

    private String module_name = "admin_cms_board_insert";
    private CommonUtil common = new CommonUtil();
    
    @RequestMapping(value = "/admin_cms_board_insert/insert/info")    
    // @ResponseBody    
    public ModelAndView admin_cms_board_insert_info(CommandMap body, HttpServletRequest request    
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
                String TOP_MENU = "6";
                String LEFT_MENU = "1";

                int ID = 0;
                String ACTION = "INSERT";

                // header.jsp
                JSONObject jsonHeader = common.getJsonHeader(request, TOP_MENU, LEFT_MENU);                
                
                jsonHeader.put("ID",ID);                                
                jsonHeader.put("ACTION",ACTION);        
                
                
                view.addObject("header",jsonHeader);

                String strSQL = "CALL SP_CMS_BOARD_INFO_1_1_92('"+ID+"')";
                JSONArray jsonArray = common.execute_query(strSQL);
                view.addObject("data",jsonArray);

		        view.setViewName("board/admin_cms_board_insert");
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

    @RequestMapping(value = "/admin_cms_board_insert/insert_submit")    
    // @ResponseBody    
    public ModelAndView insert_submit(CommandMap body, HttpServletRequest request    
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
                String TOP_MENU = "6";
                String LEFT_MENU = "1";

                String GBN = body.get("GBN").toString();
                String TITLE = body.get("TITLE").toString();
                String CONTENT = body.get("CONTENT").toString();                
                String ALARM_CONTENT = body.get("ALARM_CONTENT").toString();
                String V_ALARM_CONTENT = body.get("V_ALARM_CONTENT").toString();
                String SMS_CONTENT = body.get("SMS_CONTENT").toString();
                String V_SMS_CONTENT = body.get("V_SMS_CONTENT").toString();
                String MSG_CONTENT = body.get("MSG_CONTENT").toString();
                String MSG_CORP_ID = body.get("MSG_CORP_ID").toString();
                String USER_GBN = body.get("USER_GBN").toString();
                String SMS_FILE_ID = body.get("SMS_FILE_ID").toString();
                if (SMS_FILE_ID.equals(""))
                {
                    SMS_FILE_ID = "0";
                }                
                String SMS_FILE_NAME = body.get("SMS_FILE_NAME").toString();
                String S_DATE = body.get("S_DATE").toString();
                String E_DATE = body.get("E_DATE").toString();
                String EVENT_LINK = "";


                String strSQL = "";

                // 공지사항
                if (GBN.equals("A1"))
                {
                    strSQL = "CALL SP_CMS_BOARD_INSERT_1_1_92_2('"+GBN+"', '"+TITLE+"', "+
                                            "'"+CONTENT+"', "+
                                            "'"+ALARM_CONTENT+"','"+V_ALARM_CONTENT+"', "+
                                            "'"+SMS_CONTENT+"','"+V_SMS_CONTENT+"', "+
                                            "'"+MSG_CONTENT+"', '"+MSG_CORP_ID+"','"+USER_GBN+"','"+SMS_FILE_ID+"', "+
                                            "'"+ADMIN_ID+"')";
                }
                else if (GBN.equals("A2"))
                {
                    strSQL = "CALL SP_CMS_BOARD_INSERT_EVENT_1_1_92_2('"+GBN+"', '"+TITLE+"', "+
                                "'"+CONTENT+"', "+
                                "'"+ALARM_CONTENT+"','"+V_ALARM_CONTENT+"', "+
                                "'"+SMS_CONTENT+"','"+V_SMS_CONTENT+"', "+
                                "'"+MSG_CONTENT+"', '"+MSG_CORP_ID+"', '"+USER_GBN+"','"+SMS_FILE_ID+"', '"+ADMIN_ID+"',"+
                                "'"+S_DATE+"','"+E_DATE+"','"+EVENT_LINK+"')";
                }

                JSONArray jsonArray = common.execute_query_commit(strSQL);

                int BOARD_ID = common.get_json_array_value_int(jsonArray,"BOARD_ID");

                // 로고 파일 등록
                MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)request;
                Iterator<String> iterator = multipartHttpServletRequest.getFileNames();
                
                MultipartFile multipartFile = null;
                String originalFileName = null;
                String originalFileExtension = null;
                String storedFileName = null;
                
                List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
                Map<String, Object> listMap = null;
                String filePath = common.cms_dir;

                File file = new File(filePath);
                if(file.exists() == false){
                    file.mkdirs();
                }
                
                String IMG_TYPE = "LOGO";
                String VIRTUAL_PATH = "/upload/board/"+common.now_year_month()+"/";
                String FILE_NAME = "";
                strSQL = "";
                
                while(iterator.hasNext()){
                    multipartFile = multipartHttpServletRequest.getFile(iterator.next());
                    if(multipartFile.isEmpty() == false){                        
                        if(multipartFile.getName().equals("image")){
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
                            String ORG_FILE_NAME = originalFileName;

                            strSQL = "CALL SP_CMS_BOARD_FILE_INSERT("+BOARD_ID+", '"+FILE_NAME+"','"+VIRTUAL_PATH + storedFileName+"','"+ORG_FILE_NAME+"')";

                            jsonArray = common.execute_query_commit(strSQL);
                            
                        }                        
                    }
                }
                if(SMS_FILE_NAME.equals("")==false){
                    strSQL = "CALL SP_CMS_BOARD_SMS_FILE_INSERT_1_1_92("+BOARD_ID+", '"+SMS_FILE_NAME+"','http://file.veteranscout.co.kr/upload/mmsmt','"+SMS_FILE_NAME+"')";
                    jsonArray = common.execute_query_commit(strSQL);
                }


                String SEARCH_TYPE = "1";
                String SEARCH_TXT = "";                
                int PAGE = 1;

                // header.jsp
                JSONObject jsonHeader = common.getJsonHeader(request, TOP_MENU, LEFT_MENU);                
                
                jsonHeader.put("SEARCH_TYPE",SEARCH_TYPE);                                
                jsonHeader.put("SEARCH_TXT",SEARCH_TXT);                 
                jsonHeader.put("PAGE",PAGE);    
                
                
                view.addObject("header",jsonHeader);

                strSQL = "CALL SP_CMS_BOARD_PAGE('"+SEARCH_TYPE+"','"+SEARCH_TXT+"','"+PAGE+"')";
                jsonArray = common.execute_query(strSQL);
                view.addObject("data",jsonArray);

		        view.setViewName("board/admin_cms_board");
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

    @RequestMapping(value = "/admin_cms_board_insert/update/info/{id}")    
    // @ResponseBody    
    public ModelAndView admin_cms_board_insert_update_info(CommandMap body, HttpServletRequest request,
    @PathVariable("id") int id
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
                String TOP_MENU = "6";
                String LEFT_MENU = "1";

                int ID = id;
                String ACTION = "UPDATE";

                // header.jsp
                JSONObject jsonHeader = common.getJsonHeader(request, TOP_MENU, LEFT_MENU);                
                
                jsonHeader.put("IN_ID",IN_ID);                                
                jsonHeader.put("ACTION",ACTION);    
                
                
                view.addObject("header",jsonHeader);

                String strSQL = "CALL SP_CMS_BOARD_INFO_1_1_92('"+ID+"')";
                JSONArray jsonArray = common.execute_query(strSQL);
                view.addObject("data",jsonArray);

		        view.setViewName("board/admin_cms_board_insert");
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

    @RequestMapping(value = "/admin_cms_board_insert/update_submit")    
    // @ResponseBody    
    public ModelAndView update_submit(CommandMap body, HttpServletRequest request    
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
                String TOP_MENU = "6";
                String LEFT_MENU = "1";

                String ID = body.get("ID").toString();
                String GBN = body.get("GBN").toString();
                String TITLE = body.get("TITLE").toString();
                String CONTENT = body.get("CONTENT").toString();                
                String ALARM_CONTENT = body.get("ALARM_CONTENT").toString();
                String V_ALARM_CONTENT = body.get("V_ALARM_CONTENT").toString();
                String SMS_CONTENT = body.get("SMS_CONTENT").toString();
                String V_SMS_CONTENT = body.get("V_SMS_CONTENT").toString();
                String MSG_CONTENT = body.get("MSG_CONTENT").toString();
                String MSG_CORP_ID = body.get("MSG_CORP_ID").toString();
                String USER_GBN = body.get("USER_GBN").toString();
                String SMS_FILE_ID = body.get("SMS_FILE_ID").toString();                
                if (SMS_FILE_ID.equals(""))
                {
                    SMS_FILE_ID = "0";
                }
                String SMS_FILE_NAME = body.get("SMS_FILE_NAME").toString();
                String S_DATE = body.get("S_DATE").toString();
                String E_DATE = body.get("E_DATE").toString();
                String EVENT_LINK = "";                
                String BOARD_ID = body.get("ID").toString();
                // CONTENT = CONTENT.replace(/"/gi,'&quot;');
                String strSQL = "";

                if (GBN.equals("A1"))
                {
                    strSQL = "CALL SP_CMS_BOARD_UPDATE_1_1_92_2('"+ID+"','"+GBN+"', '"+TITLE+"', "+
                                            "'"+CONTENT+"', "+
                                            "'"+ALARM_CONTENT+"','"+V_ALARM_CONTENT+"', "+
                                            "'"+SMS_CONTENT+"','"+V_SMS_CONTENT+"', "+
                                            "'"+MSG_CONTENT+"', '"+MSG_CORP_ID+"','"+USER_GBN+"','"+SMS_FILE_ID+"', "+
                                            "'"+ADMIN_ID+"')";
                }
                else if (GBN.equals("A2"))
                {
                    strSQL = "CALL SP_CMS_BOARD_UPDATE_EVENT_1_1_92_2('"+ID+"','"+GBN+"', '"+TITLE+"', "+
                                "'"+CONTENT+"', "+ 
                                "'"+ALARM_CONTENT+"','"+V_ALARM_CONTENT+"', "+
                                "'"+SMS_CONTENT+"','"+V_SMS_CONTENT+"', "+
                                "'"+MSG_CONTENT+"', '"+MSG_CORP_ID+"', '"+USER_GBN+"','"+SMS_FILE_ID+"', '"+ADMIN_ID+"',"+
                                "'"+S_DATE+"','"+E_DATE+"','"+EVENT_LINK+"')";
                }

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
                String filePath = common.cms_dir;

                File file = new File(filePath);
                if(file.exists() == false){
                    file.mkdirs();
                }
                
                String IMG_TYPE = "LOGO";
                String VIRTUAL_PATH = "/upload/board/"+common.now_year_month()+"/";
                String FILE_NAME = "";
                strSQL = "";
                
                while(iterator.hasNext()){
                    multipartFile = multipartHttpServletRequest.getFile(iterator.next());
                    if(multipartFile.isEmpty() == false){                        
                        if(multipartFile.getName().equals("image")){
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
                            String ORG_FILE_NAME = originalFileName;

                            strSQL = "CALL SP_CMS_BOARD_FILE_INSERT("+BOARD_ID+", '"+FILE_NAME+"','"+VIRTUAL_PATH + storedFileName+"','"+ORG_FILE_NAME+"')";

                            jsonArray = common.execute_query_commit(strSQL);
                            
                        }                        
                    }
                }
                if(SMS_FILE_NAME.equals("")==false){
                    strSQL = "CALL SP_CMS_BOARD_SMS_FILE_INSERT_1_1_92("+BOARD_ID+", '"+SMS_FILE_NAME+"','http://file.veteranscout.co.kr/upload/mmsmt','"+SMS_FILE_NAME+"')";
                    jsonArray = common.execute_query_commit(strSQL);
                }

                String SEARCH_TYPE = "1";
                String SEARCH_TXT = "";                
                int PAGE = 1;

                // header.jsp
                JSONObject jsonHeader = common.getJsonHeader(request, TOP_MENU, LEFT_MENU);                
                
                jsonHeader.put("SEARCH_TYPE",SEARCH_TYPE);                                
                jsonHeader.put("SEARCH_TXT",SEARCH_TXT);                 
                jsonHeader.put("PAGE",PAGE);    
                
                
                view.addObject("header",jsonHeader);

                strSQL = "CALL SP_CMS_BOARD_PAGE('"+SEARCH_TYPE+"','"+SEARCH_TXT+"','"+PAGE+"')";
                jsonArray = common.execute_query(strSQL);
                view.addObject("data",jsonArray);

		        view.setViewName("board/admin_cms_board");
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