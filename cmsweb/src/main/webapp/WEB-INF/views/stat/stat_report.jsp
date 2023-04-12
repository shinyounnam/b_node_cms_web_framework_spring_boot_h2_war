<%@page import="org.json.*"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<jsp:include page="/WEB-INF/views/templates/header_pop.jsp"/>

<%
    JSONArray data = (JSONArray)request.getAttribute("data");    
    JSONObject header = (JSONObject)request.getAttribute("header");
    JSONObject obj0_0 = data.getJSONArray(0).getJSONObject(0);
    int i = 0;
    JSONObject obj = null;
    String S_DATE = header.getString("S_DATE");
    String E_DATE = header.getString("E_DATE");
	String[] ARR_S_DATE = S_DATE.split("-");
	String[] ARR_E_DATE = E_DATE.split("-");
%>

<div class="title">
	통계 리포트
	<a href="javascript:OnClose();"><img src="/images/popup_close.gif" alt="" /></a>
</div>

<!-- contents -->
<!-- contents -->
<div class="pop_contents">
	
	<div style="text-align:center; font-size:18px; border-top:1px solid #ebebeb; border-bottom:1px solid #ebebeb; padding:12px 0; ">
	
	<img src="/images/day.gif" alt=""  style="margin-right:10px;"/>
	<span class="blue bold">
		<%=ARR_S_DATE[0]%>년<%=ARR_S_DATE[1]%>월<%=ARR_S_DATE[2]%>일
	</span> 부터 <span class="blue bold">
		<%=ARR_E_DATE[0]%>년<%=ARR_E_DATE[1]%>월<%=ARR_E_DATE[2]%>일
	</span> 까지
	 
	</div>
	<div class="pt30"></div>
	<table class="list_B" summary="가입 신청 현황">
		<colgroup>
			<col width="*%" />
			<col width="10%" />
			<col width="10%" />
			<col width="10%" />
			<col width="10%" />
			<col width="10%" />
			<col width="10%" />
			<col width="10%" />
			<col width="10%" />
			<col width="10%" />		
		</colgroup>
		<tr>
			<th>날짜(일/월)</th>
			<th colspan="4">가맹점 회원 </th>
			<th  colspan="5">근로자회원</th>
		</tr>
		<tr>
			<th rowspan="2">기간내<br/>누적[전체]</th>
			<th>가입(전체)</th>
			<th>보류</th>
			<th>휴면</th>
			<th><span class="blue">활성화</span></th>
			<th>가입(전체)</th>
			<th>탈퇴</th>
			<th>휴면</th>
			<th><span class="blue">활성화</span></th>
			<th>국내/국외</th>
		</tr>
		<tr>			
			<td><%=obj0_0.getInt("CORP_ATTEND_DATE")%><br/>[<%=obj0_0.getInt("CORP_ATTEND_ALL")%>]</td>
			<td><%=obj0_0.getInt("CORP_WAIT_DATE")%><br/>[<%=obj0_0.getInt("CORP_WAIT_ALL")%>]</td>
			<td><%=obj0_0.getInt("CORP_REST_DATE")%><br/>[<%=obj0_0.getInt("CORP_REST_ALL")%>]</td>
			<td><%=obj0_0.getInt("CORP_ACCEPT_DATE")%><br/>[<%=obj0_0.getInt("CORP_ACCEPT_ALL")%>]</td>

			<td><%=obj0_0.getInt("EMP_ATTEND_DATE")%><br/>[<%=obj0_0.getInt("EMP_ATTEND_ALL")%>]</td>
			<td><%=obj0_0.getInt("EMP_OUT_DATE")%><br/>[<%=obj0_0.getInt("EMP_OUT_ALL")%>]</td>
			<td><%=obj0_0.getInt("EMP_REST_DATE")%><br/>[<%=obj0_0.getInt("EMP_REST_ALL")%>]</td>
			<td><%=obj0_0.getInt("EMP_ACCEPT_DATE")%><br/>[<%=obj0_0.getInt("EMP_ACCEPT_ALL")%>]</td>
			<td><%=obj0_0.getInt("EMP_NATI_IN_ALL")%>/<%=obj0_0.getInt("EMP_NATI_OUT_ALL")%></td>
		</tr>
		<%
		JSONArray data1 = data.getJSONArray(1);
		for (i=0;i<data1.length();i++) {
            obj = data1.getJSONObject(i);
		%>
		<tr>			
			<td><%=obj.getString("STAT_DATE")%></td>
			<td><%=obj.getInt("CORP_ATTEND_ALL")%></td>
			<td><%=obj.getInt("CORP_WAIT_ALL")%></td>
			<td><%=obj.getInt("CORP_REST_ALL")%></td>
			<td><%=obj.getInt("CORP_ACCEPT_ALL")%></td>

			<td><%=obj.getInt("EMP_ATTEND_ALL")%></td>
			<td><%=obj.getInt("EMP_OUT_ALL")%></td>
			<td><%=obj.getInt("EMP_REST_ALL")%></td>
			<td><%=obj.getInt("EMP_ACCEPT_ALL")%></td>
			<td><%=obj.getInt("EMP_NATI_IN_ALL")%>/<%=obj.getInt("EMP_NATI_OUT_ALL")%></td>
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