<%@page import="org.json.*"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<jsp:include page="/WEB-INF/views/templates/header_pop.jsp"/>

<%
    JSONArray data = (JSONArray)request.getAttribute("data");    
    JSONObject header = (JSONObject)request.getAttribute("header");
    String S_DATE = header.getString("S_DATE");
    String E_DATE = header.getString("E_DATE");
    JSONObject obj0_0 = null;
	String TITLE = "";
	if(data.getJSONArray(0).length()>0){
		obj0_0 = data.getJSONArray(0).getJSONObject(0);
		TITLE = obj0_0.getString("TITLE");
	}
	
    JSONObject obj = null;
    int i = 0;

	String[] ARR_S_DATE = S_DATE.split("-");
	String[] ARR_E_DATE = E_DATE.split("-");
%>

<div class="title">
	이벤트 참여 통계 리포트
	<a href="javascript:OnClose();"><img src="/images/popup_close.gif" alt="" /></a>
</div>

<!-- contents -->
<!-- contents -->
<div class="pop_contents">
	
	<div style="text-align:center; font-size:18px; border-top:1px solid #ebebeb; border-bottom:1px solid #ebebeb; padding:12px 0; ">
		<img src="/images/day.gif" alt=""  style="margin-right:10px;"/>
		<span class="blue bold">
			<%=ARR_S_DATE[0]%>년<%=ARR_S_DATE[1]%>월<%=ARR_S_DATE[2]%>일
		</span> 부터 
		<span class="blue bold">
			<%=ARR_E_DATE[0]%>년<%=ARR_E_DATE[1]%>월<%=ARR_E_DATE[2]%>일
		</span> 까지 
		&nbsp;&nbsp;<br/>
		<span style="font-weight:bold;">이벤트명 : <%=TITLE%></span>
	</div>
	<div class="pt30"></div>	
	<table class="list_B" summary="통계 리포트">
		<colgroup>
			<col width="20%" />
			<col width="10%" />
			<col width="10%" />
			<col width="15%" />
			<col width="15%" />
			<col width="20%" />
		</colgroup>
		<tr>
			<th>날짜</th>
			<th>구분</th>
			<th>이름</th>
			<th>로그인구분</th>
			<th>전화번호</th>
			<th>주소</th>
		</tr>		
		<%
		
        JSONArray data1 = data.getJSONArray(1);
		for (i=0;i<data1.length();i++) {
            obj = data1.getJSONObject(i);
		%>
		<tr>
			<td><%=obj.getString("EVENT_DATE")%></td>
			<td><%=obj.getString("LOG_NM")%></td>
			<td><%=obj.getString("NAME")%></td>
			<td><%=obj.getString("LINK_TYPE")%></td>
			<td><%=obj.getString("TEL")%></td>
			<td><%=obj.getString("ADDR1")%></td>
		</tr>
		<%
		}
		%>
	</table>
	<div class="pt30"></div>
	<div class="alignright">
		<a class="btn03" href="javascript:OnPrint();">리포트 출력</a>
		<a class="btn04" href="javascript:OnClose();">닫기</a>
	</div>
</div>
<script type="text/javascript">
	
	var S_DATE = "<%=S_DATE%>";
	var E_DATE = "<%=E_DATE%>";
	
	function WriteSDate()
	{
		var result = S_DATE.substring(0,4)+" 년 "+
			     S_DATE.substring(5,7)+" 월 "+
			     S_DATE.substring(8,10);
	
		document.write(result);
	}
	function WriteEDate()
	{
		var result = E_DATE.substring(0,4)+" 년 "+
			     E_DATE.substring(5,7)+" 월 "+
			     E_DATE.substring(8,10);
	
		document.write(result);
	}
	function OnClose()
	{
		self.close();
	}

	function OnPrint()
	{
		window.print();
	}

</script>

<jsp:include page="/WEB-INF/views/templates/bottom_pop.jsp"/>	