package com.veteranscout.cmsweb.thread;

import com.veteranscout.cmsweb.util.*;
import org.springframework.beans.factory.annotation.Autowired;  
import java.util.List;
import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;
import org.json.*;
import java.net.*;
import java.io.*;
import java.sql.*;

public class QueueRunnable implements Runnable {

    CommonUtil common = new CommonUtil();
    
    Object jsonData = "";

    public QueueRunnable(Object msg) {
        jsonData = msg;
    }

    @Override
    public void run() {
        System.out.println(jsonData);        
        JSONObject jsonObject = null;
    }   
    
    


}