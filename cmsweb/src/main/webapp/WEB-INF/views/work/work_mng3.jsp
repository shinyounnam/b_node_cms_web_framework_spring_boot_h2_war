<%@page import="org.json.*"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<jsp:include page="/WEB-INF/views/templates/header.jsp"/>

<%
    JSONArray data = (JSONArray)request.getAttribute("data");    
    JSONObject header = (JSONObject)request.getAttribute("header");

    
    String SEARCH_TYPE = header.getString("SEARCH_TYPE");
    String SEARCH_TXT = header.getString("SEARCH_TXT");
    int PAGE = header.getInt("PAGE");
    int PAGE_SIZE = header.getInt("PAGE_SIZE");

    JSONObject obj1_0 = data.getJSONArray(1).getJSONObject(0);
    JSONObject obj2_0 = data.getJSONArray(2).getJSONObject(0);
    JSONObject obj3_0 = data.getJSONArray(3).getJSONObject(0);

	int i = 0;
    JSONObject obj = null;   
%>

<div class="contents">
	<h2>현장 현황 리스트</h2>
	<div class="navi"><a href="">현장 관리</a> &nbsp; > &nbsp; 현장 현황 &nbsp; > &nbsp; <span class="blue">리스트</span></div>
	<div class="pt30"></div>
	<div class="floatleft arrow">총 <span class="blue"> <%=obj2_0.getInt("AVL_WORK_SU")%>&nbsp;(<%=obj2_0.getInt("TOTAL_WORK_SU")%>)</span>개 현장</div>
	<div class="alignright">
		<%
		String searchType1 = "";
		String searchType2 = "";
		if (SEARCH_TYPE.equals("1")) searchType1 = "selected";
		if (SEARCH_TYPE.equals("2")) searchType2 = "selected";
		%>
		<select id="SEARCH_TYPE" name="SEARCH_TYPE">
			<option value="1" <%=searchType1%> >현장명</option>
			<option value="2" <%=searchType2%> >직업소개소명</option>
		</select>
		<input type="hidden" id="PAGE" name="PAGE" value="<%=PAGE%>"/>
		<input type="hidden" id="PAGE_SIZE" name="PAGE_SIZE" value="<%=PAGE_SIZE%>"/>
		<input type="text" id="SEARCH_TXT" name="SEARCH_TXT" value="<%=SEARCH_TXT%>" style="width:100px;"/>
		<a href="javascript:OnSearch()" class="btn01">검색</a>
	</div>
	<div class="pt30"></div>
	<div class="tab01">
		<ul>
			<li><a href="/work_mng">전체</a></li>
			<li><a href="/work_mng1">예약 현장</a></li>
			<li><a href="/work_mng2">진행 현장</a></li>
			<li class="on"><a href="/work_mng3">완료 현장</a></li><div class="font15 floatright bold">Today [<%=obj3_0.getString("TODAY_NM")%>]</div>
		</ul>
	</div>
	<div class="pt30"></div>
	<table class="list_A" summary="현장 현황 리스트">
		<colgroup>
			<col width="5%" />
			<col width="*%" />
			<col width="12%" />
			<col width="13%" />
			<col width="13%" />
			<col width="13%" />
			<col width="6%" />
			<col width="12%" />
		</colgroup>
		<tr>
			<th>NO</th>
			<th>현장 명</th>
			<th>베테랑서비스/<br/>알림</th>
			<th>직업소개소</th>
			<th>직종</th>
			<th>인원/예약/출근</th>
			<th>구분</th>
			<th>해당일</th>
		</tr>
		<%
		
		int nSEQ = 0;
		int nPAGE = PAGE;
		int nPAGE_SIZE = PAGE_SIZE;
		nSEQ = (nPAGE-1)*nPAGE_SIZE;
		int pageIndex = 0;
		int pageSeq = 0;

        JSONArray data0 = data.getJSONArray(0);

		for (i=0;i<data0.length();i++) { 
            
            obj = data0.getJSONObject(i);

		    pageSeq = (obj1_0.getInt("TOTAL_COUNT") - pageIndex) - ((PAGE-1)*15);
		    nSEQ = nSEQ + 1;
		%>
		<tr>
			<td><%=pageSeq%></td>
			<td>[<%=obj.getString("DATE_GBN_NM")%>]&nbsp;<%=obj.getString("SCHEDULE_NAME")%></td>
			<td>
				<% if (obj.getString("EMC_YN").equals("Y")) { %>O<%} else {%>-<%}%>/
				<% if (obj.getString("EMC_ITEM_YN").equals("Y")) { %>O<%} else {%>-<%}%>
			</td>
			<td>
				<%=obj.getString("CORP_NAME")%>
			</td>
			<td>
				<%=obj.getString("JOB_TYPE_NAMES")%>
			</td>
			<td>
				<% if(obj.getInt("TOTAL_SU") > 0) { %><%=obj.getInt("TOTAL_SU")%><%}else{%>-<%}%>&nbsp;/&nbsp;
				<% if(obj.getInt("TOTAL_BOOK_SU") > 0) { %><%=obj.getInt("TOTAL_BOOK_SU")%><%}else{%>-<%}%>&nbsp;/&nbsp;
				<% if(obj.getInt("TOTAL_SHOW_SU") > 0) { %><%=obj.getInt("TOTAL_SHOW_SU")%><%}else{%>-<%}%>&nbsp;
			</td>
			<td>
				<%=obj.getString("GBN_NM")%>
			</td>
			<td>
				<%
				String WORK_DATE_NM = "";
				if(obj.getString("DATE_GBN").equals("1"))
				{
					WORK_DATE_NM = obj.getString("WORK_DATE");
				}
				else if (obj.getString("DATE_GBN").equals("2") || obj.getString("DATE_GBN").equals("3"))
				{
					WORK_DATE_NM = obj.getString("WORK_DATE")+"~<br/>"+obj.getString("TO_WORK_DATE");
				}				
				%>
				<%=WORK_DATE_NM%>
				<a href="javascript:OnWorkDetail('<%=obj.getInt("WORK_ID")%>')" class="btn01">상세</a>
			</td>
		</tr>
		<%
		pageIndex = pageIndex + 1;
		}
		%>
	</table>
	<div class="pt30"></div>	
	<script>
		var totalPage = '<%=obj1_0.getInt("TOTAL_PAGE")%>';
		if (totalPage > 0)
		{
			document.write(PageMake('<%=PAGE%>','<%=PAGE_SIZE%>','<%=obj1_0.getInt("TOTAL_PAGE")%>'));
		}
	</script>
