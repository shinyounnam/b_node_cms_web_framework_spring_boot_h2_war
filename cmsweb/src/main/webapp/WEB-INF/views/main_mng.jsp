<%@page import="org.json.*"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<jsp:include page="/WEB-INF/views/templates/header.jsp"/>

<%    
    JSONObject header = (JSONObject)request.getAttribute("header");
%>

<div class="contents">
    
</div>


<jsp:include page="/WEB-INF/views/templates/bottom.jsp"/>	