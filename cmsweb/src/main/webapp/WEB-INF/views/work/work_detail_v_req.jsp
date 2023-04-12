<%@page import="org.json.*"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<jsp:include page="/WEB-INF/views/templates/header_pop.jsp"/>

<%
    JSONArray data = (JSONArray)request.getAttribute("data");    
    JSONObject header = (JSONObject)request.getAttribute("header");
    JSONObject data0_0 = data.getJSONArray(0).getJSONObject(0);
    int WORK_ID = header.getInt("WORK_ID");
    int NEED_ID = header.getInt("NEED_ID");
    String WORK_DATE = header.getString("WORK_DATE");
%>

<!-- wrap -->
 <div id="pop_wrap">
	<div class="title">
		베테랑 서비스 요청
		<a href="javascript:OnClose();"><img src="/images/popup_close.gif" alt="" /></a>
	</div>
	<!-- contents -->
	<div class="pop_contents">
	<h3>베테랑 서비스 정보</h3>
	<div class="pt10"></div>
	<table class="write_B" summary="기본정보">
		<colgroup>
			<col width="35%" />
			<col width="65%" />
		</colgroup>
		<tr>
			<th>현장명</th>
			<th>업체명</th>
		</tr>
		<tr>
			<td>
				<%=data0_0.getString("SITE_NAME")%>
			</td>
			<td>
				<%=data0_0.getString("BIZ_NAME")%>
			</td>
		</tr>
		<tr>
			<th>일시/기간</th>
			<td>
				<%=data0_0.getString("S_WORK_DATE")%>
			</td>
		</tr>		
		<tr>
			<th>종목/인원수/인건비</th>
			<td>
				[<%=data0_0.getString("JOB_GBN_NM")%>]<%=data0_0.getString("JOB_TYPE_NM")%>/
				<%=data0_0.getInt("MAN_CNT")%>/
				<%=data0_0.getString("WORK_PAY")%>
			</td>
		</tr>
		<tr>
			<th>인원수</th>
			<td>
				<%=data0_0.getInt("NEED_CNT")%>
			</td>
		</tr>
		<tr>
			<th>전체수수료(인당)</th>
			<td>
				<%=data0_0.getString("FEE_AMT")%>
			</td>
		</tr>
	</table>	
	<div class="pt30"></div>
	<div class="alignright">
		<input type="hidden" id="WORK_ID" name="WORK_ID" value="<%=WORK_ID%>"/>
		<input type="hidden" id="NEED_ID" name="NEED_ID" value="<%=NEED_ID%>"/>
		<input type="hidden" id="WORK_DATE" name="WORK_DATE" value="<%=WORK_DATE%>"/>
		<a class="btn03" href="javascript:OnVeteranReq();">확인</a>
		<a class="btn03" href="javascript:OnClose();">닫기</a>
	</div>

</div>

<script type="text/javascript">
	function OnClose()
	{
		self.close();
	}
	function OnVeteranReq()
	{
		var data = {};
		var aWorkID = $("#WORK_ID").val();
		var aNeedID = $("#NEED_ID").val();
		var aWorkDate = $("#WORK_DATE").val();
		data.WORK_ID = aWorkID;
		data.NEED_ID = aNeedID;
		data.WORK_DATE = aWorkDate;
		$.ajax({
			url: '/work_detail_v_req/req_veteran_service',
            type: 'POST',
            data: JSON.stringify(data),
			contentType: 'application/json',
            success: function(data) 
			{
				Callback_OnVeteranReq(data);
            },
			error: function(err) {
				alert(err.responseText);
			}
		});
	}

	function Callback_OnVeteranReq(result)
	{
		var jsonData = JSON.parse(result);
		if (jsonData.result == "ok")
		{
			alert("베테랑 서비스 요청을 했습니다.");
			window.close();
		}
		else
		{	
			alert(jsonData.message);

		}
	}
</script>

<jsp:include page="/WEB-INF/views/templates/bottom_pop.jsp"/>	