</div>


<script type="text/javascript">
	
		
	function OnPage(aPage)
	{
		var SEARCH_TYPE = $("#SEARCH_TYPE").val();
		var SEARCH_TXT = $("#SEARCH_TXT").val();
		var PAGE_SIZE = $("#PAGE_SIZE").val();
		var PAGE = aPage;
		if (SEARCH_TXT=="") SEARCH_TXT = " ";
		location.href = "/work_mng3/move" +
				"/" + SEARCH_TYPE+
				"/"+SEARCH_TXT+
				"/"+PAGE_SIZE+
				"/"+PAGE;
	}
	

	function OnSearch()
	{
		var SEARCH_TYPE = $("#SEARCH_TYPE").val();
		var SEARCH_TXT = $("#SEARCH_TXT").val();
		if (SEARCH_TXT == "") SEARCH_TXT = " ";
		var PAGE_SIZE = $("#PAGE_SIZE").val();
		var PAGE = $("#PAGE").val();
		if (SEARCH_TXT=="") SEARCH_TXT = " ";
		location.href = "/work_mng3/move" +
				"/" + SEARCH_TYPE+
				"/"+SEARCH_TXT+
				"/"+PAGE_SIZE+
				"/"+PAGE;
	}
	
	function OnWorkDetail(aWorkID)
	{
		var url = "";
		url = "/work_detail/info/" + aWorkID;
		OpenWin(url,"work_detail",900,600);
		
	}

	function OnInsertSchedule()
	{
		location.href="/v_req_schedule_add/info";
	}
</script>


<jsp:include page="/WEB-INF/views/templates/bottom.jsp"/>	