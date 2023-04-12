package com.veteranscout.cmsweb.controller.item;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.veteranscout.cmsweb.util.*;
import org.json.*;

@Controller
public class ItemSaleMngController {  

    private String module_name = "item_sale_mng";
    private CommonUtil common = new CommonUtil();
    
    @RequestMapping(value = "/item_sale_mng")    
    // @ResponseBody    
    public ModelAndView item_sale_mng(CommandMap body, HttpServletRequest request,HttpServletResponse response) 
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
                String LEFT_MENU = "1";
                
                
                String AUTH_MESSAGE = "권한이 없습니다.";

                if (AUTH.indexOf(TOP_MENU)==-1)
                {
                    common.ResponseAlsert(response, AUTH_MESSAGE, "/logout");                    
                }
                
                String S_DATE = common.now_date_string();
                String E_DATE = common.now_date_string();
                String SALE_TYPE = "0" ;
                String SEARCH_TYPE = "1";
                String SEARCH_TXT = "";                
                int PAGE_SIZE = 15;
                int PAGE = 1;

                // header.jsp
                JSONObject jsonHeader = common.getJsonHeader(request, TOP_MENU, LEFT_MENU);                

                jsonHeader.put("S_DATE",S_DATE);        
                jsonHeader.put("E_DATE",E_DATE);        
                jsonHeader.put("SALE_TYPE",SALE_TYPE);        
                jsonHeader.put("SEARCH_TYPE",SEARCH_TYPE);                                
                jsonHeader.put("SEARCH_TXT",SEARCH_TXT);                 
                jsonHeader.put("PAGE_SIZE",PAGE_SIZE);    
                jsonHeader.put("PAGE",PAGE);    
                
                
                view.addObject("header",jsonHeader);

                String strSQL = "CALL SP_M_CMS_ITEM_SALE_LIST_PAGE('"+SEARCH_TYPE+"','"+SEARCH_TXT+"','"+S_DATE+"','"+E_DATE+"','"+SALE_TYPE+"','"+PAGE_SIZE+"','"+PAGE+"')";
                JSONArray jsonArray = common.execute_query(strSQL);
                view.addObject("data",jsonArray);

		        view.setViewName("item/item_sale_mng");
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

