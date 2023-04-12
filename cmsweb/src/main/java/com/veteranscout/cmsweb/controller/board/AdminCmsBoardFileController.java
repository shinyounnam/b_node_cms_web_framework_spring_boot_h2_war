package com.veteranscout.cmsweb.controller.board;

import java.util.HashMap;
import java.util.Map;
import java.io.File;
import java.util.*;

import javax.activation.FileTypeMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.veteranscout.cmsweb.util.*;
import org.json.*;

@Controller
public class AdminCmsBoardFileController {  

    private String module_name = "admin_cms_board_file";
    private CommonUtil common = new CommonUtil();
    
    @RequestMapping(value = "/admin_cms_board_file")    
    // @ResponseBody    
    public ModelAndView admin_cms_board_file(CommandMap body, HttpServletRequest request    
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

                String FILE_NAME = "";
                String FILE_SIZE = "0";
                String CATEGORY = "";

                // header.jsp
                JSONObject jsonHeader = common.getJsonHeader(request, TOP_MENU, LEFT_MENU);                
                
                jsonHeader.put("FILE_NAME",FILE_NAME);                                
                jsonHeader.put("FILE_SIZE",FILE_SIZE);        
                jsonHeader.put("CATEGORY",CATEGORY);        
                
                
                view.addObject("header",jsonHeader);


		        view.setViewName("board/admin_cms_board_file");
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

    @RequestMapping(value = "/admin_cms_board_file/insert")    
    // @ResponseBody    
    public ModelAndView insert(CommandMap body, HttpServletRequest request    
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

                String FILE_SIZE = "0";
                String CATEGORY = "contentimg";
                String FILE_NAME = "";

                // header.jsp
                JSONObject jsonHeader = common.getJsonHeader(request, TOP_MENU, LEFT_MENU);
                
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
                String VIRTUAL_PATH = "/upload/editorimg/"+common.now_year_month() +"/";
                
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

                            if(originalFileExtension.indexOf("png")!= -1 ||
                            originalFileExtension.indexOf("jpg")!= -1 ||
                            originalFileExtension.indexOf("jpeg")!= -1
                            ){
                                multipartFile.transferTo(file);        
                                FILE_NAME = VIRTUAL_PATH + storedFileName;                        
                            }
                            
                            
                            break;
                        }                        
                    }
                }

                jsonHeader.put("FILE_NAME",FILE_NAME);                                
                jsonHeader.put("FILE_SIZE",FILE_SIZE);        
                jsonHeader.put("CATEGORY",CATEGORY); 
                
                view.addObject("header",jsonHeader);

		        view.setViewName("board/admin_cms_board_file");
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