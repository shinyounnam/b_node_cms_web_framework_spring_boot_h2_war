package com.veteranscout.cmsweb.alarm;

import com.veteranscout.cmsweb.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;
import org.json.*;
import java.net.*;
import java.io.*;
import java.sql.*;


public class MsgSendRunable implements Runnable{
    
    CommonUtil common;

    String module_name = "MsgSendRunable";
    Object jsmMsg = "";
    MsgSend msgServer = new MsgSend();

    public MsgSendRunable(Object msg) {
        common = new CommonUtil();
        jsmMsg = msg;
    }

    @Override
    public void run() {
        String result = "";
        common.log(module_name, jsmMsg.toString());
        result = SendMsg((JSONObject)jsmMsg);
    }   

    private String SendMsg(JSONObject data)
    {
        String result = "";
        try
        {
            JSONObject jsonData = data;
            switch(common.get_json_value(jsonData,"GBN"))
            {
                case "E-E":
                    msgServer.SetEmpMsg(data);
                    break;
                case "B-E":
                    msgServer.SetCorpMsg(data);
                    break;
                case "E-B":
                    msgServer.SetCorpMsg(data);
                    break;
                case "A-E":
                    msgServer.SetAdminMsg(data);
                    break;
                case "A-B":
                    msgServer.SetAdminMsg(data);
                    break;
                case "E-A":
                    msgServer.SetAdminMsg(data);
                    break;
                case "B-A":
                    msgServer.SetAdminMsg(data);
                    break;
                default:
                    common.log(module_name,"SendMsg Error ALARM_TYPE : " + data);
                    break;
            }
        }
        catch (Exception ex)
        {
            common.log(module_name,"SendMsg Error : " + ex.getMessage());
        }
        return result;
    }

}