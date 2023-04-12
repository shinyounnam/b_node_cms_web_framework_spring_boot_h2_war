<%@page import="org.json.*"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<jsp:include page="/WEB-INF/views/templates/header.jsp"/>

<%
    JSONArray data = (JSONArray)request.getAttribute("data");    
    JSONObject header = (JSONObject)request.getAttribute("header");

    String S_DATE = header.getString("S_DATE");
    String E_DATE = header.getString("E_DATE");    
    int PAGE = header.getInt("PAGE");
    

    JSONObject obj0_0 = data.getJSONArray(0).getJSONObject(0);    
    JSONObject obj2_0 = data.getJSONArray(2).getJSONObject(0);    
    JSONObject obj = null;   
%>

<!-- contents -->
<!-- contents -->
<div class="contents">
	<h2>기간별 통계</h2>
	<div class="navi"><a href="">통계</a> &nbsp; > &nbsp; <span class="blue">기간별 통계</span></div>
	<div class="pt30"></div>
	<div class="aligncenter"  style=" border-top:1px solid #ebebeb; border-bottom:1px solid #ebebeb; padding:12px 0; ">기간 &nbsp; 
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
		int i = 0;
        JSONArray data1= data.getJSONArray(1);

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
	<script>
		var totalPage = '<%=obj2_0.getInt("TOTAL_PAGE")%>';
		if (totalPage > 0)
		{
			document.write(PageMake('<%=PAGE%>','10','<%=obj2_0.getInt("TOTAL_PAGE")%>'));
		}
	</script>

	<div class="pt10"></div>
	<div style="width:100%;text-align:right;">
		<a href="javascript:OnExcel();" class="btn02" style="margin-left:12px;">근로자 리스트 엑셀 저장</a>
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
		var PAGE = aPage;
		location.href = "/statistics/move" +
				"/" + S_DATE+
				"/"+E_DATE+
				"/"+PAGE;
	}
	

	function OnSearch()
	{
		var S_DATE = $("#S_DATE").val();
		var E_DATE = $("#E_DATE").val();
		var PAGE = "1";
		location.href = "/statistics/move" +
				"/" + S_DATE+
				"/"+E_DATE+
				"/"+PAGE;
	}

	function OnPrint()
	{
		var url = "/stat_report/info/"+$("#S_DATE").val()+"/"+$("#E_DATE").val()+"/";
		OpenWin(url,"stat_report",1000,600);
	}

	function OnExcel()
	{
		var url = "http://xls.veteranscout.co.kr/cms_web/stat_emp_info_list.php?S_DATE="+$("#S_DATE").val();
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

</script>



<jsp:include page="/WEB-INF/views/templates/bottom.jsp"/>	