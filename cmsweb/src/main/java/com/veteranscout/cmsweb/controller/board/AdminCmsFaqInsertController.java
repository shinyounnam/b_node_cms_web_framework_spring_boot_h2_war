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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.veteranscout.cmsweb.util.*;
import org.json.*;

@Controller
public class AdminCmsFaqInsertController {  

    private String module_name = "admin_cms_faq_insert";
    private CommonUtil common = new CommonUtil();
    
    @RequestMapping(value = "/admin_cms_faq_insert/insert/info")    
    // @ResponseBody    
    public ModelAndView admin_cms_faq_insert(CommandMap body, HttpServletRequest request) 
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
                String LEFT_MENU = "2";         

                int ID = 0;
                String ACTION = "INSERT";
                // header.jsp
                JSONObject jsonHeader = common.getJsonHeader(request, TOP_MENU, LEFT_MENU);                
                
                jsonHeader.put("ID",ID);                                
                jsonHeader.put("ACTION",ACTION); 
                
                String strSQL = "";
                strSQL = "CALL SP_CMS_FAQ_INFO('"+ID+"')";
                JSONArray jsonArray = common.execute_query(strSQL);

                view.addObject("header",jsonHeader);
                view.addObject("data",jsonArray);

		        view.setViewName("board/admin_cms_faq_insert");
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

    @RequestMapping(value = "/admin_cms_faq_insert/insert_submit")    
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
                String TOP_MENU = "5";
                String LEFT_MENU = "2";         

                String GBN = body.get("GBN").toString();
                String TITLE = body.get("TITLE").toString();
                String CONTENT = body.get("CONTENT").toString();

                // header.jsp
                JSONObject jsonHeader = common.getJsonHeader(request, TOP_MENU, LEFT_MENU);                                                 
                
                String strSQL = "";
                strSQL = "CALL SP_CMS_FAQ_INSERT('"+GBN+"', '"+TITLE+"', "+
											 "'"+CONTENT+"', '"+ADMIN_ID+"')";
                JSONArray jsonArray = common.execute_query(strSQL);

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
                String VIRTUAL_PATH = "/upload/faq/"+common.now_year_month()+"/";
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
                            String FILE_URL = VIRTUAL_PATH + storedFileName;
                            file = new File(filePath + VIRTUAL_PATH + storedFileName);
                            multipartFile.transferTo(file);

                            FILE_NAME = storedFileName;
                            String ORG_FILE_NAME = originalFileName;

                            strSQL = "CALL SP_CMS_BOARD_FAQ_INSERT("+BOARD_ID+", '"+FILE_NAME+"','"+FILE_URL+"','"+ORG_FILE_NAME+"')";

                            jsonArray = common.execute_query_commit(strSQL);
                            
                        }                        
                    }
                }
                
                
                

                view.addObject("header",jsonHeader);
                view.addObject("data",jsonArray);

		        view.setViewName("redirect:/admin_cms_faq");
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

    @RequestMapping(value = "/admin_cms_faq_insert/update/info/{id}")    
    // @ResponseBody    
    public ModelAndView update_info(CommandMap body, HttpServletRequest request,
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
                String TOP_MENU = "5";
                String LEFT_MENU = "2";         

                int ID = id;
                String ACTION = "UPDATE";

                // header.jsp
                JSONObject jsonHeader = common.getJsonHeader(request, TOP_MENU, LEFT_MENU);
                
                jsonHeader.put("ID",ID);                                
                jsonHeader.put("ACTION",ACTION); 
                
                String strSQL = "";
                strSQL = "CALL SP_CMS_FAQ_INFO('"+ID+"')";
                JSONArray jsonArray = common.execute_query(strSQL);

                view.addObject("header",jsonHeader);
                view.addObject("data",jsonArray);

		        view.setViewName("board/admin_cms_faq_insert");
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

    @RequestMapping(value = "/admin_cms_faq_insert/update_submit")    
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
                String TOP_MENU = "5";
                String LEFT_MENU = "2";         

                String ID = body.get("ID").toString();
                String GBN = body.get("GBN").toString();
                String TITLE = body.get("TITLE").toString();
                String CONTENT = body.get("CONTENT").toString();

                // header.jsp
                JSONObject jsonHeader = common.getJsonHeader(request, TOP_MENU, LEFT_MENU);                                                      
                
                String strSQL = "";
                strSQL = "CALL SP_CMS_FAQ_UPDATE('"+ID+"','"+GBN+"', '"+TITLE+"', "+
                         "'"+CONTENT+"', '"+ADMIN_ID+"')";
                JSONArray jsonArray = common.execute_query(strSQL);

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
                String VIRTUAL_PATH = "/upload/faq/"+common.now_year_month()+"/";
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
                            String FILE_URL = VIRTUAL_PATH + storedFileName;
                            file = new File(filePath + VIRTUAL_PATH + storedFileName);
                            multipartFile.transferTo(file);

                            FILE_NAME = storedFileName;
                            String ORG_FILE_NAME = originalFileName;

                            strSQL = "CALL SP_CMS_BOARD_FAQ_INSERT("+ID+", '"+FILE_NAME+"','"+FILE_URL+"','"+ORG_FILE_NAME+"')";

                            jsonArray = common.execute_query_commit(strSQL);
                            
                        }                        
                    }
                }
                
                
                

                view.addObject("header",jsonHeader);
                view.addObject("data",jsonArray);

		        view.setViewName("redirect:/admin_cms_faq");
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