    @RequestMapping(value = "/item_sale_mng/move/{search_type}/{search_txt}/{s_date}/{e_date}/{sale_type}/{page}")    
    // @ResponseBody    
    public ModelAndView item_sale_mng_move(CommandMap body, HttpServletRequest request,                                        
                                        @PathVariable("search_type") String search_type,
                                        @PathVariable("search_txt") String search_txt,
                                        @PathVariable("s_date") String s_date,
                                        @PathVariable("e_date") String e_date,
                                        @PathVariable("sale_type") String sale_type,         
                                        @PathVariable("page") int page                                        
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
                String LEFT_MENU = "1";                
                
                String S_DATE = s_date;
                String E_DATE = e_date;
                String SALE_TYPE = sale_type;
                String SEARCH_TYPE = search_type;
                String SEARCH_TXT = search_txt;     
                if(SEARCH_TXT.equals(" ")) SEARCH_TXT = "";           
                int PAGE_SIZE = 15;
                int PAGE = page;

                // header.jsp
                JSONObject jsonHeader = common.getJsonHeader(request, TOP_MENU, LEFT_MENU);                

                jsonHeader.put("S_DATE",S_DATE);        
                jsonHeader.put("E_DATE",E_DATE);        
                jsonHeader.put("SALE_TYPE",SALE_TYPE);        
                jsonHeader.put("SEARCH_TYPE",SEARCH_TYPE);                                
                jsonHeader.put("SEARCH_TXT",SEARCH_TXT);                 
                jsonHeader.put("PAGE_SIZE",PAGE_SIZE);    
                jsonHeader.put("PAGE",PAGE);    
                
                
                view.addObject("header",jsonHeader);

                String strSQL = "CALL SP_M_CMS_ITEM_SALE_LIST_PAGE('"+SEARCH_TYPE+"','"+SEARCH_TXT+"','"+S_DATE+"','"+E_DATE+"','"+SALE_TYPE+"','"+PAGE_SIZE+"','"+PAGE+"')";
                JSONArray jsonArray = common.execute_query(strSQL);
                view.addObject("data",jsonArray);

		        view.setViewName("item/item_sale_mng");
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

    @RequestMapping(value = "/item_sale_mng/item_cancel", produces = "application/text; charset=utf8")
    @ResponseBody
    public String item_cancel(HttpServletRequest request) {
        JSONObject user = null;
        String result = "";
        try {
            user = common.getAttribute(request);
            if (user != null) {

                String ADMIN_ID = user.get("ADMIN_ID").toString();
                String IN_ID = user.get("ID").toString();
                String NAME = user.get("NAME").toString();
                String AUTH = user.get("AUTH").toString();
                String TOP_MENU = "6";
                String LEFT_MENU = "3";

                String jsonString = common.get_body(request);
                JSONObject body = new JSONObject(jsonString);

                String INCOME_ID = body.get("INCOME_ID").toString();

                String strSQL = "";
                strSQL = "CALL SP_M_CORP_ITEM_CANCEL_OK('" + INCOME_ID + "')";

                JSONArray jsonArray = common.execute_query_commit(strSQL);
                result = common.get_data_result(jsonArray);
                common.log(module_name, "jsonArray : " + jsonArray.toString());

                if (common.get_json_array_value(jsonArray, "result").equals("ok")) {

                    String USE_STATUS = common.get_json_array_value(jsonArray, "USE_STATUS");

                    if (USE_STATUS.equals("MOBILE") || USE_STATUS.equals("PC-WEB")) {
                        String PACKAGE_NAME = common.get_json_array_value(jsonArray, "PACKAGE_NAME");
                        String PAY_NO = common.get_json_array_value(jsonArray, "PAY_NO");

                        String MID = "pgveteranm";
                        String SVC_CD = common.get_json_array_value(jsonArray, "SVC_CD");
                        int PAY_PRIC = common.get_json_array_value_int(jsonArray, "PAY_PRIC");
                        int PAY_MTHD = common.get_json_array_value_int(jsonArray, "PAY_MTHD");

                        int nResponseCode_5 = 0;
                        String strResponse_5 = "";
                        JSONObject jsonResponse_5;

                        if (PACKAGE_NAME.equals("com.veteranscout.vendorapp.onestore")) {

                            String strPayNo = PAY_NO;
                            String strCancelCd = "TRD_CANCEL_USER";

                            strSQL = "CALL SP_M_ITEM_INCOME_SEND_SELECT_WEB('" + strPayNo + "') ";

                            jsonArray = common.execute_query_commit(strSQL);
                            common.log(module_name, "jsonArray : " + jsonArray.toString());
                            String strCancelTime = common.get_json_array_value(jsonArray, "CANCEL_TIME");

                            String strUseStatus = "APP";
                            String strOneStoreCancelUrl = "https://m.veteranscout.co.kr/pay_res_app_cancel_time_ok.asp";

                            String strData = "TRANS_SEQ=" + strPayNo + "&CANCEL_CD=" + strCancelCd;

                            common.log(module_name, "strOneStoreCancelUrl : " + strOneStoreCancelUrl);
                            common.log(module_name, "strData : " + strData);

                            CloseableHttpClient client = HttpClients.createDefault();
                            HttpPost httpPost = new HttpPost(strOneStoreCancelUrl);

                            httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");

                            StringEntity entity = new StringEntity(strData, "UTF-8");
                            httpPost.setEntity(entity);

                            CloseableHttpResponse response = client.execute(httpPost);

                            int nResponseCode = response.getStatusLine().getStatusCode();
                            String strResponse = response.getStatusLine().toString();

                            common.log(module_name, "strResponse : " + strResponse);

                            HttpEntity responseEntity = response.getEntity();
                            JSONObject jsonResponse = new JSONObject();
                            if (responseEntity != null) {
                                strResponse = EntityUtils.toString(responseEntity);
                                jsonResponse = new JSONObject(strResponse);
                            }
                            common.log(module_name, "nResponseCode : " + String.valueOf(nResponseCode));
                            common.log(module_name, "strResponse : " + strResponse.toString());
                            common.log(module_name, "jsonResponse : " + jsonResponse.toString());
                            client.close();
                        }

                    }

                } else {
                    result = common.get_data_result(jsonArray);
                }

            } else {
                result = common.get_error_message("session error");

            }
        } catch (Exception e) {
            e.printStackTrace();
            result = common.get_message("error", e.getMessage().toString());
        }
        return result;
    }

}