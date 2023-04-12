<%@page import="org.json.*"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<jsp:include page="/WEB-INF/views/templates/header.jsp"/>

<%
    JSONArray data = (JSONArray)request.getAttribute("data");    
    JSONObject header = (JSONObject)request.getAttribute("header");

    String S_DATE = header.getString("S_DATE");
    String E_DATE = header.getString("E_DATE");
    int EVENT_ID = header.getInt("EVENT_ID");
    String EVENT_NAME = header.getString("EVENT_NAME");
    String APP_VERSION = header.getString("APP_VERSION");
    int PAGE = header.getInt("PAGE");

    int i = 0;
    JSONObject obj = null;

    JSONObject obj1_0 = data.getJSONArray(1).getJSONObject(0);
%>

<!-- contents -->
<!-- contents -->
<div class="contents">
	<h2>이벤트 앱 업데이트 통계</h2>
	<div class="navi"><a href="">통계</a> &nbsp; > &nbsp; <span class="blue">이벤트 앱 업데이트 통계</span></div>
	<div class="pt30"></div>
	<div class="aligncenter"  style=" border-top:1px solid #ebebeb; border-bottom:1px solid #ebebeb; padding:12px 0;">
		이벤트명 &nbsp; 
		<input type="hidden" id="EVENT_ID" name="EVENT_ID" value="<%=EVENT_ID%>"/>&nbsp;&nbsp;
		<input type="text" id="EVENT_NAME" name="EVENT_NAME" value="<%=EVENT_NAME%>" style="width:600px" readonly/>
		<a href="javascript:OnSelectEvent();" class="btn01">이벤트선택</a><br/>
		앱 버전 &nbsp;
		<%
		String strApp1 = "";
		String strApp2 = "";
		String strApp3 = "";
		String strApp4 = "";
		String strApp5 = "";
		String strApp6 = "";
		
		if(APP_VERSION.equals("2.1.02")) strApp1 = "selected";		
		%>
		<select id="APP_VERSION" name="APP_VERSION" style="width:200px">
			<option value="2.1.02" <%=strApp1%>>2.1.02</option>			
		</select>
		<br/>
		기간 &nbsp; 
		<input type="text" id="S_DATE" name="S_DATE" value="<%=S_DATE%>"/>&nbsp;~ &nbsp;
		<input type="text" id="E_DATE" name="E_DATE" value="<%=E_DATE%>"/>&nbsp;&nbsp;
		<a href="javascript:OnSearch();" class="btn01">검색</a>
	</div>

	<div class="pt50"></div>
	<div class="arrow">
	기간  <span class="blue"><script>WriteSDate()</script></span> 부터 <span class="blue"><script>WriteEDate()</script></span> 까지 
	<a href="javascript:OnPrint();" class="btn02" style="margin-left:12px;">리포트 보기</a>
	</div>
	<div class="pt10"></div>
	<div style="width:100%;text-align:left;">
		<span style="color:red;text-size:15px;font-weight:bold;">
			* 선택된 이벤트에서 LMS 발송된 전화번호에 해당하는 앱 버전이 수정된 리스트를 조회합니다.
		</span><br/>
		<span style="color:red;text-size:15px;font-weight:bold;">
			* 직업소개소 APP버전(2.1.02)은 이벤트 알림을 받을 수 있는 버전입니다.
		</span>
	</div>
	<div class="pt10"></div>
	<table class="list_A" summary="가입 신청 현황">
		<colgroup>
			<col width="20%" />
			<col width="10%" />
			<col width="10%" />
			<col width="10%" />
			<col width="10%" />
			<col width="20%" />
		</colgroup>
		<tr>
			<th>APP버전날짜</th>
			<th>이름</th>
			<th>APP버전</th>
			<th>구분</th>
			<th>전화번호</th>
			<th>주소</th>
		</tr>		
		<%
		JSONArray data0 = data.getJSONArray(0);
		for (i=0;i<data0.length();i++) {
            obj = data0.getJSONObject(i);
		%>
		<tr>
			<td><%=obj.getString("LOG_DATE")%></td>
			<td><%=obj.getString("NAME")%></td>
			<td><%=obj.getString("APP_VERSION")%></td>
			<td><%=obj.getString("LINK_TYPE")%></td>
			<td><%=obj.getString("TEL")%></td>
			<td><%=obj.getString("ADDR1")%></td>
		</tr>
		<%
		}
		%>
	</table>
	<script>
		var totalPage = '<%=obj1_0.getInt("TOTAL_PAGE")%>';
		if (totalPage > 0)
		{
			document.write(PageMake('<%=PAGE%>','10','<%=obj1_0.getInt("TOTAL_PAGE")%>'));
		}
	</script>
	<div class="pt10"></div>
	<div style="width:100%;text-align:right;">
		<a href="javascript:OnExcel();" class="btn02" style="margin-left:12px;">엑셀저장</a>
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

	function OnPage(aPage)
	{
		var S_DATE = $("#S_DATE").val();
		var E_DATE = $("#E_DATE").val();
		var EVENT_ID = $("#EVENT_ID").val();
		var APP_VERSION = $("#APP_VERSION").val();
		var PAGE = aPage;
		location.href = "/statistics_v_app_update/move" +
				"/" + S_DATE+
				"/"+E_DATE+
				"/"+EVENT_ID+
				"/"+APP_VERSION+
				"/"+PAGE;
	}
	

	function OnSearch()
	{
		
		var S_DATE = $("#S_DATE").val();
		var E_DATE = $("#E_DATE").val();
		var EVENT_ID = $("#EVENT_ID").val();
		var APP_VERSION = $("#APP_VERSION").val();
		var PAGE = "1";
		location.href = "/statistics_v_app_update/move" +
				"/" + S_DATE+
				"/"+E_DATE+
				"/"+EVENT_ID+
				"/"+APP_VERSION+
				"/"+PAGE;
	}

	function OnPrint()
	{
		var url = "/stat_v_app_update_report/info/"+$("#S_DATE").val()+"/"+$("#E_DATE").val()+"/";
		url += $("#EVENT_ID").val() + "/";
		url += $("#APP_VERSION").val() + "/";
		OpenWin(url,"stat_app_update_report",1000,600);
	}

	function OnExcel()
	{
		var url = j_xls_web_url + "/stat_app_update.php?S_DATE="+$("#S_DATE").val();
		url += "&E_DATE="+$("#E_DATE").val();
		url += "&EVENT_ID="+$("#EVENT_ID").val();
		url += "&APP_VERSION="+$("#APP_VERSION").val();
		url += "&EMP_GBN=CORP";
		location.href = url;
	}
	
	$(document).ready(function() {
		
		$("#S_DATE").datepicker({ showOn: 'button', 
			buttonImageOnly: true, 
			dateFormat:'yy-mm-dd',
			buttonImage: '/images/day.gif' });
		$("#E_DATE").datepicker({ showOn: 'button', 
			buttonImageOnly: true, 
			dateFormat:'yy-mm-dd',
			buttonImage: '/images/day.gif' });
	});

	function OnSelectEvent()
	{
		var url = "/slt_event/info";
		OpenWin(url,"slt_event",1000,600);
	}

	function SetEventId(data)
	{
		var jsonData = JSON.parse(data);
		$("#EVENT_ID").val(jsonData.ID);
		$("#EVENT_NAME").val(jsonData.TITLE);
	}

</script>



<jsp:include page="/WEB-INF/views/templates/bottom.jsp"/>