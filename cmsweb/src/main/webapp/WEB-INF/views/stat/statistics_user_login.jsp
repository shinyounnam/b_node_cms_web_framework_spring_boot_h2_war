<%@page import="org.json.*"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<jsp:include page="/WEB-INF/views/templates/header.jsp"/>

<%
    JSONArray data = (JSONArray)request.getAttribute("data");    
    JSONObject header = (JSONObject)request.getAttribute("header");

    int EVENT_ID = header.getInt("EVENT_ID");
    String EVENT_NAME = "";
    String S_DATE = header.getString("S_DATE");
    String E_DATE = header.getString("E_DATE");
	int PAGE = header.getInt("PAGE");
    JSONObject obj = null;
    JSONObject obj1_0 = data.getJSONArray(1).getJSONObject(0);
%>


<!-- contents -->
<!-- contents -->
<div class="contents">
	<h2>로그인 이벤트 통계</h2>
	<div class="navi"><a href="">통계</a> &nbsp; > &nbsp; <span class="blue">로그인 이벤트 통계</span></div>
	<div class="pt30"></div>
	<div class="aligncenter"  style=" border-top:1px solid #ebebeb; border-bottom:1px solid #ebebeb; padding:12px 0; ">기간 &nbsp; 
		이벤트명 &nbsp; 
		<input type="hidden" id="EVENT_ID" name="EVENT_ID" value="<%=EVENT_ID%>"/>&nbsp;&nbsp;
		<input type="text" id="EVENT_NAME" name="EVENT_NAME" value="<%=EVENT_NAME%>" style="width:600px" readonly/>
		<a href="javascript:OnSelectEvent();" class="btn01">이벤트선택</a><br/>
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
			* 선택된 이벤트에서 LMS 발송된 전화번호에 해당하는 로그인 리스트를 조회합니다.
		</span>
	</div>
	<div class="pt10"></div>
	<table class="list_A" summary="가입 신청 현황">
		<colgroup>
			<col width="20%" />
			<col width="10%" />
			<col width="10%" />
			<col width="10%" />
			<col width="20%" />
		</colgroup>
		<tr>
			<th>로그인 날짜</th>
			<th>이름</th>
			<th>로그인구분</th>
			<th>APP버전</th>
			<th>전화번호</th>
			<th>주소</th>
		</tr>		
		<%
		int i = 0;
        JSONArray data0 = data.getJSONArray(0);
		for (i=0;i<data0.length();i++) {
            obj = data0.getJSONObject(i);
		%>
		<tr>
			<td><%=obj.getString("LOG_DATE")%></td>
			<td><%=obj.getString("NAME")%></td>
			<td><%=obj.getString("LINK_TYPE")%></td>
			<td><%=obj.getString("APP_VERSION")%></td>
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
		<a href="javascript:OnExcel();" class="btn02" style="margin-left:12px;">로그인 이벤트 통계 엑셀 저장</a>
	</div>
	<div class="pt10"></div>
	<div style="width:100%;text-align:right;">
		<a href="javascript:OnSmsExcel();" class="btn02" style="margin-left:12px;">SMS 발송 리스트 엑셀 저장</a>
		<a href="javascript:OnSmsResultExcel();" class="btn02" style="margin-left:12px;">SMS 발송 결과 엑셀 저장</a>
		<a href="javascript:OnSmsErrorExcel();" class="btn02" style="margin-left:12px;">SMS 발송 에러리스트 엑셀 저장</a>
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
		var PAGE = aPage;
		location.href = "/statistics_user_login/move" +
				"/" + S_DATE+
				"/"+E_DATE+
				"/"+EVENT_ID+
				"/"+PAGE;
	}
	

	function OnSearch()
	{		
		var S_DATE = $("#S_DATE").val();
		var E_DATE = $("#E_DATE").val();
		var EVENT_ID = $("#EVENT_ID").val();
		var PAGE = "1";
		location.href = "/statistics_user_login/move" +
				"/" + S_DATE+
				"/"+E_DATE+
				"/"+EVENT_ID+
				"/"+PAGE;
	}

	function OnPrint()
	{
		var url = "/stat_user_login_report/info/"+$("#S_DATE").val()+"/"+$("#E_DATE").val()+"/";
		url += $("#EVENT_ID").val() + "/";
		OpenWin(url,"stat_user_login_report",1000,600);
	}

	function OnExcel()
	{
		var url = j_xls_web_url + "/stat_login_user.php?S_DATE="+$("#S_DATE").val();
		url += "&E_DATE="+$("#E_DATE").val();
		url += "&EVENT_ID="+$("#EVENT_ID").val();
		location.href = url;
	}

	function OnSmsExcel()
	{
		var url = j_xls_web_url + "/stat_sms_list.php?S_DATE="+$("#S_DATE").val();
		url += "&E_DATE="+$("#E_DATE").val();
		url += "&EVENT_ID="+$("#EVENT_ID").val();
		location.href = url;
	}

	function OnSmsResultExcel()
	{
		var url = j_xls_web_url + "/stat_sms_send_stat.php?S_DATE="+$("#S_DATE").val();
		url += "&E_DATE="+$("#E_DATE").val();
		location.href = url;
	}

	function OnSmsErrorExcel()
	{
		var url = j_xls_web_url + "/stat_sms_send_stat_error.php?S_DATE="+$("#S_DATE").val();
		url += "&E_DATE="+$("#E_DATE").val();
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

	function SetEventId(eventId, title)
	{		
		$("#EVENT_ID").val(eventId);
		$("#EVENT_NAME").val(title);
	}

</script>


<jsp:include page="/WEB-INF/views/templates/bottom.jsp"/>	