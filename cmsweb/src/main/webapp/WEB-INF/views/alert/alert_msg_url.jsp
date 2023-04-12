<%@page import="org.json.*"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%
    JSONObject header = (JSONArray)request.getAttribute("header");
    String url = header.get("URL").toString();
    String message = header.get("MESSAGE").toString();
%>
<script>
	var url = "<%=url%>";
	alert("<%=message%>");
	if (url=="")
	{
		location.href="/";
	}
	else
	{
		location.href="/<%=url%>";
	}
	
</script>
